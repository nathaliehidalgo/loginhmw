package com.example.mapsandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import edu.udb.mapsandroid.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Place> places;
    private Spinner spinnerMapType;
    private SeekBar seekBarZoom;
    private LatLng defaultLatLng = new LatLng(13.664510, -89.219427);
    private FollowPosition followPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when
        //the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        seekBarZoom = (SeekBar) findViewById(R.id.seekBarZoom);

        //HAGA USO DEL ASISTENTE PARA CREAR setOnSeekBarChangeListener. El único método que modificará es onProgressChanged
        seekBarZoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                chooseMoveCamera(mMap, defaultLatLng, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        spinnerMapType = (Spinner) findViewById(R.id.spinnerMapType);

        //HAGA USO DEL ASISTENTE PARA CREAR setOnItemSelectedListener
        spinnerMapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mapType = spinnerMapType.getSelectedItem().toString();
                if (mMap == null) return;
                if (mapType.equals("MAP_TYPE_NORMAL")) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                } else if (mapType.equals("MAP_TYPE_SATELLITE")) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else if (mapType.equals("MAP_TYPE_HYBRID")) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //Broadcast Receiver. Permanecerá escuchando por actualizaciones de FetchPlacesService
    // (Servicio que intentará descargar los datos) HAGA USO DEL ASISTENTE PARA CREAR BroadcastReceiver

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                places = (ArrayList<Place>) bundle.getSerializable(FetchPlacesService.RESULT);
                if (places != null && places.size() > 0) {
                    if (mMap != null) {
                        for (Place tmp : places) {
                            LatLng tmpLatLng = new LatLng(tmp.getLat(), tmp.getLon());
                            mMap.addMarker(new MarkerOptions().position(tmpLatLng).title(tmp.getPlaceName())
                            );
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(FetchPlacesService.NOTIFICATION));
        /**/
        Intent intent = new Intent(this, FetchPlacesService.class);
        startService(intent);
        if (followPosition != null) {
            followPosition.register(MapsActivity.this);
        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(broadcastReceiver);
        if (followPosition != null)
            followPosition.unRegister(MapsActivity.this);
        super.onPause();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        followPosition = new FollowPosition(this.mMap, MapsActivity.this);
        followPosition.register(MapsActivity.this);

        Marker markerPerth;
        final LatLng PERTH = new LatLng(13.715578, -89.152609);

        //Moveremos la cámara a la Universidad Don Bosco
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLatLng));
        chooseMoveCamera(mMap, defaultLatLng, 30);
        markerPerth = mMap.addMarker(new MarkerOptions().position(PERTH).title("Facultad de estudios Tecnológicos"));
        drawShapes();
    }


    //El siguiente método permitirá movernos de manera animada a una posición del mapa
    private void chooseMoveCamera(GoogleMap googleMap, LatLng tmpLatLng, int zoom) {
        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(zoom).target(tmpLatLng).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    //El siguiente método custom permite agregar diferentes figuras
    private void drawShapes() {
        ShapesMap shapesMap = new ShapesMap(this.mMap);

        ArrayList<LatLng> liness = new ArrayList<>();
        liness.add(new LatLng(13.662734, -89.219577));
        liness.add(new LatLng(13.662627, -89.219577));
        liness.add(new LatLng(13.662626, -89.219472));
        liness.add(new LatLng(13.662734, -89.219473));
        liness.add(new LatLng(13.662734, -89.219577));

        shapesMap.drawLine(liness, 6, Color.CYAN);


        //PolyLines
        ArrayList<LatLng> lines = new ArrayList<>();
        lines.add(new LatLng(13.669485, -89.223040));
        lines.add(new LatLng(13.669132, -89.223095));
        lines.add(new LatLng(13.669096, -89.222872));
        lines.add(new LatLng(13.669450, -89.222819));
        lines.add(new LatLng(13.669485, -89.223040));

        //Llamado al método custom drawLine de shapesMap
        shapesMap.drawLine(lines, 5, Color.RED);
        ArrayList<LatLng> linesD = new ArrayList<>();
        ArrayList<LatLng> poligon = new ArrayList<>();
        poligon.add(new LatLng(13.667109, -89.221006));
        poligon.add(new LatLng(13.664086, -89.220325));
        poligon.add(new LatLng(13.664039, -89.219483));
        poligon.add(new LatLng(13.664571, -89.219526));
        poligon.add(new LatLng(13.664675, -89.218641));
        poligon.add(new LatLng(13.666943, -89.218700));
        poligon.add(new LatLng(13.667182, -89.219595));
        poligon.add(new LatLng(13.667109, -89.221006));


        //Transparencia
        //Valor Hexadecimal, transparencia + color
        //0x: Valor hexadecimal
        //2F: Trasparencia
        //00FF00: Color Hexadecimal
        shapesMap.drawPoligon(poligon, 6, Color.GREEN, 0x2F00FF00);


        //Circulos supermercados
        LatLng circlePoint = new LatLng(13.6668296, -89.2230094);
        shapesMap.drawCircle(circlePoint, 50, Color.MAGENTA, 2, Color.TRANSPARENT);

    }
}