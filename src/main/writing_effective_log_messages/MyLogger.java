package main.writing_effective_log_messages;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created by G.Chalauri on 04/03/17.
 */
public class MyLogger {
    private static Handler handler;

    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setLevel(Level.ALL);

        try {
            if (handler == null) {
                handler = new FileHandler("recipe8.log");
                Formatter format = new MyFormatter();
                handler.setFormatter(format);
            }

            if (logger.getHandlers().length == 0) {
                logger.addHandler(handler);
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logger;
    }
}
