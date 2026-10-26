package com.example.pglpreguntas.data

import android.content.Context
import com.example.pglpreguntas.R
import com.example.pglpreguntas.model.Pregunta
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class PreguntaRepository(private val context: Context) {
    private val archivo = File(context.filesDir, context.getString(R.string.archivo_preguntas))

    fun cargarPreguntas(): List<Pregunta> {
        // Solo la primera vez creamos las cinco preguntas iniciales.
        // Después el fichero es el almacén de TODAS las preguntas.
        if (!archivo.exists()) {
            val iniciales = listOf(
                Pregunta(context.getString(R.string.pregunta_1), true, "logo"),
                Pregunta(context.getString(R.string.pregunta_2), true, "logo"),
                Pregunta(context.getString(R.string.pregunta_3), true, "logo"),
                Pregunta(context.getString(R.string.pregunta_4), false, "logo"),
                Pregunta(context.getString(R.string.pregunta_5), true, "logo")
            )
            FileOutputStream(archivo).bufferedWriter(Charsets.UTF_8).use { escritor ->
                for (pregunta in iniciales) {
                    escritor.write("${pregunta.enunciado}|${pregunta.respuesta}|${pregunta.imagen}")
                    escritor.newLine()
                }
            }
        }
        val preguntas = mutableListOf<Pregunta>()
        FileInputStream(archivo).bufferedReader(Charsets.UTF_8).use { lector ->
            var linea = lector.readLine()
            while (linea != null) {
                val partes = linea.split('|')
                if (partes.size != 3 || partes[0].isBlank() ||
                    (partes[1] != "true" && partes[1] != "false")) {
                    throw IOException(context.getString(R.string.error_fichero))
                }
                preguntas.add(Pregunta(partes[0], partes[1] == "true", partes[2]))
                linea = lector.readLine()
            }
        }
        if (preguntas.isEmpty()) throw IOException(context.getString(R.string.error_fichero))
        return preguntas
    }

    fun guardarPregunta(pregunta: Pregunta) {
        require(pregunta.enunciado.isNotBlank() && pregunta.enunciado.length <= 300)
        require(!pregunta.enunciado.contains('|') && !pregunta.enunciado.contains('\n') &&
            !pregunta.enunciado.contains('\r'))
        cargarPreguntas() // Comprueba e inicializa el fichero antes de añadir.
        // true activa append y conserva las preguntas que ya estaban guardadas.
        FileOutputStream(archivo, true).bufferedWriter(Charsets.UTF_8).use { escritor ->
            escritor.write("${pregunta.enunciado}|${pregunta.respuesta}|${pregunta.imagen}")
            escritor.newLine()
        }
    }
}
