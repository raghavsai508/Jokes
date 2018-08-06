package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.android.jokeandroidlibrary.JokeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityFreeTest {

    private IdlingResource mIdlingResource;

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        MainActivityFragment mainActivityFragment = (MainActivityFragment) intentsTestRule.getActivity().getSupportFragmentManager().findFragmentByTag(MainActivityFragment.class.getName());
        mIdlingResource = mainActivityFragment.getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Test
    public void testMainActivityJokeButton() {
        onView(withId(R.id.btn_tell_joke)).check(matches(isDisplayed()));
    }

    @Test
    public void testInterstitialAdDisplay() {
        onView(withId(R.id.adView)).check(matches(isDisplayed()));
    }

    @Test
    public void testJokeActivity() {
        onView(withId(R.id.btn_tell_joke)).perform(click());
        intended(hasComponent(JokeActivity.class.getName()));
    }

}
