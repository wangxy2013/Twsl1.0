package com.twlrg.twsl.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.twlrg.twsl.R;
import com.twlrg.twsl.adapter.ConferenceManageAdapter;
import com.twlrg.twsl.entity.ConferenceInfo;
import com.twlrg.twsl.http.DataRequest;
import com.twlrg.twsl.http.HttpRequest;
import com.twlrg.twsl.http.IRequestListener;
import com.twlrg.twsl.json.CommentListHandler;
import com.twlrg.twsl.json.ConferenceListHandler;
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
 * 描述：会议室管理
 */
public class ConferenceManageActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<RecyclerView>, IRequestListener
{
    @BindView(R.id.topView)
    View                      topView;
    @BindView(R.id.iv_back)
    ImageView                 ivBack;
    @BindView(R.id.tv_title)
    AutoFitTextView           tvTitle;
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    @BindView(R.id.btn_add)
    Button                    btnAdd;

    private RecyclerView mRecyclerView;
    private int pn = 1;
    private int mRefreshStatus;
    private List<ConferenceInfo> conferenceInfoList = new ArrayList<>();
    private ConferenceManageAdapter mConferenceManageAdapter;

    private String merchant_id;


    private static final String GET_COMMENT_LIST = "get_comment_list";

    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL    = 0x02;

    @SuppressLint("HandlerLeak")
    private final BaseHandler mHandler = new BaseHandler(ConferenceManageActivity.this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    ConferenceListHandler mConferenceListHandler = (ConferenceListHandler) msg.obj;
                    conferenceInfoList.addAll(mConferenceListHandler.getConferenceInfoList());
                    mConferenceManageAdapter.notifyDataSetChanged();

                    break;

                case REQUEST_FAIL:
                    ToastUtil.show(ConferenceManageActivity.this, msg.obj.toString());

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
            conferenceInfoList.add(new ConferenceInfo());
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_conference_manage);
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
        tvTitle.setText("会议室管理");


        mPullToRefreshRecyclerView.setPullLoadEnabled(true);
        mRecyclerView = mPullToRefreshRecyclerView.getRefreshableView();
        mPullToRefreshRecyclerView.setOnRefreshListener(this);
        mPullToRefreshRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ConferenceManageActivity.this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerDecoration(ConferenceManageActivity.this));


        mConferenceManageAdapter = new ConferenceManageAdapter(conferenceInfoList, new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                startActivity(new Intent(ConferenceManageActivity.this, ConferenceDetailActivity.class));
            }
        });
        mRecyclerView.setAdapter(mConferenceManageAdapter);
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
        conferenceInfoList.clear();
        pn = 1;
        mRefreshStatus = 0;
        getRoomList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView)
    {
        pn += 1;
        mRefreshStatus = 1;
        getRoomList();
    }

    private void getRoomList()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("merchant_id", merchant_id);

        valuePairs.put("page", pn + "");
        DataRequest.instance().request(ConferenceManageActivity.this, Urls.getCommentListUrl(), this, HttpRequest.POST, GET_COMMENT_LIST, valuePairs,
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


}
