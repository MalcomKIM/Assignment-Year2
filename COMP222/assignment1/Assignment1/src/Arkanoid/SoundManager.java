/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arkanoid;

import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

/**
 *
 * @author sgmjin2
 */
public class SoundManager {

    public AudioNode audio_ball_brick_collision;
    public AudioNode audio_ball_player_collision;
    public AudioNode audio_lose;
    public AudioNode audio_win;
    public Node Sounds;
    
    public SoundManager() {
    }
    

    void initSounds() {
        Sounds = new Node("Sounds");
        
        //set properties of the audio when ball collides with the brick
        audio_ball_brick_collision = new AudioNode(Main.app.getAssetManager(), "Sounds/Collision1.wav");
        audio_ball_brick_collision.setPositional(false);
        audio_ball_brick_collision.setLooping(false);
        audio_ball_brick_collision.setVolume(2);
        Sounds.attachChild(audio_ball_brick_collision);

        //set properties of the audio when ball collides with the paddle
        audio_ball_player_collision = new AudioNode(Main.app.getAssetManager(), "Sounds/Collision2.wav");
        audio_ball_player_collision.setPositional(false);
        audio_ball_player_collision.setLooping(false);
        audio_ball_player_collision.setVolume(2);
        Sounds.attachChild(audio_ball_player_collision);
        
        //set properties of the audio when losing the game
        audio_lose= new AudioNode(Main.app.getAssetManager(), "Sounds/Lose.wav");
        audio_lose.setPositional(false);
        audio_lose.setLooping(false);
        audio_lose.setVolume(2);
        Sounds.attachChild(audio_lose);
        
        //set properties of the audio when winning the game
        audio_win= new AudioNode(Main.app.getAssetManager(), "Sounds/Win.wav");
        audio_win.setPositional(false);
        audio_win.setLooping(false);
        audio_win.setVolume(2);
        Sounds.attachChild(audio_win);
        
    }
    
    
}
