package Game.Galaga.Entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by AlexVR on 1/25/2020
 */
public class EntityManager {
	Random random;
	int maxBee = 16;
	int maxShip = 12;
	int counter1,counter2;
	boolean avaliable = true;
	boolean avaliableButterfly = true;
	public ArrayList<BaseEntity> entities;
	public PlayerShip playerShip;
	public int row=0,col=0,rowButterfly = 0, colButterfly = 1;
	public boolean[][] position = new boolean[4][8];
	public EntityManager(PlayerShip playerShip) {
		random = new Random();
		entities = new ArrayList<>();
		this.playerShip = playerShip;
	}

	public void tick(){
		playerShip.tick();
		ArrayList<BaseEntity> toRemove = new ArrayList<>();
		for (BaseEntity entity: entities){
			if (entity.remove){
				toRemove.add(entity);
				continue;
			}
			entity.tick();
			if (entity.bounds.intersects(playerShip.bounds)){
				playerShip.damage(entity);
			}
		}
		for (BaseEntity toErase:toRemove){
			entities.remove(toErase);
		}

	}

	public void render(Graphics g){
		for (BaseEntity entity: entities){
			entity.render(g);
		}
		playerShip.render(g);

	}

	public void addBee () {
		avaliable=true;
		int counter1 = 0; 
		for(BaseEntity entity:entities)
			if (entity instanceof EnemyBee)
				counter1 ++;

		if(counter1 == maxBee)
			avaliable = false;
		if (avaliable) {
			while(position[row][col] || row<2) {
				row = random.nextInt(2) + 2;
				col = random.nextInt(8);		
			}
			entities.add(new EnemyBee(0,0,32,32,playerShip.handler, row,col));
			position[row][col] = true;

		}

	}

	public void addButterfly() {
		avaliableButterfly=true;
		int counter2 =0; 
		for(BaseEntity entity:entities)
			if (entity instanceof EnemyButterfly)
				counter2 ++;
		if(counter2 == maxShip)
			avaliableButterfly = false;
		if (avaliableButterfly) {
			while(position[rowButterfly][colButterfly] || colButterfly > 7) {
				rowButterfly = random.nextInt(2);
				colButterfly = random.nextInt(6)+1;		
			}
			entities.add(new EnemyButterfly(0,0,32,32,playerShip.handler, rowButterfly,colButterfly));
			position[rowButterfly][colButterfly] = true;
			
			

		}

	}


}


