import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class TTT implements ActionListener{

    private Cell[][] fields = new Cell[3][3];
    private JFrame window;
    private JFrame gameFrame;
    private JButton pvp, pvc, exit;
    private byte mode; //if 0 then pvp else pvc
    int turn = 0; // 0 - before game is created, if 1 then turn of the player/computer else 2 then player [second]

    TTT() throws IOException {
        menuStart();
    }

    private void menuStart() throws IOException {

        window = new JFrame("Tictactoe");

        Container panel  = window.getContentPane();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        pvp = new JButton("Player vs Player");
        pvp.setFont(new Font("Arial", Font.BOLD, 15));
        pvp.setSize(50,25);
        pvp.addActionListener(this);

        pvc = new JButton("Player vs Computer");
        pvc.setFont(new Font("Arial", Font.BOLD, 15));
        pvc.setSize(50, 25);
        pvc.addActionListener(this);

        exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.BOLD, 15));
        exit.setSize(50, 25);
        //exit.addActionListener(this);
        exit.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               System.out.println("wewnetrzny event");
               window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
           }
        });

        //BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\Marvin\\IdeaProjects\\tictactoe-swing\\src\\ttt.png"));
        //ImageIcon im = new ImageIcon("C:\\Users\\Marvin\\IdeaProjects\\tictactoe-swing\\src\\ttt.png");

        BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\Marvin\\IdeaProjects\\tictactoe-swing\\src\\ttt.png"));
        JLabel picLabel = new JLabel(new ImageIcon(new ImageIcon(myPicture).getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT)));


        Container logo = new JPanel();
        logo.add(picLabel);


        logo.setBackground(Color.PINK); //set background for our container with image
        panel.add(logo);


        pvp.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(pvp);
        panel.add(Box.createRigidArea(new Dimension(0, 5))); //like spacing in VBox in Fx - VBox(15)

        pvc.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(pvc);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exit);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        window.setSize(new Dimension(250, 275));
        window.setVisible(true);
        window.setFocusable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.getContentPane().setBackground(Color.PINK); //set background JFrame

        window.setResizable(false);

        //window.pack(); //metoda dopasowywuje rozmiar okna do rozmieszczonych elementów
        //window.add(panel); //adding container's parent to itself!!!!!
    }

    public void play(){
        gameFrame = new JFrame("New game pvp");
        gameFrame.setVisible(true);
        gameFrame.setFocusable(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(new Dimension(300, 390)) ;
        //gameFrame.setBackground(Color.RED);

        //gameFrame.getContentPane().setBackground(Color.RED);

        JButton quit = new JButton();
        makeQuitButton(quit);

        Container container = gameFrame.getContentPane();
        Container grid = new Container();

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        //Container container = new Container();
        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints bagConstraints = new GridBagConstraints();

        for (int i=0; i<3; i++){
            for(int j=0; j<3;j++){
                fields[i][j] = new Cell();
            }
        }

        makeMesh(grid, bagLayout, bagConstraints);
        //makeMesh(container, bagLayout, bagConstraints);

        container.add(grid);
        //container.add(Box.createRigidArea(new Dimension(0, 5)));
        quit.setAlignmentX(Box.CENTER_ALIGNMENT);
        container.add(quit);
        container.add(Box.createRigidArea(new Dimension(0,25)));
        container.setBackground(Color.RED);
        gameFrame.setResizable(false);
        //container.add(Box.createRigidArea(new Dimension(0,100)));
        //container.add(quit);

        //System.out.println(fields[1][1].field);
        //turn = 1;
        //gameFrame.add(quit);
    }

    public void makeQuitButton(JButton button){
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setSize(new Dimension(180, 30));
        button.setText("Exit to home screen");

        button.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public void makeMesh(Container container, GridBagLayout layout, GridBagConstraints constraints){
        container.setLayout(layout);

        for(int i = 0; i<3; i++) {
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = i;
            for(int j=0; j<3; j++){
                constraints.gridy = j;
                //constraints.weightx = 85;
                //constraints.weighty = 85;
                container.add(fields[i][j], constraints);
//                layout.setConstraints(fields[i][j], constraints);
//                container.add(fields[i][j]);
            }
        }


        JButton quit = new JButton("Quit");
        constraints.gridx=1;
        constraints.gridy=3;
        //constraints.weightx=3;
        //container.add((quit), constraints);
        //layout.setConstraints(quit, constraints);
        //container.add(quit);

        /*
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        container.add(new Cell(), constraints);
        */

        //panel.add(container);
    }

    private class Cell extends JPanel{

        private char field  = ' ';

        Cell(){
            makePane();
            if(mode == 0){
                makePVP();
            }else if(mode == 1){
                //System.out.println('x');
                makePVC();
            }
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        public void makePVP(){
            this.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    if(turn == 1 && field == ' '){
                        field = 'O';
                        repaint();
                        if(win(field)){
                            turn = 3;
                            //System.out.println("O won!");
                            EventQueue.invokeLater(new Runnable(){
                                public void run(){
                                    makeWinner('O');
                                }
                            });
                        }else if(isDraw()){
                            turn = 3;
                            System.out.println("Draw!");
                        }else{
                            changeTurn();
                        }
                    } else if (turn == 2 && field == ' '){
                        field = 'X';
                        repaint();
                        if(win(field)){
                            turn = 3;
                            //System.out.println("X won!");
                            EventQueue.invokeLater(new Runnable(){
                                public void run(){
                                    makeWinner('X');
                                }
                            });
                        }else if(isDraw()){
                            turn = 3;
                            System.out.println("Draw!");
                        }else{
                            changeTurn();
                        }
                    }
                    //System.out.println(turn);
                }
            });
        }

        public void makePVC(){
            this.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    if (turn == 2) {
                        field = 'X';
                        repaint();
                        if (win(field)) {
                            turn = 3;
                            //System.out.println("X won!");
                            EventQueue.invokeLater(new Runnable(){
                                public void run(){
                                    makeWinner('X');
                                }
                            });

                        } else if (isDraw()) {
                            turn = 3;
                            //System.out.println("Draw!");
                            EventQueue.invokeLater(new Runnable(){
                                public void run(){
                                    makeWinner('O');
                                }
                            });
                        } else {
                            changeTurn();
                        }

                    }
                    //System.out.println(turn);

                    if(turn!= 3)
                        computerTurn();

                }
            });
        }

        private void computerTurn(){
            int x,y;
            Random gen = new Random();

            do{
                x = gen.nextInt(3);
                y = gen.nextInt(3);
            }while(fields[x][y].field != ' ');

            fields[x][y].field = 'O';
            fields[x][y].repaint();
            if(win(field)){
                turn = 3;
                System.out.println("O won!");
            }else if(isDraw()){
                turn = 3;
                System.out.println("Draw!");
            }else{
                changeTurn();
            }
        }

        public void makePane(){
            this.setPreferredSize(new Dimension(85, 85));
            this.setBackground(Color.WHITE);
        }


        public void paintComponent(Graphics g){
            super.paintComponent(g); //REQUIRED!!!! IF BE NOT USED THEN WILL HAVE ERRORS AND REFRESHING DOESN'T WORK CORRECTLY

            Graphics2D g2d = (Graphics2D) g;
            if(field == 'X'){
                g2d.setStroke(new BasicStroke(5));
                Line2D.Double lineOne = new Line2D.Double(0, 0, 85, 85);
                g2d.draw(lineOne);
                Line2D.Double lineTwo = new Line2D.Double(0, 85, 85, 0);
                g2d.draw(lineTwo);
            }else if(field == 'O'){
                Ellipse2D.Double ellipse = new Ellipse2D.Double(0, 0, 80, 80);
                g2d.setColor(Color.BLACK);
                g2d.fill(ellipse);
                Ellipse2D.Double smallEllipse = new Ellipse2D.Double(5, 5, 70, 70);
                g2d.setColor(Color.white);
                g2d.fill(smallEllipse);
            }
        }
    }

    public void makeWinner(char sign){
        JFrame winner = new JFrame();
        Container cont = winner.getContentPane();
        JLabel label = new JLabel(sign + " has won!");
        label.setFont(new Font("Arial", Font.BOLD, 25));
        //cont.setLayout();
        //label.setAlignmentX(5);
        cont.add(label);
        winner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winner.setVisible(true);
        winner.setSize(new Dimension(200, 200));
        winner.setResizable(false);
    }

    public void changeTurn(){
        System.out.println(turn);
        if(turn == 1) turn = 2;
        else turn = 1;
    }

    private boolean win(char sign){
        if(vonNeumann(sign) || diagonal(sign)){
            return true;
        }else{
            return false;
        }
    }

    private boolean isDraw(){
        int filled=0;
        for(int row=0; row<fields.length; row++){
            for(int col=0; col<fields[row].length; col++){
                if(fields[row][col].field != ' '){
                    filled++;
                }
            }
        }

        if(filled == 9){
            return true;
        }else{
            return false;
        }
    }

    private boolean vonNeumann(char sign){
        int count;
        for(int row=0; row<fields.length; row++){
            for(int col=0; col<fields[row].length; col++){
                count=0;

                for(int tcol=col-1; tcol<=col+1; tcol++){
                    if(tcol<0 || tcol>fields.length-1 || tcol==col)
                        continue;
                    if(fields[row][col].field == fields[row][tcol].field && fields[row][col].field == sign)
                        count++;
                }

                if(count == 2){
                    return true;
                }

                count=0;
                for(int trow=row-1; trow<=row+1; trow++){
                    if (trow < 0 || trow > fields.length-1 || trow == row)
                        continue;
                    if (fields[row][col].field == fields[trow][col].field && fields[row][col].field == sign)
                        count++;
                }

                if(count == 2){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean diagonal(char sign){
        if(sign == fields[1][1].field){
            if(fields[1][1].field == fields[0][0].field && fields[1][1].field == fields[2][2].field){
                return true;
            }else if(fields[1][1].field == fields[0][2].field && fields[1][1].field == fields[2][0].field){
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == pvp){
            //System.out.println("dupa");
            //window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            window.dispose();
            turn=1;
            mode=0;
            EventQueue.invokeLater(new Runnable(){
                public void run() {
                    play();
                }
            });

        } else if (e.getSource() == pvc){
            //System.out.println("automatyczna dupa");
            turn=2;
            mode=1;
            EventQueue.invokeLater(new Runnable(){
                public void run(){
                    play();
                }
            });

        }else if(e.getSource() == exit){
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }
    }

}

class GameStart{
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                try {
                    TTT ttt = new TTT();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
