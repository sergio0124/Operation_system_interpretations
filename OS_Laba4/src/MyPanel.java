import javax.swing.*;
import java.awt.*;

/*отрисовывает сектора*/
public class MyPanel extends JPanel {

    Disk disk;

    public MyPanel(Disk disk){
        this.disk=disk;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        disk.draw(this.getWidth(),g);
    }
}
