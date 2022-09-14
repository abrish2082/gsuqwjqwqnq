/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsNeedAssessment;
import et.gov.eep.mms.entity.MmsNeedAssessmentDtl;
import et.gov.eep.mms.entity.MmsNeedAssessmentService;
import et.gov.eep.prms.entity.PrmsPurchasePlan;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PurchasePlanBeanLocal {

    public void save(PrmsPurchasePlan eepPurchasePlan);

    public ArrayList<PrmsPurchasePlan> searchPlnNo(PrmsPurchasePlan eepPurchasePlan);

    public PrmsPurchasePlan getPlan(PrmsPurchasePlan eepPurchasePlan);

    public ArrayList<MmsItemRegistration> getListOfCodeName(String toString);

    public List<MmsNeedAssessment> getBudgetYear();

    public List<PrmsPurchasePlan> searchByPlanNo(PrmsPurchasePlan prmsActionPlan);

    public List<MmsNeedAssessmentDtl> getByYearss(PrmsPurchasePlan selectYear, int needAssStatus);

    public void update(PrmsPurchasePlan eepPurchasePlan);

    public PrmsPurchasePlan getLastPlnNo();

    public PrmsPurchasePlan getSelectedRequest(String id);

    public List<MmsNeedAssessmentService> getServiceByYear(PrmsPurchasePlan eepPurchasePlan, String purchaseType);

    public List<MmsNeedAssessment> getPurchaseType(String toString);
    
    public List<PrmsPurchasePlan> searchByPlanNo_();
    
    public List<PrmsPurchasePlan> getAPlanNo(int status,int UserId,PrmsPurchasePlan eepPurchasePlan);

    public PrmsPurchasePlan searchById(String id);

    public List<MmsCategory> findItemsCategoriesList();

    public String getAnnualPlanNo();

    public List<PrmsPurchasePlan> getParamNameList();
}
