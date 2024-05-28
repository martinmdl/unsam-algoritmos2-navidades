@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import java.time.*
import java.time.temporal.ChronoUnit

class Usuario(
    override var id: Int?= null,
    var nombre: String,
    val apellido: String,
    val username: String,
    val palabrasPorMinuto: Int,
    private val fechaNac: LocalDate,
    val direccionEmail: String,
    val amigos: MutableSet<Usuario> = mutableSetOf(),
    val librosLeidos: MutableMap<Libro, Int> = mutableMapOf(),
    var autorFavorito: Autor,
    val recomendacionesPorValorar: MutableSet<Recomendacion> = mutableSetOf(),
    val librosPorLeer: MutableSet<Libro> = mutableSetOf(),
    val lenguaNativa: Lenguaje
) : Identidad {

// ##TIPO_LECTOR##
    var tipoLector: TipoLector = LectorPromedio(this)

    fun variarTipoLector(tipo: TipoLector) {
        tipoLector = tipo
    }

    fun tiempoDeLectura(libro: Libro) =
        tipoLector.tiempoDeLectura(libro)

//  ##PERFIL DE RECOMENDACION##
    var perfilDeRecomendacion: PerfilDeRecomendacion = Leedor(this)
    fun cambiarPerfilDeRecomendacion(perfil: PerfilDeRecomendacion) {
        perfilDeRecomendacion = perfil
    }
    fun buscarRecomendaciones(recomendacion: Recomendacion): Boolean =
        perfilDeRecomendacion.validarRecomendacion(recomendacion)

    fun edad(): Long = ChronoUnit.YEARS.between(fechaNac, LocalDate.now())


//  ##LIBROS##
    fun leerLibro(libro: Libro) {
        val vecesLeido: Int = librosLeidos.getOrPut(libro) { 0 } + 1
            // ∃Key -> return Value
            // ∄Key -> crea Key, Value = 0, return Value
        librosLeidos[libro] = vecesLeido
        eliminarLibroPorLeer(libro)
    }

    fun cantidadLecturas(libro: Libro): Int =
        librosLeidos.getOrDefault(libro, 0)

    private fun libroYaLeido(libro: Libro): Boolean = librosLeidos.contains(libro)

    fun agregarLibroPorLeer(libro: Libro) {
        when {
            libroYaLeido(libro) -> throw Exception("Este libro ya fue leído")
            else -> librosPorLeer.add(libro)
        }
    }

    fun eliminarLibroPorLeer(libro: Libro) {
        librosPorLeer.remove(libro)
    }

//  ##AUTOR##
   fun variarAutorFavorito(autor: Autor) {
        autorFavorito = autor
    }


//  ##AMIGOS##
    fun agregarAmigo(amigoParaAgregar: Usuario) {
        if(!amigoParaAgregar.amigos.contains(this)) {
            amigos.add(amigoParaAgregar)
            amigoParaAgregar.agregarAmigo(this)
        }
    }

    fun eliminarAmigo(amigo: Usuario) {
        amigos.remove(amigo)
    }

//  ##VALORACIONES##
    fun valorarRecomendacion(recomendacion: Recomendacion, valor: Int, comentario: String, ) {
        recomendacion.crearValoracion(valor, comentario, this)
        eliminarRecomendacionPorValorar(recomendacion)
    }

    fun agregarRecomendacionPorValorar(recomendacion: Recomendacion) {
        recomendacionesPorValorar.add(recomendacion)
    }

    fun eliminarRecomendacionPorValorar(recomendacion: Recomendacion) {
        recomendacionesPorValorar.remove(recomendacion)
    }

//  ##RECOMENDACION##
    fun crearRecomendacion(libros: MutableSet<Libro>, descripcion: String, privacidad: Boolean): Recomendacion {

        val nuevaRecomendacion: Recomendacion = RecomendacionBuilder()
            .esPrivado(privacidad)
            .descripcion(descripcion)
            .librosRecomendados(libros)
            .creador(this)
            .build()

        listaDeRecomendacionesCreada.add(nuevaRecomendacion)

        return nuevaRecomendacion
    }

//  ##PROCESO DE ADMINISTRACION##
    val listaDeRecomendacionesCreada = mutableListOf<Recomendacion>()
    val listaDeValoracionesDadas = mutableListOf<Valoracion>()
    fun esActivo(): Boolean = amigos.isNotEmpty() || listaDeRecomendacionesCreada.isNotEmpty() || listaDeValoracionesDadas.isNotEmpty()
}
