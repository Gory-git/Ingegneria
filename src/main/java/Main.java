import Gioco.soluzione.Soluzione;
import Gioco.soluzione.SoluzioneMatrix;
import grafica.FinestraIniziale;
import mediator.MediatorConcreto;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException, CloneNotSupportedException
    {
/*

        for (int i = 0; i < 10; i++)
        {
            new SoluzioneMatrix(9);
        }
        //Gioco.INSTANCE.avvia(2, 7);

        Mediator mediator = new ConcreteMediator();
        mediator.carica();
        System.out.println(Gioco.INSTANCE.getSoluzioni().get(0));

        System.out.println(Gioco.INSTANCE.getSoluzioni().get(1));

        System.out.println(Gioco.INSTANCE.getSoluzioni().get(2));
        //mediator.salva();
*/
        FinestraIniziale f = new FinestraIniziale();
        f.setMediator(new MediatorConcreto());
/*
        Soluzione s = new SoluzioneMatrix(6, 20);
        System.out.println(s);
        System.out.println("\n---CLONE---\n");
        Soluzione clone = s.clone();
        System.out.println(clone);
        System.out.println(clone.risolta());
/**/
    }
}
