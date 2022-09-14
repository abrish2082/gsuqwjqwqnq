/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.succession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.succession.CompetencyLocal;
import et.gov.eep.hrms.businessLogic.succession.CompetencysubtybebeanLocal;
import et.gov.eep.hrms.businessLogic.succession.HrsmskillcompetencybeanLocal;
import et.gov.eep.hrms.businessLogic.succession.SMKnowledgeCompetencyLocal;
import et.gov.eep.hrms.businessLogic.succession.competencyTypeLocal;
import et.gov.eep.hrms.businessLogic.succession.successionBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.entity.succession.HrSmCompetency;
import et.gov.eep.hrms.entity.succession.HrSmCompetencySubtypes;
import et.gov.eep.hrms.entity.succession.HrSmCompetencyTypes;
import et.gov.eep.hrms.entity.succession.HrSmKnowledgeCompetency;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmKmp;
import et.gov.eep.hrms.entity.succession.HrSmSkillCompetency;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffencePenality;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.mapper.succession.HrSmKmpFacade;
import et.gov.eep.hrms.mapper.organization.HrDeptJobsFacade;

@Named("CompetencyKmpcontroller")
@ViewScoped
public class CompetencyKmpcontroller implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">
    @Inject
    HrSmSkillCompetency hrSmSkillCompetency;
    @Inject
    HrSmKnowledgeCompetency hrSmKnowledgeCompetency;
    @Inject
    HrSmCompetencyTypes hrSmCompetencyTypes;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrDeptJobs hrDeptJobs;
    @Inject
    HrSmKmp hrSmKmp;
    @Inject
    SessionBean SessionBean;
    @Inject
    HrSmCompetency hrSmCompetency;
    @Inject
    HrSmCompetencySubtypes hrSmCompetencySubtypes;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrDisciplineOffencePenality OffencePenalty;

    @EJB
    HrsmskillcompetencybeanLocal hrsmskillcompetencybeanLocal;
    @EJB
    competencyTypeLocal competencyTypeLocal;
    @EJB
    CompetencysubtybebeanLocal competencysubtybebeanLocal;
    @EJB
    SMKnowledgeCompetencyLocal sMKnowledgeCompetencyLocal;
    @EJB
    CompetencyLocal competencyLocal;
    @EJB
    HrSmKmpFacade hrSmKmpFacade;
    @EJB
    successionBeanLocal successionBean;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
    HrDeptJobsFacade hrDeptJobsFacade;

    private Date date1;
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
    private boolean renderPnlApproved = false;
    private boolean checkBoxReqst = false;
    private boolean checkBoxApprove = false;
    private String saveorupdatebutton = "save";
    private String saveorupdatebutton2 = "save";
    private boolean value1 = false;
    private boolean value2 = false;
    private boolean value3 = false;
    private boolean value4 = false;
    private boolean value5 = false;
    private boolean value6 = false;

    private ArrayList<HrSmCompetencyTypes> selectedRowdt;
    HrSmSkillCompetency selectedrow = null;
    HrSmKnowledgeCompetency selectedrow1 = null;
    DataModel<HrSmCompetencyTypes> competencyDataModeltype = new ListDataModel<>();
    DataModel<HrSmSkillCompetency> competencyDataModelskill = new ListDataModel<>();
    DataModel<HrSmKnowledgeCompetency> competencyDataModelknowledge = new ListDataModel<>();
    HrSmCompetencySubtypes selectedRow = null;
    List<HrSmKmp> hrsmkmp12 = new ArrayList<>();
    List<HrSmKnowledgeCompetency> HrSmKnowledgeCompetencylist;
    private ArrayList<HrSmCompetencySubtypes> arraylistsubtype;
    private HrSmCompetencyTypes[] arraylisttype;
    DataModel<HrSmCompetencySubtypes> competencyDataModelsubtype = new ListDataModel<>();
    List<HrSmKmp> hrSmKmpsList = new ArrayList<>();
    List<HrSmSkillCompetency> hrSmSkillCompeteList = new ArrayList<>();
    List<HrSmCompetencySubtypes> compList1 = new ArrayList<>();
    private List<HrSmKnowledgeCompetency> compList12;
    List<HrSmSkillCompetency> compList13;
    List<HrSmCompetencyTypes> compListContain = new ArrayList<>();
    List<HrDisciplineOffencePenality> HrSmKnowledgeCompetencysss = new ArrayList<>();
    List<HrSmCompetencyTypes> compList;
