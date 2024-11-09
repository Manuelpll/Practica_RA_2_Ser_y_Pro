import java.util.Random;
public class TareaCalculo extends Thread {
    //Variables globales
    public  static  Random random = new Random();
    public static int sumaAcumulada =0;
    //Variables especificas de cada hilo
    private String nombre = "";
    /**
     * Metodo que genera en el numero aletorio entre 100 y 1000
     * y lo suma hasta que el siguiente numero sumado con los anteriores de un millon
     * @throws InterruptedException Si se interumpe el hilo
     */
    public void run() {
        while (true) {
            try {
                int numeroAleatorio = random.nextInt(901) + 100;
                sumaAcumulada += numeroAleatorio;
                if(sumaAcumulada >= 1000000) {
                    break;
                }//Fin if
                System.out.println("Número generado: " + numeroAleatorio + ", Suma acumulada: " + sumaAcumulada);
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                System.out.println("El hilo ha sido interrumpido.");
                break;
            }//Fin try-catch
        }//Fin de while
    }//Fin de run
    /**
     * Metodo de la clase para ponerle nombre al nuevo hilo
     * @param nombre- El nombre que le quieres poner al hilo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }//Fin de setnombre
}//Fin de TareaCalculo