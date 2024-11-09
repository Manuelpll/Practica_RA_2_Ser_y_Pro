import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeneradorIdentificadores extends Thread {
    private Set<String> identificadores;
    private static final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int logitud = 6;
    private static final String archivo = "identificadores.txt";

    public GeneradorIdentificadores() {
        identificadores = new HashSet<>();
    }

    @Override
    public void run() {
        Random random = new Random();
        while (identificadores.size() < 200) {
            StringBuilder identificador = new StringBuilder(logitud);
            for (int i = 0; i < logitud; i++) {
                int index = random.nextInt(caracteres.length());
                identificador.append(caracteres.charAt(index));
            }//Fin for
            identificadores.add(identificador.toString());
        }//Fin while
        try (FileWriter writer = new FileWriter(archivo)) {
            for (String id : identificadores) {
                writer.write(id + System.lineSeparator());
            }//fin for
            System.out.println("Identificadores guardados en " + archivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }//Fin try-catch
    }

    public static void main(String[] args) {
        GeneradorIdentificadores generador = new GeneradorIdentificadores();
        generador.start();
        try {
            generador.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }//Fin try-catch
    }//Fin de main
}
