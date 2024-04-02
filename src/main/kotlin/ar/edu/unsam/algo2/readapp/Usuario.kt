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
    val librosLeidos: MutableMap<Libro, Int>, // revisar
    val recomendaciones: MutableList<Recomendacion>,
    val autorFavorito: String
) : TipoLector {

    companion object {
        const val COEFICIENTE_POR_LIBRO_DESAFIANTE = 2 // si el libro es desafiante
    }

    fun edad(): Long = ChronoUnit.YEARS.between(fechaNac, LocalDate.now())

    fun leerLibro(libroALeer: Libro) {
//        librosLeidos[libroALeer] = 1
    }

//    override fun velocidadDeLectura(libro: Libro): Double {
//        return if(libro.esDesafiante()) {
//            (palabrasPorMinuto / COEFICIENTE_POR_LIBRO_DESAFIANTE).toDouble()
//        } else {
//            palabrasPorMinuto.toDouble()
//        }
//    }

    override fun velocidadDeLectura(libro: Libro): Double {
        return when {
            libro.esDesafiante() -> (palabrasPorMinuto / COEFICIENTE_POR_LIBRO_DESAFIANTE).toDouble()
            else -> palabrasPorMinuto.toDouble()
        }
    }

    //chequear
//    open fun contieneLibro(libroAleer: Libro) : Boolean = libroAleer.this.librosLeidos.contains()
//    fun crearRecomendacion(nuevaRecomendacion: Recomendacion){
//        recomendaciones.add(nuevaRecomendacion)
//    }

}
