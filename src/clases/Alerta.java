package src.clases;
import java.time.LocalDateTime;

import src.enums.*;
import src.excepciones.*;
/**
 * Representa una alerta emitida en el sistema ante situaciones climáticas de riesgo 
 * en una zona geográfica (como crecidas de ríos o tormentas severas).
 * Gestiona el nivel de severidad, el estado de atención y la asignación de equipos de respuesta.
 */
public class Alerta {
    //atributos
    private int idAlerta;
    private LocalDateTime fechaHoraEmision;
    private NivelAlerta nivel;
    private TipoEventoClimatico evento;
    private String descripcion ;
    private boolean atendida;

    //relaciones
    private EquipoRespuesta equipoRespuesta;
    private ZonaGeografica zonaGeografica;

    //constructor por defecto
    public Alerta() {
    }

    //constructor parametrizado
    public Alerta(int idAlerta, LocalDateTime fechaHoraEmision, NivelAlerta nivel, TipoEventoClimatico evento, String descripcion, boolean atendida, ZonaGeografica zonaGeografica) {
        this.idAlerta = idAlerta;
        this.fechaHoraEmision = fechaHoraEmision;
        this.nivel = nivel;
        this.evento = evento;
        this.descripcion = descripcion;
        this.atendida = atendida;
        this.zonaGeografica = zonaGeografica;
    }
    
    //getters
    public int getIdAlerta() {
        return idAlerta;
    }

    public LocalDateTime getFechaHoraEmision() {
        return fechaHoraEmision;
    }

    public NivelAlerta getNivel() {
        return nivel;
    }

    public TipoEventoClimatico getEvento() {
        return evento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public EquipoRespuesta getEquipoRespuesta() {
        return equipoRespuesta;
    }

    public ZonaGeografica getZonaGeografica() {
        return zonaGeografica;
    }

    //settera
    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
    }

    public void setFechaHoraEmision(LocalDateTime fechaHoraEmision) {
        this.fechaHoraEmision = fechaHoraEmision;
    }

    public void setNivel(NivelAlerta nivel) {
        this.nivel = nivel;
    }

    public void setEvento(TipoEventoClimatico evento) {
        this.evento = evento;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }

    public void setEquipoRespuesta(EquipoRespuesta equipoRespuesta) {
        this.equipoRespuesta = equipoRespuesta;
    }

    public void setZonaGeografica(ZonaGeografica zonaGeografica) {
        this.zonaGeografica = zonaGeografica;
    }

    // metodo asignar equipo
    public void asignarEquipo(EquipoRespuesta e) throws AsignacionInvalidaException{
        // Validamos si el nivel de la alerta exige un equipo (Naranja o Roja)
        if (!this.requiereEquipo()) {
            throw new AsignacionInvalidaException("La alerta ID " + this.getIdAlerta() + " (Nivel " + this.getNivel() + ") no requiere equipo de respuesta");
        }
        //Validamos si el nivel de la alerta exige un equipo
        if (this.requiereEquipo()) {
            
            //Validamos que el equipo este libre
            if (e.getDisponible()) {
                
                this.equipoRespuesta = e;
                e.agregarAlerta(this);
                
                //Cambiamos el estado del equipo
                e.setDisponible(false);

                
                System.out.println("EXITO: Equipo " + e.getNombreEquipo() + "asignado a la alerta ID " + this.getIdAlerta());
                
            } else {
                System.out.println("ERROR: El equipo '" + e.getNombreEquipo() + " no esta disponible en este momento");
            }
            
        } else {
            System.out.println("INFO: La alerta ID " + this.getIdAlerta() + " (Nivel " + this.getNivel() + ") no requiere equipo");
        }
    }

    // metodo si requiere equipo
    public boolean requiereEquipo(){
        return nivel == NivelAlerta.NARANJA || nivel == NivelAlerta.ROJA;
    }

    //toString
    @Override
    public String toString() {
        return "Alerta{" + "idAlerta=" + idAlerta + ", fechaHoraEmision=" + fechaHoraEmision + ", nivel=" + nivel + ", evento=" + evento + ", descripcion=" + descripcion + ", atendida=" + atendida + ", equipoRespuesta=" + equipoRespuesta + ", zonaGeografica=" + zonaGeografica + '}';
    }
}