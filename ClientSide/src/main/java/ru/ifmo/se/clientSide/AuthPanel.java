package ru.ifmo.se.clientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.locks.ReentrantLock;

public class AuthPanel extends JFrame {
    private ClientApp app;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel label;
    private JLabel serverAnsw;
    private JPasswordField textField;
    private Container container;
    private JPanel jPanel;
    private ReentrantLock lock = new ReentrantLock();
    private GroupLayout groupLayout;

    public AuthPanel(ClientApp app) {
        super("Authorisation");
        this.app = app;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        container = getContentPane();
        jPanel = new JPanel();
        groupLayout = new GroupLayout(jPanel);
        groupLayout.getAutoCreateGaps();
        container.add(jPanel);

        label = new JLabel("Enter password:");
        serverAnsw = new JLabel("Connecting to server...");
        textField = new JPasswordField();
        textField.setEchoChar('☀');
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    char[] chars;
                    chars = textField.getPassword();
                    String password = new String(chars);
                    try {
                        ClientApp.toServer.println(password);
                    } catch (NullPointerException ee) {

                    }
                    String answer = app.gettingResponse();
                    if (answer.startsWith("You've")) {
                        setVisible(false);
                        MainPanel.isAuthorized = true;
                    } else
                        serverAnsw.setText(answer);
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        okButton = new JButton("Ok");
        okButton.addActionListener(e -> {
            char[] chars;
            chars = textField.getPassword();
            String password = new String(chars);
            try {
                ClientApp.toServer.println(password);
            } catch (NullPointerException ee) {

            }
            String answer = app.gettingResponse();
            if (answer.startsWith("You've")) {
                setVisible(false);
                MainPanel.isAuthorized = true;
            } else
                serverAnsw.setText(answer);
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> System.exit(0));

        group();
        jPanel.setLayout(groupLayout);
        groupLayout.linkSize(textField);
        pack();
        setVisible(true);
    }

    public void group() {
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(label).addGap(10)
                        .addComponent(textField, 20, 20, 20).addGap(10)
                        .addGroup(groupLayout.createParallelGroup()
                                .addComponent(okButton).addGap(10)
                                .addComponent(cancelButton)).addGap(10)
                        .addComponent(serverAnsw));
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup()
                        .addComponent(label).addGap(10)
                        .addComponent(textField, 100, 150, 150).addGap(10)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(okButton).addGap(10)
                                .addComponent(cancelButton)).addGap(10)
                        .addComponent(serverAnsw, 200, 250, 300));
    }

    public void setLabel(String string) {
        serverAnsw.setText(string);
    }
}
