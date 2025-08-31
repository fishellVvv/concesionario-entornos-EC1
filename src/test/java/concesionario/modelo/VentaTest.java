package concesionario.modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class VentaTest {

    private Cliente c;
    private Moto m1, m2;
    private Venta v;

    @BeforeEach
    public void setUp() {
        c  = new Cliente("Ana", "11111111A", "600000000", "ana@ex.com");
        m1 = new Moto("Honda",  "CL500",  6790.00, 5);
        m2 = new Moto("Yamaha", "XSR700", 8799.99, 2);
        v  = new Venta(c, List.of(m1, m2));
    }

    @Test
    public void importeEsSumaDePrecios() {
        double esperado = m1.getPrecio() + m2.getPrecio();
        assertEquals(esperado, v.getImporte(), 0.01);
    }

    @Test
    public void vendeDosMotos() {
        assertEquals(2, v.getMotos().size());
    }

    @Test
    public void clienteEsElMismoObjeto() {
        assertSame(c, v.getCliente());
    }

    @Test
    public void fechaNoEsNula() {
        assertNotNull(v.getFecha());
    }
}
