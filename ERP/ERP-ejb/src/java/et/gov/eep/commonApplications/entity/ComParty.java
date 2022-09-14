/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

//import et.gov.insa.epse.spras.entity.SprasDeliveryNote;
//import et.gov.insa.epse.spras.entity.SprasReceiptNote;


//import et.gov.eep.mms.entity.PapmsDirectPurchase;
//import et.gov.eep.mms.entity.PapmsEvaluationReqDetail;
//import et.gov.eep.mms.entity.PapmsPaymentRequisition;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sahele
 */
@Entity
@Table(name = "com_party")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComParty.findAll", query = "SELECT c FROM ComParty c"),
    @NamedQuery(name = "ComParty.findByPartyId", query = "SELECT c FROM ComParty c WHERE c.partyId = :partyId"),
    @NamedQuery(name = "ComParty.findByName", query = "SELECT c FROM ComParty c WHERE c.name = :name"),
    @NamedQuery(name = "ComParty.findByDescription", query = "SELECT c FROM ComParty c WHERE c.description = :description"),
    @NamedQuery(name = "ComParty.findByLocation", query = "SELECT c FROM ComParty c WHERE c.location = :location"),
    @NamedQuery(name = "ComParty.findByTinNo", query = "SELECT c FROM ComParty c WHERE c.tinNo = :tinNo"),
    @NamedQuery(name = "ComParty.findByVatNo", query = "SELECT c FROM ComParty c WHERE c.vatNo = :vatNo"),
    @NamedQuery(name = "ComParty.searchParty", query = "SELECT c FROM ComParty c WHERE c.name Like :name"),
    @NamedQuery(name = "ComParty.findByType", query = "SELECT c FROM ComParty c WHERE c.type = :type")})
public class ComParty implements Serializable {

//    @OneToMany(mappedBy = "transporter")
//    private List<SprasDeliveryNote> sprasDeliveryNoteList;
//    @OneToMany(mappedBy = "customerName")
//    private List<SprasDeliveryNote> sprasDeliveryNoteList1;
//    @OneToMany(mappedBy = "transporter")
//    private List<SprasReceiptNote> sprasReceiptNoteList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "party_id")
    private Integer partyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tin_no")
    private String tinNo;
    @Size(max = 50)
    @Column(name = "vat_no")
    private String vatNo;
    @Size(max = 20)
    @Column(name = "type")
    private String type;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partyId")
//    private List<ComContactPerson> comContactPersonList;
//    @JoinColumn(name = "address_info_id", referencedColumnName = "address_info_id")
//    @ManyToOne(optional = false)
//    private ComAddressInfo addressInfoId;
//    @JoinColumn(name = "party_type_id", referencedColumnName = "party_type_id")
//    @ManyToOne(optional = false)
//    private ComLuPartyType partyTypeId;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vendorId")
//    private PapmsEvaluationReqDetail papmsEvaluationReqDetailList;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vendorName")
//    private PapmsDirectPurchase papmsDirectPurchase;
//
//    @OneToOne(mappedBy = "vendorId")
//    private PapmsPaymentRequisition papmsPaymentRequisition;

    /**
     *
     */
    
    public ComParty() {
    }

    /**
     *
     * @param partyId
     */
    public ComParty(Integer partyId) {
        this.partyId = partyId;
    }

    /**
     *
     * @param partyId
     * @param name
     * @param location
     * @param tinNo
     */
    public ComParty(Integer partyId, String name, String location, String tinNo) {
        this.partyId = partyId;
        this.name = name;
        this.location = location;
        this.tinNo = tinNo;
    }

    /**
     *
     * @return
     */
    public Integer getPartyId() {
        return partyId;
    }

    /**
     *
     * @param partyId
     */
    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */
    public String getTinNo() {
        return tinNo;
    }

    /**
     *
     * @param tinNo
     */
    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    /**
     *
     * @return
     */
    public String getVatNo() {
        return vatNo;
    }

    /**
     *
     * @param vatNo
     */
    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

//    @XmlTransient
//    public List<ComContactPerson> getComContactPersonList() {
//        return comContactPersonList;
//    }
//
//    public void setComContactPersonList(List<ComContactPerson> comContactPersonList) {
//        this.comContactPersonList = comContactPersonList;
//    }
//
//    public ComAddressInfo getAddressInfoId() {
//        return addressInfoId;
//    }
//
//    public void setAddressInfoId(ComAddressInfo addressInfoId) {
//        this.addressInfoId = addressInfoId;
//    }
//
//    public ComLuPartyType getPartyTypeId() {
//        return partyTypeId;
//    }
//
//    public void setPartyTypeId(ComLuPartyType partyTypeId) {
//        this.partyTypeId = partyTypeId;
//    }

//    public PapmsDirectPurchase getPapmsDirectPurchase() {
//        return papmsDirectPurchase;
//    }
//
//    public void setPapmsDirectPurchase(PapmsDirectPurchase papmsDirectPurchase) {
//        this.papmsDirectPurchase = papmsDirectPurchase;
//    }
//
//    public PapmsPaymentRequisition getPapmsPaymentRequisition() {
//        return papmsPaymentRequisition;
//    }
//
//    public void setPapmsPaymentRequisition(PapmsPaymentRequisition papmsPaymentRequisition) {
//        this.papmsPaymentRequisition = papmsPaymentRequisition;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partyId != null ? partyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComParty)) {
            return false;
        }
        ComParty other = (ComParty) object;
        if ((this.partyId == null && other.partyId != null) || (this.partyId != null && !this.partyId.equals(other.partyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

//    @XmlTransient
//    public List<SprasReceiptNote> getSprasReceiptNoteList() {
//        return sprasReceiptNoteList;
//    }
//
//    public void setSprasReceiptNoteList(List<SprasReceiptNote> sprasReceiptNoteList) {
//        this.sprasReceiptNoteList = sprasReceiptNoteList;
//    }
//
//    @XmlTransient
//    public List<SprasDeliveryNote> getSprasDeliveryNoteList() {
//        return sprasDeliveryNoteList;
//    }
//
//    public void setSprasDeliveryNoteList(List<SprasDeliveryNote> sprasDeliveryNoteList) {
//        this.sprasDeliveryNoteList = sprasDeliveryNoteList;
//    }

//    @XmlTransient
//    public List<SprasDeliveryNote> getSprasDeliveryNoteList1() {
//        return sprasDeliveryNoteList1;
//    }
//
//    public void setSprasDeliveryNoteList1(List<SprasDeliveryNote> sprasDeliveryNoteList1) {
//        this.sprasDeliveryNoteList1 = sprasDeliveryNoteList1;
//    }

//    @XmlTransient
//    public List<PapmsEvaluationReqDetail> getPapmsEvaluationReqDetailList() {
//        if (papmsEvaluationReqDetailList == null) {
//            papmsEvaluationReqDetailList = new ArrayList<>();
//        }
//        return papmsEvaluationReqDetailList;
//    }
//
//    public void setPapmsEvaluationReqDetailList(List<PapmsEvaluationReqDetail> papmsEvaluationReqDetailList) {
//        this.papmsEvaluationReqDetailList = papmsEvaluationReqDetailList;
//    }
//    public PapmsEvaluationReqDetail getPapmsEvaluationReqDetailList() {
//
//        return papmsEvaluationReqDetailList;
//    }
//
//    public void setPapmsEvaluationReqDetailList(PapmsEvaluationReqDetail papmsEvaluationReqDetailList) {
//        this.papmsEvaluationReqDetailList = papmsEvaluationReqDetailList;
//    }

}
