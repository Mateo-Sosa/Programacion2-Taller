package src.clases;
import java.time.LocalDateTime;
import java.util.ArrayList;

import src.enums.NivelAlerta;
import src.enums.NivelVulnerabilidad;
import src.enums.TipoEventoClimatico;

public class ZonaGeografica {
    //atributos
    private int idZona;
    private String nombre;
    private NivelVulnerabilidad nivelVulnerabilidad;
    private NivelAlerta nivelAlertaActual;

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
    public ZonaGeografica(int idZona, String nombre, NivelVulnerabilidad nivelVulnerabilidad, NivelAlerta nivelAlertaActual) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.nivelVulnerabilidad = nivelVulnerabilidad;
        this.nivelAlertaActual = nivelAlertaActual;
        this.valoresHistoricos= new ArrayList<ValorHistorico>();
        this.estacionesMeteorologicas= new ArrayList<EstacionMeteorologica>();
        this.alertas= new ArrayList<Alerta>();
    }
 

    //getters
    public int getIdZona() {
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
    public void setIdZona(int idZona) {
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
        // 1. Obtenemos la lluvia acumulada de los sensores
        double acumulado = lluviaAcumulada24h();
        boolean superoUmbral = false;
        double umbralrojo = 150;
        double umbralnaranja = 100;
        double umbralamarilla = 50;

        // 2. Evaluamos de mayor a menor para actualizar el nivel
        if (acumulado >= umbralrojo) {
            this.nivelAlertaActual = NivelAlerta.ROJA;
            superoUmbral = true;
        } else if (acumulado < umbralnaranja) {
            this.nivelAlertaActual = NivelAlerta.NARANJA;
            superoUmbral = true;
        } else if (acumulado >= umbralamarilla) {
            this.nivelAlertaActual = NivelAlerta.AMARILLA;
            superoUmbral = true;
        } else if (acumulado < umbralamarilla){
            this.nivelAlertaActual = NivelAlerta.VERDE;
            superoUmbral = false;
        }

        // 3. Disparo de alerta si corresponde
        if (superoUmbral) {
            this.emitirAlerta(TipoEventoClimatico.LLUVIA_COPIOSA);
        }
        return this.nivelAlertaActual;
    }
    
    //metodo emitir alerta 
    public Alerta emitirAlerta(TipoEventoClimatico ev) {
        int id = (alertas.size() + 1);
        String descripcion = "Alerta de " + ev + " en la zona " + nombre;
        Alerta alerta = new Alerta(id, LocalDateTime.now(), nivelAlertaActual, ev, descripcion, false, this);
        alertas.add(alerta);
        return alerta;
    }
    // Método para registro manual por un operador
    public Alerta registrarAlertaManual(TipoEventoClimatico evento, NivelAlerta nivel, String descripcion) {
        // 1. Validamos que el tipo de evento sea uno de los permitidos para registro manual
        if (evento != TipoEventoClimatico.CRECIDA_RIO && evento != TipoEventoClimatico.TORMENTA_SEVERA) {
            System.out.println("ERROR: El operador solo puede registrar alertas de CRECIDA_RIO o TORMENTA_SEVERA.");
            return null; 
        }
        
        // 2. Generamos el ID secuencial basado en el tamaño actual de la lista
        int id = (this.alertas.size() + 1);
        
        // 3. Instanciamos la alerta usando el nivel y la descripción que pasó el operador por parámetro
        Alerta nuevaAlerta = new Alerta(id, LocalDateTime.now(), nivel, evento, descripcion, false, this);
        
        // 4. Agregamos a la lista de alertas de la zona
        this.alertas.add(nuevaAlerta);
        
        System.out.println("ÉXITO: Operador registró una alerta de " + evento + " (Nivel: " + nivel + ") en la zona '" + this.nombre + "'.");
        
        return nuevaAlerta;
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
        return "ZonaGeografica{" + "idZona=" + idZona + ", nombre=" + nombre + ", nivelVulnerabilidad=" + nivelVulnerabilidad + ", nivelAlertaActual=" + nivelAlertaActual + "valoresHistoricos=" + valoresHistoricos + ", estacionesMeteorologicas=" + estacionesMeteorologicas + ", alertas=" + alertas + '}';
    }
}