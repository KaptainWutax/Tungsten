package kaptainwutax.tungsten.frame;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;

public class FrameRaycastContext extends RaycastContext {

	private final ShapeType shapeType;
	private final FrameShapeContext shapeContext;

	public FrameRaycastContext(Vec3d start, Vec3d end, ShapeType shapeType, FluidHandling fluidHandling, Frame frame) {
		super(start, end, shapeType, fluidHandling, null);
		this.shapeType = shapeType;
		this.shapeContext = new FrameShapeContext(frame);
	}

	@Override
	public VoxelShape getBlockShape(BlockState state, BlockView world, BlockPos pos) {
		return this.shapeType.get(state, world, pos, this.shapeContext);
	}

}
