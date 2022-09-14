/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidSubmissionDetail;
import et.gov.eep.prms.entity.PrmsBidSubmission;
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
public interface BIdSubmissionBeanLocal {

    /**
     *
     * @param prmsBidSubmissionDetail
     */
    public void deleteSubmissionDetail(PrmsBidSubmissionDetail prmsBidSubmissionDetail);

    /**
     *
     * @param prmsBidSubmission
     */
    public void create(PrmsBidSubmission prmsBidSubmission);

    /**
     *
     * @param prmsBidSubmission
     */
    public void update(PrmsBidSubmission prmsBidSubmission);

    /**
     *
     * @return
     */
    public ArrayList<PrmsBid> bidNumberList();

    /**
     *
     * @param string
     * @return
     */
    public ArrayList<PrmsBid> ItemName(String string);

    /**
     *
     * @param prmsBidSubmission
     * @return
     */
    List<PrmsBidSubmission> searchBidSubmission(PrmsBidSubmission prmsBidSubmission);

    /**
     *
     * @param string
     * @return
     */
    public ArrayList<PrmsSupplyProfile> biddingCompanyAddress(String string);

    public PrmsBidSubmission LastCheckListNo();

    public PrmsBidSubmission getSelectedRequest(BigDecimal id);

    public List<PrmsBid> getBidNoList();

    public PrmsBidSubmission selectBidSubmissionByBidNo(PrmsBid bid);

    public List<PrmsSupplyProfile> getSupplierList(PrmsBid bid);

    public ArrayList<PrmsBidSubmission> searchBidSubmission();

    public List<PrmsBidSubmission> getParamNameList();

}
