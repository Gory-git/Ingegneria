package grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FinestraIniziale extends FinestraSubscriber
{
    private final JButton nuovoGioco;
    private final JButton caricaGioco;

    public FinestraIniziale()
    {
        super("KENKEN");
        this.setSize(300, 80);
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
                mediator.crea("ng");
            } else if (e.getSource() == caricaGioco)
            {
                try
                {
                    mediator.carica();
                }catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(FinestraIniziale.this,"Non trovo un salvataggio idoneo!","ATTENZIONE!",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mediator.crea("gi");
            }
            setVisible(false);
        }
    }
}
