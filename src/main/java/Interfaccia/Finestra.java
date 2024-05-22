package Interfaccia;

import javax.swing.*;

public class Finestra
{

    JFrame frame;
    JButton button;
    public Finestra()
    {
        frame = new JFrame("KENKEN");
        frame.setSize(500, 500);
        frame.setResizable(false);

        button = new JButton("AVVIA");
        button.setBounds(200, 210, 100, 40);

        frame.add(button);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
