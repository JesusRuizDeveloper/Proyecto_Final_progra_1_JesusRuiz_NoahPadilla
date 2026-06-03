package co.edu.uptc.supplier.enums;

/**
 * Enumeración que representa los tipos de cliente que puede atender un proveedor
 * a través de un pedido ({@code Order}).
 *
 * <p>El uso de un {@code enum} garantiza que el atributo {@code clientType}
 * de la entidad {@code Order} solo pueda tomar uno de los valores válidos
 * definidos aquí, evitando errores por cadenas de texto arbitrarias.</p>
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public enum ClientType {

    /** Cliente clasificado como gran empresa. */
    BIG_ENTERPRISE,

    /** Cliente clasificado como empresa de tamaño medio. */
    ENTERPRISE,

    /** Cliente clasificado como pequeño comerciante o negocio. */
    SMALL_BUSINESS_OWNER;

    /**
     * Verifica si el texto recibido corresponde a alguno de los valores
     * definidos en esta enumeración, sin distinguir mayúsculas de minúsculas.
     *
     * @param value texto a evaluar (por ejemplo, lo digitado por el usuario)
     * @return {@code true} si el texto coincide con una constante del enum;
     *         {@code false} en caso contrario o si {@code value} es {@code null}
     */
    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }
        for (ClientType type : values()) {
            if (type.name().equalsIgnoreCase(value.trim())) {
                return true;
            }
        }
        return false;
    }
}
