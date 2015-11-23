package ru.terra.game.shared.constants;

public interface OpCodes
{
	public interface Client
	{
		public static final int CMSG_LOGIN = 1;
		public static final int CMSG_SAY = 2;
		public static final int CMSG_WISP = 3;
		public static final int CMSG_MOVE_FORWARD = 4;
		public static final int CMSG_MOVE_BACK = 5;
		public static final int CMSG_MOVE_LEFT = 6;
		public static final int CMSG_MOVE_RIGHT = 7;
		public static final int CMSG_MOVE_TELEPORT = 9;
		public static final int CMSG_MOVE_STOP = 10;
		public static final int CMSG_LOGOUT = 11;
		public static final int CMSG_PLAYER_INFO_REQUEST = 12;
	}

	public interface Server
	{
		public static final int SMSG_OK = 501;
		public static final int SMSG_SAY = 502;
		public static final int SMSG_PLAYER_LOGGED_IN = 503;
		public static final int SMSG_PLAYER_LOGGED_OUT = 504;
		public static final int SMSG_MOVE_HEARTBEAT = 505;
		public static final int SMSG_MOVE_TELEPORT = 506;
		public static final int SMSG_MOVE_ROOT = 507;
		public static final int SMSG_MOVE_UNROOT = 508;
		public static final int SMSG_MAPOBJECT_ADD = 509;
		public static final int SMSG_ENTITY_DEL = 510;
		public static final int SMSG_PLAYER_IN_GAME = 511;
		public static final int SMSG_PLAYER_INFO = 512;
        public static final int SMSG_MESSAGE = 513;
	}
}
