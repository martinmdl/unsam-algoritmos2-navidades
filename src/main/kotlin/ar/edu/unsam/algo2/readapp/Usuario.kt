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
    val direccionEmail: String,
    val amigos: MutableSet<Usuario> = mutableSetOf(),
    val librosLeidos: MutableMap<Libro, Int> = mutableMapOf(),
    private val recomendacionesEmitidas: MutableSet<Recomendacion> = mutableSetOf(),
    val autorFavorito: Autor,
    private val recomendacionesPorValorar: MutableSet<Recomendacion> = mutableSetOf(),
    val librosPorLeer: MutableSet<Libro> = mutableSetOf(),
    private var tipoLector: TipoLector = LectorPromedio, // que tipo de lector es este usuario? REVISAR
    private var perfilDeRecomendacion: PerfilDeRecomendacion = Leedor,
    val lenguaNativa: Lenguaje,
    // DEUDA TECNICA 
    var rangoMin: Int = 0,
    var rangoMax: Int = 0
) : TipoLector {

    // DEUDA TECNICA
    // SETTERS para 'Calculador'
    fun rangoMin(valor: Int) { rangoMin = valor }
    fun rangoMax(valor: Int) { rangoMax = valor }

    // GETTERS para 'Calculador'
    fun rangoMin() = rangoMin
    fun rangoMax() = rangoMax

    fun edad(): Long = ChronoUnit.YEARS.between(fechaNac, LocalDate.now())

//  ##LIBROS##

    fun leerLibro(libro: Libro) {
        val vecesLeido: Int = librosLeidos.getOrPut(libro) { 0 } + 1
            // ∃Key -> return Value
            // ∄Key -> crea Key, Value = 0, return Value
        librosLeidos[libro] = vecesLeido
    }
    private fun libroYaLeido(libro: Libro): Boolean = librosLeidos.contains(libro)

    fun agregarLibroPorLeer(libro: Libro) {
        when {
            libroYaLeido(libro) -> throw Exception("Este libro ya fue leído")
            else -> librosPorLeer.add(libro)
        }
    }
    fun variarTipoLector(tipo: TipoLector) {
        tipoLector = tipo
    }

//  ##AMIGOS##
    fun agregarAmigo(amigo: Usuario) {
        amigos.add(amigo)
    }

    fun eliminarAmigo(amigo: Usuario) {
        amigos.remove(amigo)
    }

//  ##RECOMENDACIONES Y VALORACIONES##
    fun crearRecomendacion(
        esPrivado: Boolean,
        creador: Usuario,
        libroRecomendados: MutableSet<Libro>,
        descripcion: String,
        valoraciones: MutableMap<Usuario, Valoracion>
    ) {
        val nuevaRecomendacion = Recomendacion(
            esPrivado,
            creador,
            libroRecomendados,
            descripcion,
            valoraciones
        )
        recomendacionesEmitidas.add(nuevaRecomendacion)
        HistorialRecomendaciones.agregarAlHistorial(nuevaRecomendacion)
    }

    fun valorarRecomendacion(
        recomendacion: Recomendacion,
        valor: Int,
        comentario: String,
        usuario: Usuario
    ) {
        recomendacion.crearValoracion(valor, comentario, usuario)
        eliminarRecomendacionPorValorar(recomendacion)
    }

    fun eliminarRecomendacion(recomendacion: Recomendacion) {
        recomendacionesEmitidas.remove(recomendacion)
        HistorialRecomendaciones.eliminarDelHistorial(recomendacion)
    }

    fun cambiarPerfilDeRecomendacion(nuevoPerfil: PerfilDeRecomendacion) {
        perfilDeRecomendacion = nuevoPerfil
    }

    fun buscarRecomendaciones(recomendacion: Recomendacion): Boolean =
        perfilDeRecomendacion.validarRecomendacion(this,recomendacion)

    fun agregarRecomendacionPorValorar(recomendacion: Recomendacion) {
        recomendacionesPorValorar.add(recomendacion)
    }

    private fun eliminarRecomendacionPorValorar(recomendacion: Recomendacion) {
        recomendacionesPorValorar.remove(recomendacion)
    }
}
