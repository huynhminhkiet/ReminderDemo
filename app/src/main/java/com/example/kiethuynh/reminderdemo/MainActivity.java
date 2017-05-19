package com.example.kiethuynh.reminderdemo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private EditText edtHour, edtMinute;
    private TextView tvAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtHour = (EditText) findViewById(R.id.edt_hour);
        edtMinute = (EditText) findViewById(R.id.edt_minute);
        tvAlarm = (TextView) findViewById(R.id.tv_alarm);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(edtHour.getText().toString(), edtMinute.getText().toString())) {
                    scheduleNotification(Integer.parseInt(edtHour.getText().toString()),
                            Integer.parseInt(edtMinute.getText().toString()));
                    tvAlarm.setText("Alarm: " + edtHour.getText().toString() + ":" + edtMinute.getText().toString());
                }
            }
        });
    }

    private boolean validate(String hour, String minute) {
        return !hour.isEmpty() && !minute.isEmpty();
    }

    private void scheduleNotification(int hour, int minute) {
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
    }
}
