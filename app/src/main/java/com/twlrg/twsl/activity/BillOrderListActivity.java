package com.twlrg.twsl.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twlrg.twsl.R;
import com.twlrg.twsl.adapter.BillOrderAdapter;
import com.twlrg.twsl.entity.BillInfo;
import com.twlrg.twsl.entity.OrderInfo;
import com.twlrg.twsl.http.DataRequest;
import com.twlrg.twsl.http.HttpRequest;
import com.twlrg.twsl.http.IRequestListener;
import com.twlrg.twsl.json.BillListHandler;
import com.twlrg.twsl.json.CommentListHandler;
import com.twlrg.twsl.json.OrderListHandler;
import com.twlrg.twsl.listener.MyItemClickListener;
import com.twlrg.twsl.utils.APPUtils;
import com.twlrg.twsl.utils.ConstantUtil;
import com.twlrg.twsl.utils.ToastUtil;
import com.twlrg.twsl.utils.Urls;
import com.twlrg.twsl.widget.AutoFitTextView;
import com.twlrg.twsl.widget.DividerDecoration;
import com.twlrg.twsl.widget.list.refresh.PullToRefreshBase;
import com.twlrg.twsl.widget.list.refresh.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：王先云 on 2018/5/23 20:46
 * 邮箱：wangxianyun1@163.com
 * 描述：账单支付
 */
public class BillOrderListActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<RecyclerView>, IRequestListener
{
    @BindView(R.id.topView)
    View                      topView;
    @BindView(R.id.iv_back)
    ImageView                 ivBack;
    @BindView(R.id.tv_title)
    AutoFitTextView           tvTitle;
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    @BindView(R.id.tv_noData)
    TextView                  tvNoData;
    @BindView(R.id.tv_total_bill)
    TextView                  tvTotalBill;
    @BindView(R.id.tv_total_order)
    TextView                  tvTotalOrder;

    private RecyclerView mRecyclerView;
    private int pn = 1;
    private int mRefreshStatus;
    private List<OrderInfo> orderInfoList = new ArrayList<>();
    private BillOrderAdapter mBillOrderAdapter;

    private String merchant_id;


    private static final String GET_COMMENT_LIST = "get_comment_list";

    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL    = 0x02;

    @SuppressLint("HandlerLeak")
    private final BaseHandler mHandler = new BaseHandler(BillOrderListActivity.this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    OrderListHandler mOrderListHandler = (OrderListHandler) msg.obj;
                    orderInfoList.addAll(mOrderListHandler.getOrderInfoList());
                    mBillOrderAdapter.notifyDataSetChanged();

                    if (orderInfoList.isEmpty())
                    {
                        tvNoData.setVisibility(View.VISIBLE);
                        mPullToRefreshRecyclerView.setVisibility(View.GONE);
                    }
                    else
                    {
                        tvNoData.setVisibility(View.GONE);
                        mPullToRefreshRecyclerView.setVisibility(View.VISIBLE);
                    }

                    break;

                case REQUEST_FAIL:
                    ToastUtil.show(BillOrderListActivity.this, msg.obj.toString());

                    break;


            }
        }
    };

    @Override
    protected void initData()
    {
        merchant_id = getIntent().getStringExtra("MERCHANT_ID");


        for (int i = 0; i < 10; i++)
        {
            orderInfoList.add(new OrderInfo());
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_bill_order_list);
        setTranslucentStatus();
    }

    @Override
    protected void initEvent()
    {
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        setStatusBarTextDeep(true);
        topView.setVisibility(View.VISIBLE);
        topView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, APPUtils.getStatusBarHeight(this)));
        tvTitle.setText("账单详情");


        mPullToRefreshRecyclerView.setPullLoadEnabled(true);
        mRecyclerView = mPullToRefreshRecyclerView.getRefreshableView();
        mPullToRefreshRecyclerView.setOnRefreshListener(this);
        mPullToRefreshRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(BillOrderListActivity.this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerDecoration(BillOrderListActivity.this));


        mBillOrderAdapter = new BillOrderAdapter(orderInfoList, new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                startActivity(new Intent(BillOrderListActivity.this, BillDetailActivity.class));
            }
        });
        mRecyclerView.setAdapter(mBillOrderAdapter);
        // getBillList();
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        if (v == ivBack)
        {
            finish();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        orderInfoList.clear();
        pn = 1;
        mRefreshStatus = 0;
        getBillList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        pn += 1;
        mRefreshStatus = 1;
        getBillList();
    }

    private void getBillList()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("merchant_id", merchant_id);

        valuePairs.put("page", pn + "");
        DataRequest.instance().request(BillOrderListActivity.this, Urls.getCommentListUrl(), this, HttpRequest.POST, GET_COMMENT_LIST, valuePairs,
                new CommentListHandler());
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        if (mRefreshStatus == 1)
        {
            mPullToRefreshRecyclerView.onPullUpRefreshComplete();
        }
        else
        {
            mPullToRefreshRecyclerView.onPullDownRefreshComplete();
        }

        if (GET_COMMENT_LIST.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
