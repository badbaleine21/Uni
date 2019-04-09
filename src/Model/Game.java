package Model;

import View.Window;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.omg.CosNaming.IstringHelper;

public class Game implements DeletableObserver {
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private Player active_player = null;
    private Player mainChar;

    private Window window;

    public Game(Window window, Player mainChar) {
        this.window = window;
        // Creating one Player at position (1,1)
        this.mainChar = mainChar;
        objects.add(mainChar);
        players.add(mainChar);
        window.setPlayer(mainChar);
        active_player = mainChar;
        ArrayList<GameObject> objectList = window.getObjects(2);
        // Map building
        for (int i = 0; i < objectList.size(); i++) {
            objects.add(objectList.get(i));
            // use currInstance
        }
        for (int i = 0; i < objects.size(); i++) {
            System.out.println(objects.get(i));
            // use currInstance
        }
        window.setGameObjects(this.getGameObjects());
        window.setChar(this.mainChar);
        notifyView();
    }




    public void movePlayer(int x, int y) {
        int nextX = active_player.getPosX() + x;
        int nextY = active_player.getPosY() + y;

        boolean obstacle = false;
        for (GameObject object : objects) {
            if (object.isAtPosition(nextX, nextY)) {
                obstacle = object.isObstacle();
            }
            if (obstacle == true) {
                break;
            }
        }
        active_player.rotate(x, y);
        if (obstacle == false) {
            active_player.move(x, y);
        }
        notifyView();
    }

    public void tirePlayer() {
    	active_player.tire();
    	notifyView();
    }
    public void action() {
        Activable aimedObject = null;
		for(GameObject object : objects){
			if(object.isAtPosition(active_player.getFrontX(),active_player.getFrontY())){
			    if(object instanceof Activable){
			        aimedObject = (Activable) object;
			    }
			}
		}
		if(aimedObject != null){
		    aimedObject.activate();
            notifyView();
		}
        
    }

    private void notifyView() {
        window.update();
    }

    public ArrayList<GameObject> getGameObjects() {
        return this.objects;
    }

    @Override
    synchronized public void delete(Deletable ps, ArrayList<GameObject> loot) {
        objects.remove(ps);
        if (loot != null) {
            objects.addAll(loot);
        }
        notifyView();
    }


    public void playerPos() {
        System.out.println(active_player.getPosX() + ":" + active_player.getPosY());
        
    }



	public void sendPlayer(int x, int y) {
		Thread t = new Thread(new AStarThread(this, active_player, x,  y));
		t.start();
	}


}