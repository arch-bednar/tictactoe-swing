import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Game {

    byte turn = 0;

    public static void main(String[] args){


        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                Game g = new Game();
            }
        });
    }

    Game(){
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(250, 250));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new Cell());
    }
    private class Cell extends JPanel{ //as Pane in javafx
        boolean isFilled = false;

        Cell(){
            addMouseListener(new MouseAdapter(){ //this.setOnMousePressed(new EventHandler<MouseEvent>(){ in FX
                @Override
                public void mousePressed(MouseEvent e){
                    if(SwingUtilities.isLeftMouseButton(e)) {
                        System.out.println("lewy");
                        System.out.println(isFilled);
                        if(!isFilled){
                            isFilled = true;
                            repaint();
                        }
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e){

                }
            });
        }

        public void paintComponent(Graphics g){
            if(isFilled){
                /*
                if(turn == 0){
                super.paintComponent(g);
                System.out.println("l;inia");
                g.drawLine(0,0,10,10);
                }else if(turn == 1){
                    super.paintComponent(g);
                    System.out.println("kolo");
                    g.drawOval(50, 50, 50, 50);
                }
                */

                if(turn == 0){
                    Graphics2D g2d = (Graphics2D) g;

                    Ellipse2D.Double circle = new Ellipse2D.Double(50, 50, 50, 50);
                    g2d.setColor(Color.RED);
                    g2d.fill(circle);
                } else if(turn == 1) {
                    Graphics2D g2d = (Graphics2D) g;
                    Rectangle2D.Double rect = new Rectangle2D.Double(50, 50, 50, 50);

                    g2d.setColor(Color.GREEN);
                    g2d.fill(rect);
                }
            }
        }
    }
}
