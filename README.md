Cat_Game
Team project (group 14) arcade game

Cat Game is the classic game of running away from dogs, getting treasure, and getting out alive! The hero is a simple feline, trapped in a room with murderous dogs. He has to get out! But to get out, he needs a key, and there's so much treasure lying around... If he gets the key, and gets to the gate, he wins! If he gets hit by a dog... he doesn't win! Controls are with wasd... press enter after every keypress. Currently the game is textual... but that will change...


How to Load the Game:

The main application class is ~/class/CatGame.class.  The gui version will load maps automatically and requires no command line argument. The text game requires one command line argument: the path of a map to load. The maps are located in ~/maps/.  To load you can make class your pwd and enter java CatGame ../maps/GameTest.map. This program does not have a module-info.java file.  In order to run it, you'll need to change the arguments to the VM.  Go to run configuration and add the following lines
to the command line arguments in the vm arguments box: --module-path $PATH_TO_FX --add-modules javafax.controls,javafx.fxml.  $PATH_TO_FX should be the path of the javafx jdk on your machine.  Please note that while the text version does not have a gui, it still requires javafx to compile, since it uses classes that have a dependency on javafx.  Javafx is, however, not loaded on run.


How to Play the Game:

Movement is with wasd, as in most PC games. The game starts out paused.  You can pause/unpause using space. You are a cat.  Enemy dogs chase your cat...  You need to collect a key and escape, and you escape via the gate. The gate starts out closed. Once you have a key, the gate will open.  If you can evade the cats, you might get some treasure.  This will raise your score.  If at any time you want to quit, press q. Any other keypress is ignored. This is useful if you want to watch the cat get horribly mauled by the AI controlled dogs. 
The text version, functions, but movement is wonky (because instead updating every 100th of a second, it updates with every key press.) It's best to test it on maps/GateTest.map, maps/TestingLevel.map, and maps/AITest3.maps, since the cat easily gets stuck on walls and corners. 

How to Design a Level:

All good computer games include a modding component and CatGame is no different!  Levels can be designed in any text editor.  A description of the level format is provided in the README in the maps directory.  Please be aware, the current level loader is extremely fragile, so you need to follow the file format precisely to avoid a real life dog coming out of your computer and mauling you!
