package dev.jaimeleon.estudiantes.errors

sealed class EstudiantesErrors(val message: String) {
    class EstudianteNoEncontrado(message: String) : EstudiantesErrors(message)
    class EstudianteNoValido(message: String) : EstudiantesErrors(message)
    class EstudianteNoActualizado(message: String) : EstudiantesErrors(message)
    class EstudianteNoEliminado(message: String) : EstudiantesErrors(message)
    class EstudianteStorageError(message: String) : EstudiantesErrors(message)
}