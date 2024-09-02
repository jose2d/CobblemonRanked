package jse.planetacorp.cobblemonranked;

import jse.planetacorp.cobblemonranked.commands.QueueCommand;
import net.fabricmc.api.ModInitializer;

public class CobblemonRanked implements ModInitializer {

    @Override
    public void onInitialize() {
        // Iniciando a ranked
        System.out.println("CobblemonRanked está pronto");

        QueueCommand.register();
    }
}
