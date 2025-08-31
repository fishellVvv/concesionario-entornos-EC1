package concesionario.modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    private Cliente c;

    @BeforeEach
    public void setUp() {
        c = new Cliente("Ana", "11111111A", "600000000", "ana@ex.com");
    }

    @Test
    public void constructorGuardaNombre() {
        assertEquals("Ana", c.getNombre());
    }

    @Test
    public void setNombreActualiza() {
        c.setNombre("Ana López");
        assertEquals("Ana López", c.getNombre());
    }

    @Test
    public void setTelefonoActualiza() {
        c.setTelefono("611111111");
        assertEquals("611111111", c.getTelefono());
    }

    @Test
    public void setEmailActualiza() {
        c.setEmail("ana@nuevo.com");
        assertEquals("ana@nuevo.com", c.getEmail());
    }
}
