/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.entity.MmsStorereqDetail;
import et.gov.eep.mms.entity.MmsServiceReg;
import et.gov.eep.prms.entity.PrmsMarketAssessment;
import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import et.gov.eep.prms.entity.PrmsMarketAssmntService;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PurchaseReqBeanLocal {

    public void save(PrmsPurchaseRequest eepPurchaseRequest);

    public void edit(PrmsPurchaseRequest eepPurchaseRequest);

    public ArrayList<PrmsPurchaseRequest> searchPurchaseReqNo(PrmsPurchaseRequest eepPurchaseRequest);

    public PrmsPurchaseRequest getPurchReqList(PrmsPurchaseRequest eepPurchaseRequest);

    public ArrayList<PrmsPurchaseRequest> getPurchReqNo();

    public List<MmsStorereq> getSrNo();

    public List<MmsStorereqDetail> getSrNo(String sRNo);

    public List<PrmsMarketAssessmentDetail> getItemName();

    public MmsItemRegistration getListOfItemName(String toString);

    public List<PrmsMarketAssmntService> getServiceNo(String sRNo);

    public List<PrmsMarketAssessment> getServiceNo();

    public List<MmsServiceReg> getServiceName();

    public List<PrmsPurchaseRequest> searchByPrNo(PrmsPurchaseRequest eepPurchaseRequest);

    public List<PrmsMarketAssmntService> getServName();

    public PrmsMarketAssessmentDetail findsrvcNam(String toString);

    public List<PrmsMarketAssessmentDetail> getItemNam();

    public List<PrmsMarketAssessment> getItemNameAss();

    public List<PrmsMarketAssessmentDetail> ItemName(String toString);

    public MmsItemRegistration ItemName(MmsItemRegistration toString);

    public PrmsPurchaseRequest getSelectedRequest(Long id);

    public PrmsPurchaseRequest getLastPrNo();

    public List<PrmsMarketAssmntService> getServNameNon();

    public List<PrmsMarketAssmntService> getworkNames();

    public PrmsMarketAssmntService workNameChanging(PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public PrmsMarketAssmntService getByServiceName(PrmsMarketAssmntService prmsMarketAssmntService);

    public PrmsMarketAssmntService getUP(PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public List<PrmsMarketAssessmentDetail> getFilteredItemByGLId(FmsOperatingBudgetDetail fmsOperatingBudgetDetail);

    public List<FmsOperatingBudgetDetail> getBudgetCodeListsByDepId(HrDepartments departments, FmsAccountingPeriod currentPeriod);

    public List<FmsOperatingBudgetDetail> getBudgetCodeListsByDepIdWhenWork(HrDepartments departments, FmsAccountingPeriod currentPeriod);

    public PrmsMarketAssmntService getServiceInfoByGL(FmsOperatingBudgetDetail fmsOperatingBudgetDetail);

    public PrmsMarketAssmntService getWorkInfoByGL(FmsOperatingBudgetDetail fmsOperatingBudgetDetail);

    public List<PrmsPurchaseRequest> getPurchaseReqOnLists();

    public List<MmsStorereq> getSrNoListsHavePrQuantity(int pr_QuantitySrDetail);

    public HrDepartments getdepName(String sRNo);

    public List<PrmsPurchaseRequest> getViewHistoryLists();

    public WfPrmsProcessed getWorkFlowInfoByPRId(PrmsPurchaseRequest eepPurchaseRequest);

    public String getPrDeptOrStoreSeqNo(String requisitorType);

    public List<PrmsPurchaseRequest> getParameterList();
}
