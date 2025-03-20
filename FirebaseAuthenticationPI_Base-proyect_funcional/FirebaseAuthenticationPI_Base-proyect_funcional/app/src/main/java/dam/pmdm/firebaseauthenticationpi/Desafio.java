package dam.pmdm.firebaseauthenticationpi;

import java.util.ArrayList;
import java.util.List;

public class Desafio {
    private String descripcion;
    private String nombre;
    private List<Experiencia> experiencias; // Cambiado a List

    public Desafio() {
        // Constructor vacío requerido para Firebase
        this.experiencias = new ArrayList<>(); // Inicializar como lista vacía
    }

    public Desafio(String descripcion, String nombre, List<Experiencia> experiencias) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.experiencias = new ArrayList<>(); // Inicializar como lista vacía
        if (experiencias != null) {
            for (Experiencia exp : experiencias) {
                if (exp != null) { // Omitir el null
                    this.experiencias.add(exp);
                }
            }
        }
    }
    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Experiencia> getExperiencias() {
        return experiencias;
    }
}