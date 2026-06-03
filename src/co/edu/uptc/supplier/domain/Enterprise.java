package co.edu.uptc.supplier.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Entidad <b>Empresa</b> asociada a la entidad principal {@link Supplier}.
 *
 * <p>Modela cinco atributos: {@code idEnterprise}, {@code name},
 * {@code addres}, {@code numberOrders} y la colección {@code suppliers}.</p>
 *
 * <p>Mantiene una relación de <b>agregación</b> con {@link Supplier}: una empresa
 * agrupa a varios proveedores mediante un {@code Map}, pero los proveedores
 * pueden existir de forma independiente de la empresa.</p>
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class Enterprise {

    /** Proveedores asociados a la empresa, indexados por su identificador. */
    private Map<Integer, Supplier> suppliers;

    /** Identificador único de la empresa. */
    private int idEnterprise;

    /** Nombre de la empresa. */
    private String name;

    /** Dirección de la empresa. */
    private String addres;

    /** Número total de pedidos gestionados por la empresa. */
    private int numberOrders;

    /**
     * Constructor por defecto. Inicializa la colección de proveedores vacía.
     */
    public Enterprise() {
        super();
        this.suppliers = new HashMap<>();
    }

    /**
     * Crea una empresa con sus atributos escalares.
     * La colección de proveedores se inicializa vacía.
     *
     * @param idEnterprise identificador único de la empresa
     * @param name         nombre de la empresa
     * @param addres       dirección de la empresa
     * @param numberOrders número de pedidos de la empresa
     */
    public Enterprise(int idEnterprise, String name, String addres, int numberOrders) {
        super();
        this.suppliers = new HashMap<>();
        this.idEnterprise = idEnterprise;
        this.name = name;
        this.addres = addres;
        this.numberOrders = numberOrders;
    }

    /**
     * Devuelve la colección de proveedores de la empresa.
     *
     * @return mapa de proveedores indexados por su identificador
     */
    public Map<Integer, Supplier> getSuppliers() {
        return suppliers;
    }

    /**
     * Establece la colección de proveedores de la empresa.
     *
     * @param suppliers nuevo mapa de proveedores
     */
    public void setSuppliers(Map<Integer, Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    /**
     * Devuelve el identificador de la empresa.
     *
     * @return valor de idEnterprise
     */
    public int getIdEnterprise() {
        return idEnterprise;
    }

    /**
     * Establece el identificador de la empresa.
     *
     * @param idEnterprise nuevo valor de idEnterprise
     */
    public void setIdEnterprise(int idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    /**
     * Devuelve el nombre de la empresa.
     *
     * @return valor de name
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre de la empresa.
     *
     * @param name nuevo valor de name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve la dirección de la empresa.
     *
     * @return valor de addres
     */
    public String getAddres() {
        return addres;
    }

    /**
     * Establece la dirección de la empresa.
     *
     * @param addres nuevo valor de addres
     */
    public void setAddres(String addres) {
        this.addres = addres;
    }

    /**
     * Devuelve el número de pedidos de la empresa.
     *
     * @return valor de numberOrders
     */
    public int getNumberOrders() {
        return numberOrders;
    }

    /**
     * Establece el número de pedidos de la empresa.
     *
     * @param numberOrders nuevo valor de numberOrders
     */
    public void setNumberOrders(int numberOrders) {
        this.numberOrders = numberOrders;
    }

    /**
     * Representación textual de la empresa. Para la colección de proveedores
     * solo se muestra la cantidad.
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Enterprise [idEnterprise=" + idEnterprise + ", name=" + name
                + ", addres=" + addres + ", numberOrders=" + numberOrders
                + ", suppliers=" + (suppliers == null ? 0 : suppliers.size()) + "]";
    }
}
