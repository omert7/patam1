package test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Commands {

    // Default IO interface
    public interface DefaultIO {
        public String readText();

        public void write(String text);

        public float readVal();

        public void write(float val);

        // you may add default methods here
    }

    // the default IO to be used in all commands
    DefaultIO dio;

    public Commands(DefaultIO dio) {
        this.dio = dio;
    }

    // you may add other helper classes here


    // the shared state of all commands
    private class SharedState {
        ;
        // implement here whatever you need
        String trainFile;
        String testFile;
        List<AnomalyReport> areport;
        float threashold = (float) 0.9;
    }

    private SharedState sharedState = new SharedState();


    // Command abstract class
    public abstract class Command {
        protected String description;

        public Command(String description) {
            this.description = description;
        }

        public abstract void execute();
    }

    // Command class for example:
    public class ExampleCommand extends Command {

        public ExampleCommand() {
            super("this is an example of command");
        }

        @Override
        public void execute() {
            dio.write(description);
        }
    }

    // implement here all other commands

    public class MenuCommand extends Command {

        public MenuCommand() {
            super("Welcome to the Anomaly Detection Server.\n" +
                    "Please choose an option:\n" +
                    "1. upload a time series csv file\n" +
                    "2. algorithm settings\n" +
                    "3. detect anomalies\n" +
                    "4. display results\n" +
                    "5. upload anomalies and analyze results\n" +
                    "6. exit\n");
        }

        @Override
        public void execute() {
            dio.write(description);
        }
    }

    public class UploadFileWrapper extends Command {
        UploadFileWrapper() {
            super("UploadFileWrapper");
        }

        @Override
        public void execute() {
            this.getFile("train");
            this.getFile("Test");
        }

        public void readAndWriteToCSV(String csvFile) {
            try {
                FileWriter fileWriter = new FileWriter(csvFile);
                String v = dio.readText();
                while (!v.equals("done")) {
                    fileWriter.write(v);
                    fileWriter.write("\n");
                    fileWriter.flush();
                    v = dio.readText();
                }
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }

        public void getFile(String file) {
            PreUploadFile preUploadFile = new PreUploadFile(file);
            String csvFile = "anomaly" + file + ".csv";
            preUploadFile.execute();
            this.readAndWriteToCSV(csvFile);
            PostUploadFile postUploadFile = new PostUploadFile();
            postUploadFile.execute();

        }
    }

    public class PreUploadFile extends Command {

        public PreUploadFile(String fileMode) {
            super("please upload your local " + fileMode + " CSV file.\n");
        }

        @Override
        public void execute() {
            dio.write(description);
        }

    }

    public class PostUploadFile extends Command {

        public PostUploadFile() {
            super("Upload complete.\n");
        }

        @Override
        public void execute() {
            dio.write(description);
        }

    }

    public class ThreshHoldCommand extends Command {
        public ThreshHoldCommand() {
            super("Threshold\n");
        }

        @Override
        public void execute() {
            String firstMessage = "The current correlation threshold is 0.9\n" +
                    "Type a new threshold\n";
            dio.write(firstMessage);
            String v = dio.readText();
            while(Float.parseFloat(v)>1 || Float.parseFloat(v) <0){
                dio.write("please choose a value between 0 and 1.");
                v = dio.readText();
            }
            sharedState.threashold = Float.parseFloat(v);
        }
    }

    public class RunDetectCommand extends Command{
        public RunDetectCommand() {
            super("Detection.\n");
        }

        @Override
        public void execute() {
            SimpleAnomalyDetector sad = new SimpleAnomalyDetector();
            sad.learnNormal(new TimeSeries("anomalyTrain.csv"));
            sharedState.areport =  sad.detect(new TimeSeries("anomalyTest.csv"));
            dio.write("anomaly detection complete.\n");
        }

    }

    public class DisplayReports extends Command {
        DisplayReports() {
            super("Reports Displays");
        }

        @Override
        public void execute() {
            for(AnomalyReport ar: sharedState.areport){
                String line = ar.timeStep + "\t" + ar.description;
                dio.write(line + '\n');
            }
            dio.write("done.\n");
        }
    }

    public class AnomaliesAnalayze extends Command{
        AnomaliesAnalayze(){
            super("AnomaliesAnalayze");
        }

        @Override
        public void execute() {

        }
    }

}
