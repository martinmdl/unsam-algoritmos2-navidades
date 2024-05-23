@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate

class RecomendacionesSpec : DescribeSpec({
    describe("Test de las Recomendaciones") {
        //Arrange
        /*Autor*/
        val autor1 = Autor(
            id = null,
            "pipo",
            "Alegre",
            "yagoo", LocalDate.of(1990, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2
        )
        /*LIBROS*/
        val harryPotter = Libro(
            id = null,
            "HarryPotter",
            "Salamandra",
            800,
            50000,
            true,
            5,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP),
            10001,
            autor1
        )
        val harryPotter2 = Libro(
            id = null,
            "HarryPotter",
            "Salamandra",
            800,
            55000,
            true,
            5,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP),
            10001,
            autor1
        )
        val harryPotter3 = Libro(
            id = null,
            "HarryPotter",
            "Salamandra",
            800,
            60000,
            true,
            5,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP),
            10001,
            autor1
        )
        val harryPotter4 = Libro(
            id = null,
            "HarryPotter",
            "Salamandra",
            800,
            65000,
            true,
            5,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP),
            10001,
            autor1
        )
        val harryPotter5 = Libro(
            id = null,
            "HarryPotter",
            "Salamandra",
            800,
            70000,
            true,
            5,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP),
            10001,
            autor1
        )

        /*USUARIOS*/

        val usuario1 = Usuario(
            nombre = "pipo1",
            apellido = "alegre",
            username = "pipojr10",
            palabrasPorMinuto = 150,
            fechaNac = LocalDate.of(1990, 3, 27),
            direccionEmail = "pipo@yahoo.com",
            librosLeidos = mutableMapOf(harryPotter to 1, harryPotter2 to 1, harryPotter3 to 1, harryPotter4 to 1),
            autorFavorito = autor1,
            lenguaNativa = Lenguaje.es_ES
        )

        val usuario2 = Usuario(
            nombre = "pipo2",
            apellido = "alegre",
            username = "pipojr10",
            palabrasPorMinuto = 200,
            fechaNac = LocalDate.of(1990, 3, 27),
            direccionEmail = "pipo@yahoo.com",
            amigos = mutableSetOf(usuario1),
            librosLeidos = mutableMapOf(harryPotter2 to 1, harryPotter3 to 1, harryPotter5 to 1),
            autorFavorito = autor1,
            lenguaNativa = Lenguaje.es_ES
        )

        val usuario3 = Usuario(
            nombre = "pipo3",
            apellido = "alegre",
            username = "pipojr10",
            palabrasPorMinuto = 250,
            fechaNac = LocalDate.of(1990, 3, 27),
            direccionEmail = "pipo@yahoo.com",
            amigos = mutableSetOf(usuario1, usuario2),
            librosLeidos = mutableMapOf(
                harryPotter to 1,
                harryPotter2 to 1,
                harryPotter3 to 1,
                harryPotter4 to 1,
                harryPotter5 to 1
            ),
            autorFavorito = autor1,
            lenguaNativa = Lenguaje.es_ES
        )

        /*RECOMENDACIONES*/

        val recomendacion1 = Recomendacion(
            id = null,
            esPrivado = true,
            creador = usuario1,
            librosRecomendados = mutableSetOf(harryPotter2),
            descripcion = "no se leer",
            mutableMapOf()
        )
        val recomendacion2 = Recomendacion(
            id = null,
            esPrivado = false,
            creador = usuario2,
            librosRecomendados = mutableSetOf(harryPotter2, harryPotter3),
            descripcion = "no se leer",
            mutableMapOf()
        )
        val recomendacion3 = Recomendacion(
            id = null,
            esPrivado = true,
            creador = usuario3,
            librosRecomendados = mutableSetOf(harryPotter, harryPotter2),
            descripcion = "no se leer",
            mutableMapOf()
        )

        describe("Test de edicion de la privacidad") {

            it("El creador puede editar la privacidad en la recomendacion") {
                recomendacion3.editarPrivacidad(usuario3)

                recomendacion3.esPrivado shouldBe false
            }
            it("El amigo no puede editar la privacidad en la recomendacion") {
                shouldThrow<Businessexception> { recomendacion3.editarPrivacidad(usuario2) }
            }
        }

        describe("Test de edicion de la descripcion") {

            it("El creador puede editar la descripcion en la recomendacion") {

                recomendacion3.editarDescripcion(usuario3, "nueva descripcion")

                recomendacion3.descripcion shouldBe "nueva descripcion"
            }
            it("El amigo puede editar la descripcion en la recomendacion") {

                recomendacion3.editarDescripcion(usuario2, "cambio de descripcion")

                recomendacion3.descripcion shouldBe "cambio de descripcion"
            }
            it("El usuario no puede editar la descripcion en la recomendacion") {
                shouldThrow<Businessexception> { recomendacion1.editarDescripcion(usuario2, "cambio de descripcion") }
            }
        }

        describe("Test de agregar un libro") {

            it("El creador puede agregar un libro (a la recomendacion)") {

                recomendacion3.agregarALibrosDeRecomendacion(usuario3, harryPotter3)

                recomendacion3.librosRecomendados shouldBe mutableSetOf(harryPotter, harryPotter2, harryPotter3)
            }
            it("El creador no puede agregar un libro a la recomendacion porque no leyo ese libro") {
                shouldThrow<Businessexception> { recomendacion1.agregarALibrosDeRecomendacion(usuario1, harryPotter5) }
            }
            it("El amigo puede agregar un libro (a la recomendacion)") {

                recomendacion3.agregarALibrosDeRecomendacion(usuario3, harryPotter4)

                recomendacion3.librosRecomendados shouldBe mutableSetOf(
                    harryPotter,
                    harryPotter2,
                    harryPotter3,
                    harryPotter4
                )
            }
            it("El amigo no puede agregar un libro (a la recomendacion) porque no leyo ese libro") {
                shouldThrow<Businessexception> { recomendacion3.agregarALibrosDeRecomendacion(usuario1, harryPotter5) }
            }
            it("El usuario no puede agregar un libro (a la recomendacion) porque es privada (la recomendacion)") {
                shouldThrow<Businessexception> { recomendacion1.agregarALibrosDeRecomendacion(usuario2, harryPotter3) }
            }
            it("El amigo no puede agregar un libro (a la recomencadion) porque no leyo todos los libros (de la recomendacion)") {
                shouldThrow<Businessexception> { recomendacion3.agregarALibrosDeRecomendacion(usuario2, harryPotter5) }
            }
        }

        describe("Test de leer recomendacion") {

            it("Creador puede leer la recomendacion") {

                recomendacion1.leerRecomendacion(usuario1) shouldBe true
            }
            it("amigo puede leer la recomendacion") {

                recomendacion3.leerRecomendacion(usuario1) shouldBe true
            }
            it("usuario puede leer la recomendacion") {

                recomendacion2.leerRecomendacion(usuario3) shouldBe true
            }
            it("usuario no puede leer la recomendacion") {

                recomendacion1.leerRecomendacion(usuario2) shouldBe false
            }
        }
        describe("Test de Tiempos de lectura") {

            it("Tiempo de lectura total; Recomendacion tiene 4 libros") {
                recomendacion3.tiempoDeLecturaTotal(usuario1).toInt() shouldBe 3066
            }

            it("Tiempo de lectura neto; Recomendacion tiene 4 libros") {
                recomendacion3.tiempoDeLecturaNeto(usuario2).toInt() shouldBe 1150
            }

            it("Tiempo de lectura ahorrado; Recomendacion tiene 4 libros") {
                recomendacion3.tiempoDeLecturaAhorrado(usuario2).toInt() shouldBe 1150
            }
        }
    }
})