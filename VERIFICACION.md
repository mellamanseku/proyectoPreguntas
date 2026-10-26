# Verificación del proyecto

Compilación comprobada el 26 de octubre de 2026 con JDK 17, Gradle 8.13, Android SDK 36 y Build Tools 36.0.0.

- `testDebugUnitTest`: 5 pruebas, 0 fallos, 0 errores y 0 omitidas.
- `assembleDebug`: compilación correcta, APK generado.
- Resultado de Gradle: `BUILD SUCCESSFUL in 4m 32s`.
- APK: `app/build/outputs/apk/debug/app-debug.apk`.

## Detalles

Las pruebas verifican NEXT y PREV circulares, los tres niveles del resultado y los porcentajes de estadísticas. No se afirma que estas pruebas sustituyan las comprobaciones de interfaz.

El APK se instaló en un emulador Android 11 (API 30) con resultado Success. Se intentó iniciar la aplicación, pero la interfaz del sistema Android mostró System UI isn't responding en este entorno sin aceleración. El registro de cierres de la app estaba vacío. Por esta limitación no se afirma haber validado visualmente todas las pantallas. La guía incluye vistas del código real, diferenciadas de capturas de ejecución. PRUEBAS_MANUALES.md indica las comprobaciones que deben repetirse en Android Studio o en un teléfono.

Repositorio de entrega: https://github.com/mellamanseku/proyectoPreguntas
