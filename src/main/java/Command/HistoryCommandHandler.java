package Command;

public interface HistoryCommandHandler extends CommandHanler
{
    void redo();
    void undo();
}
