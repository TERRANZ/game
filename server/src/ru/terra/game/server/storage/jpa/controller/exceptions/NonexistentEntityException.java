package ru.terra.game.server.storage.jpa.controller.exceptions;

public class NonexistentEntityException extends Exception
{
	public NonexistentEntityException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public NonexistentEntityException(String message)
	{
		super(message);
	}
}
