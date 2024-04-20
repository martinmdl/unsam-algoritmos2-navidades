@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

abstract class PerfilDeRecomendacion() {

    abstract fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean
}

object Precavido : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        librosQuiereLeerRecomendacion(recomendacion, usuario) || amigosLeyeron(recomendacion, usuario)

    private fun amigosLeyeron(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        usuario.amigos.any { it.librosLeidos.keys.intersect(librosRecomendados(recomendacion)).isNotEmpty() }

    private fun librosQuiereLeerRecomendacion(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        librosRecomendados(recomendacion).intersect(usuario.librosPorLeer).isNotEmpty()

    private fun librosRecomendados(recomendacion: Recomendacion): MutableSet<Libro> =
        recomendacion.librosRecomendados
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

    private fun cantLenguajes(recomendacion: Recomendacion): Int =
        recomendacion.librosRecomendados.flatMap { it.idioma }.toSet().size
}

object Nativista : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        lenguasIguales(recomendacion, usuario)

    private fun lenguasIguales(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.librosRecomendados.any { it.autor.lenguaNativa == usuario.lenguaNativa }
}

object Calculador : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        puedeLeer(recomendacion, usuario)

    private fun puedeLeer(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.tiempoDeLecturaTotal(usuario) >= usuario.rangoMin && recomendacion.tiempoDeLecturaTotal(usuario) <= usuario.rangoMax
}

object Demandante : PerfilDeRecomendacion() {

    private const val VALORACION_MINIMA_PROMEDIO = 3

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        valoracionAlta(recomendacion)

//    private fun valoracionAlta(recomendacion: Recomendacion): Boolean =
//        recomendacion.valoraciones.values.any { it.valor >= VALORACION_MINIMA_PROMEDIO }

    private fun valoracionAlta(recomendacion: Recomendacion): Boolean =
        recomendacion.promedioValoraciones() > VALORACION_MINIMA_PROMEDIO
}

object Experimentado : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        lenguasIguales(recomendacion)

    private fun lenguasIguales(recomendacion: Recomendacion): Boolean =
        //DEUDA TECNICA
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

class Combinador(
    val perfilesCombinados: MutableSet<PerfilDeRecomendacion>
) : PerfilDeRecomendacion() {

    override fun validarRecomendacion(usuario: Usuario, recomendacion: Recomendacion): Boolean =
        perfilesCombinados.any { it.validarRecomendacion(usuario, recomendacion) }

    fun agregarPerfil(perfil: PerfilDeRecomendacion) {
        perfilesCombinados.add(perfil)
    }

    fun removerPerfil(perfil: PerfilDeRecomendacion) {
        perfilesCombinados.remove(perfil)
    }
}