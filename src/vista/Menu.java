package src.vista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.clases.*;
import src.enums.*;

/**
 * Clase encargada de la interfaz de usuario por consola.
 * Maneja los diferentes menús interactivos del sistema, validando entradas 
 * mediante estructuras de control y excepciones.
 */
public class Menu {

    private Gestora gestora;
    private Scanner scanner;

    public Menu(Gestora gestora) {
        this.gestora = gestora;
        this.scanner = new Scanner(System.in);
    }
    /**
     * Inicia el ciclo principal del sistema, mostrando el menú general 
     * y redirigiendo al submenú seleccionado por el usuario.
     */
    public void iniciar() {
        int opcion = -1;
        do {
            System.out.println("\n=== SISTEMA METEOROLOGICO ===");
            System.out.println("1. Gestionar zonas y estaciones");
            System.out.println("2. Gestionar sensores y mediciones");
            System.out.println("3. Consultar y gestionar alertas");
            System.out.println("4. Administrar personal técnico y equipos");
            System.out.println("5. Generar informes y reportes");
            System.out.println("0. Salir");
            System.out.print("\nSeleccione una opción: ");

            opcion = leerEntero();
            System.out.println();

            switch (opcion) {
                case 1:menuZonasYEstaciones();break;
                case 2:menuSensores();break;
                case 3:menuAlertas();break;
                case 4:menuPersonalTecnico();break;
                case 5:menuInformesYReportes();break;
                case 0:System.out.println("Saliendo del sistema...");break;
                default:System.out.println("Opcion no valida. Intente nuevamente.\n");
            }
        } while (opcion != 0);
    }
    /**
     * Gestiona el submenú de Zonas Geográficas y Estaciones Meteorológicas,
     * permitiendo listar, agregar o eliminar elementos de forma interactiva.
     */
    //ZONAS Y ESTACIONES 
    private void menuZonasYEstaciones() {
        int op = -1;
        do {
            System.out.println("\n--- GESTION DE ZONAS Y ESTACIONES ---");
            System.out.println("1. Listar zonas registradas");
            System.out.println("2. Listar estaciones meteorologicas");
            System.out.println("3. Agregar nueva zona geografica");
            System.out.println("4. Agregar nueva estación meteorologica");
            System.out.println("5. Eliminar zona geografica");       
            System.out.println("6. Eliminar estación meteorologica");  
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");
            
            try {
                op = Integer.parseInt(scanner.nextLine().trim());
                switch (op) {
                    case 1:mostrarZonas();break;
                    case 2:mostrarEstaciones(); break;
                    case 3:agregarNuevaZona(); break;
                    case 4:agregarNuevaEstacion();break;
                    case 5:eliminarZona();break;
                    case 6:eliminarEstacion();break;
                    case 0:break;
                    default:System.out.println("\n Opcion inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n Ingresa un numero valido");
                op = -1;
            }
        } while (op != 0);
    }
    // Metodo para listar todas las zonas
    private void mostrarZonas() {
        System.out.println("\nZONAS REGISTRADAS:");
        if (gestora.getZonas().isEmpty()) {
            System.out.println(" No hay zonas registradas en el sistema");
        } else {
            for (ZonaGeografica z : gestora.getZonas()) {
                System.out.println("- ID " + z.getIdZona() + ": " + z.getNombre() + " (Nivel actual: " + z.getNivelAlertaActual() + ")");
            }
        }
    }

    // Metodo para listar todas las estaciones
    private void mostrarEstaciones() {
        System.out.println("\nESTACIONES METEOROLOGICAS:");
        if (gestora.getEstaciones().isEmpty()) {
            System.out.println(" No hay estaciones registradas en el sistema");
        } else {
            for (EstacionMeteorologica e : gestora.getEstaciones()) {
                System.out.println("- ID " + e.getIdEstacion() + ": " + e.getNombre() + " | Estado: "  + e.getEstado());
            }
        }
    }

    // Metodo para crear nueva zona
    private void agregarNuevaZona() {
        System.out.println("\n ALTA DE NUEVA ZONA GEOGRAFICA ");
        System.out.print("Ingrese ID de la zona: ");
        int id = leerEntero();
        
        System.out.print("Ingrese nombre de la zona: ");
        String nombre = scanner.nextLine();
        
        System.out.println("Niveles de Vulnerabilidad: 1. BAJO | 2. MEDIO | 3. ALTO | 4. MUY_ALTO");
        System.out.print("Seleccione el nivel (1-4): ");
        int vul = leerEntero();
        
        NivelVulnerabilidad nivelVul = NivelVulnerabilidad.BAJO;
        if (vul == 2) nivelVul = NivelVulnerabilidad.MEDIO;
        if (vul == 3) nivelVul = NivelVulnerabilidad.ALTO;
        if (vul == 4) nivelVul = NivelVulnerabilidad.MUY_ALTO;

        // Por defecto arranca en verde al crearse
        ZonaGeografica nueva = new ZonaGeografica(id, nombre, nivelVul, NivelAlerta.VERDE);
        gestora.agregarZona(nueva);
        System.out.println("Zona geografica " + nombre + " agregada con exito");
    }
    // Metodo para crear estaciones
    private void agregarNuevaEstacion() {
        System.out.println("\n ALTA DE NUEVA ESTACION METEOROLOGICA ");
        
        if (gestora.getZonas().isEmpty() || gestora.getResponsables().isEmpty()) {
            System.out.println("Error: Para crear una estacion debe existir al menos una Zona y un Responsable Tecnico en el sistema.");
            return;
        }

        System.out.print("Ingrese ID de la nueva estacion: ");
        int id = leerEntero();
        
        System.out.print("Ingrese nombre de la estacion: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Ingrese latitud: ");
        double lat = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Ingrese longitud: ");
        double lon = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Ingrese altitud en metros: ");
        double alt = Double.parseDouble(scanner.nextLine());
        
        System.out.println("Tipo de Estacion: 1. AUTOMATICA | 2. CONVENCIONAL");
        System.out.print("Seleccione el tipo (1-2): ");
        TipoEstacion tipo = (leerEntero() == 2) ? TipoEstacion.CONVENCIONAL : TipoEstacion.AUTOMATICA;

        // Seleccion de Responsable Tecnico por indice
        System.out.println("\nSeleccione el Responsable Tecnico a cargo:");
        for (int i = 0; i < gestora.getResponsables().size(); i++) {
            System.out.println((i + 1) + ". " + gestora.getResponsables().get(i).getNombreCompleto());
        }
        System.out.print("Opcion: ");
        int idxRt = leerEntero() - 1;
        ResponsableTecnico rt = gestora.getResponsables().get(idxRt);

        // Seleccion de Zona
        System.out.println("\nSeleccione la Zona Geografica donde se instalara:");
        for (int i = 0; i < gestora.getZonas().size(); i++) {
            System.out.println((i + 1) + ". " + gestora.getZonas().get(i).getNombre());
        }
        System.out.print("Opcion: ");
        int idxZona = leerEntero() - 1;
        ZonaGeografica zona = gestora.getZonas().get(idxZona);

        // Creamos y guardamos la estacion
        EstacionMeteorologica nueva = new EstacionMeteorologica(id, nombre, lat, lon, alt, EstadoOperativo.OPERATIVO, tipo, rt);
        
        try {
            zona.agregarEstacionMeteorologica(nueva);
            gestora.agregarEstacion(nueva);
            System.out.println(" Estacion " + nombre + " agregada e instalada en la zona correctamente");
        } catch (Exception e) {
            System.out.println(" ERROR: " + e.getMessage());
        }
    }
    // Metodo para eliminar una zona geografica
    private void eliminarZona() {
        System.out.println("\n--- ELIMINAR ZONA GEOGRAFICA ---");
        if (gestora.getZonas().isEmpty()) {
            System.out.println("No hay zonas registradas en el sistema.");
            return;
        }

        System.out.println("Zonas disponibles:");
        for (ZonaGeografica z : gestora.getZonas()) {
            System.out.println("- ID " + z.getIdZona() + ": " + z.getNombre());
        }

        System.out.print("Ingrese el ID de la zona que desea eliminar: ");
        int idZona = leerEntero();

        ZonaGeografica zonaEncontrada = null;
        for (ZonaGeografica z : gestora.getZonas()) {
            if (z.getIdZona() == idZona) {
                zonaEncontrada = z;
                break;
            }
        }

        if (zonaEncontrada == null) {
            System.out.println("Error: No se encontro ninguna zona con ese ID.");
            return;
        }

        // Validacion de negocio: No eliminar si tiene estaciones asociadas
        if (!zonaEncontrada.getEstacionesMeteorologicas().isEmpty()) {
            System.out.println(" No se puede eliminar la zona '" + zonaEncontrada.getNombre() + 
                               " porque tiene estaciones meteorologicas asociadas. Debe reubicarlas o eliminarlas primero.");
            return;
        }

        // Eliminamos de la lista general de la gestora
        gestora.getZonas().remove(zonaEncontrada);
        System.out.println(" La zona geográfica fue eliminada correctamente del sistema");
    }

    // Metodo para eliminar una estacion meteorológica
    private void eliminarEstacion() {
        System.out.println("\n--- ELIMINAR ESTACION METEOROLOGICA ---");
        if (gestora.getEstaciones().isEmpty()) {
            System.out.println("No hay estaciones registradas en el sistema");
            return;
        }

        System.out.println("Estaciones disponibles:");
        for (EstacionMeteorologica e : gestora.getEstaciones()) {
            String zonaAsociada = "Sin zona";
            // Buscamos a que zona pertenece para mostrar mejor info
            for (ZonaGeografica z : gestora.getZonas()) {
                if (z.getEstacionesMeteorologicas().contains(e)) {
                    zonaAsociada = z.getNombre();
                    break;
                }
            }
            System.out.println("- ID " + e.getIdEstacion() + ": " + e.getNombre() + " (Zona: " + zonaAsociada + ")");
        }

        System.out.print("Ingrese el ID de la estacion que desea eliminar: ");
        int idEstacion = leerEntero();

        EstacionMeteorologica estacionEncontrada = null;
        for (EstacionMeteorologica e : gestora.getEstaciones()) {
            if (e.getIdEstacion() == idEstacion) {
                estacionEncontrada = e;
                break;
            }
        }

        if (estacionEncontrada == null) {
            System.out.println("Error: No se encontro ninguna estacion con ese ID");
            return;
        }

        // Validacion de negocio: No eliminar si tiene sensores instalados
        if (!estacionEncontrada.getSensores().isEmpty()) {
            System.out.println(" No se puede eliminar la estación '" + estacionEncontrada.getNombre() + 
                               " porque tiene sensores instalados. Debe retirarlos previamente");
            return;
        }

        //  La eliminamos de la lista de la Zona geografica a la que pertenezca
        for (ZonaGeografica z : gestora.getZonas()) {
            if (z.getEstacionesMeteorologicas().contains(estacionEncontrada)) {
                z.getEstacionesMeteorologicas().remove(estacionEncontrada);
                break;
            }
        }

        // La eliminamos de la lista general de estaciones de la gestora
        gestora.getEstaciones().remove(estacionEncontrada);
        System.out.println(" La estación meteorológica fue eliminada correctamente del sistema");
    }
    /**
     * Gestiona el submenú de Sensores y Mediciones, permitiendo el alta de sensores,
     * registro de lecturas y consultas de su historial.
     */
    //  SENSORES 
    private void menuSensores() {
        int op = -1;
        do {
            System.out.println("\n--- GESTION DE SENSORES Y MEDICIONES ---");
            System.out.println("1. Listar sensores registrados");
            System.out.println("2. Agregar nuevo sensor");
            System.out.println("3. Registrar nueva medicion manual");
            System.out.println("4. Ver mediciones de un sensor");
            System.out.println("5. Retirar sensor de una estacion"); 
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");
            
            try {
                op = Integer.parseInt(scanner.nextLine().trim());
                switch (op) {
                    case 1:mostrarSensores();break;
                    case 2:agregarNuevoSensor();break;
                    case 3:registrarMedicion();break;
                    case 4:verMedicionesSensor();break;
                    case 5:retirarSensor();break;
                    case 0:break;
                    default:System.out.println("\n Opcion invalida");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n Ingresa un numero valido");
                op = -1;
            }
        } while (op != 0);
    }
    // Metodo para listar todos los sensores registrados
    private void mostrarSensores() {
        System.out.println("\nSENSORES REGISTRADOS:");
        if (gestora.getSensores().isEmpty()) {
            System.out.println(" No hay sensores registrados.");
        } else {
            for (Sensor s : gestora.getSensores()) {
                String est = (s.getEstacion() != null) ? s.getEstacion().getNombre() : "Sin asignar";
                System.out.println("- ID " + s.getIdSensor() + " [" + s.getMarca() + " | " + s.getModelo() + "] | Estado: " + s.getEstado() + " | Estación: " + est );
            }
        }
    }
    // Metodo para crear sensores
    private void agregarNuevoSensor() {
        System.out.println("\n-- ALTA DE NUEVO SENSOR --");
        
        if (gestora.getEstaciones().isEmpty()) {
            System.out.println("Error: Debe existir al menos una estación para instalar el sensor");
            return;
        }

        System.out.print("Ingrese ID del sensor: ");
        int id = leerEntero();
        
        System.out.print("Ingrese Marca: ");
        String marca = scanner.nextLine();
        
        System.out.print("Ingrese Modelo: ");
        String modelo = scanner.nextLine();
        
        System.out.print("Ingrese Margen de Error (ej: 0.1): ");
        double margen = Double.parseDouble(scanner.nextLine());

        // Listamos las estaciones para que el operador elija dónde instalarlo
        System.out.println("\nSeleccione la Estacion donde se instalara:");
        for (int i = 0; i < gestora.getEstaciones().size(); i++) {
            System.out.println((i + 1) + ". " + gestora.getEstaciones().get(i).getNombre());
        }
        System.out.print("Opción: ");
        int idxEst = leerEntero() - 1;
        
        if (idxEst < 0 || idxEst >= gestora.getEstaciones().size()) {
            System.out.println("Opcion de estacion invalida");
            return;
        }
        
        EstacionMeteorologica estacionElegida = gestora.getEstaciones().get(idxEst);

        // Creamos el sensor nuevo (estado operativo por defecto y fecha de calibración actual)
        Sensor nuevoSensor = new Sensor(id, marca, modelo, margen, EstadoOperativo.OPERATIVO, LocalDate.now());

        try {
            estacionElegida.instalarSensor(nuevoSensor);
            gestora.agregarSensor(nuevoSensor);
            System.out.println(" Sensor " + marca + " " + modelo + " instalado correctamente en la estación " + estacionElegida.getNombre());
        } catch (src.excepciones.AsignacionInvalidaException e) {
            System.out.println(" ERROR DE ASIGNACION: " + e.getMessage());
        }
    }

    // Metodo para registrar medicion
    private void registrarMedicion() {
        System.out.println(" REGISTRAR NUEVA MEDICION ");
        if (gestora.getSensores().isEmpty()) {
            System.out.println("Error: No hay sensores disponibles para registrar mediciones\n");
            return;
        }

        System.out.print("Ingrese el ID del sensor: ");
        int idSensor = leerEntero();
        
        Sensor sensorEncontrado = null;
        for (Sensor s : gestora.getSensores()) {
            if (s.getIdSensor() == idSensor) {
                sensorEncontrado = s;
                break;
            }
        }

        if (sensorEncontrado == null) {
            System.out.println("Error: No se encontro ningun sensor con ese ID\n");
            return;
        }

        System.out.print("Ingrese los milimetros de lluvia (mm): ");
        try {
            double mm = Double.parseDouble(scanner.nextLine());
            
            Medicion m = sensorEncontrado.registrar(mm, LocalDateTime.now());
            if (m != null) {
                System.out.println("Medicion registrada exitosamente");
                
                // EVALUAMOS EL RIESGO AUTOMATICAMENTE DE LA ZONA DE ESTE SENSOR
                if (sensorEncontrado.getEstacion() != null) {
                    // Buscamos la zona a la que pertenece la estacion del sensor
                    for (ZonaGeografica z : gestora.getZonas()) {
                        if (z.getEstacionesMeteorologicas().contains(sensorEncontrado.getEstacion())) {
                            NivelAlerta previo = z.getNivelAlertaActual();
                            NivelAlerta nuevo = z.evaluarRiesgo(); // Aqui recalcula y emite alerta si supero umbral
                            if (nuevo != previo) {
                                System.out.println(" La zona " + z.getNombre() + " cambio su nivel de alerta a: " + nuevo);
                            }
                            break;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(" Formato de numero invalido\n");
        } catch (src.excepciones.LecturaInvalidaException | src.excepciones.AsignacionInvalidaException e) {
            System.out.println("das" + e.getMessage() + "\n");
        }
    }

    // Metodo  para consultar el historial de mediciones de un sensor
    private void verMedicionesSensor() {
        System.out.println("\n-- CONSULTAR MEDICIONES DE SENSOR --");

        if (gestora.getSensores().isEmpty()) {
            System.out.println("No hay sensores registrados en el sistema");
            return;
        }

        // Listamos los sensores disponibles para que el operador elija
        System.out.println("Sensores disponibles:");
        for (int i = 0; i < gestora.getSensores().size(); i++) {
            Sensor s = gestora.getSensores().get(i);
            String estNombre = (s.getEstacion() != null) ? s.getEstacion().getNombre() : "Sin estacion";
            System.out.println((i + 1) + ". Sensor ID " + s.getIdSensor() + " (" + s.getMarca() + " " + s.getModelo() + ") - Estación: " + estNombre);
        }

        System.out.print("Seleccione el numero del sensor que desea consultar: ");
        int idx = leerEntero() - 1;

        if (idx < 0 || idx >= gestora.getSensores().size()) {
            System.out.println("Opción invalida");
            return;
        }

        Sensor sensorElegido = gestora.getSensores().get(idx);

        System.out.println("\n=== HISTORIAL DE MEDICIONES (Sensor ID: " + sensorElegido.getIdSensor());
        
        if (sensorElegido.getMediciones().isEmpty()) {
            System.out.println("Este sensor no registra mediciones historicas");
        } else {
            int nro = 1;
            for (Medicion m : sensorElegido.getMediciones()) {
                // Formateamos la fecha y hora
                String fh = m.getFechaHora().toString().replace('T', ' ').substring(0, 16);

                String alertaAnomala = m.isAnomala() ? " [ANÓMALA]" : "";
                
                System.out.println("   " + nro + ". Fecha/Hora: " + fh + " | Valor: " + m.getMilimetros() + " mm" + alertaAnomala);
                nro++;
            }
        }
    }

    // Metodo para retirar Sensor
    private void retirarSensor() {
        System.out.println("\n-- RETIRAR SENSOR DE ESTACION --");

        if (gestora.getSensores().isEmpty()) {
            System.out.println("No hay sensores registrados en el sistema");
            return;
        }

        // Listamos los sensores instalados
        List<Sensor> sensoresInstalados = new ArrayList<>();
        for (Sensor s : gestora.getSensores()) {
            if (s.getEstacion() != null) {
                sensoresInstalados.add(s);
            }
        }

        if (sensoresInstalados.isEmpty()) {
            System.out.println("No hay sensores instalados en ninguna estacion en este momento");
            return;
        }

        for (int i = 0; i < sensoresInstalados.size(); i++) {
            Sensor s = sensoresInstalados.get(i);
            System.out.println((i + 1) + ". Sensor ID " + s.getIdSensor() + " (" + s.getMarca() + ") - Estacion: " + s.getEstacion().getNombre());
        }

        System.out.print("Seleccione el numero del sensor que desea retirar: ");
        int idx = leerEntero() - 1;

        if (idx < 0 || idx >= sensoresInstalados.size()) {
            System.out.println("Opcion invalida");
            return;
        }

        // Retiramos el sensor seleccionado
        Sensor sensorElegido = sensoresInstalados.get(idx);
        EstacionMeteorologica estacionActual = sensorElegido.getEstacion();

        estacionActual.retirarSensor(sensorElegido); 

        System.out.println(" El sensor ID " + sensorElegido.getIdSensor() + 
                           " fue retirado correctamente de la estacion " + estacionActual.getNombre());
    }
    /**
     * Gestiona el submenú de Alertas, permitiendo consultar el historial,
     * generar alertas manuales y vincular equipos de respuesta.
     */
    //  ALERTAS 
    private void menuAlertas() {
        int op = -1;
        do {
            System.out.println("\n--- CONSULTAR Y GESTIONAR ALERTAS ---");
            System.out.println("1. Ver historial completo de alertas");
            System.out.println("2. Generar alerta manual");
            System.out.println("3. Asignar equipo de respuesta a una alerta");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            try {
                op = Integer.parseInt(scanner.nextLine().trim());
                switch (op) {
                    case 1:gestora.historialAlertas(null, null, null, null);break;
                    case 2:agregarAlertaManual();break;
                    case 3:asignarEquipoAlerta();break;
                    case 0:break;
                    default:
                        System.out.println("\n Opcion invalida");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n Ingresa un numero valido");
                op = -1;
            }
        } while (op != 0);
    }

    // Metodo para crear y asignar una alerta por consola
    private void agregarAlertaManual() {
        System.out.println("\n--- ALTA MANUAL DE ALERTA ---");
        
        if (gestora.getZonas().isEmpty()) {
            System.out.println("Error: Debe existir al menos una Zona Geografica para asignarle una alerta");
            return;
        }

        System.out.print("Ingrese el ID de la Zona afectada: ");
        int idZona = leerEntero();
        
        ZonaGeografica zonaAfectada = null;
        for (ZonaGeografica z : gestora.getZonas()) {
            if (z.getIdZona() == idZona) {
                zonaAfectada = z;
                break;
            }
        }

        if (zonaAfectada == null) {
            System.out.println("Error: No se encontro ninguna zona registrada con ese ID");
            return;
        }

        // Pedimos el Nivel
        System.out.println("Niveles de Alerta: 1. AMARILLA | 2. NARANJA | 3. ROJA");
        System.out.print("Seleccione el nivel (1-3): ");
        int nivelInput = leerEntero();
        NivelAlerta nivel = NivelAlerta.AMARILLA;
        if (nivelInput == 2) nivel = NivelAlerta.NARANJA;
        if (nivelInput == 3) nivel = NivelAlerta.ROJA;

        // Pedimos el Evento 
        System.out.println("Tipo de Evento: 1. CRECIDA_RIO | 2. TORMENTA_SEVERA");
        System.out.print("Seleccione el evento (1-2): ");
        int eventoInput = leerEntero();
        
        TipoEventoClimatico evento = TipoEventoClimatico.CRECIDA_RIO; 
        if (eventoInput == 2) evento = TipoEventoClimatico.TORMENTA_SEVERA;

        System.out.print("Ingrese una descripcion de la situacion: ");
        String descripcion = scanner.nextLine();

        try {
            Alerta nuevaAlerta = zonaAfectada.registrarAlertaManual(evento, nivel, descripcion);
            
            if (nuevaAlerta != null) {
                // Actualizamos también el nivel de alerta actual de la zona si corresponde
                zonaAfectada.setNivelAlertaActual(nivel);
                System.out.println("Alerta generada correctamente en el sistema");
            }
        } catch (src.excepciones.AsignacionInvalidaException e) {
            System.out.println("ERROR DE REGISTRO MANUAL: " + e.getMessage());
        }
        
    }
    // Metodo para vincular un equipo de respuesta a una alerta
    private void asignarEquipoAlerta() {
        System.out.println("\n-- ASIGNAR EQUIPO DE RESPUESTA A ALERTA --");

        if (gestora.getEquipos().isEmpty()) {
            System.out.println("Error: No hay equipos de respuesta registrados en el sistema");
            return;
        }

        List<Alerta> todasLasAlertas = new ArrayList<>();
        for (ZonaGeografica zona : gestora.getZonas()) {
            todasLasAlertas.addAll(zona.getAlertas());
        }

        if (todasLasAlertas.isEmpty()) {
            System.out.println("No hay alertas registradas en el sistema para atender");
            return;
        }

        System.out.println("\nAlertas registradas:");
        for (int i = 0; i < todasLasAlertas.size(); i++) {
            Alerta a = todasLasAlertas.get(i);
            String estadoAtendida = a.isAtendida() ? "[ATENDIDA]" : "[PENDIENTE]";
            System.out.println((i + 1) + ". ID " + a.getIdAlerta() + " | Nivel: " + a.getNivel() + " | Evento: " + a.getEvento() + " " + estadoAtendida);
        }
        
        System.out.print("Seleccione el numero de la alerta a atender: ");
        int idxAlerta = leerEntero() - 1;
        
        if (idxAlerta < 0 || idxAlerta >= todasLasAlertas.size()) {
            System.out.println("Opcion de alerta invalida");
            return;
        }
        Alerta alertaSeleccionada = todasLasAlertas.get(idxAlerta);

        // Listamos equipos disponibles
        List<EquipoRespuesta> equiposDisponibles = new ArrayList<>();
        for (EquipoRespuesta eq : gestora.getEquipos()) {
            if (eq.getDisponible()) {
                equiposDisponibles.add(eq);
            }
        }

        if (equiposDisponibles.isEmpty()) {
            System.out.println("ADVERTENCIA: No hay equipos de respuesta disponibles en este momento");
            return;
        }

        System.out.println("\nEquipos de respuesta disponibles:");
        for (int i = 0; i < equiposDisponibles.size(); i++) {
            EquipoRespuesta eq = equiposDisponibles.get(i);
            System.out.println((i + 1) + ". " + eq.getNombreEquipo());
        }

        System.out.print("Seleccione el numero del equipo que acudira: ");
        int idxEquipo = leerEntero() - 1;

        if (idxEquipo < 0 || idxEquipo >= equiposDisponibles.size()) {
            System.out.println("Opcion de equipo invalida");
            return;
        }
        
        EquipoRespuesta equipoSeleccionado = equiposDisponibles.get(idxEquipo);

        try {
            alertaSeleccionada.asignarEquipo(equipoSeleccionado);
            alertaSeleccionada.setAtendida(true);
            System.out.println("El equipo " + equipoSeleccionado.getNombreEquipo() + 
                               " fue asignado correctamente a la alerta ID " + alertaSeleccionada.getIdAlerta());
        } catch (src.excepciones.AsignacionInvalidaException | src.excepciones.EquipoOcupadoException e) {
            System.out.println("ERROR OPERATIVO: " + e.getMessage());
        }
    }
    /**
     * Gestiona el submenú de Personal Técnico y Equipos de Respuesta,
     * permitiendo dar de alta responsables, equipos y asignar tareas de mantenimiento.
     */
    // PERSONAL TECNICO 
    private void menuPersonalTecnico() {
        int op = -1;
        do {
            System.out.println("\n--- ADMINISTRAR PERSONAL Y EQUIPOS DE RESPUESTA ---");
            System.out.println("1. Listar personal tecnico y equipos");
            System.out.println("2. Agregar nuevo responsable tecnico");
            System.out.println("3. Agregar nuevo equipo de respuesta");
            System.out.println("4. Asignar responsable tecnico a un equipo de respuesta");
            System.out.println("5. Asignar responsable a el mantenimiento de sensores (reparacion/calibracion)");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            
            try {
                op = Integer.parseInt(scanner.nextLine().trim());
                switch (op) {
                    case 1:mostrarPersonalYEquipos();break;
                    case 2:agregarNuevoResponsable();break;
                    case 3:agregarNuevoEquipo();break;
                    case 4:agregarIntegranteAEquipo(); break;
                    case 5:asignarMantenimientoSensores();break;
                    case 0:break;
                    default:System.out.println("\n Opcion invalida");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n Ingresa un numero valido");
                op = -1;
            }
        } while (op != 0);
    }

    // Metodo para listar personal y Equipos de respuesta
    private void mostrarPersonalYEquipos() {
        System.out.println("\nLISTADO DE PERSONAL TECNICO:");
        if (gestora.getResponsables().isEmpty()) {
            System.out.println("  No hay personal tecnico registrado");
        } else {
            for (ResponsableTecnico r : gestora.getResponsables()) {
                System.out.println("-" + r.getNombreCompleto() + " | Licencia: " + r.getNumeroLicencia() + " | Especialidad: " + r.getEspecialidad());
            }
        }
        
        System.out.println("\nEQUIPOS DE RESPUESTA Y SUS INTEGRANTES:");
        if (gestora.getEquipos().isEmpty()) {
            System.out.println("  No hay equipos de respuesta registrados");
        } else {
            for (EquipoRespuesta eq : gestora.getEquipos()) {
                String disp = eq.getDisponible() ? "LIBRE" : "OCUPADO";
                System.out.println(" ID " + eq.getIdEquipo() + ": " + eq.getNombreEquipo() + " [" + disp + "]");
                
                if (eq.getResponsablesTecnicos() == null || eq.getResponsablesTecnicos().isEmpty()) {
                    System.out.println(" Integrantes: Sin personal asignado");
                } else {
                    System.out.println(" Integrantes:");
                    for (ResponsableTecnico miembro : eq.getResponsablesTecnicos()) {
                        System.out.println("        " + miembro.getNombreCompleto() + " (Licencia: " + miembro.getNumeroLicencia() + ")");
                    }
                }
            }
        }
    }

    // Metodo  para crear nuevo responsable
    private void agregarNuevoResponsable() {
        System.out.println("\n-- ALTA DE RESPONSABLE TECNICO --");
        System.out.print("Ingrese DNI (solo numeros): ");
        int dni = leerEntero();
        
        System.out.print("Ingrese Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Ingrese Apellido: ");
        String apellido = scanner.nextLine();
        
        System.out.print("Ingrese Correo Electronico: ");
        String correo = scanner.nextLine();
        
        System.out.print("Ingrese numero de Celular: ");
        String celular = scanner.nextLine();
        
        System.out.print("Ingrese Especialidad (Ej. Ing. Ambiental): ");
        String especialidad = scanner.nextLine();
        
        System.out.print("Ingrese Numero de Licencia/Matricula: ");
        String licencia = scanner.nextLine();

        ResponsableTecnico nuevoTecnico = new ResponsableTecnico(dni, nombre, apellido, correo, celular, especialidad, licencia);
        gestora.agregarResponsable(nuevoTecnico);
        System.out.println("Personal tecnico " + nombre + " " + apellido + " agregado con éxito");
    }

    // Metodo para crear Equipos de Respuesta
    private void agregarNuevoEquipo() {
        System.out.println("\n-- ALTA DE EQUIPO DE RESPUESTA --");
        
        System.out.print("Ingrese ID numérico del equipo: ");
        int id = leerEntero();
        
        System.out.print("Ingrese Nombre del equipo (Ej: Defensa Civil): ");
        String nombre = scanner.nextLine();
        
        // Se crea por defecto estando "disponible"
        EquipoRespuesta nuevo = new EquipoRespuesta(id, nombre, true);
        gestora.agregarEquipo(nuevo);
        
        System.out.println("Equipo " + nombre + " agregado correctamente");
    }
    
    private void agregarIntegranteAEquipo() {
        System.out.println("\n-- ASIGNAR RESPONSABLE TECNICO A EQUIPO --");

        // Validamos que existan equipos y responsables cargados
        if (gestora.getEquipos().isEmpty()) {
            System.out.println("Error: No hay equipos de respuesta registrados en el sistema");
            return;
        }
        if (gestora.getResponsables().isEmpty()) {
            System.out.println("Error: No hay responsables tecnicos registrados en el sistema");
            return;
        }

        // Listamos y seleccionamos el Equipo de Respuesta
        System.out.println("Seleccione el equipo de respuesta:");
        for (int i = 0; i < gestora.getEquipos().size(); i++) {
            EquipoRespuesta eq = gestora.getEquipos().get(i);
            System.out.println((i + 1) + ". " + eq.getNombreEquipo() + " (ID: " + eq.getIdEquipo() + ")");
        }
        System.out.print("Opción de equipo: ");
        int idxEquipo = leerEntero() - 1;

        if (idxEquipo < 0 || idxEquipo >= gestora.getEquipos().size()) {
            System.out.println("Opción de equipo inválida.");
            return;
        }
        EquipoRespuesta equipoElegido = gestora.getEquipos().get(idxEquipo);

        // Listamos y seleccionamos el Responsable Tecnico
        System.out.println("\nSeleccione el Responsable Tecnico que se sumara:");
        for (int i = 0; i < gestora.getResponsables().size(); i++) {
            ResponsableTecnico rt = gestora.getResponsables().get(i);
            System.out.println((i + 1) + ". " + rt.getNombreCompleto() + " | Especialidad: " + rt.getEspecialidad() + " | Licencia: " + rt.getNumeroLicencia());
        }
        System.out.print("Opcion de responsable: ");
        int idxRt = leerEntero() - 1;

        if (idxRt < 0 || idxRt >= gestora.getResponsables().size()) {
            System.out.println("Opcion de responsable invalida");
            return;
        }
        ResponsableTecnico rtElegido = gestora.getResponsables().get(idxRt);

        // Invocamos el metodo de la clase EquipoRespuesta para agregarlo formalmente
        try {
            equipoElegido.agregarIntegrante(rtElegido); 
            
            System.out.println(" El técnico " + rtElegido.getNombreCompleto() + 
                               " fue asignado correctamente al equipo '" + equipoElegido.getNombreEquipo() + "'.");
        } catch (Exception e) {
            System.out.println(" Error al vincular el integrante: " + e.getMessage());
        }
    }

    // Metodo para arreglar y calibrar sensores
    private void asignarMantenimientoSensores() {
        System.out.println("\n--- MANTENIMIENTO Y CALIBRACIÓN DE SENSORES ---");
        
        // Buscamos y mostramos que sensores tienen problemas
        boolean haySensoresConProblemas = false;
        System.out.println("Sensores que requieren atencion:");

        for (Sensor s : gestora.getSensores()) {
            boolean roto = (s.getEstado() != src.enums.EstadoOperativo.OPERATIVO);
            boolean descalibrado = s.requiereCalibracion();
            
            if (roto || descalibrado) {
                System.out.println("- ID: " + s.getIdSensor() + " | Marca: " + s.getMarca() + 
                                   " | Estado: " + s.getEstado() + 
                                   " | Descalibrado: " + (descalibrado ? "SI" : "NO"));
                haySensoresConProblemas = true;
            }
        }

        if (!haySensoresConProblemas) {
            System.out.println(" Todos los sensores del sistema estan operativos y con la calibracion al dia");
            return;
        }

        // Elegimos el sensor a reparar
        System.out.print("\nIngrese el ID del sensor a reparar/calibrar (o 0 para cancelar): ");
        int idSensor = leerEntero();
        if (idSensor == 0) return;

        Sensor sensorSelec = null;
        for (Sensor s : gestora.getSensores()) {
            if (s.getIdSensor() == idSensor) {
                sensorSelec = s;
                break;
            }
        }

        if (sensorSelec == null) {
            System.out.println("Error: Sensor no encontrado");
            return;
        }

        if (gestora.getResponsables().isEmpty()) {
            System.out.println("Error: No hay personal tecnico registrado para realizar el mantenimiento");
            return;
        }

        // Se muestra el personal tecnico disponible
        System.out.println("\nPersonal Tecnico disponible:");
        for (ResponsableTecnico rt : gestora.getResponsables()) {
            System.out.println("- DNI: " + rt.getDni() + " | Nombre: " + rt.getNombreCompleto());
        }

        // Se pide el DNI del tecnico que estara a cargo
        System.out.print("Ingrese el DNI del tecnico a cargo del arreglo: ");
        int dniResp = leerEntero();
        ResponsableTecnico respSelec = null;
        for (ResponsableTecnico rt : gestora.getResponsables()) {
            if (rt.getDni() == dniResp) {
                respSelec = rt;
                break;
            }
        }

        if (respSelec == null) {
            System.out.println("Error: Tecnico no encontrado");
            return;
        }

        // Repara y renueva la calibracion
        sensorSelec.setEstado(src.enums.EstadoOperativo.OPERATIVO);
        sensorSelec.setUltimaCalibracion(java.time.LocalDate.now());
        
        // Mensaje para informar que se realiza con exito
        System.out.println("\n Mantenimiento completado por " + respSelec.getNombreCompleto());
        System.out.println(" El sensor ID " + sensorSelec.getIdSensor() + " ha vuelto a estado operativo y su fecha de calibracion es hoy");
    }
    
    /**
     * Gestiona el submenú de Informes y Reportes, permitiendo generar rankings,
     * historicos de precipitaciones, análisis de anomalías y fichas técnicas.
     */
    // INFORMES Y REPORTES
    private void menuInformesYReportes() {
        int op = -1;
        do {
            System.out.println("\n--- GENERAR INFORMES Y REPORTES ---");
            System.out.println("1. Historico de precipitaciones por zona y estacion");
            System.out.println("2. Ranking de estaciones por volumetria");
            System.out.println("3. Historial de alertas emitidas (filtrado)");
            System.out.println("4. Ficha tecnica operativa de una estación");
            System.out.println("5. Informe de fallas y mantenimiento");
            System.out.println("6. Analisis de anomalía climatica mensual");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opción: ");

            try {
                op = Integer.parseInt(scanner.nextLine().trim());
                System.out.println();

                switch (op) {
                    case 1:gestora.reportePrecipitacionesHistoricas();break;
                    case 2:generarRankingEstaciones();break;
                    case 3:generarHistorialAlertasFiltrado();;break;
                    case 4:generarFichaTecnica();break;
                    case 5:gestora.informeFallas();break;
                    case 6:generarAnalisisAnomalia();break;
                    case 0:break;
                    default:System.out.println("Opcion no valida\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n Ingresa un numero valido");
                op = -1;
            }
        } while (op != 0);
    }

    // Metodo auxiliar para el caso 2
    private void generarRankingEstaciones() {
        // Se pide gecha de inicio y finalizacion para la volumetria de estaciones
        System.out.println("-- RANKING DE ESTACIONES POR VOLUMETRIA --");
        System.out.print("Ingrese fecha desde (AAAA-MM-DD): ");
        LocalDate desded = leerFecha();
        System.out.print("Ingrese fecha hasta (AAAA-MM-DD): ");
        LocalDate hastad = leerFecha();
        
        if (desded != null && hastad != null) {
            gestora.rankingEstaciones(desded, hastad);
        }
    }

    // Metodo auxiliar para el caso 3 
    private void generarHistorialAlertasFiltrado() {
        System.out.println("\n-- HISTORIAL DE ALERTAS EMITIDAS (FILTRADO) --");
        System.out.println("(Para omitir un filtro, ingrese 0 en las opciones o presione Enter en las fechas)");

        // Filtro por Nivel de Severidad
        System.out.println("\nNiveles de Alerta: 1.VERDE | 2. AMARILLA | 3. NARANJA | 4. ROJA | 0. Sin filtro");
        System.out.print("Seleccione el nivel de severidad (0-4): ");
        int nivelInput = leerEntero();
        NivelAlerta nivelFiltro = null;
        if (nivelInput == 1) nivelFiltro = NivelAlerta.VERDE;
        if (nivelInput == 2) nivelFiltro = NivelAlerta.AMARILLA;
        else if (nivelInput == 3) nivelFiltro = NivelAlerta.NARANJA;
        else if (nivelInput == 4) nivelFiltro = NivelAlerta.ROJA;

        // Filtro por Fechas (Lo hacemos directo con el scanner para poder detectar el Enter vacío)
        System.out.print("\nIngrese fecha DESDE (AAAA-MM-DD) o presione Enter para omitir: ");
        String strDesde = scanner.nextLine().trim();
        LocalDate fechaDesde = null;
        if (!strDesde.isEmpty()) {
            try {
                fechaDesde = LocalDate.parse(strDesde);
            } catch (Exception e) {
                System.out.println("Formato invalido. Se omitira el filtro de fecha de inicio");
            }
        }

        System.out.print("Ingrese fecha HASTA (AAAA-MM-DD) o presione Enter para omitir: ");
        String strHasta = scanner.nextLine().trim();
        LocalDate fechaHasta = null;
        if (!strHasta.isEmpty()) {
            try {
                fechaHasta = LocalDate.parse(strHasta);
            } catch (Exception e) {
                System.out.println("Formato invalido. Se omitira el filtro de fecha de fin");
            }
        }

        // Filtro por Zona
        ZonaGeografica zonaFiltro = null;
        if (!gestora.getZonas().isEmpty()) {
            System.out.println("\nZonas disponibles para filtrar:");
            for (ZonaGeografica z : gestora.getZonas()) {
                System.out.println("- ID " + z.getIdZona() + ": " + z.getNombre());
            }
            // Pedimos ID de la zona a filtrar
            System.out.print("Ingrese ID de la zona a filtrar (o 0 para buscar en todas): ");
            int idZona = leerEntero();

            // Realizamos la busqueda de la zona y en caso de no encontrarse o seleccionar 0 se tomaran todas las zonas
            if (idZona > 0) {
                for (ZonaGeografica z : gestora.getZonas()) {
                    if (z.getIdZona() == idZona) {
                        zonaFiltro = z;
                        break;
                    }
                }
                if (zonaFiltro == null) {
                    System.out.println(" Zona no encontrada. Se buscara en todas las zonas");
                }
            }
        }

        // Ejecutamos la busqueda en la gestora enviando los filtros aplicados
        System.out.println("\nGenerando reporte con los filtros aplicados\n");
        gestora.historialAlertas(nivelFiltro, fechaDesde, fechaHasta, zonaFiltro);
    }

    // Metodo auxiliar para el caso 4
    private void generarFichaTecnica() {
        System.out.println("-- FICHA TECNICA OPERATIVA DE ESTACION --");
        if (gestora.getEstaciones().isEmpty()) {
            System.out.println("No hay estaciones cargadas en el sistema\n");
            return;
        } 
        
        // Listamos brevemente las estaciones disponibles para ayudar al usuario
        System.out.println("Estaciones disponibles:");
        for (EstacionMeteorologica e : gestora.getEstaciones()) {
            System.out.println("- ID " + e.getIdEstacion() + ": " + e.getNombre());
        }

        // Pedimos ID de la estacion que se quiere la ficha tecnica
        System.out.print("\nIngrese ID de la estacion: ");
        int idEst = leerEntero();

        //Buscamos la estacion solicitada
        EstacionMeteorologica est = null;
        for (EstacionMeteorologica e : gestora.getEstaciones()) {
            if (e.getIdEstacion() == idEst) {
                est = e;
                break;
            }
        }
        // Realizamos la ficha tecnica si la estacion fue encontrada. Sino da error
        if (est != null) {
            gestora.fichaTecnica(est);
        } else {
            System.out.println("Error: Estacion no encontrada\n");
        }
    }

    // Metodo auxiliar para el caso 6
    private void generarAnalisisAnomalia() {
        System.out.println("-- ANALISIS DE ANOMALIA CLIMATICA MENSUAL --");
        if (gestora.getZonas().isEmpty()) {
            System.out.println("No hay zonas cargadas en el sistema\n");
            return;
        } 

        // Listamos brevemente las zonas disponibles
        System.out.println("Zonas disponibles:");
        for (ZonaGeografica z : gestora.getZonas()) {
            System.out.println("- ID " + z.getIdZona() + ": " + z.getNombre());
        }

        // Pedimos ID de la zona a analizar
        System.out.print("\nIngrese ID de la zona a analizar: ");
        int idZona = leerEntero();

        // Buscamos la zona solicitada
        ZonaGeografica zona = null;
        for (ZonaGeografica z : gestora.getZonas()) {
            if (z.getIdZona() == idZona) {
                zona = z;
                break;
            }
        }

        // Pedimos año y mes para realizar la anomalia
        if (zona != null) {
            System.out.print("Ingrese año (ej: 2026): ");
            int anio = leerEntero();
            System.out.print("Ingrese mes (1-12): ");
            int mes = leerEntero();
            // Realizamos la anomalia climatica si la fecha y zona coincide. Sino devuelve 0
            gestora.anomaliaClimatica(zona, anio, mes);
        } else {
            System.out.println("Error: Zona no encontrada\n");
        }
    }

    // Auxiliares de lectura por consola
    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private LocalDate leerFecha() {
        try {
            return LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Fecha invalida. Debe usar el formato AAAA-MM-DD.");
            return null;
        }
    }
}
