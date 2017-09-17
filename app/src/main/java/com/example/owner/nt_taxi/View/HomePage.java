package com.example.owner.nt_taxi.View;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.nt_taxi.Model.constants_class;
import com.example.owner.nt_taxi.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomePage extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    public static ArrayList<String> menusItems;
    protected GoogleApiClient mGoogleApiClient;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle Toggle;
    private TextView userNameTxtView;
    private ImageView headerImage;
    //    private static final String IMAGE_DIRECTORY = "/nt_taxi";
    private SharedPreferences.Editor editor;
    private NavigationView navigationView;
    private View header;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editor = getSharedPreferences(constants_class.MyPREFERENCES, MODE_PRIVATE).edit();

        menusItems = new ArrayList<>();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();


        setContentView(R.layout.page_main);

        map frag = new map();
        FragmentManager FG = getFragmentManager();
        FragmentTransaction FT = FG.beginTransaction();
        FT.replace(R.id.Fragment, frag);
        FT.commit();

        frag.mGoogleApiClient = mGoogleApiClient;


        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);

        Toggle = new ActionBarDrawerToggle(this,drawerLayout,
                R.string.open, R.string.close);

        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);
        userNameTxtView = header.findViewById(R.id.usernameTextView);
        userNameTxtView.setText(constants_class.sharedPreferences.getString(constants_class.UserName,""));
        headerImage = header.findViewById(R.id.profile_image);

        Picasso.with(HomePage.this)
                .load("http://192.168.61.2/nt_taxi_BackEnd/" + constants_class.sharedPreferences.getString(constants_class.Image, ""))
                .into(headerImage);

//        headerImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

//        File imgFile = new  File(constants_class.sharedPreferences.getString(constants_class.Image,""));
//
//        if(imgFile.exists()){
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//            headerImage.setImageBitmap(myBitmap);
//
//        }



    }


//    public String saveImage(Bitmap myBitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(
//                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
//        // have the object build the directory structure, if needed.
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs();
//        }
//
//        try {
//            File f = new File(wallpaperDirectory, Calendar.getInstance()
//                    .getTimeInMillis() + ".jpg");
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(this,
//                    new String[]{f.getPath()},
//                    new String[]{"image/jpeg"}, null);
//            fo.close();
//            editor.putString(constants_class.Image,f.getAbsolutePath());
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
//
//            return f.getAbsolutePath();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return "";
//    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(count == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);

            builder.setMessage("Are you want to exit ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Warning");
            alertDialog.show();

        }else {
            uncheckAllMenuItems(navigationView);
            menusItems.remove(menusItems.size() - 1);

            if (menusItems.size() != 0 && !(menusItems.get(menusItems.size() - 1).equals("EditProfile"))) {
                navigationView.getMenu().getItem(Integer.parseInt(menusItems.get(menusItems.size() - 1))).setChecked(true);
            } else if (menusItems.size() == 0)
                navigationView.getMenu().getItem(0).setChecked(true);

            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return Toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        uncheckAllMenuItems(navigationView);
        int id = item.getItemId();
        Fragment subFragment = null;

        Picasso.with(HomePage.this)
                .load("http://192.168.61.2/nt_taxi_BackEnd/" + constants_class.sharedPreferences.getString(constants_class.Image, ""))
                .into(headerImage);

        if (id == R.id.nav_Favourite) {
            item.setChecked(true);
            Toast.makeText(HomePage.this,"Favourite",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_History) {
            menusItems.add("2");
            item.setChecked(true);
            subFragment = new History();
            FragmentManager FG = getFragmentManager();
            FragmentTransaction FT = FG.beginTransaction();
            FT.replace(R.id.Fragment,subFragment);
            FT.addToBackStack(null);
            FT.commit();

        } else if (id == R.id.nav_Home) {
            menusItems.add("0");
            item.setChecked(true);
            map frag = new map();
            FragmentManager FG = getFragmentManager();
            FragmentTransaction FT = FG.beginTransaction();
            FT.replace(R.id.Fragment,frag);
            FT.addToBackStack(null);
            FT.commit();

            frag.mGoogleApiClient = mGoogleApiClient;

        } else if (id == R.id.nav_Profile) {
            menusItems.add("1");
            item.setChecked(true);
            subFragment = new ProfileFragment();
            FragmentManager FG = getFragmentManager();
            FragmentTransaction FT = FG.beginTransaction();
            FT.replace(R.id.Fragment,subFragment);
            FT.addToBackStack(null);
            FT.commit();

        } else if (id == R.id.nav_LogOut) {
            item.setChecked(true);
            editor.putBoolean(constants_class.isLoggedIn,false);
            editor.putString(constants_class.Token,null);
            editor.apply();
            startActivity(new Intent(HomePage.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void uncheckAllMenuItems(NavigationView navigationView) {
        final Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.hasSubMenu()) {
                SubMenu subMenu = item.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    subMenuItem.setChecked(false);
                }
            } else {
                item.setChecked(false);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }
}
