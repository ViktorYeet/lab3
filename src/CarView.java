import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the full view of the MVC pattern of your car simulator.
 * It initializes with being center on the screen and attaching it's controller in it's state.
 * It communicates with the Controller by calling methods of it when an action fires of in
 * each of it's components.
 **/

public class CarView extends JFrame{
    private static final int X = 800;
    private static final int Y = 800;

    // The controller member
    CarController carC;
    DrawPanel drawPanel;
    JPanel controlPanel = new JPanel();
    JPanel gasBrakePanel = new JPanel();
    JSpinner gasBrakeSpinner = new JSpinner();
    int gasAmount = 0;
    int brakeAmount = 0;
    JLabel gasBrakeLabel = new JLabel("Amount of gas/brake");
    JButton gasButton = new JButton("Gas");
    JButton brakeButton = new JButton("Brake");
    JButton turboOnButton = new JButton("Saab Turbo on");
    JButton turboOffButton = new JButton("Saab Turbo off");
    JButton liftBedButton = new JButton("Scania Lift Bed");
    JButton lowerBedButton = new JButton("Lower Lift Bed");
    JButton startButton = new JButton("Start all cars");
    JButton stopButton = new JButton("Stop all cars");

    // Constructor
    public CarView(String framename, CarController cc) {
        this.carC = cc;
        drawPanel  = new DrawPanel(X, Y-240,carC.cars);
        initComponents(framename);
    }

    // Sets everything in place and fits everything
    private void initComponents(String title) {

        this.setTitle(title);
        this.setPreferredSize(new Dimension(X,Y));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setBounds(0,0,X,Y);

        this.add(drawPanel);

        SpinnerModel spinnerModel =
                new SpinnerNumberModel(0, //initial value
                        0, //min
                        100, //max
                        1);//step
        gasBrakeSpinner = new JSpinner(spinnerModel);

        gasBrakeSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                gasAmount = (int) ((JSpinner)e.getSource()).getValue();
                brakeAmount = (int) ((JSpinner)e.getSource()).getValue();
            }
        });

        gasBrakePanel.setLayout(new BorderLayout());
        // Adds label for gas/brake (left of screen)
        gasBrakePanel.add(gasBrakeLabel, BorderLayout.PAGE_START);
        // Adds number input for gas/brake
        gasBrakePanel.add(gasBrakeSpinner, BorderLayout.PAGE_END);


        this.add(gasBrakePanel);

        controlPanel.setLayout(new GridLayout(2,4));

        controlPanel.add(gasButton, 0);
        controlPanel.add(turboOnButton, 1);
        controlPanel.add(liftBedButton, 2);
        controlPanel.add(brakeButton, 3);
        controlPanel.add(turboOffButton, 4);
        controlPanel.add(lowerBedButton, 5);
        controlPanel.setPreferredSize(new Dimension((X/2)+4, 200));
        this.add(controlPanel);
        controlPanel.setBackground(Color.CYAN);


        startButton.setBackground(Color.blue);
        startButton.setForeground(Color.green);
        startButton.setPreferredSize(new Dimension(X/5-15,200));
        this.add(startButton);


        stopButton.setBackground(Color.red);
        stopButton.setForeground(Color.black);
        stopButton.setPreferredSize(new Dimension(X/6-15,200));
        this.add(stopButton);

        liftBedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Car car : carC.cars){
                    if (car.getClass() == Scania.class){
                        ((Scania) car).raiseTrailer(5);
                    }
                }

            }
        });

        lowerBedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Car car : carC.cars){
                    if (car.getClass() == Scania.class){
                        ((Scania) car).lowerTrailer(5);
                    }
                }
            }
        });

        turboOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Car car : carC.cars) {
                    if (car.getClass() == Saab95.class) {
                        ((Saab95) car).setTurboOn();
                    }
                }
            }
        });
        turboOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Car car : carC.cars) {
                    if (car.getClass() == Saab95.class) {
                        ((Saab95) car).setTurboOff();
                    }
                }
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Car car : carC.cars) {
                    car.startEngine();
                }
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Car car : carC.cars) {
                    car.stopEngine();
                }
            }
        });

        // This actionListener is for the gas button only
        gasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carC.gas(gasAmount);
            }
        });

        // actionlistener for brake button
        brakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carC.brake(brakeAmount);
            }
        });

        // Make the frame pack all it's components by respecting the sizes if possible.
        this.pack();

        // Get the computer screen resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // Center the frame
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        // Make the frame visible
        this.setVisible(true);
        // Make sure the frame exits when "x" is pressed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}