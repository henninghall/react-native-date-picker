package com.datepickerexample;


import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.StringContains.containsString;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EspressoTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private String getDateString(int dayDiff) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, dayDiff);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }

    private int getCurrentMinute(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MINUTE);
    }

    @Before
    public void beforeEach() {
        sleep(8000);
        onView(withText("Advanced")).perform(click());
    }

    @Test
    public void changeDateTest() {
        onView(ViewMatchers.withId(R.id.day)).perform(swipeDown());
        onView(withText(containsString(getDateString(-4)))).check(matches(isDisplayed()));
    }

    @Test()
    public void maxDate() {
        onView(withText("maxDate")).perform(scrollTo());
        onView(withText("maxDate")).perform(click());
        onView(withText(containsString("Picker date: " + getDateString(0)))).check(matches(isDisplayed()));
        for (int i = 0; i<6 ;i++ ) onView(withText(containsString("-1 DAY"))).perform(click());
        onView(withText(containsString("Picker date: " + getDateString(-1)))).check(matches(isDisplayed()));
    }

    @Test
    public void minDate() {
        onView(withText("minDate")).perform(scrollTo()).perform(click());
        onView(withText(containsString("Picker date: " + getDateString(0)))).check(matches(isDisplayed()));
        for (int i = 0; i<6 ;i++ ) onView(withText(containsString("+1 DAY"))).perform(click());
        onView(withText(containsString("Picker date: " + getDateString(+1)))).check(matches(isDisplayed()));
    }

    @Test
    public void minuteInterval() {
        onView(withText("minuteInterval")).perform(scrollTo()).perform(click());
        onView(withText("5 MIN")).perform(click());
        onView(ViewMatchers.withId(R.id.minutes)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.VISIBLE_CENTER, GeneralLocation.TOP_CENTER, Press.FINGER ));

        int swipeSteps = 2;
        int minuteInterval = 5;
        int minuteRestFromPrevEven5minutes = getCurrentMinute() % minuteInterval;
        int withAddedMinutes = (minuteRestFromPrevEven5minutes < minuteInterval / 2
                ? getCurrentMinute() - minuteRestFromPrevEven5minutes + minuteInterval * swipeSteps
                : getCurrentMinute() - minuteRestFromPrevEven5minutes + minuteInterval * (swipeSteps + 1));
        int expectedNextMinute = withAddedMinutes % 60;
        String expectedNextMinutePadded = String.format("%02d", expectedNextMinute);
        String expectedDateString = ":" + expectedNextMinutePadded + ":";
        onView(withText(containsString(expectedDateString))).check(matches(isDisplayed()));
    }

    @Test
    public void dateTimeMode() {
        onView(ViewMatchers.withId(R.id.day)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.year)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.month)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.date)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.hour)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.minutes)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.ampm)).check(matches(isDisplayed()));
    }

    @Test
    public void dateMode() {
        onView(withText("date ")).perform(scrollTo()).perform(click());
        onView(ViewMatchers.withId(R.id.day)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.year)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.month)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.date)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.hour)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.minutes)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.ampm)).check(matches(not(isDisplayed())));
    }

    @Test
    public void timeMode() {
        onView(withText("time")).perform(scrollTo()).perform(click());
        onView(ViewMatchers.withId(R.id.hour)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.minutes)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.ampm)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.day)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.year)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.month)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.date)).check(matches(not(isDisplayed())));
    }


    @Test
    public void changeLocale() {
        onView(withText("locale")).perform(scrollTo()).perform(click());
        onView(withText("nn-NO")).perform(scrollTo()).perform(click());
    }


    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}


