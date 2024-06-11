import Gioco.soluzione.Soluzione;
import Gioco.soluzione.SoluzioneMatrix;
import grafica.FinestraIniziale;

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
        //new FinestraIniziale();


        Soluzione s = new SoluzioneMatrix(5);
        System.out.println(s.clone());


    }
}
