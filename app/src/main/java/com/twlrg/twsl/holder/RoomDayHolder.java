package com.twlrg.twsl.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twlrg.twsl.R;


/**
 *
 */
public class RoomDayHolder extends RecyclerView.ViewHolder
{

    public TextView     mDayTv;      //日期文本

    public RoomDayHolder(View itemView)
    {
        super(itemView);
        mDayTv = (TextView) itemView.findViewById(R.id.tv_day);
    }
}
