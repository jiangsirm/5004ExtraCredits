package com.example.extracredits;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * TaskLog creates an Array list which is used to store multiple Tasks.
 */
public class TaskLog {
    ArrayList<Task> log;
    private String header;

    /**
     * @author Yuchen Jiang, Marianny De Leon and Alex Burns
     * <p>
     * Creates a new TaskLog object containing an Arraylist.
     */
    TaskLog() {
        log = new ArrayList<>();
        try {
            File fileIn = new File(
                    "/Users/konsteintinj/Desktop/NEU/2023Summer/5004/extra credits/5004ExtraCredits/ExtraCredits/sampledata.csv");
            Scanner scan = new Scanner(fileIn);
            header = scan.nextLine();
            while (scan.hasNext()) {
                String line = scan.nextLine();
                Scanner thisLine = new Scanner(line).useDelimiter(",");

                String text = "";
                boolean comp = false;
                LocalDate dueDate = LocalDate.of(-1, 1, 1);
                Priority p = Priority.LOW;
                String cat = "";

                for (int i = 0; i < 6; i++) {
                    if (thisLine.hasNext()) {
                        String cur = thisLine.next();
//                        System.out.println(cur);
                        switch (i) {
                            case 1:
                                text = cur.replace('�', ',');
                                break;
                            case 3:
                                if (!cur.isEmpty()) {
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/dd/MM");
                                    dueDate = LocalDate.parse(cur, formatter);
                                }
                                break;
                            case 2:
                                if (!cur.isEmpty()) {
                                    comp = Boolean.parseBoolean(cur);
                                }
                                break;
                            case 4:
                                if (!cur.isEmpty()) {
                                    p = Priority.valueOf(cur);
                                }
                                break;
                            case 5:
                                if (!cur.isEmpty()) {
                                    cat = cur.replace('�', ',');
                                }
                        }
                    }
                }
                log.add(new Task(text, dueDate, comp, p, cat));
            }
            scan.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * constructor with a pathway
     *
     * @param path string for the directory path of the CSV file to be read from.
     */
    TaskLog(String path) {
        log = new ArrayList<>();
        try {
            File fileIn = new File(path);
            Scanner scan = new Scanner(fileIn);
            header = scan.nextLine();
            while (scan.hasNext()) {
                String line = scan.nextLine();
                Scanner thisLine = new Scanner(line).useDelimiter(",");

                String text = "";
                boolean comp = false;
                LocalDate dueDate = LocalDate.of(-1, 1, 1);
                Priority p = Priority.LOW;
                String cat = "";

                for (int i = 0; i < 6; i++) {
                    if (thisLine.hasNext()) {
                        String cur = thisLine.next();
//                        System.out.println(cur);
                        switch (i) {
                            case 1:
                                text = cur.replace('�', ',');
                                break;
                            case 3:
                                if (!cur.isEmpty()) {
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/dd/MM");
                                    dueDate = LocalDate.parse(cur, formatter);
                                }
                                break;
                            case 2:
                                if (!cur.isEmpty()) {
                                    comp = Boolean.parseBoolean(cur);
                                }
                                break;
                            case 4:
                                if (!cur.isEmpty()) {
                                    p = Priority.valueOf(cur);
                                }
                                break;
                            case 5:
                                if (!cur.isEmpty()) {
                                    cat = cur.replace('�', ',');
                                }
                        }
                    }
                }
                log.add(new Task(text, dueDate, comp, p, cat));
            }
            scan.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows the contents of all the Tasks contained in the TaskLog.
     */
    public void showLog() {
        log.stream().map(t -> "ID: " + (log.indexOf(t) + 1) + "\n" + t).forEach(System.out::println);
    }

    /**
     * Adds a new task to the TaskLog list.
     *
     * @param description a string description current Task to be added into log.
     */
    public void addTask(String description) {
        log.add(new Task(description));
    }

    /**
     * Adds a new task to the TaskLog List without setting anything
     * <p>for the minimal requirement of the Task class, this would still set description to a string.
     *
     * @param t the Task object to be added
     */
    public void addTask(Task t) {
        log.add(t);
    }

    /**
     * Completes a Task in the Task log. If a task is already complete will throw an exception.
     *
     * @param ID id of a Task
     */
    public void completeTask(int ID) throws IllegalArgumentException {
        int idx = ID;

        try {
            Task noUse = log.get(idx);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        if (log.get(idx).isCompleted()) {
            throw new IllegalArgumentException("Given task is already completed!");
        }

        log.get(idx).complete();
        writeToFile();
        System.out.println("Complete Task " + ID + "\n" + log.get(idx));
    }

    /**
     * take a task object and display all information.
     *
     * @param firstTask the task to be displayed
     */
    public void displayTask(Task firstTask) {
        System.out.println("Task Description:" + firstTask.getText());
        System.out.println("Task ID:" + (log.indexOf(firstTask) + 1));
        System.out.println("Task Category:" + firstTask.getCategory());
        String date = "";
        if (firstTask.getDue().getYear() != -1) {
            date = firstTask.getDue().toString();
        }
        System.out.println("Task Due Date:" + date);
        System.out.println("Task Priority Status:" + firstTask.getPriority());
        System.out.println("Task Completion Status:" + firstTask.isCompleted());
    }

    /**
     * display all tasks
     */
    public void displayAllTasks() {
        for (Task task : log) {
            displayTask(task);
        }
    }

    /**
     * display incomplete tasks
     */
    public void displayIncompleteTasks() {
        for (Task task : log) {
            if (!task.isCompleted()) {
                displayTask(task);
            }
        }
    }

    /**
     * display tasks of a given category
     *
     * @param category the string to specify a category
     * @return the string for that category
     */
    public String displayByCategory(String category) {
        for (Task task : log) {
            if (task.getCategory().equals(category)) {
                displayTask(task);
            }
        }
        return category;
    }

    /**
     * display by the date of each tasks
     */
    public void displayByDate() {
        Comparator<Task> dueComparator = new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                if (task1.getDue() == null && task2.getDue() == null) {
                    return 0;
                } else if (task1.getDue() == null) {
                    return 1;
                } else if (task2.getDue() == null) {
                    return -1;
                }
                return task1.getDue().compareTo(task2.getDue());
            }
        };
        List<Task> tmp = new ArrayList<>(log);
        Collections.sort(tmp, dueComparator);
        for (Task task : tmp) {
            displayTask(task);
        }
    }

    /**
     * display given the priority of the tasks
     */
    public void displayByPriority() {
        Comparator<Task> priorityComparator = new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return task1.getPriority().compareTo(task2.getPriority()); //add .value
            }
        };
        List<Task> tmp = new ArrayList<>(log);
        Comparator<Task> reverse = Collections.reverseOrder(priorityComparator);
        Collections.sort(tmp, reverse);
        for (Task task : tmp) {
            displayTask(task);
        }
    }

    /**
     * write the current ArrayList to the CSV file
     */
    public void writeToFile() {
        try {
            File fileOut = new File("/Users/konsteintinj/Desktop/NEU/2023Summer/5004/extra credits/5004ExtraCredits/ExtraCredits/sampledata.csv");
            FileWriter w = new FileWriter(fileOut);
            w.write(header);
            for (Task t : log) {
                String toWrite = "\n" + (log.indexOf(t) + 1) + "," + t.toCSVString();
                w.write(toWrite);
            }
            w.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}