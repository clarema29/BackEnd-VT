# Backend de Tienda en Línea de Productos Tecnológicos

Este proyecto es el backend de una tienda en línea de productos tecnológicos, desarrollado con Spring Boot, Spring Security con JWT y MySQL como gestor de base de datos.

## Desarrolladores

- Claudia Redondo ([@clarema29](https://github.com/clarema29))
- Angie Morales ([@angiemoralesam](https://github.com/angiemoralesam))
- Cindy Gonzalez ([@cimagoca027](https://github.com/cimagoca027))
- William Cortés ([@WilliamCortesT](https://github.com/WilliamCortesT))

## Requisitos

- Java 17 
- MySQL
- IntelliJ IDEA (o cualquier otro IDE compatible con Java)

  ## Configuración

1. Clona el repositorio del proyecto backend: https://github.com/clarema29/BackEnd-VT.git
  
2. Crea la base de datos en tu servidor local de MySQL.

3. Abre el archivo `application.properties` y configura las credenciales de tu base de datos:
   
   - spring.datasource.url=jdbc:mysql://localhost:3306/nombre_base_datos
   - spring.datasource.username=tu_usuario
   - spring.datasource.password=tu_contraseña
  
4. Abre IntelliJ IDEA y selecciona "Open" para abrir el proyecto clonado.

## Ejecución

1. En IntelliJ IDEA, busca el archivo `VisionTecnologiaApplication.java` en el panel de proyectos.

2. Haz clic derecho en el archivo y selecciona "Run 'VisionTecnologiaApplication'".

3. Espera a que la aplicación se compile y se inicie correctamente.

4. Una vez que el servidor esté en funcionamiento, podrás acceder a los endpoints de la API desde el frontend o mediante herramientas como Postman.

## Contribución

Si deseas contribuir a este proyecto, puedes seguir los siguientes pasos:

1. Realiza un fork del repositorio.
2. Crea una nueva rama para tus cambios: `git checkout -b mi-nueva-caracteristica`
3. Realiza los cambios y commit: `git commit -am 'Agrega una nueva característica'`
4. Sube tus cambios a tu fork: `git push origin mi-nueva-caracteristica`
5. Crea un nuevo Pull Request en el repositorio original.
