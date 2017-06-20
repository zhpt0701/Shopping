package com.example.cloudAndPurchasing.sqllite;

/**
 * Created by Administrator on 2016/5/5 0005.
 */
public class Personal {
    public static final String TABLE_NAMES="personal";
    public static final String NICK_NAME = "nicnkname";
    public static final String NAME = "name";
    public static final String PASS = "pass";
    public static final String TOKEN = "token";
    public static final String PHONE = "moblilbephone";
    public static final String ID = "id_number";
    public static final String LEVELID = "levelid";
    public static final String LOGIN_NAME = "loginname";
    public static final String HEARDERURL = "url";
    public static final String CREATE_TABLES = new StringBuilder()
            .append("CREATE TABLE ")
            .append(TABLE_NAMES)
            .append("(")
            .append(ID)
            .append(" INTEGER PRIMARY KEY,")
            .append(NICK_NAME)
            .append(" TEXT,")
            .append(NAME)
            .append(" TEXT,")
            .append(PASS)
            .append(" TEXT,")
            .append(LOGIN_NAME)
            .append(" TEXT,")
            .append(TOKEN)
            .append(" TEXT,")
            .append(LEVELID)
            .append(" TEXT,")
            .append(PHONE)
            .append(" TEXT,")
            .append(HEARDERURL)
            .append(" TEXT")
            .append(")")
            .toString();
}
