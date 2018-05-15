package com.twlrg.twsl.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.twlrg.twsl.R;
import com.twlrg.twsl.entity.BillInfo;
import com.twlrg.twsl.entity.CommentInfo;
import com.twlrg.twsl.listener.MyItemClickListener;
import com.twlrg.twsl.utils.Urls;


/**
 * Date:
 */
public class BillHolder extends RecyclerView.ViewHolder
{

    private LinearLayout mItemLayout;
    private TextView     mTotalIncomeTv;
    private TextView     mStatusTv;
    private TextView     mTimeTv;

    private MyItemClickListener listener;

    public BillHolder(View rootView,MyItemClickListener listener)
    {
        super(rootView);
        mTotalIncomeTv = (TextView) rootView.findViewById(R.id.tv_total_income);
        mStatusTv = (TextView) rootView.findViewById(R.id.tv_status);
        mTimeTv = (TextView) rootView.findViewById(R.id.tv_time);
        mItemLayout =(LinearLayout)rootView.findViewById(R.id.ll_item);
        this.listener = listener;
    }


    public void setBillInfo(BillInfo mBillInfo,final int p)
    {
        mItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v,p);
            }
        });
    }

}
