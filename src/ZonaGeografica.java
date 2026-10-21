import java.util.ArrayList;
import java.util.List;

public class ZonaGeografica {
    //atributos
    private String idZona;
    private String nombre;
    private NivelVulnerabilidad nivelVulnerabilidad;
    private NivelAlerta nivelAlertaActual;
    private double umbralAmarilla;
    private double umbralNaranja;
    private double umbralRoja;
    
    //relaciones
    private List<ValorHistorico> valoresHistoricos;
    private List<EstacionMeteorologica> estacionesMeteorologicas;
    private List<Alerta> alertas;

    //constructor por defecto
    public ZonaGeografica() {
        this.valoresHistoricos= new ArrayList<ValorHistorico>();
        this.estacionesMeteorologicas= new ArrayList<EstacionMeteorologica>();
        this.alertas= new ArrayList<Alerta>();
    }

    //constructor parametrizdo
    public ZonaGeografica(String idZona, String nombre, NivelVulnerabilidad nivelVulnerabilidad, NivelAlerta nivelAlertaActual, double umbralAmarilla, double umbralNaranja, double umbralRoja) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.nivelVulnerabilidad = nivelVulnerabilidad;
        this.nivelAlertaActual = nivelAlertaActual;
        this.umbralAmarilla = umbralAmarilla;
        this.umbralNaranja = umbralNaranja;
        this.umbralRoja = umbralRoja;
        this.valoresHistoricos= new ArrayList<ValorHistorico>();
        this.estacionesMeteorologicas= new ArrayList<EstacionMeteorologica>();
        this.alertas= new ArrayList<Alerta>();
    }
 

    //getters
    public String getIdZona() {
        return idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public NivelVulnerabilidad getNivelVulnerabilidad() {
        return nivelVulnerabilidad;
    }

    public NivelAlerta getNivelAlertaActual() {
        return nivelAlertaActual;
    }

    public double getUmbralAmarilla() {
        return umbralAmarilla;
    }

    public double getUmbralNaranja() {
        return umbralNaranja;
    }

    public double getUmbralRoja() {
        return umbralRoja;
    }

    public List<ValorHistorico> getValoresHistoricos() {
        return valoresHistoricos;
    }

    public List<EstacionMeteorologica> getEstacionesMeteorologicas() {
        return estacionesMeteorologicas;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }
 
    //setters
    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNivelVulnerabilidad(NivelVulnerabilidad nivelVulnerabilidad) {
        this.nivelVulnerabilidad = nivelVulnerabilidad;
    }

    public void setNivelAlertaActual(NivelAlerta nivelAlertaActual) {
        this.nivelAlertaActual = nivelAlertaActual;
    }

    public void setUmbralAmarilla(double umbralAmarilla) {
        this.umbralAmarilla = umbralAmarilla;
    }

    public void setUmbralNaranja(double umbralNaranja) {
        this.umbralNaranja = umbralNaranja;
    }

    public void setUmbralRoja(double umbralRoja) {
        this.umbralRoja = umbralRoja;
    }

    public void setValoresHistoricos(List<ValorHistorico> valoresHistoricos) {
        this.valoresHistoricos = valoresHistoricos;
    }

    public void setEstacionesMeteorologicas(List<EstacionMeteorologica> estacionesMeteorologicas) {
        this.estacionesMeteorologicas = estacionesMeteorologicas;
    }

    public void setAlertas(List<Alerta> alertas) {
        this.alertas = alertas;
    }
    
    
    //metodo de lluvia acumulada en 24hs
    public double lluviaAcumulada24h() {
        
    }
    
    //metodo evaluar riesgo 
    public NivelAlerta evaluarRiesgo() {

    }
    
    //metodo emitir alerta 
    public Alerta emitirAlerta(TipoEventoClimatico ev) {

    }
    
    //metodo para agregar valor historico
    public void agregarValorHistorico(int mes, double acumuladoMedio){
      this.valoresHistoricos.add(new ValorHistorico (mes, acumuladoMedio));
    }
    
    //metodo para agregar estacion meteorologica
    public void agregarEstacionMeteorologica(EstacionMeteorologica em){
      this.estacionesMeteorologicas.add(em);
    }
    
    //metodo para agregar alerta 
    public void agregarAlerta(Alerta a){
      this.alertas.add(a);
    }

    //toString 
    @Override
    public String toString() {
        return "ZonaGeografica{" + "idZona=" + idZona + ", nombre=" + nombre + ", nivelVulnerabilidad=" + nivelVulnerabilidad + ", nivelAlertaActual=" + nivelAlertaActual + ", umbralAmarilla=" + umbralAmarilla + ", umbralNaranja=" + umbralNaranja + ", umbralRoja=" + umbralRoja + ", valoresHistoricos=" + valoresHistoricos + ", estacionesMeteorologicas=" + estacionesMeteorologicas + ", alertas=" + alertas + '}';
    }



    
}