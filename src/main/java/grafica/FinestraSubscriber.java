package grafica;

import observer.Subscriber;

import javax.swing.*;

abstract class FinestraSubscriber extends JFrame implements Subscriber
{
    public FinestraSubscriber(String s)
    {
        super(s);
    }

    @Override
    public void update()
    {
        setVisible(true);
    }
}
