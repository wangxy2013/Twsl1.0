package com.twlrg.twsl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twlrg.twsl.R;
import com.twlrg.twsl.entity.OrderInfo;
import com.twlrg.twsl.holder.OrderPriceHolder;
import com.twlrg.twsl.holder.PriceDetailHolder;

import java.util.List;

/**
 */
public class OrderPriceAdapter extends RecyclerView.Adapter<OrderPriceHolder>
{

    private List<OrderInfo> list;

    public OrderPriceAdapter(List<OrderInfo> list)
    {
        this.list = list;
    }

    @Override
    public OrderPriceHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_price, parent, false);
        OrderPriceHolder mHolder = new OrderPriceHolder(itemView);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(OrderPriceHolder holder, int position)
    {
        OrderInfo mOrderInfo = list.get(position);
        holder.setOrderInfo(mOrderInfo);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
