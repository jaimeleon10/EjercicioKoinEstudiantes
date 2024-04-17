package dev.jaimeleon.estudiantes.models

data class Estudiante(
    val id: Long = -1,
    val nombre: String,
    val calificacion: Int
) {
    override fun toString(): String {
        return "Estudiante(ID: $id, Nombre: $nombre, Calificacion: $calificacion)"
    }
}