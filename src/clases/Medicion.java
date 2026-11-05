package src.clases;
import java.time.LocalDateTime;
public class Medicion{
    //Atributos
    private int idMedicion;
    private LocalDateTime fechaHora;
    private double milimetros;
    private boolean anomala;

    //Constructor por defecto
    public Medicion(){
    }

    //Constructor parametrizado
    public Medicion(int idMedicion, LocalDateTime fechaHora, double milimetros, boolean anomala) {
        this.idMedicion = idMedicion;
        this.fechaHora = fechaHora;
        this.milimetros = milimetros;
        this.anomala = anomala;
    }

    //Getters
    public int getIdMedicion() {
        return idMedicion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getMilimetros() {
        return milimetros;
    }

    //Setters
    public void setIdMedicion(int idMedicion){
        this.idMedicion = idMedicion;
    }
    public void setFechaHora(LocalDateTime fechaHora){
        this.fechaHora = fechaHora;
    }
    public void setMilimetros(double milimetros){
        this.milimetros = milimetros;
    }
    public void setAnomala(boolean anomala){
        this.anomala = anomala;
    }

    //retorna true si es anomala
    public boolean isAnomala() {
        return anomala;
    }

    @Override
    public String toString() {
    return "Id medicion:" + idMedicion + ", Fecha: " + fechaHora + ", Milimetros: " + milimetros + ", Anomala: " + anomala;
    }

}