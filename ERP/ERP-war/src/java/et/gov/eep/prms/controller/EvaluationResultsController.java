/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.businessLogic.FinancialResultBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvalResult;
import et.gov.eep.prms.entity.PrmsFinancialEvaluaDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvaluation;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsThechincalEvaluationDet;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.swap;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named("evaluationResultsController")
@ViewScoped
public class EvaluationResultsController implements Serializable {

    @EJB
    FinancialResultBeanLocal financialResultBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @Inject
    PrmsFinancialEvalResult prmsFinancialEvalResult;
    @Inject
    PrmsFinancialEvlResultyDtl financialEvlResultyDtl;
    @Inject
    PrmsFinancialEvaluation financialEvaluation;
    @Inject
    PrmsBid prmsBid;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    PrmsBidDetail prmsBidDetail;
    @Inject
    PrmsQuotation prmsQuotation;
    @Inject
    PrmsServiceAndWorkReg prmsServiceAndWorkReg;
    @Inject
    PrmsQuotationDetail prmsQuotationDetail;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workFlow;
    DataModel<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlMdl;
    private DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<PrmsFinancialEvalResult> financialEvalResultMdl;
    List<PrmsFinancialEvalResult> financialEvalResultList;
    List<PrmsBid> prmsBidList;
    List<PrmsQuotation> prmsQuotationList;
    List<PrmsThechincalEvaluationDet> thechincalEvaluationDetList;
    List<PrmsQuotationDetail> prmsQuotationDetailList;
    List<PrmsServiceAndWorkReg> prmsServiceAndWorkRegList;
    Integer requestNotificationCounter = 0;
    List<PrmsFinancialEvaluation> prmsFinancialEvaluationLst;
    List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailLst;
    List<PrmsFinancialEvalResult> financialResSearchParameterLst;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean proforma = true;
    private boolean awardType = false;
    private boolean bid = false;
    private boolean lotTabel;
    private boolean itemTable = true;

    private boolean mainPnlFrItmLot = false;
    PrmsFinancialEvalResult financialEvalResultSelect;

    boolean renderCompliance = true;
    boolean renderMerit = false;

    boolean renderItembaseItem = false;
    boolean renderItembaseService = false;
    boolean renderItembaseWork = false;

    boolean renderLotItem = true;
    boolean renderLotService = false;
    boolean renderLotWork = false;

    private boolean isRenderedIconWorkflow = false;
    private boolean isDecision = false;
    private boolean isCommentGiven = false;
    int selectRowIndex;
    private boolean isCreateButton;
    private boolean renderpnlToSearchPage;
    String selectedValue = "";

    @PostConstruct
    public void init() {
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
        System.out.println("" + workFlow.isApproveStatus() + "or" + workFlow.isPrepareStatus() + "or " + workFlow.isCheckStatus());
        if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
            isDecision = true;
            isCommentGiven = true;
            isCreateButton = false;

        } else {
            isDecision = false;
            isCommentGiven = false;
            isCreateButton = true;
        }
        System.out.println("" + workFlow.isApproveStatus() + "or" + workFlow.isPrepareStatus() + "or " + workFlow.isCheckStatus());
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public boolean isIsCreateButton() {
        return isCreateButton;
    }

