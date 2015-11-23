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
import ru.terra.game.server.storage.jpa.entity.MapObjectDB;
import ru.terra.game.server.storage.jpa.entity.Maps;

/**
 * 
 * @author terranz
 */
public class MapObjectJpaController
{

	public MapObjectJpaController()
	{
		emf = Persistence.createEntityManagerFactory("terragamePU");
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(MapObjectDB mapObject)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Maps map = mapObject.getMap();
			if (map != null)
			{
				map = em.getReference(map.getClass(), map.getId());
				mapObject.setMap(map);
			}
			em.persist(mapObject);
			if (map != null)
			{
				map.getMapObjects().add(mapObject);
				map = em.merge(map);
			}
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void edit(MapObjectDB mapObject) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			MapObjectDB persistentMapObject = em.find(MapObjectDB.class, mapObject.getId());
			Maps mapOld = persistentMapObject.getMap();
			Maps mapNew = mapObject.getMap();
			if (mapNew != null)
			{
				mapNew = em.getReference(mapNew.getClass(), mapNew.getId());
				mapObject.setMap(mapNew);
			}
			mapObject = em.merge(mapObject);
			if (mapOld != null && !mapOld.equals(mapNew))
			{
				mapOld.getMapObjects().remove(mapObject);
				mapOld = em.merge(mapOld);
			}
			if (mapNew != null && !mapNew.equals(mapOld))
			{
				mapNew.getMapObjects().add(mapObject);
				mapNew = em.merge(mapNew);
			}
			em.getTransaction().commit();
		} catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				int id = mapObject.getId();
				if (findMapObject(id) == null)
				{
					throw new NonexistentEntityException("The mapObject with id " + id + " no longer exists.");
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
			MapObjectDB mapObject;
			try
			{
				mapObject = em.getReference(MapObjectDB.class, id);
				mapObject.getId();
			} catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The mapObject with id " + id + " no longer exists.", enfe);
			}
			Maps map = mapObject.getMap();
			if (map != null)
			{
				map.getMapObjects().remove(mapObject);
				map = em.merge(map);
			}
			em.remove(mapObject);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public List<MapObjectDB> findMapObjectEntities()
	{
		return findMapObjectEntities(true, -1, -1);
	}

	public List<MapObjectDB> findMapObjectEntities(int maxResults, int firstResult)
	{
		return findMapObjectEntities(false, maxResults, firstResult);
	}

	private List<MapObjectDB> findMapObjectEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(MapObjectDB.class));
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

	public MapObjectDB findMapObject(int id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(MapObjectDB.class, id);
		} finally
		{
			em.close();
		}
	}

	public int getMapObjectCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<MapObjectDB> rt = cq.from(MapObjectDB.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally
		{
			em.close();
		}
	}

}
