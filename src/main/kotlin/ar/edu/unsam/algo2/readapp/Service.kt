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

    fun update(obj: Libro): Libro {
        val librosActualizados = this.parseJson()
        val libroConActualizaciones: LibroActualizado? = librosActualizados.libros.find { it.id == obj.id }
        if(libroConActualizaciones == null) {return obj}
        obj.setEditarEdiciones(libroConActualizaciones.ediciones!!)
        obj.setVentaSemanales(obj.getVentasSemanales() + libroConActualizaciones.ventasSemanales!!)
        return obj
    }
}

data class LibroActualizado(val id: Int?, val ediciones: Int?, val ventasSemanales: Int?)

data class LibrosActualizados(val libros: List<LibroActualizado>)