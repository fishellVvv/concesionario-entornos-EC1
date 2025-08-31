package concesionario.servicio;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

import concesionario.modelo.Cliente;
import concesionario.modelo.Moto;
import concesionario.modelo.Venta;

/**
 * Servicio en memoria que gestiona las colecciones de clientes, motos y ventas.
 * <p>No hay persistencia: los datos se pierden al cerrar la aplicación.</p>
 * <p><b>Importante:</b> los getters de listas devuelven <i>listas vivas</i>;
 * modificarlas afecta al estado interno del servicio.</p>
 * @author Víctor
 * @version 1.0
 */
public class Datos {
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Moto> motos = new ArrayList<>();
    private final List<Venta> ventas = new ArrayList<>();

    // Constructor

    /**
     * Crea el servicio con las listas vacías.
     * De forma opcional se puede llamar a {@code cargarEjemplo()} (comentado).
     */
    public Datos() {
        // cargarEjemplo();    // "descomentar" para usar los datos de ejemplo
    }

    // Getters

    /** @return lista viva de clientes (modificarla afecta al servicio) */
    public List<Cliente> getClientes() { return clientes; }

    /** @return lista viva de motos (modificarla afecta al servicio) */
    public List<Moto> getMotos() { return motos; }

    /** @return lista viva de ventas (modificarla afecta al servicio) */
    public List<Venta> getVentas() { return ventas; }

    // Altas

    /**
     * Añade un cliente si el DNI no existe (comparación sin distinguir mayúsculas/minúsculas).
     * @param c cliente a añadir
     * @return {@code true} si se añadió; {@code false} si ya existía ese DNI o {@code c} es null
     */
    public boolean addCliente(Cliente c) {    // validamos DNI
        if (c == null) return false;
        for (Cliente x : clientes) {
            if (x.getDni().equalsIgnoreCase(c.getDni())) return false;
        }
        clientes.add(c);
        return true;
    }

    /**
     * Añade una moto al catálogo (si no es null).
     * @param m moto a añadir
     */
    public void addMoto(Moto m) { if (m != null) motos.add(m); }

    /**
     * Registra una venta (si no es null).
     * @param v venta a registrar
     */
    public void addVenta(Venta v) { if (v != null) ventas.add(v); }

    // Bajas

    /**
     * Elimina el cliente en el índice indicado.
     * @param idx índice basado en 0
     * @return {@code true} si se eliminó; {@code false} si el índice no es válido
     */
    public boolean removeClientePorIndice(int idx) {
        if (idx < 0 || idx >= clientes.size()) return false;
        clientes.remove(idx);
        return true;
    }

    // Búsqueda

    /**
     * Busca un cliente por DNI exacto (ignorando mayúsculas/minúsculas).
     * @param dni DNI a buscar
     * @return cliente encontrado o {@code null} si no existe
     */
    public Cliente buscarClientePorDni(String dni) {
        for (Cliente c : clientes) if (c.getDni().equalsIgnoreCase(dni)) return c;
        return null;
    }

    /**
     * Busca motos cuyo modelo o marca contenga el texto (ignorando mayúsculas/minúsculas).
     * @param q texto a buscar
     * @return lista (puede estar vacía) de coincidencias
     */
    public List<Moto> buscarMotosPorTexto(String q) {
        List<Moto> res = new ArrayList<>();
        String s = q.toLowerCase();
        for (Moto m : motos) {
            if (m.getMarca().toLowerCase().contains(s) || m.getModelo().toLowerCase().contains(s)) {
                res.add(m);
            }
        }
        return res;
    }

    /**
     * Devuelve las ventas realizadas al cliente con el DNI dado.
     * @param dni DNI del cliente
     * @return lista de ventas (puede estar vacía)
     */
    public List<Venta> ventasPorDni(String dni) {
        List<Venta> res = new ArrayList<>();
        for (Venta v : ventas) {
            if (v.getCliente().getDni().equalsIgnoreCase(dni)) {
                res.add(v);
            }
        }
        return res;
    }

    /**
     * Suma el importe de todas las ventas registradas.
     * @return total acumulado en euros
     */
    public double totalVentas() {
        double total = 0.0;
        for (Venta v : ventas) total += v.getImporte();
        return total;
    }

