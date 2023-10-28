import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.nio.file.*;
import java.nio.file.Files;
import java.io.*;
import java.io.FileReader;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Terminal {
    Main parser;
    List<String> history;
    public Terminal() {
        parser = new Main();
        history = new ArrayList<>();

    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            history.add(input);
            if (!parser.parse(input)) {
                System.out.println("Invalid command");
                continue;
            }
            switch (parser.getCommandName()) {
                case "clear":
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    break;

                case "echo":
                    System.out.println(String.join(" ", parser.getArgs()));
                    break;

                case "uname":
                    System.out.println(System.getProperty("os.name"));
                    break;
                case "pwd":
                    System.out.println(new File("").getAbsolutePath());
                    break;
                case "cd":                       //testing
                    if (parser.getArgs().length == 0) {
                        System.out.println("Usage: cd <directory>");
                        break;
                    }
                    String path = parser.getArgs()[0];
                    File directory = new File(path);
                    if (!directory.exists()) {
                        System.out.println("Directory not found");
                        break;
                    }
                    if (!directory.isDirectory()) {
                        System.out.println(path + " is not a directory");
                        break;
                    }
                    System.setProperty("user.dir", directory.getAbsolutePath());
                    break;
                case "ls":
                    File currentDirectory = new File(System.getProperty("user.dir"));
                    File[] files = currentDirectory.listFiles();
                    for (File file : files) {
                        System.out.println(file.getName());
                    }
                    break;
                case "history":
                    for (int i = 0; i < history.size(); i++) {
                        System.out.printf("%d %s\n", i + 1, history.get(i));
                    }
                    break;
                case "mkdir":
                    if (parser.getArgs().length == 0) {
                        System.out.println("Usage: mkdir <directory>");
                        break;
                    }
                    String folder = parser.getArgs()[0];
                    File dir =  new File(folder);
                    dir.mkdir();
                    break;
                case "touch":
                    if (parser.getArgs().length == 0) {
                        System.out.println("Usage: mkdir <directory>");
                        break;
                    }
                    String file = parser.getArgs()[0];
                    File newFile = new File(file);
                    try {
                        newFile.createNewFile();
                    }catch (Exception e){
                        System.out.print("Could not create file");
                    }
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Command not found");
            }
        }
    }

    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        terminal.run();
    }
}
