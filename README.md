# Drone Clash - Servidor Backend

Bienvenido al repositorio del servidor para el juego por turnos "Drone Clash". Este documento sirve como guía completa para entender la arquitectura, las decisiones de diseño y el funcionamiento del proyecto.

## Stack Tecnológico

- **Lenguaje**: Java 17+
- **Framework**: Spring Boot 3.x
- **API**: REST con comunicación vía JSON
- **Servidor Web**: Tomcat (embebido por Spring Boot)
- **Gestor de Dependencias y Construcción**: Maven

---

## Arquitectura y Decisiones de Diseño

### ¿Por qué una API REST en lugar de WebSockets?

Aunque inicialmente se consideró usar WebSockets, se tomó la decisión estratégica de migrar a una **API REST** por las siguientes razones, especialmente relevantes en un contexto académico y para un juego por turnos:

1.  **Simplicidad y Claridad**: El modelo de petición-respuesta (Request-Response) de una API REST es mucho más sencillo de entender y depurar que el flujo de mensajes persistente de WebSockets. El cliente pide algo (`GET /estado`) o envía algo (`POST /accion`), y recibe una respuesta directa.
2.  **Adecuado para Juegos por Turnos**: La necesidad de comunicación en tiempo real de WebSockets es crítica para juegos de acción (shooters, etc.), pero es excesiva para un juego por turnos. En nuestro caso, el estado del juego solo cambia cuando un jugador completa una acción, por lo que un modelo de "consultar estado tras la acción" es perfectamente válido y más robusto.
3.  **Facilidad de Prueba**: Los endpoints de una API REST son muy fáciles de probar con herramientas estándar como Postman, Insomnia o incluso un simple `curl`, sin necesidad de un cliente de juego funcional.
4.  **Evidencia de Diseño Propio**: Una API REST bien estructurada es un pilar de la ingeniería de software moderna. Su diseño y implementación son más fáciles de explicar y justificar como un trabajo de arquitectura propio, mientras que una implementación compleja de WebSockets podría ser percibida de otra manera en un entorno académico.

### ¿Por qué Spring Boot?

Spring Boot fue elegido como framework por su capacidad para acelerar el desarrollo y por ser un estándar en la industria:

- **Autoconfiguración**: Reduce al mínimo la configuración manual.
- **Servidor Integrado**: No requiere instalar y configurar un servidor web por separado; Tomcat viene incluido y se ejecuta con un solo clic.
- **Gestión de Dependencias**: Se integra perfectamente con Maven para manejar todas las librerías necesarias.
- **Ecosistema Robusto**: Proporciona soluciones probadas para casi cualquier problema (seguridad, acceso a datos, etc.) si el proyecto crece en el futuro.

---

## Estructura del Proyecto

El proyecto está organizado en paquetes con responsabilidades bien definidas para mantener el código limpio y escalable.

-   **`org.example`**:
    -   `Main.java`: **Punto de Entrada**. Es la clase que arranca toda la aplicación Spring Boot. Su anotación `@ComponentScan` es crucial, ya que le dice a Spring que busque componentes en todos los demás paquetes.

-   **`LogicaNegocio`**: **El Cerebro del Juego**. Este paquete contiene la lógica pura del juego y no sabe nada sobre APIs o internet.
    -   `Clases/ControlJuego/`: Contiene las clases que dirigen el juego (`MotorJuego`, `Partida`, `ReglasJuego`).
    -   `Clases/ObjetosJuego/`: Contiene las representaciones de las piezas del juego (`Unidad`, `Dron`, `PortaDrones`).
    -   `Excepciones/`: Contiene las excepciones personalizadas (`ReglaJuegoException`) que comunican errores de lógica de forma clara.

-   **`ConexionServCli`**: **La Capa de Comunicación**. Define cómo se comunica el servidor con el mundo exterior.
    -   `DTO/`: (Data Transfer Objects). Contiene las clases "tontas" (`EstadoJuego`, `DatosDrone`, etc.) que definen la estructura del JSON que se envía y recibe del cliente. Son el "idioma" compartido.
    -   `Conexion/`: Contiene las clases que manejan directamente las peticiones web. Aquí vivirá nuestro `ControladorJuego`.

