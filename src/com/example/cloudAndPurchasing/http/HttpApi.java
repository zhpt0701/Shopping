package com.example.cloudAndPurchasing.http;


/**
 * Created by Administrator on 2016/5/3 0003.
 * 网络请求接口类
 */
public class HttpApi {
    public static String XIAYIWEBVIEW  = "http://101.200.167.129:3343/";
    //apk更新地址
    public static String downloading = "";
    public static String tu = "http://123.57.25.215:3342/Images/";
    public static String pople = "http://101.200.167.129:3353/Images/";
//    public static String yu = "http://123.57.25.215:3341";
    public static String tu_ol = "http://101.200.167.129:3342";
    public static String yu = "http://101.200.167.129:3341";
    public static String tu_ool = "http://101.200.167.129:3342/Images/";
//    public static String yu = "http://101.200.167.129:3351";
    //获取验证码
    public static String single = yu + "/api/Register/RegisterByMobilePhone";
    //注册
    public static String phone_auth_code = yu + "/api/Common/SendMobile?mobile=";
    //登陆
    public static String Loding = yu + "/api/Login/MemberLogin";
    //手机号修改密码
    public static String Alter_password = yu + "/api/Member/UpdatePasswordMobile";
    //广告
    public static String Ad = yu + "/api/Bulletin/GetBilltImagesList";
    //修改昵称
    public static String nickname = yu + "/api/Member/ModifyNickname";
    //修改年龄
    public static String updateage = yu + "/";
    //修改图像
    public static String updatephoto = yu + "/";
    //密码修改密码
    public static String new_password = yu + "/api/Member/ChangePassWord";
    //获取服务器时间
    public static String service_time = yu + "/api/Common/GetServiceTime";
    //获取版本信息
    public static String vertion = yu + "/api/Version/CheckVersion";
    //商品分类
    public static String cartegory = yu + "/api/Category/Get";
    //商品搜索
    public static String goods_each = yu + "/api/Periodicals/SearchTrading";
    //商品分类商品数据
    public static String goods_ol = yu + "/api/Periodicals/TradingByCategory";
    //城市列表
    public static String city_ol = yu + "/api/BaseInfo/GetRegionList";
    //获取房间列表
    public static String room_ol = yu + "/api/Room/Get?uid=";
    //云购房间
    public static String room_each = yu + "/api/Periodicals/RoomProduct";
    //创建房间
    public static String crate_room = yu + "/api/Room/Add";
    //删除房间
    public static String delete_room = yu + "/api/Room/Remove?";
    //修改房间商品
    public static String updata_goods = yu + "/api/Room/Update";
    //惊喜无限最新揭晓
    public static String spurised_pulish_goods = yu + "/api/Periodicals/Traded";
    //惊喜无限房间
    public static String spuris_room = yu + "/api/Room/Get";
    //惊喜无限晒单列表接口
    public static String sperised_share = yu + "/api/Comment/GetMemberCommentList";
    //惊喜无限晒单点赞接口
    public static String zan = yu + "/api/Comment/Praise?id=";
    //惊喜无限晒单评论接口
    public static String comm = yu + "/api/Comment/GetReplyList?id=";
    //晒单评论接口
    public static String publish = yu + "/api/Comment/AddReply";
    //首页最新揭晓
    public static String main_newpublish = yu + "/api/Periodicals/Publishing";
    //首页最新，最快，低价
    public static String main_new_flase = yu + "/api/Periodicals/Trading";
    //商品详情
    public static String goods_dateil = yu + "/api/Periodicals/Deal?id=";
    //图文详情
    public static String image_text = yu + "/api/Products/Teletext?id=";
    //购物车列表
    public static String shopping_catlist = yu + "/api/ShoppingCar/Get?uid=";
    //向购物车添加商品
    public static String add_shoppingcat = yu + "/api/ShoppingCar/Add";
    //移除购物车商品
    public static String delete_shoppingcat = yu + "/api/ShoppingCar/Remove";
    //评论回复接口
    public static String reply = yu + "/api/Comment/Praise?id=";
    //地址
    public static String addresslist = yu + "/api/MemberAddress/GetMyAddressList";
    //删除地址
    public static String deleteaddress = yu + "/api/MemberAddress/DeleteMyAddress";
    //添加地址
    public static String addaddress = yu + "/api/MemberAddress/AddMyAddress";
    //设置默认地址
    public static String address = yu + "/api/MemberAddress/SetDefaultAddress?addressID=";
    //公告
    public static String bulltin = yu + "/api/Bulletin/GetBulletinListInfo";
    //云购邀请好友消息
    public static String counld_news = yu + "/api/MemberRoom/QueryRoomInvite";
    //云购邀请好友消息
//    public static String counld_news = yu + "/api/MemberRoom/GetInvited";
    //云购好友消息处理接口
    public static String counld_news_frend = yu + "/api/MemberRoom/OperateRoomInvite";
    //邀请好友消息
    public static String news = yu + "/api/MemberFriends/QueryFriendInvite";
    //好友消息处理接口
    public static String news_friend = yu + "/api/MemberFriends/OperateFriendInvite";
    //消息总数
    public static String newsnumberall = yu + "/api/MemberFriends/QueryMessageCount";
    //我的积分
    public static String inegarl = yu + "/api/Member/GetMyPoints";
    //余额查询
    public static String balance = yu + "/api/Member/QueryBalance";
    //我的积分兑换
    public static String inefarlexchange = yu + "/api/Member/PointsRecharge?points=";
    //我的中奖记录
    public static String lottery = yu + "/api/OrdersAward/AwardRecord";
    //领奖接口
    public static String award = yu + "/api/OrdersAward/AcceptAward";
    //云购记录所有
    public static String could_all = yu + "/api/OrdersAward/AwardTradedRecord";
    //回复列表
    public static String single_dateil = yu + "/api/Comment/GetReplyList";
    //我的好友
    public static String my_friend = yu + "/api/MemberFriends/QueryMyFriend";
    //添加好友
    public static String add_friend = yu + "/api/MemberFriends/AddMyFriend";
    //订单
    public static String indent = yu + "/api/Order/Create";
    //获取chanal接口
    public static String changal = yu + "/api/Account/Payment";
    //根据手机号码查询会员信息
    public static String firend = yu + "/api/Member/SearchMemByPhone?phoneNo=";
    //余额接口
    public static String sum = yu + "/api/Member/QueryBalance";
    //揭晓
    public static String jiexiao = yu + "/api/Periodicals/TradedDeal?id=";
    //支付成功回调接口
    public static String webhook = yu + "/api/Account/AppPaySuccess";
    //云购房间好友邀请
    public static String please = yu + "/api/MemberRoom/InvitedFriend";
    //中奖者接口
    public static String winning = yu + "/api/Periodicals/GetPeriodsWinner/?id=";
    //晒单
    public static String publish_ol = yu + "/api/Comment/AddComment";
    //计算详情
    public static String count = yu + "/api/Periodicals/PublishResult?id=";
    //上传图像
    public static String thistu = yu + "/api/Member/ModifyMemPhoto";
    //获取
    public static String mytu = yu + "/api/Member/DownLoadMemPhoto?memID=";
    //参与记录
    public static String groupbuy = yu + "/api/Periodicals/TradingRecord";
    //他人参与
    public static String who = yu + "/api/ordersaward/AwardTradedRecord";
    //他人晒单记录
    public static String whopu = yu + "/api/comment/GetMemberCommentList";
    //商品往期记录
    public static String wanqi = yu + "/api/Periodicals/TradedForProduct";
    //意见反馈列表
    public static String feedback = yu + "/api/Opinion/GetOpinionList";
    //新增意见反馈
    public static String addfeedback = yu + "/api/Opinion/AddOpinion";
    //所有晒单接口
    public static String allgoods = yu + "/api/comment/getCommentList";
    //往期晒单
    public static String pastpublish = yu+"/api/Comment/GetProductCommentList";
    //获取幸运码
    public static String luckynumber = yu+"/api/OrdersAward/GetLuckyNumbers";
    //获取商品数量
    public static String goods_number = yu+"/api/shoppingcar/GetCountByPID?id=";
    //获取商品件数
    public static String car_number = yu+"/api/shoppingcar/GetCarCountByUID";
    //签到
    public static String qiandao = yu+"/api/Member/AddMemberSign";
}
