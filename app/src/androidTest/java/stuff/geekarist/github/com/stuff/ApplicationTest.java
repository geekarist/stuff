package stuff.geekarist.github.com.stuff;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.suitebuilder.annotation.LargeTest;

import com.squareup.spoon.Spoon;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {
    @Rule
    public ActivityTestRule<ProductListActivity> mActivityRule = new ActivityTestRule<>(ProductListActivity.class);

    @Test
    public void shouldDisplayTitle() throws Throwable {
        Espresso.onView(ViewMatchers.withText("Stuff")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void shouldOpenItem() throws Throwable {
        // When
        Espresso.onView(ViewMatchers.withText("Item 2")).perform(ViewActions.click());

        // Then
        Espresso.onView(ViewMatchers.withText(Matchers.containsString("Details about Item: 2")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void shouldEditItem() throws Throwable {
        // Given
        Espresso.onView(ViewMatchers.withText("Item 2")).perform(ViewActions.click());

        // When
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click());

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.product_detail))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.product_detail_edit))
                .check(ViewAssertions.matches(Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.withText(Matchers.containsString("Details about Item: 2")))));
    }

    @Test
    public void shouldValidateItemEdit() throws Throwable {
        // Given
        Espresso.onView(ViewMatchers.withText("Item 2")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click());

        // When
        Espresso.onView(ViewMatchers.withId(R.id.product_detail_edit)).perform(
                ViewActions.clearText(),
                ViewActions.typeText("New information!"));
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click());

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.product_detail))
                .check(ViewAssertions.matches(Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.withText(Matchers.containsString("New information!")))));
    }

    @Test
    public void shouldSaveItem() throws Throwable {
        // Given
        Espresso.onView(ViewMatchers.withText("Item 2")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.product_detail_edit)).perform(
                ViewActions.clearText(),
                ViewActions.typeText("New information!"));
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click());

        // When
        Espresso.pressBack();
        Espresso.onView(ViewMatchers.withText("Item 2")).perform(ViewActions.click());

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.product_detail))
                .check(ViewAssertions.matches(Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        ViewMatchers.withText(Matchers.containsString("New information!")))));
    }

    @NonNull
    private File screenshot() throws Throwable {
        return Spoon.screenshot(getCurrentActivity(), "Application");
    }

    private Activity getCurrentActivity() throws Throwable {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        final Activity[] activity = new Activity[1];
        mActivityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                java.util.Collection<Activity> activities =
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                activity[0] = Iterables.getOnlyElement(activities);
            }
        });
        return activity[0];
    }
}