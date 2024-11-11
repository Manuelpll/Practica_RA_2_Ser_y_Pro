/**
 * Este programa solicita al usuario un numero de palabras
 * que quiere que genere aleatoriamente , el nombre del archivo
 * y lanza un hilo que genera un directorio llamado Lenguaje
 * donde esta dentro un archivo txt que contiene
 * el numero de palabras solicitadas
 * @author Mparr
 * @version 1.0
 * @date 11/11/2024
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
public class Lenguaje implements Runnable {
    private final int numPalabras;
    private final String nombreFichero;
    private static final String ruta = "Lenguaje\\";

    /**
     * Metodo construcor de la clase Lenguaje
     * @param numeroPalabras-El numero de palabras pedido
     * @param fichero-La ruta del fichero con el nombre elegido
     */
    public Lenguaje(int numeroPalabras, String fichero) {
        this.numPalabras = numeroPalabras;
        this.nombreFichero = ruta + fichero;
    }//Fin de constructor

    /**
     * Metodo que  inicia el hilo que crea un archivo
     * dentro del directorio Lenguaje donde estan
     * las palabras aleatorias pedidas
     */
    @Override
    public void run() {
        Random random = new Random();
        verificacionExistenciaDirectorio();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true))) {
            for (int i = 0; i < numPalabras; i++) {
                StringBuilder palabra = new StringBuilder();
                int longitudPalabra = random.nextInt(10) + 3;
                for (int j = 0; j < longitudPalabra; j++) {
                    char letra = (char) ('a' + random.nextInt(26));
                    palabra.append(letra);
                }//Fin for
                writer.write(palabra.toString());
                writer.newLine();
            }//Fin for
            System.out.println("Escritura completada en el archivo: " + nombreFichero);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }//Fin try catch
    }//Fin run

    /**
     * Metodo que verifica si esta el directorio
     * Lenguaje y si no esta lo crea
     */
    private static void verificacionExistenciaDirectorio() {
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }//Fin if
    }//Fin de verificacionExistenciaDirectorio

    /**
     * Metodo que ejecuta el codigo principal
     * @param args Los argumentos de la linea de comandos
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el número de palabras que quieres generar: ");
        int numeroPalabras = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nombre del archivo donde se guardarán: ");
        String fichero = scanner.nextLine();
        Lenguaje lenguaje = new Lenguaje(numeroPalabras, fichero);
        new Thread(lenguaje).start();
        System.out.println("Archivo generado correctamente en: " + ruta + fichero);
        scanner.close();
    }//Fin main
}//Fin de Lenguaje