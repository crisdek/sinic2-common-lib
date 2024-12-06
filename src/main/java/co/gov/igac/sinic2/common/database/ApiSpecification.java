package co.gov.igac.sinic2.common.database;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Especificaciones reutilizables para consultas dinámicas en Spring Data JPA.
 *
 * <p>Esta clase proporciona métodos estáticos para generar {@link Specification}
 * que permiten construir consultas avanzadas basadas en criterios dinámicos.
 * Es compatible con Spring Boot 3.4 y Jakarta Persistence.</p>
 *
 * <h2>Funcionalidades Principales</h2>
 * <p>Incluye métodos para crear especificaciones de búsqueda basadas en:
 * <ul>
 *   <li>Igualdad ({@link #equals(String, Object)})</li>
 *   <li>Contención (LIKE, {@link #contains(String, String)})</li>
 *   <li>Comparaciones ({@link #greaterThan(String, Comparable)}, {@link #lessThan(String, Comparable)})</li>
 *   <li>Rangos ({@link #between(String, Comparable, Comparable)})</li>
 *   <li>Inclusión en listas (IN, {@link #in(String, List)})</li>
 * </ul>
 * </p>
 *
 * <h2>Ejemplo de Uso</h2>
 * <pre>
 * Specification<MyEntity> specification = Specification.where(Specifications.contains("nombre", "Juan"))
 *     .and(Specifications.equals("estado", "activo"))
 *     .and(Specifications.between("edad", 18, 30));
 * List<MyEntity> resultados = myEntityRepository.findAll(specification);
 * </pre>
 *
 * <h2>Requisitos</h2>
 * <p>Los proyectos que utilicen esta clase deben incluir:
 * <ul>
 *   <li>La dependencia de Spring Data JPA:</li>
 * </ul>
 * <pre>
 * &lt;dependency&gt;
 *     &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;
 *     &lt;artifactId&gt;spring-boot-starter-data-jpa&lt;/artifactId&gt;
 * &lt;/dependency&gt;
 * </pre>
 * </p>
 *
 * <p>Además, el repositorio debe extender {@link org.springframework.data.jpa.repository.JpaSpecificationExecutor}.</p>
 *
 * @param <T> El tipo de la entidad sobre la cual se aplican las especificaciones.
 * 
 * @author Diego Fernando Villegas Flórez
 * @version 1.0
 */

public class ApiSpecification<T> {

    /**
     * Genera una especificación para buscar un atributo que contenga un valor (LIKE %value%).
     * 
     * @param attribute El nombre del atributo.
     * @param value El valor a buscar.
     * @return Una especificación que puede combinarse con otras.
     */
    public static <T> Specification<T> contains(String attribute, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null || value.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get(attribute)),
                "%" + value.toLowerCase() + "%"
            );
        };
    }

    /**
     * Genera una especificación para validar igualdad en un atributo.
     * 
     * @param attribute El nombre del atributo.
     * @param value El valor a buscar.
     * @return Una especificación que puede combinarse con otras.
     */
    public static <T> Specification<T> equals(String attribute, Object value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(attribute), value);
        };
    }

    /**
     * Genera una especificación para buscar valores mayores que (greater than).
     * 
     * @param attribute El nombre del atributo.
     * @param value El valor mínimo (exclusivo).
     * @return Una especificación que puede combinarse con otras.
     */
    @SuppressWarnings("unchecked")
    public static <T, Y extends Comparable<? super Y>> Specification<T> greaterThan(String attribute, Y value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }
            return criteriaBuilder.greaterThan(
                root.get(attribute).as((Class<Y>) value.getClass()), 
                value
            );
        };
    }

    /**
     * Genera una especificación para buscar valores menores que (less than).
     * 
     * @param attribute El nombre del atributo.
     * @param value El valor máximo (exclusivo).
     * @return Una especificación que puede combinarse con otras.
     */
    @SuppressWarnings("unchecked")
    public static <T, Y extends Comparable<? super Y>> Specification<T> lessThan(String attribute, Y value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }
            return criteriaBuilder.lessThan(
                root.get(attribute).as((Class<Y>) value.getClass()), 
                value
            );
        };
    }

    /**
     * Genera una especificación para buscar valores entre un rango (BETWEEN).
     * 
     * @param attribute El nombre del atributo.
     * @param min El valor mínimo (inclusive).
     * @param max El valor máximo (inclusive).
     * @return Una especificación que puede combinarse con otras.
     */
    @SuppressWarnings("unchecked")
    public static <T, Y extends Comparable<? super Y>> Specification<T> between(String attribute, Y min, Y max) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (min != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get(attribute).as((Class<Y>) min.getClass()), 
                    min
                ));
            }
            if (max != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get(attribute).as((Class<Y>) max.getClass()), 
                    max
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Genera una especificación para validar si un atributo está en una lista de valores.
     * 
     * @param attribute El nombre del atributo.
     * @param values La lista de valores a buscar.
     * @return Una especificación que puede combinarse con otras.
     */
    public static <T> Specification<T> in(String attribute, List<?> values) {
        return (root, query, criteriaBuilder) -> {
            if (values == null || values.isEmpty()) {
                return null;
            }
            return root.get(attribute).in(values);
        };
    }
}
