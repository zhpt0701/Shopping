package com.example.cloudAndPurchasing.customcontrol;

import com.example.cloudAndPurchasing.kind.City;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<City> {

	public int compare(City o1, City o2) {
		if (o1.getCitypinyin().equals("@")
				|| o2.getCitypinyin().equals("#")) {
			return -1;
		} else if (o1.getCitypinyin().equals("#")
                || o2.getCitypinyin().equals("@")) {
			return 1;
		} else {
			return o1.getCitypinyin().compareTo(o2.getCitypinyin());
		}
	}

}
