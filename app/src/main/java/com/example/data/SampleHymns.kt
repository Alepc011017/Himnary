package com.example.data

object SampleHymns {

    val defaultCategories = listOf(
        HymnCategory(name = "Alabanza", isDefault = true, colorHex = "#1B365D"),
        HymnCategory(name = "Adoración", isDefault = true, colorHex = "#C29026"),
        HymnCategory(name = "Navidad", isDefault = true, colorHex = "#A61C1C"),
        HymnCategory(name = "Hogar y Familia", isDefault = true, colorHex = "#D97706"),
        HymnCategory(name = "Niños", isDefault = true, colorHex = "#0284C7"),
        HymnCategory(name = "Resurrección", isDefault = true, colorHex = "#059669"),
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
        // HIMNOS DE GRACIA Y DEVOCIÓN
        Hymn(
            number = 1,
            title = "Cuán Grande Es Él",
            category = "Alabanza, Adoración, Culto Dominical",
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
            notes = "Himnario Gracia y Devoción. Ideal para apertura de culto.",
            isFavorite = true
        ),
        Hymn(
            number = 2,
            title = "¡Santo! ¡Santo! ¡Santo!",
            category = "Adoración, Culto Dominical",
            tone = "Mi Mayor (E)",
            author = "Reginald Heber (Gracia y Devoción #2)",
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
            notes = "Himno clásico #2 de Gracia y Devoción.",
            isFavorite = true
        ),
        Hymn(
            number = 3,
            title = "¡Adoradle!",
            category = "Adoración, Alabanza",
            tone = "Fa Mayor (F)",
            author = "Tradicional (Gracia y Devoción #5)",
            scriptureRef = "Filipenses 2:9-11",
            chorus = "¡Adoradle! ¡Adoradle!\nCristo es el Señor glorioso;\n¡Adoradle! ¡Adoradle!\nDigno es el Salvador.",
            lyrics = """
1. Dad al Padre toda gloria,
Dad al Hijo todo honor,
Y al Espíritu divino,
Alabanzas por su amor.

2. Es Jesús el Rey de gloria,
Vencedor de la maldad;
Coronadle con victoria,
Por su gracia y su bondad.

3. Cielos y tierra canten
Alabanzas a su prez,
Y los redimidos dancen
Ante el trono de su Rey.
            """.trimIndent(),
            notes = "Himno #5 de Gracia y Devoción.",
            isFavorite = false
        ),
        Hymn(
            number = 4,
            title = "Te Loamos, ¡Oh Dios!",
            category = "Alabanza, Culto Dominical",
            tone = "Fa Mayor (F)",
            author = "William P. Mackay (Gracia y Devoción #6)",
            scriptureRef = "Salmo 85:6",
            chorus = "¡Aleluya! Te alabamos,\n¡Cuán grande es tu amor!\n¡Aleluya! Te adoramos,\nBendito Señor.",
            lyrics = """
1. Te loamos, ¡oh Dios!,
Con unánime voz,
Porque en Cristo tu Hijo
Nos diste perdón.

2. Te loamos, Jesús,
Quien por darnos tu luz,
Inmolado en el Calvario
Moriste en la cruz.

3. Dios Espíritu, ven,
Con tu gracia y tu bien,
Y aviva en las almas
Tu fuego de amor.
            """.trimIndent(),
            notes = "Himno #6 de Gracia y Devoción.",
            isFavorite = true
        ),
        Hymn(
            number = 5,
            title = "¡Pastoréanos, Jesús Amante!",
            category = "Consagración, Comunión",
            tone = "Re Mayor (D)",
            author = "Dorothy A. Thrupp (Gracia y Devoción #9)",
            scriptureRef = "Salmo 23:1",
            chorus = "¡Oh Jesús, bendito Salvador!\nOye nuestra humilde oración;\n¡Oh Jesús, bendito Salvador!\nGuíanos con tierno amor.",
            lyrics = """
1. Pastoréanos, Jesús amante,
Cuídanos con tierno amor;
En tus pastos abundantes
Apaciéntanos, Señor.

2. Somos tuyos, nos compraste
Con tu sangre redentora;
Por tu gracia nos salvaste,
Danos fuerzas a toda hora.

3. Líbranos de todo engaño,
Guárdanos en santidad;
A las ovejas de tu rebaño
Muestra tu fidelidad.
            """.trimIndent(),
            notes = "Himno #9 de Gracia y Devoción.",
            isFavorite = false
        ),
        Hymn(
            number = 6,
            title = "¡Cuán Firme Cimiento!",
            category = "Fe y Confianza",
            tone = "La Bemol (Ab)",
            author = "John Rippon (Gracia y Devoción #22)",
            scriptureRef = "Isaías 43:1-2",
            chorus = "",
            lyrics = """
1. ¡Cuán firme cimiento se ha dado a la fe,
De Dios en su fiel y gloriosa palabra!
¿Qué más pudo Dios a su pueblo decir,
Que lo que ha dicho a los que han de vivir?

2. No temas por nada, contigo Yo soy;
Tu Dios Yo soy siempre y ayuda te doy;
Tu fuerza y consuelo por siempre seré,
Y con mi diestra fiel te sostendré.

3. En todas las pruebas que habrás de pasar,
Mi gracia divina te habrá de bastar;
Las llamas del fuego no habrán de dañar,
Tan solo tu escoria vendrán a limpiar.

4. El alma que anhela en Jesús descansar,
Jamás el peligro la habrá de arrancar;
Pues aunque mil fuerzas procuren su mal,
Eterna victoria le da su caudal.
            """.trimIndent(),
            notes = "Himno #22 de Gracia y Devoción.",
            isFavorite = true
        ),
        Hymn(
            number = 7,
            title = "Oh Cristo, Yo Te Amo",
            category = "Adoración, Comunión",
            tone = "Fa Mayor (F)",
            author = "William R. Featherston",
            scriptureRef = "1 Juan 4:19",
            chorus = "",
            lyrics = """
1. Oh Cristo, yo te amo, yo sé que eres mío;
Por ti los deleites del mundo desvío.
Jesús, Redentor, pues me diste perdón,
Te amo, Señor, de sincero corazón.

2. Primero me amaste y me diste tu gracia,
Por mí padeciste desprecio y desgracia;
Llevaste en tu frente corona de espinas,
Por mí soportaste las penas divinas.

3. En vida y en muerte amarte prometo,
Honrarte por siempre con culto secreto;
Al darte loores con voz sin cesar,
Te amo, Señor, por tu amor sin par.
            """.trimIndent(),
            notes = "Himno de consagración de Gracia y Devoción.",
            isFavorite = true
        ),
        Hymn(
            number = 8,
            title = "Roca de la Eternidad",
            category = "Gracia, Fe y Confianza",
            tone = "Si Bemol (Bb)",
            author = "Augustus Toplady (Gracia y Devoción)",
            scriptureRef = "1 Corintios 10:4",
            chorus = "",
            lyrics = """
1. Roca de la eternidad,
Fuiste abierta para mí;
Sé mi escondedero fiel,
Solo me cobijo en ti.
Rico, limpio manantial,
Que lavaste mi maldad.

2. Ni con llanto ni dolor
Puedo el precio yo pagar;
Solo tu perfecto amor
Pudo al pecador salvar.
En mi mano nada traigo,
A tu cruz tan solo me asgo.

3. Mientras viva yo aquí,
O al cerrar los ojos yo,
Al volar a ti, mi Dios,
Y contemplarte en tu fulgor:
Roca de la eternidad,
Fuiste abierta para mí.
            """.trimIndent(),
            notes = "Himno solemne de Gracia y Devoción.",
            isFavorite = false
        ),
        Hymn(
            number = 9,
            title = "Habla, ¡Oh Dios!",
            category = "Adoración, Oración",
            tone = "Re Mayor (D)",
            author = "Keith Getty & Stuart Townend",
            scriptureRef = "1 Samuel 3:9-10",
            chorus = "",
            lyrics = """
1. Habla, oh Dios, que tu pueblo oyendo está;
Siembra en nosotros tu Santa Verdad.
Fórmanos a la imagen de Jesús,
Para vivir en santidad y luz.
Llena de fe cada corazón,
Somete hoy nuestra devoción.

2. Habla, oh Dios, y renueva nuestro ser;
Haznos tu clara palabra entender.
Prueba el sentir de la mente y la razón,
Limpia las faltas del corazón.
Que tu verdad permanezca fiel,
Como un lucero en nuestro cuartel.

3. Habla, oh Dios, por tu iglesia en la oración;
Cumple en la tierra tu santa misión.
Hasta que el mundo confiese que Tú eres Rey,
Y te adore gloriosa tu grey.
            """.trimIndent(),
            notes = "Himno contemporáneo de Gracia y Devoción.",
            isFavorite = true
        ),

        // CATEGORÍA: NAVIDAD
        Hymn(
            number = 10,
            title = "Noche de Paz, Noche de Amor",
            category = "Navidad, Adoración",
            tone = "Si Bemol (Bb)",
            author = "Joseph Mohr / Franz Gruber",
            scriptureRef = "Lucas 2:1-14",
            chorus = "",
            lyrics = """
1. ¡Noche de paz, noche de amor!
Todo duerme en derredor.
Entre los astros que esparcen su luz,
Bella, anunciando al niñito Jesús,
Brilla la estrella de paz,
Brilla la estrella de paz.

2. ¡Noche de paz, noche de amor!
Oye humilde el fiel pastor
Coros celestes que anuncian salud,
Gracias y glorias en gran plenitud,
Por nuestro buen Redentor,
Por nuestro buen Redentor.

3. ¡Noche de paz, noche de amor!
Ved qué bello resplandor
Luce en el rostro del tierno Jesús,
En el pesebre, del mundo la luz,
Astro de eterno fulgor,
Astro de eterno fulgor.
            """.trimIndent(),
            notes = "Himno universal de Navidad.",
            isFavorite = true
        ),
        Hymn(
            number = 11,
            title = "Al Mundo Paz, Nació Jesús",
            category = "Navidad, Alabanza",
            tone = "Re Mayor (D)",
            author = "Isaac Watts / G. F. Handel",
            scriptureRef = "Salmo 98:4-9",
            chorus = "",
            lyrics = """
1. ¡Al mundo paz, nació Jesús,
Nació ya nuestro Rey!
El corazón ya tiene luz,
Y paz su santa grey,
Y paz su santa grey,
Y paz, y paz su santa grey.

2. ¡Al mundo paz, el Salvador
En tierra reinará!
Ya es feliz el pecador:
Jesús perdón le da,
Jesús perdón le da,
Jesús, Jesús perdón le da.

3. Al mundo gobernarás
Con gracia y con poder;
Y a las naciones probarás
Tu amor y tu saber,
Tu amor y tu saber,
Tu amor, tu amor y tu saber.
            """.trimIndent(),
            notes = "Himno jubiloso de Navidad.",
            isFavorite = true
        ),
        Hymn(
            number = 12,
            title = "Oíd un Son en Alta Esfera",
            category = "Navidad, Alabanza",
            tone = "Fa Mayor (F)",
            author = "Charles Wesley / Felix Mendelssohn",
            scriptureRef = "Lucas 2:13-14",
            chorus = "¡Oíd un son en alta esfera:\nEn los cielos gloria a Dios,\nY al mortal paz en la tierra!\nCanta la celeste voz.",
            lyrics = """
1. Oíd un son en alta esfera:
«¡En los cielos gloria a Dios,
Y al mortal paz en la tierra!»,
Canta la celeste voz.
Con los cielos alabemos
Al bendito Emanuel;
Con los santos adoremos
Al Mesías de Israel.

2. El Señor de los señores,
El Ungido celestial,
A salvar a pecadores
Vino en seno virginal.
¡Gloria al Verbo encarnado,
En la humanidad velado!
¡Gloria al Santo de Israel,
Nuestro amado Emanuel!

3. Príncipe de paz eterna,
Gloria a ti, Señor Jesús;
Entregaste gracia tierna
Y nos diste vida y luz.
Naces para renacer
A la prole de Adán,
Naces para socorrer
Al mortal en su afán.
            """.trimIndent(),
            notes = "Himno de Navidad solemne.",
            isFavorite = false
        ),

        // CATEGORÍA: HOGAR Y FAMILIA
        Hymn(
            number = 13,
            title = "Dios Bendiga Nuestro Hogar",
            category = "Hogar y Familia, Oración",
            tone = "Sol Mayor (G)",
            author = "Himno tradicional de familia",
            scriptureRef = "Josué 24:15",
            chorus = "Dios bendiga nuestro hogar,\nPaz y gracia sin cesar;\nEn Jesús unidos siempre,\nPara honrarle y alabar.",
            lyrics = """
1. Dios bendiga nuestra casa,
Sea un templo de oración,
Donde more tu palabra
Y reine la bendición.

2. Que los padres den ejemplo
De piedad y de virtud,
Y los hijos crezcan firmes
En amor y rectitud.

3. Cuando vengan las tormentas
Y el dolor quiera llegar,
En tus brazos encontramos
El refugio de este hogar.
            """.trimIndent(),
            notes = "Especial para cultos familiares y matrimonios.",
            isFavorite = true
        ),
        Hymn(
            number = 14,
            title = "Hogar Feliz, Hogar de Dios",
            category = "Hogar y Familia, Consagración",
            tone = "Mi Mayor (E)",
            author = "Himno de consagración hogareña",
            scriptureRef = "Salmo 128:1-4",
            chorus = "¡Hogar feliz, hogar de paz!\nDonde reina el Redentor;\n¡Hogar feliz, hogar de Dios!\nLleno de divino amor.",
            lyrics = """
1. Hogar feliz do Cristo mora,
Centro y gozo del vivir;
Donde cada nuevo día
Se dedica a bendecir.

2. Donde el llanto es consolado
Con la tierna voz de Dios,
Y en alegre compañerismo
Cantan todos a una voz.

3. Guarda, oh Dios, las familias
En pureza y santidad,
Hasta el día que en los cielos
Moremos por la eternidad.
            """.trimIndent(),
            notes = "Para dedicación de hogares y cultos de familia.",
            isFavorite = false
        ),

        // CATEGORÍA: NIÑOS
        Hymn(
            number = 15,
            title = "Cristo Me Ama",
            category = "Niños, Comunión, Gracia",
            tone = "Do Mayor (C)",
            author = "Anna B. Warner / William B. Bradbury",
            scriptureRef = "Marcos 10:14",
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
            notes = "Amado himno para niños y escuela dominical.",
            isFavorite = true
        ),
        Hymn(
            number = 16,
            title = "Bellas Palabras de Vida",
            category = "Niños, Fe y Confianza",
            tone = "Sol Mayor (G)",
            author = "Philip P. Bliss",
            scriptureRef = "Juan 6:68",
            chorus = "¡Bellas palabras de vida,\nSon sostén y guía!\n¡Bellas palabras de vida,\nLlenan de alegría!",
            lyrics = """
1. Oh, cantádmelas otra vez,
Bellas palabras de vida;
Hallo en ellas mi gozo y paz,
Bellas palabras de vida.
Sí, de luz y vida,
Son sostén y guía.

2. Jesucristo a todos da
Bellas palabras de vida;
Oye su tierna voz llamar,
Bellas palabras de vida.
Bondadoso te salva,
Y al cielo te llama.

3. Grato el cántico sonará,
Bellas palabras de vida;
Tus pecados perdonará,
Bellas palabras de vida.
Solo Cristo salva,
Vida eterna sana.
            """.trimIndent(),
            notes = "Para niños, jóvenes y congregación.",
            isFavorite = false
        ),

        // CATEGORÍA: RESURRECCIÓN
        Hymn(
            number = 17,
            title = "La Tumba le Encerró (¡Cristo Resucitó!)",
            category = "Resurrección, Alabanza",
            tone = "Si Bemol (Bb)",
            author = "Robert Lowry",
            scriptureRef = "Mateo 28:5-6",
            chorus = "¡Cristo la tumba venció!\nY con gran poder resucitó;\nHa vencido a la muerte y al Satán;\n¡Para siempre en los cielos reinará!\n¡Resucitó! ¡Resucitó!\n¡Aleluya, resucitó!",
            lyrics = """
1. La tumba le encerró,
Cristo mi Cristo;
El alba allí esperó
Cristo el Señor.

2. De balde guardas son,
Cristo mi Cristo;
De balde el gran sellón,
Cristo el Señor.

3. La muerte ya venció,
Cristo mi Cristo;
La tumba destruyó
Cristo el Señor.
            """.trimIndent(),
            notes = "Himno triunfal de Resurrección.",
            isFavorite = true
        ),
        Hymn(
            number = 18,
            title = "Cristo Ha Resucitado",
            category = "Resurrección, Culto Dominical",
            tone = "Do Mayor (C)",
            author = "Charles Wesley",
            scriptureRef = "1 Corintios 15:20",
            chorus = "",
            lyrics = """
1. ¡Cristo ha resucitado! ¡Aleluya!
Hombres y ángeles cantad: ¡Aleluya!
Canten los cielos su loor: ¡Aleluya!
Goce la tierra en su fulgor: ¡Aleluya!

2. El que en la cruz murió por nos: ¡Aleluya!
Hoy reina vivo ante Dios: ¡Aleluya!
La tumba ya no tiene afán: ¡Aleluya!
Vencido está el vil Satán: ¡Aleluya!

3. Rey soberano y Creador: ¡Aleluya!
Digno de gloria y de honor: ¡Aleluya!
Por tu victoria cantaré: ¡Aleluya!
Y vida eterna alcanzaré: ¡Aleluya!
            """.trimIndent(),
            notes = "Himno pascual de Resurrección.",
            isFavorite = true
        ),

        // OTROS HIMNOS CLÁSICOS
        Hymn(
            number = 19,
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
            number = 20,
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
            number = 21,
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
            number = 22,
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
            number = 23,
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
            number = 24,
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
            number = 25,
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
        // Himnos listos para agregar o completar texto
        Hymn(
            number = 26,
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
            number = 27,
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
            number = 28,
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
