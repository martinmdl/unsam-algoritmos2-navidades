package ar.edu.unsam.algo2.readapp

abstract class Perfil {

    abstract fun buscar(): MutableSet<Recomendacion>
}

object calculador : Perfil() {

    override fun buscar(): MutableSet<Recomendacion> {
        return mutableSetOf()
    }

}


//    poliglota {
//        override fun busqueda(recomendacionGlobal): List<Recomendacion> {
//
//        }
//    },
//
//    nativista {
//        override fun busqueda(recomendacionGlobal): List<Recomendacion> {
//
//        }
//    },
//
//    precavido {
//        override fun busqueda(recomendacionGlobal): List<Recomendacion> {
//            usuario.librosLeidos.
//        }
//    },
//
//    leedor {
//        override fun busqueda(recomendacionGlobal): List<Recomendacion> {
//
//        }
//    },
//
//    demandante {
//        override fun busqueda(recomendacionGlobal): List<Recomendacion> {
//
//        }
//    },
//
//    experimentado {
//        override fun busqueda(recomendacionGlobal): List<Recomendacion> {
//
//        }
//    },
//
//    cambiante {
//        override fun busqueda(recomendacionGlobal): List<Recomendacion> {
//
//        }
//    };
//
//    abstract fun busqueda(recomendacionGlobal): List<Recomendacion>
}