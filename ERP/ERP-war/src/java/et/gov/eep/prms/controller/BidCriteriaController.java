/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.BidCriteriaBeanLocal;
import et.gov.eep.prms.entity.PrmsBidCriteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
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
@Named("bidCriteriaController")
@ViewScoped
public class BidCriteriaController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="declaration and initialization">
    @EJB
    BidCriteriaBeanLocal criteriaBeanLocal;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    @Inject
    PrmsBidCriteria prmsBidCriteria;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workFlow;
    DataModel<PrmsBidCriteria> bidCriteriaMdl;
    List<PrmsBidCriteria> bidCriteriaList;
    List<PrmsBidCriteria> bidCriteriaSearchParameterLst;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate = false;
    private String duplicattion = null;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderpnlToSearchPage;
    private String activeIndex;
    private String icone = "ui-icon-plus";
    int updateStatus = 0;
    private String loggerName;
    PrmsBidCriteria prmsBidCriteriaSelect;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Post Construct ">
    @PostConstruct
    public void init() {
        prmsBidCriteria.setPreparedBy(workFlow.getUserAccount());
        setLoggerName(workFlow.getUserName());
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
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

    public PrmsBidCriteria getPrmsBidCriteriaSelect() {
        return prmsBidCriteriaSelect;
    }

    public void setPrmsBidCriteriaSelect(PrmsBidCriteria prmsBidCriteriaSelect) {
        this.prmsBidCriteriaSelect = prmsBidCriteriaSelect;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public List<PrmsBidCriteria> getBidCriteriaList() {
        if (bidCriteriaList == null) {
            bidCriteriaList = new ArrayList();
        }
        return bidCriteriaList;
    }

    public void setBidCriteriaList(List<PrmsBidCriteria> bidCriteriaList) {
        this.bidCriteriaList = bidCriteriaList;
    }

    public List<PrmsBidCriteria> getBidCriteriaSearchParameterLst() {
        if (bidCriteriaSearchParameterLst == null) {
            bidCriteriaSearchParameterLst = new ArrayList<>();
            bidCriteriaSearchParameterLst = criteriaBeanLocal.getParamNameList();
        }
        return bidCriteriaSearchParameterLst;
    }

    public void setBidCriteriaSearchParameterLst(List<PrmsBidCriteria> bidCriteriaSearchParameterLst) {
        this.bidCriteriaSearchParameterLst = bidCriteriaSearchParameterLst;
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

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public DataModel<PrmsBidCriteria> getBidCriteriaMdl() {
        if (bidCriteriaMdl == null) {
            bidCriteriaMdl = new ListDataModel<>();
        }
        return bidCriteriaMdl;
    }

    public void setBidCriteriaMdl(DataModel<PrmsBidCriteria> bidCriteriaMdl) {
        this.bidCriteriaMdl = bidCriteriaMdl;
    }

    public PrmsBidCriteria getPrmsBidCriteria() {
        if (prmsBidCriteria == null) {
            prmsBidCriteria = new PrmsBidCriteria();
        }
        return prmsBidCriteria;
    }

    public void setPrmsBidCriteria(PrmsBidCriteria prmsBidCriteria) {
        this.prmsBidCriteria = prmsBidCriteria;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Methods">
    public void recreatMdel() {
        bidCriteriaMdl = null;
        bidCriteriaMdl = new ListDataModel<>(new ArrayList(getBidCriteriaList()));
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsBidCriteria.setColumnName(event.getNewValue().toString());
            prmsBidCriteria.setColumnValue(null);
        }
    }
    /*This method is used to select row       
     */

    public void rowSelect(SelectEvent event) {
        prmsBidCriteria = (PrmsBidCriteria) event.getObject();
        prmsBidCriteria.setId(prmsBidCriteria.getId());
        prmsBidCriteria = criteriaBeanLocal.getBidCriteriaId(prmsBidCriteria.getId());
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        saveorUpdateBundle = "Update";
        renderpnlToSearchPage = true;

    }
    /*This method is used to search
    
     */

    public void search_BidCriteria() {
        prmsBidCriteria.setPreparedBy(workFlow.getUserAccount());
        bidCriteriaList = criteriaBeanLocal.searchCriterialNo(prmsBidCriteria);
        recreatMdel();
        prmsBidCriteria = new PrmsBidCriteria();
    }
    /*This method is used to clear search 
    
     */

    public void clearsearch() {
        prmsBidCriteria = null;
    }
    /*This method is used to exchenge save and button icon
    
     */

    public void createNewBidCriteria() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            clearsearch();
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
    /*This method is used to clear search page 
        
     */

    public void clear() {
        prmsBidCriteria = null;
        saveorUpdateBundle = "Save";
    }
    String newCriteriaNo;
    String bidCriteriaNo = "0";
    /*This method is used to generate auto numeber 
    
     */

    public String generateBidCiteriaNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newCriteriaNo = prmsBidCriteria.getCriteriaNo();

        } else {
            PrmsBidCriteria lastBidCriteriaNo = criteriaBeanLocal.getBidCriteria();
            if (lastBidCriteriaNo != null) {
                bidCriteriaNo = lastBidCriteriaNo.getId();
            }

            int newBidCriteriaList = 0;
            String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
            if (bidCriteriaNo.equals("0")) {
                newBidCriteriaList = 1;
                newCriteriaNo = "BC-NO-" + newBidCriteriaList;
            } else {
                String[] lastInspNos = bidCriteriaNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidCriteriaList = Integer.parseInt(lastDatesPaterns[0]);
                newBidCriteriaList = newBidCriteriaList + 1;
                newCriteriaNo = "BC-NO-" + newBidCriteriaList + "/" + eYear;
            }
        }
        return newCriteriaNo;

    }
    /*This method is used to save
    
     */

    public String save_BidCriteria() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "save_BidCriteria", dataset)) {
                try {
                    if (saveorUpdateBundle.equals("Save")) {
                        generateBidCiteriaNo();
                        prmsBidCriteria.setCriteriaNo(newCriteriaNo);
                        prmsBidCriteria.setPreparedBy(wfPrmsProcessed.getProcessedBy());
                        prmsBidCriteria.setStatus(Constants.PREPARE_VALUE);
                        prmsBidCriteria.setPreparedBy(workFlow.getUserAccount());
                        criteriaBeanLocal.save(prmsBidCriteria);
                        JsfUtil.addSuccessMessage("Data is Created");
                        clear();

                    } else {
                        generateBidCiteriaNo();
                        criteriaBeanLocal.update(prmsBidCriteria);
                        saveorUpdateBundle = "Save";
                        JsfUtil.addSuccessMessage("Data is Updated");
                        clear();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    clear();
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
            ex.printStackTrace();
        }

        return null;
    }
    /*This method is used to fetch bid number from bid registration
    
     */

    public SelectItem[] getBidNo() {
        return JsfUtil.getSelectItems(criteriaBeanLocal.getBidNo(), true);
    }
    ////</editor-fold>
}
