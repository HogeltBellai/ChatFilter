package ru.hogeltbellai.chatfilter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.hogeltbellai.chatfilter.ChatFilter;
import ru.hogeltbellai.chatfilter.api.config.ConfigAPI;

public class ChatFilter_Command implements CommandExecutor {

    public ChatFilter_Command() {
        ChatFilter.getInstance().getCommand("chatfilter").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        ConfigAPI config = new ConfigAPI("words");

        if(sender.hasPermission("chatfilter.admin")) {
            if(args.length == 0) {
                String blockedWords = String.join(", ", ChatFilter.getInstance().getWordsBlocked());

                sender.sendMessage("Запрещённые слова:");
                sender.sendMessage(blockedWords);
            } else if (args[0].equalsIgnoreCase("add") && args.length > 1) {
                String blockWord = args[1].toLowerCase();

                if(!ChatFilter.getInstance().getWordsBlocked().contains(blockWord)) {
                    ChatFilter.getInstance().getWordsBlocked().add(blockWord);
                    config.getConfig().set("blocked_word", ChatFilter.getInstance().getWordsBlocked());
                    config.saveConfig();
                    sender.sendMessage("Вы добавили слово: " + blockWord);
                } else {
                    sender.sendMessage("Это слово уже запрещено: " + blockWord);
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("delete") && args.length > 1) {
                String blockWord = args[1].toLowerCase();

                if(ChatFilter.getInstance().getWordsBlocked().contains(blockWord)) {
                    ChatFilter.getInstance().getWordsBlocked().remove(blockWord);
                    config.getConfig().set("blocked_word", ChatFilter.getInstance().getWordsBlocked());
                    config.saveConfig();
                    sender.sendMessage("Вы удалили слово: " + blockWord);
                } else {
                    sender.sendMessage("Этого слова нет в списке: " + blockWord);
                    return true;
                }
            }
        }
        return false;
    }
}
