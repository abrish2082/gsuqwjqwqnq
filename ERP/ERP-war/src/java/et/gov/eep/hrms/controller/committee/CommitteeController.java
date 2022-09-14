/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.committee;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.committee.HrCommitteeBeanLocal;
import et.gov.eep.hrms.businessLogic.committee.HrCommitteeMembersBeaLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuCommitteeTypesBeanLocal;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.committee.HrExComitteMembers;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.lookup.HrLuCommitteeTypes;
import et.gov.eep.hrms.entity.lookup.HrLuTitles;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBElement;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named(value = "committeeControllers")
@ViewScoped
public class CommitteeController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="ENtity enjections">
    @Inject
            ColumnNameResolver columnNameResolver;
    @Inject
            SessionBean sessionBean;
    @Inject
            HrLuCommitteeTypes hrLuCommitteeTypes;
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrCommittees hrCommittees;
    @Inject
            HrDepartments departments;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrCommitteeMembers hrCommitteeMembers;
    @Inject
            HrExComitteMembers hrExComitteMembers;
    @Inject
            HrLuTitles hrLuTitles;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB declarations">
    @EJB
    private HREmployeesBeanLocal hrEmployeeBean;
    @EJB
            HrCommitteeBeanLocal hrCommitteeBeanLocal;
    @EJB
            HrCommitteeMembersBeaLocal hrCommitteeMembersBeaLocal;
    @EJB
            HrLuCommitteeTypesBeanLocal hrLuCommitteeTypesBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="list and databomedel declaration">
    DataModel<HrExComitteMembers> hrExCommitteeMemberModel;
    DataModel<HrCommitteeMembers> hrCommitteeMemberModel;
    Set<String> declaration = new HashSet<>();
    Set<String> declarationEmpFullName = new HashSet<>();
    private HrCommitteeMembers selectComMembers;
    DataModel<HrCommittees> committeeDatamodel;
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    private String SaveOrUpdateButton = "Save";
    int update = 0;
    boolean committe_type;
    boolean comMemberEndDate;
    private String comMemberStatus = "";
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    boolean validFromTo = false;
    private String icone = "ui-icon-plus";
    private String activeIndex;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct method">
    @PostConstruct
    public void _init() {
        committe_type = true; //to make disable Start and End Date
        
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public List<ColumnNameResolver> getColumnNameResolverList() {
        ColumnNameResolverList = hrCommitteeBeanLocal.findColumns();
        if (ColumnNameResolverList == null) {
            ColumnNameResolverList = new ArrayList<>();
        }
        return ColumnNameResolverList;
    }
    
    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }
    
    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }
    
    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }
    
    public boolean isValidFromTo() {
        return validFromTo;
    }
    
    public void setValidFromTo(boolean validFromTo) {
        this.validFromTo = validFromTo;
    }
    
    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }
    
    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }
    
    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }
    
    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }
    
    public String getIcone() {
        return icone;
    }
    
    public void setIcone(String icone) {
        this.icone = icone;
    }
     public String getMsg() {
        return msg;
    }
  public HrExComitteMembers getHrExComitteMembers() {
        if (hrExComitteMembers == null) {
            hrExComitteMembers = new HrExComitteMembers();
        }
        return hrExComitteMembers;
    }

    public void setHrExComitteMembers(HrExComitteMembers hrExComitteMembers) {
        this.hrExComitteMembers = hrExComitteMembers;
    }

    public DataModel<HrExComitteMembers> getHrExCommitteeMemberModel() {
        if (hrExCommitteeMemberModel == null) {
            hrExCommitteeMemberModel = new ArrayDataModel<>();
        }
        return hrExCommitteeMemberModel;
    }

    public void setHrExCommitteeMemberModel(DataModel<HrExComitteMembers> hrExCommitteeMemberModel) {
        this.hrExCommitteeMemberModel = hrExCommitteeMemberModel;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataModel<HrCommittees> getCommitteeDatamodel() {
        return committeeDatamodel;
    }

    public void setCommitteeDatamodel(DataModel<HrCommittees> committeeDatamodel) {
        this.committeeDatamodel = committeeDatamodel;
    }

    public boolean isCommitte_type() {
        return committe_type;
    }

    public void setCommitte_type(boolean committe_type) {
        this.committe_type = committe_type;
    }

    public boolean isComMemberEndDate() {
        return comMemberEndDate;
    }

    public void setComMemberEndDate(boolean comMemberEndDate) {
        this.comMemberEndDate = comMemberEndDate;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
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

    public DataModel<HrCommitteeMembers> getHrCommitteeMemberModel() {
        if (hrCommitteeMemberModel == null) {
            hrCommitteeMemberModel = new ArrayDataModel<>();
        }
        return hrCommitteeMemberModel;
    }

    public void setHrCommitteeMemberModel(DataModel<HrCommitteeMembers> hrCommitteeMemberModel) {
        this.hrCommitteeMemberModel = hrCommitteeMemberModel;
    }

    public HrLuTitles getHrLuTitles() {
        return hrLuTitles;
    }

    public void setHrLuTitles(HrLuTitles hrLuTitles) {
        this.hrLuTitles = hrLuTitles;
    }

    public HrDepartments getDepartments() {
        if (departments == null) {
            departments = new HrDepartments();
        }
        return departments;
    }

    public void setDepartments(HrDepartments departments) {
        this.departments = departments;
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

    public HrCommitteeMembers getSelectComMembers() {
        return selectComMembers;
    }

    public void setSelectComMembers(HrCommitteeMembers selectComMembers) {
        this.selectComMembers = selectComMembers;
    }

    List<HrCommittees> committeList = new ArrayList<>();

    public List<HrCommittees> getCommitteList() {
        return committeList;
    }

    public void setCommitteList(List<HrCommittees> committeList) {
        this.committeList = committeList;
    }

    public HrLuCommitteeTypes getHrLuCommitteeTypes() {
        if (hrLuCommitteeTypes == null) {
            hrLuCommitteeTypes = new HrLuCommitteeTypes();
        }
        return hrLuCommitteeTypes;
    }

    public void setHrLuCommitteeTypes(HrLuCommitteeTypes hrLuCommitteeTypes) {
        this.hrLuCommitteeTypes = hrLuCommitteeTypes;
    }

    public HrCommittees getHrCommittees() {
        if (hrCommittees == null) {
            hrCommittees = new HrCommittees();
        }
        return hrCommittees;
    }

    public void setHrCommittees(HrCommittees hrCommittees) {
        this.hrCommittees = hrCommittees;
    }

    public HrCommitteeMembers getHrCommitteeMembers() {
        if (hrCommitteeMembers == null) {
            hrCommitteeMembers = new HrCommitteeMembers();
        }
        return hrCommitteeMembers;
    }

    public SelectItem[] getCommitteeType() {

        return JsfUtil.getSelectItems(hrLuCommitteeTypesBeanLocal.findAll(), true);
    }

    public void setHrCommitteeMembers(HrCommitteeMembers hrCommitteeMembers) {
        this.hrCommitteeMembers = hrCommitteeMembers;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }
    
    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="main methods">
    public void changeSEndDate(ValueChangeEvent events) {
        String comType = events.getNewValue().toString();
        if (comType.equalsIgnoreCase("Contract")) {
            validFromTo = true;
            
        } else {
            validFromTo = false;
        }
    }
    
    public void changeSEndDateForCommitteMembers(ValueChangeEvent events) {
        comMemberStatus = events.getNewValue().toString();
        if (comMemberStatus.equalsIgnoreCase("Inactive")) {
            comMemberEndDate = false;
        } else {
            comMemberEndDate = true;
        }
    }
    String msg;
    
    
    public void findByCommitteeName() {
        
        if (hrCommittees.getCol_Value() != null && columnNameResolver.getCol_Name_FromTable() != null) {
            committeList = hrCommitteeBeanLocal.findByParameteredColumnValue(columnNameResolver.getCol_Name_FromTable(), hrCommittees.getCol_Value());
        } else {
            committeList = hrCommitteeBeanLocal.findAll();
            
        }
        
    }
    
    public void recreate() {
        hrCommittees = null;
    }
    
    int selectedRowIndexLanguage = -1;
    
    public void saveCommittee() {
        
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveCommittee", dataset)) {
                //  put ur code here...!
                
                hrCommitteeBeanLocal.saveOrUpdate(hrCommittees);
                if (update == 0) {
                    JsfUtil.addSuccessMessage("Saved Successfuly.");
                } else {
                    JsfUtil.addSuccessMessage("Update Successful.");
                }
                
                clearCommittee();
                
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
////..... add more information by calling fields defined in the object
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage(hrCommittees.getCommitteeName() + ":allready registered.  Error");
        }
    }
    
    List<HrCommitteeMembers> committeeMembersList;
    
    public List<HrCommitteeMembers> getCommitteeMembersList() {
        return committeeMembersList;
    }
    
    public void setCommitteeMembersList(List<HrCommitteeMembers> committeeMembersList) {
        this.committeeMembersList = committeeMembersList;
    }
    
    public void committeeNameEvent(ValueChangeEvent events) {
        
        hrLuCommitteeTypes = (HrLuCommitteeTypes) events.getNewValue();
        if (hrLuCommitteeTypes != null) {
            
            hrCommittees = hrCommitteeBeanLocal.findByCommitteType(hrLuCommitteeTypes);
            if (hrCommittees != null) {
                committeeMembersList = hrCommitteeMembersBeaLocal.getCommitteeMembers(hrCommittees);
                if (committeeMembersList != null) {
                    
                    hrCommittees.setHrCommitteeMembersList(committeeMembersList);
                    anewModel();
                    SaveOrUpdateButton = "Update";
                    update = 0;
                }
            }
        }
        
    }
    
    public void populate(SelectEvent events) {
        hrCommitteeMemberModel = null;
        
        hrCommittees = (HrCommittees) events.getObject();
        hrCommittees.setId(hrCommittees.getId());
        SaveOrUpdateButton = "Update";
        update = 0;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        String comType = hrCommittees.getCommitteeName();
        if (comType.equalsIgnoreCase("Permanent")) {
            validFromTo = false;
        } else {
            validFromTo = true;
        }
        
        anewModel();
        aneExwModel();
    }
    
    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setFirstName(hrEmployee);
        employees = hrEmployeeBean.searchEmployeeByName(hrEmployees);
        employees = hrCommitteeMembersBeaLocal.searchEmp(hrEmployee);
        return employees;
    }
    
    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(event.getNewValue().toString().toLowerCase() + ":"));
            // hrCandidiates.setCol_Value(columnNameResolver.getParsed_Col_Name());
        }
    }
    
    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }
    
    public void getByFirstName(SelectEvent event) {
        hrEmployees.setFirstName((event.getObject().toString()));
        hrEmployees = hrCommitteeMembersBeaLocal.getByFirstName(hrEmployees);
        hrJobTypes = hrEmployees.getJobId();
        departments = hrEmployees.getDeptId();
        
    }
    
    public void edit() {
        hrCommitteeMembers = hrCommitteeMemberModel.getRowData();
        
        hrExComitteMembers.getExcmId();
        
        hrEmployees = hrCommitteeMembers.getEmpId();
        hrJobTypes = hrEmployees.getJobId();
        departments = hrEmployees.getDeptId();
        
    }
    
    public void editEx() {
        hrExComitteMembers = hrExCommitteeMemberModel.getRowData();
        hrExComitteMembers.getExcmId();
        
    }
    
    public void addCommitteeMembers() {
        hrCommitteeMembers.setEmpId(hrEmployees);
        hrCommittees.addCommitteeMember(hrCommitteeMembers);
        anewModel();
        resetInternal();
        
    }
    
    public void addExCommitteeMembers() {
        hrCommittees.addExCommitteeMember(hrExComitteMembers);
        aneExwModel();
        resetEXternal();
        
    }
    
    public void anewModel() {
        hrCommitteeMemberModel = null;
        hrCommitteeMemberModel = new ListDataModel(new ArrayList(hrCommittees.getHrCommitteeMembersList()));
    }
    
    public void aneExwModel() {
        hrExCommitteeMemberModel = null;
        hrExCommitteeMemberModel = new ListDataModel(new ArrayList(hrCommittees.getHrExComitteMembersList()));
    }
    
    public String clearCommittee() {
        resetEXternal();
        resetInternal();
        hrExCommitteeMemberModel = null;
        hrCommitteeMemberModel = null;
        hrCommittees = null;
        update = 0;
        SaveOrUpdateButton = "Save";
        return null;
        
    }
    
    public void resetEXternal() {
        hrExComitteMembers = null;
    }
    
    public void resetInternal() {
        hrCommitteeMembers = null;
        departments = null;
        hrJobTypes = null;
        hrEmployees = null;
    }
    
    public void createNewAdditionalAmount() {
        if (createOrSearchBundle.equals("New")) {
            update = 1;
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
//</editor-fold>
  

}
