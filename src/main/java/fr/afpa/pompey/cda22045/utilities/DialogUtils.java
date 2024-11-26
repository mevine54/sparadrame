package fr.afpa.pompey.cda22045.utilities;

import javax.swing.*;
import java.awt.*;

public class DialogUtils {

    // Affiche un message d'information
    public static void showInfoDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Affiche un message d'avertissement
    public static void showWarningDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }

    // Affiche un message d'erreur
    public static void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    // Affiche un dialogue de confirmation
    public static boolean showConfirmDialog(String message, String title) {
        int response = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return response == JOptionPane.YES_OPTION;
    }

    // Affiche un champ d'entrée (dialogue avec un champ de texte)
    public static String showInputDialog(String message, String title) {
        return JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    // Affiche une boîte de sélection
    public static String showDropdownDialog(String[] options, String message, String title) {
        return (String) JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    // Affiche un message dans la console
    public static void showConsoleMessage(String message) {
        System.out.println(message);
    }

    // Dialogue d'erreur générique pour des exceptions
    public static void showExceptionDialog(Exception e, String title) {
        StringBuilder message = new StringBuilder("Une erreur est survenue :\n");
        message.append(e.getMessage()).append("\n");
        for (StackTraceElement element : e.getStackTrace()) {
            message.append(element.toString()).append("\n");
        }
        JTextArea textArea = new JTextArea(message.toString());
        textArea.setEditable(false);
        textArea.setBackground(new Color(240, 240, 240));
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), title, JOptionPane.ERROR_MESSAGE);
    }
}
