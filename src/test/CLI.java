package test;

import java.util.ArrayList;

import test.Commands.Command;
import test.Commands.DefaultIO;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

public class CLI {

    ArrayList<Command> commands;
    DefaultIO dio;
    Commands c;

    public CLI(DefaultIO dio) {
        this.dio = dio;
        c = new Commands(dio);
        commands = new ArrayList<>();
        commands.add(c.new MenuCommand());
        commands.add(c.new UploadFileWrapper());
        commands.add(c.new ThreshHoldCommand());
        commands.add(c.new RunDetectCommand());
        commands.add(c.new DisplayReports());
        commands.add(c.new AnomaliesAnalayze());


        //        this.commands = new HashMap<>();
        //         example: commands.add(c.new ExampleCommand());
        // implement
    }

    public void start() {
        commands.get(0).execute();
        String value = dio.readText();
        int v = Integer.parseInt(value);
        while (1 <= v && v <= 6) {
            if (v == 6)
                break;
            commands.get(v).execute();
            commands.get(0).execute();
            v = Integer.parseInt(dio.readText());
        }

    }
}
