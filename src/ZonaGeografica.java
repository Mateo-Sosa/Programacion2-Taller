import java.time.LocalDateTime;
import java.util.ArrayList;

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
    private ArrayList<ValorHistorico> valoresHistoricos;
    private ArrayList<EstacionMeteorologica> estacionesMeteorologicas;
    private ArrayList<Alerta> alertas;

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

    public ArrayList<ValorHistorico> getValoresHistoricos() {
        return valoresHistoricos;
    }

    public ArrayList<EstacionMeteorologica> getEstacionesMeteorologicas() {
        return estacionesMeteorologicas;
    }

    public ArrayList<Alerta> getAlertas() {
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

    public void setValoresHistoricos(ArrayList<ValorHistorico> valoresHistoricos) {
        this.valoresHistoricos = valoresHistoricos;
    }

    public void setEstacionesMeteorologicas(ArrayList<EstacionMeteorologica> estacionesMeteorologicas) {
        this.estacionesMeteorologicas = estacionesMeteorologicas;
    }

    public void setAlertas(ArrayList<Alerta> alertas) {
        this.alertas = alertas;
    }
    
    
    //metodo de lluvia acumulada en 24hs
    public double lluviaAcumulada24h() {
        LocalDateTime desde = LocalDateTime.now().minusHours(24);
        double total = 0;
        for (EstacionMeteorologica estacion : estacionesMeteorologicas) {
            for (Sensor sensor : estacion.getSensores()) {
                for (Medicion m : sensor.getMediciones()) {
                    if (m.getFechaHora().isAfter(desde)) {
                        total += m.getMilimetros();
                    }
                }
            }
        }
        return total;
    }
    
    //metodo evaluar riesgo 
    public NivelAlerta evaluarRiesgo() {
        double acumulado = lluviaAcumulada24h();
        if (acumulado >= umbralRoja) {
            nivelAlertaActual = NivelAlerta.ROJA;
        } else if (acumulado >= umbralNaranja) {
            nivelAlertaActual = NivelAlerta.NARANJA;
        } else if (acumulado >= umbralAmarilla) {
            nivelAlertaActual = NivelAlerta.AMARILLA;
        } else {
            nivelAlertaActual = NivelAlerta.VERDE;
        }
        return nivelAlertaActual;
    }
    
    //metodo emitir alerta 
    public Alerta emitirAlerta(TipoEventoClimatico ev) {
        String id = idZona + "-A" + (alertas.size() + 1);
        String descripcion = "Alerta de " + ev + " en la zona " + nombre;
        Alerta alerta = new Alerta(id, LocalDateTime.now(), nivelAlertaActual, ev, descripcion, false, this);
        alertas.add(alerta);
        return alerta;
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