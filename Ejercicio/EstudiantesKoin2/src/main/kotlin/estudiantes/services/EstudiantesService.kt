package dev.jaimeleon.estudiantes.services

import dev.jaimeleon.estudiantes.errors.EstudiantesErrors
import dev.jaimeleon.estudiantes.models.Estudiante
import com.github.michaelbull.result.Result


interface EstudiantesService {
    fun getAll(): Result<List<Estudiante>, EstudiantesErrors>
    fun getById(id: Long): Result<Estudiante, EstudiantesErrors>
    fun save(estudiante: Estudiante): Result<Estudiante, EstudiantesErrors>
    fun update(id: Long, estudiante: Estudiante): Result<Estudiante, EstudiantesErrors>
    fun delete(id: Long): Result<Estudiante, EstudiantesErrors>
}