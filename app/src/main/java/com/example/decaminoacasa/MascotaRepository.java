package com.example.decaminoacasa;

import java.util.ArrayList;
import java.util.List;

public class MascotaRepository {
    private static final List<Mascota> listaMascotas = new ArrayList<>();

    public static void agregarMascota(Mascota mascota) {
        listaMascotas.add(mascota);
    }

    public static List<Mascota> getListaMascotas() {
        return listaMascotas;
    }
}