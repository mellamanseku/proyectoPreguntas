package com.example.pglpreguntas.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.*
import com.example.pglpreguntas.R
import com.example.pglpreguntas.ui.componentes.*

@Composable
fun PantallaInicio(onAnadir: () -> Unit, onJugar: () -> Unit, onEstadisticas: () -> Unit) {
    EstructuraPantalla {
        Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(Medidas.espacio)) {
            Text(stringResource(R.string.menu_titulo), style = MaterialTheme.typography.headlineMedium)
            Text(stringResource(R.string.menu_subtitulo))
            Image(painterResource(R.drawable.logo), stringResource(R.string.logo_descripcion), Modifier.size(Medidas.imagen))
            Button(onClick = onAnadir, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.anadir)) }
            Button(onClick = onJugar, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.jugar)) }
            Button(onClick = onEstadisticas, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.estadisticas)) }
        }
    }
}
