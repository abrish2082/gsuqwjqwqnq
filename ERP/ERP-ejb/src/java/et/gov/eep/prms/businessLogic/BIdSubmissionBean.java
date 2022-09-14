/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidSubmission;
import et.gov.eep.prms.entity.PrmsBidSubmissionDetail;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsBidFacade;
import et.gov.eep.prms.mapper.PrmsBidSubmissionFacade;
import et.gov.eep.prms.mapper.PrmsBidSubmissionDetailFacade;
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
public class BIdSubmissionBean implements BIdSubmissionBeanLocal {

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private PrmsBidSubmissionFacade prmsBidSubmissionFacade;
    @EJB
    private PrmsBidSubmissionDetailFacade prmsBidSubmissionDetailFacade;
    @EJB
    PrmsBidFacade prmsBidFacade;
    @EJB
    PrmsSupplyProfileFacade prmsSupplyProfileFacade;
    //</editor-fold>

    /**
     *
     * @param prmsBidSubmissionDetail
     */
    @Override
    public void deleteSubmissionDetail(PrmsBidSubmissionDetail prmsBidSubmissionDetail) {
        prmsBidSubmissionDetailFacade.remove(prmsBidSubmissionDetail);
    }

    /**
     *
     * @param prmsBidSubmission
     */
    @Override
    public void create(PrmsBidSubmission prmsBidSubmission) {
        prmsBidSubmissionFacade.create(prmsBidSubmission);
    }

    /**
     *
     * @param prmsBidSubmission
     */
    @Override
    public void update(PrmsBidSubmission prmsBidSubmission) {
        prmsBidSubmissionFacade.edit(prmsBidSubmission);
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<PrmsBid> bidNumberList() {
        return prmsBidFacade.bidNumberList();

    }

    /**
     *
     * @param string
     * @return
     */
    @Override
    public ArrayList<PrmsBid> ItemName(String string) {
        return prmsBidFacade.getByRefNo(string);
    }

    /**
     *
     * @param prmsBidSubmission
     * @return
     */
    @Override
    public List<PrmsBidSubmission> searchBidSubmission(PrmsBidSubmission prmsBidSubmission) {
        return prmsBidSubmissionFacade.searchBidSubmission(prmsBidSubmission);
    }

    /**
     *
     * @param string
     * @return
     */
    @Override
    public ArrayList<PrmsSupplyProfile> biddingCompanyAddress(String string) {
        return prmsSupplyProfileFacade.biddingCompanyAddress(string);
    }

    @Override
    public PrmsBidSubmission LastCheckListNo() {
        return prmsBidSubmissionFacade.LastCheckListNo();
    }

    @Override
    public PrmsBidSubmission getSelectedRequest(BigDecimal id) {
        return prmsBidSubmissionFacade.getSelectedRequest(id);
    }

    @Override
    public List<PrmsBid> getBidNoList() {
        return prmsBidFacade.getBidNoLists();
    }

    @Override
    public PrmsBidSubmission selectBidSubmissionByBidNo(PrmsBid bid) {
        return prmsBidSubmissionFacade.selectBidSubmissionByBidNo(bid);
    }

    @Override
    public List<PrmsSupplyProfile> getSupplierList(PrmsBid bid) {
        return prmsSupplyProfileFacade.getSupplierList(bid);
    }

    @Override
    public ArrayList<PrmsBidSubmission> searchBidSubmission() {
        return prmsBidSubmissionFacade.searchBidSubmission();
    }

    @Override
    public List<PrmsBidSubmission> getParamNameList() {
       return prmsBidSubmissionFacade.getParamNameList();
    }

}
