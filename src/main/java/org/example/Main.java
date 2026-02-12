package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = { "ServJuego", "ConexionServCli", "LogicaNegocio" })
public class Main {

    // 3. Este es el método que realmente arranca el servidor y toda la aplicación.
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
