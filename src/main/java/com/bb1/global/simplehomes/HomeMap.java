package com.bb1.global.simplehomes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
 * A universal storage for {@link HomePosition}s
 * </blockquote><br>
 *
 */
public class HomeMap extends ConcurrentHashMap<UUID, HomePosition> {

	private static final long serialVersionUID = 5185266414179900681L;
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	private static final JsonParser PARSER = new JsonParser();
	
	public void saveTo(@NotNull File file) throws IOException {
		if (!file.exists()) { file.createNewFile(); }
		file.createNewFile();
		BufferedWriter b = new BufferedWriter(new PrintWriter(file));
		b.write(GSON.toJson(toJson()));
		b.flush();
		b.close();
	}
	
	public @NotNull JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();
		for (Entry<UUID, HomePosition> entry : entrySet()) {
			jsonObject.add(entry.getKey().toString(), entry.getValue().toJson());
		}
		return jsonObject;
	}
	
	public void loadFrom(@NotNull File file) throws IOException {
		if (!file.exists()) { return; }
		ArrayList<String> r = new ArrayList<String>();
		Scanner s = new Scanner(file);
		while (s.hasNext()) {
		   	r.add(s.nextLine());
		}
		s.close();
		fromJson(PARSER.parse(String.join("", r)));
	}
	
	public void fromJson(@NotNull JsonElement jsonElement) {
		if (!jsonElement.isJsonObject()) return;
		for (Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
			put(UUID.fromString(entry.getKey()), HomePosition.fromJson(entry.getValue()));
		}
	}
	
}
