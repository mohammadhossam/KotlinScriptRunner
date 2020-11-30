package model;

import java.io.*;

public class Runner {
    private String input;
    private BufferedReader outputBuffer, errorBuffer;

    public BufferedReader getOutputBuffer() {
        return outputBuffer;
    }

    public BufferedReader getErrorBuffer() {
        return errorBuffer;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void runScript() throws IOException {
        File file = new File("Script.kts");
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        fw.write(input);
        fw.close();
        Process pr = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "kotlinc -script script.kts; echo $?"});
        outputBuffer = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        errorBuffer = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
    }
}
