import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Snake extends JFrame {

    Snake(){
        JFrame snakeFrame = new JFrame("Snake"); // создает окно программы c названием рамки
        snakeFrame.add(new SnakePanel()); // добавляет панель в окно калькулятора
        snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // закрывает процесс
        snakeFrame.pack();
        snakeFrame.setLocationRelativeTo(null);
        snakeFrame.setVisible(true); // задает видимоcть фигуры
        snakeFrame.setResizable(false);
    }

    public static void main(String[] args){
        EventQueue.invokeLater(Snake::new);
    }
}
