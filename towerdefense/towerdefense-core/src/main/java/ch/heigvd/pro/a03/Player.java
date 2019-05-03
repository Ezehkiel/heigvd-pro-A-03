package ch.heigvd.pro.a03;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Player implements Serializable{

    private static final long serialVersionUID = 1L;
    Vector2 position;
    String textureLoc;

    private static final int col = 4;
    private static final int row = 4;

    Animation animation;
    Texture playerTexture;
    TextureRegion[]  frames;
    TextureRegion currentFrame;
    float stateTime;
    Rectangle bounds;
    String movement;
    boolean reAdjusting;

    public Player(Vector2 position, String textureLoc){
        this.position = position;
        movement = "";

        playerTexture = new Texture(Gdx.files.internal("characterSpriteSheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(playerTexture, playerTexture.getWidth() / col, playerTexture.getHeight() / row);
        frames = new TextureRegion[col * row];

        int index = 0;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                frames[index++] = tmp[i][j];
            }
        }

        animation = new Animation(1, frames);
        stateTime = 0f;
        currentFrame = (TextureRegion) animation.getKeyFrame(0);
        reAdjusting = false;
        bounds = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void update(){

        bounds.set(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());

        if(stateTime < 4){
            stateTime += Gdx.graphics.getDeltaTime();
        }else{
            stateTime = 0;
        }

        if(Gdx.input.isKeyPressed(Keys.W)){
            position.y += 2f;
            movement = "up";
            currentFrame = (TextureRegion) animation.getKeyFrame(12 + stateTime);
        }
        else if(Gdx.input.isKeyPressed(Keys.A)){
            position.x -= 2f;
            movement = "left";
            currentFrame = (TextureRegion) animation.getKeyFrame(4 + stateTime);
        }
        else if(Gdx.input.isKeyPressed(Keys.S)){
            position.y -= 2f;
            movement = "down";
            currentFrame = (TextureRegion) animation.getKeyFrame(0 + stateTime);
        }
        else if(Gdx.input.isKeyPressed(Keys.D)){
            position.x += 2f;
            movement = "right";
            currentFrame = (TextureRegion) animation.getKeyFrame(8 + stateTime);
        }
    }

    public void reAdjust(){
        if(movement == "up"){
            position.y -= 2f;
        }
        if(movement == "down"){
            position.y += 2f;
        }
        if(movement == "right"){
            position.x -= 2f;
        }
        if(movement == "left"){
            position.x += 2f;
        }
    }

    public static void savePlayer(Player playerPosition) throws IOException{
        FileHandle file = Gdx.files.local("player.dat");
        OutputStream out = null;
        try{
            file.writeBytes(serialize(playerPosition.getPosition()), false);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }finally{
            if(out != null) try{out.close();} catch(Exception ex){}
        }

        System.out.println("Saving Player");
    }

    public static Vector2 readPlayer() throws IOException, ClassNotFoundException{
        Vector2 playerPosition = null;
        FileHandle file = Gdx.files.local("player.dat");
        playerPosition = (Vector2) deserialize(file.readBytes());
        return playerPosition;
    }

    @SuppressWarnings("unused")
    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public String getTextureLoc() {
        return textureLoc;
    }

    public void setTextureLoc(String textureLoc) {
        this.textureLoc = textureLoc;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public TextureRegion[] getFrames() {
        return frames;
    }

    public void setFrames(TextureRegion[] frames) {
        this.frames = frames;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }


}
