import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
/**
 * Класс, представляющий собой графический интерфейс для работы с классом {@link FileWorking}
 * @author Андрей Помошников
 * @version 1.0
 */
public class GuiInterface {
    /** Поле оболочки GUI */
    private static final JFrame jFrame = new JFrame();

    /** Поле шрифта */
    private static final Font font = new Font("Times New Roman",Font.BOLD,20);
    /** Поле имени диска */
    private static String drive;
    /** Поле поле имени файла */
    private static String filename;
    /**
     * Конструктор - запуск GUI
     */
    GuiInterface() throws IOException {
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
        launchButton.setFont(font);
        jPanel.add(launchButton,BorderLayout.CENTER);
        Launch(launchButton,jPanel);
    }
    /**
     * Процедура для подсчета латинских букв в файле и создание нового с результатом
     * @param launchButton - кнопка Launch
     * @param  jPanel -  текущая панель
     * @exception IllegalArgumentException если были поданы неверные имя или диск
     * @exception AccessDeniedException если было отказано в доступе
     * @exception NullPointerException если были введены пустые значения
     * @exception IOException если произошла ошибка во время выполнения
     */
    private void Launch(JButton launchButton, JPanel jPanel) throws IOException
    {
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    drive = JOptionPane.showInputDialog(jPanel, "Please, enter the name of your local drive",
                            "C");

                    if (!drive.isEmpty())
                    {

                        filename = JOptionPane.showInputDialog(jPanel, "Please, enter the filename",
                                "my_file");

                        if (!filename.isEmpty()) {

                            try {
                               FileWorking fileWorking = new FileWorking(drive,filename);
                               if (fileWorking.working() == 0)
                               {

                                   String read = Files.readString(Path.of(drive + ":\\" + filename + "_symbols"+
                                           ".txt"));
                                   JTextArea display = new JTextArea(read, 12 ,12);
                                   display.setFont(font);
                                   display.setEditable(false);
                                   JScrollPane scroll = new JScrollPane(display);
                                   scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                                   JOptionPane.showMessageDialog(jFrame,scroll,drive + ":\\" + filename + "_symbols"+
                                           ".txt", JOptionPane.INFORMATION_MESSAGE);

                               }
                               else
                               {
                                   JOptionPane.showMessageDialog(jFrame,"File doesn't exist");
                               }
                            }
                            catch (IllegalArgumentException illegalArgumentException)
                            {
                                JOptionPane.showMessageDialog(jFrame,"You try to write illegal drive or filename, please try again");
                            }
                            catch (AccessDeniedException accessDeniedException)
                            {
                                JOptionPane.showMessageDialog(jFrame,"You don't have an access to this file");
                            }
                            catch (IOException ex) {
                                JOptionPane.showMessageDialog(jFrame,"Something went wrong");
                            }


                            jPanel.repaint();
                            jPanel.revalidate();


                        }
                        else
                        {
                            JOptionPane.showMessageDialog(jFrame,"Please, don't write empty values");
                        }

                }
                    else
                    {
                        JOptionPane.showMessageDialog(jFrame,"Please, don't write empty values");
                    }

                }
                catch (NullPointerException nul)
                {
                    JOptionPane.showMessageDialog(jFrame,"Please, fill the values correctly and don't " +
                            "interrupt input");
                }
            }
        });
    }
}
