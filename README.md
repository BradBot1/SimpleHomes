# SimpleHomes

A small and simple mod that adds a singular home for all players

## Required Mods

> This mod is dependent on [BFAPI](https://github.com/BradBot1/BradsFabricApi)
 
## Commands

|Name|Permission|OpLevel|Description|
|----|----------|-------|-----------|
|sethome|simplehomes.use|0|Sets the players home at their current location|
|home|simplehomes.use|0|Teleports the player to their home|

## Config

The config is found under `simplehomes.json` in the config directory

|Field|Type|Description|Default|
|-----|----|-----------|-------|
|homePermission|String|The permission used to see if a player can use /home|simplehomes.use|
|homeSetMessage|Text|the text sent to a player when they run /sethome|Your home has been set|
|homeStorage|JsonObject|The place where home locations are stored|{}|
|warmup|Integer|The time a player must stand still before they are teleported (in seconds)|5|
|noHomeMessage|Text|The message sent to a player when they do not have a home to return to|You do not have a home set!|
|forceHomeToBeBed|Boolean|If all homes should be tied to the players respawn point|false|
|failedToTeleportDueToMovement|Text|The message sent to a player when they move during a teleport|Teleport to home canceled as you moved!|
|sendTextViaActionBar|Boolean|If messages should be sent to players via the actionbar or via the chat|true|
|teleportSucceeded|Text|The message sent to a player when they teleport to their home|Teleported to your home!|
|teleportWait|Text|The message sent to a player when they run /home and the warmup begins|Teleporting shortly, please do not move|
|cooldown|Integer|The time (in seconds) between /home requests from players|30|
|cooldownMessage|Text|The text sent to a player when they try to do /home while on cooldown|You are on cooldown!|

```json
{
  "homePermission": "simplehomes.use",
  "homeSetMessage": {
    "color": "gold",
    "text": "Your home has been set"
  },
  "homeStorage": {},
  "warmup": 5,
  "noHomeMessage": {
    "color": "red",
    "text": "You do not have a home set!"
  },
  "forceHomeToBeBed": false,
  "failedToTeleportDueToMovement": {
    "color": "red",
    "text": "Teleport to home canceled as you moved!"
  },
  "sendTextViaActionBar": true,
  "teleportSucceeded": {
    "color": "gold",
    "text": "Teleported to your home!"
  },
  "teleportWait": {
    "color": "gold",
    "text": "Teleporting shortly, please do not move"
  },
  "cooldown": 30,
  "cooldownMessage": {
    "color": "red",
    "text": "You are on cooldown!"
  }
}
```

## Links

* [GitHub](https://github.com/BradBot1/SimpleHomes)
* [ModRinth](https://modrinth.com/mod/simplehomes)
* [CurseForge](https://www.curseforge.com/minecraft/mc-mods/simplehomes)
