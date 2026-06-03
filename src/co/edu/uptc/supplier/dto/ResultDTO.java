package co.edu.uptc.supplier.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de transferencia de datos (DTO) utilizado para comunicar el resultado
 * de una operación entre las capas de controlador y vista.
 *
 * <p>Está basado en el {@code ResultDTO} de la plantilla, pero generalizado con
 * un parámetro de tipo {@code <T>} para poder transportar cualquier entidad del
 * dominio ({@code Supplier}, {@code Enterprise} u {@code Order}) sin duplicar la
 * clase.</p>
 *
 * <p>Encapsula:</p>
 * <ul>
 *   <li>{@code successful}: si la operación fue exitosa.</li>
 *   <li>{@code message}: mensaje informativo del resultado.</li>
 *   <li>{@code data}: la entidad asociada al resultado (puede ser {@code null}).</li>
 *   <li>{@code listMessageError}: lista de mensajes de error de validación.</li>
 * </ul>
 *
 * @param <T> tipo de dato (entidad) que transporta el DTO
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class ResultDTO<T> {

    /** Indica si la operación se realizó con éxito. */
    private boolean successful;

    /** Mensaje informativo asociado al resultado. */
    private String message;

    /** Entidad transportada por el resultado. */
    private T data;

    /** Lista de mensajes de error producidos durante las validaciones. */
    private List<String> listMessageError;

    /**
     * Constructor por defecto. Inicializa la lista de errores vacía y marca el
     * resultado como exitoso por defecto.
     */
    public ResultDTO() {
        this.listMessageError = new ArrayList<>();
        this.successful = true;
    }

    /**
     * Indica si la operación fue exitosa.
     *
     * @return valor de successful
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Establece si la operación fue exitosa.
     *
     * @param successful nuevo valor de successful
     */
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    /**
     * Devuelve el mensaje informativo del resultado.
     *
     * @return valor de message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establece el mensaje informativo del resultado.
     *
     * @param message nuevo valor de message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Devuelve la entidad transportada por el resultado.
     *
     * @return valor de data
     */
    public T getData() {
        return data;
    }

    /**
     * Establece la entidad transportada por el resultado.
     *
     * @param data nuevo valor de data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Devuelve la lista de mensajes de error.
     *
     * @return valor de listMessageError
     */
    public List<String> getListMessageError() {
        return listMessageError;
    }

    /**
     * Establece la lista de mensajes de error.
     *
     * @param listMessageError nueva lista de mensajes de error
     */
    public void setListMessageError(List<String> listMessageError) {
        this.listMessageError = listMessageError;
    }

    /**
     * Agrega un mensaje de error a la lista y marca el resultado como fallido.
     * Método de conveniencia para simplificar las validaciones.
     *
     * @param errorMessage mensaje de error a registrar
     */
    public void addError(String errorMessage) {
        this.successful = false;
        this.listMessageError.add(errorMessage);
    }
}
