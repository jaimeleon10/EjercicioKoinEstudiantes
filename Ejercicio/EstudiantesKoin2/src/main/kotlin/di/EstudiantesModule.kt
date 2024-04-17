package dev.jaimeleon.di

import dev.jaimeleon.database.service.SqlDeLightManager
import dev.jaimeleon.estudiantes.cache.EstudiantesCache
import dev.jaimeleon.estudiantes.repositories.EstudiantesRepository
import dev.jaimeleon.estudiantes.repositories.EstudiantesRepositoryImpl
import dev.jaimeleon.estudiantes.services.EstudiantesService
import dev.jaimeleon.estudiantes.services.EstudiantesServiceImpl
import dev.jaimeleon.estudiantes.validators.EstudianteValidator
import org.koin.dsl.module

val estudiantesModule = module {

    single<SqlDeLightManager > {
        SqlDeLightManager(
            getProperty("database.url"),
            getProperty("database.inmemory"),
            getProperty("database.init.data")
        )
    }

    single<EstudiantesRepository> { EstudiantesRepositoryImpl(get()) }

    factory<EstudiantesCache> { EstudiantesCache(6) }

    factory<EstudianteValidator> { EstudianteValidator() }

    factory<EstudiantesService> { EstudiantesServiceImpl(get(), get(), get()) }
}