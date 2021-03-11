package com.maktech.simpleagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.ads.InterstitialAd;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.greedygame.core.AppConfig;
import com.greedygame.core.GreedyGameAds;
import com.greedygame.core.adview.modals.AdRequestErrors;
import com.greedygame.core.interfaces.GreedyGameAdsEventsListener;
import com.greedygame.core.interstitial.general.GGInterstitialAd;
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.listeners.InterstitialAdEventListener;
import com.startapp.sdk.adsbase.StartAppAd;
import com.ycuwq.datepicker.date.DatePicker;

import java.time.*;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView tv1,tv2,tv3;
    DatePicker dp;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    InMobiInterstitial interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=findViewById(R.id.textView1);
        tv2=findViewById(R.id.textView2);
        tv3=findViewById(R.id.textView3);

        dp=findViewById(R.id.datePicker);
        tv1.setText(dp.getDate());


    //greedygame ad intitialization
        AppConfig appConfig = new AppConfig.Builder(this)
                .withAppId("YOUR_APP_ID_HERE")  //Replace the app ID with your app's ID
                .build();

        GGInterstitialAd ggInterstitialAd = new GGInterstitialAd(this, "AD_UNIT_ID_HERE");
        GGInterstitialEventsListener ggInterstitialEventsListener = new GGInterstitialEventsListener() {
            @Override
            public void onAdLoaded() {
                Log.d("GGADS","Ad Loaded");
                if(ggInterstitialAd.isAdLoaded()){
                    ggInterstitialAd.show(); //Displays the ad in a full screen activity
                }
            }
            @Override
            public void onAdLeftApplication() {
                Log.d("GGADS","Ad Left Application");
            }
            @Override
            public void onAdClosed() {
                Log.d("GGADS","Ad Closed");
            }
            @Override
            public void onAdOpened() {
                Log.d("GGADS","Ad Opened");
            }
            @Override
            public void onAdLoadFailed (AdRequestErrors cause) {
                Log.d("GGADS","Ad Load Failed "+cause);
            }
        };
        // …
        ggInterstitialAd.setListener(ggInterstitialEventsListener);
        // …
        ggInterstitialAd.loadAd();

    //admob ads initialization
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    //banner ad
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    //interstitial ad
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.intrestetial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void callmAd(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mInterstitialAd.show();
            }
        }, 180000);
    }

    public void inMobiBannerAd(){
        interstitialAd= new InMobiInterstitial(MainActivity.this, 1614808549811L, new InterstitialAdEventListener() {
            @Override
            public void onAdLoadSucceeded(InMobiInterstitial inMobiInterstitial) {
                super.onAdLoadSucceeded(inMobiInterstitial);
            }

            @Override
            public void onAdLoadFailed(InMobiInterstitial inMobiInterstitial, InMobiAdRequestStatus inMobiAdRequestStatus) {
                super.onAdLoadFailed(inMobiInterstitial, inMobiAdRequestStatus);
            }

            @Override
            public void onAdReceived(InMobiInterstitial inMobiInterstitial) {
                super.onAdReceived(inMobiInterstitial);
            }

            @Override
            public void onAdClicked(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                super.onAdClicked(inMobiInterstitial, map);
            }

            @Override
            public void onAdWillDisplay(InMobiInterstitial inMobiInterstitial) {
                super.onAdWillDisplay(inMobiInterstitial);
            }

            @Override
            public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {
                super.onAdDisplayed(inMobiInterstitial);
            }

            @Override
            public void onAdDisplayFailed(InMobiInterstitial inMobiInterstitial) {
                super.onAdDisplayFailed(inMobiInterstitial);
            }

            @Override
            public void onAdDismissed(InMobiInterstitial inMobiInterstitial) {
                super.onAdDismissed(inMobiInterstitial);
            }

            @Override
            public void onUserLeftApplication(InMobiInterstitial inMobiInterstitial) {
                super.onUserLeftApplication(inMobiInterstitial);
            }

            @Override
            public void onRewardsUnlocked(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                super.onRewardsUnlocked(inMobiInterstitial, map);
            }

            @Override
            public void onRequestPayloadCreated(byte[] bytes) {
                super.onRequestPayloadCreated(bytes);
            }

            @Override
            public void onRequestPayloadCreationFailed(InMobiAdRequestStatus inMobiAdRequestStatus) {
                super.onRequestPayloadCreationFailed(inMobiAdRequestStatus);
            }
        });
        interstitialAd.load();
        interstitialAd.show();
    }

    public void ageCalc(View view) {
        inMobiBannerAd();
        callmAd();
        // date of birth
        LocalDate pdate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //pdate = LocalDate.of(1989, 05, 15);
            pdate= LocalDate.of(dp.getYear(),dp.getMonth(),dp.getDay());
            // current date
            LocalDate now = LocalDate.now();
            // difference between current date and date of birth
            Period diff = Period.between(pdate, now);
            tv1.setText(diff.getYears()+" years ");
            tv2.setText(diff.getMonths()+" months");
            tv3.setText(diff.getDays()+" days");
        }

    }

    @Override
    public void onBackPressed() {
        StartAppAd.onBackPressed(this);
        super.onBackPressed();
    }
}


