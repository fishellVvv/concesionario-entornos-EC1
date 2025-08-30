package concesionario.modelo;

public class Moto {
    private final String marca;
    private final String modelo;
    private final double precio;
    private int stock;

    // Constructor

    public Moto(String marca, String modelo, double precio, int stock) {
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = Math.max(0, stock);
    }

    // Getters

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    // Ajustes de Stock

    public void restarStock(int unidades) {
        if (unidades < 0) throw new IllegalArgumentException("unidades negativas");
        if (unidades > stock) throw new IllegalArgumentException("sin stock suficiente");
        stock -= unidades;
    }

    public void anadirStock(int unidades) {
        if (unidades < 0) throw new IllegalArgumentException("unidades negativas");
        this.stock += unidades;
    }

    @Override
    public String toString() { return marca + " " + modelo + " (" + String.format("%.2f", precio) + " €)"; }
}
