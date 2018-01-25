
package com.cardslider.ui.activity;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardslider.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Splash页
 *
 * @author Kevin 2017/12/28 10:11
 */
public class SplashActivity extends AppCompatActivity {

    private static final int ANIMATIOR_DURATION = 5000;//动画持续时间

    @BindView(R.id.iv_splash)
    ImageView Iv_splash;
    @BindView(R.id.tv_slogan)
    TextView Tv_slogan;
    @BindView(R.id.tv_version)
    TextView Tv_version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 解决初次安装后打开后按home返回后重新打开重启问题。。。
        if (!this.isTaskRoot()) { //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;//finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }

        overridePendingTransition(R.anim.zoomin, 0);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        // 状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        assignViews();

    }

    //分配视图
    private void assignViews() {
        // 设置版本号
        setVersion();
        // 设置标语
        setSlogan();
        // 开启欢迎动画
        startAnimation();
    }

    //开始动画
    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofObject(new FloatEvaluator(), 1.0f, 1.2f);
        animator.setDuration(ANIMATIOR_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                if (value != 1.2f) {
                    Iv_splash.setScaleX(value);
                    Iv_splash.setScaleY(value);
                } else {
                    goToActivity();
                }
            }

            private void goToActivity() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(0, android.R.anim.fade_out);
                finish();
            }
        });
        animator.start();
    }

    //设置标语
    private void setSlogan() {
        try {
            Typeface fontFace = Typeface.createFromAsset(this.getAssets(), "slogan.ttf");
            Tv_slogan.setTypeface(fontFace);
        } catch (Exception e) {
        }
    }

    //设备版本号
    private void setVersion() {
        Tv_version.setText(getString(R.string.version, getVersion(this)));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK;
    }

    /**
     * 获取应用版本号
     *
     * @param context context
     * @return 版本号
     */
    private String getVersion(Context context) {
        String version;
        try {
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = context.getString(R.string.version);
        }
        return version;
    }
}
