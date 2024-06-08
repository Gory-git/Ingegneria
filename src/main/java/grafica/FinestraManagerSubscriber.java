package grafica;

import observer.ManagerSubscriber;
import observer.Subscriber;

import javax.swing.*;
import java.util.LinkedList;

abstract class FinestraManagerSubscriber extends JFrame implements ManagerSubscriber
{
    private LinkedList<Subscriber> subscribers = new LinkedList<>();

    public FinestraManagerSubscriber(String s)
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

    @Override
    public void update()
    {
        setVisible(true);
    }
}
