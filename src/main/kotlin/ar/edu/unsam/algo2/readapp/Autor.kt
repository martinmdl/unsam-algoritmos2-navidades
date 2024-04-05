@file:Suppress("SpellCheckingInspection")


package ar.edu.unsam.algo2.readapp

import java.time.*
import java.time.temporal.ChronoUnit

class Autor(
    private var fechaNac: LocalDate,
    var librosEscritos: MutableSet<Libro> = mutableSetOf(),
    val lenguaNativa: Lenguaje,
    val premios: Int
) {

    companion object {
        const val EDAD_CONSAGRACION = 50
        const val CANTIDAD_MINIMA_PREMIOS = 1
    }

    fun edad(): Long = ChronoUnit.YEARS.between(fechaNac, LocalDate.now())

    fun esConsagrado(): Boolean =
        edad() >= EDAD_CONSAGRACION && premios >= CANTIDAD_MINIMA_PREMIOS

}