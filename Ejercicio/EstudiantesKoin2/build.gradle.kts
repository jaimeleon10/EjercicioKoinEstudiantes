plugins {
    kotlin("jvm") version "1.9.22"
    // Plugin para la generación de código SQLDelight
    id("app.cash.sqldelight") version "2.0.2"
    application
    id("org.jetbrains.dokka") version "1.9.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

dependencies {
    // Default
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    //Mockk
    testImplementation("io.mockk:mockk:1.13.10")

    // SQLDelight para SQLite
    implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
    //Mordant
    implementation("com.github.ajalt.mordant:mordant:2.3.0")
    implementation("net.java.dev.jna:jna:5.13.0")
    //Logger
    implementation("org.lighthousegames:logging:1.3.0")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    //Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    //JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    //BBDD
    implementation("org.xerial:sqlite-jdbc:3.45.2.0")
    //ScriptRunner
    implementation("org.mybatis:mybatis:3.5.13")
    //Koin
    implementation(platform("io.insert-koin:koin-bom:3.5.3"))
    implementation("io.insert-koin:koin-core") // Core
    implementation("io.insert-koin:koin-test") // Para test y usar checkModules
    // Result ROP
    implementation("com.michael-bull.kotlin-result:kotlin-result:2.0.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

// Configuración del plugin de SqlDeLight
sqldelight {
    databases {
        // Nombre de la base de datos
        create("Database") {
            // Paquete donde se generan las clases
            packageName.set("org.example.database")
        }
    }
}