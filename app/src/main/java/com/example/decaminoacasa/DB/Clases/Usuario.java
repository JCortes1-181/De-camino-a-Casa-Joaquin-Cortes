package com.example.decaminoacasa.DB.Clases;

public class Usuario {
    private int id;
    private String nombre;
    private String telefono;
    private String password;
    private String rol;
    private String estado;

    public Usuario(int id, String nombre, String telefono, String password, String rol, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
    }

    public Usuario(int id, String nombre, String telefono, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.password = password;
        this.rol = rol;
        this.estado = "activo";
    }

    public Usuario(String nombre, String telefono, String password) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.password = password;
        this.rol = "user";
        this.estado = "activo";
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
