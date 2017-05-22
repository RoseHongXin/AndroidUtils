package hx.widget.progress;

import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class DialogHelper {

    public static void erasePadding(Dialog dialog, int gravity){
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(gravity); //可设置dialog的位置
            window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
            WindowManager.LayoutParams lp = window.getAttributes();
            DisplayMetrics metrics = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//            lp.width = metrics.widthPixels;   //设置宽度充满屏幕
//            lp.height = metrics.heightPixels / 3;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
            window.setAttributes(lp);
        }
    }

}