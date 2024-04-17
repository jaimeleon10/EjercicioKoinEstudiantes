package dev.jaimeleon

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
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
        contPorDefecto.getById(8).onSuccess {
            println(it)
        }.onFailure {
            println("ERROR: ${it.message}")
        }

        println()

        logger.warn { "Guardando nuevo estudiante..." }
        val e1 = Estudiante(id = 11, nombre = "NuevoEstudiante", calificacion = 8)
        contPorDefecto.save(e1)
            .onSuccess { estudiante ->
                println("Estudiante '${estudiante.nombre}' guardado con éxito")
                logger.warn { "Imprimiendo estudiante guardado..." }
                contPorDefecto.getById(estudiante.id).onSuccess { println(it) }.onFailure { println("ERROR: ${it.message}") }
            }
            .onFailure { println("ERROR: ${it.message}") }

        println()

        logger.warn { "Actualizando estudiante con id 3..." }
        val eUpdate = Estudiante(id = 3, nombre = "EstudianteUpdate", calificacion = 1)
        contPorDefecto.update(3, eUpdate).onSuccess { estudiante ->
            logger.warn { "Imprimiendo estudiante actualizado..." }
            contPorDefecto.getById(estudiante.id).onSuccess { println(it) }.onFailure { println("ERROR: ${it.message}") }
        }.onFailure { println("ERROR: ${it.message}") }


        println()

        logger.warn { "Borrando estudiante con id 11..." }
        contPorDefecto.delete(11).onSuccess { estudiante ->
            logger.warn { "Imprimiendo estudiante eliminado..." }
            contPorDefecto.getById(estudiante.id).onSuccess { println(it) }.onFailure { println("ERROR: ${it.message}") }
        }.onFailure { println("ERROR: ${it.message}") }


        println()

        logger.warn { "Imprimiendo todos los estudiantes..." }
        contPorDefecto.getAll().value.forEach { println(it) }
    }
}