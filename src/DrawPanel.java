import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represent the animated part of the view with the car images.

public class DrawPanel extends JPanel {
    HashMap<Car,BufferedImage> carToImage;
    // To keep track of a list of single cars position
    ArrayList<Car> cars;

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y, ArrayList<Car> cars) {
        this.carToImage = new HashMap<Car, BufferedImage>();
        this.cars = cars;
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.darkGray);
        // Print an error message in case file is not found with a try/catch block
        for (Car car : cars) {
            try {
                String imgPath = "pics/" + car.modelName + ".jpg";
                carToImage.put(car, ImageIO.read(DrawPanel.class.getResourceAsStream(imgPath)));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Car car: cars) {
            int x = (int) Math.round(car.getPosition()[0]);
            int y = (int) Math.round(car.getPosition()[1]);
            g.drawImage(carToImage.get(car), x, y + 100 * cars.indexOf(car), null); // see javadoc for more info on the parameters
        }

    }
}
