package model;

import java.util.ArrayList;
import java.util.List;

public class TodoListModel {
    private List<TodoTask> tasks;

    public TodoListModel() {
        tasks = new ArrayList<>();
    }

    public void addTask(String description) {
        TodoTask newTask = new TodoTask(description, false);
        tasks.add(newTask);
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public void editTask(int index, String newDescription) {
        if (index >= 0 && index < tasks.size()) {
            TodoTask task = tasks.get(index);
            task.setDescription(newDescription);
        }
    }

    public void markTaskAsDone(int index) {
        if (index >= 0 && index < tasks.size()) {
            TodoTask task = tasks.get(index);
            task.setDone(true);
        }
    }

    public List<TodoTask> getTasks() {
        return tasks;
    }

    public void addTask(String description, int year, int month, int day, int hour, int minute) {
        TodoTask newTask = new TodoTask(description, false, year, month, day, hour, minute);
        tasks.add(newTask);
    }

    public static class TodoTask {
        private String description;
        private boolean done;
        private int deadlineYear;
        private int deadlineMonth;
        private int deadlineDay;
        private int deadlineHour;
        private int deadlineMinute;

        public TodoTask(String description, boolean done) {
            this(description, done, 0, 0, 0, 0, 0);
        }

        public TodoTask(String description, boolean done, int year, int month, int day, int hour, int minute) {
            this.description = description;
            this.done = done;
            this.deadlineYear = year;
            this.deadlineMonth = month;
            this.deadlineDay = day;
            this.deadlineHour = hour;
            this.deadlineMinute = minute;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        public int getDeadlineYear() {
            return deadlineYear;
        }

        public void setDeadlineYear(int deadlineYear) {
            this.deadlineYear = deadlineYear;
        }

        public int getDeadlineMonth() {
            return deadlineMonth;
        }

        public void setDeadlineMonth(int deadlineMonth) {
            this.deadlineMonth = deadlineMonth;
        }

        public int getDeadlineDay() {
            return deadlineDay;
        }

        public void setDeadlineDay(int deadlineDay) {
            this.deadlineDay = deadlineDay;
        }

        public int getDeadlineHour() {
            return deadlineHour;
        }

        public void setDeadlineHour(int deadlineHour) {
            this.deadlineHour = deadlineHour;
        }

        public int getDeadlineMinute() {
            return deadlineMinute;
        }

        public void setDeadlineMinute(int deadlineMinute) {
            this.deadlineMinute = deadlineMinute;
        }
    }
}
