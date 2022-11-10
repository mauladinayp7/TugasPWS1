/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pabd.group.learnmigrate;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author mladi
 */
@Entity
@Table(name = "melayani")
@NamedQueries({
    @NamedQuery(name = "Melayani.findAll", query = "SELECT m FROM Melayani m"),
    @NamedQuery(name = "Melayani.findByIdMelayanii", query = "SELECT m FROM Melayani m WHERE m.idMelayanii = :idMelayanii")})
public class Melayani implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_melayanii")
    private String idMelayanii;
    @JoinColumn(name = "id_emp", referencedColumnName = "id_emp")
    @ManyToOne(optional = false)
    private Employee idEmp;
    @JoinColumn(name = "id_peminjaman", referencedColumnName = "id_peminjaman")
    @ManyToOne(optional = false)
    private Peminjaman idPeminjaman;

    public Melayani() {
    }

    public Melayani(String idMelayanii) {
        this.idMelayanii = idMelayanii;
    }

    public String getIdMelayanii() {
        return idMelayanii;
    }

    public void setIdMelayanii(String idMelayanii) {
        this.idMelayanii = idMelayanii;
    }

    public Employee getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(Employee idEmp) {
        this.idEmp = idEmp;
    }

    public Peminjaman getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(Peminjaman idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMelayanii != null ? idMelayanii.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Melayani)) {
            return false;
        }
        Melayani other = (Melayani) object;
        if ((this.idMelayanii == null && other.idMelayanii != null) || (this.idMelayanii != null && !this.idMelayanii.equals(other.idMelayanii))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pabd.group.learnmigrate.Melayani[ idMelayanii=" + idMelayanii + " ]";
    }
    
}
