package Game.Galaga.Entities;

import Main.Handler;

import java.awt.image.BufferedImage;
public class EnemyLaser extends BaseEntity{
	
	
	EntityManager enemies;
    int speed = 6;
    
    public EnemyLaser(int x, int y, int width, int height, BufferedImage sprite, Handler handler,EntityManager playerShip) {
        super(x, y, width, height, sprite, handler);
        this.enemies = playerShip;
    }

    @Override
    public void tick() {
        if (!remove) {
            super.tick();
            y += speed;
            bounds.y = y;
            for (BaseEntity enemy : enemies.entities) {
                if (enemy instanceof EnemyLaser || enemy instanceof EnemyLaser || enemy instanceof EnemyBee) {
                    continue;
                }
                if (handler.getGalagaState().entityManager.playerShip.bounds.intersects(bounds)) {
                   handler.getGalagaState().entityManager.playerShip.damage(this);;
	                }
	            }
	        
	    }
	}


}
