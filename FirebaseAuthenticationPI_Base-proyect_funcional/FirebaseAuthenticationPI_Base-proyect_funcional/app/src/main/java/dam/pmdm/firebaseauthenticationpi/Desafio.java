package dam.pmdm.firebaseauthenticationpi;

import java.util.Map;

public class Desafio {
    private String descripcion;
    private String nombre;
    private Map<String, Experiencia> experiencias;

    public Desafio() {
        // Constructor vacío requerido para Firebase
    }

    public Desafio(String descripcion, String nombre, Map<String, Experiencia> experiencias) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.experiencias = experiencias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public Map<String, Experiencia> getExperiencias() {
        return experiencias;
    }
}
