package kaptainwutax.tungsten.path;

import kaptainwutax.tungsten.render.Color;
import kaptainwutax.tungsten.frame.Frame;
import net.minecraft.world.WorldView;

public class Node {

	public Node parent;
	public Frame frame;
	public PathInput input;
	public int pathCost;
	public double heuristic;
	public Color color;

	public Node(Node parent, Frame frame, Color color, int pathCost) {
		this.parent = parent;
		this.frame = frame;
		this.color = color;
		this.pathCost = pathCost;
	}

	public Node(Node parent, WorldView world, PathInput input, Color color, int pathCost) {
		this.parent = parent;
		this.frame = Frame.of(parent.frame, input).tick(world);
		this.input = input;
		this.color = color;
		this.pathCost = pathCost;
	}

	public Node[] getChildren(WorldView world) {
		Node n = this.parent;
		boolean mismatch = false;
		int i;

		for(i = 0; i < 4 && n != null; i++) {
			if(n.frame.blockX != this.frame.blockX || n.frame.blockY != this.frame.blockY || n.frame.blockZ != this.frame.blockZ) {
				mismatch = true;
				break;
			}

			n = n.parent;
		}

		if(!mismatch && i == 5) {
			return new Node[] {};
		}

		if(this.frame.onGround || this.frame.touchingWater) {
			return new Node[] {
				new Node(this, world, new PathInput(true, false, false, false, true,
					false, true, this.frame.pitch, this.frame.yaw), new Color(0, 255, 0), this.pathCost + 1),
				new Node(this, world, new PathInput(true, false, false, false, false,
					false, true, this.frame.pitch, this.frame.yaw), new Color(255, 0, 0), this.pathCost + 1),
				new Node(this, world, new PathInput(true, false, false, false, false,
					false, true, this.frame.pitch, this.frame.yaw + 30.0F), new Color(255, 255, 0), this.pathCost + 1),
				new Node(this, world, new PathInput(true, false, false, false, false,
					false, true, this.frame.pitch, this.frame.yaw - 30.0F), new Color(255, 0, 255), this.pathCost + 1),
			};
		} else {
			return new Node[] {
				new Node(this, world, new PathInput(true, false, false, false, false,
					false, true, this.frame.pitch, this.frame.yaw), new Color(0, 255, 255), this.pathCost + 1),
			};
		}
	}

}
