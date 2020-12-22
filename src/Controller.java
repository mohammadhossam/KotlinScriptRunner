import model.Runner;
import view.View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller implements DocumentListener, ActionListener {

    private View view;
    private Runner runner;
    private boolean zeroState, running;

    private final String[] keywords = new String[]{"fun", "if", "else", "true", "false", "while", "for", "break", "continue", "in"};
    private final char[] punc = new char[]{'(', ')', '{', '}', ',', '.', ',', ';', '/', '\\', '&', '|', '?', '!',
            '@', '#', '$', '%', '^', '*', '-', '+', '=', '[', ']', ' ', '\n', '\t'};

    public Controller() throws BadLocationException {
        view = new View();
        runner = new Runner();
        view.getRun().addActionListener(this);
        view.getScriptArea().getDocument().addDocumentListener(this);
    }

    public static void main(String[] args) throws InterruptedException, BadLocationException {
        Controller controller = new Controller();
    }

    public void setZeroState(boolean zeroState) {
        this.zeroState = zeroState;
    }

    private void startThread() throws InterruptedException {
        SwingWorker sw1 = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                runner.setInput(view.getScriptArea().getText());
                try {
                    runner.runScript();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                String line = "";
                String print = line;
                while (true) {
                    print = line;
                    try {
                        if ((line = runner.getOutputBuffer().readLine()) == null) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (print.length() > 0) {
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        publish("0" + print);
                    }
                }
                if (!print.equals("0")) {
                    setZeroState(true);
                }


                line = "";
                while (true) {
                    try {
                        if ((line = runner.getErrorBuffer().readLine()) == null) {
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publish("1" + line);
                }
                return null;
            }

            @Override
            protected void process(List chunks) {
                view.getRunning().setVisible(true);
                String line = (String) chunks.get(chunks.size() - 1);
                if (line.charAt(0) == '0') {
                    int length = view.getOutput().getText().length();
                    view.getOutput().setText((length == 0 ? "<html>" : "") +
                            view.getOutput().getText().substring(0, length - (length == 0 ? 0 : 7)) +
                            (length == 0 ? "" : "<br/>") + line.substring(1) + "</html>");
                } else {
                    int length = view.getErrorPane().getText().length();
                    view.getErrorPane().setText((length == 0 ? "<html>" : "") +
                            view.getErrorPane().getText().substring(0, length - (length == 0 ? 0 : 7)) +
                            (length == 0 ? "" : "<br/>") + line.substring(1) + "</html>");
                }

            }

            @Override
            protected void done() {
                view.getRunning().setVisible(false);
                view.getError().setVisible(zeroState);
            }
        };

        sw1.execute();

    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        highlight();
    }

    private ArrayList<int[]> findKeywords(String text) {
        ArrayList<int[]> indices = new ArrayList<>();
        for (String keyword : keywords) {
            for (int i = 0, j = i + keyword.length(); j <= text.length(); i++, j++) {
                if (text.substring(i, j).equals(keyword) && (j == text.length() || isPunc(text.charAt(j))) && (i == 0 || isPunc(text.charAt(i - 1)))) {
                    indices.add(new int[]{i, j});
                }
            }
        }

        return indices;
    }

    private boolean isPunc(char c) {
        for (char x : punc) {
            if (c == x)
                return true;
        }
        return false;
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        highlight();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    private void highlight() {
        Highlighter highlighter = view.getScriptArea().getHighlighter();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
        ArrayList<int[]> indices = findKeywords(view.getScriptArea().getText());
        highlighter.removeAllHighlights();
        for (int[] index : indices) {
            try {
                highlighter.addHighlight(index[0], index[1], painter);
            } catch (BadLocationException badLocationException) {
                badLocationException.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.getOutput().setText("");
        try {
            startThread();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
