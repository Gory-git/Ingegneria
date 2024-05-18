package Gioco.blocco;

import Gioco.cella.Cella;
import org.junit.jupiter.api.Test;

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
}