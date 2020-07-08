package com.example.myapplication;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder >{

    private Context mCtx;
    List<Event> eventList;



    public EventAdapter(Context mCtx, List<Event> eventList) {
        this.mCtx = mCtx;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.preevent_data_viewer,parent, false);
        return new EventViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        Event event=eventList.get(position);
        holder.tvEName.setText(event.getTitle());
        holder.tvECategory.setText(event.getCategory());
        holder.tvEDate.setText(event.getDate());
        holder.tvEDescription.setText(event.getDescription());
        holder.tvEDescription1.setText(event.getTitle());
        //  holder.ivImage.setImageDrawable(context.getResources().getDrawable(event.getImage()));

        Glide.with(mCtx)
                .load(URL_Class.getUrl()+"images/"+event.getImage())
                .into(holder.ivImage);
        boolean isExpanded = eventList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder  extends RecyclerView.ViewHolder{

        ConstraintLayout expandableLayout;
        ImageView ivImage;
        TextView tvEName,tvECategory,tvEDate,tvEDescription,tvEDescription1;
        CardView cardView1;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage=itemView.findViewById(R.id.ivImage);
            tvEName=itemView.findViewById(R.id.tvEName);
            tvECategory=itemView.findViewById(R.id.tvECategory);
            tvEDate=itemView.findViewById(R.id.tvEDate);

            tvEDescription=itemView.findViewById(R.id.tvEDescription);
            tvEDescription1=itemView.findViewById(R.id.tvEDescription1);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            cardView1 = itemView.findViewById(R.id.cardView1);

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expandableLayout.getVisibility()==View.GONE){
                        TransitionManager.beginDelayedTransition(cardView1, new AutoTransition());
                        expandableLayout.setVisibility(View.VISIBLE);
                        tvEDescription1.setVisibility(View.GONE);


                    } else {
                        TransitionManager.beginDelayedTransition(cardView1, new AutoTransition());
                        expandableLayout.setVisibility(View.GONE);
                        tvEDescription1.setVisibility(View.VISIBLE);

                    }
                    //        Event event = eventList.get(getAdapterPosition());
                    //      event.setExpanded(!event.isExpanded());
                    //     notifyItemChanged(getAdapterPosition());

                }
            });



        }
    }
}
