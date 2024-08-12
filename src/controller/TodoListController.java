package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.TodoListModel;
import model.TodoListModel.TodoTask;
import view.TodoListView;

public class TodoListController {
    private TodoListModel model;
    private TodoListView view;

    public TodoListController(TodoListModel model, TodoListView view) {
        this.model = model;
        this.view = view;

        view.setTasks(model.getTasks());

        view.addAddButtonListener(new AddButtonListener());
        view.addRemoveButtonListener(new RemoveButtonListener());
        view.addEditButtonListener(new EditButtonListener());
        view.addDoneButtonListener(new DoneButtonListener());
    }

    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String task = view.getTaskInput();
            int year = Integer.parseInt(view.getYearInput());
            int month = Integer.parseInt(view.getMonthInput());
            int day = Integer.parseInt(view.getDayInput());
            int hour = Integer.parseInt(view.getHourInput());
            int minute = Integer.parseInt(view.getMinuteInput());

            if (!task.isEmpty()) {
                model.addTask(task, year, month, day, hour, minute);
                view.setTasks(model.getTasks());
                view.setTaskInput("");
                view.clearDateTimeInputs();
                view.addTaskWithTime(task, year, month, day, hour, minute);
            }
        }
    }

    class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = view.todoList.getSelectedIndex();
            if (selectedIndex != -1) {
                model.removeTask(selectedIndex);
                view.setTasks(model.getTasks());
            }
        }
    }

    class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = view.todoList.getSelectedIndex();
            String editedTask = view.getEditedTaskInput();
            if (selectedIndex != -1 && !editedTask.isEmpty()) {
                model.editTask(selectedIndex, editedTask);
                view.setTasks(model.getTasks());
                view.setEditedTaskInput("");
                view.displayTaskTimeInfo(selectedIndex);

                TodoTask updatedTask = model.getTasks().get(selectedIndex);
                view.yearField.setText(String.valueOf(updatedTask.getDeadlineYear()));
                view.monthField.setText(String.valueOf(updatedTask.getDeadlineMonth()));
                view.dayField.setText(String.valueOf(updatedTask.getDeadlineDay()));
                view.hourField.setText(String.valueOf(updatedTask.getDeadlineHour()));
                view.minuteField.setText(String.valueOf(updatedTask.getDeadlineMinute()));
            }
        }
    }

    class DoneButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = view.todoList.getSelectedIndex();
            if (selectedIndex != -1) {
                TodoTask task = model.getTasks().get(selectedIndex);
                if (!task.isDone()) {
                    model.markTaskAsDone(selectedIndex);
                    view.setTasks(model.getTasks());
                    view.displayTaskTimeInfo(selectedIndex);

                TodoTask updatedTask = model.getTasks().get(selectedIndex);
                view.yearField.setText(String.valueOf(updatedTask.getDeadlineYear()));
                view.monthField.setText(String.valueOf(updatedTask.getDeadlineMonth()));
                view.dayField.setText(String.valueOf(updatedTask.getDeadlineDay()));
                view.hourField.setText(String.valueOf(updatedTask.getDeadlineHour()));
                view.minuteField.setText(String.valueOf(updatedTask.getDeadlineMinute()));
                }
            }
        }
    }
}
