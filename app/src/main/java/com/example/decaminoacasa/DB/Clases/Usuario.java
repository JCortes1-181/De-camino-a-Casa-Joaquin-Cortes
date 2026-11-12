package com.example.decaminoacasa.DB.Clases;

/**
 * Damos la información de un usuario dentro de la app.
 */
public class Usuario {
    private int id;
    private String nombre;
    private String telefono;
    private String password;
    private String rol;
    private String estado;

    /**
     * Constructor completo para usuarios recuperados de la base de datos.
     *
     * @param id el id del usuario en el registro.
     * @param nombre el nombre del usuario.
     * @param telefono el teléfono de contacto del usuario.
     * @param password la contraseña de acceso.
     * @param rol el rol que cumple (ej. "user" o administrador).
     * @param estado Estado del registro (para manejo interno en base de datos sin borrado físico).
     */
    public Usuario(int id, String nombre, String telefono, String password, String rol, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
    }

    /**
     * Constructor para un usuario existente con ID, asignando su rol y estado activo por defecto.
     *
     * @param id el id del usuario en el registro.
     * @param nombre el nombre del usuario.
     * @param telefono el teléfono de contacto.
     * @param password la contraseña de acceso.
     * @param rol el rol asignado.
     */
    public Usuario(int id, String nombre, String telefono, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.password = password;
        this.rol = rol;
        this.estado = "activo";
    }

    /**
     * este es el constructor para poder registrar un nuevo usuario desde la pantalla de registro
     * por el momento todos son normales, para admin tendria otro metodo, pero aun no hay implementacion, pero hay un admin por default, que es admin y admin123 de contraseña
     * @param nombre el nombre del nuevo usuario.
     * @param telefono el teléfono de contacto.
     * @param password la contraseña elegida.
     */
    public Usuario(String nombre, String telefono, String password) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.password = password;
        this.rol = "user";
        this.estado = "activo";
    }

    /** @return el id único del usuario. */
    public int getId() {
        return id;
    }

    /** @return el nombre del usuario. */
    public String getNombre() {
        return nombre;
    }

    /** @return el teléfono de contacto registrado. */
    public String getTelefono() {
        return telefono;
    }

    /** @return la contraseña del usuario. */
    public String getPassword() {
        return password;
    }

    /** @return el rol que posee el usuario en el sistema. */
    public String getRol() {
        return rol;
    }

    /** @return el estado actual del usuario (esto es mas que nada para no borrar a los usuario de la base datos) */
    public String getEstado() {
        return estado;
    }

    /** @param nombre modifica el nombre del usuario. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @param telefono modifica el teléfono de contacto. */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /** @param password modifica la contraseña de acceso. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @param rol modifica el rol del usuario en la app. */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /** @param estado modifica el estado del registro. */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
