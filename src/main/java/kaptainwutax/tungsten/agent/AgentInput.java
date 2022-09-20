package kaptainwutax.tungsten.agent;

import net.minecraft.client.input.Input;

public class AgentInput extends Input {

	private final Agent agent;

	public AgentInput(Agent agent) {
		this.agent = agent;
	}
	
	@Override
	public void tick(boolean slowDown, float sneakSpeed) {
		this.pressingForward = this.agent.keyForward;
		this.pressingBack = this.agent.keyBack;
		this.pressingLeft = this.agent.keyLeft;
		this.pressingRight = this.agent.keyRight;
		this.movementForward = this.pressingForward == this.pressingBack ? 0.0f : (this.pressingForward ? 1.0f : -1.0f);
		this.movementSideways = this.pressingLeft == this.pressingRight ? 0.0f : (this.pressingLeft ? 1.0f : -1.0f);
		this.jumping = this.agent.keyJump;
		this.sneaking = this.agent.keySneak;

		if(slowDown) {
			this.movementSideways *= sneakSpeed;
			this.movementForward *= sneakSpeed;
		}
	}

}
