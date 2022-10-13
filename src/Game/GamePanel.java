package Game;

import Sounds.EatingSound;
import Sounds.ImpactSound;
import Sounds.SpeedingSound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static private final int SCREEN_WIDTH = 600;
    static private final int SCREEN_HEIGHT = 600;
    static private final int UNIT_SIZE = 25;
    static private final int GAME_UNIT = (SCREEN_HEIGHT * SCREEN_WIDTH) / UNIT_SIZE;
    static private final int MAIN_DELAY = 90;
    private final int[] x = new int[GAME_UNIT];
    private final int[] y = new int[GAME_UNIT];
    private final Random random;
    private final GameFrame frame;
    private final EatingSound eatingSound = new EatingSound();
    private final SpeedingSound speedingSound = new SpeedingSound();
    private final ImpactSound impactSound = new ImpactSound();
    private int DELAY = 90;
    private int bodyParts = 6;
    private int score;
    private int pointX;
    private int pointY;
    private int specialPowerValue = 100;
    private boolean isSpeeding = false;
    private boolean isMoving = false;
    private boolean start = false;
    private char direction = 'R';
    private double rotation;
    boolean running = false;
    private Timer timer;
    private BufferedImage head_1;
    private BufferedImage head_2;
    private BufferedImage body;
    //    private BufferedImage background;
    private BufferedImage mouse;

    GamePanel(GameFrame frame) {
        this.frame = frame;
        frame.scoreText.setText("Score: " + score);
        frame.speedLimit.setValue(specialPowerValue);
        random = new Random();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.addKeyListener(new MyKeyAdapter());
        this.setLayout(new BorderLayout());
        this.setFocusable(true);

        try {
            head_1 = ImageIO.read(new File("Snake_head1.gif"));
            head_2 = ImageIO.read(new File("Snake_head2.gif"));
            body = ImageIO.read(new File("Snake_body.gif"));
            mouse = ImageIO.read(new File("Mouse.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        startGame();
    }

    public void startGame() {
        newPoint();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

        for (int i = 0; i < bodyParts; i++) {            //starting point
            x[i] = (SCREEN_WIDTH / 2) - (UNIT_SIZE * i);
            y[i] = SCREEN_HEIGHT / 2;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            if(!start) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Calibri", Font.BOLD, 15));
                FontMetrics metrics2 = getFontMetrics(g.getFont());
                g.drawString("Press any arrow", (SCREEN_WIDTH - metrics2.stringWidth("Press any arrow")) / 2, (SCREEN_HEIGHT / 2) - UNIT_SIZE);
                g.drawString("Space activates speed", (SCREEN_WIDTH - metrics2.stringWidth("Space activates speed")) / 2, (SCREEN_HEIGHT / 2) + (UNIT_SIZE * 2));
            }

            switch (direction) {
                case 'R' -> rotation = -90;
                case 'L' -> rotation = 90;
                case 'U' -> rotation = 180;
                case 'D' -> rotation = 0;
            }
            Graphics2D images2D = (Graphics2D) g;

            {
                AffineTransform at = AffineTransform.getTranslateInstance(x[0], y[0]);
                at.rotate(Math.toRadians(rotation), head_1.getWidth() >> 1, head_1.getHeight() >> 1);

                images2D.drawImage(isSpeeding ? head_2 : head_1, at, null);

                if (isSpeeding) {
                    images2D.drawImage(head_2, at, null);
                } else {
                    images2D.drawImage(head_1, at, null);
                }

                for (int i = 1; i < bodyParts; i++) {
                    images2D.drawImage(body, x[i], y[i], null);
                }

                AffineTransform at2 = AffineTransform.getTranslateInstance(pointX, pointY);
                at2.rotate(Math.toRadians(rotation), head_1.getWidth() >> 1, head_1.getHeight() >> 1);
                images2D.drawImage(mouse, at2, null);
            }  //draws body's parts

        } else {
            gameOver();
        }
    }

    public void gameOver() {
        saveScore();
        eatingSound.stop();
        speedingSound.stop();
        frame.addGameOver();
        frame.score = this.score;
        frame.playSoundtrack_1();
    }

    public void newPoint() {
        pointX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        pointY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }
    }

    public void checkPoints() {
        if ((x[0] == pointX) && (y[0] == pointY)) {
            bodyParts++;
            score++;
            frame.scoreText.setText("Score: " + score);
            newPoint();
            DELAY -= 1;
            timer.setDelay(DELAY);
            eatingSound.play();
        }
    }

    public void checkCollisions() {
        //checks if head collides with the body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
        //checks if head touches top border
        if (y[0] < 0) {
//            running = false;
            y[0] = SCREEN_HEIGHT - UNIT_SIZE;
        }
        //checks if head touches bottom border
        if (y[0] >= SCREEN_HEIGHT) {
//            running = false;
            y[0] = 0;
        }
        //checks if head touches left border
        if (x[0] < 0) {
//            running = false;
            x[0] = SCREEN_WIDTH - UNIT_SIZE;
        }
        //checks if head touches right border
        if (x[0] >= SCREEN_WIDTH) {
//            running = false;
            x[0] = 0;
        }

        if (!running) {
            impactSound.play();
            timer.stop();
        }
    }

    public void saveScore() {
        try {
            FileWriter writer = new FileWriter("Scores.txt", true);
            writer.append(String.valueOf(score)).append("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (start) {
            if (running) {
                move();
                checkPoints();
                checkCollisions();
                isMoving = false;

                //Speed options
                if (isSpeeding && specialPowerValue > 0) {
                    DELAY = (MAIN_DELAY - 40) - (score / 10);
                    timer.setDelay(DELAY);
                    specialPowerValue -= 5;
                    frame.speedLimit.setValue(specialPowerValue);
                } else {
                    isSpeeding = false;
                    speedingSound.stop();
                    if (specialPowerValue < 100) {
                        DELAY = (MAIN_DELAY - score);
                        timer.setDelay(DELAY);
                        specialPowerValue += 1;
                        frame.speedLimit.setValue(specialPowerValue);
                    }
                }

            }
            repaint();
        }
    }

    public class MyKeyAdapter implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == ' ')
                if (start) {
                    if (specialPowerValue > 0) {
                        isSpeeding = true;
                        speedingSound.play();
                    }
                }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!isMoving) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R')
                            direction = 'L';
                        start = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L')
                            direction = 'R';
                        start = true;
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D')
                            direction = 'U';
                        start = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U')
                            direction = 'D';
                        start = true;
                        break;
                }
                isMoving = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
                isSpeeding = false;
        }
    }
}
