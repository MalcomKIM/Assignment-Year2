/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arkanoid;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

/**
 *
 * @author sgmjin2
 */
public class Brick {

    public Geometry geom_brick;
    private final float x;
    private final float y;
    private int lives;
    private final String name;
    
    //Constructor for object 'Brick'
    public Brick(float x, float y, int lives, String name, Texture texture) {
        //set position, number of lives, name and texture
        this.x = x;
        this.y = y;
        this.lives = lives;
        this.name = name;

        Sphere brick = new Sphere(100, 100, 0.8f);
        Material mat = new Material(Main.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");

        mat.setTexture("ColorMap", texture);
        geom_brick = new Geometry(name, brick);
        geom_brick.setMaterial(mat);
        geom_brick.setLocalTranslation(new Vector3f(x, y, 1.25f));
    }

    //update brick's lives
    public void isCollided() {
        lives = lives - 1;

    }

    //get name of the brick
    public String getName() {
        return name;
    }

    //get lives of the brick
    public int current_lives() {
        return lives;
    }

}
