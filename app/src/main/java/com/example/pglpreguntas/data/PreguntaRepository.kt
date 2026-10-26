package com.example.pglpreguntas.data

import android.content.Context
import com.example.pglpreguntas.R
import com.example.pglpreguntas.model.Pregunta
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

// El constructor con File permite comprobar el almacenamiento con pruebas locales.
class PreguntaRepository(private val archivo: File, private val iniciales: List<Pregunta>) {
    constructor(context: Context) : this(
        File(context.filesDir, context.getString(R.string.archivo_preguntas)),
        listOf(
            Pregunta(context.getString(R.string.pregunta_1), true, "logo"),
            Pregunta(context.getString(R.string.pregunta_2), true, "logo"),
            Pregunta(context.getString(R.string.pregunta_3), true, "logo"),
            Pregunta(context.getString(R.string.pregunta_4), false, "logo"),
            Pregunta(context.getString(R.string.pregunta_5), true, "logo")
        )
    )

    fun cargarPreguntas(): List<Pregunta> {
        if (!archivo.exists()) escribirPreguntas(iniciales)
        val preguntas = mutableListOf<Pregunta>()
        var hayLineasIncorrectas = false
        FileInputStream(archivo).bufferedReader(Charsets.UTF_8).use { lector ->
            var linea = lector.readLine()
            while (linea != null) {
                // Una línea vacía no es una pregunta ni debe bloquear el juego.
                val texto = linea.trim().removePrefix("\uFEFF")
                if (texto.isNotBlank()) {
                    val partes = texto.split('|')
                    val respuesta = if (partes.size >= 2) partes[1].trim().lowercase() else ""
                    if (partes.size !in 2..3 || partes[0].isBlank() ||
                        (respuesta != "true" && respuesta != "false")) {
                        hayLineasIncorrectas = true
                    } else {
                        // También admite registros antiguos sin imagen y True/False.
                        val imagen = if (partes.size == 3 && partes[2].isNotBlank())
                            partes[2].trim() else "logo"
                        preguntas.add(Pregunta(partes[0].trim(), respuesta == "true", imagen))
                    }
                }
                linea = lector.readLine()
            }
        }
        if (hayLineasIncorrectas || preguntas.isEmpty()) {
            // Primero conservamos los bytes originales. Si falla, no tocamos el fichero.
            val copia = File(archivo.parentFile, archivo.name + ".copia-" + System.currentTimeMillis())
            archivo.copyTo(copia)
            // Conservamos todas las preguntas válidas que hubiera añadido el usuario.
            if (preguntas.isEmpty()) preguntas.addAll(iniciales)
            escribirPreguntas(preguntas)
        }
        return preguntas
    }

    private fun escribirPreguntas(preguntas: List<Pregunta>) {
        // Escribimos y cerramos un temporal antes de sustituir el fichero de datos.
        val temporal = File(archivo.parentFile, archivo.name + ".tmp")
        FileOutputStream(temporal).bufferedWriter(Charsets.UTF_8).use { escritor ->
            for (pregunta in preguntas) {
                escritor.write("${pregunta.enunciado}|${pregunta.respuesta}|${pregunta.imagen}")
                escritor.newLine()
            }
        }
        if (!temporal.renameTo(archivo)) {
            throw IOException("No se ha podido actualizar " + archivo.name)
        }
    }

    fun guardarPregunta(pregunta: Pregunta) {
        require(pregunta.enunciado.isNotBlank() && pregunta.enunciado.length <= 300)
        require(!pregunta.enunciado.contains('|') && !pregunta.enunciado.contains('\n') &&
            !pregunta.enunciado.contains('\r'))
        cargarPreguntas()
        // Antes de añadir, normalizamos el final de línea para no pegar dos registros.
        val necesitaSalto = FileInputStream(archivo).use { entrada ->
            var ultimo = -1
            var actual = entrada.read()
            while (actual != -1) { ultimo = actual; actual = entrada.read() }
            ultimo != -1 && ultimo != '\n'.code && ultimo != '\r'.code
        }
        FileOutputStream(archivo, true).bufferedWriter(Charsets.UTF_8).use { escritor ->
            if (necesitaSalto) escritor.newLine()
            escritor.write("${pregunta.enunciado}|${pregunta.respuesta}|${pregunta.imagen}")
            escritor.newLine()
        }
    }
}
