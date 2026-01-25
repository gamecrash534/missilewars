package de.butzlabben.missilewars.game;

import de.butzlabben.missilewars.Logger;
import de.butzlabben.missilewars.configuration.PluginMessages;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathMessageHandler {
    
    private final Game game;
    
    public DeathMessageHandler(Game game) {
        this.game = game;
    }
    
    public void sendDeathMessage(Player player) {
        
        if (player.getLastDamageCause() == null) return;
        
        String deathBroadcast = PluginMessages.getMessage(true, PluginMessages.MessageEnum.DIED_DEFAULT)
                .replace("%player%", player.getName())
                .replace("%player_displayname%", player.getDisplayName());
        
        EntityDamageEvent.DamageCause damageCause = player.getLastDamageCause().getCause();
        
        if ((damageCause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) || (damageCause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
            
            if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent damageByEntity) {
                Entity damager = damageByEntity.getDamager();
                
                if (damager.getType() == EntityType.TNT) {
                    deathBroadcast = PluginMessages.getMessage(true, PluginMessages.MessageEnum.DIED_EXPLOSION_TNT)
                            .replace("%player%", player.getName())
                            .replace("%player_displayname%", player.getDisplayName());

                } else if (damager.getType() == EntityType.TNT_MINECART) {
                    deathBroadcast = PluginMessages.getMessage(true, PluginMessages.MessageEnum.DIED_EXPLOSION_MINECART)
                            .replace("%player%", player.getName())
                            .replace("%player_displayname%", player.getDisplayName());
                    
                } else if (damager instanceof Fireball fireball) {
                    if (fireball.getShooter() instanceof Player killer) {
                        deathBroadcast = PluginMessages.getMessage(true, PluginMessages.MessageEnum.DIED_PROJECTILE_FIREBALL)
                                .replace("%player%", player.getName())
                                .replace("%player_displayname%", player.getDisplayName())
                                .replace("%killer%", killer.getName())
                                .replace("%killer_displayname%", killer.getDisplayName());
                    }
                }
            }
        
        
        } else if (damageCause == EntityDamageEvent.DamageCause.PROJECTILE) {
            
            if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent damageByEntity) {
                Entity damager = damageByEntity.getDamager();
                
                if (damager instanceof Arrow arrow) {
                    if (arrow.getShooter() instanceof Player killer) {
                        deathBroadcast = PluginMessages.getMessage(true, PluginMessages.MessageEnum.DIED_PROJECTILE_ARROW)
                                .replace("%player%", player.getName())
                                .replace("%player_displayname%", player.getDisplayName())
                                .replace("%killer%", killer.getName())
                                .replace("%killer_displayname%", killer.getDisplayName());
                    }
                    
                } else if (damager instanceof Fireball fireball) {
                    if (fireball.getShooter() instanceof Player killer) {
                        deathBroadcast = PluginMessages.getMessage(true, PluginMessages.MessageEnum.DIED_PROJECTILE_FIREBALL)
                                .replace("%player%", player.getName())
                                .replace("%player_displayname%", player.getDisplayName())
                                .replace("%killer%", killer.getName())
                                .replace("%killer_displayname%", killer.getDisplayName());
                    }
                }
            }
            
            
        } else if ((damageCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) || (damageCause == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
            
            if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent damageByEntity) {
                Entity damager = damageByEntity.getDamager();
                
                if (damager instanceof LivingEntity entity) {
                    if (entity instanceof Player killer) {
                        deathBroadcast = PluginMessages.getMessage(true, PluginMessages.MessageEnum.DIED_ENTITY_ATTACK)
                                .replace("%player%", player.getName())
                                .replace("%player_displayname%", player.getDisplayName())
                                .replace("%killer%", killer.getName())
                                .replace("%killer_displayname%", killer.getDisplayName());
                    }
                }
            }
            
            
        } else if (damageCause == EntityDamageEvent.DamageCause.FALL) {
            deathBroadcast = PluginMessages.getMessage(true, PluginMessages.MessageEnum.DIED_FALL)
                    .replace("%player%", player.getName())
                    .replace("%player_displayname%", player.getDisplayName());
            
            
        }

        Logger.DEBUG.log("Death by " + player.getName() + ": " + damageCause.name() + " (" + player.getLastDamageCause().getClass().getSimpleName() + ")");
        
        game.broadcast(deathBroadcast);
    
    }
    
}
