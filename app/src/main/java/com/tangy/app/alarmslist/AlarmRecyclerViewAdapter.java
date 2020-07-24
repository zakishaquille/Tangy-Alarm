package com.tangy.app.alarmslist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.tangy.app.data.Alarm;
import com.tangy.app.R;

import java.util.ArrayList;
import java.util.List;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmViewHolder> {
    private AlarmsListViewModel alarmsListViewModel;
    private List<Alarm> alarms;
    private OnToggleAlarmListener listener;
    private Context context;

    public AlarmRecyclerViewAdapter(OnToggleAlarmListener listener, Context context) {
        this.alarms = new ArrayList<Alarm>();
        this.listener = listener;
        this.context = context;
        this.alarmsListViewModel = ViewModelProviders.of((Fragment) listener).get(AlarmsListViewModel.class);
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Delete Alarm");
                builder.setMessage("Do you really want to delete this alarm?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        alarmsListViewModel.delete(alarm);
                        alarm.cancelAlarm(context);
                        alarms.remove(position);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

                return true;
            }
        });

        holder.bind(alarm, listener);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }
}

