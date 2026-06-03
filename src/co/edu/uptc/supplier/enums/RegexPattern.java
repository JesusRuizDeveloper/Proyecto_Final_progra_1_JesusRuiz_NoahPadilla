package co.edu.uptc.supplier.enums;

/**
 * Enumeración que centraliza las expresiones regulares (regex) utilizadas
 * para validar la información ingresada por el usuario en la capa de controlador.
 *
 * <p>Reunir los patrones en un {@code enum} evita repetir cadenas de regex a lo
 * largo del código y facilita su mantenimiento: si un patrón cambia, solo se
 * modifica en un único lugar.</p>
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public enum RegexPattern {

    /** Solo dígitos enteros positivos (ej: 10, 2024). */
    INTEGER("^\\d+$"),

    /** Números decimales positivos, con parte decimal opcional (ej: 10, 10.5). */
    DECIMAL("^\\d+(\\.\\d+)?$"),

    /** Solo letras (incluye tildes y ñ) y espacios. */
    NAME("^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$"),

    /** Direcciones: letras, números, espacios y signos comunes (# . , - °). */
    ADDRESS("^[a-zA-Z0-9ÁÉÍÓÚáéíóúÑñ #.,°-]+$"),

    /** Valor booleano textual: únicamente "true" o "false". */
    BOOLEAN("^(true|false)$"),

    /** Texto libre no vacío (al menos un carácter). */
    TEXT("^.+$");

    /** Cadena con la expresión regular asociada a la constante. */
    private final String pattern;

    /**
     * Crea una constante de la enumeración asociándole su expresión regular.
     *
     * @param pattern expresión regular que define el formato válido
     */
    RegexPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Devuelve la cadena de la expresión regular asociada a esta constante.
     *
     * @return la expresión regular como {@code String}
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Indica si el valor recibido cumple con esta expresión regular.
     *
     * @param value valor a validar
     * @return {@code true} si {@code value} no es {@code null} y coincide con el
     *         patrón; {@code false} en caso contrario
     */
    public boolean matches(String value) {
        return value != null && value.matches(this.pattern);
    }
}
