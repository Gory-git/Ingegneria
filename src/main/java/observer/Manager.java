package observer;

import java.util.LinkedList;

public interface Manager
{
    LinkedList<Subscriber> subscribers = new LinkedList<>();
    void addSubscriber(Subscriber subscriber);
    void removeSubscriber(Subscriber subscriber);
    void sendNotification();
}
