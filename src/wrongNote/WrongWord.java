package wrongNote;

import mainProject.Word;
public class WrongWord extends Word {
    private int mistakeCount;

    public WrongWord(String eng, String kor, int mistakeCount) {
        super(eng, kor);
        this.mistakeCount = mistakeCount;
    }

    public int getMistakeCount() {
        return mistakeCount;
    }

    public void setMistakeCount(int mistakeCount) {
        this.mistakeCount = mistakeCount;
    }
//틀리게 적었던 단어들 추가하는 vec은 만들지 말지 고민

    /**
     * 내부에서 만들어버리기
     * @param eng : 영어로 뭔지
     * @param kor : 한글로 뭔지
     */
    public WrongWord(String eng, String kor) {
        this(eng,kor,1); //만들어졌단 것 자체가 1회 틀림.
    }
    /**
     * 외부에서 받아와서 생성
     * @param word : 단어 객체를 오답단어 클래스로 깊은복사
     */
    public WrongWord(Word word) {
        this(word.getEng(),word.getKor(),1);
    }

    @Override
    public String toString() {
       String str = super.toString();
       str += " | 틀린 횟수: "+ mistakeCount;
       return str;
    }
}
