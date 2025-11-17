import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
class QuizService {
    private final WordBook book;
    private final WrongNoteService wrongNote;
    private final Scanner sc = new Scanner(System.in);
    private final Random random = new Random();
    private int numQuiz = 5;  //문제 개수
    private int choiceNum = 4; //객관식 보기 개수
    public QuizService(WordBook book, WrongNoteService wrongNote) {
        this.book = book;
        this.wrongNote = wrongNote;
    }
    public void startSubjectiveQuiz(){
        if(book.size() == 0){
            System.out.println("단어장이 비어있습니다.");
            return;
        }
        System.out.println("====== 주관식 퀴즈 시작 (영 -> 한) ======");
        int total = Math.min(numQuiz, book.size());
        int score = 0;
        for (int i = 1; i <= total; i++){
            Word q = getRandomWord();
            System.out.println("[" + i + "/" + total + "] " + q.getEng() + "의 뜻을 입력하세요: ");
            String answer = sc.nextLine().trim();
            if(answer.equalsIgnoreCase(q.getKor())){
                System.out.println("정답입니다!");
                score++;
            } else {
                System.out.println("오답입니다! 정답은 " + q.getKor());
                wrongNote.addWrongWord(q);
            }
        }
        System.out.println("총 점수 : " + score + "/" + total);
    }
    public void startMultipleChoiceQuiz(){
        if(book.size() == 0){
            System.out.println("단어장이 비어있습니다.");
            return;
        }
        if(book.size() < 2){
            System.out.println("최소 2개 이상의 단어가 필요합니다.");
            return;
        }
        System.out.println("====== 객관식 퀴즈 시작 (영 -> 한) ======");
        int total = Math.min(numQuiz, book.size());
        int score = 0;
        for(int i = 1; i <= total; i++){
            Word correct = getRandomWord();
            Vector<Word> choice = new Vector<>();
            choice.add(correct);
            while (choice.size() < Math.min(choiceNum, book.size())){
                Word example = getRandomWord();
                if(!containsWord(choice, example)){
                    choice.add(example);
                }
            }
            Collections.shuffle(choice);
            System.out.println("[" + i + "/" + total + "] " + correct.getEng() + "의 뜻은?");
            for(int idx = 0; idx < choice.size(); idx++){
                System.out.println(idx+1 + ") " + choice.get(idx).getKor());
            }
            System.out.print("번호 입력: ");
            int userChoice;
            try{
                userChoice = Integer.parseInt(sc.nextLine().trim());
            }catch (NumberFormatException e){
                userChoice = -1;
            }
            if(userChoice >= 1 && userChoice <= choice.size() && choice.get(userChoice - 1) == correct){
                System.out.println("정답입니다!");
                score++;
            }else{
                System.out.println("오답입니다! 정답: " + correct.getKor());
                wrongNote.addWrongWord(correct);
            }
        }
        System.out.println("점수: " + score + "/" + total);
    }
    public void optionMenu(){
        while(true){
            System.out.println("====== 퀴즈 옵션 설정 ======");
            System.out.println("1. 한 번에 출제할 문제 수 (현재: " + numQuiz + ")");
            System.out.println("2. 객관식 보기 개수 (현재: " + choiceNum + ")");
            System.out.println("3. 옵션 설정 종료");
            System.out.print("선택: ");
            String input = sc.nextLine().trim();
            switch (input){
                case "1" -> {
                    System.out.print("문제 개수 입력: ");
                    numQuiz = parsePostiveInt(numQuiz);
                    System.out.println("문제 수가 " + numQuiz + "개로 설정되었습니다.");
                }
                case "2" -> {
                    System.out.print("보기 개수 입력(2개 이상): ");
                    int val = parsePostiveInt(choiceNum);
                    if(val < 2){
                        System.out.println("2개 이상이어야 합니다.");
                    }else{
                        choiceNum = val;
                        System.out.println("보기 수가 " + choiceNum + "개로 설정되었습니다.");
                    }
                }
                case "3" -> {
                    System.out.println("옵션 설정을 종료합니다.");
                    return;
                }
                default -> System.out.println("1-3 중에 선택하세요.");
            }
        }
    }
    private int parsePostiveInt(int defaultVal){
        String line = sc.nextLine().trim();
        try{
            int v = Integer.parseInt(line);
            if(v > 0){
                return v;
            } else {
                System.out.println("0보다 큰 값을 입력해야 합니다.");
                return defaultVal;
            }
        } catch (NumberFormatException e){
            System.out.println("올바르지 않은 값입니다. 숫자를 입력하세요.");
            return defaultVal;
        }
    }
    private Word getRandomWord(){
        int idx = random.nextInt(book.size());
        return book.get(idx);
    }
    private boolean containsWord(Vector<Word> list, Word w){
        for(Word x : list){
            if(x.getEng().equals(w.getEng())) return true;
        }
        return false;
    }
}