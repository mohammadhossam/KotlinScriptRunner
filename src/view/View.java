package view;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private JLabel errorPane;
    private JLabel running, error;
    private JTextArea scriptArea;
    private JLabel output;
    private JButton run;

    public JLabel getRunning() {
        return running;
    }

    public JLabel getError() {
        return error;
    }

    public JTextArea getScriptArea() {
        return scriptArea;
    }

    public JLabel getOutput() {
        return output;
    }

    public JButton getRun() {
        return run;
    }

    public JLabel getErrorPane() {
        return errorPane;
    }

    public View(){
        super();
        setSize(850, 800);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Kotlin Script Runner");
        setLayout(null);

        scriptArea = new JTextArea();
        JScrollPane scriptAreaScroll = new JScrollPane(scriptArea);
        Insets insets = scriptAreaScroll.getInsets();
        Dimension size = scriptAreaScroll.getPreferredSize();
        scriptArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        scriptAreaScroll.setBounds(insets.left + 20, insets.top + 60, size.width +600, size.height + 400);
        add(scriptAreaScroll);

        JLabel editor = new JLabel("Editor Pane:");
        insets = editor.getInsets();
        size = editor.getPreferredSize();
        editor.setBounds(insets.left + 20, insets.top + 40, size.width, size.height);
        add(editor);

        JLabel outTmp = new JLabel();
        insets = outTmp.getInsets();
        outTmp.setText("Output Pane:");
        size = outTmp.getPreferredSize();
        outTmp.setBounds(insets.left + 20, insets.top + 500, size.width, size.height );
        add(outTmp);

        output = new JLabel();
        JScrollPane outputScroll = new JScrollPane(output);
        insets = outputScroll.getInsets();
        size = outputScroll.getPreferredSize();
        output.setBackground(Color.WHITE);
        output.setOpaque(true);
        output.setVerticalAlignment(JLabel.TOP);
        output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        outputScroll.setBounds(insets.left + 20, insets.top + 520, size.width + 600, size.height + 100);
        add(outputScroll);

        JLabel errorArea = new JLabel("Errors Pane:");
        insets = errorArea.getInsets();
        size = errorArea.getPreferredSize();
        errorArea.setBounds(insets.left + 20, insets.top + 630, size.width, size.height);
        add(errorArea);

        errorPane = new JLabel();
        JScrollPane errorScroll = new JScrollPane(errorPane);
        insets = errorScroll.getInsets();
        size = errorScroll.getPreferredSize();
        errorPane.setBackground(Color.white);
        errorPane.setOpaque(true);
        errorPane.setVerticalAlignment(JLabel.TOP);
        errorPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        errorScroll.setBounds(insets.left + 20, insets.top + 650, size.width + 600, size.height + 100);
        add(errorScroll);

        run =  new JButton("Run");
        insets = run.getInsets();
        size = run.getPreferredSize();
        run.setBounds(insets.left + 670, insets.top + 80, size.width + 30, size.height);
        add(run);

        running = new JLabel("Running...");
        insets = running.getInsets();
        size = running.getPreferredSize();
        running.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        running.setForeground(Color.blue);
        running.setBounds(insets.left + 700, insets.top + 200, size.width+20, size.height+20);
        add(running);
        running.setVisible(false);

        error = new JLabel("<html>Error! Exit code of<br/>last run is non-zero.</html>");
        insets = error.getInsets();
        size = error.getPreferredSize();
        error.setForeground(Color.red);
        error.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
        error.setBounds(insets.left + 680, insets.top + 630, size.width, size.height + 100);
        add(error);
        error.setVisible(false);


        setVisible(true);
    }
}
