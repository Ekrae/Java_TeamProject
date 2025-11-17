import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
class WordBook {
    String userName;
    Vector<Word> voc = new Vector<>();
    static Scanner scan = new Scanner(System.in);
    WordBook(String userName) {
        this.userName = userName;
    }
    public int size() {
        return voc.size();
    }
    public Word get(int index) {
        if (index < 0 || index >= voc.size()) {
            return null; // 인덱스 범위 밖
        }
        return voc.get(index);
    }
    boolean delete(){
        System.out.println("------ 단어 삭제 ------");
        System.out.println("삭제할 단어를 입력하세요.");

        Word word = find();
        if(word == null) {
            System.out.println("입력하신 단어를 찾을 수 없습니다.");
            return false;
        }
        else {
            System.out.println("("+word.getEng() + " : " + word.getKor()+")를 삭제합니다.");
            voc.remove(word);
            return true;
        }
    }
    void edit(){
        System.out.println("------ 단어 수정 ------");
        boolean delete = delete();
        if(delete) {
            add();
        }
    }
    void add(){
        System.out.println("추가할 단어를 입력하세요.(영-한 tab으로 구분)");
        String str = scan.nextLine();
        String[] temp = str.split("\t");
        if (temp.length < 2) {
            System.out.println("입력 형식이 잘못되었습니다. (예: apple [Tab] 사과)");
            return;
        }

        String eng = temp[0].trim();
        String kor = temp[1].trim();
        for(Word s : voc){
            if(s.getEng().equalsIgnoreCase(eng)){
                System.out.println("이미 존재하는 단어입니다.");
                return;
            }
        }
        this.addWord(eng, kor);
        System.out.println("단어가 추가되었습니다.");
    }
    void addWord(String eng, String kor) {
        this.voc.add(new Word(eng, kor));
    }
    void makeVoc(String fileName) {
        try (Scanner file = new Scanner(new File(fileName))) {
            while (file.hasNext()) {
                String str = file.nextLine();
                String[] temp = str.split("\t");
                if (temp.length >= 2) {
                    this.addWord(temp[0].trim(), temp[1].trim());
                }
            }
            System.out.println(this.userName + "님이 단어장 생성이 완료되었습니다.");
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없음: " + fileName);
        }
    }
    public void searchVoc2() {
        System.out.println("------ 단어 검색 2 ------");
        System.out.print("검색할 부분 단어를 입력하세요 (영단어) : ");
        String sWord = scan.nextLine().trim();
        boolean found = false;
        for (Word w : voc) {
            if (w.getEng().toLowerCase().startsWith(sWord.toLowerCase())) {
                System.out.println(w.getEng() +" : "+w.getKor());
                found = true;
            }
        }
        if (!found) System.out.println("찾는 단어가 없습니다.");
    }
    public void searchVoc() {
        System.out.println("------ 단어 검색 ------");
        System.out.print("검색할 단어를 입력하세요 (영단어) : ");
        Word word = find();
        if(word == null) System.out.println("입력하신 단어를 찾을 수 없습니다.");
        else {
            System.out.println(word.getEng() + " : " + word.getKor());
        }
    }
    public Word find() {
        String sWord = scan.nextLine().trim();
        for (Word w : voc) {
            if (w.getEng().equalsIgnoreCase(sWord)) {
                return w;
            }
        }
        return null;
    }
     void spellCollector(){
        System.out.println("------ 오타 수정 ------");
        System.out.println("오타를 수정할 단어의 영/한 여부를 입력해 주세요.");
        System.out.println("(1: 영어 2: 한글)");
        int lan = scan.nextInt();
        scan.nextLine();

        switch (lan){
            case 1:
                System.out.println("오타를 수정할 단어의 영단어를 입력해 주세요.");
                Word sEWord = find(); //여기서 입력을 못받음
                if(sEWord == null) {
                    System.out.println("입력하신 단어를 찾을 수 없습니다.");
                    break;
                }
                else {

                    System.out.println("올바른 단어를 입력해 주세요.");
                    String cEWord = scan.next().trim();
                    int sIdx = voc.indexOf(sEWord);
                    Word sCorrect = new Word (cEWord, sEWord.getKor());
                    voc.set(sIdx,sCorrect );
                    System.out.println("수정완료: "+sEWord.getEng() + " : " + sEWord.getKor());
                    break;
                }
            case 2:
                System.out.println("오타를 수정할 단어의 영단어를 입력해 주세요.");
                Word sKWord = find();
                if(sKWord == null) {
                    System.out.println("입력하신 단어를 찾을 수 없습니다.");
                    break;
                }
                else {

                    System.out.println("올바른 단어를 입력해 주세요.");
                    String cKWord = scan.next().trim();
                    int kIdx = voc.indexOf(sKWord);
                    Word kCorrect = new Word (cKWord, sKWord.getKor());
                    voc.set(kIdx,kCorrect );
                    System.out.println(sKWord.getEng() + " : " + sKWord.getKor());
                    break;
                }

            default:
                System.out.println("1/2로 입력해 주세요.");
        }



    }
}