/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.depreciation;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.fixedasset.FmsDprBuildingBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.fixedasset.FmsDprBuilding;
import et.gov.eep.mms.businessLogic.MmsFaBuildingBeanLocal;
import et.gov.eep.mms.controller.BuildinggController;
import et.gov.eep.mms.entity.MmsFaBuilding;
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
 *
 *
 * SELECT MMS_FA_BUILDING.BUILDING_ID FROM MMS_FA_BUILDING WHERE
 * MMS_FA_BUILDING.BUILDING_ID NOT IN (SELECT FMS_DPR_BUILDING.BU_ASSET_ID FROM
 * FMS_DPR_BUILDING);
 */
@Named(value = "dprBuildingController")
@ViewScoped
public class DPRBuildingController implements Serializable {

    @Inject
    SessionBean SessionBean;

    @Inject
    MmsFaBuilding mmsFaBuilding;
    @Inject
    FmsDprBuilding fmsDprBuilding;
    @Inject
    FmsAccountingPeriod AccountingPeriod;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @EJB
    MmsFaBuildingBeanLocal mmsFaBuildingBeanLocal;
    @EJB
    FmsDprBuildingBeanLocal fmsDprBuildingBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;

    List<MmsFaBuilding> buildingAssetList = new ArrayList<>();
    List<FmsDprBuilding> buildingDPRList = new ArrayList<>();
    List<FmsDprBuilding> dprlistBuilding = new ArrayList<>();
    List<FmsDprBuilding> filteredItems;

    private FmsDprBuilding selectedAsset;

    List<FmsDprBuilding> buildingDataList;
    DataModel<FmsDprBuilding> buildingDataModel = new ArrayDataModel<>();

    private boolean registeredPnl = true;
    private boolean newItemPnl = false;

    String tableBtnVal = "New Items";
    String saveCalc = "Calculate";

    public MmsFaBuilding getMmsFaBuilding() {
        if (mmsFaBuilding == null) {
            mmsFaBuilding = new MmsFaBuilding();
        }
        return mmsFaBuilding;
    }

