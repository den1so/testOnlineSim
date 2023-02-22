package com.example.sendpushfcm;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

public class ExampleBotHandler extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText("Hello, " + update.getMessage().getFrom().getFirstName() + "!");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

//    @Override
//    public void onUpdateReceived(Update update) {
//
//    }

    @Override
    public String getBotUsername() {
        return "ExampleBot";
    }

    @Override
    public String getBotToken() {
        return "274173725:AAGZ9f8ySmCwLlGxkR2tttKvX8DhtYTpW6w";
    }

    @Override
    public void onClosing() {
        super.onClosing();
    }
}

