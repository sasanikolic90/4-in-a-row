import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.Graphics;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class Connect4 extends JFrame implements ActionListener {
    private static JTextField txtRed;
    private static JTextField txtYellow;
    private static JTextField txtSum;
    private JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    private static JPanel panelBody;

    MenuItem                newMI, exitMI, redMI, yellowMI, humanPlayerMI, humanVSRandomPlayerMI, humanVSMinMaxPlayerMI;
    public static int[][]   theArray;
    boolean                 end = false;
    boolean                 gameStart;
    public static final int BLANK = 0;
    public static final int RED = 1;
    public static final int YELLOW = 2;
    public static final int HUMANPLAYERS = 0;
    public static final int HUMANVSRANDOMPLAYERS = 1; // Geek always starts, than random program
    public static final int HUMANVSMINMAXPLAYERS = 2; // Geek always starts, than random program

    public static final int MAXROW = 6;     // 6 rows
    public static final int MAXCOL = 7;     // 7 columns

    public static boolean canPlay = true;

    public static final String SPACE = "                  "; // 18 spaces

    //Default properties
    int activeColour = RED;
    int activePlayers = HUMANPLAYERS;

    public static int sumRed = 0;
    public static int sumYellow = 0;
    public static int sestevekSum = 0;
    private JTextField txtTimeOfPlay;

    //Players
    public RandomPlayer randomPlayer;

    public Connect4(){
        // TODO Auto-generated constructor stub
        setTitle("App4");
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setForeground(Color.ORANGE);

        MenuBar mbar = new MenuBar();

        Menu fileMenu = new Menu("File");
        newMI = new MenuItem("New");
        newMI.addActionListener(this);
        fileMenu.add(newMI);
        exitMI = new MenuItem("Exit");
        exitMI.addActionListener(this);
        fileMenu.add(exitMI);
        mbar.add(fileMenu);

        Menu playersMenu = new Menu("Players");
        humanPlayerMI = new MenuItem("Geek vs Geek");
        humanPlayerMI.addActionListener(this);
        playersMenu.add(humanPlayerMI);
        humanVSRandomPlayerMI = new MenuItem("Geek vs Random");
        humanVSRandomPlayerMI.addActionListener(this);
        playersMenu.add(humanVSRandomPlayerMI);
        humanVSMinMaxPlayerMI = new MenuItem("Geek vs MinMax");
        humanVSMinMaxPlayerMI.addActionListener(this);
        playersMenu.add(humanVSMinMaxPlayerMI);
        mbar.add(playersMenu);

        Menu optMenu = new Menu("Options");
        redMI = new MenuItem("Red starts");
        redMI.addActionListener(this);
        optMenu.add(redMI);
        yellowMI = new MenuItem("Yellow starts");
        yellowMI.addActionListener(this);
        optMenu.add(yellowMI);
        mbar.add(optMenu);

        setMenuBar(mbar);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.ORANGE);
        buttonPanel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        GridBagLayout gbl_buttonPanel = new GridBagLayout();
        gbl_buttonPanel.columnWidths = new int[]{97, 97, 97, 97, 97, 97, 97, 0};
        gbl_buttonPanel.rowHeights = new int[]{29, 0};
        gbl_buttonPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_buttonPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        buttonPanel.setLayout(gbl_buttonPanel);

        JButton btn1 = new JButton("-1-");
        GridBagConstraints gbc_btn1 = new GridBagConstraints();
        gbc_btn1.insets = new Insets(0, 0, 0, 5);
        gbc_btn1.anchor = GridBagConstraints.NORTH;
        gbc_btn1.gridx = 0;
        gbc_btn1.gridy = 0;
        buttonPanel.add(btn1, gbc_btn1);
        btn1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                humanPlay(1);
            }
        });

        JButton btn2 = new JButton("-2-");
        GridBagConstraints gbc_btn2 = new GridBagConstraints();
        gbc_btn2.insets = new Insets(0, 0, 0, 5);
        gbc_btn2.gridx = 1;
        gbc_btn2.gridy = 0;
        buttonPanel.add(btn2, gbc_btn2);
        btn2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                humanPlay(2);
            }
        });

        JButton btn3 = new JButton("-3-");
        GridBagConstraints gbc_btn3 = new GridBagConstraints();
        gbc_btn3.insets = new Insets(0, 0, 0, 5);
        gbc_btn3.gridx = 2;
        gbc_btn3.gridy = 0;
        buttonPanel.add(btn3, gbc_btn3);
        btn3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                humanPlay(3);
            }
        });

        JButton btn4 = new JButton("-4-");
        GridBagConstraints gbc_btn4 = new GridBagConstraints();
        gbc_btn4.insets = new Insets(0, 0, 0, 5);
        gbc_btn4.gridx = 3;
        gbc_btn4.gridy = 0;
        buttonPanel.add(btn4, gbc_btn4);
        btn4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                humanPlay(4);
            }
        });

        JButton btn5 = new JButton("-5-");
        GridBagConstraints gbc_btn5 = new GridBagConstraints();
        gbc_btn5.insets = new Insets(0, 0, 0, 5);
        gbc_btn5.gridx = 4;
        gbc_btn5.gridy = 0;
        buttonPanel.add(btn5, gbc_btn5);
        btn5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                humanPlay(5);
            }
        });

        JButton btn6 = new JButton("-6-");
        GridBagConstraints gbc_btn6 = new GridBagConstraints();
        gbc_btn6.insets = new Insets(0, 0, 0, 5);
        gbc_btn6.gridx = 5;
        gbc_btn6.gridy = 0;
        buttonPanel.add(btn6, gbc_btn6);
        btn6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                humanPlay(6);
            }
        });

        JButton btn7 = new JButton("-7-");
        GridBagConstraints gbc_btn7 = new GridBagConstraints();
        gbc_btn7.gridx = 6;
        gbc_btn7.gridy = 0;
        buttonPanel.add(btn7, gbc_btn7);
        btn7.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                humanPlay(7);
            }
        });

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.ORANGE);
        footerPanel.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
        getContentPane().add(footerPanel, BorderLayout.SOUTH);

        JLabel lblCreator = new JLabel("Sasa Nikolic");
        lblCreator.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(lblCreator);

        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(0, 0, 0)));
        infoPanel.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(infoPanel, BorderLayout.EAST);
        GridBagLayout gbl_infoPanel = new GridBagLayout();
        gbl_infoPanel.columnWidths = new int[]{40, 27, 0};
        gbl_infoPanel.rowHeights = new int[]{28, 0, 0, 0, 0, 0, 0, 0};
        gbl_infoPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_infoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        infoPanel.setLayout(gbl_infoPanel);

        JLabel lblInfo = new JLabel("Info:");
        lblInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblInfo.setFont(new Font("Lucida Grande", Font.BOLD, 18));
        GridBagConstraints gbc_lblInfo = new GridBagConstraints();
        gbc_lblInfo.anchor = GridBagConstraints.EAST;
        gbc_lblInfo.insets = new Insets(0, 0, 5, 5);
        gbc_lblInfo.gridx = 0;
        gbc_lblInfo.gridy = 0;
        infoPanel.add(lblInfo, gbc_lblInfo);

        JLabel lblRed = new JLabel("Red:");
        lblRed.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblRed = new GridBagConstraints();
        gbc_lblRed.anchor = GridBagConstraints.EAST;
        gbc_lblRed.insets = new Insets(0, 0, 5, 5);
        gbc_lblRed.gridx = 0;
        gbc_lblRed.gridy = 2;
        infoPanel.add(lblRed, gbc_lblRed);

        txtRed = new JTextField();
        txtRed.setEditable(false);
        GridBagConstraints gbc_txtRed = new GridBagConstraints();
        gbc_txtRed.insets = new Insets(0, 0, 5, 0);
        gbc_txtRed.anchor = GridBagConstraints.NORTHWEST;
        gbc_txtRed.gridx = 1;
        gbc_txtRed.gridy = 2;
        infoPanel.add(txtRed, gbc_txtRed);
        txtRed.setColumns(10);
        txtRed.setText("" + sumRed);

        JLabel lblYellow = new JLabel(" Yellow:");
        lblYellow.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblYellow = new GridBagConstraints();
        gbc_lblYellow.anchor = GridBagConstraints.EAST;
        gbc_lblYellow.insets = new Insets(0, 0, 5, 5);
        gbc_lblYellow.gridx = 0;
        gbc_lblYellow.gridy = 3;
        infoPanel.add(lblYellow, gbc_lblYellow);

        txtYellow = new JTextField();
        txtYellow.setEditable(false);
        GridBagConstraints gbc_txtYellow = new GridBagConstraints();
        gbc_txtYellow.insets = new Insets(0, 0, 5, 0);
        gbc_txtYellow.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtYellow.gridx = 1;
        gbc_txtYellow.gridy = 3;
        infoPanel.add(txtYellow, gbc_txtYellow);
        txtYellow.setColumns(10);
        txtYellow.setText("" + sumYellow);

        JLabel lblSum = new JLabel("Sum:");
        lblSum.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblSum = new GridBagConstraints();
        gbc_lblSum.anchor = GridBagConstraints.EAST;
        gbc_lblSum.insets = new Insets(0, 0, 5, 5);
        gbc_lblSum.gridx = 0;
        gbc_lblSum.gridy = 4;
        infoPanel.add(lblSum, gbc_lblSum);

        txtSum = new JTextField();
        txtSum.setEditable(false);
        GridBagConstraints gbc_txtSum = new GridBagConstraints();
        gbc_txtSum.insets = new Insets(0, 0, 5, 0);
        gbc_txtSum.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtSum.gridx = 1;
        gbc_txtSum.gridy = 4;
        infoPanel.add(txtSum, gbc_txtSum);
        txtSum.setColumns(10);
        txtSum.setText("" + this.sestevekSum);

        JLabel playTime = new JLabel(" Play time:");
        GridBagConstraints gbc_playTime = new GridBagConstraints();
        gbc_playTime.anchor = GridBagConstraints.EAST;
        gbc_playTime.insets = new Insets(0, 0, 0, 5);
        gbc_playTime.gridx = 0;
        gbc_playTime.gridy = 6;
        infoPanel.add(playTime, gbc_playTime);

        txtTimeOfPlay = new JTextField();
        txtTimeOfPlay.setEditable(false);
        GridBagConstraints gbc_txtCasIzvedbe = new GridBagConstraints();
        gbc_txtCasIzvedbe.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCasIzvedbe.gridx = 1;
        gbc_txtCasIzvedbe.gridy = 6;
        infoPanel.add(txtTimeOfPlay, gbc_txtCasIzvedbe);
        txtTimeOfPlay.setColumns(10);

        InitializeArray();

        setSize(900, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void InitializeArray() {
        theArray=new int[MAXROW][MAXCOL];
        for (int row=0; row<MAXROW; row++){
            for (int col=0; col<MAXCOL; col++){
                theArray[row][col]=BLANK;
            }
        }

        // Set default values
        gameStart=false;
        this.sumRed = 0;
        this.txtRed.setText("" + sumRed);
        this.sumYellow = 0;
        this.txtYellow.setText("" + sumYellow);
        this.sestevekSum =0;
        this.txtSum.setText("" + sestevekSum);
        activeColour = RED;
        activePlayers = HUMANPLAYERS;
        this.txtTimeOfPlay.setText("0ms");
        this.randomPlayer = new RandomPlayer();

        PaintGamingField();
    }

    public void PaintGamingField(){

        if(this.panelBody != null){
            Container cont = this.panelBody.getParent();
            cont.remove(this.panelBody);
        }

        panelBody = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillRect(0, 0, 100+100*MAXCOL, 100+100*MAXROW);
                for (int row=0; row<MAXROW; row++)
                    for (int col=0; col<MAXCOL; col++) {
                        if (theArray[row][col]==BLANK) g.setColor(Color.WHITE);
                        if (theArray[row][col]==RED) g.setColor(Color.RED);
                        if (theArray[row][col]==YELLOW) g.setColor(Color.YELLOW);
                        g.fillOval(10+97*col, 10+95*row, 75, 75);
                    }
            }
        };

        getContentPane().add(panelBody, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
        check4();
    }

    public void humanPlay(int n){
        try{
            putDisk(n);

            if(activePlayers == HUMANVSRANDOMPLAYERS && !end && canPlay){

                long startTime = System.nanoTime();
                putDisk(randomPlayer.returnColumn(this.theArray));
                long endTime = System.nanoTime();

                this.ShowTime(startTime, endTime);
            }

            if(activePlayers == HUMANVSMINMAXPLAYERS && !end &&canPlay){

                long startTime = System.nanoTime();

                Players.setMaxPlayer(activeColour);
                if(activeColour == RED){
                    Players.setMinPlayer(YELLOW);
                }
                else{
                    Players.setMinPlayer(RED);
                }

                MinMaxPlayer minMaxPlayer = new MinMaxPlayer(3, this.theArray); //optimalna globina je 2
                putDisk(minMaxPlayer.returnColumn());

                long endTime = System.nanoTime();

                this.ShowTime(startTime, endTime);
            }
        }
        catch(Exception ex){
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    public void putDisk(int n) {
        if (end){
            JOptionPane.showMessageDialog(null, "Game over. Please start a new game.");
            return;
        }

        gameStart=true;
        canPlay = true;
        int row;
        n--;

        for (row=0; row<MAXROW; row++){
            if (theArray[row][n] > 0){
                break;
            }
        }

        if (row>0) {
            theArray[--row][n]=activeColour;
            if (activeColour==RED){
                activeColour=YELLOW;
                this.sumRed++;
                this.sestevekSum = sumRed + sumYellow;
                this.txtRed.setText("" + sumRed);
            }
            else{
                activeColour=RED;
                this.sumYellow++;
                this.sestevekSum = sumRed + sumYellow;
                this.txtYellow.setText("" + sumYellow);
            }

            this.txtSum.setText("" + sestevekSum);

            PaintGamingField();
        }
        else{
            canPlay = false;
        }
    }

    public void check4() {
        // see if there are 4 disks in a row: horizontal, vertical or diagonal
        // horizontal rows
        for (int row=0; row<MAXROW; row++) {
            for (int col=0; col<MAXCOL-3; col++) {
                int curr = theArray[row][col];
                if (curr>0
                        && curr == theArray[row][col+1]
                        && curr == theArray[row][col+2]
                        && curr == theArray[row][col+3]) {
                    displayWinner(theArray[row][col]);
                }
            }
        }
        // vertical columns
        for (int col=0; col<MAXCOL; col++) {
            for (int row=0; row<MAXROW-3; row++) {
                int curr = theArray[row][col];
                if (curr>0
                        && curr == theArray[row+1][col]
                        && curr == theArray[row+2][col]
                        && curr == theArray[row+3][col])
                    displayWinner(theArray[row][col]);
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<MAXROW-3; row++) {
            for (int col=0; col<MAXCOL-3; col++) {
                int curr = theArray[row][col];
                if (curr>0
                        && curr == theArray[row+1][col+1]
                        && curr == theArray[row+2][col+2]
                        && curr == theArray[row+3][col+3])
                    displayWinner(theArray[row][col]);
            }
        }
        // diagonal upper left to lower right
        for (int row=MAXROW-1; row>=3; row--) {
            for (int col=0; col<MAXCOL-3; col++) {
                int curr = theArray[row][col];
                if (curr>0
                        && curr == theArray[row-1][col+1]
                        && curr == theArray[row-2][col+2]
                        && curr == theArray[row-3][col+3])
                    displayWinner(theArray[row][col]);
            }
        }
    }

    public void displayWinner(int n) {
        if (n==RED){
            JOptionPane.showMessageDialog(null, "Red wins!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Yellow wins!");
        }

        end = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == newMI) {
            end = false;
            InitializeArray();
        }
        else if (e.getSource() == exitMI) {
            System.exit(0);
        }
        else if (e.getSource() == redMI) {
            if (!gameStart){
                activeColour=RED;
            }
            else{
                JOptionPane.showMessageDialog(null, "The game has already started.");
            }
        }
        else if (e.getSource() == yellowMI) {
            if (!gameStart){
                activeColour=YELLOW;
            }
            else{
                JOptionPane.showMessageDialog(null, "The game has already started.");
            }
        }
        else if (e.getSource() == humanPlayerMI){
            if(!gameStart){
                activePlayers = HUMANPLAYERS;
            }
            else{
                JOptionPane.showMessageDialog(null, "The game has already started.");
            }
        }
        else if (e.getSource() == humanVSRandomPlayerMI){
            if(!gameStart){
                activePlayers = HUMANVSRANDOMPLAYERS;
            }
            else{
                JOptionPane.showMessageDialog(null, "The game has already started.");
            }
        }
        else if (e.getSource() == humanVSMinMaxPlayerMI){
            if(!gameStart){
                activePlayers = HUMANVSMINMAXPLAYERS;
            }
            else{
                JOptionPane.showMessageDialog(null, "The game has already started.");
            }
        }
    }

    public void ShowTime(long startTime, long endTime) {

        long time = endTime - startTime;
        this.txtTimeOfPlay.setText("" + time);

    }
}
