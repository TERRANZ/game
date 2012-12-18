package ru.terra.game.client.gui;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

import ru.terra.game.client.entity.Entity;
import ru.terra.game.client.game.GameView;

public class LWJGLGameViewImpl extends GameView
{
	private static final int width = 800;
	private static final int height = 600;
	private float rtri;
	private float rquad;

	public LWJGLGameViewImpl()
	{
	}

	@Override
	public void init()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		glViewport(0, 0, width, height); // Reset The Current Viewport

		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(45.0f, width / height, 0.1f, 100.0f);

		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
		glLoadIdentity(); // Reset The Modelview Matrix
		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		glClearDepth(1.0f); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To D

		while (!Display.isCloseRequested())
		{
			drawGL();

			Display.update();
		}

		Display.destroy();
	}

	@Override
	public void loadPlayer()
	{
	}

	@Override
	public void loadObject(Entity entity)
	{
	}

	private void drawGL()
	{

	}
}
