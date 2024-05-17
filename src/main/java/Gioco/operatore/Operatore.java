package Gioco.operatore;

import java.util.Random;

public enum Operatore
{
    SOMMA,
    SOTTRAZIONE,
    MOLTIPLICAZIONE,
    DIVISIONE,
    NONE;

    public Operatore getRandom()
    {
        Operatore[] operatori = new Operatore[]{SOMMA, SOTTRAZIONE, MOLTIPLICAZIONE, DIVISIONE};
        return operatori[new Random().nextInt(0, 4)];
    }

    @Override
    public String toString()
    {
        String ret = "n";
        switch (this)
        {
            case SOMMA -> ret = "+";
            case SOTTRAZIONE -> ret = "-";
            case MOLTIPLICAZIONE -> ret = "*";
            case DIVISIONE -> ret = "/";
        }
        return ret;
    }
}
