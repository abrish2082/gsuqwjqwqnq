/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs;

import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.ifrs.businessLogic.DepreciationSetupBeanLocal;
import et.gov.eep.mms.businessLogic.FixedAssetRegistrationBeanLocal;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import et.gov.eep.mms.entity.IfrsFixedAsset;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author user
 */
@Named("depreciationCompCalcControllers")
@ViewScoped
public class DepreciationCompCalcController implements Serializable {

    @EJB
    DepreciationSetupBeanLocal depreciationSetupBeanLocal;
    @EJB
    FixedAssetRegistrationBeanLocal registrationBeanLocal;
    private DataModel<IfrsFixedAsset> ifrsFixedAssetDataModel;
    @EJB
    private subsidiaryBeanLocal subsidiaryBeanLocal;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedger;
    List<IfrsFixedAsset> fixedAssetlist;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    IfrsFixedAsset ifrsFixedAsset;
    @Inject
    IfrsDepreciationSetup depreciationSetup;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;

    List<FmsSubsidiaryLedger> subsideryCodeList;

    //<editor-fold defaultstate="collapsed" desc="postConstructor">
    @PostConstruct
    public void init() {
        //populateFixed();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public FmsSubsidiaryLedger getFmsSubsidiaryLedger() {
        if (fmsSubsidiaryLedger == null) {
            fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        }
        return fmsSubsidiaryLedger;
    }

    public void setFmsSubsidiaryLedger(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        this.fmsSubsidiaryLedger = fmsSubsidiaryLedger;
    }

    public List<FmsSubsidiaryLedger> getSubsideryCodeList() {
        return subsideryCodeList;
    }

    public void setSubsideryCodeList(List<FmsSubsidiaryLedger> subsideryCodeList) {
        this.subsideryCodeList = subsideryCodeList;
    }

    public IfrsFixedAsset getIfrsFixedAsset() {
        if (ifrsFixedAsset == null) {
            ifrsFixedAsset = new IfrsFixedAsset();
        }
        return ifrsFixedAsset;
    }

    public void setIfrsFixedAsset(IfrsFixedAsset ifrsFixedAsset) {
        this.ifrsFixedAsset = ifrsFixedAsset;
    }

    public IfrsDepreciationSetup getDepreciationSetup() {
        if (depreciationSetup == null) {
            depreciationSetup = new IfrsDepreciationSetup();
        }
        return depreciationSetup;
    }

    public void setDepreciationSetup(IfrsDepreciationSetup depreciationSetup) {
        this.depreciationSetup = depreciationSetup;
    }

    public DataModel<IfrsFixedAsset> getIfrsFixedAssetDataModel() {
        if (ifrsFixedAssetDataModel == null) {
            ifrsFixedAssetDataModel = new ArrayDataModel<>();
        }
        return ifrsFixedAssetDataModel;
    }

    public void setIfrsFixedAssetDataModel(DataModel<IfrsFixedAsset> ifrsFixedAssetDataModel) {
        this.ifrsFixedAssetDataModel = ifrsFixedAssetDataModel;
    }
//</editor-fold>

