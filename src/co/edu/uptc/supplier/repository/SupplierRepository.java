package co.edu.uptc.supplier.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uptc.supplier.domain.Supplier;

/**
 * Repositorio en memoria para la entidad {@link Supplier}.
 *
 * <p>Almacena los proveedores en un {@code Map} cuya clave es el identificador
 * del proveedor. Esta capa solo se encarga de la persistencia (en este caso, en
 * memoria) y no contiene lógica de negocio.</p>
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class SupplierRepository {

    /** Estructura en memoria que almacena los proveedores por su id. */
    private Map<Integer, Supplier> mapSuppliers;

    /**
     * Constructor por defecto. Inicializa el mapa de proveedores vacío.
     */
    public SupplierRepository() {
        this.mapSuppliers = new HashMap<>();
    }

    /**
     * Inserta o actualiza un proveedor. Si ya existe un proveedor con el mismo
     * id, su información será reemplazada.
     *
     * @param supplier proveedor a guardar
     */
    public void addUpdateSupplier(Supplier supplier) {
        mapSuppliers.put(supplier.getIdSupplier(), supplier);
    }

    /**
     * Busca un proveedor por su identificador.
     *
     * @param idSupplier identificador del proveedor
     * @return el proveedor encontrado, o {@code null} si no existe
     */
    public Supplier findById(int idSupplier) {
        return mapSuppliers.get(idSupplier);
    }

    /**
     * Devuelve todos los proveedores almacenados.
     *
     * @return lista con todos los proveedores
     */
    public List<Supplier> findAll() {
        return new ArrayList<>(mapSuppliers.values());
    }

    /**
     * Elimina un proveedor por su identificador.
     *
     * @param idSupplier identificador del proveedor a eliminar
     * @return {@code true} si se eliminó; {@code false} si no existía
     */
    public boolean deleteById(int idSupplier) {
        return mapSuppliers.remove(idSupplier) != null;
    }

    /**
     * Verifica si existe un proveedor con el identificador dado.
     *
     * @param idSupplier identificador del proveedor
     * @return {@code true} si existe; {@code false} en caso contrario
     */
    public boolean existsById(int idSupplier) {
        return mapSuppliers.containsKey(idSupplier);
    }
}
