package com.tuhin.fitgaurdapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.myviewholder> {
    ArrayList<NotificationModel> addNotification = new ArrayList<NotificationModel>();
    //array list to hold the reminders
    Context context;

    public AlarmRecyclerViewAdapter(Context context,ArrayList<NotificationModel> addNotification) {
        this.context = context;
        this.addNotification = addNotification;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);  //inflates the xml file in recyclerview
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        NotificationModel notificationModel = addNotification.get(position);
        holder.mTitle.setText(addNotification.get(position).getTitle());                                 //Binds the single reminder objects to recycler view
        holder.mDate.setText(addNotification.get(position).getDate());
        holder.mTime.setText(addNotification.get(position).getTime());

//        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                showDeleteConfirmation(holder.getAdapterPosition());
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return addNotification.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView mTitle, mDate, mTime;
        LinearLayout llRow;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.notificationTitle);                               //holds the reference of the materials to show data in recyclerview
            mDate = (TextView) itemView.findViewById(R.id.txtDate);
            mTime = (TextView) itemView.findViewById(R.id.notificationTime);
            llRow = (LinearLayout) itemView.findViewById(R.id.llRow);
        }
    }

//    private void showDeleteConfirmation(int position){
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Delete Notification");
//        builder.setMessage("Are you sure want to delete?");
//        builder.setIcon(R.drawable.baseline_delete);

//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                NotificationModel idToDelete = addNotification.get(position);
//                deleteReminderFromDatabase(idToDelete);
//                addNotification.remove(position);
//                notifyItemRemoved(position);
//            }
//        });
//
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.show();
//    }

}




