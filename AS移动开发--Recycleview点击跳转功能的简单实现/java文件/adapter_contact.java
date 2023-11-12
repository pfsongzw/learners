
package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
    OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public adapter_contact(Context context,List<String> list) {
        myContext = context;
        myList=list;
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
    public void onBindViewHolder(@NonNull VerticalViewHolder holder, @SuppressLint("RecyclerView")int position) {
        holder.teamNum.setText(position + 1 + "");
        holder.teamContent.setText(myList.get(position));
        holder.teamContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null) {
                    mListener.onItemClick(position);}
            }
        });
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