package concesionario.modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MotoTest {

    private Moto m;

    @BeforeEach
    public void setUp() {
        m = new Moto("Honda", "CL500", 6790.00, 5);
    }

    @Test
    public void constructorGuardaPrecio() {
        assertEquals(6790.00, m.getPrecio(), 0.01);
    }

    @Test
    public void constructorGuardaStock() {
        assertEquals(5, m.getStock());
    }
}
