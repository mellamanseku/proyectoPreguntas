package com.example.pglpreguntas.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.pglpreguntas.data.EstadisticasRepository
import com.example.pglpreguntas.ui.pantallas.*

@Composable
fun Navigation() {
    val context = LocalContext.current.applicationContext
    val repositorio = remember { EstadisticasRepository(context) }
    var datos by remember { mutableStateOf(repositorio.cargar()) }
    val pila = rememberNavBackStack(Inicio)

    // El examen no llama a esta función. Sus resultados son independientes.
    fun registrarClic(respuestaCorrecta: Boolean? = null) {
        datos = datos.copy(
            aciertos = datos.aciertos + if (respuestaCorrecta == true) 1 else 0,
            fallos = datos.fallos + if (respuestaCorrecta == false) 1 else 0,
            clics = datos.clics + 1
        )
        // Guardar en cada evento también evita perder datos al cerrar la app.
        repositorio.guardar(datos)
    }
    fun volver(contar: Boolean = true) {
        if (contar) registrarClic()
        if (pila.size > 1) pila.removeAt(pila.lastIndex)
    }

    NavDisplay(
        backStack = pila,
        onBack = { volver(contar = pila.last() !is Juego || !(pila.last() as Juego).examen) },
        entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator()),
        entryProvider = entryProvider {
            entry<Inicio> {
                PantallaInicio(
                    onAnadir = { registrarClic(); pila.add(Insertar) },
                    onJugar = { registrarClic(); pila.add(Modos) },
                    onEstadisticas = { registrarClic(); pila.add(VerEstadisticas) }
                )
            }
            entry<Modos> {
                PantallaModos(
                    onNormal = { registrarClic(); pila.add(Juego(false, false)) },
                    onExamen = { pila.add(Juego(true, false)) },
                    onSimple = { registrarClic(); pila.add(Juego(false, true)) },
                    onVolver = { volver() }
                )
            }
            entry<Juego> { destino ->
                PantallaJuego(examen = destino.examen, simple = destino.simple,
                    onRespuesta = { correcta -> registrarClic(correcta) },
                    onClic = { registrarClic() },
                    onVolver = { volver(contar = !destino.examen) })
            }
            entry<Insertar> {
                PantallaAnadir(onClic = { registrarClic() }, onVolver = { volver() })
            }
            entry<VerEstadisticas> {
                PantallaEstadisticas(datos = datos, onVolver = { volver() })
            }
        }
    )
}
