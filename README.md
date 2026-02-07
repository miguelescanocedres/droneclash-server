# Drone Clash - Backend Server

Este proyecto contiene el código fuente del servidor backend para "Drone Clash", un juego multijugador por turnos. Está desarrollado en Java utilizando el framework Spring Boot y se comunica con los clientes de juego a través de WebSockets.

## Stack Tecnológico

- **Lenguaje**: Java 17
- **Framework**: Spring Boot 3.x
- **Comunicación**: Spring WebSocket
- **Serialización**: Jackson (para JSON)
- **Gestor de Dependencias**: Maven

---

## Estructura del Proyecto

A continuación se describe la función de los paquetes y archivos más importantes del proyecto.

```
.
├── pom.xml
└── src
    └── main
        └── java
            └── com
                └── example
                    └── droneclashserver
                        ├── config/
                        ├── handler/
                        ├── model/
                        ├── service/
                        └── DroneClashServerApplication.java
```

### Archivos y Paquetes Principales

- **`pom.xml`**: El corazón de la configuración del proyecto Maven. Define las dependencias necesarias (como `spring-boot-starter-web` y `spring-boot-starter-websocket`), la versión de Java y cómo debe compilarse el proyecto.

- **`DroneClashServerApplication.java`**: Es el punto de entrada de la aplicación. La anotación `@SpringBootApplication` se encarga de auto-configurar el proyecto, y el método `main` inicia el servidor web Tomcat embebido y toda la aplicación Spring.

- **`config/`**: Este paquete contiene clases de configuración de Spring.
    - **`WebSocketConfig.java`**: Configura el endpoint de WebSocket. Registra el `GameHandler` para que escuche las conexiones en la ruta `/game` y permite conexiones desde cualquier origen (`.setAllowedOrigins("*")`).
    - **`AppConfig.java`**: Define "Beans" adicionales que pueden ser inyectados en otras partes de la aplicación. Aquí se define el `ObjectMapper`, que es la herramienta estándar para convertir objetos Java a JSON y viceversa.

- **`model/`**: Contiene los POJOs (Plain Old Java Objects), que son las clases que representan los datos del juego. Su estructura está diseñada para coincidir exactamente con las interfaces TypeScript del cliente para asegurar una comunicación sin errores.
    - `GameState.java`: Representa el estado completo del juego en un momento dado (turno, unidades, etc.).
    - `GameAction.java`: Representa una acción enviada por un cliente (mover un dron, pasar de turno, etc.).
    - `PortaDroneData.java` y `DroneData.java`: Representan las propiedades de las unidades individuales.

- **`service/`**: Contiene la lógica de negocio principal de la aplicación (el "cerebro" del juego).
    - **`GameService.java`**: Es un Singleton (`@Service`) que mantiene el estado actual del juego (`GameState`) en memoria. Se encarga de inicializar el tablero y procesar las acciones que le llegan desde el `GameHandler` para modificar el estado del juego.

- **`handler/`**: Contiene las clases que manejan directamente la comunicación por WebSocket.
    - **`GameHandler.java`**: Es la clase más importante para la comunicación.
        1.  Gestiona el ciclo de vida de la conexión (`afterConnectionEstablished`, `afterConnectionClosed`).
        2.  Recibe los mensajes de texto (JSON) de los clientes (`handleTextMessage`).
        3.  Utiliza el `ObjectMapper` para deserializar los mensajes a objetos `GameAction`.
        4.  Pasa las acciones al `GameService` para que sean procesadas.
        5.  Recibe el estado actualizado del `GameService` y lo difunde (broadcast) a todos los clientes conectados.

---

## Cómo Funciona la Conexión con el Cliente

La comunicación entre el cliente (Phaser) y el servidor (Spring Boot) sigue un flujo claro y basado en eventos:

1.  **Conexión Inicial**:
    - El cliente de Phaser ejecuta `new WebSocket('ws://localhost:8080/game')`.
    - En el servidor, `WebSocketConfig` dirige esta petición al `GameHandler`.
    - El método `afterConnectionEstablished` del `GameHandler` se dispara.

2.  **Envío del Estado Inicial**:
    - Inmediatamente después de la conexión, el `GameHandler` llama al `GameService` para obtener el `GameState` actual.
    - Serializa este objeto `GameState` a una cadena de texto JSON.
    - Envía este JSON al cliente recién conectado.
    - El cliente recibe el JSON, lo parsea y renderiza las unidades (drones y porta-drones) en la pantalla.

3.  **El Cliente Envía una Acción**:
    - El jugador hace clic en el mapa.
    - El cliente de Phaser crea un objeto `GameAction` (por ejemplo, `{ action: 'moveDrone', ... }`).
    - Serializa este objeto a JSON y lo envía a través del WebSocket.

4.  **El Servidor Procesa la Acción**:
    - El método `handleTextMessage` del `GameHandler` se dispara.
    - Deserializa el JSON recibido a un objeto `GameAction`.
    - Llama a `gameService.processAction(action)`, pasando la acción al cerebro del juego.
    - El `GameService` actualiza su `GameState` interno según la lógica de la acción.

5.  **Difusión del Nuevo Estado (Broadcast)**:
    - El `GameHandler` toma el `GameState` actualizado del `GameService`.
    - Lo serializa a JSON.
    - Recorre la lista de todas las sesiones de WebSocket activas y envía el nuevo estado a **cada una de ellas**.

6.  **Sincronización de Clientes**:
    - Todos los clientes (incluido el que originó la acción) reciben el nuevo `GameState`.
    - Actualizan sus pantallas para reflejar el nuevo estado del juego, asegurando que todos los jugadores vean lo mismo.

---

## Cómo Ejecutar el Proyecto

### Backend (Servidor Java)

1.  Abre el proyecto en tu IDE (IntelliJ, Eclipse, etc.).
2.  Asegúrate de que el IDE ha sincronizado las dependencias de Maven (si no, fuerza una recarga/reimportación del `pom.xml`).
3.  Busca y ejecuta la clase `DroneClashServerApplication.java`.
4.  El servidor estará corriendo en `localhost:8080`.

### Frontend (Cliente Phaser)

1.  Abre la carpeta del proyecto frontend en VS Code.
2.  Abre una terminal y ejecuta `npm install` para descargar las dependencias (solo la primera vez).
3.  Ejecuta `npm run dev`.
4.  Abre tu navegador y ve a la dirección que indica la terminal (normalmente `http://localhost:5173`).
