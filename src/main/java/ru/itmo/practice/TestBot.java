package ru.itmo.practice;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import lombok.val;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.HashMap;
import java.util.Map;

@Log4j
public class TestBot extends TelegramLongPollingBot {

    private Map<String, ChatBot> chatBots = new HashMap<String, ChatBot>();

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ApiContextInitializer.init();
        try {
            new TelegramBotsApi()
                    .registerBot(new TestBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "TuringTestPass_bot";
    }

    @Override
    public String getBotToken() {
        return "Token";
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update upd) {
        Message msg = upd.getMessage();
        String chatID = msg.getChatId().toString();
        if (msg.hasText()) {
            if (msg.getText().equals("/start") && !chatBots.containsKey(chatID))
                chatBots.put(chatID, new ChatBot());
            sendMsg(msg, chatBots.get(chatID).answer(msg.getText()));
        } else {
            sendMsg(msg, "No text");
        }
    }

    @SneakyThrows
    @SuppressWarnings("deprecation")
    private void sendMsg(Message msg, String text) {
        SendMessage sendMsg = new SendMessage()
                .enableMarkdown(true)
                .setChatId(msg.getChatId())
                .setText(text);
        //noinspection deprecation
        try {
            sendMessage(sendMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}