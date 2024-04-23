@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class TiempoDeLecturaSpec : DescribeSpec({
    describe("Test de tiempos de lectura") {

        val autorConsagrado = Autor(
            "pipo",
            "Alegre",
            "yagoo", LocalDate.of(1969, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2
        )
        val autorNoConsagrado = Autor(
            "pipo",
            "Alegre",
            "yagoo", LocalDate.of(2002, 3, 27), mutableSetOf(), Lenguaje.es_ES, 0
        )
        val usuario = Usuario(
            nombre = "Diego",
            apellido = "Alegre",
            username = "pipo",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(2000, 4, 23),
            direccionEmail = "pipo@gmail.com",
            autorFavorito = autorNoConsagrado,
            lenguaNativa = Lenguaje.es_ES
        )

        val libroDesafiante = Libro(
            "HarryPotter",
            "Salamandra",
            800,
            75000,
            true,
            1,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
            1,
            autorConsagrado
        )

        val libroNoDesafiante = Libro(
            "HarryPotter",
            "Salamandra",
            300,
            500,
            false,
            1,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
            1,
            autorConsagrado
        )

        val libroBestSeller = Libro(
            "Skander",
            "Salamandra",
            300,
            75000,
            false,
            5,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
            1,
            autorNoConsagrado
        )

        val libroNotBestSeller = Libro(
            "jojos",
            "Salamandra",
            300,
            75000,
            false,
            1,
            setOf(Lenguaje.ja_JP),
            1,
            autorNoConsagrado
        )

        describe("Dado un lector promedio") {

            it("Con un libro desafiante, disminuye su velocidad de lectura") {
                usuario.tiempoDeLectura(libroDesafiante) shouldBe 1500

            }
            it("Con un libro no desafiante, no cambia su velocidad de lectura") {
                usuario.tiempoDeLectura(libroNoDesafiante) shouldBe 5
            }
        }

        describe("Dado un lector normal") {

            //act
            usuario.variarTipoLector(LectorNormal)

            it("Con un libro desafiante, no cambia su velocidad de lectura") {
                usuario.tiempoDeLectura(libroDesafiante) shouldBe 750
            }
        }

        describe("Dado un lector ansioso") {
            //act
            usuario.variarTipoLector(LectorAnsioso)

            it("Con un libro best seller, su tiempo de lectura disminuye a la mitad") {
                usuario.tiempoDeLectura(libroBestSeller) shouldBe 375

            }
            it("Con un libro no best seller, su tiempo de lectura disminuye un 20%") {
                usuario.tiempoDeLectura(libroNotBestSeller) shouldBe 600
            }
        }

        describe("Dado un lector fanático") {

            usuario.variarTipoLector(LectorFanatico)
            usuario.variarAutorFavorito(autorConsagrado)

            // importante = libro.autor == usuario.autorFavorito && !(usuario.librosLeidos.keys.contains(libro))
            describe("Con libro imporante") {

                it("Siendo un libro corto, se le suma 2 minutos por página") {
                    usuario.tiempoDeLectura(libroNoDesafiante) shouldBe 605
                }

                it("Siendo un libro largo, se le suma 2 minutos por página hasta 600 páginas, luego se le suma 1 minuto por página") {
                    usuario.tiempoDeLectura(libroDesafiante) shouldBe 2900
                }
            }

            usuario.variarAutorFavorito(autorNoConsagrado)

            describe("Con libro no importante") {
                it("Independientemente del largo del libro, no se le suma tiempo adicional") {
                    usuario.tiempoDeLectura(libroNoDesafiante) shouldBe 5
                }
            }
        }

        describe("Dado un lector recurrente") {

            usuario.variarTipoLector(LectorRecurrente)

            it("Libro no leído, no disminuye velocidad") {
                usuario.tiempoDeLectura(libroNoDesafiante) shouldBe 5
            }

            usuario.leerLibro(libroNoDesafiante)

            it("Libro leído 1 vez, disminuye en 1% la velocidad de lectura") {
                usuario.tiempoDeLectura(libroNoDesafiante) shouldBe 500 / (100 * 0.99)
            }

            // DEUDA TECNICA
            usuario.leerLibro(libroNoDesafiante)
            usuario.leerLibro(libroNoDesafiante)
            usuario.leerLibro(libroNoDesafiante)
            usuario.leerLibro(libroNoDesafiante)

            it("Libro leído mas de 5 veces, no disminuye mas que 4% la velocidad de lectura") {
                usuario.tiempoDeLectura(libroNoDesafiante) shouldBe 500 / (100 * 0.96)
            }
        }
    }
})

