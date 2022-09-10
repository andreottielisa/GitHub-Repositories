package com.andreotti.github.domain.converter

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.andreotti.github.R
import com.andreotti.github.testing.repoMock
import com.andreotti.github.testing.repoVOMock
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@Config(application = Application::class)
class RepoConverterTest {

    private val context = getInstrumentation().context.apply {
        setTheme(R.style.Theme_GitHub)
    }

    private val converter = RepoConverter(context.resources)

    @Test
    fun `when running conversion method, check if result is correct`() {
        assertEquals(repoVOMock, converter.convert(repoMock))
    }
}