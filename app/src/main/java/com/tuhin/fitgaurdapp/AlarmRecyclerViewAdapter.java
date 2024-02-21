package com.tuhin.fitgaurdapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.myviewholder> {
    private ArrayList<NotificationModel> addNotification;
    private Context context;
    private DatabaseManager databaseManager;

    public AlarmRecyclerViewAdapter(Context context, ArrayList<NotificationModel> addNotification) {
        this.context = context;
        this.addNotification = addNotification;
        this.databaseManager = new DatabaseManager(context);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        NotificationModel notificationModel = addNotification.get(position);
        holder.mTitle.setText(notificationModel.getTitle());
        holder.mDate.setText(notificationModel.getDate());
        holder.mTime.setText(notificationModel.getTime());

        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateView(holder.getAdapterPosition());
            }
        });

        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteConfirmation(holder.getAdapterPosition());
                return true;
            }
        });
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
            mTitle = itemView.findViewById(R.id.notificationTitle);
            mDate = itemView.findViewById(R.id.txtDate);
            mTime = itemView.findViewById(R.id.notificationTime);
            llRow = itemView.findViewById(R.id.llRow);
        }
    }

    private void showDeleteConfirmation(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Notification");
        builder.setMessage("Are you sure want to delete?");
        builder.setIcon(R.drawable.baseline_delete);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NotificationModel notificationModel = addNotification.get(position);
                deleteReminderFromDatabase(notificationModel.getId());
                addNotification.remove(position);
                notifyItemRemoved(position);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void deleteReminderFromDatabase(int id) {
        databaseManager.deleteReminder(id);
    }


    private void showUpdateView(int position) {
        NotificationModel notificationModel = addNotification.get(position);

        // Inflate the layout for the update view
        View updateView = LayoutInflater.from(context).inflate(R.layout.update_notifications, null);

        // Get references to EditText and TextView fields in the update view
        EditText titleEditView = updateView.findViewById(R.id.editTitle);
        TextView dateTextView = updateView.findViewById(R.id.btnDate);
        TextView timeTextView = updateView.findViewById(R.id.btnTime);

        // Populate EditText and TextView fields with notification details
        titleEditView.setText(notificationModel.getTitle());
        dateTextView.setText(notificationModel.getDate());
        timeTextView.setText(notificationModel.getTime());

        // Create an AlertDialog to display the update view
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(updateView);

        // Set up buttons for update and cancel
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve updated details from EditText and TextView fields
                String updatedTitle = titleEditView.getText().toString();
                String updatedDate = dateTextView.getText().toString();
                String updatedTime = timeTextView.getText().toString();

                // Update the notification in the database
                NotificationModel updatedNotification = new NotificationModel(notificationModel.getId(), updatedTitle, updatedDate, updatedTime);
                databaseManager.updateReminder(updatedNotification);

                // Update the corresponding item in the addNotification list
                addNotification.set(position, updatedNotification);
                notifyItemChanged(position);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Set OnClickListener for dateTextView to show DatePickerDialog
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement DatePickerDialog to select a new date
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set the selected date to the dateTextView
                        String selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%04d",dayOfMonth, month + 1, year);
                        dateTextView.setText(selectedDate);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        // Set OnClickListener for timeTextView to show TimePickerDialog
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement TimePickerDialog to select a new time
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Convert 24-hour format to 12-hour format
                        String amPm;
                        int hour = hourOfDay;
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                            if (hour > 12) {
                                hour -= 12;
                            }
                        } else {
                            amPm = "AM";
                            if (hour == 0) {
                                hour = 12;
                            }
                        }
                        // Set the selected time to the timeTextView
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hour, minute, amPm);
                        timeTextView.setText(selectedTime);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });

    }


}





