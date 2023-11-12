
package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class adapter_contact extends RecyclerView.Adapter<adapter_contact.VerticalViewHolder>{

    private static final String Tag = adapter_contact.class.getSimpleName();
    private Context myContext;
    private List<String> myList = new ArrayList<>();

    public adapter_contact(Context context) {
        myContext = context;
    }

    public void setVerticalDataList(List<String> list) {
        Log.d(Tag, "setVerticalDataList: " + list.size());

        myList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.rcv1_item, parent, false);
        return new VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder holder, int position) {
        holder.teamNum.setText(position + 1 + "");
        holder.teamContent.setText(myList.get(position));
    }

    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }

    public class VerticalViewHolder extends RecyclerView.ViewHolder {

        TextView teamNum, teamContent;

        public VerticalViewHolder(View itemView) {
            super(itemView);
            teamNum = itemView.findViewById(R.id.team_num);
            teamContent = itemView.findViewById(R.id.team_content);
        }
    }

}