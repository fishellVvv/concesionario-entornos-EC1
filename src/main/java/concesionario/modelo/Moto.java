package concesionario.modelo;

/**
 * Moto del catálogo: marca, modelo, precio y stock disponible.
 * Los campos {@code marca}, {@code modelo} y {@code precio} son inmutables; el stock es mutable.
 * @author Víctor
 * @version 1.0
 */
public class Moto {
    private final String marca;
    private final String modelo;
    private final double precio;
    private int stock;

    /**
     * Crea una moto.
     * @param marca   marca comercial
     * @param modelo  modelo concreto
     * @param precio  precio en euros
     * @param stock   unidades iniciales (si es negativo se normaliza a 0)
     */
    public Moto(String marca, String modelo, double precio, int stock) {
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = Math.max(0, stock);
    }

    // Getters

    /** @return marca de la moto */
    public String getMarca() { return marca; }

    /** @return modelo de la moto */
    public String getModelo() { return modelo; }

    /** @return precio en euros */
    public double getPrecio() { return precio; }

    /** @return stock disponible */
    public int getStock() { return stock; }

    // Ajustes de Stock

    /**
     * Resta unidades del stock.
     * @param unidades unidades a descontar (0 &le; unidades &le; stock)
     * @throws IllegalArgumentException si {@code unidades} es negativo o mayor que el stock disponible
     */
    public void restarStock(int unidades) {
        if (unidades < 0) throw new IllegalArgumentException("unidades negativas");
        if (unidades > stock) throw new IllegalArgumentException("sin stock suficiente");
        stock -= unidades;
    }

    /**
     * Añade unidades al stock.
     * @param unidades unidades a añadir (>= 0)
     * @throws IllegalArgumentException si {@code unidades} es negativo
     */
    public void anadirStock(int unidades) {
        if (unidades < 0) throw new IllegalArgumentException("unidades negativas");
        this.stock += unidades;
    }

    /** @return representación corta: {@code "Marca Modelo (precio €)"} con 2 decimales */
    @Override
    public String toString() { return marca + " " + modelo + " (" + String.format("%.2f", precio) + " €)"; }
}
