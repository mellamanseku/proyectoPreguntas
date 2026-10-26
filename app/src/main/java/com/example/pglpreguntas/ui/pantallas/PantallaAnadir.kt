package com.example.pglpreguntas.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.pglpreguntas.R
import com.example.pglpreguntas.data.PreguntaRepository
import com.example.pglpreguntas.model.Pregunta
import com.example.pglpreguntas.ui.componentes.*
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAnadir(onClic: () -> Unit, onVolver: () -> Unit) {
    val context = LocalContext.current.applicationContext
    val repositorio = remember { PreguntaRepository(context) }
    val texto = rememberTextFieldState()
    var respuesta by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf(true) }
    var mensaje by remember { mutableStateOf(0) }
    val preguntas = remember { mutableStateListOf<Pregunta>() }
    // remember ejecuta esta lectura una sola vez por entrada en la pantalla.
    remember {
        try { preguntas.addAll(repositorio.cargarPreguntas()) }
        catch (e: IOException) { mensaje = R.string.error_datos }
        true
    }
    EstructuraPantalla {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Medidas.espacio)) {
            Text(stringResource(R.string.anadir), style = MaterialTheme.typography.headlineMedium)
            TextField(state = texto, label = { Text(stringResource(R.string.enunciado)) },
                modifier = Modifier.fillMaxWidth())
            Text(stringResource(R.string.respuesta))
            Row {
                RadioButton(selected = respuesta, onClick = { onClic(); respuesta = true })
                Text(stringResource(R.string.verdadero))
                RadioButton(selected = !respuesta, onClick = { onClic(); respuesta = false })
                Text(stringResource(R.string.falso))
            }
            Button(onClick = {
                onClic()
                val enunciado = texto.text.toString().trim()
                if (enunciado.isBlank() || enunciado.length > 300) mensaje = R.string.error_vacia
                else if (enunciado.contains('|') || enunciado.contains('\n') || enunciado.contains('\r'))
                    mensaje = R.string.error_formato
                else {
                    try {
                        val nueva = Pregunta(enunciado, respuesta, "logo")
                        repositorio.guardarPregunta(nueva)
                        preguntas.add(nueva)
                        texto.edit { replace(0, length, "") }
                        mensaje = 0
                        Toast.makeText(context, context.getString(R.string.guardada), Toast.LENGTH_SHORT).show()
                    } catch (e: IOException) { mensaje = R.string.error_datos }
                }
            }) { Text(stringResource(R.string.guardar)) }
            if (mensaje != 0) Text(stringResource(mensaje), color = MaterialTheme.colorScheme.error)
        }
        Text(stringResource(R.string.preguntas_guardadas), style = MaterialTheme.typography.titleMedium)
        LazyColumn(Modifier.weight(1f)) {
            itemsIndexed(preguntas, key = { indice, _ -> indice }) { _, pregunta ->
                ListItem(headlineContent = { Text(pregunta.enunciado) }, supportingContent = {
                    Text(stringResource(R.string.respuesta_lista,
                        stringResource(if (pregunta.respuesta) R.string.verdadero else R.string.falso)))
                })
            }
        }
        TextButton(onClick = onVolver) { Text(stringResource(R.string.volver)) }
    }
}
