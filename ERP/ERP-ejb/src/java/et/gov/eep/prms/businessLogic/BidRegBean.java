/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
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
import et.gov.eep.prms.mapper.PrmsBidFacade;
import et.gov.eep.prms.mapper.PrmsPurchaseRequestDetFacade;
import et.gov.eep.prms.mapper.PrmsPurchaseRequestFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class BidRegBean implements BidRegBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsBidFacade eepBidRegFacade;
    @EJB
    PrmsPurchaseRequestFacade prmsPurchaseRequestFacade;
    @EJB
    PrmsPurchaseRequestDetFacade requestDetFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    // </editor-fold>

    @Override
    public void save(PrmsBid eepBidReg) {
        eepBidRegFacade.create(eepBidReg);
    }

    @Override
    public ArrayList<PrmsBid> searchBidRefce(PrmsBid eepBidReg) {
        return eepBidRegFacade.getBidRefc(eepBidReg);
    }

    @Override
    public PrmsBid getBidRe(PrmsBid eepBidReg) {
        return eepBidRegFacade.getBidRe(eepBidReg);
    }

    @Override
    public ArrayList<MmsItemRegistration> getListOfCodeName(String toString) {
        return eepBidRegFacade.getItemCodename(toString);
    }

    @Override
    public void Edit(PrmsBid eepBidReg) {
        eepBidRegFacade.edit(eepBidReg);
    }

    @Override
    public List<PrmsPurchaseRequest> getPrNo() {
        return prmsPurchaseRequestFacade.getPrNo();
    }

//    @Override
//    public List<PrmsPurchaseRequest> getPrNoList(String toString) {
//
//        return prmsPurchaseRequestFacade.getPrNList(toString);
//    }
    @Override
    public List<PrmsPurchaseRequestDet> getPrNoList(String selctPrNo) {
        return requestDetFacade.getPrNList(selctPrNo);
    }

    @Override
    public List<PrmsBid> searchBidNo(PrmsBid prmsBid) {
        return eepBidRegFacade.getBidNo(prmsBid);
    }

    @Override
    public ArrayList<PrmsBidCriteria> getBidCriteriaList() {
        return eepBidRegFacade.getCriteriaLst();
    }

    @Override
    public List<PrmsPurchasePlan> getPlanNo() {
        return eepBidRegFacade.getPlanNo();
    }

    @Override
    public List<MmsItemRegistration> getItemCodefrmPlan(String Itemcode) {
        return eepBidRegFacade.getItemCodefrmPlan(Itemcode);
    }

    @Override
    public PrmsBid getSelectedRequest(String id) {
        return eepBidRegFacade.getSelectedId(id);

    }

    @Override
    public PrmsBid getBidNo() {
        return eepBidRegFacade.getBidNoAuto();
    }

    @Override
    public List<PrmsPurchaseReqService> getService(String serviceName) {
        return eepBidRegFacade.getService(serviceName);
    }

    @Override
    public List<PrmsPurchaseRequest> searchPurchaseReqNo() {
        return prmsPurchaseRequestFacade.searchPurchaseReNo();
    }

    @Override
    public List<MmsItemRegistration> getItemcode(String itemCode) {
        return eepBidRegFacade.getItemCod(itemCode);
    }

    @Override
    public MmsItemRegistration getItemName(MmsItemRegistration itemName) {
        return eepBidRegFacade.getItemName(itemName);
    }

    @Override
    public List<PrmsPurchaseReqService> searchgetService() {
        return eepBidRegFacade.searchgetService();
    }

    @Override
    public List<PrmsServiceAndWorkReg> getServiceFrmPr(String sericeNo) {
        return eepBidRegFacade.getServiceFrmPr(sericeNo);
    }

    @Override
    public PrmsServiceAndWorkReg getServiceNamefrNonCon(BigDecimal sericeName) {
        return eepBidRegFacade.getServiceNamefrNonCon(sericeName);
    }

    @Override
    public PrmsServiceAndWorkReg getServiceNamfrPr(PrmsServiceAndWorkReg ServiceName) {
        return eepBidRegFacade.getServiceNamfrPr(ServiceName);
    }

    @Override
    public List<PrmsPurchaseRequest> searchPrNoFrWork() {
        return eepBidRegFacade.getWork();
    }

    @Override
    public List<PrmsServiceAndWorkReg> getWorkNo(String workNo) {
        return eepBidRegFacade.getworkNo(workNo);
    }

    @Override
    public PrmsServiceAndWorkReg getworkName(PrmsServiceAndWorkReg workName) {
        return eepBidRegFacade.getWorkName(workName);
    }

    @Override
    public MmsItemRegistration getItemNameFrmPlan(MmsItemRegistration itemId) {
        return eepBidRegFacade.getItemNameFrmPlan(itemId);
    }

    @Override
    public List<PrmsPurchasePlan> getServicefrmPlanNo() {
        return eepBidRegFacade.getServicefrmPlanNo();
    }

    @Override
    public List<PrmsServiceAndWorkReg> getSericeNameFrmPlan(String sericeName) {
        return eepBidRegFacade.getSericeNameFrmPlan(sericeName);
    }

    @Override
    public PrmsServiceAndWorkReg getUnitMeasure(PrmsServiceAndWorkReg serviceId) {
        return eepBidRegFacade.getUnitMeasure(serviceId);
    }

    @Override
    public PrmsAnnualPlanService getQuantity(PrmsServiceAndWorkReg seviceId) {
        return eepBidRegFacade.getQuantity(seviceId);
    }

    @Override
    public PrmsServiceAndWorkReg findId(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return eepBidRegFacade.findId(prmsServiceAndWorkReg);
    }

    @Override
    public List<PrmsPurchasePlan> getWorkfrmPlanNo() {
        return eepBidRegFacade.getWorkPlanNo();
    }

    @Override
    public PrmsPurchaseRequestDet getQuantityFrmPr(MmsItemRegistration mmsItemRegistration) {
        return eepBidRegFacade.getQuantityFrmPr(mmsItemRegistration);
    }

    @Override
    public PrmsPurchaseReqService getQuantityFrmPrService(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return eepBidRegFacade.getQuantityFrmPrService(prmsServiceAndWorkReg);
    }

    @Override
    public PrmsPurchasePlnDetail getQuantityFrmPlan(MmsItemRegistration mmsItemRegistration) {
        return eepBidRegFacade.getQuantityFrmPlan(mmsItemRegistration);
    }

    @Override
    public List<PrmsServiceAndWorkReg> getWorkNameFrmPlan(String workName) {
        return eepBidRegFacade.getWorkNameFrmPlan(workName);
    }

    @Override
    public List<PrmsPurchaseRequest> getServiceFrmPr() {
        return eepBidRegFacade.getServiceNmFrmPr();
    }

    @Override
    public List<PmsCreateProjects> getProjectId() {
        return eepBidRegFacade.getProjectId();
    }

