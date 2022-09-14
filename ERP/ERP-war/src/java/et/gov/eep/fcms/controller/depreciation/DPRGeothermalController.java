/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.depreciation;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.fixedasset.FmsDprGeothermalBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.fixedasset.FmsDprGeothermal;
import et.gov.eep.mms.businessLogic.MmsFaGeothermalBeanLocal;
import et.gov.eep.mms.entity.MmsFaGeothermal;
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
@Named(value = "dPRGeothermalController")
@ViewScoped
public class DPRGeothermalController implements Serializable {

    @Inject
    SessionBean SessionBean;

    @Inject
    MmsFaGeothermal mmsFaGeothermal;
    @Inject
    FmsDprGeothermal fmsDprGeothermal;
    @Inject
    FmsAccountingPeriod AccountingPeriod;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @EJB
    MmsFaGeothermalBeanLocal mmsFaGeothermalBeanLocal;
    @EJB
    FmsDprGeothermalBeanLocal fmsDprGeothermalBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;

    List<MmsFaGeothermal> geothermalAssetList = new ArrayList<>();
    List<FmsDprGeothermal> geothermalDPRList = new ArrayList<>();
    List<FmsDprGeothermal> dprlistGeothermal = new ArrayList<>();
    List<FmsDprGeothermal> filteredItems;

    private FmsDprGeothermal selectedAsset;

    List<FmsDprGeothermal> geothermalDataList;
    DataModel<FmsDprGeothermal> geothermalDataModel = new ArrayDataModel<>();

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

    public MmsFaGeothermal getMmsFaGeothermal() {
        return mmsFaGeothermal;
    }

    public void setMmsFaGeothermal(MmsFaGeothermal mmsFaGeothermal) {
        this.mmsFaGeothermal = mmsFaGeothermal;
    }

    public FmsDprGeothermal getFmsDprGeothermal() {
        return fmsDprGeothermal;
    }

    public void setFmsDprGeothermal(FmsDprGeothermal fmsDprGeothermal) {
        this.fmsDprGeothermal = fmsDprGeothermal;
    }

    public MmsFaGeothermalBeanLocal getMmsFaGeothermalBeanLocal() {
        return mmsFaGeothermalBeanLocal;
    }

