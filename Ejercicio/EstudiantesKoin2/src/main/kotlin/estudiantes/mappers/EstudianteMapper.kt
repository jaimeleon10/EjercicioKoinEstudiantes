package dev.jaimeleon.estudiantes.mappers

import database.Estudiantes
import dev.jaimeleon.estudiantes.dto.EstudianteDto
import dev.jaimeleon.estudiantes.models.Estudiante

fun Estudiante.toEstudianteDto(): EstudianteDto {
    return EstudianteDto(
        id = this.id,
        nombre = this.nombre,
        calificacion = this.calificacion,
    )
}

fun EstudianteDto.toEstudiante(): Estudiante {
    return Estudiante(
        id = this.id,
        nombre = this.nombre,
        calificacion = this.calificacion,
    )
}

fun Estudiantes.toEstudiante(): Estudiante {
    return Estudiante(
        id = this.id,
        nombre = this.nombre,
        calificacion = this.calificacion.toInt()
    )
}