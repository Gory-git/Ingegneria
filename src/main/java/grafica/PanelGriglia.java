package grafica;

import Gioco.blocco.Blocco;
import Gioco.cella.Cella;
import Gioco.soluzione.Soluzione;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

class PanelGriglia extends JPanel
{

    private HashMap<Blocco, Color> bloccoColore = new HashMap<>();
    private int dimensione;

    public PanelGriglia(Soluzione s)
    {
        dimensione = s.dimensione();
        impostaColori(s);
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(dimensione, dimensione, 2, 2));

        for (int i = 0; i < dimensione; i++)
            for (int j = 0; j < dimensione; j++)
            {
                JLabel labelValore = new JLabel(s.cella(i, j).getValore() + "");
                labelValore.setHorizontalAlignment(JLabel.CENTER);
                labelValore.setVerticalAlignment(JLabel.CENTER);

                JLabel labelBlocco = new JLabel(s.cella(i, j).getBlocco() + "");
                labelBlocco.setHorizontalAlignment(JLabel.RIGHT);
                labelBlocco.setVerticalAlignment(JLabel.TOP);

                JPanel panelCella = new JPanel(new BorderLayout());
                panelCella.setBackground(bloccoColore.get(s.cella(i, j).getBlocco()));

                panelCella.add(labelValore, BorderLayout.CENTER);
                panelCella.add(labelBlocco, BorderLayout.NORTH);

                this.add(panelCella);
            }

    }

    private void impostaColori(Soluzione s)
    {
        for (Cella cella : s)
        {
            int red = new Random().nextInt(120, 256);
            int green = new Random().nextInt(120, 256);
            int blue = new Random().nextInt(120, 256);
            Color colore = new Color(red, green, blue);

            boolean coloreAdiacente = false;
            for (Cella vicino : s.vicini(cella.getPosizione()[0], cella.getPosizione()[1]))
                if (vicino.getBlocco() != cella.getBlocco())
                    if (bloccoColore.containsKey(vicino.getBlocco()) && bloccoColore.get(vicino.getBlocco()).equals(colore))
                    {
                        coloreAdiacente = true;
                        break;
                    }

            if (!bloccoColore.containsKey(cella.getBlocco()) && !coloreAdiacente)
                bloccoColore.put(cella.getBlocco(), colore);
        }
    }

}
