package cn.edu.sdu.drs.util.serarch;

import java.util.ArrayList;

import cn.edu.sdu.drs.bean.resourcesBean.XmlResource;

/**
 * 
 * @author join
 *
 */
public class SortData {
	
	public static void maoPao(ArrayList<XmlResource> Fresult){
		for (int i = Fresult.size() - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				XmlResource xx1 = Fresult.get(j);
				XmlResource xx2 = Fresult.get(j + 1);
				if ((int) xx1.getPrice() < (int) xx2.getPrice()) {
					XmlResource temp;
					temp = Fresult.get(j);
					Fresult.set(j, Fresult.get(j + 1));
					Fresult.set(j + 1, temp);
				}
			}
		}
	}

}
