package dev.jaimeleon.estudiantes.repositories

import dev.jaimeleon.estudiantes.models.Estudiante

interface EstudiantesRepository {
    fun findAll(): List<Estudiante>
    fun findById(id: Long): Estudiante?
    fun save(estudiante: Estudiante): Estudiante
    fun update(id: Long, estudiante: Estudiante): Estudiante?
    fun delete(id: Long): Estudiante?
}