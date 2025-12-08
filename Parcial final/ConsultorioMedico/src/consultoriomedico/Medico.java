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
public class Medico implements Serializable {
    private int idMed;
    private String nombreMed;
    private String apellidoMed;
    private int aniosExperiencia;
    
    public Medico(int idMed, String nombreMed, String apellidoMed, 
                  int aniosExperiencia) {
        this.idMed = idMed;
        this.nombreMed = nombreMed;
        this.apellidoMed = apellidoMed;
        this.aniosExperiencia = aniosExperiencia;
    }
    
    public int getIdMed() { return idMed; }
    public void setIdMed(int idMed) { this.idMed = idMed; }
    
    public String getNombreMed() { return nombreMed; }
    public void setNombreMed(String nombreMed) { this.nombreMed = nombreMed; }
    
    public String getApellidoMed() { return apellidoMed; }
    public void setApellidoMed(String apellidoMed) { 
        this.apellidoMed = apellidoMed; 
    }
    
    public int getAniosExperiencia() { return aniosExperiencia; }
    public void setAniosExperiencia(int aniosExperiencia) { 
        this.aniosExperiencia = aniosExperiencia; 
    }
    
    @Override
    public String toString() {
        return "ID: " + idMed + " - Dr(a). " + nombreMed + " " + apellidoMed + 
               " (" + aniosExperiencia + " anios de experiencia)";
    }
}