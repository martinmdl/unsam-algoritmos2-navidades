package ar.edu.unsam.algo2.readapp

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

/*Test Unitario*/
class CentroDeLecturaSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    describe("Test de centro de lectura") {
        val autorConsagrado = Autor(
            "pipo",
            "Alegre",
            "yago", LocalDate.of(1969, 3, 27),
            mutableSetOf(),
            Lenguaje.es_ES,
            2
        )
        val autorNoConsagrado = Autor(
            "pipo",
            "Alegre",
            "yago", LocalDate.of(2002, 3, 27),
            mutableSetOf(),
            Lenguaje.es_ES,
            0
        )

        val usuario = Usuario(
            nombre = "Diego",
            apellido = "Alegre",
            username = "pipo",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(2000, 4, 23),
            direccionEmail = "yagoo@pipomail.ru",
            amigos = mutableSetOf(),
            librosLeidos = mutableMapOf(),
            autorFavorito = autorConsagrado,
            recomendacionesPorValorar = mutableSetOf(),
            librosPorLeer = mutableSetOf(),
            lenguaNativa = Lenguaje.es_ES
        )

        val libroDesafiante = Libro(
            "HarryPotter",
            "Salamandra",
            800,
            100000,
            true,
            1,
            setOf(Lenguaje.es_ES),
            1,
            autorConsagrado
        )

        val libroNoDesafiante = Libro(
            "OnePiece",
            "brea",
            150,
            10000,
            false,
            1,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP),
            10001,
           autorNoConsagrado
        )

        //Arrange
        autorConsagrado.librosEscritos.add(libroDesafiante)
        autorNoConsagrado.librosEscritos.add(libroNoDesafiante)

        //Centros de lectura
        val centroDeLecturaParticular = Particular(
            nombreDeCentroDeLectura = "Editorial Central",
            direccion = "Avenida Siempre Viva 123",
            libroAsignadoALeer = libroNoDesafiante,
            costoDeReserva = 200.0,
            conjuntoDeEncuentros = mutableSetOf(),
            capacidadMaximaFijada = 40,
            porcentajeMinimo = 5.0
        )

        val centroDeLecturaEditorial = Editorial(
            nombreDeCentroDeLectura = "Editorial Central",
            direccion = "Avenida Siempre Viva 123",
            libroAsignadoALeer = libroNoDesafiante,
            costoDeReserva = 200.0,
            conjuntoDeEncuentros = mutableSetOf(),
            montoEspecificoAlcanzar = 5000,
            autorPresente = false
        )

        val centroDeLecturaBiblioteca = Biblioteca(
            nombreDeCentroDeLectura = "Biblioteca Central",
            direccion = "Calle Principal 123",
            libroAsignadoALeer = libroNoDesafiante,
            costoDeReserva = 300.0,
            conjuntoDeEncuentros = mutableSetOf(),
            metrosCuadradosSala = 100,
            gastoFijo = mutableListOf(100.0, 200.0, 300.0)
        )

        describe("test centro particular"){
            //Arrange
            /*Encuentros no vencidos*/
            val encuentroNoVencido1 = Encuentro(
                fecha = LocalDate.now().plusDays(2),
                duracion = 1,
                centroDeLectura = centroDeLecturaParticular
            )
            val encuentroNoVencido2 = Encuentro(
                fecha = LocalDate.now().plusDays(4),
                duracion = 1,
                centroDeLectura = centroDeLecturaParticular
            )

            val encuentroNoVencido3 = Encuentro(
                fecha = LocalDate.now().plusDays(6),
                duracion = 1,
                centroDeLectura = centroDeLecturaParticular
            )

            /*Encuentros vencidos*/
            val encuentroVencido1 = Encuentro(
                fecha = LocalDate.now().minusDays(1),
                duracion = 1,
                centroDeLectura = centroDeLecturaParticular
            )
            val encuentroVencido2 = Encuentro(
                fecha = LocalDate.now().minusDays(2),
                duracion = 1,
                centroDeLectura = centroDeLecturaParticular
            )
            val encuentroVencido3 = Encuentro(
                fecha = LocalDate.now().minusDays(4),
                duracion = 1,
                centroDeLectura = centroDeLecturaParticular
            )
            it("Un usuario puede ejecutar una reserva"){
                //Act
                centroDeLecturaParticular.agregarEncuentro(encuentroNoVencido1)
                centroDeLecturaParticular.agregarEncuentro(encuentroNoVencido2)
                centroDeLecturaParticular.agregarEncuentro(encuentroNoVencido3)
                //Assert
                centroDeLecturaParticular.reserva(encuentroNoVencido1) shouldBe 1200
                centroDeLecturaParticular.reserva(encuentroNoVencido2) shouldBe 1200
                centroDeLecturaParticular.reserva(encuentroNoVencido3) shouldBe 1200
            }
            it("Un usuario puede ejecutar una reserva"){
                //Act
                centroDeLecturaParticular.reserva(encuentroNoVencido1)
                centroDeLecturaParticular.reserva(encuentroNoVencido1)
                //Assert
                centroDeLecturaParticular.reserva(encuentroNoVencido1) shouldBe 1700
            }
            it("Un usuario no puede ejecutar una reserva (fecha vencida)"){
                //Act
                centroDeLecturaParticular.agregarEncuentro(encuentroVencido1)
                //Assert
                shouldThrow<SinCupo> {centroDeLecturaParticular.reserva(encuentroVencido1)}
            }
        }

    }
})