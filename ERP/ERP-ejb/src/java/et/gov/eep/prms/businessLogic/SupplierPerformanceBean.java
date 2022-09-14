/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsSupplierPerformanceInfo;
import et.gov.eep.prms.mapper.PrmsPurchaseOrderFacade;
import et.gov.eep.prms.mapper.PrmsSupplierPerformanceInfoFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class SupplierPerformanceBean implements SupplierPerformanceBeanLocal {

    @EJB
    private PrmsSupplierPerformanceInfoFacade prmsSupplierPerformanceInfoFacade;
    @EJB
    PrmsPurchaseOrderFacade prmsPurchaseOrderFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;

    @Override
    public void update(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo) {
        prmsSupplierPerformanceInfoFacade.edit(prmsSupplierPerformanceInfo);

    }

    @Override
    public void create(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo) {
        prmsSupplierPerformanceInfoFacade.create(prmsSupplierPerformanceInfo);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public ArrayList<PrmsSupplierPerformanceInfo> searchRequestForPayment(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo) {
        return prmsSupplierPerformanceInfoFacade.searchRequestForPayment(prmsSupplierPerformanceInfo);
    }

    @Override
    public PrmsSupplierPerformanceInfo getRequestNo(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo) {
        return prmsSupplierPerformanceInfoFacade.getRequestNo(prmsSupplierPerformanceInfo);
    }

    @Override
    public ArrayList<PrmsPurchaseOrder> purchaseOrderList() {
        return prmsSupplierPerformanceInfoFacade.purchaseOrderList();
    }

    @Override
    public List<PrmsSupplierPerformanceInfo> searchCheckList(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo) {
        return prmsSupplierPerformanceInfoFacade.searchCheckList(prmsSupplierPerformanceInfo);
    }

    @Override
    public PrmsSupplierPerformanceInfo getSelectedRequest(String id) {
        return prmsSupplierPerformanceInfoFacade.getSelectedRequest(id);
    }

    @Override
    public PrmsSupplierPerformanceInfo getLastPaymentNo() {
        return prmsSupplierPerformanceInfoFacade.getLastPaymentNo();
    }

    @Override
    public List<PrmsQuotationDetail> getQuotationList(String QuotationNo) {
        return prmsSupplierPerformanceInfoFacade.getQuotationList(QuotationNo);
    }

    @Override
    public List<PrmsPurchaseOrder> getPoNos(PrmsQuotation prmsQuotation) {
        return prmsPurchaseOrderFacade.findAll();
    }

    @Override
    public List<PrmsPurchaseOrder> getPoNumberList(PrmsContract prmsCotract) {
        return prmsSupplierPerformanceInfoFacade.getPoNumberList(prmsCotract);
    }

    @Override
    public List<PrmsContract> getContratList2(PrmsBid prmsBid) {
        return prmsSupplierPerformanceInfoFacade.getContratList2(prmsBid);
    }

    @Override
    public PrmsSupplierPerformanceInfo getLastPerformanceId() {
        return prmsSupplierPerformanceInfoFacade.getLastPerformanceId();
    }

//    @Override
//    public List<PrmsSupplierPerformanceInfo> getSupPerfRqLists() {
//        return prmsSupplierPerformanceInfoFacade.getSupPerfRqLists();
//    }
    @Override
    public List<PrmsSupplierPerformanceInfo> getPerformanceLists() {
        return prmsSupplierPerformanceInfoFacade.getPerformanceLst();
    }

    @Override
    public List<PrmsSupplierPerformanceInfo> searchByPerformanceNO(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo) {
        return prmsSupplierPerformanceInfoFacade.getPerformNos(prmsSupplierPerformanceInfo);
    }

    @Override
    public PrmsContract getOthersByContractId(PrmsContract prmsCotract) {
        return prmsSupplierPerformanceInfoFacade.getOthersByContractId(prmsCotract);
    }

    @Override
    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsCotract) {
        return prmsSupplierPerformanceInfoFacade.checkAmendOrNot(prmsCotract);
    }

    @Override
    public PrmsContractAmendment getContractAmendment(PrmsContract prmsCotract) {
        return prmsSupplierPerformanceInfoFacade.getContractAmendment(prmsCotract);
    }

    @Override
    public List<PrmsBidAmend> checkBidFromAmended(PrmsBid prmsBid) {
        return prmsSupplierPerformanceInfoFacade.checkBidFromAmended(prmsBid);
    }

    @Override
    public PrmsBidAmend getBidAmendInfoByBidId(PrmsBidAmend prmsBidAmend) {
        return prmsSupplierPerformanceInfoFacade.getBidAmendInfoByBidId(prmsBidAmend);
    }

    @Override
    public List<PrmsSupplierPerformanceInfo> getParamNameList() {
         return prmsSupplierPerformanceInfoFacade.getParamNameList();
    }
}
