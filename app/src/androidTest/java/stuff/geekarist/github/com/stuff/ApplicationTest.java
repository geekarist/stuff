package stuff.geekarist.github.com.stuff;

import android.app.Application;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.squareup.spoon.Spoon;

import junit.framework.Assert;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {
    @Rule
    public ActivityTestRule<ProductListActivity> mActivityRule = new ActivityTestRule(ProductListActivity.class);

    @Test
    public void shouldDisplayTitle() {
        Spoon.screenshot(mActivityRule.getActivity(), "Application");
        onView(withText("Stuff")).check(matches(isDisplayed()));
    }
}