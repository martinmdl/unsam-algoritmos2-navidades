@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
class AutorSpec : DescribeSpec({
    describe("Testear autor"){
        val autorConsagrado = Autor(
            "pipo",
            "Alegre",
            "yagoo",
            LocalDate.of(1969, 3, 27),
            mutableSetOf(),
            Lenguaje.es_ES,
            2
        )
        val autorNoConsagrado1 = Autor(
            "pipo",
            "Alegre",
            "yagoo",
            LocalDate.of(2002, 3, 27),
            mutableSetOf(),
            Lenguaje.es_ES,
            0
        )
        val autorNoConsagrado2 = Autor(
            "marto",
            "rodriguez",
            "marta",
            LocalDate.of(1969, 3, 27),
            mutableSetOf(),
            Lenguaje.es_ES,
            0
        )

        it("Es consagrado si es mayor de 50 años y tiene por lo menos 1 premio"){
            autorConsagrado.esConsagrado() shouldBe true
        }

        it("No es consagrado si es menor de 50 años aunque tenga premios"){
            autorNoConsagrado1.esConsagrado() shouldBe false
        }

        it("No es consagrado si es mayor de 50 años y no tiene premios"){
            autorNoConsagrado2.esConsagrado() shouldBe false
        }
    }
})