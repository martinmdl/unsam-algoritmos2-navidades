@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

abstract class PerfilDeRecomendacion() {

    abstract fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean
}

object Precavido : PerfilDeRecomendacion() {

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

object Leedor : PerfilDeRecomendacion() {
    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean = true
}

object Poliglota : PerfilDeRecomendacion() {

    private const val CANT_MINIMA_LENGUAJES = 5

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        cantLenguajesValida(recomendacion)

    private fun cantLenguajesValida(recomendacion: Recomendacion): Boolean =
        cantLenguajes(recomendacion) >= CANT_MINIMA_LENGUAJES

    private fun cantLenguajes(recomendacion: Recomendacion): Int {
        // CAMBIAR A FLATTENMAP()
        val totalLenguajes: MutableSet<Libro> = mutableSetOf()
        recomendacion.librosRecomendados.forEach { libro -> totalLenguajes.union(libro.idioma) }
        return totalLenguajes.size
    }
}

object Nativista : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        lenguasIguales(recomendacion, usuario)

    private fun lenguaNativaUsuario(usuario: Usuario): Lenguaje = usuario.lenguaNativa

    private fun lenguasIguales(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.librosRecomendados.any { it.autor.lenguaNativa == usuario.lenguaNativa }
}

object Calculador : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        puedeLeer(recomendacion, usuario)

    private fun puedeLeer(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.tiempoDeLecturaTotal(usuario) >= usuario.rangoMin || recomendacion.tiempoDeLecturaTotal(usuario) <= usuario.rangoMax
}

object Demandante : PerfilDeRecomendacion() {

    private const val VALORACION_MINIMA = 3

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        valoracionAlta(recomendacion, usuario)

    private fun valoracionAlta(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.valoraciones.values.any { it.valor > VALORACION_MINIMA }
}

object Experimentado : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        lenguasIguales(recomendacion)

    private fun lenguasIguales(recomendacion: Recomendacion): Boolean =
        recomendacion.librosRecomendados.any { it.autor.esConsagrado() }
}

object Cambiante : PerfilDeRecomendacion() {

    private const val EDAD_LIMITE = 25
    private const val RANGO_MIN = 10000
    private const val RANGO_MAX = 15000

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean {
        return if (usuario.edad() <= EDAD_LIMITE) {
            Leedor.validarRecomendacion(usuario, recomendacion)
        } else {
            usuario.rangoMin(RANGO_MIN)
            usuario.rangoMax(RANGO_MAX)
            Calculador.validarRecomendacion(usuario, recomendacion)
        }
    }
}

// #### ESTAS FUNCIONES RETORNAN SETS DE RECOMENDACIONES VALIDAS #####

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