package com.example.alarm_app;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FirstFragment extends Fragment {

    @SuppressLint("SimpleDateFormat")
    private final Format formatter = new SimpleDateFormat("hh:mm a");
    private TextView selectedTime1, selectedTime2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        selectedTime1 = view.findViewById(R.id.alarm1Time);
        selectedTime2 = view.findViewById(R.id.alarm2Time);
        Button setAlarmButton1 = view.findViewById(R.id.setAlarm1);
        Button setAlarmButton2 = view.findViewById(R.id.setAlarm2);
        Button startServiceButton = view.findViewById(R.id.startService);
        Button endServiceButton = view.findViewById(R.id.endService);

        setAlarmButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Time time = new Time(hourOfDay, minute, 0);
                        selectedTime1.setText(formatter.format(time));
                        AlarmService.selectedTime1 = String.valueOf(selectedTime1.getText());
                    }
                }, hour, minute, false);
                timePickerDialog.show();

            }
        });

        setAlarmButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Time time = new Time(hourOfDay, minute, 0);
                        selectedTime2.setText(formatter.format(time));
                        AlarmService.selectedTime2 = String.valueOf(selectedTime2.getText());
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlarmService.class);
                requireActivity().startService(intent);
            }
        });

        endServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlarmService.class);
                requireActivity().stopService(intent);
            }
        });

        if (savedInstanceState != null) {
            String s1 = savedInstanceState.getString("alarm1_time");
            String s2 = savedInstanceState.getString("alarm2_time");
            selectedTime1.setText(s1);
            selectedTime2.setText(s2);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("alarm1_time", String.valueOf(selectedTime1.getText()));
        outState.putString("alarm2_time", String.valueOf(selectedTime2.getText()));
    }
}