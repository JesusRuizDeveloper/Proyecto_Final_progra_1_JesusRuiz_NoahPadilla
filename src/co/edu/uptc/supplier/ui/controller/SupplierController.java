package co.edu.uptc.supplier.ui.controller;

import java.util.List;

import co.edu.uptc.supplier.domain.Supplier;
import co.edu.uptc.supplier.dto.ResultDTO;
import co.edu.uptc.supplier.enums.RegexPattern;
import co.edu.uptc.supplier.service.SupplierService;

/**
 * Controlador de la entidad {@link Supplier}. Recibe los datos en formato texto
 * provenientes de la vista, los valida mediante expresiones regulares
 * ({@link RegexPattern}) y delega las operaciones en {@link SupplierService}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class SupplierController extends BaseController {

    /** Servicio de proveedores utilizado por el controlador. */
    private SupplierService service;

    /**
     * Constructor por defecto. Crea el servicio de proveedores.
     */
    public SupplierController() {
        this.service = new SupplierService();
    }

    /**
     * Valida y crea un proveedor a partir de datos en texto.
     *
     * @param id        identificador (numérico)
     * @param age       edad (numérico)
     * @param name      nombre (solo letras)
     * @param isActive  estado activo ("true" o "false")
     * @param comission comisión (decimal)
     * @return {@link ResultDTO} con el resultado de la operación
     */
    public ResultDTO<Supplier> addSupplier(String id, String age, String name,
            String isActive, String comission) {
        ResultDTO<Supplier> result = new ResultDTO<>();

        validateRequired(id, "id", result);
        validateRequired(age, "edad", result);
        validateRequired(name, "nombre", result);
        validateRequired(isActive, "activo", result);
        validateRequired(comission, "comisión", result);
        if (!result.isSuccessful()) {
            return result;
        }

        validatePattern(RegexPattern.INTEGER, id, "id", result);
        validatePattern(RegexPattern.INTEGER, age, "edad", result);
        validatePattern(RegexPattern.NAME, name, "nombre", result);
        validatePattern(RegexPattern.BOOLEAN, isActive, "activo", result);
        validatePattern(RegexPattern.DECIMAL, comission, "comisión", result);
        if (!result.isSuccessful()) {
            return result;
        }

        Supplier supplier = new Supplier(Integer.parseInt(id), Integer.parseInt(age),
                name, Boolean.parseBoolean(isActive), Float.parseFloat(comission));

        if (!service.addSupplier(supplier)) {
            result.addError("Ya existe un proveedor con ese id.");
            return result;
        }
        result.setData(supplier);
        result.setMessage("El proveedor fue creado correctamente.");
        return result;
    }

    /**
     * Devuelve todos los proveedores registrados.
     *
     * @return lista de proveedores
     */
    public List<Supplier> listSuppliers() {
        return service.findAll();
    }

    /**
     * Busca un proveedor por su id (recibido como texto).
     *
     * @param id identificador del proveedor
     * @return {@link ResultDTO} con el proveedor encontrado o los errores
     */
    public ResultDTO<Supplier> findById(String id) {
        ResultDTO<Supplier> result = new ResultDTO<>();
        if (!validateRequired(id, "id", result)) {
            return result;
        }
        if (!validatePattern(RegexPattern.INTEGER, id, "id", result)) {
            return result;
        }
        Supplier supplier = service.findById(Integer.parseInt(id));
        if (supplier == null) {
            result.addError("No existe un proveedor con ese id.");
            return result;
        }
        result.setData(supplier);
        return result;
    }

    /**
     * Valida y actualiza un proveedor existente. Los campos opcionales que
     * lleguen vacíos conservarán su valor anterior.
     *
     * @param id        identificador del proveedor (numérico, requerido)
     * @param age       nueva edad (numérico, opcional)
     * @param name      nuevo nombre (solo letras, opcional)
     * @param isActive  nuevo estado activo ("true"/"false", opcional)
     * @param comission nueva comisión (decimal, opcional)
     * @return {@link ResultDTO} con el resultado de la operación
     */
    public ResultDTO<Supplier> updateSupplier(String id, String age, String name,
            String isActive, String comission) {
        ResultDTO<Supplier> result = new ResultDTO<>();
        if (!validateRequired(id, "id", result)
                || !validatePattern(RegexPattern.INTEGER, id, "id", result)) {
            return result;
        }

        Supplier current = service.findById(Integer.parseInt(id));
        if (current == null) {
            result.addError("No existe un proveedor con ese id.");
            return result;
        }

        int newAge = current.getAge();
        if (age != null && !age.trim().isEmpty()) {
            if (!validatePattern(RegexPattern.INTEGER, age, "edad", result)) {
                return result;
            }
            newAge = Integer.parseInt(age);
        }
        if (name != null && !name.trim().isEmpty()
                && !validatePattern(RegexPattern.NAME, name, "nombre", result)) {
            return result;
        }
        boolean newActive = current.isActive();
        if (isActive != null && !isActive.trim().isEmpty()) {
            if (!validatePattern(RegexPattern.BOOLEAN, isActive, "activo", result)) {
                return result;
            }
            newActive = Boolean.parseBoolean(isActive);
        }
        float newComission = current.getComission();
        if (comission != null && !comission.trim().isEmpty()) {
            if (!validatePattern(RegexPattern.DECIMAL, comission, "comisión", result)) {
                return result;
            }
            newComission = Float.parseFloat(comission);
        }

        Supplier supplier = new Supplier(current.getIdSupplier(), newAge,
                (name == null || name.isBlank()) ? null : name, newActive, newComission);

        if (!service.updateSupplier(supplier)) {
            result.addError("No se pudo actualizar el proveedor.");
            return result;
        }
        result.setData(supplier);
        result.setMessage("El proveedor fue actualizado correctamente.");
        return result;
    }

    /**
     * Valida y elimina un proveedor por su id.
     *
     * @param id identificador del proveedor a eliminar
     * @return {@link ResultDTO} con el resultado de la operación
     */
    public ResultDTO<Supplier> deleteSupplier(String id) {
        ResultDTO<Supplier> result = new ResultDTO<>();
        if (!validateRequired(id, "id", result)
                || !validatePattern(RegexPattern.INTEGER, id, "id", result)) {
            return result;
        }
        if (!service.deleteById(Integer.parseInt(id))) {
            result.addError("No se pudo eliminar: el proveedor no existe.");
            return result;
        }
        result.setMessage("El proveedor fue eliminado correctamente.");
        return result;
    }
}
