package concesionario.modelo;

public class Cliente {
    private String nombre;
    private final String dni;
    private String telefono;
    private String email;

    // Constructor

    public Cliente(String nombre, String dni, String telefono, String email){
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters

    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    // Setters

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() { return nombre + " - " + dni; }
}
