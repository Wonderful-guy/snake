import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Delayed;

public class SnakePanel extends JPanel implements ActionListener {

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
    private boolean inGame = true;

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

    private void snakeMove(){
        for (int i = cells; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (up) x[0] -= cellSize;
        else if (down) x[0] += cellSize;
        else if (left) y[0] -= cellSize;
        else if (right) y[0] += cellSize;
    }

    private void checkCollision(){
        for (int i = cells; i>0; i--){
            if ( (i>=5) && (x[0] == x[i]) && (y[0] == y[i]) ){
                inGame = false;
            }
        }
        if ((y[0] >= panelHeight) ||
                (y[0] <= 0) ||
                (x[0] >= panelWidth) ||
                (x[0] <= 0)) {
            inGame = false;
        }
    }

    private void initPanel(){
        setBackground(Color.GRAY);
        addKeyListener(new KeyMove());
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
        timer = new Timer(delayGame,this);
        timer.start();
    }

    private void checkItemEaten(){
        if ((x[0] == itemX) && (y[0] == itemY)){
            cells++;
            spawnApple();
        }
    }

    private void draw(Graphics g){
        g.drawImage(item, itemX, itemY, this);
        for (int i=0; i<cells;i++){
            if (i==0){
                g.drawImage(head, x[i], y[i], this);
            }
            else{
                g.drawImage(body, x[i], y[i], this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g){
        String message = "Game Over";
        Font fontGameOver = new Font("Arial", Font.BOLD, 15);
        FontMetrics metrics = getFontMetrics(fontGameOver);

        g.setColor(Color.BLUE);
        g.setFont(fontGameOver);
        g.drawString(message, panelWidth, panelHeight);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkItemEaten();
            checkCollision();
            snakeMove();
        }
    }

    public class KeyMove extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!right)){
                left = true;
                up = false;
                down = false;
            }
            else if ((key == KeyEvent.VK_RIGHT) && (!left)){
                right = true;
                up = false;
                down = false;
            }
            else if ((key == KeyEvent.VK_UP) && (!down)){
                right = false;
                up = true;
                left = false;
            }
            else if ((key == KeyEvent.VK_DOWN) && (!up)){
                right = false;
                down = true;
                left = false;
            }
        }
    }
}


