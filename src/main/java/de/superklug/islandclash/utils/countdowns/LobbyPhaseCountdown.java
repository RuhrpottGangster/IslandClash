package de.superklug.islandclash.utils.countdowns;

import com.google.common.collect.Lists;
import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.entities.User;
import de.superklug.islandclash.utils.enums.GameState;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyPhaseCountdown extends BukkitRunnable {
    
    private final IslandClash module;
    
    private @Getter int time = 61;

    public LobbyPhaseCountdown(final IslandClash module) {
        this.module = module;
    }

    @Override
    public void run() {
        
        if(!module.getGameState().equals(GameState.LOBBY)) {
            this.cancel();
            return;
        }
        time--;
        
        Bukkit.getServer().getOnlinePlayers().forEach((players) -> {
            players.setLevel(time);
            players.setExp((float) time / (float) 60);
            
            switch(time) {
                
                case 60: case 45: case 30: case 20: case 15: case 10: case 5: case 4: case 3: case 2: case 1:
                    players.playSound(players.getLocation(), Sound.NOTE_PLING, 1F, (float) time);
                    players.sendMessage(module.format(module.getPrefix() + "Das Spiel beginnt in §b{0} §7Sekunde{1}§8!", time, (time == 1 ? "" : "n")));
                    break;
                    
                case 0:
                    
                    if(Bukkit.getOnlinePlayers().size() >= (module.getGameManager().getMaxPlayersATeam() + 1)) {
                        
                        final String mapBuilder = module.getConfigManager().getConfiguration().getString("Locations." + module.getMapName() + ".Builders");
                        final String mapBewertung = module.getConfigManager().getConfiguration().getString("Locations." + module.getMapName() + ".Bewertung");
                        final List<Player> allPlayerList = Lists.newArrayList();

                        allPlayerList.add(players);

                        players.playSound(players.getLocation(), Sound.PISTON_EXTEND, 1F, 5F);
                        players.sendMessage(module.getPrefix() + "§8§m---------------------------------------");
                        players.sendMessage(module.getPrefix() + " ");
                        players.sendMessage(module.getPrefix() + "               §a§nKarten Informationen§r ");
                        players.sendMessage(module.getPrefix() + " ");
                        players.sendMessage(module.getPrefix() + "Name §8» §a" + module.getMapName());
                        players.sendMessage(module.getPrefix() + "Erbauer §8» §a" + mapBuilder);
                        players.sendMessage(module.getPrefix() + "Bewertung §8» §6" + mapBewertung);
                        players.sendMessage(module.getPrefix() + " ");
                        players.sendMessage(module.getPrefix() + "§8§m---------------------------------------");
                        
                        module.getGameManager().setFreezePlayers(true);
                        
                        if(!module.getGameManager().getActivePlayerKit().containsKey(players)) {
                            module.getGameManager().getActivePlayerKit().put(players, module.getGameManager().getAvailibleKits().get("Starter"));
                        } else {
                            if(module.getGameManager().getActivePlayerKit().get(players).equals(module.getGameManager().getAvailibleKits().get("Random"))) {
                                User user = (User) players.getMetadata("userData").get(0).value();
                                
                                if(user.getKitsBought().isEmpty()) {
                                    module.getGameManager().getActivePlayerKit().put(players, module.getGameManager().getAvailibleKits().get("Starter")); 
                                } else {
                                    final String kitName = user.getKitsBought().get(new Random().nextInt(user.getKitsBought().size()));
                                    module.getGameManager().getActivePlayerKit().put(players, module.getGameManager().getAvailibleKits().get(kitName));
                                }
                                
                            }
                        }

                        module.runTaskLater(() -> {
                            players.closeInventory();
                            players.getInventory().clear();
                            players.setLevel(0);
                            players.setExp(0);
                            players.spigot().setCollidesWithEntities(true);

                            module.setGameState(GameState.WARMUP);
                            module.startWarmupPhaseCountdown();

                            module.getPlayerScoreboard().set(players);
                            module.getPlayerScoreboard().updatePlayerGroup(players, false);
                            
                            if(!module.getGameManager().getHasSelectedTeam().contains(players)) {
                                
                                if(module.getGameManager().getAvailibleTeams().get("Rot").getMembers().size() < module.getGameManager().getMaxPlayersATeam()) {
                                    module.getGameManager().getAvailibleTeams().get("Rot").getMembers().add(players);  
                                }
                                else if(module.getGameManager().getAvailibleTeams().get("Blau").getMembers().size() < module.getGameManager().getMaxPlayersATeam()) {
                                    module.getGameManager().getAvailibleTeams().get("Blau").getMembers().add(players);
                                }
                                else if(module.getGameManager().getAvailibleTeams().get("Grün").getMembers().size() < module.getGameManager().getMaxPlayersATeam()) {
                                    module.getGameManager().getAvailibleTeams().get("Grün").getMembers().add(players);
                                }
                                else if (module.getGameManager().getAvailibleTeams().get("Gelb").getMembers().size() < module.getGameManager().getMaxPlayersATeam()) {
                                    module.getGameManager().getAvailibleTeams().get("Gelb").getMembers().add(players);
                                }
                                else if (module.getGameManager().getAvailibleTeams().get("Orange").getMembers().size() < module.getGameManager().getMaxPlayersATeam()) {
                                    module.getGameManager().getAvailibleTeams().get("Orange").getMembers().add(players);
                                }
                                else if (module.getGameManager().getAvailibleTeams().get("Lila").getMembers().size() < module.getGameManager().getMaxPlayersATeam()) {
                                    module.getGameManager().getAvailibleTeams().get("Lila").getMembers().add(players);
                                }
                                else if (module.getGameManager().getAvailibleTeams().get("Pink").getMembers().size() < module.getGameManager().getMaxPlayersATeam()) {
                                    module.getGameManager().getAvailibleTeams().get("Pink").getMembers().add(players);
                                }
                                else if (module.getGameManager().getAvailibleTeams().get("Türkis").getMembers().size() < module.getGameManager().getMaxPlayersATeam()) {
                                    module.getGameManager().getAvailibleTeams().get("Türkis").getMembers().add(players);
                                }
                                else {
                                    players.kickPlayer(module.getPrefix() + "Alle verfügbaren Teams sind voll§8!");
                                }
                                
                            }

                            if (module.getGameManager().getMaxPlayersATeam() == 1) {

                                for (int i = 0; i < module.getGameManager().getMaxTeams(); i++) {
                                    if (module.getLocationManager().getLocation(module.getMapName(), (i + 1)) != null) {
                                        allPlayerList.get(i).playSound(allPlayerList.get(i).getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 5F);
                                        allPlayerList.get(i).teleport(module.getLocationManager().getLocation(module.getMapName(), (i + 1)));
                                    }
                                }

                            } else {

                                module.getGameManager().getAvailibleTeams().get("Rot").getMembers().forEach((members) -> {
                                    members.playSound(members.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 5F);
                                    members.teleport(module.getLocationManager().getLocation(module.getMapName(), 1));
                                });

                                module.getGameManager().getAvailibleTeams().get("Blau").getMembers().forEach((members) -> {
                                    members.playSound(members.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 5F);
                                    members.teleport(module.getLocationManager().getLocation(module.getMapName(), 2));
                                });

                                module.getGameManager().getAvailibleTeams().get("Grün").getMembers().forEach((members) -> {
                                    members.playSound(members.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 5F);
                                    members.teleport(module.getLocationManager().getLocation(module.getMapName(), 3));
                                });

                                module.getGameManager().getAvailibleTeams().get("Gelb").getMembers().forEach((members) -> {
                                    members.playSound(members.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 5F);
                                    members.teleport(module.getLocationManager().getLocation(module.getMapName(), 4));
                                });

                                module.getGameManager().getAvailibleTeams().get("Orange").getMembers().forEach((members) -> {
                                    members.playSound(members.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 5F);
                                    members.teleport(module.getLocationManager().getLocation(module.getMapName(), 5));
                                });

                                module.getGameManager().getAvailibleTeams().get("Lila").getMembers().forEach((members) -> {
                                    members.playSound(members.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 5F);
                                    members.teleport(module.getLocationManager().getLocation(module.getMapName(), 6));
                                });

                                module.getGameManager().getAvailibleTeams().get("Pink").getMembers().forEach((members) -> {
                                    members.playSound(members.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 5F);
                                    members.teleport(module.getLocationManager().getLocation(module.getMapName(), 7));
                                });

                                module.getGameManager().getAvailibleTeams().get("Türkis").getMembers().forEach((members) -> {
                                    members.playSound(members.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 5F);
                                    members.teleport(module.getLocationManager().getLocation(module.getMapName(), 8));
                                });

                            }
                        }, 5);
                        
                    } else {
                        
                        time = 60;
                        final int neededPlayers = (module.getGameManager().getMaxPlayersATeam() + 1) - Bukkit.getServer().getOnlinePlayers().size();
                        players.playSound(players.getLocation(), Sound.ANVIL_LAND, 1F, 5F);
                        players.sendMessage(module.getPrefix());
                        players.sendMessage(module.format(module.getPrefix() + "Es fehlen §b{0} Spieler bis zum Start§8!", neededPlayers));
                        players.sendMessage(module.getPrefix() + "Der §bCountdown §7startet daher neu§8!");
                        players.sendMessage(module.getPrefix());
                        
                    }
                    
                    break;
                
                default:
                    break;
                
            }
            
        });
        
    }

}
