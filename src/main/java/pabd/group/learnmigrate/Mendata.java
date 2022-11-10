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
@Table(name = "mendata")
@NamedQueries({
    @NamedQuery(name = "Mendata.findAll", query = "SELECT m FROM Mendata m"),
    @NamedQuery(name = "Mendata.findByIdData", query = "SELECT m FROM Mendata m WHERE m.idData = :idData"),
    @NamedQuery(name = "Mendata.findBySttsBuku", query = "SELECT m FROM Mendata m WHERE m.sttsBuku = :sttsBuku")})
public class Mendata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_data")
    private String idData;
    @Basic(optional = false)
    @Column(name = "stts_buku")
    private String sttsBuku;
    @JoinColumn(name = "id_emp", referencedColumnName = "id_emp")
    @ManyToOne(optional = false)
    private Employee idEmp;
    @JoinColumn(name = "id_buku", referencedColumnName = "id_buku")
    @ManyToOne(optional = false)
    private Buku idBuku;

    public Mendata() {
    }

    public Mendata(String idData) {
        this.idData = idData;
    }

    public Mendata(String idData, String sttsBuku) {
        this.idData = idData;
        this.sttsBuku = sttsBuku;
    }

    public String getIdData() {
        return idData;
    }

    public void setIdData(String idData) {
        this.idData = idData;
    }

    public String getSttsBuku() {
        return sttsBuku;
    }

    public void setSttsBuku(String sttsBuku) {
        this.sttsBuku = sttsBuku;
    }

    public Employee getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(Employee idEmp) {
        this.idEmp = idEmp;
    }

    public Buku getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(Buku idBuku) {
        this.idBuku = idBuku;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idData != null ? idData.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mendata)) {
            return false;
        }
        Mendata other = (Mendata) object;
        if ((this.idData == null && other.idData != null) || (this.idData != null && !this.idData.equals(other.idData))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pabd.group.learnmigrate.Mendata[ idData=" + idData + " ]";
    }
    
}
