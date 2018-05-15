package com.twlrg.twsl.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.twlrg.twsl.R;
import com.twlrg.twsl.adapter.RoomPriceMonthAdapter;
import com.twlrg.twsl.adapter.RoomStatusMonthAdapter;
import com.twlrg.twsl.entity.RoomDayInfo;
import com.twlrg.twsl.entity.RoomMonthInfo;
import com.twlrg.twsl.listener.MyItemClickListener;
import com.twlrg.twsl.listener.MyOnClickListener;
import com.twlrg.twsl.utils.APPUtils;
import com.twlrg.twsl.utils.DialogUtils;
import com.twlrg.twsl.widget.AutoFitTextView;
import com.twlrg.twsl.widget.EmptyDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：王先云 on 2018/5/14 10:20
 * 邮箱：wangxianyun1@163.com
 * 描述：房状维护
 */
public class RoomPriceDetailActivity extends BaseActivity
{
    @BindView(R.id.topView)
    View            topView;
    @BindView(R.id.iv_back)
    ImageView       ivBack;
    @BindView(R.id.tv_title)
    AutoFitTextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView    mRecyclerView;

    private RoomPriceMonthAdapter mRoomPriceMonthAdapter;


    private List<RoomMonthInfo> monthInfoList = new ArrayList<>();

    @Override
    protected void initData()
    {

        RoomMonthInfo mRoomMonthInfo1 = new RoomMonthInfo();
        mRoomMonthInfo1.setYear(2018);
        mRoomMonthInfo1.setMonth(5);

        List<RoomDayInfo> r1 = new ArrayList<>();
        //得到该月份的第一天
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mRoomMonthInfo1.getYear());          //指定年份
        calendar.set(Calendar.MONTH, mRoomMonthInfo1.getMonth() - 1);        //指定月份 Java月份从0开始算
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);             //得到该月份第一天 是星期几
        for (int i = 0; i < dayOfWeek - 1; i++)
        {
            RoomDayInfo mRoomDayInfo = new RoomDayInfo();
            mRoomDayInfo.setYear(2018);
            mRoomDayInfo.setMonth(5);
            mRoomDayInfo.setDay(0);
            r1.add(mRoomDayInfo);
        }
        for (int i = 0; i < 31; i++)
        {
            RoomDayInfo mRoomDayInfo = new RoomDayInfo();
            mRoomDayInfo.setYear(2018);
            mRoomDayInfo.setMonth(5);
            mRoomDayInfo.setDay(i + 1);
            r1.add(mRoomDayInfo);
        }


        RoomMonthInfo mRoomMonthInfo2 = new RoomMonthInfo();
        mRoomMonthInfo2.setYear(2018);
        mRoomMonthInfo2.setMonth(6);

        List<RoomDayInfo> r2 = new ArrayList<>();
        //得到该月份的第一天
        Calendar calendar1 = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mRoomMonthInfo2.getYear());          //指定年份
        calendar1.set(Calendar.MONTH, mRoomMonthInfo2.getMonth() - 1);        //指定月份 Java月份从0开始算
        calendar1.set(Calendar.DAY_OF_MONTH, 1);

        int dayOfWeek1 = calendar1.get(Calendar.DAY_OF_WEEK);             //得到该月份第一天 是星期几
        for (int i = 0; i < dayOfWeek1 - 1; i++)
        {
            RoomDayInfo mRoomDayInfo = new RoomDayInfo();
            mRoomDayInfo.setYear(2018);
            mRoomDayInfo.setMonth(6);
            mRoomDayInfo.setDay(0);
            r2.add(mRoomDayInfo);
        }
        for (int i = 0; i < 30; i++)
        {
            RoomDayInfo mRoomDayInfo = new RoomDayInfo();
            mRoomDayInfo.setYear(2018);
            mRoomDayInfo.setMonth(6);
            mRoomDayInfo.setDay(i + 1);
            r2.add(mRoomDayInfo);
        }


        mRoomMonthInfo1.setRoomDayInfoList(r1);
        mRoomMonthInfo2.setRoomDayInfoList(r2);
        monthInfoList.add(mRoomMonthInfo1);
        monthInfoList.add(mRoomMonthInfo2);

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_room_price_detail);
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
        tvTitle.setText("房价维护");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(RoomPriceDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new EmptyDecoration(RoomPriceDetailActivity.this, ""));
        mRoomPriceMonthAdapter = new RoomPriceMonthAdapter(monthInfoList, RoomPriceDetailActivity.this, new MyOnClickListener.OnClickCallBackListener()
        {
            @Override
            public void onSubmit(int p, int n)
            {
                int day = monthInfoList.get(p).getRoomDayInfoList().get(n).getDay();
                showModifyPriceDialog(String.valueOf(day), String.valueOf(day));
            }
        });

        mRecyclerView.setAdapter(mRoomPriceMonthAdapter);
    }


    private void showModifyPriceDialog(String startTime, String endTime)
    {
        DialogUtils.showRoomPriceDialog(startTime, endTime, RoomPriceDetailActivity.this, new MyOnClickListener.OnSubmitListener()
        {
            @Override
            public void onSubmit(String content)
            {

            }
        });
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
}
