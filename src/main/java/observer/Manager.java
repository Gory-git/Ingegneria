package observer;

import java.util.LinkedList;

public interface Manager
{
    void addSubscriber(Subscriber subscriber);
    void removeSubscriber(Subscriber subscriber);
    void sendNotification();
}
