package com.example.pglpreguntas
import com.example.pglpreguntas.model.*
import org.junit.Assert.*
import org.junit.Test
class ReglasJuegoTest {
    @Test fun nextVuelveAlPrincipio() { assertEquals(0, ReglasJuego.siguiente(4, 5)) }
    @Test fun prevVuelveAlFinal() { assertEquals(4, ReglasJuego.anterior(0, 5)) }
    @Test fun resultadoDistingueLosTresTramos() {
        assertEquals(2, ReglasJuego.nivelResultado(5, 5))
        assertEquals(1, ReglasJuego.nivelResultado(3, 5))
        assertEquals(1, ReglasJuego.nivelResultado(3, 6))
        assertEquals(0, ReglasJuego.nivelResultado(2, 5))
    }
    @Test fun porcentajesSinIntentosSonCero() {
        assertEquals(0f, Estadisticas().porcentajeAciertos(), 0.001f)
        assertEquals(0f, Estadisticas().porcentajeFallos(), 0.001f)
    }
    @Test fun porcentajesUsanIntentosYNoClics() {
        val datos = Estadisticas(3, 1, 20)
        assertEquals(75f, datos.porcentajeAciertos(), 0.001f)
        assertEquals(25f, datos.porcentajeFallos(), 0.001f)
    }
}
