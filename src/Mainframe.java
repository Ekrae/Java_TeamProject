import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class Mainframe extends JFrame {
    Container frame = getContentPane(); //최고 컨테이너
    
    JPanel northPanel; //위쪽에 있는 ui들
    JTextField text;
    JButton searchBtn;
    JRadioButton asc,desc;

    JTable table;
    DefaultTableModel model;
    String[] header = {"영단어","뜻","틀린 횟수"};

    WrongNoteService manager;

    
    public Mainframe(WrongNoteService manager){
        this.manager = manager;//메인 클래스의 매니저 이용
        this.setTitle("오답노트 단어장");
        this.setSize(500,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); //화면 중앙에
        initLayout(); //여러가지 초기화
        this.setVisible(true);
    }

    private void initLayout() {
        initCenterPanel(); // 중앙패널 모델 초기화
        initMenuBar(); //메뉴바 초기화
        initTableData(); // 모델의 단어 초기화
        initWestPanel(); // 왼편 버튼들 초시화
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // "파일" 메뉴 생성 (저장,불러오기)
        JMenu fileMenu = new JMenu("파일");
        JMenuItem saveItem = new JMenuItem("저장하기");
        saveItem.addActionListener(act ->manager.writeFile(this)); //실제 실행은 한줄이면 됨. 람다식 이용

        JMenuItem roadItem = new JMenuItem("불러오기");
        roadItem.addActionListener(act-> {
            manager.readFile(this);
            initTableData();
        });
        fileMenu.add(saveItem);
        fileMenu.add(roadItem);

        // "보기" 메뉴 생성
        JMenu viewMenu = new JMenu("보기");
        JMenuItem refreshItem = new JMenuItem("새로고침");
        refreshItem.addActionListener(act -> {
            initTableData();
            manager.printWrongWordSet();
        }); // 테이블 다시 불러오기

        viewMenu.add(refreshItem);

        // 메뉴바에 메뉴 추가
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);


        this.setJMenuBar(menuBar); // 프레임에 메뉴바 설정

    }


    private void initTableData() {
        removeTableData();
        recallWrongNote();
    }

    private void recallWrongNote() {   //기능에서 단어장 내용 불러오기
        for (WrongWord W : manager.wrongWordSet){
            model.addRow(new String[]{W.eng,W.kor, String.valueOf(W.getMistakeCount())});
        }
    }

    private void removeTableData() {    //테이블 비우기
        if (model.getRowCount()>0){
            model = new DefaultTableModel(header,0);
            table.setModel(model); //테이블 싹 비우고 단어 하나만.
        }
    }

    private void initCenterPanel() {
        this.model = new DefaultTableModel(header,0);//모델 제작
        this.table = new JTable(model);
        frame.setLayout(new BorderLayout()); // 여기서 레이아웃 설정

        frame.add(new JScrollPane(table),BorderLayout.CENTER);
    }
    private void initWestPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //단어제거버튼
        JButton removeButton = new JButton("일부 단어 제거");
        removeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();  // 최근 클릭한 행 인덱스
            if (selectedRow != -1) {  // 선택된 행이 있을 경우
                String eng = (String) model.getValueAt(selectedRow, 0);  // 영단어 가져와서, 겹치는거 제거
                manager.removeWrongWord(eng);

                model.removeRow(selectedRow);         // 해당 행 삭제
                JOptionPane.showMessageDialog(this,"선택한 단어를 제거했습니다.");
            } else {
                JOptionPane.showMessageDialog(this,"선택된 단어가 없습니다.");
            }
        });
        //초기화버튼
        JButton resetButton = new JButton("오답노트 초기화");
        resetButton.addActionListener(e -> {manager.resetWrongWord();initTableData();});
        //총 틀린수 출력버튼
        JButton countButton = new JButton("총 틀린 단어 수 출력");
        countButton.addActionListener(e -> manager.printWrongWordCount(this));
        Dimension bs = new Dimension(150, 40);
        //버튼들 크기 이쁘게 고정하기
        removeButton.setPreferredSize(bs);
        resetButton.setPreferredSize(bs);
        countButton.setPreferredSize(bs);
        removeButton.setMaximumSize(bs);
        resetButton.setMaximumSize(bs);
        countButton.setMaximumSize(bs);

        //패널에 버튼 붙이기
        buttonPanel.add(removeButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(countButton);

        // 하단 메뉴 버튼. 다른분 gui와 상호작용이라 일단 방치
        JButton backButton = new JButton("메뉴로 돌아가기");
        //backButton.addActionListener(e -> returnToMenu());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);

        // 전체 레이아웃 구성

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);

    }




}
