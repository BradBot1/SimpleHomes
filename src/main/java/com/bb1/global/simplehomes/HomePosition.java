package com.bb1.global.simplehomes;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
/**
 * @author BradBot_1
 *
 * <br><blockquote>
 * A universal way to store a location for a home
 * </blockquote><br>
 *
 */
public class HomePosition {
	
	private final @NotNull String worldId;
	
	private final double x, y, z;
	
	private final float pitch, yaw;
	
	public HomePosition(@NotNull String worldId, double x, double y, double z, float pitch, float yaw) {
		this.worldId = worldId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	public final @NotNull String getWorldId() { return this.worldId; }
	
	public final double getX() { return this.x; }
	
	public final double getY() { return this.y; }
	
	public final double getZ() { return this.z; }
	
	public final float getPitch() { return this.pitch; }
	
	public final float getYaw() { return this.yaw; }
	
	public final @NotNull JsonElement toJson() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("worldId", this.worldId);
		jsonObject.addProperty("x", this.x);
		jsonObject.addProperty("y", this.y);
		jsonObject.addProperty("z", this.z);
		jsonObject.addProperty("pitch", this.pitch);
		jsonObject.addProperty("yaw", this.yaw);
		return jsonObject;
	}
	
	public static final @Nullable HomePosition fromJson(@NotNull JsonElement jsonElement) {
		try {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			return new HomePosition(jsonObject.get("worldId").getAsString(), jsonObject.get("x").getAsDouble(), jsonObject.get("y").getAsDouble(), jsonObject.get("z").getAsDouble(), jsonObject.get("pitch").getAsFloat(), jsonObject.get("yaw").getAsFloat());
		} catch (Throwable e) {
			return null;
		}
	}
	
	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof HomePosition)) return false;
		HomePosition pos = (HomePosition) obj;
		return pos.getWorldId().equals(getWorldId())
				&& pos.getX()==getX()
				&& pos.getY()==getY()
				&& pos.getZ()==getZ()
				&& pos.getPitch()==getPitch()
				&& pos.getYaw()==getYaw();
	}
	
	@Override
	public final int hashCode() {
		return Objects.hash(this.worldId, this.x, this.y, this.z, this.pitch, this.yaw);
	}
	
}
