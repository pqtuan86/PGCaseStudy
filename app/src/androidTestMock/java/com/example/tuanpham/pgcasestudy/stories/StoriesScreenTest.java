package com.example.tuanpham.pgcasestudy.stories;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.tuanpham.pgcasestudy.R;
import com.example.tuanpham.pgcasestudy.TestUtils;

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
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.tuanpham.pgcasestudy.TestUtils.getCurrentActivity;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

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
        onView(ViewMatchers.withId(R.id.stories_list)).perform(scrollTo(hasDescendant(withText(storyTitle1))));
        onView(withItemText(storyTitle1)).check(matches(isDisplayed()));

        // check story 2 is displayed on screen
        onView(withId(R.id.stories_list)).perform(scrollTo(hasDescendant(withText(storyTitle2))));
        onView(withItemText(storyTitle2)).check(matches(isDisplayed()));
    }

    @Test
    @SdkSuppress(minSdkVersion = 21) // Blinking cursor after rotation breaks this in API 19
    public void testOrientationChange_StoriesPersist() throws Exception {

        // Rotate the screen
        TestUtils.rotateOrientation(getCurrentActivity());
        testShowTopStories();
    }

    @Test
    public void testAppNavigation() throws Exception {
        String storyTitle1 = "New Orleans man locked up nearly 8 years awaiting trial, then case gets tossed";

        // Click on the story on the list
        onView(withText(storyTitle1)).perform(click());

        // Verify screen is Comments screen
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Story Comments")));

        // Click on back
        String navigateUpDesc = storiesActivityTestRule.getActivity()
                .getString(android.support.v7.appcompat.R.string.abc_action_bar_up_description);
        onView(withContentDescription(navigateUpDesc)).perform(click());

        // Verify screen is Top Stories screen
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Top Stories")));
    }
}
