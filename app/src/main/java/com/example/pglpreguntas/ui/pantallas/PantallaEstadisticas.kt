package com.example.pglpreguntas.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.pglpreguntas.R
import com.example.pglpreguntas.model.Estadisticas
import com.example.pglpreguntas.ui.componentes.*

@Composable
fun PantallaEstadisticas(datos: Estadisticas, onVolver: () -> Unit) {
    EstructuraPantalla {
        Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(Medidas.espacio)) {
            Text(stringResource(R.string.estadisticas), style = MaterialTheme.typography.headlineMedium)
            Text(stringResource(R.string.aciertos, datos.aciertos))
            Text(stringResource(R.string.fallos, datos.fallos))
            Text(stringResource(R.string.porcentaje_aciertos, datos.porcentajeAciertos()))
            Text(stringResource(R.string.porcentaje_fallos, datos.porcentajeFallos()))
            Text(stringResource(R.string.clics, datos.clics))
            if (datos.aciertos + datos.fallos == 0) Text(stringResource(R.string.sin_respuestas))
            Text(stringResource(R.string.estadisticas_ayuda))
            TextButton(onClick = onVolver) { Text(stringResource(R.string.volver)) }
        }
    }
}
