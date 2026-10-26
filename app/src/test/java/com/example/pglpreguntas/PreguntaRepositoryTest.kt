package com.example.pglpreguntas

import com.example.pglpreguntas.data.PreguntaRepository
import com.example.pglpreguntas.model.Pregunta
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.IOException

class PreguntaRepositoryTest {
    @get:Rule val carpeta = TemporaryFolder()
    private val iniciales = listOf(Pregunta("Primera", true, "logo"), Pregunta("Segunda", false, "logo"))
    private fun archivo() = File(carpeta.root, "preguntas.txt")
    private fun repo() = PreguntaRepository(archivo(), iniciales)

    @Test fun primeraCargaCreaElBancoYSegundaCargaLoConserva() {
        assertEquals(iniciales, repo().cargarPreguntas())
        val bytes = archivo().readText()
        assertEquals(iniciales, repo().cargarPreguntas())
        assertEquals(bytes, archivo().readText())
    }
    @Test fun lasLineasVaciasNoBloqueanElJuego() {
        archivo().writeText("\nPrimera|true|logo\n\nSegunda|false|logo\n\n")
        assertEquals(iniciales, repo().cargarPreguntas())
    }
    @Test fun admiteRespuestaConMayusculasYRegistroSinImagen() {
        archivo().writeText("\uFEFFPrimera| True \nSegunda|FALSE|logo\n")
        assertEquals(iniciales, repo().cargarPreguntas())
    }
    @Test fun ficheroVacioSeRecuperaConCopiaDelOriginal() {
        archivo().writeText("")
        assertEquals(iniciales, repo().cargarPreguntas())
        val copias = carpeta.root.listFiles()!!.filter { it.name.startsWith("preguntas.txt.copia-") }
        assertEquals(1, copias.size)
        assertEquals("", copias[0].readText())
    }
    @Test fun unRegistroDanadoNoEliminaPreguntasValidasYSeGuardaCopiaExacta() {
        val original = "Mi pregunta|true|logo\nregistro incompleto\nOtra pregunta|false|logo\n"
        archivo().writeText(original)
        val esperadas = listOf(Pregunta("Mi pregunta", true, "logo"), Pregunta("Otra pregunta", false, "logo"))
        assertEquals(esperadas, repo().cargarPreguntas())
        val copia = carpeta.root.listFiles()!!.single { it.name.startsWith("preguntas.txt.copia-") }
        assertEquals(original, copia.readText())
        assertEquals(esperadas, repo().cargarPreguntas())
    }
    @Test fun archivoSinRegistrosValidosRecuperaLasIniciales() {
        archivo().writeText("Una linea sin respuesta\nOtra|quizas|logo\n")
        assertEquals(iniciales, repo().cargarPreguntas())
    }
    @Test fun guardarAnadeSinSobrescribirNiPegarLaUltimaLinea() {
        archivo().writeText("Primera|true|logo")
        val nueva = Pregunta("Añadida con acento", false, "logo")
        repo().guardarPregunta(nueva)
        assertEquals(listOf(iniciales[0], nueva), repo().cargarPreguntas())
    }
    @Test fun textoInvalidoNoModificaElArchivo() {
        repo().cargarPreguntas()
        val antes = archivo().readText()
        try { repo().guardarPregunta(Pregunta("Mal|texto", true, "logo")); fail("Debía rechazar el separador") }
        catch (e: IllegalArgumentException) { assertEquals(antes, archivo().readText()) }
    }
    @Test(expected = IOException::class) fun unFalloRealDeAccesoNoSeOculta() {
        val padre = carpeta.newFile("no_es_una_carpeta")
        PreguntaRepository(File(padre, "preguntas.txt"), iniciales).cargarPreguntas()
    }
}
