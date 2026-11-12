package com.example.decaminoacasa;

import com.example.decaminoacasa.DB.Clases.Mascota;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio temporal para gestionar y almacenar la lista de mascotas
 * utilizadas en la aplicación.
 */
public class MascotaRepository {
    private static final List<Mascota> listaMascotas = new ArrayList<>();

    /**
     * Agrega un nuevo objeto de tipo {@link Mascota} al repositorio en memoria.
     *
     * @param mascota Objeto {@link Mascota} que se desea añadir a la lista.
     */
    public static void agregarMascota(Mascota mascota) {
        listaMascotas.add(mascota);
    }

    /**
     * Obtiene la lista completa de mascotas perdidas almacenadas en el repositorio.
     *
     * @return Una lista ({@link List}) de mascotas {@link Mascota}.
     */
    public static List<Mascota> getListaMascotas() {
        return listaMascotas;
    }
}