    public void setIsCreateButton(boolean isCreateButton) {
        this.isCreateButton = isCreateButton;
    }

    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
    }

    public boolean isIsDecision() {
        return isDecision;
    }

    public void setIsDecision(boolean isDecision) {
        this.isDecision = isDecision;
    }

    public boolean isIsCommentGiven() {
        return isCommentGiven;
    }

    public void setIsCommentGiven(boolean isCommentGiven) {
        this.isCommentGiven = isCommentGiven;
    }

    public boolean isRenderLotItem() {
        return renderLotItem;
    }

    public void setRenderLotItem(boolean renderLotItem) {
        this.renderLotItem = renderLotItem;
    }

    public boolean isRenderLotService() {
        return renderLotService;
    }

    public void setRenderLotService(boolean renderLotService) {
        this.renderLotService = renderLotService;
    }

    public boolean isRenderLotWork() {
        return renderLotWork;
    }

    public void setRenderLotWork(boolean renderLotWork) {
        this.renderLotWork = renderLotWork;
    }

    public boolean isRenderItembaseItem() {
        return renderItembaseItem;
    }

    public void setRenderItembaseItem(boolean renderItembaseItem) {
        this.renderItembaseItem = renderItembaseItem;
    }

    public boolean isRenderItembaseService() {
        return renderItembaseService;
    }

    public void setRenderItembaseService(boolean renderItembaseService) {
        this.renderItembaseService = renderItembaseService;
    }

    public boolean isRenderItembaseWork() {
        return renderItembaseWork;
    }

    public void setRenderItembaseWork(boolean renderItembaseWork) {
        this.renderItembaseWork = renderItembaseWork;
    }

    public boolean isRenderCompliance() {
        return renderCompliance;
    }

    public void setRenderCompliance(boolean renderCompliance) {
        this.renderCompliance = renderCompliance;
    }

    public boolean isRenderMerit() {
        return renderMerit;
    }

    public void setRenderMerit(boolean renderMerit) {
        this.renderMerit = renderMerit;
    }

    public List<PrmsFinancialEvaluation> getPrmsFinancialEvaluationLst() {
        return prmsFinancialEvaluationLst;
    }

    public void setPrmsFinancialEvaluationLst(List<PrmsFinancialEvaluation> prmsFinancialEvaluationLst) {
        this.prmsFinancialEvaluationLst = prmsFinancialEvaluationLst;
    }

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ListDataModel<>();
        }
        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public Integer getRequestNotificationCounter() {
        financialEvalResultList = financialResultBeanLocal.getFinancialOnList();
        requestNotificationCounter = financialEvalResultList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    public List<PrmsFinancialEvaluaDetail> getPrmsFinancialEvaluaDetailLst() {
        if (prmsFinancialEvaluaDetailLst == null) {
            prmsFinancialEvaluaDetailLst = new ArrayList<>();
        }
        return prmsFinancialEvaluaDetailLst;
    }

    public void setPrmsFinancialEvaluaDetailLst(List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailLst) {
        this.prmsFinancialEvaluaDetailLst = prmsFinancialEvaluaDetailLst;
    }

    public List<PrmsQuotationDetail> getPrmsQuotationDetailList() {
        if (prmsQuotationDetailList == null) {
            prmsQuotationDetailList = new ArrayList<>();
        }
        return prmsQuotationDetailList;
    }

    public void setPrmsQuotationDetailList(List<PrmsQuotationDetail> prmsQuotationDetailList) {
        this.prmsQuotationDetailList = prmsQuotationDetailList;
    }

    public List<PrmsThechincalEvaluationDet> getThechincalEvaluationDetList() {
        if (thechincalEvaluationDetList == null) {
            thechincalEvaluationDetList = new ArrayList();
        }

        return thechincalEvaluationDetList;
    }

    public void setThechincalEvaluationDetList(List<PrmsThechincalEvaluationDet> thechincalEvaluationDetList) {
        this.thechincalEvaluationDetList = thechincalEvaluationDetList;
    }

    public List<PrmsQuotation> getPrmsQuotationList() {
        if (prmsQuotationList == null) {
            prmsQuotationList = new ArrayList<>();
        }
        return prmsQuotationList;
    }

    public void setPrmsQuotationList(List<PrmsQuotation> prmsQuotationList) {
        this.prmsQuotationList = prmsQuotationList;
    }

    public PrmsQuotation getPrmsQuotation() {
        if (prmsQuotation == null) {
            prmsQuotation = new PrmsQuotation();
        }
        return prmsQuotation;
    }

    public void setPrmsQuotation(PrmsQuotation prmsQuotation) {
        this.prmsQuotation = prmsQuotation;
    }

    public List<PrmsBid> getPrmsBidList() {
        return prmsBidList;
    }

    public void setPrmsBidList(List<PrmsBid> prmsBidList) {
        this.prmsBidList = prmsBidList;
    }

    public PrmsFinancialEvalResult getFinancialEvalResultSelect() {
        return financialEvalResultSelect;
    }

    public void setFinancialEvalResultSelect(PrmsFinancialEvalResult financialEvalResultSelect) {
        this.financialEvalResultSelect = financialEvalResultSelect;
    }

    public List<PrmsFinancialEvalResult> getFinancialEvalResultList() {
        return financialEvalResultList;
    }

    public void setFinancialEvalResultList(List<PrmsFinancialEvalResult> financialEvalResultList) {
        this.financialEvalResultList = financialEvalResultList;
    }

    public PrmsFinancialEvalResult getPrmsFinancialEvalResult() {
        if (prmsFinancialEvalResult == null) {
            prmsFinancialEvalResult = new PrmsFinancialEvalResult();
        }
        return prmsFinancialEvalResult;
    }

    public void setPrmsFinancialEvalResult(PrmsFinancialEvalResult prmsFinancialEvalResult) {
        this.prmsFinancialEvalResult = prmsFinancialEvalResult;
    }

    public PrmsFinancialEvlResultyDtl getFinancialEvlResultyDtl() {
        if (financialEvlResultyDtl == null) {
            financialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
        }
        return financialEvlResultyDtl;
    }

    public void setFinancialEvlResultyDtl(PrmsFinancialEvlResultyDtl financialEvlResultyDtl) {
        this.financialEvlResultyDtl = financialEvlResultyDtl;
    }

    public PrmsFinancialEvaluation getFinancialEvaluation() {
        if (financialEvaluation == null) {
            financialEvaluation = new PrmsFinancialEvaluation();
        }
        return financialEvaluation;
    }

    public void setFinancialEvaluation(PrmsFinancialEvaluation financialEvaluation) {
        this.financialEvaluation = financialEvaluation;
    }

    public DataModel<PrmsFinancialEvlResultyDtl> getPrmsFinancialEvlResultyDtlMdl() {
        if (prmsFinancialEvlResultyDtlMdl == null) {
            prmsFinancialEvlResultyDtlMdl = new ListDataModel<>();

        }
        return prmsFinancialEvlResultyDtlMdl;
    }

    public void setPrmsFinancialEvlResultyDtlMdl(DataModel<PrmsFinancialEvlResultyDtl> prmsFinancialEvlResultyDtlMdl) {
        this.prmsFinancialEvlResultyDtlMdl = prmsFinancialEvlResultyDtlMdl;
    }

    public DataModel<PrmsFinancialEvalResult> getFinancialEvalResultMdl() {
        if (financialEvalResultMdl == null) {
            financialEvalResultMdl = new ListDataModel<>();

        }
        return financialEvalResultMdl;
    }

    public List<PrmsServiceAndWorkReg> getPrmsServiceAndWorkRegList() {
        if (prmsServiceAndWorkRegList == null) {
            prmsServiceAndWorkRegList = new ArrayList<>();
        }
        return prmsServiceAndWorkRegList;
    }

    public void setPrmsServiceAndWorkRegList(List<PrmsServiceAndWorkReg> prmsServiceAndWorkRegList) {
        this.prmsServiceAndWorkRegList = prmsServiceAndWorkRegList;
    }

    public void setFinancialEvalResultMdl(DataModel<PrmsFinancialEvalResult> financialEvalResultMdl) {
        this.financialEvalResultMdl = financialEvalResultMdl;
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

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }

    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isAwardType() {
        return awardType;
    }

    public void setAwardType(boolean awardType) {
        this.awardType = awardType;
    }

    public boolean isLotTabel() {
        return lotTabel;
    }

    public void setLotTabel(boolean lotTabel) {
        this.lotTabel = lotTabel;
    }

    public boolean isItemTable() {
        return itemTable;
    }

    public void setItemTable(boolean itemTable) {
        this.itemTable = itemTable;
    }

    public boolean isMainPnlFrItmLot() {
        return mainPnlFrItmLot;
    }

    public void setMainPnlFrItmLot(boolean mainPnlFrItmLot) {
        this.mainPnlFrItmLot = mainPnlFrItmLot;
    }

    private String icone = "ui-icon-plus";

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isProforma() {
        return proforma;
    }

    public void setProforma(boolean proforma) {
        this.proforma = proforma;
    }

    public boolean isBid() {
        return bid;
    }

    public void setBid(boolean bid) {
        this.bid = bid;
    }

    public PrmsBid getPrmsBid() {
        if (prmsBid == null) {
            prmsBid = new PrmsBid();
        }
        return prmsBid;
    }

    public void setPrmsBid(PrmsBid prmsBid) {
        this.prmsBid = prmsBid;
    }

    public List<PrmsFinancialEvalResult> getFinancialResSearchParameterLst() {
        if (financialResSearchParameterLst == null) {
            financialResSearchParameterLst = new ArrayList<>();
            financialResSearchParameterLst = financialResultBeanLocal.getParamNameList();
        }
        return financialResSearchParameterLst;
    }

    public void setFinancialResSearchParameterLst(List<PrmsFinancialEvalResult> financialResSearchParameterLst) {
        this.financialResSearchParameterLst = financialResSearchParameterLst;
    }

    public void createNewFinancialResult() {

        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void goBackToSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsFinancialEvalResult.setColumnName(event.getNewValue().toString());
            prmsFinancialEvalResult.setColumnValue(null);
        }
    }

    public void rowSelect(SelectEvent event) {
        prmsFinancialEvalResult = (PrmsFinancialEvalResult) event.getObject();
        popUp();
        saveorUpdateBundle = "Update";

    }

    public void popUp() {
        prmsFinancialEvalResult.setId(prmsFinancialEvalResult.getId());
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        renderpnlToSearchPage = true;
        setIsRenderedIconWorkflow(true);
        String resultFrm = prmsFinancialEvalResult.getResultFrom();
        String awardTypes = prmsFinancialEvalResult.getAwardType();
        int size = financialEvlResultyDtls.size();
        for (int x = 0; x < size - 1; x++) {
            int lowindex = x;
            for (int y = financialEvlResultyDtls.size() - 1; y > x; y--) {

                if (financialEvlResultyDtls.get(y).getUnitPrice() < financialEvlResultyDtls.get(lowindex).getUnitPrice()) {
                    lowindex = y;
                }
            }

            swap(financialEvlResultyDtls, x, lowindex);
        }
        if (!saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(prmsFinancialEvalResult.getDateReg());
        }
        if (!saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
            prmsQuotation.setPurchaseType(prmsFinancialEvalResult.getPurchaseType());
        }
        if (resultFrm.equalsIgnoreCase("performa")) {

            proforma = true;
            awardType = false;
        } else {
            proforma = false;
            awardType = true;
        }
        if (prmsFinancialEvalResult.getPurchaseType().equalsIgnoreCase("Goods")) {
            renderItembaseItem = true;
            renderItembaseService = false;
            renderItembaseWork = false;
        } else if (prmsFinancialEvalResult.getPurchaseType().equalsIgnoreCase("Service")) {
            renderItembaseItem = false;
            renderItembaseService = true;
            renderItembaseWork = false;
        } else {
            renderItembaseItem = false;
            renderItembaseService = false;
            renderItembaseWork = true;
        }
        if (resultFrm.equalsIgnoreCase("Bid")) {
            if (awardTypes.equalsIgnoreCase("Item Base")) {

                mainPnlFrItmLot = true;
                itemTable = true;
                lotTabel = false;
            } else {
                mainPnlFrItmLot = true;
                itemTable = false;
                lotTabel = true;
            }

        }
        if (prmsFinancialEvalResult.getPurchaseType() != null) {
//            prmsQuotation.setPurchaser(prmsFinancialEvalResult.getPurchaseType());
            prmsBid.setBidCategory(prmsFinancialEvalResult.getPurchaseType());
        }
        reCreatModel();
    }

    public void reCreatModel() {
        System.out.println("in recreate model" + prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().size());
        prmsFinancialEvlResultyDtlMdl = null;
        prmsFinancialEvlResultyDtlMdl = new ListDataModel<>(new ArrayList<>(prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList()));
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public void reCreateFinancialResMdl() {
        // financialEvalResultMdl = null;
        financialEvalResultMdl = new ListDataModel<>(new ArrayList<>(getFinancialEvalResultList()));
    }

    public void AddToDataTable() {
        prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().add(financialEvlResultyDtl);
        financialEvlResultyDtl.setFnancialResultId(financialEvalResultSelect);
        financialEvlResultyDtl = null;
        reCreatModel();
    }
    String evaluationFrom = "";

    public String getEvaluationFrom() {
        return evaluationFrom;
    }

    public void setEvaluationFrom(String evaluationFrom) {
        this.evaluationFrom = evaluationFrom;
    }

    public String save_EvaluationResultAction() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "save_EvaluationResult", dataset)) {
                try {
                    if (saveorUpdateBundle.equals("Save")) {

                        wfPrmsProcessed.setFinancialEvaluationResultId(prmsFinancialEvalResult);
                        wfPrmsProcessed.setProcessedBy(prmsFinancialEvalResult.getPreparedBy());
                        wfPrmsProcessed.setDecision(prmsFinancialEvalResult.getRemark());
                        wfPrmsProcessed.setProcessedOn(prmsFinancialEvalResult.getDateReg());

                        prmsFinancialEvalResult.setDateReg(wfPrmsProcessed.getProcessedOn());
                        prmsFinancialEvalResult.setStatus(Constants.PREPARE_VALUE);
                        prmsFinancialEvalResult.setFinancialResultNo(newFinancialRsltNo);
                        financialResultBeanLocal.create(prmsFinancialEvalResult);
                        prmsFinancialEvalResult.getWfPrmsProcessedCollection().add(wfPrmsProcessed);
                        clearPage();
                        JsfUtil.addSuccessMessage("Data is succussful Saved");
                    } else if (saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
                        prmsFinancialEvalResult.setDateReg(wfPrmsProcessed.getProcessedOn());
                        prmsFinancialEvalResult.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        financialResultBeanLocal.edit(prmsFinancialEvalResult);
                        saveorUpdateBundle = "Save";
                        clearPage();
                        JsfUtil.addSuccessMessage("Data is Updated");
                    } else if (saveorUpdateBundle.equals("Update") && (workFlow.isApproveStatus() || workFlow.isCheckStatus())) {
                        System.out.println("----During Decision----");
                        if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            prmsFinancialEvalResult.setStatus(Constants.APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            prmsFinancialEvalResult.setStatus(workFlow.getUserStatus());
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            prmsFinancialEvalResult.setStatus(workFlow.getUserStatus());
                        } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            prmsFinancialEvalResult.setStatus(workFlow.getUserStatus());
                        }
                        prmsFinancialEvalResult.setDateApproved(wfPrmsProcessed.getProcessedOn());
                        financialResultBeanLocal.edit(prmsFinancialEvalResult);
                        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        wfPrmsProcessed = null;
                        JsfUtil.addSuccessMessage("Data is Approved");
                    }
                } catch (Exception e) {
                    JsfUtil.addErrorMessage("data is not saved");
                }
                clearPage();
                return null;

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsFinancialEvalResult = (PrmsFinancialEvalResult) event.getNewValue();
            popUp();
            saveorUpdateBundle = "Update";
        }
    }

    public void clearPage() {
        prmsFinancialEvalResult = null;
        bidDetailLists = null;
        prmsBidList = null;
        prmsFinancialEvaluationLst = null;
        prmsQuotationDetailList = null;
        prmsQuotationList = null;
        prmsFinancialEvlResultyDtlMdl = null;
        prmsQuotation = null;
        mmsItemRegistrationList = null;
        prmsServiceAndWorkRegList = null;
        prmsBid = null;
        bidDetailLists = null;

    }

    public void edit() {

        financialEvlResultyDtl = prmsFinancialEvlResultyDtlMdl.getRowData();
        selectRowIndex = prmsFinancialEvlResultyDtlMdl.getRowIndex();
    }

    public void search_EvaluationResultAction() {
        prmsFinancialEvalResult.setPreparedBy(workFlow.getUserAccount());
        financialEvalResultList = financialResultBeanLocal.searchFinancialResult(prmsFinancialEvalResult);
        reCreateFinancialResMdl();
        prmsFinancialEvalResult=new PrmsFinancialEvalResult();
    }

    public void changeEvaluationFrom(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().clear();
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Bid")) {
                proforma = false;
                mainPnlFrItmLot = true;
                bid = true;
                awardType = true;

            } else {
                mainPnlFrItmLot = false;
                proforma = true;
                bid = false;
                awardType = false;
            }
            if (select.equalsIgnoreCase("performa")) {

                itemTable = true;
                lotTabel = false;
            } else {
                itemTable = false;
                lotTabel = true;
            }

        }

    }
    String itemName = "Item";

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void changeAwardType(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String select = "select";
            select = event.getNewValue().toString();
            if (select.equalsIgnoreCase("Item")) {
                System.out.println("oooooooooo");
                itemTable = true;
                lotTabel = false;

            } else {
                itemTable = false;
                lotTabel = true;

            }
        }
    }

    public List<PrmsQuotation> getProformaList() {
        setPrmsQuotationList(financialResultBeanLocal.getProformaList());
        return prmsQuotationList;
    }

    public void valueChangeListItemInProform(ValueChangeEvent changeEvent) {
        System.out.println("----item Code in Proforma----" + changeEvent.getNewValue().toString());
        prmsQuotation = new PrmsQuotation();
        prmsQuotation = (PrmsQuotation) changeEvent.getNewValue();
        prmsFinancialEvalResult.setPurchaseType(prmsQuotation.getPurchaseType());
        String purchaseType = "";
        purchaseType = prmsQuotation.getPurchaseType();
        System.out.println("purchase type+++" + purchaseType);
        if ("Goods".equals(purchaseType)) {
            renderItembaseItem = true;
            renderItembaseService = false;
            renderItembaseWork = false;
            mmsItemRegistrationList = financialResultBeanLocal.getItemInQuatation(changeEvent.getNewValue().toString());
        } else if ("Service".equals(purchaseType)) {

            renderItembaseItem = false;
            renderItembaseService = true;
            renderItembaseWork = false;
            prmsServiceAndWorkRegList = financialResultBeanLocal.getServiceFrmQuotation(changeEvent.getNewValue().toString());
        } else {
            renderItembaseItem = false;
            renderItembaseService = false;
            renderItembaseWork = true;
            prmsServiceAndWorkRegList = financialResultBeanLocal.getWorkFrmQuotation(changeEvent.getNewValue().toString());
        }

        reCreatModel();
    }

    public PrmsBidDetail getPrmsBidDetail() {
        if (prmsBidDetail == null) {
            prmsBidDetail = new PrmsBidDetail();
        }
        return prmsBidDetail;
    }

    public void setPrmsBidDetail(PrmsBidDetail prmsBidDetail) {
        this.prmsBidDetail = prmsBidDetail;
    }
    List<String> lotName;

    public List<String> getLotName() {
        return lotName;
    }

    public void setLotName(List<String> lotName) {
        this.lotName = lotName;
    }

    public void changeValueFrItmloat(ValueChangeEvent event) {

        prmsBid = new PrmsBid();
        prmsBid = (PrmsBid) event.getNewValue();
        prmsBidDetail.setBidId(prmsBid);
        prmsFinancialEvalResult.setPurchaseType(prmsBid.getBidCategory());
        System.out.println(".......bid for lot......" + event.getNewValue().toString());
        bidDetailLists = financialResultBeanLocal.getBidDetailListForLotBased(prmsBid);
        System.out.println(".......bid category......" + prmsBid.getBidCategory());

        if (prmsBid.getBidCategory().equalsIgnoreCase("Goods")) {
            renderLotItem = true;
            renderLotService = false;
            renderLotWork = false;
        } else if (prmsBid.getBidCategory().equalsIgnoreCase("Service")) {
            renderLotItem = false;
            renderLotService = true;
            renderLotWork = false;
        } else if (prmsBid.getBidCategory().equalsIgnoreCase("Work")) {
            renderLotItem = false;
            renderLotService = false;
            renderLotWork = true;
        }

        // prmsBidDetailList = financialResultBeanLocal.getItemLotPack();
    }

    List<PrmsBidDetail> bidDetailLists = new ArrayList<>();

    public List<PrmsBidDetail> getBidDetailLists() {
        return bidDetailLists;
    }

    public void setBidDetailLists(List<PrmsBidDetail> bidDetailLists) {
        this.bidDetailLists = bidDetailLists;
    }
    List<MmsItemRegistration> mmsItemRegistrationList = new ArrayList<>();

    public List<MmsItemRegistration> getMmsItemRegistrationList() {
        return mmsItemRegistrationList;
    }

    public void setMmsItemRegistrationList(List<MmsItemRegistration> mmsItemRegistrationList) {
        this.mmsItemRegistrationList = mmsItemRegistrationList;
    }

    public void changeValueItemBase(ValueChangeEvent event) {

        prmsBid = new PrmsBid();
        prmsBid = (PrmsBid) event.getNewValue();
        prmsBidDetail.setBidId(prmsBid);
        prmsFinancialEvalResult.setPurchaseType(prmsBid.getBidCategory());
        System.out.println("bid category" + prmsBid.getBidCategory());
        if ("Goods".equals(prmsBid.getBidCategory())) {
            bidDetailLists = financialResultBeanLocal.getItemBasedList(prmsBid);
            renderItembaseItem = true;
            renderItembaseService = false;
            renderItembaseWork = false;
        } else if ("Service".equals(prmsBid.getBidCategory())) {
            bidDetailLists = financialResultBeanLocal.getItemBasedServiceList(prmsBid);
            renderItembaseItem = false;
            renderItembaseService = true;
            renderItembaseWork = false;
        } else if ("Work".equals(prmsBid.getBidCategory())) {
            bidDetailLists = financialResultBeanLocal.getItemBasedWorkList(prmsBid);
            renderItembaseItem = false;
            renderItembaseService = false;
            renderItembaseWork = true;
        }
    }
    String newFinancialRsltNo;

    public String generateFinancialRsltNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newFinancialRsltNo = prmsFinancialEvalResult.getFinancialResultNo();

        } else {
            newFinancialRsltNo = getfinancialResultNoSeq();
        }
        return newFinancialRsltNo;
    }

    public String getfinancialResultNoSeq() {
        String financialResltNo = financialResultBeanLocal.getfinancialResltNoSeq();
        return financialResltNo;
    }
    List<PrmsFinancialEvaluaDetail> financialEvalDtl = new ArrayList<>();
    List<PrmsThechincalEvaluationDet> technicalEvalDtl = new ArrayList<>();
    List<PrmsFinancialEvlResultyDtl> resultTobeRecreat = new ArrayList<>();

    public void changeValueItemBaseRank(ValueChangeEvent event) {

        PrmsBidDetail bidDtl = new PrmsBidDetail();
        bidDtl = (PrmsBidDetail) event.getNewValue();

        if (bidDtl.getFermSeltionMethd().equalsIgnoreCase("compliance")) {

            renderMerit = false;
            renderCompliance = true;
            financialEvalDtl = financialResultBeanLocal.getItemsForCompliance(bidDtl);
            int evaResultDtlSize = 0;
            evaResultDtlSize = financialEvalDtl.size();
            for (int x = 0; x < evaResultDtlSize - 1; x++) {
                int lowindex = x;
                for (int y = financialEvalDtl.size() - 1; y > x; y--) {
                    if (financialEvalDtl.get(y).getUnitPrice() < financialEvalDtl.get(lowindex).getUnitPrice()) {
                        lowindex = y;
                    }
                }
                swap(financialEvalDtl, x, lowindex);

            }
            double vat = 0.15;
            double with_hold = 10000.00;
            double percent = 0.02;
            List<Double> list = new ArrayList<>();
            for (int i = 0; i < financialEvalDtl.size(); i++) {
                list.add(financialEvalDtl.get(i).getUnitPrice());
            }

            for (int i = 0; i < financialEvalDtl.size(); i++) {
                financialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
                double rankedList = financialEvalDtl.get(i).getUnitPrice();
                int rankvalue = rankResult(list, rankedList);
                financialEvlResultyDtl.setBidDetailId(bidDtl);
                financialEvlResultyDtl.setSupplierId(financialEvalDtl.get(i).getSupplierId());
                financialEvlResultyDtl.setItemId(financialEvalDtl.get(i).getItemRegistrationId());
                financialEvlResultyDtl.setUnitPrice(financialEvalDtl.get(i).getUnitPrice());
                financialEvlResultyDtl.setTotalPrice(financialEvalDtl.get(i).getUnitPrice() * financialEvalDtl.get(i).getQuantity());
                financialEvlResultyDtl.setVat(financialEvlResultyDtl.getTotalPrice() * vat);
                if (financialEvlResultyDtl.getTotalPrice() >= with_hold) {
                    financialEvlResultyDtl.setWithHold(financialEvlResultyDtl.getTotalPrice() * percent);
                }
                financialEvlResultyDtl.setResultRank(rankvalue);
                prmsFinancialEvalResult.addEvalResultDtl(financialEvlResultyDtl);

            }

        } else {
            renderMerit = true;
            renderCompliance = false;
            financialEvalDtl = financialResultBeanLocal.getItemsForMerit(bidDtl);

            double sum = 0.0;
            System.out.println("...size for merit....." + financialEvalDtl.size());
            double finResult = 0.0;
            for (int j = 0; j < financialEvalDtl.size(); j++) {
//                technicalEvalDtl = financialResultBeanLocal.getItemsForMeritForTech(bidDtl);
                double techScore;
//                for (int i = 0; i < technicalEvalDtl.size(); i++) {
                double minPrice = financialEvalDtl.get(j).getUnitPrice();
                for (int x = 0; x < financialEvalDtl.size(); x++) {
                    if (financialEvalDtl.get(x).getUnitPrice() < minPrice) {
                        minPrice = financialEvalDtl.get(x).getUnitPrice();
                    }
                }
                finResult = minPrice * financialEvalDtl.get(j).getBidDetailId().getFinancial() / financialEvalDtl.get(j).getUnitPrice();
                techScore = financialEvalDtl.get(j).getBidDetailId().getTechnical();
                sum = finResult + techScore;

                financialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
                financialEvlResultyDtl.setBidDetailId(bidDtl);
                financialEvlResultyDtl.setSupplierId(financialEvalDtl.get(j).getSupplierId());
                financialEvlResultyDtl.setFinancialResult(finResult);
                financialEvlResultyDtl.setUnitPrice(financialEvalDtl.get(j).getUnitPrice());
                financialEvlResultyDtl.setTechnicalResult(financialEvalDtl.get(j).getBidDetailId().getTechnical());
                financialEvlResultyDtl.setItemId(financialEvalDtl.get(j).getItemRegistrationId());
                financialEvlResultyDtl.setTotalPrice(sum);

                financialEvlResultyDtls.add(financialEvlResultyDtl);

//                }
            }
            for (int x = 0; x < financialEvlResultyDtls.size() - 1; x++) {
                int lowindex = x;
                for (int y = financialEvlResultyDtls.size() - 1; y > x; y--) {
                    if (financialEvlResultyDtls.get(y).getUnitPrice() < financialEvlResultyDtls.get(lowindex).getUnitPrice()) {
                        lowindex = y;
                    }
                }
                swap(financialEvlResultyDtls, x, lowindex);
            }
            List<Double> list = new ArrayList<>();
            for (int i = 0; i < financialEvlResultyDtls.size(); i++) {
                list.add(financialEvlResultyDtls.get(i).getUnitPrice());
            }

            for (int i = 0; i < financialEvlResultyDtls.size(); i++) {

                financialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
                double rankedList = financialEvlResultyDtls.get(i).getUnitPrice();
                int rankvalue = rankResult(list, rankedList);
                financialEvlResultyDtl.setBidDetailId(bidDtl);
                financialEvlResultyDtl.setSupplierId(financialEvlResultyDtls.get(i).getSupplierId());
                financialEvlResultyDtl.setFinancialResult(financialEvlResultyDtls.get(i).getFinancialResult());
                financialEvlResultyDtl.setTechnicalResult(financialEvlResultyDtls.get(i).getTechnicalResult());
                financialEvlResultyDtl.setItemId(financialEvlResultyDtls.get(i).getItemId());
                financialEvlResultyDtl.setUnitPrice(financialEvlResultyDtls.get(i).getUnitPrice());
                financialEvlResultyDtl.setTotalPrice(financialEvlResultyDtls.get(i).getTotalPrice());
                financialEvlResultyDtl.setResultRank(rankvalue);
                prmsFinancialEvalResult.addEvalResultDtl(financialEvlResultyDtl);
                resultTobeRecreat = prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList();
            }

        }
        reCreatModel();
    }

    /**
     *
     * @param event
     */
    public void valueChangeForSupplier(ValueChangeEvent event) {

        prmsFinancialEvalResult.getPrmsFinancialEvlResultyDtlList().clear();
        prmsQuotationDetailList = financialResultBeanLocal.getSupplierList(event.getNewValue().toString());
        int evaResultDtlSize = 0;
        evaResultDtlSize = prmsQuotationDetailList.size();

        for (int x = 0; x < evaResultDtlSize - 1; x++) {
            int lowindex = x;
            for (int y = prmsQuotationDetailList.size() - 1; y > x; y--) {
                if (prmsQuotationDetailList.get(y).getUnitPrice() < prmsQuotationDetailList.get(lowindex).getUnitPrice()) {
                    lowindex = y;
                }
            }
            swap(prmsQuotationDetailList, x, lowindex);
        }
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < prmsQuotationDetailList.size(); i++) {
            list.add(prmsQuotationDetailList.get(i).getUnitPrice());
        }
        for (int i = 0; i < prmsQuotationDetailList.size(); i++) {
            financialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
            double rankedList = prmsQuotationDetailList.get(i).getUnitPrice();
            int rankvalue = rankResult(list, rankedList);

            financialEvlResultyDtl.setSupplierId(prmsQuotationDetailList.get(i).getSupId());
            financialEvlResultyDtl.setItemId(prmsQuotationDetailList.get(i).getMaterialCodeId());
            financialEvlResultyDtl.setUnitPrice(prmsQuotationDetailList.get(i).getUnitPrice());
////                financialEvlResultyDtl.setTotalPrice(prmsQuotationDetailList.get(i).getUnitPrice() * prmsQuotationDetailList.get(i).getQuantity());
//                if (financialEvlResultyDtl.getTotalPrice() >= with_hold) {
//                    financialEvlResultyDtl.setWithHold(financialEvlResultyDtl.getTotalPrice() * percent);
//                }
//                financialEvlResultyDtl.setVat(financialEvlResultyDtl.getTotalPrice() * vat);
            financialEvlResultyDtl.setResultRank(rankvalue);
            prmsFinancialEvalResult.addEvalResultDtl(financialEvlResultyDtl);

        }
        reCreatModel();

    }

    public PrmsServiceAndWorkReg getPrmsServiceAndWorkReg() {
        if (prmsServiceAndWorkReg == null) {
            prmsServiceAndWorkReg = new PrmsServiceAndWorkReg();
        }
        return prmsServiceAndWorkReg;
    }

    public void setPrmsServiceAndWorkReg(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        this.prmsServiceAndWorkReg = prmsServiceAndWorkReg;
    }

    public PrmsQuotationDetail getPrmsQuotationDetail() {
        return prmsQuotationDetail;
    }

    public void setPrmsQuotationDetail(PrmsQuotationDetail prmsQuotationDetail) {
        this.prmsQuotationDetail = prmsQuotationDetail;
    }

    public void valueChangeForSupplierFrmServ(ValueChangeEvent event) {
        System.out.println("ggg++++" + event.getNewValue().toString());
//        PrmsServiceAndWorkReg serviceAndWorkReg = new PrmsServiceAndWorkReg();
//        serviceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
//        prmsQuotationDetail.setServAndWorkId(prmsServiceAndWorkReg);
        prmsQuotationDetailList = financialResultBeanLocal.getSupplierListFrService(event.getNewValue().toString());
        int evaResultDtlSize = 0;
        evaResultDtlSize = prmsQuotationDetailList.size();

        for (int x = 0; x < evaResultDtlSize - 1; x++) {
            int lowindex = x;
            for (int y = prmsQuotationDetailList.size() - 1; y > x; y--) {
                if (prmsQuotationDetailList.get(y).getUnitPrice() < prmsQuotationDetailList.get(lowindex).getUnitPrice()) {
                    lowindex = y;
                }
            }
            swap(prmsQuotationDetailList, x, lowindex);
        }
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < prmsQuotationDetailList.size(); i++) {
            list.add(prmsQuotationDetailList.get(i).getUnitPrice());
        }
        for (int i = 0; i < prmsQuotationDetailList.size(); i++) {
            financialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
            double rankedList = prmsQuotationDetailList.get(i).getUnitPrice();
            int rankvalue = rankResult(list, rankedList);

            financialEvlResultyDtl.setSupplierId(prmsQuotationDetailList.get(i).getSupId());
            financialEvlResultyDtl.setServiceId(prmsQuotationDetailList.get(i).getServAndWorkId());
            financialEvlResultyDtl.setUnitPrice(prmsQuotationDetailList.get(i).getUnitPrice());
////                financialEvlResultyDtl.setTotalPrice(prmsQuotationDetailList.get(i).getUnitPrice() * prmsQuotationDetailList.get(i).getQuantity());
//                if (financialEvlResultyDtl.getTotalPrice() >= with_hold) {
//                    financialEvlResultyDtl.setWithHold(financialEvlResultyDtl.getTotalPrice() * percent);
//                }
//                financialEvlResultyDtl.setVat(financialEvlResultyDtl.getTotalPrice() * vat);
            financialEvlResultyDtl.setResultRank(rankvalue);
            prmsFinancialEvalResult.addEvalResultDtl(financialEvlResultyDtl);

        }
        reCreatModel();

    }

    public void valueChangeForSupplierFrmWork(ValueChangeEvent event) {
        System.out.println("ggg++++" + event.getNewValue().toString());
        PrmsServiceAndWorkReg serviceAndWorkReg = new PrmsServiceAndWorkReg();
        serviceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
//        prmsQuotationDetail.setServAndWorkId(prmsServiceAndWorkReg);
//        prmsQuotationDetailList = financialResultBeanLocal.getSupplierListFrService(serviceAndWorkReg);
        int evaResultDtlSize = 0;
        evaResultDtlSize = prmsQuotationDetailList.size();

        for (int x = 0; x < evaResultDtlSize - 1; x++) {
            int lowindex = x;
            for (int y = prmsQuotationDetailList.size() - 1; y > x; y--) {
                if (prmsQuotationDetailList.get(y).getUnitPrice() < prmsQuotationDetailList.get(lowindex).getUnitPrice()) {
                    lowindex = y;
                }
            }
            swap(prmsQuotationDetailList, x, lowindex);
        }
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < prmsQuotationDetailList.size(); i++) {
            list.add(prmsQuotationDetailList.get(i).getUnitPrice());
        }
        for (int i = 0; i < prmsQuotationDetailList.size(); i++) {
            financialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
            double rankedList = prmsQuotationDetailList.get(i).getUnitPrice();
            int rankvalue = rankResult(list, rankedList);

            financialEvlResultyDtl.setSupplierId(prmsQuotationDetailList.get(i).getSupId());
            financialEvlResultyDtl.setServiceId(prmsQuotationDetailList.get(i).getServAndWorkId());
            financialEvlResultyDtl.setUnitPrice(prmsQuotationDetailList.get(i).getUnitPrice());
////                financialEvlResultyDtl.setTotalPrice(prmsQuotationDetailList.get(i).getUnitPrice() * prmsQuotationDetailList.get(i).getQuantity());
//                if (financialEvlResultyDtl.getTotalPrice() >= with_hold) {
//                    financialEvlResultyDtl.setWithHold(financialEvlResultyDtl.getTotalPrice() * percent);
//                }
//                financialEvlResultyDtl.setVat(financialEvlResultyDtl.getTotalPrice() * vat);
            financialEvlResultyDtl.setResultRank(rankvalue);
            prmsFinancialEvalResult.addEvalResultDtl(financialEvlResultyDtl);

        }
        reCreatModel();

    }
    PrmsFinancialEvaluaDetail prmsFinancialEvaluaDetail = new PrmsFinancialEvaluaDetail();
    List<PrmsSupplyProfile> prmsSupplyProfileList;

    public List<PrmsSupplyProfile> getPrmsSupplyProfileList() {
        if (prmsSupplyProfileList == null) {
            prmsSupplyProfileList = new ArrayList<>();
        }
        return prmsSupplyProfileList;
    }

    public void setPrmsSupplyProfileList(List<PrmsSupplyProfile> prmsSupplyProfileList) {
        this.prmsSupplyProfileList = prmsSupplyProfileList;
    }
    PrmsSupplyProfile prmsSupplyProfile;

    public PrmsSupplyProfile getPrmsSupplyProfile() {
        return prmsSupplyProfile;
    }

    public void setPrmsSupplyProfile(PrmsSupplyProfile prmsSupplyProfile) {
        this.prmsSupplyProfile = prmsSupplyProfile;
    }

    public int rankResult(List<Double> list, Double value) {
        //Collections.sort(list, Collections.reverseOrder());
        Collections.sort(list);
        int i = 0;
        for (Double item : list) {
            i++;
            System.out.println("......rankResult....." + i);
            if (Objects.equals(item, value)) {
                System.out.println("......ifff....." + i);
                return i;
            }
        }
        return 0;
    }

    List<PrmsFinancialEvlResultyDtl> financialEvlResultyDtls = new ArrayList<>();

    public List<PrmsFinancialEvlResultyDtl> getFinancialEvlResultyDtls() {
        return financialEvlResultyDtls;
    }

    public void setFinancialEvlResultyDtls(List<PrmsFinancialEvlResultyDtl> financialEvlResultyDtls) {
        this.financialEvlResultyDtls = financialEvlResultyDtls;
    }

    public void changeValueOfLot(ValueChangeEvent event) {

        PrmsBidDetail bDetail = new PrmsBidDetail();
        bDetail = (PrmsBidDetail) event.getNewValue();
        prmsFinancialEvaluaDetail.setBidDetailId(prmsBidDetail);
        String fermSelection = bDetail.getFermSeltionMethd();
        if (fermSelection.equalsIgnoreCase("compliance")) {
            List<String> supplierId = financialResultBeanLocal.getEvalutionResult(prmsBid);
            for (int j = 0; j < supplierId.size(); j++) {

                int bDet = prmsBid.getPrmsBidDetailList().get(j).getPrmsFinancialEvaluaDetailList().size();
                System.out.println("in of" + bDet);
                PrmsSupplyProfile supplyProfile = new PrmsSupplyProfile();
                for (int y = 0; y < bDet; y++) {

                    supplyProfile.setId(prmsBid.getPrmsBidDetailList().get(j).getPrmsFinancialEvaluaDetailList().get(y).getSupplierId().getId());
                }
                double totalprice = 0;
                List<PrmsFinancialEvaluaDetail> unitPrices = financialResultBeanLocal.getListOfSpplierPrice(prmsBid, supplyProfile);
                System.out.println("unitPricessize" + unitPrices.size());
                for (int i = 0; i < unitPrices.size(); i++) {
                    System.out.println("id=" + unitPrices.get(i).getId());
                    System.out.println("unitPrice=" + unitPrices.get(i).getUnitPrice());
                    totalprice = totalprice + unitPrices.get(i).getUnitPrice();
                    supplyProfile = unitPrices.get(i).getSupplierId();
                }
                financialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
                financialEvlResultyDtl.setSupplierId(supplyProfile);
                financialEvlResultyDtl.setLotNo(unitPrices.get(0).getLotNo());
                financialEvlResultyDtl.setTotalPrice(totalprice);
                financialEvlResultyDtls.add(financialEvlResultyDtl);
            }
            int size = financialEvlResultyDtls.size();
            for (int x = 0; x < size - 1; x++) {
                int lowindex = x;
                for (int y = financialEvlResultyDtls.size() - 1; y > x; y--) {

                    if (financialEvlResultyDtls.get(y).getTotalPrice() < financialEvlResultyDtls.get(lowindex).getTotalPrice()) {
                        lowindex = y;
                    }
                }

                swap(financialEvlResultyDtls, x, lowindex);
            }
            List<Double> list = new ArrayList<>();
            for (int i = 0; i < financialEvlResultyDtls.size(); i++) {
                list.add(financialEvlResultyDtls.get(i).getTotalPrice());
            }
            for (int i = 0; i < financialEvlResultyDtls.size(); i++) {

                double rankedList = financialEvlResultyDtls.get(i).getTotalPrice();
                int rankvalue = rankResult(list, rankedList);
                System.out.println("......rankResult2==" + rankvalue);
                financialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
                financialEvlResultyDtl.setSupplierId(financialEvlResultyDtls.get(i).getSupplierId());
                financialEvlResultyDtl.setLotNo(financialEvlResultyDtls.get(i).getLotNo());
                financialEvlResultyDtl.setTotalPrice(financialEvlResultyDtls.get(i).getTotalPrice());

                financialEvlResultyDtl.setResultRank(rankvalue);
                prmsFinancialEvalResult.addEvalResultDtl(financialEvlResultyDtl);
            }

        } else if (fermSelection.equalsIgnoreCase("merit")) {
            List<String> supplierId = financialResultBeanLocal.getEvalutionResult(prmsBid);

            for (int k = 0; k < supplierId.size(); k++) {

                double techScore = 0.0;
                double finanRus = 0.0;
                for (int i = 0; i < supplierId.size(); i++) {
                    finanRus = finanRus + prmsBid.getPrmsBidDetailList().get(k).getFinancial() / supplierId.size();
                    techScore = techScore + prmsBid.getPrmsBidDetailList().get(k).getTechnical() / supplierId.size();
                }
                System.out.println("average fin" + finanRus);
                System.out.println("average tech" + techScore);
                int bDet = prmsBid.getPrmsBidDetailList().get(k).getPrmsFinancialEvaluaDetailList().size();
                PrmsSupplyProfile supplyProfile = new PrmsSupplyProfile();
                double totalprice = 0;
                for (int y = 0; y < bDet; y++) {

                    supplyProfile.setId(prmsBid.getPrmsBidDetailList().get(k).getPrmsFinancialEvaluaDetailList().get(y).getSupplierId().getId());
                }
                double sum = 0.0;

                List<PrmsFinancialEvaluaDetail> SupplierList = financialResultBeanLocal.getListOfSpplierPrice(prmsBid, supplyProfile);
                System.out.println("supp list==" + SupplierList.size());

                for (int i = 0; i < SupplierList.size(); i++) {
                    totalprice = totalprice + SupplierList.get(i).getUnitPrice();
                    supplyProfile = SupplierList.get(i).getSupplierId();

//                    }
                }
                System.out.println("total price" + totalprice);
                financialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
                financialEvlResultyDtl.setSupplierId(supplyProfile);
                financialEvlResultyDtl.setLotNo(SupplierList.get(0).getLotNo());
                financialEvlResultyDtl.setTotalPrice(totalprice);
                financialEvlResultyDtl.setFinancialResult(finanRus);
                financialEvlResultyDtl.setTechnicalResult(techScore);
                financialEvlResultyDtls.add(financialEvlResultyDtl);

//            }
            }
            int size = financialEvlResultyDtls.size();
            for (int x = 0; x < size - 1; x++) {
                int lowindex = x;
                for (int y = financialEvlResultyDtls.size() - 1; y > x; y--) {

                    if (financialEvlResultyDtls.get(y).getTotalPrice() < financialEvlResultyDtls.get(lowindex).getTotalPrice()) {
                        lowindex = y;
                    }
                }

                swap(financialEvlResultyDtls, x, lowindex);
            }
            List<Double> list = new ArrayList<>();
            for (int i = 0; i < financialEvlResultyDtls.size(); i++) {
                list.add(financialEvlResultyDtls.get(i).getTotalPrice());
            }
            for (int i = 0; i < financialEvlResultyDtls.size(); i++) {
                double finResultMinimum = 0;
                double sumOfTechNadFin = 0;

                double minPrice = financialEvlResultyDtls.get(i).getTotalPrice();
                for (int x = 0; x < financialEvlResultyDtls.size(); x++) {
                    if (financialEvlResultyDtls.get(x).getTotalPrice() < minPrice) {
                        minPrice = financialEvlResultyDtls.get(x).getTotalPrice();
                    }
                }
                finResultMinimum = minPrice * financialEvlResultyDtls.get(i).getFinancialResult() / financialEvlResultyDtls.get(i).getTotalPrice();
                sumOfTechNadFin = finResultMinimum + financialEvlResultyDtls.get(i).getTechnicalResult();
                System.out.println("minimum of financila " + finResultMinimum);
                double rankedList = financialEvlResultyDtls.get(i).getTotalPrice();
                int rankvalue = rankResult(list, rankedList);
                financialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
                financialEvlResultyDtl.setSupplierId(financialEvlResultyDtls.get(i).getSupplierId());
                financialEvlResultyDtl.setLotNo(financialEvlResultyDtls.get(i).getLotNo());
                financialEvlResultyDtl.setTotalPrice(financialEvlResultyDtls.get(i).getTotalPrice());
                financialEvlResultyDtl.setFinancialResult(finResultMinimum);
                financialEvlResultyDtl.setTechnicalResult(financialEvlResultyDtls.get(i).getTechnicalResult());
                financialEvlResultyDtl.setSum(sumOfTechNadFin);
                financialEvlResultyDtl.setResultRank(rankvalue);
                prmsFinancialEvalResult.addEvalResultDtl(financialEvlResultyDtl);
            }
        }

        reCreatModel();
    }

    public List<PrmsBid> getBidNo() {
        setPrmsBidList(financialResultBeanLocal.getBidNoList());
        return prmsBidList;
    }

    public List<PrmsBid> getBidNoFrItemBased() {
        setPrmsBidList(financialResultBeanLocal.getBidNoFrItemBased());
        return prmsBidList;
    }

}
