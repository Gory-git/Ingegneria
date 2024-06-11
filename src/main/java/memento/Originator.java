package memento;

public interface Originator
{
    Memento salva();
    void ripristina(Memento memento);
}
