package Interfaccia;

import Gioco.Gioco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Finestra
{

    Gioco gioco = Gioco.INSTANCE;
    JFrame frame;
    int numeroSoluzioni, dimensioneGriglia;
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
        caricaGioco.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.remove(panelIniziale);
                gioco.carica();
                finestraGioco();
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

        JPanel panelNuovoGioco = new JPanel();
        panelNuovoGioco.setLayout(new FlowLayout());
        panelNuovoGioco.setBackground(Color.LIGHT_GRAY);
        JButton avviaGioco = new JButton("Avvia!");
        avviaGioco.setBounds(350, 400, 120, 40);
        panelNuovoGioco.add(avviaGioco);


        JSlider sliderDimensdione = new JSlider(JSlider.HORIZONTAL, 3, 9, 6);
        sliderDimensdione.setMajorTickSpacing(1);
        sliderDimensdione.setPaintTicks(true);
        sliderDimensdione.setPaintLabels(true);

        panelNuovoGioco.add(sliderDimensdione);

        SpinnerModel valori = new SpinnerNumberModel(0, 0, 100, 1);
        JSpinner spinnerSoluzioni = new JSpinner(valori);

        // TODO estrapolare i dati dagli oggetti sopra e avviare il gioco.

        panelNuovoGioco.add(spinnerSoluzioni);

        frame.add(panelNuovoGioco);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        avviaGioco.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.remove(panelNuovoGioco);
                numeroSoluzioni = (Integer) spinnerSoluzioni.getValue();
                dimensioneGriglia = sliderDimensdione.getValue();
                try
                {
                    gioco.avvia(numeroSoluzioni, dimensioneGriglia);
                } catch (CloneNotSupportedException ex)
                {
                    throw new RuntimeException(ex);
                } catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }
                frame.remove(panelNuovoGioco);
                finestraGioco();
            }
        });

    }

    private void finestraGioco()
    {
        JPanel panelGioco = new JPanel();
        panelGioco.setLayout(new BorderLayout());
        panelGioco.setBackground(Color.PINK);

        JPanel panelGriglia = new JPanel();
        panelGriglia.setSize(400, 400);
        panelGriglia.setLayout(new GridLayout(dimensioneGriglia, dimensioneGriglia));
        panelGioco.setBackground(Color.LIGHT_GRAY);


        for (int i = 0; i < dimensioneGriglia; i++)
        {
            for (int j = 0; j < dimensioneGriglia; j++)
            {
                panelGriglia.add(new JLabel(gioco.getSoluzioni().get(1).cella(i, j).getValore() + ""));
            }
        }

        panelGioco.add(panelGriglia, BorderLayout.CENTER);

        frame.add(panelGioco);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


}
