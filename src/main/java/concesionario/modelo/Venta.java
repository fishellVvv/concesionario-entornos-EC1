package concesionario.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Venta realizada a un cliente con una o más motos.
 * La fecha se fija en el momento de creación ({@link LocalDate#now()}).
 * <p>
 * La lista interna de motos es la que se usa para calcular el importe:
 * cada elemento representa una unidad vendida.
 * </p>
 * <p>
 * Nota: {@link #getMotos()} devuelve la lista interna (modificable).
 * Cualquier cambio posterior en esa lista afectará al resultado de {@link #getImporte()}.
 * </p>
 * @author Víctor
 * @version 1.0
 */
public class Venta {
    private final Cliente cliente;
    private final List<Moto> motos = new ArrayList<>();
    private final LocalDate fecha;

    /**
     * Crea una venta.
     * @param cliente cliente comprador
     * @param motos   lista de motos vendidas; cada posición equivale a 1 unidad
     *                (si es {@code null} se producirá un {@link NullPointerException} al copiar)
     */
    public Venta(Cliente cliente, List<Moto> motos) {
        this.cliente = cliente;
        this.motos.addAll(motos);
        this.fecha = LocalDate.now();
    }

    // Getters

    /** @return cliente comprador */
    public Cliente getCliente() { return cliente; }

    /**
     * Devuelve la lista interna de motos (modificable).
     * @return lista de motos de la venta
     */
    public List<Moto> getMotos() { return motos; }

    /** @return fecha de la venta (día natural) */
    public LocalDate getFecha() { return fecha; }

    /**
     * Suma los precios de todas las motos de la venta.
     * @return importe total en euros
     */
    public double getImporte() {
        double total = 0.0;
        for (Moto m : motos) total += m.getPrecio();
        return total;
    }

    /** @return descripción legible de la venta */
    @Override
    public String toString() {
        return String.format("%s compró %d moto(s) el %s por %.2f €",
                cliente.getNombre(), motos.size(), fecha, getImporte());
    }
}
