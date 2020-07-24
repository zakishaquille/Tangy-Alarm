package com.tangy.app.alarmslist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tangy.app.data.Alarm;
import com.tangy.app.data.AlarmRepository;

import java.util.List;

public class AlarmsListViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;
    private LiveData<List<Alarm>> alarmsLiveData;

    public AlarmsListViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new AlarmRepository(application);
        alarmsLiveData = alarmRepository.getAlarmsLiveData();
    }

    public void update(Alarm alarm) {
        alarmRepository.update(alarm);
    }

    public void delete(Alarm alarm) {
        alarmRepository.delete(alarm);
    }

    public LiveData<List<Alarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
}
