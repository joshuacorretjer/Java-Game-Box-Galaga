package Game.Galaga.Entities;

import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by AlexVR on 1/25/2020
 */
public class PlayerShip extends BaseEntity{

    private int health = 3,attackCooldown = 30,speed =6,destroyedCoolDown = 60*7;
    private boolean attacking = false, destroyed = false;
    private Animation deathAnimation;


     public PlayerShip(int x, int y, int width, int height, BufferedImage sprite, Handler handler) {
        super(x, y, width, height, sprite, handler);

        deathAnimation = new Animation(256,Images.galagaPlayerDeath);

    }

    @Override
    public void tick() {
        super.tick();

        if(handler.getScoreManager().getGalagaCurrentScore()==1000) {
            handler.getMusicHandler().playEffect("OnaRool.wav");
            handler.getScoreManager().addGalagaCurrentScore(100);
        }
        int SpawnCooldown = 60 * (random.nextInt(20) + 100);
		if (SpawnCooldown>0) {
			SpawnCooldown--;
		}else {
			handler.getGalagaState().entityManager.addBee();
			handler.getGalagaState().entityManager.addButterfly();
	}
        if (destroyed){
            if (destroyedCoolDown<=0){
                destroyedCoolDown=60*7;
                destroyed=false;
                deathAnimation.reset();
                bounds.x=x;
            }else{
                deathAnimation.tick();
                destroyedCoolDown--;
            }
        }else {
            if (attacking) {
                if (attackCooldown <= 0) {
                    attacking = false;
                } else {
                    attackCooldown--;
                }
            }
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER) && !attacking) {
                handler.getMusicHandler().playEffect("laser.wav");
                attackCooldown = 30;
                attacking = true;
                handler.getGalagaState().entityManager.entities.add(new PlayerLaser(this.x + (width / 2), this.y - 3, width / 5, height / 2, Images.galagaPlayerLaser, handler, handler.getGalagaState().entityManager));

            }
           
            
            
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
                health--;
                destroyed = true;
                handler.getMusicHandler().playEffect("explosion.wav");
                }
            
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_B)) {
                health++;
                }
            if (handler.getKeyManager().left) {
                x -= (speed);
                if(x<=handler.getWidth()/4)
    				x = handler.getWidth()/4;
    			{
    			}
            }
            if (handler.getKeyManager().right) {
                x += (speed);
                if (x>=handler.getWidth()/4 + handler.getWidth()/2 - width) {
    				x = handler.getWidth()/4 + handler.getWidth()/2 - width;
    			}
            }

            bounds.x = x;
        }

    }

    @Override
    public void render(Graphics g) {
         if (destroyed){
             if (deathAnimation.end){
                 g.drawString("READY",handler.getWidth()/2-handler.getWidth()/12,handler.getHeight()/2);
             }else {
                 g.drawImage(deathAnimation.getCurrentFrame(), x, y, width, height, null);
             }
         }else {
             super.render(g);
         }
    }

    @Override
    public void damage(BaseEntity damageSource) {
        if (damageSource instanceof PlayerLaser){
            return;
        }
        health--;
        destroyed = true;
        handler.getMusicHandler().playEffect("explosion.wav");

        bounds.x = -10;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

}
