import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneradorParesCSV2 extends Thread {
    private List<String> identificadores;
    private String nombreArchivo;
    private static final int lineas = 100000;
    private static final int numeroMaximo = 20000;

    public GeneradorParesCSV2(List<String> identificadores, String nombreArchivo) {
        this.identificadores = identificadores;
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public void run() {
        Random random = new Random();

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            for (int i = 0; i < lineas; i++) {
                String identificador = identificadores.get(random.nextInt(identificadores.size()));
                int numeroAleatorio = random.nextInt(numeroMaximo + 1);
                writer.write(identificador + "," + numeroAleatorio + System.lineSeparator());
            }//Fin for
            System.out.println("Archivo generado: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo " + nombreArchivo + ": " + e.getMessage());
        }//Fin try-catch
    }

    public static void main(String[] args) {
        List<String> identificadores = leerIdentificadores("identificadores.txt");
        if (identificadores.isEmpty()) {
            System.err.println("No se encontraron identificadores en el archivo.");
            return;
        }//Fin if

        List<Thread> hilos = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            String nombreArchivo = "archivo_" + i + ".csv";
            GeneradorParesCSV2 generador = new GeneradorParesCSV2(identificadores, nombreArchivo);
            generador.start();
            hilos.add(generador);
        }//Fin for

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("Un hilo fue interrumpido: " + e.getMessage());
            }//Fin try-catch
        }//Fin for
        System.out.println("Generación de archivos CSV completada.");
    }

    private static List<String> leerIdentificadores(String archivo) {
        List<String> identificadores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                identificadores.add(linea.trim());
            }//Fin while
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de identificadores: " + e.getMessage());
        }//Fin try-catch
        return identificadores;
    }
}