package co.edu.uptc.supplier.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uptc.supplier.domain.Order;
import co.edu.uptc.supplier.enums.ClientType;

/**
 * Pruebas unitarias del servicio {@link OrderService}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
class OrderServiceTest {

    /** Servicio bajo prueba, reiniciado antes de cada test. */
    private OrderService service;

    /**
     * Inicializa un servicio limpio antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        service = new OrderService();
    }

    /**
     * Verifica que se pueda agregar un pedido nuevo.
     */
    @Test
    @DisplayName("Agregar un pedido nuevo debe ser exitoso")
    void testAddOrder() {
        Order order = new Order(ClientType.ENTERPRISE, "Juan", 1, "Calle 1", "Cemento");
        assertTrue(service.addOrder(1, order));
        assertEquals(1, service.findAll().size());
    }

    /**
     * Verifica que no se pueda agregar un pedido con id duplicado.
     */
    @Test
    @DisplayName("Agregar pedido con id duplicado debe fallar")
    void testAddDuplicated() {
        service.addOrder(1, new Order(ClientType.ENTERPRISE, "Juan", 1, "Calle 1", "Cemento"));
        assertFalse(service.addOrder(1,
                new Order(ClientType.BIG_ENTERPRISE, "Pepe", 2, "Calle 2", "Arena")));
    }

    /**
     * Verifica la actualización conservando valores vacíos.
     */
    @Test
    @DisplayName("Actualizar pedido conserva valores si llegan vacíos/nulos")
    void testUpdateKeepsValues() {
        service.addOrder(5, new Order(ClientType.ENTERPRISE, "Juan", 1, "Calle 1", "Cemento"));
        Order update = new Order(null, null, 9, null, null);
        assertTrue(service.updateOrder(5, update));
        Order stored = service.findById(5);
        assertEquals("Juan", stored.getNameClient());
        assertEquals(ClientType.ENTERPRISE, stored.getClientType());
        assertEquals("Cemento", stored.getOrder());
        assertEquals(9, stored.getIdSupplier());
    }

    /**
     * Verifica que actualizar un pedido inexistente devuelva falso.
     */
    @Test
    @DisplayName("Actualizar pedido inexistente debe fallar")
    void testUpdateNonExistent() {
        assertFalse(service.updateOrder(77,
                new Order(ClientType.ENTERPRISE, "X", 1, "Y", "Z")));
    }

    /**
     * Verifica la eliminación de un pedido.
     */
    @Test
    @DisplayName("Eliminar pedido")
    void testDelete() {
        service.addOrder(8, new Order(ClientType.SMALL_BUSINESS_OWNER, "Ana", 1, "Calle 3", "Pintura"));
        assertTrue(service.deleteById(8));
        assertNull(service.findById(8));
    }
}
