package com.zerovoid.shoppingcart.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.zerovoid.shoppingcart.R;
import com.zerovoid.whmshoppintcart.WhmShoppintCartActivity;
import com.zerovoid.whmshoppintcart.WhmShoppintCartAdapter;


/**
 * 闪屏
 * Created by 绯若虚无（https://github.com/joefei） on 2015/10/12.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        start();
    }

    private void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.finish(); // 结束启动动画界面
                Intent intent = new Intent(SplashActivity.this, WhmShoppintCartActivity.class);
                startActivity(intent);
            }
        }, 3000); // 启动动画持续3秒钟*/
    }
}
