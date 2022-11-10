/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pabd.group.learnmigrate;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pabd.group.learnmigrate.exceptions.IllegalOrphanException;
import pabd.group.learnmigrate.exceptions.NonexistentEntityException;
import pabd.group.learnmigrate.exceptions.PreexistingEntityException;

/**
 *
 * @author mladi
 */
public class PeminjamanJpaController implements Serializable {

    public PeminjamanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pabd.group_learnmigrate_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Peminjaman peminjaman) throws PreexistingEntityException, Exception {
        if (peminjaman.getMelayaniCollection() == null) {
            peminjaman.setMelayaniCollection(new ArrayList<Melayani>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buku idBuku = peminjaman.getIdBuku();
            if (idBuku != null) {
                idBuku = em.getReference(idBuku.getClass(), idBuku.getIdBuku());
                peminjaman.setIdBuku(idBuku);
            }
            Member1 idMember = peminjaman.getIdMember();
            if (idMember != null) {
                idMember = em.getReference(idMember.getClass(), idMember.getIdMember());
                peminjaman.setIdMember(idMember);
            }
            Collection<Melayani> attachedMelayaniCollection = new ArrayList<Melayani>();
            for (Melayani melayaniCollectionMelayaniToAttach : peminjaman.getMelayaniCollection()) {
                melayaniCollectionMelayaniToAttach = em.getReference(melayaniCollectionMelayaniToAttach.getClass(), melayaniCollectionMelayaniToAttach.getIdMelayanii());
                attachedMelayaniCollection.add(melayaniCollectionMelayaniToAttach);
            }
            peminjaman.setMelayaniCollection(attachedMelayaniCollection);
            em.persist(peminjaman);
            if (idBuku != null) {
                idBuku.getPeminjamanCollection().add(peminjaman);
                idBuku = em.merge(idBuku);
            }
            if (idMember != null) {
                idMember.getPeminjamanCollection().add(peminjaman);
                idMember = em.merge(idMember);
            }
            for (Melayani melayaniCollectionMelayani : peminjaman.getMelayaniCollection()) {
                Peminjaman oldIdPeminjamanOfMelayaniCollectionMelayani = melayaniCollectionMelayani.getIdPeminjaman();
                melayaniCollectionMelayani.setIdPeminjaman(peminjaman);
                melayaniCollectionMelayani = em.merge(melayaniCollectionMelayani);
                if (oldIdPeminjamanOfMelayaniCollectionMelayani != null) {
                    oldIdPeminjamanOfMelayaniCollectionMelayani.getMelayaniCollection().remove(melayaniCollectionMelayani);
                    oldIdPeminjamanOfMelayaniCollectionMelayani = em.merge(oldIdPeminjamanOfMelayaniCollectionMelayani);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPeminjaman(peminjaman.getIdPeminjaman()) != null) {
                throw new PreexistingEntityException("Peminjaman " + peminjaman + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Peminjaman peminjaman) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peminjaman persistentPeminjaman = em.find(Peminjaman.class, peminjaman.getIdPeminjaman());
            Buku idBukuOld = persistentPeminjaman.getIdBuku();
            Buku idBukuNew = peminjaman.getIdBuku();
            Member1 idMemberOld = persistentPeminjaman.getIdMember();
            Member1 idMemberNew = peminjaman.getIdMember();
            Collection<Melayani> melayaniCollectionOld = persistentPeminjaman.getMelayaniCollection();
            Collection<Melayani> melayaniCollectionNew = peminjaman.getMelayaniCollection();
            List<String> illegalOrphanMessages = null;
            for (Melayani melayaniCollectionOldMelayani : melayaniCollectionOld) {
                if (!melayaniCollectionNew.contains(melayaniCollectionOldMelayani)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Melayani " + melayaniCollectionOldMelayani + " since its idPeminjaman field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idBukuNew != null) {
                idBukuNew = em.getReference(idBukuNew.getClass(), idBukuNew.getIdBuku());
                peminjaman.setIdBuku(idBukuNew);
            }
            if (idMemberNew != null) {
                idMemberNew = em.getReference(idMemberNew.getClass(), idMemberNew.getIdMember());
                peminjaman.setIdMember(idMemberNew);
            }
            Collection<Melayani> attachedMelayaniCollectionNew = new ArrayList<Melayani>();
            for (Melayani melayaniCollectionNewMelayaniToAttach : melayaniCollectionNew) {
                melayaniCollectionNewMelayaniToAttach = em.getReference(melayaniCollectionNewMelayaniToAttach.getClass(), melayaniCollectionNewMelayaniToAttach.getIdMelayanii());
                attachedMelayaniCollectionNew.add(melayaniCollectionNewMelayaniToAttach);
            }
            melayaniCollectionNew = attachedMelayaniCollectionNew;
            peminjaman.setMelayaniCollection(melayaniCollectionNew);
            peminjaman = em.merge(peminjaman);
            if (idBukuOld != null && !idBukuOld.equals(idBukuNew)) {
                idBukuOld.getPeminjamanCollection().remove(peminjaman);
                idBukuOld = em.merge(idBukuOld);
            }
            if (idBukuNew != null && !idBukuNew.equals(idBukuOld)) {
                idBukuNew.getPeminjamanCollection().add(peminjaman);
                idBukuNew = em.merge(idBukuNew);
            }
            if (idMemberOld != null && !idMemberOld.equals(idMemberNew)) {
                idMemberOld.getPeminjamanCollection().remove(peminjaman);
                idMemberOld = em.merge(idMemberOld);
            }
            if (idMemberNew != null && !idMemberNew.equals(idMemberOld)) {
                idMemberNew.getPeminjamanCollection().add(peminjaman);
                idMemberNew = em.merge(idMemberNew);
            }
            for (Melayani melayaniCollectionNewMelayani : melayaniCollectionNew) {
                if (!melayaniCollectionOld.contains(melayaniCollectionNewMelayani)) {
                    Peminjaman oldIdPeminjamanOfMelayaniCollectionNewMelayani = melayaniCollectionNewMelayani.getIdPeminjaman();
                    melayaniCollectionNewMelayani.setIdPeminjaman(peminjaman);
                    melayaniCollectionNewMelayani = em.merge(melayaniCollectionNewMelayani);
                    if (oldIdPeminjamanOfMelayaniCollectionNewMelayani != null && !oldIdPeminjamanOfMelayaniCollectionNewMelayani.equals(peminjaman)) {
                        oldIdPeminjamanOfMelayaniCollectionNewMelayani.getMelayaniCollection().remove(melayaniCollectionNewMelayani);
                        oldIdPeminjamanOfMelayaniCollectionNewMelayani = em.merge(oldIdPeminjamanOfMelayaniCollectionNewMelayani);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = peminjaman.getIdPeminjaman();
                if (findPeminjaman(id) == null) {
                    throw new NonexistentEntityException("The peminjaman with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peminjaman peminjaman;
            try {
                peminjaman = em.getReference(Peminjaman.class, id);
                peminjaman.getIdPeminjaman();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peminjaman with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Melayani> melayaniCollectionOrphanCheck = peminjaman.getMelayaniCollection();
            for (Melayani melayaniCollectionOrphanCheckMelayani : melayaniCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Peminjaman (" + peminjaman + ") cannot be destroyed since the Melayani " + melayaniCollectionOrphanCheckMelayani + " in its melayaniCollection field has a non-nullable idPeminjaman field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Buku idBuku = peminjaman.getIdBuku();
            if (idBuku != null) {
                idBuku.getPeminjamanCollection().remove(peminjaman);
                idBuku = em.merge(idBuku);
            }
            Member1 idMember = peminjaman.getIdMember();
            if (idMember != null) {
                idMember.getPeminjamanCollection().remove(peminjaman);
                idMember = em.merge(idMember);
            }
            em.remove(peminjaman);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Peminjaman> findPeminjamanEntities() {
        return findPeminjamanEntities(true, -1, -1);
    }

    public List<Peminjaman> findPeminjamanEntities(int maxResults, int firstResult) {
        return findPeminjamanEntities(false, maxResults, firstResult);
    }

    private List<Peminjaman> findPeminjamanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Peminjaman.class));
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

    public Peminjaman findPeminjaman(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Peminjaman.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeminjamanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Peminjaman> rt = cq.from(Peminjaman.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
