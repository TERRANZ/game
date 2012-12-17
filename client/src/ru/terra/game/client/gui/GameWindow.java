package ru.terra.game.client.gui;


public class GameWindow
{
	//
	// /**
	// * Create a new game window
	// */
	// public GameWindow()
	// {
	// try
	// {
	// // find out what the current bits per pixel of the desktop is
	// int currentBpp = Display.getDisplayMode().getBitsPerPixel();
	// // find a display mode at 800x600
	// DisplayMode mode = findDisplayMode(800, 600, currentBpp);
	//
	// // if can't find a mode, notify the user the give up
	// if (mode == null)
	// {
	// Sys.alert("Error", "800x600x" + currentBpp + " display mode unavailable");
	// return;
	// }
	//
	// // configure and create the LWJGL display
	// Display.setTitle("My network game");
	// Display.setDisplayMode(mode);
	// Display.setFullscreen(false);
	//
	// Display.create();
	//
	// // initialise the game states
	// init();
	// } catch (LWJGLException e)
	// {
	// e.printStackTrace();
	// Sys.alert("Error", "Failed: " + e.getMessage());
	// }
	// }
	//
	// /**
	// * Initialise the window and the resources used for the game
	// */
	// public void init()
	// {
	// GL11.glEnable(GL11.GL_TEXTURE_2D);
	// GL11.glEnable(GL11.GL_CULL_FACE);
	// GL11.glEnable(GL11.GL_DEPTH_TEST);
	// GL11.glDepthFunc(GL11.GL_LEQUAL);
	// GL11.glShadeModel(GL11.GL_SMOOTH);
	//
	// // define the properties for the perspective of the scene
	// GL11.glMatrixMode(GL11.GL_PROJECTION);
	// GL11.glLoadIdentity();
	// GLU.gluPerspective(45.0f, ((float) 800) / ((float) 600), 0.1f, 100.0f);
	// GL11.glMatrixMode(GL11.GL_MODELVIEW);
	// GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	//
	// try
	// {
	//
	// GameState state = (GameState) new InGameState();
	// currentState = state;
	// state.init(this);
	//
	// } catch (IOException e)
	// {
	// Sys.alert("Error", "Unable to initialise state: " + e.getMessage());
	// System.exit(0);
	// }
	// }
	//
	// /**
	// * Get the current time in milliseconds based on the LWJGL high res system clock.
	// *
	// * @return The time in milliseconds based on the LWJGL high res clock
	// */
	// private long getTime()
	// {
	// return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	// }
	//
	// /**
	// * The main game loop which is cycled rendering and updating the registered game states
	// */
	// public void gameLoop()
	// {
	// boolean gameRunning = true;
	// long lastLoop = getTime();
	//
	// currentState.enter(this);
	//
	// // while the game is running we loop round updating and rendering
	// // the current game state
	// while (gameRunning)
	// {
	// // calculate how long it was since we last came round this loop
	// // and hold on to it so we can let the updating/rendering routine
	// // know how much time to update by
	// int delta = (int) (getTime() - lastLoop);
	// lastLoop = getTime();
	//
	// // clear the screen and the buffer used to maintain the appearance
	// // of depth in the 3D world (the depth buffer)
	// GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	//
	// // cause the game state that we're currently running to update
	// // based on the amount of time passed
	// int remainder = delta % 10;
	// int step = delta / 10;
	// for (int i = 0; i < step; i++)
	// {
	// // currentState.update(this, 10);
	// }
	// if (remainder != 0)
	// {
	// // currentState.update(this, remainder);
	// }
	//
	// // cause the game state that we're currently running to be
	// // render
	//
	// currentState.render(this, delta);
	//
	// // finally tell the display to cause an update. We've now
	// // rendered out scene we just want to get it on the screen
	// // As a side effect LWJGL re-checks the keyboard, mouse and
	// // controllers for us at this point
	// Display.update();
	//
	// // if the user has requested that the window be closed, either
	// // pressing CTRL-F4 on windows, or clicking the close button
	// // on the window - then we want to stop the game
	// if (Display.isCloseRequested())
	// {
	// gameRunning = false;
	// System.exit(0);
	// }
	// }
	// }
	//
	// /**
	// * Determine an available display that matches the specified paramaters.
	// *
	// * @param width The desired width of the screen
	// * @param height The desired height of the screen
	// * @param bpp The desired colour depth (bits per pixel) of the screen
	// * @return The display mode matching the requirements or null if none could be found
	// * @throws LWJGLException Indicates a failure interacting with the LWJGL library.
	// */
	// private DisplayMode findDisplayMode(int width, int height, int bpp) throws LWJGLException
	// {
	// DisplayMode[] modes = Display.getAvailableDisplayModes();
	// DisplayMode mode = null;
	//
	// for (int i = 0; i < modes.length; i++)
	// {
	// if ((modes[i].getBitsPerPixel() == bpp) || (mode == null))
	// {
	// if ((modes[i].getWidth() == width) && (modes[i].getHeight() == height))
	// {
	// mode = modes[i];
	// }
	// }
	// }
	//
	// return mode;
	// }
	//
	// public void startGame()
	// {
	// gameLoop();
	// }

}
