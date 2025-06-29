package com.mysparkle1991.getoffmylawn.gui;

import com.mysparkle1991.getoffmylawn.procedures.*;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.core.BlockPos;

public class ClaimScreen extends AbstractContainerScreen<ClaimMenu> {

    private EditBox claimNameBox;
    private EditBox categoryBox;
    private Button claimButton;
    private Button deleteButton;
    private Button publicButton;
    private Button privateButton;
    private Button adminButton;
    private Button infoButton;

    private boolean isCurrentChunkClaimed = false;
    private String currentClaimName = "";
    private String currentOwner = "";
    private String currentCategory = "";
    private boolean isPublic = false;
    private boolean isOwner = false;

    public ClaimScreen(ClaimMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        // Update claim information
        updateClaimInfo();

        int startX = this.leftPos;
        int startY = this.topPos;

        // Claim name input box
        this.claimNameBox = new EditBox(this.font, startX + 10, startY + 20, 100, 16, Component.literal("Claim Name"));
        this.claimNameBox.setMaxLength(30);
        this.claimNameBox.setValue(currentClaimName);
        this.addRenderableWidget(this.claimNameBox);

        // Category input box
        this.categoryBox = new EditBox(this.font, startX + 10, startY + 40, 100, 16, Component.literal("Category"));
        this.categoryBox.setMaxLength(20);
        this.categoryBox.setValue(currentCategory);
        this.addRenderableWidget(this.categoryBox);

        // Main action button (Claim or Info)
        if (!isCurrentChunkClaimed) {
            this.claimButton = Button.builder(Component.literal("Claim Chunk"),
                            button -> executeClaimCommand())
                    .pos(startX + 120, startY + 20)
                    .size(50, 20)
                    .build();
            this.addRenderableWidget(this.claimButton);
        } else {
            this.infoButton = Button.builder(Component.literal("Info"),
                            button -> executeInfoCommand())
                    .pos(startX + 120, startY + 20)
                    .size(50, 20)
                    .build();
            this.addRenderableWidget(this.infoButton);
        }

        // Owner-only buttons
        if (isCurrentChunkClaimed && isOwner) {
            this.deleteButton = Button.builder(Component.literal("Delete"),
                            button -> executeDeleteCommand())
                    .pos(startX + 120, startY + 45)
                    .size(50, 20)
                    .build();
            this.addRenderableWidget(this.deleteButton);

            // Public/Private toggle
            if (isPublic) {
                this.privateButton = Button.builder(Component.literal("Make Private"),
                                button -> executePrivateCommand())
                        .pos(startX + 10, startY + 65)
                        .size(80, 20)
                        .build();
                this.addRenderableWidget(this.privateButton);
            } else {
                this.publicButton = Button.builder(Component.literal("Make Public"),
                                button -> executePublicCommand())
                        .pos(startX + 10, startY + 65)
                        .size(80, 20)
                        .build();
                this.addRenderableWidget(this.publicButton);
            }

            // Admin management button
            this.adminButton = Button.builder(Component.literal("Admins"),
                            button -> openAdminGUI())
                    .pos(startX + 95, startY + 65)
                    .size(50, 20)
                    .build();
            this.addRenderableWidget(this.adminButton);
        }
    }

