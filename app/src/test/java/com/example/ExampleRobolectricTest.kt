package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.Hymn
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
    assertEquals("Himnario Cristiano", appName)
  }

  @Test
  fun `hymn stanza splitting works correctly`() {
    val hymn = Hymn(
      number = 1,
      title = "Prueba",
      lyrics = "Estrofa uno verso uno\nEstrofa uno verso dos\n\nEstrofa dos verso uno\nEstrofa dos verso dos"
    )
    val stanzas = hymn.getStanzas()
    assertEquals(2, stanzas.size)
    assertTrue(stanzas[0].contains("Estrofa uno"))
    assertTrue(stanzas[1].contains("Estrofa dos"))
  }

  @Test
  fun `hymn multi categories parsing works correctly`() {
    val hymn = Hymn(
      number = 1,
      title = "Cuan Grande Es El",
      category = "Alabanza, Adoración, Culto Dominical"
    )
    val categories = hymn.getCategories()
    assertEquals(3, categories.size)
    assertTrue(hymn.hasCategory("Alabanza"))
    assertTrue(hymn.hasCategory("Adoración"))
    assertTrue(hymn.hasCategory("Culto Dominical"))
  }
}
