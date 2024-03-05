package ru.hogeltbellai.chatfilter.listener;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.hogeltbellai.chatfilter.ChatFilter;
import ru.hogeltbellai.chatfilter.api.config.ConfigAPI;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        String censoredMessage = censorText(message, ChatFilter.getInstance().getWordsBlocked());

        event.setMessage(censoredMessage);
    }

    private String censorText(String text, List<String> forbiddenWords) {
        FileConfiguration config = new ConfigAPI("config").getConfig();

        String colorCodes = getColorCodes(text);
        text = ChatColor.stripColor(text);

        List<String> lowerCaseForbiddenWords = forbiddenWords.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        StringBuilder censoredText = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetterOrDigit(c) || c == '_') {
                currentWord.append(c);
            } else {
                if (currentWord.length() > 0) {
                    String word = currentWord.toString();
                    String lowerCaseWord = word.toLowerCase();
                    if (lowerCaseForbiddenWords.contains(lowerCaseWord)) {
                        String censoredWord = word.charAt(0) + Objects.requireNonNull(config.getString("replace")).repeat(word.length() - 1);
                        censoredText.append(censoredWord);
                    } else {
                        censoredText.append(word);
                    }
                    currentWord.setLength(0);
                }
                censoredText.append(c);
            }
        }

        if (currentWord.length() > 0) {
            String word = currentWord.toString();
            String lowerCaseWord = word.toLowerCase();
            if (lowerCaseForbiddenWords.contains(lowerCaseWord)) {
                String censoredWord = word.charAt(0) + Objects.requireNonNull(config.getString("replace")).repeat(word.length() - 1);
                censoredText.append(censoredWord);
            } else {
                censoredText.append(word);
            }
        }

        return colorCodes + censoredText.toString();
    }

    private String getColorCodes(String text) {
        StringBuilder colorCodes = new StringBuilder();
        boolean isColorCode = false;
        for (char c : text.toCharArray()) {
            if (c == ChatColor.COLOR_CHAR) {
                isColorCode = true;
            } else if (isColorCode) {
                if (ChatColor.getByChar(c) != null) {
                    colorCodes.append(ChatColor.COLOR_CHAR).append(c);
                }
                isColorCode = false;
            }
        }
        return colorCodes.toString();
    }
}
