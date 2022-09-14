/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs;

//import com.sun.org.apache.xalan.internal.lib.ExsltMath;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.mms.businessLogic.FixedAssetRegistrationBeanLocal;
import et.gov.eep.ifrs.businessLogic.IfrsFaRevaluationHistoryBeanLocal;
import et.gov.eep.ifrs.entity.IfrsFaRevaluationHistory;
import et.gov.eep.mms.entity.IfrsFixedAsset;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author memube
 */
@Named(value = "ifrsFixedAssetRevaluationController")
@ViewScoped
public class IfrsFixedAssetRevaluationController implements Serializable {

    @Inject
    private IfrsFaRevaluationHistory ifrsFaRevaluationHistory;
    @Inject
    private IfrsFixedAsset ifrsFixedAsset;
    @Inject
    private FmsGeneralLedger fmsGeneralLedger;
    @Inject
    private FmsSubsidiaryLedger fmsSubsidiaryLedger;

    @EJB
    private IfrsFaRevaluationHistoryBeanLocal ifrsFaRevaluationHistoryBeanLocal;
    @EJB
    private FixedAssetRegistrationBeanLocal fixedAssetRegistrationBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal fmsSubsidiaryLedgerBeanLocal;

    private List<FmsGeneralLedger> fmsGeneralLedgerList;
    private List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList;
    private DataModel<IfrsFixedAsset> ifrsFixedAssetModel;
    private List<IfrsFixedAsset> ifrsFixedAssetList;
    private IfrsFixedAsset selectedFA;
    private IfrsFaRevaluationHistory selectedFaRevHist;
    List<IfrsFixedAsset> fAList;//for search

    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateStockItem = false;
    private boolean initialStockPriceReadonly = false;
    private boolean renderPnlManPage = true;
    private boolean isRenderedIconRevHistory = false;
    private boolean isdisabledGlListBox = false;
    private boolean isdisabledSlListBox = false;
    private String icone = "ui-icon-plus";
    int updateStatus = 0;
    double differenceValue = 0.00;
    private NumberConverter numberConverter = new NumberConverter();

    /**
     * Creates a new instance of FixedAssetRevaluation
     */
    public IfrsFixedAssetRevaluationController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    public IfrsFaRevaluationHistory getIfrsFaRevaluationHistory() {
        if (ifrsFaRevaluationHistory == null) {
            ifrsFaRevaluationHistory = new IfrsFaRevaluationHistory();
        }
        return ifrsFaRevaluationHistory;
    }

