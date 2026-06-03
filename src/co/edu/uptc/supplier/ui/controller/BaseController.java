package co.edu.uptc.supplier.ui.controller;

import co.edu.uptc.supplier.dto.ResultDTO;
import co.edu.uptc.supplier.enums.RegexPattern;

/**
 * Clase base abstracta para los controladores de la aplicación.
 *
 * <p>Centraliza las validaciones comunes (campos requeridos y validación por
 * expresión regular) para evitar duplicación de código en los controladores
 * concretos. Las validaciones registran los errores en un {@link ResultDTO}.</p>
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public abstract class BaseController {

    /**
     * Valida que un campo de texto no sea nulo ni esté vacío.
     *
     * @param value  valor a validar
     * @param label  nombre del campo (para el mensaje de error)
     * @param result DTO donde se registran los errores
     * @return {@code true} si el campo es válido; {@code false} si está vacío
     */
    protected boolean validateRequired(String value, String label, ResultDTO<?> result) {
        if (value == null || value.trim().isEmpty()) {
            result.addError("El campo '" + label + "' no puede estar vacío.");
            return false;
        }
        return true;
    }

    /**
     * Valida que un campo cumpla con una expresión regular. Si el campo está
     * vacío no se evalúa el patrón (esa responsabilidad es de
     * {@link #validateRequired}).
     *
     * @param pattern patrón ({@link RegexPattern}) a aplicar
     * @param value   valor a validar
     * @param label   nombre del campo (para el mensaje de error)
     * @param result  DTO donde se registran los errores
     * @return {@code true} si el valor cumple el patrón; {@code false} si no
     */
    protected boolean validatePattern(RegexPattern pattern, String value, String label,
            ResultDTO<?> result) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        if (!pattern.matches(value.trim())) {
            result.addError("El campo '" + label + "' tiene un formato inválido.");
            return false;
        }
        return true;
    }
}
