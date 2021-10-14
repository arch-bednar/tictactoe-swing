import javax.swing.*;
import java.awt.*;

public class Tictactoe{

    private JFrame window; //odpowiednik stage w javafx

    private class Cell extends JPanel{
        public Cell(){

        }
    }

    public void gameMenu(){

        Dimension windowDimension = new Dimension(250, 275);
        JPanel menu = new JPanel();
        menu.setPrefferedSize(windowDimension);
        menu.setMaximumSize(windowDimension);

        JButton start = new JButton("start");
        JButton exit = new JButton("exit");


        window = new JFrame();
        window.getContent().add(start, exit);
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                gameMenu();
            }
        });
    }
}