package grafica;

import mediator.Mediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FinestraNuovoGioco  extends FinestraManagerSubscriber
{
    private Mediator mediator = Mediator.ISTANZA;
    private JPanel panelNuovoGioco;
    private JSlider sliderDimensione;
    private JSpinner spinnerSoluzioni;
    public FinestraNuovoGioco()
    {
        super("SELEZIONA VINCOLI");
        this.setSize(300, 150);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // BOTTONE INDIETRO
        JMenuItem indietro = new JMenuItem("INDIETRO");
        indietro.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sendNotification();
                setVisible(false);
            }
        });

        JMenu menu = new JMenu("Opzioni");
        menu.add(indietro);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);

        setJMenuBar(menuBar);

        panelNuovoGioco = new JPanel();
        panelNuovoGioco.setLayout(new FlowLayout());
        panelNuovoGioco.setBackground(Color.LIGHT_GRAY);

        sliderDimensione = new JSlider(JSlider.HORIZONTAL, 3, 9, 6);
        sliderDimensione.setMajorTickSpacing(1);
        sliderDimensione.setPaintTicks(true);
        sliderDimensione.setPaintLabels(true);
        panelNuovoGioco.add(new JLabel("Dimensione:"));
        panelNuovoGioco.add(sliderDimensione);

        SpinnerModel valoriSoluzioni = new SpinnerNumberModel(0, 0, 50, 1);
        spinnerSoluzioni = new JSpinner(valoriSoluzioni);
        panelNuovoGioco.add(new JLabel("Numero Soluzioni:"));
        panelNuovoGioco.add(spinnerSoluzioni);

        JButton avviaGioco = new JButton("Avvia!");
        avviaGioco.addActionListener(new Listener());
        panelNuovoGioco.add(avviaGioco);

        add(panelNuovoGioco);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

            int numeroSoluzioni = (int) spinnerSoluzioni.getValue();
            int dimensioneGriglia = sliderDimensione.getValue();
            Object[] numeriPossibiliBlocchi = new Integer[dimensioneGriglia * dimensioneGriglia];
            for (int i = 0; i <dimensioneGriglia * dimensioneGriglia; i++)
            {
                numeriPossibiliBlocchi[i] = i + 1;
            }
            int numeroBlocchi;
            try
            {
                numeroBlocchi= (int) JOptionPane.showInputDialog(panelNuovoGioco, "Inserire numero di blocchi che si vuole risolvere: ", "Scelta numero blocchi", JOptionPane.PLAIN_MESSAGE, null,numeriPossibiliBlocchi, (Object) 6);
            }catch (NullPointerException ex)
            {
                return;
            }
            mediator.avvia(numeroSoluzioni, dimensioneGriglia, numeroBlocchi);
            FinestraGioco f = new FinestraGioco();
            f.addSubscriber(FinestraNuovoGioco.this);
            setVisible(false);
        }
    }
}
