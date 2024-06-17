package Command;

public interface Command
{
    void execute(int riga, int colona, int valore);
    void deExecute();
    void backup(int riga, int colonna);
}
