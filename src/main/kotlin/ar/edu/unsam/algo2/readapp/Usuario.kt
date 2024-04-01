@file:Suppress("SpellCheckingInspection")
package ar.edu.unsam.algo2.readapp
import java.time.*
import java.time.temporal.ChronoUnit

open class Usuario(
    val nombre: String,
    val apellido: String,
    val username: String,
    val palabrasPorMinuto: Int,
    private val fechaNac: LocalDate,
    val direccionDeMail: String,
    val amigos: List<Usuario>,
    val librosLeidos: MutableList<Libro>,
    val recomendaciones: MutableList<Recomendacion>
) {

    fun edad(): Long = ChronoUnit.YEARS.between(fechaNac, LocalDate.now())

    fun leerLibro(libroALeer: Libro) {
        librosLeidos.add(libroALeer)
    }

    //chequear
//    open fun contieneLibro(libroAleer: Libro) : Boolean = libroAleer.this.librosLeidos.contains()
//    fun crearRecomendacion(nuevaRecomendacion: Recomendacion){
//        recomendaciones.add(nuevaRecomendacion)
//    }

}
