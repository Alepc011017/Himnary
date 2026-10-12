# Himnario Cristiano 📖🎵

Una aplicación moderna, elegante y reverente para Android diseñada para la alabanza congregacional, estudio personal y directores de adoración. Desarrollada con **Kotlin**, **Jetpack Compose** y **Material Design 3**, con soporte para lectura cómoda en atril o púlpito, persistencia local con **Room Database** y un completo sistema de creación y categorización de himnos.

---

## ✨ Características Principales

### ✍️ Creación y Edición de Textos de Himnos
* **Agregar Nuevos Himnos:** Permite registrar nuevos himnos asignando número (con sugerencia automática), título, tono musical, autor, citas bíblicas y notas litúrgicas.
* **Editor Asistido de Estrofas:** Herramientas de inserción rápida (`+ Estrofa 1`, `+ Estrofa 2`, etc.) y botón para pegar texto copiado del portapapeles.
* **Sección de Coro Destacada:** Campo específico para el coro, que se resalta con un marco estilizado, tipografía en cursiva e ícono musical en la pantalla de canto.
* **Completar Textos Después:** Himnos precargados esperando su letra cuentan con un aviso visual directo y el filtro rápido *"Sin Letra"* para redactarlos o pegarlos cuando lo desees.
* **Vista Previa en Vivo:** Pestaña interactiva en el editor para verificar exactamente cómo lucirá el himno antes de guardarlo.

---

### 🏷️ Sistema de Categorización Personalizada y Múltiple
* **Categorías Personalizadas:** Crea tus propias categorías (por ejemplo: *Culto de Jóvenes*, *Vigilia*, *Santa Cena*, *Misiones*) asignándoles un color representativo.
* **Asignación Múltiple:** Cada himno puede pertenecer a una o más categorías simultáneamente (ejemplo: *Alabanza* y *Culto Dominical*).
* **Agrupación en la Pantalla Principal:** Visualiza los himnos ordenados por sus categorías en secciones colapsables y expandibles, o cambia a la vista de lista general tradicional con un solo toque.

---

### 🔍 Ajuste Dinámico del Tamaño de Fuente
* **Control Deslizante Continuo:** Slider dinámico en el lector (de `14 sp` a `36 sp`) que actualiza el tamaño del texto en tiempo real.
* **Preajustes Rápidos para Cantar:**
  * **Compacto (`14 sp`)**: Mayor cantidad de texto visible.
  * **Normal (`18 sp`)**: Lectura equilibrada de uso cotidiano.
  * **Grande (`22 sp`)**: Cómodo para vista cansada.
  * **Muy Grande (`28 sp`)**: Lectura clara a distancia.
  * **Púlpito / Atril (`34 sp`)**: Diseñado para leer sin esfuerzo desde un atril o soporte de instrumentos.
* **Gesto Táctil (Pinch-to-Zoom):** Pellizca con dos dedos sobre la letra para agrandar o reducir el texto de forma natural.
* **HUD Flotante:** Indicador visual que muestra el valor exacto de la fuente y porcentaje de escala al ajustar.

---

### 📜 Experiencia de Lectura Sagrada
* **Temas Visuales Especializados:**
  * 📜 **Pergamino:** Fondo cálido sepia que descansa la vista.
  * ☀️ **Luz:** Fondo blanco limpio para lectura diurna.
  * 🌙 **Noche:** Fondo azul noche profundo para cultos con iluminación tenue.
* **Estilos Tipográficos:** Alterna con un toque entre estilo **Serif litúrgico** y **Sans-serif limpio**.
* **Desplazamiento Automático (Auto-Scroll):** Avance continuo de la pantalla con velocidad regulable (`0.5x`, `1.0x`, `1.5x`, `2.0x`) y botón de pausa/reanudación para cantar con manos libres.
* **Navegación Fluida:** Botones de *Anterior* y *Siguiente* para hojear los himnos como en un himnario impreso.
* **Copiar y Compartir:** Envía la letra completa del himno con su coro y estrofas por WhatsApp, correo o redes sociales.

---

### ⚡ Búsqueda y Navegación Ágil
* **Marcador Numérico (Dialpad):** Teclado numérico táctil flotante para saltar directamente al número del himno anunciado en el culto (ej. teclear `12` y abrirlo al instante).
* **Búsqueda Global en Tiempo Real:** Filtra por número, título, categoría o cualquier palabra contenida en las estrofas.
* **Favoritos:** Marca himnos con una estrella para tenerlos siempre a mano.

---

## 🛠️ Arquitectura y Tecnologías

* **Lenguaje:** Kotlin
* **UI Toolkit:** Jetpack Compose con Material Design 3 (M3)
* **Persistencia:** Room Database (SQLite) con migración y precarga inicial
* **Gestión de Estado:** ViewModel, `StateFlow` y Corrutinas (`collectAsStateWithLifecycle`)
* **Asincronía y Flujos:** Kotlin Flow para consultas reactivas a la base de datos
* **Assets Visuales:** Ícono adaptable Material You (vectorial + rasterizado en todas las densidades de mipmap) y banner ilustrado de santuario

---

## 📁 Estructura del Código

```
app/src/main/java/com/example/
├── MainActivity.kt                      # Punto de entrada y navegación con Scaffold
├── data/
│   ├── Hymn.kt                          # Entidad Room del himno (estrofas, coro, categorías)
│   ├── HymnCategory.kt                  # Entidad Room para categorías personalizadas
│   ├── HymnDao.kt                       # Consultas y operaciones de himnos
│   ├── CategoryDao.kt                   # Operaciones CRUD para categorías
│   ├── AppDatabase.kt                   # Configuración de Room y precarga
│   ├── HymnRepository.kt                # Repositorio que desacopla la capa de datos
│   └── SampleHymns.kt                   # Catálogo inicial de himnos clásicos en español
└── ui/
    ├── HymnViewModel.kt                 # Lógica de presentación, filtros, fuentes y temas
    ├── screens/
    │   ├── HymnListScreen.kt            # Lista principal agrupada y buscador
    │   ├── HymnDetailScreen.kt          # Lector con auto-scroll, zoom y temas
    │   ├── HymnEditScreen.kt            # Editor y creador con vista previa en vivo
    │   ├── ManageCategoriesDialog.kt    # Diálogo de gestión de categorías y colores
    │   └── QuickNumberDialog.kt         # Teclado numérico táctil para salto directo
    └── theme/
        ├── Color.kt                     # Paleta sacra (Azul Zafiro, Oro, Borgoña, Marfil)
        ├── Theme.kt                     # Configuración de MaterialTheme
        └── Type.kt                      # Definición de tipografía M3
```

---

## 🚀 Requisitos de Ejecución

* **Android SDK mínimo:** Android 7.0 (API 24)
* **Target SDK:** API 36
* **JDK:** Java 11 o superior
* **Gradle:** 8.x+ / Kotlin 2.2+

Para compilar y ejecutar los tests unitarios en local:
```bash
gradle :app:testDebugUnitTest
```
