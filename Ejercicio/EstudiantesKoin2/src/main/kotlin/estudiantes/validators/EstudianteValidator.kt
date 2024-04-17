package dev.jaimeleon.estudiantes.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import dev.jaimeleon.estudiantes.errors.EstudiantesErrors
import dev.jaimeleon.estudiantes.models.Estudiante
import com.github.michaelbull.result.Result


class EstudianteValidator {
    fun validate(estudiante: Estudiante): Result<Estudiante, EstudiantesErrors> {
        return when {
            estudiante.nombre.isBlank() -> Err(EstudiantesErrors.EstudianteNoValido("Nombre no puede estar vacío"))
            estudiante.calificacion <= 0 -> Err(EstudiantesErrors.EstudianteNoValido("Calificacion no puede ser menor o igual a 0"))
            else -> Ok(estudiante)
        }
    }
}