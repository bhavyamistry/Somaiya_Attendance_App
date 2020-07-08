package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {


    private Context mCtx;
    private List<Notification> notificationList;

    public NotificationAdapter(Context mCtx, List<Notification> notificationList) {
        this.mCtx = mCtx;
        this.notificationList = notificationList;
    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.notification_layout,null);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification=notificationList.get(position);
        holder.tvTitle.setText(notification.getTitle());
        holder.tvFaculty_name.setText(notification.getFacultyName());
        holder.tvDate.setText(notification.getDate());
//        holder.Image.setImageDrawable(mCtx.getResources().getDrawable(notification.getImage(),null));
        Glide .with(mCtx)
                .load(URL_Class.getUrl()+"images/"+notification.getImage())
                .into(holder.Image);

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder{

        CircleImageView Image;
        TextView tvFaculty_name,tvTitle,tvDate;



        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            Image=itemView.findViewById(R.id.profile_image);
            tvFaculty_name=itemView.findViewById(R.id.tvFaculty_name);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvDate=itemView.findViewById(R.id.tvDate);



        }
    }
}
