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
@Table(name = "member")
@NamedQueries({
    @NamedQuery(name = "Member1.findAll", query = "SELECT m FROM Member1 m"),
    @NamedQuery(name = "Member1.findByIdMember", query = "SELECT m FROM Member1 m WHERE m.idMember = :idMember"),
    @NamedQuery(name = "Member1.findByNmMember", query = "SELECT m FROM Member1 m WHERE m.nmMember = :nmMember"),
    @NamedQuery(name = "Member1.findByAlmtMember", query = "SELECT m FROM Member1 m WHERE m.almtMember = :almtMember"),
    @NamedQuery(name = "Member1.findByTlpMember", query = "SELECT m FROM Member1 m WHERE m.tlpMember = :tlpMember"),
    @NamedQuery(name = "Member1.findByPassw", query = "SELECT m FROM Member1 m WHERE m.passw = :passw")})
public class Member1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_member")
    private String idMember;
    @Basic(optional = false)
    @Column(name = "nm_member")
    private String nmMember;
    @Basic(optional = false)
    @Column(name = "almt_member")
    private String almtMember;
    @Basic(optional = false)
    @Column(name = "tlp_member")
    private int tlpMember;
    @Basic(optional = false)
    @Column(name = "passw")
    private String passw;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMember")
    private Collection<Meminjam> meminjamCollection;
    @OneToMany(mappedBy = "idMember")
    private Collection<Peminjaman> peminjamanCollection;

    public Member1() {
    }

    public Member1(String idMember) {
        this.idMember = idMember;
    }

    public Member1(String idMember, String nmMember, String almtMember, int tlpMember, String passw) {
        this.idMember = idMember;
        this.nmMember = nmMember;
        this.almtMember = almtMember;
        this.tlpMember = tlpMember;
        this.passw = passw;
    }

    public String getIdMember() {
        return idMember;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public String getNmMember() {
        return nmMember;
    }

    public void setNmMember(String nmMember) {
        this.nmMember = nmMember;
    }

    public String getAlmtMember() {
        return almtMember;
    }

    public void setAlmtMember(String almtMember) {
        this.almtMember = almtMember;
    }

    public int getTlpMember() {
        return tlpMember;
    }

    public void setTlpMember(int tlpMember) {
        this.tlpMember = tlpMember;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public Collection<Meminjam> getMeminjamCollection() {
        return meminjamCollection;
    }

    public void setMeminjamCollection(Collection<Meminjam> meminjamCollection) {
        this.meminjamCollection = meminjamCollection;
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
        hash += (idMember != null ? idMember.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Member1)) {
            return false;
        }
        Member1 other = (Member1) object;
        if ((this.idMember == null && other.idMember != null) || (this.idMember != null && !this.idMember.equals(other.idMember))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pabd.group.learnmigrate.Member1[ idMember=" + idMember + " ]";
    }
    
}
