package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.jokeandroidlibrary.JokeActivity;
import com.udacity.gradle.builditbigger.idlingResource.SimpleIdlingResource;
import com.udacity.gradle.builditbigger.network.EndpointsAsyncTask;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.EndPointListener {

    private static final String JOKE_KEY = "joke_key";

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        getIdlingResource();
        return rootView;
    }


    @OnClick(R.id.btn_tell_joke)
    public void btnTellJokeClick(View view) {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
        new EndpointsAsyncTask(this).execute();
    }

    //region EndpointsAsyncTask.EndPointListener
    @Override
    public void onPostExecute(String jokeString) {
        Intent intent = new Intent(getContext(), JokeActivity.class);
        intent.putExtra(JOKE_KEY, jokeString);
        startActivity(intent);
        mIdlingResource.setIdleState(true);
    }

    //endregion
}
