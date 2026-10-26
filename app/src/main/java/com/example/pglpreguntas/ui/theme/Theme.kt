package com.example.pglpreguntas.ui.theme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
@Composable
fun PGLTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(primary = Azul, background = Fondo, error = Rojo),
        typography = Typography(),
        content = content
    )
}
