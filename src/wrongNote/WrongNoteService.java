package wrongNote;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;
import mainProject.Word; //외부와 상호작용 위함
public class WrongNoteService extends WrongAnswerNotes{
    @Override
    protected void printWrongWordSet() {
        for (WrongWord wrongWord : wrongWordSet){
            System.out.println(wrongWord);
        }
    }

    public WrongNoteService() {
        this.wrongWordSet = new Vector<WrongWord>();
    }

    @Override
    protected void removeWrongWord() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("삭제할 영단어를 입력하세요 : ");
            String targetWord = sc.next().trim(); //공백도 제거해서 전달
            WrongWord wrongWord = isContains(targetWord);

            if (wrongWord == null){
                System.out.println(targetWord+"는 오답노트에 포함되어 있지 않습니다.");
            }else{
                System.out.println(wrongWord.getEng()+"를 삭제했습니다.");
                wrongWordSet.remove(wrongWord);
            }
        }
    }

    /**
     * 이름으로 단어 찾기
     * @param a : 입력받는 단어
     * @return :이름이 동일한 단어의 주소 리턴
     */
    private WrongWord isContains(String a){
        for (WrongWord wrongWordFind : wrongWordSet) {
            if (wrongWordFind.getEng().equals(a))
                return wrongWordFind;
        }
        return null;
    }

    //만약 또 틀린거면 그냥 틀린 횟수를 여기서 올려버림. 기본적으로 자동 추가
    @Override
    public void addWrongWord(Word word) { //equal 메소드 재정의 and contains가 best. 다만 충돌 가능성 있어서 그냥 메서드 만듦
        WrongWord wrongWord = isContains(word.getEng());
        if (wrongWord!= null){
                wrongWord.setMistakeCount(wrongWord.getMistakeCount()+1); //기본적으로 주소로 컨트롤
        }else{
            wrongWord = new WrongWord(word);
            wrongWordSet.add(wrongWord); //없다면 추가
        }
    }

    @Override
    public void resetWrongWord() {
        wrongWordSet.clear();
        System.out.println("기존 오답노트를 초기화합니다.");
    }
    @Override
    public void wrongWordMenu() {
        try(Scanner scanner = new Scanner(System.in)){

            while (true){
                System.out.print("오답노트 메뉴입니다. 1.오답노트 보이기 2.저장하기 3.불러오기 4.오답노트에서 단어 제거 5.오답노트 초기화 6.오답노트 메뉴창 종료\n:");

                switch (scanner.next()){
                    case "1"-> printWrongWordSet();
                    case "2"-> writeFile();
                    case "3"-> readFile();
                    case "4"-> removeWrongWord();
                    case "5" ->resetWrongWord();
                    case "6"-> {return;}
                    default -> System.out.println("1번부터 6번 사이 값만을 입력해주세요!");
                }
            }
        }
    }

    /**
     * 파일 불러오기. 기존 파일 삭제될 가능성 존재
     */
    private void readFile(){
        if (!wrongWordSet.isEmpty()){
            Scanner scanner = new Scanner(System.in);
            System.out.println("오답노트 파일을 불러오면 현재 오답노트는 제거됩니다. 그래도 진행하시겠습니까? ('네'를 입력해야만 진행됩니다.)");
            if (!scanner.next().equals("네")){
                System.out.println("파일 불러오기를 종료합니다.");
                return;//파일 삭제의 위험-> 불러오기 종료
            }
        } //현재 파일 비우고 불러오기 진행
        resetWrongWord();

        try(Scanner sc = new Scanner(System.in)){
            System.out.print("불러올 파일명만 입력 : ");
            String readFilename = "res/WrongNotes/" +sc.nextLine()+".txt";

            try(Scanner readFile = new Scanner(new File(readFilename))){
                while (readFile.hasNext()){
                    String eng = readFile.next();String kor = readFile.next();int mis = readFile.nextInt();
                    wrongWordSet.add(new WrongWord(eng,kor,mis));
                }
            } catch (FileNotFoundException e) {
                System.out.println("해당 파일을 찾을 수 없습니다!");
            }
        }
    }

    /**
     * 파일 저장. 출력 형식과 동일하게 감
     */
    private void writeFile(){
        try(Scanner sc = new Scanner(System.in)){
            System.out.print("저장시 쓸 파일명만 입력 : ");
            String saveFilename = "res/WrongNotes/" +sc.nextLine()+".txt";
            try(PrintWriter outFile = new PrintWriter(saveFilename)){
                for (WrongWord wrongWord : wrongWordSet) {
                    outFile.println(wrongWord.getEng()+" "+wrongWord.getKor()+" "+wrongWord.getMistakeCount()); // 공백으로 구분해서 저장
                }
            } catch (FileNotFoundException e) {
                System.err.println("이거 실행되면 안됨");
            }
        }
    }
}
