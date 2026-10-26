package com.example.pglpreguntas.model

// La imagen es un nombre de recurso, no una ruta externa.
data class Pregunta(val enunciado: String, val respuesta: Boolean, val imagen: String)
