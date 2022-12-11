package Actividad_2_ejercicio_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ServidorBuffer {

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Utilizamos la clase ServerSocket en el servidor
        serverSocket = new ServerSocket();
        System.out.println("**JUEGO DEL AHORCADO - SERVIDOR**");
        System.out.println("Esperando Conexion...");
        // Creamos un Socket Adress para una máquina y numero de puerto
        InetSocketAddress addr = new InetSocketAddress("localhost", 5050);
        // Asignamos el socket a la direccion
        serverSocket.bind(addr);
        // Aceptamos la conexion
        Socket socket = serverSocket.accept();
        System.out.println("Cliente conectado...");
        System.out.println();
        // Creamos un buffer para leer los datos que nos envia el cliente
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        PrintStream ps = new PrintStream(socket.getOutputStream(), true);

        Random rand = new Random();
        // Crea una lista de palabras
        String[] palabras = {
                "casa",
                "gato",
        };
        // Selecciona una palabra al azar
        int aleatorio = rand.nextInt(palabras.length);
        String palabra = palabras[aleatorio];
        // Inicializa el contador de intentos
        int intentos = 0;
        // Crea una cadena de guiones para mostrar la palabra
        StringBuilder guiones = new StringBuilder();
        for (int i = 0; i < palabra.length(); i++) {
            guiones.append("-");
        }

        // Crear un array de strings para dibujar el ahorcado
        String[] ahorcado = new String[6];
        ahorcado[0] = "  +---+";
        ahorcado[1] = "  |   |";
        ahorcado[2] = "      |";
        ahorcado[3] = "      |";
        ahorcado[4] = "      |";
        ahorcado[5] = "========";

        // Crea una variable para guardar la última letra ingresada
        String letra;

        // Crea una variable para salir del bucle
        boolean exit = false;

        // Bucle de juego
        while (!exit) {
            // Vacía el buffer
            ps.flush();
            // Si la palabra ya no tiene guiones, el jugador ha ganado
            if (guiones.indexOf("-") == -1) {
                ps.println("¡Ganaste!");
                ps.println("La palabra era: " + palabra);
                ps.println("¿Quieres jugar de nuevo? (s/n)");
                ps.println("#");
                ps.flush();
                String respuesta = br.readLine();
                if (respuesta.equals("s")) {
                    aleatorio = rand.nextInt(palabras.length);
                    palabra = palabras[aleatorio];
                    intentos = 0;
                    guiones = new StringBuilder();
                    for (int i = 0; i < palabra.length(); i++) {
                        guiones.append("-");
                    }
                    ahorcado[0] = "  +---+";
                    ahorcado[1] = "  |   |";
                    ahorcado[2] = "      |";
                    ahorcado[3] = "      |";
                    ahorcado[4] = "      |";
                    ahorcado[5] = "========";
                } else {
                    exit = true;
                    ps.print("e");
                    break;
                }
            }

            // Si el contador de intentos es 6, el jugador ha perdido
            if (intentos == 6) {
                ps.println("¡Perdiste!");
                ps.println("La palabra era: " + palabra);
                ps.println("¿Quieres jugar de nuevo? (s/n)");
                ps.println("#");
                ps.flush();
                String respuesta = br.readLine();
                if (respuesta.equals("s")) {
                    aleatorio = rand.nextInt(palabras.length);
                    palabra = palabras[aleatorio];
                    intentos = 0;
                    guiones = new StringBuilder();
                    for (int i = 0; i < palabra.length(); i++) {
                        guiones.append("-");
                    }
                    ahorcado[0] = "  +---+";
                    ahorcado[1] = "  |   |";
                    ahorcado[2] = "      |";
                    ahorcado[3] = "      |";
                    ahorcado[4] = "      |";
                    ahorcado[5] = "========";
                } else {
                    exit = true;
                    ps.print("e");
                    break;
                }

            }
            // Titulo del juego
            ps.println("**JUEGO DEL AHORCADO - CLIENTE**");
            // Muestra el ahorcado lineas por lineas
            ps.println(ahorcado[0]);
            ps.println(ahorcado[1]);
            ps.println(ahorcado[2]);
            ps.println(ahorcado[3]);
            ps.println(ahorcado[4]);
            ps.println(ahorcado[5]);
            ps.println();

            // Muestra la palabra
            ps.println("Palabra: " + guiones);
            ps.println("Intentos: " + intentos + "/6");
            // Pide una letra
            ps.println("Ingresa una letra: ");
            ps.println("#");
            ps.flush();
            // recibe la letra de un cliente
            letra = br.readLine();

            // Si la letra está en la palabra, reemplaza los guiones por la letra
            if (palabra.indexOf(letra) != -1) {
                for (int i = 0; i < palabra.length(); i++) {
                    if (palabra.charAt(i) == letra.charAt(0)) {
                        guiones.setCharAt(i, letra.charAt(0));
                    }
                }
            } else {
                // Si la letra no está en la palabra, aumenta el contador de intentos
                intentos++;
                // Imprime los intentos

            }

        }
        // Cierra el Scanner
        scanner.close();
        // Cierra el PrintStream
        ps.close();
        // Cierra el buffer
        br.close();
        // Cierra el socket
        socket.close();
        // Cerrar el servidor
        serverSocket.close();
        // Termina el programa
        System.exit(0);

    }
}
