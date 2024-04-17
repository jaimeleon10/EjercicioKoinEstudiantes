package dev.jaimeleon.estudiantes.services

import com.github.michaelbull.result.*
import dev.jaimeleon.estudiantes.cache.EstudiantesCache
import dev.jaimeleon.estudiantes.repositories.EstudiantesRepository
import dev.jaimeleon.estudiantes.validators.EstudianteValidator
import org.lighthousegames.logging.logging
import dev.jaimeleon.estudiantes.errors.EstudiantesErrors
import dev.jaimeleon.estudiantes.models.Estudiante

private val logger = logging()

class EstudiantesServiceImpl(
    private val estudiantesRepository: EstudiantesRepository,
    private val estudianteValidator: EstudianteValidator,
    private val estudiantesCache: EstudiantesCache,
) : EstudiantesService {
    override fun getAll(): Result<List<Estudiante>, EstudiantesErrors> {
        logger.debug { "Obteniendo todos los estudiantes" }
        return Ok(estudiantesRepository.findAll())
    }

    override fun getById(id: Long): Result<Estudiante, EstudiantesErrors> {
        logger.debug { "Obteniendo estudiante por id: $id" }

        return estudiantesCache.get(id).mapBoth(
            success = {
                logger.debug { "Estudiante encontrado en cache" }
                Ok(it)
            },
            failure = {
                logger.debug { "Estudiante no encontrado en cache" }
                estudiantesRepository.findById(id)
                    ?.let { Ok(it) }
                    ?: Err(EstudiantesErrors.EstudianteNoEncontrado("Estudiante no encontrado con id: $id"))
            }
        )
    }

    override fun save(estudiante: Estudiante): Result<Estudiante, EstudiantesErrors> {
        logger.debug { "Guardando estudiante: $estudiante" }
        return estudianteValidator.validate(estudiante).andThen {
            Ok(estudiantesRepository.save(it))
        }.andThen { e ->
            logger.debug { "Guardando en cache" }
            estudiantesCache.put(e.id, e)
        }
    }

    override fun update(id: Long, estudiante: Estudiante): Result<Estudiante, EstudiantesErrors> {
        logger.debug { "Actualizando estudiante por id: $id" }
        return estudianteValidator.validate(estudiante).andThen { e ->
            estudiantesRepository.update(id, e)
                ?.let { Ok(it) }
                ?: Err(EstudiantesErrors.EstudianteNoActualizado("Estudiante no actualizado con id: $id"))
        }.andThen {
            estudiantesCache.put(id, it)
        }
    }

    override fun delete(id: Long): Result<Estudiante, EstudiantesErrors> {
        logger.debug { "Borrando estudiante por id: $id" }
        return estudiantesRepository.delete(id)
            ?.let {
                estudiantesCache.remove(id)
                Ok(it)
            }
            ?: Err(EstudiantesErrors.EstudianteNoEliminado("Estudiante no eliminado con id: $id"))
    }

}