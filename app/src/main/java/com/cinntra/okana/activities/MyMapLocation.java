package com.cinntra.okana.activities;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.ActivityMyMapLocationBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.MapData;
import com.cinntra.okana.receivers.FetchURL;
import com.cinntra.okana.receivers.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;
import java.util.List;

public class MyMapLocation extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    private ActivityMyMapLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyMapLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            mdplist = (List<MapData>) getIntent().getExtras().getSerializable("MapList");
        } else {
            mdplist = new ArrayList<>();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    public BitmapDescriptor getEndCapIcon(Context context, int color) {

        // mipmap icon - white arrow, pointing up, with point at center of image
        // you will want to create:  mdpi=24x24, hdpi=36x36, xhdpi=48x48, xxhdpi=72x72, xxxhdpi=96x96
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_arrow_right_24dp);

        // set the bounds to the whole image (may not be necessary ...)
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        // overlay (multiply) your color over the white icon
        drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        // create a bitmap from the drawable
        android.graphics.Bitmap bitmap = android.graphics.Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // render the bitmap on a blank canvas
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);

        // create a BitmapDescriptor from the new bitmap
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return false;
    }


    private Circle circle;
    LatLng homeLatLng;
    List<LatLng> lngList = new ArrayList<>();
    List<MapData> mdplist;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*mMap.setMinZoomPreference(8.0f);
        mMap.setMaxZoomPreference(16.0f);*/
        float zoomLevel = 12.0f;
        if (mdplist.size() > 1) {
            for (int i = 0; i < mdplist.size(); i++) {
                double resultLOng = Globals.convertStringToDouble(mdplist.get(i).getLong());
                double resultLat = Globals.convertStringToDouble(mdplist.get(i).getLat());

                if (resultLat == 0.0 && resultLOng == 0.0) {

                }else {

                    homeLatLng = new LatLng(resultLat,resultLOng);
                    lngList.add(homeLatLng);
                    LatLng place1 = new LatLng(resultLat,resultLOng);
                    // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1, zoomLevel));
                    mMap.addMarker(new MarkerOptions().position(place1)
                            .title(mdplist.get(i).getAddress())
                            .snippet("Lat = " + resultLat + ", Long = " + resultLOng)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel));
                   /* homeLatLng = new LatLng(Double.parseDouble(mdplist.get(i).getLat()), Double.parseDouble(mdplist.get(i).getLong()));
                    lngList.add(homeLatLng);
                    LatLng place1 = new LatLng(Double.parseDouble(mdplist.get(i).getLat()), Double.parseDouble(mdplist.get(i).getLong()));
                    // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1, zoomLevel));
                    mMap.addMarker(new MarkerOptions().position(place1)
                            .title(mdplist.get(i).getAddress())
                            .snippet("Lat = " + Double.parseDouble(mdplist.get(i).getLat()) + ", Long = " + Double.parseDouble(mdplist.get(i).getLong()))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel));
*/

                }


            }

            if (lngList.size() > 1) {

                for (int i = 0; i < lngList.size(); i++) {
                    if (i < lngList.size() - 1) {
                        drawPolylineWithArrowEndcap(this, mMap, lngList.get(i), lngList.get(i + 1));
                    }
                }

                new FetchURL(MyMapLocation.this).execute(getUrl(lngList.get(0), lngList.get(lngList.size() - 1), "driving"), "driving");

            }


          /*  circle = mMap.addCircle(new CircleOptions()
                    .center(homeLatLng)
                    .radius(100)
                    .strokeWidth(5)
                    .strokeColor(Color.GREEN)
                    .fillColor(Color.argb(128, 255, 0, 0))
                    .clickable(true));
            int arrowColor = Color.BLUE; // change this if you want another color (Color.BLUE)
            int lineColor = Color.RED;

            BitmapDescriptor endCapIcon = getEndCapIcon(this,arrowColor);
            mMap.addPolyline((new PolylineOptions()).addAll(lngList).
                    // below line is use to specify the width of poly line.
                            width(5)
                    // below line is use to add color to our poly line.
                    .color(Color.RED)
                    .startCap(new RoundCap())
                    .endCap(new CustomCap(endCapIcon,18))
                    .jointType(JointType.ROUND)
                    // below line is to make our poly line geodesic.
                    .geodesic(true));*/



        } else {

            homeLatLng = new LatLng(Double.parseDouble(mdplist.get(0).getLat()), Double.parseDouble(mdplist.get(0).getLong()));

            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mdplist.get(0).getLat()), Double.parseDouble(mdplist.get(0).getLong())))
                    .title(mdplist.get(0).getAddress())
                    .snippet("Lat = " + Double.parseDouble(mdplist.get(0).getLat()) + ", Long = " + Double.parseDouble(mdplist.get(0).getLong()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel));


        }


    }

    Polyline polyline;

    public Polyline drawPolylineWithArrowEndcap(Context context,
                                                GoogleMap googleMap,
                                                LatLng fromLatLng,
                                                LatLng toLatLng) {

        int arrowColor = Color.BLUE; // change this if you want another color (Color.BLUE)
        int lineColor = Color.RED;

        BitmapDescriptor endCapIcon = getEndCapIcon(context, arrowColor);

        // have googleMap create the line with the arrow endcap
        // NOTE:  the API will rotate the arrow image in the direction of the line
        polyline = googleMap.addPolyline(new PolylineOptions()
                .geodesic(true)
                .color(lineColor)
                .width(8)
                .startCap(new RoundCap())
                .endCap(new CustomCap(endCapIcon, 18))
                .jointType(JointType.ROUND)
                .add(fromLatLng, toLatLng));

        return polyline;
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (polyline != null)
            polyline.remove();
        polyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}