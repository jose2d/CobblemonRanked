package jse.planetacorp.cobblemonranked.commands;

import com.mojang.brigadier.CommandDispatcher;
import jse.planetacorp.cobblemonranked.util.CasualQueueManager;
import jse.planetacorp.cobblemonranked.util.QueueScreenHandler;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class QueueCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("queue")
                    .then(literal("leave")
                            .executes(context -> {
                                ServerPlayerEntity player = context.getSource().getPlayer();
                                if (player != null) {
                                    CasualQueueManager.removePlayerFromQueue(player);
                                }
                                return 1;
                            }))
                    .executes(context -> {
                        ServerPlayerEntity player = context.getSource().getPlayer();
                        if (player != null) {
                            openQueueInventory(player);
                        }
                        return 1;
                    }));
        });
    }

    private static void openQueueInventory(ServerPlayerEntity player) {
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inv, p) -> {
            return new QueueScreenHandler(syncId, inv);
        }, Text.literal("Cobblemon Queue")));
    }
}
