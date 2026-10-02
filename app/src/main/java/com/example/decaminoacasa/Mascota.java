package com.example.decaminoacasa;

import java.io.Serializable;

public class Mascota implements Serializable {
    private String nombre;
    private String color;
    private String informacion;
    private String ubicacionGps;

    public Mascota(String nombre, String color, String informacion, String ubicacionGps) {
        this.nombre = nombre;
        this.color = color;
        this.informacion = informacion;
        this.ubicacionGps = ubicacionGps;
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
}