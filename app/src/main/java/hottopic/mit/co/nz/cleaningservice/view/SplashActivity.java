package hottopic.mit.co.nz.cleaningservice.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import hottopic.mit.co.nz.cleaningservice.view.user.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Execute the handler program after a second
        new MyHandler(new WeakReference(this)).sendEmptyMessageDelayed(0, 1000);
    }

    static class MyHandler extends Handler {
        private WeakReference<SplashActivity> activityWeakReference;

        MyHandler(WeakReference<SplashActivity> activityWeakReference) {
            this.activityWeakReference = activityWeakReference;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (activityWeakReference.get() != null) {
                SplashActivity splashActivity = activityWeakReference.get();
                // Start login activity
                splashActivity.startActivity(new Intent(splashActivity, LoginActivity.class));
                // close splash activity
                splashActivity.finish();
            }
        }
    }
}
