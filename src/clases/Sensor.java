package src.clases;
import java.util.List;

import src.enums.EstadoOperativo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Franco Molina
 */

public class Sensor {
    //Atributos
    private int idSensor;
    private String marca;
    private String modelo;
    private double precision;
    private EstadoOperativo estado;
    private LocalDate ultimaCalibracion;

    //Relaciones
    private List<Medicion> mediciones;
    private List<Mantenimiento> mantenimientos;
    private EstacionMeteorologica estacion;

    //Constructor por defecto
    public Sensor(){
        this.mediciones = new ArrayList<Medicion>();
        this.mantenimientos = new ArrayList<Mantenimiento>();
    }

    //Constructor parametrizado
    public Sensor(int idSensor, String marca, String modelo, double precision, EstadoOperativo estado, LocalDate ultimaCalibracion) {
        this.idSensor = idSensor;
        this.marca = marca;
        this.modelo = modelo;
        this.precision = precision;
        this.estado = estado;
        this.ultimaCalibracion = ultimaCalibracion;
        this.mediciones = new ArrayList<Medicion>();
        this.mantenimientos = new ArrayList<Mantenimiento>();
    }

    public Medicion registrar(double mm, LocalDateTime fh) {
        //Control operativo (Sensor)
        if (this.estado != EstadoOperativo.OPERATIVO) {
            System.out.println("Registro denegado: El sensor no se encuentra operativo");
            return null;
        }

        //Control operativo (Estacion)
        if (this.estacion != null) {
            EstadoOperativo estadoEst = this.estacion.getEstado();
            if (estadoEst == EstadoOperativo.EN_MANTENIMIENTO || estadoEst == EstadoOperativo.FUERA_DE_SERVICIO) {
                System.out.println("Registro denegado: La estacion esta en estado " + estadoEst);
                return null;
            }
        } else {
            System.out.println("Error: El sensor no esta asignado a ninguna estacion");
            return null;
        }

        //Validacion de lecturas
        if (!validar(mm)) {
            System.out.println("Registro denegado: Valor de lectura invalido");
            return null;
        }

        //Deteccion de lectura anomala
        boolean esAnomala = false;
        if (!mediciones.isEmpty()) {
            Medicion ultima = mediciones.get(mediciones.size() - 1);
            double diferencia = mm - ultima.getMilimetros();
            
            //Verificacion de la diferencia absoluta
            if (diferencia > 100) {
                esAnomala = true;
            }
        }

        //Registro de la medicion
        int id = mediciones.size() + 1;
        Medicion nueva = new Medicion(id, fh, mm, esAnomala);
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
   public int getIdSensor() {
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
    public EstacionMeteorologica getEstacion() {
        return estacion;
    }
 
    //setters
    public void setIdSensor(int idSensor){
        this.idSensor = idSensor;
    }
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
    public void setEstacion(EstacionMeteorologica estacion) {
        this.estacion = estacion;
    }

    //Metodo agregar medicion
    public void agregarMedicion(int idMedicion, LocalDateTime fechaHora, double milimetros, boolean anomala){
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