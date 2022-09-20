package kaptainwutax.tungsten.path;

import kaptainwutax.tungsten.TungstenMod;
import kaptainwutax.tungsten.agent.Agent;
import kaptainwutax.tungsten.render.Cuboid;
import kaptainwutax.tungsten.render.Line;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;

import java.util.*;

public class PathFinder {

	public static boolean active = false;

	public static void find(WorldView world, Vec3d target) {
		if(active)return;
		active = true;

		new Thread(() -> {
			try {
				search(world, target);
			} catch(Exception e) {
				e.printStackTrace();
			}

			active = false;
		}).start();
	}

	private static void search(WorldView world, Vec3d target) {
		TungstenMod.RENDERERS.clear();

		ClientPlayerEntity player = Objects.requireNonNull(MinecraftClient.getInstance().player);

		Node start = new Node(null, Agent.of(player), null, 0);

		Queue<Node> open = new PriorityQueue<>(Comparator.comparingDouble(o -> o.pathCost + o.heuristic));
		Set<Vec3d> closed = new HashSet<>();

		open.add(start);

		while(!open.isEmpty()) {
			Node next = open.poll();
			closed.add(next.agent.getPos());
			if(closed.size() > 100000)break;

			if(next.agent.getPos().squaredDistanceTo(target) <= 1.0D) {
				TungstenMod.RENDERERS.clear();
				Node n = next;
				List<Node> path = new ArrayList<>();

				while(n.parent != null) {
					path.add(n);
					TungstenMod.RENDERERS.add(new Line(n.agent.getPos(), n.parent.agent.getPos(), n.color));
					TungstenMod.RENDERERS.add(new Cuboid(n.agent.getPos().subtract(0.05D, 0.05D, 0.05D), new Vec3d(0.1D, 0.1D, 0.1D), n.color));
					n = n.parent;
				}

				path.add(n);
				Collections.reverse(path);
				TungstenMod.EXECUTOR.setPath(path);
				break;
			}

			for(Node child : next.getChildren(world)) {
				if(closed.contains(child.agent.getPos()))continue;
				child.heuristic = child.pathCost / child.agent.getPos().distanceTo(start.agent.getPos()) * child.agent.getPos().distanceTo(target);
				//child.heuristic = 20.0D * child.frame.getPos().distanceTo(target);
				open.add(child);

				if(TungstenMod.RENDERERS.size() > 5000) {
					TungstenMod.RENDERERS.clear();
				}

				TungstenMod.RENDERERS.add(new Line(child.agent.getPos(), child.parent.agent.getPos(), child.color));
			}
		}
	}

}