    public void setIfrsFaRevaluationHistory(IfrsFaRevaluationHistory ifrsFaRevaluationHistory) {
        this.ifrsFaRevaluationHistory = ifrsFaRevaluationHistory;
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

    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public FmsSubsidiaryLedger getFmsSubsidiaryLedger() {
        if (fmsSubsidiaryLedger == null) {
            fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        }
        return fmsSubsidiaryLedger;
    }

    public void setFmsSubsidiaryLedger(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        this.fmsSubsidiaryLedger = fmsSubsidiaryLedger;
    }

    public List<FmsGeneralLedger> getFmsGeneralLedgerList() {
        if (fmsGeneralLedgerList == null) {
            fmsGeneralLedgerList = new ArrayList<>();
        }
        
        fmsGeneralLedgerList = fmsGeneralLedgerBeanLocal.getGLListForFixedAsset();
        return fmsGeneralLedgerList;
    }

    public void setFmsGeneralLedgerList(List<FmsGeneralLedger> fmsGeneralLedgerList) {
        this.fmsGeneralLedgerList = fmsGeneralLedgerList;
    }

    public List<FmsSubsidiaryLedger> getFmsSubsidiaryLedgerList() {
        if (fmsSubsidiaryLedgerList == null) {
            fmsSubsidiaryLedgerList = new ArrayList<>();
        }
        return fmsSubsidiaryLedgerList;
    }

    public void setFmsSubsidiaryLedgerList(List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList) {
        this.fmsSubsidiaryLedgerList = fmsSubsidiaryLedgerList;
    }

    public DataModel<IfrsFixedAsset> getIfrsFixedAssetModel() {
        if (ifrsFixedAssetModel == null) {
            ifrsFixedAssetModel = new ListDataModel<>();
        }
        return ifrsFixedAssetModel;
    }

    public void setIfrsFixedAssetModel(DataModel<IfrsFixedAsset> ifrsFixedAssetModel) {
        this.ifrsFixedAssetModel = ifrsFixedAssetModel;
    }

    public List<IfrsFixedAsset> getIfrsFixedAssetList() {
        if (ifrsFixedAssetList == null) {
            ifrsFixedAssetList = new ArrayList<>();
        }
        return ifrsFixedAssetList;
    }

    public void setIfrsFixedAssetList(List<IfrsFixedAsset> ifrsFixedAssetList) {
        this.ifrsFixedAssetList = ifrsFixedAssetList;
    }

    public IfrsFixedAsset getSelectedFA() {
        return selectedFA;
    }

    public void setSelectedFA(IfrsFixedAsset selectedFA) {
        this.selectedFA = selectedFA;
    }

    public IfrsFaRevaluationHistory getSelectedFaRevHist() {
        return selectedFaRevHist;
    }

    public void setSelectedFaRevHist(IfrsFaRevaluationHistory selectedFaRevHist) {
        this.selectedFaRevHist = selectedFaRevHist;
    }

    public List<IfrsFixedAsset> getfAList() {
        if (fAList == null) {
            fAList = new ArrayList<>();
        }
        return fAList;
    }

    public void setfAList(List<IfrsFixedAsset> fAList) {
        this.fAList = fAList;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateStockItem() {
        return renderPnlCreateStockItem;
    }

    public void setRenderPnlCreateStockItem(boolean renderPnlCreateStockItem) {
        this.renderPnlCreateStockItem = renderPnlCreateStockItem;
    }

    public boolean isInitialStockPriceReadonly() {
        return initialStockPriceReadonly;
    }

    public void setInitialStockPriceReadonly(boolean initialStockPriceReadonly) {
        this.initialStockPriceReadonly = initialStockPriceReadonly;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isIsRenderedIconRevHistory() {
        return isRenderedIconRevHistory;
    }

    public void setIsRenderedIconRevHistory(boolean isRenderedIconRevHistory) {
        this.isRenderedIconRevHistory = isRenderedIconRevHistory;
    }

    public boolean isIsdisabledGlListBox() {
        return isdisabledGlListBox;
    }

    public void setIsdisabledGlListBox(boolean isdisabledGlListBox) {
        this.isdisabledGlListBox = isdisabledGlListBox;
    }

    public boolean isIsdisabledSlListBox() {
        return isdisabledSlListBox;
    }

    public void setIsdisabledSlListBox(boolean isdisabledSlListBox) {
        this.isdisabledSlListBox = isdisabledSlListBox;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public Double getDifferenceValue() {
        return differenceValue;
    }

    public void setDifferenceValue(Double differenceValue) {
        this.differenceValue = differenceValue;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public void getFixedAssetListByGLCode(ValueChangeEvent event) {
        try {
            clearPage();
            if (null != event.getNewValue()) {
                fmsGeneralLedger = (FmsGeneralLedger) event.getNewValue();
                ifrsFixedAssetList = null;
                ifrsFixedAssetList = fmsSubsidiaryLedgerBeanLocal.getSLListByGlId(fmsGeneralLedger.getGeneralLedgerId());
                if (ifrsFixedAssetList.isEmpty()) {
                    JsfUtil.addFatalMessage("No data found.");
                }
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Failed to Process. Try again relaoding the Page");
        }
    }

    public void onSelectSlCode(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                ifrsFixedAsset = (IfrsFixedAsset) event.getNewValue();
                ifrsFaRevaluationHistory.setIfrsFaId(ifrsFixedAsset);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Failed to Process. Try again relaoding the Page");
        }
    }

    public void saveStockRevaluation() {
//        AAA securityService = new AAA();
//        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//        String systemBundle = "cfg/securityServer";
//        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
//        if (security.checkAccess(SessionBean.getUserName(), "saveAppealRequest", dataset)) {
//                 put ur code here...! 
        try {
            ifrsFaRevaluationHistory.setIfrsFaId(ifrsFixedAsset);
            ifrsFaRevaluationHistory.setOldRevCost(ifrsFixedAsset.getRevalutionCost());
            ifrsFixedAsset.setRevalutionCost(ifrsFaRevaluationHistory.getNewRevCost());//edit ifrsFixedAsset revaluation cost
            if (updateStatus == 0) {
                fixedAssetRegistrationBeanLocal.edit(ifrsFixedAsset);
                ifrsFaRevaluationHistoryBeanLocal.create(ifrsFaRevaluationHistory);
                JsfUtil.addSuccessMessage("Saved Successfully!");
                clearPage();
            } else {
                fixedAssetRegistrationBeanLocal.edit(ifrsFixedAsset);
                ifrsFaRevaluationHistoryBeanLocal.edit(ifrsFaRevaluationHistory);
                JsfUtil.addSuccessMessage("Updated Successfully!");
                clearPage();
            }

        } catch (Exception e) {
            JsfUtil.addFatalMessage("Failed to Save. Try Agian Reloading the Page.");
        }
    }

    public void clearPage() {
        ifrsFixedAsset = null;
        ifrsFaRevaluationHistory = null;
        fmsGeneralLedger = null;
        fmsSubsidiaryLedger = null;
        ifrsFixedAssetModel = new ListDataModel<>();
        ifrsFixedAssetList = new ArrayList<>();
        fmsGeneralLedgerList = new ArrayList<>();
        differenceValue = 0.00;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }

    public void createNewStockRevaluation() {
        clearPage();
        saveorUpdateBundle = "Save";
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateStockItem = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateStockItem = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void searchFmsStockLedgerCard() {
//        if (ifrsFixedAsset.getMaterialCode() != null && ifrsFixedAsset.getStoreNo() == null) {
//            slcList = fixedAssetRegistrationBeanLocal.findByMatCode(ifrsFixedAsset);
//            ifrsFixedAssetModel = new ListDataModel(slcList);
//        } else if (ifrsFixedAsset.getMaterialCode() == null && ifrsFixedAsset.getStoreNo() != null) {
//            slcList = fixedAssetRegistrationBeanLocal.findByStoreNo(ifrsFixedAsset);
//            ifrsFixedAssetModel = new ListDataModel(slcList);
//        } else if (ifrsFixedAsset.getMaterialCode() != null && ifrsFixedAsset.getStoreNo() != null) {
//            slcList = fixedAssetRegistrationBeanLocal.findByAll(ifrsFixedAsset);
//            ifrsFixedAssetModel = new ListDataModel(slcList);
//        } else {
        fAList = fixedAssetRegistrationBeanLocal.findAllRevaluedFA();
        ifrsFixedAssetModel = new ListDataModel(fAList);
//        }
    }

    public void populateStockRevaluation(SelectEvent event) {
        try {
            ifrsFixedAsset = (IfrsFixedAsset) event.getObject();
            ifrsFaRevaluationHistory.setIfrsFaId(ifrsFixedAsset);
            fmsGeneralLedger = ifrsFixedAsset.getSubsidiaryId().getGeneralLedgerId();
            ifrsFixedAssetList = new ArrayList<>();
            ifrsFixedAssetList.add(ifrsFixedAsset);
            isRenderedIconRevHistory = true;
            renderPnlCreateStockItem = true;
            renderPnlManPage = false;
            isdisabledGlListBox = true;
            isdisabledSlListBox = true;
            saveorUpdateBundle = "Update";
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            updateStatus = 1;
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Failed to populate. try again reloading the page.");
        }
    }

    public void onSelectFA(SelectEvent event) {
        try {
            if (null != event.getObject()) {
                ifrsFaRevaluationHistory = (IfrsFaRevaluationHistory) event.getObject();
                ifrsFixedAsset = ifrsFaRevaluationHistory.getIfrsFaId();
                calculateDifferenceValue();
                System.out.println("--------differenceValue------" + differenceValue);
//                differenceValue = ExsltMath.abs(ifrsFaRevaluationHistory.getNewRevCost().doubleValue() - ifrsFixedAsset.getRevalutionCost().doubleValue());
                System.out.println("--------differenceValue 2------" + differenceValue);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    public void calculateDifferenceValue() {
        try {
        //    differenceValue = ExsltMath.abs(ifrsFaRevaluationHistory.getNewRevCost().doubleValue() - ifrsFixedAsset.getRevalutionCost().doubleValue());
        } catch (Exception e) {
        }
    }
}
