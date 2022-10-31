/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.terra.game.server.storage.jpa.controller;

import ru.terra.game.server.storage.jpa.controller.exceptions.IllegalOrphanException;
import ru.terra.game.server.storage.jpa.controller.exceptions.NonexistentEntityException;
import ru.terra.game.server.storage.jpa.entity.MapObjectDB;
import ru.terra.game.server.storage.jpa.entity.Maps;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author terranz
 */
public class MapsJpaController {

    public MapsJpaController() {
        emf = Persistence.createEntityManagerFactory("terragamePU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maps maps) {
        if (maps.getMapObjects() == null) {
            maps.setMapObjects(new ArrayList<MapObjectDB>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MapObjectDB> attachedMapObjects = new ArrayList<MapObjectDB>();
            for (MapObjectDB mapObjectsMapObjectToAttach : maps.getMapObjects()) {
                mapObjectsMapObjectToAttach = em.getReference(mapObjectsMapObjectToAttach.getClass(), mapObjectsMapObjectToAttach.getId());
                attachedMapObjects.add(mapObjectsMapObjectToAttach);
            }
            maps.setMapObjects(attachedMapObjects);
            em.persist(maps);
            for (MapObjectDB mapObjectsMapObject : maps.getMapObjects()) {
                Maps oldMapOfMapObjectsMapObject = mapObjectsMapObject.getMap();
                mapObjectsMapObject.setMap(maps);
                mapObjectsMapObject = em.merge(mapObjectsMapObject);
                if (oldMapOfMapObjectsMapObject != null) {
                    oldMapOfMapObjectsMapObject.getMapObjects().remove(mapObjectsMapObject);
                    oldMapOfMapObjectsMapObject = em.merge(oldMapOfMapObjectsMapObject);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maps maps) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maps persistentMaps = em.find(Maps.class, maps.getId());
            List<MapObjectDB> mapObjectsOld = persistentMaps.getMapObjects();
            List<MapObjectDB> mapObjectsNew = maps.getMapObjects();
            List<String> illegalOrphanMessages = null;
            for (MapObjectDB mapObjectsOldMapObject : mapObjectsOld) {
                if (!mapObjectsNew.contains(mapObjectsOldMapObject)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MapObject " + mapObjectsOldMapObject + " since its map field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<MapObjectDB> attachedMapObjectsNew = new ArrayList<MapObjectDB>();
            for (MapObjectDB mapObjectsNewMapObjectToAttach : mapObjectsNew) {
                mapObjectsNewMapObjectToAttach = em.getReference(mapObjectsNewMapObjectToAttach.getClass(), mapObjectsNewMapObjectToAttach.getId());
                attachedMapObjectsNew.add(mapObjectsNewMapObjectToAttach);
            }
            mapObjectsNew = attachedMapObjectsNew;
            maps.setMapObjects(mapObjectsNew);
            maps = em.merge(maps);
            for (MapObjectDB mapObjectsNewMapObject : mapObjectsNew) {
                if (!mapObjectsOld.contains(mapObjectsNewMapObject)) {
                    Maps oldMapOfMapObjectsNewMapObject = mapObjectsNewMapObject.getMap();
                    mapObjectsNewMapObject.setMap(maps);
                    mapObjectsNewMapObject = em.merge(mapObjectsNewMapObject);
                    if (oldMapOfMapObjectsNewMapObject != null && !oldMapOfMapObjectsNewMapObject.equals(maps)) {
                        oldMapOfMapObjectsNewMapObject.getMapObjects().remove(mapObjectsNewMapObject);
                        oldMapOfMapObjectsNewMapObject = em.merge(oldMapOfMapObjectsNewMapObject);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = maps.getId();
                if (findMaps(id) == null) {
                    throw new NonexistentEntityException("The maps with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maps maps;
            try {
                maps = em.getReference(Maps.class, id);
                maps.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maps with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<MapObjectDB> mapObjectsOrphanCheck = maps.getMapObjects();
            for (MapObjectDB mapObjectsOrphanCheckMapObject : mapObjectsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maps (" + maps + ") cannot be destroyed since the MapObject " + mapObjectsOrphanCheckMapObject
                        + " in its mapObjects field has a non-nullable map field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(maps);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Maps> findMapsEntities() {
        return findMapsEntities(true, -1, -1);
    }

    public List<Maps> findMapsEntities(int maxResults, int firstResult) {
        return findMapsEntities(false, maxResults, firstResult);
    }

    private List<Maps> findMapsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maps.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Maps findMaps(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maps.class, id);
        } finally {
            em.close();
        }
    }

    public int getMapsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maps> rt = cq.from(Maps.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Maps> findMapsByMapId(int id) {
        EntityManager em = getEntityManager();
        try {
            Query maps = em.createNamedQuery("Maps.findByID").setParameter("id", id);
            return maps.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }

    }

}
