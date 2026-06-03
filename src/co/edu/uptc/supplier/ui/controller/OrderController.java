package co.edu.uptc.supplier.ui.controller;

import java.util.List;

import co.edu.uptc.supplier.domain.Order;
import co.edu.uptc.supplier.dto.ResultDTO;
import co.edu.uptc.supplier.enums.ClientType;
import co.edu.uptc.supplier.enums.RegexPattern;
import co.edu.uptc.supplier.service.OrderService;

/**
 * Controlador de la entidad {@link Order}. Valida los datos provenientes de la
 * vista mediante expresiones regulares ({@link RegexPattern}) y la enumeración
 * {@link ClientType}, y delega las operaciones en {@link OrderService}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class OrderController extends BaseController {

    /** Servicio de pedidos utilizado por el controlador. */
    private OrderService service;

    /**
     * Constructor por defecto. Crea el servicio de pedidos.
     */
    public OrderController() {
        this.service = new OrderService();
    }

    /**
     * Valida y crea un pedido a partir de datos en texto.
     *
     * @param idOrder       identificador del pedido (numérico)
     * @param clientType    tipo de cliente (valor de {@link ClientType})
     * @param nameClient    nombre del cliente (solo letras)
     * @param idSupplier    id del proveedor dueño del pedido (numérico)
     * @param addressClient dirección del cliente
     * @param order         descripción del pedido
     * @return {@link ResultDTO} con el resultado de la operación
     */
    public ResultDTO<Order> addOrder(String idOrder, String clientType, String nameClient,
            String idSupplier, String addressClient, String order) {
        ResultDTO<Order> result = new ResultDTO<>();

        validateRequired(idOrder, "id pedido", result);
        validateRequired(clientType, "tipo de cliente", result);
        validateRequired(nameClient, "nombre del cliente", result);
        validateRequired(idSupplier, "id proveedor", result);
        validateRequired(addressClient, "dirección del cliente", result);
        validateRequired(order, "descripción del pedido", result);
        if (!result.isSuccessful()) {
            return result;
        }

        validatePattern(RegexPattern.INTEGER, idOrder, "id pedido", result);
        validateClientType(clientType, result);
        validatePattern(RegexPattern.NAME, nameClient, "nombre del cliente", result);
        validatePattern(RegexPattern.INTEGER, idSupplier, "id proveedor", result);
        validatePattern(RegexPattern.ADDRESS, addressClient, "dirección del cliente", result);
        validatePattern(RegexPattern.TEXT, order, "descripción del pedido", result);
        if (!result.isSuccessful()) {
            return result;
        }

        Order newOrder = new Order(ClientType.valueOf(clientType.trim().toUpperCase()),
                nameClient, Integer.parseInt(idSupplier), addressClient, order);

        if (!service.addOrder(Integer.parseInt(idOrder), newOrder)) {
            result.addError("Ya existe un pedido con ese id.");
            return result;
        }
        result.setData(newOrder);
        result.setMessage("El pedido fue creado correctamente.");
        return result;
    }

    /**
     * Devuelve todos los pedidos registrados.
     *
     * @return lista de pedidos
     */
    public List<Order> listOrders() {
        return service.findAll();
    }

    /**
     * Busca un pedido por su id (recibido como texto).
     *
     * @param idOrder identificador del pedido
     * @return {@link ResultDTO} con el pedido encontrado o los errores
     */
    public ResultDTO<Order> findById(String idOrder) {
        ResultDTO<Order> result = new ResultDTO<>();
        if (!validateRequired(idOrder, "id pedido", result)) {
            return result;
        }
        if (!validatePattern(RegexPattern.INTEGER, idOrder, "id pedido", result)) {
            return result;
        }
        Order order = service.findById(Integer.parseInt(idOrder));
        if (order == null) {
            result.addError("No existe un pedido con ese id.");
            return result;
        }
        result.setData(order);
        return result;
    }

    /**
     * Valida y actualiza un pedido existente. Los campos opcionales que lleguen
     * vacíos conservarán su valor anterior.
     *
     * @param idOrder       identificador del pedido (numérico, requerido)
     * @param clientType    nuevo tipo de cliente (opcional)
     * @param nameClient    nuevo nombre del cliente (solo letras, opcional)
     * @param idSupplier    nuevo id de proveedor (numérico, opcional)
     * @param addressClient nueva dirección del cliente (opcional)
     * @param order         nueva descripción del pedido (opcional)
     * @return {@link ResultDTO} con el resultado de la operación
     */
    public ResultDTO<Order> updateOrder(String idOrder, String clientType, String nameClient,
            String idSupplier, String addressClient, String order) {
        ResultDTO<Order> result = new ResultDTO<>();
        if (!validateRequired(idOrder, "id pedido", result)
                || !validatePattern(RegexPattern.INTEGER, idOrder, "id pedido", result)) {
            return result;
        }

        Order current = service.findById(Integer.parseInt(idOrder));
        if (current == null) {
            result.addError("No existe un pedido con ese id.");
            return result;
        }

        ClientType newClientType = current.getClientType();
        if (clientType != null && !clientType.trim().isEmpty()) {
            if (!validateClientType(clientType, result)) {
                return result;
            }
            newClientType = ClientType.valueOf(clientType.trim().toUpperCase());
        }
        if (nameClient != null && !nameClient.trim().isEmpty()
                && !validatePattern(RegexPattern.NAME, nameClient, "nombre del cliente", result)) {
            return result;
        }
        int newIdSupplier = current.getIdSupplier();
        if (idSupplier != null && !idSupplier.trim().isEmpty()) {
            if (!validatePattern(RegexPattern.INTEGER, idSupplier, "id proveedor", result)) {
                return result;
            }
            newIdSupplier = Integer.parseInt(idSupplier);
        }
        if (addressClient != null && !addressClient.trim().isEmpty()
                && !validatePattern(RegexPattern.ADDRESS, addressClient,
                        "dirección del cliente", result)) {
            return result;
        }

        Order updated = new Order(newClientType,
                (nameClient == null || nameClient.isBlank()) ? null : nameClient,
                newIdSupplier,
                (addressClient == null || addressClient.isBlank()) ? null : addressClient,
                (order == null || order.isBlank()) ? null : order);

        if (!service.updateOrder(Integer.parseInt(idOrder), updated)) {
            result.addError("No se pudo actualizar el pedido.");
            return result;
        }
        result.setData(updated);
        result.setMessage("El pedido fue actualizado correctamente.");
        return result;
    }

    /**
     * Valida y elimina un pedido por su id.
     *
     * @param idOrder identificador del pedido a eliminar
     * @return {@link ResultDTO} con el resultado de la operación
     */
    public ResultDTO<Order> deleteOrder(String idOrder) {
        ResultDTO<Order> result = new ResultDTO<>();
        if (!validateRequired(idOrder, "id pedido", result)
                || !validatePattern(RegexPattern.INTEGER, idOrder, "id pedido", result)) {
            return result;
        }
        if (!service.deleteById(Integer.parseInt(idOrder))) {
            result.addError("No se pudo eliminar: el pedido no existe.");
            return result;
        }
        result.setMessage("El pedido fue eliminado correctamente.");
        return result;
    }

    /**
     * Valida que el texto recibido corresponda a un valor válido de
     * {@link ClientType}.
     *
     * @param clientType texto a validar
     * @param result     DTO donde se registran los errores
     * @return {@code true} si es un tipo de cliente válido; {@code false} si no
     */
    private boolean validateClientType(String clientType, ResultDTO<?> result) {
        if (!ClientType.isValid(clientType)) {
            result.addError("El 'tipo de cliente' no es válido. Use: BIG_ENTERPRISE, "
                    + "ENTERPRISE o SMALL_BUSINESS_OWNER.");
            return false;
        }
        return true;
    }
}
