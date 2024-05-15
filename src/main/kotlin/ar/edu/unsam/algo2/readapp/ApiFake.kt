@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

interface ApiFake<T> {
    fun callApi(obj: T): MutableList<Map<String, Int>>
}

object ApiFakeLibro: ApiFake<RepositorioLibros> {
    override fun callApi(obj: RepositorioLibros): MutableList<Map<String, Int>> {
        val getIDFromRepository = obj.dataMap.keys.toList()
        val getValuesFromRepository = obj.dataMap.values.toList()
        val jsonMap: MutableList<Map<String, Int>> = mutableListOf()
        for (i in getIDFromRepository.indices) {
            val libro = getValuesFromRepository[i]
            val dataMap = mapOf(
                "id" to getIDFromRepository[i],
                "ediciones" to libro.getEdiciones(),
                "ventasSemanales" to libro.getVentasSemanales()
            )
            jsonMap.add(dataMap)
        }
        return jsonMap
    }
}