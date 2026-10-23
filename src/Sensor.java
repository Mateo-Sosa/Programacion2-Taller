import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Franco Molina
 */

public class Sensor {
    //Atributos
    private String idSensor;
    private String marca;
    private String modelo;
    private double precision;
    private EstadoOperativo estado;
    private LocalDate ultimaCalibracion;

    //Relaciones
    private List<Medicion> mediciones; //composicion con la clase medicion
    private List<Mantenimiento> mantenimientos;

    //Constructor por defecto
    public Sensor(){
        this.mediciones = new ArrayList<Medicion>();
        this.mantenimientos = new ArrayList<Mantenimiento>();
    }

    //Constructor parametrizado
    public Sensor(String idSensor, String marca, String modelo, double precision, EstadoOperativo estado, LocalDate ultimaCalibracion) {
        this.idSensor = idSensor;
        this.marca = marca;
        this.modelo = modelo;
        this.precision = precision;
        this.estado = estado;
        this.ultimaCalibracion = ultimaCalibracion;
        this.mediciones = new ArrayList<Medicion>();
        this.mantenimientos = new ArrayList<Mantenimiento>();
    }

    //metodo incompleto falta validar el valor y si la medicion es anomala
    public Medicion registrar(double mm, LocalDateTime fh){
        String id = idSensor + "-" + (mediciones.size() + 1);
        Medicion nueva = new Medicion(id, fh, mm, false);
        mediciones.add(nueva);
        return nueva;
    }
    
    //metodo para validar
    public boolean validar(double mm) {
        return mm >= 0 && mm <= 500;
    }

    //metodo para verificar si requiere calibracion
    public boolean requiereCalibracion() {
        return LocalDate.now().isAfter(ultimaCalibracion.plusMonths(12));
    }
   
   //Getters
   public String getIdSensor() {
        return idSensor;
    }
 
    public String getMarca() {
        return marca;
    }
 
    public String getModelo() {
        return modelo;
    }
 
    public double getPrecision() {
        return precision;
    }
 
    public EstadoOperativo getEstado() {
        return estado;
    }
 
    public LocalDate getUltimaCalibracion() {
        return ultimaCalibracion;
    }
 
    public List<Medicion> getMediciones() {
        return mediciones;
    }

    public List<Mantenimiento> getMantenimientos(){
        return mantenimientos;
    }
 
    //setters
    public void setMarca(String marca) {
        this.marca = marca;
    }
 
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
 
    public void setPrecision(double precision) {
        this.precision = precision;
    }
 
    public void setEstado(EstadoOperativo estado) {
        this.estado = estado;
    }
 
    public void setUltimaCalibracion(LocalDate ultimaCalibracion) {
        this.ultimaCalibracion = ultimaCalibracion;
    }
   
    public void setMediciones(List<Medicion> mediciones){
        this.mediciones = mediciones;
    }

    public void setMantenimientos(List<Mantenimiento> mantenimientos){
        this.mantenimientos = mantenimientos;
    }

    //Metodo agregar medicion
    public void agregarMedicion(String idMedicion, LocalDateTime fechaHora, double milimetros, boolean anomala){
        this.mediciones.add(new Medicion(idMedicion, fechaHora, milimetros, anomala));
    }
    //Metodo agregar mantenimiento
    public void agregarMantenimiento(Mantenimiento man){
        this.mantenimientos.add(man);
    }

    //toString
    @Override
    public String toString() {
        return "Sensor " + idSensor + " (" + marca + " " + modelo + ") | Estado: " + estado + " | Mediciones: " + mediciones.size();
    }

}