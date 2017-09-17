package com.example.owner.nt_taxi.View;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.owner.nt_taxi.Controller.Adapter.PlaceAutocompleteAdapter;
import com.example.owner.nt_taxi.Controller.Network.RequestCallback;
import com.example.owner.nt_taxi.Controller.Network.Services;
import com.example.owner.nt_taxi.Model.DriverLocations;
import com.example.owner.nt_taxi.Model.Location;
import com.example.owner.nt_taxi.Model.getLocationParser.GetRouteOnMap;
import com.example.owner.nt_taxi.Model.loginRootObject;
import com.example.owner.nt_taxi.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


public class map extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener,
        GoogleMap.OnInfoWindowClickListener, LocationListener {
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(22.041458, 24.790100), new LatLng(33.682247, 36.383362));
    public View view;
    public Marker M_current, M_destination;
    public android.location.Location myLoc;
    public LocationManager locationManager;
    public GoogleApiClient mGoogleApiClient;
    private GoogleMap googleMap;
    private MapView mapView;
    private FloatingActionButton floatingActionButton;
    private String totalDistance,totalDuration;
    private int PLACE_AUTOCOMPLETE_CURRENT_REQUEST_CODE = 1;
    private int PLACE_AUTOCOMPLETE_DESTINATION_REQUEST_CODE = 2;
    private ArrayList<Location> locations = null;
    private Polyline polyline;
    private String FromLocation,ToLocation;
    private PlaceAutocompleteAdapter mCurrentAdapter,mDestinationAdapter;
    private AutoCompleteTextView mCurrentAutocompleteView,mDestinationAutocompleteView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_main,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapFragment);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
            initMap();
        }

    }

    private void initMap() {

        floatingActionButton = view.findViewById(R.id.FloatActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            showChooseLocationDialog();

            }
        });

    }

    private void showChooseLocationDialog(){
        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);

        // Retrieve the AutoCompleteTextView that will display Place suggestions.
        mCurrentAutocompleteView = dialog.findViewById(R.id.CurrentPlace);

        mDestinationAutocompleteView = dialog.findViewById(R.id.PlaceWantToGo);

//                // Register a listener that receives callbacks when a suggestion has been selected
//                mCurrentAutocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    }
//                });
//
//                mDestinationAutocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    }
//                });

        // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.
        mCurrentAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);

        mDestinationAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);


        mCurrentAutocompleteView.setAdapter(mCurrentAdapter);
        mDestinationAutocompleteView.setAdapter(mDestinationAdapter);


