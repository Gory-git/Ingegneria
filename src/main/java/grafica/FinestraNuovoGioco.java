package grafica;

import mediator.ConcreteMediator;
import mediator.Mediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FinestraNuovoGioco  extends FinestraManagerSubscriber
{
    private Mediator mediator = new ConcreteMediator();
    private JSlider sliderDimensione;
    private JSpinner spinnerSoluzioni;
    private JProgressBar progressBar;
    public FinestraNuovoGioco()
    {
        super("SELEZIONA VINCOLI");
        this.setSize(400, 150);
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

        JPanel panelNuovoGioco = new JPanel();
        panelNuovoGioco.setLayout(new FlowLayout());
        panelNuovoGioco.setBackground(Color.LIGHT_GRAY);

        JButton avviaGioco = new JButton("Avvia!");
        avviaGioco.addActionListener(new Listener());
        avviaGioco.setBounds(350, 400, 120, 40);
        panelNuovoGioco.add(avviaGioco);


        sliderDimensione = new JSlider(JSlider.HORIZONTAL, 3, 9, 6);
        sliderDimensione.setMajorTickSpacing(1);
        sliderDimensione.setPaintTicks(true);
        sliderDimensione.setPaintLabels(true);

        panelNuovoGioco.add(sliderDimensione);

        SpinnerModel valori = new SpinnerNumberModel(0, 0, 100, 1);
        spinnerSoluzioni = new JSpinner(valori);

        panelNuovoGioco.add(spinnerSoluzioni);

        progressBar = new JProgressBar();

        progressBar.setIndeterminate(true);

        this.add(panelNuovoGioco);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
    private class Listener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            add(progressBar);

            int numeroSoluzioni = (Integer) spinnerSoluzioni.getValue();
            int dimensioneGriglia = sliderDimensione.getValue();
            mediator.avvia(numeroSoluzioni, dimensioneGriglia);
            FinestraGioco f = new FinestraGioco();
            f.addSubscriber(FinestraNuovoGioco.this);
            setVisible(false);
        }
    }

}
