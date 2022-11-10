/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pabd.group.learnmigrate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author mladi
 */
@Entity
@Table(name = "peminjaman")
@NamedQueries({
    @NamedQuery(name = "Peminjaman.findAll", query = "SELECT p FROM Peminjaman p"),
    @NamedQuery(name = "Peminjaman.findByIdPeminjaman", query = "SELECT p FROM Peminjaman p WHERE p.idPeminjaman = :idPeminjaman"),
    @NamedQuery(name = "Peminjaman.findByNmMember", query = "SELECT p FROM Peminjaman p WHERE p.nmMember = :nmMember"),
    @NamedQuery(name = "Peminjaman.findByJudul", query = "SELECT p FROM Peminjaman p WHERE p.judul = :judul"),
    @NamedQuery(name = "Peminjaman.findByTglPjm", query = "SELECT p FROM Peminjaman p WHERE p.tglPjm = :tglPjm"),
    @NamedQuery(name = "Peminjaman.findByTglKbl", query = "SELECT p FROM Peminjaman p WHERE p.tglKbl = :tglKbl"),
    @NamedQuery(name = "Peminjaman.findByPenerbit", query = "SELECT p FROM Peminjaman p WHERE p.penerbit = :penerbit"),
    @NamedQuery(name = "Peminjaman.findByAuthor", query = "SELECT p FROM Peminjaman p WHERE p.author = :author")})
public class Peminjaman implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_peminjaman")
    private String idPeminjaman;
    @Basic(optional = false)
    @Column(name = "nm_member")
    private String nmMember;
    @Basic(optional = false)
    @Column(name = "judul")
    private String judul;
    @Basic(optional = false)
    @Column(name = "tgl_pjm")
    @Temporal(TemporalType.DATE)
    private Date tglPjm;
    @Basic(optional = false)
    @Column(name = "tgl_kbl")
    @Temporal(TemporalType.DATE)
    private Date tglKbl;
    @Basic(optional = false)
    @Column(name = "penerbit")
    private String penerbit;
    @Basic(optional = false)
    @Column(name = "author")
    private String author;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPeminjaman")
    private Collection<Melayani> melayaniCollection;
    @JoinColumn(name = "id_buku", referencedColumnName = "id_buku")
    @ManyToOne
    private Buku idBuku;
    @JoinColumn(name = "id_member", referencedColumnName = "id_member")
    @ManyToOne
    private Member1 idMember;

    public Peminjaman() {
    }

    public Peminjaman(String idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public Peminjaman(String idPeminjaman, String nmMember, String judul, Date tglPjm, Date tglKbl, String penerbit, String author) {
        this.idPeminjaman = idPeminjaman;
        this.nmMember = nmMember;
        this.judul = judul;
        this.tglPjm = tglPjm;
        this.tglKbl = tglKbl;
        this.penerbit = penerbit;
        this.author = author;
    }

    public String getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(String idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public String getNmMember() {
        return nmMember;
    }

    public void setNmMember(String nmMember) {
        this.nmMember = nmMember;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTglPjm() {
        return tglPjm;
    }

    public void setTglPjm(Date tglPjm) {
        this.tglPjm = tglPjm;
    }

    public Date getTglKbl() {
        return tglKbl;
    }

    public void setTglKbl(Date tglKbl) {
        this.tglKbl = tglKbl;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Collection<Melayani> getMelayaniCollection() {
        return melayaniCollection;
    }

    public void setMelayaniCollection(Collection<Melayani> melayaniCollection) {
        this.melayaniCollection = melayaniCollection;
    }

    public Buku getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(Buku idBuku) {
        this.idBuku = idBuku;
    }

    public Member1 getIdMember() {
        return idMember;
    }

    public void setIdMember(Member1 idMember) {
        this.idMember = idMember;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeminjaman != null ? idPeminjaman.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Peminjaman)) {
            return false;
        }
        Peminjaman other = (Peminjaman) object;
        if ((this.idPeminjaman == null && other.idPeminjaman != null) || (this.idPeminjaman != null && !this.idPeminjaman.equals(other.idPeminjaman))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pabd.group.learnmigrate.Peminjaman[ idPeminjaman=" + idPeminjaman + " ]";
    }
    
}
