@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import java.time.LocalDate

/**
 * CentroDeLectura.
 *
 * @param nombreDeCentroDeLectura Es el nombre del centro de lectura: [String], ej Biblioteca Juanito.
 * @param direccion Indica la direccion fisica del centro: [String].
 * @param libroAsignadoALeer Es el Libro: [Libro] asignado al encuentro actual, cuando se cambia el encuentro se actualiza el libro.
 * @param costoDeReserva El valor numerico: [Float] del costo de la reserva.
 * @param conjuntoDeEncuentros Conjunto de Encuentros: [Encuentro].
 */

abstract class CentroDeLectura(
    private val nombreDeCentroDeLectura: String,
    private val direccion: String,
    private var libroAsignadoALeer: Libro,
    private var costoDeReserva: Float,
    private val conjuntoDeEncuentros: MutableSet<Encuentro>
) {

    companion object {
        const val COSTO_DIVULGACION_APP = 1000
    }

    /*SETTERS*/
    fun setLibroAsignadoALeer(libroNuevo: Libro) {
        this.libroAsignadoALeer = libroNuevo
    }

    fun setCostoDeReserva(costoNuevo: Float) {
        this.costoDeReserva = costoNuevo
    }

    fun agregarEncuentro(encuentro: Encuentro) {
        this.conjuntoDeEncuentros.add(encuentro)
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
     *[reserva]:
     *
     *  En esta funcion ejecuta una reserva siempre y cuando exista cupo en el encuentro y su fecha no este vencida.
     */
    fun reserva(encuentro: Encuentro) {
        if (encuentro.reservaDisponible()) {
            encuentro.reservarCupo()
            this.costo(encuentro)
        } else {
            throw SinCupo("No hay cupo disponible")
        }
    }

    /**
     *[vencimento]:
     *
     *  Metodo que indica si se expiro la publicacion/divulgacion actual de encuentros.
     */
    fun vencimento(): Boolean {
        return seVencieronTodasLasFechas() || capacidadMaximaAlcanzada()
    }

    /**
     *[maximaCapacidadDePerticipantes]:
     *
     *  Metodo abstracto que indica, segun su implementacion, el numero maximo permitido de participantes.
     */
    abstract fun maximaCapacidadPorEncuentro(): Int

    /**
     *[costo]:
     *
     *  Metodo abstracto que indica el costo.
     */
    abstract fun costo(encuentro: Encuentro): Float

    /*AUX*/
    fun seVencieronTodasLasFechas(): Boolean {
        return this.conjuntoDeEncuentros.last().fecha().isBefore(LocalDate.now())
    }

    fun capacidadMaximaAlcanzada(): Boolean = !this.conjuntoDeEncuentros.all { it.disponibilidad() }

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
 * @param porcentajeMinimo Valor minimo porcentual: [Double] fijado para el cobro extra.
 * @constructor Se crea un objeto que hereda de :[CentroDeLectura] con el agregado de [capacidadMaximaFijada]
 */
class Particular(
    private val nombreDeCentroDeLectura: String,
    private val direccion: String,
    private var libroAsignadoALeer: Libro,
    private val costoDeReserva: Float,
    private val conjuntoDeEncuentros: MutableSet<Encuentro>,
    private val capacidadMaximaFijada: Int,
    private val porcentajeMinimo: Double
) : CentroDeLectura(nombreDeCentroDeLectura, direccion, libroAsignadoALeer, costoDeReserva, conjuntoDeEncuentros) {

    companion object {
        const val ADICION_RESERVA_POR_ESPACIO = 500
        const val PORCENTAJE = 100
    }

    /**
     *[maximaCapacidadDePerticipantes]:
     *
     *  Capacidad maxima definida para la clase [Particular], en este caso se define via constructor.
     */
    override fun maximaCapacidadPorEncuentro(): Int {
        return this.capacidadMaximaFijada
    }

    /**
     *[costo]:
     *
     *  Costo definido para la clase [Particular], se calcula apartir de un porcentaje de ocupacion minimo definido,.
     */
    override fun costo(encuentro: Encuentro): Float {
        val costoFijo = this.costoDeReserva + COSTO_DIVULGACION_APP
        val porcentajeDisponible = (encuentro.disponible() * PORCENTAJE) / this.maximaCapacidadPorEncuentro()
        val porcentajeOcupado = PORCENTAJE - porcentajeDisponible
        if (porcentajeOcupado > this.porcentajeMinimo) {
            return costoFijo + ADICION_RESERVA_POR_ESPACIO
        }
        return costoFijo
    }
}

/**
 * Particular: [Editorial].
 *
 *@param nombreDeCentroDeLectura Es el nombre del centro de lectura: [String], ej Biblioteca Juanito.
 * @param direccion Indica la direccion fisica del centro: [String].
 * @param libroAsignadoALeer Es el Libro: [Libro] asignado al encuentro actual, cuando se cambia el encuentro se actualiza el libro.
 * @param costoDeReserva El valor numerico: [Float] del costo de la reserva.
 * @param conjuntoDeEncuentros Conjunto de Encuentros: [Encuentro].
 * @param montoEspecificoAlcanzar Valor maximo: [Int] fijado.
 * @param autorPresente Indica si el autro va estar presenta en los encuentros
 * @constructor Se crea un objeto que hereda de :[CentroDeLectura] con el agregado de [montoEspecificoAlcanzar]
 */
class Editorial(
    private val nombreDeCentroDeLectura: String,
    private val direccion: String,
    private var libroAsignadoALeer: Libro,
    private val costoDeReserva: Float,
    private val conjuntoDeEncuentros: MutableSet<Encuentro>,
    private val montoEspecificoAlcanzar: Int,
    private val autorPresente: Boolean
) : CentroDeLectura(nombreDeCentroDeLectura, direccion, libroAsignadoALeer, costoDeReserva, conjuntoDeEncuentros) {

    companion object {
        const val ADICION_RESERVA_FIJO = 800
        const val ADICION_RESERVA_POR_AUTOR_NO_BEST_SSELLER = 200
        const val ADICION_PORCENTUAL_BESTSELLER = 0.1
    }

    /**
     *[maximaCapacidadDePerticipantesPorEncuentro]:
     *
     *  Capacidad maxima definida para la clase [Editorial], se calcula a partir del monto fijo a alcanzar y a partir de ahi se reparte entre los encuentros.
     */
    override fun maximaCapacidadPorEncuentro(): Int {
        return this.montoEspecificoAlcanzar / this.costoDeReserva.toInt() / this.conjuntoDeEncuentros.size
    }

    /**
     *[costo]:
     *
     *  Costo definido para la clase [Editorial], se calcula a partir de si el libro asignado es o no un [Libro.esBestSeller]
     */
    override fun costo(encuentro: Encuentro): Float {
        val costoFijo = this.costoDeReserva + COSTO_DIVULGACION_APP + ADICION_RESERVA_FIJO
        if (autorPresente) {
            if (this.libroAsignadoALeer.esBestSeller()) {
                val adicional = (this.libroAsignadoALeer.getVentasSemanales() * ADICION_PORCENTUAL_BESTSELLER).toFloat()
                return costoFijo + adicional
            }
            return costoFijo + ADICION_RESERVA_POR_AUTOR_NO_BEST_SSELLER
        }
        return costoFijo
    }

}

/**
 * Particular: [Biblioteca].
 *
 * @param nombreDeCentroDeLectura Es el nombre del centro de lectura: [String], ej Biblioteca Juanito.
 * @param direccion Indica la direccion fisica del centro: [String].
 * @param libroAsignadoALeer Es el Libro: [Libro] asignado al encuentro actual, cuando se cambia el encuentro se actualiza el libro.
 * @param costoDeReserva El valor numerico: [Float] del costo de la reserva.
 * @param conjuntoDeEncuentros Conjunto de Encuentros: [Encuentro].
 * @param metrosCuadradosSala Valor: [Int] fijado.
 * @param gastoFijo Una lista de gastos fijos: [Float].
 * @constructor Se crea un objeto que hereda de :[CentroDeLectura] con el agregado de [montoEspecificoAlcanzar]
 */
class Biblioteca(
    private val nombreDeCentroDeLectura: String,
    private val direccion: String,
    private var libroAsignadoALeer: Libro,
    private val costoDeReserva: Float,
    private val conjuntoDeEncuentros: MutableSet<Encuentro>,
    private val metrosCuadradosSala: Int,
    private val gastoFijo: MutableList<Float>,
) : CentroDeLectura(nombreDeCentroDeLectura, direccion, libroAsignadoALeer, costoDeReserva, conjuntoDeEncuentros) {

    companion object {
        const val METROS_CUADRADO_POR_PERSONA = 1
        const val CANTIDAD_MINIMA_ENCUEUNTROS = 5
        const val ADICION_PORCENTUAL_MAS_DE_5_ENCUENTROS = 0.5
        const val ADICION_PORCENTUAL_MENOS_DE_5_ENCUENTROS = 0.1
    }

    /*SETTER*/

    fun agregarGasto(gasto: Float) {
        this.gastoFijo.add(gasto)
    }

    /**
     *[maximaCapacidadPorEncuentro]:
     *
     *  Capacidad maxima definida para la clase [Biblioteca], se calcula a partir del espacio fisico asignado y a partir de ahi se reparte entre los encuentros.
     */
    override fun maximaCapacidadPorEncuentro(): Int {
        return this.metrosCuadradosSala / METROS_CUADRADO_POR_PERSONA
    }

    /**
     *[costo]:
     *
     *  Costo definido para la clase [Biblioteca],
     */
    override fun costo(encuentro: Encuentro): Float {
        val costoFijo = this.costoDeReserva + COSTO_DIVULGACION_APP + this.gastoFijo.sum()

        if (this.conjuntoDeEncuentros.size > CANTIDAD_MINIMA_ENCUEUNTROS) {
            return costoFijo + (this.gastoFijo.sum() * ADICION_PORCENTUAL_MAS_DE_5_ENCUENTROS).toFloat()
        }
        return costoFijo + (this.gastoFijo.sum() * ADICION_PORCENTUAL_MENOS_DE_5_ENCUENTROS * this.gastoFijo.size).toFloat()
    }
}


class SinCupo(message: String) : Exception(message)