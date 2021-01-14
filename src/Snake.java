import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Snake extends JFrame {

    Snake(){
        JFrame snakeFrame = new JFrame("Snake"); // создает окно программы c названием рамки
        snakeFrame.setSize(800,800); // задает размер окна
        snakeFrame.setLocationRelativeTo(null);
        snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // закрывает процесс
        snakeFrame.add(new SnakePanel()); // добавляет панель в окно калькулятора
        snakeFrame.setVisible(true); // задает видимоcть фигуры
        snakeFrame.setResizable(false);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Snake();
            }
        });
    }
}
