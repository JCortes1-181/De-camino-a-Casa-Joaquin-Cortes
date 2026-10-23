package com.example.decaminoacasa;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetalleMascotaActivity extends AppCompatActivity {

    private double currentLat = -33.4489;
    private double currentLng = -70.6693;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_mascota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btn_detail_back);
        btnBack.setOnClickListener(v -> finish());

        TextView tvName = findViewById(R.id.tv_detail_pet_name);
        TextView tvColor = findViewById(R.id.tv_detail_pet_color);
        TextView tvInfo = findViewById(R.id.tv_detail_info_text);
        TextView tvGps = findViewById(R.id.tv_detail_gps_text);
        WebView wvMap = findViewById(R.id.wv_detail_map);

        wvMap.getSettings().setJavaScriptEnabled(true);

        Mascota mascota = (Mascota) getIntent().getSerializableExtra("mascota");
        if (mascota != null) {
            tvName.setText(mascota.getNombre());
            tvColor.setText("Color: " + mascota.getColor());
            tvInfo.setText(mascota.getInformacion());
            tvGps.setText(mascota.getUbicacionGps());

            try {
                String loc = mascota.getUbicacionGps();
                if (loc.contains("Lat:") && loc.contains("Lng:")) {
                    String[] parts = loc.split(",");
                    currentLat = Double.parseDouble(parts[0].replace("Lat:", "").trim());
                    currentLng = Double.parseDouble(parts[1].split(" ")[1].trim());
                }
            } catch (Exception ignored) {}
        }

        loadMapInWebView(wvMap, currentLat, currentLng);
    }

    private void loadMapInWebView(WebView wv, double lat, double lng) {
        String htmlMap = "<!DOCTYPE html><html><head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.css\" />" +
                "<script src=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.js\"></script>" +
                "<style>html, body, #map { height: 100%; margin: 0; padding: 0; }</style>" +
                "</head><body><div id=\"map\"></div><script>" +
                "var map = L.map('map').setView([" + lat + ", " + lng + "], 15);" +
                "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 19 }).addTo(map);" +
                "L.marker([" + lat + ", " + lng + "]).addTo(map).bindPopup('Ubicación de la mascota').openPopup();" +
                "</script></body></html>";
        wv.loadDataWithBaseURL(null, htmlMap, "text/html", "UTF-8", null);
    }
}