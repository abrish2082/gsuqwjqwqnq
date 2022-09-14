/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.mapper.ComLuCountryFacade;
import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.mapper.committee.HrCommitteeMembersFacade;
import et.gov.eep.hrms.mapper.committee.HrCommitteesFacade;
import et.gov.eep.prms.entity.PrmsBidOpeningCheckList;
import et.gov.eep.prms.mapper.PrmsBidOpeningCheckListFacade;
import et.gov.eep.hrms.mapper.employee.HrEmployeesFacade;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsBidOpeningChecklstDt;
import et.gov.eep.prms.entity.PrmsBidSubmission;
import et.gov.eep.prms.entity.PrmsBidSubmissionDetail;
import et.gov.eep.prms.entity.PrmsCheckListDetailforlot;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsBidFacade;
import et.gov.eep.prms.mapper.PrmsBidOpeningChecklstDtFacade;
import et.gov.eep.prms.mapper.PrmsBidSubmissionDetailFacade;
import et.gov.eep.prms.mapper.PrmsBidSubmissionFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
// </editor-fold>

/**
 *
 * @author user
 */
@Stateless
public class BidOpeningListBean implements BidOpeningListLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private PrmsBidOpeningCheckListFacade prmsBidOpeningCheckListFacade;
    @EJB
    private PrmsBidFacade prmsBidFacade;
    @EJB
    private HrEmployeesFacade hrEmployeesFacade;
    @EJB
    private PrmsBidOpeningChecklstDtFacade prmsBidOpeningChecklstDtFacade;
    @EJB
    private ComLuCountryFacade comLuCountryFacade;
    @EJB
    private HrCommitteesFacade hrCommitteesFacade;
    @EJB
    HrCommitteeMembersFacade hrCommitteeMembersFacade;
    @EJB
    PrmsSupplyProfileFacade prmsSupplyProfileFacade;
    @EJB
    PrmsBidSubmissionDetailFacade submissionDetailFacade;
    @EJB
    PrmsBidSubmissionFacade prmsBidSubmissionFacade;
    // </editor-fold>

    /**
     *
     * @param prmsBidOpeningCheckList
     */
    @Override
    public void update(PrmsBidOpeningCheckList prmsBidOpeningCheckList) {
        prmsBidOpeningCheckListFacade.edit(prmsBidOpeningCheckList);
    }

    /**
     *
     * @param prmsBidOpeningCheckList
     */
    @Override
    public void create(PrmsBidOpeningCheckList prmsBidOpeningCheckList) {
        prmsBidOpeningCheckListFacade.create(prmsBidOpeningCheckList);

    }

    /**
     *
     * @param prmsBidOpeningCheckList
     * @return
     */
    @Override
    public PrmsBidOpeningCheckList getRequestNo(PrmsBidOpeningCheckList prmsBidOpeningCheckList) {
        return prmsBidOpeningCheckListFacade.getRequestNo(prmsBidOpeningCheckList);
    }

    @Override
    public List<HrEmployees> Employees() {
        return hrEmployeesFacade.findAll();
    }

    /**
     *
     * @param prmsBidOpeningCheckList
     * @return
     */
    @Override
    public ArrayList<PrmsBidOpeningCheckList> searchRequestForPayment(PrmsBidOpeningCheckList prmsBidOpeningCheckList) {
        return prmsBidOpeningCheckListFacade.searchRequestForPayment(prmsBidOpeningCheckList);
    }

    /**
     *
     * @return
     */
    @Override
    public PrmsBidOpeningCheckList getLastCheckListNo() {
        return prmsBidOpeningCheckListFacade.getLastCheckListNo();
    }

    /**
     *
     * @return
     */
    @Override
    public String getLastSIVNo() {
        return prmsBidOpeningCheckListFacade.getLastSIVNo();
    }

    /**
     *
     * @param prmsBidOpeningChecklstDt
     */
    @Override
    public void deleteContactPersonInfo(PrmsBidOpeningChecklstDt prmsBidOpeningChecklstDt) {
        prmsBidOpeningChecklstDtFacade.remove(prmsBidOpeningChecklstDt);
    }

    /**
     *
     * @param prmsBidOpeningCheckList
     * @return
     */
    @Override
    public List<PrmsBidOpeningCheckList> searchCheckList(PrmsBidOpeningCheckList prmsBidOpeningCheckList) {
        return prmsBidOpeningCheckListFacade.searchCheckList(prmsBidOpeningCheckList);
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<PrmsBid> BidNo() {
        return prmsBidFacade.BidNoForCheckList();
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<PrmsBidSubmission> BidNoListForCheckList() {
        return prmsBidSubmissionFacade.BidNoListForCheckList();
    }

    /**
     *
     * @param string
     * @return
     */
    @Override
    public ArrayList<PrmsBid> bidNo(String string) {
        return prmsBidFacade.getByRefNo(string);
    }

    /**
     *
     * @return
     */
    @Override
    public PrmsBidOpeningCheckList LastCheckListNo() {
        return prmsBidOpeningCheckListFacade.LastCheckListNo();

    }

    @Override
    public PrmsBid bidGetter(PrmsBid prmsBid) {
        return prmsBidFacade.getBidRe(prmsBid);
    }

    @Override
    public List<HrEmployees> getEmployeeNames() {
        return hrEmployeesFacade.getEmployeesName();
    }

    @Override
    public PrmsBidOpeningCheckList getSelectedRequest(BigDecimal id) {
        return prmsBidOpeningCheckListFacade.getSelectedRequest(id);
    }

    @Override
    public PrmsSupplyProfile vendourName(PrmsSupplyProfile supplyProfile) {
        return prmsSupplyProfileFacade.getBySuppId(supplyProfile);
    }

    @Override
    public ArrayList<ComLuCountry> getCountryName() {
        return comLuCountryFacade.getCountryName();
    }

    @Override
    public ArrayList<PrmsBidDetail> getLotNum() {
        return prmsBidFacade.getLotNum();
    }

    @Override
    public ArrayList<HrCommittees> getCommitee() {
        return hrCommitteesFacade.getCommitee();
    }

    @Override
    public List<PrmsBidSubmission> getBidNoList() {
        return prmsBidSubmissionFacade.getBidNoList();
    }

    @Override
    public List<PrmsBid> getBidNoListForCheckLIst() {
        return prmsBidFacade.getBidNoListForCheckLIst();
    }

    @Override
    public List<PrmsBidSubmissionDetail> getsupplierlist(String bidNo) {
        return submissionDetailFacade.getSubmList(bidNo);
    }

    @Override
    public List<PrmsBidDetail> getLotNo(String bidNo) {
        return prmsBidFacade.getLotNo(bidNo);
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getsupplierlist(PrmsBid bidNo) {
        return prmsSupplyProfileFacade.getsupplierlist(bidNo);
    }

    @Override
    public List<PrmsBidDetail> getBidListForLot(PrmsBid bidNo) {
        return prmsBidFacade.getBidListForLot(bidNo);
    }

    @Override
    public ArrayList<PrmsBid> BidNoForCheck() {
        return prmsBidFacade.BidNoForCheck();
    }

    @Override
    public HrCommittees getCommittee(HrCommittees hrCommittees) {
        return hrCommitteesFacade.getCommitteeId(hrCommittees);
    }

    @Override
    public List<HrCommitteeMembers> getCommitteeMembers(HrCommittees hrCommittees) {
        return hrCommitteeMembersFacade.getCommitteeMembersById(hrCommittees);
    }

    @Override
    public PrmsBidOpeningChecklstDt LastCheckListDtid() {
        return prmsBidOpeningChecklstDtFacade.LastCheckListDtid();
    }

    @Override
    public PrmsCheckListDetailforlot LastCheckListlotid() {
        return prmsBidOpeningCheckListFacade.LastCheckListlotid();
    }

    @Override
    public ArrayList<PrmsBidOpeningCheckList> searchBidderRegistration() {
        return prmsBidOpeningCheckListFacade.searchBidderRegistration();
    }

    @Override
    public List<PrmsSupplyProfile> findSuppliers() {
        return prmsSupplyProfileFacade.findSuppliers();
    }

    @Override
    public List<PrmsBidOpeningCheckList> getBidOpeningCheckListSearchParameterLst() {
        return prmsBidOpeningCheckListFacade.getBidOpeningCheckListSearchParameterLst();

    }

}
