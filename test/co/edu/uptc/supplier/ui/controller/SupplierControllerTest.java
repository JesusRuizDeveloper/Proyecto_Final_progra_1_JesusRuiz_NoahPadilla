package co.edu.uptc.supplier.ui.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uptc.supplier.domain.Supplier;
import co.edu.uptc.supplier.dto.ResultDTO;

/**
 * Pruebas unitarias del controlador {@link SupplierController}, centradas en la
 * validación de los datos de entrada mediante expresiones regulares.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
class SupplierControllerTest {

    /** Controlador bajo prueba, reiniciado antes de cada test. */
    private SupplierController controller;

    /**
     * Inicializa un controlador limpio antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        controller = new SupplierController();
    }

    /**
     * Verifica que un proveedor con datos válidos se cree correctamente.
     */
    @Test
    @DisplayName("Crear proveedor con datos válidos")
    void testAddValid() {
        ResultDTO<Supplier> result = controller.addSupplier("1", "30", "Carlos", "true", "10.5");
        assertTrue(result.isSuccessful());
    }

    /**
     * Verifica que un id no numérico sea rechazado por la validación regex.
     */
    @Test
    @DisplayName("Crear proveedor con id no numérico debe fallar")
    void testAddInvalidId() {
        ResultDTO<Supplier> result = controller.addSupplier("abc", "30", "Carlos", "true", "10.5");
        assertFalse(result.isSuccessful());
        assertFalse(result.getListMessageError().isEmpty());
    }

    /**
     * Verifica que un nombre con números sea rechazado por la validación regex.
     */
    @Test
    @DisplayName("Crear proveedor con nombre inválido debe fallar")
    void testAddInvalidName() {
        ResultDTO<Supplier> result = controller.addSupplier("1", "30", "Carl0s99", "true", "10.5");
        assertFalse(result.isSuccessful());
    }

    /**
     * Verifica que los campos vacíos sean rechazados.
     */
    @Test
    @DisplayName("Crear proveedor con campos vacíos debe fallar")
    void testAddEmpty() {
        ResultDTO<Supplier> result = controller.addSupplier("", "", "", "", "");
        assertFalse(result.isSuccessful());
    }

    /**
     * Verifica el flujo completo: crear, buscar y eliminar.
     */
    @Test
    @DisplayName("Flujo crear, buscar y eliminar")
    void testCrudFlow() {
        controller.addSupplier("10", "40", "Ana", "false", "5");
        assertTrue(controller.findById("10").isSuccessful());
        assertTrue(controller.deleteSupplier("10").isSuccessful());
        assertFalse(controller.findById("10").isSuccessful());
    }
}
