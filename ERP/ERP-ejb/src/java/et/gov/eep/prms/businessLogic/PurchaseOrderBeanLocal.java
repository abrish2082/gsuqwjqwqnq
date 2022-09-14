/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import et.gov.eep.prms.entity.PrmsPurOrderDetail;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsQuotation;
import java.math.BigDecimal;
//
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PurchaseOrderBeanLocal {

    public ArrayList<PrmsPurchaseOrder> searchPurchaseOrderByPoNo(PrmsPurchaseOrder papmsPurchaseOrder);

    public void update(PrmsPurchaseOrder papmsPurchaseOrder);

    public void create(PrmsPurchaseOrder papmsPurchaseOrder);

    public PrmsPurchaseOrder getPoId(PrmsPurchaseOrder papmsPurchaseOrder);

    List<String> getApprovedWithBidPurchaseTypePRList(String status);

    PrmsAward SearchAwardByAwardId(String papmsAward);

    public List<PrmsAward> getApprovedWithBidPurchaseTypePRListt(PrmsAward papmsAward);

    public ArrayList<PrmsAward> getInspectionListByStatus(PrmsAward papmsAward);

    public List<PrmsBid> findBidNo();

    public List<PrmsSupplyProfile> VendorName();

    public List<PrmsAward> getAwardLists();

    public List<PrmsAwardDetail> getAwardDetailLists(String AwardNo);

    public List<HrDepartments> searchdeptName();

    public ArrayList<PrmsBid> bidNumberList();

    public void deletePurchaseOredeDtail(PrmsPurOrderDetail prmsPurOrderDetail);

    public List<PrmsPurchaseOrder> searchPurchaseOrder(PrmsPurchaseOrder prmsPurchaseOrder);

    public PrmsPurchaseOrder getLastPaymentNo();

    public PrmsPurchaseOrder getSelectedRequest(String id);

    public ArrayList<PrmsBid> BidNoFormContract();

    public List<PrmsQuotation> quotationFromResult();

    public ArrayList<PrmsSupplyProfile> getsupplierNameFromQuotation(String quotationNo);

    public List<PrmsFinancialEvlResultyDtl> supplierName(PrmsSupplyProfile eepVendorReg);

    public PrmsBid getBidType(String toString);

    public List<PrmsPurchaseRequest> getPrNo(String prmsQuotation);

    public List<HrDepartments> getDeptName(String toString);

    public List<PrmsContractDetail> getItemList(BigDecimal itemName);

    public List<PrmsPurchaseOrder> getPOReauested();
    
    public List<PrmsPurchaseOrder> searchPurchaseOrderNo(PrmsPurchaseOrder prmsPurchaseOrder);
    
    public List<PrmsPurchaseOrder> searchPurchaseOrderNo(int status,int UserId);
    
    public List<PrmsPurchaseOrder> findPreparedPoNumbers();

    public List<PrmsContractAmendment> checkContractIdFromAmended(PrmsContract prmsContract);

    public PrmsContractAmendment getContractAmendedInfoByContractId(PrmsContract prmsContract);

    public List<PrmsBidAmend> checkBidFromAmended(PrmsBid eepBidReg);

    public PrmsBidAmend getBidAmendInfoByBidId(PrmsBidAmend prmsBidAmend);

    public List<PrmsPurchaseOrder> getParamNameList();
}
