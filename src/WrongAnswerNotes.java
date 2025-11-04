//package 설정

/**
 * 해당 추상클래스는
 */
public abstract class WrongAnswerNotes {
    /**
     * 오답노트 클래스에서 쓸 오답노트 필드명
     */
    public Word[] WrongWordSet;

    /**
     * 오답노트 출력
     * WrongWordSet을 형식에 맞게 출력. toString override를 쓸지 메서드 만들지 고민하기.
     */
    public abstract void printWrongWordSet();

    /**
     * 오답단어 세팅하기
     */
    public abstract void setWrongWord();

    /**
     * 오답단어 제거하기
     */
    public abstract void removeWrongWord();

    /**
     * 오답노트 초기화.
     */
    public abstract void resetWrongWord();
    /**
     * 아직 어떻게 될 지 모름. 만약을 대비해서, 오답노트 내부 메뉴메서드. 오답노트 내부에서 관리하면, 전부 private으로 관리
     */
    public abstract void wrongWordMenu();





}
