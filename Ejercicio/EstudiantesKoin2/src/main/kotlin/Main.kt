package dev.jaimeleon

import dev.jaimeleon.di.estudiantesModule
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.GlobalContext.startKoin
import org.koin.fileProperties
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
fun main() {

    startKoin {
        printLogger()
        fileProperties("/config.properties")
        estudiantesModule.verify()
        modules(estudiantesModule)
    }

    val app = EstudiantesApp()
    app.run()
}