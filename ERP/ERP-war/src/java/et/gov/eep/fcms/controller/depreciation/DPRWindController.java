/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.depreciation;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.fixedasset.FmsDprWindBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.fixedasset.FmsDprWind;
import et.gov.eep.mms.businessLogic.MmsFaWindBeanLocal;
import et.gov.eep.mms.entity.MmsFaWind;
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
@Named(value = "dPRWindController")
@ViewScoped
public class DPRWindController implements Serializable {

    @Inject
    SessionBean SessionBean;

    @Inject
    MmsFaWind mmsFaWind;
    @Inject
    FmsDprWind fmsDprWind;
    @Inject
    FmsAccountingPeriod AccountingPeriod;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @EJB
    MmsFaWindBeanLocal mmsFaWindBeanLocal;
    @EJB
    FmsDprWindBeanLocal fmsDprWindBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;

    List<MmsFaWind> windAssetList = new ArrayList<>();
    List<FmsDprWind> windDPRList = new ArrayList<>();
    List<FmsDprWind> dprlistWind = new ArrayList<>();
    List<FmsDprWind> filteredItems;

    private FmsDprWind selectedAsset;

    List<FmsDprWind> windDataList;
    DataModel<FmsDprWind> windDataModel = new ArrayDataModel<>();

    private boolean registeredPnl = true;
    private boolean newItemPnl = false;

    String tableBtnVal = "New Items";
    String saveCalc = "Calculate";

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

    public MmsFaWind getMmsFaWind() {
        return mmsFaWind;
    }

    public void setMmsFaWind(MmsFaWind mmsFaWind) {
        this.mmsFaWind = mmsFaWind;
    }

    public FmsDprWind getFmsDprWind() {
        return fmsDprWind;
    }

    public void setFmsDprWind(FmsDprWind fmsDprWind) {
        this.fmsDprWind = fmsDprWind;
    }

    public MmsFaWindBeanLocal getMmsFaWindBeanLocal() {
        return mmsFaWindBeanLocal;
    }

    public void setMmsFaWindBeanLocal(MmsFaWindBeanLocal mmsFaWindBeanLocal) {
        this.mmsFaWindBeanLocal = mmsFaWindBeanLocal;
    }

    public FmsAccountingPeriod getAccountPeriod() {
        return AccountingPeriod;
    }

