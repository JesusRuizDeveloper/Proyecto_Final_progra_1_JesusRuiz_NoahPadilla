package co.edu.uptc.supplier.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uptc.supplier.domain.Order;

/**
 * Repositorio en memoria para la entidad {@link Order}.
 *
 * <p>Almacena los pedidos en un {@code Map} cuya clave es un identificador
 * entero de pedido. Como la entidad {@code Order} no posee un atributo de id
 * propio (solo referencia al proveedor mediante {@code idSupplier}), la clave del
 * mapa actúa como identificador único de cada pedido.</p>
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class OrderRepository {

    /** Estructura en memoria que almacena los pedidos por su id. */
    private Map<Integer, Order> mapOrders;

    /**
     * Constructor por defecto. Inicializa el mapa de pedidos vacío.
     */
    public OrderRepository() {
        this.mapOrders = new HashMap<>();
    }

    /**
     * Inserta o actualiza un pedido bajo el identificador indicado. Si ya existe
     * un pedido con ese id, su información será reemplazada.
     *
     * @param idOrder identificador único del pedido (clave del mapa)
     * @param order   pedido a guardar
     */
    public void addUpdateOrder(int idOrder, Order order) {
        mapOrders.put(idOrder, order);
    }

    /**
     * Busca un pedido por su identificador.
     *
     * @param idOrder identificador del pedido
     * @return el pedido encontrado, o {@code null} si no existe
     */
    public Order findById(int idOrder) {
        return mapOrders.get(idOrder);
    }

    /**
     * Devuelve todos los pedidos almacenados.
     *
     * @return lista con todos los pedidos
     */
    public List<Order> findAll() {
        return new ArrayList<>(mapOrders.values());
    }

    /**
     * Elimina un pedido por su identificador.
     *
     * @param idOrder identificador del pedido a eliminar
     * @return {@code true} si se eliminó; {@code false} si no existía
     */
    public boolean deleteById(int idOrder) {
        return mapOrders.remove(idOrder) != null;
    }

    /**
     * Verifica si existe un pedido con el identificador dado.
     *
     * @param idOrder identificador del pedido
     * @return {@code true} si existe; {@code false} en caso contrario
     */
    public boolean existsById(int idOrder) {
        return mapOrders.containsKey(idOrder);
    }
}
