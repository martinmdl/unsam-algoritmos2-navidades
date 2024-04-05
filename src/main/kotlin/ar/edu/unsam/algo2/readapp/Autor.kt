package ar.edu.unsam.algo2.readapp
import java.time.*
import java.time.temporal.ChronoUnit

class Autor(
    private var fechaNac: LocalDate,
    var librosEscritos: MutableSet<Libro> = mutableSetOf(),
    var lenguaNativa: Lenguaje
) {

    fun edad(): Long = ChronoUnit.YEARS.between(fechaNac, LocalDate.now())

}