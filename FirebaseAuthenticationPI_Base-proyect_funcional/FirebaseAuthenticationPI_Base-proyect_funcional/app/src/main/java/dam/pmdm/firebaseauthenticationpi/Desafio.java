package dam.pmdm.firebaseauthenticationpi;

import java.util.ArrayList;
import java.util.List;

public class Desafio {
    private String nombre;
    private String descripcion;
    private List<Experiencia> experiencias; // Asegúrate de que esto sea una lista

    public Desafio() {
        // Constructor vacío requerido para Firebase
        this.experiencias = new ArrayList<>(); // Inicializar la lista
    }

    public Desafio(String nombre, String descripcion, List<Experiencia> experiencias) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.experiencias = (experiencias != null) ? experiencias : new ArrayList<>(); // Inicializar si es null
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Experiencia> getExperiencias() {
        return experiencias;
    }

    // Método para establecer la lista de experiencias
    public void setExperiencias(List<Experiencia> experiencias) {
        this.experiencias = experiencias;
    }
}