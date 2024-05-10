@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import com.google.gson.Gson



interface Service {
    fun getLibros(repositorioLibros: RepositorioLibros): String
}

class ServiceLibro: Service {
    private val gson = Gson()
    override fun getLibros(repositorioLibros: RepositorioLibros): String {
        val jsonMap: MutableList<Map<String, Int>> = ApiFakeLibro.callApi(repositorioLibros)
        return gson.toJson(jsonMap)
    }
}

