package Interfaccia;

import Gioco.Gioco;
import Gioco.mediator.ConcreteMediator;
import Gioco.mediator.Mediator;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;

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
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());


        JPanel panelGriglia = new JPanel();
        panelGriglia.setLayout(new GridLayout(dimensioneGriglia, dimensioneGriglia, 5, 5));
        panelGriglia.setBounds(1, 1, 500, 500);
        panelGriglia.setBackground(Color.LIGHT_GRAY);

        JPanel panelBottoni = new JPanel(); // TODO lo uso magari per metterci i comandi (do, undo, salva, esci ecc.)
        panelBottoni.setSize(dimensioneGriglia, 1);
        panelBottoni.setBounds(1, 1, 500, 100);
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
        panelGriglia.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                valoriDaInserire.show(frame , e.getX(), e.getY());
                X_Y[0] = e.getX();
                X_Y[1] = e.getY();
            }
        });
        for (int i = 0; i < dimensioneGriglia; i++)
        {
            final int j = i;
            menuItems[i].addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    //inserisco il valore i nella cella clickata
                    inserisciValore(X_Y[0], X_Y[1], (j+1));
                }
            });
        }

        LinkedList<JButton> bottoni = new LinkedList<>();
        bottoni.add(new JButton("UNDO")); // bottoneUndo TODO actionlistener
        bottoni.add(new JButton("DO")); // bottoneDo TODO actionlistener
        bottoni.add(new JButton("SALVA")); // bottoneSalva TODO actionlistener
        bottoni.add(new JButton("INDIETRO")); // bottoneIndietro TODO actionlistener

        for (JButton bottone : bottoni)
            panelBottoni.add(bottone);

        for (int i = 0; i < dimensioneGriglia; i++)
            for (int j = 0; j < dimensioneGriglia; j++)
            {
                JLabel label = new JLabel(mediator.soluzioni().get(0).cella(i, j).getValore() + "");
                //label.setBackground(Color.LIGHT_GRAY);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                panelGriglia.add(label);
                JSeparator separatore = new JSeparator();
                separatore.setOrientation(JSeparator.VERTICAL);
                panelGriglia.add(separatore);
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
