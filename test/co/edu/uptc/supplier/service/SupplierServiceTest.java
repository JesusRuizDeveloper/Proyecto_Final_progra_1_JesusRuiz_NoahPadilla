package co.edu.uptc.supplier.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uptc.supplier.domain.Supplier;

/**
 * Pruebas unitarias del servicio {@link SupplierService}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
class SupplierServiceTest {

    /** Servicio bajo prueba, reiniciado antes de cada test. */
    private SupplierService service;

    /**
     * Inicializa un servicio limpio antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        service = new SupplierService();
    }

    /**
     * Verifica que se pueda agregar un proveedor nuevo.
     */
    @Test
    @DisplayName("Agregar un proveedor nuevo debe ser exitoso")
    void testAddSupplier() {
        assertTrue(service.addSupplier(new Supplier(1, 30, "Carlos", true, 10.5f)));
        assertEquals(1, service.findAll().size());
    }

    /**
     * Verifica que no se pueda agregar un proveedor con id duplicado.
     */
    @Test
    @DisplayName("Agregar un proveedor con id duplicado debe fallar")
    void testAddDuplicatedSupplier() {
        service.addSupplier(new Supplier(1, 30, "Carlos", true, 10.5f));
        assertFalse(service.addSupplier(new Supplier(1, 40, "Otro", false, 5f)));
        assertEquals(1, service.findAll().size());
    }

    /**
     * Verifica la búsqueda por id de un proveedor existente y de uno inexistente.
     */
    @Test
    @DisplayName("Buscar proveedor por id")
    void testFindById() {
        service.addSupplier(new Supplier(7, 25, "Ana", true, 8f));
        assertNotNull(service.findById(7));
        assertEquals("Ana", service.findById(7).getName());
        assertNull(service.findById(99));
    }

    /**
     * Verifica que la actualización conserve el nombre anterior si llega vacío.
     */
    @Test
    @DisplayName("Actualizar proveedor conserva nombre si llega vacío")
    void testUpdateKeepsName() {
        service.addSupplier(new Supplier(2, 30, "Pedro", true, 10f));
        Supplier update = new Supplier(2, 45, null, false, 20f);
        assertTrue(service.updateSupplier(update));
        assertEquals("Pedro", service.findById(2).getName());
        assertEquals(45, service.findById(2).getAge());
    }

    /**
     * Verifica que actualizar un proveedor inexistente devuelva falso.
     */
    @Test
    @DisplayName("Actualizar proveedor inexistente debe fallar")
    void testUpdateNonExistent() {
        assertFalse(service.updateSupplier(new Supplier(50, 30, "X", true, 1f)));
    }

    /**
     * Verifica la eliminación de un proveedor existente e inexistente.
     */
    @Test
    @DisplayName("Eliminar proveedor")
    void testDelete() {
        service.addSupplier(new Supplier(3, 30, "Luis", true, 10f));
        assertTrue(service.deleteById(3));
        assertFalse(service.deleteById(3));
        assertTrue(service.findAll().isEmpty());
    }
}
