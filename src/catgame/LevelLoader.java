package catgame;

import gameobjects.updatable.*;
import gameobjects.updatable.controller.*;
import gameobjects.updatable.drawable.*;
import gameobjects.updatable.drawable.collidable.*;
import gameobjects.updatable.drawable.collidable.collectables.*;
import gameobjects.updatable.drawable.collidable.pawn.*;
import gameobjects.updatable.drawable.tile.*;
import geometry.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import ui.ImageManager;
import ui.Screen;
import ui.TextScreen;

/**
 * Level loader imports levels data from a file. Output a mostly full GameState. The format is .map
 * file this is documented in the README in maps.
 */
public class LevelLoader {

  private CatGame mainApp;
  private boolean isTextVersion;

  /**
   * Loads a level
   *
   * @param filename The name a map file to load.
   * @param isTextVersion whether or not is text version of game
   * @return A mostly complete gamestate (a screen still needs to be set)
   */
  LevelLoader(CatGame mainApp, boolean isTextVersion) {

    this.mainApp = mainApp;
    this.isTextVersion = isTextVersion;
  }

  public GameState load(String filename, int startingScore) {

    try {
      GameState theGameState = new GameState(mainApp, startingScore, isTextVersion);
      FileReader levelReader = new FileReader(filename);
      Scanner levelScanner = new Scanner(levelReader);

      prepareGameState(theGameState, levelScanner);
      levelScanner.nextLine(); // skip two lines
      levelScanner.nextLine();
      buildMap(theGameState, levelScanner);
      levelReader.close(); // closes file!
      return theGameState;

    } catch (
        FileNotFoundException
            exception) { // This should be more precise, will be improved on 3rd iternation

      System.err.println(filename + " is not a valid map filename");
      System.exit(2);
      return null;
    } catch (IOException exception) {

      System.err.println("There was an error loading the level");
      exception.printStackTrace();
      return null;
    }
  }

  private void prepareGameState(GameState theGameState, Scanner levelScanner) throws IOException {

    String nameLineString = levelScanner.nextLine(); // get level name
    String nameString = nameLineString.strip().substring(6);

    String lengthLineString = levelScanner.nextLine(); // get map length
    String lengthString = lengthLineString.strip().substring(8);
    int length = Integer.valueOf(lengthString);

    String heightLineString = levelScanner.nextLine(); // get map height
    String heightString = heightLineString.strip().substring(8);
    int height = Integer.valueOf(heightString);

    String keysLineString =
        levelScanner.nextLine(); // get the number of keys.  Currently should always be 1
    String keysString = keysLineString.strip().substring(6);
    int numKeys = Integer.valueOf(keysString);

    theGameState.setLevelName(nameString);
    theGameState.setNumKeys(numKeys);
    Updater theUpdater;
    if (!isTextVersion) theUpdater = new Updater(theGameState);
    else theUpdater = new TextUpdater(theGameState);
    theGameState.setUpdater(theUpdater);
    Screen theScreen;
    if (!isTextVersion)
      theScreen =
          new Screen(
              theGameState, length, height); // Text version requires different versions of classes
    else theScreen = new TextScreen(theGameState, length, height);
    theGameState.setScreen(theScreen);
    Collider theCollider = new Collider(theGameState);
    theGameState.setCollider(theCollider);
    if (!isTextVersion) {
      ImageManager theImageManager = new ImageManager();
      theImageManager.loadImages();
      theGameState.setImageManager(theImageManager);
    }

    Map theMap = new Map(length, height); // create a map and a heatmap
    theGameState.setMap(theMap);
    HeatMap theHeatMap = new HeatMap(theGameState);
    theGameState.setHeatMap(theHeatMap);
  }

  /*The default heat level for tiles is as follows :
  Walls, and starting squares of various objects 1
  Floor 1 + a level set in the map
  */

  private void buildMap(GameState theGameState, Scanner levelScanner) throws IOException {

    Map theMap = theGameState.getMap();

    for (int j = 0; j < theMap.getHeight(); j++) {

      String levelLineStr = levelScanner.nextLine().strip();

      for (int i = 0; i < theMap.getLength(); i++) {

        char tileSymbol = levelLineStr.charAt(i);

        if (tileSymbol == '*') { // wall

          Tile theTile = new Walls(theGameState, new Point(i, j), 3);
          theMap.setTile(i, j, theTile);

        } else if (tileSymbol == 'X') { // Gate

          Tile theTile = new Gates(theGameState, new Point(i, j), 1);
          theMap.setTile(i, j, theTile);

        } else if (Character.isDigit(tileSymbol)) { // empty floor, the digit is the default heat

          double defaultHeat = 1 + Character.digit(tileSymbol, 10);
          Tile theTile = new Floor(theGameState, new Point(i, j), defaultHeat);
          theMap.setTile(i, j, theTile);

        } else if (tileSymbol == 'K') {

          Tile theTile = new Floor(theGameState, new Point(i, j), 1); // Key
          theMap.setTile(i, j, theTile);
          Key aKey = new Key(theGameState, new Point(i, j));

        } else if (tileSymbol == 'T') {

          Tile theTile = new Floor(theGameState, new Point(i, j), 1); // Treasure
          theMap.setTile(i, j, theTile);
          Treasure theTreasure = new Treasure(theGameState, new Point(i, j));

        } else if (tileSymbol == 'C') { // The Hero (a cat)

          Tile theTile = new Floor(theGameState, new Point(i, j), 1);
          theMap.setTile(i, j, theTile);
          Hero theHero = new Hero(theGameState, new Point(i, j));
          theGameState.setHero(theHero);
          HeroController theController = new HeroController(theGameState, theHero);
          theHero.setController(theController);

        } else if (tileSymbol == 'D') { // a vicious dog

          Tile theTile = new Floor(theGameState, new Point(i, j), 1);
          theMap.setTile(i, j, theTile);
          Dogs thePawn = new Dogs(theGameState, new Point(i, j));
          SimpleAiController theController = new SimpleAiController(theGameState, thePawn);
          thePawn.setController(theController);
        } else {

          System.err.println("The level data had an unknown symbol" + " " + tileSymbol);
          System.exit(1);
        }
      }
    }
    theGameState.getHeatMap().setHeat();
  }
}
