package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.jokeandroidlibrary.JokeActivity;
import com.example.android.jokelibrary.JokeLibrary;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityPaidTest {

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
    public void testJokeActivity() {
        onView(withId(R.id.btn_tell_joke)).perform(click());
        intended(hasComponent(JokeActivity.class.getName()));
    }

    @Test
    public void checkCurrentJokeExist() {
        onView(withId(R.id.btn_tell_joke)).perform(click());
        JokeLibrary jokeLibrary = new JokeLibrary();
        onView(withText(jokeLibrary.getJoke())).check(matches(isDisplayed()));
    }

}
