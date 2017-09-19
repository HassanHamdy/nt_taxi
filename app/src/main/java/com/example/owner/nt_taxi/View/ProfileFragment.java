package com.example.owner.nt_taxi.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.owner.nt_taxi.Model.constants_class;
import com.example.owner.nt_taxi.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ProfileFragment extends BaseFragment implements OnMapReadyCallback {
    private ImageView banar1;
    private Button BtnEditProfile;
    private com.example.owner.nt_taxi.View.customfonts.MyTextView name, job, number, email;
    private GoogleMap googleMap;
    private MapView mapView;
    private String lat, lng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banar1 = view.findViewById(R.id.banar1);
        name = view.findViewById(R.id.fullName);
        job = view.findViewById(R.id.job);
        number = view.findViewById(R.id.mobileNumber);
        email = view.findViewById(R.id.Email);

        lat = constants_class.sharedPreferences.getString(constants_class.Lat, "");
        lng = constants_class.sharedPreferences.getString(constants_class.Long, "");

        name.setText(constants_class.sharedPreferences.getString(constants_class.Name, ""));
        job.setText(constants_class.sharedPreferences.getString(constants_class.Category, ""));
        number.setText(constants_class.sharedPreferences.getString(constants_class.Number, ""));
        email.setText(constants_class.sharedPreferences.getString(constants_class.Email, ""));

        forCircleImage(banar1, R.drawable.white);

        Picasso.with(getActivity())
                .load("http://192.168.61.2/nt_taxi_BackEnd/" + constants_class.sharedPreferences.getString(constants_class.Image, ""))
                .into(banar1);

        mapView = view.findViewById(R.id.location);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        BtnEditProfile = view.findViewById(R.id.EditBTN);
        BtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment frag = new EditProfileFragment();
                FragmentManager FG = getFragmentManager();
                FragmentTransaction FT = FG.beginTransaction();
                FT.replace(R.id.Fragment,frag);
                FT.addToBackStack(null);
                HomePage.menusItems.add("EditProfile");
                FT.commit();
            }
        });
    }

    private void forCircleImage(ImageView imageView, int image){

        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),
                image);

        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

        imageView.setImageBitmap(circleBitmap);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MapsInitializer.initialize(getContext());
        }

        this.googleMap = googleMap;

        this.googleMap.getUiSettings().setAllGesturesEnabled(false);
        goToLocationZoom(Double.valueOf(lat), Double.valueOf(lng), 13);
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {

        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert addresses != null;
        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        String countryName = addresses.get(0).getAddressLine(2);


        LatLng ll = new LatLng(lat, lng);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

        googleMap.addMarker(new MarkerOptions().position(ll).title(stateName + " " + cityName + ", " + countryName))
                .showInfoWindow();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        googleMap.moveCamera(update);
    }
}
