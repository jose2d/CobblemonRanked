package jse.planetacorp.cobblemonranked.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class CasualScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public CasualScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ScreenHandlerType.GENERIC_9X5, syncId);  // 9x5 = 45 slots
        this.inventory = new SimpleInventory(45);

        // Adicionando 45 slots ao inventário
        int i;
        for (i = 0; i < 5; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(this.inventory, j + i * 9, 8 + j * 18, 18 + i * 18) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return false;  // Impede a inserção de itens nos slots da interface
                    }

                    @Override
                    public boolean canTakeItems(PlayerEntity playerEntity) {
                        return false;  // Impede a remoção de itens dos slots da interface
                    }
                });
            }
        }

        // Adicionando o botão que vai aceitar a queue casual
        int middleSlot = 31;  // Slot do botão
        this.inventory.setStack(middleSlot, createButton("Aceitar"));

        // Slots do inventário do jogador
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 198));
        }
    }

    private ItemStack createButton(String name) {
        ItemStack stack = new ItemStack(Items.GREEN_WOOL);  // Colocal custommodeldata futuramente
        stack.setCustomName(Text.literal(name));
        return stack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        return ItemStack.EMPTY;  // Impede a movimentação rápida de itens (Shift + Click)
    }

    @Override
    public void onSlotClick(int slotId, int button, SlotActionType actionType, PlayerEntity player) {
        if (slotId == 31) {  // Fazer a lógica de iniciar a queue event quando clicar no botão
            // Adiciona o jogador à fila casual
            CasualQueueManager.addPlayerToQueue(player);

            // Fecha a tela quandoi entrar na fila
            if (player instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) player).closeHandledScreen();
            }
        } else {
            super.onSlotClick(slotId, button, actionType, player);
        }
    }
}