    // Stock

    /**
     * Intenta descontar stock para todas las motos de una selección
     * (cada aparición en la lista representa 1 unidad). Operación atómica:
     * si falta stock en alguna, no descuenta ninguna.
     * @param seleccion lista de motos a descontar (con repetidos si se venden varias unidades)
     * @return {@code true} si se descontó; {@code false} si faltaba stock
     */
    public boolean descontarStock(java.util.List<Moto> seleccion) {
        // contar cuántas unidades de cada moto se piden
        java.util.Map<Moto, Integer> cnt = new java.util.HashMap<>();
        for (Moto m : seleccion) cnt.merge(m, 1, Integer::sum);

        // comprobar disponibilidad
        for (var e : cnt.entrySet()) {
            Moto m = e.getKey();
            int pedidas = e.getValue();
            if (m.getStock() < pedidas) return false;
        }

        // restar el Stock
        for (var e : cnt.entrySet()) {
            e.getKey().restarStock(e.getValue());
        }
        return true;
    }

    /**
     * Añade unidades al stock de la moto en el índice indicado.
     * @param idx índice de la moto en la lista (basado en 0)
     * @param unidades unidades a añadir (debe ser &gt; 0)
     * @return {@code true} si se repuso; {@code false} si el índice es inválido o {@code unidades} &le; 0
     */
    public boolean reponerStockPorIndice(int idx, int unidades) {
        if (idx < 0 || idx >= motos.size()) return false;
        if (unidades <= 0) return false;
        motos.get(idx).anadirStock(unidades);
        return true;
    }

    // Ordenar

    /**
     * Devuelve una copia de la lista de clientes ordenada por nombre (ascendente, sin distinción de mayúsculas).
     * @return nueva lista ordenada por nombre
     */
    public List<Cliente> clientesOrdenadosPorNombre() {
        List<Cliente> copia = new ArrayList<>(clientes);
        copia.sort(Comparator.comparing(c -> c.getNombre().toLowerCase()));
        return copia;
    }

    /**
     * Devuelve una copia de la lista de motos ordenada por marca y luego modelo (ascendente).
     * @return nueva lista ordenada por marca y modelo
     */
    public List<Moto> motosOrdenadasPorMarca() {
        List<Moto> copia = new ArrayList<>(motos);
        copia.sort(Comparator.comparing((Moto m) -> m.getMarca().toLowerCase())
                .thenComparing(m -> m.getModelo().toLowerCase()));
        return copia;
    }

    // Datos de ejemplo

    /**
     * Carga datos de ejemplo para pruebas manuales.
     * <p><b>Uso:</b> descomentar la llamada en el constructor.</p>
     */
    private void cargarEjemplo() {
        clientes.add(new Cliente("Ana López", "12345678A", "600111222", "ana@correofalso.com"));
        clientes.add(new Cliente("Carlos Ruiz", "98765432B", "600333444", "carlos@correofalso.com"));
        clientes.add(new Cliente("María Torres", "11223344C", "600555666", "maria@correofalso.com"));
        clientes.add(new Cliente("Javier Gómez", "55667788D", "600777888", "javier@correofalso.com"));
        clientes.add(new Cliente("Lucía Martín", "33445566E", "600999000", "lucia@correofalso.com"));

        motos.add(new Moto("Honda", "CL500", 6790.00, 3));
        motos.add(new Moto("Yamaha", "XSR700 XTribute", 8799.00, 2));
        motos.add(new Moto("Ducati", "Scrambler Icon", 9990.00, 2));
        motos.add(new Moto("Ducati", "Scrambler Full Throttle", 11990.00, 1));
        motos.add(new Moto("Triumph", "Scrambler 900", 10995.00, 2));
        motos.add(new Moto("Triumph", "Scrambler 1200 XC", 14995.00, 1));
        motos.add(new Moto("Fantic", "Caballero 500 Scrambler", 7390.00, 3));
        motos.add(new Moto("Benelli", "Leoncino 500 Trail", 6190.00, 4));
        motos.add(new Moto("Husqvarna", "Svartpilen 401", 5899.00, 5));
        motos.add(new Moto("Royal Enfield", "Scram 411", 5490.00, 6));
    }
}