//                // set the custom dialog components - text, image and button
//                CurrentTxt = (TextView) dialog.findViewById(R.id.CurrentPlace);
//                DestinationTxt = (TextView) dialog.findViewById(R.id.PlaceWantToGo);
//
//                CurrentTxt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        try {
//                            Intent intent =
//                                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
//                                            .build(getActivity());
//                            startActivityForResult(intent, PLACE_AUTOCOMPLETE_CURRENT_REQUEST_CODE);
//                        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
//                            // TODO: Handle the error.
//                        }
//                    }
//                });
//
//                DestinationTxt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        try {
//                            Intent intent =
//                                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
//                                            .build(getActivity());
//                            startActivityForResult(intent, PLACE_AUTOCOMPLETE_DESTINATION_REQUEST_CODE);
//                        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
//                            // TODO: Handle the error.
//                        }
//                    }
//                });
//
        Button dialogButton = dialog.findViewById(R.id.GoToPlace);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FromLocation = mCurrentAutocompleteView.getText().toString();
                ToLocation = mDestinationAutocompleteView.getText().toString();

                if(FromLocation.equals("") ){
                    mCurrentAutocompleteView.setError("Enter the current place");
                }else if(ToLocation.equals("") ) {
                    mDestinationAutocompleteView.setError("Enter the destination place");
                }else {
                    Services.getDirection(FromLocation.replaceAll(" ", "+"),
                            ToLocation.replaceAll(" ", "+"),
                            new RequestCallback() {
                                @Override
                                public void Success(String response) {

                                    GetRouteOnMap Route = new Gson().fromJson(response,GetRouteOnMap.class);
                                    if(Route.getStatus().equals("OK")){

                                        RemoveMarker(M_current);
                                        RemoveMarker(M_destination);

                                        M_current = AddSimpleMarker(Route.getRoutes().get(0).getLegs().get(0).getLegStartLocation().getLat(),
                                                Route.getRoutes().get(0).getLegs().get(0).getLegStartLocation().getLng(),
                                                Route.getRoutes().get(0).getLegs().get(0).getStartAddress());

                                        M_destination = AddSimpleMarker(Route.getRoutes().get(0).getLegs().get(0).getLegEndLocation().getLat(),
                                                Route.getRoutes().get(0).getLegs().get(0).getLegEndLocation().getLng(),
                                                Route.getRoutes().get(0).getLegs().get(0).getEndAddress());
                                        totalDistance = Route.getRoutes().get(0).getLegs().get(0).getLegDistance().getText();
                                        totalDuration = Route.getRoutes().get(0).getLegs().get(0).getDurationLeg().getText();
                                        DrawLine(PolyUtil.decode(Route.getRoutes().get(0).getOverviewPolyline().getPoints()));
                                        zoomRoute(googleMap,PolyUtil.decode(Route.getRoutes().get(0).getOverviewPolyline().getPoints()));

                                    }else {
                                        Toast.makeText(getActivity(),Route.getStatus(),Toast.LENGTH_LONG).show();
                                    }


                                }

                                @Override
                                public void Error(Exception ex) {

                                }
                            },getActivity());
                    dialog.dismiss();

                }
            }
        });

        dialog.show();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PLACE_AUTOCOMPLETE_CURRENT_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                Place From = PlaceAutocomplete.getPlace(getActivity(), data);