    public void handleListner(ValueChangeEvent event) {

        try {
            depreciationSetup = null;
            fixedAssetlist.clear();
            ifrsFixedAssetDataModel = new ArrayDataModel<>();

            if (event.getNewValue().toString() != null) {
                depreciationSetup = (IfrsDepreciationSetup) event.getNewValue();
                fixedAssetlist = registrationBeanLocal.findByDepreciationType(depreciationSetup);
                for (int i = 0; i < fixedAssetlist.size(); i++) {
                    IfrsFixedAsset get = fixedAssetlist.get(i);
                    Double depreciationAnyPeriod = fixedAssetlist.get(i).getInitialCost() - fixedAssetlist.get(i).getSalvageCost() / fixedAssetlist.get(i).getUseFullLifeTime();
//                 ifrsFixedAsset.setNetBookValue(depreciationAnyPeriod);
                }
                ifrsFixedAssetDataModel = new ListDataModel(new ArrayList<>(fixedAssetlist));
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public List<IfrsDepreciationSetup> getListofGroups() {
        List<IfrsDepreciationSetup> list = null;
        list = depreciationSetupBeanLocal.depreciationList();
        return list;
    }
    Double depreciationInAnyPeriod = 0.0;

    public Double getDepreciationInAnyPeriod() {
        return depreciationInAnyPeriod;
    }

    public void setDepreciationInAnyPeriod(Double depreciationInAnyPeriod) {
        this.depreciationInAnyPeriod = depreciationInAnyPeriod;
    }

    public void populateFixed() {
        fixedAssetlist = registrationBeanLocal.findAll();
        for (int i = 0; i < fixedAssetlist.size(); i++) {
            IfrsFixedAsset ifrsFixedAsset = fixedAssetlist.get(i);
            if ((ifrsFixedAsset.getFagId().getId() != null) && (ifrsFixedAsset.getFagId().getId() == 21)) { //straght line depreciation type
//Depreciation in Any Period = ((Cost - Salvage) / Life)
                Double depreciationAnyPeriod = fixedAssetlist.get(i).getInitialCost() - fixedAssetlist.get(i).getSalvageCost() / fixedAssetlist.get(i).getUseFullLifeTime();
                //ifrsFixedAsset.setNetBookValue(depreciationAnyPeriod);
                Double bv = 0.0;
                String currentDate = StringDateManipulation.toDayInEc();
                System.out.print("The current date in EC is ==>" + StringDateManipulation.toDayInEc());
//                int datediff = StringDateManipulation.datesDifferenceInDays(fixedAssetlist.get(i).getInService(), currentDate);
//                int ItemLifeYear = datediff / 365;
//                while (ItemLifeYear <= fixedAssetlist.get(i).getUseFullLifeTime()) {
//                    bv = fixedAssetlist.get(i).getInitialCost() - ItemLifeYear * depreciationAnyPeriod;
//                }
//                depreciationInAnyPeriod = (ifrsFixedAsset.getRevalutionCost()) / ifrsFixedAsset.getUseFullLifeTime();
            } else {

            }

        }
        ifrsFixedAssetDataModel = new ListDataModel(new ArrayList<>(fixedAssetlist));
    }

    public List<FmsGeneralLedger> getGlList() {
        List<FmsGeneralLedger> glList = null;
        glList = fmsGeneralLedgerBeanLocal.getGeneralLedgerCodeList();
        return glList;
    }
    String glCode[];

    public SelectItem[] handleSelectGlCode(ValueChangeEvent event) {
        glCode = event.getNewValue().toString().split("--");
        int id = Integer.parseInt(glCode[0]);
        fmsGeneralLedger = null;
        fmsGeneralLedger = fmsGeneralLedgerBeanLocal.findByMasterId(id);
        if (glCode != null) {
            setSubsideryCodeList(subsidiaryBeanLocal.findSubsideryCodeByGlCode(fmsGeneralLedger));
            return Utility.JsfUtil.getSelectItems(getSubsideryCodeList(), true);
        } else {
            return Utility.JsfUtil.getSelectItems(null, true);
        }
    }

    public SelectItem[] handleSelectGlCode1(ValueChangeEvent event) {
//        glCode = event.getNewValue().toString().split("--");
//        int id = Integer.parseInt(glCode[0]);
        int id = Integer.parseInt(event.getNewValue().toString());
        System.out.println("====== id1 " + id);
        if (event.getNewValue() != null) {
            fmsGeneralLedger = null;
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.findByMasterId(id);

            System.out.println("====== id2 " + fmsGeneralLedger.getGeneralLedgerId());
            subsideryCodeList = subsidiaryBeanLocal.findSubsideryCodeByGlCode(fmsGeneralLedger);
//            setSubsideryCodeList(subsidiaryBeanLocal.findSubsideryCodeByGlCode(fmsGeneralLedger));
            System.out.println("====== 1 " + getSubsideryCodeList());
//            System.out.println("====2"+fmsGeneralLedger.getGeneralLedgerCode());
            System.out.println("3===" + subsideryCodeList.size());
            return Utility.JsfUtil.getSelectItems(getSubsideryCodeList(), true);
        } else {
            return Utility.JsfUtil.getSelectItems(null, true);
        }
    }
//    public void calcDepreciation() {
//
//        int datediff = StringDateManipulation.datesDifferenceInDays(fixedAssetlist.get(i).getInService(), currentDate);
//        int ItemLifeYear = datediff / 365;
//
//    }
    double itemLifeYears = 0.0;
//    public ArrayList deprciationList = new ArrayList();
    List<IfrsFixedAsset> fixedAssets = new ArrayList<>();

    public List<IfrsFixedAsset> getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(List<IfrsFixedAsset> fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    public void getListOfAttributesByCategory(ValueChangeEvent e) {
        System.out.println("---------event-----" );
        String currentDate = StringDateManipulation.toDayInEc();
        fixedAssets.clear();
        if (null != e.getNewValue() && !e.getNewValue().equals("")) {

            fmsSubsidiaryLedger = subsidiaryBeanLocal.getSubsidiaryCode(Integer.parseInt(e.getNewValue().toString()));
            ifrsFixedAsset = fmsSubsidiaryLedger.getFixedassetList();
            int datediff = StringDateManipulation.datesDifferenceInDays(ifrsFixedAsset.getInService(), currentDate);
            Double depreciationAnyPeriod = ifrsFixedAsset.getInitialCost() - ifrsFixedAsset.getSalvageCost() / ifrsFixedAsset.getUseFullLifeTime();

            if (datediff >= 365) {
                itemLifeYears = datediff % 365;
            }
            for (int i = 0; i < ifrsFixedAsset.getUseFullLifeTime(); i++) {
                Double accumulateDep = itemLifeYears * depreciationAnyPeriod;
                Double bookValue = ifrsFixedAsset.getInitialCost() - accumulateDep;
//                ifrsFixedAsset.setDepreciationYear(currentDate);
                ifrsFixedAsset.setAccumulatedDepreciation(String.valueOf(accumulateDep));
                ifrsFixedAsset.setNetBookValue(String.valueOf(bookValue));
                fixedAssets.add(ifrsFixedAsset);
            }
            System.out.println("----fixedAssets---" + fixedAssets);
        }
//        generateFixAssNo();

    }
}
