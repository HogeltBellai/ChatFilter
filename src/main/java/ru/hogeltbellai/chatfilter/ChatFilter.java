package ru.hogeltbellai.chatfilter;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.hogeltbellai.chatfilter.api.config.ConfigAPI;
import ru.hogeltbellai.chatfilter.listener.ChatListener;

import java.util.List;

/**
 * Programming by HogeltBellai
 * Site: hogeltbellai.ru
 */
public final class ChatFilter extends JavaPlugin {

    @Getter public static ChatFilter instance;
    @Getter public List<String> wordsBlocked;

    @Override
    public void onEnable() {
        instance = this;

        new ConfigAPI("config");
        wordsBlocked = new ConfigAPI("words").getConfig().getStringList("blocked_word");

        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
