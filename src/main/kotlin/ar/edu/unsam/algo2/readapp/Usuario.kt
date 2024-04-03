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
    val recomendacionesEmitidas: MutableSet<Recomendacion> = mutableSetOf(),
    val autorFavorito: String,
    private val recomendacionesPorValorar: MutableSet<Recomendacion> = mutableSetOf(),
    private val librosPorLeer: MutableSet<Libro> = mutableSetOf(),
    private var tipoLector: TipoLector, // que tipo de lector es este usuario? REVISAR
    private var perfilLector: Perfil
) : TipoLector {

    companion object {
        const val COEFICIENTE_POR_LIBRO_DESAFIANTE = 2 // si el libro es desafiante
    }

    fun edad(): Long = ChronoUnit.YEARS.between(fechaNac, LocalDate.now())

    fun leerLibro(libro: Libro) {
        val vecesLeido: Int = librosLeidos.getOrPut(libro) { 0 } + 1
            // ∃Key -> return Value
            // ∄Key -> crea Key, Value = 0, return Value
        librosLeidos[libro] = vecesLeido
    }

    override fun velocidadDeLectura(libro: Libro): Double {
        return when {
            libro.esDesafiante() -> (palabrasPorMinuto / COEFICIENTE_POR_LIBRO_DESAFIANTE).toDouble()
            else -> palabrasPorMinuto.toDouble()
        }
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

    fun agregarRecomendacionPorValorar(recomendacion: Recomendacion) {
        recomendacionesPorValorar.add(recomendacion)
    }

    // PREGUNTAR SI ES REDUNDANTE HACER ESTA "TRADUCCION"
    private fun eliminarRecomendacionPorValorar(recomendacion: Recomendacion) {
        recomendacionesPorValorar.remove(recomendacion)
    }

    fun agregarAmigo(amigo: Usuario) {
        amigos.add(amigo) // testear amigo repetido
    }

    fun eliminarAmigo(amigo: Usuario) {
        amigos.remove(amigo) // testear si el amigo no existe
    }

    // REVISAR Y TESTEAR
    fun variarTipoLector(tipo: TipoLector) {
        tipoLector = tipo
    }

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
    }

    fun eliminarRecomendacion(recomendacion: Recomendacion) {
        recomendacionesEmitidas.remove(recomendacion)
    }

    fun agregarLibroPorLeer(libro: Libro) {
        when {
            libroYaLeido(libro) -> throw Exception("Este libro ya fue leído")
            else -> librosPorLeer.add(libro)
        }
    }

    fun cambiarPerfilLector(nuevoPerfil: Perfil) {
        perfilLector = nuevoPerfil
    }

    fun buscarRecomendaciones(): MutableSet<Recomendacion> =
        perfilLector.buscar()

    private fun libroYaLeido(libro: Libro): Boolean = librosLeidos.contains(libro)
}
