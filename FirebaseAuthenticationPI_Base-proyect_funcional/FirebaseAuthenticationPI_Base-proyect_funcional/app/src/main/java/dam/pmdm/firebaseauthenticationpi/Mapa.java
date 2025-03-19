package dam.pmdm.firebaseauthenticationpi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    //Para manejar nuestro fragmento de mapa debemos crear un objeto GoogleMap
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        //Para mostrar un mapa debemos usar un SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fgMap);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        //this.mMap.setOnMapClickListener(this);
        //this.mMap.setOnMapLongClickListener(this);

        //Configurar tipo de mapa
        // mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//      mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//      mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // LISTA DE UBICACIONES
        //LatLng cascoViejo = new LatLng(43.2581099,-2.9246896);
        LatLng cafeBarBilbao = new LatLng(43.2587326,-2.922716);
        LatLng charlyTaberna = new LatLng(43.2588329,-2.9249083);
        LatLng gureToki = new LatLng(43.2593827,-2.9248702);
        LatLng barBacaicoa = new LatLng(43.2584668,-2.9238667);
        LatLng  baste = new LatLng(43.3270562,-3.0120704);
        LatLng  berton = new LatLng(43.2584133,-2.9276452);
        LatLng  barFermin = new LatLng(43.2581105,-2.926097);
        LatLng  motrikes = new LatLng(43.2571331,-2.9250823);
        LatLng  conBdeBilbao = new LatLng(43.2582938,-2.9283214);
        LatLng  victorMontes = new LatLng(43.2589075,-2.9249399);
        LatLng  barIrrintzi = new LatLng(43.3275103,-3.0142095);
        LatLng  txiriboga = new LatLng(43.2578376,-2.9280001);

        // Añadir marcadores al mapa
       /* mMap.addMarker(new MarkerOptions()
                        .position(cascoViejo)
                        .title("Casco Viejo Bilbao")
                        .snippet("El Casco Viejo es el antiguo núcleo medieval, ")
                        .snippet("un animado barrio a la orilla de la ría con tiendas modernas ")
                        .snippet("y tabernas tradicionales en las Siete Calles."))
                .showInfoWindow();*/

        mMap.addMarker(new MarkerOptions()
                        .position(cafeBarBilbao)
                        .title("Cafe Bar Bilbao")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();
        mMap.addMarker(new MarkerOptions()
                        .position(charlyTaberna)
                        .title("Charly Taberna")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                        .position(gureToki)
                        .title("Gure Toki")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                        .position(barBacaicoa)
                        .title("Bar Bacaicoa")
                        .snippet("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                        .position(baste)
                        .title("Baste")
                        .snippet("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                        .position(berton)
                        .title("Berton")
                        .snippet("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();


        mMap.addMarker(new MarkerOptions()
                        .position(barFermin)
                        .title("Bar Fermin")
                        .snippet("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();


        mMap.addMarker(new MarkerOptions()
                        .position(motrikes)
                        .title("Motrikes")
                        .snippet("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();


        mMap.addMarker(new MarkerOptions()
                        .position(conBdeBilbao)
                        .title("Con B de Bilbao")
                        .snippet("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();


        mMap.addMarker(new MarkerOptions()
                        .position(victorMontes)
                        .title("Victor Montes")
                        .snippet("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                        .position(barIrrintzi)
                        .title("Bar Irrintzi")
                        .snippet("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                        .position(txiriboga)
                        .title("Txiriboga")
                        .snippet("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)))
                .showInfoWindow();






        //  Movemos la camara del mapa a la ubicacion de referencia y vamoa a aplicar un zoom
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(victorMontes, 16);
        mMap.animateCamera(cameraUpdate);


        // Configurar un listener para el clic en la ventana del marcador y navegar hasta el sitio
        // Aunque podemos usar los botones por defecto del mapa

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                String uri = "google.navigation:q="
                        + marker.getPosition().latitude
                        + ","
                        + marker.getPosition().longitude
                        // W = es andando
                        // D = conducir
                        // B = bicicleta
                        // R = transporte público
                        + "&mode=w";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                i.setPackage("com.google.android.apps.maps");
                startActivity(i);
            }
        });

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }
}
