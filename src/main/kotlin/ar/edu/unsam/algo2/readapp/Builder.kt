@file:Suppress("SpellCheckingInspection")
package ar.edu.unsam.algo2.readapp

import java.time.LocalDate

//Builders
class AutorBuilder {
    private var id : Int? = null
    private var nombre: String = ""
    private var apellido: String = ""
    private var seudonimo: String = ""
    private var fechaNac: LocalDate = LocalDate.now()
    private var librosEscritos: MutableSet<Libro> = mutableSetOf()
    private var lenguaNativa: Lenguaje = Lenguaje.es_ES
    private var premios: Int = 0

    fun id(id: Int) = apply { this.id = id }
    fun nombre(nombre: String) = apply { this.nombre = nombre }
    fun apellido(apellido: String) = apply { this.apellido = apellido }
    fun seudonimo(seudonimo: String) = apply { this.seudonimo = seudonimo }
    fun fechaNac(fechaNac: LocalDate) = apply { this.fechaNac = fechaNac }
    fun librosEscritos(librosEscritos: MutableSet<Libro>) = apply { this.librosEscritos = librosEscritos }
    fun lenguaNativa(lenguaNativa: Lenguaje) = apply { this.lenguaNativa = lenguaNativa }
    fun premios(premios: Int) = apply { this.premios = premios }

    fun build() = Autor(
        id,
        nombre,
        apellido,
        seudonimo,
        fechaNac,
        librosEscritos,
        lenguaNativa,
        premios)
}

class LibroBuilder {
    private var id : Int? = null
    private var nombre: String = ""
    private var editorial: String = ""
    private var paginas: Int = 0
    private var cantPalabras: Int = 0
    private var lecturaCompleja: Boolean = false
    private var ediciones: Int = 0
    private var lenguajes: Set<Lenguaje> = setOf()
    private var ventasSemanales: Int = 0
    private var autor: Autor = AutorBuilder().build()

    fun id(id: Int) = apply { this.id = id }
    fun nombre(nombre: String) = apply { this.nombre = nombre }
    fun editorial(editorial: String) = apply { this.editorial = editorial }
    fun paginas(paginas: Int) = apply { this.paginas = paginas }
    fun cantPalabras(cantPalabras: Int) = apply { this.cantPalabras = cantPalabras }
    fun lecturaCompleja(lecturaCompleja: Boolean) = apply { this.lecturaCompleja = lecturaCompleja }
    fun ediciones(ediciones: Int) = apply { this.ediciones = ediciones }
    fun lenguajes(lenguajes: Set<Lenguaje>) = apply { this.lenguajes = lenguajes }
    fun ventasSemanales(ventasSemanales: Int) = apply { this.ventasSemanales = ventasSemanales }
    fun autor(autor: Autor) = apply { this.autor = autor }

    fun build() = Libro(
        id,
        nombre,
        editorial,
        paginas,
        cantPalabras,
        lecturaCompleja,
        ediciones,
        lenguajes,
        ventasSemanales,
        autor
    )
}

class UsuarioBuilder {
    var id: Int? = null
    var nombre: String = ""
    var apellido: String = ""
    private var username: String = ""
    private var palabrasPorMinuto: Int = 0
    private var fechaNac: LocalDate = LocalDate.now()
    private var direccionEmail: String = ""
    private var amigos: MutableSet<Usuario> = mutableSetOf()
    var librosLeidos: MutableMap<Libro, Int> = mutableMapOf()
    var autorFavorito: Autor = AutorBuilder().build()
    private var recomendacionesPorValorar: MutableSet<Recomendacion> = mutableSetOf()
    private var librosPorLeer: MutableSet<Libro> = mutableSetOf()
    var lenguaNativa: Lenguaje = Lenguaje.es_ES

    fun id(id: Int) = apply { this.id = id }
    fun nombre(nombre: String) = apply { this.nombre = nombre }
    fun apellido(apellido: String) = apply { this.apellido = apellido }
    fun username(username: String) = apply { this.username = username }
    fun palabrasPorMinuto(palabrasPorMinuto: Int) = apply { this.palabrasPorMinuto = palabrasPorMinuto }
    fun fechaNac(fechaNac: LocalDate) = apply { this.fechaNac = fechaNac }
    fun direccionEmail(direccionEmail: String) = apply { this.direccionEmail = direccionEmail }
    fun amigos(amigos: MutableSet<Usuario>) = apply { this.amigos = amigos }
    fun librosLeidos(librosLeidos: MutableMap<Libro, Int>) = apply { this.librosLeidos = librosLeidos }
    fun autorFavorito(autorFavorito: Autor) = apply { this.autorFavorito = autorFavorito }
    fun recomendacionesPorValorar(recomendacionesPorValorar: MutableSet<Recomendacion>) = apply { this.recomendacionesPorValorar = recomendacionesPorValorar }
    fun librosPorLeer(librosPorLeer: MutableSet<Libro>) = apply { this.librosPorLeer = librosPorLeer }
    fun lenguaNativa(lenguaNativa: Lenguaje) = apply { this.lenguaNativa = lenguaNativa }

    fun build() = Usuario(
        id,
        nombre,
        apellido,
        username,
        palabrasPorMinuto,
        fechaNac,
        direccionEmail,
        amigos,
        librosLeidos,
        autorFavorito,
        recomendacionesPorValorar,
        librosPorLeer,
        lenguaNativa
    )
}

class RecomendacionBuilder {
    var id: Int? = null
    private var esPrivado: Boolean = false
    var creador: Usuario = UsuarioBuilder().build()
    var librosRecomendados: MutableSet<Libro> = mutableSetOf()
    private var descripcion: String = ""
    private var valoraciones: MutableMap<Usuario, Valoracion> = mutableMapOf()

    fun id(id: Int) = apply { this.id = id }
    fun esPrivado(esPrivado: Boolean) = apply { this.esPrivado = esPrivado }
    fun creador(creador: Usuario) = apply { this.creador = creador }
    fun librosRecomendados(librosRecomendados: MutableSet<Libro>) = apply { this.librosRecomendados = librosRecomendados }
    fun descripcion(descripcion: String) = apply { this.descripcion = descripcion }
    fun valoraciones(valoraciones: MutableMap<Usuario, Valoracion>) = apply { this.valoraciones = valoraciones }

    fun build() = Recomendacion(
        id,
        esPrivado,
        creador,
        librosRecomendados,
        descripcion,
        valoraciones
    )
}
class ValoracionBuilder {
    var autor: Usuario = UsuarioBuilder().build()
    private var valor: Int = 0
    private var comentario: String = ""

    fun autor(autor: Usuario) = apply { this.autor = autor }
    fun valor(valor: Int) = apply { this.valor = valor }
    fun comentario(comentario: String) = apply { this.comentario = comentario }

    fun build() = Valoracion(
        autor,
        valor,
        comentario
    )
}
