@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

interface ServiceLibros {
    fun getLibros(repositorioLibros: RepositorioLibros): MutableList<Map<String, Int>>
}

class Service: ServiceLibros {
    override fun getLibros(repositorioLibros: RepositorioLibros): MutableList<Map<String, Int>> {
        val getIDFromRepository = repositorioLibros.dataMap.keys.toList()
        val getValuesFromRepository = repositorioLibros.dataMap.values.toList()
        val json: MutableList<Map<String, Int>> = mutableListOf()
        for (i in getIDFromRepository.indices) {
            val libro = getValuesFromRepository[i]
            val parseJSON = mapOf(
                "id" to getIDFromRepository[i],
                "ediciones" to libro.getEdiciones(),
                "ventasSemanales" to libro.getVentasSemanales()
            )
            json.add(parseJSON)
        }
        return json
    }
}