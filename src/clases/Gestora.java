package src.clases;
 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
 
import src.enums.*;
 
/**
 * Clase principal de gestión y lógica de negocios del sistema meteorológico.
 * Administra las colecciones de zonas geográficas, estaciones, sensores, alertas y personal técnico.
 */
public class Gestora {
 
    //listas generales
    private List<ZonaGeografica> zonas;
    private List<EstacionMeteorologica> estaciones;
    private List<Sensor> sensores;
    private List<ResponsableTecnico> responsables;
    private List<EquipoRespuesta> equipos;
 
    //constructor: inicia las listas
    public Gestora() {
        this.zonas = new ArrayList<ZonaGeografica>();
        this.estaciones = new ArrayList<EstacionMeteorologica>();
        this.sensores = new ArrayList<Sensor>();
        this.responsables = new ArrayList<ResponsableTecnico>();
        this.equipos = new ArrayList<EquipoRespuesta>();
    }
 
    //getters
    public List<ZonaGeografica> getZonas() {
        return zonas;
    }
 
    public List<EstacionMeteorologica> getEstaciones() {
        return estaciones;
    }
 
    public List<Sensor> getSensores() {
        return sensores;
    }
 
    public List<ResponsableTecnico> getResponsables() {
        return responsables;
    }
 
    public List<EquipoRespuesta> getEquipos() {
        return equipos;
    }
    
    //metodos para agregar
    public void agregarZona(ZonaGeografica zona) {
        this.zonas.add(zona);
    }
 
    public void agregarEstacion(EstacionMeteorologica estacion) {
        this.estaciones.add(estacion);
    }
 
    public void agregarSensor(Sensor sensor) {
        this.sensores.add(sensor);
    }
 
    public void agregarResponsable(ResponsableTecnico responsable) {
        this.responsables.add(responsable);
    }
 
    public void agregarEquipo(EquipoRespuesta equipo) {
        this.equipos.add(equipo);
    }
 
    // HISTORICO DE PRECIPITACIONES
    public void reportePrecipitacionesHistoricas() {
        System.out.println("\n REPORTE: HISTORICO DE PRECIPITACIONES POR ZONA Y ESTACION");
        
        if (zonas.isEmpty()) {
            System.out.println("No hay zonas registradas en el sistema");
            return;
        }

        LocalDateTime ahora = LocalDateTime.now();
        int mesActual = ahora.getMonthValue(); // Obtenemos el numero del mes actual (1 a 12)
        int anioActual = ahora.getYear();

        // Recorremos todas las zonas
        for (ZonaGeografica zona : zonas) {
            double acumuladoDiarioZona = 0;
            double acumuladoSemanalZona = 0;
            double acumuladoMensualZona = 0;

            System.out.println(" Zona: " + zona.getNombre() + " (ID: " + zona.getIdZona() + ")");
            
            if (zona.getEstacionesMeteorologicas().isEmpty()) {
                System.out.println(" Sin estaciones instaladas en esta zona");
            } else {
                //  Recorremos las estaciones de esa zona
                for (EstacionMeteorologica estacion : zona.getEstacionesMeteorologicas()) {
                    double diarioEst = 0;
                    double semanalEst = 0;
                    double mensualEst = 0;
                    
                    // Recorremos los sensores y sus mediciones
                    for (Sensor sensor : estacion.getSensores()) {
                        for (Medicion medicion : sensor.getMediciones()) {
                            LocalDateTime fh = medicion.getFechaHora();
                            double mm = medicion.getMilimetros();
                            
                            // Acumulado Diario (ultimas 24 horas)
                            if (fh.isAfter(ahora.minusHours(24))) {
                                diarioEst += mm;
                            }
                            
                            // Acumulado Semanal (últimos 7 días)
                            if (fh.isAfter(ahora.minusDays(7))) {
                                semanalEst += mm;
                            }
                            
                            // Acumulado Mensual (del mes y año en curso)
                            if (fh.getMonthValue() == mesActual && fh.getYear() == anioActual) {
                                mensualEst += mm;
                            }
                        }
                    }
                    
                    // Imprimimos el desglose por estación
                    System.out.println("   - Estación '" + estacion.getNombre() + "': Diario=" + diarioEst + " mm | Semanal=" + semanalEst + " mm | Mensual=" + mensualEst + " mm");
                    
                    // Sumamos al total de la zona
                    acumuladoDiarioZona += diarioEst;
                    acumuladoSemanalZona += semanalEst;
                    acumuladoMensualZona += mensualEst;
                }
            }
            
            // Imprimimos el total consolidado de la zona
            System.out.println("   [TOTAL" + zona.getNombre().toUpperCase() + "]: Diario=" + acumuladoDiarioZona + " mm | Semanal=" + acumuladoSemanalZona + " mm | Mensual=" + acumuladoMensualZona + " mm");
            
            // Comparacion historica de la zona
            boolean tieneHistoria = false;
            double mediaHistorica = 0;
            
            // Buscamos si la zona tiene un ValorHistorico cargado para este mes exacto
            for (ValorHistorico vh : zona.getValoresHistoricos()) {
                if (vh.getMes() == mesActual) {
                    mediaHistorica = vh.getAcumuladoMedio();
                    tieneHistoria = true;
                    break;
                }
            }
            
            if (tieneHistoria) {
                System.out.println("   [ANALISIS HISTORICO - Mes " + mesActual + "]: La media histOrica de la zona es " + mediaHistorica + " mm.");
                double diferencia = acumuladoMensualZona - mediaHistorica;
                
                if (diferencia > 0) {
                    System.out.println("Las precipitaciones superaron la media historica por " + diferencia + " mm.");
                } else if (diferencia < 0) {
                    System.out.println("Las precipitaciones están " + (diferencia) + " mm por debajo de la media historica.");
                } else {
                    System.out.println("Las precipitaciones igualan exactamente la media historica.");
                }
            } else {
                System.out.println("   [ANALISIS HISTORICO - Mes " + mesActual + "]: No hay valores historicos registrados para comparar este mes");
            }
        }
    }

