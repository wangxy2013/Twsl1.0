package com.twlrg.twsl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twlrg.twsl.R;
import com.twlrg.twsl.entity.OrderInfo;
import com.twlrg.twsl.holder.OrderPriceHolder;
import com.twlrg.twsl.holder.PriceDetailHolder;
import com.twlrg.twsl.listener.MyOnClickListener;

import java.util.List;

/**
 */
public class OrderPriceAdapter extends RecyclerView.Adapter<OrderPriceHolder>
{

    private List<OrderInfo>                      list;
    private MyOnClickListener.OnCallBackListener listener;
    private Context                              mContext;

    public OrderPriceAdapter(Context mContext, List<OrderInfo> list, MyOnClickListener.OnCallBackListener listener)
    {
        this.list = list;
        this.listener = listener;
        this.mContext = mContext;
    }

    @Override
    public OrderPriceHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_price, parent, false);
        OrderPriceHolder mHolder = new OrderPriceHolder(itemView, mContext, listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(OrderPriceHolder holder, int position)
    {
        OrderInfo mOrderInfo = list.get(position);
        holder.setOrderInfo(mOrderInfo, position);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
