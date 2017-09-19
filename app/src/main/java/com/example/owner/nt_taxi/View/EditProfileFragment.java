package com.example.owner.nt_taxi.View;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.owner.nt_taxi.BuildConfig;
import com.example.owner.nt_taxi.Controller.Network.RequestCallback;
import com.example.owner.nt_taxi.Controller.Network.Services;
import com.example.owner.nt_taxi.Model.constants_class;
import com.example.owner.nt_taxi.Model.loginRootObject;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;


public class EditProfileFragment extends BaseFragment implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener {
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private ImageView banar1;
    private Button BtnSaveChanges;
    private com.example.owner.nt_taxi.View.customfonts.MyEditText Username, Email, editNum;
    private com.example.owner.nt_taxi.View.customfonts.MyTextView job, number, viewEmail;
    private int GALLERY = 1, CAMERA = 2;
    private Bitmap bitmap;
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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banar1 = view.findViewById(R.id.edit_banar1);
        BtnSaveChanges = view.findViewById(R.id.SaveBTN);
        Username = view.findViewById(R.id.Edit_name);
        Email = view.findViewById(R.id.Edit_email);
        job = view.findViewById(R.id.Edit_job);
        number = view.findViewById(R.id.view_number);
        editNum = view.findViewById(R.id.Edit_number);
        viewEmail = view.findViewById(R.id.view_email);

        lat = constants_class.sharedPreferences.getString(constants_class.Lat, "");
        lng = constants_class.sharedPreferences.getString(constants_class.Long, "");

        Username.setText(constants_class.sharedPreferences.getString(constants_class.Name, ""));
        job.setText(constants_class.sharedPreferences.getString(constants_class.Category, ""));

        if (BuildConfig.category == 0) {
            editNum.setVisibility(View.GONE);
            viewEmail.setVisibility(View.GONE);
            number.setText(constants_class.sharedPreferences.getString(constants_class.Number, ""));
            Email.setText(constants_class.sharedPreferences.getString(constants_class.Email, ""));
        } else {
            number.setVisibility(View.GONE);
            Email.setVisibility(View.GONE);
            editNum.setText(constants_class.sharedPreferences.getString(constants_class.Number, ""));
            viewEmail.setText(constants_class.sharedPreferences.getString(constants_class.Email, ""));
        }




        forCircleImage(banar1, R.drawable.white);
        Picasso.with(getActivity())
                .load("http://192.168.61.2/nt_taxi_BackEnd/" + constants_class.sharedPreferences.getString(constants_class.Image, ""))
                .into(banar1);

        bitmap = ((BitmapDrawable) banar1.getDrawable()).getBitmap();

