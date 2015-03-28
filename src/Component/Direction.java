package Component;
import java.util.Random;

public enum Direction {
	NORTH,SOUTH,WEST,EAST;
	
	public static Direction randomDirection() {
		Direction[] dirs = Direction.values();
		Random r = new Random();
		int rn = r.nextInt(dirs.length);
		Direction dir = dirs[rn];
		return dir;
	}
}