    // RANKING DE ESTACIONES
    /**
     * Ranking de estaciones de mayor a menor acumulado de precipitacion entre dos fechas (inclusive).
     */
    public void rankingEstaciones(LocalDate desde, LocalDate hasta) {
        //se ordena una copia para no cambiar el orden de la lista de la gestora
        List<EstacionMeteorologica> ranking = new ArrayList<EstacionMeteorologica>(estaciones);
        Comparator<EstacionMeteorologica> mayorAcumulado = (a, b) -> Double.compare(acumuladoEstacion(b, desde, hasta), acumuladoEstacion(a, desde, hasta));
        Collections.sort(ranking, mayorAcumulado);
 
        System.out.println("RANKING DE ESTACIONES (" + desde + " al " + hasta + ")");
        int puesto = 1;
        for (EstacionMeteorologica estacion : ranking) {
            System.out.println(puesto + ". " + estacion.getNombre() + " [" + estacion.getIdEstacion() + "] - "+ acumuladoEstacion(estacion, desde, hasta) + " mm");puesto++;
        }
        System.out.println();
    }
 
    // HISTORIAL DE ALERTAS 
    /**
     * Historial de alertas emitidas, ordenado por fecha. Cada filtro es opcional: si se pasa null no se aplica.
     */
    public void historialAlertas(NivelAlerta nivel, LocalDate desde, LocalDate hasta, ZonaGeografica zonaFiltro) {
        List<Alerta> alertas = new ArrayList<Alerta>();
        for (ZonaGeografica zona : zonas) {
            for (Alerta alerta : zona.getAlertas()) {
                LocalDate fecha = alerta.getFechaHoraEmision().toLocalDate();
                if ((zonaFiltro == null || zona == zonaFiltro) && (nivel == null || alerta.getNivel() == nivel)
                        && (desde == null || !fecha.isBefore(desde)) && (hasta == null || !fecha.isAfter(hasta))) {
                    alertas.add(alerta);
                }
            }
        }
        Comparator<Alerta> porFecha = (a, b) -> a.getFechaHoraEmision().compareTo(b.getFechaHoraEmision());
        Collections.sort(alertas, porFecha);
 
        System.out.println("=== HISTORIAL DE ALERTAS ===");
        for (Alerta alerta : alertas) {
            String equipo = (alerta.getEquipoRespuesta() == null) ? "sin equipo" : alerta.getEquipoRespuesta().getNombreEquipo();
            System.out.println(alerta.getFechaHoraEmision() + " | " + alerta.getZonaGeografica().getNombre() + " | " + alerta.getNivel()
                    + " | " + alerta.getEvento() + " | " + equipo + " | " + alerta.getDescripcion());
        }
        System.out.println("Total de alertas: " + alertas.size());
    }
 