//                Log.i("HASSAN", "CurrentPlace: " + From.getName());
//                CurrentTxt.setText(From.getAddress());
//
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
//                // TODO: Handle the error.
//                Log.i("HASSAN", status.getStatusMessage());
//
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }else if(requestCode == PLACE_AUTOCOMPLETE_DESTINATION_REQUEST_CODE){
//            if (resultCode == RESULT_OK) {
//                Place From = PlaceAutocomplete.getPlace(getActivity(), data);
//                Log.i("HASSAN", "DestinationPlace: " + From.getName());
//                DestinationTxt.setText(From.getAddress());
//
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
//                // TODO: Handle the error.
//                Log.i("HASSAN", status.getStatusMessage());
//
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }
//    }


    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(getActivity());

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(getActivity(), isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(getActivity(), "Cann't connect to play service", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MapsInitializer.initialize(getContext());
        }

        this.googleMap = googleMap;

        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.getUiSettings().setCompassEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        this.googleMap.getUiSettings().setAllGesturesEnabled(true);

        this.googleMap.setTrafficEnabled(true);

        this.googleMap.setOnMapLongClickListener(this);
        this.googleMap.setOnInfoWindowClickListener(this);




        Services.getDriversLocation(new RequestCallback() {
            @Override
            public void Success(String response) {
                DriverLocations D_location = new Gson().fromJson(response,DriverLocations.class);
                if(D_location.getSuccess() == 1){
                    locations = D_location.getLocation();
                    for (int i = 0; i < D_location.getLocation().size(); ++i){
                        AddMarker(Double.valueOf(locations.get(i).getLatitude()),Double.valueOf(locations.get(i).getLongitude())
                                , locations.get(i).getName(), locations.get(i).getInfo());
                    }

                }else {
                    Toast.makeText(getActivity(),"Invalid Token ..!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(),MainActivity.class));
                }

            }

            @Override
            public void Error(Exception ex) {

            }
        },getActivity());

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria,true);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            googleMap.setMyLocationEnabled(true);
            android.location.Location location = locationManager.getLastKnownLocation(provider);
            if(location != null){
                myLoc = location;
            }
            locationManager.requestLocationUpdates(provider, 2000, 0,this);
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_FINE_LOCATION);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case MY_PERMISSION_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                        googleMap.setMyLocationEnabled(true);
                    }else {
                        Toast.makeText(getActivity(),"This App require location permission to be granted",Toast.LENGTH_LONG).show();
                    }

                }
                break;
        }
    }

    private void AddMarker(double lat, double lng, String name, String info) {

        LatLng ll = new LatLng(lat,lng);

        googleMap.addMarker(new MarkerOptions().position(ll).title(name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_image))
                .snippet(info));

    }

    private Marker AddSimpleMarker(double lat, double lng, String name) {

        LatLng ll = new LatLng(lat,lng);
        return googleMap.addMarker(new MarkerOptions().position(ll).title(name));

    }

    private void RemoveMarker(Marker m){
        if(m != null)
            m.remove();
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {

        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        googleMap.moveCamera(update);
    }

    private void DrawLine(List<LatLng> points){
        if(polyline != null) {
            polyline.remove();
        }
        polyline = googleMap.addPolyline(new PolylineOptions().addAll(points).width(10).color(Color.CYAN).clickable(true));
        googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Snackbar.make(view, "Time : " + totalDuration + "\n" + "Distance : " + totalDistance, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    /**
     * Zooms a Route (given a List of LalLng) at the greatest possible zoom level.
     *
     * @param googleMap: instance of GoogleMap
     * @param lstLatLngRoute: list of LatLng forming Route
     */
    public void zoomRoute(GoogleMap googleMap, List<LatLng> lstLatLngRoute) {

        if (googleMap == null || lstLatLngRoute == null || lstLatLngRoute.isEmpty()) return;

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (LatLng latLngPoint : lstLatLngRoute)
            boundsBuilder.include(latLngPoint);

        int routePadding = 100;
        LatLngBounds latLngBounds = boundsBuilder.build();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding));
    }



    @Override
    public void onInfoWindowClick(Marker marker) {
        final String str = marker.getId().replaceAll("\\D+","");
        if(Integer.parseInt(str) > locations.size())
            return;

        AlertDialog.Builder a_builder =  new AlertDialog.Builder(getActivity());



        a_builder.setMessage("You want to ride with " + locations.get(Integer.parseInt(str)).getName())
                .setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton("Ride", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        if(M_destination == null || M_current == null){
                            dialogInterface.dismiss();
                            Toast.makeText(getActivity(),"sorry you should choose From&To Locations First",Toast.LENGTH_LONG).show();
                            showChooseLocationDialog();
                            Toast.makeText(getActivity(),"now you can choose car you want to ride",Toast.LENGTH_LONG).show();

                        }else {
                            Services.requestTaxi(locations.get(Integer.parseInt(str)), FromLocation,
                                    ToLocation, new LatLng(myLoc.getLatitude(), myLoc.getLongitude()),
                                    M_current.getPosition(), M_destination.getPosition(),
                                    new RequestCallback() {
                                        @Override
                                        public void Success(String response) {
                                            loginRootObject Root = new Gson().fromJson(response,loginRootObject.class);
                                            if(Root.getSuccess() == 1){
                                                Toast.makeText(getActivity(),"wait for driver accept", Toast.LENGTH_LONG).show();
                                            }else {
                                                Toast.makeText(getActivity(),Root.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void Error(Exception ex) {
                                            Toast.makeText(getActivity(),"Network Error...",Toast.LENGTH_LONG).show();
                                        }
                                    },getActivity());
                        }

                    }
                });


        AlertDialog alertDialog = a_builder.create();
        alertDialog.setTitle("Wish to ride....");
        alertDialog.show();

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

//        Marker newMarker = googleMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .snippet(latLng.toString()));
//        newMarker.setTitle(newMarker.getId());

    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        myLoc = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
