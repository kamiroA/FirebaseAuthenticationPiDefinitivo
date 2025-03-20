package dam.pmdm.firebaseauthenticationpi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser ;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextInputEditText etEmail, etPassword;
    Button btnLogin;
    ImageButton btnFacebook, btnGoogle;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView tvClickRegister;
    CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progressBar);
        tvClickRegister = findViewById(R.id.tvClickRegister);
        btnLogin = findViewById(R.id.btnLogin);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnGoogle = findViewById(R.id.btnGoogle);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> loginUser ());

        // Configurar Google Sign-In
        configureGoogleSignIn();

        // Configurar el botón de Google Sign-In
        btnGoogle.setOnClickListener(view -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            googleSignInLauncher.launch(signInIntent);
        });

        // Configurar Facebook Sign-In
        configureFacebookSignIn();

        btnFacebook.setOnClickListener(view -> signInWithFacebook());

        tvClickRegister.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Registro.class);
            startActivity(i);
        });
        etEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                etEmail.setText("");
            }
        });

        etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                etPassword.setText("");
            }
        });
    }

    private void loginUser () {
        String emailText = etEmail.getText().toString().trim();
        String passwordText = etPassword.getText().toString().trim();

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(Login.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser  user = mAuth.getCurrentUser ();
                        verificarRegistro(user.getUid());
                    } else {
                        String errorMessage = (task.getException() != null) ? task.getException().getMessage() : "Error desconocido";
                        Toast.makeText(Login.this, "Error al iniciar sesión: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void verificarRegistro(String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("usuarios").document(uid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // El usuario ya está registrado, redirigir a MainActivity
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // El usuario no está registrado, guardar datos del usuario
                    guardarDatosUsuario(uid);
                }
            } else {
                Log.e("Firestore", "Error al verificar el registro", task.getException());
                Toast.makeText(Login.this, "Error al verificar el registro", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarDatosUsuario(String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Aquí puedes agregar más información del usuario si es necesario
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("email", etEmail.getText().toString().trim());
        usuario.put("nombre", "Nombre del usuario"); // Cambia esto por el nombre real
        usuario.put("apellidos", "Apellidos del usuario"); // Cambia esto por los apellidos reales
        usuario.put("profileImage", "Imagen en Base64"); // Cambia esto por la imagen en Base64 real

        db.collection("usuarios").document(uid)
                .set(usuario)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Login.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                    // Redirigir a MainActivity después de guardar los datos
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Login.this, "Error al guardar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            handleGoogleSignInResult(account);
                        } catch (ApiException e) {
                            Log.w("Google SignIn", "Error en Google Sign-In", e);
                            Toast.makeText(this, "Error en Google Sign-In", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void handleGoogleSignInResult(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser  user = mAuth.getCurrentUser ();
                        verificarRegistro(user.getUid());
                    } else {
                        Log.e("GoogleAuth", "Error en la autenticación con Google", task.getException());
                        Toast.makeText(Login.this, "Error al autenticar con Google", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void configureFacebookSignIn() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(Login.this, "Inicio de sesión con Facebook cancelado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Login.this, "Error en Facebook Sign-In", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser  user = mAuth.getCurrentUser ();
                        verificarRegistro(user.getUid());
                    } else {
                        Toast.makeText(Login.this, "Error en Facebook Sign-In", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}