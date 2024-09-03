package jse.planetacorp.cobblemonranked.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class QueueScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public QueueScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ScreenHandlerType.GENERIC_9X6, syncId);  // 9x6 = 54 slots
        this.inventory = new SimpleInventory(54);

        // Adicionando 54 slots ao inventory
        int i;
        for (i = 0; i < 6; ++i) {
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

        // Adicionando botões para o Casual, Rankedzinha e Pro league >:D
        this.inventory.setStack(20, createButton("Casual"));
        this.inventory.setStack(22, createButton("Ranked"));
        this.inventory.setStack(24, createButton("Pro League"));

        // Slots do inventário do jogador
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 140 + i * 18) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return false;  // Impede a inserção de itens no inventário do jogador
                    }

                    @Override
                    public boolean canTakeItems(PlayerEntity playerEntity) {
                        return false;  // Impede a remoção de itens do inventário do jogador
                    }
                });
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 198) {
                @Override
                public boolean canInsert(ItemStack stack) {
                    return false;  // Impede a inserção de itens no inventário do jogador
                }

                @Override
                public boolean canTakeItems(PlayerEntity playerEntity) {
                    return false;  // Impede a remoção de itens do inventário do jogador
                }
            });
        }
    }

    private ItemStack createButton(String name) {
        ItemStack stack = new ItemStack(Items.PAPER);
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
    public boolean onButtonClick(PlayerEntity player, int id) {
        // Impede qualquer interação diferente dos botões
        return true;
    }

    @Override
    public void onSlotClick(int slotId, int button, SlotActionType actionType, PlayerEntity player) {
        if (slotId == 20) {  // Slot do botao Casual
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerEntity) -> {
                return new CasualScreenHandler(syncId, playerInventory);
            }, Text.literal("Casual")));
        } else {
            super.onSlotClick(slotId, button, actionType, player);
        }
    }
}
