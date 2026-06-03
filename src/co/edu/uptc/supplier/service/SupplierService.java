package co.edu.uptc.supplier.service;

import java.util.List;
import java.util.Objects;

import co.edu.uptc.supplier.domain.Supplier;
import co.edu.uptc.supplier.repository.SupplierRepository;

/**
 * Servicio de la entidad {@link Supplier}. Contiene la lógica de negocio y
 * coordina el acceso al {@link SupplierRepository}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class SupplierService {

    /** Repositorio de proveedores utilizado por el servicio. */
    private SupplierRepository repository;

    /**
     * Constructor por defecto. Crea el repositorio de proveedores.
     */
    public SupplierService() {
        this.repository = new SupplierRepository();
    }

    /**
     * Agrega un nuevo proveedor solo si no existe otro con el mismo id.
     *
     * @param supplier proveedor a agregar
     * @return {@code true} si se agregó; {@code false} si el id ya existía
     */
    public boolean addSupplier(Supplier supplier) {
        if (repository.existsById(supplier.getIdSupplier())) {
            return false;
        }
        repository.addUpdateSupplier(supplier);
        return true;
    }

    /**
     * Busca un proveedor por su identificador.
     *
     * @param idSupplier identificador del proveedor
     * @return el proveedor encontrado, o {@code null} si no existe
     */
    public Supplier findById(int idSupplier) {
        return repository.findById(idSupplier);
    }

    /**
     * Devuelve todos los proveedores registrados.
     *
     * @return lista de proveedores
     */
    public List<Supplier> findAll() {
        return repository.findAll();
    }

    /**
     * Actualiza un proveedor existente. Conserva el nombre anterior si el nuevo
     * llega vacío o nulo, y mantiene la colección de pedidos previa.
     *
     * @param supplier proveedor con la información a actualizar
     * @return {@code true} si se actualizó; {@code false} si el proveedor no existe
     */
    public boolean updateSupplier(Supplier supplier) {
        Supplier current = repository.findById(supplier.getIdSupplier());
        if (Objects.isNull(current)) {
            return false;
        }
        if (Objects.isNull(supplier.getName()) || supplier.getName().isBlank()) {
            supplier.setName(current.getName());
        }
        supplier.setOrders(current.getOrders());
        repository.addUpdateSupplier(supplier);
        return true;
    }

    /**
     * Elimina un proveedor por su identificador.
     *
     * @param idSupplier identificador del proveedor a eliminar
     * @return {@code true} si se eliminó; {@code false} si no existía
     */
    public boolean deleteById(int idSupplier) {
        return repository.deleteById(idSupplier);
    }
}
