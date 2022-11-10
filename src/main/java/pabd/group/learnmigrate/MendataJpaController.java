/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pabd.group.learnmigrate;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pabd.group.learnmigrate.exceptions.NonexistentEntityException;
import pabd.group.learnmigrate.exceptions.PreexistingEntityException;

/**
 *
 * @author mladi
 */
public class MendataJpaController implements Serializable {

    public MendataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pabd.group_learnmigrate_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mendata mendata) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee idEmp = mendata.getIdEmp();
            if (idEmp != null) {
                idEmp = em.getReference(idEmp.getClass(), idEmp.getIdEmp());
                mendata.setIdEmp(idEmp);
            }
            Buku idBuku = mendata.getIdBuku();
            if (idBuku != null) {
                idBuku = em.getReference(idBuku.getClass(), idBuku.getIdBuku());
                mendata.setIdBuku(idBuku);
            }
            em.persist(mendata);
            if (idEmp != null) {
                idEmp.getMendataCollection().add(mendata);
                idEmp = em.merge(idEmp);
            }
            if (idBuku != null) {
                idBuku.getMendataCollection().add(mendata);
                idBuku = em.merge(idBuku);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMendata(mendata.getIdData()) != null) {
                throw new PreexistingEntityException("Mendata " + mendata + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mendata mendata) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mendata persistentMendata = em.find(Mendata.class, mendata.getIdData());
            Employee idEmpOld = persistentMendata.getIdEmp();
            Employee idEmpNew = mendata.getIdEmp();
            Buku idBukuOld = persistentMendata.getIdBuku();
            Buku idBukuNew = mendata.getIdBuku();
            if (idEmpNew != null) {
                idEmpNew = em.getReference(idEmpNew.getClass(), idEmpNew.getIdEmp());
                mendata.setIdEmp(idEmpNew);
            }
            if (idBukuNew != null) {
                idBukuNew = em.getReference(idBukuNew.getClass(), idBukuNew.getIdBuku());
                mendata.setIdBuku(idBukuNew);
            }
            mendata = em.merge(mendata);
            if (idEmpOld != null && !idEmpOld.equals(idEmpNew)) {
                idEmpOld.getMendataCollection().remove(mendata);
                idEmpOld = em.merge(idEmpOld);
            }
            if (idEmpNew != null && !idEmpNew.equals(idEmpOld)) {
                idEmpNew.getMendataCollection().add(mendata);
                idEmpNew = em.merge(idEmpNew);
            }
            if (idBukuOld != null && !idBukuOld.equals(idBukuNew)) {
                idBukuOld.getMendataCollection().remove(mendata);
                idBukuOld = em.merge(idBukuOld);
            }
            if (idBukuNew != null && !idBukuNew.equals(idBukuOld)) {
                idBukuNew.getMendataCollection().add(mendata);
                idBukuNew = em.merge(idBukuNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = mendata.getIdData();
                if (findMendata(id) == null) {
                    throw new NonexistentEntityException("The mendata with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mendata mendata;
            try {
                mendata = em.getReference(Mendata.class, id);
                mendata.getIdData();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mendata with id " + id + " no longer exists.", enfe);
            }
            Employee idEmp = mendata.getIdEmp();
            if (idEmp != null) {
                idEmp.getMendataCollection().remove(mendata);
                idEmp = em.merge(idEmp);
            }
            Buku idBuku = mendata.getIdBuku();
            if (idBuku != null) {
                idBuku.getMendataCollection().remove(mendata);
                idBuku = em.merge(idBuku);
            }
            em.remove(mendata);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mendata> findMendataEntities() {
        return findMendataEntities(true, -1, -1);
    }

    public List<Mendata> findMendataEntities(int maxResults, int firstResult) {
        return findMendataEntities(false, maxResults, firstResult);
    }

    private List<Mendata> findMendataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mendata.class));
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

    public Mendata findMendata(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mendata.class, id);
        } finally {
            em.close();
        }
    }

    public int getMendataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mendata> rt = cq.from(Mendata.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
