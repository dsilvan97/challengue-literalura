# Literalura 📚

Literalura es un proyecto diseñado para gestionar y explorar información sobre autores y libros de manera eficiente, utilizando un backend desarrollado con Spring Boot. Este sistema permite registrar, consultar y gestionar datos de autores y sus obras literarias, con soporte para integración de datos a través de JSON.

<H2>Anexo link donde se podrá ver un video con su funcionamiento:</H2>
<a href="https://www.youtube.com/watch?v=SsGTTEzkW4s" target="_blank" rel="noopener noreferrer">¡VER EL VIDEO!</a>

<h4>Mi Linkedin: <a href="https://www.linkedin.com/in/david-silva-nunez/" target="_blank" rel="noopener noreferrer">David Silva</a></h4>

<h2>Características principales 🚀</h2>

<h3>Gestión de autores:</h3>

* Registro de autores con información como nombre, año de nacimiento y fallecimiento.
* Relación directa entre autores y sus libros.

<h3>Gestión de libros:</h3>

* Registro de libros con detalles como título, identificador único, idioma, y cantidad de descargas.
* Relación con los autores a los que pertenecen las obras.

<h3>Estructura robusta:</h3>

* Uso de JPA para el mapeo de entidades a tablas en base de datos.
* Consultas optimizadas mediante interfaces de repositorios como AutorRepository.

<h3>Compatibilidad con JSON:</h3>

* Uso de anotaciones de Jackson para mapear datos desde archivos o APIs externas.
* Clases como DatosAutor y DatosLibro para facilitar la manipulación de datos en formato JSON.

<h3>Estrategia de relaciones:</h3>

* Relación de uno-a-muchos entre Autor y Libro para mantener un vínculo directo y organizado.

<h2>Tecnologías utilizadas 🛠️</h2>

* Lenguaje: Java 17.
* Framework: Spring Boot 3.3.4.
* Base de datos: Configuración adaptable con JPA/Hibernate.
* Dependencias principales:
* Jackson Databind para manipulación de JSON.
*Spring Data JPA para la gestión de repositorios.
