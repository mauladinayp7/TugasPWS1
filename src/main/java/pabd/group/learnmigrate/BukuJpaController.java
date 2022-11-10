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
public class BukuJpaController implements Serializable {

    public BukuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pabd.group_learnmigrate_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Buku buku) throws PreexistingEntityException, Exception {
        if (buku.getMeminjamCollection() == null) {
            buku.setMeminjamCollection(new ArrayList<Meminjam>());
        }
        if (buku.getMendataCollection() == null) {
            buku.setMendataCollection(new ArrayList<Mendata>());
        }
        if (buku.getPeminjamanCollection() == null) {
            buku.setPeminjamanCollection(new ArrayList<Peminjaman>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Meminjam> attachedMeminjamCollection = new ArrayList<Meminjam>();
            for (Meminjam meminjamCollectionMeminjamToAttach : buku.getMeminjamCollection()) {
                meminjamCollectionMeminjamToAttach = em.getReference(meminjamCollectionMeminjamToAttach.getClass(), meminjamCollectionMeminjamToAttach.getIdMeminjam());
                attachedMeminjamCollection.add(meminjamCollectionMeminjamToAttach);
            }
            buku.setMeminjamCollection(attachedMeminjamCollection);
            Collection<Mendata> attachedMendataCollection = new ArrayList<Mendata>();
            for (Mendata mendataCollectionMendataToAttach : buku.getMendataCollection()) {
                mendataCollectionMendataToAttach = em.getReference(mendataCollectionMendataToAttach.getClass(), mendataCollectionMendataToAttach.getIdData());
                attachedMendataCollection.add(mendataCollectionMendataToAttach);
            }
            buku.setMendataCollection(attachedMendataCollection);
            Collection<Peminjaman> attachedPeminjamanCollection = new ArrayList<Peminjaman>();
            for (Peminjaman peminjamanCollectionPeminjamanToAttach : buku.getPeminjamanCollection()) {
                peminjamanCollectionPeminjamanToAttach = em.getReference(peminjamanCollectionPeminjamanToAttach.getClass(), peminjamanCollectionPeminjamanToAttach.getIdPeminjaman());
                attachedPeminjamanCollection.add(peminjamanCollectionPeminjamanToAttach);
            }
            buku.setPeminjamanCollection(attachedPeminjamanCollection);
            em.persist(buku);
            for (Meminjam meminjamCollectionMeminjam : buku.getMeminjamCollection()) {
                Buku oldIdBukuOfMeminjamCollectionMeminjam = meminjamCollectionMeminjam.getIdBuku();
                meminjamCollectionMeminjam.setIdBuku(buku);
                meminjamCollectionMeminjam = em.merge(meminjamCollectionMeminjam);
                if (oldIdBukuOfMeminjamCollectionMeminjam != null) {
                    oldIdBukuOfMeminjamCollectionMeminjam.getMeminjamCollection().remove(meminjamCollectionMeminjam);
                    oldIdBukuOfMeminjamCollectionMeminjam = em.merge(oldIdBukuOfMeminjamCollectionMeminjam);
                }
            }
            for (Mendata mendataCollectionMendata : buku.getMendataCollection()) {
                Buku oldIdBukuOfMendataCollectionMendata = mendataCollectionMendata.getIdBuku();
                mendataCollectionMendata.setIdBuku(buku);
                mendataCollectionMendata = em.merge(mendataCollectionMendata);
                if (oldIdBukuOfMendataCollectionMendata != null) {
                    oldIdBukuOfMendataCollectionMendata.getMendataCollection().remove(mendataCollectionMendata);
                    oldIdBukuOfMendataCollectionMendata = em.merge(oldIdBukuOfMendataCollectionMendata);
                }
            }
            for (Peminjaman peminjamanCollectionPeminjaman : buku.getPeminjamanCollection()) {
                Buku oldIdBukuOfPeminjamanCollectionPeminjaman = peminjamanCollectionPeminjaman.getIdBuku();
                peminjamanCollectionPeminjaman.setIdBuku(buku);
                peminjamanCollectionPeminjaman = em.merge(peminjamanCollectionPeminjaman);
                if (oldIdBukuOfPeminjamanCollectionPeminjaman != null) {
                    oldIdBukuOfPeminjamanCollectionPeminjaman.getPeminjamanCollection().remove(peminjamanCollectionPeminjaman);
                    oldIdBukuOfPeminjamanCollectionPeminjaman = em.merge(oldIdBukuOfPeminjamanCollectionPeminjaman);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBuku(buku.getIdBuku()) != null) {
                throw new PreexistingEntityException("Buku " + buku + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Buku buku) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buku persistentBuku = em.find(Buku.class, buku.getIdBuku());
            Collection<Meminjam> meminjamCollectionOld = persistentBuku.getMeminjamCollection();
            Collection<Meminjam> meminjamCollectionNew = buku.getMeminjamCollection();
            Collection<Mendata> mendataCollectionOld = persistentBuku.getMendataCollection();
            Collection<Mendata> mendataCollectionNew = buku.getMendataCollection();
            Collection<Peminjaman> peminjamanCollectionOld = persistentBuku.getPeminjamanCollection();
            Collection<Peminjaman> peminjamanCollectionNew = buku.getPeminjamanCollection();
            List<String> illegalOrphanMessages = null;
            for (Meminjam meminjamCollectionOldMeminjam : meminjamCollectionOld) {
                if (!meminjamCollectionNew.contains(meminjamCollectionOldMeminjam)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Meminjam " + meminjamCollectionOldMeminjam + " since its idBuku field is not nullable.");
                }
            }
            for (Mendata mendataCollectionOldMendata : mendataCollectionOld) {
                if (!mendataCollectionNew.contains(mendataCollectionOldMendata)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mendata " + mendataCollectionOldMendata + " since its idBuku field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Meminjam> attachedMeminjamCollectionNew = new ArrayList<Meminjam>();
            for (Meminjam meminjamCollectionNewMeminjamToAttach : meminjamCollectionNew) {
                meminjamCollectionNewMeminjamToAttach = em.getReference(meminjamCollectionNewMeminjamToAttach.getClass(), meminjamCollectionNewMeminjamToAttach.getIdMeminjam());
                attachedMeminjamCollectionNew.add(meminjamCollectionNewMeminjamToAttach);
            }
            meminjamCollectionNew = attachedMeminjamCollectionNew;
            buku.setMeminjamCollection(meminjamCollectionNew);
            Collection<Mendata> attachedMendataCollectionNew = new ArrayList<Mendata>();
            for (Mendata mendataCollectionNewMendataToAttach : mendataCollectionNew) {
                mendataCollectionNewMendataToAttach = em.getReference(mendataCollectionNewMendataToAttach.getClass(), mendataCollectionNewMendataToAttach.getIdData());
                attachedMendataCollectionNew.add(mendataCollectionNewMendataToAttach);
            }
            mendataCollectionNew = attachedMendataCollectionNew;
            buku.setMendataCollection(mendataCollectionNew);
            Collection<Peminjaman> attachedPeminjamanCollectionNew = new ArrayList<Peminjaman>();
            for (Peminjaman peminjamanCollectionNewPeminjamanToAttach : peminjamanCollectionNew) {
                peminjamanCollectionNewPeminjamanToAttach = em.getReference(peminjamanCollectionNewPeminjamanToAttach.getClass(), peminjamanCollectionNewPeminjamanToAttach.getIdPeminjaman());
                attachedPeminjamanCollectionNew.add(peminjamanCollectionNewPeminjamanToAttach);
            }
            peminjamanCollectionNew = attachedPeminjamanCollectionNew;
            buku.setPeminjamanCollection(peminjamanCollectionNew);
            buku = em.merge(buku);
            for (Meminjam meminjamCollectionNewMeminjam : meminjamCollectionNew) {
                if (!meminjamCollectionOld.contains(meminjamCollectionNewMeminjam)) {
                    Buku oldIdBukuOfMeminjamCollectionNewMeminjam = meminjamCollectionNewMeminjam.getIdBuku();
                    meminjamCollectionNewMeminjam.setIdBuku(buku);
                    meminjamCollectionNewMeminjam = em.merge(meminjamCollectionNewMeminjam);
                    if (oldIdBukuOfMeminjamCollectionNewMeminjam != null && !oldIdBukuOfMeminjamCollectionNewMeminjam.equals(buku)) {
                        oldIdBukuOfMeminjamCollectionNewMeminjam.getMeminjamCollection().remove(meminjamCollectionNewMeminjam);
                        oldIdBukuOfMeminjamCollectionNewMeminjam = em.merge(oldIdBukuOfMeminjamCollectionNewMeminjam);
                    }
                }
            }
            for (Mendata mendataCollectionNewMendata : mendataCollectionNew) {
                if (!mendataCollectionOld.contains(mendataCollectionNewMendata)) {
                    Buku oldIdBukuOfMendataCollectionNewMendata = mendataCollectionNewMendata.getIdBuku();
                    mendataCollectionNewMendata.setIdBuku(buku);
                    mendataCollectionNewMendata = em.merge(mendataCollectionNewMendata);
                    if (oldIdBukuOfMendataCollectionNewMendata != null && !oldIdBukuOfMendataCollectionNewMendata.equals(buku)) {
                        oldIdBukuOfMendataCollectionNewMendata.getMendataCollection().remove(mendataCollectionNewMendata);
                        oldIdBukuOfMendataCollectionNewMendata = em.merge(oldIdBukuOfMendataCollectionNewMendata);
                    }
                }
            }
            for (Peminjaman peminjamanCollectionOldPeminjaman : peminjamanCollectionOld) {
                if (!peminjamanCollectionNew.contains(peminjamanCollectionOldPeminjaman)) {
                    peminjamanCollectionOldPeminjaman.setIdBuku(null);
                    peminjamanCollectionOldPeminjaman = em.merge(peminjamanCollectionOldPeminjaman);
                }
            }
            for (Peminjaman peminjamanCollectionNewPeminjaman : peminjamanCollectionNew) {
                if (!peminjamanCollectionOld.contains(peminjamanCollectionNewPeminjaman)) {
                    Buku oldIdBukuOfPeminjamanCollectionNewPeminjaman = peminjamanCollectionNewPeminjaman.getIdBuku();
                    peminjamanCollectionNewPeminjaman.setIdBuku(buku);
                    peminjamanCollectionNewPeminjaman = em.merge(peminjamanCollectionNewPeminjaman);
                    if (oldIdBukuOfPeminjamanCollectionNewPeminjaman != null && !oldIdBukuOfPeminjamanCollectionNewPeminjaman.equals(buku)) {
                        oldIdBukuOfPeminjamanCollectionNewPeminjaman.getPeminjamanCollection().remove(peminjamanCollectionNewPeminjaman);
                        oldIdBukuOfPeminjamanCollectionNewPeminjaman = em.merge(oldIdBukuOfPeminjamanCollectionNewPeminjaman);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = buku.getIdBuku();
                if (findBuku(id) == null) {
                    throw new NonexistentEntityException("The buku with id " + id + " no longer exists.");
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
            Buku buku;
            try {
                buku = em.getReference(Buku.class, id);
                buku.getIdBuku();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The buku with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Meminjam> meminjamCollectionOrphanCheck = buku.getMeminjamCollection();
            for (Meminjam meminjamCollectionOrphanCheckMeminjam : meminjamCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Buku (" + buku + ") cannot be destroyed since the Meminjam " + meminjamCollectionOrphanCheckMeminjam + " in its meminjamCollection field has a non-nullable idBuku field.");
            }
            Collection<Mendata> mendataCollectionOrphanCheck = buku.getMendataCollection();
            for (Mendata mendataCollectionOrphanCheckMendata : mendataCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Buku (" + buku + ") cannot be destroyed since the Mendata " + mendataCollectionOrphanCheckMendata + " in its mendataCollection field has a non-nullable idBuku field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Peminjaman> peminjamanCollection = buku.getPeminjamanCollection();
            for (Peminjaman peminjamanCollectionPeminjaman : peminjamanCollection) {
                peminjamanCollectionPeminjaman.setIdBuku(null);
                peminjamanCollectionPeminjaman = em.merge(peminjamanCollectionPeminjaman);
            }
            em.remove(buku);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Buku> findBukuEntities() {
        return findBukuEntities(true, -1, -1);
    }

    public List<Buku> findBukuEntities(int maxResults, int firstResult) {
        return findBukuEntities(false, maxResults, firstResult);
    }

    private List<Buku> findBukuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Buku.class));
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

    public Buku findBuku(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Buku.class, id);
        } finally {
            em.close();
        }
    }

    public int getBukuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Buku> rt = cq.from(Buku.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
