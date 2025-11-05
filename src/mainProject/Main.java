package mainProject;// mainProject.Main.java
import wrongNote.WrongNoteService;
// ============================================================================
// [프로젝트 운영 규칙 요약]
// - 구현 파트:
//   • 단어 CRUD + 파일 저장/불러오기 .......................... [신정우]
//   • 퀴즈 모드(주관식/객관식) ................................. [남윤서]
//   • 오답노트 저장 ............................................ [이창민]
//   • 콘솔 메뉴/진입점 + 종료 시 자동 저장 ..................... [김윤찬]
//   • (+) 기타 유용한 추가 기능 .................................
// ============================================================================
//
// 명령어:
// - 추가(신정우): a(추가) e(수정) d(삭제) s(검색) ls(목록) l(불러오기) w(저장)
// - 퀴즈(남윤서): qz s(주관식) | qz o(객관식) | qz opt(옵션)
// - 오답(이창민): wn ls | wn clr | wn save | wn load
// - 메뉴(김윤찬): q(종료; 자동 저장)
// ============================================================================


import java.util.Scanner;


/** Menu.run()을 호출해 콘솔 메뉴를 실행 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 준비
        //WordBook book = new WordBook();                  // 추가
        //QuizService quiz = new QuizService(book);        // 퀴즈
        WrongNoteService wrong = new WrongNoteService(); // 오답노트
        wrong.addWrongWord(new Word("consider","고려하다"));
        wrong.addWrongWord(new Word("consider","고려하다"));
        wrong.addWrongWord(new Word("consider","고려하다"));
        wrong.addWrongWord(new Word("consider","고려하다"));
        wrong.wrongWordMenu();
        // 메뉴 실행
        //new Menu(sc, book, quiz, wrong).run();  //Scanner sc
    }
}