package com.example.userlogin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.QueryViewHolder>{

    private Context mCtx;
    private List<Queries> queriesList;

    public QueryAdapter(Context mCtx,List<Queries> queriesList)
    {
        this.mCtx=mCtx;
        this.queriesList=queriesList;
    }




    @NonNull
    @Override
    public QueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QueryViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.query_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull QueryViewHolder holder, int position) {
        Queries queries = queriesList.get(position);

        holder.LabText.setText("Lab No : "+queries.getLab());
        holder.ComputerText.setText("Computer No : "+queries.getComputer());
        holder.DeviceText.setText("Device : "+queries.getDevice());
        holder.DescriptionText.setText("Description : " + queries.getDescription());
        holder.StatusText.setText("Status : " + queries.getStatus());
    }

    @Override
    public int getItemCount() {
        return queriesList.size();
    }




    class QueryViewHolder extends RecyclerView.ViewHolder
    {
        TextView LabText,ComputerText,DeviceText,DescriptionText,StatusText;
        public QueryViewHolder(View itemView)
        {
            super(itemView);
            LabText=itemView.findViewById(R.id.LabText);
            ComputerText=itemView.findViewById(R.id.ComputerText);
            DeviceText=itemView.findViewById(R.id.DeviceText);
            DescriptionText=itemView.findViewById(R.id.DescriptionText);
            StatusText=itemView.findViewById(R.id.StatusText);
        }
    }

  }


