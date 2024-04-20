@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import java.time.*

/**
 * CentroDeLectura.
 *
 * @param nombreDeCentroDeLectura Es el nombre del centro de lectura: [String], ej Biblioteca Juanito.
 * @param direccion Indica la direccion fisica del centro: [String].
 * @param libroAsignadoALeer Es el Libro: [Libro] asignado al encuentro actual, cuando se cambia el encuentro se actualiza el libro.
 * @param costoDeReserva El valor numerico: [Float] del costo de la reserva.
 * @param conjuntoDeEncuentros Conjunto de Encuentros: [Encuentro].
 * @property disponible Es el cupo disponible: [Int] para reservar
 */

abstract class CentroDeLectura(
    private val nombreDeCentroDeLectura: String,
    private val direccion: String,
    private var libroAsignadoALeer: Libro,
    private var costoDeReserva: Float,
    private val conjuntoDeEncuentros: MutableSet<Encuentro>
) {
    private var disponible = 0

    /*SETTERS*/
    /**
     *[setReserva]:
     *
     *  En esta funcion se ejecuta una reserva siempre y cuando sea posible.
     */
    fun setReserva() {
        if (disponible < maximaCapacidadDePerticipantes()) {
            disponible += 1
        }
        else{
            throw SinCupo("No hay cupo disponible")
        }
    }
    /*GETTERS*/
    /**
     *[getConjuntoDeEncuentros]:
     *
     *  Metodo accesorio del conjunto de encuentros.
     */
    fun getConjuntoDeEncuentros(): MutableSet<Encuentro> = this.conjuntoDeEncuentros

    /*METODOS*/
    /**
     *[vencimento]:
     *
     *  Metodo abstracto que indica si se vencieron, segun su implementacion, la publicacion actual de encuentros.
     */
    abstract fun vencimento(): Boolean
    /**
     *[maximaCapacidadDePerticipantes]:
     *
     *  Metodo abstracto que indica, segun su implementacion, el numero maximo permitido de participantes.
     */
    abstract fun maximaCapacidadDePerticipantes(): Int
    /*AUX*/
    abstract fun seVencieronTodasLasFechas(): Boolean
    abstract fun capacidadMaximaAlcanzada(): Boolean
}

/**
 * Particular: [CentroDeLectura].
 *
 * @param nombreDeCentroDeLectura Es el nombre del centro de lectura: [String], ej Biblioteca Juanito.
 * @param direccion Indica la direccion fisica del centro: [String].
 * @param libroAsignadoALeer Es el Libro: [Libro] asignado al encuentro actual, cuando se cambia el encuentro se actualiza el libro.
 * @param costoDeReserva El valor numerico: [Float] del costo de la reserva.
 * @param conjuntoDeEncuentros Conjunto de Encuentros: [Encuentro].
 * @param capacidadMaximaFijada Valor maximo: [Int] fijado.
 * @constructor Se crea un objeto que hereda de :[CentroDeLectura] con el agregado de [capacidadMaximaFijada]
 */
class Particular(
    private val nombreDeCentroDeLectura: String,
    private val direccion: String,
    private var libroAsignadoALeer: Libro,
    private val costoDeReserva: Float,
    private val conjuntoDeEncuentros: MutableSet<Encuentro>,
    private val capacidadMaximaFijada: Int)
    : CentroDeLectura( nombreDeCentroDeLectura,direccion, libroAsignadoALeer,constoDeReserva, conjuntoDeEncuentros) {
    /**
     *[vencimento]:
     *
     *  Metodo que indica si los encuentros vigentes se encuentran vencidos, ya sea por falta de cupo o por que paso el tiempo.
     */
    override fun vencimento(): Boolean {
        return seVencieronTodasLasFechas() || capacidadMaximaAlcanzada()
    }
    /**
     *[capacidadMaximaAlcanzada]:
     *
     *  Capacidad maxima definida para la clase [Particular]
     */
    override fun capacidadMaximaAlcanzada(): Boolean {
        return this.capacidadMaximaAlcanzada()
    }

    override fun seVencieronTodasLasFechas(): Boolean {
        return conjuntoDeEncuentros.last().fecha.isBefore(LocalDate.now())
    }

    override fun maximaCapacidadDePerticipantes(): Int {
        return this.capacidadMaximaFijada
    }
}

class Editorial(
    private val nombreDeCentroDeLectura: String,
    private val direccion: String,
    private var libroAsignadoALeer: Libro,
    private val costoDeReserva: Float,
    private val conjuntoDeEncuentros: MutableSet<Encuentro>,
    private val capacidadMaximaFijada: Int)
    : CentroDeLectura( nombreDeCentroDeLectura,direccion, libroAsignadoALeer,constoDeReserva, conjuntoDeEncuentros) {

    override fun vencimento(): Boolean {
        return seVencieronTodasLasFechas() || capacidadMaximaAlcanzada()
    }

    override fun capacidadMaximaAlcanzada(): Boolean {
        return this.capacidadMaximaAlcanzada()
    }

    override fun seVencieronTodasLasFechas(): Boolean {
        TODO("Not yet implemented")
    }


    override fun maximaCapacidadDePerticipantes(): Int {
        return this.capacidadMaximaFijada
    }
}

class Biblioteca(
    private val nombreDeCentroDeLectura: String,
    private val direccion: String,
    private var libroAsignadoALeer: Libro,
    private val constoDeReserva: Float,
    private val conjuntoDeEncuentros: MutableSet<Encuentro>,
    private val capacidadMaximaFijada: Int)
    : CentroDeLectura( nombreDeCentroDeLectura,direccion, libroAsignadoALeer,constoDeReserva, conjuntoDeEncuentros) {

    override fun vencimento(): Boolean {
        return seVencieronTodasLasFechas() || capacidadMaximaAlcanzada()
    }

    override fun capacidadMaximaAlcanzada(): Boolean {
        return this.capacidadMaximaAlcanzada()
    }

    override fun seVencieronTodasLasFechas(): Boolean {
        TODO("Not yet implemented")
    }


    override fun maximaCapacidadDePerticipantes(): Int {
        return this.capacidadMaximaFijada
    }
}

/* CLASES A REVISAR*/

class Encuentro(
    val fecha: LocalDate,
    val duracion: Int,
) {
}

class SinCupo(message: String) : Exception(message)