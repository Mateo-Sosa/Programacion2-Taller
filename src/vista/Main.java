package src.vista;
import src.clases.*;
import src.precarga.*;
public class Main {
    public static void main(String[] args) throws Exception{
        
        //Creamos la gestora
        Gestora gestora = new Gestora();

        //Ejecutamos la precarga
        PrecargaDatos.cargar(gestora);

        //Iniciamos la interfaz del usuario
        Menu menu = new Menu(gestora);
        menu.iniciar();
        
    }
}
