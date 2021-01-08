package test;

import java.util.ArrayList;
import java.util.HashMap;

import test.Commands.Command;
import test.Commands.DefaultIO;

public class CLI {

    HashMap<String, Command> commands;
    DefaultIO dio;
    Commands c;

    public CLI(DefaultIO dio) {
        this.dio = dio;
        c = new Commands(dio);
//		commands=new ArrayList<>();
        this.commands = new HashMap<>();

        commands.put("0", c.new MenuCommand());
        commands.put("1", c.new UploadFileWrapper());
        commands.put("2", c.new ThreshHoldCommand());
        commands.put("3", c.new RunDetectCommand());
        commands.put("4", c.new DisplayReports());
        //         example: commands.add(c.new ExampleCommand());
        // implement
    }

    public void start() {
        commands.get("0").execute();
        String value = dio.readText();
        while (commands.containsKey(value)) {
            if(value.equals("6"))
                break;
            commands.get(value).execute();
            commands.get("0").execute();
            value = dio.readText();
        }
        //		commands.get("1");
        //		dio.write("OMER");
        // implement
    }
}
