package co.edu.uptc.supplier.domain;

import co.edu.uptc.supplier.enums.ClientType;

/**
 * Entidad <b>Pedido</b> que forma parte de un {@link Supplier} mediante una
 * relación de <b>composición</b>.
 *
 * <p>Modela cinco atributos: {@code clientType}, {@code nameClient},
 * {@code idSupplier}, {@code addressClient} y {@code order}. El atributo
 * {@code clientType} utiliza la enumeración {@link ClientType} para restringir
 * los valores válidos.</p>
 *
 * <p>El atributo {@code idSupplier} actúa como referencia al proveedor dueño
 * del pedido.</p>
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class Order {

    /** Tipo de cliente asociado al pedido. */
    private ClientType clientType;

    /** Nombre del cliente que realiza el pedido. */
    private String nameClient;

    /** Identificador del proveedor dueño del pedido. */
    private int idSupplier;

    /** Dirección del cliente. */
    private String addressClient;

    /** Descripción o contenido del pedido. */
    private String order;

    /**
     * Constructor por defecto.
     */
    public Order() {
        super();
    }

    /**
     * Crea un pedido con todos sus atributos.
     *
     * @param clientType    tipo de cliente ({@link ClientType})
     * @param nameClient    nombre del cliente
     * @param idSupplier    identificador del proveedor dueño del pedido
     * @param addressClient dirección del cliente
     * @param order         descripción del pedido
     */
    public Order(ClientType clientType, String nameClient, int idSupplier,
            String addressClient, String order) {
        super();
        this.clientType = clientType;
        this.nameClient = nameClient;
        this.idSupplier = idSupplier;
        this.addressClient = addressClient;
        this.order = order;
    }

    /**
     * Devuelve el tipo de cliente del pedido.
     *
     * @return valor de clientType
     */
    public ClientType getClientType() {
        return clientType;
    }

    /**
     * Establece el tipo de cliente del pedido.
     *
     * @param clientType nuevo valor de clientType
     */
    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    /**
     * Devuelve el nombre del cliente.
     *
     * @return valor de nameClient
     */
    public String getNameClient() {
        return nameClient;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nameClient nuevo valor de nameClient
     */
    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    /**
     * Devuelve el identificador del proveedor dueño del pedido.
     *
     * @return valor de idSupplier
     */
    public int getIdSupplier() {
        return idSupplier;
    }

    /**
     * Establece el identificador del proveedor dueño del pedido.
     *
     * @param idSupplier nuevo valor de idSupplier
     */
    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    /**
     * Devuelve la dirección del cliente.
     *
     * @return valor de addressClient
     */
    public String getAddressClient() {
        return addressClient;
    }

    /**
     * Establece la dirección del cliente.
     *
     * @param addressClient nuevo valor de addressClient
     */
    public void setAddressClient(String addressClient) {
        this.addressClient = addressClient;
    }

    /**
     * Devuelve la descripción del pedido.
     *
     * @return valor de order
     */
    public String getOrder() {
        return order;
    }

    /**
     * Establece la descripción del pedido.
     *
     * @param order nuevo valor de order
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * Representación textual del pedido.
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Order [clientType=" + clientType + ", nameClient=" + nameClient
                + ", idSupplier=" + idSupplier + ", addressClient=" + addressClient
                + ", order=" + order + "]";
    }
}
