package com.example.pglpreguntas.ui.componentes

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.pglpreguntas.R
import com.example.pglpreguntas.ui.theme.Verde
import com.example.pglpreguntas.ui.theme.Rojo

@Composable
fun BotonRespuesta(valor: Boolean, correcta: Boolean, seleccionada: Boolean?,
                   enabled: Boolean, onResponder: (Boolean) -> Unit) {
    val color = when {
        seleccionada == null -> MaterialTheme.colorScheme.primary
        valor == correcta -> Verde
        valor == seleccionada -> Rojo
        else -> MaterialTheme.colorScheme.primary
    }
    Button(onClick = { onResponder(valor) }, enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = Color.White,
            disabledContainerColor = color, disabledContentColor = Color.White)) {
        Text(stringResource(if (valor) R.string.verdadero else R.string.falso))
    }
}
