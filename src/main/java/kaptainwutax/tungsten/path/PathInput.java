package kaptainwutax.tungsten.path;

public class PathInput {

	public final boolean forward, back, right, left, jump, sneak, sprint;
	public final float pitch, yaw;

	public PathInput(boolean forward, boolean back, boolean right, boolean left, boolean jump, boolean sneak, boolean sprint, float pitch, float yaw) {
		this.forward = forward;
		this.back = back;
		this.right = right;
		this.left = left;
		this.jump = jump;
		this.sneak = sneak;
		this.sprint = sprint;
		this.pitch = pitch;
		this.yaw = yaw;
	}

}
