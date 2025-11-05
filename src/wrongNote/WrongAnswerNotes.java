package wrongNote;//package 설정

import mainProject.Word;

import java.util.Vector;

/**
 * 해당 추상클래스는
 */
public abstract class WrongAnswerNotes {
    /**
     * 오답노트 클래스에서 쓸 오답노트 필드명
     */
    public Vector<WrongWord> wrongWordSet;

    /**
     * 오답노트 출력
     * WrongWordSet을 형식에 맞게 출력. toString override를 쓸지 메서드 만들지 고민하기.
     */
    protected abstract void printWrongWordSet();

    /**
     * 오답단어 제거하기. 성공, 실패는 메서드 내부 처리
     * */
    protected abstract void removeWrongWord();

    /**
     * 오답단어 추가하기. 오답 나온 단어에 이 메서드만 써주면 됨.
     */
    public abstract void addWrongWord(Word word);

    /**
     * 오답노트 초기화.
     */
    public abstract void resetWrongWord();
    /**
     * 아직 어떻게 될 지 모름. 만약을 대비해서, 오답노트 내부 메뉴메서드. 오답노트 내부에서 관리하면, 전부 private으로 관리
     */
    public abstract void wrongWordMenu();





}
