package com.example.androidsession_08.launch_mode;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    /**
     * 创建单例
     */
    private static class Inner {
        private static final ActivityCollector INSTANCE = new ActivityCollector();
    }

    public static ActivityCollector getActivityCollector() {
        return Inner.INSTANCE;
    }

    /**
     * 管理 Activity
     */
    private List<Activity> activities = new ArrayList<>();

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 销毁所有在 activities 的 Activity
     */
    public void destroyAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        // 删除 activities 集合中的所有 Activity
        activities.clear();
    }
}
