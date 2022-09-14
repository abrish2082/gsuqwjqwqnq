/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.depreciation;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.fixedasset.FmsDprHydropowerBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.fixedasset.FmsDprHydropower;
import et.gov.eep.mms.businessLogic.MmsFaHydroPowerBeanLocal;
import et.gov.eep.mms.entity.MmsFaHydropower;
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
@Named(value = "dPRHydropowerController")
@ViewScoped
public class DPRHydropowerController implements Serializable {

    @Inject
    SessionBean SessionBean;

    @Inject
    MmsFaHydropower mmsFaHydropower;
    @Inject
    FmsDprHydropower fmsDprHydropower;
    @Inject
    FmsAccountingPeriod AccountingPeriod;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @EJB
    MmsFaHydroPowerBeanLocal mmsFaHydroPowerBeanLocal;
    @EJB
    FmsDprHydropowerBeanLocal fmsDprHydropowerBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;

    List<MmsFaHydropower> hydropowerAssetList = new ArrayList<>();
    List<FmsDprHydropower> hydropowerDPRList = new ArrayList<>();
    List<FmsDprHydropower> dprlistHydropower = new ArrayList<>();
    List<FmsDprHydropower> filteredItems;

    private FmsDprHydropower selectedAsset;

    List<FmsDprHydropower> hydropowerDataList;
    DataModel<FmsDprHydropower> hydropowerDataModel = new ArrayDataModel<>();

    private boolean registeredPnl = true;
    private boolean newItemPnl = false;

    String tableBtnVal = "New Items";
    String saveCalc = "Calculate";

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

    public MmsFaHydropower getMmsFaHydropower() {
        if (mmsFaHydropower == null) {
            mmsFaHydropower = new MmsFaHydropower();
        }
        return mmsFaHydropower;
    }

    public void setMmsFaHydropower(MmsFaHydropower mmsFaHydropower) {
        this.mmsFaHydropower = mmsFaHydropower;
    }

    public FmsDprHydropower getFmsDprHydropower() {
        if (fmsDprHydropower == null) {
            fmsDprHydropower = new FmsDprHydropower();
        }
        return fmsDprHydropower;
    }

    public void setFmsDprHydropower(FmsDprHydropower fmsDprHydropower) {
        this.fmsDprHydropower = fmsDprHydropower;
    }

    public FmsAccountingPeriod getAccountingPeriod() {
        return AccountingPeriod;
    }

