package pack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JFrame;
public class MainClass extends JFrame{
    private static My3dLine t;

    public MainClass(){
        super("My First Window");
        setBounds(100, 100, 500, 500);
        t = new My3dLine(50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        MainClass app = new MainClass();
        t = new My3dLine(40);
        app.add(t);
        app.setVisible(true); //С этого момента приложение запущено!
    }
}