package grafica;

import Gioco.blocco.Blocco;
import Gioco.cella.Cella;
import Gioco.soluzione.Soluzione;
import mediator.ConcreteMediator;
import mediator.Mediator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class PanelGriglia extends JPanel
{

    private final HashMap<String, Color> bloccoColore = new HashMap<>();
    private final int dimensione;
    private int indiceSoluzione;
    private final Mediator mediator;

    public PanelGriglia()
    {
        this(-1);
    }

    public PanelGriglia(int numeroSoluzione)
    {

        this.indiceSoluzione = numeroSoluzione;
        mediator = new ConcreteMediator();
        dimensione = mediator.dimensione();
        impostaColori();
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(dimensione, dimensione, 2, 2));

        for (int i = 0; i < dimensione; i++)
            for (int j = 0; j < dimensione; j++)
            {
                JLabel labelValore = new JLabel(" ");
                if (numeroSoluzione >= 0)
                    labelValore.setText(mediator.valore(i, j, indiceSoluzione) + "");

                labelValore.setHorizontalAlignment(JLabel.CENTER);
                labelValore.setVerticalAlignment(JLabel.CENTER);


                String[] blocco = mediator.blocco(i, j);
                String idBlocco = blocco[0];
                String valoreBlocco = blocco[1];

                JLabel labelBlocco = new JLabel(valoreBlocco);
                labelBlocco.setHorizontalAlignment(JLabel.RIGHT);
                labelBlocco.setVerticalAlignment(JLabel.TOP);

                JPanel panelCella = new JPanel(new BorderLayout());
                panelCella.setBackground(bloccoColore.get(idBlocco));

                panelCella.add(labelValore, BorderLayout.CENTER);
                panelCella.add(labelBlocco, BorderLayout.NORTH);

                this.add(panelCella);
            }

    }

    private void impostaColori()
    {
        Set<Color> colori = new HashSet<>();

        for (int i = 0; i < dimensione; i++)
            for (int j = 0; j < dimensione; j++)
            {
                String[] blocco = mediator.blocco(i, j);
                String idBlocco = blocco[0];

                if (!bloccoColore.containsKey(idBlocco))
                {

                    int red = new Random().nextInt(120, 256);
                    int green = new Random().nextInt(120, 256);
                    int blue = new Random().nextInt(120, 256);
                    Color colore = new Color(red, green, blue);

                    while (colori.contains(colore))
                    {
                        red = new Random().nextInt(120, 256);
                        green = new Random().nextInt(120, 256);
                        blue = new Random().nextInt(120, 256);
                        colore = new Color(red, green, blue);
                    }
                    colori.add(colore);
                    bloccoColore.put(idBlocco, colore);
                }
            }

    }
}
