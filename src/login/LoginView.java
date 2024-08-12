package login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import model.TodoListModel;
import register.RegisterView;
import view.TodoListView;
import controller.TodoListController;
import datauser.DataUser;
import datauserstorage.DataUserStorage;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton goToRegisterButton;

    public LoginView() {
        setTitle("Login");
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

        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());

        goToRegisterButton = new JButton("Don't have an account? Register here");
        goToRegisterButton.addActionListener(new GoToRegisterListener());

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel)
            .addGap(20)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(usernameLabel)
                    .addComponent(passwordLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(usernameField)
                    .addComponent(passwordField)))
            .addComponent(loginButton)
            .addComponent(goToRegisterButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(titleLabel)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(usernameLabel)
                .addComponent(usernameField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passwordLabel)
                .addComponent(passwordField))
            .addComponent(loginButton)
            .addComponent(goToRegisterButton)
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

    public void setLoginListener(ActionListener actionListener) {
        loginButton.addActionListener(actionListener);
    }

    public void clearPassword() {
        passwordField.setText("");
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = getUsername();
            String password = getPassword();

            DataUser user = new DataUser(username, password);

            if (DataUserStorage.isValidUser(user)) {
                openTodoListApp();
                dispose();
            } else {
                showErrorMessage("Invalid username or password");
                clearPassword();
            }
        }

        private void showErrorMessage(String message) {
            JOptionPane.showMessageDialog(LoginView.this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
        }

        private void openTodoListApp() {
            TodoListModel model = new TodoListModel();
            TodoListView view = new TodoListView(model);
            new TodoListController(model, view);

            dispose();
        }
    }

    private class GoToRegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            openRegisterView();
        }
    }

    private void openRegisterView() {
        RegisterView registerView = new RegisterView();
        registerView.setVisible(true);
    }
}
