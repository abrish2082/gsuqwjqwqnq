/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.fcms.entity.bank.FmsBank;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import et.gov.eep.prms.entity.PrmsPaymentRequisition;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsQuotation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface RequestforPaymentBeanLocal {

    /**
     *
     * @param prmsPaymentRequisition
     * @return
     */
    List<PrmsPaymentRequisition> searchCheckList(PrmsPaymentRequisition prmsPaymentRequisition);

    public ArrayList<PrmsPaymentRequisition> searchRequestForPayment(PrmsPaymentRequisition prmsPaymentRequisition);

    /**
     *
     * @param prmsPaymentRequisition
     */
    public void update(PrmsPaymentRequisition prmsPaymentRequisition);

    /**
     *
     * @param prmsPaymentRequisition
     */
    public void create(PrmsPaymentRequisition prmsPaymentRequisition);

    /**
     *
     * @param prmsPaymentRequisition
     * @return
     */
    public PrmsPaymentRequisition getRequestNo(PrmsPaymentRequisition prmsPaymentRequisition);

    /**
     *
     * @return
     */
    public List<FmsBank> VendorName();
//           public List<HrDepartments>searchdeptName();
//           public ArrayList<HrDepartments> depList();

    public PrmsPaymentRequisition getSelectedRequest(Integer id);

    public PrmsPaymentRequisition getLastPaymentNo();

    public List<PrmsPaymentRequisition> getPrmsPaymentRequisitions();

    public PrmsPaymentRequisition getPrmsPaymentReq(String refNo_);

    public List<PrmsQuotation> getProformaNo(int approvedStatus);

    public List<PrmsContract> getContractNo(PrmsBid prmsBid, int status);

    public List<PrmsPurchaseOrder> getPONumber(PrmsQuotation prmsQuotation, int status);

    public List<PrmsBidDetail> getPRNumber(PrmsBid prmsBid);

    public List<MmsStoreInformation> getStoreNameLists();

    public List<MmsGrn> getGrnNoByStoreId(MmsStoreInformation mmsStoreInformation);

    public List<MmsGrn> getPoNoByGrnNo(MmsGrn mmsGrn);

    public List<PrmsBid> getBidContractedOnly(int approvedStatus);

    public List<MmsGrn> getGrnNo(MmsInspection mmsInspection);

    public List<PrmsPaymentRequisition> getRequestsOnLists();

    public List<PrmsBidAmend> checkBidFromAmended(PrmsBid prmsBid);

    public PrmsBidAmend getBidAmendInfoByBidId(PrmsBidAmend prmsBidAmend);

    public List<PrmsContractAmendment> checkContractIdFromAmended(PrmsContract prmsContract);

    public PrmsContractAmendment getContractAmendedInfoByContractId(PrmsContract prmsContract);

    public List<PrmsContractCurrencyDetail> getContractAmoutList(PrmsContract prmsContract);

    public List<String> getAmoutList(PrmsContract prmsContract);

    public List<PrmsPaymentRequisition> getColumnNameList();
}
