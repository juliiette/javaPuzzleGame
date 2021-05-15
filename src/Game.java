import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JFrame {

    private ArrayList<Point> solution;
    private ArrayList<PuzzleButton> buttons;
    private JPanel panel;
    private BufferedImage sourceImage;
    private BufferedImage resizedImage;
    private int width, height;
    private final int DESIRED_WIDTH = 1000;
    private Image cell;
    private PuzzleButton lastButton;
    private static final Random RANDOM = new Random();
    private boolean gameOver;

    public Game(){
        gameOver = true;
        initUI();
    }

    public void initUI(){
        solution = fillSolution(solution);
        buttons = new ArrayList<>();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        panel.setLayout(new GridLayout(4, 5));

        try {
            sourceImage = loadImage();
            int height = getNewHeight(sourceImage.getWidth(), sourceImage.getHeight());

            resizedImage = resizeImage(sourceImage, height);

        } catch (IOException exception){
            System.out.println("Problems with source image.");
        }

        width = resizedImage.getWidth();
        height = resizedImage.getHeight();

        add(panel, BorderLayout.CENTER);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                cell = createImage(new FilteredImageSource(resizedImage.getSource(),
                        new CropImageFilter(j*width/5, i*height/4, width/5, height/4)));

                PuzzleButton button = new PuzzleButton(cell);
                button.putClientProperty("position", new Point(i, j));

                if (i== 3 && j == 4){
                    lastButton = new PuzzleButton();
                    lastButton.setBorderPainted(false);
                    lastButton.setContentAreaFilled(false);
                    lastButton.setLastButton(true);
                } else {
                    buttons.add(button);
                }
            }
        }

        newGame();
    }

    private void newGame(){
        do {
            // reset();
            shuffle();
        } while (!isSolvable());

        gameOver = false;
    }

    private void shuffle(){

    }

    private BufferedImage resizeImage(BufferedImage originImage, int height){
        BufferedImage resizedImage = new BufferedImage(1000, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originImage, 0, 0, 1000, height, null);
        g.dispose();

        return resizedImage;
    }

    private BufferedImage loadImage() throws IOException {
        return ImageIO.read(new File("images/David_La_morte_di_Seneca.jpg"));
    }

    private int getNewHeight(int w, int h){
        double ratio = DESIRED_WIDTH/(double)w;

        return (int)(h * ratio);
    }

    private ArrayList<Point> fillSolution(ArrayList<Point> list){
        list = new ArrayList<Point>();

        list.add(new Point(0,0));
        list.add(new Point(0,1));
        list.add(new Point(0,2));
        list.add(new Point(0,3));
        list.add(new Point(0,4));

        list.add(new Point(1,0));
        list.add(new Point(1,1));
        list.add(new Point(1,2));
        list.add(new Point(1,3));
        list.add(new Point(1,4));

        list.add(new Point(2,0));
        list.add(new Point(2,1));
        list.add(new Point(2,2));
        list.add(new Point(2,3));
        list.add(new Point(2,4));

        list.add(new Point(3,0));
        list.add(new Point(3,1));
        list.add(new Point(3,2));
        list.add(new Point(3,3));
        list.add(new Point(3,4));

        return list;
    }

}
