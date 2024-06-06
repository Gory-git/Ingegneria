package Interfaccia;

import Gioco.blocco.Blocco;
import Gioco.cella.Cella;
import Gioco.mediator.ConcreteMediator;
import Gioco.mediator.Mediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class FinestraIniziale // FIXME devo fare classi diverse, altrimenti esce una merda
{
    private final Mediator mediator = new ConcreteMediator();
    private final JFrame frame;
    int numeroSoluzioni, dimensioneGriglia;
    private HashMap<Blocco, Color> bloccoColore = new HashMap<>();
    public FinestraIniziale()
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
                finestraGriglia();
            }
        });

    }

    private void finestraGriglia()
    {
        //frame.getContentPane().setSize(504, 504);
        //frame.setLayout(new BorderLayout());

        System.out.println(frame.getWidth());
        System.out.println(frame.getHeight());

        impostaColori();

        JPanel panelGriglia = new JPanel();
        panelGriglia.setLayout(new GridLayout(dimensioneGriglia, dimensioneGriglia, 2, 2));
        panelGriglia.setSize(500, 500);
        panelGriglia.setBackground(Color.LIGHT_GRAY);

        JPanel panelBottoni = new JPanel();
        panelBottoni.setSize(dimensioneGriglia, 1);
        panelBottoni.setSize(504, 100);
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
                valoriDaInserire.show(panelGriglia , e.getX(), e.getY());
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
                    inserisciValore(X_Y[0], X_Y[1], (j+1), panelGriglia);
                }
            });
        }

        LinkedList<JButton> bottoni = new LinkedList<>();
        bottoni.add(new JButton("UNDO")); // bottoneUndo TODO actionlistener
        bottoni.add(new JButton("DO")); // bottoneDo TODO actionlistener
        bottoni.add(new JButton("SALVA")); // bottoneSalva TODO actionlistener
        bottoni.add(new JButton("INDIETRO")); // bottoneIndietro TODO actionlistener
        // BOTTONE UNDO TODO

        // BOTTONE DO TODO

        // BOTTONE SALVA
        final boolean[] salvato = {false};
        bottoni.get(2).addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mediator.salva();
                salvato[0] = true;
                JOptionPane.showMessageDialog(frame,"Salvataggio effettuato!","Salvataggio",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // BOTTONE INDIETRO
        bottoni.get(3).addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!salvato[0])
                {
                    JOptionPane.showMessageDialog(frame,"Salvataggio non ancora effettuato!","Attenzione",JOptionPane.ERROR_MESSAGE);
                    //finestraGriglia();
                }
                else
                {
                    frame.remove(panelGriglia);
                    frame.remove(panelBottoni);
                    finestraNuovoGioco();
                }
            }
        });

        for (JButton bottone : bottoni)
            panelBottoni.add(bottone);

        //frame.add(panelBottoni, BorderLayout.SOUTH);

        for (int i = 0; i < dimensioneGriglia; i++)
            for (int j = 0; j < dimensioneGriglia; j++)
            {
                JLabel labelValore = new JLabel(mediator.soluzioni().get(0).cella(i, j).getValore() + "");
                labelValore.setHorizontalAlignment(JLabel.CENTER);
                labelValore.setVerticalAlignment(JLabel.CENTER);

                JLabel labelBlocco = new JLabel(mediator.soluzioni().get(0).cella(i, j).getBlocco() + "");
                labelBlocco.setHorizontalAlignment(JLabel.RIGHT);
                labelBlocco.setVerticalAlignment(JLabel.TOP);

                JPanel panelCella = new JPanel(new BorderLayout());
                panelCella.setBackground(bloccoColore.get(mediator.soluzioni().get(0).cella(i, j).getBlocco()));

                panelCella.add(labelValore, BorderLayout.CENTER);
                panelCella.add(labelBlocco, BorderLayout.NORTH);

                panelGriglia.add(panelCella);
            }

        frame.add(panelGriglia/*, BorderLayout.CENTER*/);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private void inserisciValore(int x, int y, int valore, JPanel panelGriglia)
    {
        // System.out.println("X: " + x + ", Y: " + y + "--> valore: " + valore);

        JPanel panelLabel = (JPanel) panelGriglia.getComponentAt(x, y);
        JLabel label;
        try
        {
            label = (JLabel) panelLabel.getComponent(0);
        }catch (ClassCastException e)
        {
            return;
        }

        int riga = Math.min(Math.floorDiv(y * dimensioneGriglia, 462), dimensioneGriglia - 1);
        int colonna = Math.min(Math.floorDiv(x * dimensioneGriglia, 485), dimensioneGriglia - 1);

        // System.out.println("R: " + riga + "; C: " + colonna);

        mediator.inserisciValore(riga, colonna, valore);

        // System.out.println(mediator.soluzioni().get(0));

        label.setText(mediator.soluzioni().get(0).cella(riga, colonna).getValore() + "");

        if (!mediator.soluzioni().get(0).controlla(riga, colonna, valore))
            JOptionPane.showMessageDialog(frame,"Valore errato!","ERRORE!",JOptionPane.ERROR_MESSAGE);

        if (mediator.soluzioni().get(0).risolta())
            System.out.println("YUPPI!!"); // TODO schermata finale!!
    }

    private void impostaColori()
    {
        for (Cella cella : mediator.soluzioni().get(0))
        {
            int red = new Random().nextInt(120, 256);
            int green = new Random().nextInt(120, 256);
            int blue = new Random().nextInt(120, 256);
            Color colore = new Color(red, green, blue);
            if (!bloccoColore.containsKey(cella.getBlocco()))
                bloccoColore.put(cella.getBlocco(), colore);
        }
    }

}
