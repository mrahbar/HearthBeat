package com.smartsoftware.android.hearthbeat;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 14.09.2015
 * Time: 16:12
 * Email: mrahbar.azad@gmail.com
 */
public class EspressoHelper {

    private EspressoHelper() {
    }

    public static void clickOnViewById(@IdRes int id) {
        onView(withId(id))
                .perform(click());
    }

    public static void clickOnViewByText(@StringRes int id) {
        onView(withText(id))
                .perform(click());
    }

    public static void clickOnViewByText(String text) {
        onView(withText(text))
                .perform(click());
    }
    public static void checkViewByIdVisible(@IdRes int id) {
        onView(withId(id))
                .check(matches(isDisplayed()));
    }

    public static void checkViewByIdNotVisible(@IdRes int id) {
        onView(withId(id))
                .check(matches(not(isDisplayed())));
    }

    public static void checkViewByStringVisible(@StringRes int text) {
        onView(withText(text))
                .check(matches(isDisplayed()));
    }

    public static void checkViewByStringNotVisible(@StringRes int text) {
        onView(withText(text))
                .check(matches(not(isDisplayed())));
    }

    public static void checkViewByStringVisible(String text) {
        onView(withText(text))
                .check(matches(isDisplayed()));
    }

    public static void enterTextOnViewWithHint(@StringRes int id, String link) {
        onView(withHint(id))
                .perform(replaceText(link));
    }

    public static void swipeViewpagerLeft(@IdRes int viewpager) {
        onView(withId(viewpager))
                .perform(swipeLeft());
    }

    public static void clickOnListItem(int listId, int position) {
        onData(anything()).inAdapterView(withId(listId))
                .atPosition(position)
                .perform(click());
    }
}
