@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import java.time.LocalDate

/*Test Unitario*/
class CentroDeLecturaSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    //Funciones auxiliares

    fun crearEncuentroNoVencido(cantidadDeEncuentro: Int, centroAVincular: CentroDeLectura) {
        for (i in 1..cantidadDeEncuentro) {
            val encuentro = Encuentro(LocalDate.now().plusDays(i.toLong()), i, centroAVincular)
            centroAVincular.agregarEncuentro(encuentro)
        }
    }
    fun crearEncuentroVencido(cantidadDeEncuentro: Int, centroAVincular: CentroDeLectura) {
        for (i in 1..cantidadDeEncuentro) {
            val encuentro = Encuentro(LocalDate.now().minusDays(i.toLong()), i, centroAVincular)
            centroAVincular.agregarEncuentro(encuentro)
        }
    }

    fun reservarEncuentroAll(cantidadDeReservas: Int,centro: CentroDeLectura){
        repeat(cantidadDeReservas){
            for (encuentro in centro.getConjuntoDeEncuentros()){
                centro.reserva(encuentro)
            }
        }
    }

    describe("Test de centro de lectura") {

        describe("Centro de Lectura Particular") {

            val particular = Particular(
                nombreDeCentroDeLectura = "Centro de Lectura Particular",
                direccion = "Casa de Pipo",
                libroAsignadoALeer = LibroBuilder().build(),
                costoDeReserva = 100.0,
                conjuntoDeEncuentros = mutableSetOf(),
                capacidadMaximaFijada = 40,
                porcentajeMinimo = 5.0
            )
            crearEncuentroNoVencido(3, particular)

            it("Maxima capacidad de personas por encuentro") {
                particular.maximaCapacidadPorEncuentro() shouldBe 40
            }

            it("Costo de reserva del primer encuentro para la primer persona (precio sin el adicional)") {
                particular.costo(particular.getConjuntoDeEncuentros().first()) shouldBe 1100
            }

            it("Costo de reserva del primer encuentro para una tercer persona (precio con el adicional)") {
                particular.getConjuntoDeEncuentros().first().reservarCupo()
                particular.getConjuntoDeEncuentros().first().reservarCupo()
                particular.costo(particular.getConjuntoDeEncuentros().first()) shouldBe 1600
            }

            it("Costo de reserva del ultimo encuentro una segunda persona (precio sin el adicional)") {
                particular.getConjuntoDeEncuentros().last().reservarCupo()
                particular.costo(particular.getConjuntoDeEncuentros().last()) shouldBe 1100
            }
        }

        describe("Centro de Lectura Editorial") {

            //val libroBestSeller = LibroBuilder().ediciones(3).ventasSemanales(15000).build()
            val libroNoBestSeller = LibroBuilder().ediciones(3).ventasSemanales(9000).build()

            val editorial = Editorial(
                nombreDeCentroDeLectura = "Centro de Lectura Editorial",
                direccion = "Casa de Pipo",
                libroAsignadoALeer = libroNoBestSeller,
                costoDeReserva = 2000.0,
                conjuntoDeEncuentros = mutableSetOf(),
                montoEspecificoAlcanzar = 20000,
                autorPresente = false
            )

            crearEncuentroNoVencido(3, editorial)

            it("Maxima capacidad de personas por encuentro") {
                editorial.maximaCapacidadPorEncuentro() shouldBe 10
            }

            it("Costo de reserva del primer encuentro (no asiste autor)") {
                editorial.costo(editorial.getConjuntoDeEncuentros().first()) shouldBe 3800
            }

            it("Costo de reserva del primer encuentro (asiste autor y no es BestSeller)") {
                editorial.setAutorPresente(true)
                editorial.costo(editorial.getConjuntoDeEncuentros().first()) shouldBe 4000
            }

            it("Costo de reserva del primer encuentro (asiste autor y es BestSeller)") {
                //editorial.setLibroAsignadoALeer(libroBestSeller) PREGUNTAR porque no funciona
                editorial.setAutorPresente(true)
                libroNoBestSeller.setVentaSemanales(15000)
                editorial.costo(editorial.getConjuntoDeEncuentros().first()) shouldBe 5300
            }
        }

        describe("Centro de Lectura Biblioteca"){
            
            val biblioteca = Biblioteca(
                nombreDeCentroDeLectura = "Centro de Lectura Biblioteca",
                direccion = "Casa de Pipo",
                libroAsignadoALeer = LibroBuilder().build(),
                costoDeReserva = 2000.0,
                conjuntoDeEncuentros = mutableSetOf(),
                metrosCuadradosSala = 100,
                gastoFijo = mutableListOf(10000.0, 5000.0)
            )

            biblioteca.agregarGasto(15000.0)

            it("Maxima capacidad de personas por encuentro") {
                biblioteca.maximaCapacidadPorEncuentro() shouldBe 100
            }

            it("Costo de reserva del primer encuentro (con cuatro encuentros)") {
                crearEncuentroNoVencido(4, biblioteca)
                biblioteca.costo(biblioteca.getConjuntoDeEncuentros().first()) shouldBe 3420
            }

            it("Costo de reserva del ultimo encuentro (con 10 encuentros)") {
                crearEncuentroNoVencido(10, biblioteca)
                biblioteca.costo(biblioteca.getConjuntoDeEncuentros().last()) shouldBe 3450
            }
        }

        describe("Centro de Lectura General"){

            val particularGeneral = Particular(
                nombreDeCentroDeLectura = "Centro de Lectura Biblioteca",
                direccion = "Casa de Pipo",
                libroAsignadoALeer = LibroBuilder().build(),
                costoDeReserva = 2000.0,
                conjuntoDeEncuentros = mutableSetOf(),
                capacidadMaximaFijada = 40,
                porcentajeMinimo = 5.0)

            it("Se cambia el libro asignado para un encuentro") {
                //Arrange
                crearEncuentroNoVencido(3, particularGeneral)
                val libroALeer = LibroBuilder().nombre("El Principito").build()
                //Act
                particularGeneral.setLibroAsignadoALeer(libroALeer)
                //Assert
                particularGeneral.getLibroAsignadoALeer() shouldBe libroALeer

            }

            it("Se cambia el costo de la reserva") {
                //Arrange
                crearEncuentroNoVencido(3, particularGeneral)
                //Act
                particularGeneral.setCostoDeReserva(3000.0)
                //Assert
                particularGeneral.getCostoDeReserva() shouldBe 3000

            }
            it("Se intenta reservar un cupo exitosamente, la cantidad de encuentros disponibles es igual a 39") {
                //Arrange
                crearEncuentroNoVencido(3, particularGeneral)
                //Act
                particularGeneral.reserva(particularGeneral.getConjuntoDeEncuentros().first())
                //Assert
                particularGeneral.getConjuntoDeEncuentros().first().disponible() shouldBe 39

            }

            it("Se intenta reservar un cupo en un encuentro cuya fecha esta vencida") {
                //Arrange
                crearEncuentroVencido(3, particularGeneral)
                //Assert
                shouldThrow<SinCupo> { particularGeneral.reserva(particularGeneral.getConjuntoDeEncuentros().first()) }
            }

            it("Se verifican si todas los encuentros estan vencidos.") {
                //Arrange
                crearEncuentroVencido(3, particularGeneral)
                //Assert
                particularGeneral.vencimento() shouldBeEqual true
            }

            it("Los encuentros no estan vencidos y pero no hay cupo disponibles.") {
                //Arrange
                crearEncuentroNoVencido(3, particularGeneral)
                //Act
                reservarEncuentroAll(40, particularGeneral)
                //Assert
                shouldThrow<SinCupo> { particularGeneral.reserva(particularGeneral.getConjuntoDeEncuentros().first()) }
            }

            it("No hay cupos disponibles.") {
                //Arrange
                crearEncuentroNoVencido(3, particularGeneral)
                //Act
                reservarEncuentroAll(40, particularGeneral)
                //Assert
                particularGeneral.capacidadMaximaAlcanzada() shouldBe true
            }
            it("Se quitan purga un encuentro de la 'collection' de encuentros.") {
                //Arrange
                crearEncuentroNoVencido(3, particularGeneral)
                //Act
                particularGeneral.eliminarEncuentro(particularGeneral.getConjuntoDeEncuentros().first())
                //Assert
                particularGeneral.getConjuntoDeEncuentros().size shouldBe 2
            }
        }
    }
})