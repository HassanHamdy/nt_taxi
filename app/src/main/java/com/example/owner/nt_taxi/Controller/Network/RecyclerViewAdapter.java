package com.example.owner.nt_taxi.Controller.Network;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owner.nt_taxi.Model.HistoryList;
import com.example.owner.nt_taxi.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


// RecyclerView.Adapter<vh> => vh [viewHolder]
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HistoryViewHolder>{

    private Context context;
    private ArrayList<HistoryList> Data;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, ArrayList<HistoryList> data) {
        this.context = context;
        Data = data;
        // to tell where it will inflate the singleItem xml file (means that who is the parent)
        inflater = LayoutInflater.from(context);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_history_item,parent,false);
        HistoryViewHolder songViewHolder = new HistoryViewHolder(view);
        return songViewHolder;
    }

    /**
     * @param holder that is returned from onCreateViewHolder() method
     * @param position number of data in arrayList that use to fill item
     */
    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.DriverName.setText(Data.get(position).DriverName);
        holder.FromLoc.setText("From : " + Data.get(position).Location);
        holder.ToLoc.setText("To : " +Data.get(position).DropLocation);
        switch (Data.get(position).Accept){
            case "0":
                holder.accept.setText("Pinding");
                break;
            case "1":
                holder.accept.setText("Accepted");
                break;
            case "2":
                holder.accept.setText("Rejected by " + Data.get(position).DriverName);
                break;
            case "3":
                holder.accept.setText("You Cancel it");

        }


    }

    @Override
    public int getItemCount() {
        // Because from it's name return the number of Data (items)
        return Data.size();
    }


    //this class used to declare component of each item in recyclerView should
    // extends RecyclerView.ViewHolder

    /*
    the adapter is used to declare items as much as the screen can show
    when scrolling down or up not define new one but when scrolling down (go downward)
    the item at the top come at the bottom after disappear from screen but with new Data
     */

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        private TextView DriverName;
        private TextView FromLoc;
        private TextView ToLoc;
        private TextView accept;

        // itemView is the single item that will be shown
        // i will make declaration here of item components
        public HistoryViewHolder(View itemView) {
            super(itemView);
            DriverName = (TextView) itemView.findViewById(R.id.Driver_name);
            FromLoc = itemView.findViewById(R.id.FromLocation);
            ToLoc = itemView.findViewById(R.id.DropLocation);
            accept = itemView.findViewById(R.id.accept);
        }
    }
}


/*
to call it from main create recycler view in xml then put this code

        SongRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Data = new ArrayList<>();
        adapter = new Adapter(getApplicationContext(),Data);
        SongRecyclerView.setAdapter(adapter);
        SongRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
 */