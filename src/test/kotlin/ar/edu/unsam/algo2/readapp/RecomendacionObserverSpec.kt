@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import java.time.LocalDate

class RecomendacionObserverSpec: DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    describe("Test de Observer NotificarCreador") {
        val mockMailSender = mockk<MailSender>( relaxUnitFun = true)
        //UsuarioA: Creador de la recomendacion
    }

    describe("Test de Observer Registro"){

    }

    describe("Test de Observer BannearSpammer"){

    }

    describe("Test de Observer ValoracionAutomatica"){

    }
})