package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsBidOpeningCheckList;
import et.gov.eep.prms.entity.PrmsBidOpeningChecklstDt;
import et.gov.eep.prms.entity.PrmsBidSubmission;
import et.gov.eep.prms.entity.PrmsBidSubmissionDetail;
import et.gov.eep.prms.entity.PrmsCheckListDetailforlot;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface BidOpeningListLocal {

    /**
     *
     * @param prmsBidOpeningCheckList
     */
    public void update(PrmsBidOpeningCheckList prmsBidOpeningCheckList);

    /**
     *
     * @param prmsBidOpeningCheckList
     */
    public void create(PrmsBidOpeningCheckList prmsBidOpeningCheckList);

    /**
     *
     * @param prmsBidOpeningCheckList
     * @return
     */
    PrmsBidOpeningCheckList getRequestNo(PrmsBidOpeningCheckList prmsBidOpeningCheckList);

    /**
     *
     * @return
     */
//    public ArrayList<PrmsBidDocSale> ItemBidNoList();
    /**
     *
     * @param string
     * @return
     */
//    public ArrayList<PrmsBidDocSale> ItemName(String string);
//    ArrayList<PrmsBidOpeningCheckList> searchPartyList(PrmsBidOpeningCheckList prmsBidOpeningCheckList);
//
//    PrmsBidOpeningCheckList getCustomerFee(PrmsBidOpeningCheckList customerFee);
    /**
     *
     * @return
     */
    public List<HrEmployees> Employees();

    /**
     *
     * @param prmsBidOpeningCheckList
     * @return
     */
    public ArrayList<PrmsBidOpeningCheckList> searchRequestForPayment(PrmsBidOpeningCheckList prmsBidOpeningCheckList);

    /**
     *
     * @return
     */
    public PrmsBidOpeningCheckList getLastCheckListNo();

    /**
     *
     * @return
     */
    public String getLastSIVNo();

    /**
     *
     * @param prmsBidOpeningChecklstDt
     */
    public void deleteContactPersonInfo(PrmsBidOpeningChecklstDt prmsBidOpeningChecklstDt);

    /**
     *
     * @param prmsBidOpeningCheckList
     * @return
     */
    List<PrmsBidOpeningCheckList> searchCheckList(PrmsBidOpeningCheckList prmsBidOpeningCheckList);

    /**
     *
     * @return
     */
    public ArrayList<PrmsBid> BidNo();

    /**
     *
     * @param string
     * @return
     */
    public ArrayList<PrmsBid> bidNo(String string);

    public PrmsBid bidGetter(PrmsBid prmsBid);

    public PrmsBidOpeningCheckList LastCheckListNo();

    public PrmsBidOpeningCheckList getSelectedRequest(BigDecimal id);

    public PrmsSupplyProfile vendourName(PrmsSupplyProfile supplyProfile);

    public List<HrEmployees> getEmployeeNames();

    public ArrayList<ComLuCountry> getCountryName();

    public ArrayList<PrmsBidDetail> getLotNum();

    public ArrayList<HrCommittees> getCommitee();

    public List<PrmsBidSubmission> getBidNoList();

    public List<PrmsBid> getBidNoListForCheckLIst();

    public List<PrmsBidSubmissionDetail> getsupplierlist(String bidNo);

    public List<PrmsBidDetail> getLotNo(String bidNo);

    public ArrayList<PrmsSupplyProfile> getsupplierlist(PrmsBid bidNo);

    public ArrayList<PrmsBidSubmission> BidNoListForCheckList();

    public List<PrmsBidDetail> getBidListForLot(PrmsBid bidNo);

    public ArrayList<PrmsBid> BidNoForCheck();

    public HrCommittees getCommittee(HrCommittees hrCommittees);

    public List<HrCommitteeMembers> getCommitteeMembers(HrCommittees hrCommittees);

    public PrmsBidOpeningChecklstDt LastCheckListDtid();

    public PrmsCheckListDetailforlot LastCheckListlotid();

    public ArrayList<PrmsBidOpeningCheckList> searchBidderRegistration();

    public List<PrmsSupplyProfile> findSuppliers();

    public List<PrmsBidOpeningCheckList> getBidOpeningCheckListSearchParameterLst();
}
