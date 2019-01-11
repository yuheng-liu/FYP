package com.example.viapatron2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.viapatron2.R;
import com.example.viapatron2.service.viaPatronWorkerService;

public class SplashScreenActivity extends AppActivity {

    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 2000;

    private void openActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Todo : update picture of layout for viaPatron spash screen
        setContentView(R.layout.activity_splash_screen);

        // Todo : find out the use of AutoWorkerService and implement it for viaPatron
        // start worker service
        startService(new Intent(this, viaPatronWorkerService.class));

//        openActivity();
    }


    // todo: uncomment override after finalising
    //@Override
    protected void onServiceConnected() {
        int splashTimeout = SPLASH_TIME_OUT;
        final Handler homeScreenHandler = new Handler();

        // todo: settle getDataManager()
        // check session
//        if (getDataManager().getUserSession() != null) {
//            // double splash timeout if there is existing session
//            splashTimeout *= 2;
//            getApiManager().validateSession(
//                    new Action() {
//                        @Override
//                        public void run() {
////                            openActivity(HomeScreenActivity.class);
//                        }
//                    },
//                    new Consumer<StatusResponse>() {
//                        @Override
//                        public void accept(StatusResponse statusResponse) {
//                            homeScreenHandler.removeCallbacksAndMessages(null);
//                            openActivity(LoginActivity.class);
//                        }
//                    }
//            );
//            // open main activity when socket session connected
//            addDisposable(getSocketManager().addOnSessionConnected(new Consumer<UserContext>() {
//                @Override
//                public void accept(UserContext userContext) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            homeScreenHandler.removeCallbacksAndMessages(null);
//                            openActivity(HomeScreenActivity.class);
//                        }
//                    });
//                }
//            }));
//        }

        homeScreenHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // todo: rename new Login Activity
                //openActivity(LoginActivity.class);
            }
        }, splashTimeout);
    }
}
