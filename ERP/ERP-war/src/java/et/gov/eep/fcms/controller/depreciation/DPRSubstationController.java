/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.depreciation;

/**
 *
 * @author Binyam
 */
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.fixedasset.FmsDprSubstationBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.fixedasset.FmsDprSubstation;
import et.gov.eep.mms.businessLogic.MmsFaSubstationBeanLocal;
import et.gov.eep.mms.entity.MmsFaSubstation;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Binyam
 */
@Named(value = "dprSubstationController")
@ViewScoped
public class DPRSubstationController implements Serializable {

    @Inject
    SessionBean SessionBean;

    @Inject
    MmsFaSubstation mmsFaSubstation;
    @Inject
    FmsDprSubstation fmsDprSubstation;
    @EJB
    MmsFaSubstationBeanLocal mmsFaSubstationBeanLocal;
    @EJB
    FmsDprSubstationBeanLocal fmsDprSubstationBeanLocal;
    @Inject
    FmsAccountingPeriod AccountingPeriod;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    private FmsDprSubstation selectedAsset;

    List<MmsFaSubstation> substationAssetList = new ArrayList<>();
    List<FmsDprSubstation> substationDPRList = new ArrayList<>();
    List<FmsDprSubstation> dprlistSubstation = new ArrayList<>();
    List<FmsDprSubstation> substationDataList = new ArrayList<>();
    DataModel<FmsDprSubstation> substationDataModel = new ArrayDataModel<>();
    List<FmsDprSubstation> filteredItems;

    String budgetYear = new String();

    private boolean registeredPnl = true;
    private boolean newItemPnl = false;

    String tableBtnVal = "New Items";
    String saveCalc = "Calculate";

    public DPRSubstationController() {
    }

    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    public String getTableBtnVal() {
        return tableBtnVal;
    }

    public void setTableBtnVal(String tableBtnVal) {
        this.tableBtnVal = tableBtnVal;
    }

    public String getSaveCalc() {
        return saveCalc;
    }

    public void setSaveCalc(String saveCalc) {
        this.saveCalc = saveCalc;
    }

