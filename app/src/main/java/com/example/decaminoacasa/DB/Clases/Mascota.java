package com.example.decaminoacasa.DB.Clases;

import java.io.Serializable;

public class Mascota implements Serializable {
    private int id;
    private String nombre;
    private String color;
    private String informacion;
    private String ubicacionGps;
    private String usuarioCreador;
    private String estado;

    public Mascota(int id, String nombre, String color, String informacion, String ubicacionGps, String usuarioCreador, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
        this.informacion = informacion;
        this.ubicacionGps = ubicacionGps;
        this.usuarioCreador = usuarioCreador;
        this.estado = estado;
    }

    public Mascota(String nombre, String color, String informacion, String ubicacionGps, String usuarioCreador) {
        this.nombre = nombre;
        this.color = color;
        this.informacion = informacion;
        this.ubicacionGps = ubicacionGps;
        this.usuarioCreador = usuarioCreador;
        this.estado = "activo";
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    public String getInformacion() {
        return informacion;
    }

    public String getUbicacionGps() {
        return ubicacionGps;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public String getEstado() {
        return estado;
    }
}
