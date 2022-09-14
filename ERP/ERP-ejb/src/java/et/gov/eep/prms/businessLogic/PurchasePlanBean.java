/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsItemRegistration;
//import et.gov.eep.mms.entity.PapmsItemRegistration;
import et.gov.eep.mms.entity.MmsNeedAssessment;
import et.gov.eep.mms.entity.MmsNeedAssessmentDtl;
import et.gov.eep.mms.entity.MmsNeedAssessmentService;
import et.gov.eep.prms.entity.PrmsPurchasePlan;
import et.gov.eep.prms.mapper.PrmsMarketAssessmentDetailFacade;
import et.gov.eep.prms.mapper.PrmsPurchasePlanFacade;
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
public class PurchasePlanBean implements PurchasePlanBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsPurchasePlanFacade eepPurchasePlanFacade;
    @EJB
    PrmsMarketAssessmentDetailFacade assessmentDetailFacade;
    @EJB
    EthiopianCalendarBeanLocal EthiopianCalendarBeanLocal;
    // </editor-fold>

    @Override
    public void save(PrmsPurchasePlan eepPurchasePlan) {
        eepPurchasePlanFacade.create(eepPurchasePlan);
    }

    @Override
    public ArrayList<PrmsPurchasePlan> searchPlnNo(PrmsPurchasePlan eepPurchasePlan) {
        return eepPurchasePlanFacade.searchPlnNo(eepPurchasePlan);
    }

    @Override
    public PrmsPurchasePlan getPlan(PrmsPurchasePlan eepPurchasePlan) {
        return eepPurchasePlanFacade.searchEvent(eepPurchasePlan);
    }

    @Override
    public ArrayList<MmsItemRegistration> getListOfCodeName(String toString) {
        return eepPurchasePlanFacade.getListOfCodeName(toString);
    }

    @Override
    public List<MmsNeedAssessment> getBudgetYear() {
        return eepPurchasePlanFacade.getBudgetYears();
    }

    @Override
    public List<PrmsPurchasePlan> searchByPlanNo(PrmsPurchasePlan prmsActionPlan) {
        return eepPurchasePlanFacade.getAPlanNo(prmsActionPlan);
    }

    @Override
    public void update(PrmsPurchasePlan eepPurchasePlan) {
        eepPurchasePlanFacade.edit(eepPurchasePlan);
    }

    @Override
    public PrmsPurchasePlan getLastPlnNo() {
        return eepPurchasePlanFacade.getLastPlnNo();
    }

    @Override
    public PrmsPurchasePlan getSelectedRequest(String id) {
        return eepPurchasePlanFacade.getplanId(id);
    }

    @Override
    public List<MmsNeedAssessmentService> getServiceByYear(
            PrmsPurchasePlan prmsPurchasePlan, String purchaseType) {
        return eepPurchasePlanFacade.getServByYears(prmsPurchasePlan, purchaseType);
    }

    @Override
    public List<MmsNeedAssessment> getPurchaseType(String purchaseType) {
        return eepPurchasePlanFacade.getPurchaseType(purchaseType);
    }

    @Override
    public List<PrmsPurchasePlan> searchByPlanNo_() {
        return eepPurchasePlanFacade.getAPlanNo_();
    }

    @Override
    public List<PrmsPurchasePlan> getAPlanNo(int status, int UserId, PrmsPurchasePlan eepPurchasePlan) {
        return eepPurchasePlanFacade.getAPlanNo(status, UserId, eepPurchasePlan);
    }

    @Override
    public PrmsPurchasePlan searchById(String id) {
        return eepPurchasePlanFacade.findById(id);
    }

    @Override
    public List<MmsNeedAssessmentDtl> getByYearss(PrmsPurchasePlan selectYear, int needAssStatus) {
        return eepPurchasePlanFacade.getByYear(selectYear, needAssStatus);
    }

    @Override
    public List<MmsCategory> findItemsCategoriesList() {
        return eepPurchasePlanFacade.findItemsCategoriesList();
    }

    @Override
    public String getAnnualPlanNo() {
        String ethYear = EthiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String prefix = "AP-NO";
        String planNo;
        int maxNo = 0;
        List<PrmsPurchasePlan> annualPlanNoList = eepPurchasePlanFacade.getAnnualPlanNo(prefix, ethYear);
        for (int index = 0; index < annualPlanNoList.size(); index++) {
            planNo = annualPlanNoList.get(index).getPlanNo();
            String[] lastPlanNos = planNo.split("-");
            String lastDatesPatern = lastPlanNos[2];
            System.out.println("Last Pattern " + lastDatesPatern);
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increament = Integer.parseInt(lastDatesPaterns[0]);
            if (maxNo < increament) {
                maxNo = increament;
            }

        }
        maxNo = maxNo + 1;
        planNo = prefix + "-" + maxNo + "/" + ethYear;
        return planNo;
    }

    @Override
    public List<PrmsPurchasePlan> getParamNameList() {
        return eepPurchasePlanFacade.getParamNameList();
    }
}
