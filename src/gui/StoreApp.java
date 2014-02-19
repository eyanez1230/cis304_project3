package gui;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

public class StoreApp extends JApplet {

    public void init() {
        final String author = getParameter("author");
        final String course = getParameter("course");
        final String fontFamily = getParameter("fontFamily:");
        final String defaultImage = getParameter("defaultImage");
        final String errorMessage = getParameter("errorMessage");

        if (course.equalsIgnoreCase("CIS 304") && author.equalsIgnoreCase("Enrique Yanez")) {
            this.setSize(1000, 525);
            getContentPane().add(new StorePanel(fontFamily, defaultImage));
            setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, errorMessage);
        }
    }
}