//    public List<PrmsContract> findbycontractId(PrmsBid eepBidReg) {
//       return eepBidRegFacade.findbycontractId(eepBidReg);
//    }
//  
    @Override
    public List<PrmsContract> findbycontractId(PrmsBid eepBidReg) {
        return eepBidRegFacade.findbycontractId(eepBidReg);
    }

    @Override
    public List<PrmsAward> findbyID(PrmsBid eepBidReg) {
        return eepBidRegFacade.findbyID(eepBidReg);
    }

    @Override
    public List<PrmsBid> getBidVocherList() {
        return eepBidRegFacade.getBidNoList();
    }

    @Override
    public List<MmsItemRegistration> getItemFrmProjectPlan(String itemName) {
        return eepBidRegFacade.getItemNameFrmProject(itemName);
    }

    @Override
    public MmsItemRegistration getUnitMeasureFrmProjec(MmsItemRegistration mmsItemRegistration) {
        return eepBidRegFacade.getUnitOfMeasureFrmProject(mmsItemRegistration);
    }

    @Override
    public PmsResources getQuantityFrmProjectPlan(MmsItemRegistration pmsResources) {
        return eepBidRegFacade.getQuantityFrmProjectPln(pmsResources);
    }

    @Override
    public List<PrmsServiceAndWorkReg> getServiceFrmProjectPlan(String serviceName) {
        return eepBidRegFacade.getServiceFrmProjectPlan(serviceName);
    }

    @Override
    public PrmsServiceAndWorkReg getUnitMeasureServiceFrmProjec(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return eepBidRegFacade.getUnitMeasAndQuantFrmService(prmsServiceAndWorkReg);
    }

    @Override
    public PmsResources getQuantityServiceFrmProjectPlan(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return eepBidRegFacade.getQuantityServiceFrmPrjctPlan(prmsServiceAndWorkReg);
    }

    @Override
    public List<PrmsBid> getBidOnList() {
        return eepBidRegFacade.getBidOnList();
    }

    @Override
    public String getGoods_Service_WorkBidSeqNo(String ProcurementCategory) {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String goods_Service_WorkBidNo = null;
        String prefix = null;
        int maxNo = 0;
        if (ProcurementCategory.equals("Goods")) {
            prefix = "Bid-Goods-No";
        } else if (ProcurementCategory.equals("Work")) {
            prefix = "Bid-Work-No";
        } else if (ProcurementCategory.equals("Service")) {
            prefix = "Bid-Service-No";
        }
        List<PrmsBid> prmsBidList = eepBidRegFacade.getGoods_Service_WorkBidSeqNo(prefix, eYear);
        for (int k = 0; k < prmsBidList.size(); k++) {
            goods_Service_WorkBidNo = prmsBidList.get(k).getRefNo();
            String[] lastInspNos = goods_Service_WorkBidNo.split("-");
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
        System.out.println("maxNo " + maxNo);
//        ServOrWorkNo = (prefix + "-" + maxNo + "/" + df1.format(now));
        goods_Service_WorkBidNo = (prefix + "-" + maxNo + "/" + eYear);
        return goods_Service_WorkBidNo;
    }

    @Override
    public List<PrmsBid> getParamNameList() {
        return eepBidRegFacade.getParamNameList();
    }
}
