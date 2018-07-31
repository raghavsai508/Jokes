package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.jokeandroidlibrary.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.udacity.gradle.builditbigger.network.EndpointsAsyncTask;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.EndPointListener {

    private static final String JOKE_KEY = "joke_key";

//    @BindView(R.id.adView)
//    AdView mAdView;

    private InterstitialAd mInterstitialAd;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        setupInterstitialAd();

        return rootView;
    }

    private void setupInterstitialAd() {

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        MobileAds.initialize(getContext(),"ca-app-pub-3940256099942544~3347511713");

//        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mInterstitialAd.loadAd(getAdRequest());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d("TAG", "The interstitial add error."+errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                new EndpointsAsyncTask(MainActivityFragment.this).execute();
                mInterstitialAd.loadAd(getAdRequest());
            }
        });
    }

    @NonNull
    private AdRequest getAdRequest() {
        return new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
    }


    @OnClick(R.id.btn_tell_joke)
    public void btnTellJokeClick(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
//            new EndpointsAsyncTask(MainActivityFragment.this).execute();
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    //region EndpointsAsyncTask.EndPointListener
    @Override
    public void onPostExecute(String jokeString) {
        Intent intent = new Intent(getContext(), JokeActivity.class);
        intent.putExtra(JOKE_KEY, jokeString);
        startActivity(intent);
    }
    //endregion

}
