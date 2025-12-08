/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consultoriomedico;

/**
 *
 * @author Educrack0101
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("|  SISTEMA DE GESTION DE CONSULTORIO MEDICO (ABC)   |");
        System.out.println("-----------------------------------------------------");
        
        Consultorio consultorio = new Consultorio();
        
        System.out.println(" INCISO A: ALTA DE MEDICOS Y CONSULTAS");
        consultorio.inicializarDatos();
        
        consultorio.mostrarMedicos();
        consultorio.mostrarConsultas();
        
        System.out.println("  INCISO B: BAJA DE MEDICO Y SUS CONSULTAS  ");
        System.out.println("Eliminando al Dr. Juan García...");
        consultorio.bajarMedico("Juan", "Garcia");
        
        consultorio.mostrarMedicos();
        consultorio.mostrarConsultas();
        
        System.out.println(" INCISO C: REPROGRAMAR CONSULTAS DE NAVIDAD Y ANIO NUEVO ");
        consultorio.cambiarConsultasNavidad();
        
        consultorio.mostrarConsultas();
        
        System.out.println(" INCISO D (OPCIONAL): PACIENTES EN FECHA ESPECIFICA");
        consultorio.mostrarPacientesPorFecha("15/03/2024");
        consultorio.mostrarPacientesPorFecha("27/12/2024");
        
        System.out.println("----------------------------------------------------");
        System.out.println("|  PROGRAMA EJECUTADO EXITOSAMENTE                |");
        System.out.println("|  Los archivos medicos.dat y consultas.dat         |");
        System.out.println("|  fueron creados/actualizados                      |");
        System.out.println("----------------------------------------------------");
    }
}
