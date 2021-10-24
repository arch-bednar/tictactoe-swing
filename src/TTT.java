import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.Box;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;


public class TTT implements ActionListener{

    private JFrame window;
    JButton pvp, pvc, exit;

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
        JFrame gameFrame = new JFrame("New game pvp");
        gameFrame.setVisible(true);
        gameFrame.setFocusable(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
    public void makeWindowListener(){ //metod to make exit button to close window
        window.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){

            }
        });
    }
    */

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == pvp){
            System.out.println("dupa");
            window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            SwingUtilities.invokeLater(new Runnable(){
                public void run() {
                    play();
                }
            });
        } else if (e.getSource() == pvc){
            System.out.println("automatyczna dupa");
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
