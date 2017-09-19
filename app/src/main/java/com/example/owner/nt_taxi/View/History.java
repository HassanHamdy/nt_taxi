package com.example.owner.nt_taxi.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.owner.nt_taxi.Controller.Adapter.RecyclerViewAdapter;
import com.example.owner.nt_taxi.Controller.Network.RequestCallback;
import com.example.owner.nt_taxi.Controller.Network.Services;
import com.example.owner.nt_taxi.Model.HistoryList;
import com.example.owner.nt_taxi.Model.RideListParser;
import com.example.owner.nt_taxi.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class History extends BaseFragment {

    private View view;
    private RecyclerView HistoryRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private ArrayList<HistoryList> Data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);

        Data = new ArrayList<HistoryList>();

        Services.HistoryList(new RequestCallback() {
            @Override
            public void Success(String response) {
                RideListParser Rides = new Gson().fromJson(response,RideListParser.class);
                if(Rides.getSuccess() == 1){
                    for(int i = 0; i < Rides.getRidelist().size(); ++i){
                        Data.add(new HistoryList(Rides.getRidelist().get(i).getDriverName(),
                                Rides.getRidelist().get(i).getLocation(),
                                Rides.getRidelist().get(i).getDroplocation(),
                                Rides.getRidelist().get(i).getAccept(),
                                Rides.getRidelist().get(i).getCost()));

                        HistoryRecyclerView = view.findViewById(R.id.RecyclerView);

                        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),Data);
                        HistoryRecyclerView.setAdapter(recyclerViewAdapter);
                        HistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                }else {
                    Toast.makeText(getActivity(),Rides.getMessage(),Toast.LENGTH_LONG).show();
                    if (Rides.getMessage().equals("Invalid Token !")) {
                        startActivity(new Intent(getActivity(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        getActivity().finish();
                    }
                }


            }

            @Override
            public void Error(Exception ex) {

            }
        },getActivity());



        return view;

    }

}
