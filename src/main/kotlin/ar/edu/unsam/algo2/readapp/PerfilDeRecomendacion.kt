@file:Suppress("SpellCheckingInspection")
package ar.edu.unsam.algo2.readapp


abstract class PerfilDeRecomendacion() {

    abstract fun buscar(usuario: Usuario): MutableSet<Recomendacion>
}

// el precavido: solo le interesan las recomendaciones de libros que por lo menos incluyan
// uno de los libros que tiene pendiente de lectura, o libros que algún amigo haya leído.

object precavido : PerfilDeRecomendacion() {

    override fun buscar(usuario: Usuario): MutableSet<Recomendacion> {

        val setParaRetornar: MutableSet<Recomendacion> = mutableSetOf()

        HistorialRecomendaciones.historialRecomendaciones().forEach { recomendacion ->

            val librosRecomendados: MutableSet<Libro> = recomendacion.librosRecomendados

            // Condición 1
            if (usuario.librosPorLeer.any { it in librosRecomendados }) {
                setParaRetornar.add(recomendacion)
                return@forEach // similar al continue o break
            }

            // Condición 2
            usuario.amigos.forEach { amigo ->
                if (amigo.librosLeidos.keys.any { it in librosRecomendados }) {
                    setParaRetornar.add(recomendacion)
                    return@forEach
                }
            }
        }

        return setParaRetornar

    }

    // PARA CADA RECOMENDACION EVALUAR:
    // CONDICION 1:
    // Interseccion entre (usuario.librosPorLeer) y (historialRecomendaciones.libroRecomendados) > 0
    // CONDICION 2:
    // Interseccion entre (usuario.amigos.librosLeidos) y (historialRecomendaciones.libroRecomendados) > 0
    // SI CUMPLEN ALGUNA DE LAS DOS CONDICIONES, AGREGAR ESA RECOMENDACION AL SET QUE SE RETORNARA

    // historialRecomendaciones = {A, B, C} NO SE PUEDE COMPARAR CONTRA (usuario.librosPorLeer)
    // libroRecomendadosA = { A1, A2 } SI SE PUEDE COMPARAR CONTRA (usuario.librosPorLeer)
    // libroRecomendadosB = { B1, B2 }
    // libroRecomendadosC = { C1, C2 }


}

// el leedor: no tiene una preferencia específica, por lo que le interesa cualquier recomendación.

object leedor : PerfilDeRecomendacion() {

    override fun buscar(usuario: Usuario): MutableSet<Recomendacion> =
        HistorialRecomendaciones.historialRecomendaciones()
}

// el políglota: como maneja varios idiomas, le gustaría ver recomendaciones que tengan
// por lo menos 5 idiomas distintos.

object poliglota : PerfilDeRecomendacion() {


    override fun buscar(usuario: Usuario): MutableSet<Recomendacion> {

        val setParaRetornar: MutableSet<Recomendacion> = mutableSetOf()

        HistorialRecomendaciones.historialRecomendaciones().forEach { recomendacion ->

            val librosRecomendados: MutableSet<Libro> = recomendacion.librosRecomendados

            // Libro() contiene property -> private var lenguajes: Set<Lenguaje>

            // val cantLenguajes: MutableSet<Lenguaje> = mutableSetOf()
            // cantLenguajes.size() >= 5 --> setParaRetornar.add(recomendacion)
            // else -> return@forEach

            if (librosRecomendados.) {
                setParaRetornar.add(recomendacion)
                return@forEach // similar al continue o break
            }

            // dos forEach ?????
        }

        // HistorialRecomendaciones.historialRecomendaciones().librosRecomendados.lenguaje >= 5
    }
}

// el nativista: espera recomendaciones que tengan libros cuyo idioma original sea el mismo nativo de él.

object nativista : PerfilDeRecomendacion() {

    override fun buscar(usuario: Usuario): MutableSet<Recomendacion> {

    }
}

// el calculador: como le gusta tener control del tiempo que lee, acepta recomendaciones
// que no le lleven más de un rango de tiempo (el tiempo correspondiente a leer toda la serie de libros),
// este rango puede variar entre los distintos usuarios. (Ej. un usuario puede tener de 600 min a 1.000 min,
// y otro de 500 min a 800 min).

// el demandante: quiere que le ofrezcamos recomendaciones que tengan una valoración de entre 4 y 5 puntos.

// el experimentado: quiere recomendaciones de libros donde la mayoría sean autores consagrados
// (es decir, que tengan 50 o más años de edad y tenga por lo menos un premio como escritor).

// los cambiantes: son los que se comportan como leedor hasta los 25 años de edad,
// luego se comportan como un calculador con una tolerancia de 10.000 a 15.000 minutos.




//object calculador : Perfil() {
//
//    override fun buscar(): MutableSet<Recomendacion> {
//        return mutableSetOf()
//    }
//
//}


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