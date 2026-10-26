package com.example.pglpreguntas.model

// Funciones pequeñas que también podemos comprobar sin un teléfono.
object ReglasJuego {
    fun siguiente(indice: Int, total: Int): Int = (indice + 1) % total
    fun anterior(indice: Int, total: Int): Int = (indice + total - 1) % total
    fun nivelResultado(aciertos: Int, total: Int): Int {
        if (aciertos == total) return 2
        if (aciertos * 2 >= total) return 1
        return 0
    }
}
