package concesionario.modelo;

/**
 * Cliente del concesionario, identificado por su DNI (inmutable).
 * Contiene nombre, teléfono y correo de contacto.
 * <p>El DNI es único en el sistema y no se puede modificar una vez creado.</p>
 * @author Víctor
 * @version 1.0
 */
public class Cliente {
    private String nombre;
    private final String dni;
    private String telefono;
    private String email;

    /**
     * Crea un cliente.
     * @param nombre   nombre completo
     * @param dni      documento de identidad (único y no modificable)
     * @param telefono teléfono de contacto
     * @param email    correo electrónico
     */
    public Cliente(String nombre, String dni, String telefono, String email){
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters

    /** @return nombre del cliente */
    public String getNombre() { return nombre; }

    /** @return DNI del cliente (inmutable) */
    public String getDni() { return dni; }

    /** @return teléfono del cliente */
    public String getTelefono() { return telefono; }

    /** @return email del cliente */
    public String getEmail() { return email; }

    // Setters

    /** Cambia el nombre del cliente. */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** Actualiza el teléfono de contacto. */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /** Actualiza el correo electrónico. */
    public void setEmail(String email) { this.email = email; }



    /** @return representación corta: "Nombre - DNI" */
    @Override
    public String toString() { return nombre + " - " + dni; }
}
