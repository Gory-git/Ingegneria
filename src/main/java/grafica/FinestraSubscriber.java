package grafica;

import mediator.Component;
import mediator.Mediator;
import observer.Manager;
import observer.Subscriber;

import javax.swing.*;

abstract class FinestraSubscriber extends JFrame implements Subscriber, Component
{
    protected Mediator mediator;

    @Override
    public void setMediator(Mediator mediator)
    {
        this.mediator = mediator;
    }

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
