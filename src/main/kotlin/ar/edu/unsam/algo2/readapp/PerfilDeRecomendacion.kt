@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

abstract class PerfilDeRecomendacion(val usuario: Usuario) {
    abstract fun validarRecomendacion(recomendacion: Recomendacion): Boolean
}

class Precavido(usuario: Usuario) : PerfilDeRecomendacion(usuario) {
    override fun validarRecomendacion(recomendacion: Recomendacion) = librosQuiereLeerRecomendacion(recomendacion) || amigosLeyeron(recomendacion)
    private fun amigosLeyeron(recomendacion: Recomendacion): Boolean = usuario.amigos.any { it.librosLeidos.keys.intersect(librosRecomendados(recomendacion)).isNotEmpty() }
    private fun librosQuiereLeerRecomendacion(recomendacion: Recomendacion): Boolean = librosRecomendados(recomendacion).intersect(usuario.librosPorLeer).isNotEmpty()
    private fun librosRecomendados(recomendacion: Recomendacion): MutableSet<Libro> = recomendacion.librosRecomendados
}

class Leedor(usuario: Usuario) : PerfilDeRecomendacion(usuario) {
    override fun validarRecomendacion(recomendacion: Recomendacion) = true
}

class Poliglota(usuario: Usuario, private val libro: Libro) : PerfilDeRecomendacion(usuario) {
    companion object { private const val CANT_MINIMA_LENGUAJES = 5 }
    override fun validarRecomendacion(recomendacion: Recomendacion) = cantLenguajesValida()
    private fun cantLenguajesValida(): Boolean = libro.getCantLenguajes() >= CANT_MINIMA_LENGUAJES
}

class Nativista(usuario: Usuario) : PerfilDeRecomendacion(usuario) {
    override fun validarRecomendacion(recomendacion: Recomendacion) = lenguasIguales(recomendacion)
    private fun lenguasIguales(recomendacion: Recomendacion): Boolean = recomendacion.librosRecomendados.any { it.autor.lenguaNativa == usuario.lenguaNativa }
}

class Calculador(
    usuario: Usuario,
    private val rangoMin: Int,
    private val rangoMax: Int
): PerfilDeRecomendacion(usuario) {
    override fun validarRecomendacion(recomendacion: Recomendacion) = cumpleRangoTiempoMinimo(recomendacion) && cumpleRangoTiempoMaximo(recomendacion)
    private fun cumpleRangoTiempoMinimo(recomendacion: Recomendacion) = recomendacion.tiempoDeLecturaTotal(usuario) >= rangoMin
    private fun cumpleRangoTiempoMaximo(recomendacion: Recomendacion) = recomendacion.tiempoDeLecturaTotal(usuario) <= rangoMax
}

class Demandante(usuario: Usuario) : PerfilDeRecomendacion(usuario) {
    companion object { private const val VALORACION_MINIMA_PROMEDIO = 3 }
    override fun validarRecomendacion(recomendacion: Recomendacion) = valoracionAlta(recomendacion)
    private fun valoracionAlta(recomendacion: Recomendacion): Boolean = recomendacion.promedioValoraciones() > VALORACION_MINIMA_PROMEDIO
}

class Experimentado(usuario: Usuario) : PerfilDeRecomendacion(usuario)  {
    companion object { private const val DIVISOR_MITAD = 2 }
    override fun validarRecomendacion(recomendacion: Recomendacion) = cantidadAutoresConsagrados(recomendacion) > cantidadAutoresASuperar(recomendacion)
    private fun cantidadAutoresConsagrados(recomendacion: Recomendacion) = recomendacion.librosRecomendados.count { it.autor.esConsagrado() }
    private fun cantidadAutoresASuperar(recomendacion: Recomendacion) = recomendacion.librosRecomendados.size / DIVISOR_MITAD
}

class Cambiante(usuario: Usuario) : PerfilDeRecomendacion(usuario)  {
    companion object {
        private const val EDAD_LIMITE = 25
        private const val RANGO_MIN = 10000
        private const val RANGO_MAX = 15000
    }
    override fun validarRecomendacion(recomendacion: Recomendacion): Boolean {
        return if (!mayoriaEdad()) {
            Leedor(usuario).validarRecomendacion(recomendacion)
        } else {
            Calculador(usuario, RANGO_MIN, RANGO_MAX).validarRecomendacion(recomendacion)
        }
    }
    private fun mayoriaEdad(): Boolean = usuario.edad() > EDAD_LIMITE
}

class Combinador(
    usuario: Usuario,
    private val perfilesCombinados: MutableSet<PerfilDeRecomendacion>
) : PerfilDeRecomendacion(usuario) {

    override fun validarRecomendacion(recomendacion: Recomendacion) =  perfilesCombinados.any { it.validarRecomendacion(recomendacion) }

    fun agregarPerfil(perfil: PerfilDeRecomendacion) {
        validarElemento(perfil)
        perfilesCombinados.add(perfil)
    }

    fun removerPerfil(perfil: PerfilDeRecomendacion) {
        perfilesCombinados.remove(perfil)
    }

    private fun validarElemento(perfil: PerfilDeRecomendacion) {
        if(perfil is Combinador) {
            throw Exception("Combinador no puede contener elementos de tipo Combinador")
        }
    }
}