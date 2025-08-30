package concesionario.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private final Cliente cliente;
    private final List<Moto> motos = new ArrayList<>();
    private final LocalDate fecha;

    // Constructor

    public Venta(Cliente cliente, List<Moto> motos) {
        this.cliente = cliente;
        this.motos.addAll(motos);
        this.fecha = LocalDate.now();
    }

    // Getters

    public Cliente getCliente() { return cliente; }
    public List<Moto> getMotos() { return motos; }
    public LocalDate getFecha() { return fecha; }

    public double getImporte() {
        double total = 0.0;
        for (Moto m : motos) total += m.getPrecio();
        return total;
    }

    @Override
    public String toString() {
        return String.format("%s compró %d moto(s) el %s por %.2f €",
                cliente.getNombre(), motos.size(), fecha, getImporte());
    }
}
