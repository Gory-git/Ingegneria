package grafica;

import mediator.Mediator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FinestraFinale extends JFrame
{
    private Mediator mediator;
    private int numeroSoluzioni;
    private PanelGriglia[] griglie;
    private int[] attuale = {0};
    public FinestraFinale()
    {
        super("SOLUZIONI!");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mediator = Mediator.ISTANZA;
        numeroSoluzioni = mediator.numeroSoluzioni();
        griglie = new PanelGriglia[numeroSoluzioni];

        for (int i = 0; i < numeroSoluzioni; i++)
        {
            griglie[i] = new PanelGriglia(i);
            griglie[i].setVisible(true);
        }
        add(griglie[0]);

        // AVANTI
        JMenuItem avanti = new JMenuItem("AVANTI");
        avanti.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (attuale[0] == numeroSoluzioni - 1)
                {
                    JOptionPane.showMessageDialog
                            (
                                    griglie[attuale[0]],
                                    "Hai visto tutte le " + numeroSoluzioni + " soluzioni!",
                                    "Attenzione",
                                    JOptionPane.ERROR_MESSAGE
                            );
                }else
                {
                    remove(griglie[attuale[0]]);
                    attuale[0] = attuale[0] + 1;
                    add(griglie[attuale[0]]);
                    SwingUtilities.updateComponentTreeUI(FinestraFinale.this);
                }
            }
        });

        // INDIETRO
        JMenuItem indietro = new JMenuItem("INDIETRO");
        indietro.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (attuale[0] == 0)
                {
                    JOptionPane.showMessageDialog
                            (
                                    griglie[attuale[0]],
                                    "Sei alla prima soluzione disponibile!",
                                    "Attenzione",
                                    JOptionPane.ERROR_MESSAGE
                            );
                }else
                {
                    remove(griglie[attuale[0]]);
                    attuale[0] = attuale[0] - 1;
                    add(griglie[attuale[0]]);
                    SwingUtilities.updateComponentTreeUI(FinestraFinale.this);
                }
            }
        });
        // NUOVOGIOCO
        JMenuItem nuovoGioco = new JMenuItem("NUOVO GIOCO");
        nuovoGioco.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new FinestraIniziale();
                setVisible(false);
            }
        });

        JMenu opzioni = new JMenu("Opzioni");
        opzioni.add(avanti);
        opzioni.add(indietro);
        opzioni.add(nuovoGioco);

        JMenuBar barraMenu = new JMenuBar();
        barraMenu.add(opzioni);

        setJMenuBar(barraMenu);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
