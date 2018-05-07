package com.twlrg.twsl.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.twlrg.twsl.R;
import com.twlrg.twsl.entity.OrderInfo;
import com.twlrg.twsl.entity.WxPayInfo;
import com.twlrg.twsl.http.DataRequest;
import com.twlrg.twsl.http.HttpRequest;
import com.twlrg.twsl.http.IRequestListener;
import com.twlrg.twsl.json.OrderInfoHandler;
import com.twlrg.twsl.json.OrderListHandler;
import com.twlrg.twsl.json.ResultHandler;
import com.twlrg.twsl.json.WxpayHandler;
import com.twlrg.twsl.listener.MyItemClickListener;
import com.twlrg.twsl.utils.APPUtils;
import com.twlrg.twsl.utils.ConfigManager;
import com.twlrg.twsl.utils.ConstantUtil;
import com.twlrg.twsl.utils.DialogUtils;
import com.twlrg.twsl.utils.PayResult;
import com.twlrg.twsl.utils.StringUtils;
import com.twlrg.twsl.utils.ToastUtil;
import com.twlrg.twsl.utils.Urls;
import com.twlrg.twsl.widget.AutoFitTextView;
import com.twlrg.twsl.widget.CircleImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

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


    private String    order_id;
    private OrderInfo mOrderInfo;


    private static final int REQUEST_LOGIN_SUCCESS = 0x01;
    public static final  int REQUEST_FAIL          = 0x02;

    private static final String GET_ORDER_INFO = "get_order_info";


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

                    if (null != mOrderInfo)
                    {
                        tvTitle.setText("订单号" + mOrderInfo.getOrdcode());
                        tvName.setText(mOrderInfo.getOccupant());
                        tvMobile.setText(mOrderInfo.getMobile());
                        tvMerchant.setText(mOrderInfo.getMerchant());

                        tvTime.setText(mOrderInfo.getCheck_in() + "至" + mOrderInfo.getCheck_out());

                        String zc = getZc(mOrderInfo.getPrice_type());


                        tvRoom.setText(mOrderInfo.getTitle() + "[" + zc + "]");
                        tvBuynum.setText(mOrderInfo.getBuynum() + "间");
                        tvDays.setText(mOrderInfo.getDays() + "晚");


                    }

                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(OrderDetailActivity.this, msg.obj.toString());
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
    }

    @Override
    protected void initViewData()
    {
        setStatusBarTextDeep(true);
        topView.setVisibility(View.VISIBLE);
        topView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, APPUtils.getStatusBarHeight(this)));


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
    }


    private String getZc(String price_type)
    {
        String zc = "无早餐";
        if ("wz".equals(price_type))
        {
            zc = "无早餐";
        }
        else if ("dz".equals(price_type))
        {
            zc = "单早餐";
        }
        else if ("sz".equals(price_type))
        {
            zc = "双早餐";
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
    }

}
