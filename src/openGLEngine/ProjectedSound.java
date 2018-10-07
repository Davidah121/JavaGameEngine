package openGLEngine;

public class ProjectedSound extends parentGameObject {


	private double volume = 1.0;
	private double strength = 128.0;
	
	private Sound soundObject;
	
	public ProjectedSound(double x, double y, double z, double depth, Sound s)
	{
		position = new Vec3f(x,y,z);
		this.depth = depth;
		
		soundObject = s;
	}
	
	public void setSoundObject(Sound s)
	{
		this.soundObject = s;
	}
	
	@Override
	public void update() {

		Vec3f camPos = Game.currentCamera.getPosition();
		Vec3f toSound = GameMath.vectorSubtract(position, camPos);
		
		double balance = GameMath.dot(GameMath.normalize(toSound), GameMath.normalize(Game.currentCamera.getLeftVec()));
		double volume = GameMath.clamp( this.strength / toSound.getLength(), 0.0, 1.0);
		
		soundObject.setVolume(volume);
		soundObject.setBalance(balance);
		
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
