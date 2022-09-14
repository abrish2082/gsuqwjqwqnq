package et.gov.eep.prms.businessLogic;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import et.gov.eep.prms.entity.PrmsPurOrderDetail;
import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.mapper.PrmsPurchaseOrderFacade;
import et.gov.eep.prms.mapper.PrmsBidFacade;
import et.gov.eep.prms.mapper.PrmsAwardDetailFacade;
import et.gov.eep.prms.mapper.PrmsPurOrderDetailFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PurchaseOrderBean implements PurchaseOrderBeanLocal {

    @EJB
    private PrmsPurOrderDetailFacade prmsPurOrderDetailFacade;
    @EJB
    private PrmsPurchaseOrderFacade prmsPurchaseOrderFacade;
    @EJB
    private PrmsBidFacade prmsBidFacade;
    @EJB
    private PrmsSupplyProfileFacade supplyProfileFacade;
    @EJB
    PrmsAwardDetailFacade prmsAwardDetailFacade;

    @Override
    public ArrayList<PrmsPurchaseOrder> searchPurchaseOrderByPoNo(PrmsPurchaseOrder papmsPurchaseOrder) {
        return prmsPurchaseOrderFacade.searchPurchaseOrderByPoNo(papmsPurchaseOrder);
    }

    @Override
    public void update(PrmsPurchaseOrder papmsPurchaseOrder) {
        prmsPurchaseOrderFacade.edit(papmsPurchaseOrder);
    }

    @Override
    public void create(PrmsPurchaseOrder papmsPurchaseOrder) {
        prmsPurchaseOrderFacade.create(papmsPurchaseOrder);
    }

    @Override
    public PrmsPurchaseOrder getPoId(PrmsPurchaseOrder papmsPurchaseOrder) {

        return prmsPurchaseOrderFacade.getPoId(papmsPurchaseOrder);
    }

    @Override
    public List<String> getApprovedWithBidPurchaseTypePRList(String status) {
        return prmsPurchaseOrderFacade.getApprovedWithBidPurchaseTypePRList(status);
    }

    @Override
    public PrmsAward SearchAwardByAwardId(String papmsAward) {
        return prmsPurchaseOrderFacade.SearchAwardByAwardId(papmsAward);
    }

    @Override
    public List<PrmsAward> getApprovedWithBidPurchaseTypePRListt(PrmsAward papmsAward) {
        return prmsPurchaseOrderFacade.getApprovedWithBidPurchaseTypePRList(papmsAward);
    }

    @Override
    public ArrayList<PrmsAward> getInspectionListByStatus(PrmsAward papmsAward) {
        return prmsPurchaseOrderFacade.getInspectionListByStatus(papmsAward);
    }

    @Override
    public List<PrmsBid> findBidNo() {
        return prmsBidFacade.findAll();
    }

    @Override
    public List<PrmsSupplyProfile> VendorName() {
        return supplyProfileFacade.findAll();
    }

    @Override
    public List<PrmsAward> getAwardLists() {
        return prmsPurchaseOrderFacade.getAwardLists();
    }

    @Override
    public List<PrmsAwardDetail> getAwardDetailLists(String AwardNo) {
        return prmsAwardDetailFacade.getAwardDetailList(AwardNo);

    }

    @Override
    public List<HrDepartments> searchdeptName() {
        return prmsPurchaseOrderFacade.searchdeptName();
    }

    @Override
    public ArrayList<PrmsBid> bidNumberList() {
        return prmsPurchaseOrderFacade.bidNumberList();
    }

    @Override
    public void deletePurchaseOredeDtail(PrmsPurOrderDetail prmsPurOrderDetail) {
        prmsPurOrderDetailFacade.remove(prmsPurOrderDetail);
    }

    @Override
    public List<PrmsPurchaseOrder> searchPurchaseOrder(PrmsPurchaseOrder prmsPurchaseOrder) {
        return prmsPurchaseOrderFacade.searchPurchaseOrder(prmsPurchaseOrder);
    }

    @Override
    public PrmsPurchaseOrder getLastPaymentNo() {
        return prmsPurchaseOrderFacade.getLastPaymentNo();
    }

    @Override
    public PrmsPurchaseOrder getSelectedRequest(String id) {
        return prmsPurchaseOrderFacade.getSelectedRequest(id);
    }

    @Override
    public ArrayList<PrmsBid> BidNoFormContract() {
        return prmsPurchaseOrderFacade.BidNoFormContract();
    }

    @Override
    public List<PrmsQuotation> quotationFromResult() {
        return prmsPurchaseOrderFacade.quotationFromResult();
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getsupplierNameFromQuotation(String quotationNo) {
        return prmsPurchaseOrderFacade.getsupplierNameFromQuotation(quotationNo);
    }

    @Override
    public List<PrmsFinancialEvlResultyDtl> supplierName(PrmsSupplyProfile eepVendorReg) {
        return prmsPurchaseOrderFacade.getSupplierName(eepVendorReg);
    }

    @Override
    public PrmsBid getBidType(String bidType) {
        return prmsPurchaseOrderFacade.getBidTyp(bidType);
    }

    @Override
    public List<PrmsPurchaseRequest> getPrNo(String prmsQuotation) {
        return prmsPurchaseOrderFacade.getPrNum(prmsQuotation);
    }

    @Override
    public List<HrDepartments> getDeptName(String depName) {
        return prmsPurchaseOrderFacade.getDeptName(depName);
    }

    @Override
    public List<PrmsContractDetail> getItemList(BigDecimal itemName) {
        return prmsPurchaseOrderFacade.getItemName(itemName);
    }

    @Override
    public List<PrmsPurchaseOrder> getPOReauested() {
        return prmsPurchaseOrderFacade.getPOReauested();
    }
    
    public List<PrmsPurchaseOrder> findPreparedPoNumbers() {
    return prmsPurchaseOrderFacade.findPreparedPoNumbers();
    }

    @Override
    public List<PrmsPurchaseOrder> searchPurchaseOrderNo(PrmsPurchaseOrder prmsPurchaseOrder) {
        return prmsPurchaseOrderFacade.searchPurchaseOrderNo( prmsPurchaseOrder);
    }
    
    @Override
    public List<PrmsPurchaseOrder> searchPurchaseOrderNo(int status,int UserId){
        return prmsPurchaseOrderFacade.searchPurchaseOrderNo( status, UserId);
    }

    @Override
    public List<PrmsContractAmendment> checkContractIdFromAmended(PrmsContract prmsContract) {
      return prmsPurchaseOrderFacade.checkContractIdFromAmended(prmsContract);  
    }

    @Override
    public PrmsContractAmendment getContractAmendedInfoByContractId(PrmsContract prmsContract) {
      return prmsPurchaseOrderFacade.getContractAmendedInfoByContractId(prmsContract);  
    }

     @Override
    public List<PrmsBidAmend> checkBidFromAmended(PrmsBid prmsBid) {
        return prmsPurchaseOrderFacade.checkBidFromAmended(prmsBid);
    }

    @Override
    public PrmsBidAmend getBidAmendInfoByBidId(PrmsBidAmend prmsBidAmend) {
        return prmsPurchaseOrderFacade.getBidAmendInfoByBidId(prmsBidAmend);
    }

    @Override
    public List<PrmsPurchaseOrder> getParamNameList() {
        return prmsPurchaseOrderFacade.getParamNameList();
    }
}
