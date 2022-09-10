package com.andreotti.github.testing

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

internal class RecyclerViewMatcher(@IdRes private val recyclerViewId: Int) {

    fun atPosition(position: Int, @IdRes targetViewId: Int? = null): Matcher<View?> =
        object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText("$recyclerViewId not found")
            }

            override fun matchesSafely(item: View): Boolean {
                val recycler = item.rootView.findViewById<RecyclerView>(recyclerViewId)
                val viewHolder = recycler.findViewHolderForAdapterPosition(position)?.itemView

                return if (targetViewId == null)
                    item == viewHolder
                else viewHolder?.findViewById<View>(targetViewId) == item
            }
        }
}