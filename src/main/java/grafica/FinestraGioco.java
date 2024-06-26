package grafica;

import Command.HistoryCommandHandler;
import Command.Inserisci;
import mediator.Mediator;
import mediator.MediatorConcreto;

import javax.swing.*;
import java.awt.event.*;

public class FinestraGioco extends FinestraManagerSubscriber
{
    private final int dimensioneGriglia;
    private boolean controllaErrori = true;
    private final PanelGriglia panelGriglia;
    private final GiocoHandler giocoHandler = new GiocoHandler();

    public FinestraGioco(Mediator mediator)
    {
        super("KENKEN GAME!");

        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMediator(mediator);
        mediator.addSubscriber(this);
        this.dimensioneGriglia = mediator.dimensione();

        panelGriglia = new PanelGriglia(0, mediator);

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
            menuItems[i].addActionListener(e -> {
                //inserisco il valore i nella cella clickata
                inserisciValore(X_Y[0], X_Y[1], (j+1));
            });
        }

        // UNDO
        JMenuItem undo = new JMenuItem("UNDO"); // Undo
        undo.addActionListener(e -> giocoHandler.undo());

        // REDO
        JMenuItem mDo= new JMenuItem("REDO"); // Redo
        mDo.addActionListener(e -> giocoHandler.redo());

        // SALVA
        JMenuItem salva = new JMenuItem("SALVA");
        final boolean[] salvato = {false};
        salva.addActionListener(e -> {
            mediator.salva();
            salvato[0] = true;
            JOptionPane.showMessageDialog
                    (
                            panelGriglia,
                            "Salvataggio effettuato!",
                            "Salvataggio",
                            JOptionPane.INFORMATION_MESSAGE
                    );
        });
        // BOTTONE INDIETRO
        JMenuItem indietro = new JMenuItem("INDIETRO");
        indietro.addActionListener(e -> {
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
        });
        // TERMINA
        JMenuItem termina = new JMenuItem("TERMINA");
        termina.addActionListener(e -> {
            int opzione = JOptionPane.showConfirmDialog
                    (
                            panelGriglia,
                            "I progressi non salvati verranno persi!",
                            "Attenzione",
                            JOptionPane.OK_CANCEL_OPTION
                    );
            if (opzione == JOptionPane.OK_OPTION)
            {
                if (mediator.numeroSoluzioni() - 1 > 0)
                {
                    new FinestraFinale(mediator);
                    setVisible(false);
                }else
                {
                    opzione = JOptionPane.showConfirmDialog
                            (
                                panelGriglia,
                                "Iniziare una nuova partita?",
                                "Attenzione",
                                JOptionPane.YES_NO_OPTION
                            );
                    if (opzione == JOptionPane.YES_OPTION)
                    {
                        mediator.crea("i");
                        setVisible(false);
                    }else
                        dispatchEvent(new WindowEvent(FinestraGioco.this, WindowEvent.WINDOW_CLOSING));
                }
            }
        });
        // MOSTRA ERRORI
        JMenuItem avvisoMossaErrata = new JMenuItem("DISATTIVA AVVISO");
        avvisoMossaErrata.addActionListener(e ->
        {
            controllaErrori = !controllaErrori;
            if (controllaErrori)
                avvisoMossaErrata.setText("DISATTIVA AVVISO");
            else
                avvisoMossaErrata.setText("ATTIVA AVVISO");
        });

        JMenu operazioni = new JMenu("Operazioni");
        operazioni.add(undo);
        operazioni.add(mDo);

        JMenu opzioni = new JMenu("Opzioni");
        opzioni.add(indietro);
        opzioni.add(salva);
        opzioni.add(termina);
        opzioni.add(avvisoMossaErrata);

        JMenuBar barraMenu = new JMenuBar();
        barraMenu.add(operazioni);
        barraMenu.add(opzioni);

        this.setJMenuBar(barraMenu);
        this.add(panelGriglia);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    private void inserisciValore(int x, int y, int valore)
    {

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
        Inserisci inserisci = new Inserisci(riga, colonna, valore, x, y);
        inserisci.setMediator(mediator);
        giocoHandler.handle(inserisci);

        if (mediator.valore(riga, colonna) == 0)
            label.setText(" ");
        else
            label.setText(mediator.valore(riga, colonna) + "");

        if (controllaErrori && !mediator.controlla(riga, colonna, valore))
            JOptionPane.showMessageDialog(this,"Valore errato!","ERRORE!",JOptionPane.ERROR_MESSAGE);
    }

    private void risolta()
    {
        if (mediator.numeroSoluzioni() - 1 == 0)
        {
            int opzione = JOptionPane.showConfirmDialog
                    (
                            panelGriglia,
                            "Hai risolto il gioco correttamente!\nIniziare una nuova partita?",
                            "Attenzione",
                            JOptionPane.YES_NO_OPTION
                    );
            if (opzione == JOptionPane.YES_OPTION)
            {
                setVisible(false);
                mediator.crea("i");
            }else
                dispatchEvent(new WindowEvent(FinestraGioco.this, WindowEvent.WINDOW_CLOSING));
        }else
        {
            JOptionPane.showMessageDialog
                    (
                            panelGriglia,
                            "COMPLIMENTI!! Hai risolto il gioco correttamente!",
                            "YUPPIE",
                            JOptionPane.INFORMATION_MESSAGE
                    );
            setVisible(false);
            mediator.crea("f");
        }

    }

    @Override
    public void update()
    {
        risolta();
    }

    private class GiocoHandler extends HistoryCommandHandler
    {
        @Override
        public void redo()
        {
            if (!comandiRedo.isEmpty())
            {
                Inserisci comando = (Inserisci) comandiRedo.getFirst();
                JPanel panelLabel = (JPanel) panelGriglia.getComponentAt(comando.getX(), comando.getY());
                JLabel label;
                try
                {
                    label = (JLabel) panelLabel.getComponent(0);
                }catch (ClassCastException e)
                {
                    return;
                }
                String valore = comando.getNuovoValore()  + "";
                if (comando.getNuovoValore() == 0)
                    valore = " ";
                label.setText(valore);
            }
            super.redo();
        }

        @Override
        public void undo()
        {
            if (!comandi.isEmpty())
            {
                Inserisci comando = (Inserisci) comandi.getFirst();
                JPanel panelLabel = (JPanel) panelGriglia.getComponentAt(comando.getX(), comando.getY());
                JLabel label;
                try
                {
                    label = (JLabel) panelLabel.getComponent(0);
                }catch (ClassCastException e)
                {
                    return;
                }
                String valore = comando.getValorePrecedente()  + "";
                if (comando.getValorePrecedente() == 0)
                    valore = " ";
                label.setText(valore);
            }
            super.undo();
        }
    }

}
