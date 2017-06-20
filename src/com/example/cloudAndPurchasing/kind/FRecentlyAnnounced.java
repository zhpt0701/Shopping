package com.example.cloudAndPurchasing.kind;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class FRecentlyAnnounced {

    /**
     * state : 1
     * msg : 查询成功
     * msgCode : null
     * token : null
     * data : [{"AnnouncedTime":"2016-08-18 10:41:18","MainImg":"http://101.200.167.129:3342/Images/Product/201607261118540.png","TotalCount":55,"TradingCount":1,"CountDown":-2967,"WinnerID":"5313867709007264727","Winner":"梦","LuckyNumber":10000054,"ID":"96742622923596626","PNumber":"212","Title":"特仑苏（特色）","ProductID":"5742996606521002654","State":3},{"AnnouncedTime":"2016-08-18 10:36:31","MainImg":"http://101.200.167.129:3342/Images/Product/201607261118540.png","TotalCount":55,"TradingCount":1,"CountDown":-3254,"WinnerID":"5313867709007264727","Winner":"梦","LuckyNumber":10000025,"ID":"96742622923596344","PNumber":"211","Title":"特仑苏（特色）","ProductID":"5742996606521002654","State":3},{"AnnouncedTime":"2016-08-18 09:32:04","MainImg":"http://101.200.167.129:3342/Images/Product/201608161516230.png","TotalCount":66,"TradingCount":1,"CountDown":-7121,"WinnerID":"5190057357172118286","Winner":"-冯挺将T_T多的说法亚个～","LuckyNumber":10000055,"ID":"96742622923596483","PNumber":"53","Title":"并发专用测试商品","ProductID":"5642416725582385905","State":3},{"AnnouncedTime":"2016-08-18 00:00:45","MainImg":"http://101.200.167.129:3342/Images/Product/201608161516230.png","TotalCount":66,"TradingCount":1,"CountDown":-41400,"WinnerID":"5339971093964090134","Winner":"王先生","LuckyNumber":10000043,"ID":"96742622923596221","PNumber":"52","Title":"并发专用测试商品","ProductID":"5642416725582385905","State":3},{"AnnouncedTime":"2016-08-17 22:10:30","MainImg":"http://101.200.167.129:3342/Images/Product/201608161516230.png","TotalCount":66,"TradingCount":1,"CountDown":-48015,"WinnerID":"5190057357172118286","Winner":"-冯挺将T_T多的说法亚个～","LuckyNumber":10000061,"ID":"96742622923596288","PNumber":"47","Title":"并发专用测试商品","ProductID":"5642416725582385905","State":3},{"AnnouncedTime":"2016-08-17 22:02:41","MainImg":"http://101.200.167.129:3342/Images/Product/201607261118540.png","TotalCount":55,"TradingCount":1,"CountDown":-48484,"WinnerID":"5190057357172118286","Winner":"-冯挺将T_T多的说法亚个～","LuckyNumber":10000052,"ID":"96742622923596095","PNumber":"210","Title":"特仑苏（特色）","ProductID":"5742996606521002654","State":3},{"AnnouncedTime":"2016-08-17 22:02:41","MainImg":"http://101.200.167.129:3342/Images/Product/201608161516230.png","TotalCount":66,"TradingCount":1,"CountDown":-48484,"WinnerID":"5190057357172118286","Winner":"-冯挺将T_T多的说法亚个～","LuckyNumber":10000023,"ID":"96742622923596153","PNumber":"46","Title":"并发专用测试商品","ProductID":"5642416725582385905","State":3},{"AnnouncedTime":"2016-08-17 22:02:40","MainImg":"http://101.200.167.129:3342/Images/Product/201608161516230.png","TotalCount":66,"TradingCount":1,"CountDown":-48485,"WinnerID":"5190057357172118286","Winner":"-冯挺将T_T多的说法亚个～","LuckyNumber":10000001,"ID":"96742622923595888","PNumber":"51","Title":"并发专用测试商品","ProductID":"5642416725582385905","State":3},{"AnnouncedTime":"2016-08-17 21:57:22","MainImg":"http://101.200.167.129:3342/Images/Product/201608161516230.png","TotalCount":66,"TradingCount":7,"CountDown":-48803,"WinnerID":"5190057357172118286","Winner":"-冯挺将T_T多的说法亚个～","LuckyNumber":10000003,"ID":"96742622923596028","PNumber":"45","Title":"并发专用测试商品","ProductID":"5642416725582385905","State":3},{"AnnouncedTime":"2016-08-17 21:53:32","MainImg":"http://101.200.167.129:3342/Images/Product/201607261118540.png","TotalCount":55,"TradingCount":1,"CountDown":-49033,"WinnerID":"5190057357172118286","Winner":"-冯挺将T_T多的说法亚个～","LuckyNumber":10000055,"ID":"96742622923595821","PNumber":"209","Title":"特仑苏（特色）","ProductID":"5742996606521002654","State":3}]
     */

    public int state;
    public String msg;
    public Object msgCode;
    public Object token;
    /**
     * AnnouncedTime : 2016-08-18 10:41:18
     * MainImg : http://101.200.167.129:3342/Images/Product/201607261118540.png
     * TotalCount : 55
     * TradingCount : 1
     * CountDown : -2967
     * WinnerID : 5313867709007264727
     * Winner : 梦
     * LuckyNumber : 10000054
     * ID : 96742622923596626
     * PNumber : 212
     * Title : 特仑苏（特色）
     * ProductID : 5742996606521002654
     * State : 3
     */

    public List<DataBean> data;


    public static class DataBean {
        public String AnnouncedTime;
        public String MainImg;
        public int TotalCount;
        public int TradingCount;
        public Long CountDown;
        public String WinnerID;
        public String Winner;
        public int LuckyNumber;
        public String ID;
        public String PNumber;
        public String Title;
        public String ProductID;
        public int State;
    }

}
