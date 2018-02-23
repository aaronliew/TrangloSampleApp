package com.tranglo.transactionhistory;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import com.tranglo.transactionhistory.network.model.TransactionDetail;
import com.tranglo.transactionhistory.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by aaronliew on 21/02/2018.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        CountingIdlingResource mainActivityIdlingResource = mActivityRule.getActivity().getEspressoIdlingResourceForMainActivity();

        // registering MainActivity's idling resource for enabling Espresso sync with MainActivity's background threads
        Espresso.registerIdlingResources(mainActivityIdlingResource);

        //perform your tests here

        Espresso.onView(ViewMatchers.withId(R.id.transaction_list)).perform(RecyclerViewActions.scrollToPosition(9));

        Espresso.onView(withText("Rose Tico")).check(matches(isDisplayed()));

        Thread.sleep(3000);

    }
}
