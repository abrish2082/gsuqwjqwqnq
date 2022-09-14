/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.depreciation;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.fixedasset.FmsDprTransmissionBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.fixedasset.FmsDprTransmisson;
import et.gov.eep.mms.businessLogic.MmsFaTransmissionBeanLocal;
import et.gov.eep.mms.entity.MmsFaTransmission;
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
@Named(value = "dprTrasmissionController")
@ViewScoped
public class DPRTransmissionController implements Serializable {

    @Inject
    SessionBean SessionBean;

    @Inject
    MmsFaTransmission mmsFaTransmission;
    @Inject
    FmsDprTransmisson fmsDprTransmisson;
    @Inject
    FmsAccountingPeriod AccountingPeriod;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @EJB
    MmsFaTransmissionBeanLocal mmsFaTransmissionBeanLocal;
    @EJB
    FmsDprTransmissionBeanLocal fmsDprTransmissonBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;

    List<MmsFaTransmission> transmissionAssetList = new ArrayList<>();
    List<FmsDprTransmisson> transmissionDPRList = new ArrayList<>();
    List<FmsDprTransmisson> dprlistTransmission = new ArrayList<>();
    List<FmsDprTransmisson> filteredItems;

    private FmsDprTransmisson selectedAsset;

    List<FmsDprTransmisson> transmissionDataList;
    DataModel<FmsDprTransmisson> transmissionDataModel = new ArrayDataModel<>();

    private boolean registeredPnl = true;
    private boolean newItemPnl = false;

    String tableBtnVal = "New Items";
    String saveCalc = "Calculate";

    public DPRTransmissionController() {
    }

    public List<FmsDprTransmisson> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<FmsDprTransmisson> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public MmsFaTransmissionBeanLocal getMmsFaTransmissionBeanLocal() {
        return mmsFaTransmissionBeanLocal;
    }

    public void setMmsFaTransmissionBeanLocal(MmsFaTransmissionBeanLocal mmsFaTransmissionBeanLocal) {
        this.mmsFaTransmissionBeanLocal = mmsFaTransmissionBeanLocal;
    }

    public FmsDprTransmissionBeanLocal getFmsDprTransmissonBeanLocal() {
        return fmsDprTransmissonBeanLocal;
    }

    public void setFmsDprTransmissonBeanLocal(FmsDprTransmissionBeanLocal fmsDprTransmissonBeanLocal) {
        this.fmsDprTransmissonBeanLocal = fmsDprTransmissonBeanLocal;
    }

    public FmsDprTransmisson getSelectedAsset() {
        return selectedAsset;
    }

