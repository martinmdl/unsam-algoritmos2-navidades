package ar.edu.unsam.algo2.readapp
import java.time.*
import java.time.temporal.ChronoUnit

open class Usuario(
    val nombre: String,
    val apellido: String,
    val username: String,
    private var tiempoLectura: Int,
    val fechaNac: LocalDate
) {

    /* Nombre, Apellido, Username (el alias que lo identificará dentro de la aplicación), Fecha de Nacimiento,
    Edad y el Tiempo de Lectura Promcada usuario puede leer una cantidad de palabras por miedio (nutos,
    este valor puede aumentar al doble si el libro es desafiante). */
    //
    fun edad(): Long {
        val hoy = LocalDate.now()
        val edad = ChronoUnit.YEARS.between(fechaNac, hoy)
        return edad
    }

    /*fun aumentaVelocidadLectura() {
        if(Libro.esDesafiante()) {
            tiempoLectura * 2
        }
    }
*/
}

















