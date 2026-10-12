package com.example.data

object SampleHymns {

    val defaultCategories = listOf(
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

    val initialHymns = listOf(
        Hymn(
            number = 1,
            title = "Cuán Grande Es Él",
            category = "Alabanza, Adoración",
            tone = "Sol Mayor (G)",
            author = "Carl Boberg / Stuart K. Hine",
            scriptureRef = "Salmo 8:1-9",
            chorus = "¡Mi corazón entona la canción!\n¡Cuán grande es Él! ¡Cuán grande es Él!\n¡Mi corazón entona la canción!\n¡Cuán grande es Él! ¡Cuán grande es Él!",
            lyrics = """
1. Señor, mi Dios, al contemplar los cielos,
El firmamento y las mil estrellas,
Al oír tu voz en los potentes truenos
Y ver brillar el sol en su cenit.

2. Al recorrer los montes y los valles
Y ver las bellas flores al pasar,
Al escuchar el canto de las aves
Y el murmurar del claro manantial.

3. Cuando recuerdo del amor divino,
Que desde el cielo al Salvador envió;
Aquel Jesús que por salvarme vino
Y en una cruz por mí se entregó.

4. Cuando el Señor me llame a su presencia,
Al dulce hogar, al cielo de esplendor,
Le adoraré cantando la grandeza
De su poder y su infinito amor.
            """.trimIndent(),
            notes = "Ideal para abrir cultos de alabanza y adoración.",
            isFavorite = true
        ),
        Hymn(
            number = 2,
            title = "Sublime Gracia",
            category = "Gracia, Evangelismo",
            tone = "Sol Mayor (G)",
            author = "John Newton (1779)",
            scriptureRef = "Efesios 2:8-9",
            chorus = "",
            lyrics = """
1. Sublime gracia del Señor
Que a un infeliz salvó;
Yo ciego fui, mas veo ya,
Perdido y Él me halló.

2. Su gracia me enseñó a temer,
Mis dudas ahuyentó;
¡Oh, cuán preciosa fue a mi ser
La hora en que creí!

3. En los peligros y aflicción
Que yo he cruzado ya,
Su gracia siempre me libró
Y al cielo me guiará.

4. Y cuando en Sion por siglos mil
Brillemos como el sol,
Cantar sin fin allí podré
La gracia de mi Dios.
            """.trimIndent(),
            notes = "Testimonio del poder redentor del Señor.",
            isFavorite = true
        ),
        Hymn(
            number = 3,
            title = "Castillo Fuerte Es Nuestro Dios",
            category = "Fe y Confianza, Alabanza",
            tone = "Re Mayor (D)",
            author = "Martín Lutero (1529)",
            scriptureRef = "Salmo 46:1",
            chorus = "",
            lyrics = """
1. Castillo fuerte es nuestro Dios,
Defensa y buen escudo;
Con su poder nos librará
En este trance agudo.
Con furia y con afán
Acósanos Satán;
Por armas hace gala
De fuerza y de vil treta;
Con ellas lucha fiero.

2. Nuestro valor es nada aquí,
Con él todo es perdido;
Mas por nosotros luchará
De Dios el Escogido.
¿Sabéis quién es Jesús?
El que venció en la cruz;
Señor de Sabaoth,
Y pues Él solo es Dios,
Él triunfa en la batalla.

3. Y si este mundo desleal
Nos amenazare con daño,
No temeremos ningún mal,
Dios guardará a su rebaño.
Que muestre su furor
Satán y su rencor;
Dañarnos no podrá,
Pues condenado está
Por la Palabra Santa.
            """.trimIndent(),
            notes = "Himno histórico de la Reforma.",
            isFavorite = false
        ),
        Hymn(
            number = 4,
            title = "Santo, Santo, Santo",
            category = "Adoración, Culto Dominical",
            tone = "Mi Mayor (E)",
            author = "Reginald Heber / John B. Dykes",
            scriptureRef = "Isaías 6:3, Apocalipsis 4:8",
            chorus = "",
            lyrics = """
1. ¡Santo! ¡Santo! ¡Santo! Señor Omnipotente,
Siempre el labio mío loores te dará;
¡Santo! ¡Santo! ¡Santo! Te adoro reverente,
Dios en tres Personas, bendita Trinidad.

2. ¡Santo! ¡Santo! ¡Santo! En numeroso coro,
Santos escogidos te adoran con fervor;
De alegría llenos y sus coronas de oro
Rinden ante el trono glorioso del Señor.

3. ¡Santo! ¡Santo! ¡Santo! La inmensa muchedumbre
De ángeles que cumplen tu santa voluntad,
Ante ti se postra, bañada con tu lumbre,
Ante ti que has sido, que eres y serás.

4. ¡Santo! ¡Santo! ¡Santo! Por más que estés velado,
E imposibilitado para contemplar tu faz,
Santo tú eres solo, y nada hay a tu lado
En poder perfecto, pureza y caridad.
            """.trimIndent(),
            notes = "Himno de adoración trinitaria solemne.",
            isFavorite = true
        ),
        Hymn(
            number = 5,
            title = "En la Cruz",
            category = "Evangelismo, Gracia",
            tone = "Mi Bemol (Eb)",
            author = "Isaac Watts / Ralph E. Hudson",
            scriptureRef = "Gálatas 6:14",
            chorus = "En la cruz, en la cruz, do primero vi la luz,\nY las manchas de mi alma yo lavé;\nFue allí por fe do vi a Jesús,\n¡Y siempre feliz con Él seré!",
            lyrics = """
1. Me hirió el pecado, fui a Jesús,
Mostréle mi dolor;
Perdido, errante, vi su luz,
Bendíjome en su amor.

2. Venció la muerte con poder
Y al cielo se exaltó;
Confiar en Él es mi placer,
Morir no temo yo.

3. Aunque se oscurezca el sol
Y deje de alumbrar,
Jesús es de mi alma el sol,
Jamás me va a dejar.
            """.trimIndent(),
            notes = "Muy cantado en llamados y bautismos.",
            isFavorite = false
        ),
        Hymn(
            number = 6,
            title = "Tu Fidelidad Es Grande",
            category = "Alabanza, Fe y Confianza",
            tone = "Re Mayor (D)",
            author = "Thomas O. Chisholm / William M. Runyan",
            scriptureRef = "Lamentaciones 3:22-23",
            chorus = "¡Oh, tu fidelidad! ¡Oh, tu fidelidad!\nCada mañana renuevas tu amor;\nNada me falta, pues todo provees,\n¡Grande, Señor, es tu fidelidad!",
            lyrics = """
1. ¡Oh, Dios eterno, tu misericordia
Ni una sombra de duda tendrá!
Tú nunca cambias, no tienes mudanza;
Cristo en tu gracia por siempre será.

2. El sol radiante, la luna en su gloria,
Las estaciones en su resplandor,
Junto a las aves y brisa del cielo,
Rinden tributo a su Creador.

3. Perdón por culpa y paz durable,
Tu fiel presencia que alivia el dolor,
Fuerza presente y viva esperanza,
Son bendiciones de tu gran amor.
            """.trimIndent(),
            notes = "Alabanza al Dios inmutable.",
            isFavorite = true
        ),
        Hymn(
            number = 7,
            title = "A Dios Sea la Gloria",
            category = "Alabanza, Culto Dominical",
            tone = "La Bemol (Ab)",
            author = "Fanny Crosby / William H. Doane",
            scriptureRef = "Romanos 11:36",
            chorus = "¡Alabad al Señor! Oiga el mundo su voz;\n¡Alabad al Señor! Que se goce la grey;\nVenid al Padre por Cristo Jesús,\nY dadle la gloria por su gran poder.",
            lyrics = """
1. ¡A Dios sea gloria por siempre jamás!
Dio a su propio Hijo por la humanidad;
Entregó su vida en rescate por mí,
Abriendo la puerta del cielo feliz.

2. ¡Oh gracia perfecta que Cristo compró!
A todo creyente perdón prometió;
Al más vil pecador que a Cristo acudió,
En ese momento perdón recibió.

3. Inmensa es la obra que Cristo logró,
Y grande es el gozo que en Él comenzó;
Mas más alto y puro será el loor
Al ver a Jesús nuestro Rey y Señor.
            """.trimIndent(),
            notes = "Alabanza congregacional jubilosa.",
            isFavorite = false
        ),
        Hymn(
            number = 8,
            title = "Dulce Oración",
            category = "Oración, Comunión",
            tone = "Re Mayor (D)",
            author = "William W. Walford / William B. Bradbury",
            scriptureRef = "Filipenses 4:6-7",
            chorus = "",
            lyrics = """
1. Dulce oración, dulce oración,
De toda pena y aflicción
Consuelas tú mi corazón
En horas de tribulación.
En este valle de dolor
Me llevas ante el Redentor,
Y en su presencia celestial
Derramo todo mi pesar.

2. Dulce oración, dulce oración,
Al trono de la salvación
Llevarás siempre mi fervor
Y mi ferviente petición.
Por fe la vista fijaré
En quien mis culpas expió,
Y en dulce paz descansaré
Creyendo lo que prometió.
            """.trimIndent(),
            notes = "Himno íntimo para momentos de oración.",
            isFavorite = false
        ),
        Hymn(
            number = 9,
            title = "Cristo Me Ama",
            category = "Comunión, Gracia",
            tone = "Do Mayor (C)",
            author = "Anna B. Warner / William B. Bradbury",
            scriptureRef = "Juan 13:1",
            chorus = "Sí, Cristo me ama;\nSí, Cristo me ama;\nSí, Cristo me ama,\nLa Biblia dice así.",
            lyrics = """
1. Cristo me ama, bien lo sé,
Su Palabra me hace ver
Que los niños son de Él;
Quien es débil, fuerte es.

2. Cristo me ama, pues murió,
Y el cielo me abrió;
Mis pecados borrará,
A sus hijos salvará.

3. Cristo me ama, es verdad,
Y me cuida en bondad;
Si le sigo por doquier,
Él me guardará muy fiel.
            """.trimIndent(),
            notes = "Himno querido para todas las edades.",
            isFavorite = false
        ),
        Hymn(
            number = 10,
            title = "Firmes y Adelante",
            category = "Consagración, Fe y Confianza",
            tone = "Mi Mayor (E)",
            author = "Sabine Baring-Gould / Arthur Sullivan",
            scriptureRef = "2 Timoteo 2:3",
            chorus = "¡Firmes y adelante, huestes de la fe,\nSin temor alguno, que Jesús nos ve!",
            lyrics = """
1. Firmes y adelante, huestes de la fe,
Sin temor alguno, que Jesús nos ve.
Jefe soberano, Cristo al frente va,
Y la regia enseña tremolando está.

2. Al sagrado nombre de nuestro adalid,
Tiembla el enemigo y huye de la lid.
Nuestra es la victoria, dad a Dios loor,
Y óiganlo los orbes cantar con fervor.

3. Muévese potente la iglesia de Dios,
De los ya gloriosos marchamos en pos;
Somos un solo cuerpo y uno es el Señor,
Una la esperanza y uno nuestro amor.
            """.trimIndent(),
            notes = "Himno triunfante y dinámico.",
            isFavorite = false
        ),
        Hymn(
            number = 11,
            title = "Hay Poder en Jesús",
            category = "Evangelismo, Gracia",
            tone = "Si Bemol (Bb)",
            author = "Lewis E. Jones",
            scriptureRef = "Apocalipsis 12:11",
            chorus = "Hay poder, poder, sin igual poder,\nEn Jesús quien murió;\nHay poder, poder, sin igual poder,\nEn la sangre que Él vertió.",
            lyrics = """
1. ¿Quieres ser salvo de toda maldad?
Tan solo hay poder en Jesús.
¿Quieres vivir en perfecta verdad?
Tan solo hay poder en Jesús.

2. ¿Quieres ser libre de orgullo y pasión?
Tan solo hay poder en Jesús.
Ven, y recibe hoy la salvación,
Tan solo hay poder en Jesús.
            """.trimIndent(),
            notes = "",
            isFavorite = false
        ),
        Hymn(
            number = 12,
            title = "Cantaré la Bella Historia",
            category = "Alabanza, Evangelismo",
            tone = "Sol Mayor (G)",
            author = "Francis H. Rowley / Peter P. Bilhorn",
            scriptureRef = "Salmo 89:1",
            chorus = "Cantaré la bella historia\nDe mi Salvador Jesús,\nY con santos en la gloria\nCantaré en clara luz.",
            lyrics = """
1. Cantaré la bella historia
De Jesús mi Salvador,
Y de cómo vino al mundo
Por salvar al pecador.
            """.trimIndent(),
            notes = "Toca en editar para agregar las siguientes estrofas.",
            isFavorite = false
        ),
        Hymn(
            number = 13,
            title = "Todas las Promesas",
            category = "Fe y Confianza",
            tone = "Si Bemol (Bb)",
            author = "Russell Kelso Carter",
            scriptureRef = "2 Pedro 1:4",
            chorus = "",
            lyrics = "",
            notes = "Toca para redactar o pegar la letra de este himno.",
            isFavorite = false
        ),
        Hymn(
            number = 14,
            title = "Cual Pendón Hermoso",
            category = "Consagración",
            tone = "Fa Mayor (F)",
            author = "Philip P. Bliss",
            scriptureRef = "Salmo 60:4",
            chorus = "",
            lyrics = "",
            notes = "Toca para redactar o pegar la letra de este himno.",
            isFavorite = false
        ),
        Hymn(
            number = 15,
            title = "Pecador, Ven al Dulce Jesús",
            category = "Evangelismo",
            tone = "La Bemol (Ab)",
            author = "Will L. Thompson",
            scriptureRef = "Mateo 11:28",
            chorus = "",
            lyrics = "",
            notes = "Toca para redactar o pegar la letra de este himno.",
            isFavorite = false
        )
    )
}
