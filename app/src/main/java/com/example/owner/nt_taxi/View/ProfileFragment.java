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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.owner.nt_taxi.Model.constants_class;
import com.example.owner.nt_taxi.R;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends BaseFragment {
    private ImageView banar1;
    private Button BtnEditProfile;
    private com.example.owner.nt_taxi.View.customfonts.MyTextView name, job, number, email, location;

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
        banar1 = view.findViewById(R.id.banar1);
        name = view.findViewById(R.id.fullName);
        job = view.findViewById(R.id.job);
        number = view.findViewById(R.id.mobileNumber);
        email = view.findViewById(R.id.Email);
        location = view.findViewById(R.id.Location);

        name.setText(constants_class.sharedPreferences.getString(constants_class.UserName, ""));
        job.setText(constants_class.sharedPreferences.getString(constants_class.Category, ""));
        number.setText(constants_class.sharedPreferences.getString(constants_class.Number, ""));
        email.setText(constants_class.sharedPreferences.getString(constants_class.Email, ""));
        location.setText(constants_class.sharedPreferences.getString(constants_class.Location, ""));
        forCircleImage(banar1, R.drawable.white);

        Picasso.with(getActivity())
                .load("http://192.168.61.2/nt_taxi_BackEnd/" + constants_class.sharedPreferences.getString(constants_class.Image, ""))
                .into(banar1);

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

}
