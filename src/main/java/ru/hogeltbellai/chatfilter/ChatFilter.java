package ru.hogeltbellai.chatfilter;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.hogeltbellai.chatfilter.api.config.ConfigAPI;

/**
 * Programming by HogeltBellai
 * Site: hogeltbellai.ru
 */
public final class ChatFilter extends JavaPlugin {

    @Getter private static ChatFilter instance;

    @Override
    public void onEnable() {
        instance = this;

        new ConfigAPI("config");
        new ConfigAPI("words");
    }

    @Override
    public void onDisable() {

    }
}
