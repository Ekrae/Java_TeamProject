import java.util.Scanner;

public class Main {
    private static WordBook myBook;
    private static WrongNoteService wrongNote;
    private static QuizService quizService;
    private static Scanner scan = new Scanner(System.in);
    private static final String AUTOSAVE_FILE = "res/autosave_wrongnote.txt";
    private static final String WORDBOOK_FILE = "res/words.txt";
    public static void main(String[] args) {
        System.out.print("사용자 이름을 입력하세요: ");
        String userName = scan.nextLine();
        myBook = new WordBook(userName);
        wrongNote = new WrongNoteService();
        quizService = new QuizService(myBook, wrongNote);
        System.out.println("기본 단어장(" + WORDBOOK_FILE + ")을(를) 불러옵니다...");
        myBook.makeVoc(WORDBOOK_FILE);
        runMenu();
        scan.close();
    }
    public static void runMenu() {
        while (true) {
            printMainMenu();
            String choice = scan.nextLine().trim();
            switch (choice) {
                case "1":
                    manageWordBook();
                    break;
                case "2":
                    manageQuiz();
                    break;
                case "3":
                    wrongNote.wrongWordMenu();
                    break;
                case "4":
                    SaveFunction.saveFileDirectory();
                    break;
                case "0":
                    System.out.println("프로그램을 종료하기 전에 오답노트를 자동 저장합니다...");
                    wrongNote.autoSave(AUTOSAVE_FILE);

                    System.out.println(myBook.userName + "님의 단어장 프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 0-3 사이의 숫자를 입력하세요.");
            }
        }
    }
    public static void printMainMenu() {
        System.out.println("\n" + "=".repeat(25));
        System.out.println("     " + myBook.userName + "의 단어장     ");
        System.out.println("=".repeat(25));
        System.out.println("1. 단어장 관리");
        System.out.println("2. 퀴즈");
        System.out.println("3. 오답노트 관리");
        System.out.println("4. 저장 위치 설정하기");
        System.out.println("0. 종료");
        System.out.print("메뉴 선택: ");
    }
    public static void manageWordBook() {
        while (true) {
            printWordBookMenu();
            String choice = scan.nextLine().trim();
            switch (choice) {
                case "1":
                    myBook.add();
                    break;
                case "2":
                    myBook.edit();
                    break;
                case "3":
                    myBook.delete();
                    break;
                case "4":
                    myBook.searchVoc();
                    break;
                case "5":
                    myBook.searchVoc2();
                    break;
                case "6":
                    System.out.print("불러올 파일 경로를 입력하세요: ");
                    String fileName = scan.nextLine().trim();
                    if (fileName != null && !fileName.isEmpty()) {
                        myBook.makeVoc(fileName);
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 0-6 사이의 숫자를 입력하세요.");
            }
        }
    }
    public static void printWordBookMenu() {
        System.out.println("\n--- 단어장 관리 ---");
        System.out.println("1. 단어 추가");
        System.out.println("2. 단어 수정");
        System.out.println("3. 단어 삭제");
        System.out.println("4. 단어 검색 (정확히 일치)");
        System.out.println("5. 단어 검색 (부분 포함)");
        System.out.println("6. (다른) 파일에서 단어장 불러오기");
        System.out.println("0. 메인 메뉴로");
        System.out.print("선택: ");
    }
    public static void manageQuiz() {
        while (true) {
            printQuizMenu();
            String choice = scan.nextLine().trim();
            switch (choice) {
                case "1":
                    quizService.startMultipleChoiceQuiz();
                    break;
                case "2":
                    quizService.startSubjectiveQuiz();
                    break;
                case "3":
                    quizService.optionMenu();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 0-3 사이의 숫자를 입력하세요.");
            }
        }
    }
    public static void printQuizMenu() {
        System.out.println("\n--- 퀴즈 메뉴 ---");
        System.out.println("1. 객관식 퀴즈 (영 -> 한)");
        System.out.println("2. 주관식 퀴즈 (영 -> 한)");
        System.out.println("3. 퀴즈 옵션 설정");
        System.out.println("0. 메인 메뉴로");
        System.out.print("선택: ");
    }
}