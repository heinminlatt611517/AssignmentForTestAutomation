package com.zg.burgerjoint.Uitests.login

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.zg.burgerjoint.R
import com.zg.burgerjoint.activities.LoginActivity
import com.zg.burgerjoint.activities.MainActivity
import kotlinx.android.synthetic.main.activity_login.view.*
import org.junit.Before
import org.junit.Test

class LoginSuccessTest {
    private val activityTestRule= ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @Before
    open fun setUp(){
        activityTestRule.launchActivity(Intent())
    }

//    @Test
//    fun checkBtnLoginIsDeplay(){
//        onView(withId(R.id.btnLogin))
//            .check(matches(isDisplayed()))
//    }

    @Test
    fun  enterInformation_navigateToMainScreen(){

        onView(withId(R.id.etUserName)).
                perform(typeText(TEST_USER_NAME),
                    closeSoftKeyboard())

        onView(withId(R.id.etPassword)).
            perform(typeText(TEST_PASSWORD),
                closeSoftKeyboard())

        onView(withId(R.id.btnLogin))
            .perform(click())
        onView(withId(R.id.rvBurgerList))
            .check(matches(isDisplayed()))

    }

    companion object{
        const val TEST_USER_NAME="hein min latt"
        const val TEST_PASSWORD="12345"
    }
}