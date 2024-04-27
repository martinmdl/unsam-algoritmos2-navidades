@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import java.time.*
import java.time.temporal.ChronoUnit

open class Usuario(
    var nombre: String,
    val apellido: String,
    val username: String,
    val palabrasPorMinuto: Int,
    private val fechaNac: LocalDate,
    val direccionEmail: String,
    val amigos: MutableSet<Usuario> = mutableSetOf(),
    val librosLeidos: MutableMap<Libro, Int> = mutableMapOf(),
    private val recomendacionesEmitidas: MutableSet<Recomendacion> = mutableSetOf(),
    var autorFavorito: Autor,
    val recomendacionesPorValorar: MutableSet<Recomendacion> = mutableSetOf(),
    val librosPorLeer: MutableSet<Libro> = mutableSetOf(),
    var tipoLector: TipoLector,
    var perfilDeRecomendacion: PerfilDeRecomendacion,
    val lenguaNativa: Lenguaje,
) {

    fun edad(): Long = ChronoUnit.YEARS.between(fechaNac, LocalDate.now())

// ##TIEMPO_DE_LECTURA
    fun tiempoDeLectura(libro: Libro) =
        tipoLector.tiempoDeLectura(libro)

// ##TIPO_LECTOR##
    fun variarTipoLector(tipo: TipoLector) {
        tipoLector = tipo
    }

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
    open fun variarAutorFavorito(autor: Autor) {
        autorFavorito = autor
    }

//  ##AMIGOS##
    fun agregarAmigo(amigo: Usuario) {
        amigos.add(amigo)
    }

    fun eliminarAmigo(amigo: Usuario) {
        amigos.remove(amigo)
    }

//  ##PERFIL DE RECOMENDACION##

    // "perfil" se instancia unicamente en los tests
    fun cambiarPerfilDeRecomendacion(perfil: PerfilDeRecomendacion) {
        perfilDeRecomendacion = perfil
    }

    fun cambiarAPerfilCombinador(perfiles: MutableSet<PerfilDeRecomendacion>) {
        perfilDeRecomendacion = Combinador(this, perfiles)
    }

    fun buscarRecomendaciones(recomendacion: Recomendacion): Boolean =
        perfilDeRecomendacion.validarRecomendacion(recomendacion)

//  ##VALORACIONES##
    fun valorarRecomendacion(
        recomendacion: Recomendacion,
        valor: Int,
        comentario: String,
    ) {
        recomendacion.crearValoracion(valor, comentario, this)
        eliminarRecomendacionPorValorar(recomendacion)
    }

    fun agregarRecomendacionPorValorar(recomendacion: Recomendacion) {
        recomendacionesPorValorar.add(recomendacion)
    }

    fun eliminarRecomendacionPorValorar(recomendacion: Recomendacion) {
        recomendacionesPorValorar.remove(recomendacion)
    }
}