    public void setMmsFaBuilding(MmsFaBuilding mmsFaBuilding) {
        this.mmsFaBuilding = mmsFaBuilding;
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

    public FmsDprBuilding getFmsDprBuilding() {
        if (fmsDprBuilding == null) {
            fmsDprBuilding = new FmsDprBuilding();
        }
        return fmsDprBuilding;
    }

    public void setFmsDprBuilding(FmsDprBuilding fmsDprBuilding) {
        this.fmsDprBuilding = fmsDprBuilding;
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

    public List<MmsFaBuilding> getBuildingAssetList() {
        return buildingAssetList;
    }

    public void setBuildingAssetList(List<MmsFaBuilding> buildingAssetList) {
        this.buildingAssetList = buildingAssetList;
    }

    public List<FmsDprBuilding> getBuildingDPRList() {
        return buildingDPRList;
    }

    public void setBuildingDPRList(List<FmsDprBuilding> buildingDPRList) {
        this.buildingDPRList = buildingDPRList;
    }

    public List<FmsDprBuilding> getDprlistBuilding() {
        if (dprlistBuilding == null) {
            dprlistBuilding = new ArrayList<>();
        }
        return dprlistBuilding;
    }

    public void setDprlistBuilding(List<FmsDprBuilding> dprlistBuilding) {
        this.dprlistBuilding = dprlistBuilding;
    }

    public List<FmsDprBuilding> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<FmsDprBuilding> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public FmsDprBuilding getSelectedAsset() {
        return selectedAsset;
    }

    public void setSelectedAsset(FmsDprBuilding selectedAsset) {
        this.selectedAsset = selectedAsset;
    }

    public List<FmsDprBuilding> getBuildingDataList() {
        return buildingDataList;
    }

    public void setBuildingDataList(List<FmsDprBuilding> buildingDataList) {
        this.buildingDataList = buildingDataList;
    }

    public DataModel<FmsDprBuilding> getBuildingDataModel() {
        if (buildingDataModel == null) {
            buildingDataModel = new ArrayDataModel<>();
        }
        return buildingDataModel;
    }

    public void setBuildingDataModel(DataModel<FmsDprBuilding> buildingDataModel) {
        this.buildingDataModel = buildingDataModel;
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

    public DPRBuildingController() {
    }

    @PostConstruct
    public void SearchTRAsset() {
        buildingDPRList = fmsDprBuildingBeanLocal.findStatus1();
        calculateDPR();
        getActivePeriod();
    }

    public void getActivePeriod() {
        AccountingPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        AccountingPeriod.getStartDate();
        AccountingPeriod.getEndDate();
        AccountingPeriod.getAcountigPeriodId();
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
        buildingDPRList = null;
        buildingDPRList = null;
        dprlistBuilding = null;

        buildingAssetList = mmsFaBuildingBeanLocal.findNewItems();
        buildingDPRList = fmsDprBuildingBeanLocal.findStatus1();
        SaveNewItems();
    }

    public void registeredItem() {
        registeredPnl = true;
        newItemPnl = false;

        buildingDPRList = null;
        buildingDPRList = null;
        dprlistBuilding = null;

        SearchTRAsset();
    }

    public void SaveNewItems() {
        for (int h = 0; h < buildingAssetList.size(); h++) {

            fmsDprBuilding = new FmsDprBuilding(null);
            fmsDprBuilding.setBuAssetId(buildingAssetList.get(h));
            fmsDprBuilding.setDprYear(BigDecimal.ZERO);
            fmsDprBuilding.setAccumulatedDpr(BigDecimal.ZERO);
            fmsDprBuilding.setAccountPeriod(AccountingPeriod);
            fmsDprBuilding.setStatus(1);
            getDprlistBuilding().add(fmsDprBuilding);
            fmsDprBuilding = new FmsDprBuilding(null);

        }
        if (!(dprlistBuilding == null)) {
            buildingDataModel = new ListDataModel(new ArrayList(dprlistBuilding));
        } else {
            buildingDataModel = null;
        }
    }

    public void calculateDPR() {

        if (!buildingDPRList.isEmpty()) {

            for (FmsDprBuilding buildingDPRList1 : buildingDPRList) {
                fmsDprBuilding.setDprBuildingId(buildingDPRList1.getDprBuildingId());
                fmsDprBuilding.setBuAssetId(buildingDPRList1.getBuAssetId());
                fmsDprBuilding.setDprYear(buildingDPRList1.getDprYear());
                fmsDprBuilding.setAccumulatedDpr(buildingDPRList1.getAccumulatedDpr());
                fmsDprBuilding.setNetBookValue(buildingDPRList1.getNetBookValue());
                fmsDprBuilding.setDataCardNo(buildingDPRList1.getDataCardNo());
                fmsDprBuilding.setRevaluationCost(buildingDPRList1.getRevaluationCost());
                fmsDprBuilding.setRevaluationServiceYear(buildingDPRList1.getRevaluationServiceYear());
                fmsDprBuilding.setAccountPeriod(buildingDPRList1.getAccountPeriod());
                fmsDprBuilding.setAccountNo(buildingDPRList1.getAccountNo());
                fmsDprBuilding.setRevaluationCost(buildingDPRList1.getRevaluationCost());
                fmsDprBuilding.setRevaluationServiceYear(buildingDPRList1.getRevaluationServiceYear());

                getDprlistBuilding().add(fmsDprBuilding);
                fmsDprBuilding = new FmsDprBuilding(null);
            }
        }

        buildingDataModel = new ListDataModel(new ArrayList(dprlistBuilding));

    }

    public void NewBuilding(FmsDprBuilding fmsDprBuilding) {
        buildingDataList = fmsDprBuildingBeanLocal.fetchBuilding(fmsDprBuilding);
        if (!buildingDataList.isEmpty()) {
            buildingDataModel = new ListDataModel(new ArrayList(buildingDataList));
        } else {
            buildingDataModel = null;
        }
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    public void populateDPR(SelectEvent event) {

        fmsDprBuilding = (FmsDprBuilding) event.getObject();

    }

    public void Add() {
        for (int i = 0; i < dprlistBuilding.size(); i++) {
            if (fmsDprBuilding.getBuAssetId() == dprlistBuilding.get(i).getBuAssetId()) {
                dprlistBuilding.set(i, fmsDprBuilding);
            }
        }
        buildingDataModel = new ListDataModel(new ArrayList(dprlistBuilding));
    }

    public void saveDataC() {

        for (int i = 0; i < dprlistBuilding.size(); i++) {
            if (fmsDprBuilding.getBuAssetId() == dprlistBuilding.get(i).getBuAssetId()) {
                dprlistBuilding.get(i).setDataCardNo(fmsDprBuilding.getDataCardNo());
            }
        }
        buildingDataModel = new ListDataModel(new ArrayList(dprlistBuilding));
    }

    public void saveBuildingDPR() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveBuildingDPR", dataset)) {
                if (newItemPnl == true && !dprlistBuilding.isEmpty()) {
                    int saveStat = 0;
                    for (int i = 0; i < dprlistBuilding.size(); i++) {
                        if (!(dprlistBuilding.get(i).getDataCardNo() == null)) {
                            fmsDprBuildingBeanLocal.create(dprlistBuilding.get(i));
                            saveStat = 1;
                        }
                    }
                    buildingDataModel = new ListDataModel(new ArrayList(dprlistBuilding));
                    if (saveStat == 1) {
                        registeredItem();
                        saveStat = 0;
                        tableBtnVal = "New Items";
                        saveCalc = "Calculate";
                        JsfUtil.addSuccessMessage("Fixed Asset is Saved");
                    }
                } else if (registeredPnl == true) {
                    int saveStat = 0;
                    if (!dprlistBuilding.isEmpty()) {
                        for (int i = 0; i < dprlistBuilding.size(); i++) {
                            fmsDprBuilding = new FmsDprBuilding();
                            if (dprlistBuilding.get(i).getDprYear() == BigDecimal.ZERO && Objects.equals(dprlistBuilding.get(i).getAccountPeriod(), AccountingPeriod) && dprlistBuilding.get(i).getBuAssetId().getRevaluationCost() == null) {       //INITIALLY
                                String Smonth[] = dprlistBuilding.get(i).getBuAssetId().getBuInservice().split("/");
                                int Imonth = Integer.parseInt(Smonth[1]);
                                if (Imonth == 1) {
                                    fmsDprBuilding.setBuAssetId(dprlistBuilding.get(i).getBuAssetId());
                                    fmsDprBuilding.setDprYear(dprlistBuilding.get(i).getBuAssetId().getBuUnitCost().divide(new BigDecimal(dprlistBuilding.get(i).getBuAssetId().getBuServiceLife()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprBuilding.setAccumulatedDpr(dprlistBuilding.get(i).getDprYear());
                                    fmsDprBuilding.setNetBookValue(dprlistBuilding.get(i).getBuAssetId().getBuUnitCost().subtract(dprlistBuilding.get(i).getAccumulatedDpr()));
                                    fmsDprBuilding.setStatus(1);
                                    fmsDprBuilding.setAccountPeriod(AccountingPeriod);
                                    fmsDprBuilding.setAccountNo(dprlistBuilding.get(i).getAccountNo());
                                    fmsDprBuilding.setDataCardNo(dprlistBuilding.get(i).getDataCardNo());

                                } else {
                                    fmsDprBuilding.setBuAssetId(dprlistBuilding.get(i).getBuAssetId());
                                    fmsDprBuilding.setDprYear(((dprlistBuilding.get(i).getBuAssetId().getBuUnitCost().divide(new BigDecimal(dprlistBuilding.get(i).getBuAssetId().getBuServiceLife().doubleValue() * 12), 2, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(Imonth))).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsDprBuilding.setAccumulatedDpr(fmsDprBuilding.getDprYear());
                                    fmsDprBuilding.setNetBookValue(dprlistBuilding.get(i).getBuAssetId().getBuUnitCost().subtract(dprlistBuilding.get(i).getAccumulatedDpr()));
                                    fmsDprBuilding.setStatus(1);
                                    fmsDprBuilding.setAccountPeriod(AccountingPeriod);
                                    fmsDprBuilding.setAccountNo(dprlistBuilding.get(i).getAccountNo());
                                    fmsDprBuilding.setDataCardNo(dprlistBuilding.get(i).getDataCardNo());
                                }
                            } else if (dprlistBuilding.get(i).getDprYear() != BigDecimal.ZERO && !Objects.equals(dprlistBuilding.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId()) && dprlistBuilding.get(i).getBuAssetId().getRevaluationCost() == null) {       //Nth time

                                fmsDprBuilding.setBuAssetId(dprlistBuilding.get(i).getBuAssetId());
                                fmsDprBuilding.setDprYear(dprlistBuilding.get(i).getBuAssetId().getBuUnitCost().divide(new BigDecimal(dprlistBuilding.get(i).getBuAssetId().getBuServiceLife()), 2, RoundingMode.HALF_EVEN));
                                fmsDprBuilding.setAccumulatedDpr(fmsDprBuilding.getDprYear().add(dprlistBuilding.get(i).getAccumulatedDpr()));
                                fmsDprBuilding.setNetBookValue(dprlistBuilding.get(i).getBuAssetId().getBuUnitCost().subtract(dprlistBuilding.get(i).getAccumulatedDpr()));
                                fmsDprBuilding.setAccountPeriod(AccountingPeriod);
                                fmsDprBuilding.setStatus(1);
                                fmsDprBuilding.setAccountNo(dprlistBuilding.get(i).getAccountNo());
                                fmsDprBuilding.setDataCardNo(dprlistBuilding.get(i).getDataCardNo());
                            } else if (dprlistBuilding.get(i).getBuAssetId().getRevaluationCost() != null && !Objects.equals(dprlistBuilding.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId())) {

                                if (dprlistBuilding.get(i).getRevaluationCost() == null) {
                                    fmsDprBuilding.setBuAssetId(dprlistBuilding.get(i).getBuAssetId());
                                    fmsDprBuilding.setRevaluationCost(dprlistBuilding.get(i).getBuAssetId().getRevaluationCost());
                                    fmsDprBuilding.setRevaluationServiceYear(dprlistBuilding.get(i).getBuAssetId().getRevaluationServiceYear());
                                    fmsDprBuilding.setDprYear(dprlistBuilding.get(i).getBuAssetId().getRevaluationCost().divide(new BigDecimal(dprlistBuilding.get(i).getBuAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprBuilding.setAccumulatedDpr(dprlistBuilding.get(i).getAccumulatedDpr().add(fmsDprBuilding.getDprYear()));
                                    fmsDprBuilding.setNetBookValue(dprlistBuilding.get(i).getNetBookValue().subtract(fmsDprBuilding.getDprYear()));
                                    fmsDprBuilding.setAccountPeriod(AccountingPeriod);
                                    fmsDprBuilding.setStatus(1);
                                    fmsDprBuilding.setAccountNo(dprlistBuilding.get(i).getAccountNo());
                                    fmsDprBuilding.setDataCardNo(dprlistBuilding.get(i).getDataCardNo());
                                } else if (dprlistBuilding.get(i).getBuAssetId().getRevaluationCost() != null) {
                                    fmsDprBuilding.setBuAssetId(dprlistBuilding.get(i).getBuAssetId());
                                    fmsDprBuilding.setDprYear(dprlistBuilding.get(i).getBuAssetId().getRevaluationCost().divide(new BigDecimal(dprlistBuilding.get(i).getBuAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprBuilding.setAccumulatedDpr(dprlistBuilding.get(i).getAccumulatedDpr().add(fmsDprBuilding.getDprYear()));
                                    fmsDprBuilding.setNetBookValue(dprlistBuilding.get(i).getNetBookValue().subtract(fmsDprBuilding.getDprYear()));
                                    fmsDprBuilding.setAccountPeriod(AccountingPeriod);
                                    fmsDprBuilding.setStatus(1);
                                    fmsDprBuilding.setAccountNo(dprlistBuilding.get(i).getAccountNo());
                                    fmsDprBuilding.setDataCardNo(dprlistBuilding.get(i).getDataCardNo());
                                    fmsDprBuilding.setRevaluationCost(dprlistBuilding.get(i).getBuAssetId().getRevaluationCost());
                                    fmsDprBuilding.setRevaluationServiceYear(dprlistBuilding.get(i).getBuAssetId().getRevaluationServiceYear());
                                }
                            }
                            dprlistBuilding.get(i).setStatus(0);
                            if (fmsDprBuilding.getDprYear() != null && dprlistBuilding != null) {
                                if (fmsDprBuilding.getNetBookValue().compareTo(BigDecimal.TEN) == -1) {
                                    fmsDprBuilding.setNetBookValue(BigDecimal.TEN);
                                    fmsDprBuildingBeanLocal.create(fmsDprBuilding);
                                    fmsDprBuildingBeanLocal.edit(dprlistBuilding.get(i));
                                    saveStat = 1;
                                } else {
                                    fmsDprBuildingBeanLocal.create(fmsDprBuilding);
                                    fmsDprBuildingBeanLocal.edit(dprlistBuilding.get(i));
                                    saveStat = 1;
                                }

                            }
                        }
                        if (saveStat == 1) {
                            JsfUtil.addSuccessMessage("Fixed asset depreciation is calculated and saved");
                        }
                        buildingDataModel = new ListDataModel(new ArrayList(dprlistBuilding));
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

    public String navigate() {
        return "success";
    }

    BuildinggController mmsBuilding = new BuildinggController();

//    public void navigationListner(ActionEvent event){
//        mmsFaBuilding = getBuildingDataModel().getRowData().getBuAssetId();
//        mmsBuilding.displayData(mmsFaBuilding);
//    }
}
