package com.example.pglpreguntas.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.pglpreguntas.R
import com.example.pglpreguntas.ui.componentes.*

@Composable
fun PantallaModos(onNormal: () -> Unit, onExamen: () -> Unit, onSimple: () -> Unit, onVolver: () -> Unit) {
    EstructuraPantalla {
        Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(Medidas.espacio)) {
            Text(stringResource(R.string.modos), style = MaterialTheme.typography.headlineMedium)
            Text(stringResource(R.string.modo_ayuda))
            Button(onClick = onNormal) { Text(stringResource(R.string.normal)) }
            Button(onClick = onExamen) { Text(stringResource(R.string.examen)) }
            Button(onClick = onSimple) { Text(stringResource(R.string.simple)) }
            TextButton(onClick = onVolver) { Text(stringResource(R.string.volver)) }
        }
    }
}
