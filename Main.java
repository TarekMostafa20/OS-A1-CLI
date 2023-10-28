import java.util.Scanner;
public class Main {
    String commandName;
    String[] args;

    public boolean parse(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length == 0) {
            return false;
        }
        commandName = tokens[0];
        args = new String[tokens.length - 1];
        for (int i = 1; i < tokens.length; i++) {
            args[i - 1] = tokens[i];
        }
        return true;
    }

    public String getCommandName() {

        return commandName;
    }

    public String[] getArgs() {

        return args;
    }
}


































