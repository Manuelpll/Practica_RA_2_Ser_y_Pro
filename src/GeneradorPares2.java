import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneradorPares2 {
    private static final String identificadores = "identificadores.txt";
    private static final String archivo1 = "pares1.csv";
    private static final String archivo2 = "pares2.csv";
    private static final int lineas = 50000;

    public static void main(String[] args) {
        generaIdentificadores();
        List<String> identificadores = leerIdentificadores();
        if (identificadores.isEmpty()) {
            System.err.println("No se encontraron identificadores en el archivo.");
            return;
        }//Fin if
        generarArchivoCSV(identificadores, archivo1);
        generarArchivoCSV(identificadores, archivo2);
    }

    private static List<String> leerIdentificadores() {
        List<String> identificadores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(GeneradorPares2.identificadores))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                identificadores.add(linea.trim());
            }//Fin while
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de identificadores: " + e.getMessage());
        }//Fin try-catch

        return identificadores;
    }

    private static void generarArchivoCSV(List<String> identificadores, String nombreArchivo) {
        Random random = new Random();

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            for (int i = 0; i < lineas; i++) {
                String identificador = identificadores.get(random.nextInt(identificadores.size()));
                int numeroAleatorio = random.nextInt(20001);
                writer.write(identificador + "," + numeroAleatorio + System.lineSeparator());
            }//Fin for
            System.out.println("Archivo CSV generado con éxito: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo CSV: " + e.getMessage());
        }//Fin try-catch
    }

    private static void generaIdentificadores() {
        GeneradorIdentificadores generador = new GeneradorIdentificadores();
        generador.start();

        try {
            generador.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }//Fin try-catch
    }
}
