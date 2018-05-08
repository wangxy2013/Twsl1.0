package com.twlrg.twsl.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twlrg.twsl.R;
import com.twlrg.twsl.adapter.OrderPriceAdapter;
import com.twlrg.twsl.entity.OrderInfo;
import com.twlrg.twsl.http.DataRequest;
import com.twlrg.twsl.http.HttpRequest;
import com.twlrg.twsl.http.IRequestListener;
import com.twlrg.twsl.json.OrderInfoHandler;
import com.twlrg.twsl.json.OrderListHandler;
import com.twlrg.twsl.utils.APPUtils;
import com.twlrg.twsl.utils.ConstantUtil;
import com.twlrg.twsl.utils.ToastUtil;
import com.twlrg.twsl.utils.Urls;
import com.twlrg.twsl.widget.AutoFitTextView;
import com.twlrg.twsl.widget.EmptyDecoration;
import com.twlrg.twsl.widget.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：王先云 on 2018/4/23 13:47
 * 邮箱：wangxianyun1@163.com
 * 描述：订单详情
 */
public class OrderDetailActivity extends BaseActivity implements IRequestListener
{


    @BindView(R.id.topView)
    View            topView;
    @BindView(R.id.iv_back)
    ImageView       ivBack;
    @BindView(R.id.tv_title)
    AutoFitTextView tvTitle;
    @BindView(R.id.tv_merchant)
    AutoFitTextView tvMerchant;
    @BindView(R.id.tv_name)
    TextView        tvName;
    @BindView(R.id.tv_mobile)
    TextView        tvMobile;
    @BindView(R.id.tv_room)
    TextView        tvRoom;
    @BindView(R.id.tv_buynum)
    TextView        tvBuynum;
    @BindView(R.id.tv_time)
    TextView        tvTime;
    @BindView(R.id.tv_days)
    TextView        tvDays;
    @BindView(R.id.tv_order_code)
    TextView        tvOrderCode;
    @BindView(R.id.recyclerView)
    RecyclerView    mRecyclerView;
    @BindView(R.id.tv_pay_status)
    TextView        tvPayStatus;
    @BindView(R.id.tv_cancel_policy)
    TextView        tvCancelPolicy;
    @BindView(R.id.tv_requirement)
    TextView        tvRequirement;
    @BindView(R.id.tv_invoice)
    TextView        tvInvoice;
    @BindView(R.id.tv_is_used)
    TextView        tvIsUsed;
    @BindView(R.id.tv_salesperson)
    TextView        tvSalesperson;
    @BindView(R.id.tv_modify_price)
    TextView        tvModifyPrice;
    @BindView(R.id.btn_contact_customer)
    Button          btnContactCustomer;
    @BindView(R.id.btn_accept)
    Button          btnAccept;
    @BindView(R.id.btn_refuse)
    Button          btnRefuse;
    @BindView(R.id.btn_already)
    Button          btnAlready;


    private String    order_id;
    private OrderInfo mOrderInfo;

    private OrderPriceAdapter mOrderPriceAdapter;
    private List<OrderInfo> mOrderInfoList = new ArrayList<>();
    private boolean isModifyPrice;

    private static final int    REQUEST_LOGIN_SUCCESS    = 0x01;
    public static final  int    REQUEST_FAIL             = 0x02;
    private static final int    GET_ORDER_DETAIL_SUCCESS = 0x04;
    private static final String GET_ORDER_INFO           = "get_order_info";
    private static final String GET_ORDER_DETAIL         = "get_order_detail";

