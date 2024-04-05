@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class ValoracionSpec:DescribeSpec({
    describe("Test de Valoracion") {
        //Arrange
        /*Autor*/
        val autor1 = Autor(Lenguaje.es_ES,2)

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
            "pipo",
            "alegre",
            "pipojr10",
            mutableSetOf(),
            mutableSetOf(harryPotter),
            250,
            (LocalDate.of(2000, 10, 1)),
            autor1
        )
        val usuario2 = Usuario(
            "pipa",
            "alegre",
            "pipojr10",
            mutableSetOf(),
            mutableSetOf(harryPotter2),
            250,
            (LocalDate.of(2000, 10, 1)),
            autor1
        )
        val recomendacion1 = Recomendacion(
            esPrivado = true,
            creador = usuario1,
            libroRecomendados = mutableSetOf(harryPotter),
            descripcion = "no se leer",
            mutableMapOf()
        )
        it("El usuario1 da una valoracion para le recomendacion1"){
            //Act
            recomendacion1.crearValoracion(1,"no se leer",usuario1)
            //Assert
            (recomendacion1.valoraciones[usuario1]?.comentario ?:String) shouldBe  "no se leer"
            (recomendacion1.valoraciones[usuario1]?.valor ?: Int) shouldBe  1
            (recomendacion1.valoraciones[usuario1]?.autor ?: Usuario) shouldBe  usuario1
        }
        it("El usuario1 edita el valor numerico de su recomendacion"){
            //Act
            recomendacion1.valoraciones[usuario1]?.editarValor(5,usuario1)
            //Assert
            (recomendacion1.valoraciones[usuario1]?.valor ?: Int) shouldBe  5
        }
        it("El usuario1 edita el comentario de su recomendacion"){
            //Act
            recomendacion1.valoraciones[usuario1]?.editarComentario("ahora si se leer",usuario1)
            //Assert
            (recomendacion1.valoraciones[usuario1]?.comentario ?: String) shouldBe  "ahora si se leer"
        }
        it("El usuario2 da una valoracion para le recomendacion1 por autor favorito"){
            //Act
            recomendacion1.crearValoracion(3,"no se leer",usuario2)
            //Assert
            (recomendacion1.valoraciones[usuario2]?.comentario ?:String) shouldBe  "no se leer"
            (recomendacion1.valoraciones[usuario2]?.valor ?: Int) shouldBe  3
            (recomendacion1.valoraciones[usuario2]?.autor ?: Usuario) shouldBe  usuario2
        }

    }
})