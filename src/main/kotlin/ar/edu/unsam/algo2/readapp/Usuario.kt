@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp
import java.time.*
import java.time.temporal.ChronoUnit

abstract class Usuario(
    val nombre: String,
    val apellido: String,
    val username: String,
    var palabrasPorMinuto: Int,
    var fechaNac: LocalDate
) {

    /*
    * Nombre, Apellido, Username (el alias que lo identificará dentro de la aplicación),
    * Fecha de Nacimiento, Edad y el Tiempo de Lectura Promedio
    * (cada usuario puede leer una cantidad de palabras por minutos, este valor puede aumentar al doble si el libro es desafiante).
    */

    companion object {
        const val DISMINUCION_VELOCIDAD_LECTURA = 2
    }

    fun edad(): Long = ChronoUnit.YEARS.between(fechaNac, LocalDate.now())
    open fun tiempoDeLectura(libroAleer: Libro): Int = libroAleer.cantPalabras / velocidadDeLectura(libroAleer)

    // Aux
    abstract fun velocidadDeLectura(libroAleer: Libro): Int
}