package grafica.finestreObserver;

import observer.Manager;
import observer.Subscriber;

import javax.swing.*;
import java.util.LinkedList;

public abstract class FinestraManager extends JFrame implements Manager
{
    private LinkedList<Subscriber> subscribers = new LinkedList<>();

    public FinestraManager(String s)
    {
        super(s);
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

}
