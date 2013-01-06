package ru.terra.game.client.gui.jmonkey;

import java.util.HashMap;

import ru.terra.game.client.entity.Entity;
import ru.terra.game.client.entity.MapObject;
import ru.terra.game.client.entity.Player;
import ru.terra.game.client.game.GameManager;
import ru.terra.game.shared.constants.OpCodes.Client;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.terrain.geomipmap.TerrainQuad;

public class JMEGameViewImpl extends SimpleApplication implements ActionListener
{

	private Spatial sceneModel;
	private BulletAppState bulletAppState;
	private RigidBodyControl landscape;
	private CharacterControl player;
	private Vector3f walkDirection = new Vector3f();
	private boolean left = false, right = false, up = false, down = false;
	private TerrainQuad terrain;
	Material mat_terrain;
	private float currY = 500;

	private CameraNode camNode;
	private float diffY = 50;
	private Geometry controlCube;

	private HashMap<Player, Geometry> players = new HashMap<Player, Geometry>();
	private HashMap<MapObject, Geometry> mapObjects = new HashMap<MapObject, Geometry>();
	private HashMap<Long, Geometry> entities = new HashMap<>();
	private Material mat1;

	public void simpleInitApp()
	{
		/**
		 * Set up Physics
		 */
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		// bulletAppState.getPhysicsSpace().enableDebug(assetManager);

		// We re-use the flyby camera for rotation, while positioning is handled by physics
		viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
		// flyCam.setMoveSpeed(100);
		setUpKeys();
		setUpLight();

		// We load the scene from the zip file and adjust its size.
		// assetManager.registerLocator("town.zip", ZipLocator.class);
		sceneModel = assetManager.loadModel("Scenes/newScene.j3o");
		sceneModel.setLocalScale(2f);

		// We set up collision detection for the scene by creating a
		// compound collision shape and a static RigidBodyControl with mass zero.
		CollisionShape sceneShape = CollisionShapeFactory.createMeshShape((Node) sceneModel);
		landscape = new RigidBodyControl(sceneShape, 0);
		sceneModel.addControl(landscape);

		// We set up collision detection for the player by creating
		// a capsule collision shape and a CharacterControl.
		// The CharacterControl offers extra settings for
		// size, stepheight, jumping, falling, and gravity.
		// We also put the player in its starting position.
		CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
		player = new CharacterControl(capsuleShape, 0.05f);
		// player.setJumpSpeed(20);
		// player.setFallSpeed(30);
		player.setGravity(30);
		player.setPhysicsLocation(new Vector3f(0, 10, 0));

		// We attach the scene and the player to the rootNode and the physics space,
		// to make them appear in the game world.
		rootNode.attachChild(sceneModel);
		bulletAppState.getPhysicsSpace().add(landscape);
		bulletAppState.getPhysicsSpace().add(player);

		Box b = new Box(5, 5, 5);
		controlCube = new Geometry("Box", b);
		mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		controlCube.setMaterial(mat1);
		controlCube.setLocalTranslation(10, 10, 10);
		rootNode.attachChild(controlCube);
		// bulletAppState.getPhysicsSpace().add(red);
		flyCam.setEnabled(false);
		camNode = new CameraNode("Camera Node", cam);
		// This mode means that camera copies the movements of the target:
		camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);

