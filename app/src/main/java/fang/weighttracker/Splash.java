package fang.weighttracker;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * @author Fang Fang
 * Date: 2016/6/5
 * Description: Add a splash screen for the application loading when user access to the app.
 */

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final ImageView iv = (ImageView) findViewById(R.id.iv_splash);
        final Animation an = AnimationUtils.loadAnimation(getApplication(),R.anim.rotate);
        final Animation an_end = AnimationUtils.loadAnimation(getApplication(),R.anim.abc_fade_out);

        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an_end);
                finish();
                Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
                startActivity(intent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
