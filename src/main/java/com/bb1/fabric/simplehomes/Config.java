package com.bb1.fabric.simplehomes;

import com.bb1.api.config.Storable;
import com.google.gson.JsonObject;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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
public class Config extends com.bb1.api.config.Config {

	public Config() { super("simplehomes"); }
	/** The permission used to control who can use homes */
	@Storable public String homePermission = "simplehomes.use";
	
	@Storable public Text homeSetMessage = new LiteralText("Your home has been set").formatted(Formatting.GOLD);
	
	@Storable public JsonObject homeStorage = new JsonObject();
	/** Handled in seconds */
	@Storable public int warmup = 5;
	
	@Storable public Text noHomeMessage = new LiteralText("You do not have a home set!").formatted(Formatting.RED);
	/** If enabled all homes goto the players bed, not recommended lol */
	@Storable public boolean forceHomeToBeBed = false;
	
	@Storable public Text failedToTeleportDueToMovement = new LiteralText("Teleport to home canceled as you moved!").formatted(Formatting.RED);
	
	@Storable public boolean sendTextViaActionBar = true;
	
	@Storable public Text teleportSucceeded = new LiteralText("Teleported to your home!").formatted(Formatting.GOLD);
	/** Not sent if {@link #warmup}<0 */
	@Storable public Text teleportWait = new LiteralText("Teleporting shortly, please do not move").formatted(Formatting.GOLD);
	
	@Storable public int cooldown = 30;
	
	@Storable public Text cooldownMessage = new LiteralText("You are on cooldown!").formatted(Formatting.RED);
	
}
