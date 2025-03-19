package dam.pmdm.firebaseauthenticationpi;

public class Experiencia {
    private String descripcion;
    private String nombre;

    public Experiencia() {
        // Constructor vacío requerido para Firebase
    }

    public Experiencia(String descripcion, String nombre) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }
}