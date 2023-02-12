import java.io.IOException;

public class Main {
    public static void main(String[] args)  {
        try {
            GuiInterface guiInterface = new GuiInterface();
        } catch (IOException e) {
            System.exit(0);
        }
    }
}