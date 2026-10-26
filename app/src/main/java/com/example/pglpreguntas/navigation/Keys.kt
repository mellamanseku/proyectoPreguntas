package com.example.pglpreguntas.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable data object Inicio : NavKey
@Serializable data object Modos : NavKey
@Serializable data object Insertar : NavKey
@Serializable data object VerEstadisticas : NavKey
@Serializable data class Juego(val examen: Boolean, val simple: Boolean) : NavKey