    //  FICHA TECNICA OPERATIVA 
    /**
     * Ficha tecnica de una estacion: datos, responsable, estado de sus sensores y sus ultimas lecturas
     */
    public void fichaTecnica(EstacionMeteorologica estacion) {
        ResponsableTecnico responsable = estacion.getResponsable();
        System.out.println(" FICHA TECNICA - " + estacion.getNombre() + " [" + estacion.getIdEstacion() + "] | " + estacion.getTipo() + " | " + estacion.getEstado());
        System.out.println("Responsable: " + responsable.getNombreCompleto() + " | " + responsable.getEspecialidad() + " | licencia "
                + responsable.getNumeroLicencia() + " | " + responsable.getCorreoElectronico() + " | " + responsable.getCelular());
        
        Comparator<Medicion> porFecha = (a, b) -> a.getFechaHora().compareTo(b.getFechaHora());
        for (Sensor sensor : estacion.getSensores()) {
            System.out.println("Sensor " + sensor.getIdSensor() + " " + sensor.getMarca() + " " + sensor.getModelo() + " | " + sensor.getEstado()
                    + " | ultima calibracion: " + sensor.getUltimaCalibracion() + " | requiere calibracion: " + sensor.requiereCalibracion());
            List<Medicion> lecturas = new ArrayList<Medicion>(sensor.getMediciones());
            Collections.sort(lecturas, porFecha);
            for (int i = 0; i < lecturas.size(); i++) {
            // Si la posición actual 'i' es mayor o igual al tamaño total menos 3, lo mostramos
                if (i >= lecturas.size() - 3) {
                Medicion medicion = lecturas.get(i);
                System.out.println(medicion.getFechaHora() + " | " + medicion.getMilimetros() + " mm" + (medicion.isAnomala() ? " (ANOMALA)" : ""));
                }
            }
        }
    }

    //  FALLAS Y MANTENIMIENTO 
    /**
     * Informe de fallas: sensores fuera de servicio o en mantenimiento, lecturas anomalas y sensores que requieren calibracion.
     */
    public void informeFallas() {
        System.out.println("=== INFORME DE FALLAS Y MANTENIMIENTO ===");
        for (Sensor sensor : sensores) {
            if (sensor.getEstado() != EstadoOperativo.OPERATIVO) {
                System.out.println("FALLA: sensor " + sensor.getIdSensor() + " " + sensor.getEstado());
            }
            if (sensor.requiereCalibracion()) {
                System.out.println("CALIBRACION: sensor " + sensor.getIdSensor() + " (ultima calibracion: " + sensor.getUltimaCalibracion() + ")");
            }
            for (Medicion medicion : sensor.getMediciones()) {
                if (medicion.isAnomala()) {
                    System.out.println("ANOMALA: sensor " + sensor.getIdSensor() + " " + medicion.getMilimetros() + " mm el " + medicion.getFechaHora().withNano(0));
                }
            }
        }
    }
 
    //  ANOMALIA CLIMATICA 
    /**
     * Porcentaje de desviacion del acumulado de un mes de la zona respecto de su media historica
     * Un valor positivo indica mas lluvia que la media.
     */
    public void anomaliaClimatica(ZonaGeografica zona, int anio, int mes) {
        LocalDate inicio = LocalDate.of(anio, mes, 1);
        LocalDate fin = inicio.plusMonths(1).minusDays(1);
        double total = acumulado(zona.getEstacionesMeteorologicas(), inicio, fin);
        double media = mediaHistorica(zona, mes);
 
        System.out.println("ANOMALIA CLIMATICA - ZONA " + zona.getNombre() + " - " + mes + "/" + anio);
        System.out.println("Acumulado del mes: " + total + " mm");
        if (media == 0) {
            System.out.println("Sin valor historico para comparar");
        } else {
            System.out.println("Media historica: " + media + " mm");
            System.out.println("Desvio: " + ((total - media) / media * 100) + "%");
        }
        System.out.println();
    }
 
    //  Metodos auxiliares 
    //milimetros acumulados entre dos fechas (inclusive) por una lista de estaciones
    private double acumulado(List<EstacionMeteorologica> lista, LocalDate desde, LocalDate hasta) {
        double total = 0;
        for (EstacionMeteorologica estacion : lista) {
            total = total + acumuladoEstacion(estacion, desde, hasta);
        }
        return total;
    }
 
    //milimetros acumulados entre dos fechas (inclusive) por una estacion
    private double acumuladoEstacion(EstacionMeteorologica estacion, LocalDate desde, LocalDate hasta) {
        double total = 0;
        for (Sensor sensor : estacion.getSensores()) {
            for (Medicion medicion : sensor.getMediciones()) {
                LocalDate dia = medicion.getFechaHora().toLocalDate();
                if (!dia.isBefore(desde) && !dia.isAfter(hasta)) {
                    total = total + medicion.getMilimetros();
                }
            }
        }
        return total;
    }
 
    //media historica de la zona para un mes (1 a 12). Devuelve 0 si no hay dato
    private double mediaHistorica(ZonaGeografica zona, int mes) {
        if (zona != null) {
            for (ValorHistorico valor : zona.getValoresHistoricos()) {
                if (valor.getMes() == mes) {
                    return valor.getAcumuladoMedio();
                }
            }
        }
        return 0;
    }
}