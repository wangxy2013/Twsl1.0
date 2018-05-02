package com.twlrg.twsl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


import com.twlrg.twsl.R;
import com.twlrg.twsl.fragment.HomeFragment;
import com.twlrg.twsl.fragment.MessageFragment;
import com.twlrg.twsl.fragment.OrderFragment;
import com.twlrg.twsl.fragment.UserCenterFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity
{

    @BindView(android.R.id.tabhost)
    FragmentTabHost fragmentTabHost;

    private String texts[]       = {"首页", "消息", "订单", "我的"};
    private int    imageButton[] = {
            R.drawable.ic_home_selector, R.drawable.ic_message_selector,
            R.drawable.ic_order_selector, R.drawable.ic_user_center_selector};

    UserCenterFragment mUserCenterFragment = new UserCenterFragment();

    private Class fragmentArray[] = {HomeFragment.class, MessageFragment.class, OrderFragment.class, mUserCenterFragment.getClass()};


    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_main);
        setTranslucentStatus();
    }

    @Override
    protected void initEvent()
    {

    }

    @Override
    protected void initViewData()
    {
        fragmentTabHost.setup(this, getSupportFragmentManager(),
                R.id.main_layout);

        for (int i = 0; i < texts.length; i++)
        {
            TabHost.TabSpec spec = fragmentTabHost.newTabSpec(texts[i]).setIndicator(getView(i));

            fragmentTabHost.addTab(spec, fragmentArray[i], null);

            //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
            // fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.main_tab_selector);
        }
        fragmentTabHost.getTabWidget().setDividerDrawable(R.color.transparent);
    }

    private View getView(int i)
    {
        //取得布局实例
        View view = View.inflate(MainActivity.this, R.layout.tabcontent, null);
        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);

        //设置图标
        imageView.setImageResource(imageButton[i]);
        //设置标题
        textView.setText(texts[i]);
        return view;
    }

    public void changeTabStatusColor(int index)
    {

        if (index == 0)
        {
            setStatusBarTextDeep(false);
        }
        else
        {
            setStatusBarTextDeep(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mUserCenterFragment.onActivityResult(requestCode, resultCode, data);
    }

}
