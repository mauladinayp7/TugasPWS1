/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pabd.group.learnmigrate;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author mladi
 */
@Entity
@Table(name = "employee")
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findByIdEmp", query = "SELECT e FROM Employee e WHERE e.idEmp = :idEmp"),
    @NamedQuery(name = "Employee.findByNmEmp", query = "SELECT e FROM Employee e WHERE e.nmEmp = :nmEmp")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_emp")
    private String idEmp;
    @Basic(optional = false)
    @Column(name = "nm_emp")
    private String nmEmp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmp")
    private Collection<Melayani> melayaniCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmp")
    private Collection<Mendata> mendataCollection;

    public Employee() {
    }

    public Employee(String idEmp) {
        this.idEmp = idEmp;
    }

    public Employee(String idEmp, String nmEmp) {
        this.idEmp = idEmp;
        this.nmEmp = nmEmp;
    }

    public String getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(String idEmp) {
        this.idEmp = idEmp;
    }

    public String getNmEmp() {
        return nmEmp;
    }

    public void setNmEmp(String nmEmp) {
        this.nmEmp = nmEmp;
    }

    public Collection<Melayani> getMelayaniCollection() {
        return melayaniCollection;
    }

    public void setMelayaniCollection(Collection<Melayani> melayaniCollection) {
        this.melayaniCollection = melayaniCollection;
    }

    public Collection<Mendata> getMendataCollection() {
        return mendataCollection;
    }

    public void setMendataCollection(Collection<Mendata> mendataCollection) {
        this.mendataCollection = mendataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmp != null ? idEmp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.idEmp == null && other.idEmp != null) || (this.idEmp != null && !this.idEmp.equals(other.idEmp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pabd.group.learnmigrate.Employee[ idEmp=" + idEmp + " ]";
    }
    
}
