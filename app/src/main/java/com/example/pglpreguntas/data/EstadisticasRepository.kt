package com.example.pglpreguntas.data

import android.content.Context
import com.example.pglpreguntas.R
import com.example.pglpreguntas.model.Estadisticas

class EstadisticasRepository(context: Context) {
    private val preferencias = context.getSharedPreferences(
        context.getString(R.string.prefs_nombre), Context.MODE_PRIVATE)
    private val aciertos = context.getString(R.string.clave_aciertos)
    private val fallos = context.getString(R.string.clave_fallos)
    private val clics = context.getString(R.string.clave_clics)

    fun cargar(): Estadisticas = Estadisticas(
        preferencias.getInt(aciertos, 0), preferencias.getInt(fallos, 0),
        preferencias.getInt(clics, 0))

    fun guardar(datos: Estadisticas) {
        preferencias.edit().putInt(aciertos, datos.aciertos)
            .putInt(fallos, datos.fallos).putInt(clics, datos.clics).apply()
    }
}
