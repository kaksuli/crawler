package com.imdb.gui.logging;

import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

import javax.validation.constraints.NotNull;
import java.io.PrintStream;

/**
 * @author sulaiman kadkhodaei
 * A SystemLogger class that redirects another stream like e.g. stdout or stderr to the tinylog logger
 */
public class    SystemLogger extends PrintStream {

    private final StringBuilder logMessage = new StringBuilder();

    private final Level logLevel;
    private boolean logToLogger = true;

    public SystemLogger(PrintStream out, Level logLevel) {
        super(out);
        this.logLevel = logLevel;

        // In order to stop com.imdb.gui.logging on shutdown, we need to make sure that we do not call the Logger anymore
        Runtime.getRuntime().addShutdownHook(new Thread(() -> logToLogger = false));
    }

    private void flushLogMessage() {

        String str = logMessage.toString();
        logMessage.setLength(0); // set length of buffer to 0
        logMessage.trimToSize(); // trim the underlying buffer

        switch (logLevel) {
            case INFO:
                Logger.info(str);
                break;
            case DEBUG:
                Logger.debug(str);
                break;
            case WARNING:
                Logger.warn(str);
                break;
            default:
                Logger.trace(str);
        }

    }

    @Override
    public void write(@NotNull byte[] buffer, int off, int len) {
        /*
         * Log to tinylog as long as the Logger is available (before shutdown)
         * As tinylog will forward all logs to the console, we do not call the super method
         */
        if (logToLogger) {
            String str = new String(buffer, off, len);
            if (str.endsWith("\n")) {
                logMessage.append(str, 0, str.length() - 1);
                flushLogMessage();
            } else {
                logMessage.append(str);
            }
        } else {
            super.write(buffer, off, len);
        }
    }
}
