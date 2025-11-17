import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SaveFunction {
    static Scanner scanner = new Scanner(System.in);
    /**
     * 함수 호출시 해당 주소가 유효한지 판별함
     */
    public static boolean isAddress(String fileAddress){
        if (!fileAddress.contains("/")){
            System.out.print("/가 포함되지 않은 경로는 무조건 최상위 폴더에 저장됩니다. 괜찮습니까? ('네'일때만 작동)");
            String ans = scanner.next().trim();
            if (ans.equals("네")){
                return true;    //최상위 폴더는 항상 존재함...
            }
        }

        try(PrintWriter outFile = new PrintWriter(fileAddress)){
            outFile.println("해당 파일 위치에 저장됩니다.");
            return true;
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println(fileAddress+"는 정상적인 주소가 아닙니다!");
            return false;
        }
    }
    public static String saveFileDirectory(){

        String ans = scanner.next();
        if (isAddress(ans)){
            return ans;
        }
        System.out.println("제발제발");
        return null;
    }
}
