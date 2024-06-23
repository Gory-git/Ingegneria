package Gioco.soluzione;

import memento.Memento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoluzioneMatrixTest
{
    @Test
    void cloneRisolto()
    {
        SoluzioneMatrix soluzione = new SoluzioneMatrix(5, 10);
        SoluzioneMatrix clone = soluzione.clone();
        assertTrue(clone.risolta());
    }

    @Test
    void soluzioneDaSoluzioneUguale()
    {
        SoluzioneMatrix soluzione = new SoluzioneMatrix(5, 10);
        SoluzioneMatrix dupl = new SoluzioneMatrix(soluzione);
        assertEquals(soluzione, dupl);
    }

    @Test
    void mementoUgualeOriginator()
    {
        SoluzioneMatrix soluzione = new SoluzioneMatrix(5, 10);
        Memento memento = soluzione.salva();
        SoluzioneMatrix dupl = new SoluzioneMatrix(soluzione);
        dupl.ripristina(memento);
        assertEquals(soluzione, dupl);
    }
}