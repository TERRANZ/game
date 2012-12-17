package ru.terra.game.shared.constants;

public interface OpCodes
{
	public interface Client
	{
		public static final int CMSG_LOGIN = 1;
		public static final int CMSG_SAY = 2;
		public static final int CMSG_WISP = 3;
	}

	public interface Server
	{
		public static final int SMSG_OK = 1;
		public static final int SMSG_SAY = 2;
	}
}
