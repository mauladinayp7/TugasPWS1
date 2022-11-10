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
public class Member1JpaController implements Serializable {

    public Member1JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pabd.group_learnmigrate_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Member1 member1) throws PreexistingEntityException, Exception {
        if (member1.getMeminjamCollection() == null) {
            member1.setMeminjamCollection(new ArrayList<Meminjam>());
        }
        if (member1.getPeminjamanCollection() == null) {
            member1.setPeminjamanCollection(new ArrayList<Peminjaman>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Meminjam> attachedMeminjamCollection = new ArrayList<Meminjam>();
            for (Meminjam meminjamCollectionMeminjamToAttach : member1.getMeminjamCollection()) {
                meminjamCollectionMeminjamToAttach = em.getReference(meminjamCollectionMeminjamToAttach.getClass(), meminjamCollectionMeminjamToAttach.getIdMeminjam());
                attachedMeminjamCollection.add(meminjamCollectionMeminjamToAttach);
            }
            member1.setMeminjamCollection(attachedMeminjamCollection);
            Collection<Peminjaman> attachedPeminjamanCollection = new ArrayList<Peminjaman>();
            for (Peminjaman peminjamanCollectionPeminjamanToAttach : member1.getPeminjamanCollection()) {
                peminjamanCollectionPeminjamanToAttach = em.getReference(peminjamanCollectionPeminjamanToAttach.getClass(), peminjamanCollectionPeminjamanToAttach.getIdPeminjaman());
                attachedPeminjamanCollection.add(peminjamanCollectionPeminjamanToAttach);
            }
            member1.setPeminjamanCollection(attachedPeminjamanCollection);
            em.persist(member1);
            for (Meminjam meminjamCollectionMeminjam : member1.getMeminjamCollection()) {
                Member1 oldIdMemberOfMeminjamCollectionMeminjam = meminjamCollectionMeminjam.getIdMember();
                meminjamCollectionMeminjam.setIdMember(member1);
                meminjamCollectionMeminjam = em.merge(meminjamCollectionMeminjam);
                if (oldIdMemberOfMeminjamCollectionMeminjam != null) {
                    oldIdMemberOfMeminjamCollectionMeminjam.getMeminjamCollection().remove(meminjamCollectionMeminjam);
                    oldIdMemberOfMeminjamCollectionMeminjam = em.merge(oldIdMemberOfMeminjamCollectionMeminjam);
                }
            }
            for (Peminjaman peminjamanCollectionPeminjaman : member1.getPeminjamanCollection()) {
                Member1 oldIdMemberOfPeminjamanCollectionPeminjaman = peminjamanCollectionPeminjaman.getIdMember();
                peminjamanCollectionPeminjaman.setIdMember(member1);
                peminjamanCollectionPeminjaman = em.merge(peminjamanCollectionPeminjaman);
                if (oldIdMemberOfPeminjamanCollectionPeminjaman != null) {
                    oldIdMemberOfPeminjamanCollectionPeminjaman.getPeminjamanCollection().remove(peminjamanCollectionPeminjaman);
                    oldIdMemberOfPeminjamanCollectionPeminjaman = em.merge(oldIdMemberOfPeminjamanCollectionPeminjaman);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMember1(member1.getIdMember()) != null) {
                throw new PreexistingEntityException("Member1 " + member1 + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Member1 member1) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Member1 persistentMember1 = em.find(Member1.class, member1.getIdMember());
            Collection<Meminjam> meminjamCollectionOld = persistentMember1.getMeminjamCollection();
            Collection<Meminjam> meminjamCollectionNew = member1.getMeminjamCollection();
            Collection<Peminjaman> peminjamanCollectionOld = persistentMember1.getPeminjamanCollection();
            Collection<Peminjaman> peminjamanCollectionNew = member1.getPeminjamanCollection();
            List<String> illegalOrphanMessages = null;
            for (Meminjam meminjamCollectionOldMeminjam : meminjamCollectionOld) {
                if (!meminjamCollectionNew.contains(meminjamCollectionOldMeminjam)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Meminjam " + meminjamCollectionOldMeminjam + " since its idMember field is not nullable.");
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
            member1.setMeminjamCollection(meminjamCollectionNew);
            Collection<Peminjaman> attachedPeminjamanCollectionNew = new ArrayList<Peminjaman>();
            for (Peminjaman peminjamanCollectionNewPeminjamanToAttach : peminjamanCollectionNew) {
                peminjamanCollectionNewPeminjamanToAttach = em.getReference(peminjamanCollectionNewPeminjamanToAttach.getClass(), peminjamanCollectionNewPeminjamanToAttach.getIdPeminjaman());
                attachedPeminjamanCollectionNew.add(peminjamanCollectionNewPeminjamanToAttach);
            }
            peminjamanCollectionNew = attachedPeminjamanCollectionNew;
            member1.setPeminjamanCollection(peminjamanCollectionNew);
            member1 = em.merge(member1);
            for (Meminjam meminjamCollectionNewMeminjam : meminjamCollectionNew) {
                if (!meminjamCollectionOld.contains(meminjamCollectionNewMeminjam)) {
                    Member1 oldIdMemberOfMeminjamCollectionNewMeminjam = meminjamCollectionNewMeminjam.getIdMember();
                    meminjamCollectionNewMeminjam.setIdMember(member1);
                    meminjamCollectionNewMeminjam = em.merge(meminjamCollectionNewMeminjam);
                    if (oldIdMemberOfMeminjamCollectionNewMeminjam != null && !oldIdMemberOfMeminjamCollectionNewMeminjam.equals(member1)) {
                        oldIdMemberOfMeminjamCollectionNewMeminjam.getMeminjamCollection().remove(meminjamCollectionNewMeminjam);
                        oldIdMemberOfMeminjamCollectionNewMeminjam = em.merge(oldIdMemberOfMeminjamCollectionNewMeminjam);
                    }
                }
            }
            for (Peminjaman peminjamanCollectionOldPeminjaman : peminjamanCollectionOld) {
                if (!peminjamanCollectionNew.contains(peminjamanCollectionOldPeminjaman)) {
                    peminjamanCollectionOldPeminjaman.setIdMember(null);
                    peminjamanCollectionOldPeminjaman = em.merge(peminjamanCollectionOldPeminjaman);
                }
            }
            for (Peminjaman peminjamanCollectionNewPeminjaman : peminjamanCollectionNew) {
                if (!peminjamanCollectionOld.contains(peminjamanCollectionNewPeminjaman)) {
                    Member1 oldIdMemberOfPeminjamanCollectionNewPeminjaman = peminjamanCollectionNewPeminjaman.getIdMember();
                    peminjamanCollectionNewPeminjaman.setIdMember(member1);
                    peminjamanCollectionNewPeminjaman = em.merge(peminjamanCollectionNewPeminjaman);
                    if (oldIdMemberOfPeminjamanCollectionNewPeminjaman != null && !oldIdMemberOfPeminjamanCollectionNewPeminjaman.equals(member1)) {
                        oldIdMemberOfPeminjamanCollectionNewPeminjaman.getPeminjamanCollection().remove(peminjamanCollectionNewPeminjaman);
                        oldIdMemberOfPeminjamanCollectionNewPeminjaman = em.merge(oldIdMemberOfPeminjamanCollectionNewPeminjaman);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = member1.getIdMember();
                if (findMember1(id) == null) {
                    throw new NonexistentEntityException("The member1 with id " + id + " no longer exists.");
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
            Member1 member1;
            try {
                member1 = em.getReference(Member1.class, id);
                member1.getIdMember();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The member1 with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Meminjam> meminjamCollectionOrphanCheck = member1.getMeminjamCollection();
            for (Meminjam meminjamCollectionOrphanCheckMeminjam : meminjamCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Member1 (" + member1 + ") cannot be destroyed since the Meminjam " + meminjamCollectionOrphanCheckMeminjam + " in its meminjamCollection field has a non-nullable idMember field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Peminjaman> peminjamanCollection = member1.getPeminjamanCollection();
            for (Peminjaman peminjamanCollectionPeminjaman : peminjamanCollection) {
                peminjamanCollectionPeminjaman.setIdMember(null);
                peminjamanCollectionPeminjaman = em.merge(peminjamanCollectionPeminjaman);
            }
            em.remove(member1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Member1> findMember1Entities() {
        return findMember1Entities(true, -1, -1);
    }

    public List<Member1> findMember1Entities(int maxResults, int firstResult) {
        return findMember1Entities(false, maxResults, firstResult);
    }

    private List<Member1> findMember1Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Member1.class));
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

    public Member1 findMember1(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Member1.class, id);
        } finally {
            em.close();
        }
    }

    public int getMember1Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Member1> rt = cq.from(Member1.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
