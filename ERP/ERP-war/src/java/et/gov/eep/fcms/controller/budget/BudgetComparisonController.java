/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.budget;
   //<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsBudgetControlBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.budgetCodeBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import et.gov.eep.fcms.entity.budget.FmsBudgetControl;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.TabChangeEvent;
    //</editor-fold>


/**
 *
 * @author Me
 */
@Named(value = "budgetComparisonController")
@ViewScoped
public class BudgetComparisonController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    FmsBudgetControl fmsBudgetControl;
    @Inject
    FmsCapitalBudget1 fmsCapitalBudget1;
    @Inject
    FmsCapitalBudgetDetail fmsCapitalBudgetDetail;
    @Inject
    FmsOperatingBudget1 fmsOperatingBudget1;
    @Inject
    FmsOperatingBudgetDetail fmsOperatingBudgetDetail;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsBudgetCode fmsBudgetCode;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunction;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Inject">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
     @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    budgetCodeBeanLocal fmsBudgetCodeBeanLocal;
    @EJB
    FmsOperatingBudgetDetailBeanLocal fmsOperatingBudgetDetailBeanLocal;
    @EJB
    FmsOperatingBudgetBeanLocal fmsOperatingBudgetBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FmsLuBudgetYearBeanLocal fmsLuBudgetYearBeanLocal;
    @EJB
    FmsCapitalBudgetBeanLocal fmsCapitalBudgetBeanLocal;
    @EJB
    FmsCapitalBudgetDetailBeanLocal fmsCapitalBudgetDetailBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    budgetYearLookUpBeanLocal budgetYearLookUpBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FmsBudgetControlBeanLocal fmsBudgetControlBeanLocal;
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="variable declaration">
       FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<FmsBudgetControl> fmsBudgetControlsList = new ArrayList<>();
    List<FmsCostCenter> costCenterList;
    DataModel<FmsBudgetControl> fmsFmsBudgetControlDatamodel;

   

    String budgetTab = "Operating Budget";
    boolean printerRender = false;
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="getter and setters">
     public boolean isPrinterRender() {
        return printerRender;
    }

    public void setPrinterRender(boolean printerRender) {
        this.printerRender = printerRender;
    }

    public List<FmsCostCenter> getCostCenterList() {
        return costCenterList;
    }

    public void setCostCenterList(List<FmsCostCenter> costCenterList) {
        this.costCenterList = costCenterList;
    }

    public List<FmsBudgetControl> getFmsBudgetControlsList() {
        return fmsBudgetControlsList;
    }

    public void setFmsBudgetControlsList(List<FmsBudgetControl> fmsBudgetControlsList) {
        this.fmsBudgetControlsList = fmsBudgetControlsList;
    }

    public DataModel<FmsBudgetControl> getFmsFmsBudgetControlDatamodel() {
        if (fmsFmsBudgetControlDatamodel == null) {
            fmsFmsBudgetControlDatamodel = new ListDataModel<>();
        }
        return fmsFmsBudgetControlDatamodel;
    }

    public void setFmsFmsBudgetControlDatamodel(DataModel<FmsBudgetControl> fmsFmsBudgetControlDatamodel) {
        this.fmsFmsBudgetControlDatamodel = fmsFmsBudgetControlDatamodel;
    }

    public FmsAccountingPeriod getCurrPeriod() {
        return currPeriod;
    }

    public void setCurrPeriod(FmsAccountingPeriod currPeriod) {
        this.currPeriod = currPeriod;
    }

    public FmsLuBudgetYear getNextPeriod() {
        return nextPeriod;
    }

    public void setNextPeriod(FmsLuBudgetYear nextPeriod) {
        this.nextPeriod = nextPeriod;
    }

    public List<FmsLuBudgetYear> getBudgetyrList() {
        return budgetyrList;
    }

    public void setBudgetyrList(List<FmsLuBudgetYear> budgetyrList) {
        this.budgetyrList = budgetyrList;
    }

    public List<FmsLuBudgetYear> getListLuBudgetYearList() {
        return listLuBudgetYearList;
    }

    public void setListLuBudgetYearList(List<FmsLuBudgetYear> listLuBudgetYearList) {
        this.listLuBudgetYearList = listLuBudgetYearList;
    }

    public String getBudgetTab() {
        return budgetTab;
    }

    public void setBudgetTab(String budgetTab) {
        this.budgetTab = budgetTab;
    }

    public FmsBudgetControl getFmsBudgetControl() {
        return fmsBudgetControl;
    }

    public void setFmsBudgetControl(FmsBudgetControl fmsBudgetControl) {
        this.fmsBudgetControl = fmsBudgetControl;
    }

    public FmsCostCenter getFmsCostCenter() {
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }

    public FmsCapitalBudget1 getFmsCapitalBudget1() {
        return fmsCapitalBudget1;
    }

    public void setFmsCapitalBudget1(FmsCapitalBudget1 fmsCapitalBudget1) {
        this.fmsCapitalBudget1 = fmsCapitalBudget1;
    }

    public FmsCapitalBudgetDetail getFmsCapitalBudgetDetail() {
        return fmsCapitalBudgetDetail;
    }

    public void setFmsCapitalBudgetDetail(FmsCapitalBudgetDetail fmsCapitalBudgetDetail) {
        this.fmsCapitalBudgetDetail = fmsCapitalBudgetDetail;
    }

    public FmsOperatingBudget1 getFmsOperatingBudget1() {
        return fmsOperatingBudget1;
    }

    public void setFmsOperatingBudget1(FmsOperatingBudget1 fmsOperatingBudget1) {
        this.fmsOperatingBudget1 = fmsOperatingBudget1;
    }

    public FmsOperatingBudgetDetail getFmsOperatingBudgetDetail() {
        return fmsOperatingBudgetDetail;
    }

    public void setFmsOperatingBudgetDetail(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        this.fmsOperatingBudgetDetail = fmsOperatingBudgetDetail;
    }

    public FmsLuBudgetYear getFmsLuBudgetYear() {
        return fmsLuBudgetYear;
    }

    public void setFmsLuBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
        this.fmsLuBudgetYear = fmsLuBudgetYear;
    }

    public FmsLuSystem getFmsLuSystem() {
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public FmsBudgetCode getFmsBudgetCode() {
        return fmsBudgetCode;
    }

    public void setFmsBudgetCode(FmsBudgetCode fmsBudgetCode) {
        this.fmsBudgetCode = fmsBudgetCode;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="BudgetComparisonController">
     public BudgetComparisonController() {
    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="other metheds">
     
    public void onBudgetTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        switch (event.getTab().getTitle()) {
            case "Operating Budget":

                budgetTab = "Operating Budget";
                break;

            case "Capital Budget":

                budgetTab = "Capital Budget";
                break;

        }
        tabChangeClear();
    }

    public void tabChangeClear() {
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsLuSystem = new FmsLuSystem();
        fmsCostCenter = new FmsCostCenter();
        fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        fmsFmsBudgetControlDatamodel = new ArrayDataModel<>();
        fmsBudgetControlsList = new ArrayList<>();
    }

    public void searchYearValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYear);
            fmsOperatingBudget1.setBudgetYear(fmsLuBudgetYear);
        }
    }

    public SelectItem[] getLuBudgetYearSearchList() {
        listLuBudgetYearList = fmsLuBudgetYearBeanLocal.getLuBudgetYear();
        listLuBudgetYearList.size();

        currPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        nextPeriod = new FmsLuBudgetYear();
        budgetyrList = new ArrayList<>();
        for (int i = 0; i < listLuBudgetYearList.size(); i++) {
            if (Objects.equals(currPeriod.getLuBudgetYearId().getLuBudgetYearId(), listLuBudgetYearList.get(i).getLuBudgetYearId())) {
                nextPeriod = listLuBudgetYearList.get(i );
            }
        }
        budgetyrList.add(currPeriod.getLuBudgetYearId());
        budgetyrList.add(nextPeriod);
        return JsfUtil.getSelectItems(budgetyrList, true);
    }

    public SelectItem[] getSystemSearchList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    public void SystemSearchChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
        }
    }

    public SelectItem[] getCostCenterSearchTO() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findAll(), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    public void CostCenterChangeTO(ValueChangeEvent event) {        
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
            if (budgetTab == "Operating Budget") {
                fmsBudgetControlsList = new ArrayList<>();
                fmsBudgetControlsList = fmsBudgetControlBeanLocal.fetchOBComparison(fmsLuBudgetYear, fmsCostcSystemJunction);
                if (fmsBudgetControlsList.isEmpty()) {
                    JsfUtil.addFatalMessage("No result found for cost center " + fmsCostCenter.getSystemName() + " and budget year " + fmsLuBudgetYear.getBudgetYear() + ".");
                } else {
                    printerRender = true;
                }
                recreatDataModel();
            } else if (budgetTab == "Capital Budget") {
                fmsBudgetControlsList = new ArrayList<>();
                System.out.println("fmsCostcSystemJunction" + fmsCostcSystemJunction.getId() + "budgetYear" + fmsLuBudgetYear.getLuBudgetYearId());
                fmsBudgetControlsList = fmsBudgetControlBeanLocal.fetchCBComparison(fmsLuBudgetYear, fmsCostcSystemJunction);
                if (fmsBudgetControlsList.isEmpty()) {
                    JsfUtil.addFatalMessage("No result found for cost center " + fmsCostCenter.getSystemName() + " and budget year " + fmsLuBudgetYear.getBudgetYear() + ".");
                } else {
                    printerRender = true;
                }
                recreatDataModel();
            }
        }
    }

    public void recreatDataModel() {
        fmsFmsBudgetControlDatamodel = null;
        fmsFmsBudgetControlDatamodel = new ListDataModel(new ArrayList(fmsBudgetControlsList));
        fmsBudgetControlsList = new ArrayList<>();
    }

//    public SelectItem[] OBReqcodesTO() {
//        if (fmsCostCenter != null) {
//            return JsfUtil.getSelectItems(fmsOperatingBudgetBeanLocal.findByBudgetYearAndCostCenterAuthorized(fmsLuBudgetYear, fmsCostCenter), true);
//        } else {
//            SelectItem[] items = new SelectItem[1];
//            return items;
//        }
//    }
    public void RequestCodeChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsOperatingBudget1.setRequestCode(event.getNewValue().toString());
            fmsOperatingBudget1 = fmsOperatingBudgetBeanLocal.fetchByRequestCode(fmsOperatingBudget1);
            fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
            fmsOperatingBudgetDetail.setOperatingBudgetId(fmsOperatingBudget1);
            fmsOperatingBudgetDetailBeanLocal.fetchOBDetail(fmsOperatingBudget1);

        }
    }
    //</editor-fold>

    
    
    
    
    
    
    
    

 

   

   

}
