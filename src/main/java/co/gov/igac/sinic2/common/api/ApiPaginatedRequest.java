package co.gov.igac.sinic2.common.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO genérico para solicitudes con paginación, filtros y ordenación.
 * 
 * <p>Este DTO permite realizar solicitudes HTTP que incluyan:
 * - Paginación (número de página y tamaño de página).
 * - Ordenación (propiedad y dirección).
 * - Filtros específicos para el recurso.</p>
 * 
 * <h2>Ejemplo de uso</h2>
 * 
 * <pre>
 * // Ejemplo en un controlador
 * @GetMapping("/api/resource")
 * public ResponseEntity<PaginatedResponse<Resource>> getResources(
 *     @Valid PaginatedRequest<MyFilterDto> paginatedRequest) {
 *     // Lógica del servicio
 * }
 * </pre>
 * 
 * <h2>Valores predeterminados</h2>
 * - Página: 0 (primera página).
 * - Tamaño: 10 elementos.
 * - Orden: "id,asc".
 * 
 * @param <T> Tipo de los filtros específicos del recurso.
 * 
 * @author Diego Fernando Villegas Flórez
 * @version 1.0
 */
public class ApiPaginatedRequest<T> {

    /**
     * Número de página (0 para la primera página).
     */
    @Min(value = 0, message = "El número de página debe ser mayor o igual a 0.")
    private int page = 0;

    /**
     * Tamaño de la página.
     */
    @Min(value = 1, message = "El tamaño de página debe ser al menos 1.")
    @Max(value = 100, message = "El tamaño de página no puede ser mayor a 100.")
    private int size = 10;

    /**
     * Criterios de ordenación en formato "propiedad,dirección".
     * Ejemplo: ["nombre,asc", "fecha,desc"]
     */
    @NotNull(message = "El criterio de ordenación no puede ser nulo.")
    private String[] sort = {"id,asc"};

    /**
     * Filtros específicos para el recurso.
     * Puede contener campos personalizados dependiendo del recurso solicitado.
     */
    private T filters;

    public ApiPaginatedRequest() {
    }

    
    /**
     * Constructs a new PaginatedRequest with the specified page number, page size, sort criteria, and filters.
     *
     * @param page    the page number to be requested
     * @param size    the number of items per page
     * @param sort    an array of sort criteria
     * @param filters the filters to be applied to the request
     */
    public ApiPaginatedRequest(int page, int size, String[] sort, T filters) {
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.filters = filters;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String[] getSort() {
        return sort;
    }

    public void setSort(String[] sort) {
        this.sort = sort;
    }

    public T getFilters() {
        return filters;
    }

    public void setFilters(T filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "PaginatedRequest{" +
               "page=" + page +
               ", size=" + size +
               ", sort=" + String.join(", ", sort) +
               ", filters=" + filters +
               '}';
    }
}
