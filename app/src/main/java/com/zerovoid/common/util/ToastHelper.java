package com.zerovoid.common.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
/**
 * Created by Administrator on 2015/10/14.
 */
public class ToastHelper {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static ToastHelper toast;
    private static Toast toast1 = null;
    private Context context;
    private static Object synObj = new Object();
    public static ToastHelper getInstance() {
        if (toast == null) {
            toast = new ToastHelper();
        }
        return toast;
    }

    public void init(Context context) {
        this.context = context;
    }

    public Toast _toast(String str) {
        return displayToastShort(str);
    }

    /**
     * 显示Toast，duration为short
     *
     * @param context
     *            {@link Context} 当前窗体的上下文
     * @param str
     *            {@link String} 消息主体
     *
     */
    public Toast displayToastShort(String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    /**
     * 显示Toast，duration为long
     *
     * @param context
     *            {@link Context} 当前窗体的上下文
     * @param str
     *            {@link String} 消息主体
     *
     */
    public void displayToastLong(String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
    /**
     * 快速关闭toast
     * 不停的疯狂的点击某个按钮，触发了toast以后，toast内容会一直排着队的显示出来，不能很快的消失。这样可能会影响用户的使用。
     *
     * @param context
     *            {@link Context} 当前窗体的上下文
     * @param str
     *            {@link String} 消息主体
     *
     * */

    public  void displayToastWithQuickClose(
            final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast1 != null) {
                                toast1.setText(str);
                                toast1.setDuration(Toast.LENGTH_SHORT);
                            } else {
                                toast1 = Toast.makeText(context, str,
                                        Toast.LENGTH_SHORT);
                            }
                            toast1.show();
                        }
                    }
                });
            }
        }).start();
    }
}
