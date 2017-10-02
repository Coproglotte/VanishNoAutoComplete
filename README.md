# VanishNoAutoComplete
A [Spigot](https://www.spigotmc.org/) plugin to improve invisibility for [VanishNoPacket](http://dev.bukkit.org/bukkit-plugins/vanish/) vanished players

## Features
VanishNoAutoComplete is aimed for server admins who want to be *really* invisible from normal players when vanished.

With VanishNoPacket alone, vanished players can still be found by normal players. Type the beginning of a vanished player's nickname and press [TAB]: if the nickname is completed, then you know they're connected and vanished.

VanishNoAutoComplete allows only ops and vanished players to see autocompletion for other vanished players.

## Dependencies
This plugin depends on [VanishNoPacket](http://dev.bukkit.org/bukkit-plugins/vanish/) and [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)

## Building
Create a `libs/` directory in the root folder and copy ProtocolLib-API and VanishNoPacket jars inside.

Run `gradle shadowJar` to compile the jar file.

A `buildAndStartServer` (`bASS`) Gradle task allows you to build and copy the plugin to a Spigot server's directory and start the server.