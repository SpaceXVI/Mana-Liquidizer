package ms55.manaliquidizer.common.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import ms55.manaliquidizer.ManaLiquidizer;
import ms55.manaliquidizer.common.container.ManaLiquidizerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import vazkii.botania.api.BotaniaAPI;

public class ManaLiquidizerScreen extends ContainerScreen<ManaLiquidizerContainer> {
	protected ResourceLocation backgroundTexture = new ResourceLocation(ManaLiquidizer.MODID, "textures/gui/mana_liquidizer.png");

    public ManaLiquidizerScreen(ManaLiquidizerContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		this.getMinecraft().getTextureManager().bindTexture(this.backgroundTexture);
        int i = this.getGuiLeft();
        int j = this.getGuiTop();

        ManaLiquidizerContainer container = this.getContainer();

		if (container.getCurrentMana() > 0) {
			TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(new ResourceLocation(BotaniaAPI.MODID, "textures/block/mana_water.png"));
			minecraft.getTextureManager().bindTexture(new ResourceLocation(BotaniaAPI.MODID, "textures/block/mana_water.png"));
			blit(matrixStack, guiLeft + 45, guiTop + 6 + (75 - container.tile.getCurrentMana()), 0, 8, container.tile.getCurrentMana(), sprite);
			System.out.println(sprite);
		}

		if (container.getCurrentFluidMana() > 0) {
			TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(new ResourceLocation(BotaniaAPI.MODID, "textures/block/mana_water.png"));
			minecraft.getTextureManager().bindTexture(new ResourceLocation(BotaniaAPI.MODID, "textures/block/mana_water.png"));
			blit(matrixStack, guiLeft + 124, guiTop + 6 + (75 - container.tile.getCurrentMana()), 0, 8, container.tile.getCurrentMana(), sprite);
		}

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(backgroundTexture);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.xSize, this.ySize);
	}
}