package co.edu.uptc.supplier.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Entidad principal del dominio: <b>Proveedor</b>.
 *
 * <p>Es la entidad asignada en el proyecto. Modela seis atributos:
 * {@code idSupplier}, {@code age}, {@code name}, {@code isActive},
 * {@code comission} y la colección {@code orders}.</p>
 *
 * <p>Mantiene una relación de <b>composición</b> con la entidad {@link Order}:
 * un proveedor administra sus pedidos mediante un {@code Map} cuya clave es el
 * identificador del pedido. Los pedidos forman parte del proveedor, por lo que
 * se modelan como atributo de tipo colección.</p>
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class Supplier {

    /** Identificador único del proveedor. */
    private int idSupplier;

    /** Edad del proveedor. */
    private int age;

    /** Nombre del proveedor. */
    private String name;

    /** Indica si el proveedor se encuentra activo. */
    private boolean isActive;

    /** Comisión (porcentaje o valor) asociada al proveedor. */
    private float comission;

    /** Pedidos administrados por el proveedor, indexados por su identificador. */
    private Map<Integer, Order> orders;

    /**
     * Constructor por defecto. Inicializa la colección de pedidos vacía.
     */
    public Supplier() {
        super();
        this.orders = new HashMap<>();
    }

    /**
     * Crea un proveedor con todos sus atributos escalares.
     * La colección de pedidos se inicializa vacía.
     *
     * @param idSupplier identificador único del proveedor
     * @param age        edad del proveedor
     * @param name       nombre del proveedor
     * @param isActive   estado de actividad del proveedor
     * @param comission  comisión asociada al proveedor
     */
    public Supplier(int idSupplier, int age, String name, boolean isActive, float comission) {
        super();
        this.idSupplier = idSupplier;
        this.age = age;
        this.name = name;
        this.isActive = isActive;
        this.comission = comission;
        this.orders = new HashMap<>();
    }

    /**
     * Devuelve el identificador del proveedor.
     *
     * @return valor de idSupplier
     */
    public int getIdSupplier() {
        return idSupplier;
    }

    /**
     * Establece el identificador del proveedor.
     *
     * @param idSupplier nuevo valor de idSupplier
     */
    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    /**
     * Devuelve la edad del proveedor.
     *
     * @return valor de age
     */
    public int getAge() {
        return age;
    }

    /**
     * Establece la edad del proveedor.
     *
     * @param age nuevo valor de age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Devuelve el nombre del proveedor.
     *
     * @return valor de name
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del proveedor.
     *
     * @param name nuevo valor de name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Indica si el proveedor está activo.
     *
     * @return valor de isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Establece el estado de actividad del proveedor.
     *
     * @param isActive nuevo valor de isActive
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Devuelve la comisión del proveedor.
     *
     * @return valor de comission
     */
    public float getComission() {
        return comission;
    }

    /**
     * Establece la comisión del proveedor.
     *
     * @param comission nuevo valor de comission
     */
    public void setComission(float comission) {
        this.comission = comission;
    }

    /**
     * Devuelve la colección de pedidos del proveedor.
     *
     * @return mapa de pedidos indexados por su identificador
     */
    public Map<Integer, Order> getOrders() {
        return orders;
    }

    /**
     * Establece la colección de pedidos del proveedor.
     *
     * @param orders nuevo mapa de pedidos
     */
    public void setOrders(Map<Integer, Order> orders) {
        this.orders = orders;
    }

    /**
     * Representación textual del proveedor. Para la colección de pedidos solo
     * se muestra la cantidad, evitando una salida demasiado extensa.
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Supplier [idSupplier=" + idSupplier + ", age=" + age + ", name=" + name
                + ", isActive=" + isActive + ", comission=" + comission
                + ", orders=" + (orders == null ? 0 : orders.size()) + "]";
    }
}
