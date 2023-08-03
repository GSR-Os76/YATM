package com.gsr.gsr_yatm.block.hanging_pot;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DefaultHangingPotSupportChainModel extends Model
{
	public static final String NORTH_KEY = "north";
	public static final String WEST_KEY = "west";
	public static final String SOUTH_KEY = "south";
	public static final String EAST_KEY = "east";
	
	private final ModelPart m_root;

	
	
	public DefaultHangingPotSupportChainModel(ModelPart model)
	{
        super((l) -> RenderType.cutout());
        
        m_root = model;
	} // end constructor

	
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertConsumer, int i1, int i2, float f1, float f2, float f3, float f4)
	{
		this.m_root.render(poseStack, vertConsumer, i1, i2, f1, f2, f3, f4);
	} // end renderToBuffer()
	
	
	
	public static LayerDefinition createModel() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		DefaultHangingPotSupportChainModel.createChildren(partdefinition);
		return LayerDefinition.create(meshdefinition, 128, 64);
	}
	
	public static void createChildren(PartDefinition partDefinition) {
		partDefinition.addOrReplaceChild(NORTH_KEY, CubeListBuilder.create().texOffs(0, 0).addBox(8f, 5f, 6f, 0f, 8f, 1f), PartPose.offsetAndRotation(0f, 10f + 4f, 7f + 10f, 22.5f, 0f, 0f));
		partDefinition.addOrReplaceChild(WEST_KEY, CubeListBuilder.create().texOffs(1, 0).addBox(6f, 5f, 8f, 1f, 8f, 0f), PartPose.offsetAndRotation(17f, 14f, 0, 0f, 0f, -22.5f));
		partDefinition.addOrReplaceChild(SOUTH_KEY, CubeListBuilder.create().texOffs(2, 0).addBox(8f, 5f, 9f, 0f, 8f, 1f), PartPose.offsetAndRotation(0f, 10f + 12f, 9f + 4f, -22.5f, 0f, 0f));
		partDefinition.addOrReplaceChild(EAST_KEY, CubeListBuilder.create().texOffs(3, 0).addBox(9f, 5f, 8f, 1f, 8f, 0f), PartPose.offsetAndRotation(13f, 22f, 0, 0f, 0f, 22.5f));
	} // end createChildren()
	
} // end class