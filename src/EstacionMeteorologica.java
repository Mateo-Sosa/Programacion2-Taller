import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
/**
 *
 * @author Franco Molina
 */
public class EstacionMeteorologica {
    // Atributos
    private String idEstacion;
    private String nombre;
    private double latitud;
    private double longitud;
    private double altitud;
    private EstadoOperativo estado;
    private TipoEstacion tipo;

    //Relaciones
    private List<Sensor> sensores;
    private ResponsableTecnico responsable;
    private List<Mantenimiento> mantenimientos;

    //constructor por defecto
    public EstacionMeteorologica() {
        this.sensores = new ArrayList<>();
        this.mantenimientos = new ArrayList<>();
    }
    //Constructor parametrizado
     public EstacionMeteorologica(String idEstacion, String nombre, double latitud, double longitud, double altitud, EstadoOperativo estado, TipoEstacion tipo, ResponsableTecnico responsable) {
        this.idEstacion = idEstacion;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
        this.estado = estado;
        this.tipo = tipo;
        this.responsable = responsable;
        this.sensores = new ArrayList<>();
        this.mantenimientos = new ArrayList<>();
    }
    
    //metodo void para instalar sensor indicado en el UML
    public void instalarSensor(Sensor s) {
        sensores.add(s);
    }
    //Metodo void para retirar un sensor indicado en el UML
    public void retirarSensor(Sensor s) {
        sensores.remove(s);
    }

    //Metodo para Gestion de infraestructura meteorologica
    public boolean estaOperativa() {
        return this.estado == EstadoOperativo.OPERATIVO;
    }
    
    //Getters
    public String getIdEstacion() {
        return idEstacion;
    }

    public  String getNombre(){
        return nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public double getAltitud() {
        return altitud;
    }

    public EstadoOperativo getEstado() {
        return estado;
    }

    public TipoEstacion getTipo() {
        return tipo;
    }

    public ResponsableTecnico getResponsable() {
        return responsable;
    }

    public List<Sensor> getSensores() {
        return new ArrayList<>(sensores);
    }

    public List<Mantenimiento> getMantenimientos() {
        return new ArrayList<>(mantenimientos);
    }

    //Setters 
    public void setIdEstacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

      public void setEstado(EstadoOperativo estado) {
        this.estado = estado;
    }

    public void setTipo(TipoEstacion tipo) {
        this.tipo = tipo;
    }

    public void setResponsable(ResponsableTecnico responsable) {
        this.responsable = responsable;
    }
    public void setSensores(List<Sensor> sensores){
        this.sensores = sensores;
    }
    //Metodo agregar sensor
    public void agregarSensor(Sensor s){
        this.sensores.add(s);
    }
    //Metodo agregar Mantenimiento
    public void agregarMantenimiento(String idMantenimiento, LocalDate fecha, TipoMantenimiento tipo, String descripcion, ResponsableTecnico responsable, Sensor sensor){
        Mantenimiento m = new Mantenimiento(idMantenimiento, tipo, fecha, descripcion, responsable, sensor);
        this.mantenimientos.add(m);
        responsable.agregarMantenimiento(m);
        if (sensor != null) {
            sensor.agregarMantenimiento(m);
        }
    }

    //toString
    @Override
    public String toString() {
    return "Estación [" + idEstacion + "] " + nombre + " | Tipo: " + tipo + " | Estado: " + estado + " | Sensores instalados: " + sensores.size();
    }
}
  