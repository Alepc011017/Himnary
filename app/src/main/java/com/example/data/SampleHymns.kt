package com.example.data

import android.content.Context
import org.json.JSONArray

object SampleHymns {

    val defaultCategories = listOf(
        HymnCategory(name = "Navidad", isDefault = true, colorHex = "#A61C1C"),
        HymnCategory(name = "Hogar y Familia", isDefault = true, colorHex = "#D97706"),
        HymnCategory(name = "Niños", isDefault = true, colorHex = "#0284C7"),
        HymnCategory(name = "Resurrección", isDefault = true, colorHex = "#059669"),
        HymnCategory(name = "Alabanza", isDefault = true, colorHex = "#1B365D"),
        HymnCategory(name = "Adoración", isDefault = true, colorHex = "#C29026"),
        HymnCategory(name = "Gracia", isDefault = true, colorHex = "#7D2338"),
        HymnCategory(name = "Fe y Confianza", isDefault = true, colorHex = "#2E6B4F"),
        HymnCategory(name = "Comunión", isDefault = true, colorHex = "#4A3B78"),
        HymnCategory(name = "Evangelismo", isDefault = true, colorHex = "#B85D19"),
        HymnCategory(name = "Consagración", isDefault = true, colorHex = "#22577A"),
        HymnCategory(name = "Oración", isDefault = true, colorHex = "#387780"),
        HymnCategory(name = "Santa Cena", isDefault = true, colorHex = "#8E2800"),
        HymnCategory(name = "Culto Dominical", isDefault = true, colorHex = "#1B4965")
    )

    /**
     * Loads the complete collection of 424 hymns from Gracia y Devoción
     * bundled in assets/hymns_gracia_y_devocion.json.
     */
    fun loadHymns(context: Context): List<Hymn> {
        return try {
            val jsonString = context.assets.open("hymns_gracia_y_devocion.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)
            val list = mutableListOf<Hymn>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val number = obj.optInt("number", i + 1)
                list.add(
                    Hymn(
                        id = number, // Align ID with hymn number (1 to 424)
                        number = number,
                        title = obj.optString("title", ""),
                        category = obj.optString("category", "Alabanza"),
                        tone = obj.optString("tone", ""),
                        author = obj.optString("author", "Himnario Gracia y Devoción"),
                        scriptureRef = obj.optString("scriptureRef", ""),
                        chorus = obj.optString("chorus", ""),
                        lyrics = obj.optString("lyrics", ""),
                        notes = obj.optString("notes", ""),
                        isFavorite = obj.optBoolean("isFavorite", false),
                        lastReadAt = 0L
                    )
                )
            }
            if (list.isNotEmpty()) list else initialHymns
        } catch (e: Exception) {
            e.printStackTrace()
            initialHymns
        }
    }

