package com.example.blinkit.ActivityTracker;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class Tracker {
    private static final List<Class<? extends Activity>> activityList = new ArrayList<>();

    public static void addActivity(Class<? extends Activity> activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Class<? extends Activity> activity) {
        activityList.remove(activity);
    }

    public static Class<? extends Activity> getThirdLastActivity() {
        int size = activityList.size();
        if (size < 4) {
            return null; // or handle appropriately
        }
        return activityList.get(size - 4);
    }
}

