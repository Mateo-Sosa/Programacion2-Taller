import java.util.ArrayList;
import java.util.List;
public class EquipoRespuesta{
    //Atributos
    private String idEquipo;
    private String nombreEquipo;
    private boolean disponible;

    //Relaciones
    private List<ResponsableTecnico> responsablesTecnicos; 
    private List<Alerta> alertas;
                           
    //Constructor por defecto
    public EquipoRespuesta(){
        this.responsablesTecnicos = new ArrayList<ResponsableTecnico>();
        this.alertas = new ArrayList<Alerta>();
    }

    //Constructor Parametrizado
    public EquipoRespuesta(String idEquipo, String nombreEquipo, boolean disponible){
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.disponible = disponible;
        this.responsablesTecnicos = new ArrayList<ResponsableTecnico>();
        this.alertas = new ArrayList<Alerta>();
    }

    //Getters
    public String getIdEquipo(){
        return idEquipo;
    }
    public String getNombreEquipo(){
        return nombreEquipo;
    }
    public boolean getDisponible(){
        return disponible;
    }
    public List<ResponsableTecnico> getResponsablesTecnicos() {
        return responsablesTecnicos;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    //Setters
    public void setIdEquipo(String idEquipo){
        this.idEquipo = idEquipo;
    }
    public void setNombreEquipo(String nombreEquipo){
        this.nombreEquipo = nombreEquipo;
    }
    public void setDisponible(boolean disponible){
        this.disponible = disponible;
    }
    public void setResponsablesTecnicos(List<ResponsableTecnico> responsablesTecnicos) {
        this.responsablesTecnicos = responsablesTecnicos;
    }

    public void setAlertas(List<Alerta> alertas) {
        this.alertas = alertas;
    }

    //Metodo para agregar integrante
    public void agregarIntegrante(ResponsableTecnico r) {
        this.responsablesTecnicos.add(r);
    }
    //Metodo para agregar alertas
    public void agregarAlerta(Alerta a) {
        this.alertas.add(a);
    }
    //toString
    @Override
    public String toString() {
        return "ID equipo: " + idEquipo + ", Nombre Equipo: " + nombreEquipo + ", Disponible: " + disponible;
    }
}