package Interfaccia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
        panelIniziale.setLayout(new FlowLayout());
        panelIniziale.setBackground(Color.LIGHT_GRAY);

        JButton nuovoGioco = new JButton("Nuovo Gioco");
        //nuovoGioco.setBounds(150, 200, 120, 40);
        nuovoGioco.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.remove(panelIniziale);
                finestraNuovoGioco();
            }
        });

        JButton caricaGioco = new JButton("Carica Gioco");
        //caricaGioco.setBounds(200, 200, 120, 40);
        caricaGioco.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.remove(panelIniziale);
                finestraNuovoGioco();
            }
        });

        panelIniziale.add(nuovoGioco);
        panelIniziale.add(caricaGioco);

        frame.add(panelIniziale);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void finestraNuovoGioco()
    {
        //frame.setVisible(false);

        JPanel panelNuovoGioco = new JPanel();
        panelNuovoGioco.setLayout(new FlowLayout());
        panelNuovoGioco.setBackground(Color.LIGHT_GRAY);
        JButton avviaGioco = new JButton("Avvia!");
        avviaGioco.setBounds(350, 400, 120, 40);
        panelNuovoGioco.add(avviaGioco);


        JSlider sliderDimensdione = new JSlider(JSlider.HORIZONTAL, 3, 9, 5);
        sliderDimensdione.setMajorTickSpacing(1);
        sliderDimensdione.setPaintTicks(true);
        sliderDimensdione.setPaintLabels(true);

        panelNuovoGioco.add(sliderDimensdione);

        SpinnerModel valori = new SpinnerNumberModel(1, 0, 100, 1);
        JSpinner spinnerSoluzioni = new JSpinner(valori);

        // TODO estrapolare i dati dagli oggetti sopra e avviare il gioco.

        panelNuovoGioco.add(spinnerSoluzioni);

        frame.add(panelNuovoGioco);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
