/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.object.line;

import me.filoghost.holographicdisplays.api.handler.TouchHandler;
import me.filoghost.holographicdisplays.object.BaseHologram;
import org.bukkit.World;

/**
 * Useful class that implements TouchablePiece. The downside is that subclasses must extend this, and cannot extend other classes.
 * But all the current items are touchable.
 */
public abstract class TouchableLineImpl extends HologramLineImpl {

    protected TouchSlimeLineImpl touchSlime;
    private TouchHandler touchHandler;

    
    protected TouchableLineImpl(BaseHologram parent) {
        super(parent);
    }
    
    
    protected void setTouchHandler(TouchHandler touchHandler, World world, double x, double y, double z) {
        this.touchHandler = touchHandler;
        
        if (touchHandler != null && touchSlime == null && world != null) {
            // If the touch handler was null before and no entity has been spawned, spawn it now.
            touchSlime = new TouchSlimeLineImpl(getParent(), this);
            touchSlime.spawn(world, x, y + (getHeight() / 2.0 - touchSlime.getHeight() / 2.0), z);
            
        } else if (touchHandler == null && touchSlime != null) {
            // Opposite case, the touch handler was not null and an entity was spawned, but now it's useless.
            touchSlime.despawn();
            touchSlime = null;
        }
    }

    
    public TouchHandler getTouchHandler() {
        return this.touchHandler;
    }


    @Override
    public void spawnEntities(World world, double x, double y, double z) {
        if (touchHandler != null) {
            touchSlime = new TouchSlimeLineImpl(getParent(), this);
            touchSlime.spawn(world, x, y + (getHeight() / 2.0 - touchSlime.getHeight() / 2.0), z);
        }
    }


    @Override
    public void despawnEntities() {
        if (touchSlime != null) {
            touchSlime.despawn();
            touchSlime = null;
        }
    }
    
    
    @Override
    public void teleport(double x, double y, double z) {
        if (touchSlime != null) {
            touchSlime.teleport(x, y + (getHeight() / 2.0 - touchSlime.getHeight() / 2.0), z);
        }
    }


    public TouchSlimeLineImpl getTouchSlime() {
        return touchSlime;
    }
    
}