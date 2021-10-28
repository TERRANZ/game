package ru.terra.game.shared.constants;

public interface OpCodes {
    interface Client {
        int CMSG_LOGIN = 1;
        int CMSG_SAY = 2;
        int CMSG_WISP = 3;
        int CMSG_MOVE_FORWARD = 4;
        int CMSG_MOVE_BACK = 5;
        int CMSG_MOVE_LEFT = 6;
        int CMSG_MOVE_RIGHT = 7;
        int CMSG_MOVE_TELEPORT = 9;
        int CMSG_MOVE_STOP = 10;
        int CMSG_LOGOUT = 11;
        int CMSG_PLAYER_INFO_REQUEST = 12;
    }

    interface Server {
        int SMSG_OK = 501;
        int SMSG_SAY = 502;
        int SMSG_PLAYER_LOGGED_IN = 503;
        int SMSG_PLAYER_LOGGED_OUT = 504;
        int SMSG_MOVE_HEARTBEAT = 505;
        int SMSG_MOVE_TELEPORT = 506;
        int SMSG_MOVE_ROOT = 507;
        int SMSG_MOVE_UNROOT = 508;
        int SMSG_MAPOBJECT_ADD = 509;
        int SMSG_ENTITY_DEL = 510;
        int SMSG_PLAYER_IN_GAME = 511;
        int SMSG_PLAYER_INFO = 512;
        int SMSG_MESSAGE = 513;
    }
}
