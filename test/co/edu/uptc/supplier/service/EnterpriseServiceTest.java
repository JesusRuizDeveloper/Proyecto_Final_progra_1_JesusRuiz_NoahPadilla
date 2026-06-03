package co.edu.uptc.supplier.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uptc.supplier.domain.Enterprise;

/**
 * Pruebas unitarias del servicio {@link EnterpriseService}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
class EnterpriseServiceTest {

    /** Servicio bajo prueba, reiniciado antes de cada test. */
    private EnterpriseService service;

    /**
     * Inicializa un servicio limpio antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        service = new EnterpriseService();
    }

    /**
     * Verifica que se pueda agregar una empresa nueva.
     */
    @Test
    @DisplayName("Agregar una empresa nueva debe ser exitoso")
    void testAddEnterprise() {
        assertTrue(service.addEnterprise(new Enterprise(1, "Acme", "Calle 1", 5)));
        assertEquals(1, service.findAll().size());
    }

    /**
     * Verifica que no se pueda agregar una empresa con id duplicado.
     */
    @Test
    @DisplayName("Agregar empresa con id duplicado debe fallar")
    void testAddDuplicated() {
        service.addEnterprise(new Enterprise(1, "Acme", "Calle 1", 5));
        assertFalse(service.addEnterprise(new Enterprise(1, "Otra", "Calle 2", 3)));
    }

    /**
     * Verifica la actualización conservando valores vacíos.
     */
    @Test
    @DisplayName("Actualizar empresa conserva nombre y dirección si llegan vacíos")
    void testUpdateKeepsValues() {
        service.addEnterprise(new Enterprise(2, "Global", "Av 80", 10));
        assertTrue(service.updateEnterprise(new Enterprise(2, null, null, 25)));
        assertEquals("Global", service.findById(2).getName());
        assertEquals("Av 80", service.findById(2).getAddres());
        assertEquals(25, service.findById(2).getNumberOrders());
    }

    /**
     * Verifica que actualizar una empresa inexistente devuelva falso.
     */
    @Test
    @DisplayName("Actualizar empresa inexistente debe fallar")
    void testUpdateNonExistent() {
        assertFalse(service.updateEnterprise(new Enterprise(99, "X", "Y", 1)));
    }

    /**
     * Verifica la eliminación de una empresa.
     */
    @Test
    @DisplayName("Eliminar empresa")
    void testDelete() {
        service.addEnterprise(new Enterprise(3, "Beta", "Calle 3", 2));
        assertTrue(service.deleteById(3));
        assertNull(service.findById(3));
    }
}
