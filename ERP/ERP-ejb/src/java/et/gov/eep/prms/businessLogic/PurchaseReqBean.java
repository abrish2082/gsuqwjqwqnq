/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.mapper.WfPrmsProcessedFacade;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.entity.MmsStorereqDetail;
import et.gov.eep.mms.mapper.MmsStorereqFacade;
import et.gov.eep.mms.entity.MmsServiceReg;
import et.gov.eep.prms.entity.PrmsMarketAssessment;
import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import et.gov.eep.prms.entity.PrmsMarketAssmntService;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.mapper.PrmsPurchaseRequestFacade;
import et.gov.eep.fcms.mapper.budget.FmsOperatingBudgetDetailFacade;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.mms.mapper.MmsStorereqDetailFacade;
import et.gov.eep.prms.entity.PrmsPurchaseReqService;
import et.gov.eep.prms.mapper.PrmsMarketAssessmentDetailFacade;
import et.gov.eep.prms.mapper.PrmsMarketAssessmentFacade;
import et.gov.eep.prms.mapper.PrmsMarketAssmntServiceFacade;
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
public class PurchaseReqBean implements PurchaseReqBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsPurchaseRequestFacade eepPurchaseRequestFacade;
    @EJB
    MmsStorereqFacade mmsStorereqFacade;
    @EJB
    MmsStorereqDetailFacade mmsStorereqDetailFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    @EJB
    FmsOperatingBudgetDetailFacade fmsOperatingBudgetDetailFacade;
    @EJB
    WfPrmsProcessedFacade wfPrmsProcessedFacade;
    @EJB
    PrmsMarketAssessmentFacade prmsMarketAssessmentFacade;
    @EJB
    PrmsMarketAssmntServiceFacade prmsMarketAssmntServiceFacade;
    @EJB
    PrmsMarketAssessmentDetailFacade prmsMarketAssessmentDetailFacade;
    @EJB
    HrDepartmentsFacade hrDepartmentsFacade;
