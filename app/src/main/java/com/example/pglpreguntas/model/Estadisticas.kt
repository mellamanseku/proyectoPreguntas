package com.example.pglpreguntas.model

data class Estadisticas(val aciertos: Int = 0, val fallos: Int = 0, val clics: Int = 0) {
    fun porcentajeAciertos(): Float {
        val total = aciertos + fallos
        if (total == 0) return 0f
        return aciertos * 100f / total
    }
    fun porcentajeFallos(): Float {
        val total = aciertos + fallos
        if (total == 0) return 0f
        return fallos * 100f / total
    }
}
