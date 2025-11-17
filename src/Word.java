class Word {
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
    public String getKor() {
        return kor;
    }
    @Override
    public String toString() {
        return eng + " : " + kor;
    }
}