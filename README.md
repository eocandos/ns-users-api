# API Gestion de Usuarios

## Tecnologías
* Java 17
* Spring Boot 3.3.3
* Maven 3x
* JPA
* H2
* JWT

## Diagrama de Secuencia
![secuence-diagram](https://github.com/user-attachments/assets/5d55156a-481a-4434-b0e5-6716c4364062)

## Como Ejecutarla

La aplicación es empaquetada como un jar con un Tomcat embebido. Por ende no es necesario instalar tomcat.

### Pasos

* Clonar repositorio
* Asegurarse de usar JDK 17 and Maven 3.x
* Crear jar con
  
```
 mvn clean package 
 ```
* Una vez construido el jar es posible correr la aplicación empleando alguno de los siguientes metodos:
    Ingresar a la carpeta target/  con cd .\target\ y luego:
```
java -jar .\users.api-0.0.1-SNAPSHOT.jar
```
O directamente en la carpeta raiz del proyecto:
```
mvn spring-boot:run
```

Una vez ejecutada debería aparecer algo como esto en la consola:
```
2024-09-05T09:15:26.283-05:00  INFO 33508 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2024-09-05T09:15:26.314-05:00  INFO 33508 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2024-09-05T09:15:26.314-05:00  INFO 33508 --- [  restartedMain] com.ns.users.api.Application             : Started Application in 3.998 seconds (process running for 4.513)
```


## Acceso y Pruebas

### Enpoints
##### * Servicios *
```
http://localhost:8080/api
```
##### * Base de Datos H2 *
```
http://localhost:8080/h2-console
```

#### Colección postman para pruebas
```
../resources/NS USERS APP.postman_collection.json
```
![postman-collection](https://github.com/user-attachments/assets/ad6a852e-a429-473a-8066-a44cb60c0578)


### Consulta usuarios (Base de datos vacía)
```
    http://localhost:8080/api/users
```
![get-users-empty](https://github.com/user-attachments/assets/8973c2a5-7988-420d-96ec-2753607d5c30)

### Registro Nuevo Usuario de manera exitosa
#### Se crea con los datos: correo, contraseña, nombre, telefonos y rol
```
    http://localhost:8080/api/users
```
![post-create-user-succesful](https://github.com/user-attachments/assets/15d4438e-ebe8-40b1-a6a9-407d4aba3615)

### ### Registro Nuevo Usuario mensaje error
```
    http://localhost:8080/api/users
```
![post-create-user-error](https://github.com/user-attachments/assets/e214546b-9c95-4cb5-8c63-5e10651936ab)

### Intento acceso No Autorizado
#### Usuario que no está registrado
```
    http://localhost:8080/api/users
```
![access-denied](https://github.com/user-attachments/assets/83bab363-4e68-4b23-8c59-54cd02e7572a)

### Acceso Exitoso
#### Se ingresa correo y contraseña registrados previamente
```
    http://localhost:8080/api/auth/login
```
![access-succesful](https://github.com/user-attachments/assets/33eea804-04a2-409f-a71f-4119ac1308a8)

### Obtener todos los usuarios registrados
#### Usuario registrado, se usa token generado para poder consultar la lista de usuarios almacenados
```
    http://localhost:8080/api/auth/login
```
![get-users-list](https://github.com/user-attachments/assets/e9ad8709-51d2-4e4c-8f95-408cacec72b0)

### Acceso a la Base de Datos H2
#### (Credenciales en archivo application.yml)
```
    http://localhost:8080/h2-console
```

![ui-1-h2-console-db](https://github.com/user-attachments/assets/d6689f08-8aed-4b20-aba2-53b4f93803b5)

#### Consulta de Usuarios creados
![ui-h2-console-db](https://github.com/user-attachments/assets/d6e1a394-6a26-464a-acb7-4fffe50c6e28)

### Acceso al contrato Swagger generado automaticamente
```
http://localhost:8080/swagger-ui/index.html
```
![swagger-doc](https://github.com/user-attachments/assets/0b52ffa5-13dc-45b0-aead-2a37a802b097)
```
http://localhost:8080/v3/api-docs
```
![swagger-doc-2](https://github.com/user-attachments/assets/7b43a9fa-017c-45db-b443-8631b755978e)

### Creación Pruebas Unitarias
```
mvn test
mvn surefire-report:report
```
El primer comando ejecuta las pruebas, y el segundo comando genera el reporte HTML.

El reporte HTML se genera en el directorio target/site de tu proyecto. Puedes abrir el archivo surefire-report.html en un navegador para ver el reporte.

La ruta completa al archivo suele ser:
```
target/site/surefire-report.html
```