// </editor-fold>

    @Override
    public void save(PrmsPurchaseRequest eepPurchaseRequest) {
        eepPurchaseRequestFacade.create(eepPurchaseRequest);
    }

    @Override
    public void edit(PrmsPurchaseRequest eepPurchaseRequest) {
        eepPurchaseRequestFacade.edit(eepPurchaseRequest);
    }

    @Override
    public ArrayList<PrmsPurchaseRequest> searchPurchaseReqNo(PrmsPurchaseRequest eepPurchaseRequest) {
        return eepPurchaseRequestFacade.searchPurchaseReNo(eepPurchaseRequest);
    }

    @Override
    public PrmsPurchaseRequest getPurchReqList(PrmsPurchaseRequest eepPurchaseRequest) {
        return eepPurchaseRequestFacade.searchEvent(eepPurchaseRequest);
    }

    @Override
    public ArrayList<PrmsPurchaseRequest> getPurchReqNo() {
        return eepPurchaseRequestFacade.getPrReNo();
    }

    @Override
    public List<MmsStorereq> getSrNo() {
        return mmsStorereqFacade.getSrNo();
    }

    @Override
    public List<MmsStorereqDetail> getSrNo(String sRNo) {
        return mmsStorereqDetailFacade.getSrNo(sRNo);
    }

    @Override
    public List<PrmsMarketAssessmentDetail> getItemName() {
        return prmsMarketAssessmentDetailFacade.getItemNameLists();
    }

    @Override
    public MmsItemRegistration getListOfItemName(String toString) {
        return eepPurchaseRequestFacade.getListOfItemName(toString);
    }

    @Override
    public List<PrmsMarketAssmntService> getServiceNo(String sRNo) {
        return eepPurchaseRequestFacade.getserviceNo(sRNo);
    }

    @Override
    public List<PrmsMarketAssessment> getServiceNo() {
        return eepPurchaseRequestFacade.getServNo();
    }

    @Override
    public List<MmsServiceReg> getServiceName() {
        return eepPurchaseRequestFacade.getServiceName();
    }

    @Override
    public List<PrmsPurchaseRequest> searchByPrNo(PrmsPurchaseRequest eepPurchaseRequest) {
        return eepPurchaseRequestFacade.searchByPrNo(eepPurchaseRequest);
    }

    @Override
    public List<PrmsMarketAssmntService> getServName() {
        return prmsMarketAssmntServiceFacade.getConsultancyServiceNameLists();
    }

    @Override
    public PrmsMarketAssessmentDetail findsrvcNam(String servicename) {
        return null;
    }

    @Override
    public List<PrmsMarketAssessment> getItemNameAss() {
        return prmsMarketAssessmentFacade.getItemNameAss();
    }

    @Override
    public List<PrmsMarketAssessmentDetail> getItemNam() {
        return prmsMarketAssessmentDetailFacade.getItemNameLists();
    }

    @Override
    public MmsItemRegistration ItemName(MmsItemRegistration itemName) {
        return null;
//        return eepPurchaseRequestFacade.getItemName(itemName);
    }

    @Override
    public List<PrmsMarketAssessmentDetail> ItemName(String itemName) {
        return eepPurchaseRequestFacade.getItemName(itemName);
    }

    @Override
    public PrmsPurchaseRequest getSelectedRequest(Long id) {
        return eepPurchaseRequestFacade.getIdNo(id);
    }

    @Override
    public PrmsPurchaseRequest getLastPrNo() {
        return eepPurchaseRequestFacade.getprNoLast();
    }

    @Override
    public List<PrmsMarketAssmntService> getServNameNon() {
        return prmsMarketAssmntServiceFacade.getNonConsultancyServiceNameLists();
    }

    @Override
    public List<PrmsMarketAssmntService> getworkNames() {
        return prmsMarketAssmntServiceFacade.getWorkNameLists();
    }

    @Override
    public PrmsMarketAssmntService workNameChanging(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return eepPurchaseRequestFacade.newSelectedWorkName(prmsServiceAndWorkReg);
    }

    @Override
    public PrmsMarketAssmntService getByServiceName(PrmsMarketAssmntService prmsMarketAssmntService) {
        return eepPurchaseRequestFacade.getOthersBySerName(prmsMarketAssmntService);
    }

    @Override
    public PrmsMarketAssmntService getUP(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return eepPurchaseRequestFacade.getUP(prmsServiceAndWorkReg);
    }

    @Override
    public List<PrmsMarketAssessmentDetail> getFilteredItemByGLId(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        return prmsMarketAssessmentDetailFacade.getFilteredItemByGLId(fmsOperatingBudgetDetail);
    }

    @Override
    public List<FmsOperatingBudgetDetail> getBudgetCodeListsByDepId(HrDepartments departments, FmsAccountingPeriod currentPeriod) {
        return fmsOperatingBudgetDetailFacade.fetchByDepartmentandCurrenPerid(departments, currentPeriod);
    }

    @Override
    public List<FmsOperatingBudgetDetail> getBudgetCodeListsByDepIdWhenWork(HrDepartments departments, FmsAccountingPeriod currentPeriod) {
        return fmsOperatingBudgetDetailFacade.getBudgetCodeListsByDepIdWhenWork(departments, currentPeriod);
    }

    @Override
    public PrmsMarketAssmntService getServiceInfoByGL(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        return prmsMarketAssmntServiceFacade.getServiceInfoByGL(fmsOperatingBudgetDetail);
    }

    @Override
    public PrmsMarketAssmntService getWorkInfoByGL(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        return prmsMarketAssmntServiceFacade.getWorkInfoByGL(fmsOperatingBudgetDetail);
    }

    @Override
    public List<PrmsPurchaseRequest> getPurchaseReqOnLists() {
        return eepPurchaseRequestFacade.getPurchaseReqOnLists();
    }

    @Override
    public List<MmsStorereq> getSrNoListsHavePrQuantity(int pr_QuantitySrDetail) {
        return mmsStorereqFacade.getSrNoListsHavePrQuantity(pr_QuantitySrDetail);
    }

    @Override
    public HrDepartments getdepName(String sRNo) {
        return hrDepartmentsFacade.getDepNameBySrNo(sRNo);
    }

    @Override
    public List<PrmsPurchaseRequest> getViewHistoryLists() {
        return eepPurchaseRequestFacade.getViewHistoryLists();
    }

    @Override
    public WfPrmsProcessed getWorkFlowInfoByPRId(PrmsPurchaseRequest eepPurchaseRequest) {
        return wfPrmsProcessedFacade.getWorkFlowInfoByPRId(eepPurchaseRequest);
    }

    @Override
    public String getPrDeptOrStoreSeqNo(String requisitorType) {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String prDept_Store_No = null;
        String prefix = null;
        int maxNo = 0;
        if (requisitorType.equals("department")) {
            prefix = "PR-Wu-No";
        } else if (requisitorType.equals("sr")) {
            prefix = "PR-Store-No";
        }
        List<PrmsPurchaseRequest> purchaseRequestLists = eepPurchaseRequestFacade.getPrDeptOrStoreSeqNo(prefix, eYear);
        for (int k = 0; k < purchaseRequestLists.size(); k++) {
            prDept_Store_No = purchaseRequestLists.get(k).getPrNumber();
            String[] lastInspNos = prDept_Store_No.split("-");
            String lastDatesPatern = lastInspNos[3];
            System.out.println("1 " + lastDatesPatern);
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increment = Integer.parseInt(lastDatesPaterns[0]);
            System.out.println("increment " + increment);
            if (maxNo < increment) {
                maxNo = increment;
            }
        }
        maxNo = maxNo + 1;
        prDept_Store_No = (prefix + "-" + maxNo + "/" + eYear);
        return prDept_Store_No;
    }

    @Override
    public List<PrmsPurchaseRequest> getParameterList() {
        return eepPurchaseRequestFacade.getParamNameList();
    }
}