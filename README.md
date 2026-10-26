# Práctica 2 de PGL

Repositorio: https://github.com/mellamanseku/proyectoPreguntas

[Guía paso a paso con imágenes del código](docs/Guia_PGL.docx)

Aplicación nativa Android de preguntas verdadero o falso, escrita en Kotlin con Jetpack Compose. Sigue el Anexo I del enunciado y utiliza Navigation 3, como los apuntes de actividades y navegación.

## Abrir y ejecutar

1. Descomprime la entrega y abre la carpeta **PGLPractica2** desde **File > Open** en Android Studio.
2. Usa una versión de Android Studio compatible con Android Gradle Plugin 8.13.2 (Otter 2025.2.1 o posterior). No crees un proyecto vacío ni pegues archivos encima de otra plantilla.
3. En **Settings > Build, Execution, Deployment > Build Tools > Gradle**, selecciona un JDK 17 o superior compatible con Gradle 8.13.
4. En **SDK Manager**, instala **Android SDK Platform 36** y **Android SDK Build Tools 36.0.0**. Acepta las licencias que muestre Android Studio.
5. Espera a que termine **Sync Project with Gradle Files**. La primera vez necesita Internet.
6. Selecciona un emulador o un teléfono con Android 6.0 o superior y pulsa **Run app**.

Desde terminal, en la carpeta raíz:

```sh
# Linux y macOS
chmod +x gradlew
./gradlew testDebugUnitTest assembleDebug
```

```bat
:: Windows
 gradlew.bat testDebugUnitTest assembleDebug
```

El APK se genera en `app/build/outputs/apk/debug/app-debug.apk`.

## Carpetas del profesor

- `MainActivity.kt`: inicializa Compose y llama al componente principal.
- `navigation/Keys.kt`: destinos serializables de Navigation 3.
- `navigation/Navigation.kt`: pila y `NavDisplay`. Envía callbacks a los hijos.
- `model/`: `Pregunta`, `Estadisticas` y funciones sencillas de `ReglasJuego`.
- `data/PreguntaRepository.kt`: `cargarPreguntas()` y `guardarPregunta()`.
- `data/EstadisticasRepository.kt`: lectura y escritura de SharedPreferences.
- `ui/pantallas/`: inicio, selección de modos, juego, inserción y estadísticas.
- `ui/componentes/`: botones, imagen, diálogo, contenedor y medidas compartidas.
- `ui/theme/`: colores y tema Material 3.
- `res/values/strings.xml`: textos de interfaz, claves y nombres de ficheros.
- `res/drawable/`: imágenes vectoriales locales. La aplicación funciona sin Internet.

## Apartados incluidos

| Apartado | Puntos del enunciado | Implementación |
| --- | ---: | --- |
| Cinco preguntas, imagen, TRUE/FALSE, NEXT circular, corrección verde/roja | 5 | PantallaJuego, BotonRespuesta, ImagenPregunta |
| PREV circular con flecha izquierda | 0,25 | PantallaJuego, ReglasJuego |
| RANDOM | 0,25 | PantallaJuego |
| Menú, pila, estadísticas y SharedPreferences | 1 | Navigation, PantallaInicio, PantallaEstadisticas, EstadisticasRepository |
| Examen y tres resultados con imagen y texto | 1 | PantallaJuego, DialogoResultado |
| Añadir preguntas, fichero sin sobrescribir, Toast y repositorio | 1,5 | PantallaAnadir, PreguntaRepository |
| Simple sin imágenes y con barra de progreso | 1 | PantallaJuego |

El examen incluye todas las preguntas guardadas al entrar en él. No permite saltar preguntas, retroceder ni cambiar una respuesta. Sus botones, aciertos y fallos no alteran las estadísticas generales. La selección de Examen también queda fuera del contador.

Normal y Simple permiten repetir respuestas, como la práctica básica. Cada pulsación de TRUE/FALSE registra un intento. Los porcentajes se calculan con aciertos + fallos, nunca con el total de clics. Los demás botones fuera del examen también suman clics. SharedPreferences se guarda en cada evento, para no perder datos si se cierra el proceso.

En Simple, la barra indica `(posición + 1) / número de preguntas`. Al llegar al final, NEXT vuelve a la primera y reinicia la barra.

## Almacenamiento

En el primer acceso se escriben cinco preguntas iniciales en `filesDir/preguntas.txt`. A partir de entonces se leen desde ese fichero, incluyendo las añadidas. Formato UTF-8:

```text
El animal nacional de Escocia es el unicornio|true|logo
```

Los campos son enunciado, respuesta y nombre de imagen. La escritura de nuevas preguntas utiliza `FileOutputStream(archivo, true)`. Los textos nuevos deben tener de 1 a 300 caracteres y no pueden incluir `|` ni saltos de línea. Las preguntas nuevas utilizan la imagen genérica. No se necesitan permisos de almacenamiento.

Para verlo: **View > Tool Windows > Device Explorer**, después `/data/data/com.example.pglpreguntas/files/preguntas.txt` en una instalación debug. Las preferencias están en `shared_prefs/estadisticas_pgl.xml`. Desinstalar o borrar datos elimina preguntas y estadísticas.

## Conceptos de clase

Se usan Row, Column, Image, Text, Button, Modifier, estado y recomposición, elevación de estado, rememberSaveable, TextField con estado, RadioButton, MaterialTheme, AlertDialog, LazyColumn/ListItem y Navigation 3. Ficheros, Toast, SharedPreferences y LinearProgressIndicator aparecen en la propia práctica. No se añaden ViewModel, Room, Retrofit, DataStore, corrutinas ni inyección de dependencias.

`@Serializable` y su plugin son necesarios para conservar las claves de `rememberNavBackStack`. Las versiones del proyecto están fijadas para que no dependan de la versión de una plantilla nueva de Android Studio.

## Comprobaciones

Consulta `VERIFICACION.md` para el resultado real y `docs/Guia_PGL.docx` para la explicación con capturas. El flujo de GitHub Actions compila y ejecuta las catorce pruebas unitarias al subir el proyecto. El informe de pruebas queda en `app/build/reports/tests/testDebugUnitTest/`.

## Subir a GitHub desde tu equipo

Si necesitas hacer la subida manual, crea un repositorio vacío en tu cuenta, abre la terminal dentro de esta carpeta y ejecuta:

```sh
git init
git add .
git commit -m "Completar practica 2 de PGL"
git branch -M main
git remote add origin URL_DEL_REPOSITORIO
git push -u origin main
```

Sustituye `URL_DEL_REPOSITORIO` por la URL real que te facilite GitHub. Android Studio también permite hacerlo desde **VCS > Share Project on GitHub**. No subas `local.properties`, carpetas de compilación ni SDKs. El `.gitignore` ya los excluye.

El enunciado conserva una fecha de entrega de 2023; no se interpreta como la fecha actual del curso. Confirma la entrega vigente con el profesor.

## Corrección de lectura en la versión 1.1

La lectura ignora líneas vacías, admite respuestas True/False y registros antiguos sin imagen. Si encuentra registros incompletos, primero guarda una copia exacta en `preguntas.txt.copia-FECHA` y conserva todas las preguntas válidas. Si no queda ninguna, recupera las cinco iniciales. Si el acceso al almacenamiento falla, muestra el detalle del error para diagnosticarlo. Las nuevas preguntas se siguen añadiendo al final sin sobrescribir las anteriores.

Se han ejecutado 14 pruebas: cinco de lógica y nueve de lectura, recuperación y escritura. El APK 1.1 (versionCode 2) puede instalarse como actualización de la versión de prueba anterior, conservando sus datos.
