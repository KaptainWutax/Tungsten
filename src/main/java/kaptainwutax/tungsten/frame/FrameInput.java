package kaptainwutax.tungsten.frame;

import net.minecraft.client.input.Input;

public class FrameInput extends Input {

	private final Frame frame;

	public FrameInput(Frame frame) {
		this.frame = frame;
	}
	
	@Override
	public void tick(boolean slowDown, float sneakSpeed) {
		this.pressingForward = this.frame.keyForward;
		this.pressingBack = this.frame.keyBack;
		this.pressingLeft = this.frame.keyLeft;
		this.pressingRight = this.frame.keyRight;
		this.movementForward = this.pressingForward == this.pressingBack ? 0.0f : (this.pressingForward ? 1.0f : -1.0f);
		this.movementSideways = this.pressingLeft == this.pressingRight ? 0.0f : (this.pressingLeft ? 1.0f : -1.0f);
		this.jumping = this.frame.keyJump;
		this.sneaking = this.frame.keySneak;

		if(slowDown) {
			this.movementSideways *= sneakSpeed;
			this.movementForward *= sneakSpeed;
		}
	}

}
