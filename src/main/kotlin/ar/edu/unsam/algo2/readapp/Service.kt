@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


interface Service {
    fun getLibros(repositorioLibros: RepositorioLibros): String
}

class ServiceLibro: Service {
    private val gson = Gson()
    override fun getLibros(repositorioLibros: RepositorioLibros): String {
        val jsonMap: MutableList<Map<String, Int>> = ApiFakeLibro.callApi(repositorioLibros)
        return gson.toJson(jsonMap)
    }

    fun getObjectLibros(repositorioLibros: RepositorioLibros): LibrosActualizados {
        val json = getLibros(repositorioLibros)
        val type = object : TypeToken<List<Map<String, Int >>>() {}.type
        val jsonMap: List<Map<String, Int>> = gson.fromJson(json, type)
        val librosActualizados = jsonMap.map { LibroActualizado(it["id"]?: 0, it["ediciones"] ?: 0, it["ventasSemanales"]?: 0) }
        return LibrosActualizados(librosActualizados)
    }
}

data class LibroActualizado(val id: Int, val ediciones: Int, val ventasSemanales: Int)

data class LibrosActualizados(val libros: List<LibroActualizado>)

