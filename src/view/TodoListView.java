package view;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.Font;
import model.TodoListModel;
import model.TodoListModel.TodoTask;
import java.awt.event.ActionListener;
import java.util.List;

public class TodoListView {
    public static final String TodoList = null;
    private JFrame frame;
    public JList<String> todoList;
    private DefaultListModel<String> listModel;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton doneButton;
    public JTextField yearField;
    public JTextField monthField;
    public JTextField dayField;
    public JTextField hourField;
    public JTextField minuteField;
    private TodoListModel model;
    private JTextField taskField;

    public TodoListView(TodoListModel model) {
        this.model = model;
        frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        JPanel panel = new JPanel();
        listModel = new DefaultListModel<>();
        todoList = new JList<>(listModel);
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        editButton = new JButton("Edit");
        doneButton = new JButton("Done");
        yearField = new JTextField(4);
        monthField = new JTextField(2);
        dayField = new JTextField(2);
        hourField = new JTextField(2);
        minuteField = new JTextField(2);
        taskField = new JTextField(15);

        JLabel titleLabel = new JLabel("To-do List Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);

        panel.add(taskField);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(editButton);
        panel.add(doneButton);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Month:"));
        panel.add(monthField);
        panel.add(new JLabel("Day:"));
        panel.add(dayField);
        panel.add(new JLabel("Hour:"));
        panel.add(hourField);
        panel.add(new JLabel("Minute:"));
        panel.add(minuteField);
        panel.add(new JScrollPane(todoList));

        frame.add(panel);
        frame.setVisible(true);
    }

    public String getTaskInput() {
        return taskField.getText();
    }

    public String getEditedTaskInput() {
        return taskField.getText();
    }

    public void setTaskInput(String text) {
        taskField.setText(text);
    }

    public void setEditedTaskInput(String text) {
        taskField.setText(text);
    }

    public void addTaskWithTime(String task, int year, int month, int day, int hour, int minute) {
        String minuteFormatted = String.format("%02d", minute);
        String taskDescription = task + " (" + year + "/" + month + "/" + day + " " + hour + ":" + minuteFormatted + ")";
        listModel.addElement(taskDescription);
    }
    

    public void displayTaskTimeInfo(int index) {
        if (index >= 0 && index < model.getTasks().size()) {
            TodoTask selectedTask = model.getTasks().get(index);
    
            String existingTaskDescription = listModel.getElementAt(index);
    
            String taskName = selectedTask.getDescription();
            String minuteFormatted = String.format("%02d", selectedTask.getDeadlineMinute());
            String timeInfo = " (" + selectedTask.getDeadlineYear() + "/" + selectedTask.getDeadlineMonth() + "/"
                    + selectedTask.getDeadlineDay() + " " + selectedTask.getDeadlineHour() + ":"
                    + minuteFormatted + ")";
            // Check if the task is already marked as Done
            boolean isDone = existingTaskDescription.contains("(Done)");
    
            // Preserve the existing task name and time info
            String updatedTaskDescription;
            if (isDone) {
                // If it's already marked as Done, retain that status
                updatedTaskDescription = taskName + timeInfo + " (Done)";
            } else {
                // Otherwise, keep it as Pending
                updatedTaskDescription = taskName + timeInfo + " (Pending)";
            }
    
            listModel.setElementAt(updatedTaskDescription, index);
    
            yearField.setText(String.valueOf(selectedTask.getDeadlineYear()));
            monthField.setText(String.valueOf(selectedTask.getDeadlineMonth()));
            dayField.setText(String.valueOf(selectedTask.getDeadlineDay()));
            hourField.setText(String.valueOf(selectedTask.getDeadlineHour()));
            minuteField.setText(String.valueOf(selectedTask.getDeadlineMinute()));
    
            taskField.setText(selectedTask.getDescription());
        }
    }
    
    
    

    public void setTasks(List<TodoTask> list) {
        listModel.clear();
        for (TodoTask task : list) {
            String taskDescription = task.getDescription();
            String status = task.isDone() ? " (Done)" : " (Pending)";
            listModel.addElement(taskDescription + status);
        }
    }

    public int getSelectedIndex() {
        return todoList.getSelectedIndex();
    }
    
    public void updateTaskDateTime(int index, int year, int month, int day, int hour, int minute) {
        if (index != -1) {
            model.getTasks().get(index).setDeadlineYear(year);
            model.getTasks().get(index).setDeadlineMonth(month);
            model.getTasks().get(index).setDeadlineDay(day);
            model.getTasks().get(index).setDeadlineHour(hour);
            model.getTasks().get(index).setDeadlineMinute(minute);
        }
    }    

    static class JTextFieldLimit extends PlainDocument {
        private int limit;

        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            if ((getLength() + str.length()) <= limit) {
                try {
                    Integer.parseInt(str); 
                    super.insertString(offset, str, attr);
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(e -> {
            if (!validateInputs()) {
                JOptionPane.showMessageDialog(frame, "Input error", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            listener.actionPerformed(e);
        });
    }

    public void addRemoveButtonListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }

    public void addEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void addEditButtonListener() {
        editButton.addActionListener(e -> {
            int selectedIndex = todoList.getSelectedIndex();
            String editedTask = getEditedTaskInput();
            if (selectedIndex != -1 && !editedTask.isEmpty()) {
                model.editTask(selectedIndex, editedTask);
                setTasks(model.getTasks());
                setEditedTaskInput("");

                displayTaskTimeInfo(selectedIndex);
            }
        });
    }

    public void addDoneButtonListener(ActionListener listener) {
        doneButton.addActionListener(listener);
    }

    public String getYearInput() {
        return yearField.getText();
    }

    public String getMonthInput() {
        return monthField.getText();
    }

    public String getDayInput() {
        return dayField.getText();
    }

    public String getHourInput() {
        return hourField.getText();
    }

    public String getMinuteInput() {
        return minuteField.getText();
    }

    private boolean validateInputs() {
        return isNumeric(yearField.getText()) && yearField.getText().length() == 4 &&
                isNumeric(monthField.getText()) && isNumeric(dayField.getText()) &&
                isNumeric(hourField.getText()) && isNumeric(minuteField.getText());
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public void clearDateTimeInputs() {
        yearField.setText("");
        monthField.setText("");
        dayField.setText("");
        hourField.setText("");
        minuteField.setText("");
    }
}
