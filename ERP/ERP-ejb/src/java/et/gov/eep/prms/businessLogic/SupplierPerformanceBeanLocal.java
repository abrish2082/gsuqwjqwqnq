/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsPaymentRequisition;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsSupplierPerformanceInfo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface SupplierPerformanceBeanLocal {

    public void update(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo);

    public void create(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo);

    List<PrmsSupplierPerformanceInfo> searchCheckList(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo);

    public ArrayList<PrmsSupplierPerformanceInfo> searchRequestForPayment(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo);

    public PrmsSupplierPerformanceInfo getRequestNo(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo);

    public ArrayList<PrmsPurchaseOrder> purchaseOrderList();

    public PrmsSupplierPerformanceInfo getSelectedRequest(String id);

    public PrmsSupplierPerformanceInfo getLastPaymentNo();

    public List<PrmsQuotationDetail> getQuotationList(String QuotationNo);

    public List<PrmsPurchaseOrder> getPoNos(PrmsQuotation prmsQuotation);

    public List<PrmsPurchaseOrder> getPoNumberList(PrmsContract prmsCotract);

    public List<PrmsContract> getContratList2(PrmsBid prmsBid);

    public PrmsSupplierPerformanceInfo getLastPerformanceId();

//    public List<PrmsSupplierPerformanceInfo> getSupPerfRqLists();
    public List<PrmsSupplierPerformanceInfo> getPerformanceLists();

    public List<PrmsSupplierPerformanceInfo> searchByPerformanceNO(PrmsSupplierPerformanceInfo prmsSupplierPerformanceInfo);

    public PrmsContract getOthersByContractId(PrmsContract prmsCotract);

    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsCotract);

    public PrmsContractAmendment getContractAmendment(PrmsContract prmsCotract);

    public List<PrmsBidAmend> checkBidFromAmended(PrmsBid prmsBid);

    public PrmsBidAmend getBidAmendInfoByBidId(PrmsBidAmend prmsBidAmend);

    public List<PrmsSupplierPerformanceInfo> getParamNameList();

}
