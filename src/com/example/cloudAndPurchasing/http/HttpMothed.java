package com.example.cloudAndPurchasing.http;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.example.cloudAndPurchasing.ase.AESUtils;
import com.example.cloudAndPurchasing.kind.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class HttpMothed {
    public static String authcode(String response, Context context) {
        String code = null;

        return code;
    }

    /**
     * 登陆信息解析
     * @param vb1
     * @param applicationContext
     * @return
     */
    public static ArrayList<Single> loadingjson(String vb1, Context applicationContext,String pass) {
        ArrayList<Single> arrayList = new ArrayList<Single>();
        try {
            JSONObject jsonObject = new JSONObject(vb1);
            Single single = new Single();
            single.setYesoron(jsonObject.getString("msg"));
            String  db = jsonObject.getString("data");
            String vb = AESUtils.decrypt(db);
            Log.i("this",vb+"jdslk");
            SaveShared.init(applicationContext,vb,jsonObject.getString("token"),pass);
//            SqLiteAddDelete.insert(vb,applicationContext,jsonObject.getString("token"),pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 类别数据解析
     * @param activity
     * @param content
     * @return
     */
    public static ArrayList<Category> catrgory(Context activity, String content) {
        ArrayList<Category> arrayList = new ArrayList<Category>();
        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Category category = new Category();
                category.setCategoryname(jsonObject1.getString("Name"));
                category.setCoding(jsonObject1.getString("Code"));
                category.setCategoryid(jsonObject1.getString("ID"));
                arrayList.add(category);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                Category a;
                if (Integer.parseInt(arrayList.get(i).getCoding()) > Integer.parseInt(arrayList.get(j).getCoding())) {   //比较两个整数的大小
                    a = arrayList.get(i);
                    arrayList.set(i, arrayList.get(j));
                    arrayList.set(j, a);
                }
            }

        }
        return arrayList;
    }


    /**
     *
     *房间商品数据解析
     * @param activity
     * @param data
     * @return
     */
    public static ArrayList<Goods> goodscateoryjson(Context activity, String data) {
        ArrayList<Goods> arrayList = new ArrayList<Goods>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Goods goods = new Goods();
                goods.setGoodsid(jsonObject.getString("ID"));
                goods.setGoodsname(jsonObject.getString("Title"));
                goods.setGoodsallpople(jsonObject.getString("TotalCount"));
                goods.setGoods_current(jsonObject.getString("TradingCount"));
                goods.setProductID(jsonObject.getString("ProductID"));
                goods.setState(jsonObject.getString("State"));
                goods.setGoodsxian(jsonObject.getString("LimitCount"));
                goods.setGoods_surplus(jsonObject.getString("LeftCount"));
                goods.setImagepath(jsonObject.getString("MainImg"));
                goods.setGoodspnumber(jsonObject.getString("PNumber"));
                arrayList.add(goods);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * json解room数据
     * @param activity
     * @param data
     * @return
     */
    public static ArrayList<Rooms> jsonroomdata(Context activity, String data) {
        ArrayList<Rooms> arrayList = new ArrayList<Rooms>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int c = 0; c < jsonArray.length(); c++) {
                JSONObject jsonObject = jsonArray.getJSONObject(c);
                Rooms rooms = new Rooms();
                rooms.setRoomgoodsallnumber(jsonObject.getString("TotalCount"));
                rooms.setRoomgoodssurplus(jsonObject.getString("LeftCount"));
                rooms.setTradingCount(jsonObject.getString("TradingCount"));
                rooms.setPNumber(jsonObject.getString("PNumber"));
                rooms.setRoomgoodsname(jsonObject.getString("Title"));
                rooms.setRoomod(jsonObject.getString("RID"));
                rooms.setRoomtimes(jsonObject.getString("Duration"));
                rooms.setProductID(jsonObject.getString("ProductID"));
                rooms.setID(jsonObject.getString("ID"));
                rooms.setLimitCount(jsonObject.getString("LimitCount"));
                rooms.setCreateuserID(jsonObject.getString("CreateUserID"));
                rooms.setRoomsimnagepath(jsonObject.getString("MainImg"));
                arrayList.add(rooms);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    /**
     * 广告图片获取
     * @param context
     * @param data
     * @return
     */
    public static ArrayList<Image> banner(Context context,String data) {
        ArrayList<Image> arrayList = new ArrayList<Image>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(data);
            for (int c = 0; c<jsonArray.length(); c++){
                JSONObject jsonObject = jsonArray.getJSONObject(c);
                Image image = new Image();
                image.setShowImg(jsonObject.getString("ShowImg"));
                image.setCategory(jsonObject.getInt("Category"));
                image.setIsEnable(jsonObject.getBoolean("IsEnable"));
                image.setRemark(jsonObject.getString("Remark"));
                arrayList.add(image);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    /**
     * 首页即将揭晓数据解析
     * @param activity
     * @param data
     * @return
     */
    public static ArrayList<Publish> newgoodsjson(Context activity, String data) {
        ArrayList<Publish> arrayList = new ArrayList<Publish>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int c = 0;c <jsonArray.length();c++){
                JSONObject jsonObject = jsonArray.getJSONObject(c);
                Publish publish = new Publish();
                publish.setQID(jsonObject.getString("ID"));
                publish.setShoppingname(jsonObject.getString("Title"));
                publish.setPublishnumber(jsonObject.getString("PNumber"));
                publish.setTime(String.valueOf(jsonObject.getLong("CountDown")*1000));
                publish.setAnnouncedtime(jsonObject.getString("AnnounceTime"));
                publish.setPublishid(jsonObject.getString("ProductID"));
                publish.setImagepath(jsonObject.getString("MainImg"));
                arrayList.add(publish);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 惊喜无限最新揭晓数据解析
     * @param activity
     * @param data
     * @return
     */
    public static ArrayList<PurchaseDateil> spurisedjson(Context activity, String data) {
        ArrayList<PurchaseDateil> arrayList1 = new ArrayList<PurchaseDateil>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int c = 0 ; c < jsonArray.length(); c++){
                JSONObject jsonObject = jsonArray.getJSONObject(c);
                PurchaseDateil purchaseDateil = new PurchaseDateil();
                purchaseDateil.setTitle(jsonObject.getString("Title"));
                purchaseDateil.setID(jsonObject.getString("ID"));
                purchaseDateil.setAnnouncedtime(jsonObject.getString("AnnouncedTime"));
                purchaseDateil.setPublicDate(String.valueOf(jsonObject.getLong("CountDown")*1000));
                purchaseDateil.setProductID(jsonObject.getString("ProductID"));
                purchaseDateil.setPNumber(jsonObject.getInt("PNumber"));
                purchaseDateil.setLuckyNumber(jsonObject.getString("LuckyNumber"));
                purchaseDateil.setWinnerID(jsonObject.getString("WinnerID"));
                purchaseDateil.setWinner(jsonObject.getString("Winner"));
                purchaseDateil.setTotalCount(jsonObject.getInt("TotalCount"));
                purchaseDateil.setMainImg(jsonObject.getString("MainImg"));
                arrayList1.add(purchaseDateil);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList1;
    }

    /**
     * 商品分类搜索结果
     * @param application
     * @param data
     * @return
     */
    public static ArrayList<Goods> eachdatajson(Context application, String data) {
        ArrayList<Goods> arrayList = new ArrayList<Goods>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int c = 0 ; c<jsonArray.length(); c++){
                JSONObject jsonObject = jsonArray.getJSONObject(c);
                Goods goods = new Goods();
                goods.setGoodsname(jsonObject.getString("Title"));
                goods.setGoodsallpople(jsonObject.getString("TotalCount"));
                goods.setGoods_current(jsonObject.getString("TradingCount"));
                goods.setGoods_surplus(jsonObject.getString("LeftCount"));
                goods.setProductID(jsonObject.getString("ProductID"));
                goods.setGoodsxian(jsonObject.getString("LimitCount"));
                goods.setGoodsid(jsonObject.getString("ID"));
                goods.setState(jsonObject.getString("State"));
                goods.setGoodspnumber(jsonObject.getString("PNumber"));
                goods.setImagepath(jsonObject.getString("MainImg"));
                arrayList.add(goods);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    /**
     * 商品详情解析
     * @param applicationContext
     * @param data
     * @return
     */
    public static GoodsDateils goodsdateiljson(Context applicationContext, String data) {
        GoodsDateils goods = new GoodsDateils();
        List<String> list = new ArrayList();
        try {
            JSONObject jsonObject = new JSONObject(data);
            goods.setTotalCount(jsonObject.getInt("TotalCount"));
            if (!jsonObject.getString("Title").equals("null")){
                goods.setTitle(jsonObject.getString("Title"));
            }else {
                goods.setTitle("");
            }
            goods.setState(jsonObject.getString("State"));
            goods.setPNumber(jsonObject.getInt("PNumber"));
            goods.setStatement(jsonObject.getString("Statement"));
            goods.setID(jsonObject.getString("ID"));
            goods.setPrice(jsonObject.getDouble("Price"));
            goods.setTradingCount(jsonObject.getInt("TradingCount"));
            goods.setLimitCount(jsonObject.getInt("LimitCount"));
            goods.setLeftCount(jsonObject.getString("LeftCount"));
            goods.setProductID(jsonObject.getString("ProductID"));
            JSONArray jsonArray = new JSONArray(jsonObject.getString("ImgList"));
            for (int i = 0 ; i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                list.add(jsonObject1.getString("ShowImg"));
            }
            goods.setShowImg(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return goods;
    }

    /**
     * 消息总数解析
     * @param data
     * @return
     */
    public static Statese messageallnumberjson(Context context,String data) {
        Statese statese = new Statese();
        try {
            JSONObject jsonObject = new JSONObject(data);
            statese.setAddFriendCount(jsonObject.getInt("AddFriendCount"));
            statese.setInviteCount(jsonObject.getInt("inviteCount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return statese;
    }

    /**
     * 最新最快商品解析
     * @param activity
     * @param data
     * @return
     */
    public static ArrayList<Goods> maingoodsjson(FragmentActivity activity, String data) {
        ArrayList<Goods> arrayList = new ArrayList<Goods>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int c = 0; c<jsonArray.length();c++){
                JSONObject jsonObject = jsonArray.getJSONObject(c);
                Goods goods = new Goods();
                goods.setImagepath(jsonObject.getString("MainImg"));
                goods.setGoodsname(jsonObject.getString("Title"));
                goods.setGoodsid(jsonObject.getString("ID"));
                goods.setGoodsallpople(jsonObject.getString("TotalCount"));
                goods.setGoods_surplus(jsonObject.getString("LeftCount"));
                goods.setGoods_current(jsonObject.getString("TradingCount"));
                goods.setGoodsxian(jsonObject.getString("LimitCount"));
                goods.setProductID(jsonObject.getString("ProductID"));
                goods.setGoodspnumber(jsonObject.getString("PNumber"));
                arrayList.add(goods);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 购物车
     * @param data
     * @return
     */
    public static ArrayList<Goods> shoppingcatjson(String data) {
        ArrayList<Goods> arrayList = new ArrayList<Goods>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int c = 0 ; c <jsonArray.length() ; c++){
                JSONObject jsonObject = jsonArray.getJSONObject(c);
                Goods goods = new Goods();
                goods.setProductID(jsonObject.getString("ProductID"));
                goods.setGoodsname(jsonObject.getString("Title"));
                goods.setGoodspnumber(jsonObject.getString("PNumber"));
                goods.setGoodsallpople(jsonObject.getString("TotalCount"));
                goods.setGoods_surplus(jsonObject.getString("LeftCount"));
                goods.setGoodsxian(jsonObject.getString("LimitCount"));
                goods.setGoods_current(jsonObject.getString("TradingCount"));
                goods.setImagepath(jsonObject.getString("MainImg"));
                goods.setID(jsonObject.getString("ID"));
                goods.setCount(jsonObject.getString("Count"));
                goods.setGoodsid(jsonObject.getString("CarID"));
                goods.setGoodsnumber("0");
                goods.setGoodsmoney("1");
                arrayList.add(goods);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    /**
     * 房间商品搜索
     * @param application
     * @param data
     * @return
     */
    public static ArrayList<Goods> eachroomjson(Application application, String data) {
        ArrayList<Goods> arrayList = new ArrayList<Goods>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int c = 0 ; c <jsonArray.length() ; c++){
                JSONObject jsonObject = jsonArray.getJSONObject(c);
                Goods goods = new Goods();
                goods.setGoodsname(jsonObject.getString("Title"));
                goods.setImagepath(jsonObject.getString("MainImg"));
                goods.setProductID(jsonObject.getString("ProductID"));
                goods.setGoodsallpople(jsonObject.getString("TotalCount"));
                goods.setGoods_surplus(jsonObject.getString("LeftCount"));
                goods.setGoods_current(jsonObject.getString("TradingCount"));
                goods.setGoodsxian(jsonObject.getString("LimitCount"));
                goods.setGoodspnumber(jsonObject.getString("PNumber"));
                goods.setGoodsid(jsonObject.getString("ID"));
                arrayList.add(goods);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 惊喜无限商品晒单数据解析
     * @param data
     * @return
     */
    public static ArrayList<Publish> Pulishdateiljson(String data) {
        ArrayList<Publish> arrayList = new ArrayList<Publish>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int c= 0;c<jsonArray.length();c++){
                List<String> list = new ArrayList();
                JSONObject jsonObject = jsonArray.getJSONObject(c);
                Publish publish = new Publish();
                publish.setImagepath(jsonObject.getString("PhotoPath"));
                publish.setImageviewthis(jsonObject.getString("ImgList"));
                publish.setPublishid(jsonObject.getString("PeriodsID"));
                publish.setNickname(jsonObject.getString("Nick"));
                publish.setContent(jsonObject.getString("Content"));
                publish.setTime(jsonObject.getString("PublicDate"));
                publish.setQID(jsonObject.getString("ID"));
                publish.setTitle(jsonObject.getString("Title"));
                publish.setFaverconunt(jsonObject.getString("FavorCount"));
                publish.setProductID(jsonObject.getString("ProductID"));
                publish.setPublishnumber(jsonObject.getString("CommentCount"));
                JSONArray jsonArray2 = new JSONArray(jsonObject.getString("ImgList"));
                if (jsonArray2.length()>0){
                    Log.i("23042398ry","e90239w"+jsonArray2.length());
                    for (int i = 0; i<jsonArray2.length();i++){
                        list.add(jsonArray2.get(i).toString());
                    }
                }
                publish.setArrayList(list);
                arrayList.add(publish);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 查询地址
     * @param application
     * @param data
     * @return
     */
    public static ArrayList<AddressGoods> addressjson(Context application, String data) {
        ArrayList<AddressGoods> arrayList = new ArrayList<AddressGoods>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            Log.i(application+"","fpewfje"+data);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AddressGoods addressGoods = new AddressGoods();
                addressGoods.setPhones(jsonObject.getString("Phone1"));
                addressGoods.setName(jsonObject.getString("Addressee"));
                addressGoods.setAddress(jsonObject.getString("Address1"));
                addressGoods.setAddressid(jsonObject.getString("ID"));
                addressGoods.setCreatetime(jsonObject.getString("CreateTime"));
                addressGoods.setPostCode(jsonObject.getString("PostCode"));
                addressGoods.setDefault(jsonObject.getBoolean("IsDefault"));
                addressGoods.setMiD(jsonObject.getString("MID"));
                arrayList.add(addressGoods);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 公告
     * @param arrayList
     * @param runnable
     * @param data
     * @return
     */
    public static ArrayList<Bulltin> noticehttp(ArrayList<Bulltin> arrayList,Context runnable, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("dataList"));
            for (int c = 0; c<jsonArray.length();c++){
                Bulltin bulltin = new Bulltin();
                JSONObject jsonObject1 = jsonArray.getJSONObject(c);
                bulltin.setID(jsonObject1.getString("ID"));
                bulltin.setState(jsonObject1.getString("State"));
                bulltin.setContent(jsonObject1.getString("Content"));
                bulltin.setTitle(jsonObject1.getString("Title"));
                bulltin.setCreateTime(jsonObject1.getString("CreateTime"));
                arrayList.add(bulltin);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 好友消息
     * @param arrayList
     * @param application
     * @param data
     * @return
     */
    public static ArrayList<Friend> friendnewjson(ArrayList<Friend> arrayList,Context application, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("dataList"));
            for (int i = 0 ; i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Friend friend = new Friend();
                friend.setID(jsonObject1.getString("MemPID"));
                friend.setNickname(jsonObject1.getString("MemPName"));
                friend.setCreatetime(jsonObject1.getString("CreateTime"));
                friend.setImagepath(jsonObject1.getString("PhotoPath"));
                arrayList.add(friend);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 好友信息
     * @param arrayList
     * @param context
     * @param data
     * @return
     */
    public static ArrayList<Friend> friengjson(ArrayList<Friend> arrayList, Context context, String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int c= 0;c<jsonArray.length();c++){
                Log.i("fjowehifods","fwoeifods"+data);
                JSONObject jsonObject = jsonArray.getJSONObject(c);
                Friend friend = new Friend();
                friend.setNickname(jsonObject.getString("NickName"));
                friend.setPhonenumber(jsonObject.getString("MobilePhone"));
                friend.setSex(jsonObject.getString("Gender"));
                friend.setID(jsonObject.getString("MemID"));
                friend.setImagepath(jsonObject.getString("photo"));
                friend.setArea(jsonObject.getString("Area"));
                friend.setSignature(jsonObject.getString("Signature"));
                arrayList.add(friend);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 云购记录json解析
     * @param arrayList
     * @param activity
     * @param data
     * @return
     */
    public static ArrayList<PurchaseDateil> couldthjson(ArrayList<PurchaseDateil> arrayList, Context activity, String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PurchaseDateil purchaseDateil = new PurchaseDateil();
                purchaseDateil.setID(jsonObject.getString("ID"));
                purchaseDateil.setTotalCount(jsonObject.getInt("TotalCount"));
                purchaseDateil.setSurpluspople(jsonObject.getInt("LeftCount"));
                purchaseDateil.setTradingCount(jsonObject.getString("TradingCount"));
                purchaseDateil.setLuckyNumber(jsonObject.getString("LuckyNumber"));
                purchaseDateil.setMainImg(jsonObject.getString("ShowImg"));
                purchaseDateil.setWinnerID(jsonObject.getString("WinnerID"));
                purchaseDateil.setWinner(jsonObject.getString("WinnerName"));
                purchaseDateil.setPNumber(jsonObject.getInt("PNumber"));
                purchaseDateil.setTitle(jsonObject.getString("Title"));
                purchaseDateil.setProductID(jsonObject.getString("ProductID"));
                arrayList.add(purchaseDateil);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 好友数据解析
     * @param arrayList
     * @param data
     * @return
     */
    public static ArrayList<Friend> myfriendjson(ArrayList<Friend> arrayList, String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0;i<jsonArray.length();i++){
                Friend friend = new Friend();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                friend.setNickname(jsonObject.getString("NickName"));
                friend.setID(jsonObject.getString("MemID"));
                friend.setPhonenumber(jsonObject.getString("MobilePhone"));
                friend.setImagepath(jsonObject.getString("photo"));
                friend.setArea(jsonObject.getString("Area"));
                friend.setSex(jsonObject.getString("Gender"));
                friend.setLevel(jsonObject.getString("LevelID"));
                arrayList.add(friend);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    /**
     * 揭晓详情
     * @param purchaseDateil
     * @param data
     * @return
     */
    public static PurchaseDateil Contentjson(PurchaseDateil purchaseDateil, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            purchaseDateil.setPublicDate(jsonObject.getString("CountDown"));
            JSONArray jsonArray = new JSONArray(jsonObject.getString("ImgList"));
            List<String> list = new ArrayList<String>();
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                list.add(jsonObject1.getString("ShowImg"));
            }
            purchaseDateil.setPhotopath_ol(list);
            purchaseDateil.setPNumber(jsonObject.getInt("PNumber"));
            purchaseDateil.setID(jsonObject.getString("ID"));
            purchaseDateil.setSurpluspople(jsonObject.getInt("NextPNumber"));
            purchaseDateil.setUpdatetime(jsonObject.getString("AnnounceTime"));
            purchaseDateil.setTradingCount(jsonObject.getString("TradingCount"));
            purchaseDateil.setTotalCount(jsonObject.getInt("TotalCount"));
            purchaseDateil.setProductID(jsonObject.getString("ProductID"));
            purchaseDateil.setStatement(jsonObject.getString("Statement"));
            purchaseDateil.setTitle(jsonObject.getString("Title"));
            purchaseDateil.setNextPnumber(jsonObject.getString("NextPNumber"));
            purchaseDateil.setNextPID(jsonObject.getString("NextPID"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return purchaseDateil;
    }

    public static Integraton integrtionjsoncontent(Integraton integraton,Context context,String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            integraton.setAvailablePoints(jsonObject.getInt("AvailablePoints"));
            integraton.setCumulativePoints(jsonObject.getInt("CumulativePoints"));
            integraton.setTotalExchangePoints(jsonObject.getInt("TotalExchangePoints"));
            integraton.setMemID(jsonObject.getString("MemID"));
            integraton.setUpdateUser(jsonObject.getString("UpdateUser"));
            integraton.setUpdateTime(jsonObject.getString("UpdateTime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return integraton;
    }

    /**
     * 中奖者信息
     * @param application
     * @param purchaseDateil
     * @param data
     * @return
     */
    public static PurchaseDateil winningjson(Application application, PurchaseDateil purchaseDateil,String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            purchaseDateil.setWinner(jsonObject.getString("UNick"));
            purchaseDateil.setAdress(jsonObject.getString("UAddress"));
            purchaseDateil.setPhotopath(jsonObject.getString("PhotoPath"));
            purchaseDateil.setPublicDate(jsonObject.getString("PublicDate"));
            purchaseDateil.setAllpople(jsonObject.getInt("TotalCount"));
            purchaseDateil.setStatement(jsonObject.getString("Statement"));
            purchaseDateil.setWinnerID(jsonObject.getString("UIPAddress"));
            purchaseDateil.setTradingCount(jsonObject.getString("TradingCount"));
            purchaseDateil.setLuckyNumber(jsonObject.getString("LuckyNumber"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return purchaseDateil;
    }

    /**
     * 中奖记录数据解析
     * @param application
     * @param arrayList
     * @param data
     * @return
     */
    public static ArrayList<Winning> winnjson(Context application, ArrayList<Winning> arrayList, String data) {

        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Winning winning = new Winning();
                winning.setWinninggoodsname(jsonObject.getString("Title"));
                winning.setWinningallman_time(jsonObject.getString("PublishedTime"));
                winning.setWinningid(jsonObject.getString("ProductID"));
                winning.setWinningluckCode(jsonObject.getString("LuckyNumber"));
                winning.setCourierNO(jsonObject.getString("CourierNO"));
                winning.setCourierCompany(jsonObject.getString("CourierCompany"));
                winning.setID(jsonObject.getString("ID"));
                winning.setAID(jsonObject.getString("AID"));
                winning.setAwardState(jsonObject.getString("AwardState"));
                winning.setTotalCount(jsonObject.getString("TotalCount"));
                winning.setShowImg(jsonObject.getString("ShowImg"));
                winning.setPnumber(jsonObject.getInt("PNumber"));
                winning.setAddress(jsonObject.getString("Address"));
                winning.setCustomCode(jsonObject.getString("CustomCode"));
                winning.setIsDelivery(jsonObject.getString("IsDelivery"));
                winning.setTradingCount(jsonObject.getString("TradingCount"));
                arrayList.add(winning);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 回复详情解析
     * @param arrayList
     * @param application
     * @param data
     * @return
     */
    public static ArrayList<Commentes> jsoncontent(ArrayList<Commentes> arrayList, Application application, String data) {

        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Commentes commentes = new Commentes();
                commentes.setCommentesname(jsonObject.getString("Nick"));
                commentes.setCommentescontent(jsonObject.getString("Content"));
                commentes.setCommemtestime(jsonObject.getString("PublicDate"));
                commentes.setCommentpath(jsonObject.getString("PhotoPath"));
                commentes.setCommentesid(jsonObject.getString("ID"));
                commentes.setState(jsonObject.getString("State"));
                arrayList.add(commentes);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 往期揭晓
     * @param application
     * @param arrayList
     * @param data
     * @return
     */
    public static ArrayList<Record> groupbuyjson(Context application, ArrayList<Record> arrayList, String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Record record = new Record();
                record.setRecorduserid(jsonObject.getString("MemID"));
                record.setRecordimage(jsonObject.getString("PhotoPath"));
                record.setRecordnickname(jsonObject.getString("UNick"));
                record.setIP(jsonObject.getString("UIPAddress"));
                record.setRecordpoplenumber(jsonObject.getString("TradingCount"));
                record.setRecordaddress(jsonObject.getString("UAddress"));
                record.setRecordgoodstimepublish(jsonObject.getString("JoinDateTime"));
                arrayList.add(record);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 他人参与解析
     * @param activity
     * @param arrayList
     * @param data
     * @return
     */
    public static ArrayList<PurchaseDateil> whojson(FragmentActivity activity, ArrayList<PurchaseDateil> arrayList, String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PurchaseDateil purchaseDateil = new PurchaseDateil();
                purchaseDateil.setWinner(jsonObject.getString("WinnerName"));
                purchaseDateil.setMainImg(jsonObject.getString("ShowImg"));
                purchaseDateil.setTitle(jsonObject.getString("Title"));
                purchaseDateil.setPNumber(jsonObject.getInt("PNumber"));
                purchaseDateil.setWinnerID(jsonObject.getString("WinnerID"));
                purchaseDateil.setID(jsonObject.getString("ID"));
                if (jsonObject.getString("TotalCount") != "null"){
                    purchaseDateil.setTotalCount(jsonObject.getInt("TotalCount"));
                }
                if (jsonObject.getString("LeftCount") != "null"){
                    purchaseDateil.setSurpluspople(jsonObject.getInt("LeftCount"));
                }
                purchaseDateil.setLuckyNumber(jsonObject.getString("LuckyNumber"));
                purchaseDateil.setTradingCount(jsonObject.getString("TradingCount"));
                purchaseDateil.setProductID(jsonObject.getString("ProductID"));
                purchaseDateil.setUpdateuser(jsonObject.getString("ShowImg"));
                arrayList.add(purchaseDateil);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 往期记录json
     * @param arrayList
     * @param application
     * @param data
     * @return
     */
    public static ArrayList<Record> thistimey(ArrayList<Record> arrayList, Application application, String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Record record = new Record();
                record.setIP(jsonObject.getString("TradeIPAdress"));
                record.setRecordaddress(jsonObject.getString("TradeAddress"));
                record.setID(jsonObject.getString("ID"));
                record.setProductid(jsonObject.getString("ProductID"));
                record.setWinner(jsonObject.getString("Winner"));
                record.setWinnerid(jsonObject.getString("WinnerID"));
                record.setRecordimage(jsonObject.getString("PhotoPath"));
                record.setRecordnickname(jsonObject.getString("MainImg"));
                record.setRecordgoodsname(jsonObject.getString("Title"));
                record.setRecordlucknumber(jsonObject.getString("LuckyNumber"));
                record.setRecordpoplenumber(jsonObject.getString("TotalCount"));
                record.setTradingCount(jsonObject.getString("TradingCount"));
                record.setState(jsonObject.getString("State"));
                record.setPNumber(jsonObject.getString("PNumber"));
                record.setRecordgoodstimepublish(jsonObject.getString("AnnouncedTime"));
                arrayList.add(record);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 反馈信息列表解析
     * @param application
     * @param arrayList
     * @param data
     * @return
     */
    public static ArrayList<Feedback> feedbackjson(Application application, ArrayList<Feedback> arrayList, String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0;i<jsonArray.length();i++){
                Feedback feedback = new Feedback();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                feedback.setID(jsonObject.getString("ID"));
                feedback.setContent(jsonObject.getString("Content"));
                feedback.setCreateTime(jsonObject.getString("CreateTime"));
                feedback.setMemID(jsonObject.getString("MemID"));
                feedback.setReplyRemark(jsonObject.getString("ReplyRemark"));
                feedback.setReplyTime(jsonObject.getString("ReplyTime"));
                arrayList.add(feedback);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 晒单内容解析
     * @param arrayList
     * @param data
     * @return
     */
    public static ArrayList<Publish> allgoodsjson(ArrayList<Publish> arrayList, String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject =  jsonArray.getJSONObject(i);
                Publish share = new Publish();
                share.setNickname(jsonObject.getString("Nick"));
                share.setContent(jsonObject.getString("Content"));
                share.setTitle(jsonObject.getString("Title"));
                share.setProductID(jsonObject.getString("ProductID"));
                share.setPublishid(jsonObject.getString("PeriodsID"));
                share.setQID(jsonObject.getString("ID"));
                share.setFavorCount(jsonObject.getString("FavorCount"));
                share.setCommendnumber(jsonObject.getString("CommentCount"));
                share.setTime(jsonObject.getString("PublicDate"));
                share.setImagepath(jsonObject.getString("ImgList"));
                JSONArray jsonArray1 = new JSONArray(jsonObject.getString("ImgList"));
                List list = new ArrayList();
                for (int j = 0 ;j<jsonArray1.length();j++){
                    list.add(jsonArray1.get(j));
                }
                share.setArrayList(list);
                share.setImageviewthis(jsonObject.getString("PhotoPath"));
                arrayList.add(share);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 往期晒单content
     * @param arrayList
     * @param data
     * @return
     */
    public static ArrayList<Publish> pasrjson(ArrayList<Publish> arrayList, String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Publish publish = new Publish();
                publish.setTime(jsonObject.getString("PublicDate"));
                publish.setTitle(jsonObject.getString("Title"));
                publish.setContent(jsonObject.getString("Content"));
                publish.setCommendnumber(jsonObject.getString("CommentCount"));
                publish.setProductID(jsonObject.getString("ProductID"));
                publish.setPublishid(jsonObject.getString("PeriodsID"));
                publish.setQID(jsonObject.getString("ID"));
                publish.setNickname(jsonObject.getString("Nick"));
                publish.setFavorCount(jsonObject.getString("FavorCount"));
                JSONArray jsonArray1 = new JSONArray(jsonObject.getString("ImgList"));
                List list = new ArrayList();
                for (int j = 0 ;j<jsonArray1.length();j++){
                    list.add(jsonArray1.get(j));
                }
                publish.setArrayList(list);
                publish.setImagepath(jsonObject.getString("ImgList"));
                arrayList.add(publish);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 房间邀请信息解析
     * @param application
     * @param arrayList
     * @param data
     * @return
     */
    public static ArrayList<Friend> askjson(Application application, ArrayList<Friend> arrayList, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("dataList"));
            for (int i = 0;i<jsonArray.length();i++){
                Friend friend = new Friend();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                friend.setID(jsonObject1.getString("MemberID"));
                friend.setNickname(jsonObject1.getString("NickName"));
                friend.setCreatetime(jsonObject1.getString("CreateTime"));
                friend.setCreateUser(jsonObject1.getString("CreateUser"));
                friend.setRemark(jsonObject1.getString("Remark"));
                friend.setRoomID(jsonObject1.getString("RoomID"));
                friend.setRoomName(jsonObject1.getString("RoomName"));
                arrayList.add(friend);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }
}
