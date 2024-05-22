@file:Suppress("SpellCheckingInspection")
package ar.edu.unsam.algo2.readapp
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class UsuarioSpec : DescribeSpec({

    describe("Tests de usuario") {

        val autorConsagrado = Autor(
            id = null,
            "pipo",
            "Alegre",
            "yagoo", LocalDate.of(1969, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2
        )

        val autorNoConsagrado = Autor(
            id = null,
            "pipo",
            "Alegre",
            "yagoo", LocalDate.of(2002, 3, 27), mutableSetOf(), Lenguaje.es_ES, 0
        )

        val usuario1 = Usuario(
            nombre = "Diego",
            apellido = "Alegre",
            username = "pipo",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(2000, 4, 23),
            direccionEmail = "pipo@gmail.com",
            autorFavorito = autorNoConsagrado,
            lenguaNativa = Lenguaje.es_ES
        )

        val usuario2 = Usuario(
            nombre = "Diego",
            apellido = "Alegre",
            username = "pipo",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(2000, 4, 23),
            direccionEmail = "pipo@gmail.com",
            autorFavorito = autorNoConsagrado,
            lenguaNativa = Lenguaje.ja_JP
        )

        val libroDesafiante = Libro(
            id = null,
            "HarryPotter",
            "Salamandra",
            800,
            75000,
            true,
            5,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP),
            10001,
            autorConsagrado
        )

        val libroNoDesafiante = Libro(
            id = null,
            "OnePiece",
            "ivrea",
            150,
            10000,
            false,
            1,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP),
            10001,
            autorConsagrado
        )
        val recomendacion1 = Recomendacion(
            id = null,
            esPrivado = true,
            creador = usuario1,
            librosRecomendados = mutableSetOf(libroDesafiante, libroNoDesafiante),
            descripcion = "no se leer",
            mutableMapOf()
        )

        val recomendacion2 = Recomendacion(
            id = null,
            esPrivado = true,
            creador = usuario1,
            librosRecomendados = mutableSetOf(libroNoDesafiante),
            descripcion = "no pude leer",
            mutableMapOf()
        )

        describe("Tiempo de lectura") {

            it("cuánto tarda en leer un libro desafiante") {
                usuario1.tiempoDeLectura(libroDesafiante) shouldBe 1500
            }

            it("cuánto tarda en leer un libro no desafiante") {
                usuario1.tiempoDeLectura(libroNoDesafiante) shouldBe 100
            }
        }

        describe("Tipo lector") {
            it("Por defecto es un lector promedio") {
                usuario1.tipoLector.shouldBeInstanceOf<LectorPromedio>()
            }

            it("El usuario puede variar su tipo de lectura") {
                val lectorNormal = LectorNormal(usuario1)
                usuario1.variarTipoLector(lectorNormal)
                usuario1.tipoLector shouldBe lectorNormal
            }
        }

        describe("Libros") {

            it("El usuario puede agregar libros que quiere leer") {

                usuario1.agregarLibroPorLeer(libroDesafiante)
                usuario1.agregarLibroPorLeer(libroNoDesafiante)

                usuario1.librosPorLeer shouldBe mutableSetOf(libroDesafiante, libroNoDesafiante)
            }


            it("Leo un libro, se agrega a libros leídos y se elimina de libros por leer") {

                usuario1.leerLibro(libroDesafiante)
                usuario1.leerLibro(libroDesafiante)

                usuario1.librosPorLeer shouldBe mutableSetOf(libroNoDesafiante)
                usuario1.librosLeidos shouldBe mutableMapOf(libroDesafiante to 2)
            }

            it("Cuando el usuario agrega a libros por leer un libro ya leído, se captura el error") {
                assertThrows<Exception> { usuario1.agregarLibroPorLeer(libroDesafiante) }
                usuario1.librosLeidos shouldBe mutableMapOf(libroDesafiante to 2)
            }

            it("El usuario puede eliminar libros por leer") {
                usuario1.eliminarLibroPorLeer(libroNoDesafiante)
                usuario1.librosPorLeer shouldBe mutableSetOf()
            }
        }

        describe("Autor") {

            it("El usuario puede cambiar su autor favorito") {
                usuario1.autorFavorito shouldBe autorNoConsagrado

                usuario1.variarAutorFavorito(autorConsagrado)

                usuario1.autorFavorito shouldBe autorConsagrado
            }
        }

        describe("Amigos") {

            it("El usuario puede agregar amigos") {
                usuario1.amigos shouldBe mutableSetOf()
                usuario1.agregarAmigo(usuario2)
                usuario1.amigos shouldBe mutableSetOf(usuario2)
            }
            it("El usuario puede eliminar amigos de su lista") {
                usuario1.eliminarAmigo(usuario2)
                usuario1.amigos shouldBe mutableSetOf()
            }
        }

        describe("Recomendaciones") {

            it("Por defecto tiene el perfil de recomendación de Leedor") {
                usuario1.perfilDeRecomendacion.shouldBeInstanceOf<Leedor>()
            }

            it("El usuario puede cambiar el perfil de recomendación") {
                val poliglota = Poliglota(usuario1)
                usuario1.cambiarPerfilDeRecomendacion(poliglota)
                usuario1.perfilDeRecomendacion shouldBe poliglota
            }
        }

        describe("Valoraciones") {

            it("El usuario puede agregar una recomendacion a valorar a una lista") {

                usuario1.agregarRecomendacionPorValorar(recomendacion1)
                usuario1.agregarRecomendacionPorValorar(recomendacion2)

                usuario1.recomendacionesPorValorar shouldBe mutableSetOf(recomendacion2, recomendacion1)
            }

            it("El usuario puede eliminar una recomendacion de la lista") {

                usuario1.eliminarRecomendacionPorValorar(recomendacion1)

                usuario1.recomendacionesPorValorar shouldBe mutableSetOf(recomendacion2)
            }
            it("El usuario puede valorar una recomendación y esta se eliminará de la lista") {
                usuario1.valorarRecomendacion(recomendacion2, 4, "me fue mal")
                usuario1.recomendacionesPorValorar shouldBe mutableSetOf()
            }
        }
    }
})