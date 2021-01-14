import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Delayed;

public class SnakePanel extends JPanel {

    private int cellSize = 10;
    private int panelWidth = 310;
    private int panelHeight = 310;
    private int cellSum = panelHeight*panelWidth / (cellSize*cellSize);
    private int itemPos = 1;
    private int cellRandom = 31;
    private int delayGame = 500;

    private int cells = 3;
    private int itemX;
    private int itemY;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private Timer timer;
    private Image item;
    private Image head;
    private Image body;

    private int x[] = new int[cellSum];
    private int y[] = new int[cellSum];

    private void uploadImages(){
        ImageIcon itemImage = new ImageIcon("C:\\Users\\NewUser\\Desktop\\snake\\src\\resources\\apple.png");
        ImageIcon snakeBodyImage = new ImageIcon("C:\\Users\\NewUser\\Desktop\\snake\\src\\resources\\snakeBody.png");
        ImageIcon snakeHeadImage = new ImageIcon("C:\\Users\\NewUser\\Desktop\\snake\\src\\resources\\snakeHead.png");
        item = itemImage.getImage();
        body = snakeBodyImage.getImage();
        head = snakeHeadImage.getImage();
    }

    public SnakePanel(){
        initPanel();
    }

    private void initPanel(){
        setBackground(Color.GRAY);
        addKeyListener(new TAdapt());
        setFocusable(true);
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        uploadImages();
        initGame();
    }

    private void spawnApple(){
        int newAxis;

        newAxis = (int) Math.random() * cellRandom;
        itemX = newAxis * cellSize;

        newAxis = (int) Math.random() * cellRandom;
        itemY = newAxis * cellSize;
    }

    private void initGame(){
        for (int i=0; i<cells;i++){
            x[i] = 50 - i*10;
            y[i] = 150;
        }
        spawnApple();
        timer = new Timer(Delayed,this);
    }

        private void checkItemEaten(){
            if ((x[0] == itemX) && (y[0] == itemY)){
                cells++;
                spawnApple();
            }
        }

    }



}

