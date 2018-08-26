package de.superklug.islandclash.utils.scoreboard;

import de.superklug.islandclash.IslandClash;
import de.superklug.islandclash.utils.entities.User;
import de.superklug.islandclash.utils.enums.GameState;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerScoreboard implements IScoreboardUtil {
    
    private final IslandClash module;
    
    private int animationTick = 0;
    private int task;

    public PlayerScoreboard(final IslandClash module) {
        this.module = module;
    }
    
    private final String[] displayNames = new String[]{
        "§3•§b● IslandClash",
        "§3•§b● IslandClas",
        "§3•§b● IslandCla",
        "§3•§b● IslandCl",
        "§3•§b● IslandC",
        "§3•§b● Island",
        "§3•§b● Islan",
        "§3•§b● Isla",
        "§3•§b● Isl",
        "§3•§b● Is",
        "§3•§b● I",
        "§3•§b● ",
        "§3•§b● I",
        "§3•§b● Is",
        "§3•§b● Isl",
        "§3•§b● Isla",
        "§3•§b● Islan",
        "§3•§b● Island",
        "§3•§b● IslandC",
        "§3•§b● IslandCl",
        "§3•§b● IslandCla",
        "§3•§b● IslandClas",
        "§3•§b● IslandClash"
    };

    @Override
    public void set(final Player player) {
        
        player.setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard());
        
        Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("aaa", "bbb");
        
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(displayNames[0]);
        
        createGroupTeams(scoreboard);
        
        objective.getScore("§a§8§m---------------").setScore(15);
        objective.getScore("§f ").setScore(14);
        
        if(module.getGameState().equals(GameState.LOBBY)) {
            
            //<editor-fold defaultstate="collapsed" desc="Lobby-State Teams">
            {
                Team team = scoreboard.registerNewTeam("x13");
                
                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§0");
                
                objective.getScore("§8•§7● Karte").setScore(13);
            }
            
            {
                Team team = scoreboard.registerNewTeam("x12");
                
                team.setPrefix(" §8➥ ");
                team.setSuffix("§a" + module.getMapName());
                team.addEntry("§1");
                
                objective.getScore("§1").setScore(12);
            }
            
            {
                Team team = scoreboard.registerNewTeam("x11");
                
                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§2");
                
                objective.getScore("§f  ").setScore(11);
            }
            
            //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
            
            {
                Team team = scoreboard.registerNewTeam("x10");
                
                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§3");
                
                objective.getScore("§8•§7● Ausgewähltes Kit").setScore(10);
            }
            
            {
                Team team = scoreboard.registerNewTeam("x9");
                
                team.setPrefix(" §8➥ ");
                team.setSuffix("§b" + (module.getGameManager().getActivePlayerKit().get(player) == null ? "-" : module.getGameManager().getActivePlayerKit().get(player).getName()));
                team.addEntry("§4");
                
                objective.getScore("§4").setScore(9);
            }
            
            {
                Team team = scoreboard.registerNewTeam("x8");
                
                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§5");
                
                objective.getScore("§f   ").setScore(8);
            }
            
            //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
            
            {
                Team team = scoreboard.registerNewTeam("x7");
                
                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§6");
                
                objective.getScore("§8•§7● Gegner").setScore(7);
            }
            
            {
                Team team = scoreboard.registerNewTeam("x6");
                
                team.setPrefix(" §8➥ ");
                team.setSuffix("§b" + ((Bukkit.getServer().getOnlinePlayers().size() - module.getGameManager().getSpectators().size()) - 1));
                team.addEntry("§7");
                
                objective.getScore("§7").setScore(6);
            }
            
            {
                Team team = scoreboard.registerNewTeam("x5");
                
                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§8");
                
                objective.getScore("§f    ").setScore(5);
            }
            
            //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
            
            {
                Team team = scoreboard.registerNewTeam("x4");
                
                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§9");
                
                objective.getScore("§8•§7● Coins").setScore(4);
            }
            
            {
                Team team = scoreboard.registerNewTeam("x3");
                
                team.setPrefix(" §8➥ ");
                team.setSuffix("§b" + "Lädt...");
                team.addEntry("§a");
                
                objective.getScore("§a").setScore(3);
            }
            
            {
                Team team = scoreboard.registerNewTeam("x2");
                
                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§b");
                
                objective.getScore("§f     ").setScore(2);
            }
            //</editor-fold>
            
        } else {
            
            //<editor-fold defaultstate="collapsed" desc="Other-State Teams">
            {
                Team team = scoreboard.registerNewTeam("x13");

                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§0");

                objective.getScore("§8•§7● Karte").setScore(13);
            }

            {
                Team team = scoreboard.registerNewTeam("x12");

                team.setPrefix(" §8➥ ");
                team.setSuffix("§a" + module.getMapName());
                team.addEntry("§1");

                objective.getScore("§1").setScore(12);
            }

            {
                Team team = scoreboard.registerNewTeam("x11");

                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§2");

                objective.getScore("§f  ").setScore(11);
            }

            //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
            {
                Team team = scoreboard.registerNewTeam("x10");

                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§3");

                objective.getScore("§8•§7● Ausgewähltes Kit").setScore(10);
            }

            {
                Team team = scoreboard.registerNewTeam("x9");

                team.setPrefix(" §8➥ ");
                team.setSuffix("§b" + (module.getGameManager().getActivePlayerKit().get(player) == null ? "-" : module.getGameManager().getActivePlayerKit().get(player).getName()));
                team.addEntry("§4");

                objective.getScore("§4").setScore(9);
            }

            {
                Team team = scoreboard.registerNewTeam("x8");

                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§5");

                objective.getScore("§f   ").setScore(8);
            }

            //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
            {
                Team team = scoreboard.registerNewTeam("x7");

                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§6");

                objective.getScore("§8•§7● Verbleibende Gegner").setScore(7);
            }

            {
                Team team = scoreboard.registerNewTeam("x6");

                team.setPrefix(" §8➥ ");
                team.setSuffix("§b" + ((Bukkit.getServer().getOnlinePlayers().size() - module.getGameManager().getSpectators().size()) - 1));
                team.addEntry("§7");

                objective.getScore("§7").setScore(6);
            }

            {
                Team team = scoreboard.registerNewTeam("x5");

                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§8");

                objective.getScore("§f    ").setScore(5);
            }

            //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
            {
                Team team = scoreboard.registerNewTeam("x4");

                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§9");

                objective.getScore("§8•§7● Verbleibende Zeit").setScore(4);
            }

            {
                Team team = scoreboard.registerNewTeam("x3");

                team.setPrefix(" §8➥ ");
                team.setSuffix("§b" + (module.getRemainingRoundTime() / 60) + (module.getRemainingRoundTime() == 1 ? " §7Minute" : " §7Minuten"));
                team.addEntry("§a");

                objective.getScore("§a").setScore(3);
            }

            {
                Team team = scoreboard.registerNewTeam("x2");

                team.setPrefix("");
                team.setSuffix("");
                team.addEntry("§b");

                objective.getScore("§f      ").setScore(2);
            }
            //</editor-fold>
            
        }
        
        objective.getScore("§f§8§m---------------").setScore(1);
        objective.getScore("   §f§lYourLimit.eu ").setScore(0);
        
        player.setScoreboard(scoreboard);
    }

    @Override
    public void update(final Player player) {
        User user = (User) player.getMetadata("userData").get(0).value();
        
        if (player.getScoreboard() == null | Objects.requireNonNull(player.getScoreboard()).getObjective(DisplaySlot.SIDEBAR) == null) return;
        
        player.getScoreboard().getTeam("x12").setSuffix("§a" + module.getMapName());
        player.getScoreboard().getTeam("x9").setSuffix("§b" + (module.getGameManager().getActivePlayerKit().get(player) == null ? "-" : module.getGameManager().getActivePlayerKit().get(player).getName()));
        
        if(module.getGameState().equals(GameState.LOBBY)) {
            
            player.getScoreboard().getTeam("x6").setSuffix("§b" + ((Bukkit.getServer().getOnlinePlayers().size() - module.getGameManager().getSpectators().size()) - 1));
            
            player.getScoreboard().getTeam("x3").setSuffix("§b" + user.getCoins());
            
        } else {
            
            player.getScoreboard().getTeam("x6").setSuffix("§b" + ((Bukkit.getServer().getOnlinePlayers().size() - module.getGameManager().getSpectators().size()) - 1));

            player.getScoreboard().getTeam("x3").setSuffix("§b" + (module.getRemainingRoundTime() / 60) + (module.getRemainingRoundTime() == 1 ? " §7Minute" : " §7Minuten"));
            
        }
        
    }

    @Override
    public void animate() {

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(module, () -> {

            this.animationTick = 0;

            this.task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(module, () -> {

                if (this.animationTick == this.displayNames.length) {
                    Bukkit.getScheduler().cancelTask(this.task);
                }

                Bukkit.getOnlinePlayers().forEach((players) -> {
                    if (players.getScoreboard() != null) {
                        try {
                            players.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(this.displayNames[this.animationTick]);
                        } catch (Exception exception) {
                            
                        }
                    }
                });

                this.animationTick++;

            }, 2, 2);

        }, 100, 100);

    }
    
    //<editor-fold defaultstate="collapsed" desc="createGroupTeams">
    private void createGroupTeams(final Scoreboard scoreboard) {
        
        {
            Team team = scoreboard.registerNewTeam("10-Owner");
            
            team.setPrefix("§4§lOwner §8● §4");
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("09-Administrator");
            
            team.setPrefix("§4Admin §8● §4");
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("08-Developer");
            
            team.setPrefix("§bDev §8● §b");
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("07-SrModerator");
            
            team.setPrefix("§cSrMod §8● §c");
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("06-Moderator");
            
            team.setPrefix("§cMod §8● §c");
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("05-Supporter");
            
            team.setPrefix("§9Sup §8● §9");
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("04-Builder");
            
            team.setPrefix("§2Builder §8● §2");
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("03-VIP");
            
            team.setPrefix("§5VIP §8● §5");
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("02-Premium");
            
            team.setPrefix("§6Premium §8● §6");
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("01-Spieler");
            
            team.setPrefix("§a");
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("00-Spectator");
            
            team.setPrefix("§4✘ §8● §8");
            team.setSuffix("");
            team.setCanSeeFriendlyInvisibles(true);
        }
        
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\\
        
        {
            Team team = scoreboard.registerNewTeam("08-Rot");

            team.setPrefix(module.getGameManager().getAvailibleTeams().get(team.getName().split("-")[1]).getPrefix());
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("07-Blau");

            team.setPrefix(module.getGameManager().getAvailibleTeams().get(team.getName().split("-")[1]).getPrefix());
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("06-Grün");

            team.setPrefix(module.getGameManager().getAvailibleTeams().get(team.getName().split("-")[1]).getPrefix());
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("05-Gelb");

            team.setPrefix(module.getGameManager().getAvailibleTeams().get(team.getName().split("-")[1]).getPrefix());
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("04-Orange");

            team.setPrefix(module.getGameManager().getAvailibleTeams().get(team.getName().split("-")[1]).getPrefix());
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("03-Lila");

            team.setPrefix(module.getGameManager().getAvailibleTeams().get(team.getName().split("-")[1]).getPrefix());
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("02-Pink");

            team.setPrefix(module.getGameManager().getAvailibleTeams().get(team.getName().split("-")[1]).getPrefix());
            team.setSuffix("");
        }
        
        {
            Team team = scoreboard.registerNewTeam("01-Türkis");

            team.setPrefix(module.getGameManager().getAvailibleTeams().get(team.getName().split("-")[1]).getPrefix());
            team.setSuffix("");
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="updatePlayerGroup">
    public void updatePlayerGroup(final Player player, final boolean spectator) {
        Scoreboard scoreboard = player.getScoreboard();
        
        if(module.getGameState().equals(GameState.LOBBY)) {
            if (spectator) {

                scoreboard.getTeam("00-Spectator").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());

            } else if (player.hasPermission("IslandClash.Owner")) {
                scoreboard.getTeam("10-Owner").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
            } else if (player.hasPermission("IslandClash.Administrator")) {
                scoreboard.getTeam("09-Administrator").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
            } else if (player.hasPermission("IslandClash.Developer")) {
                scoreboard.getTeam("08-Developer").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
            } else if (player.hasPermission("IslandClash.SrModerator")) {
                scoreboard.getTeam("07-SrModerator").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
            } else if (player.hasPermission("IslandClash.Moderator")) {
                scoreboard.getTeam("06-Moderator").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
            } else if (player.hasPermission("IslandClash.Supporter")) {
                scoreboard.getTeam("05-Supporter").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
            } else if (player.hasPermission("IslandClash.Builder")) {
                scoreboard.getTeam("04-Builder").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
            } else if (player.hasPermission("IslandClash.VIP")) {
                scoreboard.getTeam("03-VIP").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
            } else if (player.hasPermission("IslandClash.Premium")) {
                scoreboard.getTeam("02-Premium").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
            } else {
                scoreboard.getTeam("01-Spieler").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
            }

            Bukkit.getServer().getOnlinePlayers().forEach((players) -> {
                if (module.getGameManager().getSpectators().contains(players)) {

                    scoreboard.getTeam("00-Spectator").addPlayer(players);
                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());

                } else if (players.hasPermission("IslandClash.Owner")) {
                    scoreboard.getTeam("10-Owner").addPlayer(players);
                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                } else if (players.hasPermission("IslandClash.Administrator")) {
                    scoreboard.getTeam("09-Administrator").addPlayer(players);
                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                } else if (players.hasPermission("IslandClash.Developer")) {
                    scoreboard.getTeam("08-Developer").addPlayer(players);
                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                } else if (players.hasPermission("IslandClash.SrModerator")) {
                    scoreboard.getTeam("07-SrModerator").addPlayer(players);
                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                } else if (players.hasPermission("IslandClash.Moderator")) {
                    scoreboard.getTeam("06-Moderator").addPlayer(players);
                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                } else if (players.hasPermission("IslandClash.Supporter")) {
                    scoreboard.getTeam("05-Supporter").addPlayer(players);
                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                } else if (players.hasPermission("IslandClash.Builder")) {
                    scoreboard.getTeam("04-Builder").addPlayer(players);
                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                } else if (players.hasPermission("IslandClash.VIP")) {
                    scoreboard.getTeam("03-VIP").addPlayer(players);
                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                } else if (players.hasPermission("IslandClash.Premium")) {
                    scoreboard.getTeam("02-Premium").addPlayer(players);
                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                } else {
                    scoreboard.getTeam("01-Spieler").addPlayer(players);
                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                }
            });
            
        } else {
            
            if(module.getGameManager().getSpectators().contains(player)) {
                
                scoreboard.getTeam("00-Spectator").addPlayer(player);
                player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
                
            } else {
                
                scoreboard.getTeams().forEach((teams) -> {

                    if (module.getGameManager().getAvailibleTeams().get("Rot").getMembers().contains(player)) {
                        if (teams.getName().endsWith("Rot")) {
                            scoreboard.getTeam(teams.getName()).addPlayer(player);
                            player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
                        }
                    }

                    if (module.getGameManager().getAvailibleTeams().get("Blau").getMembers().contains(player)) {
                        if (teams.getName().endsWith("Blau")) {
                            scoreboard.getTeam(teams.getName()).addPlayer(player);
                            player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
                        }
                    }

                    if (module.getGameManager().getAvailibleTeams().get("Grün").getMembers().contains(player)) {
                        if (teams.getName().endsWith("Grün")) {
                            scoreboard.getTeam(teams.getName()).addPlayer(player);
                            player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
                        }
                    }

                    if (module.getGameManager().getAvailibleTeams().get("Gelb").getMembers().contains(player)) {
                        if (teams.getName().endsWith("Gelb")) {
                            scoreboard.getTeam(teams.getName()).addPlayer(player);
                            player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
                        }
                    }

                    if (module.getGameManager().getAvailibleTeams().get("Orange").getMembers().contains(player)) {
                        if (teams.getName().endsWith("Orange")) {
                            scoreboard.getTeam(teams.getName()).addPlayer(player);
                            player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
                        }
                    }

                    if (module.getGameManager().getAvailibleTeams().get("Lila").getMembers().contains(player)) {
                        if (teams.getName().endsWith("Lila")) {
                            scoreboard.getTeam(teams.getName()).addPlayer(player);
                            player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
                        }
                    }

                    if (module.getGameManager().getAvailibleTeams().get("Pink").getMembers().contains(player)) {
                        if (teams.getName().endsWith("Pink")) {
                            scoreboard.getTeam(teams.getName()).addPlayer(player);
                            player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
                        }
                    }

                    if (module.getGameManager().getAvailibleTeams().get("Türkis").getMembers().contains(player)) {
                        if (teams.getName().endsWith("Türkis")) {
                            scoreboard.getTeam(teams.getName()).addPlayer(player);
                            player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());
                        }
                    }
                    
                    Bukkit.getServer().getOnlinePlayers().forEach((players) -> {
                        if(module.getGameManager().getSpectators().contains(players)) {
                            
                            scoreboard.getTeam("00-Spectator").addPlayer(players);
                            players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                            
                        } else {
                            
                            if (module.getGameManager().getAvailibleTeams().get("Rot").getMembers().contains(players)) {
                                if (teams.getName().endsWith("Rot")) {
                                    scoreboard.getTeam(teams.getName()).addPlayer(players);
                                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                                }
                            }

                            if (module.getGameManager().getAvailibleTeams().get("Blau").getMembers().contains(players)) {
                                if (teams.getName().endsWith("Blau")) {
                                    scoreboard.getTeam(teams.getName()).addPlayer(players);
                                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                                }
                            }

                            if (module.getGameManager().getAvailibleTeams().get("Grün").getMembers().contains(players)) {
                                if (teams.getName().endsWith("Grün")) {
                                    scoreboard.getTeam(teams.getName()).addPlayer(players);
                                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                                }
                            }

                            if (module.getGameManager().getAvailibleTeams().get("Gelb").getMembers().contains(players)) {
                                if (teams.getName().endsWith("Gelb")) {
                                    scoreboard.getTeam(teams.getName()).addPlayer(players);
                                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                                }
                            }

                            if (module.getGameManager().getAvailibleTeams().get("Orange").getMembers().contains(players)) {
                                if (teams.getName().endsWith("Orange")) {
                                    scoreboard.getTeam(teams.getName()).addPlayer(players);
                                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                                }
                            }

                            if (module.getGameManager().getAvailibleTeams().get("Lila").getMembers().contains(players)) {
                                if (teams.getName().endsWith("Lila")) {
                                    scoreboard.getTeam(teams.getName()).addPlayer(players);
                                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                                }
                            }

                            if (module.getGameManager().getAvailibleTeams().get("Pink").getMembers().contains(players)) {
                                if (teams.getName().endsWith("Pink")) {
                                    scoreboard.getTeam(teams.getName()).addPlayer(players);
                                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                                }
                            }

                            if (module.getGameManager().getAvailibleTeams().get("Türkis").getMembers().contains(players)) {
                                if (teams.getName().endsWith("Türkis")) {
                                    scoreboard.getTeam(teams.getName()).addPlayer(players);
                                    players.setDisplayName(scoreboard.getPlayerTeam(players).getPrefix() + players.getName());
                                }
                            }
                            
                        }
                    });

                });
                
            }
            
        }
        
    }
    //</editor-fold>

}
