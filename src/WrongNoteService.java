import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;
public class WrongNoteService extends WrongAnswerNotes {
    private Scanner scanner = new Scanner(System.in);

    @Override
    protected void printWrongWordSet() {
        if (wrongWordSet.isEmpty()){
            System.out.println("오답노트가 비어있습니다.");
        }
        for (WrongWord wrongWord : wrongWordSet) {
            System.out.println(wrongWord);
        }
    }


    public WrongNoteService() {
        this.wrongWordSet = new Vector<WrongWord>();
    }

    @Override
    protected void removeWrongWord() {

        System.out.print("삭제할 영단어를 입력하세요 : ");
        String targetWord = scanner.next().trim(); //공백도 제거해서 전달
        WrongWord wrongWord = isContains(targetWord);

        if (wrongWord == null) {
            System.out.println(targetWord + "는 오답노트에 포함되어 있지 않습니다.");
        } else {
            System.out.println(wrongWord.getEng() + "를 삭제했습니다.");
            wrongWordSet.remove(wrongWord);
        }

    }
    private WrongWord isContains(String a) {
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
        if (wrongWord != null) {
            wrongWord.setMistakeCount(wrongWord.getMistakeCount() + 1); //기본적으로 주소로 컨트롤
        } else {
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


        while (true) {
            System.out.println("=".repeat(20));
            System.out.print("오답노트 메뉴입니다. 1.오답노트 보이기 2.저장하기 3.불러오기 4.오답노트에서 단어 제거 5.오답노트 초기화 0.오답노트 메뉴창 종료\n:");

            switch (scanner.next()) {
                case "1" -> printWrongWordSet();
                case "2" -> writeFile();
                case "3" -> readFile();
                case "4" -> removeWrongWord();
                case "5" -> resetWrongWord();
                case "0" -> {
                    return;
                }
                default -> System.out.println("0번부터 5번 사이 값만을 입력해주세요!");
            }
        }

    }

    /**
     * 파일 불러오기. 기존 파일 삭제될 가능성 존재
     */
    private void readFile() {
        if (!wrongWordSet.isEmpty()) {

            System.out.print("오답노트 파일을 불러오면 현재 오답노트는 제거됩니다. 그래도 진행하시겠습니까? ('네'를 입력해야만 진행됩니다.) : ");
            if (!scanner.next().equals("네")) {
                System.out.println("파일 불러오기를 종료합니다.");
                return;//파일 삭제의 위험-> 불러오기 종료
            }
        } //현재 파일 비우고 불러오기 진행
        Vector<WrongWord> temp = (Vector<WrongWord>) wrongWordSet.clone();
        resetWrongWord(); //근데 잘못되면 기존거 다시 가져오기


        System.out.print("불러올 파일명과 주소 입력 : ");
        String readFilename = scanner.next().trim();

        try (java.io.BufferedReader br = java.nio.file.Files.newBufferedReader(
                java.nio.file.Paths.get(readFilename), java.nio.charset.StandardCharsets.UTF_8)) {

            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                line = line.trim();
                if (line.isEmpty()) continue;

                // 탭 기준 3필드: eng \t kor \t count
                String[] t = line.split("\t", -1);
                if (t.length < 3) {
                    System.out.println("잘못된 형식의 파일입니다! (행 " + lineNo + ")");
                    wrongWordSet = temp; // 복구
                    return;
                }
                String eng = t[0];
                String kor = t[1];
                int cnt;
                try {
                    cnt = Integer.parseInt(t[2]);
                } catch (NumberFormatException nfe) {
                    System.out.println("잘못된 형식의 파일입니다! (행 " + lineNo + ", 숫자 아님: " + t[2] + ")");
                    wrongWordSet = temp; // 복구
                    return;
                }
                wrongWordSet.add(new WrongWord(eng, kor, cnt));
            }
            System.out.println("오답노트 불러오기가 완료되었습니다.");

        } catch (java.io.FileNotFoundException e) {
            System.out.println("해당 파일을 찾을 수 없습니다!");
            wrongWordSet = temp;
        } catch (java.io.IOException e) {
            System.out.println("파일 읽기 중 오류: " + e.getMessage());
            wrongWordSet = temp;
        }
    }




    /**
     * 파일 저장. 출력 형식과 동일하게 감
     */
    private void writeFile() {
        if (wrongWordSet.isEmpty()){
            System.out.println("오답노트의 내용이 없습니다.");
            return;
        }
        System.out.print("저장시 쓸 파일명과 주소 입력 : ");
        String saveFilename = scanner.next();
        try (PrintWriter outFile = new PrintWriter(saveFilename)) {
            for (WrongWord wrongWord : wrongWordSet) {
                outFile.println(wrongWord.getEng() + "\t" + wrongWord.getKor() + "\t" + wrongWord.getMistakeCount()); // 공백으로 구분해서 저장
            }
            System.out.println("오답노트 저장이 완료되었습니다.");
        } catch (FileNotFoundException e) {
            System.err.println("파일 주소 형식이 이상합니다!");
        }
    }

    public void autoSave(String filename) {
        if (wrongWordSet.isEmpty()){
            return;
        }
        try (PrintWriter outFile = new PrintWriter(filename)) {
            for (WrongWord wrongWord : wrongWordSet) {
                outFile.println(wrongWord.getEng() + " " + wrongWord.getKor() + " " + wrongWord.getMistakeCount());
            }
            System.out.println("오답노트가 " + filename + "에 자동 저장되었습니다.");
        } catch (FileNotFoundException e) {
            System.err.println("자동 저장 실패: " + filename + " 경로를 확인하세요.");
        }
    }
}
