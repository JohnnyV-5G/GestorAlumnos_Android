package com.john.gestoralumnos.student;

/**
 * <h1>Gestor Alumnos</h1>
 * <h2>StudentItem</h2>
 * <p>La comunicacion de los datos del almuno. Getters and setters del alumno Student</p>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0
 */
public class StudentItem {

    private final int id;
    private final String nombre;
    private final String apellido;
    private final String email;
    private final byte[] foto;


    public StudentItem(int id, String nombre, String apellido, String email, byte[] foto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.foto = foto;
    }

    @Override
    public String toString() {
        return getId() + ": " + getNombre() + " " + getApellido();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getFoto() {
        return foto;
    }
}
