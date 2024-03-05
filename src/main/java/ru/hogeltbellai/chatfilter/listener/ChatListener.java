package ru.hogeltbellai.chatfilter.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if() {

        }
    }

    private String censorText(String inputText, List<String> forbiddenWords) {
        String regex = "\\b(" + String.join("|", forbiddenWords) + ")\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputText);

        StringBuilder censoredText = new StringBuilder();
        while (matcher.find()) {
            String word = matcher.group();
            String censoredWord = word.charAt(0) + "*".repeat(word.length() - 1);
            matcher.appendReplacement(censoredText, censoredWord);
        }
        matcher.appendTail(censoredText);

        return censoredText.toString();
    }
}
