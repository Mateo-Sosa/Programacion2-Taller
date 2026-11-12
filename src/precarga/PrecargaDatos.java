package src.precarga;

import java.time.LocalDate;
import java.time.LocalDateTime;

import src.enums.*;
import src.clases.*;
import src.excepciones.*;

public class PrecargaDatos {

    public static void cargar(Gestora gestora) {
        
        System.out.println("Cargando datos iniciales extendidos del sistema...");

        // ================= 1. EQUIPOS DE RESPUESTA =================
        EquipoRespuesta eq1 = new EquipoRespuesta(1, "Defensa Civil Litoral", true);
        EquipoRespuesta eq2 = new EquipoRespuesta(2, "Bomberos Voluntarios", true);
        EquipoRespuesta eq3 = new EquipoRespuesta(3, "Brigada de Emergencia Vial", true);
        gestora.agregarEquipo(eq1);
        gestora.agregarEquipo(eq2);
        gestora.agregarEquipo(eq3);

        // ================= 2. RESPONSABLES TÉCNICOS =================
        ResponsableTecnico rt1 = new ResponsableTecnico(11223344, "Ana", "Gomez", "ana@mail.com", "3456112233", "Ingeniera Ambiental", "LIC-9901");
        ResponsableTecnico rt2 = new ResponsableTecnico(55667788, "Luis", "Perez", "luis@mail.com", "3456998877", "Técnico Electrónico", "LIC-3344");
        ResponsableTecnico rt3 = new ResponsableTecnico(99887766, "Carla", "Benitez", "carla@mail.com", "3456445566", "Hidróloga", "LIC-7788");
        gestora.agregarResponsable(rt1);
        gestora.agregarResponsable(rt2);
        gestora.agregarResponsable(rt3);

        // ================= 3. ZONAS GEOGRÁFICAS =================
        ZonaGeografica zona1 = new ZonaGeografica(1, "Zona Litoral (Concordia)", NivelVulnerabilidad.ALTO, NivelAlerta.VERDE);
        ZonaGeografica zona2 = new ZonaGeografica(2, "Zona Centro (Paraná)", NivelVulnerabilidad.MEDIO, NivelAlerta.VERDE);
        ZonaGeografica zona3 = new ZonaGeografica(3, "Zona Sur (Gualeguaychú)", NivelVulnerabilidad.MUY_ALTO, NivelAlerta.VERDE);
        
        // Cargamos valores históricos (Mes 11 = Noviembre)
        zona1.agregarValorHistorico(11, 120.0);
        zona2.agregarValorHistorico(11, 95.0);
        zona3.agregarValorHistorico(11, 140.0);

        gestora.agregarZona(zona1);
        gestora.agregarZona(zona2);
        gestora.agregarZona(zona3);

        // ================= 4. ESTACIONES METEOROLÓGICAS =================
        EstacionMeteorologica est1 = new EstacionMeteorologica(10, "Base Norte", -31.39, -57.95, 30.0, EstadoOperativo.OPERATIVO, TipoEstacion.AUTOMATICA, rt1);
        EstacionMeteorologica est2 = new EstacionMeteorologica(11, "Base Sur", -31.73, -60.52, 40.0, EstadoOperativo.OPERATIVO, TipoEstacion.CONVENCIONAL, rt2);
        EstacionMeteorologica est3 = new EstacionMeteorologica(12, "Base Río Uruguay", -33.01, -58.51, 15.0, EstadoOperativo.OPERATIVO, TipoEstacion.AUTOMATICA, rt3);
        
        zona1.agregarEstacionMeteorologica(est1);
        zona2.agregarEstacionMeteorologica(est2);
        zona3.agregarEstacionMeteorologica(est3);
        
        gestora.agregarEstacion(est1);
        gestora.agregarEstacion(est2);
        gestora.agregarEstacion(est3);

        // ================= 5. SENSORES =================
        Sensor s1 = new Sensor(101, "Pluvius", "Pro-X", 0.1, EstadoOperativo.OPERATIVO, LocalDate.now().minusMonths(2));
        Sensor s2 = new Sensor(102, "Pluvius", "Pro-X", 0.1, EstadoOperativo.OPERATIVO, LocalDate.now().minusYears(2)); // Requiere calibración (>1 año)
        Sensor s3 = new Sensor(103, "SenseRain", "Basic", 0.5, EstadoOperativo.FUERA_DE_SERVICIO, LocalDate.now().minusMonths(5));
        Sensor s4 = new Sensor(104, "HydroTech", "Ultra", 0.05, EstadoOperativo.OPERATIVO, LocalDate.now().minusMonths(1));
        Sensor s5 = new Sensor(105, "StormMaster", "V2", 0.2, EstadoOperativo.OPERATIVO, LocalDate.now().minusMonths(3));

        // Instalación de sensores con manejo de excepciones
        try {
            est1.instalarSensor(s1);
            est1.instalarSensor(s2);
            est2.instalarSensor(s4);
            est3.instalarSensor(s5);
        } catch (AsignacionInvalidaException e) {
            System.out.println("Aviso Precarga Instalación: " + e.getMessage());
        }

        try {
            // Este lanzará excepción porque s3 está FUERA_DE_SERVICIO
            est2.instalarSensor(s3);
        } catch (AsignacionInvalidaException e) {
            System.out.println("-> EXCEPCIÓN CAPTURADA EN PRECARGA (Instalación s3 fuera de servicio): " + e.getMessage());
        }

        gestora.agregarSensor(s1);
        gestora.agregarSensor(s2);
        gestora.agregarSensor(s3);
        gestora.agregarSensor(s4);
        gestora.agregarSensor(s5);

        // ================= 6. MEDICIONES Y SIMULACIÓN DE LLUVIA =================
        LocalDateTime ahora = LocalDateTime.now();
        
        try {
            // Lluvias para est1 (Base Norte - Zona 1)
            s1.registrar(25.0, ahora.minusDays(2)); 
            s1.registrar(35.5, ahora.minusHours(10)); 
            
            // Lluvia fuerte y anómala para s2 (Base Norte - Zona 1) -> Disparará alerta de riesgo
            s2.registrar(110.0, ahora.minusHours(2)); 

            // Lluvias para est2 (Base Sur - Zona 2)
            s4.registrar(40.0, ahora.minusDays(1));
            s4.registrar(15.0, ahora.minusHours(4));

            // Lluvias para est3 (Base Río Uruguay - Zona 3)
            s5.registrar(60.0, ahora.minusDays(1));
            s5.registrar(50.0, ahora.minusHours(3));
            
        } catch (LecturaInvalidaException | AsignacionInvalidaException e) {
            System.out.println("Aviso Precarga Medición: " + e.getMessage());
        }

        // PRUEBA DE EXCEPCIÓN 1: Registrar valor negativo
        try {
            System.out.println("Probando excepción: intento de registro con milímetros negativos (-5.0 mm)...");
            s1.registrar(-5.0, ahora.minusHours(1));
        } catch (LecturaInvalidaException | AsignacionInvalidaException e) {
            System.out.println("-> EXCEPCIÓN CAPTURADA EXITOSAMENTE (Lectura negativa): " + e.getMessage());
        }

        // PRUEBA DE EXCEPCIÓN 2: Registrar en sensor sin estación
        Sensor sSinEstacion = new Sensor(999, "Test", "Test", 0.1, EstadoOperativo.OPERATIVO, LocalDate.now());
        try {
            System.out.println("Probando excepción: intento de registro en sensor sin estación asignada...");
            sSinEstacion.registrar(30.0, ahora);
        } catch (LecturaInvalidaException | AsignacionInvalidaException e) {
            System.out.println("-> EXCEPCIÓN CAPTURADA EXITOSAMENTE (Sensor sin estación): " + e.getMessage());
        }
        
        // Evaluamos el riesgo de las zonas según el acumulado de lluvia
        zona1.evaluarRiesgo();
        zona2.evaluarRiesgo();
        zona3.evaluarRiesgo(); 
        
        System.out.println("-> ¡Datos iniciales extendidos cargados con éxito!\n");
    }
}
