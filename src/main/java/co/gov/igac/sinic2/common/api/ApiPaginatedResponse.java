package co.gov.igac.sinic2.common.api;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * DTO genérico para respuestas con paginación.
 * 
 * <p>Este DTO encapsula:
 * - Los datos de la página actual.
 * - Metadatos de paginación mediante {@link ApiPageInfo}.</p>
 * 
 * <h2>Ejemplo de uso</h2>
 * 
 * <pre>
 * List<MyEntity> datos = ...; // Datos recuperados de la base de datos
 * PageInfo pageInfo = new PageInfo(currentPage, totalPages, pageSize, totalElements);
 * PaginatedResponse<MyEntity> response = new PaginatedResponse<>(datos, pageInfo);
 * </pre>
 * 
 * @param <T> Tipo de los datos en la respuesta.
 * 
 * @author Diego Fernando Villegas Flórez
 * @version 1.0
 */
public class ApiPaginatedResponse<T> {

    /**
     * Datos de la página actual.
     */
    private List<T> data;

    /**
     * Información de paginación.
     */
    private ApiPageInfo page;

    public ApiPaginatedResponse(List<T> data, Page<?> page) {
        this.data = data;
        this.page = new ApiPageInfo(
            page.getNumber(),
            page.getTotalPages(),
            page.getSize(),
            page.getTotalElements()
        );
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public ApiPageInfo getPage() {
        return page;
    }

    public void setPage(ApiPageInfo page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PaginatedResponse{" +
               "data=" + data +
               ", page=" + page +
               '}';
    }
}
