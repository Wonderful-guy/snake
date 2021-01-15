import javax.swing.*;

public class Panel extends JPanel {
    public Panel(){
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new InfoPanel());
        add(new SnakePanel());
    }
}
