package com.example.owner.nt_taxi.View;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.owner.nt_taxi.Controller.Network.RequestCallback;
import com.example.owner.nt_taxi.Controller.Network.Services;
import com.example.owner.nt_taxi.Model.constants_class;
import com.example.owner.nt_taxi.Model.loginRootObject;
import com.example.owner.nt_taxi.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;
import static android.content.Context.MODE_PRIVATE;


public class EditProfileFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {
    private ImageView banar1;
    private Button BtnSaveChanges;
    private com.example.owner.nt_taxi.View.customfonts.MyEditText Username, Email;
    private com.example.owner.nt_taxi.View.customfonts.MyTextView job, number;
    private Spinner spinner;
    private String location;
    private int GALLERY = 1, CAMERA = 2;
    private Bitmap bitmap;


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
        banar1 = view.findViewById(R.id.edit_banar1);
        BtnSaveChanges = view.findViewById(R.id.SaveBTN);
        Username = view.findViewById(R.id.Edit_name);
        Email = view.findViewById(R.id.Edit_email);
        job = view.findViewById(R.id.Edit_job);
        number = view.findViewById(R.id.Edit_number);

        Username.setText(constants_class.sharedPreferences.getString(constants_class.UserName, ""));
        job.setText(constants_class.sharedPreferences.getString(constants_class.Category, ""));
        number.setText(constants_class.sharedPreferences.getString(constants_class.Number, ""));
        Email.setText(constants_class.sharedPreferences.getString(constants_class.Email, ""));

        forCircleImage(banar1, R.drawable.white);
        Picasso.with(getActivity())
                .load("http://192.168.61.2/nt_taxi_BackEnd/" + constants_class.sharedPreferences.getString(constants_class.Image, ""))
                .into(banar1);

        spinner = view.findViewById(R.id.Edit_location_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.governorate_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        banar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });





        BtnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Services.Update_Profile(Email.getText().toString(), Username.getText().toString(),
                        location, getStringImage(bitmap), new RequestCallback() {
                            @Override
                            public void Success(String response) {

                                loginRootObject Root = new Gson().fromJson(response, loginRootObject.class);
                                if (Root.getSuccess() == 1) {
                                    Toast.makeText(getActivity(), Root.getMessage(), Toast.LENGTH_LONG).show();
                                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(constants_class.MyPREFERENCES, MODE_PRIVATE).edit();
                                    editor.putString(constants_class.Token, Root.getInfo().get(0).getToken());
                                    editor.putString(constants_class.UserName, Root.getInfo().get(0).getName());
                                    editor.putString(constants_class.UserID, Root.getInfo().get(0).getId());
                                    editor.putString(constants_class.Number, Root.getInfo().get(0).getNumber());
                                    editor.putString(constants_class.Image, Root.getInfo().get(0).getImage());
                                    editor.putString(constants_class.Email, Root.getInfo().get(0).getEmail());
                                    editor.putString(constants_class.Location, Root.getInfo().get(0).getLocation());
                                    editor.putString(constants_class.Category, Root.getInfo().get(0).getCategory());
                                    editor.putBoolean(constants_class.isLoggedIn, true);
                                    editor.apply();

                                    Fragment frag = new ProfileFragment();
                                    FragmentManager FG = getFragmentManager();
                                    FragmentTransaction FT = FG.beginTransaction();
                                    FT.replace(R.id.Fragment, frag);
                                    FT.commit();


                                } else {
                                    Toast.makeText(getActivity(), Root.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void Error(Exception ex) {

                            }
                        }, getActivity());
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        location = (String) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getActivity(), "Please choose you location", Toast.LENGTH_LONG).show();

    }
}