    public List<FmsDprSubstation> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<FmsDprSubstation> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public String getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear;
    }

    public FmsDprSubstation getSelectedAsset() {
        return selectedAsset;
    }

    public void setSelectedAsset(FmsDprSubstation selectedAsset) {
        this.selectedAsset = selectedAsset;
    }

    public MmsFaSubstation getMmsFaSubstation() {
        if (mmsFaSubstation == null) {
            mmsFaSubstation = new MmsFaSubstation();
        }
        return mmsFaSubstation;
    }

    public void setMmsFaSubstation(MmsFaSubstation mmsFaSubstation) {
        this.mmsFaSubstation = mmsFaSubstation;
    }

    public FmsDprSubstation getFmsDprSubstation() {
        if (fmsDprSubstation == null) {
            fmsDprSubstation = new FmsDprSubstation();
        }
        return fmsDprSubstation;
    }

    public void setFmsDprSubstation(FmsDprSubstation fmsDprSubstation) {
        this.fmsDprSubstation = fmsDprSubstation;
    }

    public List<MmsFaSubstation> getSubstationAssetList() {
        return substationAssetList;
    }

    public void setSubstationAssetList(List<MmsFaSubstation> substationAssetList) {
        this.substationAssetList = substationAssetList;
    }

    public List<FmsDprSubstation> getSubstationDPRList() {
        if (substationDPRList == null) {
            substationDPRList = new ArrayList<>();
        }
        return substationDPRList;
    }

    public void setSubstationDPRList(List<FmsDprSubstation> substationDPRList) {
        this.substationDPRList = substationDPRList;
    }

    public MmsFaSubstationBeanLocal getMmsFaSubstationBeanLocal() {
        return mmsFaSubstationBeanLocal;
    }

    public void setMmsFaSubstationBeanLocal(MmsFaSubstationBeanLocal mmsFaSubstationBeanLocal) {
        this.mmsFaSubstationBeanLocal = mmsFaSubstationBeanLocal;
    }

    public FmsDprSubstationBeanLocal getFmsDprSubstationBeanLocal() {
        return fmsDprSubstationBeanLocal;
    }

    public void setFmsDprSubstationBeanLocal(FmsDprSubstationBeanLocal fmsDprSubstationBeanLocal) {
        this.fmsDprSubstationBeanLocal = fmsDprSubstationBeanLocal;
    }

    public FmsAccountingPeriod getAccountPeriod() {
        return AccountingPeriod;
    }

    public void setAccountPeriod(FmsAccountingPeriod AccountingPeriod) {
        this.AccountingPeriod = AccountingPeriod;
    }

    public FmsAccountingPeriodBeanLocal getFmsAccountingPeriodBeanLocal() {
        return fmsAccountingPeriodBeanLocal;
    }

    public void setFmsAccountingPeriodBeanLocal(FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal) {
        this.fmsAccountingPeriodBeanLocal = fmsAccountingPeriodBeanLocal;
    }

    public List<FmsDprSubstation> getDprlistSubstation() {
        if (dprlistSubstation == null) {
            dprlistSubstation = new ArrayList<>();
        }
        return dprlistSubstation;
    }

    public void setDprlistSubstation(List<FmsDprSubstation> dprlistSubstation) {
        this.dprlistSubstation = dprlistSubstation;
    }

    public List<FmsDprSubstation> getSubstationDataList() {
        return substationDataList;
    }

    public void setSubstationDataList(List<FmsDprSubstation> substationDataList) {
        this.substationDataList = substationDataList;
    }

    public DataModel<FmsDprSubstation> getSubstationDataModel() {
        if (substationDataModel == null) {
            substationDataModel = new ArrayDataModel<>();
        }
        return substationDataModel;
    }

    public void setSubstationDataModel(DataModel<FmsDprSubstation> substationDataModel) {
        this.substationDataModel = substationDataModel;
    }

    public boolean isRegisteredPnl() {
        return registeredPnl;
    }

    public void setRegisteredPnl(boolean registeredPnl) {
        this.registeredPnl = registeredPnl;
    }

    public boolean isNewItemPnl() {
        return newItemPnl;
    }

    public void setNewItemPnl(boolean newItemPnl) {
        this.newItemPnl = newItemPnl;
    }
    //</editor-fold>

    @PostConstruct
    public void SearchTRAsset() {
        substationDPRList = fmsDprSubstationBeanLocal.findStatus1();
        calculateDPR();
        getActivePeriod();
    }

    public void getActivePeriod() {
        AccountingPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        AccountingPeriod.getStartDate();
        AccountingPeriod.getEndDate();
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    public void tableBtn() {
        if ("Registered Items".equals(tableBtnVal)) {
            registeredItem();
            tableBtnVal = "New Items";
            saveCalc = "Calculate";
        } else if ("New Items".equals(tableBtnVal)) {
            newItem();
            tableBtnVal = "Registered Items";
            saveCalc = "Save";
        }
    }

    public void newItem() {
        newItemPnl = true;
        registeredPnl = false;
        substationAssetList = null;
        substationDPRList = null;
        dprlistSubstation = null;

        substationAssetList = mmsFaSubstationBeanLocal.findNewItems();
        substationDPRList = fmsDprSubstationBeanLocal.findAll();
        SaveNewItems();
    }

    public void registeredItem() {
        registeredPnl = true;
        newItemPnl = false;

        substationAssetList = null;
        substationDPRList = null;
        dprlistSubstation = null;

        SearchTRAsset();
    }

    public void SaveNewItems() {
        for (int h = 0; h < substationAssetList.size(); h++) {

            fmsDprSubstation = new FmsDprSubstation(null);
            fmsDprSubstation.setSsAssetId(substationAssetList.get(h));
            fmsDprSubstation.setDprYear(BigDecimal.ZERO);
            fmsDprSubstation.setAccumulatedDpr(BigDecimal.ZERO);
            fmsDprSubstation.setAccountPeriod(AccountingPeriod);
            fmsDprSubstation.setStatus(1);
            getDprlistSubstation().add(fmsDprSubstation);
            fmsDprSubstation = new FmsDprSubstation(null);

        }
        if (!(dprlistSubstation == null)) {
            substationDataModel = new ListDataModel(new ArrayList(dprlistSubstation));
        } else {
            substationDataModel = null;
        }
    }

    public void calculateDPR() {
        dprlistSubstation = new ArrayList<>();
        if (!substationDPRList.isEmpty()) {
            for (FmsDprSubstation substationDPRList1 : substationDPRList) {
                fmsDprSubstation.setDprSubstationId(substationDPRList1.getDprSubstationId());
                fmsDprSubstation.setSsAssetId(substationDPRList1.getSsAssetId());
                fmsDprSubstation.setDprYear(substationDPRList1.getDprYear());
                fmsDprSubstation.setAccumulatedDpr(substationDPRList1.getAccumulatedDpr());
                fmsDprSubstation.setNetBookValue(substationDPRList1.getNetBookValue());
                fmsDprSubstation.setDataCardNo(substationDPRList1.getDataCardNo());
                fmsDprSubstation.setRevaluationCost(substationDPRList1.getRevaluationCost());
                fmsDprSubstation.setRevaluationServiceYear(substationDPRList1.getRevaluationServiceYear());
                fmsDprSubstation.setAccountPeriod(substationDPRList1.getAccountPeriod());
                fmsDprSubstation.setAccountNo(substationDPRList1.getAccountNo());
                getDprlistSubstation().add(fmsDprSubstation);
                fmsDprSubstation = new FmsDprSubstation(null);
            }
        }
        substationDataModel = new ListDataModel(new ArrayList(dprlistSubstation));
    }

    public void getByAssetName(SelectEvent event) {
        mmsFaSubstation.setSsLocation(event.getObject().toString());
    }

    public void NewSubstations(FmsDprSubstation fmsDprSubstation) {
        substationDataList = fmsDprSubstationBeanLocal.fetchSubstation(fmsDprSubstation);
        if (!substationDataList.isEmpty()) {
            substationDataModel = new ListDataModel(new ArrayList(substationDataList));
        } else {
            substationDataModel = null;
        }
    }

    public void populateDPR(SelectEvent event) {
        fmsDprSubstation = (FmsDprSubstation) event.getObject();
    }

    public void Add() {
        for (int i = 0; i < dprlistSubstation.size(); i++) {
            if (fmsDprSubstation.getSsAssetId() == dprlistSubstation.get(i).getSsAssetId()) {
                dprlistSubstation.set(i, fmsDprSubstation);
            }
        }
        substationDataModel = new ListDataModel(new ArrayList(dprlistSubstation));
    }

    public void saveDataC() {

        for (int i = 0; i < dprlistSubstation.size(); i++) {
            if (fmsDprSubstation.getSsAssetId() == dprlistSubstation.get(i).getSsAssetId()) {
                dprlistSubstation.get(i).setDataCardNo(fmsDprSubstation.getDataCardNo());
            }
        }
        substationDataModel = new ListDataModel(new ArrayList(dprlistSubstation));
    }

    double ten = 10.0;

    public void saveSubstationDPR() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveSubstationDPR", dataset)) {
                if (newItemPnl == true && !dprlistSubstation.isEmpty()) {
                    int saveStat = 0;
                    for (int i = 0; i < dprlistSubstation.size(); i++) {
                        if (!(dprlistSubstation.get(i).getDataCardNo() == null)) {
                            fmsDprSubstationBeanLocal.create(dprlistSubstation.get(i));
                            saveStat = 1;
                        }
                    }
                    substationDataModel = new ListDataModel(new ArrayList(dprlistSubstation));
                    if (saveStat == 1) {
                        registeredItem();
                        saveStat = 0;
                        tableBtnVal = "New Items";
                        saveCalc = "Calculate";
                        JsfUtil.addSuccessMessage("Fixed Asset is Saved");
                    }
                } else if (registeredPnl == true) {
                    int saveStat = 0;
                    if (!dprlistSubstation.isEmpty()) {

                        for (int i = 0; i < dprlistSubstation.size(); i++) {
                            fmsDprSubstation = new FmsDprSubstation();

                            if (dprlistSubstation.get(i).getDprYear() == BigDecimal.ZERO && Objects.equals(dprlistSubstation.get(i).getAccountPeriod(), AccountingPeriod) && dprlistSubstation.get(i).getSsAssetId().getRevaluationCost() == null) {       //INITIALLY
                                String Smonth[] = dprlistSubstation.get(i).getSsAssetId().getSsInservice().split("/");
                                int Imonth = Integer.parseInt(Smonth[1]);
                                if (Imonth == 1) {
                                    fmsDprSubstation.setSsAssetId(dprlistSubstation.get(i).getSsAssetId());
                                    fmsDprSubstation.setDprYear(dprlistSubstation.get(i).getSsAssetId().getSsUnitCost().divide(new BigDecimal(dprlistSubstation.get(i).getSsAssetId().getSsServiceLife()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprSubstation.setAccumulatedDpr(dprlistSubstation.get(i).getDprYear());
                                    fmsDprSubstation.setNetBookValue(dprlistSubstation.get(i).getSsAssetId().getSsUnitCost().subtract(dprlistSubstation.get(i).getAccumulatedDpr()));
                                    fmsDprSubstation.setStatus(1);
                                    fmsDprSubstation.setAccountPeriod(AccountingPeriod);
                                    fmsDprSubstation.setAccountNo(dprlistSubstation.get(i).getAccountNo());
                                    fmsDprSubstation.setDataCardNo(dprlistSubstation.get(i).getDataCardNo());

                                } else {
                                    fmsDprSubstation.setSsAssetId(dprlistSubstation.get(i).getSsAssetId());
                                    fmsDprSubstation.setDprYear(((dprlistSubstation.get(i).getSsAssetId().getSsUnitCost().divide(new BigDecimal(dprlistSubstation.get(i).getSsAssetId().getSsServiceLife().doubleValue() * 12), 2, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(Imonth))).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsDprSubstation.setAccumulatedDpr(fmsDprSubstation.getDprYear());
                                    fmsDprSubstation.setNetBookValue(dprlistSubstation.get(i).getSsAssetId().getSsUnitCost().subtract(dprlistSubstation.get(i).getAccumulatedDpr()));
                                    fmsDprSubstation.setStatus(1);
                                    fmsDprSubstation.setAccountPeriod(AccountingPeriod);
                                    fmsDprSubstation.setAccountNo(dprlistSubstation.get(i).getAccountNo());
                                    fmsDprSubstation.setDataCardNo(dprlistSubstation.get(i).getDataCardNo());
                                }
                            } else if (dprlistSubstation.get(i).getDprYear() != BigDecimal.ZERO && !Objects.equals(dprlistSubstation.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId()) && dprlistSubstation.get(i).getSsAssetId().getRevaluationCost() == null) {       //Nth time

                                fmsDprSubstation.setSsAssetId(dprlistSubstation.get(i).getSsAssetId());
                                fmsDprSubstation.setDprYear(dprlistSubstation.get(i).getSsAssetId().getSsUnitCost().divide(new BigDecimal(dprlistSubstation.get(i).getSsAssetId().getSsServiceLife()), 2, RoundingMode.HALF_EVEN));
                                fmsDprSubstation.setAccumulatedDpr(fmsDprSubstation.getDprYear().add(dprlistSubstation.get(i).getAccumulatedDpr()));
                                fmsDprSubstation.setNetBookValue(dprlistSubstation.get(i).getSsAssetId().getSsUnitCost().subtract(dprlistSubstation.get(i).getAccumulatedDpr()));
                                fmsDprSubstation.setAccountPeriod(AccountingPeriod);
                                fmsDprSubstation.setStatus(1);
                                fmsDprSubstation.setAccountNo(dprlistSubstation.get(i).getAccountNo());
                                fmsDprSubstation.setDataCardNo(dprlistSubstation.get(i).getDataCardNo());
                            } else if (dprlistSubstation.get(i).getSsAssetId().getRevaluationCost() != null && !Objects.equals(dprlistSubstation.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId())) {

                                if (dprlistSubstation.get(i).getRevaluationCost() == null) {
                                    fmsDprSubstation.setSsAssetId(dprlistSubstation.get(i).getSsAssetId());
                                    fmsDprSubstation.setRevaluationCost(dprlistSubstation.get(i).getSsAssetId().getRevaluationCost());
                                    fmsDprSubstation.setRevaluationServiceYear(dprlistSubstation.get(i).getSsAssetId().getRevaluationServiceYear());
                                    fmsDprSubstation.setDprYear(dprlistSubstation.get(i).getSsAssetId().getRevaluationCost().divide(new BigDecimal(dprlistSubstation.get(i).getSsAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprSubstation.setAccumulatedDpr(dprlistSubstation.get(i).getAccumulatedDpr().add(fmsDprSubstation.getDprYear()));
                                    fmsDprSubstation.setNetBookValue(dprlistSubstation.get(i).getNetBookValue().subtract(fmsDprSubstation.getDprYear()));
                                    fmsDprSubstation.setAccountPeriod(AccountingPeriod);
                                    fmsDprSubstation.setStatus(1);
                                    fmsDprSubstation.setAccountNo(dprlistSubstation.get(i).getAccountNo());
                                    fmsDprSubstation.setDataCardNo(dprlistSubstation.get(i).getDataCardNo());
                                } else if (dprlistSubstation.get(i).getSsAssetId().getRevaluationCost() != null) {
                                    fmsDprSubstation.setSsAssetId(dprlistSubstation.get(i).getSsAssetId());
                                    fmsDprSubstation.setDprYear(dprlistSubstation.get(i).getSsAssetId().getRevaluationCost().divide(new BigDecimal(dprlistSubstation.get(i).getSsAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprSubstation.setAccumulatedDpr(dprlistSubstation.get(i).getAccumulatedDpr().add(fmsDprSubstation.getDprYear()));
                                    fmsDprSubstation.setNetBookValue(dprlistSubstation.get(i).getNetBookValue().subtract(fmsDprSubstation.getDprYear()));
                                    fmsDprSubstation.setAccountPeriod(AccountingPeriod);
                                    fmsDprSubstation.setStatus(1);
                                    fmsDprSubstation.setAccountNo(dprlistSubstation.get(i).getAccountNo());
                                    fmsDprSubstation.setDataCardNo(dprlistSubstation.get(i).getDataCardNo());
                                    fmsDprSubstation.setRevaluationCost(dprlistSubstation.get(i).getSsAssetId().getRevaluationCost());
                                    fmsDprSubstation.setRevaluationServiceYear(dprlistSubstation.get(i).getSsAssetId().getRevaluationServiceYear());
                                }
                            }
                            dprlistSubstation.get(i).setStatus(0);
                            if (fmsDprSubstation.getDprYear() != null && dprlistSubstation != null) {
                                if (fmsDprSubstation.getNetBookValue().compareTo(BigDecimal.TEN) == -1) {
                                    fmsDprSubstation.setNetBookValue(BigDecimal.TEN);
                                    fmsDprSubstationBeanLocal.create(fmsDprSubstation);
                                    fmsDprSubstationBeanLocal.edit(dprlistSubstation.get(i));
                                    saveStat = 1;
                                } else {
                                    fmsDprSubstationBeanLocal.create(fmsDprSubstation);
                                    fmsDprSubstationBeanLocal.edit(dprlistSubstation.get(i));
                                    saveStat = 1;
                                }
                            }
                        }
                        if (saveStat == 1) {
                            JsfUtil.addSuccessMessage("Fixed asset depreciation is calculated and saved");
                        }
                        substationDataModel = new ListDataModel(new ArrayList(dprlistSubstation));
                    }
                    registeredItem();
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
        }
    }

}
