package com.example.decaminoacasa.DB.Clases;

import java.io.Serializable;

/**
 * Damos la informacio de la mascota perdida en la app.
 */
public class Mascota implements Serializable {
    private int id;
    private String nombre;
    private String color;
    private String informacion;
    private String ubicacionGps;
    private String usuarioCreador;

    /**
     * Constructor completo para mascotas recuperadas de la base de datos.
     *
     * @param id el id del registro.
     * @param nombre el nombre de la mascota.
     * @param color el color de la mascota.
     * @param informacion los Detalles adicionales.
     * @param ubicacionGps las coordenadas de gps del reporte.
     * @param usuarioCreador el usuario que registró la mascota.
     */
    public Mascota(int id, String nombre, String color, String informacion, String ubicacionGps, String usuarioCreador) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
        this.informacion = informacion;
        this.ubicacionGps = ubicacionGps;
        this.usuarioCreador = usuarioCreador;
    }

    /**
     * este es el constructor para registrar una nueva mascota o crear el anuncio de una mascota nueva
     *
     * @param nombre el nombre de la mascota.
     * @param color el color de la mascota.
     * @param informacion los detalles adicionales.
     * @param ubicacionGps las coordenadas de gps del reporte.
     * @param usuarioCreador el usuario que registró la mascota.
     */
    public Mascota(String nombre, String color, String informacion, String ubicacionGps, String usuarioCreador) {
        this.nombre = nombre;
        this.color = color;
        this.informacion = informacion;
        this.ubicacionGps = ubicacionGps;
        this.usuarioCreador = usuarioCreador;
    }

    /** @return id único que tiene la mascota. */
    public int getId() {
        return id;
    }

    /** @return el nombre de la mascota. */
    public String getNombre() {
        return nombre;
    }

    /** @return el color de la mascota. */
    public String getColor() {
        return color;
    }

    /** @return nos da la información adicional que pusimos en el reporte. */
    public String getInformacion() {
        return informacion;
    }

    /** @return nos da las coordenadas  del gps registradas. */
    public String getUbicacionGps() {
        return ubicacionGps;
    }

    /** @return  nos dice el usuario que creó el reporte. */
    public String getUsuarioCreador() {
        return usuarioCreador;
    }
}
