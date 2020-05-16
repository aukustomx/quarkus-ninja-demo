
# quarkus-ninja-demo
Quarkus Demo.

REST Endpoint
-

- Habilitar `jdk11`

- Crear proyecto quarkus via el `quarkus-maven pluging`. Getting started [link](https://quarkus.io/get-started/).
```bash
mvn io.quarkus:quarkus-maven-plugin:1.4.2.Final:create \
    -DprojectGroupId=com.aukusto \
    -DprojectArtifactId=quarkus-ninja-demo \
    -DclassName="com.aukusto.greeting.GreetingResource" \
    -Dpath="/greeting"
```
> También se puede crear un proyecto Quarkus desde la [herramienta Web](https://code.quarkus.io/)

- Moverse a proyecto recien creado
```bash
cd quarkus-ninja-demo
```

- Ejecutar la aplicación
```bash
mvn compile quarkus:dev
```

- Consultar endpoint e `index.html`
```bash
curl http://localhost:8080/greeting ; echo
```
[http://localhost:8080/](http://localhost:8080/)


> Ejecutar `echo` solo mejora la salida del comando, al agregar un salto de línea.

- Explorar contenido
```bash
mvn clean ;  tree
```

- Abrir proyecto en el IDE de preferencia
```bash
idea .
``` 

- Testing
```bash
mvn test
```

- Empaquetando la aplicación para una jvm
```bash
mvn clean package -DskipTests
```

- Revisar artefactos generados
```bash
ll -h target/
...
 12K drwxr-xr-x 2 user user  12K May 15 20:50 lib
8.0K -rw-r--r-- 1 user user 7.7K May 15 20:50 quarkus-ninja-demo-1.0-SNAPSHOT.jar
3.8M -rw-r--r-- 1 user user 3.8M May 15 20:50 quarkus-ninja-demo-1.0-SNAPSHOT-runner.jar
...
```

- Ejecutar aplicación jar con dependencias, UberJAR
```bash
java -jar target/quarkus-ninja-demo-1.0-SNAPSHOT-runner.jar
```

- Consultar que responda al ejecutar con java -jar
```bash
curl http://localhost:8080/greeting ; echo
```

- Habilitar GraalVM CE 19.3.2
```bash
graalvmce1932 #Esto es una alias
java -version
```

- Compilar para nativo con GraalVM
```bash
mvn clean package -DskipTests -Pnative
```

- Un vistazo a las extensiones online y por cli
```bash
google-chrome https://quarkus.io/extensions
mvn quarkus:list-extensions | less
```

Usando [CDI](https://quarkus.io/blog/quarkus-dependency-injection/) y persistencia con [Hibernate Panache](https://quarkus.io/guides/hibernate-orm-panache)
-

- Agregar extensiones panache, jdb de postgres y jsonb
```bash
mvn quarkus:add-extensions -Dextensions="quarkus-hibernate-orm-panache,quarkus-jdbc-postgresql,resteasy-jsonb"
```

- Agregar properties de conexión a base de datos postgres
```java
quarkus.datasource.db-kind = postgresql
quarkus.datasource.username = ninja
quarkus.datasource.password = ninja
quarkus.datasource.jdbc.url = jdbc:postgresql://localhost:5432/postgres
quarkus.hibernate-orm.database.generation = drop-and-create
```

- Desplegar base de datos con Docker
```bash
docker run -d --rm -p 5432:5432 --name postgres-ninja -v $HOME/postgres-docker-volumes/ninja:/var/lib/postgresql/data -e POSTGRES_PASSWORD=ninja -e POSTGRES_USER=ninja postgres
```

- Ejecutar aplicación en dev mode
```bash
mvn compile quarkus:dev
```

- Definir entidad BookEntity

- Definir BookService

- Definir el BookResource REST Resource

- Sin detener y arrancar nuevamente la aplicación
```bash
curl -s http://localhost:8080/books/ | jq
```

- Definir clase requet BookRequest

- Definir endpoint POST en BookRequest

- Definir método add en BookService
```java
    @Transactional
    BookEntity add(BookRequest request) {
        var bookEntity = new BookEntity();
        bookEntity.title = request.getTitle();
        bookEntity.author = request.getAuthor();
        bookEntity.publicatedOn = request.getPublicatedOn();
        bookEntity.persist();
        return bookEntity;
    }
```

- sertamos Books a la base de datos
```bash
curl -s http://localhost:8080/books/ -H "Content-Type: application/json" -d '{"title":"Effective Java", "author":"Joshua Bloch", "publicatedOn":"2017"}' | jq 
{
  "id": 1,
  "author": "Joshua Bloch",
  "publicatedOn": 2017,
  "title": "Effective Java"
}
```

- Invocamos URL books
```bash
curl -s http://localhost:8080/books/ | jq
```

- Agregar custom query method a `BookEntity.java`
```java
    public static BookEntity byTitle(String title) {
        return find("title", title).firstResult();
    }
```

- Agregar método en servicio
```java
   BookEntity byTitle(String title) {
        return BookEntity.byTitle(title);
    }
```

- Agregar endpoint en Resource
```java
    @GET
    @Path("{title}")
    public Response byTitle(@PathParam("title") String title) {
        return Response
                .status(Response.Status.OK)
                .entity(bookService.byTitle(title))
                .build();
    }
```

- Cargar nuevamente books (por )
```bash
curl -s http://localhost:8080/books/ -H "Content-Type: application/json" -d '{"title":"Effective Java", "author":"Joshua Bloch", "publicatedOn":"2017"}' | jq 
{
  "id": 1,
  "author": "Joshua Bloch",
  "publicatedOn": 2017,
  "title": "Effective Java"
}

curl -s http://localhost:8080/books/ -H "Content-Type: application/json" -d '{"title":"Clean Code", "author":"Robert c. Martin", "publicatedOn":"2011"}' | jq 
curl -s http://localhost:8080/books/ -H "Content-Type: application/json" -d '{"title":"Java Concurrency", "author":"Brian Goetz", "publicatedOn":"2017"}' | jq 
```

- Consultar un `book` por `title`
```bash
curl -s http://localhost:8080/books/Effective%20Java | jq
curl -s http://localhost:8080/books/Clean%20Code | jq
```

>Más de Hibernate Panache
>Repository pattern, JPA, Advanced Query, Paging, Sorting, Named Queries, etc., en [sitio](https://quarkus.io/guides/hibernate-orm-panache).

- Agregar extensión de OpenApi (Swagger UI incluído)
```bash
mvn quarkus:add-extension -Dextensions="openapi"
mvn compile quarkus:dev
```

- Consultar spec
```bash
curl -s http://localhost:8080/openapi | less
```

- Agregar propiedad swagger UI en `src/main/resources/application.properties`
```java
quarkus.swagger-ui.always-include=true
```

- Consultar en navegador el [Swagger-UI](http://localhost:8080/swagger-ui)

- Ejecutar nativo, tiempo de arranque
```bash
./target/quarkus-ninja-demo-1.0-SNAPSHOT-runner
```

- Probar una vez más los endpoint, ahora con el nativo ejecutándose
```bash
curl http://localhost:8080/greeting ; echo
```

