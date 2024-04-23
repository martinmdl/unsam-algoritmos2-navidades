@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class ValoracionSpec:DescribeSpec({
    describe("Test de Valoracion") {
        //Arrange
        /*Autor*/
        val autor1 = Autor("Alegre", LocalDate.of(1990, 3, 27), mutableSetOf(),Lenguaje.es_ES,2)

        /*Libro*/
        val harryPotter = Libro(
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

        val harryPotter2 = Libro(
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

        val usuario1 = Usuario(
            nombre = "pipo",
            apellido = "Alegre",
            username = "pipojr10",
            palabrasPorMinuto = 250,
            fechaNac = LocalDate.of(1990, 3, 27),
            direccionEmail = "pipo@yahoo.com",
            librosLeidos = mutableMapOf(harryPotter to 1),
            autorFavorito = autor1,
            lenguaNativa = Lenguaje.es_ES
        )

        val usuario2 = Usuario(
            nombre = "pipa",
            apellido = "Alegre",
            username = "pipojr10",
            palabrasPorMinuto = 250,
            fechaNac = LocalDate.of(1990, 3, 27),
            direccionEmail = "pipo@yahoo.com",
            librosLeidos = mutableMapOf(harryPotter2 to 1),
            autorFavorito = autor1,
            lenguaNativa = Lenguaje.es_ES
        )

        val recomendacion1 = Recomendacion(
            esPrivado = true,
            creador = usuario1,
            librosRecomendados = mutableSetOf(harryPotter),
            descripcion = "no se leer",
            mutableMapOf()
        )

        it("El usuario2 da una valoracion para le recomendacion1"){
            //Act
            recomendacion1.crearValoracion(1,"no se leer",usuario2)
            println(recomendacion1.valoraciones)
            //Assert
            (recomendacion1.valoraciones[usuario2]?.comentario ?:String) shouldBe  "no se leer"
            (recomendacion1.valoraciones[usuario2]?.valor ?: Int) shouldBe  1
            (recomendacion1.valoraciones[usuario2]?.autor) shouldBe  usuario2
        }

        it("El usuario2 edita el valor numerico de su recomendacion"){
            //Act
            recomendacion1.valoraciones[usuario2]?.editarValor(5,usuario2)
            //Assert
            (recomendacion1.valoraciones[usuario2]?.valor ?: Int) shouldBe  5
        }

        it("El usuario2 edita el comentario de su recomendacion"){
            //Act
            recomendacion1.valoraciones[usuario2]?.editarComentario("ahora si se leer",usuario2)
            //Assert
            (recomendacion1.valoraciones[usuario2]?.comentario ?: String) shouldBe  "ahora si se leer"
        }

        it("El usuario2 da una valoracion para le recomendacion1 por autor favorito"){
            //Act
            recomendacion1.crearValoracion(3,"no se leer",usuario2)
            //Assert
            (recomendacion1.valoraciones[usuario2]?.comentario ?:String) shouldBe  "no se leer"
            (recomendacion1.valoraciones[usuario2]?.valor ?: Int) shouldBe  3
            (recomendacion1.valoraciones[usuario2]?.autor) shouldBe  usuario2
        }
    }
})