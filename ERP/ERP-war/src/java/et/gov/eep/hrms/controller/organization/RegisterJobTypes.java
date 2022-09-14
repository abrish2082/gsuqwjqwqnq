/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.organization;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.lookup.HrLuEducLevelsBeanLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuEducTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuJobLevelsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDeptJobsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrJobEducQualificationsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrJobTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrSalaryScaleRangesBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobEducQualifications;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Administrator
 */
@Named(value = "registerJobTypes")
@ViewScoped
public class RegisterJobTypes implements Serializable {

    @EJB
    HrDeptJobsBeanLocal hrDeptJobsBeanLocal;//used to add the department jobs unde the departments

    @Inject
    HrJobTypes hrSelectedJobTypes;

    /**
     * Creates a new instance of RegisterJobTypes
     */
    public RegisterJobTypes() {
    }

    /**
     *
     * @return
     */
    public HrJobTypes getHrSelectedJobTypes() {

        if (hrSelectedJobTypes == null) {
            hrSelectedJobTypes = new HrJobTypes();
        }
        return hrSelectedJobTypes;
    }

    /**
     *
     * @param hrSelectedJobTypes
     */
    public void setHrSelectedJobTypes(HrJobTypes hrSelectedJobTypes) {
        this.hrSelectedJobTypes = hrSelectedJobTypes;
    }

