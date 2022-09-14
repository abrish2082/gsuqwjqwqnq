/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.pms.entity.PmsResources;
import et.gov.eep.prms.entity.PrmsAnnualPlanService;
import et.gov.eep.prms.entity.PrmsAward;
//import et.gov.eep.mms.entity.PapmsItemRegistration;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidCriteria;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsPurchasePlan;
import et.gov.eep.prms.entity.PrmsPurchasePlnDetail;
import et.gov.eep.prms.entity.PrmsPurchaseReqService;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface BidRegBeanLocal {

    public void save(PrmsBid eepBidReg);

    public void Edit(PrmsBid eepBidReg);

    public ArrayList<PrmsBid> searchBidRefce(PrmsBid eepBidReg);

    public PrmsBid getBidRe(PrmsBid eepBidReg);

    public ArrayList<MmsItemRegistration> getListOfCodeName(String toString);

    public List<PrmsPurchaseRequest> getPrNo();

//    public List<PrmsPurchaseRequest> getPrNoList(String toString);
    public List<PrmsPurchaseRequestDet> getPrNoList(String selctPrNo);

    List<PrmsBid> searchBidNo(PrmsBid prmsBid);

    public ArrayList<PrmsBidCriteria> getBidCriteriaList();

    public List<PrmsPurchasePlan> getPlanNo();

    public List<MmsItemRegistration> getItemCodefrmPlan(String toString);

    public PrmsBid getSelectedRequest(String id);

    public PrmsBid getBidNo();

    public List<PrmsPurchaseReqService> getService(String toString);

    public List<PrmsPurchaseRequest> searchPurchaseReqNo();

    public List<MmsItemRegistration> getItemcode(String toString);

    public MmsItemRegistration getItemName(MmsItemRegistration itemName);

    public List<PrmsPurchaseReqService> searchgetService();

    public List<PrmsServiceAndWorkReg> getServiceFrmPr(String toString);

    public PrmsServiceAndWorkReg getServiceNamefrNonCon(BigDecimal toString);

    public PrmsServiceAndWorkReg getServiceNamfrPr(PrmsServiceAndWorkReg toString);

    public List<PrmsPurchaseRequest> searchPrNoFrWork();

    public List<PrmsServiceAndWorkReg> getWorkNo(String toString);

    public PrmsServiceAndWorkReg getworkName(PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public MmsItemRegistration getItemNameFrmPlan(MmsItemRegistration itemId);

    public List<PrmsPurchasePlan> getServicefrmPlanNo();

    public List<PrmsServiceAndWorkReg> getSericeNameFrmPlan(String toString);

    public PrmsServiceAndWorkReg getUnitMeasure(PrmsServiceAndWorkReg serviceId);

    public PrmsAnnualPlanService getQuantity(PrmsServiceAndWorkReg seviceId);

    public PrmsServiceAndWorkReg findId(PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public List<PrmsPurchasePlan> getWorkfrmPlanNo();

    public PrmsPurchaseRequestDet getQuantityFrmPr(MmsItemRegistration mmsItemRegistration);

    public PrmsPurchaseReqService getQuantityFrmPrService(PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public PrmsPurchasePlnDetail getQuantityFrmPlan(MmsItemRegistration mmsItemRegistration);

    public List<PrmsServiceAndWorkReg> getWorkNameFrmPlan(String toString);

    public List<PrmsPurchaseRequest> getServiceFrmPr();

    public List<PmsCreateProjects> getProjectId();

    public List<PrmsContract> findbycontractId(PrmsBid eepBidReg);

    public List<PrmsAward> findbyID(PrmsBid eepBidReg);

    public List<PrmsBid> getBidVocherList();

    public List<MmsItemRegistration> getItemFrmProjectPlan(String toString);

    public MmsItemRegistration getUnitMeasureFrmProjec(MmsItemRegistration mmsItemRegistration);

    public PmsResources getQuantityFrmProjectPlan(MmsItemRegistration pmsResources);

    public List<PrmsServiceAndWorkReg> getServiceFrmProjectPlan(String toString);

    public PrmsServiceAndWorkReg getUnitMeasureServiceFrmProjec(PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public PmsResources getQuantityServiceFrmProjectPlan(PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public List<PrmsBid> getBidOnList();

    public String getGoods_Service_WorkBidSeqNo(String ProcurementCategory);

    public List<PrmsBid> getParamNameList();
}
