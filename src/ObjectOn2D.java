
public class ObjectOn2D {
	int x;
	int y;
	int width;
	int height;
}

class MovingObject extends ObjectOn2D {
	int speed;
	int accel;
	
	public void calculateSpeed() {
		speed += accel;
	}
	
	public void calculateY() {
		y += speed;
	}
	
//	public boolean Collide(ObjectOn2D other) {
//		this.x
//		x + width
//	}
}