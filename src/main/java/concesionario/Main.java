package concesionario;

import java.util.*;

import concesionario.modelo.Cliente;
import concesionario.modelo.Moto;
import concesionario.modelo.Venta;
import concesionario.servicio.Datos;

/**
 * Aplicación de consola para la gestión del concesionario.
 * <p>Se encarga de mostrar menús, leer la entrada de usuario y delegar las
 * operaciones en el servicio {@link concesionario.servicio.Datos}.</p>
 * <p>No contiene lógica de negocio: solo orquesta E/S y llamadas a servicio.</p>
 * @author Víctor
 * @version 1.0
 */
public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Datos datos = new Datos();

    /**
     * Punto de entrada del programa. Ejecuta el bucle del menú principal
     * hasta que el usuario elige la opción de salida.
     * @param args argumentos de línea de comandos (no usados)
     */
    public static void main(String[] args) {
        int opcion;
        do {
            imprimirMenuPrincipal();
            opcion = leerEntero("\nElige una opción: ");
            switch (opcion) {
                case 1 -> menuClientes();
                case 2 -> menuMotos();
                case 3 -> menuVentas();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
            System.out.println();
        } while (opcion != 0);
    }

    // --- Menús ---

    private static void imprimirMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Gestión de Clientes");
        System.out.println("2. Gestión de Motos");
        System.out.println("3. Gestión de Ventas");
        System.out.println("0. Salir");
    }

    private static void imprimirMenuClientes() {
        System.out.println("\n=== GESTIÓN DE CLIENTES ===");
        System.out.println("1. Alta de Cliente");
        System.out.println("2. Baja de Cliente");
        System.out.println("3. Modificar Cliente");
        System.out.println("4. Búsqueda por DNI");
        System.out.println("5. Listado de Clientes");
        System.out.println("6. Listado de Clientes (ordenado por nombre)");
        System.out.println("0. Volver al Menú Principal");
    }

    private static void imprimirMenuMotos() {
        System.out.println("\n=== GESTIÓN DE MOTOS ===");
        System.out.println("1. Alta de Moto");
        System.out.println("2. Búsqueda por Marca/modelo");
        System.out.println("3. Listado de Motos");
        System.out.println("4. Listado de Motos (ordenado por marca)");
        System.out.println("5. Reponer Stock");
        System.out.println("0. Volver al Menú Principal");
    }

    private static void imprimirMenuVentas() {
        System.out.println("\n=== GESTIÓN DE VENTAS ===");
        System.out.println("1. Registrar Venta");
        System.out.println("2. Listado de Ventas");
        System.out.println("3. Búsqueda por Cliente");
        System.out.println("4. Mostrar Totales Ventas");
        System.out.println("0. Volver al Menú Principal");
    }

    private static void menuClientes() {
        while (true) {
            imprimirMenuClientes();
            int opcion = leerEntero("\nElige una opción: ");
            switch (opcion) {
                case 1 -> altaCliente();
                case 2 -> bajaCliente();
                case 3 -> modificarCliente();
                case 4 -> buscarCliente();
                case 5 -> listarClientes();
                case 6 -> listarClientesOrdenados();
                case 0 -> { return; }
                default -> System.out.println("Opción no válida.");
            }
            System.out.println();
        }
    }

    private static void menuMotos() {
        while (true) {
            imprimirMenuMotos();
            int opcion = leerEntero("\nElige una opción: ");
            switch (opcion) {
                case 1 -> altaMoto();
                case 2 -> buscarMoto();
                case 3 -> listarMotos();
                case 4 -> listarMotosOrdenadas();
                case 5 -> reponerStock();
                case 0 -> { return; }
                default -> System.out.println("Opción no válida.");
            }
            System.out.println();
        }
    }

    private static void menuVentas() {
        while (true) {
            imprimirMenuVentas();
            int opcion = leerEntero("\nElige una opción: ");
            switch (opcion) {
                case 1 -> registrarVenta();
                case 2 -> listarVentas();
                case 3 -> buscarVentas();
                case 4 -> mostrarTotalesVentas();
                case 0 -> { return; }
                default -> System.out.println("Opción no válida.");
            }
            System.out.println();
        }
    }

    // --- Listados ---

    private static void listarClientes() {
        List<Cliente> lista = datos.getClientes();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay clientes.");
            esperarEnter();
            return;
        }
        System.out.println("\nClientes:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ") " + lista.get(i));
        }
    }

    private static void listarMotos() {
        List<Moto> lista = datos.getMotos();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay motos.");
            esperarEnter();
            return;
        }
        System.out.println("\nMotos:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ") " + vistaMotoConStock(lista.get(i)));
        }
    }

    private static void listarVentas() {
        List<Venta> lv = datos.getVentas();
        if (lv.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            esperarEnter();
            return;
        }
        System.out.println("\nVentas:");
        for (int i = 0; i < lv.size(); i++) {
            System.out.println((i + 1) + ") " + lv.get(i));
        }
        esperarEnter();
    }

    // --- Altas ---

    private static void altaCliente() {
        System.out.println("\n=== ALTA CLIENTE ===");
        String nombre   = leerTextoNoVacio("Nombre: ");
        String dni      = leerTextoNoVacio("DNI: ");
        String telefono = leerTextoNoVacio("Teléfono: ");
        String email    = leerTextoNoVacio("Email: ");

        var nuevo = new Cliente(nombre, dni, telefono, email);
        boolean ok = datos.addCliente(nuevo);
        if (ok) {
            System.out.println("Cliente añadido correctamente.");
            esperarEnter();
        } else {
            System.out.println("Ya existe un cliente con ese DNI.");
            esperarEnter();
        }
    }

    private static void altaMoto() {
        System.out.println("\n=== ALTA MOTO ===");
        String marca  = leerTextoNoVacio("Marca: ");
        String modelo = leerTextoNoVacio("Modelo: ");
        double precio = leerPrecio();
        int stock = leerStockInicial();

        datos.addMoto(new Moto(marca, modelo, precio, stock));
        System.out.println("Moto añadida correctamente.");
        esperarEnter();
    }

    // --- Bajas ---

    private static void bajaCliente() {
        var lista = datos.getClientes();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes.");
            esperarEnter();
            return;
        }

        listarClientes();
        int idx = leerIndiceValido("Elige cliente a eliminar (1-" + lista.size() + "): ", lista.size());
        Cliente c = lista.get(idx);

        if (!datos.ventasPorDni(c.getDni()).isEmpty()) {
            System.out.println("No se puede borrar: el cliente tiene ventas registradas.");
            esperarEnter();
            return;
        }

        String conf = leerTextoNoVacio("Confirmar borrado de '" + c + "' (s/n): ");
        if (conf.equalsIgnoreCase("s")) {
            boolean ok = datos.removeClientePorIndice(idx);
            if (ok) {
                System.out.println("Cliente eliminado.");
                esperarEnter();
            } else {
                System.out.println("No se pudo eliminar.");
                esperarEnter();
            }
        } else {
            System.out.println("Operación cancelada.");
            esperarEnter();
        }
    }

    // --- Modificación ---

    private static void modificarCliente() {
        var lista = datos.getClientes();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes.");
            esperarEnter();
            return;
        }

        listarClientes();
        int idx = leerIndiceValido("Elige cliente a modificar (1-" + lista.size() + "): ", lista.size());
        Cliente c = lista.get(idx);

        System.out.println("Pulsa Enter para mantener el valor actual.");
        String nuevoNombre = leerTextoOpcional("Nombre (" + c.getNombre() + "): ");
        String nuevoTel    = leerTextoOpcional("Teléfono (" + c.getTelefono() + "): ");
        String nuevoEmail  = leerTextoOpcional("Email (" + c.getEmail() + "): ");

        if (!nuevoNombre.isEmpty()) c.setNombre(nuevoNombre);
        if (!nuevoTel.isEmpty())    c.setTelefono(nuevoTel);
        if (!nuevoEmail.isEmpty())  c.setEmail(nuevoEmail);

        System.out.println("Cliente actualizado.");
        esperarEnter();
    }

    // --- Búsquedas ---

    private static void buscarCliente() {
        String dni = leerTextoNoVacio("DNI a buscar: ");
        Cliente c = datos.buscarClientePorDni(dni);
        if (c == null) {
            System.out.println("No existe cliente con ese DNI.");
            esperarEnter();
        } else {
            System.out.println("Encontrado: " + c);
            esperarEnter();
        }
    }

    private static void buscarMoto() {
        String q = leerTextoNoVacio("Buscar por marca/modelo: ");
        List<Moto> res = datos.buscarMotosPorTexto(q);
        if (res.isEmpty()) {
            System.out.println("No hay coincidencias.");
            esperarEnter();
        } else {
            System.out.println("\nResultados:");
            for (int i = 0; i < res.size(); i++) {
                System.out.println((i + 1) + ") " + vistaMotoConStock(res.get(i)));
            }
            esperarEnter();
        }
    }

    private static void buscarVentas() {
        String dni = leerTextoNoVacio("DNI del cliente: ");
        List<Venta> res = datos.ventasPorDni(dni);
        if (res.isEmpty()) {
            System.out.println("No hay ventas para ese cliente.");
            esperarEnter();
            return;
        }
        double subtotal = 0.0;
        System.out.println("\nVentas del cliente " + dni + ":");
        for (int i = 0; i < res.size(); i++) {
            Venta v = res.get(i);
            subtotal += v.getImporte();
            System.out.println((i + 1) + ") " + v);
        }
        System.out.printf("Subtotal del cliente: %.2f €%n", subtotal);
        esperarEnter();
    }

    // --- Ventas ---

    private static void registrarVenta() {
        if (datos.getClientes().isEmpty()) {
            System.out.println("No hay clientes. Da de alta al menos uno.");
            esperarEnter();
            return;
        }
        if (datos.getMotos().isEmpty()) {
            System.out.println("No hay motos. Da de alta al menos una.");
            esperarEnter();
            return;
        }

        System.out.println("\n=== REGISTRAR VENTA ===");

        // Selección de cliente
        System.out.println("\nClientes:");
        List<Cliente> lc = datos.getClientes();
        for (int i = 0; i < lc.size(); i++) System.out.println((i + 1) + ") " + lc.get(i));
        int idxCliente = leerIndiceValido("\nElige cliente (1-" + lc.size() + "): ", lc.size());
        Cliente c = lc.get(idxCliente);

        // Selección MÚLTIPLE
        ArrayList<Moto> elegidas = seleccionarMotos();

        // Confirmación de la venta con resumen
        System.out.println("\nResumen de la venta:");
        for (int i = 0; i < elegidas.size(); i++) {
            System.out.println((i + 1) + ") " + elegidas.get(i));
        }
        System.out.printf("TOTAL: %.2f €%n", sumaPrecios(elegidas));
        String conf = leerTextoNoVacio("Confirmar venta (s/n): ");
        if (!conf.equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada.");
            esperarEnter();
            return;
        }

        if (!datos.descontarStock(elegidas)) {
            System.out.println("Stock insuficiente. No se ha registrado la venta.");
            return;
        }

        Venta v = new Venta(c, elegidas);
        datos.addVenta(v);

        System.out.println("\nVenta registrada:");
        System.out.println(" - " + v);
        esperarEnter();
    }

    private static String resumenVentaSinImporte(Venta v) {
        return String.format("%s compró %d moto(s) el %s", v.getCliente().getNombre(), v.getMotos().size(), v.getFecha());
    }

    private static void mostrarTotalesVentas() {
        List<Venta> lv = datos.getVentas();
        if (lv.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            esperarEnter();
            return;
        }

        System.out.println("\nImporte de cada venta:");
        for (int i = 0; i < lv.size(); i++) {
            Venta v = lv.get(i);
            System.out.printf("%d) %.2f € — %s%n",
                    i + 1, v.getImporte(), resumenVentaSinImporte(v));
        }

        double total = datos.totalVentas();
        System.out.printf("TOTAL acumulado: %.2f €%n", total);
        esperarEnter();
    }

    private static ArrayList<Moto> seleccionarMotos() {
        List<Moto> lm = datos.getMotos();
        ArrayList<Moto> elegidas = new ArrayList<>();
        Map<Moto, Integer> parciales = new HashMap<>();

        System.out.println("\nSelecciona una o más motos. Escribe 0 para terminar.");
        while (true) {
            for (int i = 0; i < lm.size(); i++) {
                Moto m = lm.get(i);
                int ya = parciales.getOrDefault(m, 0);
                int disp = m.getStock() - ya;
                System.out.println((i + 1) + ") " + vistaMotoConStock(m) + "  -> disp. para añadir ahora: " + disp);
            }
            int opt = leerEntero("Moto (0 para terminar): ");
            if (opt == 0) {
                if (elegidas.isEmpty()) {
                    System.out.println("Debes seleccionar al menos una moto.");
                    esperarEnter();
                } else {
                    return elegidas;
                }
            } else if (opt >= 1 && opt <= lm.size()) {
                Moto m = lm.get(opt - 1);
                int ya  = parciales.getOrDefault(m, 0);
                int disp = m.getStock() - ya;
                if (disp <= 0) {
                    System.out.println("Sin stock disponible para añadir más de esta moto.");
                    esperarEnter();
                    continue;
                }
                int cant = leerCantidad(disp);

                for (int i = 0; i < cant; i++) elegidas.add(m);
                parciales.put(m, ya + cant);

                System.out.printf("Añadidas %d. Seleccionadas: %d, subtotal: %.2f €%n", cant, elegidas.size(), sumaPrecios(elegidas));
            } else {
                System.out.println("Índice fuera de rango.");
                esperarEnter();
            }
        }
    }

    private static double sumaPrecios(List<Moto> lista) {
        double t = 0.0;
        for (Moto m : lista) t += m.getPrecio();
        return t;
    }

    // --- Stock ---

    private static void reponerStock() {
        List<Moto> lista = datos.getMotos();
        if (lista.isEmpty()) { System.out.println("No hay motos."); return; }

        System.out.println("\nMotos:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ") " + vistaMotoConStock(lista.get(i)));
        }

        int idx = leerIndiceValido("Elige moto a reponer (1-" + lista.size() + "): ", lista.size());
        int unidades = leerUnidadesAReponer();

        boolean ok = datos.reponerStockPorIndice(idx, unidades);
        System.out.println(ok ? "Stock repuesto correctamente." : "No se pudo reponer el stock.");
        esperarEnter();
    }

    private static String vistaMotoConStock(Moto m) {
        return String.format("%s %s (%.2f €) [stock: %d]",
                m.getMarca(), m.getModelo(), m.getPrecio(), m.getStock());
    }

    // --- Ordenado ---

    private static void listarClientesOrdenados() {
        List<Cliente> lista = datos.clientesOrdenadosPorNombre();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay clientes.");
            esperarEnter();
            return;
        }
        System.out.println("\nClientes (ordenados por nombre):");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ") " + lista.get(i));
        }
        esperarEnter();
    }

    private static void listarMotosOrdenadas() {
        List<Moto> lista = datos.motosOrdenadasPorMarca();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay motos.");
            esperarEnter();
            return;
        }
        System.out.println("\nMotos (ordenadas por marca):");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ") " + vistaMotoConStock(lista.get(i)));
        }
        esperarEnter();
    }

    // --- Utilidades de lectura ---

    private static int leerEntero(String entrada) {
        while (true) {
            System.out.print(entrada);
            String linea = sc.nextLine();
            try {
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido.");
                esperarEnter();
            }
        }
    }

    private static int leerStockInicial() {
        while (true) {
            int n = leerEntero("Stock inicial (unidades): ");
            if (n >= 0) return n;
            System.out.println("Debe ser un entero >= 0.");
        }
    }

    private static int leerCantidad(int max) {
        while (true) {
            int n = leerEntero("Cantidad (1-" + max + "): ");
            if (n >= 1 && n <= max) return n;
            System.out.println("Debe estar entre 1 y " + max + ".");
        }
    }

    private static int leerUnidadesAReponer() {
        final int LIM = 9999;
        while (true) {
            int n = leerEntero("Unidades a añadir (1-" + LIM + "): ");
            if (n >= 1 && n <= LIM) return n;
            System.out.println("Debe estar entre 1 y " + LIM + ".");
        }
    }

    private static String leerTextoNoVacio(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("El texto no puede estar vacío.");
            esperarEnter();
        }
    }

    private static double leerPrecio() {
        while (true) {
            System.out.print("Precio (€): ");
            String s = sc.nextLine().trim().replace(',', '.');
            try {
                double v = Double.parseDouble(s);
                if (v >= 0) return v;
                System.out.println("Debe ser un número >= 0.");
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido (ej. 7899.99).");
            }
        }
    }

    private static String leerTextoOpcional(String prompt) {    // lectura opcional (permite vacío para “conservar”)
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int leerIndiceValido(String prompt, int size) {
        while (true) {
            int num = leerEntero(prompt);
            if (num >= 1 && num <= size) return num - 1;
            System.out.println("Índice fuera de rango.");
            esperarEnter();
        }
    }

    private static void esperarEnter() {
        System.out.print("\nPulsa ENTER para continuar.");
        sc.nextLine();
    }
}
