package com.bb1.fabric.simplehomes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.bb1.api.events.Events;
import com.bb1.api.permissions.Permission;
import com.bb1.api.permissions.Permission.PermissionValue;
import com.bb1.api.permissions.PermissionManager;
import com.bb1.api.timings.timers.AbstractTimer;
import com.bb1.api.timings.timers.SystemTimer;
import com.bb1.api.utils.Inputs.Input;
import com.bb1.global.simplehomes.CooldownMap;
import com.bb1.global.simplehomes.HomeMap;
import com.bb1.global.simplehomes.HomePosition;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Copyright 2021 BradBot_1
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Loader implements ModInitializer {
	
	private static final Config CONFIG = new Config();
	
	public static final @NotNull Config getConfig() { return CONFIG; }
	
	private static final HomeMap HOMES = new HomeMap();
	
	private static CooldownMap COOLDOWNS;
	
	@Override
	public void onInitialize() {
		CONFIG.load();
		CONFIG.save();
		COOLDOWNS = new CooldownMap(CONFIG.cooldown*1000);
		PermissionManager.getInstance().register(Input.from(new Permission(CONFIG.homePermission, PermissionValue.DEFAULT)));
		HOMES.fromJson(CONFIG.homeStorage);
		Events.GameEvents.STOP_EVENT.register((server)->{
			CONFIG.homeStorage = HOMES.toJson();
			CONFIG.save();
		});
		Events.GameEvents.COMMAND_REGISTRATION_EVENT.register((input)->{
			input.get().register(CommandManager.literal("sethome").requires((source)->{
				return isAllowedToExecute(source) && !CONFIG.forceHomeToBeBed;
			}).executes((context)->{
				ServerPlayerEntity player = context.getSource().getPlayer();
				HOMES.put(player.getUuid(), new HomePosition(player.getServerWorld().getRegistryKey().getValue().toString(), player.getX(), player.getY(), player.getZ(), player.getPitch(), player.getYaw()));
				player.sendMessage(CONFIG.homeSetMessage, true);
				return 1;
			}));
			input.get().register(CommandManager.literal("home").requires((source)->{
				return isAllowedToExecute(source);
			}).executes((context)->{
				final boolean sendTextViaActionBar = CONFIG.sendTextViaActionBar;
				final ServerPlayerEntity player = context.getSource().getPlayer();
				if (COOLDOWNS.isOnCooldown(player.getUuid())) {
					player.sendMessage(CONFIG.cooldownMessage, sendTextViaActionBar);
					return 0;
				}
				final AbstractTimer timer = new SystemTimer(CONFIG.warmup*1000);
				final ServerWorld world;
				final double x,y,z;
				final float pitch, yaw;
				if (CONFIG.forceHomeToBeBed) {
					@Nullable BlockPos blockPos = player.getSpawnPointPosition();
					if (blockPos==null) {
						player.sendMessage(CONFIG.noHomeMessage, sendTextViaActionBar);
						return 0;
					}
					world = getWorld(player.getSpawnPointDimension().getValue());
					x = blockPos.getX();
					y = blockPos.getY();
					z = blockPos.getZ();
					pitch = 0f;
					yaw = 0f;
				} else {
					@Nullable HomePosition homePosition = HOMES.get(player.getUuid());
					if (homePosition==null) {
						player.sendMessage(CONFIG.noHomeMessage, sendTextViaActionBar);
						return 0;
					}
					world = getWorld(Identifier.tryParse(homePosition.getWorldId()));
					x = homePosition.getX();
					y = homePosition.getY();
					z = homePosition.getZ();
					pitch = homePosition.getPitch();
					yaw = homePosition.getYaw();
				}
				Vec3d pos = player.getPos();
				if (CONFIG.warmup>=0) {
					player.sendMessage(CONFIG.teleportWait, sendTextViaActionBar);
				}
				com.bb1.api.Loader.getScheduler().schedule(()->{
					if (!pos.equals(player.getPos())) {
						player.sendMessage(CONFIG.failedToTeleportDueToMovement, sendTextViaActionBar);
						return;
					}
					player.stopRiding();
					player.teleport(world, x, y, z, yaw, pitch);
					player.sendMessage(CONFIG.teleportSucceeded, sendTextViaActionBar);
					COOLDOWNS.giveCooldown(player.getUuid());
				}, timer);
				return 0;
			}));
		});
	}
	
	@SuppressWarnings("resource")
	public static @Nullable ServerWorld getWorld(@NotNull Identifier worldIdentifier) {
		for (ServerWorld world : com.bb1.api.Loader.getMinecraftServer().getWorlds()) {
			if (world.getRegistryKey().getValue().equals(worldIdentifier)) {
				return world;
			}
		}
		return null;
	}
	
	public static boolean isAllowedToExecute(@NotNull ServerCommandSource source) {
		try {
			ServerPlayerEntity player = source.getPlayer();
			PermissionManager permissionManager = PermissionManager.getInstance();
			return player!=null && (permissionManager.isUsable() ? permissionManager.hasPermission(player.getUuid(), CONFIG.homePermission) : true);
		} catch (Throwable t) {
			return false;
		}
	}
	
}
