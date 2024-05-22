@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface ServiceLibros {
    fun getLibros(): String
}

interface ServiceAutor {
    fun getLibros(): String
}

interface ServiceUsuario {
    fun getLibros(): String
}

interface ServiceRecomendaciones {
    fun getLibros(): String
}

interface ServiceCentroDeLectura {
    fun getLibros(): String
}

interface ServiceUpdate<T> {
    fun update(obj: T)
}

class UpdateLibros(private val serviceLibros: ServiceLibros): ServiceUpdate<Libro> {

    private fun parseJson(): LibrosActualizados {
        val gson = Gson()
        val parse: String = serviceLibros.getLibros()
        val type = object : TypeToken<List<Map<String, Int>>>() {}.type
        val jsonList: List<Map<String, Int>> = gson.fromJson(parse, type)
        val listaDeLibrosActualizados =
            jsonList.map { LibroActualizado(it["id"], it["ediciones"], it["ventasSemanales"])}
        return LibrosActualizados(listaDeLibrosActualizados)
    }

    override fun update(obj: Libro) {

        val librosActualizados = this.parseJson()
        val libroConActualizaciones: LibroActualizado? = librosActualizados.libros.find { it.id == obj.id }

        if(libroConActualizaciones == null) throw Businessexception("No hay actualizaciones")

        obj.setEditarEdiciones(libroConActualizaciones.ediciones!!)

        obj.setVentaSemanales(obj.getVentasSemanales() + libroConActualizaciones.ventasSemanales!!)
    }
}

class UpdateUsuario (private val serviceLibros: ServiceUsuario): ServiceUpdate<Usuario> {
    override fun update(obj: Usuario) {throw Businessexception("No se puede actualizar")}
}

class UpdateAutor (private val serviceLibros: ServiceAutor): ServiceUpdate<Autor> {
    override fun update(obj: Autor) {throw Businessexception("No se puede actualizar")}
}

class UpdateRecomendaciones (private val serviceLibros: ServiceRecomendaciones): ServiceUpdate<Recomendacion> {
    override fun update(obj: Recomendacion) {throw Businessexception("No se puede actualizar")}
}
class UpdateCentroDeLectura (private val serviceLibros: ServiceCentroDeLectura): ServiceUpdate<CentroDeLectura> {
    override fun update(obj: CentroDeLectura) {throw Businessexception("No se puede actualizar")}
}

data class LibroActualizado(val id: Int?, val ediciones: Int?, val ventasSemanales: Int?)

data class LibrosActualizados(val libros: List<LibroActualizado>)