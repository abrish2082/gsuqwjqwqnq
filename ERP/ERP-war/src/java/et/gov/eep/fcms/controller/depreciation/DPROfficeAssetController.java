/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.depreciation;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.fixedasset.FmsDprOfficeAssetBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.mms.businessLogic.MmsFixedAssetRegistrationDtlBeanLocal;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
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
 * @author Binyam SELECT MMS_FA_BUILDING.BUILDING_ID FROM MMS_FA_BUILDING WHERE
 * MMS_FA_BUILDING.BUILDING_ID NOT IN (SELECT FMS_DPR_BUILDING.BU_ASSET_ID FROM
 * FMS_DPR_BUILDING);
 */
@Named(value = "dPROfficeAssetController")
@ViewScoped
public class DPROfficeAssetController implements Serializable {

    @Inject
    SessionBean SessionBean;

    @Inject
    MmsFixedassetRegstDetail mmsFixedassetRegstDetail;
    @Inject
    FmsDprOfficeAsset fmsDprOfficeAsset;
    @Inject
    FmsAccountingPeriod AccountingPeriod;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @EJB
    MmsFixedAssetRegistrationDtlBeanLocal mmsFixedAssetRegistrationDtlBeanLocal;
    @EJB
    FmsDprOfficeAssetBeanLocal fmsDprOfficeAssetBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;

    List<MmsFixedassetRegstDetail> officeAssetList = new ArrayList<>();
    List<FmsDprOfficeAsset> officeDPRList = new ArrayList<>();
    List<FmsDprOfficeAsset> dprlistOffice = new ArrayList<>();
    List<FmsDprOfficeAsset> filteredItems;

    private FmsDprOfficeAsset selectedAsset;

    List<FmsDprOfficeAsset> officeDataList;
    DataModel<FmsDprOfficeAsset> officeDataModel = new ArrayDataModel<>();

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

    public MmsFixedassetRegstDetail getMmsFixedassetRegstDetail() {
        return mmsFixedassetRegstDetail;
    }

    public void setMmsFixedassetRegstDetail(MmsFixedassetRegstDetail mmsFixedassetRegstDetail) {
        this.mmsFixedassetRegstDetail = mmsFixedassetRegstDetail;
    }

    public FmsDprOfficeAsset getFmsDprOfficeAsset() {
        return fmsDprOfficeAsset;
    }

