package concesionario.servicio;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

import concesionario.modelo.Cliente;
import concesionario.modelo.Moto;
import concesionario.modelo.Venta;

public class Datos {
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Moto> motos = new ArrayList<>();
    private final List<Venta> ventas = new ArrayList<>();

    // Constructor

    public Datos() {
        // cargarEjemplo();    // "descomentar" para usar los datos de ejemplo
    }

    // Getters

    public List<Cliente> getClientes() { return clientes; }
    public List<Moto> getMotos() { return motos; }
    public List<Venta> getVentas() { return ventas; }

    // Altas

    public boolean addCliente(Cliente c) {    // validamos DNI
        if (c == null) return false;
        for (Cliente x : clientes) {
            if (x.getDni().equalsIgnoreCase(c.getDni())) return false;
        }
        clientes.add(c);
        return true;
    }
    public void addMoto(Moto m) { if (m != null) motos.add(m); }
    public void addVenta(Venta v) { if (v != null) ventas.add(v); }

    // Bajas

    public boolean removeClientePorIndice(int idx) {
        if (idx < 0 || idx >= clientes.size()) return false;
        clientes.remove(idx);
        return true;
    }

    // Busqueda

    public Cliente buscarClientePorDni(String dni) {
        for (Cliente c : clientes) if (c.getDni().equalsIgnoreCase(dni)) return c;
        return null;
    }

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

    public List<Venta> ventasPorDni(String dni) {
        List<Venta> res = new ArrayList<>();
        for (Venta v : ventas) {
            if (v.getCliente().getDni().equalsIgnoreCase(dni)) {
                res.add(v);
            }
        }
        return res;
    }

    public double totalVentas() {
        double total = 0.0;
        for (Venta v : ventas) total += v.getImporte();
        return total;
    }

    // Stock

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

    public boolean reponerStockPorIndice(int idx, int unidades) {
        if (idx < 0 || idx >= motos.size()) return false;
        if (unidades <= 0) return false;
        motos.get(idx).anadirStock(unidades);
        return true;
    }

    // Ordenar

    public List<Cliente> clientesOrdenadosPorNombre() {
        List<Cliente> copia = new ArrayList<>(clientes);
        copia.sort(Comparator.comparing(c -> c.getNombre().toLowerCase()));
        return copia;
    }

    public List<Moto> motosOrdenadasPorMarca() {
        List<Moto> copia = new ArrayList<>(motos);
        copia.sort(Comparator.comparing((Moto m) -> m.getMarca().toLowerCase())
                .thenComparing(m -> m.getModelo().toLowerCase()));
        return copia;
    }

    // Datos de ejemplo

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
