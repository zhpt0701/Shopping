package com.example.cloudAndPurchasing;

import android.support.v4.app.Fragment;
import android.util.Log;
import com.example.cloudAndPurchasing.fragment.*;

import java.util.HashMap;

/**
 * 创建Fragment的工厂类
 *
 * @author zhengping
 */
public class FragmentFactory {

    private static HashMap<Integer, Fragment> savedFragment = new HashMap<Integer, Fragment>();

    public static Fragment getFragment(int position) {
        Fragment fragment = savedFragment.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new MainFragment();
                    break;
                case 1:
                    fragment = new ShoppingFragment();
                    break;
                case 2:
                    fragment = new SurprisedFragment();
                    break;
                case 3:
                    fragment = new ShoppingCarFragment();
                    break;
                case 4:
                    fragment = new MyCloudFragment();
            }
            savedFragment.put(position, fragment);
        }
        return fragment;
    }

}