    public void setFmsDprOfficeAsset(FmsDprOfficeAsset fmsDprOfficeAsset) {
        this.fmsDprOfficeAsset = fmsDprOfficeAsset;
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

    public List<MmsFixedassetRegstDetail> getOfficeAssetList() {
        return officeAssetList;
    }

    public void setOfficeAssetList(List<MmsFixedassetRegstDetail> officeAssetList) {
        this.officeAssetList = officeAssetList;
    }

    public List<FmsDprOfficeAsset> getOfficeDPRList() {
        return officeDPRList;
    }

    public void setOfficeDPRList(List<FmsDprOfficeAsset> officeDPRList) {
        this.officeDPRList = officeDPRList;
    }

    public List<FmsDprOfficeAsset> getDprlistOffice() {
        if (dprlistOffice == null) {
            dprlistOffice = new ArrayList<>();
        }
        return dprlistOffice;
    }

    public void setDprlistOffice(List<FmsDprOfficeAsset> dprlistOffice) {
        this.dprlistOffice = dprlistOffice;
    }

    public List<FmsDprOfficeAsset> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<FmsDprOfficeAsset> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public FmsDprOfficeAsset getSelectedAsset() {
        if (selectedAsset == null) {
            selectedAsset = new FmsDprOfficeAsset();
        }
        return selectedAsset;
    }

    public void setSelectedAsset(FmsDprOfficeAsset selectedAsset) {
        this.selectedAsset = selectedAsset;
    }

    public List<FmsDprOfficeAsset> getOfficeDataList() {
        return officeDataList;
    }

    public void setOfficeDataList(List<FmsDprOfficeAsset> officeDataList) {
        this.officeDataList = officeDataList;
    }

    public DataModel<FmsDprOfficeAsset> getOfficeDataModel() {
        return officeDataModel;
    }

    public void setOfficeDataModel(DataModel<FmsDprOfficeAsset> officeDataModel) {
        this.officeDataModel = officeDataModel;
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

    public DPROfficeAssetController() {
    }

    @PostConstruct
    public void SearchTRAsset() {
        officeDPRList = fmsDprOfficeAssetBeanLocal.findStatus1();
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
        officeDPRList = null;
        officeDPRList = null;
        dprlistOffice = null;

        officeAssetList = mmsFixedAssetRegistrationDtlBeanLocal.findNewItems();
        officeDPRList = fmsDprOfficeAssetBeanLocal.findStatus1();
        SaveNewItems();
    }

    public void registeredItem() {
        registeredPnl = true;
        newItemPnl = false;

        officeDPRList = null;
        officeDPRList = null;
        dprlistOffice = null;

        SearchTRAsset();
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    public void populateDPR(SelectEvent event) {
        fmsDprOfficeAsset = (FmsDprOfficeAsset) event.getObject();
    }

    public void Add() {
        for (int i = 0; i < dprlistOffice.size(); i++) {
            if (fmsDprOfficeAsset.getOfAssetId() == dprlistOffice.get(i).getOfAssetId()) {
                dprlistOffice.set(i, fmsDprOfficeAsset);
            }
        }
        officeDataModel = new ListDataModel(new ArrayList(dprlistOffice));
    }

    public void SaveNewItems() {
        for (int h = 0; h < officeAssetList.size(); h++) {

            fmsDprOfficeAsset = new FmsDprOfficeAsset(null);
            fmsDprOfficeAsset.setOfAssetId(officeAssetList.get(h));
            fmsDprOfficeAsset.setTagNo(officeAssetList.get(h).getTagNo());
            fmsDprOfficeAsset.setDprYear(BigDecimal.ZERO);
            fmsDprOfficeAsset.setAccumulatedDpr(BigDecimal.ZERO);
            fmsDprOfficeAsset.setAccountPeriod(AccountingPeriod);
            fmsDprOfficeAsset.setStatus(1);
            getDprlistOffice().add(fmsDprOfficeAsset);
            fmsDprOfficeAsset = new FmsDprOfficeAsset(null);

        }
        if (!(dprlistOffice == null)) {
            officeDataModel = new ListDataModel(new ArrayList(dprlistOffice));
        } else {
            officeDataModel = null;
        }
    }

    public void calculateDPR() {

        if (!officeDPRList.isEmpty()) {

            for (FmsDprOfficeAsset officeDPRList1 : officeDPRList) {
                fmsDprOfficeAsset.setDprOfficeId(officeDPRList1.getDprOfficeId());
                fmsDprOfficeAsset.setOfAssetId(officeDPRList1.getOfAssetId());
                fmsDprOfficeAsset.setDprYear(officeDPRList1.getDprYear());
                fmsDprOfficeAsset.setAccumulatedDpr(officeDPRList1.getAccumulatedDpr());
                fmsDprOfficeAsset.setNetBookValue(officeDPRList1.getNetBookValue());
                fmsDprOfficeAsset.setTagNo(officeDPRList1.getOfAssetId().getTagNo());
                fmsDprOfficeAsset.setRevaluationCost(officeDPRList1.getRevaluationCost());
                fmsDprOfficeAsset.setRevaluationServiceYear(officeDPRList1.getRevaluationServiceYear());
                fmsDprOfficeAsset.setAccountPeriod(officeDPRList1.getAccountPeriod());
                fmsDprOfficeAsset.setAccumulatedDpr(officeDPRList1.getAccumulatedDpr());
                fmsDprOfficeAsset.setRevaluationCost(officeDPRList1.getRevaluationCost());
                fmsDprOfficeAsset.setRevaluationServiceYear(officeDPRList1.getRevaluationServiceYear());

                getDprlistOffice().add(fmsDprOfficeAsset);
                fmsDprOfficeAsset = new FmsDprOfficeAsset(null);
            }
        }

        officeDataModel = new ListDataModel(new ArrayList(dprlistOffice));

    }

    public void NewWind(FmsDprOfficeAsset fmsDprOfficeAsset) {
        officeDataList = fmsDprOfficeAssetBeanLocal.fetchOfficeAsset(fmsDprOfficeAsset);
        if (!officeDataList.isEmpty()) {
            officeDataModel = new ListDataModel(new ArrayList(officeDataList));
        } else {
            officeDataModel = null;
        }
    }

    public void saveOfficeDPR() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveOfficeDPR", dataset)) {
                if (newItemPnl == true && !dprlistOffice.isEmpty()) {   //When items are inintially saved to the FMS system.
                    int saveStat = 0;
                    for (int i = 0; i < dprlistOffice.size(); i++) {
                        fmsDprOfficeAssetBeanLocal.create(dprlistOffice.get(i));
                        saveStat = 1;
                    }
                    officeDataModel = new ListDataModel(new ArrayList(dprlistOffice));
                    if (saveStat == 1) {
                        registeredItem();
                        saveStat = 0;
                        tableBtnVal = "New Items";
                        saveCalc = "Calculate";
                        JsfUtil.addSuccessMessage("Fixed Asset is Saved");
                    }
                } else if (registeredPnl == true) {
                    int saveStat = 0;
                    if (!dprlistOffice.isEmpty()) {

                        for (int i = 0; i < dprlistOffice.size(); i++) {
                            fmsDprOfficeAsset = new FmsDprOfficeAsset();

                            if (dprlistOffice.get(i).getDprYear() == BigDecimal.ZERO && Objects.equals(dprlistOffice.get(i).getAccountPeriod(), AccountingPeriod)) {       //INITIALLY         && dprlistOffice.get(i).getOfAssetId().getRevaluationCost() == null
                                String Smonth[] = dprlistOffice.get(i).getOfAssetId().getInService().split("/");
                                int Imonth = Integer.parseInt(Smonth[1]);
                                if (Imonth == 1) {
                                    fmsDprOfficeAsset.setOfAssetId(dprlistOffice.get(i).getOfAssetId());
                                    fmsDprOfficeAsset.setDprYear(dprlistOffice.get(i).getOfAssetId().getUnitPrice().divide(new BigDecimal(dprlistOffice.get(i).getOfAssetId().getServiceLife()), 2, RoundingMode.HALF_EVEN));
                                    fmsDprOfficeAsset.setAccumulatedDpr(dprlistOffice.get(i).getDprYear());
                                    fmsDprOfficeAsset.setNetBookValue(dprlistOffice.get(i).getOfAssetId().getUnitPrice().subtract(dprlistOffice.get(i).getAccumulatedDpr()));
                                    fmsDprOfficeAsset.setStatus(1);
                                    fmsDprOfficeAsset.setAccountPeriod(AccountingPeriod);
                                    fmsDprOfficeAsset.setTagNo(dprlistOffice.get(i).getOfAssetId().getTagNo());

                                } else {
                                    fmsDprOfficeAsset.setOfAssetId(dprlistOffice.get(i).getOfAssetId());
                                    fmsDprOfficeAsset.setDprYear(((dprlistOffice.get(i).getOfAssetId().getUnitPrice().divide(new BigDecimal(dprlistOffice.get(i).getOfAssetId().getServiceLife().doubleValue() * 12), 2, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(Imonth))).setScale(2, RoundingMode.HALF_EVEN));
                                    fmsDprOfficeAsset.setAccumulatedDpr(fmsDprOfficeAsset.getDprYear());
                                    fmsDprOfficeAsset.setNetBookValue(dprlistOffice.get(i).getOfAssetId().getUnitPrice().subtract(dprlistOffice.get(i).getAccumulatedDpr()));
                                    fmsDprOfficeAsset.setStatus(1);
                                    fmsDprOfficeAsset.setAccountPeriod(AccountingPeriod);
                                    fmsDprOfficeAsset.setTagNo(dprlistOffice.get(i).getOfAssetId().getTagNo());
                                }
                            } else if (dprlistOffice.get(i).getDprYear() != BigDecimal.ZERO && !Objects.equals(dprlistOffice.get(i).getAccountPeriod().getAcountigPeriodId(), AccountingPeriod.getAcountigPeriodId())) {       //Nth time      && dprlistOffice.get(i).getOfAssetId().getRevaluationCost() == null

                                fmsDprOfficeAsset.setOfAssetId(dprlistOffice.get(i).getOfAssetId());
                                fmsDprOfficeAsset.setDprYear(dprlistOffice.get(i).getOfAssetId().getUnitPrice().divide(new BigDecimal(dprlistOffice.get(i).getOfAssetId().getServiceLife()), 2, RoundingMode.HALF_EVEN));
                                fmsDprOfficeAsset.setAccumulatedDpr(fmsDprOfficeAsset.getDprYear().add(dprlistOffice.get(i).getAccumulatedDpr()));
                                fmsDprOfficeAsset.setNetBookValue(dprlistOffice.get(i).getOfAssetId().getUnitPrice().subtract(dprlistOffice.get(i).getAccumulatedDpr()));
                                fmsDprOfficeAsset.setAccountPeriod(AccountingPeriod);
                                fmsDprOfficeAsset.setStatus(1);
                                fmsDprOfficeAsset.setTagNo(dprlistOffice.get(i).getOfAssetId().getTagNo());
                            }

                            dprlistOffice.get(i).setStatus(0);
                            if (fmsDprOfficeAsset.getDprYear() != null && dprlistOffice != null) {
                                if (fmsDprOfficeAsset.getNetBookValue().compareTo(BigDecimal.TEN) == -1) {
                                    fmsDprOfficeAsset.setNetBookValue(BigDecimal.TEN);
                                    fmsDprOfficeAssetBeanLocal.create(fmsDprOfficeAsset);
                                    fmsDprOfficeAssetBeanLocal.edit(dprlistOffice.get(i));
                                    saveStat = 1;
                                } else {
                                    fmsDprOfficeAssetBeanLocal.create(fmsDprOfficeAsset);
                                    fmsDprOfficeAssetBeanLocal.edit(dprlistOffice.get(i));
                                    saveStat = 1;
                                }
                            }
                        }
                        if (saveStat == 1) {
                            JsfUtil.addSuccessMessage("Fixed asset depreciation is calculated and saved");
                        }
                        officeDataModel = new ListDataModel(new ArrayList(dprlistOffice));
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
