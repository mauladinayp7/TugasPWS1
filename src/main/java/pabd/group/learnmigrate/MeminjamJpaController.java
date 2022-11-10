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
public class MeminjamJpaController implements Serializable {

    public MeminjamJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pabd.group_learnmigrate_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Meminjam meminjam) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Member1 idMember = meminjam.getIdMember();
            if (idMember != null) {
                idMember = em.getReference(idMember.getClass(), idMember.getIdMember());
                meminjam.setIdMember(idMember);
            }
            Buku idBuku = meminjam.getIdBuku();
            if (idBuku != null) {
                idBuku = em.getReference(idBuku.getClass(), idBuku.getIdBuku());
                meminjam.setIdBuku(idBuku);
            }
            em.persist(meminjam);
            if (idMember != null) {
                idMember.getMeminjamCollection().add(meminjam);
                idMember = em.merge(idMember);
            }
            if (idBuku != null) {
                idBuku.getMeminjamCollection().add(meminjam);
                idBuku = em.merge(idBuku);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMeminjam(meminjam.getIdMeminjam()) != null) {
                throw new PreexistingEntityException("Meminjam " + meminjam + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Meminjam meminjam) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Meminjam persistentMeminjam = em.find(Meminjam.class, meminjam.getIdMeminjam());
            Member1 idMemberOld = persistentMeminjam.getIdMember();
            Member1 idMemberNew = meminjam.getIdMember();
            Buku idBukuOld = persistentMeminjam.getIdBuku();
            Buku idBukuNew = meminjam.getIdBuku();
            if (idMemberNew != null) {
                idMemberNew = em.getReference(idMemberNew.getClass(), idMemberNew.getIdMember());
                meminjam.setIdMember(idMemberNew);
            }
            if (idBukuNew != null) {
                idBukuNew = em.getReference(idBukuNew.getClass(), idBukuNew.getIdBuku());
                meminjam.setIdBuku(idBukuNew);
            }
            meminjam = em.merge(meminjam);
            if (idMemberOld != null && !idMemberOld.equals(idMemberNew)) {
                idMemberOld.getMeminjamCollection().remove(meminjam);
                idMemberOld = em.merge(idMemberOld);
            }
            if (idMemberNew != null && !idMemberNew.equals(idMemberOld)) {
                idMemberNew.getMeminjamCollection().add(meminjam);
                idMemberNew = em.merge(idMemberNew);
            }
            if (idBukuOld != null && !idBukuOld.equals(idBukuNew)) {
                idBukuOld.getMeminjamCollection().remove(meminjam);
                idBukuOld = em.merge(idBukuOld);
            }
            if (idBukuNew != null && !idBukuNew.equals(idBukuOld)) {
                idBukuNew.getMeminjamCollection().add(meminjam);
                idBukuNew = em.merge(idBukuNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = meminjam.getIdMeminjam();
                if (findMeminjam(id) == null) {
                    throw new NonexistentEntityException("The meminjam with id " + id + " no longer exists.");
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
            Meminjam meminjam;
            try {
                meminjam = em.getReference(Meminjam.class, id);
                meminjam.getIdMeminjam();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The meminjam with id " + id + " no longer exists.", enfe);
            }
            Member1 idMember = meminjam.getIdMember();
            if (idMember != null) {
                idMember.getMeminjamCollection().remove(meminjam);
                idMember = em.merge(idMember);
            }
            Buku idBuku = meminjam.getIdBuku();
            if (idBuku != null) {
                idBuku.getMeminjamCollection().remove(meminjam);
                idBuku = em.merge(idBuku);
            }
            em.remove(meminjam);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Meminjam> findMeminjamEntities() {
        return findMeminjamEntities(true, -1, -1);
    }

    public List<Meminjam> findMeminjamEntities(int maxResults, int firstResult) {
        return findMeminjamEntities(false, maxResults, firstResult);
    }

    private List<Meminjam> findMeminjamEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Meminjam.class));
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

    public Meminjam findMeminjam(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Meminjam.class, id);
        } finally {
            em.close();
        }
    }

    public int getMeminjamCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Meminjam> rt = cq.from(Meminjam.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
