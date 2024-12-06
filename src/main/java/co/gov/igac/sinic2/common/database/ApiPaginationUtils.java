package co.gov.igac.sinic2.common.database;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import co.gov.igac.sinic2.common.api.ApiPaginatedRequest;

/**
 * Utilidades para manejar la paginación y ordenación en solicitudes.
 */
public class ApiPaginationUtils {

    /**
     * Genera valores predeterminados para una solicitud de paginación.
     *
     * @return Un objeto {@link ApiPaginatedRequest} con valores por defecto.
     */
    public static ApiPaginatedRequest<Object> defaultPaginatedRequest() {
        return new ApiPaginatedRequest<>(0, 10, new String[]{"id,asc"}, null);
    }

    /**
     * Crea un {@link PageRequest} basado en los valores de paginación y ordenación de la solicitud.
     *
     * @param page Número de página.
     * @param size Tamaño de la página.
     * @param sortCriteria Criterios de ordenación en formato "campo,dirección".
     * @return Un objeto {@link PageRequest} configurado.
     */
    public static PageRequest buildPageRequest(int page, int size, String[] sortCriteria) {
        Sort sort = buildSort(sortCriteria);
        return PageRequest.of(page, size, sort);
    }


    /**
     * Genera un objeto {@link Sort} basado en los criterios de ordenación proporcionados en la solicitud.
     *
     * @param sortCriteria Un arreglo de cadenas con formato "campo,dirección" (ejemplo: ["nombre,asc", "id,desc"]).
     * @return Un objeto {@link Sort} que representa el ordenamiento especificado.
     */
    public static Sort buildSort(String[] sortCriteria) {
        if (sortCriteria == null || sortCriteria.length == 0) {
            return Sort.unsorted();
        }

        Sort sort = Sort.unsorted();
        for (String sortOrder : sortCriteria) {
            String[] sortParams = sortOrder.split(",");
            String property = sortParams[0].trim();
            Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1].trim())
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            sort = sort.and(Sort.by(direction, property));
        }
        return sort;
    }
}