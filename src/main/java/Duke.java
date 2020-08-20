import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        // Initialise booleans and scanners
        boolean quitProgram = false;
        Scanner inputScanner = new Scanner(System.in);

        PrintStream out;
        try {
            out = new PrintStream(System.out, true, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            out = new PrintStream(System.out, true);
        }


        // Initialise Task List
        TaskList taskList = new TaskList();

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        out.println("Hello, my name is\n" + logo);
        out.println("What can I do for you?");

        while (!quitProgram) {
            // blocks program until input is received
            String newInput = inputScanner.nextLine();
            // then prints the input
            
            //trim the input to remove leading and ending whitespace
            newInput = newInput.trim();

            try {
                if (newInput.equals("bye")) {
                    quitProgram = true;
                } else if (newInput.equals("list")) {
                    out.println(taskList.getAllTasksAsString());
                } else if (newInput.indexOf("done") == 0) {
                    if (newInput.length() == 4) {
                        throw new DukeException("OOPS!!! Please specify a task to mark as complete.");
                    } else {
                        int taskIndex = Integer.parseInt(newInput.substring(5));    // this is not corrected for 0 index
                        Task completedTask = taskList.getTask(taskIndex);
                        completedTask.markAsDone();

                        out.println("Nice! I've marked this task as done: ");
                        out.println(completedTask);
                    }
                } else if (newInput.indexOf("delete") == 0) {
                    if (newInput.length() == 6) {
                        throw new DukeException(" OOPS!!! Please specify a task to delete.");
                    } else {
                        try {
                            int taskIndex = Integer.parseInt(newInput.substring(7));
                            
                            Task removedTask = taskList.getTask(taskIndex);
                            taskList.deleteTask(taskIndex);

                            out.println("Noted. I've removed this task: ");
                            out.println(removedTask);
                            out.println("Now you have " + taskList.numTasks() + " tasks in the list.");
                        } catch (NumberFormatException e) {
                            throw new DukeException(" OOPS!!! Please specify a valid task to delete.");
                        }
                    }
                } else {
                    // use indexOf() method to find substring
                    Task newTask = new Task("");    // this is to make the compiler happy

                    if (newInput.indexOf("event") == 0) {
                        if (newInput.length() <= 5) {
                            throw new DukeException(" OOPS!!! The description of an event cannot be empty.");
                        } else {
                            int indexOfAtKeyword = newInput.indexOf(" /at ");
                            
                            if (indexOfAtKeyword == -1) {
                                throw new DukeException(" OOPS!!! Please specify the event time.");
                            } else {
                                String eventDesc = newInput.substring(6, indexOfAtKeyword);
                                String eventTime = newInput.substring(indexOfAtKeyword + 5);

                                newTask = new Event(eventDesc, eventTime);
                                taskList.addTask(newTask);
                            }
                        }
                    } else if (newInput.indexOf("deadline") == 0) {
                        if (newInput.length() <= 8) {
                            throw new DukeException(" OOPS!!! The description of a deadline cannot be empty.");
                        } else {
                            int indexOfByKeyword = newInput.indexOf(" /by ");
                            
                            if (indexOfByKeyword == -1) {
                                throw new DukeException(" OOPS!!! Please specify the deadline time.");
                            } else {
                                String deadlineDesc = newInput.substring(9, indexOfByKeyword);
                                String deadlineTime = newInput.substring(indexOfByKeyword + 5);

                                newTask = new Deadline(deadlineDesc, deadlineTime);
                                taskList.addTask(newTask);
                            }
                        }
                    } else if (newInput.indexOf("todo") == 0){
                        if (newInput.length() <= 4 || newInput.substring(5).trim().length() == 0) {
                            throw new DukeException(" OOPS!!! The description of a todo cannot be empty.");
                        } else {
                            newTask = new Todo(newInput.substring(5));
                            taskList.addTask(newTask);
                        }
                    } else {
                        throw new DukeException(" OOPS!!! I'm sorry, but I don't know what that means :-(");
                    }

                    out.println("Got it. I've added this task: ");
                    out.println(newTask);
                    out.println("Now you have " + taskList.numTasks() + " tasks in the list.");
                }
            } catch (DukeException e) {
                out.println(e.getMessage());
            }

        }

        // quit
        inputScanner.close();
        out.println("See you space cowboy!");
    }
}
