/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.overtime;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.overtime.OverTimeRateBeanLocal;
import et.gov.eep.hrms.entity.overtime.HrOvertimeRates;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author meles
 */
@Named(value = "OtRateController")
@ViewScoped
public class overtimeRateController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Initializations ">
    @EJB
            OverTimeRateBeanLocal overtimeratebeanLocal;
    @Inject
            HrOvertimeRates hrOvertimeRate;
    @Inject
            SessionBean SessionBean;
    DataModel<HrOvertimeRates> overtimeDataModel = new ListDataModel<>();
    HrOvertimeRates selectedRow = null;
    private boolean renderPnlCreateGatePass = false;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean position = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String tab = "disabled";
    private boolean renderPnlApproved = false;
    private boolean checkBoxReqst = false;
    private boolean checkBoxApprove = false;
    private ArrayList<SelectItem> listofratetype = new ArrayList<>();
    private String saveOrUpdateButton = "Save";
    int update = 0;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void init() {
        setListofratetype(ListofRateType());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public ArrayList<SelectItem> getListofratetype() {
        return listofratetype;
    }
    
    public void setListofratetype(ArrayList<SelectItem> listofratetype) {
        this.listofratetype = listofratetype;
    }
    
    public OverTimeRateBeanLocal getOvertimeratebeanLocal() {
        return overtimeratebeanLocal;
    }
    
    public void setOvertimeratebeanLocal(OverTimeRateBeanLocal overtimeratebeanLocal) {
        this.overtimeratebeanLocal = overtimeratebeanLocal;
    }
    
    public HrOvertimeRates getHrOvertimeRate() {
        if (hrOvertimeRate == null) {
            hrOvertimeRate = new HrOvertimeRates();
        }
        return hrOvertimeRate;
    }
    
    public void setHrOvertimeRate(HrOvertimeRates hrOvertimeRate) {
        this.hrOvertimeRate = hrOvertimeRate;
    }
    
    public DataModel<HrOvertimeRates> getOvertimeDataModel() {
        return overtimeDataModel;
    }
    
    public void setOvertimeDataModel(DataModel<HrOvertimeRates> overtimeDataModel) {
        this.overtimeDataModel = overtimeDataModel;
    }
    
    public HrOvertimeRates getSelectedRow() {
        return selectedRow;
    }
    
    public void setSelectedRow(HrOvertimeRates selectedRow) {
        this.selectedRow = selectedRow;
    }
    
    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }
    
    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }
    
    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }
    
    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
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
    
    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }
    
    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }
    
    public boolean isPosition() {
        return position;
    }
    
    public void setPosition(boolean position) {
        this.position = position;
    }
    
    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }
    
    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }
    
    public String getIcone() {
        return icone;
    }
    
    public void setIcone(String icone) {
        this.icone = icone;
    }
    
    public String getActiveIndex() {
        return activeIndex;
    }
    
    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }
    
    public String getTab() {
        return tab;
    }
    
    public void setTab(String tab) {
        this.tab = tab;
    }
    
    public boolean isRenderPnlApproved() {
        return renderPnlApproved;
    }
    
    public void setRenderPnlApproved(boolean renderPnlApproved) {
        this.renderPnlApproved = renderPnlApproved;
    }
    
    public boolean isCheckBoxReqst() {
        return checkBoxReqst;
    }
    
    public void setCheckBoxReqst(boolean checkBoxReqst) {
        this.checkBoxReqst = checkBoxReqst;
    }
    
    public boolean isCheckBoxApprove() {
        return checkBoxApprove;
    }
    
    public void setCheckBoxApprove(boolean checkBoxApprove) {
        this.checkBoxApprove = checkBoxApprove;
    }
    
    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }
    
    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
    }
    
    public int getUpdate() {
        return update;
    }
    
    public void setUpdate(int update) {
        this.update = update;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main methods">
    public void createNewAdditionalAmount() {
        
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    List<HrOvertimeRates> overtimelist;
    
    public List<HrOvertimeRates> getOvertimelist() {
        return overtimelist;
    }
    
    public void setOvertimelist(List<HrOvertimeRates> overtimelist) {
        this.overtimelist = overtimelist;
    }
    
    public void findByRateName() {
        
        if (hrOvertimeRate.getOtTypes().isEmpty()) {
            overtimelist = overtimeratebeanLocal.findAll(hrOvertimeRate);
            overtimeDataModel = new ListDataModel(new ArrayList(overtimelist));
        } else {
            overtimelist = overtimeratebeanLocal.findbyRateTypes(hrOvertimeRate);
            overtimeDataModel = new ListDataModel(new ArrayList(overtimelist));
        }
    }
    
    public void populate(SelectEvent events) {
        hrOvertimeRate = null;
        hrOvertimeRate = (HrOvertimeRates) events.getObject();
        hrOvertimeRate = overtimeratebeanLocal.getSelectedRequest(hrOvertimeRate.getId());
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "update";
    }
    
    public void saveOvertimeRate() {
        
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveOvertimeRate", dataset)) {
                boolean result = overtimeratebeanLocal.searchdup(hrOvertimeRate);
                if (update == 0 && result == false) {
                    try {
                        overtimeratebeanLocal.SaveOrUpdate(hrOvertimeRate);
                        JsfUtil.addSuccessMessage("Saved Successfuly.");
                        hrOvertimeRate = null;
                        overtimeDataModel = null;
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Something Error Occured on Data created");
                    }
                } else if (update == 1) {
                    overtimeratebeanLocal.SaveOrUpdate(hrOvertimeRate);
                    JsfUtil.addSuccessMessage("Updated Successfuly.");
                    hrOvertimeRate = null;
                    overtimeDataModel = null;
                } else {
                    JsfUtil.addFatalMessage("data already exist.");
                    
                }
                
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
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
    }
    
    public void resetOvertimeRate() {
        hrOvertimeRate = null;
        hrOvertimeRate = new HrOvertimeRates();
    }
    
    public ArrayList<SelectItem> ListofRateType() {
        ArrayList<SelectItem> list_Rates = new ArrayList<>();
        list_Rates.add(new SelectItem("OT Factor A", "OT Factor A"));
        list_Rates.add(new SelectItem("OT Factor B", "OT Factor B"));
        list_Rates.add(new SelectItem("OT Factor C", "OT Factor C"));
        list_Rates.add(new SelectItem("OT Factor D", "OT Factor D"));
        return list_Rates;
    }
    
    public ArrayList<HrOvertimeRates> findByRateType(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            String ratetype = event.getNewValue().toString();
            if (ratetype.equalsIgnoreCase("Find All")) {
                overtimeDataModel = new ListDataModel(new ArrayList(overtimeratebeanLocal.findAll(hrOvertimeRate)));;
            } else {
                hrOvertimeRate.setOtTypes(String.valueOf(event.getNewValue().toString()));
                overtimeDataModel = new ListDataModel(new ArrayList(overtimeratebeanLocal.findByRateType(hrOvertimeRate)));
            }
        }
        return null;
        
    }
    
    public void findall(ValueChangeEvent event) {
        
    }
    
    /**
     * Creates a new instance of overtimeRateController
     */
    public overtimeRateController() {
    }
//</editor-fold>
}