    public void setMmsFaGeothermalBeanLocal(MmsFaGeothermalBeanLocal mmsFaGeothermalBeanLocal) {
        this.mmsFaGeothermalBeanLocal = mmsFaGeothermalBeanLocal;
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

    public FmsDprGeothermalBeanLocal getFmsDprGeothermalBeanLocal() {
        return fmsDprGeothermalBeanLocal;
    }

    public void setFmsDprGeothermalBeanLocal(FmsDprGeothermalBeanLocal fmsDprGeothermalBeanLocal) {
        this.fmsDprGeothermalBeanLocal = fmsDprGeothermalBeanLocal;
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

    public List<MmsFaGeothermal> getGeothermalAssetList() {
        return geothermalAssetList;
    }

    public void setGeothermalAssetList(List<MmsFaGeothermal> geothermalAssetList) {
        this.geothermalAssetList = geothermalAssetList;
    }

    public List<FmsDprGeothermal> getGeothermalDPRList() {
        return geothermalDPRList;
    }

    public void setGeothermalDPRList(List<FmsDprGeothermal> geothermalDPRList) {
        this.geothermalDPRList = geothermalDPRList;
    }

    public List<FmsDprGeothermal> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<FmsDprGeothermal> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public FmsDprGeothermal getSelectedAsset() {
        return selectedAsset;
    }

    public void setSelectedAsset(FmsDprGeothermal selectedAsset) {
        this.selectedAsset = selectedAsset;
    }

    public List<FmsDprGeothermal> getGeothermalDataList() {
        return geothermalDataList;
    }

    public void setGeothermalDataList(List<FmsDprGeothermal> geothermalDataList) {
        this.geothermalDataList = geothermalDataList;
    }

    public DataModel<FmsDprGeothermal> getGeothermalDataModel() {
        return geothermalDataModel;
    }

    public void setGeothermalDataModel(DataModel<FmsDprGeothermal> geothermalDataModel) {
        this.geothermalDataModel = geothermalDataModel;
    }

    public List<FmsDprGeothermal> getDprlistGeothermal() {
        if (dprlistGeothermal == null) {
            dprlistGeothermal = new ArrayList<>();
        }
        return dprlistGeothermal;
    }

    public void setDprlistGeothermal(List<FmsDprGeothermal> dprlistGeothermal) {
        this.dprlistGeothermal = dprlistGeothermal;
    }

    public DPRGeothermalController() {
    }

    public void getActivePeriod() {
        AccountingPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        AccountingPeriod.getStartDate();
        AccountingPeriod.getEndDate();
        AccountingPeriod.getAcountigPeriodId();
    }

    @PostConstruct
    public void SearchTRAsset() {
        geothermalDPRList = fmsDprGeothermalBeanLocal.findStatus1();
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
        geothermalDPRList = null;
        geothermalDPRList = null;
        dprlistGeothermal = null;

        geothermalAssetList = mmsFaGeothermalBeanLocal.findNewItems();
        geothermalDPRList = fmsDprGeothermalBeanLocal.findStatus1();
        SaveNewItems();
    }

    public void SaveNewItems() {
        for (int h = 0; h < geothermalAssetList.size(); h++) {

            fmsDprGeothermal = new FmsDprGeothermal(null);
            fmsDprGeothermal.setGeoAssetId(geothermalAssetList.get(h));
            fmsDprGeothermal.setDprYear(BigDecimal.ZERO);
            fmsDprGeothermal.setAccumulatedDpr(BigDecimal.ZERO);
            fmsDprGeothermal.setAccountPeriod(AccountingPeriod);
            fmsDprGeothermal.setStatus(1);
            getDprlistGeothermal().add(fmsDprGeothermal);
            fmsDprGeothermal = new FmsDprGeothermal(null);

        }
        if (!(dprlistGeothermal == null)) {
            geothermalDataModel = new ListDataModel(new ArrayList(dprlistGeothermal));
        } else {
            geothermalDataModel = null;
        }
    }

    public void registeredItem() {
        registeredPnl = true;
        newItemPnl = false;

        geothermalDPRList = null;
        geothermalDPRList = null;
        dprlistGeothermal = null;

        SearchTRAsset();
    }

    public void calculateDPR() {

        if (!geothermalDPRList.isEmpty()) {

            for (FmsDprGeothermal geothermalDPRList1 : geothermalDPRList) {
                fmsDprGeothermal.setDprGeothermalId(geothermalDPRList1.getDprGeothermalId());
                fmsDprGeothermal.setGeoAssetId(geothermalDPRList1.getGeoAssetId());
                fmsDprGeothermal.setDprYear(geothermalDPRList1.getDprYear());
                fmsDprGeothermal.setAccumulatedDpr(geothermalDPRList1.getAccumulatedDpr());
                fmsDprGeothermal.setNetBookValue(geothermalDPRList1.getNetBookValue());
                fmsDprGeothermal.setDataCardNo(geothermalDPRList1.getDataCardNo());
                fmsDprGeothermal.setRevaluationCost(geothermalDPRList1.getRevaluationCost());
                fmsDprGeothermal.setRevaluationServiceYear(geothermalDPRList1.getRevaluationServiceYear());
                fmsDprGeothermal.setAccountPeriod(geothermalDPRList1.getAccountPeriod());
                fmsDprGeothermal.setAccountNo(geothermalDPRList1.getAccountNo());
                fmsDprGeothermal.setRevaluationCost(geothermalDPRList1.getRevaluationCost());
                fmsDprGeothermal.setRevaluationServiceYear(geothermalDPRList1.getRevaluationServiceYear());

                getDprlistGeothermal().add(fmsDprGeothermal);
                fmsDprGeothermal = new FmsDprGeothermal(null);
            }
        }

        geothermalDataModel = new ListDataModel(new ArrayList(dprlistGeothermal));

    }

    public void NewGeothermal(FmsDprGeothermal fmsDprGeothermal) {
        geothermalDataList = fmsDprGeothermalBeanLocal.fetchGeothermal(fmsDprGeothermal);
        if (!geothermalDataList.isEmpty()) {
            geothermalDataModel = new ListDataModel(new ArrayList(geothermalDataList));
        } else {
            geothermalDataModel = null;
        }
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    public void populateDPR(SelectEvent event) {

        fmsDprGeothermal = (FmsDprGeothermal) event.getObject();

    }

    public void Add() {
        for (int i = 0; i < dprlistGeothermal.size(); i++) {
            if (fmsDprGeothermal.getGeoAssetId() == dprlistGeothermal.get(i).getGeoAssetId()) {
                dprlistGeothermal.set(i, fmsDprGeothermal);
            }
        }
        geothermalDataModel = new ListDataModel(new ArrayList(dprlistGeothermal));
    }

    public void saveDataC() {

        for (int i = 0; i < dprlistGeothermal.size(); i++) {
            if (fmsDprGeothermal.getGeoAssetId() == dprlistGeothermal.get(i).getGeoAssetId()) {
                dprlistGeothermal.get(i).setDataCardNo(fmsDprGeothermal.getDataCardNo());
            }
        }
        geothermalDataModel = new ListDataModel(new ArrayList(dprlistGeothermal));
    }

    public void saveGeothermalDPR() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveGeothermalDPR", dataset)) {

                if (newItemPnl == true && !dprlistGeothermal.isEmpty()) {
                    int saveStat = 0;
                    for (int i = 0; i < dprlistGeothermal.size(); i++) {
                        if (!(dprlistGeothermal.get(i).getDataCardNo() == null)) {
                            fmsDprGeothermalBeanLocal.create(dprlistGeothermal.get(i));
                            saveStat = 1;
                        }
                    }
                    geothermalDataModel = new ListDataModel(new ArrayList(dprlistGeothermal));
                    if (saveStat == 1) {
                        registeredItem();
                        saveStat = 0;
                        tableBtnVal = "New Items";
                        saveCalc = "Calculate";
                        JsfUtil.addSuccessMessage("Fixed Asset is Saved");
                    }
                } else if (registeredPnl == true) {
                    int saveStat = 0;

                    if (!dprlistGeothermal.isEmpty()) {

                        for (int i = 0; i < dprlistGeothermal.size(); i++) {
                            fmsDprGeothermal = new FmsDprGeothermal();

                            if (dprlistGeothermal.get(i).getDprYear() == BigDecimal.ZERO && Objects.equals(dprlistGeothermal.get(i).getAccountPeriod(), AccountingPeriod) && dprlistGeothermal.get(i).getGeoAssetId().getRevaluationCost() == null) {       //INITIALLY
                                String Smonth[] = dprlistGeothermal.get(i).getGeoAssetId().getGtInService().split("/");
                                int Imonth = Integer.parseInt(Smonth[1]);
                                if (Imonth == 1) {
                                    fmsDprGeothermal.setGeoAssetId(dprlistGeothermal.get(i).getGeoAssetId());
                                    fmsDprGeothermal.setDprYear(dprlistGeothermal.get(i).getGeoAssetId().getGtUnitCost().divide(new BigDecimal(dprlistGeothermal.get(i).getGeoAssetId().getGtServiceLife()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprGeothermal.setAccumulatedDpr(dprlistGeothermal.get(i).getDprYear());
                                    fmsDprGeothermal.setNetBookValue(dprlistGeothermal.get(i).getGeoAssetId().getGtUnitCost().subtract(dprlistGeothermal.get(i).getAccumulatedDpr()));
                                    fmsDprGeothermal.setStatus(1);
                                    fmsDprGeothermal.setAccountPeriod(AccountingPeriod);
                                    fmsDprGeothermal.setAccountNo(dprlistGeothermal.get(i).getAccountNo());
                                    fmsDprGeothermal.setDataCardNo(dprlistGeothermal.get(i).getDataCardNo());

                                } else {
                                    fmsDprGeothermal.setGeoAssetId(dprlistGeothermal.get(i).getGeoAssetId());
                                    fmsDprGeothermal.setDprYear(((dprlistGeothermal.get(i).getGeoAssetId().getGtUnitCost().divide(new BigDecimal(dprlistGeothermal.get(i).getGeoAssetId().getGtServiceLife().doubleValue() * 12), 2, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(Imonth))).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsDprGeothermal.setAccumulatedDpr(fmsDprGeothermal.getDprYear());
                                    fmsDprGeothermal.setNetBookValue(dprlistGeothermal.get(i).getGeoAssetId().getGtUnitCost().subtract(dprlistGeothermal.get(i).getAccumulatedDpr()));
                                    fmsDprGeothermal.setStatus(1);
                                    fmsDprGeothermal.setAccountPeriod(AccountingPeriod);
                                    fmsDprGeothermal.setAccountNo(dprlistGeothermal.get(i).getAccountNo());
                                    fmsDprGeothermal.setDataCardNo(dprlistGeothermal.get(i).getDataCardNo());
                                }
                            } else if (dprlistGeothermal.get(i).getDprYear() != BigDecimal.ZERO && !Objects.equals(dprlistGeothermal.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId()) && dprlistGeothermal.get(i).getGeoAssetId().getRevaluationCost() == null) {       //Nth time

                                fmsDprGeothermal.setGeoAssetId(dprlistGeothermal.get(i).getGeoAssetId());
                                fmsDprGeothermal.setDprYear(dprlistGeothermal.get(i).getGeoAssetId().getGtUnitCost().divide(new BigDecimal(dprlistGeothermal.get(i).getGeoAssetId().getGtServiceLife()), 2, RoundingMode.HALF_EVEN));
                                fmsDprGeothermal.setAccumulatedDpr(fmsDprGeothermal.getDprYear().add(dprlistGeothermal.get(i).getAccumulatedDpr()));
                                fmsDprGeothermal.setNetBookValue(dprlistGeothermal.get(i).getGeoAssetId().getGtUnitCost().subtract(dprlistGeothermal.get(i).getAccumulatedDpr()));
                                fmsDprGeothermal.setAccountPeriod(AccountingPeriod);
                                fmsDprGeothermal.setStatus(1);
                                fmsDprGeothermal.setAccountNo(dprlistGeothermal.get(i).getAccountNo());
                                fmsDprGeothermal.setDataCardNo(dprlistGeothermal.get(i).getDataCardNo());
                            } else if (dprlistGeothermal.get(i).getGeoAssetId().getRevaluationCost() != null && !Objects.equals(dprlistGeothermal.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId())) {

                                if (dprlistGeothermal.get(i).getRevaluationCost() == null) {
                                    fmsDprGeothermal.setGeoAssetId(dprlistGeothermal.get(i).getGeoAssetId());
                                    fmsDprGeothermal.setRevaluationCost(dprlistGeothermal.get(i).getGeoAssetId().getRevaluationCost());
                                    fmsDprGeothermal.setRevaluationServiceYear(dprlistGeothermal.get(i).getGeoAssetId().getRevaluationServiceYear());
                                    fmsDprGeothermal.setDprYear(dprlistGeothermal.get(i).getGeoAssetId().getRevaluationCost().divide(new BigDecimal(dprlistGeothermal.get(i).getGeoAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprGeothermal.setAccumulatedDpr(dprlistGeothermal.get(i).getAccumulatedDpr().add(fmsDprGeothermal.getDprYear()));
                                    fmsDprGeothermal.setNetBookValue(dprlistGeothermal.get(i).getNetBookValue().subtract(fmsDprGeothermal.getDprYear()));
                                    fmsDprGeothermal.setAccountPeriod(AccountingPeriod);
                                    fmsDprGeothermal.setStatus(1);
                                    fmsDprGeothermal.setAccountNo(dprlistGeothermal.get(i).getAccountNo());
                                    fmsDprGeothermal.setDataCardNo(dprlistGeothermal.get(i).getDataCardNo());
                                } else if (dprlistGeothermal.get(i).getGeoAssetId().getRevaluationCost() != null) {
                                    fmsDprGeothermal.setGeoAssetId(dprlistGeothermal.get(i).getGeoAssetId());
                                    fmsDprGeothermal.setDprYear(dprlistGeothermal.get(i).getGeoAssetId().getRevaluationCost().divide(new BigDecimal(dprlistGeothermal.get(i).getGeoAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprGeothermal.setAccumulatedDpr(dprlistGeothermal.get(i).getAccumulatedDpr().add(fmsDprGeothermal.getDprYear()));
                                    fmsDprGeothermal.setNetBookValue(dprlistGeothermal.get(i).getNetBookValue().subtract(fmsDprGeothermal.getDprYear()));
                                    fmsDprGeothermal.setAccountPeriod(AccountingPeriod);
                                    fmsDprGeothermal.setStatus(1);
                                    fmsDprGeothermal.setAccountNo(dprlistGeothermal.get(i).getAccountNo());
                                    fmsDprGeothermal.setDataCardNo(dprlistGeothermal.get(i).getDataCardNo());
                                    fmsDprGeothermal.setRevaluationCost(dprlistGeothermal.get(i).getGeoAssetId().getRevaluationCost());
                                    fmsDprGeothermal.setRevaluationServiceYear(dprlistGeothermal.get(i).getGeoAssetId().getRevaluationServiceYear());
                                }
                            }
                            dprlistGeothermal.get(i).setStatus(0);
                            if (fmsDprGeothermal.getDprYear() != null && dprlistGeothermal != null) {
                                if (fmsDprGeothermal.getNetBookValue().compareTo(BigDecimal.TEN) == -1) {
                                    fmsDprGeothermal.setNetBookValue(BigDecimal.TEN);
                                    fmsDprGeothermalBeanLocal.create(fmsDprGeothermal);
                                    fmsDprGeothermalBeanLocal.edit(dprlistGeothermal.get(i));
                                    saveStat = 1;
                                } else {
                                    fmsDprGeothermalBeanLocal.create(fmsDprGeothermal);
                                    fmsDprGeothermalBeanLocal.edit(dprlistGeothermal.get(i));
                                    saveStat = 1;
                                }
                            }
                        }
                        if (saveStat == 1) {
                            JsfUtil.addSuccessMessage("Fixed asset depreciation is calculated and saved");
                        }
                        geothermalDataModel = new ListDataModel(new ArrayList(dprlistGeothermal));
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