    // Fallback sample hymns for unit testing when Android Context is unavailable
    val initialHymns = listOf(
        Hymn(
            id = 1,
            number = 1,
            title = "Del Culto El Tiempo Llega",
            category = "Oración, Culto Dominical",
            tone = "Sol Mayor (G)",
            author = "Carl Boberg",
            scriptureRef = "Salmo 95:1-6",
            chorus = "",
            lyrics = """
1. Del culto el tiempo llega,
Comienza la oración.
El alma a Dios se entrega:
¡Silencio y atención!
Si al santo Dios la mente
Queremos elevar,
Silencio reverente
Habremos de guardar.

2. Mil coros celestiales
A Dios cantando están,
A ellos los mortales
Sus voces unirán.
Alcemos, pues, el alma
Con santa devoción,
Gozando en dulce calma
De Dios la comunión.

3. La Biblia bendecida,
De Dios revelación,
A meditar convida
En nuestra condición.
¡Silencio! Que ha llegado
Del culto la ocasión.
Dios se halla a nuestro lado:
¡Silencio y devoción!
            """.trimIndent(),
            notes = "Himno #1 de Gracia y Devoción. Apertura del culto."
        ),
        Hymn(
            id = 2,
            number = 2,
            title = "¡Santo! ¡Santo! ¡Santo!",
            category = "Adoración, Culto Dominical",
            tone = "Mi Mayor (E)",
            author = "Reginald Heber / John B. Dykes",
            scriptureRef = "Apocalipsis 4:8",
            chorus = "",
            lyrics = """
1. ¡Santo! ¡Santo! ¡Santo! Señor omnipotente,
Siempre el labio mío loores te dará;
¡Santo! ¡Santo! ¡Santo! Te adoro reverente,
Dios en tres personas, bendita Trinidad.

2. ¡Santo! ¡Santo! ¡Santo! En numeroso coro,
Santos escogidos te adoran con fervor,
De alegría llenos, y sus coronas de oro,
Rinden ante el trono glorioso del Señor.

3. ¡Santo! ¡Santo! ¡Santo! La inmensa muchedumbre,
De ángeles que cumplen tu santa voluntad,
Ante ti se postra bañada con tu lumbre,
Ante ti que has sido, que eres y serás.

4. ¡Santo! ¡Santo! ¡Santo! por más que estés velado,
E imposible sea tu gloria contemplar,
Santo Tú eres sólo y nada hay a tu lado,
en poder perfecto, pureza y caridad.

5. ¡Santo! ¡Santo! ¡Santo! La gloria de tu nombre,
Vemos en tus obras, en cielo, tierra y mar.
¡Santo! ¡Santo! ¡Santo! Te adorará todo hombre,
Dios en tres personas, bendita Trinidad.
            """.trimIndent(),
            notes = "Himno #2 de Gracia y Devoción."
        ),
        Hymn(
            id = 367,
            number = 367,
            title = "¡ Noche De Paz! ¡noche De Amor !",
            category = "Navidad, Gracia",
            tone = "Si bemol (Bb)",
            author = "Joseph Mohr / Franz Gruber",
            scriptureRef = "Lucas 2:1-14",
            chorus = "",
            lyrics = """
1. ¡Noche de paz, noche de amor!
Todo duerme en derredor,
Entre los astros que esparcen su luz,
Bella anunciando al niñito Jesús,
Brilla la estrella de paz,
Brilla la estrella de paz.

2. ¡Noche de paz, noche de amor!
Oye humilde el fiel pastor,
Coros celestes que anuncian salud,
Gracias y glorias en gran plenitud,
Por nuestro buen Redentor,
Por nuestro buen Redentor.

3. ¡Noche de paz, noche de amor!
Ved qué bello resplandor
Luce en el rostro del niño Jesús,
En el pesebre, del mundo la luz
Astro de eterno fulgor,
Astro de eterno fulgor.
            """.trimIndent(),
            notes = "Himno #367 de Gracia y Devoción (Navidad)."
        ),
        Hymn(
            id = 405,
            number = 405,
            title = "Todo A Cristo Yo Me Rindo",
            category = "Evangelismo, Consagración",
            tone = "Re Mayor (D)",
            author = "Judson W. Van DeVenter",
            scriptureRef = "Romanos 12:1",
            chorus = "Yo me rindo a El,\nYo me rindo a El,\nTodo a cristo yo me entrego,\nQuiero serle fiel.",
            lyrics = """
1. Todo a Cristo yo me rindo,
Con el fin de serle fiel;
Para siempre quiero amarle,
Y agradarle sólo a El.

2. Todo a Cristo yo me rindo,
A sus pies postrado estoy;
Los placeres he dejado,
Y le sigo desde hoy.

3. Todo a Cristo yo me rindo,
Sí, de todo corazón;
Yo le entrego alma y cuerpo,
Busco hoy su santa unción.

4. Todo a Cristo he rendido,
Siento el fuego de su amor;
¡Oh, qué gozo hay en mi alma!
¡Gloria, gloria a mi Señor!
            """.trimIndent(),
            notes = "Himno #405 de Gracia y Devoción (Consagración)."
        )
    )
}
