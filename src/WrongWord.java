class WrongWord extends Word {
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
    public WrongWord(String eng, String kor) {
        this(eng,kor,1); //만들어졌단 것 자체가 1회 틀림.
    }
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