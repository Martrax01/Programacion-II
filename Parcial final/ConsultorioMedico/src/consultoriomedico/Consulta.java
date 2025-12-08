/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consultoriomedico;
import java.io.Serializable;

/**
 *
 * @author Educrack0101
 */
public class Consulta implements Serializable {
    private int id;
    private String nombrePaciente;
    private String apellidoPaciente;
    private int idMed;
    private String dia;
    
    public Consulta(int id, String nombrePaciente, String apellidoPaciente, 
                    int idMed, String dia) {
        this.id = id;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
        this.idMed = idMed;
        this.dia = dia;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { 
        this.nombrePaciente = nombrePaciente; 
    }
    
    public String getApellidoPaciente() { return apellidoPaciente; }
    public void setApellidoPaciente(String apellidoPaciente) { 
        this.apellidoPaciente = apellidoPaciente; 
    }
    
    public int getIdMed() { return idMed; }
    public void setIdMed(int idMed) { this.idMed = idMed; }
    
    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }
    
    @Override
    public String toString() {
        return "Consulta ID: " + id + " - Paciente: " + nombrePaciente + " " + 
               apellidoPaciente + " - Fecha: " + dia + " (Medico ID: " + idMed + ")";
    }
}