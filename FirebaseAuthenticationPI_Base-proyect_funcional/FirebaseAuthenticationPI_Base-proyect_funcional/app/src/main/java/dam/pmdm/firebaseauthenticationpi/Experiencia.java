package dam.pmdm.firebaseauthenticationpi;

public class Experiencia {
    private String descripcion;
    private String nombre;
    private int id; // Agregar el id
    private boolean completed; // Campo para indicar si la experiencia está completada

    public Experiencia() {
        // Constructor vacío requerido para Firebase
    }

    public Experiencia(String descripcion, String nombre, int id, boolean completed) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.id = id;
        this.completed = completed; // Inicializar el campo completed
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public boolean isCompleted() { // Método para obtener el estado de completado
        return completed;
    }

    public void setCompleted(boolean completed) { // Método para establecer el estado de completado
        this.completed = completed;
    }
}