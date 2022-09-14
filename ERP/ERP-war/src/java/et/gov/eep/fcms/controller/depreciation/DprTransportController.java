/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.depreciation;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.fixedasset.FmsDprTransportBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.fixedasset.FmsDprTransport;
import et.gov.eep.mms.businessLogic.MmsFaTransportBeanLocal;
import et.gov.eep.mms.entity.MmsFaTransport;
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
@Named(value = "dprTransportController")
@ViewScoped
public class DprTransportController implements Serializable {

    @Inject
    SessionBean SessionBean;

    @Inject
    MmsFaTransport mmsFaTransport;
    @Inject
    FmsDprTransport fmsDprTransport;
    @Inject
    FmsAccountingPeriod AccountingPeriod;
    @EJB
    MmsFaTransportBeanLocal mmsFaTransportBeanLocal;
    @EJB
    FmsDprTransportBeanLocal fmsDprTransportBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;

    List<MmsFaTransport> transportAssetList = new ArrayList<>();
    List<FmsDprTransport> transportDPRList = new ArrayList<>();
    List<FmsDprTransport> dprlistTransport = new ArrayList<>();
    List<FmsDprTransport> filteredItems;

    private FmsDprTransport selectedAsset;

    List<FmsDprTransport> transportDataList;
    DataModel<FmsDprTransport> transportDataModel = new ArrayDataModel<>();

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

    public MmsFaTransport getMmsFaTransport() {
        if (mmsFaTransport == null) {
            mmsFaTransport = new MmsFaTransport();
        }
        return mmsFaTransport;
    }

    public void setMmsFaTransport(MmsFaTransport mmsFaTransport) {
        this.mmsFaTransport = mmsFaTransport;
    }

    public FmsDprTransport getFmsDprTransport() {
        if (fmsDprTransport == null) {
            fmsDprTransport = new FmsDprTransport();
        }
        return fmsDprTransport;
    }

    public void setFmsDprTransport(FmsDprTransport fmsDprTransport) {
        this.fmsDprTransport = fmsDprTransport;
    }

    public FmsAccountingPeriod getAccountingPeriod() {
        return AccountingPeriod;
    }

    public void setAccountingPeriod(FmsAccountingPeriod AccountingPeriod) {
        this.AccountingPeriod = AccountingPeriod;
    }

    public FmsAccountingPeriodBeanLocal getFmsAccountingPeriodBeanLocal() {
        return fmsAccountingPeriodBeanLocal;
    }

    public void setFmsAccountingPeriodBeanLocal(FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal) {
        this.fmsAccountingPeriodBeanLocal = fmsAccountingPeriodBeanLocal;
    }

    public List<MmsFaTransport> getTransportAssetList() {
        return transportAssetList;
    }

    public void setTransportAssetList(List<MmsFaTransport> transportAssetList) {
        this.transportAssetList = transportAssetList;
    }

    public List<FmsDprTransport> getTransportDPRList() {
        if (transportDPRList == null) {
            transportDPRList = new ArrayList<>();
        }
        return transportDPRList;
    }

    public void setTransportDPRList(List<FmsDprTransport> transportDPRList) {
        this.transportDPRList = transportDPRList;
    }

    public List<FmsDprTransport> getDprlistTransport() {
        if (dprlistTransport == null) {
            dprlistTransport = new ArrayList<>();
        }
        return dprlistTransport;
    }

    public void setDprlistTransport(List<FmsDprTransport> dprlistTransport) {
        this.dprlistTransport = dprlistTransport;
    }

    public List<FmsDprTransport> getTransportDataList() {
        return transportDataList;
    }

    public void setTransportDataList(List<FmsDprTransport> transportDataList) {
        this.transportDataList = transportDataList;
    }

    public DataModel<FmsDprTransport> getTransportDataModel() {
        return transportDataModel;
    }

    public void setTransportDataModel(DataModel<FmsDprTransport> transportDataModel) {
        this.transportDataModel = transportDataModel;
    }

    public List<FmsDprTransport> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<FmsDprTransport> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public FmsDprTransport getSelectedAsset() {
        return selectedAsset;
    }

