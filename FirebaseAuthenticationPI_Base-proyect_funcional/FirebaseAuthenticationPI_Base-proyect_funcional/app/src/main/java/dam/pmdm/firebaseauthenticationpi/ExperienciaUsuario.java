package dam.pmdm.firebaseauthenticationpi;

public class ExperienciaUsuario {
    private String id; // ID único para la experiencia
    private String nombre; // Nombre de la experiencia
    private boolean completado; // Estado de completado

    // Constructor vacío requerido para Firestore
    public ExperienciaUsuario() {
    }

    // Constructor con parámetros
    public ExperienciaUsuario(String id, String nombre, boolean completado) {
        this.id = id;
        this.nombre = nombre;
        this.completado = completado;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}