    /**
     *
     * @return
     */
    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }

    /**
     *
     * @param hrJobTypes
     */
    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;

    }

    private String txt1;

    /**
     *
     * @return
     */
    public String getTxt1() {
        return txt1;
    }

    /**
     *
     * @param txt1
     */
    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    /**
     *
     * @param query
     * @return
     */
    public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            results.add(query + i);
        }

        return results;
    }

    /**
     *
     */
    public void updateJobTypes() {
        try {
            hrJobTypes.setId(Integer.SIZE);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     */
    public void removeEduQualification() {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Inject
    HrLuGrades hrLuGrades;

    private List<HrJobEducQualifications> hrJobEducationQual;

    /**
     *
     * @return
     */
    public List<HrJobEducQualifications> getHrJobEducationQual() {
        return hrJobEducationQual;
    }

    /**
     *
     * @param hrJobEducationQual
     */
    public void setHrJobEducationQual(List<HrJobEducQualifications> hrJobEducationQual) {
        this.hrJobEducationQual = hrJobEducationQual;
    }

    /**
     *
     */
//    public void saveJobTypes() {
//        try {
//
//            System.out.print("The salary scale code is --------------->" + salaryScalseCode);
//            BigDecimal salScaleid = new BigDecimal(String.valueOf(salaryScalseCode));
////            salaryScale.setId(salScaleid);
//            HrSalaryScaleRanges hr = new HrSalaryScaleRanges();
//            hr.setId(salScaleid);
//            hrJobTypes.setJobGradeId(hr);
//            hrJobTypesBeanLocal.edit(hrJobTypes);
//            if (saveOrUpdate.equalsIgnoreCase("Save")) {
//                addMessage("Successfully Saved!");
//            }
//            if (saveOrUpdate.equalsIgnoreCase("Update")) {
//                addMessage("Successfully Updated!");
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
    /**
     *
     */
//    public void addEducationQualification() {
//        try {
//            if (isNewJobType == false) {//edit is made
//                HrJobEducQualifications jq = new HrJobEducQualifications();
//                HrLuEducLevels eduLevel = new HrLuEducLevels();
//                HrLuEducTypes eduType = new HrLuEducTypes();
//
//                eduLevel.setId(getEducLevelId());
//                eduLevel.setEducLevel(getEducTypeDescription());
//                eduType.setId(getEducTypeId());
//                eduType.setEducType(getEducTypeDescription());
//
//                jq.setEducLevelId(eduLevel);
//                jq.setEducQualId(eduType);
//                jq.setMinExperiance(jobEducationQualification.getMinExperiance());
//                jq.setId(jobEducationQualification.getId());
//                jq.setJobId(hrJobTypes);
//                hrJobTypes.getHrJobEducQualificationsList().add(jq);
//                hrJobEducationQuals = hrJobTypes.getHrJobEducQualificationsList();
//                hrJobTypes.getHrJobEducQualificationsList().remove(jobEducationQualification);
//            } else {//addint new education qualification is ma
//                HrJobEducQualifications jq = new HrJobEducQualifications();
//
//                HrLuEducLevels eduLevel = new HrLuEducLevels();
//                HrLuEducTypes eduType = new HrLuEducTypes();
//
//                eduLevel.setId(getEducLevelId());
//                eduLevel.setEducLevel(getEducTypeDescription());
//                eduType.setId(getEducTypeId());
//                eduType.setEducType(getEducTypeDescription());
//
//                jq.setEducLevelId(eduLevel);
//                jq.setEducQualId(eduType);
//
//                jq.setMinExperiance(jobEducationQualification.getMinExperiance());
//                jq.setJobId(hrJobTypes);
//                hrJobTypes.getHrJobEducQualificationsList().add(jq);
//                hrJobEducationQuals = hrJobTypes.getHrJobEducQualificationsList();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
    /**
     *
     */
//    public void save() {
//        try {
//            hrJobTypes.setAdditionalSkill("Either CCNA or CCNP");
//            BigDecimal big = new BigDecimal(30);
//            jobEducationQualification.setId(big);
//            hrJobTypes.getHrJobEducQualificationsList().add(jobEducationQualification);
//
//            // hrJobTypes.s
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
    @Inject
    HrJobEducQualifications jobEducationQualification;

    /**
     *
     * @return
     */
    public HrJobEducQualifications getJobEducationQualification() {
        if (jobEducationQualification == null) {

            jobEducationQualification = new HrJobEducQualifications();
        }
        return jobEducationQualification;
    }

    /**
     *
     * @param jobEducationQualification
     */
    public void setJobEducationQualification(HrJobEducQualifications jobEducationQualification) {
        this.jobEducationQualification = jobEducationQualification;
    }

    /**
     *
     */
    public void loadEducationQualification() {

    }

    private List<HrLuEducLevels> listOfEducLevelrs;
    private List<HrLuEducTypes> listOfEduTypes;

    /**
     *
     * @return
     */
    public List<HrLuEducLevels> getListOfEducLevelrs() {
        return hrLuEducLevelsBeanLocal.findAll();

    }

    /**
     *
     * @param listOfEducLevelrs
     */
    public void setListOfEducLevelrs(List<HrLuEducLevels> listOfEducLevelrs) {

        this.listOfEducLevelrs = listOfEducLevelrs;
    }

    /**
     *
     * @return
     */
    public List<HrLuEducTypes> getListOfEduTypes() {
        return hrLuEducTypesBeanLocal.findAll();

    }

    /**
     *
     * @param listOfEduTypes
     */
    public void setListOfEduTypes(List<HrLuEducTypes> listOfEduTypes) {
        this.listOfEduTypes = listOfEduTypes;
    }

    /**
     *
     */
    private String jobTitle;

    /**
     *
     * @return
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     *
     * @param jobTitle
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     *
     * @param event
     */
    public void onItemSelect(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Selected", event.getObject().toString()));
    }

    private String message;

    /**
     *
     * @param query
     * @return
     */
    public List<String> complete(String query) {
        List<String> queries = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            queries.add(query + i);
        }
        return queries;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * returns the first value of the concatination
     *
     * @param vToBeSplited value to be splited eg xxx-yyyy
     * @return xxx
     */
    public int firstSplietedValue(String vToBeSplited) {
        try {
            int fSplitedValue;
            String conc[];
            conc = vToBeSplited.split("-");
            fSplitedValue = Integer.valueOf(conc[0]);
            return fSplitedValue;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     *
     * @param actionEvent
     */
    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
    }

    /**
     *
     * @param summary
     */
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private boolean isNewJobType;

    /**
     *
     * @return
     */
    public boolean isIsNewJobType() {
        return isNewJobType;
    }

    /**
     *
     * @param isNewJobType
     */
    public void setIsNewJobType(boolean isNewJobType) {
        this.isNewJobType = isNewJobType;
    }

    /**
     *
     */
    public void edit() {
        try {
            isNewJobType = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     */
    public void addNewItem() {
        try {
            isNewJobType = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private BigDecimal step;

    /**
     *
     * @return
     */
    public BigDecimal getStep() {
        return step;
    }

    /**
     *
     * @param step
     */
    public void setStep(BigDecimal step) {
        this.step = step;
    }

    private String stepDescription;

    /**
     *
     * @return
     */
    public String getStepDescription() {
        return stepDescription;
    }

    /**
     *
     * @param stepDescription
     */
    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    /**
     *
     * @param steps
     * @return
     */
    public String returnId(String steps) {
        try {
            String depId = null;
            String conc[];
            conc = steps.split("-");
            depId = conc[0];
            return depId;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param stepDescription
     * @return
     */
    public String returnDescrption(String stepDescription) {
        try {
            String description = null;
            String conc[];
            conc = stepDescription.split("-");
            description = conc[1];
            return description;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Integer educLevelId;
    private String deducLevelDesc;

    /**
     *
     * @return
     */
    public Integer getEducLevelId() {
        return educLevelId;
    }

    /**
     *
     * @param educLevelId
     */
    public void setEducLevelId(Integer educLevelId) {
        this.educLevelId = educLevelId;
    }

    /**
     *
     * @return
     */
    public String getDeducLevelDesc() {
        return deducLevelDesc;
    }

    /**
     *
     * @param deducLevelDesc
     */
    public void setDeducLevelDesc(String deducLevelDesc) {
        this.deducLevelDesc = deducLevelDesc;
    }

    /**
     *
     * @param event
     */
    public void educationLevel(ValueChangeEvent event) {
        educLevelId = null;
        deducLevelDesc = null;
        setEducLevelId(Integer.valueOf(event.getNewValue().toString()));
        setDeducLevelDesc(returnDescrption(event.getNewValue().toString()));

    }

    private BigDecimal educTypeId;
    private String educTypeDescription;

    /**
     *
     * @return
     */
    public BigDecimal getEducTypeId() {
        return educTypeId;
    }

    /**
     *
     * @param educTypeId
     */
    public void setEducTypeId(BigDecimal educTypeId) {
        this.educTypeId = educTypeId;
    }

    /**
     *
     * @return
     */
    public String getEducTypeDescription() {
        return educTypeDescription;
    }

    /**
     *
     * @param educTypeDescription
     */
    public void setEducTypeDescription(String educTypeDescription) {
        this.educTypeDescription = educTypeDescription;
    }

    /**
     *
     * @param event
     */
    public void educationType(ValueChangeEvent event) {
        educTypeId = null;
        educTypeDescription = null;
        String educatonType = event.getNewValue().toString();
        BigDecimal educType = new BigDecimal(returnId(educatonType));
        setEducTypeId(educType);
        setEducTypeDescription(returnDescrption(educatonType));

    }

    private String selectedGradeItemValue;

    /**
     *
     * @return
     */
    public String getSelectedGradeItemValue() {
        return selectedGradeItemValue;
    }

    /**
     *
     * @param selectedGradeItemValue
     */
    public void setSelectedGradeItemValue(String selectedGradeItemValue) {
        this.selectedGradeItemValue = selectedGradeItemValue;
    }

    /**
     *
     * @param event
     */
    public void getGrades(ValueChangeEvent event) {
        try {
            selectedGradeItemValue = event.getNewValue().toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param event
     */
    public void salaryScaleRange(ValueChangeEvent event) {
        try {
            System.out.print("The selected event is " + event.getNewValue().toString());
            salaryScalseCode = event.getNewValue().toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String salaryScalseCode;

    /**
     *
     * @return
     */
    public String getSalaryScalseCode() {
        return salaryScalseCode;
    }

    /**
     *
     * @param salaryScalseCode
     */
    public void setSalaryScalseCode(String salaryScalseCode) {
        this.salaryScalseCode = salaryScalseCode;
    }

    /**
     *
     * @param ev
     */
    public void onSalaryScaleRangeChange(ValueChangeEvent ev) {
        System.out.print("The selected value is " + ev.getNewValue().toString());
    }

    /**
     *
     * @param event
     */
    public void handleChange(ValueChangeEvent event) {
        salaryScalseCode = event.getNewValue().toString();
    }

    /**
     *
     */
    public void handleChange1() {
        System.out.println("Entered!");

        System.out.println("New value: ");
//        code = returnId(event.getNewValue().toString());

    }

    /**
     * munir code
     */
    // <editor-fold defaultstate="collapsed" desc="main">
    @EJB
    HrJobTypesBeanLocal hrJobTypesBeanLocal;
    @EJB
    HrSalaryScaleRangesBeanLocal hrSalaryScaleRangesBeanLocal;
    @EJB
    HrLuEducLevelsBeanLocal hrLuEducLevelsBeanLocal;
    @EJB
    HrLuEducTypesBeanLocal hrLuEducTypesBeanLocal;
    @EJB
    HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
    HrJobEducQualificationsBeanLocal hrJobEducQualificationsBeanLocal;
    @EJB
    HrLuJobLevelsBeanLocal hrLuJobLevelsBeanLocal;

    @Inject
    HrJobEducQualifications hrJobEducQualifications;
    @Inject
    HrJobTypes hrJobTypes;
    @Inject
    HrLuEducLevels hrLuEducLevels;
    @Inject
    HrLuEducTypes hrLuEducTypes;

    @Inject
    HrDepartments hrDepartments;
    @Inject
    HrSalaryScaleRanges hrSalaryScaleRanges;

    List<HrSalaryScaleRanges> jobGrades;
    List<HrLuEducLevels> educLevels;
    List<HrLuEducTypes> educTypes;
    List<HrLuJobLevels> jobLevels;
    List<HrJobEducQualifications> hrJobEducationQuals;

    private String saveOrUpdate = "Save";
    private int updated = 0;
    private int selectedRowIndex = -1;

    // <editor-fold defaultstate="collapsed" desc="getter & setter">
    List<HrSalaryScaleRanges> listOfSalaryScaleRanges;

    /**
     *
     * @return
     */
    public List<HrSalaryScaleRanges> getListOfSalaryScaleRanges() {
        listOfSalaryScaleRanges = hrSalaryScaleRangesBeanLocal.findAll();
        return listOfSalaryScaleRanges;
    }

    /**
     *
     * @param listOfSalaryScaleRanges
     */
    public void setListOfSalaryScaleRanges(List<HrSalaryScaleRanges> listOfSalaryScaleRanges) {
        this.listOfSalaryScaleRanges = listOfSalaryScaleRanges;
    }

    /**
     *
     * @return
     */
    public List<HrSalaryScaleRanges> getJobGrades() {
        return hrSalaryScaleRangesBeanLocal.allJobGrades();
    }

    /**
     *
     * @param jobGrades
     */
    public void setJobGrades(List<HrSalaryScaleRanges> jobGrades) {
        this.jobGrades = jobGrades;
    }

    /**
     *
     * @return
     */
    public List<HrLuEducLevels> getEducLevels() {
        return hrLuEducLevelsBeanLocal.findAll();
    }

    /**
     *
     * @param educLevels
     */
    public void setEducLevels(List<HrLuEducLevels> educLevels) {
        this.educLevels = educLevels;
    }

    /**
     *
     * @return
     */
    public List<HrLuEducTypes> getEducTypes() {
        return hrLuEducTypesBeanLocal.findAll();
    }

    /**
     *
     * @param educTypes
     */
    public void setEducTypes(List<HrLuEducTypes> educTypes) {
        this.educTypes = educTypes;
    }

    /**
     *
     * @return
     */
    public List<HrLuJobLevels> getJobLevels() {
        return hrLuJobLevelsBeanLocal.findAll();
    }

    /**
     *
     * @param jobLevels
     */
    public void setJobLevels(List<HrLuJobLevels> jobLevels) {
        this.jobLevels = jobLevels;
    }

    /**
     *
     * @return
     */
    public List<HrJobEducQualifications> getHrJobEducationQuals() {
        return hrJobEducationQuals;
    }

    /**
     *
     * @param hrJobEducationQuals
     */
    public void setHrJobEducationQuals(List<HrJobEducQualifications> hrJobEducationQuals) {
        this.hrJobEducationQuals = hrJobEducationQuals;
    }

    /**
     *
     * @return
     */
    public HrLuEducLevels getHrLuEducLevels() {
        if (hrLuEducLevels == null) {
            hrLuEducLevels = new HrLuEducLevels();
        }
        return hrLuEducLevels;
    }

    /**
     *
     * @param hrLuEducLevels
     */
    public void setHrLuEducLevels(HrLuEducLevels hrLuEducLevels) {
        this.hrLuEducLevels = hrLuEducLevels;
    }

    /**
     *
     * @return
     */
    public HrLuEducTypes getHrLuEducTypes() {
        if (hrLuEducTypes == null) {
            hrLuEducTypes = new HrLuEducTypes();
        }
        return hrLuEducTypes;
    }

    /**
     *
     * @param hrLuEducTypes
     */
    public void setHrLuEducTypes(HrLuEducTypes hrLuEducTypes) {
        this.hrLuEducTypes = hrLuEducTypes;
    }

    /**
     *
     * @return
     */
    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        if (hrSalaryScaleRanges == null) {
            hrSalaryScaleRanges = new HrSalaryScaleRanges();
        }
        return hrSalaryScaleRanges;
    }

    /**
     *
     * @param hrSalaryScaleRanges
     */
    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
    }

    /**
     *
     * @return
     */
    public HrJobEducQualifications getHrJobEducQualifications() {
        if (hrJobEducQualifications == null) {
            hrJobEducQualifications = new HrJobEducQualifications();
        }
        return hrJobEducQualifications;
    }

    /**
     *
     * @param hrJobEducQualifications
     */
    public void setHrJobEducQualifications(HrJobEducQualifications hrJobEducQualifications) {
        this.hrJobEducQualifications = hrJobEducQualifications;
    }

    /**
     *
     * @return
     */
    public String getSaveorUpdate() {
        return saveOrUpdate;
    }

    /**
     *
     * @param saveOrUpdate
     */
    public void setSaveorUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    // </editor-fold>
    /**
     *
     */
    @PostConstruct
    public void load() {
        loadTree();
    }

    //  <editor-fold defaultstate="collapsed" desc=" department popup ">
    private TreeNode root;
    private TreeNode selectedNode;
    private String selected;
    private int departmentId = 0;

    private static List<HrDepartments> departments;
    private static List<HrDepartments> allDepartments;

    //  <editor-fold defaultstate="collapsed" desc=" Getter and Setter ">
    /**
     *
     * @return
     */
    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }

    /**
     *
     * @param hrDepartments
     */
    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }

    /**
     *
     * @return
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     *
     * @param root
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     *
     * @return
     */
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     *
     * @param selectedNode
     */
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    /**
     *
     * @return
     */
    public String getSelected() {
        return selected;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(String selected) {
        this.selected = selected;
    }

    /**
     *
     * @return
     */
    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    /**
     *
     * @param saveOrUpdate
     */
    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    /**
     *
     * @return
     */
    public static List<HrDepartments> getAllDepartments() {
        return allDepartments;
    }

    /**
     *
     * @param allDepartments
     */
    public static void setAllDepartments(List<HrDepartments> allDepartments) {
        RegisterJobTypes.allDepartments = allDepartments;
    }
    //  </editor-fold>

    /**
     *
     */
    public void loadTree() {
        allDepartments = hrDepartmentsBeanLocal.findAllDepartment();
        root = new DefaultTreeNode("Root", null);
        populateNode(allDepartments, 0, root);
    }

    /**
     *
     * @param departmentData
     * @param id
     * @param node
     */
    public void populateNode(List<HrDepartments> departmentData, int id, TreeNode node) {
        departments = new ArrayList<>();
        for (HrDepartments k : getAllDepartments()) {
            if (k.getParentId() == id) {
                TreeNode childNode = new DefaultTreeNode(k.getDepName() + "=>" + k.getDepId(), node);
                departments.add(k);
                populateNode(departments, k.getDepId(), childNode);
            }
        }
    }

    /**
     *
     * @param event
     */
    public void onNodeSelect(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        departmentId = Integer.parseInt((selectedNode.getData().toString()).split("=>")[1]);
        hrDepartments.setDepName((selectedNode.getData().toString()).split("=>")[0]);
        hrDepartments.setDepId(departmentId);
        hrDepartments = hrDepartmentsBeanLocal.findByDepartmentId(hrDepartments);
//        int depid = hrDepartments.getDepId();
        hrJobTypes.setDepId(hrDepartments);
        System.out.println("dep---------------------------------" + hrJobTypes.getDepId().getDepName());
    }
    // </editor-fold>

    /**
     *
     * @param jobTitle
     * @return
     */
    public List<HrJobTypes> searchByJobTitle(String jobTitle) {
        List<HrJobTypes> jobTitles = null;
        if (jobTitle != null) {
//            jobTitles = hrJobTypesBeanLocal.searchByJobTitle(jobTitle);
        }
        return jobTitles;
    }

    /**
     * this method is changed by the above searchByjobTitle method
     *
     * @param jType
     * @return
     */
    public List<HrJobTypes> loadListOfJobTypes(String jType) {
        try {
            return hrJobTypesBeanLocal.returnJobTypes(jType);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param event
     */
    public void findAllJobDetail(SelectEvent event) {
        if (event.getObject() != null) {
            int JobId = Integer.valueOf(event.getObject().toString());
            hrJobTypes.setId(JobId);
            hrJobTypes = hrJobTypesBeanLocal.findAllJobDetail(hrJobTypes);
            hrDepartments.setDepName(hrJobTypes.getDepId().getDepName());
//            hrSalaryScaleRanges.setGradeId(hrJobTypes.getJobGradeId().getGradeId());
            saveOrUpdate = "Update";
            updated = 1;
            recreateModelDetail();
        }
    }

    /**
     * replace by findAllJobDetail method
     *
     * @param event
     */
//    public void handleSelect(SelectEvent event) {
//        try {
//            System.out.print("The selected event is " + event.getObject().toString());
//            saveOrUpdate = null;
//            hrJobTypes = hrJobTypesBeanLocal.searchJobTypesById(firstSplietedValue(event.getObject().toString()));
//            hrJobEducationQual = hrJobTypes.getHrJobEducQualificationsList();
//            saveOrUpdate = "Update";
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
    private DataModel<HrJobEducQualifications> jobEducQualModel;

    /**
     *
     * @return
     */
    public DataModel<HrJobEducQualifications> getJobEducQualModel() {
        if (hrJobEducQualifications == null) {
            hrJobEducQualifications = new HrJobEducQualifications();
        }
        return jobEducQualModel;
    }

    /**
     *
     * @param jobEducQualModel
     */
    public void setJobEducQualModel(DataModel<HrJobEducQualifications> jobEducQualModel) {
        this.jobEducQualModel = jobEducQualModel;
    }

    /**
     *
     */
    public void recreateModelDetail() {
        jobEducQualModel = null;
        jobEducQualModel = new ListDataModel(new ArrayList<>(hrJobTypes.getHrJobEducQualificationsList()));
    }

    /**
     *
     */
    public void clearPopup() {
        hrJobEducQualifications = null;
    }

    /**
     *
     */
    public void clearPage() {
        hrJobTypes = null;
        hrDepartments = null;
        hrSalaryScaleRanges = null;
        jobEducQualModel = null;
        updated = 0;
    }

    /**
     *
     * @param event
     */
    public void somEducLevel_valueChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrLuEducLevels.setId(Integer.valueOf(event.getNewValue().toString()));
            hrLuEducLevels = hrLuEducLevelsBeanLocal.find(hrLuEducLevels.getId());
            hrJobEducQualifications.setEducLevelId(hrLuEducLevels);
        }
    }

    /**
     *
     * @param event
     */
    public void somEducType_valueChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String _eduTypeId = event.getNewValue().toString();
            BigDecimal eduTypeId = new BigDecimal(_eduTypeId);
            hrLuEducTypes.setId(eduTypeId);
            hrJobEducQualifications.setEducQualId(hrLuEducTypes);
        }
    }

    /**
     *
     */
    public void btnRowSelector() {
        selectedRowIndex = getJobEducQualModel().getRowIndex();
        displayEducQual();
    }

    private void displayEducQual() {
        clearPopup();
        if (selectedRowIndex > -1) {
            hrJobEducQualifications = getJobEducQualModel().getRowData();
            hrLuEducLevels = hrJobEducQualifications.getEducLevelId();
            hrLuEducTypes = hrJobEducQualifications.getEducQualId();
        }
    }

    private boolean checkDuplicate() {
        for (HrJobEducQualifications ent : hrJobTypes.getHrJobEducQualificationsList()) {
            if (ent.getEducLevelId().getEducLevel() != null && ent.getEducQualId().getEducType() != null && ent.getMinExperiance() != null) {
                if (ent.getEducLevelId().getEducLevel().equalsIgnoreCase(hrJobEducQualifications.getEducLevelId().getEducLevel())
                        && ent.getEducQualId().getEducType().equalsIgnoreCase(hrJobEducQualifications.getEducQualId().getEducType())
                        && ent.getMinExperiance().toString().equalsIgnoreCase(hrJobEducQualifications.getMinExperiance().toString())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     */
    public void addToTable() {
        hrJobEducQualifications.setEducLevelId(hrLuEducLevels);
        hrJobEducQualifications.setEducQualId(hrLuEducTypes);
        if (selectedRowIndex > -1) {
            hrJobEducQualifications.setId(hrJobTypes.getHrJobEducQualificationsList().get(selectedRowIndex).getId());
            hrJobTypes.getHrJobEducQualificationsList().remove(selectedRowIndex);
            if (checkDuplicate()) {
                hrJobTypes.getHrJobEducQualificationsList().add(selectedRowIndex, hrJobEducQualifications);
                JsfUtil.addErrorMessage("Succefully Updated!");
                recreateModelDetail();
            } else {
                JsfUtil.addErrorMessage("Duplicate Entry!");
            }
        } else {
            if (checkDuplicate()) {
                hrJobTypes.addJobEducQual(hrJobEducQualifications);
                JsfUtil.addErrorMessage("Succefully Added!");
                recreateModelDetail();
            } else {
                JsfUtil.addErrorMessage("Duplicate Entry!");
            }
        }
        clearPopup();
        selectedRowIndex = -1;
    }

    /**
     *
     */
    public void save() {
        System.out.println("--------------------save-----------------------");
        try {
            if (updated == 0) {
                if (hrJobTypesBeanLocal.checkDuplicateJob(hrJobTypes)) {
                    JsfUtil.addErrorMessage("The Job Title or Job Code is already registered.");
                    clearPage();
                } else {
                    hrJobTypesBeanLocal.create(hrJobTypes);
                    JsfUtil.addSuccessMessage("New Job has been Saved Successfully.");
                }
            } else {
                hrJobTypesBeanLocal.edit(hrJobTypes);
                JsfUtil.addSuccessMessage("Job Information Updated Successfully.");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error ocurred while saving. Please try again.");
        }
    }

    /**
     *
     */
    public void reset() {
        clearPage();
        SaveOrUpdateButton = "Save";
    }

    // </editor-fold> 
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-document";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private String renderpnlContrat = "false";
    private String SaveOrUpdateButton = "Save";

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public void createNewGatePassInfo() {
        SaveOrUpdateButton = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-document");
        }
    }

    // binnymatty
    DataModel<HrJobTypes> hrJobTypesListDataModel = new ListDataModel<>();
    private List<HrJobTypes> hrJobTypesList = new ArrayList<>();
//    DataModel<HrJobTypes> hrJobTypesListDataModel;

    public List<HrJobTypes> getHrJobTypesList() {
        return hrJobTypesList;
    }

    public void setHrJobTypesList(List<HrJobTypes> hrJobTypesList) {
        this.hrJobTypesList = hrJobTypesList;
    }

    public DataModel<HrJobTypes> getHrJobTypesListDataModel() {
        return hrJobTypesListDataModel;
    }

    public void setHrJobTypesListDataModel(DataModel<HrJobTypes> hrJobTypesListDataModel) {
        this.hrJobTypesListDataModel = hrJobTypesListDataModel;
    }

    public void findByJobTitle() {
//        hrJobTypesList = new ArrayList<>(hrJobTypesBeanLocal.findByJobTitle(hrJobTypes));
        hrJobTypesListDataModel = new ListDataModel(hrJobTypesList);

    }

    public void selectJob(SelectEvent event) {
        SaveOrUpdateButton = "Update";
        hrJobTypes = (HrJobTypes) event.getObject();
        hrDepartments = hrJobTypes.getDepId();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        setIcone("ui-icon-search");
        recreateModelDetail();
    }

}
