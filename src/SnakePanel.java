import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SnakePanel extends JPanel {
    private JLabel display;
    private JPanel panel;

    String s0 = "0"; // первое число
    String s1 = ""; // оператор
    String s2 = ""; // второе число
    Double result = null; // результат

    JButton b0 = new JButton("0");
    JButton b1 = new JButton("1");
    JButton b2 = new JButton("2");
    JButton b3 = new JButton("3");
    JButton b4 = new JButton("4");
    JButton b5 = new JButton("5");
    JButton b6 = new JButton("6");
    JButton b7 = new JButton("7");
    JButton b8 = new JButton("8");
    JButton b9 = new JButton("9");
    JButton bEqual = new JButton("=");
    JButton bPlus = new JButton("+");
    JButton bMinus = new JButton("-");
    JButton bMultiply = new JButton("*");
    JButton bDivide = new JButton("/");
    JButton bDelete = new JButton("C");
    JButton bDot = new JButton(".");
    JButton bFactorial = new JButton("!");

    public CalculatorPanel(){
        setLayout(new BorderLayout());
        display = new JLabel("0"); // добавляет дисплей
        display.setEnabled(false); // не позволяет взаимодействовать с полем дисплея
        add(display, BorderLayout.NORTH); // отображает дисплей сверху
        display.setFont(new Font(display.getFont().getName(), display.getFont().getStyle(), 40));
        display.setBorder(BorderFactory.createEmptyBorder(0,5,10,0));
        panel = new JPanel(); // добавление панели
        panel.setLayout(new GridLayout(5,4, 2,2)); // табличное расположение
        add(panel); // добавляет панель
        panel.setVisible(true);

        // добавление кнопок на панель
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(bDelete);
        panel.add(b4);
        panel.add(b5);
        panel.add(b6);
        panel.add(bMultiply);
        panel.add(b7);
        panel.add(b8);
        panel.add(b9);
        panel.add(bDivide);
        panel.add(b0);
        panel.add(bDot);
        panel.add(bEqual);
        panel.add(bMinus);
        panel.add(bPlus);
        panel.add(bFactorial);

        panel.setBackground(Color.GRAY);

        // добавление обработчика команд для каждой кнопки
        b0.addActionListener(this::valuePerformed);
        b1.addActionListener(this::valuePerformed);
        b2.addActionListener(this::valuePerformed);
        b3.addActionListener(this::valuePerformed);
        b4.addActionListener(this::valuePerformed);
        b5.addActionListener(this::valuePerformed);
        b6.addActionListener(this::valuePerformed);
        b7.addActionListener(this::valuePerformed);
        b8.addActionListener(this::valuePerformed);
        b9.addActionListener(this::valuePerformed);
        bPlus.addActionListener(this::operatorPerformed);
        bMultiply.addActionListener(this::operatorPerformed);
        bMinus.addActionListener(this::operatorPerformed);
        bEqual.addActionListener(this::operatorPerformed);
        bDot.addActionListener(this::valuePerformed);
        bDivide.addActionListener(this::operatorPerformed);
        bDelete.addActionListener(this::operatorPerformed);
        bFactorial.addActionListener(this::operatorPerformed);
    }

    private void valuePerformed(ActionEvent ae) {

        char ch = ae.getActionCommand().charAt(0);

        if (ch == '.'){
            bPlus.setEnabled(false);
            bMinus.setEnabled(false);
            bMultiply.setEnabled(false);
            bDivide.setEnabled(false);
            bDot.setEnabled(false);
        }
        else{
            bPlus.setEnabled(true);
            bMinus.setEnabled(true);
            bMultiply.setEnabled(true);
            bDivide.setEnabled(true);
        }

        if (result == null){
            if (s1.equals("")){
                if (s0.equals("0")){
                    if (ch == '0'){ }
                    else{
                        s0 = (ch == '.') ? s0+dotProcessor(s0) : ""+ch;
                    }
                }
                else{
                    s0 += ch;
                }
                bFactorial.setEnabled(!s0.equals("0") && Double.parseDouble(s0) % 1 == 0);
                bDot.setEnabled(!s0.contains("."));
            }
            else{
                if (s2.equals("0")){
                    if (ch == '0'){ }
                    else{
                        s2 = (ch == '.') ? s2+dotProcessor(s2) : ""+ch;
                    }
                }
                else{
                    s2 += ch;
                }
                bDot.setEnabled(!s2.contains("."));
            }
        }
        else{
            if (s1.equals("")){
                s0 = (ch=='.') ? (s0 = "0.") : (""+ch);
                bFactorial.setEnabled(true);
            }
            else{
                s0 = ""+result;
                s2 = (ch=='.') ? (s2 = "0.") : (""+ch);
                bDot.setEnabled(!s2.contains("."));
                bFactorial.setEnabled(false);

            }
            result = null;
        }

        display.setText(s0+s1+s2);
    }
    private void operatorPerformed(ActionEvent ae) {
        char ch = ae.getActionCommand().charAt(0);

        if (ch == 'C'){
            s1=s2="";
            s0="0";
            display.setText("0");
        }

        else if (ch == '='){
            if (!s2.equals("")){
                switch (s1) {
                    case "+" -> result = Double.parseDouble(s0) + Double.parseDouble(s2);
                    case "-" -> result = Double.parseDouble(s0) - Double.parseDouble(s2);
                    case "*" -> result = Double.parseDouble(s0) * Double.parseDouble(s2);
                    case "/" -> result = Double.parseDouble(s0) / Double.parseDouble(s2);
                }
            }
            else{
                if (s1.equals("!")){
                    result = factorial( (int)Double.parseDouble(s0));
                }
                else{
                    result = (s0.equals("")) ? 0 : Double.parseDouble(s0);
                }
            }
            display.setText(displayDoubleInt(result));

            s0 = Double.toString(result);
            s1=s2="";
            bFactorial.setEnabled(result % 1 ==0);
        }

        else{
            s1 = Character.toString(ch);
            display.setText(displayDoubleInt(s0,s1,s2));
        }
    }

    private String displayDoubleInt (String str0, String str1, String str2){
        String newStr = (str0.charAt(str0.length() - 1)=='.') ?
                str0.substring(0,str0.length()-1) :
                str0;
        return(Double.parseDouble(newStr)%1 != 0) ? newStr+str1+str2 : ((int)Double.parseDouble(newStr))+str1+str2;
    }

    private String displayDoubleInt (double res){
        return (res % 1 != 0) ? ""+res : ""+(int)res;
    }

    private double factorial(int str0){
        return (str0==1) ? 1 : str0 * factorial(str0-1);
    }

    private String dotProcessor (String str){
        return (str.contains(".")) ? "" : ".";
    }
}

