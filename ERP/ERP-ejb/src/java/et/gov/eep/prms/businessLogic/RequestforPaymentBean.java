/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsPaymentRequisition;
import et.gov.eep.fcms.entity.bank.FmsBank;
import et.gov.eep.prms.mapper.PrmsPaymentRequisitionFacade;
import et.gov.eep.fcms.mapper.bank.FmsBankFacade;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.mapper.PrmsContractFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author user
 */
@Stateless
public class RequestforPaymentBean implements RequestforPaymentBeanLocal {

    @EJB
    private PrmsPaymentRequisitionFacade prmsPaymentRequisitionFacade;
    @EJB
    private FmsBankFacade fmsBankFacade;

    @EJB
    private PrmsContractFacade prmsContractFacade;
    @Inject
    MmsInspection mmsInspection;

    @Override
    public ArrayList<PrmsPaymentRequisition> searchRequestForPayment(PrmsPaymentRequisition prmsPaymentRequisition) {
        return prmsPaymentRequisitionFacade.searchRequestForPayment(prmsPaymentRequisition);
    }

    @Override
    public void update(PrmsPaymentRequisition prmsPaymentRequisition) {
        prmsPaymentRequisitionFacade.edit(prmsPaymentRequisition);
    }

    @Override
    public void create(PrmsPaymentRequisition prmsPaymentRequisition) {
        prmsPaymentRequisitionFacade.create(prmsPaymentRequisition);

    }

    @Override
    public PrmsPaymentRequisition getRequestNo(PrmsPaymentRequisition prmsPaymentRequisition) {
        return prmsPaymentRequisitionFacade.getRequestNo(prmsPaymentRequisition);
    }

    @Override
    public List<FmsBank> VendorName() {
        return fmsBankFacade.findAll();
    }

    @Override
    public List<PrmsPaymentRequisition> searchCheckList(PrmsPaymentRequisition prmsPaymentRequisition) {
        return prmsPaymentRequisitionFacade.searchCheckList(prmsPaymentRequisition);
    }

    @Override
    public PrmsPaymentRequisition getSelectedRequest(Integer id) {
        return prmsPaymentRequisitionFacade.getSelectedRequest(id);
    }

    @Override
    public PrmsPaymentRequisition getLastPaymentNo() {
        return prmsPaymentRequisitionFacade.getLastPaymentNo();
    }

    @Override
    public List<PrmsPaymentRequisition> getPrmsPaymentRequisitions() {
        return prmsPaymentRequisitionFacade.getPrmsPaymentRequisitions();
    }

    @Override
    public PrmsPaymentRequisition getPrmsPaymentReq(String refNo_) {
        return prmsPaymentRequisitionFacade.getPrmsPaymentReq(refNo_);
    }

    @Override
    public List<PrmsQuotation> getProformaNo(int approvedStatus) {
        return prmsPaymentRequisitionFacade.getProformaNo(approvedStatus);
    }

    @Override
    public List<PrmsContract> getContractNo(PrmsBid prmsBid, int status) {
        return prmsPaymentRequisitionFacade.getContractNo(prmsBid, status);
    }

    @Override
    public List<PrmsPurchaseOrder> getPONumber(PrmsQuotation prmsQuotation, int status) {
        return prmsPaymentRequisitionFacade.getPONumberList(prmsQuotation, status);
    }

    @Override
    public List<PrmsBidDetail> getPRNumber(PrmsBid prmsBid) {
        return prmsPaymentRequisitionFacade.getPRNumbers(prmsBid);
    }

    @Override
    public List<MmsStoreInformation> getStoreNameLists() {
        return prmsPaymentRequisitionFacade.getAllStoreName();
    }

    @Override
    public List<MmsGrn> getGrnNoByStoreId(MmsStoreInformation mmsStoreInformation) {
        return prmsPaymentRequisitionFacade.getGrnNoByStoreId(mmsStoreInformation);
    }

    @Override
    public List<MmsGrn> getPoNoByGrnNo(MmsGrn mmsGrn) {
        return prmsPaymentRequisitionFacade.getPoNoByGrnNo(mmsGrn);
    }

    @Override
    public List<PrmsBid> getBidContractedOnly(int approvedStatus) {
        return prmsPaymentRequisitionFacade.getBidContractedOnly(approvedStatus);
    }

    @Override
    public List< MmsGrn> getGrnNo(MmsInspection mmsInspection) {
        return prmsPaymentRequisitionFacade.getGrnNo(mmsInspection);
    }

    @Override
    public List<PrmsPaymentRequisition> getRequestsOnLists() {
        return prmsPaymentRequisitionFacade.getRequestsOnLists();
    }

    @Override
    public List<PrmsBidAmend> checkBidFromAmended(PrmsBid prmsBid) {
        return prmsPaymentRequisitionFacade.checkBidFromAmended(prmsBid);
    }

    @Override
    public PrmsBidAmend getBidAmendInfoByBidId(PrmsBidAmend prmsBidAmend) {
        return prmsPaymentRequisitionFacade.getBidAmendInfoByBidId(prmsBidAmend);
    }

    @Override
    public List<PrmsContractAmendment> checkContractIdFromAmended(PrmsContract prmsContract) {
        return prmsPaymentRequisitionFacade.checkContractIdFromAmended(prmsContract);
    }

    @Override
    public PrmsContractAmendment getContractAmendedInfoByContractId(PrmsContract prmsContract) {
        return prmsPaymentRequisitionFacade.getContractAmendedInfoByContractId(prmsContract);
    }

    @Override
    public List<PrmsContractCurrencyDetail> getContractAmoutList(PrmsContract prmsContract) {
        return prmsPaymentRequisitionFacade.getContractAmoutList(prmsContract);
    }

    @Override
    public List<String> getAmoutList(PrmsContract prmsContract) {
        return prmsPaymentRequisitionFacade.getAmoutList(prmsContract);
    }

    @Override
    public List<PrmsPaymentRequisition> getColumnNameList() {
        return prmsPaymentRequisitionFacade.getColumnNameList();
    }

}
