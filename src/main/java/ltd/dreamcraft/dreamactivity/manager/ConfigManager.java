package ltd.dreamcraft.dreamactivity.manager;

import ltd.dreamcraft.dreamactivity.DreamActivity;

public class ConfigManager {
    public static boolean isCheckUpdate() {
        return DreamActivity.in().getConfig().getBoolean("Settings.check-update");
    }

}