    public void setAccountingPeriod(FmsAccountingPeriod AccountingPeriod) {
        this.AccountingPeriod = AccountingPeriod;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public FmsAccountingPeriodBeanLocal getFmsAccountingPeriodBeanLocal() {
        return fmsAccountingPeriodBeanLocal;
    }

    public void setFmsAccountingPeriodBeanLocal(FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal) {
        this.fmsAccountingPeriodBeanLocal = fmsAccountingPeriodBeanLocal;
    }

    public List<MmsFaHydropower> getHydropowerAssetList() {
        return hydropowerAssetList;
    }

    public void setHydropowerAssetList(List<MmsFaHydropower> hydropowerAssetList) {
        this.hydropowerAssetList = hydropowerAssetList;
    }

    public List<FmsDprHydropower> getHydropowerDPRList() {
        return hydropowerDPRList;
    }

    public void setHydropowerDPRList(List<FmsDprHydropower> hydropowerDPRList) {
        this.hydropowerDPRList = hydropowerDPRList;
    }

    public List<FmsDprHydropower> getDprlistHydropower() {
        if (dprlistHydropower == null) {
            dprlistHydropower = new ArrayList<>();
        }
        return dprlistHydropower;
    }

    public void setDprlistHydropower(List<FmsDprHydropower> dprlistHydropower) {
        this.dprlistHydropower = dprlistHydropower;
    }

    public List<FmsDprHydropower> getHydropowerDataList() {
        return hydropowerDataList;
    }

    public void setHydropowerDataList(List<FmsDprHydropower> hydropowerDataList) {
        this.hydropowerDataList = hydropowerDataList;
    }

    public DataModel<FmsDprHydropower> getHydropowerDataModel() {
        return hydropowerDataModel;
    }

    public void setHydropowerDataModel(DataModel<FmsDprHydropower> hydropowerDataModel) {
        this.hydropowerDataModel = hydropowerDataModel;
    }

    public List<FmsDprHydropower> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<FmsDprHydropower> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public FmsDprHydropower getSelectedAsset() {
        return selectedAsset;
    }

    public void setSelectedAsset(FmsDprHydropower selectedAsset) {
        this.selectedAsset = selectedAsset;
    }

    public DPRHydropowerController() {
    }

    public void getActivePeriod() {
        AccountingPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        AccountingPeriod.getStartDate();
        AccountingPeriod.getEndDate();
        AccountingPeriod.getAcountigPeriodId();
    }

    @PostConstruct
    public void SearchTRAsset() {
        hydropowerDPRList = fmsDprHydropowerBeanLocal.findStatus1();
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
        hydropowerDPRList = null;
        hydropowerDPRList = null;
        dprlistHydropower = null;

        hydropowerAssetList = mmsFaHydroPowerBeanLocal.findNewItems();
        hydropowerDPRList = fmsDprHydropowerBeanLocal.findStatus1();
        SaveNewItems();
    }

    public void registeredItem() {
        registeredPnl = true;
        newItemPnl = false;

        hydropowerDPRList = null;
        hydropowerDPRList = null;
        dprlistHydropower = null;

        SearchTRAsset();
    }

    public void SaveNewItems() {
        for (int h = 0; h < hydropowerAssetList.size(); h++) {

            fmsDprHydropower = new FmsDprHydropower(null);
            fmsDprHydropower.setHpAssetId(hydropowerAssetList.get(h));
            fmsDprHydropower.setDprYear(BigDecimal.ZERO);
            fmsDprHydropower.setAccumulatedDpr(BigDecimal.ZERO);
            fmsDprHydropower.setAccountPeriod(AccountingPeriod);
            fmsDprHydropower.setStatus(1);
            getDprlistHydropower().add(fmsDprHydropower);
            fmsDprHydropower = new FmsDprHydropower(null);
        }
        if (!(dprlistHydropower == null)) {
            hydropowerDataModel = new ListDataModel(new ArrayList(dprlistHydropower));
        } else {
            hydropowerDataModel = null;
        }
    }

    public void calculateDPR() {

        if (!hydropowerDPRList.isEmpty()) {

            for (FmsDprHydropower hydropowerDPRList1 : hydropowerDPRList) {
                fmsDprHydropower.setDprHydropowerId(hydropowerDPRList1.getDprHydropowerId());
                fmsDprHydropower.setHpAssetId(hydropowerDPRList1.getHpAssetId());
                fmsDprHydropower.setDprYear(hydropowerDPRList1.getDprYear());
                fmsDprHydropower.setAccumulatedDpr(hydropowerDPRList1.getAccumulatedDpr());
                fmsDprHydropower.setNetBookValue(hydropowerDPRList1.getNetBookValue());
                fmsDprHydropower.setDataCardNo(hydropowerDPRList1.getDataCardNo());
                fmsDprHydropower.setRevaluationCost(hydropowerDPRList1.getRevaluationCost());
                fmsDprHydropower.setRevaluationServiceYear(hydropowerDPRList1.getRevaluationServiceYear());
                fmsDprHydropower.setAccountPeriod(hydropowerDPRList1.getAccountPeriod());
                fmsDprHydropower.setAccountNo(hydropowerDPRList1.getAccountNo());
                fmsDprHydropower.setRevaluationCost(hydropowerDPRList1.getRevaluationCost());
                fmsDprHydropower.setRevaluationServiceYear(hydropowerDPRList1.getRevaluationServiceYear());

                getDprlistHydropower().add(fmsDprHydropower);
                fmsDprHydropower = new FmsDprHydropower(null);
            }
        }

        hydropowerDataModel = new ListDataModel(new ArrayList(dprlistHydropower));

    }

    public void NewHydropower(FmsDprHydropower fmsDprHydropower) {
        hydropowerDataList = fmsDprHydropowerBeanLocal.fetchDprHydropowers(fmsDprHydropower);
        if (!hydropowerDataList.isEmpty()) {
            hydropowerDataModel = new ListDataModel(new ArrayList(hydropowerDataList));
        } else {
            hydropowerDataModel = null;
        }
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    public void populateDPR(SelectEvent event) {

        fmsDprHydropower = (FmsDprHydropower) event.getObject();

    }

    public void Add() {
        for (int i = 0; i < dprlistHydropower.size(); i++) {
            if (fmsDprHydropower.getHpAssetId() == dprlistHydropower.get(i).getHpAssetId()) {
                dprlistHydropower.set(i, fmsDprHydropower);
            }
        }
        hydropowerDataModel = new ListDataModel(new ArrayList(dprlistHydropower));
    }

    public void saveDataC() {

        for (int i = 0; i < dprlistHydropower.size(); i++) {
            if (fmsDprHydropower.getHpAssetId() == dprlistHydropower.get(i).getHpAssetId()) {
                dprlistHydropower.get(i).setDataCardNo(fmsDprHydropower.getDataCardNo());
            }
        }
        hydropowerDataModel = new ListDataModel(new ArrayList(dprlistHydropower));
    }

    public void saveHydropowerDPR() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveHydropowerDPR", dataset)) {
                if (newItemPnl == true && !dprlistHydropower.isEmpty()) {
                    int saveStat = 0;
                    for (int i = 0; i < dprlistHydropower.size(); i++) {
                        if (!(dprlistHydropower.get(i).getDataCardNo() == null)) {
                            fmsDprHydropowerBeanLocal.create(dprlistHydropower.get(i));
                            saveStat = 1;
                        }
                    }
                    hydropowerDataModel = new ListDataModel(new ArrayList(dprlistHydropower));
                    if (saveStat == 1) {
                        registeredItem();
                        saveStat = 0;
                        tableBtnVal = "New Items";
                        saveCalc = "Calculate";
                        JsfUtil.addSuccessMessage("Fixed Asset is Saved");
                    }
                } else if (registeredPnl == true) {
                    int saveStat = 0;
                    if (!dprlistHydropower.isEmpty()) {

                        for (int i = 0; i < dprlistHydropower.size(); i++) {
                            fmsDprHydropower = new FmsDprHydropower();

                            if (dprlistHydropower.get(i).getDprYear() == BigDecimal.ZERO && Objects.equals(dprlistHydropower.get(i).getAccountPeriod(), AccountingPeriod) && dprlistHydropower.get(i).getHpAssetId().getRevaluationCost() == null) {       //INITIALLY
                                String Smonth[] = dprlistHydropower.get(i).getHpAssetId().getHpInservice().split("/");
                                int Imonth = Integer.parseInt(Smonth[1]);
                                if (Imonth == 1) {
                                    fmsDprHydropower.setHpAssetId(dprlistHydropower.get(i).getHpAssetId());
                                    fmsDprHydropower.setDprYear(dprlistHydropower.get(i).getHpAssetId().getHpUnitCost().divide(new BigDecimal(dprlistHydropower.get(i).getHpAssetId().getHpServiceLife()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprHydropower.setAccumulatedDpr(dprlistHydropower.get(i).getDprYear());
                                    fmsDprHydropower.setNetBookValue(dprlistHydropower.get(i).getHpAssetId().getHpUnitCost().subtract(dprlistHydropower.get(i).getAccumulatedDpr()));
                                    fmsDprHydropower.setStatus(1);
                                    fmsDprHydropower.setAccountPeriod(AccountingPeriod);
                                    fmsDprHydropower.setAccountNo(dprlistHydropower.get(i).getAccountNo());
                                    fmsDprHydropower.setDataCardNo(dprlistHydropower.get(i).getDataCardNo());

                                } else {
                                    fmsDprHydropower.setHpAssetId(dprlistHydropower.get(i).getHpAssetId());
                                    fmsDprHydropower.setDprYear(((dprlistHydropower.get(i).getHpAssetId().getHpUnitCost().divide(new BigDecimal(dprlistHydropower.get(i).getHpAssetId().getHpServiceLife().doubleValue() * 12), 2, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(Imonth))).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsDprHydropower.setAccumulatedDpr(fmsDprHydropower.getDprYear());
                                    fmsDprHydropower.setNetBookValue(dprlistHydropower.get(i).getHpAssetId().getHpUnitCost().subtract(dprlistHydropower.get(i).getAccumulatedDpr()));
                                    fmsDprHydropower.setStatus(1);
                                    fmsDprHydropower.setAccountPeriod(AccountingPeriod);
                                    fmsDprHydropower.setAccountNo(dprlistHydropower.get(i).getAccountNo());
                                    fmsDprHydropower.setDataCardNo(dprlistHydropower.get(i).getDataCardNo());
                                }
                            } else if (dprlistHydropower.get(i).getDprYear() != BigDecimal.ZERO && !Objects.equals(dprlistHydropower.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId()) && dprlistHydropower.get(i).getHpAssetId().getRevaluationCost() == null) {       //Nth time

                                fmsDprHydropower.setHpAssetId(dprlistHydropower.get(i).getHpAssetId());
                                fmsDprHydropower.setDprYear(dprlistHydropower.get(i).getHpAssetId().getHpUnitCost().divide(new BigDecimal(dprlistHydropower.get(i).getHpAssetId().getHpServiceLife()), 2, RoundingMode.HALF_EVEN));
                                fmsDprHydropower.setAccumulatedDpr(fmsDprHydropower.getDprYear().add(dprlistHydropower.get(i).getAccumulatedDpr()));
                                fmsDprHydropower.setNetBookValue(dprlistHydropower.get(i).getHpAssetId().getHpUnitCost().subtract(dprlistHydropower.get(i).getAccumulatedDpr()));
                                fmsDprHydropower.setAccountPeriod(AccountingPeriod);
                                fmsDprHydropower.setStatus(1);
                                fmsDprHydropower.setAccountNo(dprlistHydropower.get(i).getAccountNo());
                                fmsDprHydropower.setDataCardNo(dprlistHydropower.get(i).getDataCardNo());
                            } else if (dprlistHydropower.get(i).getHpAssetId().getRevaluationCost() != null && !Objects.equals(dprlistHydropower.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId())) {

                                if (dprlistHydropower.get(i).getRevaluationCost() == null) {
                                    fmsDprHydropower.setHpAssetId(dprlistHydropower.get(i).getHpAssetId());
                                    fmsDprHydropower.setRevaluationCost(dprlistHydropower.get(i).getHpAssetId().getRevaluationCost());
                                    fmsDprHydropower.setRevaluationServiceYear(dprlistHydropower.get(i).getHpAssetId().getRevaluationServiceYear());
                                    fmsDprHydropower.setDprYear(dprlistHydropower.get(i).getHpAssetId().getRevaluationCost().divide(new BigDecimal(dprlistHydropower.get(i).getHpAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprHydropower.setAccumulatedDpr(dprlistHydropower.get(i).getAccumulatedDpr().add(fmsDprHydropower.getDprYear()));
                                    fmsDprHydropower.setNetBookValue(dprlistHydropower.get(i).getNetBookValue().subtract(fmsDprHydropower.getDprYear()));
                                    fmsDprHydropower.setAccountPeriod(AccountingPeriod);
                                    fmsDprHydropower.setStatus(1);
                                    fmsDprHydropower.setAccountNo(dprlistHydropower.get(i).getAccountNo());
                                    fmsDprHydropower.setDataCardNo(dprlistHydropower.get(i).getDataCardNo());
                                } else if (dprlistHydropower.get(i).getHpAssetId().getRevaluationCost() != null) {
                                    fmsDprHydropower.setHpAssetId(dprlistHydropower.get(i).getHpAssetId());
                                    fmsDprHydropower.setDprYear(dprlistHydropower.get(i).getHpAssetId().getRevaluationCost().divide(new BigDecimal(dprlistHydropower.get(i).getHpAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprHydropower.setAccumulatedDpr(dprlistHydropower.get(i).getAccumulatedDpr().add(fmsDprHydropower.getDprYear()));
                                    fmsDprHydropower.setNetBookValue(dprlistHydropower.get(i).getNetBookValue().subtract(fmsDprHydropower.getDprYear()));
                                    fmsDprHydropower.setAccountPeriod(AccountingPeriod);
                                    fmsDprHydropower.setStatus(1);
                                    fmsDprHydropower.setAccountNo(dprlistHydropower.get(i).getAccountNo());
                                    fmsDprHydropower.setDataCardNo(dprlistHydropower.get(i).getDataCardNo());
                                    fmsDprHydropower.setRevaluationCost(dprlistHydropower.get(i).getHpAssetId().getRevaluationCost());
                                    fmsDprHydropower.setRevaluationServiceYear(dprlistHydropower.get(i).getHpAssetId().getRevaluationServiceYear());
                                }
                            }
                            dprlistHydropower.get(i).setStatus(0);
                            if (fmsDprHydropower.getDprYear() != null && dprlistHydropower != null) {
                                if (fmsDprHydropower.getNetBookValue().compareTo(BigDecimal.TEN) == -1) {
                                    fmsDprHydropower.setNetBookValue(BigDecimal.TEN);
                                    fmsDprHydropowerBeanLocal.create(fmsDprHydropower);
                                    fmsDprHydropowerBeanLocal.edit(dprlistHydropower.get(i));
                                    saveStat = 1;
                                } else {
                                    fmsDprHydropowerBeanLocal.create(fmsDprHydropower);
                                    fmsDprHydropowerBeanLocal.edit(dprlistHydropower.get(i));
                                    saveStat = 1;
                                }
                            }
                        }
                        if (saveStat == 1) {
                            JsfUtil.addSuccessMessage("Fixed asset depreciation is calculated and saved");
                        }
                        hydropowerDataModel = new ListDataModel(new ArrayList(dprlistHydropower));
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
