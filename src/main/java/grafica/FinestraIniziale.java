package grafica;

import mediator.ConcreteMediator;
import mediator.Mediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FinestraIniziale extends FinestraSubscriber
{
    private final Mediator mediator = new ConcreteMediator();
    private JButton nuovoGioco, caricaGioco;

    public FinestraIniziale()
    {
        super("KENKEN");
        this.setSize(200, 110);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelIniziale = new JPanel();
        panelIniziale.setLayout(new FlowLayout());
        panelIniziale.setBackground(Color.LIGHT_GRAY);

        nuovoGioco = new JButton("Nuovo Gioco");
        nuovoGioco.addActionListener(new Listener());

        caricaGioco = new JButton("Carica Gioco");
        caricaGioco.addActionListener(new Listener());

        panelIniziale.add(nuovoGioco);
        panelIniziale.add(caricaGioco);

        this.add(panelIniziale);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private class Listener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == nuovoGioco)
            {
                FinestraNuovoGioco f = new FinestraNuovoGioco();
                f.addSubscriber(FinestraIniziale.this);
            } else if (e.getSource() == caricaGioco)
            {
                mediator.carica();
                FinestraGioco f = new FinestraGioco();
                f.addSubscriber(FinestraIniziale.this);
            }
            setVisible(false);
        }
    }
}
