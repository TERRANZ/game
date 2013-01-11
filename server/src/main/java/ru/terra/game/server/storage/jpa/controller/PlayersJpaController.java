/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.terra.game.server.storage.jpa.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ru.terra.game.server.storage.jpa.controller.exceptions.NonexistentEntityException;
import ru.terra.game.server.storage.jpa.entity.Players;

/**
 * 
 * @author terranz
 */
public class PlayersJpaController
{

	public PlayersJpaController()
	{
		emf = Persistence.createEntityManagerFactory("terragamePU");
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(Players players)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(players);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void edit(Players players) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			players = em.merge(players);
			em.getTransaction().commit();
		} catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				int id = players.getId();
				if (findPlayers(id) == null)
				{
					throw new NonexistentEntityException("The players with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void destroy(int id) throws NonexistentEntityException
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Players players;
			try
			{
				players = em.getReference(Players.class, id);
				players.getId();
			} catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The players with id " + id + " no longer exists.", enfe);
			}
			em.remove(players);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public List<Players> findPlayersEntities()
	{
		return findPlayersEntities(true, -1, -1);
	}

	public List<Players> findPlayersEntities(int maxResults, int firstResult)
	{
		return findPlayersEntities(false, maxResults, firstResult);
	}

	private List<Players> findPlayersEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Players.class));
			Query q = em.createQuery(cq);
			if (!all)
			{
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally
		{
			em.close();
		}
	}

	public Players findPlayers(int id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(Players.class, id);
		} finally
		{
			em.close();
		}
	}

	public Players findPlayerByGUID(long guid)
	{
		EntityManager em = getEntityManager();
		try
		{
			Query blocks = em.createNamedQuery("Players.findByGUID").setParameter("guid", guid);
			return (Players) blocks.getSingleResult();
		} finally
		{
			em.close();
		}
	}

	public int getPlayersCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Players> rt = cq.from(Players.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally
		{
			em.close();
		}
	}

}
