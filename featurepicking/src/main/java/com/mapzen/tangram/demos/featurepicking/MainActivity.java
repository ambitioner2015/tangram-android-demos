package com.mapzen.tangram.demos.featurepicking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.TouchInput;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MapView.OnMapReadyCallback {

    MapController map;
    MapView view;
    TextView textWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (MapView)findViewById(R.id.map);
        view.onCreate(savedInstanceState);
        view.getMapAsync(this, "bubble-wrap/bubble-wrap.yaml");

        textWindow = (TextView)findViewById(R.id.textWindow);
        textWindow.setText("Tap an icon on the map.");
    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;

        map.setFeaturePickListener(new MapController.FeaturePickListener() {
            // A scene file can declare certain groups of features to be 'interactive', meaning that
            // they can be selected in a call to pickFeature(). If an 'interactive' feature is found
            // at the given position, its information is returned in onFeaturePick. If no
            // 'interactive' feature is found, onFeaturePick won't be called.
            @Override
            public void onFeaturePick(Map<String, String> properties, float x, float y) {
                // After a feature is picked from the map, we receive a map of 'properties' as
                // string keys and values, as well as the screen position of the feature's center.
                textWindow.setText("Selected feature properties:");
                for (Map.Entry entry : properties.entrySet()) {
                    textWindow.append("\n" + entry.getKey() + " : " + entry.getValue());
                }
            }
        });

        map.setTapResponder(new TouchInput.TapResponder() {
            @Override
            public boolean onSingleTapUp(float x, float y) {
                return false;
            }

            @Override
            public boolean onSingleTapConfirmed(float x, float y) {
                map.pickFeature(x, y);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        view.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        view.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        view.onLowMemory();
    }
}
