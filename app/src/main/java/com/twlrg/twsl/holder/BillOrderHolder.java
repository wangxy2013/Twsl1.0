package com.twlrg.twsl.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twlrg.twsl.R;
import com.twlrg.twsl.entity.BillInfo;
import com.twlrg.twsl.entity.OrderInfo;
import com.twlrg.twsl.listener.MyItemClickListener;


/**
 * Date:
 */
public class BillOrderHolder extends RecyclerView.ViewHolder
{

    private RelativeLayout      mItemLayout;
    private TextView            mTotalPriceTv;
    private TextView            mNameTv;
    private TextView            mTimeTv;
    private TextView            mRoomTitleTv;
    private MyItemClickListener listener;

    public BillOrderHolder(View rootView, MyItemClickListener listener)
    {
        super(rootView);
        mTotalPriceTv = (TextView) rootView.findViewById(R.id.tv_total_price);
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
        mTimeTv = (TextView) rootView.findViewById(R.id.tv_time);
        mRoomTitleTv = (TextView) rootView.findViewById(R.id.tv_room_title);
        mItemLayout = (RelativeLayout) rootView.findViewById(R.id.ll_item);
        this.listener = listener;
    }


    public void setOrderInfo(OrderInfo mOrderInfo, final int p)
    {
        mItemLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v, p);
            }
        });
    }

}
