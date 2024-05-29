package Gioco.memento;

public interface Originator
{
    Memento salva();
    void ripristina(Memento memento);
}
