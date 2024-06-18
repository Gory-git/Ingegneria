package Command;

public abstract class CommandHanler
{
    public void handle(Command command)
    {
        command.execute();
    }
}
