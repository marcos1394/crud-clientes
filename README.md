# API REST CRUD Clientes

API REST desarrollada como prueba técnica. Permite gestionar
Clientes y Estados mediante operaciones CRUD completas.

---

## Tecnologías utilizadas

| Tecnología        | Versión  | Uso                              |
|-------------------|----------|----------------------------------|
| Java              | 17       | Lenguaje de programación         |
| Spring Boot       | 3.2.5    | Framework base                   |
| Hibernate         | 6.4.4    | ORM con configuración XML        |
| Maven             | 3.x      | Gestión de dependencias          |
| H2                | -        | Base de datos en memoria         |
| Springdoc OpenAPI | 2.5.0    | Documentación Swagger            |
| Apache Tomcat     | 10.1.20  | Servidor de aplicaciones         |
| JUnit 5 + Mockito | -        | Tests unitarios                  |

---

## Decisiones técnicas importantes

### Configuración XML sin anotaciones
La prueba requiere configuración de Spring e Hibernate
mediante archivos XML, sin usar anotaciones como
@Entity, @Table, @Column o @Configuration.

Archivos XML clave:
- `spring-config.xml` → DataSource, SessionFactory,
  TransactionManager
- `Estado.hbm.xml` → Mapeo Hibernate de la entidad Estado
- `Cliente.hbm.xml` → Mapeo Hibernate de la entidad Cliente

Las clases Java (Estado.java, Cliente.java) son POJOs puros
sin ninguna anotación JPA.

### Servidor Tomcat
El proyecto se empaqueta como WAR y extiende
SpringBootServletInitializer para poder desplegarse
en un Apache Tomcat externo.

---


## Cómo ejecutar

### Opción 1 — Modo desarrollo (JAR embebido)

```bash
mvn clean package -DskipTests
mvn spring-boot:run
```

### Opción 2 — WAR en Tomcat externo

```bash
# Compilar
mvn clean package -DskipTests

# Copiar WAR a Tomcat
cp target/crud-clientes.war $TOMCAT_HOME/webapps/

# Iniciar Tomcat
$TOMCAT_HOME/bin/startup.sh
```

---

## Endpoints disponibles

### Estados — /api/estados

| Método | URL              | Descripción       |
|--------|------------------|-------------------|
| GET    | /api/estados     | Listar todos      |
| GET    | /api/estados/{id}| Obtener por ID    |
| POST   | /api/estados     | Crear nuevo       |
| PUT    | /api/estados/{id}| Actualizar        |
| DELETE | /api/estados/{id}| Eliminar          |

### Clientes — /api/clientes

| Método | URL               | Descripción       |
|--------|-------------------|-------------------|
| GET    | /api/clientes     | Listar todos      |
| GET    | /api/clientes/{id}| Obtener por ID    |
| POST   | /api/clientes     | Crear nuevo       |
| PUT    | /api/clientes/{id}| Actualizar        |
| DELETE | /api/clientes/{id}| Eliminar          |

---

## Ejemplos de uso

### Crear un estado
```json
POST /api/estados
{
  "nombre": "Jalisco"
}
```

### Crear un cliente
```json
POST /api/clientes
{
  "nombre": "Juan",
  "apPaterno": "García",
  "apMaterno": "López",
  "fechaNacimiento": "1990-05-15",
  "status": 1,
  "estado": {
    "estadoId": 1
  }
}
```

### Actualizar un cliente
```json
PUT /api/clientes/1
{
  "nombre": "Juan Carlos",
  "apPaterno": "García",
  "apMaterno": "López",
  "status": 1,
  "estado": {
    "estadoId": 2
  }
}
```

---

## URLs de acceso

| Recurso       | URL                                    |
|---------------|----------------------------------------|
| Swagger UI    | http://localhost:8080/swagger-ui.html  |
| API Docs      | http://localhost:8080/api-docs         |
| H2 Console    | http://localhost:8080/h2-console       |

### Configuración H2 Console
- JDBC URL: `jdbc:h2:mem:cruddb`
- User Name: `sa`
- Password: *(vacío)*

---

## Tests

```bash
mvn test
```

Resultado esperado:
Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS