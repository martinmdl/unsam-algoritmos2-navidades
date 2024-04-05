@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import com.sun.org.apache.xpath.internal.operations.Bool
import sun.misc.Perf
import sun.security.ec.point.ProjectivePoint.Mutable

// podria ser una interface
abstract class PerfilDeRecomendacion() {

    abstract fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean
}

// el precavido: solo le interesan las recomendaciones de libros que por lo menos incluyan
// uno de los libros que tiene pendiente de lectura, o libros que algún amigo haya leído.

object precavido : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        librosQuiereLeerRecomendacion(recomendacion, usuario) || amigosLeyeron(recomendacion, usuario)

    // CONDICION 1
    private fun amigosLeyeron(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        librosRecomendados(recomendacion).intersect(librosLeidosPorAmigos(recomendacion, usuario)).isNotEmpty()

    // CONDICION 2
    private fun librosQuiereLeerRecomendacion(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        librosRecomendados(recomendacion).intersect(usuario.librosPorLeer).isNotEmpty()

    // GETTERS
    private fun librosRecomendados(recomendacion: Recomendacion): MutableSet<Libro> =
        recomendacion.librosRecomendados

    private fun librosLeidosPorAmigos(recomendacion: Recomendacion, usuario: Usuario): MutableSet<Libro> {
        // CAMBIAR A FLATTENMAP()
        val librosLeidosPorAmigos: MutableSet<Libro> = mutableSetOf()
        usuario.amigos.forEach { amigo -> librosLeidosPorAmigos.union(amigo.librosLeidos.keys) }
        return librosLeidosPorAmigos
    }
}

// el leedor: no tiene una preferencia específica, por lo que le interesa cualquier recomendación.

object leedor : PerfilDeRecomendacion() {
    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean = true
}

// el políglota: como maneja varios idiomas, le gustaría ver recomendaciones que tengan
// por lo menos 5 idiomas distintos.

object poliglota : PerfilDeRecomendacion() {

    private const val CANT_MINIMA_LENGUAJES = 5

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        cantLenguajesValida(recomendacion)

    private fun cantLenguajesValida(recomendacion: Recomendacion): Boolean =
        cantLenguajes(recomendacion) >= CANT_MINIMA_LENGUAJES

    private fun cantLenguajes(recomendacion: Recomendacion): Int {
        // CAMBIAR A FLATTENMAP()
        val totalLenguajes: MutableSet<Libro> = mutableSetOf()
        recomendacion.librosRecomendados.forEach { libro -> totalLenguajes.union(libro.lenguajes) }
        return totalLenguajes.size
    }
}

// el nativista: espera recomendaciones que tengan libros cuyo idioma original sea el mismo nativo de él.

object nativista : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        lenguasIguales(recomendacion, usuario)

    private fun lenguaNativaUsuario(usuario: Usuario): Lenguaje = usuario.lenguaNativa

    private fun lenguasIguales(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.librosRecomendados.any { it.autor.lenguaNativa == usuario.lenguaNativa }
}

// el calculador: como le gusta tener control del tiempo que lee, acepta recomendaciones
// que no le lleven más de un rango de tiempo (el tiempo correspondiente a leer toda la serie de libros),
// este rango puede variar entre los distintos usuarios. (Ej. un usuario puede tener de 600 min a 1.000 min,
// y otro de 500 min a 800 min).


object calculador : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        puedeLeer(recomendacion, usuario)

    private fun puedeLeer(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.tiempoDeLecturaTotal(usuario) >= usuario.rangoMin || recomendacion.tiempoDeLecturaTotal(usuario) <= usuario.rangoMax
}

// el demandante: quiere que le ofrezcamos recomendaciones que tengan una valoración de entre 4 y 5 puntos.

object demandante : PerfilDeRecomendacion() {

    private const val VALORACION_MINIMA = 3

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        valoracionAlta(recomendacion, usuario)

    private fun valoracionAlta(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.valoraciones.values.any { it.valor > VALORACION_MINIMA } // it = Valoracion()
}

// el experimentado: quiere recomendaciones de libros donde la mayoría sean autores consagrados
// (es decir, que tengan 50 o más años de edad y tenga por lo menos un premio como escritor).

object experimentado : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        lenguasIguales(recomendacion)

    private fun lenguasIguales(recomendacion: Recomendacion): Boolean =
        recomendacion.librosRecomendados.any { it.autor.esConsagrado() }
}

// los cambiantes: son los que se comportan como leedor hasta los 25 años de edad,
// luego se comportan como un calculador con una tolerancia de 10.000 a 15.000 minutos.


object cambiante : PerfilDeRecomendacion() {

    private const val EDAD_LIMITE = 25
    private const val RANGO_MIN = 10000
    private const val RANGO_MAX = 15000

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean {
        return if (usuario.edad() <= EDAD_LIMITE) {
            leedor.validarRecomendacion(usuario, recomendacion)
        } else {
            usuario.rangoMin(RANGO_MIN)
            usuario.rangoMax(RANGO_MAX)
            calculador.validarRecomendacion(usuario, recomendacion)
        }
    }
}

// ESTAS FUNCIONES RETORNAN SETS DE RECOMENDACIONES VALIDAS

//object precavido : PerfilDeRecomendacion() {
//
//    override fun buscar(usuario: Usuario): MutableSet<Recomendacion> {
//
//        val setParaRetornar: MutableSet<Recomendacion> = mutableSetOf()
//
//        HistorialRecomendaciones.historialRecomendaciones().forEach { recomendacion ->
//
//            val librosRecomendados: MutableSet<Libro> = recomendacion.librosRecomendados
//
//            // Condición 1
//            if (usuario.librosPorLeer.any { it in librosRecomendados }) {
//                setParaRetornar.add(recomendacion)
//                return@forEach // similar al continue o break
//            }
//
//            // Condición 2
//            usuario.amigos.forEach { amigo ->
//                if (amigo.librosLeidos.keys.any { it in librosRecomendados }) {
//                    setParaRetornar.add(recomendacion)
//                    return@forEach
//                }
//            }
//        }
//
//        return setParaRetornar
//
//    }
//}

//object leedor : PerfilDeRecomendacion() {
//
//    override fun buscar(usuario: Usuario): MutableSet<Recomendacion> =
//        HistorialRecomendaciones.historialRecomendaciones()
//}

//object poliglota : PerfilDeRecomendacion() {
//
//
//    override fun buscar(usuario: Usuario): MutableSet<Recomendacion> {
//
//        val setParaRetornar: MutableSet<Recomendacion> = mutableSetOf()
//
//        HistorialRecomendaciones.historialRecomendaciones().forEach { recomendacion ->
//
//            val librosRecomendados: MutableSet<Libro> = recomendacion.librosRecomendados
//
//            // Libro() contiene property -> private var lenguajes: Set<Lenguaje>
//
//            // val cantLenguajes: MutableSet<Lenguaje> = mutableSetOf()
//            // cantLenguajes.size() >= 5 --> setParaRetornar.add(recomendacion)
//            // else -> return@forEach
//
//            if (librosRecomendados.) {
//                setParaRetornar.add(recomendacion)
//                return@forEach // similar al continue o break
//            }
//
//            // dos forEach ?????
//        }
//
//        // HistorialRecomendaciones.historialRecomendaciones().librosRecomendados.lenguaje >= 5
//    }
//}

//object nativista : PerfilDeRecomendacion() {
//
//    override fun buscar(usuario: Usuario): MutableSet<Recomendacion> {
//
//    }
//}