package concesionario.servicio;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import concesionario.modelo.Cliente;
import concesionario.modelo.Moto;

public class DatosTest {

    private Datos datos;
    private Moto cl500;
    private Moto xsr700;

    @BeforeEach
    public void setUp() {
        datos = new Datos();
        cl500  = new Moto("Honda",  "CL500",  6790.00, 3);
        xsr700 = new Moto("Yamaha", "XSR700", 8799.99, 2);
        datos.addMoto(cl500);
        datos.addMoto(xsr700);
        datos.addCliente(new Cliente("Ana", "11111111A", "600", "ana@ex.com"));
    }

    // --- addCliente ---

    @Test
    public void addClienteNuevoDevuelveTrue() {
        boolean ok = datos.addCliente(new Cliente("Carlos", "22222222B", "600", "c@ex.com"));
        assertTrue(ok);
    }

    @Test
    public void addClienteDuplicadoDevuelveFalse() {
        boolean ok = datos.addCliente(new Cliente("Ana Dup", "11111111A", "600", "dup@ex.com"));
        assertFalse(ok);
    }

    // --- descontarStock (éxito) ---

    @Test
    public void descontarStockSuficienteDevuelveTrue() {
        var sel = new ArrayList<Moto>();
        sel.add(cl500);
        sel.add(cl500); // 2 unidades (hay 3)
        boolean ok = datos.descontarStock(sel);
        assertTrue(ok);
    }

    @Test
    public void descontarStockSuficienteRestaStock() {
        var sel = new ArrayList<Moto>();
        sel.add(cl500);
        sel.add(cl500); // 2 unidades (hay 3)
        datos.descontarStock(sel);
        assertEquals(1, cl500.getStock()); // 3 - 2 = 1
    }

    // --- descontarStock (falta de stock) ---

    @Test
    public void descontarStockInsuficienteDevuelveFalse() {
        var sel = new ArrayList<Moto>();
        sel.add(xsr700);
        sel.add(xsr700);
        sel.add(xsr700); // pido 3 y solo hay 2
        boolean ok = datos.descontarStock(sel);
        assertFalse(ok);
    }

    @Test
    public void descontarStockInsuficienteNoCambiaStock() {
        var sel = new ArrayList<Moto>();
        sel.add(xsr700);
        sel.add(xsr700);
        sel.add(xsr700); // pido 3 y solo hay 2
        datos.descontarStock(sel);
        assertEquals(2, xsr700.getStock()); // debe permanecer igual
    }
}
