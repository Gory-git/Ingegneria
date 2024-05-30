package Interfaccia;

import Gioco.Gioco;
import Gioco.mediator.ConcreteMediator;
import Gioco.mediator.Mediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Finestra // TODO
{
    Mediator mediator = new ConcreteMediator();
    JFrame frame;
    int numeroSoluzioni, dimensioneGriglia;
    public Finestra()
    {
        frame = new JFrame("KENKEN");
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestraIniziale();
        /* FIXME devo sistemare le prossime righe.
        numeroSoluzioni = 0;
        dimensioneGriglia = 3;
        mediator.avvia(numeroSoluzioni, dimensioneGriglia);
        finestraGioco();
        // TODO fino a qua bro
        */
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
                mediator.carica();
                numeroSoluzioni = mediator.soluzioni().size();
                dimensioneGriglia = mediator.soluzioni().get(0).dimensione();
                finestraGriglia();
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


        JSlider sliderDimensione = new JSlider(JSlider.HORIZONTAL, 3, 9, 6);
        sliderDimensione.setMajorTickSpacing(1);
        sliderDimensione.setPaintTicks(true);
        sliderDimensione.setPaintLabels(true);

        panelNuovoGioco.add(sliderDimensione);

        SpinnerModel valori = new SpinnerNumberModel(0, 0, 100, 1);
        JSpinner spinnerSoluzioni = new JSpinner(valori);

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
                dimensioneGriglia = sliderDimensione.getValue();
                mediator.avvia(numeroSoluzioni, dimensioneGriglia);
                frame.remove(panelNuovoGioco);
                finestraGriglia();
            }
        });

    }

    private void finestraGriglia()
    {
        frame.setLayout(new BorderLayout());

        JPanel panelGriglia = new JPanel();
        panelGriglia.setLayout(new GridLayout(dimensioneGriglia, dimensioneGriglia, 5, 5));
        panelGriglia.setBackground(Color.LIGHT_GRAY);

        JPanel panelBottoni = new JPanel(); // TODO lo uso magari per metterci i comandi (do, undo, salva, esci ecc.)
        panelBottoni.setSize(dimensioneGriglia, 1);
        panelBottoni.setBackground(Color.BLUE);

        //clicka per scegliere il valore da assegnare.
        JPopupMenu valoriDaInserire = new JPopupMenu();
        JMenuItem[] menuItems = new JMenuItem[dimensioneGriglia];
        for (int i = 0; i < dimensioneGriglia; i++)
        {
            menuItems[i] = new JMenuItem("" + (i+1));
            valoriDaInserire.add(menuItems[i]);
        }
        final int[] X_Y = {0, 0};
        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                valoriDaInserire.show(frame , e.getX(), e.getY());
                X_Y[0] = e.getX();
                X_Y[1] = e.getY();
            }
        });
        for (int i = 0; i < dimensioneGriglia; i++)
        {
            final int j = i;
            menuItems[i].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //inserisco il valore i nella cella clickata
                inserisciValore(X_Y[0], X_Y[1], (j+1));
            }
        });
        }



        //JButton[] bottoni = new JButton[dimensioneGriglia];

        for (int i = 0; i < dimensioneGriglia; i++)
        {
            for (int j = 0; j < dimensioneGriglia; j++)
            {
                JLabel label = new JLabel(mediator.soluzioni().get(0).cella(i, j).getValore() + "");
                label.setBackground(Color.LIGHT_GRAY);
                label.setHorizontalAlignment(JLabel.CENTER);
                panelGriglia.add(label);
            }
            //bottoni[i] = new JButton("" + (i+1));
            //panelBottoni.add(bottoni[i]);
        }

        frame.add(panelGriglia, BorderLayout.CENTER);
        frame.add(panelBottoni, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private void inserisciValore(int x, int y, int valore)
    {
        System.out.println("X: " + x + ", Y: " + y + "--> valore: " + valore);
    }


}
