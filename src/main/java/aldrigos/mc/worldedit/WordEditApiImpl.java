package aldrigos.mc.worldedit;

import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class WordEditApiImpl implements WorldEdit {
    public final Map<Long, Cuboid> selection = new HashMap<>();
    private final Server server;

    public WordEditApiImpl(Server s){
        server = s;
    }

    public Cuboid getSelection(long playerId) {
        return selection.get(playerId);
    }

    public void setSelection(long playerId, Cuboid c) {
        selection.put(playerId, c);
    }

    public boolean isWand(Item i){
        return i != null && i.hasCustomBlockData() && i.getCustomBlockData().getBoolean("wand");
    }

    public void set(final int level, final Cuboid c, final int blockId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            double x1 = Math.min(c.P1.x, c.P2.x), x2 = Math.max(c.P1.x, c.P2.x),
                    y1 = Math.min(c.P1.y, c.P2.y), y2 = Math.max(c.P1.y, c.P2.y),
                    z1 = Math.min(c.P1.z, c.P2.z), z2 = Math.max(c.P1.z, c.P2.z);

            ExecutorService subEx = Executors.newCachedThreadPool();

            final Level world = server.getLevel(level);
            for(int z = (int)z1; z <= z2; z++)
                for(int x = (int)x1; x <= x2; x++)
                    for(int y = (int)y1; y <= y2; y++) {
                        final int fx = x, fy = y, fz = z;
                        subEx.execute(() -> world.setBlockIdAt(fx, fy, fz, blockId));
                    }

            subEx.shutdown();
        });
        executor.shutdown();
    }
}