		// Move camNode, e.g. behind and above the target:
		camNode.setLocalTranslation(new Vector3f(0, currY, 0));
		// Rotate the camNode to look at the target:
		camNode.lookAt(controlCube.getLocalTranslation(), Vector3f.UNIT_Z);
		// Attach the camNode to the target:
		rootNode.attachChild(camNode);
		controlCube.addControl(player);
	}

	private void setUpLight()
	{
		// We add light so we see the scene
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1.3f));
		rootNode.addLight(al);

		DirectionalLight dl = new DirectionalLight();
		dl.setColor(ColorRGBA.White);
		dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
		rootNode.addLight(dl);
	}

	/**
	 * We over-write some navigational key mappings here, so we can add physics-controlled walking and jumping:
	 */
	private void setUpKeys()
	{
		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addMapping("Z", new KeyTrigger(KeyInput.KEY_Z));
		inputManager.addMapping("Q", new KeyTrigger(KeyInput.KEY_Q));
		inputManager.addListener(this, "Left");
		inputManager.addListener(this, "Right");
		inputManager.addListener(this, "Up");
		inputManager.addListener(this, "Down");
		inputManager.addListener(this, "Q");
		inputManager.addListener(this, "Z");
		// inputManager.addListener(this, "Jump");
	}

	/**
	 * These are our custom actions triggered by key presses. We do not walk yet, we just keep track of the direction the user pressed.
	 */
	public void onAction(String binding, boolean value, float tpf)
	{
		if (binding.equals("Left"))
		{
			left = value;
		}
		else if (binding.equals("Right"))
		{
			right = value;
		}
		else if (binding.equals("Up"))
		{
			up = value;
		}
		else if (binding.equals("Down"))
		{
			down = value;
		}
		else if (binding.equals("Jump"))
		{
			// player.jump();
		}
		else if (binding.equals("Q"))
		{
			currY = currY + diffY;
			camNode.setLocalTranslation(new Vector3f(0, currY, 0));
		}
		else if (binding.equals("Z"))
		{
			currY = currY - diffY;
			camNode.setLocalTranslation(new Vector3f(0, currY, 0));
		}
	}

	/**
	 * This is the main event loop--walking happens here. We check in which direction the player is walking by interpreting the camera direction
	 * forward (camDir) and to the side (camLeft). The setWalkDirection() command is what lets a physics-controlled player walk. We also make sure
	 * here that the camera moves with player.
	 */
	@Override
	public void simpleUpdate(float tpf)
	{
		Vector3f camDir = cam.getUp().clone().multLocal(0.4f);
		Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
		walkDirection.set(0, 0, 0);
		int direction = 0;
		Vector3f dir = new Vector3f(0, 0, 0);
		if (left)
		{
			dir = camLeft;
			walkDirection.addLocal(dir);
			direction = Client.CMSG_MOVE_LEFT;
		}
		if (right)
		{
			dir = camLeft.negate();
			walkDirection.addLocal(dir);
			direction = Client.CMSG_MOVE_RIGHT;
		}
		if (up)
		{
			dir = camDir;
			walkDirection.addLocal(dir);
			direction = Client.CMSG_MOVE_FORWARD;
		}
		if (down)
		{
			dir = camDir.negate();
			walkDirection.addLocal(dir);
			direction = Client.CMSG_MOVE_BACK;
		}
		player.setWalkDirection(walkDirection);
		// camNode.lookAt(controlCube.getLocalTranslation(), Vector3f.UNIT_Z);
		camNode.setLocalTranslation(controlCube.getLocalTranslation().setY(currY));
		GameManager.getInstance().sendPlayerMove(direction, dir.getX(), dir.getY(), dir.getZ(), 0);
	}

	public void loadPlayer()
	{
	}

	public void addMapObject(MapObject entity)
	{
		Box b = new Box(2, 2, 2);
		Geometry g = new Geometry("Box", b);
		g.setMaterial(mat1);
		g.setLocalTranslation(entity.getX(), entity.getY(), entity.getZ());
		rootNode.attachChild(g);
		mapObjects.put(entity, g);
		entities.put(entity.getGuid(), g);
	}

	public void enemyLoggedIn(Player enemy)
	{
		Sphere s = new Sphere(10, 10, 5);
		Geometry g = new Geometry("Sphere", s);
		g.setMaterial(mat1);
		g.setLocalTranslation(enemy.getX(), enemy.getY(), enemy.getZ());
		rootNode.attachChild(g);
		players.put(enemy, g);
		entities.put(enemy.getGuid(), g);
	}

	public void updateEntityPosition(Entity entity)
	{
		Geometry g = entities.get(entity.getGuid());
		if (g != null)
		{
			g.setLocalTranslation(entity.getX(), entity.getY(), entity.getZ());
		}
	}
}
