@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate

class ServiceSpec : DescribeSpec ({
    isolationMode = IsolationMode.InstancePerLeaf

    //MOCK SERVICIO
    val serviceLibro = mockk<ServiceLibros>(relaxUnitFun = true)

    describe("Test de Servicio de Actualizacion de Libros"){
        it("Se recibe un JSON y se parsea correctamente") {
            //ARRANGE
            every { serviceLibro.getLibros() } answers { "[{\"id\": 5, \"ediciones\": 6, \"ventasSemanales\": 15000}, {\"id\": 12, \"ediciones\": 1, \"ventasSemanales\": 1000}, {\"id\": 15, \"ediciones\": 3, \"ventasSemanales\": 11000}, {\"id\": 2, \"ediciones\": 2, \"ventasSemanales\": 2000}]" }
            val updateLibros = UpdateLibros(serviceLibro)
            //ACT
            val dataUpdate = updateLibros.parseJson()
            println("dataUpdate: $dataUpdate")
            //ASSERT
            dataUpdate.libros.size shouldBe 4
        }

        it("Servicio de actualizacion de libros") {
            //ARRANGE
            val autor1 = Autor(
                id= null,
                "pipo",
                "Alegre",
                "yagoo", LocalDate.of(1990, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2
            )
            val harryPotter = Libro(
                id= 1,
                "HarryPotter",
                "Salamandra",
                800,
                75000,
                true,
                1,
                setOf(Lenguaje.es_ES, Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
                1,
                autor1
            )
            every { serviceLibro.getLibros() } answers { "[{\"id\": 1, \"ediciones\": 2, \"ventasSemanales\": 2000}]" }
            val updateLibros = UpdateLibros(serviceLibro)
            //ACT
            updateLibros.update(harryPotter)
            //ASSERT
            harryPotter.getEdiciones() shouldBe 2
            harryPotter.getVentasSemanales() shouldBe 2001
        }
    }
})