@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

interface ProcesoAdministracion {
    fun ejecutar()
}
class BorrarUsuarioInactivo : ProcesoAdministracion {
    override fun ejecutar() {

    }
}

class ActualizacionDeLibro : ProcesoAdministracion {
    override fun ejecutar() {
        TODO("Not yet implemented")
    }
}

class BorrarCentroDeLecturaExpirados : ProcesoAdministracion {
    override fun ejecutar() {
        TODO("Not yet implemented")
    }
}

class AgregarAutores : ProcesoAdministracion {
    override fun ejecutar() {
        TODO("Not yet implemented")
    }
}