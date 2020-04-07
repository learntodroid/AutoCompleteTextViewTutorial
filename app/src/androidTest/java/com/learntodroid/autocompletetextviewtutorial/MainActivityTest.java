package com.learntodroid.autocompletetextviewtutorial;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.learntodroid.autocompletetextviewtutorial.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void autoCompleteTextViewTest() {
        onView(withId(R.id.activity_main_autoCompleteTextView)).perform(typeText("Melbou"));
        onData(equalTo("Melbourne")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.activity_main_selected_place_name)).check(matches(withText("Melbourne")));
    }
}