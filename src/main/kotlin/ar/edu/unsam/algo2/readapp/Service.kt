@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface ServiceLibros {
    fun getLibros(): String
}

class UpdateLibros(private val serviceLibros : ServiceLibros) {

    fun parseJson(): LibrosActualizados {
        val gson = Gson()
        val parse: String = serviceLibros.getLibros()
        val type = object : TypeToken<List<Map<String, Int>>>() {}.type
        val jsonList: List<Map<String, Int>> = gson.fromJson(parse, type)
        val listaDeLibrosActualizados =
            jsonList.map { LibroActualizado(it["id"], it["ediciones"], it["ventasSemanales"])}
        return LibrosActualizados(listaDeLibrosActualizados)
    }

    // A REVISAR
    fun update(obj: Libro) {
        val librosActualizados = this.parseJson()
        val libroConActualizaciones: LibroActualizado? = librosActualizados.libros.find { it.id == obj.id }

        if(libroConActualizaciones == null) throw Businessexception("No hay actualizaciones")

        obj.setEditarEdiciones(libroConActualizaciones.ediciones!!)
        obj.setVentaSemanales(obj.getVentasSemanales() + libroConActualizaciones.ventasSemanales!!)
    }
}

data class LibroActualizado(val id: Int?, val ediciones: Int?, val ventasSemanales: Int?)

data class LibrosActualizados(val libros: List<LibroActualizado>)