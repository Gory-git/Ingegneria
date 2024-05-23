package Interfaccia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Finestra
{

    JFrame frame;
    public Finestra()
    {
        frame = new JFrame("KENKEN");
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestraIniziale();
    }

    private void finestraIniziale()
    {
        JPanel panelIniziale = new JPanel();
        panelIniziale.setBounds(50, 50, 400, 400);

        JButton nuovoGioco = new JButton("Nuovo Gioco");
        nuovoGioco.setBounds(200, 180, 120, 40);
        nuovoGioco.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                finestraNuovoGioco();
            }
        });

        JButton caricaGioco = new JButton("Carica Gioco");
        caricaGioco.setBounds(200, 240, 120, 40);
        caricaGioco.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                finestraNuovoGioco();
            }
        });

        frame.add(panelIniziale);
        panelIniziale.add(nuovoGioco);
        panelIniziale.add(caricaGioco);

        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void finestraNuovoGioco()
    {
        //frame.setVisible(false);
        frame.removeAll();

        JPanel panelNuovoGioco = new JPanel();
        Button avviaGioco = new Button("Avvia!");
        avviaGioco.setBounds(350, 400, 120, 40);
        panelNuovoGioco.add(avviaGioco);
        panelNuovoGioco.setVisible(true);
        //frame.setLayout(null);
        //frame.setVisible(true);

    }
}
