package Command;

import java.util.LinkedList;

public abstract class HistoryCommandHandler extends CommandHanler
{
    protected final LinkedList<Command> comandi = new LinkedList<>();
    protected final LinkedList<Command> comandiRedo = new LinkedList<>();
    protected int lunghezzaStoria = 100;

    @Override
    public void handle(Command command)
    {
        super.handle(command);
        comandi.addFirst(command);
        if (!comandiRedo.isEmpty())
            comandiRedo.clear();
        if (comandi.size() > lunghezzaStoria)
            comandi.removeLast();
    }

    public void redo()
    {
        if (!comandiRedo.isEmpty())
        {
            Command redo = comandiRedo.removeFirst();
            comandi.addFirst(redo);
            if (comandi.size() > lunghezzaStoria)
                comandi.removeLast();
        }
    }
    public void undo()
    {
        if (!comandi.isEmpty())
        {
            Command undo = comandi.removeFirst();
            undo.deExecute();
            comandiRedo.addFirst(undo);
        }
    }
}
