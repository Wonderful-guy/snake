import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Snake extends JFrame {

    Snake(){
        JFrame snakeFrame = new JFrame("Snake"); // создает окно программы c названием рамки
        snakeFrame.add(new Panel()); // добавляет панель в окно программы
        snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // закрывает процесс
        snakeFrame.pack(); // оставляет размер окна программы в соответствии с размером панели
        snakeFrame.setLocationRelativeTo(null); // выравниает по середине экрана
        snakeFrame.setVisible(true); // задает видимоcть фигуры
        snakeFrame.setResizable(false); // нельзя изменять размер окна программы
    }

    public static void main(String[] args){
        EventQueue.invokeLater(Snake::new);
    }
}
