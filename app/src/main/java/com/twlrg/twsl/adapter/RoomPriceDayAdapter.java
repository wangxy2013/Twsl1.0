package com.twlrg.twsl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twlrg.twsl.R;
import com.twlrg.twsl.entity.RoomDayInfo;
import com.twlrg.twsl.holder.RoomPriceDayHolder;
import com.twlrg.twsl.holder.RoomStatusDayHolder;
import com.twlrg.twsl.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class RoomPriceDayAdapter extends RecyclerView.Adapter<RoomPriceDayHolder>
{

    private List<RoomDayInfo>   list;
    private Context             mContext;
    private MyItemClickListener listener;

    public RoomPriceDayAdapter(List<RoomDayInfo> list, Context mContext, MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public RoomPriceDayHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_price_day, parent, false);
        RoomPriceDayHolder mHolder = new RoomPriceDayHolder(itemView, listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(RoomPriceDayHolder holder, int position)
    {
        RoomDayInfo mRoomDayInfo = list.get(position);
        holder.setData(mRoomDayInfo, position);
    }

    @Override
    public int getItemCount()
    {

        return list.size();


    }
}
