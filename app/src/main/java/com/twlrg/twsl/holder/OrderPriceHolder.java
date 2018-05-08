package com.twlrg.twsl.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.twlrg.twsl.R;
import com.twlrg.twsl.entity.OrderInfo;


/**
 * Date:
 */
public class OrderPriceHolder extends RecyclerView.ViewHolder
{


    private TextView mDateTv;
    private EditText mPriceEt;
    private TextView mPriceTypeTv;

    public OrderPriceHolder(View rootView)
    {
        super(rootView);
        mDateTv = (TextView) rootView.findViewById(R.id.tv_date);
        mPriceEt = (EditText) rootView.findViewById(R.id.et_price);
        mPriceTypeTv = (TextView) rootView.findViewById(R.id.tv_price_type);
    }


    public void setOrderInfo(OrderInfo mOrderInfo)
    {
        mDateTv.setText(mOrderInfo.getDate());
        mPriceEt.setText(mOrderInfo.getPrice());
        mPriceEt.setEnabled(mOrderInfo.isPriceModify());
        mPriceTypeTv.setText(getZc(mOrderInfo.getPrice_type()));
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
}
