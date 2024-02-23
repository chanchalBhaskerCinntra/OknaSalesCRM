package com.cinntra.okana.activities;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.cinntra.okana.R;
import com.cinntra.okana.databinding.ActivityMapsBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.mapparser.DirectionsJSONParser;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.data.geojson.GeoJsonPoint;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.location.Address;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    BusinessPartnerData customerItem;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private final int REQUEST_LOCATION_PERMISSION = 1;

    int mMode;
    Polyline polylineOnDisplay = null;
    LatLng homeLatLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            customerItem = (BusinessPartnerData) bundle.getSerializable(Globals.BussinessItemData);
        }
      /*  binding.bottompart.companyname.setText(customerItem.getCardName());
        binding.bottompart.address.setText(customerItem.getBPAddresses().get(0).getUState()+
               "," + customerItem.getBPAddresses().get(0).getUCountry() ) ;
*/
   //   topsheetButtons();

        /* Functionality of buttons in the bottomsheet */
   //     bottomsheetButtons();
        enableMyLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

/*
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawTrack("Delhi",customerItem.getBPAddresses().get(0).getUState()
                );
            }
        });
*/

    }


        private void drawTrack(String noida, String uState) {
            try {
                // create a uri
                Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + noida + "/" + uState);

                // initializing a intent with action view.
                Intent i = new Intent(Intent.ACTION_VIEW, uri);

                // below line is to set maps package name
                i.setPackage("com.google.android.apps.maps");

                // below line is to set flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // start activity
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                // when the google maps is not installed on users device
                // we will redirect our user to google play to download google maps.
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");

                // initializing intent with action view.
                Intent i = new Intent(Intent.ACTION_VIEW, uri);

                // set flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // to start activity
                startActivity(i);
            }

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

        mMap.setMinZoomPreference(4.0f);
        mMap.setMaxZoomPreference(16.0f);
        float zoomLevel = 14.0f;
        /* Get lat, lng from address */
        String addr = "Cinntra Infotech, Noida";
        /*if(customerItem.getBPAddresses().get(0).getUState()!=null) {
            addr = customerItem.getBPAddresses().get(0).getUState() + "," + customerItem.getBPAddresses().get(0).getUCountry();
        }*/

        GeoJsonPoint final_coordinates = null;
        try {
            final_coordinates = getLocationFromAddress(addr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        double latitude = final_coordinates.getCoordinates().latitude;
        double longitude = final_coordinates.getCoordinates().longitude;
         homeLatLng = new LatLng(latitude, longitude);




        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Globals.currentlattitude, Globals.currentlongitude))
                .title("Home")
                .snippet("Lat = " + latitude + ", Long = " + longitude));


        LatLng place1 = new LatLng(latitude, longitude);
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1, zoomLevel));
        mMap.addMarker(new MarkerOptions().position(place1)
                .title("customerItem.getCardName()")
                .snippet("Lat = " + latitude + ", Long = " + longitude)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

       /* latitude = 28.591258;
        longitude = 77.072042;
        LatLng place1 = new LatLng(latitude, longitude);
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1, zoomLevel));
        mMap.addMarker(new MarkerOptions().position(place1)
                .title("Place 1")
                .snippet("Lat = " + latitude + ", Long = " + longitude)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        latitude = 28.589591;
        longitude = 77.075374;
        LatLng place2 = new LatLng(latitude, longitude);
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place2, zoomLevel));
        mMap.addMarker(new MarkerOptions().position(place2)
                .title("Place 2")
                .snippet("Lat = " + latitude + ", Long = " + longitude)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        latitude = 28.577198;
        longitude = 77.076856;
        LatLng place3 = new LatLng(latitude, longitude);
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place3, zoomLevel));
        mMap.addMarker(new MarkerOptions().position(place3)
                .title("Place 3")
                .snippet("Lat = " + latitude + ", Long = " + longitude)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        latitude = 28.575155;
        longitude = 77.072433;
        LatLng place4 = new LatLng(latitude, longitude);
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place4, zoomLevel));
        mMap.addMarker(new MarkerOptions().position(place4)
                .title("Place 4")
                .snippet("Lat = " + latitude + ", Long = " + longitude)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
*/
        /* Show my current location */


        /* Show the dotted line between Home and Place1 */
        curvedPolyline( new LatLng(Globals.currentlattitude, Globals.currentlongitude), homeLatLng,0.5);





    }

    public void directionsRequest(LatLng source, LatLng dest, String mode){
        String url = getDirectionsURL(source, dest, mode);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    /* Playing with directions API */
    private String getDirectionsURL(LatLng origin, LatLng dest, String mode) {
        String origin_str = "origin=" + origin.latitude + "," + origin.longitude;
        String dest_str = "destination=" + dest.latitude + "," + dest.longitude;

        String key = "key=AIzaSyCPyJtXV5WCzTH9P7TwCd8hl8V-UiiVhKc";

        String parameters = origin_str + "&" + dest_str + "&" + key;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/json?" + origin_str + "&" + dest_str + "&" + mode + "&key=AIzaSyCPyJtXV5WCzTH9P7TwCd8hl8V-UiiVhKc";
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception downl. url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            // Toast.makeText(getBaseContext(), "result" + result, Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            Log.d("JSON", jsonData[0]);
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                displayTimeCost(jObject);

                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();


            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(20);

                /* Code for if you want different colors for different modes */
                /* Changing the color polyline according to the mode */
                if (mMode == 0)
                    lineOptions.color(getResources().getColor(R.color.common_blue));
                else if (mMode == 1)
                    lineOptions.color(Color.RED);
                else if (mMode == 2)
                    lineOptions.color(Color.GREEN);
                else if (mMode == 3)
                    lineOptions.color(Color.YELLOW);
            }

            if (result.size() < 1) {
                Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }


            // Drawing polyline in the Google Map for the i-th route
            polylineOnDisplay = mMap.addPolyline(lineOptions);
        }
    }

    /* To display time and cost at the top */
    public void displayTimeCost(JSONObject jObject) throws JSONException {
        if(jObject.getJSONArray("routes").length() != 0) {
            String distance = jObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text");
            String duration = jObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").getString("text");

            duration = duration.replace("hours", "hrs");
            duration = duration.replace("hour", "hr");

            distance = distance.replace(" km", "");

            double factor = 10;
            double cost = Double.parseDouble(distance) * factor;
            if (mMode == 0) {
                TextView textElement = (TextView) findViewById(R.id.textView9);
                textElement.setText(duration + "\n Rs. " + cost);
            } else if (mMode == 1) {
                TextView textElement = (TextView) findViewById(R.id.textView10);
                textElement.setText(duration + "\n Rs. " + cost);
            } else if (mMode == 2) {
                TextView textElement = (TextView) findViewById(R.id.textView11);
                textElement.setText(duration + "\n Rs. " + cost);
            } else if (mMode == 3) {
                TextView textElement = (TextView) findViewById(R.id.textView12);
                textElement.setText(duration + "\n Rs. " + cost);
            }
        }
        else{
            if (mMode == 0) {
                TextView textElement = (TextView) findViewById(R.id.textView9);
                textElement.setText("--\n--");
            } else if (mMode == 1) {
                TextView textElement = (TextView) findViewById(R.id.textView10);
                textElement.setText("--\n--");
            } else if (mMode == 2) {
                TextView textElement = (TextView) findViewById(R.id.textView11);
                textElement.setText("--\n--");
            } else if (mMode == 3) {
                TextView textElement = (TextView) findViewById(R.id.textView12);
                textElement.setText("--\n--");
            }
        }
    }

    /* Next part */
    private void bottomsheetButtons() {

        /* Using Maps button to jump to Google Maps */
        ImageButton maps_btn = findViewById(R.id.imageButton4);
        maps_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri IntentUri = Uri.parse("geo:0,0");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, IntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if(mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        /* Using Share button to send location */
        double latitude = 28.591258;
        double longitude = 77.072042;
        ImageButton share_btn = findViewById(R.id.imageButton);
        share_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String uri = "http://maps.google.com/maps?saddr=" + latitude + "," + longitude;
                String subject = "Here is my location";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, uri);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        /* Using "Start now" button to start turn-by-turn navigation */
        double new_lat = 28.59130175845276;
        double new_long = 77.07199071793913;
        Button start_now_btn = findViewById(R.id.button);
        start_now_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


           /*     String dest = "google.navigation:q=" + new_lat + "," + new_long;
                Uri IntentUri = Uri.parse(dest);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, IntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);*/

                Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+new_lat+","+new_long);
               Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
               else {
                    Toast.makeText(MapsActivity.this, "No Map Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void topsheetButtons(){

        /* Buttons */
        ImageButton driving_btn = findViewById(R.id.imageButton2);
        ImageButton bicycling_btn = findViewById(R.id.imageButton3);
        ImageButton transit_btn = findViewById(R.id.imageButton6);
        ImageButton walking_btn = findViewById(R.id.imageButton7);

        /* Driving mode */
        double latitude = 28.582328;
        double longitude = 77.0711941;
        LatLng homeLatLng = new LatLng(latitude, longitude);

        driving_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driving_btn.setColorFilter(getResources().getColor(R.color.common_blue));
                bicycling_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                transit_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                walking_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                if(polylineOnDisplay != null) {
                    polylineOnDisplay.remove();
                }
                mMode = 0;
                directionsRequest(homeLatLng, new LatLng(28.558402233089115, 77.20375446382731), "mode=driving");
            }
        });

        /* Bicycling mode */

        bicycling_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driving_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                bicycling_btn.setColorFilter(getResources().getColor(R.color.common_blue));
                transit_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                walking_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                if(polylineOnDisplay != null) {
                    polylineOnDisplay.remove();
                }
                mMode = 1;
                directionsRequest(homeLatLng, new LatLng(28.558402233089115, 77.20375446382731), "mode=bicycling");
            }
        });

        /* Transit mode */
        transit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driving_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                bicycling_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                transit_btn.setColorFilter(getResources().getColor(R.color.common_blue));
                walking_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                if(polylineOnDisplay != null) {
                    polylineOnDisplay.remove();
                }
                mMode = 2;
                directionsRequest(homeLatLng, new LatLng(28.558402233089115, 77.20375446382731), "mode=transit");
            }
        });

        /* Walking mode */
        walking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driving_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                bicycling_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                transit_btn.setColorFilter(getResources().getColor(R.color.common_grey));
                walking_btn.setColorFilter(getResources().getColor(R.color.common_blue));
                if(polylineOnDisplay != null) {
                    polylineOnDisplay.remove();
                }
                mMode = 3;
                directionsRequest(homeLatLng, new LatLng(28.558402233089115, 77.20375446382731), "mode=walking");
            }
        });


    }



    public GeoJsonPoint getLocationFromAddress(String strAddress) throws IOException {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoJsonPoint p1 = null;


            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            double lat = location.getLatitude();
            double longi = location.getLongitude();

            LatLng coordinates = new LatLng(lat, longi);

            p1 = new GeoJsonPoint(coordinates);
        return p1;
    }





    private void curvedPolyline(LatLng p1, LatLng p2, double k) {
        //Calculate distance and heading between two points
        double d = SphericalUtil.computeDistanceBetween(p1, p2);
        double h = SphericalUtil.computeHeading(p1, p2);

        //Midpoint position
        LatLng p = SphericalUtil.computeOffset(p1, d * 0.5, h);

        //Apply some mathematics to calculate position of the circle center
        double x = (1 - k * k) * d * 0.5 / (2 * k);
        double r = (1 + k * k) * d * 0.5 / (2 * k);

        LatLng c = SphericalUtil.computeOffset(p, x, h + 90.0);

        //Polyline options
        PolylineOptions options = new PolylineOptions();
        List<PatternItem> pattern = Arrays.<PatternItem>asList(new Dash(30), new Gap(20));

        //Calculate heading between circle center and two points
        double h1 = SphericalUtil.computeHeading(c, p1);
        double h2 = SphericalUtil.computeHeading(c, p2);

        //Calculate positions of points on circle border and add them to polyline options
        int numpoints = 100;
        double step = (h2 - h1) / numpoints;

        for (int i = 0; i < numpoints; i++) {
            LatLng pi = SphericalUtil.computeOffset(c, r, h1 + i * step);
            options.add(pi);
        }

        //Draw polyline
        mMap.addPolyline(options.width(10).color(Color.BLACK).geodesic(false).pattern(pattern));




    }


    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
//            mMap.setMyLocationEnabled(true);
        }
        else{
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation();
            }
        }
    }
}