    // Fix 6: ClaimScreen.java - Replace updateClaimInfo() method with proper Optional handling
    private void updateClaimInfo() {
        BlockPos playerPos = this.menu.getPlayer().blockPosition();
        this.isCurrentChunkClaimed = CheckIfChunkXYZIsClaimedProcedure.execute(
                this.menu.getLevel(), playerPos.getX(), playerPos.getY(), playerPos.getZ());

        if (isCurrentChunkClaimed) {
            double claimY = YCoordsOfBedrockClaimProcedure.execute(
                    this.menu.getLevel(), playerPos.getX(), playerPos.getY(), playerPos.getZ());

            if (claimY != -999.0) {
                BlockPos claimPos = BlockPos.containing(
                        this.menu.getLevel().getChunk(playerPos).getPos().x,
                        claimY,
                        this.menu.getLevel().getChunk(playerPos).getPos().z);

                var blockEntity = this.menu.getLevel().getBlockEntity(claimPos);
                if (blockEntity != null) {
                    var data = blockEntity.getPersistentData();
                    this.currentClaimName = data.contains("claimname") ? data.getString("claimname").orElse("") : "";
                    this.currentOwner = data.contains("ownerdisplay") ? data.getString("ownerdisplay").orElse("") : "";
                    this.currentCategory = data.contains("category") ? data.getString("category").orElse("") : "";
                    this.isPublic = data.contains("Cpublic") && data.getBoolean("Cpublic").orElse(false);
                    String ownerUUID = data.contains("owneruuid") ? data.getString("owneruuid").orElse("") : "";
                    this.isOwner = ownerUUID.equals(this.menu.getPlayer().getStringUUID());
                }
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // Draw background
        guiGraphics.fill(this.leftPos, this.topPos, this.leftPos + this.imageWidth, this.topPos + this.imageHeight, 0xc0101010);
        guiGraphics.fill(this.leftPos + 2, this.topPos + 2, this.leftPos + this.imageWidth - 2, this.topPos + this.imageHeight - 2, 0xc0404040);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Title
        guiGraphics.drawString(this.font, "Claim Management", 8, 6, 0xFFFFFF, false);

        // Current chunk status
        if (isCurrentChunkClaimed) {
            guiGraphics.drawString(this.font, "§aChunk Claimed", 8, 90, 0x55FF55, false);
            guiGraphics.drawString(this.font, "Owner: " + currentOwner, 8, 100, 0xFFFFFF, false);
            if (isOwner) {
                guiGraphics.drawString(this.font, "§aYou own this claim", 8, 110, 0x55FF55, false);
            }
        } else {
            guiGraphics.drawString(this.font, "§cChunk Unclaimed", 8, 90, 0xFF5555, false);
        }

        // Player claim count
        int currentClaims = this.menu.getPlayerClaimCount();
        int maxClaims = this.menu.getMaxClaimCount();
        guiGraphics.drawString(this.font, "Claims: " + currentClaims + "/" + maxClaims, 8, 120, 0xFFFFFF, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    // Command execution methods
    private void executeClaimCommand() {
        BlockPos pos = this.menu.getPlayer().blockPosition();
        ClaimChunkProcedure.execute(this.menu.getLevel(), pos.getX(), pos.getY(), pos.getZ(), this.menu.getPlayer());
        this.onClose();
    }

    private void executeInfoCommand() {
        BlockPos pos = this.menu.getPlayer().blockPosition();
        ClaimInfoProcedure.execute(this.menu.getLevel(), pos.getX(), pos.getY(), pos.getZ(), this.menu.getPlayer());
    }

    private void executeDeleteCommand() {
        BlockPos pos = this.menu.getPlayer().blockPosition();
        ClaimDeleteProcedure.execute(this.menu.getLevel(), pos.getX(), pos.getY(), pos.getZ(), this.menu.getPlayer());
        this.onClose();
    }

    private void executePublicCommand() {
        BlockPos pos = this.menu.getPlayer().blockPosition();
        SetPublicProcedure.execute(this.menu.getLevel(), pos.getX(), pos.getY(), pos.getZ(), this.menu.getPlayer());
        this.init(); // Refresh buttons
    }

    private void executePrivateCommand() {
        BlockPos pos = this.menu.getPlayer().blockPosition();
        SetPrivateProcedure.execute(this.menu.getLevel(), pos.getX(), pos.getY(), pos.getZ(), this.menu.getPlayer());
        this.init(); // Refresh buttons
    }

    private void openAdminGUI() {
        // TODO: Open admin management sub-GUI
        if (this.minecraft != null && this.minecraft.player != null) {
            this.minecraft.player.displayClientMessage(Component.literal("§eAdmin GUI coming soon!"), false);
        }
    }
}