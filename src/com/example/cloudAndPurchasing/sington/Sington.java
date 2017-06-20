package com.example.cloudAndPurchasing.sington;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/23 0023.
 */
public class Sington implements Serializable {
    private static class SingletonHolder {
       /**
       * 单例对象实例
       */
       static final Sington INSTANCE = new Sington();
    }

    public static Sington getInstance() {
        return SingletonHolder.INSTANCE;
    }
    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private Sington() {

    }
    /**
    * readResolve方法应对单例对象被序列化时候
    */
    private Object readResolve() {
        return getInstance();
    }
}