-   **`ServJuego`**: **El Puente / Traductor**.
    -   `ServicioJuego.java`: Es la pieza que conecta la `LogicaNegocio` con la `ConexionServCli`. Su responsabilidad es:
        1.  Recibir órdenes del `ControladorJuego`.
        2.  Llamar al `MotorJuego` para que ejecute la lógica.
        3.  "Traducir" los objetos complejos de la `LogicaNegocio` (como `Partida`) a los DTOs simples (como `EstadoJuego`) que el cliente entiende.

---

## Flujo de una Acción: "Mover un Dron"

Para entender cómo se conectan las piezas, sigamos el viaje de una acción:

1.  **Cliente (Frontend)**: El jugador hace clic. El cliente envía una petición `POST` a la URL `/api/juego/accion` con un cuerpo JSON: `{ "accion": "moverDron", ... }`.

2.  **`ControladorJuego` (Capa de Conexión)**: Recibe la petición HTTP. Deserializa el JSON a un objeto `AccionJuego` y llama al método `servicioJuego.procesarAccion(accion)`.

3.  **`ServicioJuego` (Capa de Traducción)**: Recibe el objeto `AccionJuego`. Llama al método `motorJuego.procesarMoverDron(...)` dentro de un bloque `try-catch`.

4.  **`MotorJuego` (Cerebro)**:
    -   Busca la unidad por su ID. Si no la encuentra, lanza una `ReglaJuegoException`.
    -   Consulta a `ReglasJuego.ValidarMovimiento()`. Si las reglas no lo permiten, lanza una `ReglaJuegoException`.
    -   Si todo es válido, actualiza la posición del `Dron` y el `Tablero` en el objeto `Partida`.

5.  **Captura del Resultado**:
    -   **Si hubo éxito**: La llamada en `ServicioJuego` termina sin errores.
    -   **Si hubo un fallo**: El `ServicioJuego` (o el `ControladorJuego`) captura la `ReglaJuegoException` y extrae el mensaje de error específico.

6.  **Respuesta al Cliente**:
    -   **En caso de éxito**: El `ControladorJuego` pide el estado actualizado a `ServicioJuego` (que lo traduce de `Partida` a `EstadoJuego`), lo serializa a JSON y lo devuelve al cliente con un código **200 OK**.
    -   **En caso de fallo**: El `ControladorJuego` devuelve el mensaje de la excepción con un código de error, por ejemplo, **400 Bad Request**.

7.  **Cliente (Frontend)**: Recibe la respuesta. Si es un 200 OK, actualiza la pantalla con el nuevo estado del juego. Si es un error, puede mostrar un mensaje al usuario.

---

## Endpoints de la API (Contrato con el Cliente)

-   ### Obtener Estado del Juego
    -   **URL**: `/api/juego/estado`
    -   **Método**: `GET`
    -   **Respuesta Exitosa (200 OK)**: Un objeto JSON con la estructura de `EstadoJuego`.

-   ### Enviar una Acción
    -   **URL**: `/api/juego/accion`
    -   **Método**: `POST`
    -   **Cuerpo de la Petición**: Un objeto JSON con la estructura de `AccionJuego`.
    -   **Respuesta Exitosa (200 OK)**: Un objeto JSON con el nuevo `EstadoJuego` tras la acción.
    -   **Respuesta de Error (400 Bad Request)**: Un texto con el mensaje específico del error (ej: "No se encontró la unidad con el ID: ...").

---

## Cómo Ejecutar el Servidor

1.  Abre el proyecto en tu IDE (IntelliJ IDEA es recomendado).
2.  Asegúrate de que el IDE ha importado el proyecto como un proyecto de Maven (a través del `pom.xml`).
3.  Busca el archivo `Main.java` en el paquete `org.example`.
4.  Haz clic derecho sobre el archivo y selecciona **`Run 'Main.main()'`**.
5.  El servidor se iniciará y estará escuchando en `http://localhost:8080`.
