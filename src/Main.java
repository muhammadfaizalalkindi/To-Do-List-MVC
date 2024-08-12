import controller.TodoListController;
import model.TodoListModel;
import login.LoginView;
import register.RegisterView;
import view.TodoListView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterView registerView = new RegisterView();
            registerView.setRegisterListener(e -> {
                JOptionPane.showMessageDialog(registerView, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                registerView.dispose();
                openLoginView();
            });
            registerView.setVisible(true);
        });
    }

    private static void openLoginView() {
        LoginView loginView = new LoginView();
        loginView.setLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginView.getUsername();
                String password = loginView.getPassword();

                if (username.equals("user") && password.equals("password")) {
                    openTodoListApp();
                    loginView.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginView, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    loginView.clearPassword();
                }
            }

            private void openTodoListApp() {
                TodoListModel model = new TodoListModel();
                TodoListView view = new TodoListView(model);
                new TodoListController(model, view);
            }
        });
    }
}
