package src.clases;
import java.time.LocalDateTime;
import java.util.ArrayList;

import src.enums.NivelAlerta;
import src.enums.TipoEventoClimatico;

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
    public void asignarEquipo(Alerta alerta,ArrayList<EquipoRespuesta> equipos) {
        // 1. Validamos si el nivel de la alerta exige un equipo obligatoriamente
        if (alerta.requiereEquipo()) {
            boolean asignado = false;
            
            // 2. Recorremos manualmente la lista buscando un equipo libre
            for (int i = 0; i < equipos.size(); i++) {
                EquipoRespuesta equipo = equipos.get(i);
                
                if (equipo.getDisponible()) {
                    // 3. Asignamos el equipo (usando tu método que hace el doble enlace)
                    alerta.asignarEquipo(equipo);
                    
                    // 4. Cambiamos el estado del equipo para que figure ocupado
                    equipo.setDisponible(false);
                    asignado = true;
                    
                    System.out.println("ÉXITO: Equipo '" + equipo.getNombreEquipo() + "' asignado a la alerta ID " + alerta.getIdAlerta());
                    break; // Cortamos la búsqueda, ya cumplimos la regla
                }
            }
            
            // Si terminó el bucle y la bandera sigue en false, no había equipos libres
            if (!asignado) {
                System.out.println("CRÍTICO: No hay equipos de respuesta disponibles para la alerta ID " + alerta.getIdAlerta());
            }
        } else {
            System.out.println("INFO: La alerta ID " + alerta.getIdAlerta() + " (Nivel " + alerta.getNivel() + ") no requiere equipo.");
        }
    }

    public void asignarEquipo(EquipoRespuesta e) {
        this.equipoRespuesta = e;
        e.agregarAlerta(this);
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