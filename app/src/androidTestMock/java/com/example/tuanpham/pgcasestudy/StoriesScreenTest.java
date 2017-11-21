package com.example.tuanpham.pgcasestudy;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.example.tuanpham.pgcasestudy.stories.StoriesActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by tuanpham on 11/20/17.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class StoriesScreenTest {

    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return Matchers.allOf(
                        isDescendantOfA(isAssignableFrom(RecyclerView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RV with text " + itemText);
            }
        };
    }

    @Rule
    public ActivityTestRule<StoriesActivity> storiesActivityTestRule = new ActivityTestRule<>(StoriesActivity.class);

    @Test
    public void testShowTopStories() throws Exception {
        String storyTitle1 = "New Orleans man locked up nearly 8 years awaiting trial, then case gets tossed";
        String storyTitle2 = "Tether Critical Announcement";

        // check story 1 is displayed on screen
        onView(withId(R.id.stories_list)).perform(scrollTo(hasDescendant(withText(storyTitle1))));
        onView(withItemText(storyTitle1)).check(matches(isDisplayed()));

        // check story 2 is displayed on screen
        onView(withId(R.id.stories_list)).perform(scrollTo(hasDescendant(withText(storyTitle2))));
        onView(withItemText(storyTitle2)).check(matches(isDisplayed()));
    }

}
