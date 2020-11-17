package com.imdb.gui.logging;

import org.pmw.tinylog.Configuration;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.LogEntry;
import org.pmw.tinylog.writers.LogEntryValue;
import org.pmw.tinylog.writers.PropertiesSupport;
import org.pmw.tinylog.writers.Writer;

import java.io.PrintStream;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author sulaiman kadkhodaei
 * Writes log entries to the given print streams.
 */
@PropertiesSupport(name = "console", properties = {})
public final class ConsoleWriter implements Writer {

    private final PrintStream stdout;
    private final PrintStream stderr;

    public ConsoleWriter(PrintStream stdout, PrintStream stderr) {
        this.stdout = stdout;
        this.stderr = stderr;
    }

    @Override
    public Set<LogEntryValue> getRequiredLogEntryValues() {
        return EnumSet.of(LogEntryValue.LEVEL, LogEntryValue.RENDERED_LOG_ENTRY);
    }

    @Override
    public void init(final Configuration configuration) {
        // Do nothing
    }

    @Override
    public void write(final LogEntry logEntry) {
        getPrintStream(logEntry.getLevel()).print(logEntry.getRenderedLogEntry());
    }

    @Override
    public void flush() {
        // Do nothing
    }

    @Override
    public void close() {
        // Do nothing
    }

    private PrintStream getPrintStream(final Level level) {
        if (level == Level.ERROR || level == Level.WARNING) {
            return stderr;
        }

        return stdout;
    }

}