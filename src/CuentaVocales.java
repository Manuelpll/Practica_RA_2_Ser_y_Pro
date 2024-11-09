/**
 * Este programa lanza 5 hilos para contar las vocales totales de un archivo
 * Cada hilo cuenta una vocal diferente y luego  se junta la cuenta de cada hilo para
 * mostrar el numero total de vocales
 * @author Mparr
 * @version 1.0
 * @date 9/11/2024
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CuentaVocales {
    private String texto;
    private int totalVocales = 0;

    /**
     * Metodo que crea la clase donde esta el texto que luego
     * leeran los 5 hilos de la clase contador
     * @param rutaArchivo-La ruta de donde esta el archivo
     * que quieres contar las palabras
     */

    public CuentaVocales(String rutaArchivo) {
        this.texto = leerArchivo(rutaArchivo).toLowerCase();
    }//Fin de contructor

    /**
     * Metodo que suma todas las cuentas de vocales
     * de los 5 hilos que cuentan una vocal cada uno
     * @param cantidad
     */

    private synchronized void incrementarTotalVocales(int cantidad) {
        totalVocales += cantidad;
    }//Fin de inccremetarTotalVocales

    private class Contador implements Runnable {
        private final char vocal;

        /**
         * Metodo que inicializa la clase contador
         * @param vocal-La vocal que va ha contar el hilo
         */

        public Contador(char vocal) {
            this.vocal = vocal;
        }//Fin de Contador

        /**
         * Metodo que empieza a contar la vocal del archivo
         * que le toca y muestra las vocales que hay de
         * ese tipo
         */

        @Override
        public void run() {
            int cuenta = 0;
            for (char c : texto.toCharArray()) {
                if (c == vocal) {
                    cuenta++;
                }//Fin if
            }//Fin for
            incrementarTotalVocales(cuenta);
            System.out.println("Vocal " + vocal + ": " + cuenta);
        }//Fin de run
    }//Fin de clase Contador

    /**
     * Meto que crea los diferentes hilos que cuentan las vocales
     * y luego muestra las vocales totales del archivo
     * @throws InterruptedException
     */

    public void contarVocales() throws InterruptedException {
        Thread hiloA = new Thread(new Contador('a'));
        Thread hiloE = new Thread(new Contador('e'));
        Thread hiloI = new Thread(new Contador('i'));
        Thread hiloO = new Thread(new Contador('o'));
        Thread hiloU = new Thread(new Contador('u'));

        hiloA.start();
        hiloE.start();
        hiloI.start();
        hiloO.start();
        hiloU.start();

        hiloA.join();
        hiloE.join();
        hiloI.join();
        hiloO.join();
        hiloU.join();

        System.out.println("Total de vocales: " + totalVocales);
    }//Fin de contarVocales

    /**
     * Metodo que lee el archivo y lo transforma en un string
     * @param rutaArchivo-Donde esta el archivo
     * @return El archivo tranformado en string
     */

    private String leerArchivo(String rutaArchivo) {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append(" ");
            }//Fin while
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }//Fin try-catch
        return contenido.toString();
    }//Fin de leer Archivo

    /**
     * Metodo que ejecuta el codigo
     * @param args-los argumentos de la linea de comandos
     * @throws InterruptedException Si se interumpe un hilo
     */

    public static void main(String[] args) throws InterruptedException {
        String rutaArchivo = "src\\texto.txt";
        CuentaVocales contador = new CuentaVocales(rutaArchivo);
        contador.contarVocales();
    }//Fin main
}//Fin de ContarVocales
