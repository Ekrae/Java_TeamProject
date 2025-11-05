package mainProject;

//package week10.lab04;
public class Word {
    String eng;
    String kor;

    public Word(String eng, String kor) {
        super();
        this.eng = eng;
        this.kor = kor;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getKor() {
        return kor;
    }

    public void setKor(String kor) {
        this.kor = kor;
    }

    @Override
    public String toString() {
        return eng + " : " + kor;
    }
}