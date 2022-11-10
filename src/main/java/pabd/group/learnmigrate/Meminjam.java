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
@Table(name = "meminjam")
@NamedQueries({
    @NamedQuery(name = "Meminjam.findAll", query = "SELECT m FROM Meminjam m"),
    @NamedQuery(name = "Meminjam.findByIdMeminjam", query = "SELECT m FROM Meminjam m WHERE m.idMeminjam = :idMeminjam"),
    @NamedQuery(name = "Meminjam.findByJmlPjm", query = "SELECT m FROM Meminjam m WHERE m.jmlPjm = :jmlPjm"),
    @NamedQuery(name = "Meminjam.findBySttsBuku", query = "SELECT m FROM Meminjam m WHERE m.sttsBuku = :sttsBuku")})
public class Meminjam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_meminjam")
    private String idMeminjam;
    @Basic(optional = false)
    @Column(name = "jml_pjm")
    private int jmlPjm;
    @Basic(optional = false)
    @Column(name = "stts_buku")
    private String sttsBuku;
    @JoinColumn(name = "id_member", referencedColumnName = "id_member")
    @ManyToOne(optional = false)
    private Member1 idMember;
    @JoinColumn(name = "id_buku", referencedColumnName = "id_buku")
    @ManyToOne(optional = false)
    private Buku idBuku;

    public Meminjam() {
    }

    public Meminjam(String idMeminjam) {
        this.idMeminjam = idMeminjam;
    }

    public Meminjam(String idMeminjam, int jmlPjm, String sttsBuku) {
        this.idMeminjam = idMeminjam;
        this.jmlPjm = jmlPjm;
        this.sttsBuku = sttsBuku;
    }

    public String getIdMeminjam() {
        return idMeminjam;
    }

    public void setIdMeminjam(String idMeminjam) {
        this.idMeminjam = idMeminjam;
    }

    public int getJmlPjm() {
        return jmlPjm;
    }

    public void setJmlPjm(int jmlPjm) {
        this.jmlPjm = jmlPjm;
    }

    public String getSttsBuku() {
        return sttsBuku;
    }

    public void setSttsBuku(String sttsBuku) {
        this.sttsBuku = sttsBuku;
    }

    public Member1 getIdMember() {
        return idMember;
    }

    public void setIdMember(Member1 idMember) {
        this.idMember = idMember;
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
        hash += (idMeminjam != null ? idMeminjam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Meminjam)) {
            return false;
        }
        Meminjam other = (Meminjam) object;
        if ((this.idMeminjam == null && other.idMeminjam != null) || (this.idMeminjam != null && !this.idMeminjam.equals(other.idMeminjam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pabd.group.learnmigrate.Meminjam[ idMeminjam=" + idMeminjam + " ]";
    }
    
}
