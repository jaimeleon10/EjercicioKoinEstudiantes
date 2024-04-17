package dev.jaimeleon.database.service

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.DatabaseQueries
import dev.jaimeleon.database.data.initDemoEstudiantes
import org.example.database.Database
import org.lighthousegames.logging.logging

private val logger = logging()

class SqlDeLightManager(
    private val databaseUrl: String,
    private val databaseInMemory: String,
    private val databaseInitData: String,
) {
    val databaseQueries: DatabaseQueries by lazy { initQueries() }

    init {
        logger.debug { "Inicializando el gestor de Bases de Datos con SQLDelight" }
        initialize()
    }

    private fun initQueries(): DatabaseQueries {

        return if (databaseInMemory.toBoolean()) {
            logger.debug { "SqlDeLightClient - InMemory" }
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        } else {
            logger.debug { "SqlDeLightClient - File: ${databaseUrl}" }
            JdbcSqliteDriver(databaseUrl)
        }.let { driver ->
            // Creamos la base de datos
            logger.debug { "Creando Tablas (si es necesario)" }
            Database.Schema.create(driver)
            Database(driver)
        }.databaseQueries

    }

    private fun initialize() {
        if (databaseInitData.toBoolean()) {
            removeAllData()
            initDataExamples()
        }
    }

    private fun initDataExamples() {
        logger.debug { "Iniciando datos de ejemplo" }
        databaseQueries.transaction {
            demoEstudiantes()
        }
    }

    private fun demoEstudiantes() {
        logger.debug { "Datos de ejemplo de Productos" }
        initDemoEstudiantes().forEach {
            databaseQueries.insertEstudiante(
                nombre = it.nombre,
                calificacion = it.calificacion.toLong(),
            )
        }
    }

    // limpiamos las tablas
    private fun removeAllData() {
        logger.debug { "SqlDeLightClient.removeAllData()" }
        databaseQueries.transaction {
            databaseQueries.removeAllEstudiantes()
        }
    }
}