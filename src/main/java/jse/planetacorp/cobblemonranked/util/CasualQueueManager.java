package jse.planetacorp.cobblemonranked.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.*;

public class CasualQueueManager {

    private static final List<PlayerEntity> queue = new ArrayList<>();
    private static final Map<PlayerEntity, Long> joinTime = new HashMap<>(); // Armazena o tempo de entrada na fila

    // bota um jogador à fila
    public static void addPlayerToQueue(PlayerEntity player) {
        if (!queue.contains(player)) {
            queue.add(player);
            joinTime.put(player, System.currentTimeMillis()); // Armazena o tempo de entrada
            player.sendMessage(Text.literal("Você entrou na fila casual!"), false);
            startQueueTimer(player); // Inicia o timer para exibir o tempo na fila
            checkQueue();
        } else {
            player.sendMessage(Text.literal("Você já está na fila casual!"), false);
        }
    }

    // remover jogador da fila
    public static void removePlayerFromQueue(PlayerEntity player) {
        queue.remove(player);
        joinTime.remove(player);
        player.sendMessage(Text.literal("Você saiu da fila casual!"), false);
    }

    // Verifica se tem pelo menos dois jogadores na fila e inicia uma batalha
    private static void checkQueue() {
        if (queue.size() >= 2) {
            Collections.shuffle(queue);
            PlayerEntity player1 = queue.remove(0);
            PlayerEntity player2 = queue.remove(0);
            joinTime.remove(player1);
            joinTime.remove(player2);
            startBattle(player1, player2);
        }
    }

    // fazer logica da batalha entre dois jogadores
    private static void startBattle(PlayerEntity player1, PlayerEntity player2) {
        player1.sendMessage(Text.literal("Você foi pareado para uma batalha casual contra " + player2.getName().getString() + "!"), false);
        player2.sendMessage(Text.literal("Você foi pareado para uma batalha casual contra " + player1.getName().getString() + "!"), false);
        /**
         * TODO
         * Teleportar para a area da arena casual
         * Iniciar a escolha de pokemon
         * logo apos iniciar a batalha
         */
    }

    // timer para exibir o tempo na fila
    private static void startQueueTimer(PlayerEntity player) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (queue.contains(player)) {
                    long timeInQueue = System.currentTimeMillis() - joinTime.get(player);
                    int seconds = (int) (timeInQueue / 1000) % 60;
                    int minutes = (int) (timeInQueue / 60000);
                    player.sendMessage(Text.literal("Tempo na fila: " + minutes + "m " + seconds + "s"), true); // mensagem em cima da hotbar
                } else {
                    timer.cancel();
                }
            }
        }, 0, 1000); // atualiza a cada segundo, perde desempenho? talvez usar 5 segundos
    }
}
