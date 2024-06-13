package Gioco.blocco;

import Gioco.cella.Cella;
import Gioco.operatore.Operatore;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class BloccoListTest {

    @Test
    void seNonPienoNonInizializzato()
    {
        int dimensione = 2;
        BloccoList b = new BloccoList(dimensione);
        for (int i = 0; i < dimensione - 1; i++)
        {
            b.aggiungiCella(new Cella(new int[]{0,0}, i + 1, b));
        }

        assertEquals(0, b.valore);

    }

    @Test
    void sePienoInizializzato()
    {
        int dimensione = 2;
        BloccoList b = new BloccoList(dimensione);
        for (int i = 0; i < dimensione; i++)
        {
            b.aggiungiCella(new Cella(new int[]{0,0}, i + 1, b));
        }
        assertTrue(b.valore != 0);
    }

    @Test
    void sePienoNonAggiungeCella()
    {
        BloccoList b = new BloccoList(1);
        b.aggiungiCella(new Cella(new int[]{0, 0}));
        try
        {
            b.aggiungiCella(new Cella(new int[]{0, 0}));
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            assertEquals(1, b.celle().size());
        }
    }

    @Test
    void seNonPienoNonSoddisfatto()
    {
        BloccoList b = new BloccoList(2);
        b.aggiungiCella(new Cella(new int[]{0,0}));

        assertFalse(b.soddisfatto());
    }

    @Test
    void sePienoSoddisfatto()
    {
        int dimensione = 2;
        BloccoList b = new BloccoList(dimensione);
        for (int i = 0; i < dimensione; i++)
        {
            b.aggiungiCella(new Cella(new int[]{0,0}, i + 1, b));
        }
        assertTrue(b.soddisfatto());
    }

    @Test
    void valoreDeveEssereCorrettoSomma()
    {
        int dimensione = 4;
        LinkedList<Cella> celle = new LinkedList<>();
        for (int i = 1; i < dimensione; i++)
            celle.add(new Cella(2, new int[]{0,0}));
        celle.add(new Cella(0, new int[]{0,0}));
        BloccoList b = new BloccoList(Operatore.SOMMA, dimensione * 2, dimensione, celle);
        assertTrue(b.assegnabile(2));
        assertFalse(b.assegnabile(3));
    }

    @Test
    void valoreDeveEssereCorrettoSottrazione()
    {
        int dimensione = 2;
        LinkedList<Cella> celle = new LinkedList<>();
        celle.add(new Cella(5, new int[]{0,0}));
        celle.add(new Cella(3, new int[]{0,0}));
        BloccoList b = new BloccoList(Operatore.SOTTRAZIONE, 1, dimensione, celle);
        assertTrue(b.assegnabile(1));
        assertFalse(b.assegnabile(5));
    }

    @Test
    void valoreDeveEssereCorrettoMoltiplicazione()
    {
        int dimensione = 4;
        LinkedList<Cella> celle = new LinkedList<>();
        for (int i = 1; i < dimensione; i++)
            celle.add(new Cella(1, new int[]{0,0}));
        celle.add(new Cella(0, new int[]{0,0}));
        BloccoList b = new BloccoList(Operatore.MOLTIPLICAZIONE, dimensione, dimensione, celle);
        assertTrue(b.assegnabile(dimensione));
        assertFalse(b.assegnabile(dimensione + 1));
    }

    @Test
    void valoreDeveEssereCorrettoDivisione()
    {
        int dimensione = 4;
        LinkedList<Cella> celle = new LinkedList<>();
        for (int i = 0; i < dimensione; i++)
            celle.add(new Cella(1, new int[]{0,0}));
        celle.get(0).setValore(dimensione);
        BloccoList b = new BloccoList(Operatore.DIVISIONE, 1, dimensione, celle);
        assertTrue(b.assegnabile(dimensione));
        assertFalse(b.assegnabile(dimensione - 1));
    }

}