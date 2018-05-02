package com.twlrg.twsl.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.twlrg.twsl.R;
import com.twlrg.twsl.http.DataRequest;
import com.twlrg.twsl.http.HttpRequest;
import com.twlrg.twsl.http.IRequestListener;
import com.twlrg.twsl.json.ResultHandler;
import com.twlrg.twsl.utils.ConstantUtil;
import com.twlrg.twsl.utils.StringUtils;
import com.twlrg.twsl.utils.ToastUtil;
import com.twlrg.twsl.utils.Urls;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 作者：王先云 on 2018/4/13 10:28
 * 邮箱：wangxianyun1@163.com
 * 描述：一句话简单描述
 */
public class RegisterActivity extends BaseActivity implements IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_phone)
    EditText  etPhone;
    @BindView(R.id.et_code)
    EditText  etCode;
    @BindView(R.id.tv_get_code)
    TextView  tvGetCode;
    @BindView(R.id.et_pwd)
    EditText  etPwd;
    @BindView(R.id.et_pwd1)
    EditText  etPwd1;
    @BindView(R.id.btn_register)
    Button    btnRegister;

    private static final int    REQUEST_REGISTER_SUCCESS = 0x01;
    public static final  int    REQUEST_FAIL             = 0x02;
    private static final int    GET_CODE_SUCCESS         = 0x03;
    private static final String USER_REGISTER            = "user_register";
    private static final String GET_CODE                 = "GET_CODE";


    @SuppressLint("HandlerLeak")
    private BaseHandler mHandler = new BaseHandler(this)
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {


                case REQUEST_REGISTER_SUCCESS:
                    ToastUtil.show(RegisterActivity.this, "注册成功!");
                    finish();
                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(RegisterActivity.this, msg.obj.toString());
                    break;

                case GET_CODE_SUCCESS:
                    ToastUtil.show(RegisterActivity.this, "验证码已发送");
                    break;
            }
        }
    };

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {

        setContentView(R.layout.activity_register);
        setTranslucentStatus();
    }

    @Override
    protected void initEvent()
    {
        ivBack.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    protected void initViewData()
    {

    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ivBack)
        {
            finish();
        }
        else if (v == tvGetCode)
        {
            String phone = etPhone.getText().toString();

            if (StringUtils.stringIsEmpty(phone) || phone.length() < 11)
            {
                ToastUtil.show(this, "请输入正确的手机号");
                return;
            }

            Map<String, String> valuePairs = new HashMap<>();
            valuePairs.put("mobile",phone);
            DataRequest.instance().request(RegisterActivity.this, Urls.getVerifycodeUrl(), this, HttpRequest.POST, GET_CODE, valuePairs,
                    new ResultHandler());
        }
        else if (v == btnRegister)
        {
            String phone = etPhone.getText().toString();
            String code = etCode.getText().toString();
            String pwd = etPwd.getText().toString();
            String pwd1 = etPwd1.getText().toString();


            if (StringUtils.stringIsEmpty(phone) || phone.length() < 11)
            {
                ToastUtil.show(this, "请输入正确的手机号");
                return;
            }
            if (StringUtils.stringIsEmpty(code))
            {
                ToastUtil.show(this, "请输入验证码");
                return;
            }
            if (StringUtils.stringIsEmpty(pwd) || pwd.length() < 8)
            {
                ToastUtil.show(this, "请输入8-16密码");
                return;
            }

            if (!pwd.equals(pwd1))
            {
                ToastUtil.show(this, "两次密码输入不一致");
                return;
            }

            Map<String, String> valuePairs = new HashMap<>();
            valuePairs.put("nickname",phone);
            valuePairs.put("mobile",phone);
            valuePairs.put("pwd",pwd);
            valuePairs.put("role","1");
            valuePairs.put("sex","0");
            valuePairs.put("verifycode",code);
            DataRequest.instance().request(RegisterActivity.this, Urls.getRegisterUrl(), this, HttpRequest.POST, USER_REGISTER, valuePairs,
                    new ResultHandler());


        }
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        if (USER_REGISTER.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_REGISTER_SUCCESS, obj));
            }

            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }

        if (GET_CODE.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_CODE_SUCCESS, obj));
            }

            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }


    }
}
