package com.imdb.gui;

import com.imdb.gui.logging.ConsoleWriter;
import com.imdb.gui.logging.SystemLogger;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.RollingFileWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.prefs.Preferences;

import static java.lang.System.*;
import static javax.swing.SwingConstants.TOP;

/**
 * @author sulaiman kadkhodaei
 * The main window of the application.
 */
public class MainFrame extends JFrame {

    private final Preferences prefs;
    private static final String PREF_WINDOW_X = "com.imdb.gui.MainFrame.windowX";
    private static final int PREF_WINDOW_X_DEF = 0;
    private static final String PREF_WINDOW_Y = "com.imdb.gui.MainFrame.windowY";
    private static final int PREF_WINDOW_Y_DEF = 0;
    private static final String PREF_WINDOW_WIDTH = "com.imdb.gui.MainFrame.windowWidth";
    private static final int PREF_WINDOW_WIDTH_DEF = 1024;
    private static final String PREF_WINDOW_HEIGHT = "com.imdb.gui.MainFrame.windowHeight";
    private static final int PREF_WINDOW_HEIGHT_DEF = 768;

    private final JTabbedPane tabs;

    public MainFrame() {

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });

        prefs = Preferences.userNodeForPackage(MainFrame.class);
        if (prefs.getInt(PREF_WINDOW_WIDTH, 50) < 50) {
            prefs.putInt(PREF_WINDOW_WIDTH, PREF_WINDOW_WIDTH_DEF);
        }

        if (prefs.getInt(PREF_WINDOW_HEIGHT, 50) < 50) {
            prefs.putInt(PREF_WINDOW_HEIGHT, PREF_WINDOW_HEIGHT_DEF);
        }

        setBounds(prefs.getInt(PREF_WINDOW_X, PREF_WINDOW_X_DEF),
                prefs.getInt(PREF_WINDOW_Y, PREF_WINDOW_Y_DEF),
                prefs.getInt(PREF_WINDOW_WIDTH, PREF_WINDOW_WIDTH_DEF),
                prefs.getInt(PREF_WINDOW_HEIGHT, PREF_WINDOW_HEIGHT_DEF));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        tabs = new JTabbedPane(TOP);

        ComponentListener componentListener = new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                prefs.putInt(PREF_WINDOW_X, getLocation().x);
                prefs.putInt(PREF_WINDOW_Y, getLocation().y);
            }

            @Override
            public void componentResized(ComponentEvent e) {
                prefs.putInt(PREF_WINDOW_WIDTH, getSize().width);
                prefs.putInt(PREF_WINDOW_HEIGHT, getSize().height);
            }
        };
        addComponentListener(componentListener);
    }

    public boolean quit() {
        Logger.trace("Shutting down...");
        try {
            Preferences.userRoot().flush();
        } catch (Exception ignored) {

        }
        Logger.trace("Shutdown complete, exiting.");
        System.exit(0);
        return true;
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    private static void configureLogging(File configurationDirectory) {

        File logDirectory = new File(configurationDirectory, "log");
        File logFile = new File(logDirectory, "crawler.log");

        Configurator
                .currentConfig()
                .writer(new RollingFileWriter(logFile.getAbsolutePath(), 100))
                .addWriter(new ConsoleWriter(out, err))
                .activate();

        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss.SSS} {class_name} {level}: {message}")
                .activate();

        // Redirect the stdout and stderr to the LogPanel
        SystemLogger out = new SystemLogger(System.out, Level.INFO);
        SystemLogger err = new SystemLogger(System.err, Level.ERROR);
        setOut(out);
        setErr(err);
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new Error(e);
        }

        File loggingDirectory = new File(getProperty("user.home"));
        loggingDirectory = new File(loggingDirectory, ".scraper");
        configureLogging(loggingDirectory);

        EventQueue.invokeLater(() -> {
            try {
                new MainFrame().setVisible(true);
                Logger.trace("IMDB Web Crawling Started!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