    public void setSelectedAsset(FmsDprTransmisson selectedAsset) {
        this.selectedAsset = selectedAsset;
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

    public MmsFaTransmission getMmsFaTransmission() {
        if (mmsFaTransmission == null) {
            mmsFaTransmission = new MmsFaTransmission();
        }
        return mmsFaTransmission;
    }

    public void setMmsFaTransmission(MmsFaTransmission mmsFaTransmission) {
        this.mmsFaTransmission = mmsFaTransmission;
    }

    public FmsDprTransmisson getFmsDprTransmisson() {
        if (fmsDprTransmisson == null) {
            fmsDprTransmisson = new FmsDprTransmisson();
        }
        return fmsDprTransmisson;
    }

    public void setFmsDprTransmisson(FmsDprTransmisson fmsDprTransmisson) {
        this.fmsDprTransmisson = fmsDprTransmisson;
    }

    public List<MmsFaTransmission> getTransmissionAssetList() {
        if (transmissionAssetList == null) {
            transmissionAssetList = new ArrayList<>();
        }
        return transmissionAssetList;
    }

    public void setTransmissionAssetList(List<MmsFaTransmission> transmissionAssetList) {
        this.transmissionAssetList = transmissionAssetList;
    }

    public List<FmsDprTransmisson> getTransmissionDPRList() {
        if (transmissionDPRList == null) {
            transmissionDPRList = new ArrayList<>();
        }
        return transmissionDPRList;
    }

    public void setTransmissionDPRList(List<FmsDprTransmisson> transmissionDPRList) {
        this.transmissionDPRList = transmissionDPRList;
    }

    public List<FmsDprTransmisson> getTransmissionDataList() {
        return transmissionDataList;
    }

    public void setTransmissionDataList(List<FmsDprTransmisson> transmissionDataList) {
        this.transmissionDataList = transmissionDataList;
    }

    public DataModel<FmsDprTransmisson> getTransmissionDataModel() {
        return transmissionDataModel;
    }

    public void setTransmissionDataModel(DataModel<FmsDprTransmisson> transmissionDataModel) {
        this.transmissionDataModel = transmissionDataModel;
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

    public void getActivePeriod() {
        AccountingPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        AccountingPeriod.getStartDate();
        AccountingPeriod.getEndDate();
        AccountingPeriod.getAcountigPeriodId();
    }

    public List<FmsDprTransmisson> getDprlistTransmission() {
        if (dprlistTransmission == null) {
            dprlistTransmission = new ArrayList<>();
        }
        return dprlistTransmission;
    }

    public void setDprlistTransmission(List<FmsDprTransmisson> dprlistTransmission) {
        this.dprlistTransmission = dprlistTransmission;
    }

    @PostConstruct
    public void SearchTRAsset() {
        transmissionDPRList = fmsDprTransmissonBeanLocal.findStatus1();
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
        transmissionAssetList = null;
        transmissionDPRList = null;
        dprlistTransmission = null;

        transmissionAssetList = mmsFaTransmissionBeanLocal.findNewItems();
        transmissionDPRList = fmsDprTransmissonBeanLocal.findStatus1();
        SaveNewItems();
    }

    public void registeredItem() {
        registeredPnl = true;
        newItemPnl = false;

        transmissionAssetList = null;
        transmissionDPRList = null;
        dprlistTransmission = null;

        SearchTRAsset();
    }

    public void SaveNewItems() {
        for (int h = 0; h < transmissionAssetList.size(); h++) {

            fmsDprTransmisson = new FmsDprTransmisson(null);
            fmsDprTransmisson.setTrAssetId(transmissionAssetList.get(h));
            fmsDprTransmisson.setDprYear(BigDecimal.ZERO);
            fmsDprTransmisson.setAccumulatedDpr(BigDecimal.ZERO);
            fmsDprTransmisson.setAccountingPeriod(AccountingPeriod);
            fmsDprTransmisson.setStatus(1);
            getDprlistTransmission().add(fmsDprTransmisson);
            fmsDprTransmisson = new FmsDprTransmisson(null);

        }
        if (!(dprlistTransmission == null)) {
            transmissionDataModel = new ListDataModel(new ArrayList(dprlistTransmission));
        } else {
            transmissionDataModel = null;
        }
    }

    public void calculateDPR() {

        if (!transmissionDPRList.isEmpty()) {

            for (FmsDprTransmisson transmissionDPRList1 : transmissionDPRList) {
                fmsDprTransmisson.setDprTransmissionId(transmissionDPRList1.getDprTransmissionId());
                fmsDprTransmisson.setTrAssetId(transmissionDPRList1.getTrAssetId());
                fmsDprTransmisson.setDprYear(transmissionDPRList1.getDprYear());
                fmsDprTransmisson.setAccumulatedDpr(transmissionDPRList1.getAccumulatedDpr());
                fmsDprTransmisson.setNetBookValue(transmissionDPRList1.getNetBookValue());
                fmsDprTransmisson.setDataCardNo(transmissionDPRList1.getDataCardNo());
                fmsDprTransmisson.setRevaluationCost(transmissionDPRList1.getRevaluationCost());
                fmsDprTransmisson.setRevaluationServiceYear(transmissionDPRList1.getRevaluationServiceYear());
                fmsDprTransmisson.setAccountingPeriod(transmissionDPRList1.getAccountingPeriod());
                fmsDprTransmisson.setAccountNoTrns(transmissionDPRList1.getAccountNoTrns());
                fmsDprTransmisson.setRevaluationCost(transmissionDPRList1.getRevaluationCost());
                fmsDprTransmisson.setRevaluationServiceYear(transmissionDPRList1.getRevaluationServiceYear());

                getDprlistTransmission().add(fmsDprTransmisson);
                fmsDprTransmisson = new FmsDprTransmisson(null);
            }
        }

        transmissionDataModel = new ListDataModel(new ArrayList(dprlistTransmission));

    }

    public void getByAssetName(SelectEvent event) {
        mmsFaTransmission.setFromName(event.getObject().toString());
        mmsFaTransmission = mmsFaTransmissionBeanLocal.getByAssetName(mmsFaTransmission);
    }

    public void NewTransmissions(FmsDprTransmisson fmsDprTransmisson) {
        transmissionDataList = fmsDprTransmissonBeanLocal.fetchTransmission(fmsDprTransmisson);
        if (!transmissionDataList.isEmpty()) {
            transmissionDataModel = new ListDataModel(new ArrayList(transmissionDataList));
        } else {
            transmissionDataModel = null;
        }
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    public void populateDPR(SelectEvent event) {

        fmsDprTransmisson = (FmsDprTransmisson) event.getObject();

    }

    public void Add() {
        for (int i = 0; i < dprlistTransmission.size(); i++) {
            if (fmsDprTransmisson.getTrAssetId() == dprlistTransmission.get(i).getTrAssetId()) {
                dprlistTransmission.set(i, fmsDprTransmisson);
            }
        }
        transmissionDataModel = new ListDataModel(new ArrayList(dprlistTransmission));
    }

    public void saveDataC() {
        for (int i = 0; i < dprlistTransmission.size(); i++) {
            if (fmsDprTransmisson.getTrAssetId() == dprlistTransmission.get(i).getTrAssetId()) {
                dprlistTransmission.get(i).setDataCardNo(fmsDprTransmisson.getDataCardNo());
            }
        }
        transmissionDataModel = new ListDataModel(new ArrayList(dprlistTransmission));
    }

    public void saveTransmissionDPR() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveTransmissionDPR", dataset)) {
                if (newItemPnl == true && !dprlistTransmission.isEmpty()) {
                    int saveStat = 0;
                    for (int i = 0; i < dprlistTransmission.size(); i++) {
                        if (!(dprlistTransmission.get(i).getDataCardNo() == null)) {
                            fmsDprTransmissonBeanLocal.create(dprlistTransmission.get(i));
                            saveStat = 1;
                        }
                    }
                    transmissionDataModel = new ListDataModel(new ArrayList(dprlistTransmission));
                    if (saveStat == 1) {
                        registeredItem();
                        saveStat = 0;
                        tableBtnVal = "New Items";
                        saveCalc = "Calculate";
                        JsfUtil.addSuccessMessage("Fixed Asset is Saved");
                    }
                } else if (registeredPnl == true) {
                    int saveStat = 0;
                    if (!dprlistTransmission.isEmpty()) {

                        for (int i = 0; i < dprlistTransmission.size(); i++) {
                            fmsDprTransmisson = new FmsDprTransmisson();

                            if (dprlistTransmission.get(i).getDprYear() == BigDecimal.ZERO && Objects.equals(dprlistTransmission.get(i).getAccountingPeriod(), AccountingPeriod) && dprlistTransmission.get(i).getTrAssetId().getRevaluationCost() == null) {       //INITIALLY
                                String Smonth[] = dprlistTransmission.get(i).getTrAssetId().getInservice().split("/");
                                int Imonth = Integer.parseInt(Smonth[1]);
                                if (Imonth == 1) {
                                    fmsDprTransmisson.setTrAssetId(dprlistTransmission.get(i).getTrAssetId());
                                    fmsDprTransmisson.setDprYear(dprlistTransmission.get(i).getTrAssetId().getUnitCost().divide(new BigDecimal(dprlistTransmission.get(i).getTrAssetId().getServiceLife()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprTransmisson.setAccumulatedDpr(dprlistTransmission.get(i).getDprYear());
                                    fmsDprTransmisson.setNetBookValue(dprlistTransmission.get(i).getTrAssetId().getUnitCost().subtract(dprlistTransmission.get(i).getAccumulatedDpr()));
                                    fmsDprTransmisson.setStatus(1);
                                    fmsDprTransmisson.setAccountingPeriod(AccountingPeriod);
                                    fmsDprTransmisson.setAccountNoTrns(dprlistTransmission.get(i).getAccountNoTrns());
                                    fmsDprTransmisson.setDataCardNo(dprlistTransmission.get(i).getDataCardNo());

                                } else {
                                    fmsDprTransmisson.setTrAssetId(dprlistTransmission.get(i).getTrAssetId());
                                    fmsDprTransmisson.setDprYear(((dprlistTransmission.get(i).getTrAssetId().getUnitCost().divide(new BigDecimal(dprlistTransmission.get(i).getTrAssetId().getServiceLife().doubleValue() * 12), 2, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(Imonth))).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsDprTransmisson.setAccumulatedDpr(fmsDprTransmisson.getDprYear());
                                    fmsDprTransmisson.setNetBookValue(dprlistTransmission.get(i).getTrAssetId().getUnitCost().subtract(dprlistTransmission.get(i).getAccumulatedDpr()));
                                    fmsDprTransmisson.setStatus(1);
                                    fmsDprTransmisson.setAccountingPeriod(AccountingPeriod);
                                    fmsDprTransmisson.setAccountNoTrns(dprlistTransmission.get(i).getAccountNoTrns());
                                    fmsDprTransmisson.setDataCardNo(dprlistTransmission.get(i).getDataCardNo());
                                }
                            } else if (dprlistTransmission.get(i).getDprYear() != BigDecimal.ZERO && !Objects.equals(dprlistTransmission.get(i).getAccountingPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId()) && dprlistTransmission.get(i).getTrAssetId().getRevaluationCost() == null) {       //Nth time

                                fmsDprTransmisson.setTrAssetId(dprlistTransmission.get(i).getTrAssetId());
                                fmsDprTransmisson.setDprYear(dprlistTransmission.get(i).getTrAssetId().getUnitCost().divide(new BigDecimal(dprlistTransmission.get(i).getTrAssetId().getServiceLife()), 2, RoundingMode.HALF_EVEN));
                                fmsDprTransmisson.setAccumulatedDpr(fmsDprTransmisson.getDprYear().add(dprlistTransmission.get(i).getAccumulatedDpr()));
                                fmsDprTransmisson.setNetBookValue(dprlistTransmission.get(i).getTrAssetId().getUnitCost().subtract(dprlistTransmission.get(i).getAccumulatedDpr()));
                                fmsDprTransmisson.setAccountingPeriod(AccountingPeriod);
                                fmsDprTransmisson.setStatus(1);
                                fmsDprTransmisson.setAccountNoTrns(dprlistTransmission.get(i).getAccountNoTrns());
                                fmsDprTransmisson.setDataCardNo(dprlistTransmission.get(i).getDataCardNo());
                            } else if (dprlistTransmission.get(i).getTrAssetId().getRevaluationCost() != null && !Objects.equals(dprlistTransmission.get(i).getAccountingPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId())) {

                                if (dprlistTransmission.get(i).getRevaluationCost() == null) {
                                    fmsDprTransmisson.setTrAssetId(dprlistTransmission.get(i).getTrAssetId());
                                    fmsDprTransmisson.setRevaluationCost(dprlistTransmission.get(i).getTrAssetId().getRevaluationCost());
                                    fmsDprTransmisson.setRevaluationServiceYear(dprlistTransmission.get(i).getTrAssetId().getRevaluationServiceYear());
                                    fmsDprTransmisson.setDprYear(dprlistTransmission.get(i).getTrAssetId().getRevaluationCost().divide(new BigDecimal(dprlistTransmission.get(i).getTrAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprTransmisson.setAccumulatedDpr(dprlistTransmission.get(i).getAccumulatedDpr().add(fmsDprTransmisson.getDprYear()));
                                    fmsDprTransmisson.setNetBookValue(dprlistTransmission.get(i).getNetBookValue().subtract(fmsDprTransmisson.getDprYear()));
                                    fmsDprTransmisson.setAccountingPeriod(AccountingPeriod);
                                    fmsDprTransmisson.setStatus(1);
                                    fmsDprTransmisson.setAccountNoTrns(dprlistTransmission.get(i).getAccountNoTrns());
                                    fmsDprTransmisson.setDataCardNo(dprlistTransmission.get(i).getDataCardNo());
                                } else if (dprlistTransmission.get(i).getTrAssetId().getRevaluationCost() != null) {
                                    fmsDprTransmisson.setTrAssetId(dprlistTransmission.get(i).getTrAssetId());
                                    fmsDprTransmisson.setDprYear(dprlistTransmission.get(i).getTrAssetId().getRevaluationCost().divide(new BigDecimal(dprlistTransmission.get(i).getTrAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprTransmisson.setAccumulatedDpr(dprlistTransmission.get(i).getAccumulatedDpr().add(fmsDprTransmisson.getDprYear()));
                                    fmsDprTransmisson.setNetBookValue(dprlistTransmission.get(i).getNetBookValue().subtract(fmsDprTransmisson.getDprYear()));
                                    fmsDprTransmisson.setAccountingPeriod(AccountingPeriod);
                                    fmsDprTransmisson.setStatus(1);
                                    fmsDprTransmisson.setAccountNoTrns(dprlistTransmission.get(i).getAccountNoTrns());
                                    fmsDprTransmisson.setDataCardNo(dprlistTransmission.get(i).getDataCardNo());
                                    fmsDprTransmisson.setRevaluationCost(dprlistTransmission.get(i).getTrAssetId().getRevaluationCost());
                                    fmsDprTransmisson.setRevaluationServiceYear(dprlistTransmission.get(i).getTrAssetId().getRevaluationServiceYear());
                                }
                            }
                            dprlistTransmission.get(i).setStatus(0);
                            if (fmsDprTransmisson.getDprYear() != null && dprlistTransmission != null) {
                                if (fmsDprTransmisson.getNetBookValue().compareTo(BigDecimal.TEN) == -1) {
                                    fmsDprTransmisson.setNetBookValue(BigDecimal.TEN);
                                    fmsDprTransmissonBeanLocal.create(fmsDprTransmisson);
                                    fmsDprTransmissonBeanLocal.edit(dprlistTransmission.get(i));
                                    saveStat = 1;
                                } else {
                                    fmsDprTransmissonBeanLocal.create(fmsDprTransmisson);
                                    fmsDprTransmissonBeanLocal.edit(dprlistTransmission.get(i));
                                    saveStat = 1;
                                }
                            }
                        }
                        if (saveStat == 1) {
                            JsfUtil.addSuccessMessage("Fixed asset depreciation is calculated and saved");
                        }
                        transmissionDataModel = new ListDataModel(new ArrayList(dprlistTransmission));
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
