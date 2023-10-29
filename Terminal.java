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
    public String cat(){
        String data = "";
        for (int i = 0; i < parser.getArgsSize(); i++) {
            try {
                File f = new File(parser.getArgs()[i]);
                Scanner sc = new Scanner(f);
                while (sc.hasNextLine()) {
                    data = data.concat(sc.nextLine()).concat("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return data;
    }
    public String cp(){
        if(parser.getArgs().length < 2){
            return "Usage: cp <source_file> <destination_file>";
        }
        File file1 = new File(parser.getArgs()[0]);
        File file2 = new File(parser.getArgs()[1]);
        try (InputStream in = new FileInputStream(file1);
            OutputStream out = new FileOutputStream(file2)){
            byte[] buffer = new byte[4096];
            int byteRead;
            while((byteRead = in.read(buffer)) != -1){
                out.write(buffer, 0, byteRead);
            }
            return "";
        }
        catch (Exception e){
            return "An error happened while copying files";

        }

    }
    public String[] wc() {
        String[] output = new String[4];
        try {

            File myObj = new File(parser.getArgs()[0]);
            Scanner myReader = new Scanner(myObj);
            int LinesNum = 0;
            int CharNum = 0;
            int WordsNum = 0;
            while (myReader.hasNextLine()) {
                String s = myReader.nextLine();

                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        WordsNum++;
                    }
                }
                if (s.length() != 0) {
                    WordsNum += 1;
                }
                String str = s.replaceAll("\\s", "");
                CharNum += s.length();
                LinesNum++;
            }
            CharNum += LinesNum;
            output[0] = Integer.toString(LinesNum);
            output[1] = Integer.toString(WordsNum);
            output[2] = Integer.toString(CharNum);
            output[3] = myObj.getName();
//             System.out.println("number of lines : " + LinesNum + "\n");
//             System.out.println("number of words : " + WordsNum + "\n");
//             System.out.println("number of characters : " + CharNum + "\n");
//             System.out.println("File name : " + myObj.getName() + "\n");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return output;
    }
    public String rmdir(){

        if (parser.getArgs().length == 0) {
            return "Usage: rmdir <directory>";

        }
        String deleted = parser.getArgs()[0];
        File DF = new File(deleted);
        if (!DF.delete()) {
            return "Could not delete the current directory as it is not empty";
        }
        return "";
    }

    public String touch(){
        if (parser.getArgs().length == 0) {
            return "Usage: touch <file>";
        }
        String file = parser.getArgs()[0];
        File newFile = new File(file);
        try {
            newFile.createNewFile();
        } catch (Exception e) {
            return "Could not create file";
        }
        return "";
    }
    public void clear() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.flush();
    }

    public String echo() {
        String str = String.join(" ", parser.getArgs());
        // System.out.println(str);
        return str;
    }

    public String uname() {
        String output = System.getProperty("os.name");
        // System.out.println(System.getProperty("os.name"));
        return output;
    }

    public String pwd() {
        String output = new File("").getAbsolutePath();
        System.out.println(new File("").getAbsolutePath());
        return output;
    }
    public String mkdir(){
        if (parser.getArgs().length == 0) {
            return "Usage: mkdir <directory>";

        }
        for (int i = 0; i < parser.getArgsSize(); i++) {
            String folder = parser.getArgs()[i];
            File dir = new File(folder);
            dir.mkdir();
        }
        return "";
    }
    public String history(){
        String output = "";
        for (int i = 0; i < history.size() - 1; i++) {
            output += Integer.toString(i+1) + " " + history.get(i) + "\n";
        }
        return output;
    }
    public String ls(){
        String data = "";
        File currentDirectory = new File(System.getProperty("user.dir"));
        File[] files = currentDirectory.listFiles();
        for (File file : files) {
            data += file.getName() + "\n";
        }
        return data;
    }
    public String cd(){
        if (parser.getArgs().length == 0) {
            return "Usage: cd <directory>";

        }
        String path = parser.getArgs()[0];
        File directory = new File(path);
        if (!directory.exists()) {
            return "Directory not found";

        }
        if (!directory.isDirectory()) {
            return path + " is not a directory";

        }
        System.setProperty("user.dir", directory.getAbsolutePath());
        return "";
    }
    public String rm(){
        if(parser.getArgs().length != 1){
            return "usage: rm <file>";
        }
        File file = new File(parser.getArgs()[0]);
        if(!file.isFile()){
            return "Not a file";
        }
        if(!file.delete()){
            return "failed to delete";
        }
        return "";
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(new File("").getAbsolutePath() + "$ ");
            String input = scanner.nextLine();
            history.add(input);
            if (!parser.parse(input)) {
                System.out.println("Invalid command");
                continue;
            }
            switch (parser.getCommandName()) {
                case "clear":
                    clear();
                    break;
                case "echo":
                    System.out.println(echo());
                    break;
                case "cp":
                    System.out.println(cp());
                    break;
                case "uname":
                    System.out.println(uname());
                    break;
                case "pwd":
                    System.out.println(pwd());
                    break;
                case "cd": // testing
                    System.out.println(cd());
                    break;
                case "ls":
                    System.out.println(ls());
                    break;
                case "rm":
                    System.out.println(rm());
                    break;
                case "history":
                    System.out.println(history());
                    break;
                case "mkdir":
                    System.out.println(mkdir());
                    break;
                case "touch":
                    System.out.println(touch());
                    break;
                case "rmdir":
                    System.out.println(rmdir());
                    break;
                case "cat":
                    System.out.print(cat());
                    break;
                case "wc":
                    String[] wcResult = wc();
                    System.out.println("number of lines : " + wcResult[0] + "\n");
                    System.out.println("number of words : " + wcResult[1] + "\n");
                    System.out.println("number of characters : " + wcResult[2] + "\n");
                    System.out.println("File name : " + wcResult[3] + "\n");
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
