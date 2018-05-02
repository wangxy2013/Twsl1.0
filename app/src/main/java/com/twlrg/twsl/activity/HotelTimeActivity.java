package com.twlrg.twsl.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.twlrg.twsl.R;
import com.twlrg.twsl.utils.APPUtils;
import com.twlrg.twsl.utils.StringUtils;
import com.twlrg.twsl.widget.AutoFitTextView;
import com.twlrg.twsl.widget.calendar.DayTimeEntity;
import com.twlrg.twsl.widget.calendar.MonthTimeAdapter;
import com.twlrg.twsl.widget.calendar.MonthTimeEntity;
import com.twlrg.twsl.widget.calendar.UpdataCalendar;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import de.greenrobot.event.EventBus;


public class HotelTimeActivity extends BaseActivity
{
    @BindView(R.id.topView)
    View            topView;
    @BindView(R.id.iv_back)
    ImageView       ivBack;
    @BindView(R.id.tv_title)
    AutoFitTextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView        tvSubmit;
    @BindView(R.id.plan_time_txt_start)
    TextView        tvStartTime;
    @BindView(R.id.plan_time_txt_stop)
    TextView        tvStopTime;
    @BindView(R.id.plan_time_calender)
    RecyclerView    mReycycler;

    private       MonthTimeAdapter           adapter;
    private       ArrayList<MonthTimeEntity> datas;
    public static DayTimeEntity              startDay;
    public static DayTimeEntity              stopDay;

    private String chek_in, chek_out;

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {

        setContentView(R.layout.activity_hotel_time);
        setTranslucentStatus();


    }

    @Override
    protected void initEvent()
    {
        EventBus.getDefault().register(this);
        ivBack.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
        setStatusBarTextDeep(true);
        topView.setVisibility(View.VISIBLE);
        topView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, APPUtils.getStatusBarHeight(this)));
        tvTitle.setText("选择时间");
        tvSubmit.setText("确定");
        tvSubmit.setVisibility(View.VISIBLE);


        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,   // 上下文
                        LinearLayout.VERTICAL,  //垂直布局,
                        false);

        mReycycler.setLayoutManager(layoutManager);


        startDay = new DayTimeEntity(0, 0, 0, 0);
        stopDay = new DayTimeEntity(-1, -1, -1, -1);
        datas = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;

        c.add(Calendar.MONTH, 1);
        int nextYear = c.get(Calendar.YEAR);
        int nextMonth = c.get(Calendar.MONTH) + 1;

        datas.add(new MonthTimeEntity(year, month));                //当前月份
        datas.add(new MonthTimeEntity(nextYear, nextMonth));        //下个月
        datas.add(new MonthTimeEntity(nextYear, nextMonth + 1));


        adapter = new MonthTimeAdapter(datas, HotelTimeActivity.this);
        mReycycler.setAdapter(adapter);
    }

    public void onEventMainThread(UpdataCalendar event)
    {
        adapter.notifyDataSetChanged();
        tvStartTime.setText(startDay.getMonth() + "月" + startDay.getDay() + "日");
        chek_in = startDay.getYear() + "-" + startDay.getMonth() + "-" + startDay.getDay();
        if (stopDay.getDay() == -1)
        {
            tvStopTime.setText("结束" + "\n" + "时间");
            chek_out = "";
        }
        else
        {
            tvStopTime.setText(stopDay.getMonth() + "月" + stopDay.getDay() + "日");
            chek_out = stopDay.getYear() + "-" + stopDay.getMonth() + "-" + stopDay.getDay();

        }
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ivBack)
        {
            finish();
        }
        else if (v == tvSubmit)
        {
            if (StringUtils.stringIsEmpty(chek_out))
            {
                return;
            }
            else
            {
                Intent intent = new Intent();
                intent.putExtra("CHEK_IN", chek_in);
                intent.putExtra("CHEK_OUT", chek_out);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