    @SuppressLint("HandlerLeak")
    private BaseHandler mHandler = new BaseHandler(this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_LOGIN_SUCCESS:
                    OrderInfoHandler mOrderInfoHandler = (OrderInfoHandler) msg.obj;
                    mOrderInfo = mOrderInfoHandler.getOrderInfo();
                    getOrderPriceDetail();
                    if (null != mOrderInfo)
                    {

                        tvOrderCode.setText("订单号:" + mOrderInfo.getOrdcode());
                        tvName.setText(mOrderInfo.getOccupant());
                        tvMobile.setText(mOrderInfo.getMobile());
                        tvMerchant.setText(mOrderInfo.getMerchant());

                        tvTime.setText(mOrderInfo.getCheck_in() + "至" + mOrderInfo.getCheck_out());
                        String zc = getZc(mOrderInfo.getPrice_type());
                        tvRoom.setText(mOrderInfo.getTitle() + "[" + zc + "]");
                        tvBuynum.setText(mOrderInfo.getBuynum() + "间");
                        tvDays.setText(mOrderInfo.getDays() + "晚");

                        String payStatus = "未支付";
                        if ("1".equals(mOrderInfo.getPayment_trade_status()))
                        {
                            payStatus = "已支付";
                        }
                        tvPayStatus.setText(payStatus + ",共计RMB: " + mOrderInfo.getTotal_fee() + "(包含服务费)");

                        tvCancelPolicy.setText(mOrderInfo.getCancel_policy());
                        tvInvoice.setText(mOrderInfo.getInvoice());
                        tvRequirement.setText(mOrderInfo.getRemark());
                        int is_used = mOrderInfo.getIs_used();

                        if (is_used == 0)
                        {
                            tvIsUsed.setText("待确认");
                            tvModifyPrice.setVisibility(View.VISIBLE);
                            btnAlready.setVisibility(View.GONE);
                            btnAccept.setVisibility(View.VISIBLE);
                            btnRefuse.setVisibility(View.VISIBLE);
                        }
                        else if (is_used == 1)
                        {
                            tvIsUsed.setText("已接受");
                            tvModifyPrice.setVisibility(View.GONE);

                            btnAccept.setVisibility(View.GONE);
                            btnRefuse.setVisibility(View.GONE);
                            btnAlready.setVisibility(View.VISIBLE);
                            btnAlready.setText("已接受");
                        }
                        else
                        {
                            tvIsUsed.setText("已拒绝");
                            tvModifyPrice.setVisibility(View.GONE);

                            btnAccept.setVisibility(View.GONE);
                            btnRefuse.setVisibility(View.GONE);
                            btnAlready.setVisibility(View.VISIBLE);
                            btnAlready.setText("已拒绝");
                        }

                        tvSalesperson.setText(mOrderInfo.getSalesperson());
                    }

                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(OrderDetailActivity.this, msg.obj.toString());
                    break;
                case GET_ORDER_DETAIL_SUCCESS:
                    OrderListHandler mOrderListHandler = (OrderListHandler) msg.obj;
                    mOrderInfoList.clear();
                    mOrderInfoList.addAll(mOrderListHandler.getOrderInfoList());
                    mOrderPriceAdapter.notifyDataSetChanged();

                    break;

            }
        }
    };

    @Override
    protected void initData()
    {
        order_id = getIntent().getStringExtra("ORDER_ID");
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_order_detail);
        setTranslucentStatus();
    }

    @Override
    protected void initEvent()
    {
        ivBack.setOnClickListener(this);
        tvModifyPrice.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
        btnRefuse.setOnClickListener(this);
        btnAlready.setOnClickListener(this);
        btnContactCustomer.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("我的订单");
        setStatusBarTextDeep(true);
        topView.setVisibility(View.VISIBLE);
        topView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, APPUtils.getStatusBarHeight(this)));
        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new EmptyDecoration(this, ""));

        mOrderPriceAdapter = new OrderPriceAdapter(mOrderInfoList);
        mRecyclerView.setAdapter(mOrderPriceAdapter);

    }


    private void getOrderPriceDetail()
    {
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("order_id", order_id);
        DataRequest.instance().request(OrderDetailActivity.this, Urls.getOrderDetailedUrl(), this, HttpRequest.POST, GET_ORDER_DETAIL, valuePairs,
                new OrderListHandler());
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        showProgressDialog();
        Map<String, String> valuePairs = new HashMap<>();
        valuePairs.put("order_id", order_id);
        DataRequest.instance().request(OrderDetailActivity.this, Urls.getOrderDetailUrl(), this, HttpRequest.POST, GET_ORDER_INFO, valuePairs,
                new OrderInfoHandler());
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ivBack)
        {
            finish();
        }
        else if (v == tvModifyPrice)
        {
            if (isModifyPrice)
            {
                isModifyPrice = false;
                tvModifyPrice.setText("修改价格");
                for (int i = 0; i < mOrderInfoList.size(); i++)
                {
                    mOrderInfoList.get(i).setPriceModify(isModifyPrice);
                }

                mOrderPriceAdapter.notifyDataSetChanged();
            }
            else
            {
                isModifyPrice = true;
                tvModifyPrice.setText("保存");
                for (int i = 0; i < mOrderInfoList.size(); i++)
                {
                    mOrderInfoList.get(i).setPriceModify(isModifyPrice);
                }

                mOrderPriceAdapter.notifyDataSetChanged();
            }
        }
    }


    private String getZc(String price_type)
    {
        String zc = "无早";
        if ("wz".equals(price_type))
        {
            zc = "无早";
        }
        else if ("dz".equals(price_type))
        {
            zc = "单早";
        }
        else if ("sz".equals(price_type))
        {
            zc = "双早";
        }

        return zc;
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        hideProgressDialog();
        if (GET_ORDER_INFO.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_LOGIN_SUCCESS, obj));
            }

            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (GET_ORDER_DETAIL.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_ORDER_DETAIL_SUCCESS, obj));
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
