package Gioco.operatore;

import java.util.Random;

public enum Operatore
{
    SOMMA,
    SOTTRAZIONE,
    MOLTIPLICAZIONE,
    DIVISIONE,
    NONE;

    public static Operatore getRandom()
    {
        return Operatore.values()[new Random().nextInt(0, 4)];
    }

    @Override
    public String toString()
    {
        String ret = "";
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
