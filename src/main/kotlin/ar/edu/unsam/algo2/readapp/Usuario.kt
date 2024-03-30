@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp
import java.time.*
import java.time.temporal.ChronoUnit

abstract class Usuario(
    val nombre: String,
    val apellido: String,
    val username: String,
    var palabrasPorMinuto: Int,
    private var fechaNac: LocalDate,
    var direccionDeMail: String,
    var amigos: List<Usuario>,
    var librosLeidos: MutableList<Libro>,
    var recomendaciones: MutableList<Recomendaciones>
) {

    companion object {
        const val DISMINUCION_VELOCIDAD_LECTURA = 2
    }

    fun edad(): Long = ChronoUnit.YEARS.between(fechaNac, LocalDate.now())
    open fun tiempoDeLectura(libroAleer: Libro): Int = libroAleer.cantPalabras / velocidadDeLectura(libroAleer)

    // Aux
    abstract fun velocidadDeLectura(libroAleer: Libro): Number

    fun leerLibro(libroALerr: Libro) {
        librosLeidos.add(libroALerr)
    }
    //chequear
    fun contieneLibro(libroAleer: Libro) : Boolean = libroAleer.librosLeidos.contains()
    fun crearRecomendacion(nuevaRecomendacion: Recomendaciones){
        recomendaciones.add(nuevaRecomendacion)
    }

}
