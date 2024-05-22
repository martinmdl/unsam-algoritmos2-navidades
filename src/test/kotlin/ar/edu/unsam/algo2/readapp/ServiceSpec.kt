@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ServiceSpec : DescribeSpec ({
    isolationMode = IsolationMode.InstancePerLeaf

    //MOCK SERVICIO
    val serviceLibro = mockk<ServiceLibros>(relaxUnitFun = true)
    every { serviceLibro.getLibros() } answers { "[{\"id\": 5, \"ediciones\": 6, \"ventasSemanales\": 15000}, {\"id\": 12, \"ediciones\": 1, \"ventasSemanales\": 1000}, {\"id\": 15, \"ediciones\": 3, \"ventasSemanales\": 11000}, {\"id\": 2, \"ediciones\": 2, \"ventasSemanales\": 2000}]" }

    describe("Test de Servicio de Actualizacion de Libros"){
        it("Se recibe un JSON y se parsea correctamente") {
            //ARRANGE
            val updateLibros = UpdateLibros(serviceLibro)
            //ACT
            val dataUpdate = updateLibros.parseJson()
            println("dataUpdate: $dataUpdate")
            //ASSERT
            dataUpdate.libros.size shouldBe 4
        }

        //it("Servicio de actualizacion de libros") {
        //
        //}
    }
})