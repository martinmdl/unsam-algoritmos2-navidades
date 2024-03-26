@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp
import java.time.*
import java.time.temporal.ChronoUnit

class Usuario(
    val nombre: String,
    val apellido: String,
    val username: String,
    private var palabrasPorMinuto: Int,
    private val fechaNac: LocalDate
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
    fun tiempoDeLectura(libroAleer: Libro): Int = libroAleer.cantPalabras / velocidadDeLectura(libroAleer)

    /*AUX*/
    private fun velocidadDeLectura(libroAleer: Libro): Int {
        return if(libroAleer.esDesafiante()) {
            palabrasPorMinuto / DISMINUCION_VELOCIDAD_LECTURA
        } else {
            palabrasPorMinuto
        }
    }
}