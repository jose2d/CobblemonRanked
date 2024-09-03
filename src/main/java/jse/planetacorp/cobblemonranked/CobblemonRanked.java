package jse.planetacorp.cobblemonranked;

import jse.planetacorp.cobblemonranked.commands.QueueCommand;
import jse.planetacorp.cobblemonranked.util.CasualQueueManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class CobblemonRanked implements ModInitializer {

    @Override
    public void onInitialize() {
        // Iniciando a ranked
        System.out.println("CobblemonRanked está pronto");

        // Registrar o comando de queue
        QueueCommand.register();

        // Registrar evento de desconexão para remover a galerinha da fila né
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            CasualQueueManager.removePlayerFromQueue(player);
        });
    }
}
