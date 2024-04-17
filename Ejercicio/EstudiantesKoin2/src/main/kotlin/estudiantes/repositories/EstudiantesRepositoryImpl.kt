package dev.jaimeleon.estudiantes.repositories

import dev.jaimeleon.database.service.SqlDeLightManager
import dev.jaimeleon.estudiantes.models.Estudiante
import org.lighthousegames.logging.logging
import dev.jaimeleon.estudiantes.mappers.toEstudiante

private val logger = logging()

class EstudiantesRepositoryImpl(
    private val dbManager: SqlDeLightManager
) : EstudiantesRepository {

    private val db = dbManager.databaseQueries

    override fun findAll(): List<Estudiante> {
        logger.debug { "Obteniendo todos los estudiantes" }
        return db.selectAllEstudiantes()
            .executeAsList()
            .map { it.toEstudiante() }
    }

    override fun findById(id: Long): Estudiante? {
        logger.debug { "Obteniendo estudiante por id: $id" }
        return db.selectEstudianteById(id)
            .executeAsOneOrNull()
            ?.toEstudiante()
    }

    override fun save(estudiante: Estudiante): Estudiante {
        logger.debug { "Guardando estudiante: $estudiante" }

        db.transaction {
            db.insertEstudiante(
                nombre = estudiante.nombre,
                calificacion = estudiante.calificacion.toLong()
            )
        }
        return db.selectEstudianteLastInserted()
            .executeAsOne()
            .toEstudiante()
    }

    override fun update(id: Long, estudiante: Estudiante): Estudiante? {
        logger.debug { "Actualizando estudiante por id: $id" }
        var result = this.findById(id) ?: return null

        result = result.copy(
            nombre = estudiante.nombre,
            calificacion = estudiante.calificacion,
        )

        db.updateEstudiante(
            nombre = result.nombre,
            calificacion = result.calificacion.toLong(),
            id = estudiante.id,
        )
        return result
    }

    override fun delete(id: Long): Estudiante? {
        logger.debug { "Borrando estudiante por id: $id" }
        val result = this.findById(id) ?: return null
        db.deleteEstudiante(id)
        return result
    }
}