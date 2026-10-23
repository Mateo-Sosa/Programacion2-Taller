import java.time.LocalDateTime;

public class Alerta {
    //atributos
    private String idAlerta;
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
    public Alerta(String idAlerta, LocalDateTime fechaHoraEmision, NivelAlerta nivel, TipoEventoClimatico evento, String descripcion, boolean atendida, ZonaGeografica zonaGeografica) {
        this.idAlerta = idAlerta;
        this.fechaHoraEmision = fechaHoraEmision;
        this.nivel = nivel;
        this.evento = evento;
        this.descripcion = descripcion;
        this.atendida = atendida;
        this.zonaGeografica = zonaGeografica;
    }
    
    //getters
    public String getIdAlerta() {
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
    public void setIdAlerta(String idAlerta) {
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