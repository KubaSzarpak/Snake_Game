package Game;

import Sounds.SoundTrack_1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameFrame extends JFrame {
    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private GameOverPanel gameOverPanel;
    private BufferedImage icon;
    private final JPanel panel = new JPanel();
    protected Label scoreText;
    protected JProgressBar speedLimit;
    protected int score;
    SoundTrack_1 soundTrack_1;

    GameFrame(){
        soundTrack_1 = new SoundTrack_1();

        Label speedLimitText = new Label("Speed time:");
        speedLimitText.setBackground(Color.darkGray);
        speedLimitText.setForeground(Color.white);
        speedLimitText.setFont(new Font("MV Boli", Font.BOLD, 30));


        scoreText = new Label();
        scoreText.setPreferredSize(new Dimension(140, 30));
        scoreText.setBackground(Color.darkGray);
        scoreText.setForeground(Color.white);
        scoreText.setFont(new Font("MV Boli", Font.BOLD, 30));

        speedLimit = new JProgressBar();
        speedLimit.setPreferredSize(new Dimension(270, 30));
        speedLimit.setPreferredSize(new Dimension(270, 30));
        speedLimit.setStringPainted(true);
        speedLimit.setFont(new Font("MV Boli", Font.BOLD, 30));
        speedLimit.setBackground(Color.LIGHT_GRAY);
        speedLimit.setForeground(Color.magenta);

        panel.setLayout(new BorderLayout());
        panel.add(scoreText, BorderLayout.WEST);
        panel.add(speedLimitText, BorderLayout.CENTER);
        panel.add(speedLimit, BorderLayout.EAST);



        try {
            icon = ImageIO.read(new File("Snake_head.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setTitle("Snake");
        this.setIconImage(icon);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocation(600, 100);
        this.addGameMenu();
        this.playSoundtrack_1();
        this.setVisible(true);

    }

    public void addGameMenu(){
        if(gamePanel != null)
            this.remove(gamePanel);
        if(gameOverPanel != null)
            this.remove(gameOverPanel);

        menuPanel = new MenuPanel(this);
        this.add(menuPanel, BorderLayout.CENTER);
        this.pack();
    }

    public void addGamePanel(){
        if(menuPanel != null)
            this.remove(menuPanel);
        if(gameOverPanel != null)
            this.remove(gameOverPanel);

        gamePanel = new GamePanel(this);
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(panel, BorderLayout.NORTH);
        this.pack();
        this.gamePanel.requestFocusInWindow();
        soundTrack_1.stop();

    }

    public void addGameOver(){
        if(gamePanel != null)
            this.remove(gamePanel);
        if(menuPanel != null)
            this.remove(menuPanel);
        if(panel != null)
            this.remove(panel);

        gameOverPanel = new GameOverPanel(this);
        this.add(gameOverPanel, BorderLayout.CENTER);
        this.pack();
    }

    public void playSoundtrack_1(){
        soundTrack_1.play();
    }
}



