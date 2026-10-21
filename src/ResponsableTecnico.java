import java.util.ArrayList;
import java.util.List;
public class ResponsableTecnico extends Persona{
    //Atributos
    private String especialidad;
    private String numeroLicencia;

    //Relaciones
    private List<EstacionMeteorologica> estaciones;
    private List<Mantenimiento> mantenimientos;

    //Constructor por defecto
    public ResponsableTecnico(){
        this.estaciones = new ArrayList<EstacionMeteorologica>();
        this.mantenimientos = new ArrayList<Mantenimiento>();
    }

    //Constructor Parametrizado
    public ResponsableTecnico(String dni, String nombre, String apellido, String correoElectronico, String celular, String especialidad, String numeroLicencia){
        super(dni, nombre, apellido, correoElectronico, celular);
        this.especialidad = especialidad;
        this.numeroLicencia = numeroLicencia;
        this.estaciones = new ArrayList<EstacionMeteorologica>();
        this.mantenimientos = new ArrayList<Mantenimiento>();
    }

    //Getters
    public String getEspecialidad(){
        return especialidad;
    }
    public String getNumeroLicencia(){
        return numeroLicencia;
    }
    public List<EstacionMeteorologica> getEstaciones() {
        return estaciones;
    }

    public List<Mantenimiento> getMantenimientos() {
        return mantenimientos;
    }

    //Setters
    public void setEspecialidad(String especialidad){
        this.especialidad = especialidad;
    }
    public void setNumeroLicencia(String numeroLicencia){
        this.numeroLicencia = numeroLicencia;
    }
    public void setEstaciones(List<EstacionMeteorologica> estaciones) {
        this.estaciones = estaciones;
    }
    public void setMantenimientos(List<Mantenimiento> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    //Metodo agregar alerta
    public void agregarEstacion(EstacionMeteorologica e) {
        this.estaciones.add(e);
    }
    //Metodo agregar mantenimiento
    public void agregarMantenimiento(Mantenimiento m) {
        this.mantenimientos.add(m);
    }

    //toString
    @Override
    public String toString() {
        return "Especialidad: " + especialidad + ", Numero de licencia: " + numeroLicencia;
    }
}
