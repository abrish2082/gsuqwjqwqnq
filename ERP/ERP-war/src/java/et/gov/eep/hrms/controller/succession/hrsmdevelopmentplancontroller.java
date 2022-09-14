/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.succession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.succession.hrsmdevelopmentplanLocal;
import et.gov.eep.hrms.businessLogic.succession.hrsmplandetailsLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlanDetails;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlans;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import et.gov.eep.hrms.entity.training.HrTdCourses;

/**
 *
 * @author meles
 */
@Named("hrsmdevelopmentplancontroller")
@ViewScoped
public class hrsmdevelopmentplancontroller implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussines logic & variables">
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrSmSuccessionPlans hrSmSuccessionPlans;
    @Inject
    HrSmSuccessorEvaluation hrSmSuccessorEvaluation;
    @Inject
    HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails;
    @Inject
    HrTdCourses hrTdCourses;
    @Inject
    SessionBean SessionBean;
    @EJB
    hrsmdevelopmentplanLocal hrsmdevelopmentplanLocal;
    @EJB
    hrsmplandetailsLocal hrsmplandetailsLocal;

    List<HrSmSuccessionPlans> hrsmsuccessusplanslist = new ArrayList();
    List<HrSmSuccessionPlanDetails> hrsmsuccessusdetailslist = new ArrayList();
    DataModel<HrSmSuccessionPlanDetails> hrsmplandetailmodel = new ListDataModel<>();
    List<HrSmSuccessionPlanDetails> plandetaillist = new ArrayList<>();
    Set<String> checkEmployee = new HashSet<>();
    List<SelectItem> filterByStatus = new ArrayList<>();
    HrSmSuccessionPlanDetails selectedRow = null;

    private boolean renderPnlCreateGatePass = false;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String tab = "disabled";
    private String choosePermanet = "true";
    private String chooseDailyLabour = "false";
    private boolean renderPnlApproved = false;
    private boolean checkBoxReqst = false;
    private boolean checkBoxApprove = false;
    private String saveOrUpdateButton = "Save";
    int update = 0;
    int status = 1;
