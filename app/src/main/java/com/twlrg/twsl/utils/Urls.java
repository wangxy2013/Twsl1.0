package com.twlrg.twsl.utils;

/**
 * URL管理类
 *
 * @date 2014年9月16日 上午9:48:03
 * @since[产品/模块版本]
 * @seejlj
 */
public class Urls
{
    public static final String HTTP_IP = "http://www.shanglvbuluo.com";

    public static final String BASE_URL = HTTP_IP + "/api/";

    //获取版本信息
    public static String getVersionUrl()
    {
        return BASE_URL + "merchant/search";
    }

    public static String getImgUrl(String url)
    {
        return HTTP_IP + url;
    }

    //用戶登录
    public static String getLoginUrl()
    {
        return BASE_URL + "user/login";
    }


    //用戶头像上传
    public static String getUploadPicUrl()
    {
        return BASE_URL + "user/update_portrait";
    }


    //首页酒店列表
    public static String getHotelListUrl()
    {
        return BASE_URL + "merchant/search";
    }

    //首页酒店列表
    public static String getHotelByKeywordUrl()
    {
        return BASE_URL + "merchant/keyword_title";
    }


    //酒店详细
    public static String getHotelDetailUrl()
    {
        return BASE_URL + "merchant/detail";
    }

    //用户注册
    public static String getRegisterUrl()
    {
        return BASE_URL + "user/register";
    }

    //获取用户信息
    public static String getUserInfoUrl()
    {
        return BASE_URL + "user/user_info";
    }

    //获取短信验证码
    public static String getVerifycodeUrl()
    {
        return BASE_URL + "user/verifycode";
    }

    //修改密码
    public static String getUpdatePwdUrl()
    {
        return BASE_URL + "user/update_pwd";
    }

    //找回密码
    public static String getForgetPwdUrl()
    {
        return BASE_URL + "user/set_pwd";
    }


    //修改用户信息
    public static String getUpdateUserInfoUrl()
    {
        return BASE_URL + "user/update_userinfo";
    }

    //生成订单
    public static String getCreatOrderUrl()
    {
        return BASE_URL + "order/create_order";
    }

    //获取订单列表
    public static String getOrderListUrl()
    {
        return BASE_URL + "order/list_info";
    }

    //获取订单详情
    public static String getOrderDetailUrl()
    {
        return BASE_URL + "order/order_detail";
    }

    //取消订单申请
    public static String getOrderCancelUrl()
    {
        return BASE_URL + "order/order_cancel";
    }

    //订单明细
    public static String getOrderDetailedUrl()
    {
        return BASE_URL + "order/detailed";
    }


    //选择销售
    public static String getSelectSaleUrl()
    {
        return BASE_URL + "order/select_sale";
    }

    //获取销售
    public static String getSaleListUrl()
    {
        return BASE_URL + "order/sale_list";
    }

    //发布评论
    public static String getAddCommentUrl()
    {
        return BASE_URL + "comment/add";
    }


    //获取评论列表
    public static String getCommentListUrl()
    {
        return BASE_URL + "comment/list_info";
    }

    //设施信息
    public static String getFacilitiesUrl()
    {
        return BASE_URL + "merchant/facilities";
    }

    //获取城市信息
    public static String getCityListUrl()
    {
        return BASE_URL + "city/list_info";
    }

    //获取城市信息
    public static String getRegionListUrl()
    {
        return BASE_URL + "city/list_region";
    }


    //获取支付宝支付
    public static String getAlipayUrl()
    {
        return BASE_URL + "alipay/pay";
    }

    //获取微信支付
    public static String getWxpayUrl()
    {
        return BASE_URL + "wxpay/pay";
    }


}
