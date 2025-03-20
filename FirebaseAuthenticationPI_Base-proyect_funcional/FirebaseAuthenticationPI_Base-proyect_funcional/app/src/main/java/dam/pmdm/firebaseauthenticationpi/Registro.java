package dam.pmdm.firebaseauthenticationpi;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.yalantis.ucrop.UCrop;
import android.Manifest;

public class Registro extends AppCompatActivity {

    // Inicializar variables
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_CODE = 100;
    private Uri imageUri;
    TextInputEditText etEmail, etPassword, etPasswordConfirm, etApellido, etNombre;
    Button btnRegistro, btnBack;
    ProgressBar progressBar;
    TextView tvClickLogin;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView lineaArriba;
    ScrollView svRegistro;
    ConstraintLayout topnav;
    ShapeableImageView iVUploadProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Asignar los componentes a la vista
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etApellido = findViewById(R.id.etApellido);
        etNombre = findViewById(R.id.etNombre);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        btnRegistro = findViewById(R.id.btnRegistro);
        progressBar = findViewById(R.id.progressBar);
        tvClickLogin = findViewById(R.id.tvClickLogin);
        btnBack = findViewById(R.id.btnBack);
        iVUploadProfilePic = findViewById(R.id.btnUploadProfilePic);
        lineaArriba = findViewById(R.id.lineaArriba);
        svRegistro = findViewById(R.id.sVRegistro);
        topnav = findViewById(R.id.topnav);

        // Obtener el email pasado desde la actividad de inicio de sesión
        String email = getIntent().getStringExtra("email");
        if (email != null) {
            etEmail.setText(email);
        }

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // Configurar el selector de imágenes
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                imageUri = result.getData().getData();
                startCrop(imageUri);
            }
        });

        // Configurar el botón de registro
        btnRegistro.setOnClickListener(view -> registerUser ());

        // Configurar el botón de volver
        btnBack.setOnClickListener(view -> finish());

        // Configurar el botón de subir imagen de perfil
        iVUploadProfilePic.setOnClickListener(v -> openFileChooser());

        // Configurar el enlace a la actividad de inicio de sesión
        tvClickLogin.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });
    }

    private void registerUser () {
        // Activar la progressBar
        progressBar.setVisibility(View.VISIBLE);

        String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString().trim();
        String passwordConfirm = Objects.requireNonNull(etPasswordConfirm.getText()).toString().trim();
        String nombre = Objects.requireNonNull(etNombre.getText()).toString().trim();
        String apellido = Objects.requireNonNull(etApellido.getText()).toString().trim();

        // Comprobar si los campos están vacíos
        if (email.isEmpty() || password.isEmpty() || !password.equals(passwordConfirm)) {
            Toast.makeText(this, "Por favor, completa todos los campos correctamente.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        // Crear el usuario en Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                String uid = mAuth.getCurrentUser ().getUid();
                uploadImageToFirestore(uid, nombre, apellido, email);
            } else {
                Toast.makeText(this, "Error al crear la cuenta.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFileChooser() {
        // Verificar si se tienen permisos para acceder a las imágenes
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Si no se tienen permisos, solicitarlos
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            // Si ya se tienen permisos, abrir el selector de imágenes
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            imagePickerLauncher.launch(Intent.createChooser(intent, "Selecciona una imagen"));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, puedes continuar con la funcionalidad que requiere el permiso
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
            } else {
                // Permiso denegado, muestra un mensaje al usuario
                Toast.makeText(this, "Permiso denegado para acceder a las imágenes", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            startCrop(imageUri);
        } else if (requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                // Mostrar la imagen recortada en el ImageView
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    Bitmap circularBitmap = getCircularBitmap(bitmap); // Obtener el bitmap circular
                    iVUploadProfilePic.setImageBitmap(circularBitmap); // Establecer la imagen circular en el ImageView
                    imageUri = resultUri; // Actualizar imageUri con la imagen recortada
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            if (cropError != null) {
                Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCrop(Uri uri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_image.jpg"));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(100);
        options.setToolbarColor(ContextCompat.getColor(this, R.color.turquesa));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.turquesa_opacado));
        options.setActiveControlsWidgetColor(ContextCompat.getColor(this, R.color.black));

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1) // Aspecto circular
                .withOptions(options)
                .start(this);
    }

    private Bitmap getCircularBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int radius = Math.min(width, height) / 2;

        Bitmap output = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        RectF rectF = new RectF(0, 0, radius * 2, radius * 2);

        paint.setAntiAlias(true);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, -((width - radius * 2) / 2), -((height - radius * 2) / 2), paint);

        return output;
    }

    private void uploadImageToFirestore(String uid, String nombre, String apellido, String email) {
        if (imageUri != null) {
            try {
                // Obtener el bitmap de la imagen
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                // Comprimir la imagen
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); // 50 es la calidad de compresión
                byte[] data = baos.toByteArray();

                // Convertir el byte[] a una cadena Base64
                String base64Image = Base64.encodeToString(data, Base64.DEFAULT);

                // Guardar los datos del usuario en Firestore
                guardarDatosUsuario(uid, nombre, apellido, email, base64Image);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor, selecciona una imagen.", Toast.LENGTH_SHORT).show();
        }
    }

    public void guardarDatosUsuario(String uid, String nombre, String apellidos, String email, String base64Image) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Crear un objeto de usuario
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", nombre);
        usuario.put("apellidos", apellidos);
        usuario.put("email", email);
        usuario.put("profileImage", base64Image); // Guardar la imagen en Base64

        // Guardar en Firestore
        db.collection("usuarios").document(uid)
                .set(usuario)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Datos del usuario guardados correctamente");
                    Toast.makeText(this, "Registro completado.", Toast.LENGTH_SHORT).show();
                    // Redirigir a la pantalla de login o a otra actividad
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error al guardar los datos", e);
                });
    }
}