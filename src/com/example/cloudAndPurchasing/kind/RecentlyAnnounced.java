package com.example.cloudAndPurchasing.kind;

import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class RecentlyAnnounced {

    /**
     * state : 1
     * msg : 查询成功
     * msgCode : null
     * token : null
     * data : [{"MainImg":"http://101.200.167.129:3342/Images/Product/201607261116050.png","CountDown":176,"AnnounceTime":"2016-08-15 16:43:09","ID":"96706436196923529","PNumber":"94","Title":"充电宝（特色）","ProductID":"4678046685809124777","State":2}]
     */

    public int state;
    public String msg;
    public Object msgCode;
    public Object token;
    /**
     * MainImg : http://101.200.167.129:3342/Images/Product/201607261116050.png
     * CountDown : 176
     * AnnounceTime : 2016-08-15 16:43:09
     * ID : 96706436196923529
     * PNumber : 94
     * Title : 充电宝（特色）
     * ProductID : 4678046685809124777
     * State : 2
     */

    public List<DataBean> data;

    public static class DataBean {
        public String MainImg;
        public long CountDown;
        public String AnnounceTime;
        public String ID;
        public String PNumber;
        public String Title;
        public String ProductID;
        public int State;
    }
}
