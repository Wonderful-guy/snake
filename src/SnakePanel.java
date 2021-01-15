import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakePanel extends JPanel implements ActionListener {

    private final int cellSize = 40; // размер грани одной ячейки
    private final int cellsPerWidth = 15; // количество ячеек в ширину
    private final int cellsPerHeight = 15; // количество ячеек в высоту
    private final int panelWidth = 15 * cellSize; // ширина панели
    private final int panelHeight = 15 * cellSize; // высота панели
    private final int cellsTotal = cellsPerWidth*cellsPerHeight; // количество ячеек на панели
    private final int delayGame = 300; // скорость игры, мс

    private int cellsSnake; // из скольких ячеек состоит змея в данный момент
    private int appleX; // координата яблока по оси X
    private int appleY; // координата яблока по оси Y

    //направление движения змейки в текущий момент
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private boolean inGame = true; // показывает, продолжается ли игра

    private Timer timer; // таймер игры
    private Image apple; // изображение яблока
    private Image headSegment; // изображение головы змеи
    private Image bodySegment; // изображение сегментов тела змеи
    private Image bodyLastSegment; // изобажение последнего сегмента тела змеи

    private final int[] xSnakeArray = new int[cellsTotal]; // массив для хранения сегментов змеи по координате X
    private final int[] ySnakeArray = new int[cellsTotal]; // массив для хранения сегментов змеи по координате Y

    public SnakePanel(){
        initPanel();
    } // инициализуование панели (хз зачем)

    private void initPanel(){
        setBackground(Color.gray); // цвет фона
        setFocusable(true); // (хз зачем, но без фокуса не работает)
        setPreferredSize(new Dimension(panelWidth, panelHeight)); // размер панели
        addKeyListener(new KeyMove()); // добавляет слушатель кнопок
        uploadImages(); // загружает изображения
        initGame(); // инициализация игры
    }

    private void initGame(){

        cellsSnake = 3; // сегменты змеи
        spawnApple(); // спаун яблока

        for (int i = 0; i < cellsSnake; i++){ // рисует сегменты змеи
            xSnakeArray[i] = 4 * cellSize - i*cellSize;
            ySnakeArray[i] = 4 * cellSize;
        }

        timer = new Timer(delayGame,this); // инициализация таймера
        timer.start(); // старт таймера
    }

    private void uploadImages(){
        // создание изображения из файла
        ImageIcon appleImage = new ImageIcon("C:\\Users\\NewUser\\Desktop\\snake\\src\\resources\\apple.png");
        ImageIcon snakeBodyImage = new ImageIcon("C:\\Users\\NewUser\\Desktop\\snake\\src\\resources\\snakeBody.png");
        ImageIcon snakeBodyLastImage = new ImageIcon("C:\\Users\\NewUser\\Desktop\\snake\\src\\resources\\snakeBodyLast.png");
        ImageIcon snakeHeadImage = new ImageIcon("C:\\Users\\NewUser\\Desktop\\snake\\src\\resources\\snakeHead.png");

        // ресайз изображений по ячейке
        apple = appleImage.getImage().getScaledInstance(cellSize,-1,Image.SCALE_SMOOTH);
        headSegment = snakeHeadImage.getImage().getScaledInstance(cellSize,-1,Image.SCALE_SMOOTH);
        bodySegment = snakeBodyImage.getImage().getScaledInstance(cellSize,-1,Image.SCALE_SMOOTH);
        bodyLastSegment = snakeBodyLastImage.getImage().getScaledInstance(cellSize,-1,Image.SCALE_SMOOTH);
    }

    // метод, описывающий движение змеи
    private void snakeMove(){

        // цикл, в котором каждый последующий сегмент змеи встает на место сегмента ,следующего перед ним
        for (int i = cellsSnake; i>0; i--){
            xSnakeArray[i] = xSnakeArray[i-1];
            ySnakeArray[i] = ySnakeArray[i-1];
        }

        // двигает первый сегмент (голову) змеи в зависимости от того направления. куда двигается змея
        if (up) ySnakeArray[0] -= cellSize;
        else if (down) ySnakeArray[0] += cellSize;
        else if (left) xSnakeArray[0] -= cellSize;
        else if (right) xSnakeArray[0] += cellSize;
    }


    // метод, проверяющий столкновения
    private void checkCollision(){

        // проверка столкновения головы змеи с одним из сегментов тела змеи
        for (int i = cellsSnake; i>0; i--){
            if ((i >= 5) && (xSnakeArray[0] == xSnakeArray[i]) && (ySnakeArray[0] == ySnakeArray[i])) {
                inGame = false; // при столкновении игра заканчивается
                break;
            }
        }

        // проверка столкновения с границами игрового поля
        if ((ySnakeArray[0] >= panelHeight) ||
                (ySnakeArray[0] < 0) ||
                (xSnakeArray[0] >= panelWidth) ||
                (xSnakeArray[0] < 0)) {
            inGame = false;
        }

        // при окончании игры таймер останавливается
        if (!inGame) timer.stop();
    }

    // метод, описывающий появление яблока
    private void spawnApple(){
        int newRandomAxis; // переменная, куда записывается рандомное значение для дальнейшего расчета
        boolean appleCoordIsEqualToSnakeCoord;

        do {
            appleCoordIsEqualToSnakeCoord = false;
            newRandomAxis = (int) (Math.random() * cellsPerWidth);
            appleX = newRandomAxis * cellSize; // координата X появления яблока

            newRandomAxis = (int) (Math.random() * cellsPerHeight);
            appleY = newRandomAxis * cellSize; // координата Y появления яблока

            // цикл, проверяющий, появлилось ли яблоко в одном из сегментов змеи
            for (int i=0; i<cellsSnake; i++){
                if ((appleX == xSnakeArray[i]) && (appleY == ySnakeArray[i])) {
                    appleCoordIsEqualToSnakeCoord = true;
                    break;
                }
            }
        }
        // если яблоко появилось на месте одного из сегментов змеи,
        // то координаты появления яблока должны быть пересчитаны
        while (appleCoordIsEqualToSnakeCoord); // если истинно, то пересчет координат яблока
    }

    // метод, првоеряющий, было ли съедено ли яблоко змеей (головой змеи)
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
                    g.drawImage(headSegment, xSnakeArray[i], ySnakeArray[i], this);
                }
                else if (i==cellsSnake-1){
                    g.drawImage(bodyLastSegment, xSnakeArray[i], ySnakeArray[i], this);
                }
                else{
                    g.drawImage(bodySegment, xSnakeArray[i], ySnakeArray[i], this);
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

    // класс, реализующий метод чтения с клавиатуры
    public class KeyMove extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode(); // переменной присваивается нажатая на клавиатуре клавиша

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


