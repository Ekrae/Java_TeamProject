import java.util.Vector;
abstract class WrongAnswerNotes {
    public Vector<WrongWord> wrongWordSet;
    protected abstract void printWrongWordSet();
    protected abstract void removeWrongWord();
    public abstract void addWrongWord(Word word);
    public abstract void resetWrongWord();
    public abstract void wrongWordMenu();
}