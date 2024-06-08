package grafica;

import mediator.ConcreteMediator;
import mediator.Mediator;

import javax.swing.*;
import java.awt.event.*;

class FinestraGioco extends FinestraManager
{
    private int dimensioneGriglia;
    private Mediator mediator;


    public FinestraGioco()
    {
        super("KENKEN GAME!");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mediator = new ConcreteMediator();
        this.dimensioneGriglia = mediator.soluzioni().get(0).dimensione();

        JPanel panelGriglia = new PanelGriglia(mediator.soluzioni().get(0));
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

        // UNDO TODO
        JMenuItem undo = new JMenuItem("UNDO"); // Undo TODO actionlistener

        // DO TODO
        JMenuItem mDo= new JMenuItem("DO"); // Do TODO actionlistener

        // SALVA
        JMenuItem salva = new JMenuItem("SALVA");
        final boolean[] salvato = {false};
        salva.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mediator.salva();
                salvato[0] = true;
                JOptionPane.showMessageDialog
                        (
                                panelGriglia,
                                "Salvataggio effettuato!",
                                "Salvataggio",
                                JOptionPane.INFORMATION_MESSAGE
                        );
            }
        });
        // BOTTONE INDIETRO
        JMenuItem indietro = new JMenuItem("INDIETRO");
        indietro.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!salvato[0])
                {
                    JOptionPane.showMessageDialog
                            (
                                    panelGriglia,
                                    "Salvataggio non ancora effettuato!",
                                    "Attenzione",
                                    JOptionPane.ERROR_MESSAGE
                            );
                }
                else
                {
                    sendNotification();
                    setVisible(false);
                }
            }
        });

        // TERMINA
        JMenuItem termina = new JMenuItem("TERMINA");
        termina.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int opzione = JOptionPane.showConfirmDialog
                        (
                                panelGriglia,
                                "La partita attuale andrà persa!",
                                "Attenzione",
                                JOptionPane.OK_CANCEL_OPTION
                        );
                if (opzione == JOptionPane.OK_OPTION)
                {
                    if (mediator.soluzioni().size() > 1)
                    {
                        new FinestraFinale();
                        setVisible(false);
                    }else
                        dispatchEvent(new WindowEvent(FinestraGioco.this, WindowEvent.WINDOW_CLOSING));
                }
            }
        });

        JMenu operazioni = new JMenu("Operazioni");
        operazioni.add(undo);
        operazioni.add(mDo);

        JMenu opzioni = new JMenu("Opzioni");
        opzioni.add(indietro);
        opzioni.add(salva);
        opzioni.add(termina);

        JMenuBar barraMenu = new JMenuBar();
        barraMenu.add(operazioni);
        barraMenu.add(opzioni);

        this.setJMenuBar(barraMenu);
        this.add(panelGriglia);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    private void inserisciValore(int x, int y, int valore, JPanel panelGriglia)
    {
        //System.out.println("X: " + x + ", Y: " + y + "--> valore: " + valore);

        JPanel panelLabel = (JPanel) panelGriglia.getComponentAt(x, y);
        JLabel label;
        try
        {
            label = (JLabel) panelLabel.getComponent(0);
        }catch (ClassCastException e)
        {
            return;
        }

        int riga = Math.min(Math.floorDiv(y * dimensioneGriglia, 435), dimensioneGriglia - 1);
        int colonna = Math.min(Math.floorDiv(x * dimensioneGriglia, 485), dimensioneGriglia - 1);

        // System.out.println("R: " + riga + "; C: " + colonna);

        mediator.inserisciValore(riga, colonna, valore);

        // System.out.println(mediator.soluzioni().get(0));

        label.setText(mediator.soluzioni().get(0).cella(riga, colonna).getValore() + "");

        if (!mediator.soluzioni().get(0).controlla(riga, colonna, valore))
            JOptionPane.showMessageDialog(this,"Valore errato!","ERRORE!",JOptionPane.ERROR_MESSAGE);

        if (mediator.soluzioni().get(0).risolta())
        {
            JOptionPane.showMessageDialog
                    (
                            panelGriglia,
                            "COMPLIMENTI!! Hai risolto il gioco correttamente!",
                            "YUPPI",
                            JOptionPane.INFORMATION_MESSAGE
                    );
            setVisible(false);
            new FinestraFinale();
        }
    }

}
