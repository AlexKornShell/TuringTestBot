package ru.itmo.practice;

import java.io.File;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;

@Log4j
class ChatBot {
    private static final boolean TRACE_MODE = true;
    private static String botName = "TuringTestBot";

    private String resourcesPath;
    private Bot bot;
    private Chat chatSession;
    private String textLine;

    private void setResources () {
        resourcesPath = getResourcesPath();
        System.out.println(resourcesPath);
        MagicBooleans.trace_mode = TRACE_MODE;
        bot = new Bot(botName, resourcesPath);
        chatSession = new Chat(bot);
        bot.brain.nodeStats();
        textLine = "";
    }

    @SneakyThrows
    String answer (String msg) {
        setResources();
        if (MagicBooleans.trace_mode)
            System.out.println("STATE=" + msg + ":THAT=" + (chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
        String response = chatSession.multisentenceRespond(msg);
        response = response.replace("&lt;", "<");
        response = response.replace("&gt;", ">");
        return response;
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        return (path + File.separator + "src" + File.separator + "main" + File.separator + "resources");
    }
}