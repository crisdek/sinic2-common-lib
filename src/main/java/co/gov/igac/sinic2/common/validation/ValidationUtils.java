package co.gov.igac.sinic2.common.validation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Utilidades comunes para validaciones avanzadas que complementan Spring Validation.
 * 
 * <p>Esta clase proporciona métodos estáticos para realizar validaciones que no están cubiertas
 * directamente por Spring Validation, como validaciones condicionales, validación de listas, 
 * detección de duplicados, y sanitización de datos.</p>
 * 
 * <h2>Métodos disponibles</h2>
 * <ul>
 *   <li>{@link #hasDuplicates(Collection)}: Verifica si una colección contiene valores duplicados.</li>
 *   <li>{@link #isRequiredIf(boolean, String)}: Valida si un campo es obligatorio bajo una condición.</li>
 *   <li>{@link #sanitizeString(String)}: Elimina espacios innecesarios al inicio y al final de un string.</li>
 *   <li>{@link #allMatch(Collection, java.util.function.Predicate)}: Verifica si todos los elementos de una colección cumplen una condición.</li>
 * </ul>
 * 
 * <h2>Ejemplos de uso</h2>
 * 
 * <h3>1. Validar duplicados en una lista</h3>
 * <pre>
 * List<String> valores = List.of("a", "b", "a");
 * boolean tieneDuplicados = ValidationUtils.hasDuplicates(valores);
 * System.out.println(tieneDuplicados); // true
 * </pre>
 * 
 * <h3>2. Validar un campo obligatorio basado en una condición</h3>
 * <pre>
 * boolean esAdministrador = true;
 * String correo = null;
 * boolean esValido = ValidationUtils.isRequiredIf(esAdministrador, correo);
 * System.out.println(esValido); // false
 * </pre>
 * 
 * <h3>3. Sanitizar un string</h3>
 * <pre>
 * String texto = "  texto con espacios  ";
 * String textoSanitizado = ValidationUtils.sanitizeString(texto);
 * System.out.println(textoSanitizado); // "texto con espacios"
 * </pre>
 * 
 * <h3>4. Validar todos los elementos de una lista</h3>
 * <pre>
 * List<Integer> numeros = List.of(1, 2, 3);
 * boolean todosPositivos = ValidationUtils.allMatch(numeros, x -> x > 0);
 * System.out.println(todosPositivos); // true
 * </pre>
 * 
 * <h2>Notas</h2>
 * <p>Esta clase no debe ser instanciada, ya que todos los métodos son estáticos.</p>
 * 
 * @author Diego Fernando Villegas Flórez
 * @version 1.0
 */
public class ValidationUtils {

    /**
     * Verifica si una colección contiene valores duplicados.
     *
     * @param collection La colección a verificar.
     * @return true si contiene duplicados, false en caso contrario.
     */
    public static boolean hasDuplicates(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        Set<Object> seen = new HashSet<>();
        for (Object item : collection) {
            if (!seen.add(item)) {
                return true; // Ya existe un duplicado
            }
        }
        return false;
    }

    /**
     * Valida condicionalmente si un campo es obligatorio basado en otro campo.
     *
     * @param condition La condición que determina si el campo es obligatorio.
     * @param value El valor del campo a validar.
     * @return true si el campo es válido, false si es obligatorio pero está vacío.
     */
    public static boolean isRequiredIf(boolean condition, String value) {
        if (!condition) {
            return true; // No es obligatorio
        }
        return value != null && !value.isBlank(); // Es obligatorio, debe tener contenido
    }

    /**
     * Sanitiza un string eliminando espacios en blanco al inicio y al final.
     *
     * @param value El string a sanitizar.
     * @return El string sin espacios al inicio y al final, o null si la entrada es null.
     */
    public static String sanitizeString(String value) {
        return value == null ? null : value.trim();
    }

    /**
     * Valida si todos los elementos en una lista cumplen una condición.
     *
     * @param collection La colección de elementos a validar.
     * @param condition  La condición que deben cumplir.
     * @return true si todos los elementos cumplen la condición, false en caso contrario.
     */
    public static <T> boolean allMatch(Collection<T> collection, java.util.function.Predicate<T> condition) {
        if (collection == null || collection.isEmpty()) {
            return true; // Vacío, no hay elementos que validar
        }
        return collection.stream().allMatch(condition);
    }
}
