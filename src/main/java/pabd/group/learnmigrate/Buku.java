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
@Table(name = "buku")
@NamedQueries({
    @NamedQuery(name = "Buku.findAll", query = "SELECT b FROM Buku b"),
    @NamedQuery(name = "Buku.findByIdBuku", query = "SELECT b FROM Buku b WHERE b.idBuku = :idBuku"),
    @NamedQuery(name = "Buku.findByJudul", query = "SELECT b FROM Buku b WHERE b.judul = :judul"),
    @NamedQuery(name = "Buku.findByAuthor", query = "SELECT b FROM Buku b WHERE b.author = :author"),
    @NamedQuery(name = "Buku.findByPenerbit", query = "SELECT b FROM Buku b WHERE b.penerbit = :penerbit"),
    @NamedQuery(name = "Buku.findByTahunTerbit", query = "SELECT b FROM Buku b WHERE b.tahunTerbit = :tahunTerbit"),
    @NamedQuery(name = "Buku.findByCetakan", query = "SELECT b FROM Buku b WHERE b.cetakan = :cetakan")})
public class Buku implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_buku")
    private String idBuku;
    @Basic(optional = false)
    @Column(name = "judul")
    private String judul;
    @Basic(optional = false)
    @Column(name = "author")
    private String author;
    @Basic(optional = false)
    @Column(name = "penerbit")
    private String penerbit;
    @Basic(optional = false)
    @Column(name = "tahun_terbit")
    @Temporal(TemporalType.DATE)
    private Date tahunTerbit;
    @Basic(optional = false)
    @Column(name = "cetakan")
    private String cetakan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBuku")
    private Collection<Meminjam> meminjamCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBuku")
    private Collection<Mendata> mendataCollection;
    @OneToMany(mappedBy = "idBuku")
    private Collection<Peminjaman> peminjamanCollection;

    public Buku() {
    }

    public Buku(String idBuku) {
        this.idBuku = idBuku;
    }

    public Buku(String idBuku, String judul, String author, String penerbit, Date tahunTerbit, String cetakan) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.author = author;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.cetakan = cetakan;
    }

    public String getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(String idBuku) {
        this.idBuku = idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public Date getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(Date tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public String getCetakan() {
        return cetakan;
    }

    public void setCetakan(String cetakan) {
        this.cetakan = cetakan;
    }

    public Collection<Meminjam> getMeminjamCollection() {
        return meminjamCollection;
    }

    public void setMeminjamCollection(Collection<Meminjam> meminjamCollection) {
        this.meminjamCollection = meminjamCollection;
    }

    public Collection<Mendata> getMendataCollection() {
        return mendataCollection;
    }

    public void setMendataCollection(Collection<Mendata> mendataCollection) {
        this.mendataCollection = mendataCollection;
    }

    public Collection<Peminjaman> getPeminjamanCollection() {
        return peminjamanCollection;
    }

    public void setPeminjamanCollection(Collection<Peminjaman> peminjamanCollection) {
        this.peminjamanCollection = peminjamanCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBuku != null ? idBuku.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buku)) {
            return false;
        }
        Buku other = (Buku) object;
        if ((this.idBuku == null && other.idBuku != null) || (this.idBuku != null && !this.idBuku.equals(other.idBuku))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pabd.group.learnmigrate.Buku[ idBuku=" + idBuku + " ]";
    }
    
}
