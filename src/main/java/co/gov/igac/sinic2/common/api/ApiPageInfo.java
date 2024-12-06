package co.gov.igac.sinic2.common.api;

/**
 * Contiene información sobre la paginación de respuestas de colecciones de datos.
 *
 * Incluye:
 * - Número de página actual
 * - Total de páginas disponibles
 * - Tamaño de cada página
 * - Número total de elementos en la colección
 *
 * Ejemplo de respuesta JSON:
 * <pre>
 * {
 *   "currentPage": 1,
 *   "totalPages": 5,
 *   "pageSize": 10,
 *   "totalElements": 50
 * }
 * </pre>
 * 
 * @author Diego Fernando Villegas Flórez
 * @version 1.0
 */

public class ApiPageInfo {

    private int currentPage; // Página actual
    private int totalPages; // Total de páginas
    private int pageSize; // Tamaño de la página
    private long totalElements; // Total de elementos

    // Constructor completo
    public ApiPageInfo(int currentPage, int totalPages, int pageSize, long totalElements) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    // Constructor sin totalPages
    public ApiPageInfo(int currentPage, int pageSize, long totalElements) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = totalElements;

        // Cálculo automático de totalPages
        this.totalPages = calculateTotalPages();
    }

    /**
     * Calcula el número total de páginas basado en totalElements y pageSize.
     * 
     * @return El número total de páginas.
     */
    private int calculateTotalPages() {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("El tamaño de página debe ser mayor a 0.");
        }
        return (int) Math.ceil((double) totalElements / pageSize);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
               "currentPage=" + currentPage +
               ", totalPages=" + totalPages +
               ", pageSize=" + pageSize +
               ", totalElements=" + totalElements +
               '}';
    }
}
