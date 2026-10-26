package com.example.pglpreguntas.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.pglpreguntas.R
import com.example.pglpreguntas.model.ReglasJuego

@Composable
fun DialogoResultado(aciertos: Int, total: Int, onCerrar: () -> Unit) {
    val nivel = ReglasJuego.nivelResultado(aciertos, total)
    val texto = when (nivel) { 2 -> R.string.perfecto; 1 -> R.string.aprobado; else -> R.string.suspenso }
    val imagen = when (nivel) { 2 -> R.drawable.resultado_pleno; 1 -> R.drawable.resultado_aprobado; else -> R.drawable.resultado_suspenso }
    AlertDialog(onDismissRequest = onCerrar,
        icon = { Image(painterResource(imagen), stringResource(R.string.resultado_descripcion), Modifier.size(Medidas.imagen)) },
        title = { Text(stringResource(R.string.resultado)) },
        text = { Text(stringResource(R.string.nota, aciertos, total) + "\n" + stringResource(texto)) },
        confirmButton = { TextButton(onClick = onCerrar) { Text(stringResource(R.string.aceptar)) } })
}