    public void setAccountPeriod(FmsAccountingPeriod AccountingPeriod) {
        this.AccountingPeriod = AccountingPeriod;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public FmsDprWindBeanLocal getFmsDprWindBeanLocal() {
        return fmsDprWindBeanLocal;
    }

    public void setFmsDprWindBeanLocal(FmsDprWindBeanLocal fmsDprWindBeanLocal) {
        this.fmsDprWindBeanLocal = fmsDprWindBeanLocal;
    }

    public FmsAccountingPeriodBeanLocal getFmsAccountingPeriodBeanLocal() {
        return fmsAccountingPeriodBeanLocal;
    }

    public void setFmsAccountingPeriodBeanLocal(FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal) {
        this.fmsAccountingPeriodBeanLocal = fmsAccountingPeriodBeanLocal;
    }

    public FmsGeneralLedgerBeanLocal getFmsGeneralLedgerBeanLocal() {
        return fmsGeneralLedgerBeanLocal;
    }

    public void setFmsGeneralLedgerBeanLocal(FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal) {
        this.fmsGeneralLedgerBeanLocal = fmsGeneralLedgerBeanLocal;
    }

    public List<MmsFaWind> getWindAssetList() {
        return windAssetList;
    }

    public void setWindAssetList(List<MmsFaWind> windAssetList) {
        this.windAssetList = windAssetList;
    }

    public List<FmsDprWind> getWindDPRList() {
        return windDPRList;
    }

    public void setWindDPRList(List<FmsDprWind> windDPRList) {
        this.windDPRList = windDPRList;
    }

    public List<FmsDprWind> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<FmsDprWind> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public FmsDprWind getSelectedAsset() {
        return selectedAsset;
    }

    public void setSelectedAsset(FmsDprWind selectedAsset) {
        this.selectedAsset = selectedAsset;
    }

    public List<FmsDprWind> getWindDataList() {
        return windDataList;
    }

    public void setWindDataList(List<FmsDprWind> windDataList) {
        this.windDataList = windDataList;
    }

    public DataModel<FmsDprWind> getWindDataModel() {
        return windDataModel;
    }

    public void setWindDataModel(DataModel<FmsDprWind> windDataModel) {
        this.windDataModel = windDataModel;
    }

    public List<FmsDprWind> getDprlistWind() {
        if (dprlistWind == null) {
            dprlistWind = new ArrayList<>();
        }
        return dprlistWind;
    }

    public void setDprlistWind(List<FmsDprWind> dprlistWind) {
        this.dprlistWind = dprlistWind;
    }

    public DPRWindController() {
    }

    public void getActivePeriod() {
        AccountingPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        AccountingPeriod.getStartDate();
        AccountingPeriod.getEndDate();
        AccountingPeriod.getAcountigPeriodId();
    }

    @PostConstruct
    public void SearchTRAsset() {
        windDPRList = fmsDprWindBeanLocal.findStatus1();
        calculateDPR();
        getActivePeriod();
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
        windDPRList = null;
        windDPRList = null;
        dprlistWind = null;

        windAssetList = mmsFaWindBeanLocal.findNewItems();
        windDPRList = fmsDprWindBeanLocal.findStatus1();
        SaveNewItems();
    }

    public void registeredItem() {
        registeredPnl = true;
        newItemPnl = false;

        windDPRList = null;
        windDPRList = null;
        dprlistWind = null;

        SearchTRAsset();
    }

    public void SaveNewItems() {
        for (int h = 0; h < windAssetList.size(); h++) {

            fmsDprWind = new FmsDprWind(null);
            fmsDprWind.setWdAssetId(windAssetList.get(h));
            fmsDprWind.setDprYear(BigDecimal.ZERO);
            fmsDprWind.setAccumulatedDpr(BigDecimal.ZERO);
            fmsDprWind.setAccountPeriod(AccountingPeriod);
            fmsDprWind.setStatus(1);
            getDprlistWind().add(fmsDprWind);
            fmsDprWind = new FmsDprWind(null);

        }
        if (!(dprlistWind == null)) {
            windDataModel = new ListDataModel(new ArrayList(dprlistWind));
        } else {
            windDataModel = null;
        }
    }

    public void calculateDPR() {

        if (!windDPRList.isEmpty()) {

            for (FmsDprWind windDPRList1 : windDPRList) {
                fmsDprWind.setDprWindId(windDPRList1.getDprWindId());
                fmsDprWind.setWdAssetId(windDPRList1.getWdAssetId());
                fmsDprWind.setDprYear(windDPRList1.getDprYear());
                fmsDprWind.setAccumulatedDpr(windDPRList1.getAccumulatedDpr());
                fmsDprWind.setNetBookValue(windDPRList1.getNetBookValue());
                fmsDprWind.setDataCardNo(windDPRList1.getDataCardNo());
                fmsDprWind.setRevaluationCost(windDPRList1.getRevaluationCost());
                fmsDprWind.setRevaluationServiceYear(windDPRList1.getRevaluationServiceYear());
                fmsDprWind.setAccountPeriod(windDPRList1.getAccountPeriod());
                fmsDprWind.setAccountNo(windDPRList1.getAccountNo());
                fmsDprWind.setRevaluationCost(windDPRList1.getRevaluationCost());
                fmsDprWind.setRevaluationServiceYear(windDPRList1.getRevaluationServiceYear());

                getDprlistWind().add(fmsDprWind);
                fmsDprWind = new FmsDprWind(null);
            }
        }

        windDataModel = new ListDataModel(new ArrayList(dprlistWind));

    }

    public void NewWind(FmsDprWind fmsDprWind) {
        windDataList = fmsDprWindBeanLocal.fetchWind(fmsDprWind);
        if (!windDataList.isEmpty()) {
            windDataModel = new ListDataModel(new ArrayList(windDataList));
        } else {
            windDataModel = null;
        }
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    public void populateDPR(SelectEvent event) {

        fmsDprWind = (FmsDprWind) event.getObject();

    }

    public void Add() {
        for (int i = 0; i < dprlistWind.size(); i++) {
            if (fmsDprWind.getWdAssetId() == dprlistWind.get(i).getWdAssetId()) {
                dprlistWind.set(i, fmsDprWind);
            }
        }
        windDataModel = new ListDataModel(new ArrayList(dprlistWind));
    }

    public void saveDataC() {

        for (int i = 0; i < dprlistWind.size(); i++) {
            if (fmsDprWind.getWdAssetId() == dprlistWind.get(i).getWdAssetId()) {
                dprlistWind.get(i).setDataCardNo(fmsDprWind.getDataCardNo());
            }
        }
        windDataModel = new ListDataModel(new ArrayList(dprlistWind));
    }

    public void saveWindDPR() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveWindDPR", dataset)) {
                if (newItemPnl == true && !dprlistWind.isEmpty()) {
                    int saveStat = 0;
                    for (int i = 0; i < dprlistWind.size(); i++) {
                        if (!(dprlistWind.get(i).getDataCardNo() == null)) {
                            fmsDprWindBeanLocal.create(dprlistWind.get(i));
                            saveStat = 1;
                        }
                    }
                    windDataModel = new ListDataModel(new ArrayList(dprlistWind));
                    if (saveStat == 1) {
                        registeredItem();
                        saveStat = 0;
                        tableBtnVal = "New Items";
                        saveCalc = "Calculate";
                        JsfUtil.addSuccessMessage("Fixed Asset is Saved");
                    }
                } else if (registeredPnl == true) {
                    int saveStat = 0;
                    if (!dprlistWind.isEmpty()) {

                        for (int i = 0; i < dprlistWind.size(); i++) {
                            fmsDprWind = new FmsDprWind();

                            if (dprlistWind.get(i).getDprYear() == BigDecimal.ZERO && Objects.equals(dprlistWind.get(i).getAccountPeriod(), AccountingPeriod) && dprlistWind.get(i).getWdAssetId().getRevaluationCost() == null) {       //INITIALLY
                                String Smonth[] = dprlistWind.get(i).getWdAssetId().getWdInservice().split("/");
                                int Imonth = Integer.parseInt(Smonth[1]);
                                if (Imonth == 1) {
                                    fmsDprWind.setWdAssetId(dprlistWind.get(i).getWdAssetId());
                                    fmsDprWind.setDprYear(dprlistWind.get(i).getWdAssetId().getWdOriginalCost().divide(new BigDecimal(dprlistWind.get(i).getWdAssetId().getWdServiceLife()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprWind.setAccumulatedDpr(dprlistWind.get(i).getDprYear());
                                    fmsDprWind.setNetBookValue(dprlistWind.get(i).getWdAssetId().getWdOriginalCost().subtract(dprlistWind.get(i).getAccumulatedDpr()));
                                    fmsDprWind.setStatus(1);
                                    fmsDprWind.setAccountPeriod(AccountingPeriod);
                                    fmsDprWind.setAccountNo(dprlistWind.get(i).getAccountNo());
                                    fmsDprWind.setDataCardNo(dprlistWind.get(i).getDataCardNo());

                                } else {
                                    fmsDprWind.setWdAssetId(dprlistWind.get(i).getWdAssetId());
                                    fmsDprWind.setDprYear(((dprlistWind.get(i).getWdAssetId().getWdOriginalCost().divide(new BigDecimal(dprlistWind.get(i).getWdAssetId().getWdServiceLife().doubleValue() * 12), 2, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(Imonth))).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsDprWind.setAccumulatedDpr(fmsDprWind.getDprYear());
                                    fmsDprWind.setNetBookValue(dprlistWind.get(i).getWdAssetId().getWdOriginalCost().subtract(dprlistWind.get(i).getAccumulatedDpr()));
                                    fmsDprWind.setStatus(1);
                                    fmsDprWind.setAccountPeriod(AccountingPeriod);
                                    fmsDprWind.setAccountNo(dprlistWind.get(i).getAccountNo());
                                    fmsDprWind.setDataCardNo(dprlistWind.get(i).getDataCardNo());
                                }
                            } else if (dprlistWind.get(i).getDprYear() != BigDecimal.ZERO && !Objects.equals(dprlistWind.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId()) && dprlistWind.get(i).getWdAssetId().getRevaluationCost() == null) {       //Nth time

                                fmsDprWind.setWdAssetId(dprlistWind.get(i).getWdAssetId());
                                fmsDprWind.setDprYear(dprlistWind.get(i).getWdAssetId().getWdOriginalCost().divide(new BigDecimal(dprlistWind.get(i).getWdAssetId().getWdServiceLife()), 2, RoundingMode.HALF_EVEN));
                                fmsDprWind.setAccumulatedDpr(fmsDprWind.getDprYear().add(dprlistWind.get(i).getAccumulatedDpr()));
                                fmsDprWind.setNetBookValue(dprlistWind.get(i).getWdAssetId().getWdOriginalCost().subtract(dprlistWind.get(i).getAccumulatedDpr()));
                                fmsDprWind.setAccountPeriod(AccountingPeriod);
                                fmsDprWind.setStatus(1);
                                fmsDprWind.setAccountNo(dprlistWind.get(i).getAccountNo());
                                fmsDprWind.setDataCardNo(dprlistWind.get(i).getDataCardNo());
                            } else if (dprlistWind.get(i).getWdAssetId().getRevaluationCost() != null && !Objects.equals(dprlistWind.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId())) {

                                if (dprlistWind.get(i).getRevaluationCost() == null) {
                                    fmsDprWind.setWdAssetId(dprlistWind.get(i).getWdAssetId());
                                    fmsDprWind.setRevaluationCost(dprlistWind.get(i).getWdAssetId().getRevaluationCost());
                                    fmsDprWind.setRevaluationServiceYear(dprlistWind.get(i).getWdAssetId().getRevaluationServiceYear());
                                    fmsDprWind.setDprYear(dprlistWind.get(i).getWdAssetId().getRevaluationCost().divide(dprlistWind.get(i).getWdAssetId().getRevaluationServiceYear(), 2, RoundingMode.HALF_EVEN));
                                    fmsDprWind.setAccumulatedDpr(dprlistWind.get(i).getAccumulatedDpr().add(fmsDprWind.getDprYear()));
                                    fmsDprWind.setNetBookValue(dprlistWind.get(i).getNetBookValue().subtract(fmsDprWind.getDprYear()));
                                    fmsDprWind.setAccountPeriod(AccountingPeriod);
                                    fmsDprWind.setStatus(1);
                                    fmsDprWind.setAccountNo(dprlistWind.get(i).getAccountNo());
                                    fmsDprWind.setDataCardNo(dprlistWind.get(i).getDataCardNo());
                                } else if (dprlistWind.get(i).getWdAssetId().getRevaluationCost() != null) {
                                    fmsDprWind.setWdAssetId(dprlistWind.get(i).getWdAssetId());
                                    fmsDprWind.setDprYear(dprlistWind.get(i).getWdAssetId().getRevaluationCost().divide(dprlistWind.get(i).getWdAssetId().getRevaluationServiceYear(), 2, RoundingMode.HALF_EVEN));
                                    fmsDprWind.setAccumulatedDpr(dprlistWind.get(i).getAccumulatedDpr().add(fmsDprWind.getDprYear()));
                                    fmsDprWind.setNetBookValue(dprlistWind.get(i).getNetBookValue().subtract(fmsDprWind.getDprYear()));
                                    fmsDprWind.setAccountPeriod(AccountingPeriod);
                                    fmsDprWind.setStatus(1);
                                    fmsDprWind.setAccountNo(dprlistWind.get(i).getAccountNo());
                                    fmsDprWind.setDataCardNo(dprlistWind.get(i).getDataCardNo());
                                    fmsDprWind.setRevaluationCost(dprlistWind.get(i).getWdAssetId().getRevaluationCost());
                                    fmsDprWind.setRevaluationServiceYear(dprlistWind.get(i).getWdAssetId().getRevaluationServiceYear());
                                }
                            }
                            dprlistWind.get(i).setStatus(0);
                            if (fmsDprWind.getDprYear() != null && dprlistWind != null) {
                                if (fmsDprWind.getNetBookValue().compareTo(BigDecimal.TEN) == -1) {
                                    fmsDprWind.setNetBookValue(BigDecimal.TEN);
                                    fmsDprWindBeanLocal.create(fmsDprWind);
                                    fmsDprWindBeanLocal.edit(dprlistWind.get(i));
                                    saveStat = 1;
                                } else {
                                    fmsDprWindBeanLocal.create(fmsDprWind);
                                    fmsDprWindBeanLocal.edit(dprlistWind.get(i));
                                    saveStat = 1;
                                }
                            }
                        }
                        if (saveStat == 1) {
                            JsfUtil.addSuccessMessage("Fixed asset depreciation is calculated and saved");
                        }
                        windDataModel = new ListDataModel(new ArrayList(dprlistWind));
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
