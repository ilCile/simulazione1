package a06.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;

public class GUI extends JFrame {
    
    private final Map<Pair<Integer,Integer>, JButton> cells = new HashMap<>();
    private final Logics logic;
    
    public GUI(int size) {
        logic = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        JButton go = new JButton("Fire");
        main.add(BorderLayout.SOUTH, go);
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(new Pair<>(i,j), jb);
                jb.setEnabled(false);
                panel.add(jb);
            }
        }

        var start = logic.start();
        start.forEach((k, v) -> {
            this.cells.get(k).setText(v.toString());
        });

        go.addActionListener(e -> {
            var fire = logic.fire();
            fire.forEach((k, v) -> {
                String text;
                if (v < 0) {
                    text = "";
                } else {
                    text = v.toString();
                }
                this.cells.get(k).setText(text);
            });
            if (logic.isDone()) {
                go.setEnabled(false);
            }
        });

        this.setVisible(true);
    }    
}
