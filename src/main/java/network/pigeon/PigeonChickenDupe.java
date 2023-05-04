package network.pigeon;

import org.bstats.bukkit.Metrics;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public final class PigeonChickenDupe extends JavaPlugin implements Listener {
    private File dataFile;
    private int taskId;

    @Override
    public void onEnable() {

        int pluginId = 18371;
        Metrics metrics = new Metrics(this, pluginId);

        // 注册事件监听器
        getServer().getPluginManager().registerEvents(this, this);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        int intervalSeconds = getConfig().getInt("SpawnInterval");
        long intervalTicks = intervalSeconds * 20L;

        // 加载或创建数据文件
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 启动插件时，创建计时器
        taskId = getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            // 定期执行任务
            spawnItemsForChickens();
        }, 0L, intervalTicks); // 每1秒等于20tick

        // 输出插件加载成功信息
        getLogger().info(ChatColor.GREEN + "--------------------");
        getLogger().info(ChatColor.GREEN + "PigeonChickenDupe");
        getLogger().info(ChatColor.GREEN + "插件加载成功");
        getLogger().info(ChatColor.GREEN + "--------------------");
    }

    @Override
    public Logger getSLF4JLogger() {
        return super.getSLF4JLogger();
    }

    @Override
    public void onDisable() {
        // 关闭插件时，取消计时器
        getServer().getScheduler().cancelTask(taskId);

        // 输出插件卸载成功信息
        getLogger().info(ChatColor.GREEN + "--------------------");
        getLogger().info(ChatColor.GREEN + "PigeonChickenDupe");
        getLogger().info(ChatColor.GREEN + "插件卸载成功");
        getLogger().info(ChatColor.GREEN + "--------------------");
    }

    // 玩家右键点击实体事件
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) throws IOException {
        Entity entity = event.getRightClicked();
        if (entity instanceof Chicken) {
            Chicken chicken = (Chicken) entity;
            // 如果鸡已经成年
            if (chicken.isAdult()) {
                Location location = event.getRightClicked().getLocation();
                YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                Player player = event.getPlayer();
                ItemStack item = player.getInventory().getItemInMainHand();
                Material material = item.getType();
                if (material != Material.AIR) {
                    // 将该鸡的唯一ID和手中物品存入数据文件
                    data.set(String.valueOf(chicken.getUniqueId()), item);
                    data.save(dataFile);
                    // 播放音效，设置鸡的名称为手中物品的名称
                    player.playEffect(location, Effect.CLICK2, null);
                    chicken.setCustomName(ChatColor.GREEN + "[物品] " + ChatColor.GOLD + item.getI18NDisplayName());
                    chicken.setCustomNameVisible(true);
                }
            }
        }
    }
    // 实体死亡事件
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) throws IOException {
        Entity entity = event.getEntity();
        if (entity instanceof Chicken) {
            String id = String.valueOf(entity.getUniqueId());
            YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
            // 从数据文件中删除该鸡的记录
            data.set(id, null);
            data.save(dataFile);
        }
    }
    private void spawnItemsForChickens() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        int SpawnNumber = getConfig().getInt("SpawnNumber");
        try {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./plugins/PigeonChickenDupe/data.yml"));
            for (World world : getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity instanceof Chicken) {
                        UUID uuid = entity.getUniqueId();
                        String key = uuid.toString();
                        if (config.contains(key)) {
                            ItemStack itemStack = config.getItemStack(key);
                            itemStack.setAmount(SpawnNumber);
                            entity.getWorld().dropItemNaturally(entity.getLocation(), itemStack);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, "读取文件失败", ex);
        }
    }
}
