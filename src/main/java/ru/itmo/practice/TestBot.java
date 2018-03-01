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

@Log4j
public class TestBot extends TelegramLongPollingBot {

    @SneakyThrows(TelegramApiRequestException.class)
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ApiContextInitializer.init();
        new TelegramBotsApi()
                .registerBot(new TestBot());
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
        if (msg != null && msg.hasText()) {
            if (msg.getText().startsWith("/")) {
                if (msg.getText().equals("/help"))
                    sendMsg(msg, "Привет, я робот");
                else
                    sendMsg(msg, "Я не знаю что ответить на это");
            }
        } else {
            sendMsg(msg, "Ответ");
        }
    }

    @SneakyThrows(TelegramApiException.class)
    @SuppressWarnings({"deprecation", "unchecked"})
    private void sendMsg(Message msg, String text) {
        SendMessage sendMsg = new SendMessage()
                .enableMarkdown(true)
                .setChatId(msg.getChatId())
                .setText(text);
        //noinspection deprecation
        sendMessage(sendMsg);
    }

}
