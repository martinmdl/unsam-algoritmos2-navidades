@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import io.mockk.verify

class RecomendacionObserverSpec: DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val usuario1 = UsuarioBuilder().build()
    val usuario2 = UsuarioBuilder().build()

    val libro1 = LibroBuilder().nombre("Pipo 1").build()
    val libro2 = LibroBuilder().nombre("Pipo 2").build()
    val libro3 = LibroBuilder().build()
    val libro4 = LibroBuilder().build()

    val recomendacion = RecomendacionBuilder()
        .librosRecomendados(mutableSetOf(libro1))
        .creador(usuario1)
        .build()

    usuario1.leerLibro(libro2)
    usuario1.leerLibro(libro3)
    usuario1.leerLibro(libro4)

    usuario1.agregarAmigo(usuario2)
    usuario2.agregarAmigo(usuario1)

    describe("Test de Observer NotificarCreador") {

        val mockMailSender = mockk<MailSender>(relaxUnitFun = true)
        val observerNotificarCreador = NotificarCreador(mockMailSender)
        recomendacion.agregarObserver(observerNotificarCreador)

        it("Un usuario agreaga un libro a la recomendación, el creador es notificado") {

            usuario2.leerLibro(libro1)
            usuario2.leerLibro(libro2)
            recomendacion.agregarALibrosDeRecomendacion(usuario2, libro2)

            verify (exactly = 1) {
                mockMailSender.sendMail(Mail(
                    from = "notificaciones@readapp.com.ar",
                    to = recomendacion.creador.direccionEmail,
                    subject = "Se agregó un Libro",
                    content = "El usuario: ${usuario2.nombre} agrego el Libro ${libro2.getNombre()} a la recomendación que tenía estos Títulos: Pipo 1"
                ))
            }
        }

        it("El creador agreaga un libro a la recomendación y no se envía ningún mail") {

            usuario1.leerLibro(libro2)
            recomendacion.agregarALibrosDeRecomendacion(usuario1, libro2)

            verify (exactly = 0) {
                mockMailSender.sendMail(any())
            }
        }
    }

    describe("Test de Observer Registro") {

        val observerRegistro = Registro()
        recomendacion.agregarObserver(observerRegistro)


        it("Nadie aportó luego de crear la recomendación, registro vacio") {
            observerRegistro.getRegistro() shouldBe emptyMap()
        }

        it("El creador aportó a la recomendacion un libro, se registra") {
            usuario1.leerLibro(libro2) // necesario para pasar validación
            recomendacion.agregarALibrosDeRecomendacion(usuario1, libro2)
            observerRegistro.getRegistro() shouldBe mutableMapOf(usuario1 to mutableListOf(libro2))
        }
    }

    describe("Test de Observer BannearSpammer") {
        val observerBannearSpammer = BannearSpammer(2)
        recomendacion.agregarObserver(observerBannearSpammer)

        it("Un amigo del creador agregó dos libros a la recomendación") {
            usuario2.leerLibro(libro1)
            usuario2.leerLibro(libro2)
            usuario2.leerLibro(libro3)

            recomendacion.agregarALibrosDeRecomendacion(usuario2,libro2)
            recomendacion.agregarALibrosDeRecomendacion(usuario2,libro3)

            observerBannearSpammer.getRegistro() shouldBe mutableMapOf(usuario2 to 2)
        }

        it("Un amigo del creador que ya agregó 2 libros a la recomendación, no puede agregar un tercero porque es eliminado de los amigos del creador") {
            usuario2.leerLibro(libro1)
            usuario2.leerLibro(libro2)
            usuario2.leerLibro(libro3)
            usuario2.leerLibro(libro4)

            recomendacion.agregarALibrosDeRecomendacion(usuario2,libro2)
            recomendacion.agregarALibrosDeRecomendacion(usuario2,libro3)
            shouldThrow<Businessexception> { recomendacion.agregarALibrosDeRecomendacion(usuario2, libro4) }
            observerBannearSpammer.getRegistro() shouldBe mutableMapOf(usuario2 to 2)
        }

        it("El creador puede agregar mas de dos libros"){

            recomendacion.agregarALibrosDeRecomendacion(usuario1,libro2)
            recomendacion.agregarALibrosDeRecomendacion(usuario1,libro3)
            recomendacion.agregarALibrosDeRecomendacion(usuario1,libro4)

            observerBannearSpammer.getRegistro() shouldBe mutableMapOf(usuario1 to 3)
        }
    }

    describe("Test de Observer ValoracionAutomatica") {
        val observerAutoVal = ValoracionAutomatica()
        recomendacion.agregarObserver(observerAutoVal)

        it("Un usuario que no valoró la recomendación, agrega un libro y se genera una valoración automaticamente") {
            usuario2.leerLibro(libro1)
            usuario2.leerLibro(libro2)

            recomendacion.agregarALibrosDeRecomendacion(usuario2,libro2)

            recomendacion.valoraciones[usuario2]?.valor shouldBe 5
            recomendacion.valoraciones[usuario2]?.comentario shouldBe "Excelente 100% recomendable"
        }

        it("Un usuario que valoró la recomendación, agrega un libro y no se genera una valoración") {
            usuario2.leerLibro(libro1)
            usuario2.leerLibro(libro2)

            usuario2.valorarRecomendacion(recomendacion, 2, "No me gustó")
            recomendacion.agregarALibrosDeRecomendacion(usuario2,libro2)

            recomendacion.valoraciones[usuario2]?.valor shouldNotBe 5
            recomendacion.valoraciones[usuario2]?.comentario shouldNotBe "Excelente 100% recomendable"
        }
    }
})