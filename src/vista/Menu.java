package src.vista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.clases.*;
import src.enums.*;
public class Menu {

    private Gestora gestora;
    private Scanner scanner;

    public Menu(Gestora gestora) {
        this.gestora = gestora;
        this.scanner = new Scanner(System.in);
    }

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
                case 1:
                    menuZonasYEstaciones();
                    break;
                case 2:
                    menuSensores();
                    break;
                case 3:
                    menuAlertas();
                    break;
                case 4:
                    menuPersonalTecnico();
                    break;
                case 5:
                    menuInformesYReportes();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.\n");
            }
        } while (opcion != 0);
    }

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
            
            op = leerEntero();
            
            if (op == 1) {
                System.out.println("\nZONAS:");
                for (ZonaGeografica z : gestora.getZonas()) {
                    System.out.println("- ID " + z.getIdZona() + ": " + z.getNombre() + " (Nivel actual: " + z.getNivelAlertaActual() + ")");
                }
            } else if (op == 2) {
                System.out.println("\nESTACIONES:");
                for (EstacionMeteorologica e : gestora.getEstaciones()) {
                    System.out.println("- ID " + e.getIdEstacion() + ": " + e.getNombre() + "| " + "Estado: "  + e.getEstado());
                }
            } else if (op == 3) {
                agregarNuevaZona();
            } else if (op == 4) {
                agregarNuevaEstacion();
            } else if (op == 5) {
                eliminarZona();          
            } else if (op == 6) {
                eliminarEstacion();    
            }
        } while (op != 0);
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
    // Método para eliminar una zona geografica
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

        // Validación de negocio: No eliminar si tiene estaciones asociadas
        if (!zonaEncontrada.getEstacionesMeteorologicas().isEmpty()) {
            System.out.println(" No se puede eliminar la zona '" + zonaEncontrada.getNombre() + 
                               " porque tiene estaciones meteorologicas asociadas. Debe reubicarlas o eliminarlas primero.");
            return;
        }

        // Eliminamos de la lista general de la gestora
        gestora.getZonas().remove(zonaEncontrada);
        System.out.println(" La zona geográfica fue eliminada correctamente del sistema");
    }

    // Método para eliminar una estación meteorológica
    private void eliminarEstacion() {
        System.out.println("\n--- ELIMINAR ESTACION METEOROLOGICA ---");
        if (gestora.getEstaciones().isEmpty()) {
            System.out.println("No hay estaciones registradas en el sistema");
            return;
        }

        System.out.println("Estaciones disponibles:");
        for (EstacionMeteorologica e : gestora.getEstaciones()) {
            String zonaAsociada = "Sin zona";
            // Buscamos a qué zona pertenece para mostrar mejor info
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

        // Validación de negocio: No eliminar si tiene sensores instalados
        if (!estacionEncontrada.getSensores().isEmpty()) {
            System.out.println(" No se puede eliminar la estación '" + estacionEncontrada.getNombre() + 
                               " porque tiene sensores instalados. Debe retirarlos previamente");
            return;
        }

        //  La removemos de la lista de la Zona geográfica a la que pertenezca
        for (ZonaGeografica z : gestora.getZonas()) {
            if (z.getEstacionesMeteorologicas().contains(estacionEncontrada)) {
                z.getEstacionesMeteorologicas().remove(estacionEncontrada);
                break;
            }
        }

        // La removemos de la lista general de estaciones de la gestora
        gestora.getEstaciones().remove(estacionEncontrada);
        System.out.println(" La estación meteorológica fue eliminada correctamente del sistema");
    }

    // ================= 2. SENSORES =================
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
            
            op = leerEntero();

            if (op == 1) {
                System.out.println("\nSENSORES:");
                if (gestora.getSensores().isEmpty()) {
                    System.out.println("No hay sensores registrados.");
                } else {
                    for (Sensor s : gestora.getSensores()) {
                        String est = (s.getEstacion() != null) ? s.getEstacion().getNombre() : "Sin asignar";
                        System.out.println("- ID " + s.getIdSensor() + " [" + s.getMarca() + " " + s.getModelo() + "] | Estado: " + s.getEstado() + " | Estación: " + est);
                    }
                }
            } else if (op == 2) {
                agregarNuevoSensor();
            } else if (op == 3) {
                registrarMedicion();
            } else if (op == 4) {
                verMedicionesSensor();
            } else if (op == 5) {
                retirarSensor(); 
            }
        } while (op != 0);
    }
    // Método auxiliar para crear sensores
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
            System.out.println(" Sensor " + marca + " " + modelo + " instalado correctamente en la estación '" + estacionElegida.getNombre());
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
            System.out.println("Error: No se encontro ningun sensor con ese ID.\n");
            return;
        }

        System.out.print("Ingrese los milímetros de lluvia (mm): ");
        try {
            double mm = Double.parseDouble(scanner.nextLine());
            
            Medicion m = sensorEncontrado.registrar(mm, LocalDateTime.now());
            if (m != null) {
                System.out.println("-> ¡ÉXITO! Medición registrada exitosamente.");
                
                // EVALUAMOS EL RIESGO AUTOMATICAMENTE DE LA ZONA DE ESTE SENSOR
                if (sensorEncontrado.getEstacion() != null) {
                    // Buscamos la zona a la que pertenece la estación del sensor
                    for (ZonaGeografica z : gestora.getZonas()) {
                        if (z.getEstacionesMeteorologicas().contains(sensorEncontrado.getEstacion())) {
                            NivelAlerta previo = z.getNivelAlertaActual();
                            NivelAlerta nuevo = z.evaluarRiesgo(); // Aqui recalcula y emite alerta si supero umbral
                            if (nuevo != previo) {
                                System.out.println("-> ¡ATENCIÓN! La zona '" + z.getNombre() + "' cambió su nivel de alerta a: " + nuevo);
                            }
                            break;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("-> ERROR: Formato de número inválido.\n");
        } catch (src.excepciones.LecturaInvalidaException | src.excepciones.AsignacionInvalidaException e) {
            System.out.println("-> ERROR: " + e.getMessage() + "\n");
        }
    }
    // Metodo auxiliar para consultar el historial de mediciones de un sensor
    private void verMedicionesSensor() {
        System.out.println("\n-- CONSULTAR MEDICIONES DE SENSOR --");

        if (gestora.getSensores().isEmpty()) {
            System.out.println("No hay sensores registrados en el sistema.");
            return;
        }

        // Listamos los sensores disponibles para que el operador elija
        System.out.println("Sensores disponibles:");
        for (int i = 0; i < gestora.getSensores().size(); i++) {
            Sensor s = gestora.getSensores().get(i);
            String estNombre = (s.getEstacion() != null) ? s.getEstacion().getNombre() : "Sin estación";
            System.out.println((i + 1) + ". Sensor ID " + s.getIdSensor() + " (" + s.getMarca() + " " + s.getModelo() + ") - Estación: " + estNombre);
        }

        System.out.print("Seleccione el numero del sensor que desea consultar: ");
        int idx = leerEntero() - 1;

        if (idx < 0 || idx >= gestora.getSensores().size()) {
            System.out.println("Opción invalida");
            return;
        }

        Sensor sensorElegido = gestora.getSensores().get(idx);

        //Mostramos la cabecera y recorremos sus mediciones
        System.out.println("\n=== HISTORIAL DE MEDICIONES (Sensor ID: " + sensorElegido.getIdSensor() + ") ===");
        
        if (sensorElegido.getMediciones().isEmpty()) {
            System.out.println("   Este sensor aún no registra mediciones históricas.");
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
    private void retirarSensor() {
        System.out.println("\n-- RETIRAR SENSOR DE ESTACION --");

        if (gestora.getSensores().isEmpty()) {
            System.out.println("No hay sensores registrados en el sistema");
            return;
        }

        // 1. Listamos los sensores instalados
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
            System.out.println((i + 1) + ". Sensor ID " + s.getIdSensor() + " (" + s.getMarca() + ") - Estación: " + s.getEstacion().getNombre());
        }

        System.out.print("Seleccione el numero del sensor que desea retirar: ");
        int idx = leerEntero() - 1;

        if (idx < 0 || idx >= sensoresInstalados.size()) {
            System.out.println("Opción invalida");
            return;
        }

        Sensor sensorElegido = sensoresInstalados.get(idx);
        EstacionMeteorologica estacionActual = sensorElegido.getEstacion();

        // 2. USAMOS LOS MÉTODOS DE TUS CLASES (Encapsulación correcta)
        // (Ajustá el nombre del método de la estación si en lugar de 'removerSensor' o 'quitarSensor' se llama distinto)
        estacionActual.retirarSensor(sensorElegido); // O el método que tengas en EstacionMeteorologica

        System.out.println(" El sensor ID " + sensorElegido.getIdSensor() + 
                           " fue retirado correctamente de la estación " + estacionActual.getNombre());
    }

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

            op = leerEntero();

            if (op == 1) {
                gestora.historialAlertas(null, null, null, null);
            } else if (op == 2) {
                agregarAlertaManual();
            } else if (op == 3) {
                asignarEquipoAlerta(); 
            }
        } while (op != 0);
    }

    // Metodo auxiliar para crear y asignar una alerta por consola
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

        // Pedimos el Evento (Recordá que tu Zona valida CRECIDA_RIO o TORMENTA_SEVERA)
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
    // Método para vincular un equipo de respuesta a una alerta usando tu método de Alerta
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
            System.out.println("Opcion de alerta inválida.");
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
            System.out.println("El equipo '" + equipoSeleccionado.getNombreEquipo() + 
                               " fue asignado correctamente a la alerta ID " + alertaSeleccionada.getIdAlerta());
        } catch (src.excepciones.AsignacionInvalidaException | src.excepciones.EquipoOcupadoException e) {
            System.out.println("ERROR OPERATIVO: " + e.getMessage());
        }
    }

    // PERSONAL TECNICO 
    private void menuPersonalTecnico() {
        int op = -1;
        do {
            System.out.println("\n--- ADMINISTRAR PERSONAL Y EQUIPOS DE RESPUESTA ---");
            System.out.println("1. Listar personal tecnico y equipos");
            System.out.println("2. Agregar nuevo responsable tecnico");
            System.out.println("3. Agregar nuevo equipo de respuesta");
            System.out.println("4. Agregar responsable tecnico a un equipo de respuesta");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            op = leerEntero();

            if (op == 1) {
                System.out.println("\nLISTADO DE PERSONAL:");
                if (gestora.getResponsables().isEmpty()) {
                    System.out.println("   No hay personal tecnico registrado");
                } else {
                    for (ResponsableTecnico r : gestora.getResponsables()) {
                        System.out.println("- " + r.getNombreCompleto() + " | Licencia: " + r.getNumeroLicencia() + " | Especialidad: " + r.getEspecialidad());
                    }
                }
                
                System.out.println("\nEQUIPOS DE RESPUESTA:");
                if (gestora.getEquipos().isEmpty()) {
                    System.out.println("   No hay equipos de respuesta registrados");
                } else {
                    for (EquipoRespuesta eq : gestora.getEquipos()) {
                        String disp = eq.getDisponible() ? "LIBRE" : "OCUPADO";
                        System.out.println("- ID " + eq.getIdEquipo() + ": " + eq.getNombreEquipo() + " [" + disp + "]");
                    }
                }
            } else if (op == 2) {
                agregarNuevoResponsable(); 
            } else if (op == 3) {
                agregarNuevoEquipo();     
            } else if (op == 4) {
                agregarIntegranteAEquipo(); 
            }
        } while (op != 0);
    }

    // Metodo auxiliar para crear nuevo responsable
    private void agregarNuevoResponsable() {
        System.out.println("\n-- ALTA DE RESPONSABLE TÉCNICO --");
        System.out.print("Ingrese DNI (solo números): ");
        int dni = leerEntero();
        
        System.out.print("Ingrese Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Ingrese Apellido: ");
        String apellido = scanner.nextLine();
        
        System.out.print("Ingrese Correo Electrónico: ");
        String correo = scanner.nextLine();
        
        System.out.print("Ingrese número de Celular: ");
        String celular = scanner.nextLine();
        
        System.out.print("Ingrese Especialidad (Ej. Ing. Ambiental): ");
        String especialidad = scanner.nextLine();
        
        System.out.print("Ingrese Número de Licencia/Matrícula: ");
        String licencia = scanner.nextLine();

        ResponsableTecnico nuevoTecnico = new ResponsableTecnico(dni, nombre, apellido, correo, celular, especialidad, licencia);
        gestora.agregarResponsable(nuevoTecnico);
        System.out.println("¡Personal técnico " + nombre + " " + apellido + " agregado con éxito!");
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
        
        System.out.println("-> ¡ÉXITO! Equipo '" + nombre + "' agregado correctamente a la flota.");
    }
    
    private void agregarIntegranteAEquipo() {
        System.out.println("\n-- ASIGNAR RESPONSABLE TÉCNICO A EQUIPO --");

        // Validamos que existan equipos y responsables cargados
        if (gestora.getEquipos().isEmpty()) {
            System.out.println("Error: No hay equipos de respuesta registrados en el sistema.");
            return;
        }
        if (gestora.getResponsables().isEmpty()) {
            System.out.println("Error: No hay responsables técnicos registrados en el sistema.");
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
        System.out.println("\nSeleccione el Responsable Técnico que se sumará:");
        for (int i = 0; i < gestora.getResponsables().size(); i++) {
            ResponsableTecnico rt = gestora.getResponsables().get(i);
            System.out.println((i + 1) + ". " + rt.getNombreCompleto() + " | Especialidad: " + rt.getEspecialidad() + " | Licencia: " + rt.getNumeroLicencia());
        }
        System.out.print("Opción de responsable: ");
        int idxRt = leerEntero() - 1;

        if (idxRt < 0 || idxRt >= gestora.getResponsables().size()) {
            System.out.println("Opción de responsable inválida.");
            return;
        }
        ResponsableTecnico rtElegido = gestora.getResponsables().get(idxRt);

        // Invocamos el metodo de la clase EquipoRespuesta para agregarlo formalmente
        try {
            equipoElegido.agregarIntegrante(rtElegido); 
            
            System.out.println("-> ¡ÉXITO! El técnico " + rtElegido.getNombreCompleto() + 
                               " fue asignado correctamente al equipo '" + equipoElegido.getNombreEquipo() + "'.");
        } catch (Exception e) {
            System.out.println("-> Error al vincular el integrante: " + e.getMessage());
        }
    }

    //INFORMES Y REPORTES
    private void menuInformesYReportes() {
        int op = -1;
        do {
            System.out.println("--- GENERAR INFORMES Y REPORTES ---");
            System.out.println("1. Historico de precipitaciones por zona y estacion");
            System.out.println("2. Ranking de estaciones por volumetria");
            System.out.println("3. Historial de alertas emitidas (filtrado)");
            System.out.println("4. Ficha tecnica operativa de una estación");
            System.out.println("5. Informe de fallas y mantenimiento");
            System.out.println("6. Analisis de anomalía climatica mensual");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opción: ");

            op = leerEntero();
            System.out.println();

            switch (op) {
                case 1:
                    gestora.reportePrecipitacionesHistoricas();
                    break;
                case 2:
                    System.out.print("Ingrese fecha desde (AAAA-MM-DD): ");
                    LocalDate desded = leerFecha();
                    System.out.print("Ingrese fecha hasta (AAAA-MM-DD): ");
                    LocalDate hastad = leerFecha();
                    if (desded != null && hastad != null) {
                        gestora.rankingEstaciones(desded, hastad);
                    }
                    break;
                case 3:
                    gestora.historialAlertas(null, null, null, null);
                    break;
                case 4:
                    if (gestora.getEstaciones().isEmpty()) {
                        System.out.println("No hay estaciones cargadas.\n");
                    } else {
                        System.out.print("Ingrese ID de la estación: ");
                        int idEst = leerEntero();
                        EstacionMeteorologica est = null;
                        for (EstacionMeteorologica e : gestora.getEstaciones()) {
                            if (e.getIdEstacion() == idEst) {
                                est = e;
                                break;
                            }
                        }
                        if (est != null) {
                            gestora.fichaTecnica(est);
                        } else {
                            System.out.println("Estación no encontrada.\n");
                        }
                    }
                    break;
                case 5:
                    gestora.informeFallas();
                    break;
                case 6:
                    if (gestora.getZonas().isEmpty()) {
                        System.out.println("No hay zonas cargadas.\n");
                    } else {
                        System.out.print("Ingrese ID de la zona: ");
                        int idZona = leerEntero();
                        ZonaGeografica zona = null;
                        for (ZonaGeografica z : gestora.getZonas()) {
                            if (z.getIdZona() == idZona) {
                                zona = z;
                                break;
                            }
                        }
                        if (zona != null) {
                            System.out.print("Ingrese año (ej: 2026): ");
                            int anio = leerEntero();
                            System.out.print("Ingrese mes (1-12): ");
                            int mes = leerEntero();
                            gestora.anomaliaClimatica(zona, anio, mes);
                        } else {
                            System.out.println("Zona no encontrada.\n");
                        }
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.\n");
            }
        } while (op != 0);
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
            System.out.println("Fecha inválida. Debe usar el formato AAAA-MM-DD.");
            return null;
        }
    }
}
