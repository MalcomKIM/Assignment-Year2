package Arkanoid;

import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.input.controls.ActionListener;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author Minhao Jin
 */
public class Main extends SimpleApplication {

    static Main app;

    SoundManager sm;
    GuiManager gm;

    float screenWidth;
    float screenHeight;

    Geometry player;
    Geometry ball;
    ParticleEmitter smoketrail;

    Node Boundaries;
    Node Bricks;
    Node Explosions;

    Vector3f unit_direction;
    float velocity = 7f;

    boolean KEY_LEFT_ENABLE;
    boolean KEY_RIGHT_ENABLE;
    boolean isPaused = false;

    int level = 1;
    int score = 0;
    String ballColideWith = "paddle";

    String gameState;
    List<Brick> BrickList = new ArrayList();

    public static void main(String[] args) {
        //fix the resolution of the game window
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1366, 768);
        app = new Main();
        app.start();
        app.setSettings(settings);
        app.restart();
    }

    //initialize all components
    @Override
    public void simpleInitApp() {
        gameState = "before_start";
        initCamera();
        setDisplayFps(false);
        setDisplayStatView(false);

        unit_direction = direction_generator();
        enable_paddle(true);
        initLight();
        initAudio();
        initBall();
        initLevel1_Bricks();
        initBackground();
        initBoundaries();
        initPaddle();
        initKeys();
        initGUI();
        initExplosion();
        initSmokeTrail();
    }

    //initialize audios
    private void initAudio() {
        sm = new SoundManager();
        sm.initSounds();
        rootNode.attachChild(sm.Sounds);
    }

    //initialize texts
    private void initGUI() {
        gm = new GuiManager(screenWidth, screenHeight);
        gm.initText();
        guiNode.detachAllChildren();
        guiNode.attachChild(gm.titleText);
        guiNode.attachChild(gm.levelText);
        guiNode.attachChild(gm.scoreText);
    }

    //initialize lights
    private void initLight() {
        DirectionalLight light = new DirectionalLight();
        AmbientLight ambient = new AmbientLight();

        light.setDirection(new Vector3f(0f, 0f, -5f));

        ColorRGBA lightColor = new ColorRGBA();
        light.setColor(lightColor.mult(0.8f));
        ambient.setColor(lightColor.mult(0.2f));

        rootNode.addLight(light);
        rootNode.addLight(ambient);
    }

    //initialize camera
    private void initCamera() {
        flyCam.setEnabled(false);
        flyCam.setMoveSpeed(100 * speed);
        cam.setLocation(new Vector3f(0f, 0f, 30f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        screenWidth = cam.getWidth();
        screenHeight = cam.getHeight();
    }

    //initialize background
    private void initBackground() {
        Quad background = new Quad(20f, 16f);
        Geometry geom_background = new Geometry("Background", background);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.DarkGray);
        geom_background.setLocalTranslation(-10, -8, 0);
        geom_background.setMaterial(mat);

        rootNode.attachChild(geom_background);
    }

    //initialize paddle/player
    private void initPaddle() {
        Vector3f position = new Vector3f(0f, -7.75f, 1.25f);
        Box paddle = new Box(position, 1f, 0.125f, 1f);
        player = new Geometry("Box", paddle);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        player.setMaterial(mat);
        rootNode.attachChild(player);
    }

    //initialize boundaries
    private void initBoundaries() {
        Boundaries = new Node("Boundries");
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", ColorRGBA.randomColor());
        mat.setColor("Ambient", ColorRGBA.randomColor());
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 100);
        mat.setBoolean("UseMaterialColors", true);

        Vector3f left_position = new Vector3f(-10f, 0f, 1f);
        Box left_bound = new Box(left_position, 0.1f, 8f, 1f);
        Geometry geom_left_bound = new Geometry("left_bound", left_bound);
        geom_left_bound.setMaterial(mat);
        Boundaries.attachChild(geom_left_bound);

        Vector3f right_position = new Vector3f(10f, 0f, 1f);
        Box right_bound = new Box(right_position, 0.1f, 8f, 1f);
        Geometry geom_right_bound = new Geometry("right_bound", right_bound);
        geom_right_bound.setMaterial(mat);
        Boundaries.attachChild(geom_right_bound);

        Vector3f up_position = new Vector3f(0f, 8f, 1f);
        Box up_bound = new Box(up_position, 10f, 0.1f, 1f);
        Geometry geom_up_bound = new Geometry("up_bound", up_bound);
        geom_up_bound.setMaterial(mat);
        Boundaries.attachChild(geom_up_bound);

        rootNode.attachChild(Boundaries);
    }

    //initialize the ball
    private void initBall() {
        Sphere s = new Sphere(100, 100, 0.5f);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/fireball.jpg"));

        ball = new Geometry("Sphere", s);
        ball.setMaterial(mat);
        ball.move(new Vector3f(0f, -7f, 1.25f));

        rootNode.attachChild(ball);
    }

    //initialize explosion effect
    private void initExplosion() {
        Explosions = new Node("Explosions");
        rootNode.attachChild(Explosions);
    }

    //initialize SmokeTrail effect
    private void initSmokeTrail() {
        smoketrail = new ParticleEmitter("SmokeTrail", ParticleMesh.Type.Triangle, 30);
        smoketrail.setStartColor(ColorRGBA.Red);
        smoketrail.setEndColor(ColorRGBA.Orange);
        smoketrail.setStartSize(.2f);
        smoketrail.setEndSize(1f);

        smoketrail.setFacingVelocity(true);
        smoketrail.setLowLife(0.2f);
        smoketrail.setHighLife(0.3f);
        smoketrail.setLocalTranslation(ball.getLocalTranslation());

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Materials/smoketrail.png"));

        smoketrail.setImagesX(1);
        smoketrail.setImagesY(3);

        smoketrail.setMaterial(mat);
        rootNode.attachChild(smoketrail);
    }

    //initialize the scene of Level 1
    private void initLevel1_Bricks() {
        Bricks = new Node("bricks");
        int count = 1;
        for (int x = -8; x <= 8; x = x + 4) {
            for (int y = 1; y <= 8; y = y + 4) {
                String name = "brick" + count;
                Brick b = new Brick(x, y, 1, name, assetManager.loadTexture("Textures/brick1.png"));
                BrickList.add(b);
                Bricks.attachChild(b.geom_brick);
                count++;
            }
        }
        rootNode.attachChild(Bricks);
    }

    //initialize the scene of Level 2
    private void initLevel2() {
        gameState = "before_start";
        level = 2;
        score = 0;
        velocity=7f;
        ballColideWith = "paddle";
        unit_direction = direction_generator();
        gm.levelText.setText("level " + level);
        gm.scoreText.setText("Score: " + score + "/10");
        rootNode.detachChild(Bricks);
        rootNode.detachChild(ball);
        rootNode.detachChild(player);

        initPaddle();
        initBall();
        initLevel2_Bricks();
    }

    //initialize the bricks of Level 2
    private void initLevel2_Bricks() {
        Bricks = new Node("bricks");
        int count = 1;
        for (int x = -8; x <= 8; x = x + 4) {
            for (int y = 1; y <= 8; y = y + 4) {
                String name = "brick" + count;
                Brick b = new Brick(x, y, 2, name, assetManager.loadTexture("Textures/brick2.jpg"));
                BrickList.add(b);
                Bricks.attachChild(b.geom_brick);
                count++;
            }
        }
        rootNode.attachChild(Bricks);
    }

    //freeze or release the paddle
    private void enable_paddle(boolean isEnabled) {
        KEY_LEFT_ENABLE = isEnabled;
        KEY_RIGHT_ENABLE = isEnabled;
    }

    //initialize keys
    private void initKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(analogListener, "Left", "Right");

        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(actionListener, "Pause");

        inputManager.addMapping("Launch", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Launch");

        inputManager.addMapping("Restart", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addListener(actionListener, "Restart");
    }

    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (!isPaused) {
                //if game is not paused
                if (name.equals("Left") && KEY_LEFT_ENABLE) {
                    //the paddle goes left when 'KEY_LEFT' is pressed
                    Vector3f v = player.getLocalTranslation();
                    player.setLocalTranslation(v.x - 25 * tpf, v.y, v.z);
                }
                if (name.equals("Right") && KEY_RIGHT_ENABLE) {
                    //the paddle goes right when 'KEY_RIGHT' is pressed
                    Vector3f v = player.getLocalTranslation();
                    player.setLocalTranslation(v.x + 25 * tpf, v.y, v.z);
                }
            }
        }
    };

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Pause") && !isPressed) {
                //if user presses 'P', change the pause state to the opposite one.
                isPaused = !isPaused;
                if (isPaused) {
                    //if game is paused, show the 'pause' text
                    guiNode.attachChild(gm.pauseText);
                } else {
                    //if game is not paused, hide the 'pause' text
                    guiNode.detachChild(gm.pauseText);
                }
            }

            if (name.equals("Launch") && gameState.equals("before_start") && !isPaused) {
                //before the game starts,if player presses 'Space', then hide hint text
                gameState = "running";
                guiNode.detachChild(gm.hintText);
            }

            if (name.equals("Restart") && level == 1 && gameState.equals("lose") && !isPaused) {
                //if 'R' is pressed when player loses in level 1, restart level 1
                score = 0;
                level = 1;
                velocity=7f;
                ballColideWith = "paddle";
                rootNode.detachAllChildren();
                simpleInitApp();
            }

            if (name.equals("Restart") && level == 2 && gameState.equals("lose") && !isPaused) {
                //if 'R' is pressed when player loses in level 2, restart level 2
                guiNode.detachChild(gm.restartText);
                velocity=7f;
                ballColideWith = "paddle";
                initLevel2();
            }

            if (name.equals("Restart") && level == 2 && gameState.equals("win") && !isPaused) {
                //if 'R' is pressed when player wins in level 2, restart level 1
                score = 0;
                level = 1;
                velocity=7f;
                ballColideWith = "paddle";
                rootNode.detachAllChildren();
                simpleInitApp();
            }

        }
    };

    @Override
    public void simpleUpdate(float tpf) {
        if (isPaused) {
            //if the game is paused, stop updating everything
            return;
        }

        //make the smoketrail follow the ball
        smoketrail.setLocalTranslation(ball.getLocalTranslation());
        smoketrail.getParticleInfluencer().setInitialVelocity(unit_direction.mult(-1f));

        if (gameState.equals("before_start")) {
            //player can choose one place to launch the ball before game starts
            guiNode.attachChild(gm.hintText);
            player_bound_collision();
            ball.setLocalTranslation(player.getLocalTranslation().x, -7f, 1.25f);
        } else if (gameState.equals("running")) {
            //when the game is running, update ball's moving direction and detect all collisions
            ball.move(unit_direction.mult(tpf).mult(velocity));
            player_bound_collision();
            ball_bound_collision();
            ball_player_collision();
            ball_brick_collision();
        }

        if (ball.getLocalTranslation().y < -10f && !gameState.equals("lose") && Bricks.getQuantity() > 0) {
            //when the player loses the game
            sm.audio_lose.playInstance();
            gameState = "lose";
            guiNode.attachChild(gm.restartText);
            enable_paddle(false);
        }

        if (Bricks.getQuantity() == 0 && level == 1) {
            //when the player wins in Level 1 
            sm.audio_win.playInstance();
            initLevel2();
        }

        if (Bricks.getQuantity() == 0 && level == 2 && !gameState.equals("win")) {
            //when the player wins in Level 2
            sm.audio_win.playInstance();
            guiNode.attachChild(gm.restartText);
            guiNode.attachChild(gm.winText);
            gameState = "win";
            enable_paddle(false);
        }
    }

    //detect the collision between the paddle and boundaries
    private void player_bound_collision() {
        CollisionResults results = new CollisionResults();
        Boundaries.collideWith(player.getWorldBound(), results);
        if (results.size() > 0) {
            if (player.getLocalTranslation().x < 0f) {
                KEY_LEFT_ENABLE = false;
            } else {
                KEY_RIGHT_ENABLE = false;
            }
        } else {
            enable_paddle(true);
        }
    }

    //detect the collision between the ball and boundaries
    private void ball_bound_collision() {
        CollisionResults results = new CollisionResults();
        Boundaries.collideWith(ball.getWorldBound(), results);
        if (results.size() > 0) {
            ballColideWith = "boundaries";
            Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            mat.setColor("Diffuse", ColorRGBA.randomColor());
            mat.setColor("Ambient", ColorRGBA.randomColor());
            mat.setColor("Specular", ColorRGBA.White);
            mat.setFloat("Shininess", 100);
            mat.setBoolean("UseMaterialColors", true);

            Boundaries.getChild("left_bound").setMaterial(mat);
            Boundaries.getChild("right_bound").setMaterial(mat);
            Boundaries.getChild("up_bound").setMaterial(mat);

            String closestGeom = results.getClosestCollision().getGeometry().getName();
            switch (closestGeom) {
                case "up_bound":
                    unit_direction.setY(-Math.abs(unit_direction.y));
                    break;
                case "left_bound":
                    unit_direction.setX(Math.abs(unit_direction.x));
                    if (Math.abs(unit_direction.y) < 0.2f) {
                        Vector3f newdir = direction_generator();
                        if (unit_direction.y < 0) {
                            newdir.y = -Math.abs(newdir.y);
                        } else {
                            newdir.y = Math.abs(newdir.y);
                        }
                        unit_direction = newdir;
                    }
                    break;
                case "right_bound":
                    unit_direction.setX(-Math.abs(unit_direction.x));
                    if (Math.abs(unit_direction.y) < 0.2f) {
                        Vector3f newdir = direction_generator();
                        if (unit_direction.y < 0) {
                            newdir.y = -Math.abs(newdir.y);
                        } else {
                            newdir.y = Math.abs(newdir.y);
                        }
                        unit_direction = newdir;
                    }
                    break;
            }
        }
    }

    //detect the collision between the ball and the paddle
    private void ball_player_collision() {
        CollisionResults results = new CollisionResults();
        player.collideWith(ball.getWorldBound(), results);
        if (results.size() > 0 && !"paddle".equals(ballColideWith)) {
            ballColideWith = "paddle";
            sm.audio_ball_player_collision.playInstance();
            float ball_x = ball.getLocalTranslation().x;
            float player_x = player.getLocalTranslation().x;
            float x = unit_direction.x;
            float y = unit_direction.y;
            float new_x;
            float length;
            if (ball_x > player_x) {
                new_x = x + Math.abs(ball_x - player_x);
            } else {
                new_x = x - Math.abs(ball_x - player_x);
            }
            length = (float) Math.sqrt(new_x * new_x + y * y);
            unit_direction.setX(new_x / length);
            unit_direction.setY(Math.abs(y / length));

        }
    }

    //detect the collision between the ball and bricks
    private void ball_brick_collision() {

        CollisionResults results = new CollisionResults();
        Bricks.collideWith(ball.getWorldBound(), results);

        if (results.size() > 0) {
            Geometry closestGeom = results.getClosestCollision().getGeometry();
            String closestGeom_name = closestGeom.getName();
            if (!closestGeom_name.equals(ballColideWith)) {
                float ball_x = ball.getLocalTranslation().x;
                float ball_y = ball.getLocalTranslation().y;

                float brick_x = closestGeom.getLocalTranslation().x;
                float brick_y = closestGeom.getLocalTranslation().y;

                float x_diff = ball_x - brick_x;
                float y_diff = ball_y - brick_y;

                sm.audio_ball_brick_collision.playInstance();

                float k = -x_diff / y_diff;
                float x = unit_direction.x;
                float y = unit_direction.y;

                float new_x = (2 * y * k + x - x * k * k) / (k * k + 1);
                float new_y = (y * k * k + 2 * x * k - y) / (k * k + 1);
                unit_direction.setX(new_x);
                unit_direction.setY(new_y);

                Brick brick = null;
                for (Brick b : BrickList) {
                    if (b.getName().equals(closestGeom_name)) {
                        brick = b;
                    }
                }
                brick.isCollided();
                if (brick.current_lives() == 0) {
                    score++;
                    gm.scoreText.setText("Score: " + score + "/10");
                    Bricks.detachChildNamed(closestGeom_name);
                    explode(closestGeom.getLocalTranslation());
                    velocity=velocity+1f;
                }
                ballColideWith=closestGeom_name;
            }
        }
    }

    //generate a random unit vector in certain range
    private Vector3f direction_generator() {
        float y = (float) (Math.random() * 0.8f + 0.2f);
        float x = (float) Math.sqrt(1 - y * y);
        if (x >= 0.5) {
            return new Vector3f(x, y, 0);
        } else {
            return new Vector3f(-x, y, 0);
        }
    }

    //provide 'assetManager' for other classes
    @Override
    public AssetManager getAssetManager() {
        return assetManager;
    }

    //create explosion at certain position
    private void explode(Vector3f explosion_position) {
        Explosions.detachAllChildren();
        ParticleEmitter explosion = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);

        explosion.setLowLife(0.5f);
        explosion.setHighLife(1f);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Materials/shockwave.png"));
        explosion.setMaterial(mat);

        explosion.setImagesX(1);
        explosion.setImagesY(1);

        explosion.setEndColor(ColorRGBA.Red);
        explosion.setStartColor(ColorRGBA.Orange);

        explosion.setStartSize(0.5f);
        explosion.setEndSize(0.1f);

        explosion.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, 1));
        explosion.getParticleInfluencer().setVelocityVariation(0.2f);

        explosion.setGravity(0, -1, 0);

        Explosions.attachChild(explosion);

        explosion.setLocalTranslation(explosion_position);
        explosion.setParticlesPerSec(0);
        explosion.emitAllParticles();
    }
}
