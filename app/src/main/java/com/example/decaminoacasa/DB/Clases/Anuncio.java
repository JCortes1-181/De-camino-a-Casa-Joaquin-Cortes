package com.example.decaminoacasa.DB.Clases;

import java.io.Serializable;

public class Anuncio implements Serializable {
    private int id;
    private String usuarioId;
    private int mascotaId;
    private String hora;
    private String estado;

    public Anuncio(int id, String usuarioId, int mascotaId, String hora, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.mascotaId = mascotaId;
        this.hora = hora;
        this.estado = estado;
    }

    public Anuncio(String usuarioId, int mascotaId, String hora) {
        this.usuarioId = usuarioId;
        this.mascotaId = mascotaId;
        this.hora = hora;
        this.estado = "activo";
    }

    public int getId() {
        return id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public String getHora() {
        return hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
