/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consultoriomedico;
import java.io.*;
import java.util.ArrayList;
public class Consultorio {
    private ArrayList<Medico> listaMedicos;
    private ArrayList<Consulta> listaConsultas;
    private static final String ARCHIVO_MEDICOS = "medicos.dat";
    private static final String ARCHIVO_CONSULTAS = "consultas.dat";
    
    public Consultorio() {
        listaMedicos = new ArrayList<>();
        listaConsultas = new ArrayList<>();
        cargarDatos();
    }

    public void guardarDatos() {
        guardarMedicos();
        guardarConsultas();
    }
    
    private void guardarMedicos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_MEDICOS))) {
            oos.writeObject(listaMedicos);
            System.out.println("Medicos guardados en archivo");
        } catch (IOException e) {
            System.out.println("Error al guardar medicos: " + e.getMessage());
        }
    }
    
    private void guardarConsultas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_CONSULTAS))) {
            oos.writeObject(listaConsultas);
            System.out.println("Consultas guardadas en archivo");
        } catch (IOException e) {
            System.out.println("Error al guardar consultas: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        cargarMedicos();
        cargarConsultas();
    }
    
    @SuppressWarnings("unchecked")
    private void cargarMedicos() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_MEDICOS))) {
            listaMedicos = (ArrayList<Medico>) ois.readObject();
            System.out.println("Medicos cargados desde archivo");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de medicos no existe, se creara uno nuevo");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar médicos: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void cargarConsultas() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_CONSULTAS))) {
            listaConsultas = (ArrayList<Consulta>) ois.readObject();
            System.out.println("Consultas cargadas desde archivo");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de consultas no existe, se creara uno nuevo");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar consultas: " + e.getMessage());
        }
    }

    public void altaMedico(Medico medico) {
        listaMedicos.add(medico);
        guardarMedicos();
        System.out.println("  Medico agregado: " + medico.getNombreMed() + 
                         " " + medico.getApellidoMed());
    }
    
    public void altaConsulta(Consulta consulta) {
        listaConsultas.add(consulta);
        guardarConsultas();
        System.out.println("  Consulta agregada para: " + 
                         consulta.getNombrePaciente() + " " + 
                         consulta.getApellidoPaciente());
    }
    
    public void inicializarDatos() {
        System.out.println("ALTA DE 3 MEDICOS:");
        altaMedico(new Medico(1, "Juan", "Garcia", 15));
        altaMedico(new Medico(2, "Maria", "Lopez", 10));
        altaMedico(new Medico(3, "Carlos", "Martinez", 20));
        
        System.out.println("ALTA DE 9 CONSULTAS:");
        altaConsulta(new Consulta(1, "Pedro", "Perez", 1, "15/11/2024"));
        altaConsulta(new Consulta(2, "Ana", "Gonzalez", 1, "20/11/2024"));
        altaConsulta(new Consulta(3, "Luis", "Ramirez", 1, "24/12/2024"));
        
        altaConsulta(new Consulta(4, "Sofia", "Torres", 2, "18/11/2024"));
        altaConsulta(new Consulta(5, "Diego", "Flores", 2, "25/12/2024"));
        altaConsulta(new Consulta(6, "Laura", "Castro", 2, "31/12/2024"));
        
        altaConsulta(new Consulta(7, "Roberto", "Vargas", 3, "10/12/2024"));
        altaConsulta(new Consulta(8, "Carmen", "Ruiz", 3, "01/01/2025"));
        altaConsulta(new Consulta(9, "Jorge", "Diaz", 3, "15/03/2024"));
        
        System.out.println("Datos inicializados correctamente");
    }
    
    public void bajarMedico(String nombre, String apellido) {
        Medico medicoAEliminar = null;
        
        for (Medico med : listaMedicos) {
            if (med.getNombreMed().equalsIgnoreCase(nombre) && 
                med.getApellidoMed().equalsIgnoreCase(apellido)) {
                medicoAEliminar = med;
                break;
            }
        }
        
        if (medicoAEliminar != null) {
            int idMedico = medicoAEliminar.getIdMed();
            int consultasEliminadas = 0;
            
            for (int i = listaConsultas.size() - 1; i >= 0; i--) {
                if (listaConsultas.get(i).getIdMed() == idMedico) {
                    listaConsultas.remove(i);
                    consultasEliminadas++;
                }
            }
            
            listaMedicos.remove(medicoAEliminar);
            guardarDatos();
            
            System.out.println("Medico eliminado: Dr(a). " + nombre + " " + apellido);
            System.out.println("Consultas eliminadas: " + consultasEliminadas);
        } else {
            System.out.println("Medico no encontrado: " + nombre + " " + apellido);
        }
    }

    public void cambiarConsultasNavidad() {
        int consultasCambiadas = 0;
        
        System.out.println("Buscando consultas en navidad o anio nuevo...");
        
        for (Consulta consulta : listaConsultas) {
            String fecha = consulta.getDia();
            
            if (fecha.equals("24/12/2024") || fecha.equals("25/12/2024") ||
                fecha.equals("31/12/2024") || fecha.equals("01/01/2025")) {
                
                String nuevaFecha = "27/12/2024";
                System.out.println("Paciente: " + consulta.getNombrePaciente() + 
                                 " " + consulta.getApellidoPaciente());
                System.out.println("    Fecha original: " + fecha + 
                                 " Nueva fecha: " + nuevaFecha);
                consulta.setDia(nuevaFecha);
                consultasCambiadas++;
            }
        }
        
        if (consultasCambiadas > 0) {
            guardarConsultas();
            System.out.println("Total de consultas reprogramadas: " + 
                             consultasCambiadas);
        } else {
            System.out.println("No hay consultas en fechas navidenias");
        }
    }
    
    public void mostrarPacientesPorFecha(String fecha) {
        System.out.println("PACIENTES ATENDIDOS EL " + fecha + ":");
        boolean hayPacientes = false;
        
        for (Consulta consulta : listaConsultas) {
            if (consulta.getDia().equals(fecha)) {
                System.out.println("  • " + consulta.getNombrePaciente() + " " + 
                                 consulta.getApellidoPaciente() + 
                                 " (Consulta ID: " + consulta.getId() + ")");
                hayPacientes = true;
            }
        }
        
        if (!hayPacientes) {
            System.out.println("No hay pacientes para esta fecha");
        }
    }
    
    public void mostrarMedicos() {
        System.out.println(" " + "=".repeat(60));
        System.out.println("LISTA DE MEDICOS REGISTRADOS");
        System.out.println("=".repeat(60));
        
        if (listaMedicos.isEmpty()) {
            System.out.println("No hay medicos registrados");
        } else {
            for (Medico med : listaMedicos) {
                System.out.println(med);
            }
        }
        System.out.println("=".repeat(60));
    }
    
    public void mostrarConsultas() {
        System.out.println(" " + "=".repeat(60));
        System.out.println("LISTA DE CONSULTAS REGISTRADAS");
        System.out.println("=".repeat(60));
        
        if (listaConsultas.isEmpty()) {
            System.out.println("No hay consultas registradas");
        } else {
            for (Consulta cons : listaConsultas) {
                System.out.println(cons);
            }
        }
        System.out.println("=".repeat(60));
    }
}