    public void setSelectedAsset(FmsDprTransport selectedAsset) {
        this.selectedAsset = selectedAsset;
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

    public DprTransportController() {
    }

    public void getActivePeriod() {
        AccountingPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        AccountingPeriod.getStartDate();
        AccountingPeriod.getEndDate();
        AccountingPeriod.getAcountigPeriodId();
    }

    @PostConstruct
    public void SearchTRAsset() {
        transportDPRList = fmsDprTransportBeanLocal.findStatus1();
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
        transportDPRList = null;
        transportDPRList = null;
        dprlistTransport = null;

        transportAssetList = mmsFaTransportBeanLocal.findNewItems();
        transportDPRList = fmsDprTransportBeanLocal.findStatus1();
        SaveNewItems();
    }

    public void registeredItem() {
        registeredPnl = true;
        newItemPnl = false;

        transportDPRList = null;
        transportDPRList = null;
        dprlistTransport = null;

        SearchTRAsset();
    }

    public void SaveNewItems() {
        for (int h = 0; h < transportAssetList.size(); h++) {

            fmsDprTransport = new FmsDprTransport(null);
            fmsDprTransport.setTpAssetId(transportAssetList.get(h));
            fmsDprTransport.setDprYear(BigDecimal.ZERO);
            fmsDprTransport.setAccumulatedDpr(BigDecimal.ZERO);
            fmsDprTransport.setAccountPeriod(AccountingPeriod);
            fmsDprTransport.setStatus(1);
            getDprlistTransport().add(fmsDprTransport);
            fmsDprTransport = new FmsDprTransport(null);

        }
        if (!(dprlistTransport == null)) {
            transportDataModel = new ListDataModel(new ArrayList(dprlistTransport));
        } else {
            transportDataModel = null;
        }
    }

    public void calculateDPR() {
        if (!transportDPRList.isEmpty()) {

            for (FmsDprTransport transportDPRList1 : transportDPRList) {
                fmsDprTransport.setDprTransportId(transportDPRList1.getDprTransportId());
                fmsDprTransport.setTpAssetId(transportDPRList1.getTpAssetId());
                fmsDprTransport.setDprYear(transportDPRList1.getDprYear());
                fmsDprTransport.setAccumulatedDpr(transportDPRList1.getAccumulatedDpr());
                fmsDprTransport.setNetBookValue(transportDPRList1.getNetBookValue());
                fmsDprTransport.setDataCardNo(transportDPRList1.getDataCardNo());
                fmsDprTransport.setRevaluationCost(transportDPRList1.getRevaluationCost());
                fmsDprTransport.setRevaluationServiceYear(transportDPRList1.getRevaluationServiceYear());
                fmsDprTransport.setAccountPeriod(transportDPRList1.getAccountPeriod());
                fmsDprTransport.setRevaluationCost(transportDPRList1.getRevaluationCost());
                fmsDprTransport.setRevaluationServiceYear(transportDPRList1.getRevaluationServiceYear());

                getDprlistTransport().add(fmsDprTransport);
                fmsDprTransport = new FmsDprTransport(null);
            }
        }

        transportDataModel = new ListDataModel(new ArrayList(dprlistTransport));

    }

    public void NewTransport(FmsDprTransport fmsDprTransport) {
        transportDataList = fmsDprTransportBeanLocal.fetchTransport(fmsDprTransport);
        if (!transportDataList.isEmpty()) {
            transportDataModel = new ListDataModel(new ArrayList(transportDataList));
        } else {
            transportDataModel = null;
        }
    }

    public void populateDPR(SelectEvent event) {
        fmsDprTransport = (FmsDprTransport) event.getObject();
    }

    public void Add() {
        for (int i = 0; i < dprlistTransport.size(); i++) {
            if (fmsDprTransport.getTpAssetId() == dprlistTransport.get(i).getTpAssetId()) {
                dprlistTransport.set(i, fmsDprTransport);
            }
        }
        transportDataModel = new ListDataModel(new ArrayList(dprlistTransport));
    }

    public void saveDataC() {

        for (int i = 0; i < dprlistTransport.size(); i++) {
            if (fmsDprTransport.getTpAssetId() == dprlistTransport.get(i).getTpAssetId()) {
                dprlistTransport.get(i).setDataCardNo(fmsDprTransport.getDataCardNo());
            }
        }
        transportDataModel = new ListDataModel(new ArrayList(dprlistTransport));
    }

    public void saveTransportDPR() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveTransportDPR", dataset)) {

                if (newItemPnl == true && !dprlistTransport.isEmpty()) {
                    int saveStat = 0;
                    for (int i = 0; i < dprlistTransport.size(); i++) {
                        if (!(dprlistTransport.get(i).getDataCardNo() == null)) {
                            fmsDprTransportBeanLocal.create(dprlistTransport.get(i));
                            saveStat = 1;
                        }
                    }
                    transportDataModel = new ListDataModel(new ArrayList(dprlistTransport));
                    if (saveStat == 1) {
                        registeredItem();
                        saveStat = 0;
                        tableBtnVal = "New Items";
                        saveCalc = "Calculate";
                        JsfUtil.addSuccessMessage("Fixed Asset is Saved");
                    }
                } else if (registeredPnl == true) {
                    int saveStat = 0;
                    if (!dprlistTransport.isEmpty()) {

                        for (int i = 0; i < dprlistTransport.size(); i++) {
                            fmsDprTransport = new FmsDprTransport();

                            if (dprlistTransport.get(i).getDprYear() == BigDecimal.ZERO && Objects.equals(dprlistTransport.get(i).getAccountPeriod(), AccountingPeriod) && dprlistTransport.get(i).getTpAssetId().getRevaluationCost() == null) {       //INITIALLY
                                String Smonth[] = dprlistTransport.get(i).getTpAssetId().getTpInservice().split("/");
                                int Imonth = Integer.parseInt(Smonth[1]);
                                if (Imonth == 1) {
                                    fmsDprTransport.setTpAssetId(dprlistTransport.get(i).getTpAssetId());
                                    fmsDprTransport.setDprYear(dprlistTransport.get(i).getTpAssetId().getTpCost().divide(new BigDecimal(dprlistTransport.get(i).getTpAssetId().getTpServiceLife()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprTransport.setAccumulatedDpr(dprlistTransport.get(i).getDprYear());
                                    fmsDprTransport.setNetBookValue(dprlistTransport.get(i).getTpAssetId().getTpCost().subtract(dprlistTransport.get(i).getAccumulatedDpr()));
                                    fmsDprTransport.setStatus(1);
                                    fmsDprTransport.setAccountPeriod(AccountingPeriod);
                                    fmsDprTransport.setDataCardNo(dprlistTransport.get(i).getDataCardNo());

                                } else {
                                    fmsDprTransport.setTpAssetId(dprlistTransport.get(i).getTpAssetId());
                                    fmsDprTransport.setDprYear(((dprlistTransport.get(i).getTpAssetId().getTpCost().divide(new BigDecimal(dprlistTransport.get(i).getTpAssetId().getTpServiceLife().doubleValue() * 12), 2, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(Imonth))).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsDprTransport.setAccumulatedDpr(fmsDprTransport.getDprYear());
                                    fmsDprTransport.setNetBookValue(dprlistTransport.get(i).getTpAssetId().getTpCost().subtract(dprlistTransport.get(i).getAccumulatedDpr()));
                                    fmsDprTransport.setStatus(1);
                                    fmsDprTransport.setAccountPeriod(AccountingPeriod);
                                    fmsDprTransport.setDataCardNo(dprlistTransport.get(i).getDataCardNo());
                                }
                            } else if (dprlistTransport.get(i).getDprYear() != BigDecimal.ZERO && !Objects.equals(dprlistTransport.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId()) && dprlistTransport.get(i).getTpAssetId().getRevaluationCost() == null) {       //Nth time

                                fmsDprTransport.setTpAssetId(dprlistTransport.get(i).getTpAssetId());
                                fmsDprTransport.setDprYear(dprlistTransport.get(i).getTpAssetId().getTpCost().divide(new BigDecimal(dprlistTransport.get(i).getTpAssetId().getTpServiceLife()), 2, RoundingMode.HALF_EVEN));
                                fmsDprTransport.setAccumulatedDpr(fmsDprTransport.getDprYear().add(dprlistTransport.get(i).getAccumulatedDpr()));
                                fmsDprTransport.setNetBookValue(dprlistTransport.get(i).getTpAssetId().getTpCost().subtract(dprlistTransport.get(i).getAccumulatedDpr()));
                                fmsDprTransport.setAccountPeriod(AccountingPeriod);
                                fmsDprTransport.setStatus(1);
                                fmsDprTransport.setDataCardNo(dprlistTransport.get(i).getDataCardNo());
                            } else if (dprlistTransport.get(i).getTpAssetId().getRevaluationCost() != null && !Objects.equals(dprlistTransport.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId())) {

                                if (dprlistTransport.get(i).getRevaluationCost() == null) {
                                    fmsDprTransport.setTpAssetId(dprlistTransport.get(i).getTpAssetId());
                                    fmsDprTransport.setRevaluationCost(dprlistTransport.get(i).getTpAssetId().getRevaluationCost());
                                    fmsDprTransport.setRevaluationServiceYear(dprlistTransport.get(i).getTpAssetId().getRevaluationServiceYear());
                                    fmsDprTransport.setDprYear(dprlistTransport.get(i).getTpAssetId().getRevaluationCost().divide(new BigDecimal(dprlistTransport.get(i).getTpAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprTransport.setAccumulatedDpr(dprlistTransport.get(i).getAccumulatedDpr().add(fmsDprTransport.getDprYear()));
                                    fmsDprTransport.setNetBookValue(dprlistTransport.get(i).getNetBookValue().subtract(fmsDprTransport.getDprYear()));
                                    fmsDprTransport.setAccountPeriod(AccountingPeriod);
                                    fmsDprTransport.setStatus(1);
                                    fmsDprTransport.setDataCardNo(dprlistTransport.get(i).getDataCardNo());
                                } else if (dprlistTransport.get(i).getTpAssetId().getRevaluationCost() != null) {
                                    fmsDprTransport.setTpAssetId(dprlistTransport.get(i).getTpAssetId());
                                    fmsDprTransport.setDprYear(dprlistTransport.get(i).getTpAssetId().getRevaluationCost().divide(new BigDecimal(dprlistTransport.get(i).getTpAssetId().getRevaluationServiceYear()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprTransport.setAccumulatedDpr(dprlistTransport.get(i).getAccumulatedDpr().add(fmsDprTransport.getDprYear()));
                                    fmsDprTransport.setNetBookValue(dprlistTransport.get(i).getNetBookValue().subtract(fmsDprTransport.getDprYear()));
                                    fmsDprTransport.setAccountPeriod(AccountingPeriod);
                                    fmsDprTransport.setStatus(1);
                                    fmsDprTransport.setDataCardNo(dprlistTransport.get(i).getDataCardNo());
                                    fmsDprTransport.setRevaluationCost(dprlistTransport.get(i).getTpAssetId().getRevaluationCost());
                                    fmsDprTransport.setRevaluationServiceYear(dprlistTransport.get(i).getTpAssetId().getRevaluationServiceYear());
                                }
                            }
                            dprlistTransport.get(i).setStatus(0);
                            if (fmsDprTransport.getDprYear() != null && dprlistTransport != null) {
                                if (fmsDprTransport.getNetBookValue().compareTo(BigDecimal.TEN) == -1) {
                                    fmsDprTransport.setNetBookValue(BigDecimal.TEN);
                                    fmsDprTransportBeanLocal.create(fmsDprTransport);
                                    fmsDprTransportBeanLocal.edit(dprlistTransport.get(i));
                                    saveStat = 1;
                                } else {
                                    fmsDprTransportBeanLocal.create(fmsDprTransport);
                                    fmsDprTransportBeanLocal.edit(dprlistTransport.get(i));
                                    saveStat = 1;
                                }
                            }
                        }
                        if (saveStat == 1) {
                            JsfUtil.addSuccessMessage("Fixed asset depreciation is calculated and saved");
                        }
                        transportDataModel = new ListDataModel(new ArrayList(dprlistTransport));
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
