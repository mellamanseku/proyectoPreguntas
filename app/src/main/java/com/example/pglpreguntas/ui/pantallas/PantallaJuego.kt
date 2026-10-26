package com.example.pglpreguntas.ui.pantallas

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.pglpreguntas.R
import com.example.pglpreguntas.data.PreguntaRepository
import com.example.pglpreguntas.model.Pregunta
import com.example.pglpreguntas.model.ReglasJuego
import com.example.pglpreguntas.ui.componentes.*
import com.example.pglpreguntas.ui.theme.Verde
import com.example.pglpreguntas.ui.theme.Rojo
import java.io.IOException
import kotlin.random.Random

@Composable
fun PantallaJuego(examen: Boolean, simple: Boolean, onRespuesta: (Boolean) -> Unit,
                  onClic: () -> Unit, onVolver: () -> Unit) {
    val context = LocalContext.current.applicationContext
    val repositorio = remember { PreguntaRepository(context) }
    var detalleError by remember { mutableStateOf<String?>(null) }
    var preguntas by remember {
        mutableStateOf(try { repositorio.cargarPreguntas() } catch (e: IOException) {
            detalleError = e.message ?: e.javaClass.simpleName
            emptyList<Pregunta>()
        })
    }
    var indice by rememberSaveable { mutableStateOf(0) }
    var seleccionada by rememberSaveable { mutableStateOf<Boolean?>(null) }
    var aciertosExamen by rememberSaveable { mutableStateOf(0) }
    var mostrarResultado by rememberSaveable { mutableStateOf(false) }
    BackHandler { onVolver() }

    fun cambiar(nuevoIndice: Int) {
        indice = nuevoIndice
        seleccionada = null
    }
    fun responder(valor: Boolean) {
        if (examen && seleccionada != null) return
        seleccionada = valor
        val correcta = valor == preguntas[indice].respuesta
        if (examen) {
            if (correcta) aciertosExamen++
        } else {
            onRespuesta(correcta)
        }
    }

    EstructuraPantalla {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Medidas.espacio)) {
            if (detalleError != null || preguntas.isEmpty()) {
                Text(stringResource(R.string.error_datos))
                if (detalleError != null) Text(stringResource(R.string.detalle_error, detalleError!!))
                Button(onClick = {
                    if (!examen) onClic()
                    try {
                        preguntas = repositorio.cargarPreguntas()
                        indice = 0; seleccionada = null; aciertosExamen = 0
                        mostrarResultado = false; detalleError = null
                    }
                    catch (e: IOException) { detalleError = e.message ?: e.javaClass.simpleName }
                }) { Text(stringResource(R.string.reintentar)) }
            } else {
                val pregunta = preguntas[indice]
                Text(stringResource(R.string.pregunta_numero, indice + 1, preguntas.size),
                    style = MaterialTheme.typography.titleMedium)
                Text(pregunta.enunciado, style = MaterialTheme.typography.headlineSmall)
                if (!simple) ImagenPregunta(pregunta.imagen)
                if (simple) LinearProgressIndicator(
                    progress = { (indice + 1).toFloat() / preguntas.size }, modifier = Modifier.fillMaxWidth())
                if (seleccionada != null) {
                    val correcta = seleccionada == pregunta.respuesta
                    Text(stringResource(if (correcta) R.string.acierto else R.string.fallo),
                        color = if (correcta) Verde else Rojo)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(Medidas.espacio)) {
                    BotonRespuesta(true, pregunta.respuesta, seleccionada,
                        !examen || seleccionada == null, ::responder)
                    BotonRespuesta(false, pregunta.respuesta, seleccionada,
                        !examen || seleccionada == null, ::responder)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(Medidas.espacio)) {
                    if (!examen) {
                        Button(onClick = { onClic(); cambiar(ReglasJuego.anterior(indice, preguntas.size)) }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            Text(stringResource(R.string.prev))
                        }
                    }
                    Button(enabled = !examen || seleccionada != null, onClick = {
                        if (!examen) onClic()
                        if (examen && indice == preguntas.lastIndex) mostrarResultado = true
                        else cambiar(ReglasJuego.siguiente(indice, preguntas.size))
                    }) {
                        Text(stringResource(if (examen && indice == preguntas.lastIndex) R.string.terminar else R.string.next))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
                if (!examen) Button(onClick = { onClic(); cambiar(Random.nextInt(preguntas.size)) }) {
                    Text(stringResource(R.string.random))
                }
            }
        }
        TextButton(onClick = onVolver) { Text(stringResource(R.string.volver)) }
    }
    if (mostrarResultado) DialogoResultado(aciertosExamen, preguntas.size, onVolver)
}
