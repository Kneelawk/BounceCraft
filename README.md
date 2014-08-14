BounceCraft2
============
My Attempt to port BounceCraft to Minecraft 1.7.10.

About BounceCraft2
------------------
BounceCraft and BounceCraft2 were and are mods that are designed to allow the
minecraft player to, to put it plainly, toss entities around.

The main difference between BounceCraft and BounceCraft2 is that BounceCraft2
has Forge Multipart compatibility, allowing the minecraft player to place more
than one of various blocks in one block's worth of space.

Downloads
---------
There are no downloads for BounceCraft2 other than the source right now.
- [Source Master Download](https://github.com/Kneelawk/BounceCraft2/archive/master.zip)

Setup
-----
In order to setup the decomp workspace, all you have to run is <code>./setupDecompWorkspace <i>ide-name</i></code> (linux, MacOSX), or <code>setupDecompWorkspace <i>ide-name</i></code> (Windows).
Please don't use gradlew directly for setting up the decomp workspace directly, because the script downloads the eclipse folder, which is needed for propper building.

In order to build the mod, all you need to run is <code>./gradlew build</code> (linux, MacOSX), or <code>gradlew.bat build</code> (Windows).
