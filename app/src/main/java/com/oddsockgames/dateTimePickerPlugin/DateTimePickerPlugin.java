package com.oddsockgames.dateTimePickerPlugin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.ArraySet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.godotengine.godot.Dictionary;
import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

import java.util.Calendar;
import java.util.Set;

public class DateTimePickerPlugin extends GodotPlugin {
    private final Context context;
    private final Activity activity;
    private FrameLayout layout = null;

    public DateTimePickerPlugin(Godot godot) {
        super(godot);
        context = godot.getContext();
        activity = godot.getActivity();
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "DateTimePickerPlugin";
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();
        signals.add(new SignalInfo("onDateTimePicked", String.class, Dictionary.class));
        return signals;
    }

    @Nullable
    @Override
    public View onMainCreate(Activity activity) {
        layout = new FrameLayout(activity);
        return layout;
    }

    @UsedByGodot
    public void showDateTimePicker() {
        showDateTimePicker("");
    }

    @UsedByGodot
    public void showDateTimePicker(String reference) {
        activity.runOnUiThread(() -> {

            final Calendar calendar = Calendar.getInstance();
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentYear = calendar.get(Calendar.YEAR);
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);

            Dictionary selectedDateTime = new Dictionary();

            TimePickerDialog timePicker = new TimePickerDialog(context,
                    (TimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute) -> {
                        selectedDateTime.put("hour", hourOfDay);
                        selectedDateTime.put("minute", minute);

                        emitSignal("onDateTimePicked", reference, selectedDateTime);
                    }, currentHour, currentMinute, false);

            DatePickerDialog datePicker = new DatePickerDialog(context,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        selectedDateTime.put("day", dayOfMonth);
                        selectedDateTime.put("month", (monthOfYear + 1));
                        selectedDateTime.put("year", year);

                        timePicker.show();
                    }, currentYear, currentMonth, currentDay);

            datePicker.show();
        });
    }
}
