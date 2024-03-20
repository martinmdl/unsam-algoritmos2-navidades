@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp
import java.time.*
import java.time.temporal.ChronoUnit

class Usuario(
    val nombre: String,
    val apellido: String,
    val username: String,
    var palabrasPorMinuto: Int,
    private val fechaNac: LocalDate
) {

    /* Nombre, Apellido, Username (el alias que lo identificará dentro de la aplicación), Fecha de Nacimiento,
    Edad y el Tiempo de Lectura Promcada usuario puede leer una cantidad de palabras por miedio (nutos,
    este valor puede aumentar al doble si el libro es desafiante). */
    //

    companion object {
        const val DISMUNUCION_VELOCIDAD_LECTURA = 2
    }

    fun edad(): Long = ChronoUnit.YEARS.between(fechaNac, LocalDate.now())

    fun tiempoDeLectura(libroAleer: Libro) : Int {
        return if(libroAleer.esDesafiante()) {
            libroAleer.cantPalabras / (palabrasPorMinuto / DISMUNUCION_VELOCIDAD_LECTURA)
        } else {
            libroAleer.cantPalabras / palabrasPorMinuto
        }
    }
}