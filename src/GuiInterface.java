import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
public class GuiInterface {
    /** Поле оболочки GUI */

    private static  JFrame jFrame = new JFrame();

    /** Поле шрифта */
    private static Font font = new Font("Times New Roman",Font.BOLD,20);
    private static String drive;
    private static String filename;

    GuiInterface()
    {
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2 - 250, dimension.height/2 - 150, 500, 300);
        jFrame.setVisible(true);
        jFrame.setTitle("Lab2");
        JMenuBar jMenuBar = new JMenuBar();
        jFrame.setJMenuBar(jMenuBar);
        jFrame.revalidate();

        JMenu save = new JMenu("Save");
        JMenu file = new JMenu("File");
        jMenuBar.add(file);
        jMenuBar.add(save);
        save.setEnabled(false);
        JMenuItem exit = file.add(new JMenuItem("Exit"));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jMenuBar.revalidate();
        jFrame.revalidate();

        JPanel jPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        jPanel.setLayout(borderLayout);
        jFrame.add(jPanel);
        JButton launchButton = new JButton("Launch");
        jPanel.add(launchButton,BorderLayout.CENTER);
        Launch(launchButton,jPanel);
    }

    private void Launch(JButton launchButton, JPanel jPanel)
    {
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drive = JOptionPane.showInputDialog(jPanel,"Please, enter the name of your local drive",
                        "C");
                if (!drive.isEmpty())
                {
                   filename = JOptionPane.showInputDialog(jPanel,"Please, enter the filename",
                            "my_file");
                    try {
                        if (!filename.isEmpty()) {

                            try {
                               FileWorking fileWorking = new FileWorking(drive,filename);
                               fileWorking.working();
                            } catch (IllegalArgumentException il)
                            {
                                JOptionPane.showMessageDialog(jFrame,"You try to enter something illegal, please exit and try again");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }


                            jPanel.repaint();
                            jPanel.revalidate();


                        }

                }

                    catch (NullPointerException nul)
                    {
                        JOptionPane.showMessageDialog(jFrame,"Please, fill the values correctly");
                    }
                }
            }
        });
    }
}
