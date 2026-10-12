package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.Hymn
import com.example.data.SampleHymns
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
    assertEquals("Cantemos Nuetra Fe", appName)
  }

  @Test
  fun `load all 424 hymns from assets`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val hymns = SampleHymns.loadHymns(context)
    assertEquals(424, hymns.size)

    val hymn1 = hymns.find { it.number == 1 }
    assertNotNull(hymn1)
    assertEquals("Del Culto El Tiempo Llega", hymn1?.title)

    val hymn424 = hymns.find { it.number == 424 }
    assertNotNull(hymn424)
    assertEquals("A Dios El Padre", hymn424?.title)

    // Verify key categories exist among hymns
    val navidadHymns = hymns.filter { it.hasCategory("Navidad") }
    assertTrue(navidadHymns.isNotEmpty())

    val ninosHymns = hymns.filter { it.hasCategory("Niños") }
    assertTrue(ninosHymns.isNotEmpty())

    val resurreccionHymns = hymns.filter { it.hasCategory("Resurrección") }
    assertTrue(resurreccionHymns.isNotEmpty())

    val hogarHymns = hymns.filter { it.hasCategory("Hogar y Familia") }
    assertTrue(hogarHymns.isNotEmpty())
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

  @Test
  fun `hymn recent reading timestamp tracks properly`() {
    val now = System.currentTimeMillis()
    val hymn = Hymn(
      number = 5,
      title = "En la Cruz",
      lastReadAt = now
    )
    assertTrue(hymn.lastReadAt > 0)
    assertEquals(now, hymn.lastReadAt)
  }

  @Test
  fun `first sung line extraction strips numbers and punctuation`() {
    val hymn = Hymn(
      number = 100,
      title = "Canto de Prueba",
      lyrics = "1. ¡Santo! ¡Santo! ¡Santo! Señor omnipotente,\nSiempre el labio mío loores te dará;"
    )
    val sungLine = hymn.getFirstSungLine()
    assertEquals("Santo! ¡Santo! ¡Santo! Señor omnipotente,", sungLine)
    assertEquals('S', hymn.getFirstSungLetter())
  }

  @Test
  fun `first sung letter indexes by what is sung rather than title`() {
    // User explicit prompt: "por ejemplo 'Señor mi Dios....' este se enumeraria por la 'S'"
    val hymn = Hymn(
      number = 400,
      title = "Cuán Grande Es Él",
      lyrics = "1. Señor mi Dios, al contemplar los cielos,\nEl firmamento y las estrellas mil..."
    )
    assertEquals("Señor mi Dios, al contemplar los cielos,", hymn.getFirstSungLine())
    assertEquals('S', hymn.getFirstSungLetter())
  }

  @Test
  fun `first sung letter normalizes accented vowels for dictionary`() {
    val hymn = Hymn(
      number = 200,
      title = "Cielos Abiertos",
      lyrics = "1. Ábranse los cielos con loor,\nCanten los querubines al Señor"
    )
    assertEquals("Ábranse los cielos con loor,", hymn.getFirstSungLine())
    assertEquals('A', hymn.getFirstSungLetter())
  }

  @Test
  fun `alphabetical index categorizes hymns under appropriate letters`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val hymns = SampleHymns.loadHymns(context)

    val sHymns = hymns.filter { it.getFirstSungLetter() == 'S' }
    assertTrue("Should find hymns starting with S", sHymns.isNotEmpty())

    val aHymns = hymns.filter { it.getFirstSungLetter() == 'A' }
    assertTrue("Should find hymns starting with A", aHymns.isNotEmpty())
  }
}
