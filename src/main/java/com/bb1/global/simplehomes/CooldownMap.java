package com.bb1.global.simplehomes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

public class CooldownMap {
	
	private final Map<UUID, Long> cooldowns = new HashMap<UUID, Long>();
	
	private final long cooldown;
	
	public CooldownMap(long cooldown) {
		this.cooldown = cooldown;
	}
	
	public boolean isOnCooldown(@NotNull UUID uuid) { return cooldowns.getOrDefault(uuid, 0l) >= System.currentTimeMillis(); }
	
	public void giveCooldown(@NotNull UUID uuid) { cooldowns.put(uuid, System.currentTimeMillis()+cooldown); }
}
