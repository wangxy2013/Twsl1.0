package com.twlrg.twsl.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.twlrg.twsl.MyApplication;
import com.twlrg.twsl.R;
import com.twlrg.twsl.activity.BaseHandler;
import com.twlrg.twsl.activity.LoginActivity;
import com.twlrg.twsl.activity.MainActivity;
import com.twlrg.twsl.activity.OrderDetailActivity;
import com.twlrg.twsl.adapter.MyViewPagerAdapter;
import com.twlrg.twsl.adapter.OrderAdapter;
import com.twlrg.twsl.entity.OrderInfo;
import com.twlrg.twsl.http.DataRequest;
import com.twlrg.twsl.http.HttpRequest;
import com.twlrg.twsl.http.IRequestListener;
import com.twlrg.twsl.json.OrderListHandler;
import com.twlrg.twsl.listener.MyItemClickListener;
import com.twlrg.twsl.utils.APPUtils;
import com.twlrg.twsl.utils.ConfigManager;
import com.twlrg.twsl.utils.ConstantUtil;
import com.twlrg.twsl.utils.ToastUtil;
import com.twlrg.twsl.utils.Urls;
import com.twlrg.twsl.widget.AutoFitTextView;
import com.twlrg.twsl.widget.EmptyDecoration;
import com.twlrg.twsl.widget.list.refresh.PullToRefreshBase;
import com.twlrg.twsl.widget.list.refresh.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：王先云 on 2018/4/12 16:50
 * 邮箱：wangxianyun1@163.com
 * 描述：订单
 */
public class OrderListFragment extends BaseFragment implements View.OnClickListener
{
    private View rootView = null;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView mSearchIv;//搜索
    private View      mViewLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_order_list, null);
            initData();
            initViews();
            initViewData();
            initEvent();
        }
        // 缓存的rootView需要判断是否已经被加过parent
        // 如果有parent需要从parent删除，否则会发生这个rootView已经有parent的错误
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }


    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    protected void initData()
    {
    }

    @Override
    protected void initViews()
    {
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        mSearchIv = (ImageView) rootView.findViewById(R.id.iv_search);
        mViewLayout = rootView.findViewById(R.id.view);
    }

    @Override
    protected void initEvent()
    {
        mSearchIv.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {
//        int widthPixels = APPUtils.getScreenWidth(getActivity());
//        //设置状态栏高度
//        LinearLayout.LayoutParams topViewParams = new LinearLayout.LayoutParams(widthPixels, APPUtils.getStatusBarHeight(getActivity()));
//        mViewLayout.setLayoutParams(topViewParams);

        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(OrderFragment.newInstance(), "待处理");//添加Fragment
        //viewPagerAdapter.addFragment(OrderFragment.newInstance(), "今日入住");
//        viewPagerAdapter.addFragment(OrderFragment.newInstance(), "全部订单");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器

        mTabLayout.addTab(mTabLayout.newTab().setText("待处理"));//给TabLayout添加Tab
       // mTabLayout.addTab(mTabLayout.newTab().setText("今日入住"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("全部订单"));
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
    }

    @Override
    public void onClick(View v)
    {
        if (v == mSearchIv)
        {
        }
    }
}
