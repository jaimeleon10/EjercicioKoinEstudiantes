package dev.jaimeleon

import dev.jaimeleon.estudiantes.models.Estudiante
import dev.jaimeleon.estudiantes.services.EstudiantesService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class EstudiantesApp : KoinComponent {
    private val contPorDefecto: EstudiantesService by inject()

    fun run() {
        logger.warn { "Imprimiendo todos los estudiantes..." }
        contPorDefecto.getAll().value.forEach { println(it) }

        println()

        logger.warn { "Imprimiendo estudiante con id 8..." }
        println(contPorDefecto.getById(8).value)

        println()

        logger.warn { "Guardando nuevo estudiante..." }
        val e1 = Estudiante(id = 11, nombre = "NuevoEstudiante", calificacion = 8)
        contPorDefecto.save(e1)
        logger.warn { "Imprimiendo estudiante guardado..." }
        println(contPorDefecto.getById(11))

        println()

        logger.warn { "Actualizando estudiante con id 3..." }
        val eUpdate = Estudiante(id = 3, nombre = "EstudianteUpdate", calificacion = 1)
        contPorDefecto.update(3, eUpdate)
        logger.warn { "Imprimiendo estudiante actualizado..." }
        println(contPorDefecto.getById(3))

        println()

        logger.warn { "Borrando estudiante con id 11..." }
        contPorDefecto.delete(11)
        logger.warn { "Imprimiendo estudiante eliminado..." }
        println(contPorDefecto.getById(11))

        println()

        logger.warn { "Imprimiendo todos los estudiantes..." }
        contPorDefecto.getAll().value.forEach { println(it) }
    }
}