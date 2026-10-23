package com.example.decaminoacasa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.Locale;

public class AgregarMascotaActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private EditText etLocation;
    private WebView wvMapPicker;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private double currentLat = -33.4489;
    private double currentLng = -70.6693;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_mascota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etName = findViewById(R.id.et_pet_name);
        EditText etColor = findViewById(R.id.et_pet_color);
        EditText etInfo = findViewById(R.id.et_pet_info);
        etLocation = findViewById(R.id.et_pet_location);
        wvMapPicker = findViewById(R.id.wv_map_picker);

        wvMapPicker.getSettings().setJavaScriptEnabled(true);
        wvMapPicker.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith("latlng://")) {
                    try {
                        String coords = url.replace("latlng://", "");
                        String[] parts = coords.split(",");
                        currentLat = Double.parseDouble(parts[0]);
                        currentLng = Double.parseDouble(parts[1]);
                        String locStr = String.format(Locale.getDefault(), "Lat: %.4f, Lng: %.4f (Mapa)", currentLat, currentLng);
                        etLocation.setText(locStr);
                        Toast.makeText(AgregarMascotaActivity.this, "¡Ubicación fijada en el mapa!", Toast.LENGTH_SHORT).show();
                    } catch (Exception ignored) {}
                    return true;
                }
                return false;
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        verificarYObtenerUbicacion();

        Button btnSave = findViewById(R.id.btn_save_pet);
        Button btnCancel = findViewById(R.id.btn_cancel_pet);

        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String color = etColor.getText().toString().trim();
            String info = etInfo.getText().toString().trim();
            String location = etLocation.getText().toString().trim();

            if (name.isEmpty() || color.isEmpty() || info.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                Mascota mascota = new Mascota(name, color, info, location);
                MascotaRepository.agregarMascota(mascota);
                Toast.makeText(this, "¡Mascota agregada con éxito!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void verificarYObtenerUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            obtenerCoordenadasGps();
        }
    }

    private void obtenerCoordenadasGps() {
        try {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        currentLat = location.getLatitude();
                        currentLng = location.getLongitude();
                        String gpsStr = String.format(Locale.getDefault(), "Lat: %.4f, Lng: %.4f (GPS Real)", currentLat, currentLng);
                        etLocation.setText(gpsStr);
                        loadPickerMap(currentLat, currentLng);
                        Toast.makeText(this, "¡Ubicación GPS real obtenida!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Fallback to last location
                        fusedLocationClient.getLastLocation().addOnSuccessListener(this, lastLoc -> {
                            if (lastLoc != null) {
                                currentLat = lastLoc.getLatitude();
                                currentLng = lastLoc.getLongitude();
                            }
                            String gpsStr = String.format(Locale.getDefault(), "Lat: %.4f, Lng: %.4f (GPS)", currentLat, currentLng);
                            etLocation.setText(gpsStr);
                            loadPickerMap(currentLat, currentLng);
                        });
                    }
                });
        } catch (SecurityException e) {
            loadPickerMap(currentLat, currentLng);
        }
    }

    private void loadPickerMap(double lat, double lng) {
        String htmlMap = "<!DOCTYPE html><html><head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.css\" />" +
                "<script src=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.js\"></script>" +
                "<style>html, body, #map { height: 100%; margin: 0; padding: 0; }</style>" +
                "</head><body><div id=\"map\"></div><script>" +
                "var map = L.map('map').setView([" + lat + ", " + lng + "], 15);" +
                "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 19 }).addTo(map);" +
                "var marker = L.marker([" + lat + ", " + lng + "], {draggable: true}).addTo(map);" +
                "map.on('click', function(e) {" +
                "  marker.setLatLng(e.latlng);" +
                "  window.location.href = 'latlng://' + e.latlng.lat + ',' + e.latlng.lng;" +
                "});" +
                "marker.on('dragend', function(e) {" +
                "  var pos = marker.getLatLng();" +
                "  window.location.href = 'latlng://' + pos.lat + ',' + pos.lng;" +
                "});" +
                "</script></body></html>";
        wvMapPicker.loadDataWithBaseURL(null, htmlMap, "text/html", "UTF-8", null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String[] requiredPermissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        super.onRequestPermissionsResult(requestCode, requiredPermissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerCooperadasGpsIfAllowed();
            } else {
                loadPickerMap(currentLat, currentLng);
                Toast.makeText(this, "Usando ubicación por defecto (permiso denegado)", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void obtenerCooperadasGpsIfAllowed() {
        obtenerCoordenadasGps();
    }
}