package grafica;

import mediator.Component;
import mediator.Mediator;
import observer.ManagerSubscriber;
import observer.Subscriber;

import javax.swing.*;
import java.util.LinkedList;

abstract class FinestraManagerSubscriber extends JFrame implements ManagerSubscriber, Component
{
    private final LinkedList<Subscriber> subscribers = new LinkedList<>();
    protected Mediator mediator;

    public FinestraManagerSubscriber(String s)
    {
        super(s);
    }

    @Override
    public void setMediator(Mediator mediator)
    {
        this.mediator = mediator;
    }

    @Override
    public void addSubscriber(Subscriber subscriber)
    {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber)
    {
        subscribers.remove(subscriber);
    }

    @Override
    public void sendNotification()
    {
        for (Subscriber subscriber : subscribers)
            subscriber.update();
    }

    @Override
    public void update()
    {
        setVisible(true);
    }
}
