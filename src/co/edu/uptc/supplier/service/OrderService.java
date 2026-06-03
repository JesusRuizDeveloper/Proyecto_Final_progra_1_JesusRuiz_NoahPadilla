package co.edu.uptc.supplier.service;

import java.util.List;
import java.util.Objects;

import co.edu.uptc.supplier.domain.Order;
import co.edu.uptc.supplier.repository.OrderRepository;

/**
 * Servicio de la entidad {@link Order}. Contiene la lógica de negocio y
 * coordina el acceso al {@link OrderRepository}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class OrderService {

    /** Repositorio de pedidos utilizado por el servicio. */
    private OrderRepository repository;

    /**
     * Constructor por defecto. Crea el repositorio de pedidos.
     */
    public OrderService() {
        this.repository = new OrderRepository();
    }

    /**
     * Agrega un nuevo pedido solo si no existe otro con el mismo id.
     *
     * @param idOrder identificador único del pedido
     * @param order   pedido a agregar
     * @return {@code true} si se agregó; {@code false} si el id ya existía
     */
    public boolean addOrder(int idOrder, Order order) {
        if (repository.existsById(idOrder)) {
            return false;
        }
        repository.addUpdateOrder(idOrder, order);
        return true;
    }

    /**
     * Busca un pedido por su identificador.
     *
     * @param idOrder identificador del pedido
     * @return el pedido encontrado, o {@code null} si no existe
     */
    public Order findById(int idOrder) {
        return repository.findById(idOrder);
    }

    /**
     * Devuelve todos los pedidos registrados.
     *
     * @return lista de pedidos
     */
    public List<Order> findAll() {
        return repository.findAll();
    }

    /**
     * Actualiza un pedido existente. Conserva los valores anteriores de
     * {@code nameClient}, {@code addressClient}, {@code order} y {@code clientType}
     * si los nuevos llegan vacíos o nulos.
     *
     * @param idOrder identificador del pedido a actualizar
     * @param order   pedido con la información a actualizar
     * @return {@code true} si se actualizó; {@code false} si el pedido no existe
     */
    public boolean updateOrder(int idOrder, Order order) {
        Order current = repository.findById(idOrder);
        if (Objects.isNull(current)) {
            return false;
        }
        if (Objects.isNull(order.getNameClient()) || order.getNameClient().isBlank()) {
            order.setNameClient(current.getNameClient());
        }
        if (Objects.isNull(order.getAddressClient()) || order.getAddressClient().isBlank()) {
            order.setAddressClient(current.getAddressClient());
        }
        if (Objects.isNull(order.getOrder()) || order.getOrder().isBlank()) {
            order.setOrder(current.getOrder());
        }
        if (Objects.isNull(order.getClientType())) {
            order.setClientType(current.getClientType());
        }
        repository.addUpdateOrder(idOrder, order);
        return true;
    }

    /**
     * Elimina un pedido por su identificador.
     *
     * @param idOrder identificador del pedido a eliminar
     * @return {@code true} si se eliminó; {@code false} si no existía
     */
    public boolean deleteById(int idOrder) {
        return repository.deleteById(idOrder);
    }
}
