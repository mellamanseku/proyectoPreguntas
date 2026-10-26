package com.example.pglpreguntas.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.pglpreguntas.R

@Composable
fun ImagenPregunta(nombre: String) {
    val recurso = when (nombre) {
        "tierra" -> R.drawable.tierra
        "triangulo" -> R.drawable.triangulo
        "agua" -> R.drawable.agua
        "islas" -> R.drawable.islas
        "android_logo" -> R.drawable.android_logo
        else -> R.drawable.logo
    }
    Image(painter = painterResource(recurso), contentDescription = stringResource(R.string.imagen_pregunta),
        modifier = Modifier.size(Medidas.imagen))
}
