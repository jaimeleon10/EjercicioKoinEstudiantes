package dev.jaimeleon.estudiantes.cache

import dev.jaimeleon.cache.CacheImpl
import dev.jaimeleon.estudiantes.models.Estudiante

class EstudiantesCache(size: Int) : CacheImpl<Long, Estudiante>(size)