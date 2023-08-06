package com.example.extracredits;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Yuchen Jiang, Marianny De Leon and Alex Burns
 * <p>
 * Task is a simple class that contains five attributes text, completed, due, priority, and
 * category.
 */
public class Task {
    private String text;
    private boolean completed;
    private LocalDate due;
    private Priority priority;
    private String category;

    /**
     * Creates a new Task object with unique text description.
     *
     * @param txt description of the Task
     */
    Task(String txt) {
        text = txt;
        completed = false;
        due = LocalDate.of(-1, 1, 1);
        priority = Priority.LOW;
        category = "";
    }

    /**
     * Sets the text of a Task
     * 
     * @param text description of a task
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Creates a complete Task object with unique text, dueDate, priority, and category.
     *
     * @param txt     description of the Task
     * @param dueDate due date of the Task
     * @param p       priority level of the Task
     * @param cate    category of the Task
     */
    Task(String txt, LocalDate dueDate, boolean comp, Priority p, String cate) {
        text = txt.trim();
        completed = comp;
        due = dueDate;
        priority = p;
        category = cate.toLowerCase().trim();
    }

    /**
     * Method to complete a task by changing its status from false to true.
     */
    public void complete() {
        completed = true;
    }

    /**
     * Method that returns the status of a task.
     *
     * @return completed as true or false
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Returns the current priority level of a Task
     *
     * @return priority level of Task
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Returns the due date of a Task
     *
     * @return due date of the current Task
     */
    public LocalDate getDue() {
        return due;
    }

    /**
     * Returns the category of a Task
     *
     * @return category that a Task is in
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the text description of a Task
     *
     * @return description of a text
     */
    public String getText() {
        return text;
    }

    /**
     * setter for priority that takes only an enum Priority object
     *
     * @param priority Priority object to be set
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * setter for priority that takes an int
     *
     * @param i 1 for LOW, 2 for MEDIUM, 3 for HIGH
     * @throws IllegalArgumentException throw exception if input is not 1, 2 or 3.
     */
    public void setPriority(int i) throws IllegalArgumentException {
        Priority p = Priority.LOW;

        if (i < 1 || i > 3) {
            throw new IllegalArgumentException("Input can only be 1, 2, or 3.");
        }

        if (i == 2) {
            p = Priority.MEDIUM;
        } else if (i == 3) {
            p = Priority.HIGH;
        }

        priority = p;
    }

    /**
     * setter for category
     *
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * setter for complete status
     *
     * @param completed true if the task is completed
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * setter for due date that takes a LocalDate variable
     * <p>
     * another setter is dedicated directly take String.
     *
     * @param due a localDate Variable for due
     */
    public void setDue(LocalDate due) {
        this.due = due;
    }

    /**
     * setter for localDate that take a String
     * <p>
     * **Format MUST be yyyy/MM/dd***
     * <p>
     * ** MUST be ALL DIGITS!***
     *
     * @param date the String to be parsed
     */
    public void setDue(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate dueDate = LocalDate.parse(date, formatter);
        due = dueDate;
    }

    /**
     * Equals method that compares to Tasks against each other and will return true or false
     * depending on if they are equal to one another.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Task))
            return false;
        Task t = (Task) obj;
        return t.text.equals(this.text) && t.completed == this.completed
                && t.priority.equals(this.priority) && t.due.equals(this.due)
                && t.category.equals(this.category);
    }

    /**
     * Pretty prints the contents of a Task
     */
    @Override
    public String toString() {
        String date = "";
        if (due.getYear() != -1) {
            date = due.toString();
        }
        return "Description:\n" + text + "\nCompleted: " + completed + "\nDue Date: " + date
                + "\nPriority: " + priority + "\nCategory: " + category;
    }

    /**
     * the method that generates String for writing to CSV files.
     *
     * @return a String that is in the correct format.
     * <p>
     * For example:
     * <p>
     * 3,Organize Code Review with ASW� On-line� and Marketing teams for new version of website,false,2004/05/07,LOW,
     */
    public String toCSVString() {
        String date = "";
        if (due.getYear() != -1) {
            date = due.getYear() + "/" + String.format("%02d", due.getDayOfMonth()) + "/" + String.format("%02d", due.getMonthValue());
        }
        return text.replace(',', '�') + "," +
                completed + "," +
                date + "," +
                priority.toString() + "," +
                category.replace(',', '�');
    }
}
