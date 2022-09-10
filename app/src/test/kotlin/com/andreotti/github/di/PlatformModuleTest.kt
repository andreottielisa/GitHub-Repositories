package com.andreotti.github.di

import android.app.Application
import android.content.res.Resources
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = Application::class)
class PlatformModuleTest : AutoCloseKoinTest() {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(getInstrumentation().targetContext)
        modules(platformModule)
    }

    @Test
    fun `it should provide Resources`() {
        val resources by inject<Resources>()
        assertNotNull(resources)
    }
}