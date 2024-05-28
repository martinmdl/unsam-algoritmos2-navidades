@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate

class ProcesosAdministracionSpec : DescribeSpec ({
    isolationMode = IsolationMode.InstancePerLeaf

    val mockMailSender = mockk<MailSender>(relaxUnitFun = true)

    val adm = Administracion()

    //Usuarios
    val usuario1 = UsuarioBuilder().direccionEmail("usuario1@gmail.com").build()
    val usuario2 = UsuarioBuilder().direccionEmail("usuario2@gmail.com").build()
    val usuario3 = UsuarioBuilder().direccionEmail("usuario3@gmail.com").build()
    val usuario4 = UsuarioBuilder().direccionEmail("usuario4@gmail.com").build()

    //Libros
    val libro1 = LibroBuilder().build()
    val libro2 = LibroBuilder().build()
    val libro3 = LibroBuilder().build()
    val libro4 = LibroBuilder().build()
    val libros = mutableSetOf(libro1, libro2)


    //Recomendaciones
    val recomendacion1 = usuario1.crearRecomendacion(libros, "recomiendo", false)
    val recomendacion2 = usuario2.crearRecomendacion(libros, "no recomiendo", true)


    //CentroDeLectura
    val centro1 = Particular(
        nombreDeCentroDeLectura = "Centro de Lectura Particular",
        direccion = "Casa de Pipo",
        libroAsignadoALeer = LibroBuilder().build(),
        costoDeReserva = 100.0,
        conjuntoDeEncuentros = mutableSetOf(),
        capacidadMaximaFijada = 40,
        porcentajeMinimo = 5.0
    )

    val centro2 = Particular(
        nombreDeCentroDeLectura = "Centro de Lectura Particular",
        direccion = "Casa de Cris",
        libroAsignadoALeer = LibroBuilder().build(),
        costoDeReserva = 100.0,
        conjuntoDeEncuentros = mutableSetOf(),
        capacidadMaximaFijada = 40,
        porcentajeMinimo = 5.0
    )

    val centro3 = Particular(
        nombreDeCentroDeLectura = "Centro de Lectura Particular",
        direccion = "Casa de Valen",
        libroAsignadoALeer = LibroBuilder().build(),
        costoDeReserva = 100.0,
        conjuntoDeEncuentros = mutableSetOf(),
        capacidadMaximaFijada = 40,
        porcentajeMinimo = 5.0
    )

    val centro4 = Particular(
        nombreDeCentroDeLectura = "Centro de Lectura Particular",
        direccion = "Casa de Marto",
        libroAsignadoALeer = LibroBuilder().build(),
        costoDeReserva = 100.0,
        conjuntoDeEncuentros = mutableSetOf(),
        capacidadMaximaFijada = 40,
        porcentajeMinimo = 5.0
    )

    fun crearEncuentroNoVencido(cantidadDeEncuentro: Int, centroAVincular: CentroDeLectura) {
        for (i in 1..cantidadDeEncuentro) {
            val encuentro = Encuentro(LocalDate.now().plusDays(i.toLong()), i, centroAVincular)
            centroAVincular.agregarEncuentro(encuentro)
        }
    }

    fun crearEncuentroVencido(cantidadDeEncuentro: Int, centroAVincular: CentroDeLectura) {
        for (i in 1..cantidadDeEncuentro) {
            val encuentro = Encuentro(LocalDate.now().minusDays(i.toLong()), i, centroAVincular)
            centroAVincular.agregarEncuentro(encuentro)
        }
    }

    //Autores
    val autor1 = AutorBuilder().build()
    val autor2 = AutorBuilder().build()
    val autor3 = AutorBuilder().build()
    val autor4 = AutorBuilder().build()

    val setDeUsuarios = mutableSetOf(usuario1, usuario2, usuario3, usuario4)
    val setDeCentrosDeLectura = mutableSetOf(centro1, centro2, centro3, centro4)
    val setAutores = mutableSetOf(autor1, autor2, autor3, autor4)

    //ADM CARGA DE DATOS
    adm.usuariosRegistrados.addAll(setDeUsuarios)
    adm.autoresRegistrados.addAll(setAutores)
    adm.centrosDeLecturaRegistrados.addAll(setDeCentrosDeLectura)

    describe("Test del proceso de eliminacion de usuarios Inactivos") {

        it("Si se acciona el comando BorrarUsuario, se eliminan los usuarios inactivos") {
            usuario1.agregarAmigo(usuario2)
            adm.run(listOf(BorrarUsuarioInactivo(mockMailSender)))
            adm.usuariosRegistrados shouldBe mutableSetOf(usuario1, usuario2)
        }
    }

    describe("Test del proceso de actualizacion de libros") {
        val serviceParaLibros = mockk<ServiceLibros>()
        val serviceLibro = UpdateLibros(serviceParaLibros)
        val repository = RepositorioLibros(serviceLibro)

        //ACT
        repository.create(libro1)
        repository.create(libro2)
        repository.create(libro3)
        repository.create(libro4)

        //UPDATE
        every { serviceParaLibros.getLibros() } answers { "[{\"id\": 1, \"ediciones\": 2, \"ventasSemanales\": 2000}]" }

        it("Si se acciona el comando ActualizacionDeLibro, se actualizan los libros") {
            adm.run(listOf(ActualizacionDeLibro(mockMailSender, repository)))

            repository.dataMap[1]?.getEdiciones() shouldBe 2
            repository.dataMap[1]?.getVentasSemanales() shouldBe 2000

            repository.dataMap[3]?.getEdiciones() shouldBe 0
            repository.dataMap[3]?.getVentasSemanales() shouldBe 0
        }
    }

    describe("Test del proceso de eliminacion de centros de lectura expirados") {

        it("Si se acciona el comando BorrarCentroDeLecturaExpirados, se eliminan los centros de lectura expirados") {
            crearEncuentroNoVencido(3, centro1)
            crearEncuentroVencido(3, centro2) // expirado
            crearEncuentroNoVencido(3, centro3)
            crearEncuentroVencido(3, centro4) // expirado

            adm.run(listOf(BorrarCentroDeLecturaExpirados(mockMailSender)))
            adm.centrosDeLecturaRegistrados shouldBe mutableSetOf(centro1, centro3)
        }
    }

    describe("Test del proceso de agregar autores") {

        it("Si se acciona el comando AgregarAutores, se agregan los autores") {
            adm.run(listOf(AgregarAutores(mockMailSender, setAutores)))
            adm.autoresRegistrados shouldBe setAutores
        }
    }

    describe("MailSender del Command") {

        it("Si se acciona el comando BorrarUsuario, admin@readapp.com.ar recibe un mail personalizado") {

            val command = BorrarUsuarioInactivo(mockMailSender)

            command.enviarMail(mockMailSender)

            verify (exactly = 1) {
                mockMailSender.sendMail(Mail(
                    from = "System@not.com",
                    to = "admin@readapp.com.ar",
                    subject = "Se ejecuto un proceso",
                    content = "Se realizó el proceso: BorrarUsuarioActivo"
                ))
            }
        }

        it("Si se acciona el comando ActualizacionDeLibro, admin@readapp.com.ar recibe un mail personalizado") {

            val serviceParaLibros = mockk<ServiceLibros>()
            val serviceLibro = UpdateLibros(serviceParaLibros)
            val repository = RepositorioLibros(serviceLibro)
            val command = ActualizacionDeLibro(mockMailSender, repository)

            command.enviarMail(mockMailSender)

            verify (exactly = 1) {
                mockMailSender.sendMail(Mail(
                    from = "System@not.com",
                    to = "admin@readapp.com.ar",
                    subject = "Se ejecuto un proceso",
                    content = "Se realizó el proceso: ActualizacionDeLibro"
                ))
            }
        }

        it("Si se acciona el comando BorrarCentroDeLecturaExpirados, admin@readapp.com.ar recibe un mail personalizado") {

            val command = BorrarCentroDeLecturaExpirados(mockMailSender)

            command.enviarMail(mockMailSender)

            verify (exactly = 1) {
                mockMailSender.sendMail(Mail(
                    from = "System@not.com",
                    to = "admin@readapp.com.ar",
                    subject = "Se ejecuto un proceso",
                    content = "Se realizó el proceso: BorrarCentroDeLecturaExpirados"
                ))
            }
        }

        it("Si se acciona el comando AgregarAutores, admin@readapp.com.ar recibe un mail personalizado") {

            val autores = mutableSetOf(autor1, autor2)
            val command = AgregarAutores(mockMailSender, autores)

            command.enviarMail(mockMailSender)

            verify (exactly = 1) {
                mockMailSender.sendMail(Mail(
                    from = "System@not.com",
                    to = "admin@readapp.com.ar",
                    subject = "Se ejecuto un proceso",
                    content = "Se realizó el proceso: AgregarAutores"
                ))
            }
        }
    }
})
