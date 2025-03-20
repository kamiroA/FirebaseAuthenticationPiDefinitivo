package dam.pmdm.firebaseauthenticationpi;

public class User {
    private String nombre;
    private String apellidos;
    private String email;
    private String profileImageBase64;

    // Constructor, getters y setters
    public User(String nombre, String apellidos, String email, String profileImageBase64) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.profileImageBase64 = profileImageBase64;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImageBase64() {
        return profileImageBase64;
    }
}
