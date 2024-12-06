
# **sinic2-common-lib**

## **Propósito**
`sinic2-common-lib` es una librería transversal diseñada para estandarizar y centralizar componentes comunes entre microservicios en el proyecto **SINIC 2**, desarrollado con **Spring Boot**. Su objetivo es mejorar la consistencia, reutilización y mantenimiento de los microservicios al proporcionar utilidades genéricas y configuraciones reutilizables.

---

## **Características principales**
- **Respuestas estandarizadas**: Manejo uniforme de respuestas exitosas y errores.
- **Gestión global de excepciones**: Manejo centralizado de excepciones personalizadas.
- **Paginación y filtros**: DTOs genéricos para implementar paginación y filtros dinámicos.
- **Seguridad**: Configuración y utilidades para validación de tokens JWT y CORS.
- **Observabilidad**: Logs estructurados y exposición de métricas con Spring Boot Actuator.
- **Mensajería**: Configuraciones estándar para Kafka y RabbitMQ.
- **Base de datos**: Utilidades para migraciones y optimización de consultas.

---

## **Estructura del proyecto**
El proyecto está organizado en los siguientes paquetes:

| **Paquete/Carpeta**       | **Descripción**                                                                                      |
|---------------------------|--------------------------------------------------------------------------------------------------|
| **`api`**                 | Clases relacionadas con las respuestas estandarizadas (`ApiResponse`, `ErrorResponse`, `PageInfo`). |
| **`exceptions`**          | Manejo global de excepciones, excepciones personalizadas y validaciones genéricas.                |
| **`security`**            | Utilidades para validación de JWT, configuración de CORS y seguridad general.                    |
| **`validation`**          | DTOs para paginación y filtros, validaciones con Bean Validation y especificaciones JPA.          |
| **`config`**              | Configuraciones comunes como logs, Actuator y otras utilidades globales.                         |
| **`messaging`**           | Configuraciones base para mensajería asíncrona con Kafka o RabbitMQ.                             |
| **`database`**            | Configuración y utilidades para migraciones, transacciones y consultas optimizadas.              |
| **`observability`**       | Logs, métricas y trazabilidad distribuida.                                                       |

---

## **Instalación**
### **1. Requisitos previos**
- **Java 17+** (compatible con Spring Boot 3.x)
- **Maven** instalado en tu sistema.

### **2. Clonar el repositorio**
Clona el proyecto localmente:
```bash
git clone https://igac@dev.azure.com/igac/SINICv2/_git/sinic2-common-lib
cd sinic2-common-lib
```

### **3. Compilar e instalar en el repositorio local**
Ejecuta el siguiente comando para empaquetar e instalar la librería en tu repositorio local de Maven:
```bash
mvn clean install
```

---

## **Integración con otros proyectos**
Para usar esta librería en un proyecto Spring Boot, sigue estos pasos:

### **1. Agregar la dependencia en el `pom.xml`**
Incluye la dependencia en el archivo `pom.xml` de tu proyecto:
```xml
<dependency>
    <groupId>co.gov.igac.sinic2</groupId>
    <artifactId>sinic2-common-lib</artifactId>
    <version>1.0.0</version>
</dependency>
```

### **2. Actualizar dependencias**
Ejecuta el siguiente comando para actualizar las dependencias:
```bash
mvn clean install
```

---

## **Ejemplo de uso**
### **Respuestas estandarizadas**
Usa `ApiResponse` para devolver respuestas uniformes desde tus controladores:
```java
@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @GetMapping
    public ResponseEntity<ApiResponse<String>> getExample() {
        return ResponseEntity.ok(ApiResponse.success("Operación exitosa"));
    }
}
```

### **Manejo global de excepciones**
Define excepciones personalizadas y manéjalas con `GlobalExceptionHandler`:
```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

En el cliente:
```json
{
  "timestamp": "2024-11-28T14:00:00",
  "status": 404,
  "message": "Recurso no encontrado",
  "path": "/api/example"
}
```

---

## **Pruebas locales**
Para probar cambios en la librería junto a otro proyecto:
1. Asegúrate de instalar la librería localmente:
   ```bash
   mvn clean install
   ```
2. Agrega la dependencia en el proyecto consumidor.
3. Usa el **Spring Boot Dashboard** de VS Code o ejecuta tu proyecto con:
   ```bash
   mvn spring-boot:run
   ```

---

## **Contribuciones**
Si deseas mejorar la librería:
1. Crea un fork del repositorio.
2. Realiza tus cambios en una nueva rama:
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. Realiza un pull request con una descripción clara de los cambios.

---

## **Futuras mejoras**
- Soporte para integración con Elastic Stack para logs avanzados.
- Extender métricas personalizadas para Grafana.
- Mejoras en la documentación y ejemplos más detallados.

---

## **Licencia**
Este proyecto está licenciado bajo los términos de [MIT License](https://opensource.org/licenses/MIT).
