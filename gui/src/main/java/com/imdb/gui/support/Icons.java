package com.imdb.gui.support;

import javax.swing.*;

/**
 * @author sulaiman kadkhodaei
 */
public final class Icons {
    public static final Icon delete = getIcon("/icons/general-remove.svg");
    public static final Icon copy = getIcon("/icons/copy.svg");
    public static final Icon scrollDown = getIcon("/icons/scroll-down.svg");

    public static Icon getIcon(String resourceName, int width, int height) {
        if (resourceName.endsWith(".svg")) {
            return new SvgIcon(Icons.class.getResource(resourceName), width, height);
        }

        return new ImageIcon(Icons.class.getResource(resourceName));
    }

    public static Icon getIcon(String resourceName) {
        return getIcon(resourceName, 24, 24);
    }
}
