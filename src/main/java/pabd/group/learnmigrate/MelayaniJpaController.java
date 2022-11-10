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
public class MelayaniJpaController implements Serializable {

    public MelayaniJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pabd.group_learnmigrate_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Melayani melayani) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee idEmp = melayani.getIdEmp();
            if (idEmp != null) {
                idEmp = em.getReference(idEmp.getClass(), idEmp.getIdEmp());
                melayani.setIdEmp(idEmp);
            }
            Peminjaman idPeminjaman = melayani.getIdPeminjaman();
            if (idPeminjaman != null) {
                idPeminjaman = em.getReference(idPeminjaman.getClass(), idPeminjaman.getIdPeminjaman());
                melayani.setIdPeminjaman(idPeminjaman);
            }
            em.persist(melayani);
            if (idEmp != null) {
                idEmp.getMelayaniCollection().add(melayani);
                idEmp = em.merge(idEmp);
            }
            if (idPeminjaman != null) {
                idPeminjaman.getMelayaniCollection().add(melayani);
                idPeminjaman = em.merge(idPeminjaman);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMelayani(melayani.getIdMelayanii()) != null) {
                throw new PreexistingEntityException("Melayani " + melayani + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Melayani melayani) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Melayani persistentMelayani = em.find(Melayani.class, melayani.getIdMelayanii());
            Employee idEmpOld = persistentMelayani.getIdEmp();
            Employee idEmpNew = melayani.getIdEmp();
            Peminjaman idPeminjamanOld = persistentMelayani.getIdPeminjaman();
            Peminjaman idPeminjamanNew = melayani.getIdPeminjaman();
            if (idEmpNew != null) {
                idEmpNew = em.getReference(idEmpNew.getClass(), idEmpNew.getIdEmp());
                melayani.setIdEmp(idEmpNew);
            }
            if (idPeminjamanNew != null) {
                idPeminjamanNew = em.getReference(idPeminjamanNew.getClass(), idPeminjamanNew.getIdPeminjaman());
                melayani.setIdPeminjaman(idPeminjamanNew);
            }
            melayani = em.merge(melayani);
            if (idEmpOld != null && !idEmpOld.equals(idEmpNew)) {
                idEmpOld.getMelayaniCollection().remove(melayani);
                idEmpOld = em.merge(idEmpOld);
            }
            if (idEmpNew != null && !idEmpNew.equals(idEmpOld)) {
                idEmpNew.getMelayaniCollection().add(melayani);
                idEmpNew = em.merge(idEmpNew);
            }
            if (idPeminjamanOld != null && !idPeminjamanOld.equals(idPeminjamanNew)) {
                idPeminjamanOld.getMelayaniCollection().remove(melayani);
                idPeminjamanOld = em.merge(idPeminjamanOld);
            }
            if (idPeminjamanNew != null && !idPeminjamanNew.equals(idPeminjamanOld)) {
                idPeminjamanNew.getMelayaniCollection().add(melayani);
                idPeminjamanNew = em.merge(idPeminjamanNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = melayani.getIdMelayanii();
                if (findMelayani(id) == null) {
                    throw new NonexistentEntityException("The melayani with id " + id + " no longer exists.");
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
            Melayani melayani;
            try {
                melayani = em.getReference(Melayani.class, id);
                melayani.getIdMelayanii();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The melayani with id " + id + " no longer exists.", enfe);
            }
            Employee idEmp = melayani.getIdEmp();
            if (idEmp != null) {
                idEmp.getMelayaniCollection().remove(melayani);
                idEmp = em.merge(idEmp);
            }
            Peminjaman idPeminjaman = melayani.getIdPeminjaman();
            if (idPeminjaman != null) {
                idPeminjaman.getMelayaniCollection().remove(melayani);
                idPeminjaman = em.merge(idPeminjaman);
            }
            em.remove(melayani);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Melayani> findMelayaniEntities() {
        return findMelayaniEntities(true, -1, -1);
    }

    public List<Melayani> findMelayaniEntities(int maxResults, int firstResult) {
        return findMelayaniEntities(false, maxResults, firstResult);
    }

    private List<Melayani> findMelayaniEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Melayani.class));
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

    public Melayani findMelayani(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Melayani.class, id);
        } finally {
            em.close();
        }
    }

    public int getMelayaniCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Melayani> rt = cq.from(Melayani.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
