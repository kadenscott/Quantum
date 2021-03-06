package dev.kscott.quantumspawn.listeners;

import com.google.inject.Inject;
import dev.kscott.quantum.location.LocationProvider;
import dev.kscott.quantum.location.QuantumLocation;
import dev.kscott.quantum.rule.ruleset.QuantumRuleset;
import dev.kscott.quantumspawn.config.Config;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * The PlayerJoinListener.
 */
public class PlayerJoinListener implements Listener {

    /**
     * Config reference.
     */
    private final @NonNull Config config;

    /**
     * JavaPlugin reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * LocationProvider reference.
     */
    private final @NonNull LocationProvider locationProvider;

    /**
     * Constructs PlayerJoinListener.
     *
     * @param locationProvider LocationProvider reference.
     */
    @Inject
    public PlayerJoinListener(
            final @NonNull JavaPlugin plugin,
            final @NonNull Config config,
            final @NonNull LocationProvider locationProvider
    ) {
        this.plugin = plugin;
        this.config = config;
        this.locationProvider = locationProvider;
    }

    /**
     * Gives players a random spawn on join.
     *
     * @param event PlayerJoinEvent.
     */
    @EventHandler
    public void onPlayerJoin(final @NonNull PlayerJoinEvent event) {
        final @NonNull Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            return;
        }

        final @NonNull World world = this.config.isDefaultWorldEnabled() ? this.config.getDefaultWorld() : player.getWorld();

        final @Nullable QuantumRuleset ruleset = config.getRuleset(world);

        if (ruleset == null) {
            return;
        }

        final @NonNull CompletableFuture<QuantumLocation> locationCf = this.locationProvider.getLocation(ruleset);

        locationCf.thenAccept(quantumLocation -> new BukkitRunnable() {
            @Override
            public void run() {
                player.teleportAsync(QuantumLocation.toCenterHorizontalLocation(quantumLocation.getLocation()));
            }
        }.runTask(plugin));
    }

}
