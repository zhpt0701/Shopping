package com.example.cloudAndPurchasing.sqllite;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class EachTable {
    public static final String TABLE_NAMES="each";
    public static final String CONTENT = "content";
    public static final String TIME_THIS = "times";
    public static final String ID = "id_number";
    public static final String CREATE_TABLES = new StringBuilder()
            .append("CREATE TABLE ")
            .append(TABLE_NAMES)
            .append("(")
            .append(ID)
            .append(" INTEGER PRIMARY KEY,")
            .append(TIME_THIS)
            .append(" TEXT,")
            .append(CONTENT)
            .append(" LONG")
            .append(")")
            .toString();
}
