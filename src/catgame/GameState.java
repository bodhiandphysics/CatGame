package catgame;

import gameobjects.updatable.HeatMap;
import gameobjects.updatable.drawable.collidable.Collider;
import gameobjects.updatable.drawable.collidable.pawn.Hero;
import gameobjects.updatable.drawable.tile.Map;
import ui.ImageManager;
import ui.Screen;

/**
 * GameState manages the shared state of the game. It's a server that all client classes can use to
 * request information about the world. Ideally most shared mutable state is located in GameState
 * (exception is the heatmap... which is controlled by the AI). The GameState also informs game
 * objects that they need to update themselves (the acutal update and drawing happens in the client
 * classes.) There should only be one GameState at a time (its a singleton), but its not static
 * because new levels require a new GameState. *
 */
public final class GameState {

  // Instance Variables

  private CatGame mainApp;

  private String levelName;

  private Hero theHero;

  private Map theGameMap;

  private Screen theScreen;

  private HeatMap theHeatMap;

  private Updater theUpdater;

  private Collider theCollider;

  private ImageManager theImageManager;

  private boolean isTextVersion;

  private double gameSpeed = 3;

  private int score = 0;

  private boolean isWon = false; // Did the Hero escape?

  private boolean isLost = false;

  private boolean isDone = false; // Gameover?

  private int numKeysLeft;

  private boolean hasKeys = false; // does the hero have the keys

  // Constructor

  /**
   * This simple constructor initializes the object registries so that we can add objects to them *
   */
  public GameState(CatGame mainApp, int aScore, boolean isTextVersion) {

    score = aScore;
    this.mainApp = mainApp;
    this.isTextVersion = isTextVersion;
    if (isTextVersion)
      gameSpeed = 1; // if we are in textApp; Note this breaks the AI for some reason
  }

  // Getters and Setters

  public String getLevelName() {

    return levelName;
  }

  void setLevelName(String aName) {

    levelName = aName;
  }

  void setNumKeys(int numKeys) {

    numKeysLeft = numKeys;
  }

  /**
   * Get the game Map. Probably should return a copy, but the map is static.
   *
   * @return the game map*
   */
  public Map getMap() {

    return theGameMap;
  }

  public CatGame getMainApp() {

    return mainApp;
  }

  /**
   * Sets the game map
   *
   * @param aMap a new (empty) map for the gamestate. The map is filled in the level loader.
   */
  void setMap(Map aMap) {

    theGameMap = aMap;
  }

  public Updater getUpdater() {

    return theUpdater;
  }

  void setUpdater(Updater anUpdater) {

    theUpdater = anUpdater;
  }

  public Collider getCollider() {

    return theCollider;
  }

  void setCollider(Collider aCollider) {

    theCollider = aCollider;
  }

  public ImageManager getImageManager() {

    return theImageManager;
  }

  void setImageManager(ImageManager theManager) {

    theImageManager = theManager;
  }
  /**
   * Sets the HeatMap. The heat map is not currently gotten by anything, but that might change so
   * the setter is here to prevent a null variable.
   *
   * @param aMap a new heatmap *
   */
  void setHeatMap(HeatMap aMap) {

    theHeatMap = aMap;
  }

  public HeatMap getHeatMap() {

    return theHeatMap;
  }

  /**
   * Gets the game screen
   *
   * @return The current game screen, which is currently just a textual display *
   */
  public Screen getScreen() {

    return theScreen;
  }

  /**
   * Sets the screen
   *
   * @param aScreen a new screen to use as the game screen
   */
  void setScreen(Screen aScreen) {

    theScreen = aScreen;
  }

  public int getScore() {

    return score;
  }

  public void addToScore(int num) {

    score += num;
    theScreen.updateScore();
  }

  /**
   * Gets the Hero
   *
   * @return The game's hero
   */
  public Hero getHero() {

    return theHero;
  }

  /**
   * Sets the hero.
   *
   * @param aHero The cat
   */
  void setHero(Hero aHero) {

    theHero = aHero;
  }

  /**
   * Check if the game is over
   *
   * @return if the game is over*
   */
  public boolean isDone() {

    return isDone;
  }

  public double getGameSpeed() {

    return gameSpeed;
  }

  /**
   * Check if the game is won
   *
   * @return if the game is won*
   */
  public boolean isWon() {

    return isWon;
  }

  public boolean isLost() {

    return isLost;
  }

  public void decrementKeys() {

    numKeysLeft--;
    if (numKeysLeft == 0) setHasKeys(true);
  }

  /**
   * Does the cat have a key. Right now the cat only needs one key, but that might change.
   *
   * @return if the cat has a key and the gate should be opened.
   */
  public boolean hasKeys() {
    return hasKeys;
  }

  /**
   * Sets whether the cat has a key. Called when the cat gets a key. Not a setter really, though
   * currently implemented as one.
   */
  private void setHasKeys(boolean value) {
    this.hasKeys = value;
  }

  /** Tells the gamestate that the hero is dead. Called in the hero's kill method. * */
  public void heroDied() {
    lose();
  }

  /** The hero reched the game */
  public void heroEscaped() {
    win();
  }

  /**
   * Tells the gamestate that the hero has won! Currently only for the gate, but that might
   * change...
   */
  private void win() {
    isWon = true;
    isLost = false;
    isDone = true;
  }

  private void lose() {

    isWon = false;
    isLost = true;
    isDone = true;
  }

  public void quit() {

    isDone = true;
  }
}
