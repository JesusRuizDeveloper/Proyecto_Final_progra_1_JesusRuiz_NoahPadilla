package co.edu.uptc.supplier.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uptc.supplier.domain.Enterprise;

/**
 * Repositorio en memoria para la entidad {@link Enterprise}.
 *
 * <p>Almacena las empresas en un {@code Map} cuya clave es el identificador de
 * la empresa.</p>
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class EnterpriseRepository {

    /** Estructura en memoria que almacena las empresas por su id. */
    private Map<Integer, Enterprise> mapEnterprises;

    /**
     * Constructor por defecto. Inicializa el mapa de empresas vacío.
     */
    public EnterpriseRepository() {
        this.mapEnterprises = new HashMap<>();
    }

    /**
     * Inserta o actualiza una empresa. Si ya existe una empresa con el mismo id,
     * su información será reemplazada.
     *
     * @param enterprise empresa a guardar
     */
    public void addUpdateEnterprise(Enterprise enterprise) {
        mapEnterprises.put(enterprise.getIdEnterprise(), enterprise);
    }

    /**
     * Busca una empresa por su identificador.
     *
     * @param idEnterprise identificador de la empresa
     * @return la empresa encontrada, o {@code null} si no existe
     */
    public Enterprise findById(int idEnterprise) {
        return mapEnterprises.get(idEnterprise);
    }

    /**
     * Devuelve todas las empresas almacenadas.
     *
     * @return lista con todas las empresas
     */
    public List<Enterprise> findAll() {
        return new ArrayList<>(mapEnterprises.values());
    }

    /**
     * Elimina una empresa por su identificador.
     *
     * @param idEnterprise identificador de la empresa a eliminar
     * @return {@code true} si se eliminó; {@code false} si no existía
     */
    public boolean deleteById(int idEnterprise) {
        return mapEnterprises.remove(idEnterprise) != null;
    }

    /**
     * Verifica si existe una empresa con el identificador dado.
     *
     * @param idEnterprise identificador de la empresa
     * @return {@code true} si existe; {@code false} en caso contrario
     */
    public boolean existsById(int idEnterprise) {
        return mapEnterprises.containsKey(idEnterprise);
    }
}
