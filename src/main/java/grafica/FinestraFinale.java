package grafica;

import mediator.ConcreteMediator;
import mediator.Mediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinestraFinale extends JFrame
{
    Mediator mediator;
    int numeroSoluzioni;
    PanelGriglia[] griglie;
    int[] attuale = {0};
    public FinestraFinale()
    {
        super("SOLUZIONI!");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mediator = new ConcreteMediator();
        numeroSoluzioni = mediator.soluzioni().size() - 1;
        griglie = new PanelGriglia[numeroSoluzioni];

        for (int i = 1; i <= numeroSoluzioni; i++)
        {
            griglie[i - 1] = new PanelGriglia(mediator.soluzioni().get(i));
            griglie[i - 1].setVisible(true);
        }
        add(griglie[0]);
        // AVANTI
        JMenuItem avanti = new JMenuItem("AVANTI"); // bottoneSalva TODO actionlistener
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
        JMenuItem indietro = new JMenuItem("INDIETRO"); // bottoneSalva TODO actionlistener
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

        JMenu opzioni = new JMenu("Opzioni");
        opzioni.add(avanti);
        opzioni.add(indietro);

        JMenuBar barraMenu = new JMenuBar();
        barraMenu.add(opzioni);

        setJMenuBar(barraMenu);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