// </editor-fold> 

    @PostConstruct
    public void init() {
        hrsmkmp12 = successionBean.findAll();
        setCompList12(sMKnowledgeCompetencyLocal.findall(hrSmKnowledgeCompetency));
        findbycompetttt();
        findbycompettt();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters setters">
    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public boolean isValue3() {
        return value3;
    }

    public void setValue3(boolean value3) {
        this.value3 = value3;
    }

    public boolean isValue4() {
        return value4;
    }

    public void setValue4(boolean value4) {
        this.value4 = value4;
    }

    public boolean isValue5() {
        return value5;
    }

    public void setValue5(boolean value5) {
        this.value5 = value5;
    }

    public boolean isValue6() {
        return value6;
    }

    public void setValue6(boolean value6) {
        this.value6 = value6;
    }

    public String getSaveorupdatebutton2() {
        return saveorupdatebutton2;
    }

    public void setSaveorupdatebutton2(String saveorupdatebutton2) {
        this.saveorupdatebutton2 = saveorupdatebutton2;
    }

    public String getSaveorupdatebutton() {
        return saveorupdatebutton;
    }

    public void setSaveorupdatebutton(String saveorupdatebutton) {
        this.saveorupdatebutton = saveorupdatebutton;
    }

    public boolean isValue1() {
        return value1;
    }

    public void setValue1(boolean value1) {
        this.value1 = value1;
    }

    public boolean isValue2() {
        return value2;
    }

    public void setValue2(boolean value2) {
        this.value2 = value2;
    }

    public HrSmCompetencyTypes[] getArraylisttype() {
        return arraylisttype;
    }

    public void setArraylisttype(HrSmCompetencyTypes[] arraylisttype) {
        this.arraylisttype = arraylisttype;
    }

    public HrSmKnowledgeCompetency getSelectedrow1() {
        return selectedrow1;
    }

    public void setSelectedrow1(HrSmKnowledgeCompetency selectedrow1) {
        this.selectedrow1 = selectedrow1;
    }

    public DataModel<HrSmKnowledgeCompetency> getCompetencyDataModelknowledge() {
        if (competencyDataModelknowledge == null) {
            competencyDataModelknowledge = new ListDataModel<>();
        }
        return competencyDataModelknowledge;
    }

    public void setCompetencyDataModelknowledge(DataModel<HrSmKnowledgeCompetency> competencyDataModelknowledge) {
        this.competencyDataModelknowledge = competencyDataModelknowledge;
    }

    private void recerateSearchModel() {
        competencyDataModelknowledge = null;
        competencyDataModelknowledge = new ListDataModel(new ArrayList<>(getCompList12()));

    }

    private void recerateSearchModel2() {
        competencyDataModelskill = null;
        competencyDataModelskill = new ListDataModel(new ArrayList<>(compList13));

    }

    public DataModel<HrSmSkillCompetency> getCompetencyDataModelskill() {
        return competencyDataModelskill;
    }

    public HrSmSkillCompetency getSelectedrow() {
        return selectedrow;
    }

    public void setSelectedrow(HrSmSkillCompetency selectedrow) {
        this.selectedrow = selectedrow;
    }

    public void setCompetencyDataModelskill(DataModel<HrSmSkillCompetency> competencyDataModelskill) {
        this.competencyDataModelskill = competencyDataModelskill;
    }

    public ArrayList<HrSmCompetencySubtypes> getArraylistsubtype() {
        return arraylistsubtype;
    }

    public void setArraylistsubtype(ArrayList<HrSmCompetencySubtypes> Arraylistsubtype) {
        this.arraylistsubtype = arraylistsubtype;
    }

    public DataModel<HrSmCompetencySubtypes> getCompetencyDataModell() {
        return competencyDataModelsubtype;
    }

    public HrSmSkillCompetency getHrSmSkillCompetency() {
        if (hrSmSkillCompetency == null) {
            hrSmSkillCompetency = new HrSmSkillCompetency();
        }
        return hrSmSkillCompetency;
    }

    public void setHrSmSkillCompetency(HrSmSkillCompetency hrSmSkillCompetency) {
        this.hrSmSkillCompetency = hrSmSkillCompetency;
    }

    public HrsmskillcompetencybeanLocal getHrsmskillcompetencybeanLocal() {
        return hrsmskillcompetencybeanLocal;
    }

    public void setHrsmskillcompetencybeanLocal(HrsmskillcompetencybeanLocal hrsmskillcompetencybeanLocal) {
        this.hrsmskillcompetencybeanLocal = hrsmskillcompetencybeanLocal;
    }

    public void setCompetencyDataModell(DataModel<HrSmCompetencySubtypes> competencyDataModell) {
        this.competencyDataModelsubtype = competencyDataModell;
    }
    private String saveOrUpdateButton = "Save";
    int update = 0;

    public ArrayList<HrSmCompetencyTypes> getSelectedRowdt() {
        return selectedRowdt;
    }

    public void setSelectedRowdt(ArrayList<HrSmCompetencyTypes> selectedRowdt) {
        this.selectedRowdt = selectedRowdt;
    }

    public HrSmKnowledgeCompetency getHrSmKnowledgeCompetency() {
        if (hrSmKnowledgeCompetency == null) {
            hrSmKnowledgeCompetency = new HrSmKnowledgeCompetency();
        }
        return hrSmKnowledgeCompetency;
    }

    public void setHrSmKnowledgeCompetency(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        this.hrSmKnowledgeCompetency = hrSmKnowledgeCompetency;
    }

    public HrSmKmpFacade getHrSmKmpFacade() {
        return hrSmKmpFacade;
    }

    public void setHrSmKmpFacade(HrSmKmpFacade hrSmKmpFacade) {
        this.hrSmKmpFacade = hrSmKmpFacade;
    }

    public successionBeanLocal getSuccessionBean() {
        return successionBean;
    }

    public void setSuccessionBean(successionBeanLocal successionBean) {
        this.successionBean = successionBean;
    }

    public HrDepartmentsBeanLocal getHrDepartmentsBeanLocal() {
        return hrDepartmentsBeanLocal;
    }

    public void setHrDepartmentsBeanLocal(HrDepartmentsBeanLocal hrDepartmentsBeanLocal) {
        this.hrDepartmentsBeanLocal = hrDepartmentsBeanLocal;
    }

    public HrDeptJobsFacade getHrDeptJobsFacade() {
        return hrDeptJobsFacade;
    }

    public void setHrDeptJobsFacade(HrDeptJobsFacade hrDeptJobsFacade) {
        this.hrDeptJobsFacade = hrDeptJobsFacade;
    }

    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public HrDeptJobs getHrDeptJobs() {
        return hrDeptJobs;
    }

    public void setHrDeptJobs(HrDeptJobs hrDeptJobs) {
        this.hrDeptJobs = hrDeptJobs;
    }

    public HrSmKmp getHrSmKmp() {
        if (hrSmKmp == null) {
            hrSmKmp = new HrSmKmp();
        }
        return hrSmKmp;
    }

    public void setHrSmKmp(HrSmKmp hrSmKmp) {
        this.hrSmKmp = hrSmKmp;
    }

    public SMKnowledgeCompetencyLocal getsMKnowledgeCompetencyLocal() {
        return sMKnowledgeCompetencyLocal;
    }

    public void setsMKnowledgeCompetencyLocal(SMKnowledgeCompetencyLocal sMKnowledgeCompetencyLocal) {
        this.sMKnowledgeCompetencyLocal = sMKnowledgeCompetencyLocal;
    }

    public HrSmCompetencyTypes getHrSmCompetencyTypes() {
        if (hrSmCompetencyTypes == null) {
            hrSmCompetencyTypes = new HrSmCompetencyTypes();
        }
        return hrSmCompetencyTypes;
    }

    public void setHrSmCompetencyTypes(HrSmCompetencyTypes hrSmCompetencyTypes) {
        this.hrSmCompetencyTypes = hrSmCompetencyTypes;
    }

    public competencyTypeLocal getCompetencyTypeLocal() {
        return competencyTypeLocal;
    }

    public void setCompetencyTypeLocal(competencyTypeLocal competencyTypeLocal) {
        this.competencyTypeLocal = competencyTypeLocal;
    }

    public HrSmCompetencySubtypes getHrSmCompetencySubtypes() {
        if (hrSmCompetencySubtypes == null) {
            hrSmCompetencySubtypes = new HrSmCompetencySubtypes();
        }
        return hrSmCompetencySubtypes;
    }

    public void setHrSmCompetencySubtypes(HrSmCompetencySubtypes hrSmCompetencySubtypes) {
        this.hrSmCompetencySubtypes = hrSmCompetencySubtypes;
    }

    public CompetencysubtybebeanLocal getCompetencysubtybebeanLocal() {
        return competencysubtybebeanLocal;
    }

    public void setCompetencysubtybebeanLocal(CompetencysubtybebeanLocal competencysubtybebeanLocal) {
        this.competencysubtybebeanLocal = competencysubtybebeanLocal;
    }

    public HrSmCompetency getHrSmCompetency() {
        return hrSmCompetency;
    }

    public void setHrSmCompetency(HrSmCompetency hrSmCompetency) {
        this.hrSmCompetency = hrSmCompetency;
    }

    public CompetencyLocal getCompetencyLocal() {
        return competencyLocal;
    }

    public void setCompetencyLocal(CompetencyLocal competencyLocal) {
        this.competencyLocal = competencyLocal;
    }

    public DataModel<HrSmCompetencyTypes> getCompetencyDataModel() {
        return competencyDataModeltype;
    }

    public void setCompetencyDataModel(DataModel<HrSmCompetencyTypes> competencyDataModel) {
        this.competencyDataModeltype = competencyDataModel;
    }

    public HrSmCompetencySubtypes getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrSmCompetencySubtypes selectedRow) {
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

    public List<HrSmKmp> getHrsmkmp12() {
        return hrsmkmp12;
    }

    public void setHrsmkmp12(List<HrSmKmp> hrsmkmp12) {
        this.hrsmkmp12 = hrsmkmp12;
    }

    public List<HrSmKmp> getHrSmKmpsList() {
        return hrSmKmpsList;
    }

    public void setHrSmKmpsList(List<HrSmKmp> hrSmKmpsList) {
        this.hrSmKmpsList = hrSmKmpsList;
    }

    public List<HrSmKnowledgeCompetency> getCompList12() {
        if (compList12 == null) {
            compList12 = new ArrayList<>();
        }
        return compList12;
    }

    public void setCompList12(List<HrSmKnowledgeCompetency> compList12) {
        this.compList12 = compList12;
    }

    public List<HrSmSkillCompetency> getCompList13() {
        if (compList13 == null) {
            compList13 = new ArrayList<>();
        }
        return compList13;
    }

    public void setCompList13(List<HrSmSkillCompetency> compList13) {
        this.compList13 = compList13;
    }

    public List<HrSmCompetencySubtypes> getCompList1() {
        return compList1;
    }

    public void setCompList1(List<HrSmCompetencySubtypes> compList1) {
        this.compList1 = compList1;
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

    public HrDisciplineOffencePenality getOffencePenalty() {
        if (OffencePenalty == null) {
            OffencePenalty = new HrDisciplineOffencePenality();
        }
        return OffencePenalty;
    }

    public void setOffencePenalty(HrDisciplineOffencePenality OffencePenalty) {
        this.OffencePenalty = OffencePenalty;
    }

    public List<HrDisciplineOffencePenality> getHrSmKnowledgeCompetencysss() {
        return HrSmKnowledgeCompetencysss;
    }

    public void setHrSmKnowledgeCompetencysss(List<HrDisciplineOffencePenality> HrSmKnowledgeCompetencysss) {
        this.HrSmKnowledgeCompetencysss = HrSmKnowledgeCompetencysss;
    }

    public List<HrSmCompetencyTypes> getCompList() {
        if (compList == null) {
            compList = new ArrayList<>();
        }
        return compList;
    }

    public void setCompList(List<HrSmCompetencyTypes> compList) {
        this.compList = compList;
    }

    public List<HrSmKnowledgeCompetency> getHrSmKnowledgeCompetencylist() {
        if (HrSmKnowledgeCompetencylist == null) {
            HrSmKnowledgeCompetencylist = new ArrayList<>();
        }
        return HrSmKnowledgeCompetencylist;
    }

    public void setHrSmKnowledgeCompetencylist(List<HrSmKnowledgeCompetency> HrSmKnowledgeCompetencylist) {
        this.HrSmKnowledgeCompetencylist = HrSmKnowledgeCompetencylist;
    }

    public List<HrSmSkillCompetency> getHrSmSkillCompeteList() {
        return hrSmSkillCompeteList;
    }

    public void setHrSmSkillCompeteList(List<HrSmSkillCompetency> hrSmSkillCompeteList) {
        this.hrSmSkillCompeteList = hrSmSkillCompeteList;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void populateKnowledgeCompitency(SelectEvent events) {
        hrSmKnowledgeCompetency = (HrSmKnowledgeCompetency) events.getObject();
        hrSmKmp = hrSmKnowledgeCompetency.getKmpId();
        int size = compList.size();
        for (int i = 0; i < size; i++) {
            List<HrSmKnowledgeCompetency> kmpCompList = sMKnowledgeCompetencyLocal.findbyKmpId(hrSmKnowledgeCompetency);
            for (HrSmKnowledgeCompetency hrSmKnowledgeCompetency : kmpCompList) {
                HrSmKnowledgeCompetency hrSmKnowledge = new HrSmKnowledgeCompetency();
                hrSmKnowledge.setCompetencyTypeId(hrSmKnowledgeCompetency.getCompetencyTypeId());
                if (hrSmKnowledge.getCompetencyTypeId().equals(compList.get(i))) {
                    compList.get(i).setApplicapble(hrSmKnowledgeCompetency.getNotApplicable());
                    compList.get(i).setNaturalLevel(hrSmKnowledgeCompetency.getNatureLevelAttend());
                    compList.get(i).setInMonth(hrSmKnowledgeCompetency.getInmonth());
                }
            }
        }
        update = 1;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        saveorupdatebutton = "Update ";
        setIcone("ui-icon-search");
    }

    public void populateSkillCompitency(SelectEvent events) {
        hrSmSkillCompetency = (HrSmSkillCompetency) events.getObject();
        hrSmKmp = hrSmSkillCompetency.getKmpId();
        int size = compList1.size();
        for (int i = 0; i < size; i++) {
            List<HrSmSkillCompetency> kmpCompList = hrsmskillcompetencybeanLocal.findbyKmpId(hrSmSkillCompetency);
            for (HrSmSkillCompetency hrSmSkillCompetency : kmpCompList) {
                HrSmSkillCompetency hrSmKnowledge = new HrSmSkillCompetency();
                hrSmKnowledge.setCompetencySubtypeId(hrSmSkillCompetency.getCompetencySubtypeId());
                if (hrSmSkillCompetency.getCompetencySubtypeId().equals(compList1.get(i))) {
                    compList1.get(i).setApllicable(hrSmSkillCompetency.getNotApplicable());
                    compList1.get(i).setImportanttohave(hrSmSkillCompetency.getImportantToHave());
                    compList1.get(i).setMusthave(hrSmSkillCompetency.getMustHave());
                    compList1.get(i).setNicetohave(hrSmSkillCompetency.getNiceToHave());
                }
            }
        }
        update = 1;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        saveorupdatebutton2 = "Update ";
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
            saveorupdatebutton = "Save ";
            saveorupdatebutton2 = "Save ";
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
        hrSmKnowledgeCompetency = new HrSmKnowledgeCompetency();
        hrSmSkillCompetency = new HrSmSkillCompetency();
        hrSmKmp = new HrSmKmp();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        update = 0;
        saveorupdatebutton = "Save ";
        saveorupdatebutton2 = "Save ";
    }

    public void searchbykmp1() {
        try {
            setCompList12(sMKnowledgeCompetencyLocal.findall(hrSmKnowledgeCompetency));
            competencyDataModelknowledge = new ListDataModel(new ArrayList(getCompList12()));
            compList13 = hrsmskillcompetencybeanLocal.findall(hrSmSkillCompetency);
            competencyDataModelskill = new ListDataModel(new ArrayList(compList13));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void searchCompetencyKmpProfile() {
        setCompList12(sMKnowledgeCompetencyLocal.findJobTitle(hrJobTypes));
        compList13 = hrsmskillcompetencybeanLocal.findJobTitle(hrJobTypes);
        recerateSearchModel();
        recerateSearchModel2();

    }
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Main method">
    boolean result;

    public void saveKnowledgeCompetency() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveKnowledgeCompetency", dataset)) {
                try {
                    if (update == 0) {
                        int size = compList.size();
                        for (int i = 0; i < size; i++) {
                            if (compList.get(i).isApplicapble() == true) {
                                hrSmKnowledgeCompetency.setCompetencyTypeId(compList.get(i));
                                hrSmKnowledgeCompetency.setKmpId(hrSmKmp);
                                hrSmKnowledgeCompetency.setInmonth(compList.get(i).getInMonth());
                                hrSmKnowledgeCompetency.setNatureLevelAttend(compList.get(i).getNaturalLevel());
                                hrSmKnowledgeCompetency.setNotApplicable(compList.get(i).isApplicapble());
                                result = sMKnowledgeCompetencyLocal.searchduplicate(hrSmKnowledgeCompetency);
                                if (result == false) {
                                    sMKnowledgeCompetencyLocal.SaveOrUpdate(hrSmKnowledgeCompetency);
                                    hrSmKnowledgeCompetency = new HrSmKnowledgeCompetency();
                                }
                            }
                        }
                        if (result == false) {
                            JsfUtil.addSuccessMessage("Saved Successfully.");
                        } else if (result == true) {
                            JsfUtil.addFatalMessage("Data already exist");
                        } else {
                            JsfUtil.addFatalMessage("unable to save data");
                        }
                    } else {
                        int size = compList.size();
                        for (int i = 0; i < size; i++) {
                            List<HrSmKnowledgeCompetency> kmpCompList = sMKnowledgeCompetencyLocal.findbyKmpId(hrSmKnowledgeCompetency);
                            for (HrSmKnowledgeCompetency hrSmKnowledgeCompetency : kmpCompList) {
                                HrSmKnowledgeCompetency hrSmKnowledge = new HrSmKnowledgeCompetency();
                                hrSmKnowledge.setCompetencyTypeId(hrSmKnowledgeCompetency.getCompetencyTypeId());
                                if (compList.get(i).isApplicapble() == true) {
                                    hrSmKnowledgeCompetency.setCompetencyTypeId(compList.get(i));
                                    hrSmKnowledgeCompetency.setKmpId(hrSmKmp);
                                    hrSmKnowledgeCompetency.setInmonth(compList.get(i).getInMonth());
                                    hrSmKnowledgeCompetency.setNatureLevelAttend(compList.get(i).getNaturalLevel());
                                    hrSmKnowledgeCompetency.setNotApplicable(compList.get(i).isApplicapble());
                                    sMKnowledgeCompetencyLocal.SaveOrUpdate(hrSmKnowledgeCompetency);
                                    hrSmKnowledgeCompetency = new HrSmKnowledgeCompetency();
                                }

                            }
                        }
                        JsfUtil.addSuccessMessage("Update Successfully.");
                        hrSmKnowledgeCompetency = null;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JsfUtil.addErrorMessage("Error occured while save update");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveKnowledgeCompetency");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        resetKnowledgeCompetency();
    }
    boolean result2;

    public void saveSkillCompetency() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveSkillCompetency", dataset)) {
                try {
                    if (update == 0) {
                        int size = compList1.size();
                        for (int i = 0; i < size; i++) {
                            if (compList1.get(i).isApllicable() == true || compList1.get(i).isImportanttohave() == true || compList1.get(i).isMusthave() == true || compList1.get(i).isNicetohave() == true) {
                                hrSmSkillCompetency.setCompetencySubtypeId(compList1.get(i));
                                hrSmSkillCompetency.setKmpId(hrSmKmp);
                                hrSmSkillCompetency.setImportantToHave(compList1.get(i).isImportanttohave());
                                hrSmSkillCompetency.setMustHave(compList1.get(i).isMusthave());
                                hrSmSkillCompetency.setNiceToHave(compList1.get(i).isNicetohave());
                                hrSmSkillCompetency.setNotApplicable(compList1.get(i).isApllicable());
                                result2 = hrsmskillcompetencybeanLocal.searchduplicate(hrSmSkillCompetency);
                                if (result2 == false) {
                                    hrsmskillcompetencybeanLocal.SaveOrUpdate(hrSmSkillCompetency);
                                    hrSmSkillCompetency = new HrSmSkillCompetency();
                                }
                            }
                        }
                        if (result2 == false) {
                            JsfUtil.addSuccessMessage("saved Successfully.");
                        } else {
                            JsfUtil.addFatalMessage("data already exist");
                        }

                    } else {
                        int size = compList1.size();
                        for (int i = 0; i < size; i++) {
                            List<HrSmSkillCompetency> kmpCompList = hrsmskillcompetencybeanLocal.findbyKmpId(hrSmSkillCompetency);
                            for (HrSmSkillCompetency hrSmSkillCompetency : kmpCompList) {
                                HrSmSkillCompetency hrSmKnowledge = new HrSmSkillCompetency();
                                hrSmKnowledge.setCompetencySubtypeId(hrSmSkillCompetency.getCompetencySubtypeId());
                                if (compList1.get(i).isApllicable() == true || compList1.get(i).isImportanttohave() == true || compList1.get(i).isMusthave() == true || compList1.get(i).isNicetohave() == true) {
                                    hrSmSkillCompetency.setCompetencySubtypeId(compList1.get(i));
                                    hrSmSkillCompetency.setKmpId(hrSmKmp);
                                    hrSmSkillCompetency.setImportantToHave(compList1.get(i).isImportanttohave());
                                    hrSmSkillCompetency.setMustHave(compList1.get(i).isMusthave());
                                    hrSmSkillCompetency.setNiceToHave(compList1.get(i).isNicetohave());
                                    hrSmSkillCompetency.setNotApplicable(compList1.get(i).isApllicable());
                                    hrsmskillcompetencybeanLocal.SaveOrUpdate(hrSmSkillCompetency);
                                    hrSmSkillCompetency = new HrSmSkillCompetency();
                                }
                            }
                        }
                        JsfUtil.addSuccessMessage("Update Successfully.");
                        hrSmSkillCompetency = null;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JsfUtil.addErrorMessage("Error occured while save update");
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveSkillCompetency");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        resetSkillCompetency();
    }

    public void resetKnowledgeCompetency() {
        hrSmKmp = null;
        hrSmCompetencyTypes = null;
        hrSmKnowledgeCompetency = null;
        hrSmKnowledgeCompetency = new HrSmKnowledgeCompetency();
    }

    public void resetSkillCompetency() {
        hrSmKmp = null;
        hrSmCompetencySubtypes = null;
        hrSmSkillCompetency = null;
        hrSmSkillCompetency = new HrSmSkillCompetency();
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Row select and value change listner ">
    public void findbycompett() {
        String knowledge = "knowledge";
        compList = sMKnowledgeCompetencyLocal.findbycompettype(knowledge);
        HrDisciplineOffencePenality comp = new HrDisciplineOffencePenality();
    }

    public void findbycompettt() {
        String knowledge = "knowledge";
        compList = competencyTypeLocal.findByKnowledge(knowledge);

    }

    public void rowSelect(SelectEvent event) {
        hrSmCompetencyTypes = (HrSmCompetencyTypes) event.getObject();
        hrSmKnowledgeCompetency.setCompetencyTypeId(hrSmCompetencyTypes);
    }

    public void rowSelect22(SelectEvent event) {
        hrSmSkillCompetency.setNotApplicable(true);

    }

    public void rowUnSelect(UnselectEvent event) {
        hrSmKnowledgeCompetency = new HrSmKnowledgeCompetency();
        hrSmCompetencyTypes = (HrSmCompetencyTypes) event.getObject();
        hrSmKnowledgeCompetency.setCompetencyTypeId(hrSmCompetencyTypes);
        HrSmKnowledgeCompetencylist.add(hrSmKnowledgeCompetency);
    }

    public void rowUnSelect22(UnselectEvent event) {
        hrSmSkillCompetency.setNotApplicable(false);
    }

    public void multiSelect(AjaxBehaviorEvent event) {
        for (int i = 0; i < compList.size(); i++) {
            hrSmKnowledgeCompetency = new HrSmKnowledgeCompetency();
            HrSmCompetencyTypes hrSmCompetencyTypes = selectedRowdt.get(i);
            hrSmKnowledgeCompetency.setCompetencyTypeId(hrSmCompetencyTypes);
            HrSmKnowledgeCompetencylist.add(hrSmKnowledgeCompetency);
        }
    }

    public void rowselect1(SelectEvent event) {
        hrSmCompetencySubtypes = (HrSmCompetencySubtypes) event.getObject();
        hrSmSkillCompetency.setCompetencySubtypeId(hrSmCompetencySubtypes);
    }

    public void onSelect1(SelectEvent event) {
        hrSmSkillCompetency = new HrSmSkillCompetency();
        hrSmSkillCompetency.setMustHave(value5);
        hrSmSkillCompetency.setNiceToHave(value3);
        hrSmSkillCompetency.setImportantToHave(value4);
        hrSmSkillCompetency.setNotApplicable(value2);
    }

    public void rowUnSelect1(UnselectEvent event) {
        hrSmSkillCompetency = new HrSmSkillCompetency();
        hrSmCompetencySubtypes = (HrSmCompetencySubtypes) event.getObject();
        hrSmSkillCompetency.setCompetencySubtypeId(hrSmCompetencySubtypes);
        hrSmSkillCompeteList.add(hrSmSkillCompetency);
    }

    public void multiSelect1(AjaxBehaviorEvent event) {
        for (int i = 0; i < compList1.size(); i++) {
            hrSmSkillCompetency = new HrSmSkillCompetency();
            HrSmCompetencySubtypes hrSmCompetencySubtypes = arraylistsubtype.get(i);
            hrSmSkillCompetency.setCompetencySubtypeId(hrSmCompetencySubtypes);
            hrSmSkillCompeteList.add(hrSmSkillCompetency);
        }
    }

    public void vcl_filiterByCategory(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrSmSkillCompetency = (HrSmSkillCompetency) event.getNewValue();
        }
    }

    public void add() {
        compList13 = hrsmskillcompetencybeanLocal.add();
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell value Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage("Message", msg);
        }
    }

    public void handleKMP(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrSmKmp = (HrSmKmp) event.getNewValue();
            hrSmKmp.setId(hrSmKmp.getId());
        }
    }

    public void findbycompetttt() {
        String skill = "skill";
        compList1 = competencysubtybebeanLocal.kmpskill(skill);
    }
    // </editor-fold>

}
