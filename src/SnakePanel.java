import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakePanel extends JPanel implements ActionListener {

    private int cellSize = 40;
    private int panelWidth = 15 * cellSize;
    private int panelHeight = 15 * cellSize;
    private int cellsTotal = panelHeight*panelWidth / (cellSize*cellSize);
    private int cellRandomX = 15;
    private int cellRandomY = 15;
    private int delayGame = 300;

    private int cellsSnake;
    private int appleX;
    private int appleY;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    private Timer timer;
    private Image apple;
    private Image head;
    private Image body;

    private final int[] xSnakeArray = new int[cellsTotal];
    private final int[] ySnakeArray = new int[cellsTotal];

    public SnakePanel(){
        initPanel();
    }

    private void initPanel(){
        setBackground(Color.gray);
        setFocusable(true);
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        addKeyListener(new KeyMove());
        uploadImages();
        initGame();
    }

    private void initGame(){

        cellsSnake = 3;
        spawnApple();

        for (int i = 0; i< cellsSnake; i++){
            xSnakeArray[i] = 4 * cellSize - i*cellSize;
            ySnakeArray[i] = 4 * cellSize;
        }
        timer = new Timer(delayGame,this);
        timer.start();
    }

    private void uploadImages(){
        ImageIcon appleImage = new ImageIcon("C:\\Users\\NewUser\\Desktop\\snake\\src\\resources\\apple.png");
        ImageIcon snakeBodyImage = new ImageIcon("C:\\Users\\NewUser\\Desktop\\snake\\src\\resources\\snakeBody.png");
        ImageIcon snakeHeadImage = new ImageIcon("C:\\Users\\NewUser\\Desktop\\snake\\src\\resources\\snakeHead.png");

        apple = appleImage.getImage().getScaledInstance(cellSize,-1,Image.SCALE_SMOOTH);
        body = snakeBodyImage.getImage().getScaledInstance(cellSize,-1,Image.SCALE_SMOOTH);
        head = snakeHeadImage.getImage().getScaledInstance(cellSize,-1,Image.SCALE_SMOOTH);
    }

    private void snakeMove(){
        for (int i = cellsSnake; i>0; i--){
            xSnakeArray[i] = xSnakeArray[i-1];
            ySnakeArray[i] = ySnakeArray[i-1];
        }
        if (up) ySnakeArray[0] -= cellSize;
        else if (down) ySnakeArray[0] += cellSize;
        else if (left) xSnakeArray[0] -= cellSize;
        else if (right) xSnakeArray[0] += cellSize;
    }

    private void checkCollision(){
        for (int i = cellsSnake; i>0; i--){
            if ((i >= 5) && (xSnakeArray[0] == xSnakeArray[i]) && (ySnakeArray[0] == ySnakeArray[i])) {
                inGame = false;
                break;
            }
        }
        if ((ySnakeArray[0] >= panelHeight) ||
                (ySnakeArray[0] < 0) ||
                (xSnakeArray[0] >= panelWidth) ||
                (xSnakeArray[0] < 0)) {
            inGame = false;
        }
        if (!inGame) timer.stop();
    }

    private void spawnApple(){
        int newRandomAxis;

        newRandomAxis = (int) (Math.random() * cellRandomX);
        appleX = newRandomAxis * cellSize;

        newRandomAxis = (int) (Math.random() * cellRandomY);
        appleY = newRandomAxis * cellSize;
    }

    private void checkAppleEaten(){
        if ((xSnakeArray[0] == appleX) && (ySnakeArray[0] == appleY)){
            cellsSnake++;
            spawnApple();
        }
    }

    private void draw(Graphics g){
        if (inGame){
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i< cellsSnake; i++){
                if (i==0){
                    g.drawImage(head, xSnakeArray[i], ySnakeArray[i], this);
                }
                else{
                    g.drawImage(body, xSnakeArray[i], ySnakeArray[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else {
            gameOver(g);
        }
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
            checkAppleEaten();
            checkCollision();
            snakeMove();
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
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