// </editor-fold> 

    @PostConstruct
    public void init() {
        hrsmsuccessusdetailslist = hrsmplandetailsLocal.findall(hrSmSuccessionPlanDetails);
    }

    // <editor-fold defaultstate="collapsed" desc="Getters setters">
    public List<HrSmSuccessionPlanDetails> getPlandetaillist() {
        return plandetaillist;
    }

    public void setPlandetaillist(List<HrSmSuccessionPlanDetails> plandetaillist) {
        this.plandetaillist = plandetaillist;
    }

    public HrTdCourses getHrTdCourses() {
        if (hrTdCourses == null) {
            hrTdCourses = new HrTdCourses();
        }
        return hrTdCourses;
    }

    public void setHrTdCourses(HrTdCourses hrTdCourses) {
        this.hrTdCourses = hrTdCourses;
    }

    public DataModel<HrSmSuccessionPlanDetails> getHrsmplandetailmodel() {
        if (hrsmplandetailmodel == null) {
            hrsmplandetailmodel = new ListDataModel<>();
        }
        return hrsmplandetailmodel;
    }

    public void setHrsmplandetailmodel(DataModel<HrSmSuccessionPlanDetails> hrsmplandetailmodel) {
        this.hrsmplandetailmodel = hrsmplandetailmodel;
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

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getChoosePermanet() {
        return choosePermanet;
    }

    public void setChoosePermanet(String choosePermanet) {
        this.choosePermanet = choosePermanet;
    }

    public String getChooseDailyLabour() {
        return chooseDailyLabour;
    }

    public void setChooseDailyLabour(String chooseDailyLabour) {
        this.chooseDailyLabour = chooseDailyLabour;
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

    public HrSmSuccessionPlanDetails getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrSmSuccessionPlanDetails selectedRow) {
        this.selectedRow = selectedRow;
    }

    public List<HrSmSuccessionPlanDetails> getHrsmsuccessusdetailslist() {
        return hrsmsuccessusdetailslist;
    }

    public void setHrsmsuccessusdetailslist(List<HrSmSuccessionPlanDetails> hrsmsuccessusdetailslist) {
        this.hrsmsuccessusdetailslist = hrsmsuccessusdetailslist;
    }

    public hrsmplandetailsLocal getHrsmplandetailsLocal() {
        return hrsmplandetailsLocal;
    }

    public void setHrsmplandetailsLocal(hrsmplandetailsLocal hrsmplandetailsLocal) {
        this.hrsmplandetailsLocal = hrsmplandetailsLocal;
    }

    public HrSmSuccessionPlanDetails getHrSmSuccessionPlanDetails() {
        if (hrSmSuccessionPlanDetails == null) {
            hrSmSuccessionPlanDetails = new HrSmSuccessionPlanDetails();
        }
        return hrSmSuccessionPlanDetails;
    }

    public void setHrSmSuccessionPlanDetails(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        this.hrSmSuccessionPlanDetails = hrSmSuccessionPlanDetails;
    }

    public HrSmSuccessorEvaluation getHrSmSuccessorEvaluation() {
        if (hrSmSuccessorEvaluation == null) {
            hrSmSuccessorEvaluation = new HrSmSuccessorEvaluation();
        }
        return hrSmSuccessorEvaluation;
    }

    public void setHrSmSuccessorEvaluation(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        this.hrSmSuccessorEvaluation = hrSmSuccessorEvaluation;
    }

    public HrSmSuccessionPlans getHrSmSuccessionPlans() {
        if (hrSmSuccessionPlans == null) {
            hrSmSuccessionPlans = new HrSmSuccessionPlans();
        }
        return hrSmSuccessionPlans;
    }

    public void setHrSmSuccessionPlans(HrSmSuccessionPlans hrSmSuccessionPlans) {
        this.hrSmSuccessionPlans = hrSmSuccessionPlans;
    }

    public hrsmdevelopmentplanLocal getHrsmdevelopmentplanLocal() {
        return hrsmdevelopmentplanLocal;
    }

    public void setHrsmdevelopmentplanLocal(hrsmdevelopmentplanLocal hrsmdevelopmentplanLocal) {
        this.hrsmdevelopmentplanLocal = hrsmdevelopmentplanLocal;
    }

    public List<HrSmSuccessionPlans> getHrsmsuccessusplanslist() {
        return hrsmsuccessusplanslist;
    }

    public void setHrsmsuccessusplanslist(List<HrSmSuccessionPlans> hrsmsuccessusplanslist) {
        this.hrsmsuccessusplanslist = hrsmsuccessusplanslist;
    }

    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public Set<String> getCheckEmployee() {
        return checkEmployee;
    }

    public void setCheckEmployee(Set<String> checkEmployee) {
        this.checkEmployee = checkEmployee;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void search() {
        hrsmplandetailsLocal.findallactivesatus(hrSmSuccessionPlanDetails);
        hrsmplandetailmodel = new ListDataModel(hrsmsuccessusdetailslist);
    }

    public void populate(SelectEvent events) {
        hrSmSuccessionPlanDetails = (HrSmSuccessionPlanDetails) events.getObject();
        hrSmSuccessionPlans = hrSmSuccessionPlanDetails.getSuccessionPlanId();
        hrTdCourses = hrSmSuccessionPlanDetails.getTrainingId();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "Modify";
        setIcone("ui-icon-search");
    }

    public void createNewAdditionalAmount() {
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            btnNewRender = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            btnNewRender = false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        resetDevelopmentPlan();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
    }

    public SelectItem[] getPopulateFilterByStatus() {
        SelectItem[] items = new SelectItem[6];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem("Inactive", "Load Inactive List");
        items[2] = new SelectItem("Active", "Load Active List");
        items[3] = new SelectItem("Cancelled", "Load Cancelled List");
        items[4] = new SelectItem("Complete", "Load Complete List");
        items[5] = new SelectItem("Failed", "Load Failed List");
        return items;
    }

    public ArrayList<HrSmSuccessionPlanDetails> findByStatus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String status = event.getNewValue().toString();
            System.out.println("status" + status);
            hrSmSuccessionPlanDetails.setStatus(String.valueOf(status));
            System.out.println("hrSmSuccessionPlanDetails" + hrSmSuccessionPlanDetails.getStatus());
            hrsmplandetailmodel = new ListDataModel(new ArrayList(hrsmplandetailsLocal.findByStatus(hrSmSuccessionPlanDetails, status)));
            System.out.println("size" + hrsmplandetailmodel.getRowCount());
        }
        return null;
    }

    public void searchemp() {
        hrsmsuccessusdetailslist = hrsmdevelopmentplanLocal.findemp(hrEmployees);
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Main Method">
    public void saveDevelopmentPlan() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveDevelopmentPlan", dataset)) {
                if ((!(plandetaillist.size() > 0))) {
                    JsfUtil.addErrorMessage("Data table shoud be filled");
                } else {
                    try {
                        for (int i = 0; i < plandetaillist.size(); i++) {
                            hrsmplandetailsLocal.saveUpdate(plandetaillist.get(i));
                        }
                        if (update == 0) {
                            JsfUtil.addSuccessMessage("Saved Successfully");
                        } else {
                            JsfUtil.addSuccessMessage("Updated Successfully.");
                        }
                        resetDevelopmentPlan();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addErrorMessage("Error occured while save update");
                    }

                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveDevelopmentPlan");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetDevelopmentPlan() {
        hrSmSuccessionPlanDetails = null;
        hrSmSuccessionPlanDetails = new HrSmSuccessionPlanDetails();
        hrSmSuccessionPlans = null;
        plandetaillist = null;
    }

    public void requestChanged(SelectEvent event) {
        hrSmSuccessionPlanDetails = (HrSmSuccessionPlanDetails) event.getObject();
        hrSmSuccessionPlanDetails.setId(hrSmSuccessionPlanDetails.getId());

    }
    String slected = "Select One";

    public void handleEmployee(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            slected = event.getNewValue().toString();
            if (slected.equalsIgnoreCase("Permanent/contract")) {
                choosePermanet = "true";
                chooseDailyLabour = "false";
            } else {
                choosePermanet = "false";
                chooseDailyLabour = "true";
            }
        }
    }

    public String addAction() {
        if (hrSmSuccessionPlanDetails == null || hrSmSuccessionPlans == null || hrTdCourses == null) {
            JsfUtil.addFatalMessage("please search data first to add datatable ");
        } else if (hrSmSuccessionPlanDetails != null) {
            plandetaillist.add(hrSmSuccessionPlanDetails);
            hrSmSuccessionPlanDetails = new HrSmSuccessionPlanDetails();
            hrTdCourses = hrSmSuccessionPlanDetails.getTrainingId();
        }
        return null;
    }

    public void display(ValueChangeEvent event) {
        hrSmSuccessionPlanDetails = (HrSmSuccessionPlanDetails) event.getNewValue();
        hrSmSuccessionPlans = hrSmSuccessionPlanDetails.getSuccessionPlanId();
        hrTdCourses = hrSmSuccessionPlanDetails.getTrainingId();
//         }
    }
    // </editor-fold>
}
