/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central;

import cz.ascaria.network.central.utils.DateHelper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author Ascaria Quynn
 */
public class Console {
    
    private JFrame frame;
    private JTextPane chatLog;
    private JTextField messageField;

    private Main app;

    /**
     * Prints output to the console with time mark.
     * @param append 
     */
    public static void sysprintln(String append) {
        System.out.println(DateHelper.time() + " " + append);
    }

    /**
     * Creates console instance.
     */
    public Console() {
    }

    /**
     * Initialize console window.
     * @param app
     */
    public void initialize(Main app) {
        this.app = app;

        frame = new JFrame();
        frame.setTitle("Zone of Uprising Central Console");
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e){
                messageField.requestFocus();
            }
        });

        JPanel chatContainer = new JPanel();
        chatContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        chatContainer.setLayout(new BorderLayout());
        frame.getContentPane().add(chatContainer, BorderLayout.CENTER);

        chatLog = new JTextPane();
        chatLog.setMargin(new Insets(5, 5, 5, 5));
        chatContainer.add(new JScrollPane(chatLog), BorderLayout.CENTER);

        JPanel commandLine = new JPanel();
        commandLine.setLayout(new BoxLayout(commandLine, BoxLayout.X_AXIS));
        commandLine.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        frame.getContentPane().add(commandLine, "South");

        messageField = new JTextField();
        messageField.setBorder(BorderFactory.createLineBorder(Color.gray));
        commandLine.add(messageField);
        commandLine.add(Box.createHorizontalStrut(5));
        commandLine.add(new JButton(new AbstractAction("send") {
            @Override
            public void actionPerformed(ActionEvent event) {
                messageField.postActionEvent();
            }
        }));

        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                println("nic nebude, poustim Console.this.app.stop()");
                Console.this.app.stop();
            }
        });
    }

    /**
     * Adds the specified action listener to receive
     * action events from this textfield.
     *
     * @param listener the action listener to be added
     */
    public void addActionListener(ActionListener listener) {
        if(null != messageField) {
            messageField.addActionListener(listener);
        }
    }

    /**
     * Displays frame.
     */
    public void show() {
        if(null == frame) {
            throw new  IllegalStateException("Use setupFrame() first.");
        }
        frame.setVisible(true);
    }

    /**
     * Clears input text from console.
     */
    public void clearInputText() {
        if(null != messageField) {
            messageField.setText(null);
        }
    }

    /**
     * Restores System.out and System.err
     */
    public void shutdown() {
        if(null != frame) {
            frame.dispose();
        }
    }

    /**
     * Append string to chat log.
     * @param append 
     */
    public void print(final String append) {
        if(null != chatLog) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Document doc = chatLog.getDocument();
                        doc.insertString(doc.getLength(), DateHelper.time() + " " + append, null);
                        // Set selection to the end so that the scroll panel will scroll down.
                        chatLog.select(doc.getLength(), doc.getLength());
                    } catch (BadLocationException ex) {
                        Main.LOG.log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    /**
     * Append string line to chat log.
     * @param append 
     */
    public void println(final String append) {
        if(null != chatLog) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Document doc = chatLog.getDocument();
                        doc.insertString(doc.getLength(), "\n" + DateHelper.time() + " " + append, null);
                        // Set selection to the end so that the scroll panel will scroll down.
                        chatLog.select(doc.getLength(), doc.getLength());
                    } catch (BadLocationException ex) {
                        Main.LOG.log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }
}
