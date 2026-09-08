package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.CurriculumRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("CodeBridge", appName)
  }

  @Test
  fun `curriculum repository contains core languages and comparisons`() {
    assertEquals(4, CurriculumRepository.languages.size)
    assertTrue(CurriculumRepository.concepts.isNotEmpty())
    assertTrue(CurriculumRepository.comparisons.isNotEmpty())
    assertTrue(CurriculumRepository.quizzes.isNotEmpty())
  }
}

