package register;

import javax.swing.*;
import datauser.DataUser;
import login.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import datauserstorage.DataUserStorage;

public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton goToLoginButton;

    public RegisterView() {
        setTitle("Register");
        setSize(300, 150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("To-do List Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());

        goToLoginButton = new JButton("Already have an account? Login here");
        goToLoginButton.addActionListener(new GoToLoginListener());

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(usernameLabel)
                    .addComponent(passwordLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(usernameField)
                    .addComponent(passwordField)))
            .addComponent(registerButton)
            .addComponent(goToLoginButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(titleLabel)
            .addGap(20)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(usernameLabel)
                .addComponent(usernameField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passwordLabel)
                .addComponent(passwordField))
            .addComponent(registerButton)
            .addComponent(goToLoginButton)
        );

        add(panel);
        setVisible(true);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void setRegisterListener(ActionListener actionListener) {
        registerButton.addActionListener(actionListener);
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = getUsername();
            String password = getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(RegisterView.this, "Please enter username and password", "Registration Error", JOptionPane.ERROR_MESSAGE);
            } else {
                DataUser newUser = new DataUser(username, password);
                boolean registrationSuccessful = registerUser(newUser);

                if (registrationSuccessful) {
                    JOptionPane.showMessageDialog(RegisterView.this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    openLoginView();
                } else {
                    JOptionPane.showMessageDialog(RegisterView.this, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private boolean registerUser(DataUser newUser) {
            return DataUserStorage.addUser(newUser);
        }
    }

    private class GoToLoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            openLoginView();
        }
    }

    public void openLoginView() {
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }
}
