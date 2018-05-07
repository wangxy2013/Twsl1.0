package com.twlrg.twsl.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twlrg.twsl.R;
import com.twlrg.twsl.activity.AddCommentActivity;
import com.twlrg.twsl.entity.OrderInfo;
import com.twlrg.twsl.listener.MyItemClickListener;
import com.twlrg.twsl.widget.AutoFitTextView;


/**
 * Date:
 */
public class OrderHolder extends RecyclerView.ViewHolder
{


    private AutoFitTextView mHotelNameTv;
    private TextView        mTypeTv;
    private TextView        mTitleTv;
    private TextView        mRoomNumberTv;
    private TextView        mNameTv;
    private TextView        mCreateTimeTv;
    private TextView        mOrderCodeTv;
    private TextView        mDaysTv;
    private LinearLayout    mItemLayout;

    private MyItemClickListener listener1;

    public OrderHolder(View rootView, MyItemClickListener listener1)
    {
        super(rootView);
        mDaysTv = (TextView) rootView.findViewById(R.id.tv_days);
        mOrderCodeTv = (TextView) rootView.findViewById(R.id.tv_order_code);
        mHotelNameTv = (AutoFitTextView) rootView.findViewById(R.id.tv_merchant);
        mTypeTv = (TextView) rootView.findViewById(R.id.tv_type);
        mTitleTv = (TextView) rootView.findViewById(R.id.tv_title);
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
        mCreateTimeTv = (TextView) rootView.findViewById(R.id.tv_create_time);
        mRoomNumberTv = (TextView) rootView.findViewById(R.id.tv_room_number);
        mItemLayout = (LinearLayout) rootView.findViewById(R.id.ll_item);
        this.listener1 = listener1;
    }


    public void setOrderInfo(final OrderInfo mOrderInfo, final Context mContext, final int p)
    {
        String price_type = mOrderInfo.getPrice_type();
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


        mHotelNameTv.setText(mOrderInfo.getMerchant());
        mTypeTv.setText("￥" + mOrderInfo.getTotal_fee());
        mTitleTv.setText(mOrderInfo.getTitle() + "[" + zc + "]");
        mNameTv.setText(mOrderInfo.getName() + " " + mOrderInfo.getCheck_in());
        mCreateTimeTv.setText(mOrderInfo.getCreate_time());
        mOrderCodeTv.setText("订单号:" + mOrderInfo.getOrdcode());
        mRoomNumberTv.setText(mOrderInfo.getBuynum() + "间");
        mDaysTv.setText(mOrderInfo.getDays() + "晚");
        mItemLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener1.onItemClick(v, p);
            }
        });


    }

}
