/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arkanoid;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author sgmjin2
 */
public class GuiManager {

    public BitmapText titleText;
    public BitmapText levelText;
    public BitmapText pauseText;
    public BitmapText hintText;
    public BitmapText scoreText;
    public BitmapText restartText;
    public BitmapText winText;

    private final float screenWidth;
    private final float screenHeight;

    GuiManager(float screenWidth, float screenHeight) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    public void initText() {
        //set font as default
        BitmapFont guiFont = Main.app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        
        //set properties of the 'title' Text
        titleText = new BitmapText(guiFont, false);
        titleText.setSize(100f);
        titleText.setText("Arkanoid");
        titleText.setLocalTranslation((screenWidth - titleText.getLineWidth()) * 0.5f, screenHeight, 0);

        //set properties of the 'level' Text
        levelText = new BitmapText(guiFont, false);
        levelText.setSize(60f);
        levelText.setText("Level " + Main.app.level);
        levelText.setLocalTranslation(screenWidth / 30, 2 * screenHeight / 3, 0);

        //set properties of the 'pause' Text
        pauseText = new BitmapText(guiFont, false);
        pauseText.setSize(60f);
        pauseText.setText("PAUSE");
        pauseText.setColor(ColorRGBA.Green);
        pauseText.setLocalTranslation(screenWidth / 30, screenHeight / 3, 0);

        //set properties of the 'hint' Text
        hintText = new BitmapText(guiFont, false);
        hintText.setSize(60f);
        hintText.setText("Press Space to Start!");
        hintText.setColor(ColorRGBA.White);
        hintText.setLocalTranslation((screenWidth - hintText.getLineWidth()) * 0.5f, screenHeight / 2, 0);

        //set properties of the 'score' Text
        scoreText = new BitmapText(guiFont, false);
        scoreText.setSize(60f);
        scoreText.setText("Score: " + Main.app.score + "/10");
        scoreText.setColor(ColorRGBA.Red);
        scoreText.setLocalTranslation(screenWidth * 3 / 4, 2 * screenHeight / 3, 0);

        //set properties of the 'restart' Text
        restartText = new BitmapText(guiFont, false);
        restartText.setSize(60f);
        restartText.setText("Press 'R' to Restart!");
        restartText.setColor(ColorRGBA.White);
        restartText.setLocalTranslation((screenWidth - hintText.getLineWidth()) * 0.5f, screenHeight / 2, 0);

        //set properties of the 'win' Text
        winText = new BitmapText(guiFont, false);
        winText.setSize(60f);
        winText.setText("You Win!");
        winText.setColor(ColorRGBA.Red);
        winText.setLocalTranslation((screenWidth - hintText.getLineWidth()) * 0.5f, screenHeight / 4 * 3, 0);
    }
}
