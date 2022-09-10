package com.andreotti.github.testing

import android.view.View
import androidx.core.view.ViewCompat.isAccessibilityHeading
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

internal fun withAccessibilityHeading(expected: Boolean): Matcher<View> {
    return object : BoundedMatcher<View, View>(View::class.java) {

        override fun describeTo(description: Description) {}

        override fun matchesSafely(view: View): Boolean =
            isAccessibilityHeading(view) == expected
    }
}