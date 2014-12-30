package jp.kobayo.save100.common;

import android.view.Menu;
import jp.kobayo.save100.R;

/**
 * Created by kobayo on 2014/12/31.
 */
public class MenuManager {

	public static final int MENU_TOP = 1;

	public static final int MENU_END = 2;


	public static void createMenu(Menu menu) {

		menu.add(0,MENU_TOP,1, R.string.top);
		menu.add(0,MENU_END,2,R.string.end);

	}
}
