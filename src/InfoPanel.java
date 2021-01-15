import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InfoPanel extends JPanel {
    public int time;
    public int applesEaten;

    JLabel timeGone = new JLabel("Time:"+ SnakePanel.);
    JLabel apples = new JLabel("Apples eaten:");

    InfoPanel(){
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        setPreferredSize(new Dimension(150, SnakePanel.HEIGHT)); // размер панели
        timeGone.setPreferredSize(new Dimension(150,70));
        apples.setPreferredSize(new Dimension(150,70));

        timeGone.setVerticalAlignment(JLabel.CENTER);
        timeGone.setHorizontalAlignment(JLabel.CENTER);
        timeGone.setBorder(solidBorder);

        apples.setVerticalAlignment(JLabel.CENTER);
        apples.setHorizontalAlignment(JLabel.CENTER);
        apples.setBorder(solidBorder);

        add(timeGone);
        add(apples);
    }
}
