package ar.edu.unsam.algo2.readapp

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

/*Test Unitario*/
class CentroDeLecturaSpec : DescribeSpec({
    isolationMode = IsolationMode.SingleInstance
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
        val bestSellerLibro = Libro(
            "HarryPotter",
            "Salamandra",
            800,
            100000,
            true,
            10,
            setOf(Lenguaje.es_ES),
            20000,
            autorConsagrado)

        //Arrange
        autorConsagrado.librosEscritos.add(libroDesafiante)
        autorNoConsagrado.librosEscritos.add(libroNoDesafiante)
        autorNoConsagrado.librosEscritos.add(bestSellerLibro)

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
        val centroDeLecturaEditorialBestSeller = Editorial(
            nombreDeCentroDeLectura = "Editorial Central",
            direccion = "Avenida Siempre Viva 123",
            libroAsignadoALeer = bestSellerLibro,
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
        describe("test de centro Lectura en general"){
            it("Un centro de lectura puede agregar un encuentro"){
                //Act
                centroDeLecturaParticular.agregarEncuentro(Encuentro(
                    fecha = LocalDate.now().plusDays(2),
                    duracion = 1,
                    centroDeLectura = centroDeLecturaParticular
                ))
                //Assert
                centroDeLecturaParticular.getConjuntoDeEncuentros().size shouldBe 1
            }
            it("Un centro de lectura puede agregar otro encuentro y luego eliminarlo"){
                //Act
                val encuentro = Encuentro(
                    fecha = LocalDate.now().plusDays(2),
                    duracion = 1,
                    centroDeLectura = centroDeLecturaParticular
                )
                centroDeLecturaParticular.agregarEncuentro(encuentro)
                centroDeLecturaParticular.eliminarEncuentro(encuentro)
                //Assert
                centroDeLecturaParticular.getConjuntoDeEncuentros().size shouldBe 1
            }
            it("Un centro de lectura puede agregar un encuentro y luego eliminarlo, pero no se encuentra en el conjunto de encuentros"){
                //Act
                val encuentro = Encuentro(
                    fecha = LocalDate.now().plusDays(2),
                    duracion = 1,
                    centroDeLectura = centroDeLecturaParticular
                )
                centroDeLecturaParticular.agregarEncuentro(encuentro)
                centroDeLecturaParticular.eliminarEncuentro(encuentro)
                //Assert
                centroDeLecturaParticular.getConjuntoDeEncuentros().contains(encuentro) shouldBe false
            }
            it("Un centro de lectura contiene encuentros vencidos."){
                //Act
                val vencidos = Particular(
                    nombreDeCentroDeLectura = "Editorial Central",
                    direccion = "Avenida Siempre Viva 123",
                    libroAsignadoALeer = libroNoDesafiante,
                    costoDeReserva = 200.0,
                    conjuntoDeEncuentros = mutableSetOf(),
                    capacidadMaximaFijada = 40,
                    porcentajeMinimo = 5.0)

                val encuentroVencido1 = Encuentro(
                    fecha = LocalDate.now().minusDays(2),
                    duracion = 1,
                    centroDeLectura = vencidos
                )
                val encuentroVencido2 = Encuentro(
                    fecha = LocalDate.now().minusDays(4),
                    duracion = 1,
                    centroDeLectura = vencidos
                )
                val encuentroVencido3 = Encuentro(
                    fecha = LocalDate.now().minusDays(6),
                    duracion = 1,
                    centroDeLectura = vencidos
                )

                vencidos.agregarEncuentro(encuentroVencido1)
                vencidos.agregarEncuentro(encuentroVencido2)
                vencidos.agregarEncuentro(encuentroVencido3)
                //Assert
                vencidos.vencimento() shouldBe  true
            }
            it("Un centro de lectura contiene encuentros validos pero sin cupo."){
                //Arrange
                val sinCupo = Particular(
                    nombreDeCentroDeLectura = "Editorial Central",
                    direccion = "Avenida Siempre Viva 123",
                    libroAsignadoALeer = libroNoDesafiante,
                    costoDeReserva = 200.0,
                    conjuntoDeEncuentros = mutableSetOf(),
                    capacidadMaximaFijada = 40,
                    porcentajeMinimo = 5.0)

                val encuentroSinCupo1 = Encuentro(
                    fecha = LocalDate.now().plusDays(2),
                    duracion = 1,
                    centroDeLectura = sinCupo
                )
                val encuentroSinCupo2 = Encuentro(
                    fecha = LocalDate.now().plusDays(4),
                    duracion = 1,
                    centroDeLectura = sinCupo
                )
                val encuentroSinCupo3 = Encuentro(
                    fecha = LocalDate.now().plusDays(6),
                    duracion = 1,
                    centroDeLectura = sinCupo
                )
                //Act
                sinCupo.agregarEncuentro(encuentroSinCupo1)
                sinCupo.agregarEncuentro(encuentroSinCupo2)
                sinCupo.agregarEncuentro(encuentroSinCupo3)
                sinCupo.apply { repeat(40) {
                    reserva(encuentroSinCupo1)
                    reserva(encuentroSinCupo2)
                    reserva(encuentroSinCupo3)
                } }
                sinCupo.capacidadMaximaAlcanzada() shouldBe true
                shouldThrow<SinCupo> { sinCupo.reserva(encuentroSinCupo1) }
            }
            it("Se cambia el libro de la editorial y se cambia el costo de reserva"){
                //Act
                centroDeLecturaParticular.setLibroAsignadoALeer(libroDesafiante)
                centroDeLecturaParticular.setCostoDeReserva(900.0)
                //Assert
                centroDeLecturaParticular.getLibroAsignadoALeer() shouldBe libroDesafiante
                centroDeLecturaParticular.getCostoDeReserva() shouldBe 900.0
            }
        }



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
            it("Un usuario puede ejecutar una reserva en un centro de lectura particular."){
                //Act
                centroDeLecturaParticular.agregarEncuentro(encuentroNoVencido1)
                centroDeLecturaParticular.agregarEncuentro(encuentroNoVencido2)
                centroDeLecturaParticular.agregarEncuentro(encuentroNoVencido3)
                //Assert
                centroDeLecturaParticular.vencimento() shouldBe false
                centroDeLecturaParticular.reserva(encuentroNoVencido1) shouldBe 1200
                centroDeLecturaParticular.reserva(encuentroNoVencido2) shouldBe 1200
                centroDeLecturaParticular.reserva(encuentroNoVencido3) shouldBe 1200
            }
            it("Un usuario puede ejecutar en un centro de lectura particular una reserva pero esta tiene un nuevo valor."){
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
        describe("test centro editorial"){
            //Arrange
            /*Encuentros no vencidos*/
            val encuentroNoVencidoEditorial1 = Encuentro(
                fecha = LocalDate.now().plusDays(2),
                duracion = 1,
                centroDeLectura = centroDeLecturaEditorial
            )
            val encuentroNoVencidoEditorial2 = Encuentro(
                fecha = LocalDate.now().plusDays(4),
                duracion = 1,
                centroDeLectura = centroDeLecturaEditorial
            )

            val encuentroNoVencidoEditorial3 = Encuentro(
                fecha = LocalDate.now().plusDays(6),
                duracion = 1,
                centroDeLectura = centroDeLecturaEditorial
            )

            /*Encuentros vencidos*/
            val encuentroVencidoEditorial1 = Encuentro(
                fecha = LocalDate.now().minusDays(1),
                duracion = 1,
                centroDeLectura = centroDeLecturaEditorial
            )
            it("Un usuario puede ejecutar una reserva en un centro editorial, sin autor presente."){
                //Act
                centroDeLecturaEditorial.agregarEncuentro(encuentroNoVencidoEditorial1)
                centroDeLecturaEditorial.agregarEncuentro(encuentroNoVencidoEditorial2)
                centroDeLecturaEditorial.agregarEncuentro(encuentroNoVencidoEditorial3)

                //Assert
                centroDeLecturaEditorial.reserva(encuentroNoVencidoEditorial1) shouldBe 2000
                centroDeLecturaEditorial.reserva(encuentroNoVencidoEditorial2) shouldBe 2000
                centroDeLecturaEditorial.reserva(encuentroNoVencidoEditorial3) shouldBe 2000
            }
            it("Un usuario puede ejecutar una reserva en un centro editorial pero el autor esta presente."){
                //Act
                centroDeLecturaEditorial.setAutorPresente(true)
                //Assert
                centroDeLecturaEditorial.reserva(encuentroNoVencidoEditorial1) shouldBe 2200
            }
            it("Un usuario puede ejecutar una reserva en un centro editorial pero el autor esta presente y es bestseller"){
                //Act
                centroDeLecturaEditorialBestSeller.setAutorPresente(true)
                //Assert
                centroDeLecturaEditorialBestSeller.reserva(encuentroNoVencidoEditorial1) shouldBe 4000

            }
            it("Un usuario no puede ejecutar una reserva (fecha vencida)"){
                //Act
                centroDeLecturaEditorial.agregarEncuentro(encuentroVencidoEditorial1)
                //Assert
                shouldThrow<SinCupo> {centroDeLecturaParticular.reserva(encuentroVencidoEditorial1)}
            }
        }
        describe("test centro Biblioteca"){
            //Arrange
            /*Encuentros no vencidos*/
            val encuentroNoVencidoBiblioteca1 = Encuentro(
                fecha = LocalDate.now().plusDays(2),
                duracion = 1,
                centroDeLectura = centroDeLecturaEditorial
            )
            val encuentroNoVencidoBiblioteca2 = Encuentro(
                fecha = LocalDate.now().plusDays(4),
                duracion = 1,
                centroDeLectura = centroDeLecturaEditorial
            )

            val encuentroNoVencidoBiblioteca3 = Encuentro(
                fecha = LocalDate.now().plusDays(6),
                duracion = 1,
                centroDeLectura = centroDeLecturaEditorial
            )
            val encuentroNoVencidoBiblioteca4 = Encuentro(
                fecha = LocalDate.now().plusDays(8),
                duracion = 1,
                centroDeLectura = centroDeLecturaEditorial
            )
            val encuentroNoVencidoBiblioteca5 = Encuentro(
                fecha = LocalDate.now().plusDays(9),
                duracion = 1,
                centroDeLectura = centroDeLecturaEditorial
            )
            val encuentroNoVencidoBiblioteca6 = Encuentro(
                fecha = LocalDate.now().plusDays(10),
                duracion = 1,
                centroDeLectura = centroDeLecturaEditorial
            )

            /*Encuentros vencidos*/
            val encuentroVencidoBiblioteca1 = Encuentro(
                fecha = LocalDate.now().minusDays(1),
                duracion = 1,
                centroDeLectura = centroDeLecturaEditorial
            )

            it("Un usuario puede ejecutar una reserva en un centro Biblioteca"){
                //Act
                centroDeLecturaBiblioteca.agregarEncuentro(encuentroNoVencidoBiblioteca1)
                centroDeLecturaBiblioteca.agregarEncuentro(encuentroNoVencidoBiblioteca2)
                centroDeLecturaBiblioteca.agregarEncuentro(encuentroNoVencidoBiblioteca3)

                //Assert
                centroDeLecturaBiblioteca.maximaCapacidadPorEncuentro() shouldBe 100
                centroDeLecturaBiblioteca.reserva(encuentroNoVencidoBiblioteca1) shouldBe 2080.0
                centroDeLecturaBiblioteca.agregarGasto(100.0)
                centroDeLecturaBiblioteca.reserva(encuentroNoVencidoBiblioteca2) shouldBe 2280.0
                centroDeLecturaBiblioteca.agregarGasto(100.0)
                centroDeLecturaBiblioteca.reserva(encuentroNoVencidoBiblioteca3) shouldBe 2500.0
            }
            it("Un usuario puede ejecutar una reserva pero los encuentros son mas de 5 dias."){
                //Act
                centroDeLecturaBiblioteca.agregarEncuentro(encuentroNoVencidoBiblioteca1)
                centroDeLecturaBiblioteca.agregarEncuentro(encuentroNoVencidoBiblioteca2)
                centroDeLecturaBiblioteca.agregarEncuentro(encuentroNoVencidoBiblioteca3)
                centroDeLecturaBiblioteca.agregarEncuentro(encuentroNoVencidoBiblioteca4)
                centroDeLecturaBiblioteca.agregarEncuentro(encuentroNoVencidoBiblioteca5)
                centroDeLecturaBiblioteca.agregarEncuentro(encuentroNoVencidoBiblioteca6)

                //Assert
                centroDeLecturaBiblioteca.getGastoFijo().size shouldBe 5
                centroDeLecturaBiblioteca.getConjuntoDeEncuentros().size shouldBe 6
                centroDeLecturaBiblioteca.reserva(encuentroNoVencidoBiblioteca1) shouldBe 2500.0
                centroDeLecturaBiblioteca.agregarGasto(100.0)
                centroDeLecturaBiblioteca.reserva(encuentroNoVencidoBiblioteca2) shouldBe 2650.0
                centroDeLecturaBiblioteca.agregarGasto(100.0)
                centroDeLecturaBiblioteca.reserva(encuentroNoVencidoBiblioteca3) shouldBe 2800.0
            }
            it("Un usuario no puede ejecutar una reserva (fecha vencida)"){
                //Act
                centroDeLecturaBiblioteca.agregarEncuentro(encuentroVencidoBiblioteca1)
                //Assert
                shouldThrow<SinCupo> {centroDeLecturaParticular.reserva(encuentroVencidoBiblioteca1)}
            }
        }

    }

})