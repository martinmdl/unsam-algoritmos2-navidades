@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp
import java.time.*
import java.time.temporal.ChronoUnit

class Usuario(
    val nombre: String,
    val apellido: String,
    val username: String,
    var tiempoLectura: Int,
    private val fechaNac: LocalDate
) {
    /* Nombre, Apellido, Username (el alias que lo identificará dentro de la aplicación), Fecha de Nacimiento,
    Edad y el Tiempo de Lectura Promcada usuario puede leer una cantidad de palabras por miedio (nutos,
    este valor puede aumentar al doble si el libro es desafiante). */
    //
    fun edad(): Long {
        val hoy = LocalDate.now()
        val edad = ChronoUnit.DAYS.between(fechaNac, hoy)/365
        return edad
    }

    fun aumentaVelocidadLectura(libroAleer: Libro) {
        if(libroAleer.esDesafiante()) {
            this.tiempoLectura *= 2
        }
    }

}

















