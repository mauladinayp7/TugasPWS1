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
public class EmployeeJpaController implements Serializable {

    public EmployeeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pabd.group_learnmigrate_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employee employee) throws PreexistingEntityException, Exception {
        if (employee.getMelayaniCollection() == null) {
            employee.setMelayaniCollection(new ArrayList<Melayani>());
        }
        if (employee.getMendataCollection() == null) {
            employee.setMendataCollection(new ArrayList<Mendata>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Melayani> attachedMelayaniCollection = new ArrayList<Melayani>();
            for (Melayani melayaniCollectionMelayaniToAttach : employee.getMelayaniCollection()) {
                melayaniCollectionMelayaniToAttach = em.getReference(melayaniCollectionMelayaniToAttach.getClass(), melayaniCollectionMelayaniToAttach.getIdMelayanii());
                attachedMelayaniCollection.add(melayaniCollectionMelayaniToAttach);
            }
            employee.setMelayaniCollection(attachedMelayaniCollection);
            Collection<Mendata> attachedMendataCollection = new ArrayList<Mendata>();
            for (Mendata mendataCollectionMendataToAttach : employee.getMendataCollection()) {
                mendataCollectionMendataToAttach = em.getReference(mendataCollectionMendataToAttach.getClass(), mendataCollectionMendataToAttach.getIdData());
                attachedMendataCollection.add(mendataCollectionMendataToAttach);
            }
            employee.setMendataCollection(attachedMendataCollection);
            em.persist(employee);
            for (Melayani melayaniCollectionMelayani : employee.getMelayaniCollection()) {
                Employee oldIdEmpOfMelayaniCollectionMelayani = melayaniCollectionMelayani.getIdEmp();
                melayaniCollectionMelayani.setIdEmp(employee);
                melayaniCollectionMelayani = em.merge(melayaniCollectionMelayani);
                if (oldIdEmpOfMelayaniCollectionMelayani != null) {
                    oldIdEmpOfMelayaniCollectionMelayani.getMelayaniCollection().remove(melayaniCollectionMelayani);
                    oldIdEmpOfMelayaniCollectionMelayani = em.merge(oldIdEmpOfMelayaniCollectionMelayani);
                }
            }
            for (Mendata mendataCollectionMendata : employee.getMendataCollection()) {
                Employee oldIdEmpOfMendataCollectionMendata = mendataCollectionMendata.getIdEmp();
                mendataCollectionMendata.setIdEmp(employee);
                mendataCollectionMendata = em.merge(mendataCollectionMendata);
                if (oldIdEmpOfMendataCollectionMendata != null) {
                    oldIdEmpOfMendataCollectionMendata.getMendataCollection().remove(mendataCollectionMendata);
                    oldIdEmpOfMendataCollectionMendata = em.merge(oldIdEmpOfMendataCollectionMendata);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmployee(employee.getIdEmp()) != null) {
                throw new PreexistingEntityException("Employee " + employee + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employee employee) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee persistentEmployee = em.find(Employee.class, employee.getIdEmp());
            Collection<Melayani> melayaniCollectionOld = persistentEmployee.getMelayaniCollection();
            Collection<Melayani> melayaniCollectionNew = employee.getMelayaniCollection();
            Collection<Mendata> mendataCollectionOld = persistentEmployee.getMendataCollection();
            Collection<Mendata> mendataCollectionNew = employee.getMendataCollection();
            List<String> illegalOrphanMessages = null;
            for (Melayani melayaniCollectionOldMelayani : melayaniCollectionOld) {
                if (!melayaniCollectionNew.contains(melayaniCollectionOldMelayani)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Melayani " + melayaniCollectionOldMelayani + " since its idEmp field is not nullable.");
                }
            }
            for (Mendata mendataCollectionOldMendata : mendataCollectionOld) {
                if (!mendataCollectionNew.contains(mendataCollectionOldMendata)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mendata " + mendataCollectionOldMendata + " since its idEmp field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Melayani> attachedMelayaniCollectionNew = new ArrayList<Melayani>();
            for (Melayani melayaniCollectionNewMelayaniToAttach : melayaniCollectionNew) {
                melayaniCollectionNewMelayaniToAttach = em.getReference(melayaniCollectionNewMelayaniToAttach.getClass(), melayaniCollectionNewMelayaniToAttach.getIdMelayanii());
                attachedMelayaniCollectionNew.add(melayaniCollectionNewMelayaniToAttach);
            }
            melayaniCollectionNew = attachedMelayaniCollectionNew;
            employee.setMelayaniCollection(melayaniCollectionNew);
            Collection<Mendata> attachedMendataCollectionNew = new ArrayList<Mendata>();
            for (Mendata mendataCollectionNewMendataToAttach : mendataCollectionNew) {
                mendataCollectionNewMendataToAttach = em.getReference(mendataCollectionNewMendataToAttach.getClass(), mendataCollectionNewMendataToAttach.getIdData());
                attachedMendataCollectionNew.add(mendataCollectionNewMendataToAttach);
            }
            mendataCollectionNew = attachedMendataCollectionNew;
            employee.setMendataCollection(mendataCollectionNew);
            employee = em.merge(employee);
            for (Melayani melayaniCollectionNewMelayani : melayaniCollectionNew) {
                if (!melayaniCollectionOld.contains(melayaniCollectionNewMelayani)) {
                    Employee oldIdEmpOfMelayaniCollectionNewMelayani = melayaniCollectionNewMelayani.getIdEmp();
                    melayaniCollectionNewMelayani.setIdEmp(employee);
                    melayaniCollectionNewMelayani = em.merge(melayaniCollectionNewMelayani);
                    if (oldIdEmpOfMelayaniCollectionNewMelayani != null && !oldIdEmpOfMelayaniCollectionNewMelayani.equals(employee)) {
                        oldIdEmpOfMelayaniCollectionNewMelayani.getMelayaniCollection().remove(melayaniCollectionNewMelayani);
                        oldIdEmpOfMelayaniCollectionNewMelayani = em.merge(oldIdEmpOfMelayaniCollectionNewMelayani);
                    }
                }
            }
            for (Mendata mendataCollectionNewMendata : mendataCollectionNew) {
                if (!mendataCollectionOld.contains(mendataCollectionNewMendata)) {
                    Employee oldIdEmpOfMendataCollectionNewMendata = mendataCollectionNewMendata.getIdEmp();
                    mendataCollectionNewMendata.setIdEmp(employee);
                    mendataCollectionNewMendata = em.merge(mendataCollectionNewMendata);
                    if (oldIdEmpOfMendataCollectionNewMendata != null && !oldIdEmpOfMendataCollectionNewMendata.equals(employee)) {
                        oldIdEmpOfMendataCollectionNewMendata.getMendataCollection().remove(mendataCollectionNewMendata);
                        oldIdEmpOfMendataCollectionNewMendata = em.merge(oldIdEmpOfMendataCollectionNewMendata);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = employee.getIdEmp();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
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
            Employee employee;
            try {
                employee = em.getReference(Employee.class, id);
                employee.getIdEmp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Melayani> melayaniCollectionOrphanCheck = employee.getMelayaniCollection();
            for (Melayani melayaniCollectionOrphanCheckMelayani : melayaniCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the Melayani " + melayaniCollectionOrphanCheckMelayani + " in its melayaniCollection field has a non-nullable idEmp field.");
            }
            Collection<Mendata> mendataCollectionOrphanCheck = employee.getMendataCollection();
            for (Mendata mendataCollectionOrphanCheckMendata : mendataCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the Mendata " + mendataCollectionOrphanCheckMendata + " in its mendataCollection field has a non-nullable idEmp field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(employee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employee> findEmployeeEntities() {
        return findEmployeeEntities(true, -1, -1);
    }

    public List<Employee> findEmployeeEntities(int maxResults, int firstResult) {
        return findEmployeeEntities(false, maxResults, firstResult);
    }

    private List<Employee> findEmployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
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

    public Employee findEmployee(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