        mapView = view.findViewById(R.id.edit_location);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }


        banar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });





        BtnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (BuildConfig.category == 0) {
                    Services.Update_Profile(Email.getText().toString(), Username.getText().toString(),
                            lat, lng, number.getText().toString(),
                            getStringImage(bitmap), new RequestCallback() {
                                @Override
                                public void Success(String response) {

                                    loginRootObject Root = new Gson().fromJson(response, loginRootObject.class);
                                    if (Root.getSuccess() == 1) {
                                        Toast.makeText(getActivity(), Root.getMessage(), Toast.LENGTH_LONG).show();
                                        editor.putString(constants_class.Token, Root.getInfo().get(0).getToken());
                                        editor.putString(constants_class.Name, Root.getInfo().get(0).getName());
                                        editor.putString(constants_class.ID, Root.getInfo().get(0).getId());
                                        editor.putString(constants_class.Number, Root.getInfo().get(0).getNumber());
                                        editor.putString(constants_class.Image, Root.getInfo().get(0).getImage());
                                        editor.putString(constants_class.Email, Root.getInfo().get(0).getEmail());
                                        editor.putString(constants_class.Lat, Root.getInfo().get(0).getLatitude());
                                        editor.putString(constants_class.Long, Root.getInfo().get(0).getLongitude());
                                        editor.putString(constants_class.Category, Root.getInfo().get(0).getCategory());

                                        editor.apply();

                                        Fragment frag = new ProfileFragment();
                                        FragmentManager FG = getFragmentManager();
                                        FragmentTransaction FT = FG.beginTransaction();
                                        FT.replace(R.id.Fragment, frag);
                                        FT.commit();


                                    } else {
                                        Toast.makeText(getActivity(), Root.getMessage(), Toast.LENGTH_LONG).show();
                                        if (Root.getMessage().equals("Invalid Token !")) {
                                            startActivity(new Intent(getActivity(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            getActivity().finish();
                                        }
                                    }

                                }

                                @Override
                                public void Error(Exception ex) {

                                }
                            }, getActivity());
                } else {
                    Services.Update_Profile(viewEmail.getText().toString(), Username.getText().toString(),
                            lat, lng, editNum.getText().toString(),
                            getStringImage(bitmap), new RequestCallback() {
                                @Override
                                public void Success(String response) {

                                    loginRootObject Root = new Gson().fromJson(response, loginRootObject.class);
                                    if (Root.getSuccess() == 1) {
                                        Toast.makeText(getActivity(), Root.getMessage(), Toast.LENGTH_LONG).show();
                                        editor.putString(constants_class.Token, Root.getInfo().get(0).getToken());
                                        editor.putString(constants_class.Name, Root.getInfo().get(0).getName());
                                        editor.putString(constants_class.ID, Root.getInfo().get(0).getId());
                                        editor.putString(constants_class.Number, Root.getInfo().get(0).getNumber());
                                        editor.putString(constants_class.Image, Root.getInfo().get(0).getImage());
                                        editor.putString(constants_class.Email, Root.getInfo().get(0).getEmail());
                                        editor.putString(constants_class.Lat, Root.getInfo().get(0).getLatitude());
                                        editor.putString(constants_class.Long, Root.getInfo().get(0).getLongitude());
                                        editor.putString(constants_class.Category, Root.getInfo().get(0).getCategory());

                                        editor.apply();

                                        Fragment frag = new ProfileFragment();
                                        FragmentManager FG = getFragmentManager();
                                        FragmentTransaction FT = FG.beginTransaction();
                                        FT.replace(R.id.Fragment, frag);
                                        FT.commit();


                                    } else {
                                        Toast.makeText(getActivity(), Root.getMessage(), Toast.LENGTH_LONG).show();
                                        if (Root.getMessage().equals("Invalid Token !")) {
                                            startActivity(new Intent(getActivity(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            getActivity().finish();
                                        }
                                    }

                                }

                                @Override
                                public void Error(Exception ex) {

                                }
                            }, getActivity());
                }


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

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
//                    String path = saveImage(bitmap);
//                    Toast.makeText(HomePage.this, "Image Saved!", Toast.LENGTH_SHORT).show();


//                    Services.UploadImage(new RequestCallback() {
//                        @Override
//                        public void Success(String response) {
//                            String s = response.trim();
//
//                            if(s.equalsIgnoreCase("error")){
//                                Toast.makeText(HomePage.this, "error while uploading ..", Toast.LENGTH_SHORT).show();
//                            }else{
//                                Toast.makeText(HomePage.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void Error(Exception ex) {
//                            Toast.makeText(HomePage.this, ex+"", Toast.LENGTH_SHORT).show();
//                        }
//                    }, HomePage.this, getStringImage(bitmap));

                    banar1.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            banar1.setImageBitmap(bitmap);


//            Services.UploadImage(new RequestCallback() {
//                @Override
//                public void Success(String response) {
//                    String s = response.trim();
//
//                    if(s.equalsIgnoreCase("error")){
//                        Toast.makeText(HomePage.this, "error while uploading ..", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(HomePage.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void Error(Exception ex) {
//                    Toast.makeText(HomePage.this, ex+"", Toast.LENGTH_SHORT).show();
//                }
//            }, HomePage.this, getStringImage(bitmap));


//            saveImage(thumbnail);
//            Toast.makeText(HomePage.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String getStringImage(Bitmap bm) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, ba);
        byte[] imagebyte = ba.toByteArray();
        return Base64.encodeToString(imagebyte, Base64.DEFAULT);
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

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            googleMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }

        goToLocationZoom(Double.valueOf(lat), Double.valueOf(lng), 13);

        this.googleMap.setOnMapLongClickListener(this);

//        this.googleMap.getUiSettings().setAllGesturesEnabled(false);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    } else {
                        Toast.makeText(getActivity(), "This App require location permission to be granted", Toast.LENGTH_LONG).show();
                    }

                }
                break;
        }
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

    @Override
    public void onMapLongClick(LatLng latLng) {
        googleMap.clear();
        lat = String.valueOf(latLng.latitude);
        lng = String.valueOf(latLng.longitude);
        goToLocationZoom(latLng.latitude, latLng.longitude, 13);
    }
}
