package co.edu.uptc.supplier.ui.controller;

import java.util.List;

import co.edu.uptc.supplier.domain.Enterprise;
import co.edu.uptc.supplier.dto.ResultDTO;
import co.edu.uptc.supplier.enums.RegexPattern;
import co.edu.uptc.supplier.service.EnterpriseService;

/**
 * Controlador de la entidad {@link Enterprise}. Valida los datos provenientes
 * de la vista mediante expresiones regulares ({@link RegexPattern}) y delega las
 * operaciones en {@link EnterpriseService}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class EnterpriseController extends BaseController {

    /** Servicio de empresas utilizado por el controlador. */
    private EnterpriseService service;

    /**
     * Constructor por defecto. Crea el servicio de empresas.
     */
    public EnterpriseController() {
        this.service = new EnterpriseService();
    }

    /**
     * Valida y crea una empresa a partir de datos en texto.
     *
     * @param id           identificador (numérico)
     * @param name         nombre (solo letras)
     * @param addres       dirección
     * @param numberOrders número de pedidos (numérico)
     * @return {@link ResultDTO} con el resultado de la operación
     */
    public ResultDTO<Enterprise> addEnterprise(String id, String name, String addres,
            String numberOrders) {
        ResultDTO<Enterprise> result = new ResultDTO<>();

        validateRequired(id, "id", result);
        validateRequired(name, "nombre", result);
        validateRequired(addres, "dirección", result);
        validateRequired(numberOrders, "número de pedidos", result);
        if (!result.isSuccessful()) {
            return result;
        }

        validatePattern(RegexPattern.INTEGER, id, "id", result);
        validatePattern(RegexPattern.NAME, name, "nombre", result);
        validatePattern(RegexPattern.ADDRESS, addres, "dirección", result);
        validatePattern(RegexPattern.INTEGER, numberOrders, "número de pedidos", result);
        if (!result.isSuccessful()) {
            return result;
        }

        Enterprise enterprise = new Enterprise(Integer.parseInt(id), name, addres,
                Integer.parseInt(numberOrders));

        if (!service.addEnterprise(enterprise)) {
            result.addError("Ya existe una empresa con ese id.");
            return result;
        }
        result.setData(enterprise);
        result.setMessage("La empresa fue creada correctamente.");
        return result;
    }

    /**
     * Devuelve todas las empresas registradas.
     *
     * @return lista de empresas
     */
    public List<Enterprise> listEnterprises() {
        return service.findAll();
    }

    /**
     * Busca una empresa por su id (recibido como texto).
     *
     * @param id identificador de la empresa
     * @return {@link ResultDTO} con la empresa encontrada o los errores
     */
    public ResultDTO<Enterprise> findById(String id) {
        ResultDTO<Enterprise> result = new ResultDTO<>();
        if (!validateRequired(id, "id", result)) {
            return result;
        }
        if (!validatePattern(RegexPattern.INTEGER, id, "id", result)) {
            return result;
        }
        Enterprise enterprise = service.findById(Integer.parseInt(id));
        if (enterprise == null) {
            result.addError("No existe una empresa con ese id.");
            return result;
        }
        result.setData(enterprise);
        return result;
    }

    /**
     * Valida y actualiza una empresa existente. Los campos opcionales que
     * lleguen vacíos conservarán su valor anterior.
     *
     * @param id           identificador de la empresa (numérico, requerido)
     * @param name         nuevo nombre (solo letras, opcional)
     * @param addres       nueva dirección (opcional)
     * @param numberOrders nuevo número de pedidos (numérico, opcional)
     * @return {@link ResultDTO} con el resultado de la operación
     */
    public ResultDTO<Enterprise> updateEnterprise(String id, String name, String addres,
            String numberOrders) {
        ResultDTO<Enterprise> result = new ResultDTO<>();
        if (!validateRequired(id, "id", result)
                || !validatePattern(RegexPattern.INTEGER, id, "id", result)) {
            return result;
        }

        Enterprise current = service.findById(Integer.parseInt(id));
        if (current == null) {
            result.addError("No existe una empresa con ese id.");
            return result;
        }

        if (name != null && !name.trim().isEmpty()
                && !validatePattern(RegexPattern.NAME, name, "nombre", result)) {
            return result;
        }
        if (addres != null && !addres.trim().isEmpty()
                && !validatePattern(RegexPattern.ADDRESS, addres, "dirección", result)) {
            return result;
        }
        int newNumberOrders = current.getNumberOrders();
        if (numberOrders != null && !numberOrders.trim().isEmpty()) {
            if (!validatePattern(RegexPattern.INTEGER, numberOrders, "número de pedidos", result)) {
                return result;
            }
            newNumberOrders = Integer.parseInt(numberOrders);
        }

        Enterprise enterprise = new Enterprise(current.getIdEnterprise(),
                (name == null || name.isBlank()) ? null : name,
                (addres == null || addres.isBlank()) ? null : addres,
                newNumberOrders);

        if (!service.updateEnterprise(enterprise)) {
            result.addError("No se pudo actualizar la empresa.");
            return result;
        }
        result.setData(enterprise);
        result.setMessage("La empresa fue actualizada correctamente.");
        return result;
    }

    /**
     * Valida y elimina una empresa por su id.
     *
     * @param id identificador de la empresa a eliminar
     * @return {@link ResultDTO} con el resultado de la operación
     */
    public ResultDTO<Enterprise> deleteEnterprise(String id) {
        ResultDTO<Enterprise> result = new ResultDTO<>();
        if (!validateRequired(id, "id", result)
                || !validatePattern(RegexPattern.INTEGER, id, "id", result)) {
            return result;
        }
        if (!service.deleteById(Integer.parseInt(id))) {
            result.addError("No se pudo eliminar: la empresa no existe.");
            return result;
        }
        result.setMessage("La empresa fue eliminada correctamente.");
        return result;
    }
}
