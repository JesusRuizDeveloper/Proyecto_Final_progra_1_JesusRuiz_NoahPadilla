package co.edu.uptc.supplier.service;

import java.util.List;
import java.util.Objects;

import co.edu.uptc.supplier.domain.Enterprise;
import co.edu.uptc.supplier.repository.EnterpriseRepository;

/**
 * Servicio de la entidad {@link Enterprise}. Contiene la lógica de negocio y
 * coordina el acceso al {@link EnterpriseRepository}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class EnterpriseService {

    /** Repositorio de empresas utilizado por el servicio. */
    private EnterpriseRepository repository;

    /**
     * Constructor por defecto. Crea el repositorio de empresas.
     */
    public EnterpriseService() {
        this.repository = new EnterpriseRepository();
    }

    /**
     * Agrega una nueva empresa solo si no existe otra con el mismo id.
     *
     * @param enterprise empresa a agregar
     * @return {@code true} si se agregó; {@code false} si el id ya existía
     */
    public boolean addEnterprise(Enterprise enterprise) {
        if (repository.existsById(enterprise.getIdEnterprise())) {
            return false;
        }
        repository.addUpdateEnterprise(enterprise);
        return true;
    }

    /**
     * Busca una empresa por su identificador.
     *
     * @param idEnterprise identificador de la empresa
     * @return la empresa encontrada, o {@code null} si no existe
     */
    public Enterprise findById(int idEnterprise) {
        return repository.findById(idEnterprise);
    }

    /**
     * Devuelve todas las empresas registradas.
     *
     * @return lista de empresas
     */
    public List<Enterprise> findAll() {
        return repository.findAll();
    }

    /**
     * Actualiza una empresa existente. Conserva los valores anteriores de
     * {@code name} y {@code addres} si los nuevos llegan vacíos o nulos, y
     * mantiene la colección de proveedores previa.
     *
     * @param enterprise empresa con la información a actualizar
     * @return {@code true} si se actualizó; {@code false} si la empresa no existe
     */
    public boolean updateEnterprise(Enterprise enterprise) {
        Enterprise current = repository.findById(enterprise.getIdEnterprise());
        if (Objects.isNull(current)) {
            return false;
        }
        if (Objects.isNull(enterprise.getName()) || enterprise.getName().isBlank()) {
            enterprise.setName(current.getName());
        }
        if (Objects.isNull(enterprise.getAddres()) || enterprise.getAddres().isBlank()) {
            enterprise.setAddres(current.getAddres());
        }
        enterprise.setSuppliers(current.getSuppliers());
        repository.addUpdateEnterprise(enterprise);
        return true;
    }

    /**
     * Elimina una empresa por su identificador.
     *
     * @param idEnterprise identificador de la empresa a eliminar
     * @return {@code true} si se eliminó; {@code false} si no existía
     */
    public boolean deleteById(int idEnterprise) {
        return repository.deleteById(idEnterprise);
    }
}
