package et.gov.eep.hrms.controller.recruitment;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.lookup.HrLuEducLevelsBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrCandidateSelectedBeanLocal;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrCandidateSelected;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author munir
 */
@Named(value = "hrCandidateSelectedController")
@ViewScoped
public class HrCandidateSelectedController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            HrCandidateSelected hrCandidateSelected;
    @Inject
            HrCandidiates hrCandidiates;
    @Inject
            HrAdvertisements hrAdvertisements;
    @Inject
            HrAdvertisedJobs hrAdvertisedJobs;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrLuEducLevels hrLuEducLevels;
    @Inject
            WfHrProcessed wfHrProcessed;
    @Inject
            SessionBean sessionBeanLocal;
    @Inject
            WorkFlow workflow;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Decl;aration">
    @EJB
            HrCandidateSelectedBeanLocal hrCandidateSelectedBeanLocal;
    @EJB
            HrLuEducLevelsBeanLocal hrLuEducLevelsBeanLocal;
    @EJB
            WfHrProcessedBeanLocal workFlowBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variable declaration">
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean renderPnlSearch = true;
    private boolean renderPnlManPage = false;
    private String SaveOrUpdateButton = "Save";
    private final String advertType = "Outside";
    private int selectedRowIndex = -1;
    private boolean approved;
    private String selectedJob = null;
    private String sexCriteria;
    private int minAgeCriteria;
    private int maxAgeCriteria;
    private Float educResultCriteria;
    private int numCandidate;
    private int minExpCriteria;
    private int maxExpCriteria;
    private String sql = "";
    private int noOfCandidates;
    private int minExp;
    private int maxExp;
    private String vacancyNo;
    private String jobCode;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List and DataModel Declaration">
    List<HrAdvertisements> bachCodes = new ArrayList<>();
    List<HrAdvertisedJobs> advertisedJobs = new ArrayList<>();
    List<HrLuEducLevels> educLevels;
    List<ShortlistCandidateTable> shortListCandidates = new ArrayList<>();
    DataModel<HrAdvertisedJobs> hrAdvertisedJobsDataModel = new ListDataModel<>();
    DataModel<ShortlistCandidateTable> shortlistCandidateDataModel = new ListDataModel<>();
    DataModel<WfHrProcessed> workflowDataModel = new ListDataModel<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Post Construct">
    public HrCandidateSelectedController() {
    }
    
    @PostConstruct
    public void init() {
        hrCandidiates = null;
        setBachCodes(hrCandidateSelectedBeanLocal.bachCodes(advertType));
        ckbRegistered.setSelected(true);
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrAdvertisements getHrAdvertisements() {
        if (hrAdvertisements == null) {
            hrAdvertisements = new HrAdvertisements();
        }
        return hrAdvertisements;
    }

    public void setHrAdvertisements(HrAdvertisements hrAdvertisements) {
        this.hrAdvertisements = hrAdvertisements;
    }

    public HrAdvertisedJobs getHrAdvertisedJobs() {
        if (hrAdvertisedJobs == null) {
            hrAdvertisedJobs = new HrAdvertisedJobs();
        }
        return hrAdvertisedJobs;
    }

    public void setHrAdvertisedJobs(HrAdvertisedJobs hrAdvertisedJobs) {
        this.hrAdvertisedJobs = hrAdvertisedJobs;
    }

    public HrCandidateSelected getHrCandidateSelected() {
        if (hrCandidateSelected == null) {
            hrCandidateSelected = new HrCandidateSelected();
        }
        return hrCandidateSelected;
    }

    public void setHrCandidateSelected(HrCandidateSelected hrCandidateSelected) {
        this.hrCandidateSelected = hrCandidateSelected;
    }

    public HrCandidiates getHrCandidiates() {
        if (hrCandidiates == null) {
            hrCandidiates = new HrCandidiates();
        }
        return hrCandidiates;
    }

    public void setHrCandidiates(HrCandidiates hrCandidiates) {
        this.hrCandidiates = hrCandidiates;
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

    public HrLuEducLevels getHrLuEducLevels() {
        if (hrLuEducLevels == null) {
            hrLuEducLevels = new HrLuEducLevels();
        }
        return hrLuEducLevels;
    }

    public void setHrLuEducLevels(HrLuEducLevels hrLuEducLevels) {
        this.hrLuEducLevels = hrLuEducLevels;
    }

    public WfHrProcessed getWfHrProcessed() {
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public DataModel<WfHrProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfHrProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public String getSelectedJob() {
        return selectedJob;
    }

    public void setSelectedJob(String selectedJob) {
        this.selectedJob = selectedJob;
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

    public boolean isRenderPnlSearch() {
        return renderPnlSearch;
    }

    public void setRenderPnlSearch(boolean renderPnlSearch) {
        this.renderPnlSearch = renderPnlSearch;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public DataModel<ShortlistCandidateTable> getShortlistCandidateDataModel() {
        return shortlistCandidateDataModel;
    }

    public void setShortlistCandidateDataModel(DataModel<ShortlistCandidateTable> shortlistCandidateDataModel) {
        this.shortlistCandidateDataModel = shortlistCandidateDataModel;
    }

    public DataModel<HrAdvertisedJobs> getHrAdvertisedJobsDataModel() {
        return hrAdvertisedJobsDataModel;
    }

    public void setHrAdvertisedJobsDataModel(DataModel<HrAdvertisedJobs> hrAdvertisedJobsDataModel) {
        this.hrAdvertisedJobsDataModel = hrAdvertisedJobsDataModel;
    }

    public List<HrAdvertisements> getBachCodes() {
        return bachCodes;
    }

    public void setBachCodes(List<HrAdvertisements> bachCodes) {
        this.bachCodes = bachCodes;
    }

    public List<HrAdvertisedJobs> getAdvertisedJobs() {
        return advertisedJobs;
    }

    public void setAdvertisedJobs(List<HrAdvertisedJobs> advertisedJobs) {
        this.advertisedJobs = advertisedJobs;
    }

    public List<ShortlistCandidateTable> getShortListCandidates() {
        return shortListCandidates;
    }

    public void setShortListCandidates(List<ShortlistCandidateTable> shortListCandidates) {
        this.shortListCandidates = shortListCandidates;
    }

    public List<HrLuEducLevels> getEducLevels() {
        return hrLuEducLevelsBeanLocal.findAll();
    }

    public void setEducLevels(List<HrLuEducLevels> educLevels) {
        this.educLevels = educLevels;
    }

    public String getSexCriteria() {
        return sexCriteria;
    }

    public void setSexCriteria(String sexCriteria) {
        this.sexCriteria = sexCriteria;
    }

    public int getMinAgeCriteria() {
        return minAgeCriteria;
    }

    public void setMinAgeCriteria(int minAgeCriteria) {
        this.minAgeCriteria = minAgeCriteria;
    }

    public int getMaxAgeCriteria() {
        return maxAgeCriteria;
    }

    public void setMaxAgeCriteria(int maxAgeCriteria) {
        this.maxAgeCriteria = maxAgeCriteria;
    }

    public int getMinExpCriteria() {
        return minExpCriteria;
    }

    public void setMinExpCriteria(int minExpCriteria) {
        this.minExpCriteria = minExpCriteria;
    }

    public int getMaxExpCriteria() {
        return maxExpCriteria;
    }

    public void setMaxExpCriteria(int maxExpCriteria) {
        this.maxExpCriteria = maxExpCriteria;
    }

    public Float getEducResultCriteria() {
        return educResultCriteria;
    }

    public void setEducResultCriteria(Float educResultCriteria) {
        this.educResultCriteria = educResultCriteria;
    }

    public int getNumCandidate() {
        return numCandidate;
    }

    public void setNumCandidate(int numCandidate) {
        this.numCandidate = numCandidate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="component binding">
    private HtmlSelectBooleanCheckbox ckbRegistered = new HtmlSelectBooleanCheckbox();

    public HtmlSelectBooleanCheckbox getCkbRegistered() {
        return ckbRegistered;
    }

    public void setCkbRegistered(HtmlSelectBooleanCheckbox ckbRegistered) {
        this.ckbRegistered = ckbRegistered;
    }
    private HtmlSelectBooleanCheckbox ckbToBeShortlisted = new HtmlSelectBooleanCheckbox();

    public HtmlSelectBooleanCheckbox getCkbToBeShortlisted() {
        return ckbToBeShortlisted;
    }

    public void setCkbToBeShortlisted(HtmlSelectBooleanCheckbox ckbToBeShortlisted) {
        this.ckbToBeShortlisted = ckbToBeShortlisted;
    }
    private HtmlSelectBooleanCheckbox ckbShortlisted = new HtmlSelectBooleanCheckbox();

    public HtmlSelectBooleanCheckbox getCkbShortlisted() {
        return ckbShortlisted;
    }

    public void setCkbShortlisted(HtmlSelectBooleanCheckbox ckbShortlisted) {
        this.ckbShortlisted = ckbShortlisted;
    }
    private boolean ckbRegisteredSelected;

    public boolean isCkbRegisteredSelected() {
        return ckbRegisteredSelected;
    }

    public void setCkbRegisteredSelected(boolean ckbRegisteredSelected) {
        this.ckbRegisteredSelected = ckbRegisteredSelected;
    }
    private boolean ckbToBeShortlistedSelected;

    public boolean isCkbToBeShortlistedSelected() {
        return ckbToBeShortlistedSelected;
    }

    public void setCkbToBeShortlistedSelected(boolean ckbToBeShortlistedSelected) {
        this.ckbToBeShortlistedSelected = ckbToBeShortlistedSelected;
    }
    private boolean ckbShortlistedSelected;

    public boolean isCkbShortlistedSelected() {
        return ckbShortlistedSelected;
    }

    public void setCkbShortlistedSelected(boolean ckbShortlistedSelected) {
        this.ckbShortlistedSelected = ckbShortlistedSelected;
    }
    private HtmlSelectBooleanCheckbox ckbApproved = new HtmlSelectBooleanCheckbox();

    public HtmlSelectBooleanCheckbox getCkbApproved() {
        return ckbApproved;
    }

    public void setCkbApproved(HtmlSelectBooleanCheckbox ckbApproved) {
        this.ckbApproved = ckbApproved;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inner class">
    public class ShortlistCandidateTable implements Serializable {

        private Integer candidateId;
        private String fullName;
        private String sex;
        private int age;
        private int exp;
        private String expDesc;
        private String educQual;
        private String educRank;
        private String result;
        private String resultType;
        private String resultLabel;
        private int status;
        private String statusLabel;
        private String slBy;
        private String slOn;
        private String slRemark;
        private String slRecommendation;
        private String slApprovedBy;
        private String slApprovedOn;
        private boolean approved;
        private String vacancyNo;
        private String jobCode;

        //<editor-fold defaultstate="collapsed" desc="getters & setters">
        public boolean isApproved() {
            return approved;
        }

        public void setApproved(boolean approved) {
            this.approved = approved;
        }

        public Integer getCandidateId() {
            return candidateId;
        }

        public void setCandidateId(Integer candidateId) {
            this.candidateId = candidateId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getExp() {
            return exp;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }

        public String getExpDesc() {
            return expDesc;
        }

        public void setExpDesc(String expDesc) {
            this.expDesc = expDesc;
        }

        public String getEducQual() {
            return educQual;
        }

        public void setEducQual(String educQual) {
            this.educQual = educQual;
        }

        public String getEducRank() {
            return educRank;
        }

        public void setEducRank(String educRank) {
            this.educRank = educRank;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getResultType() {
            return resultType;
        }

        public void setResultType(String resultType) {
            this.resultType = resultType;
        }

        public String getResultLabel() {
            return resultLabel;
        }

        public void setResultLabel(String resultLabel) {
            this.resultLabel = resultLabel;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusLabel() {
            return statusLabel;
        }

        public void setStatusLabel(String statusLabel) {
            this.statusLabel = statusLabel;
        }

        public String getSlBy() {
            return slBy;
        }

        public void setSlBy(String slBy) {
            this.slBy = slBy;
        }

        public String getSlOn() {
            return slOn;
        }

        public void setSlOn(String slOn) {
            this.slOn = slOn;
        }

        public String getSlRemark() {
            return slRemark;
        }

        public void setSlRemark(String slRemark) {
            this.slRemark = slRemark;
        }

        public String getSlRecommendation() {
            return slRecommendation;
        }

        public void setSlRecommendation(String slRecommendation) {
            this.slRecommendation = slRecommendation;
        }

        public String getSlApprovedBy() {
            return slApprovedBy;
        }

        public void setSlApprovedBy(String slApprovedBy) {
            this.slApprovedBy = slApprovedBy;
        }

        public String getSlApprovedOn() {
            return slApprovedOn;
        }

        public void setSlApprovedOn(String slApprovedOn) {
            this.slApprovedOn = slApprovedOn;
        }

        public String getVacancyNo() {
            return vacancyNo;
        }

        public void setVacancyNo(String vacancyNo) {
            this.vacancyNo = vacancyNo;
        }

        public String getJobCode() {
            return jobCode;
        }

        public void setJobCode(String jobCode) {
            this.jobCode = jobCode;
        }
//</editor-fold>

        public ShortlistCandidateTable(Integer candidateId, String fullName,
                String sex, int age, int exp, String expDesc,
                String educQual, String educRank, String result,
                String resultType, int status, String statusLabel,
                String slBy, String slOn, String slRemark,
                String slRecommendation, String slApprovedBy,
                String slApprovedOn, String vacancyNo,
                String jobCode) {
            this.candidateId = candidateId;
            this.fullName = fullName;
            this.sex = sex;
            this.age = age;
            this.exp = exp;
            this.expDesc = expDesc;
            this.educQual = educQual;
            this.educRank = educRank;
            this.result = result;
            this.resultType = resultType;
            this.resultLabel = result + "--" + resultType;
            this.status = status;
            this.statusLabel = statusLabel;
            this.slBy = slBy;
            this.slOn = slOn;
            this.slRemark = slRemark;
            this.slRecommendation = slRecommendation;
            this.slApprovedBy = slApprovedBy;
            this.slApprovedOn = slApprovedOn;
            this.vacancyNo = vacancyNo;
            this.jobCode = jobCode;
        }

    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="main">
    public void btnSearch() {
        renderPnlSearch = true;
        renderPnlManPage = false;
        JsfUtil.addInformationMessage("Load table and populate");
    }

    public void createNewGatePassInfo() {
        SaveOrUpdateButton = "Save";
        switch (createOrSearchBundle) {
            case "New":
                renderPnlSearch = false;
                renderPnlManPage = true;
                createOrSearchBundle = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                renderPnlSearch = true;
                renderPnlManPage = false;
                createOrSearchBundle = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void bachCode_vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            setAdvertisedJobs(hrCandidateSelectedBeanLocal.advertizedJobs(Integer.valueOf(event.getNewValue().toString())));
            recreatehrAdvertisedJobsDataModel();
        }
    }

    public void recreatehrAdvertisedJobsDataModel() {
        hrAdvertisedJobsDataModel = null;
        hrAdvertisedJobsDataModel = new ListDataModel(new ArrayList(advertisedJobs));
    }

    public void recreateDataModel(List<ShortlistCandidateTable> shortListCandidates) {
        shortlistCandidateDataModel = null;
        shortlistCandidateDataModel = new ListDataModel(new ArrayList<>(shortListCandidates));
    }

    public void advertizedJobs(SelectEvent event) {
        if (event.getObject() != null) {
            renderPnlManPage = true;
            renderPnlSearch = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            setIcone("ui-icon-search");
            hrAdvertisedJobs = (HrAdvertisedJobs) event.getObject();
            setSelectedJob(hrAdvertisedJobs.getId().toString());
//            setShortListDisplayCriteria();
            loadShortListCandidate();
        } else {
            setSelectedJob(null);
        }
    }

    public void sex_vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            setSexCriteria(event.getNewValue().toString());
        }
    }

    public void educLevel_vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            setSexCriteria(event.getNewValue().toString());
        }
    }

    public int calculateCandidateAgeInYears(String dateOfBirth) {
        String today = StringDateManipulation.toDayInEc();
        int candidateAgeInDays = StringDateManipulation.datesDifferenceInDays(today, dateOfBirth);
        int candidateAgeInYears = candidateAgeInDays / 365;
//        int months = (employeeAgeInDays % 365) / 30;
//        candidateAgeInYears = (months >= 6) ? candidateAgeInYears + 1 : candidateAgeInYears;
        return candidateAgeInYears;
    }

    public void ckbRegistered_vcl() {
        if (ckbRegistered.isSelected()) {
            ckbShortlisted.setSelected(false);
        }
    }

    public void ckbToBeShortlisted_vcl() {
        if (ckbToBeShortlisted.isSelected()) {
            ckbShortlisted.setSelected(false);
        }
    }

    public void ckbShortListed_vcl() {
        if (ckbShortlisted.isSelected()) {
            ckbToBeShortlisted.setSelected(false);
            ckbRegistered.setSelected(false);
        }
    }
    private List<Object[]> candidiates;

    public List<Object[]> getCandidiates() {
        return candidiates;
    }

    public void setCandidiates(List<Object[]> candidiates) {
        this.candidiates = candidiates;
    }

    public String dateFormat(String date) {
        String[] dateFromUI;
        String calcDateOfBirth;
        if (date != null && date.contains("/")) {
            dateFromUI = date.split("/");
            calcDateOfBirth = dateFromUI[2] + "-" + dateFromUI[1] + "-" + dateFromUI[0];
            return calcDateOfBirth;
        }
        return null;
    }

    private void loadShortListCandidate() {
        try {
            shortListCandidates.clear();
//            if (ddnJobList.getValue() != null) {
            String statusLabel = "Shortlisted";
            String prevCandidateId = "";
            String advertJobId = getSelectedJob();
            setShortListDisplayCriteria();
            if (ckbRegistered.isSelected() && ckbToBeShortlisted.isSelected()) {
                sql += "  AND (HR_CANDIDIATES.STATUS = " + HrCandidiates.REGISTERED
                        + " OR HR_CANDIDIATES.STATUS = " + HrCandidiates.SHORTLIST_REQUEST + " )";
            } else if (ckbRegistered.isSelected()) {
                sql += "  AND HR_CANDIDIATES.STATUS = " + HrCandidiates.REGISTERED;
            } else if (ckbToBeShortlisted.isSelected()) {
                sql += "  AND HR_CANDIDIATES.STATUS = " + HrCandidiates.SHORTLIST_REQUEST;
            } else if (ckbShortlisted.isSelected()) {
                sql += "  AND HR_CANDIDIATES.STATUS >= " + HrCandidiates.SHORTLISTED;
            }
//            System.out.println(sql);
            setCandidiates(hrCandidateSelectedBeanLocal.readCandidiates(advertJobId, sql));
//            System.out.println("size of a table " + candidiates.size());

            if (!getCandidiates().isEmpty() && getCandidiates().size() > 0) {
                int counter = 0;
                for (Object[] candidate : getCandidiates()) {
                    boolean indicator = true;
                    // Integer experience = Integer.valueOf(hrCandidiate.getEmail());
//                    if (noOfCandidates > 0 && counter >= noOfCandidates) {
//                        break;
//                    }
//                    if (minExp > 0 && maxExp > 0) {
//                        if (experience < minExp && experience > maxExp) {
//                            indicator = false;
//                        }
//                    } else if (minExp > 0) {
//                        if (experience < minExp) {
//                            indicator = false;
//                        }
//                    } else if (maxExp > 0) {
//                        if (experience > maxExp) {
//                            indicator = false;
//                        }
//                    }
                    if (indicator) {
                        int status = Integer.valueOf(candidate[2].toString());
                        String candidateId = candidate[0].toString();
                        statusLabel = (status == HrCandidiates.REGISTERED) ? "Registered" : (status == HrCandidiates.SHORTLIST_REQUEST) ? "Shortlisted" : "Approved";

                        ShortlistCandidateTable addToTable = new ShortlistCandidateTable(
                                Integer.valueOf(candidate[0].toString()),//candidateId
                                candidate[1].toString(),//full name
                                candidate[3].toString(),//sex
                                calculateCandidateAgeInYears(dateFormat(candidate[4].toString())),//age
                                candidate[11] != null ? Integer.valueOf(candidate[11].toString()) : null,//experience
                                candidate[14] != null ? String.valueOf(candidate[14].toString()) : null,//experience Desc
                                candidate[13] != null ? String.valueOf(candidate[13].toString()) : null,//educ qual
                                candidate[12] != null ? String.valueOf(candidate[12].toString()) : null,//educ rank
                                candidate[15] != null ? String.valueOf(candidate[15].toString()) : null,//result
                                "",//result type
                                status,//status
                                statusLabel,//status label
                                candidate[5] != null ? String.valueOf(candidate[5].toString()) : null,//slBy
                                candidate[6] != null ? String.valueOf(candidate[6].toString()) : null,//slOn
                                candidate[8] != null ? String.valueOf(candidate[8].toString()) : null,//slRemark
                                candidate[7] != null ? String.valueOf(candidate[7].toString()) : null,//slRecommendation
                                candidate[9] != null ? String.valueOf(candidate[9].toString()) : null,//slApprovedBy
                                candidate[10] != null ? String.valueOf(candidate[10].toString()) : null,//slApprovedOn
                                candidate[16] != null ? String.valueOf(candidate[16].toString()) : null,//vacancyNo
                                candidate[17] != null ? String.valueOf(candidate[17].toString()) : null);//jobCode
                        shortListCandidates.add(addToTable);
                        if (!prevCandidateId.equalsIgnoreCase(candidateId)) {
                            prevCandidateId = candidateId;
                            counter++;
                        }
                    }
                }
                recreateDataModel(getShortListCandidates());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void ckbApproved_vcl(ValueChangeEvent vce) {
        selectedRowIndex = getShortlistCandidateDataModel().getRowIndex();
        getShortListCandidates().get(selectedRowIndex).setApproved(true);
    }

    private boolean disableSubmit;

    public boolean isDisableSubmit() {
        return disableSubmit;
    }

    public void setDisableSubmit(boolean disableSubmit) {
        this.disableSubmit = disableSubmit;
    }

    public void rowSelector() {
        selectedRowIndex = getShortlistCandidateDataModel().getRowIndex();
        if (selectedRowIndex > -1) {
            int status = getShortListCandidates().get(selectedRowIndex).getStatus();
            getShortListCandidates().get(selectedRowIndex).setApproved(true);
            if (status == HrCandidiates.REGISTERED) {
                setDisableSubmit(false);
            } else if (status == HrCandidiates.SHORTLISTED
                    || status == HrCandidiates.SELECTED_FOR_RECRUITMENT
                    || status == HrCandidiates.REGISTERED_EMPLOYEE) {
                setDisableSubmit(true);
            }
        }
    }

    private List<HrCandidateSelected> selectedCandidates(int prevStatus, int currStatus) {
        try {
            List<HrCandidateSelected> selectedCandidates = new ArrayList<>();
            if (getShortListCandidates().size() > 0) {
                for (ShortlistCandidateTable candidate : getShortListCandidates()) {
                    if (candidate.isApproved() && candidate.getStatus() == prevStatus) {
                        HrCandidateSelected candidateList = new HrCandidateSelected();
                        candidateList.setShortlistRecommendation(hrCandidateSelected.getShortlistRecommendation());
                        candidateList.setShortlistRemark(hrCandidateSelected.getShortlistRemark());
                        candidateList.setShortlistApprovedBy(String.valueOf(sessionBeanLocal.getUserId()));
                        candidateList.setShortlistApprovedOn(StringDateManipulation.toDayInEc());
                        candidateList.setStatus(currStatus);
                        candidateList.setCandidateId(BigDecimal.valueOf(candidate.getCandidateId()));
                        selectedCandidates.add(candidateList);
                    }
                }
            }
            return selectedCandidates;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void saveShortlistCandidates() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "saveShortlistCandidates", dataset)) {

            int update = hrCandidateSelectedBeanLocal.shortListCandidates(selectedCandidates(HrCandidiates.REGISTERED, HrCandidiates.SHORTLISTED));//HrCandidiates.SHORTLISTED will be corrected by HrCandidiates.SHORTLIST_REQUEST
            if (workflow.isPrepareStatus()) {
                if (update > 0) {
                    loadShortListCandidate();
                    JsfUtil.addSuccessMessage("Selected candidates' list for shortlisting is submited successfully!");
                } else {
                    JsfUtil.addFatalMessage("Error occurs while submitting selected candidates' list for shortlisting");
                }
            } else if (workflow.isApproveStatus() || workflow.isCheckStatus()) {
                if (update > 0) {
                    loadShortListCandidate();
                    JsfUtil.addSuccessMessage("Selected candidates' list for shortlisting is approved successfully!");
                } else {
                    JsfUtil.addFatalMessage("Error occurs while submitting selected candidates' list for shortlisting");
                }
            }

        } else {
            JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(sessionBeanLocal.getSessionID());
            eventEntry.setUserId(sessionBeanLocal.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
            eventEntry.setUserLogin(userName);
            JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
            eventEntry.setModule(module);
            JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveShortlistCandidates");
            eventEntry.setProgram(program);
            security.addEventLog(eventEntry, dataset);
        }
    }
    
    public void workflowDataModel() {
        workflowDataModel = null;
//        for (int i = 0; i < hrCandidateSelected.getWfHrProcessedList().size(); i++) {
//            if (hrCandidateSelected.getWfHrProcessedList().get(i).getDecision() == 0) {
//                hrCandidateSelected.getWfHrProcessedList().get(i).setChangedStstus("Request");
//            } else if (hrCandidateSelected.getWfHrProcessedList().get(i).getDecision() == 1 || hrCandidateSelected.getWfHrProcessedList().get(i).getDecision() == 3) {
//                hrCandidateSelected.getWfHrProcessedList().get(i).setChangedStstus("Approved");
//            } else if (hrCandidateSelected.getWfHrProcessedList().get(i).getDecision() == 2 || hrCandidateSelected.getWfHrProcessedList().get(i).getDecision() == 4) {
//                hrCandidateSelected.getWfHrProcessedList().get(i).setChangedStstus("Rejected");
//            }
//        }
        workflowDataModel = new ListDataModel(new ArrayList(hrCandidateSelected.getWfHrProcessedList()));
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="shortlist criteria popup">
    public String setShortListDisplayCriteria() {
        String todayInEC = StringDateManipulation.toDayInEc();
        boolean result = true;
        sql = "";
        minExp = 0;
        maxExp = 0;
        noOfCandidates = 0;
        if (sexCriteria != null) {
            sql += "  AND  HR_CANDIDIATES.SEX = '" + getSexCriteria() + "'";
        }
        if (hrLuEducLevels.getRank() != null && !hrLuEducLevels.getRank().isEmpty()) {
            sql += "  AND  TO_NUMBER(HR_LU_EDUC_LEVELS.RANK) >= '" + hrLuEducLevels.getRank() + "'";
        }

        Float numGPA = getEducResultCriteria();
        if (!String.valueOf(numGPA).isEmpty() && numGPA != null) {
            String cgpa = String.valueOf(numGPA);
            sql += "  AND  TO_NUMBER(HR_CANDIDIATE_EDUCATIONS.EDUC_RESULT) >= " + cgpa + "";
        }
//        else {
//            result = false;
//            JsfUtil.addSuccessMessage("Education result must not be zero");
//        }

        int numMinAge = getMinAgeCriteria();
        if (!String.valueOf(numMinAge).isEmpty() && numMinAge != 0) {
            String minAge = String.valueOf(numMinAge);
            sql += " AND  (TO_NUMBER(SUBSTR('" + todayInEC + "',1,4)) - TO_NUMBER(SUBSTR(DATE_OF_BIRTH, 1,4)) >= " + minAge + ") ";
        }
//        else {
//            result = false;
//            JsfUtil.addSuccessMessage("Minimum age must not be zero");
//        }

        int numMaxAge = getMaxAgeCriteria();
        if (!String.valueOf(numMaxAge).isEmpty() && numMaxAge != 0) {
            String maxAge = String.valueOf(numMaxAge);
            sql += " AND  (TO_NUMBER(SUBSTR('" + todayInEC + "',1,4)) - TO_NUMBER(SUBSTR(DATE_OF_BIRTH, 1,4)) <= " + maxAge + ") ";
        }
//        else {
//            result = false;
//            JsfUtil.addSuccessMessage("Maximum age must not be zero");
//        }

        int numCan = numCandidate;
        if (!String.valueOf(numCan).isEmpty() && numCan != 0) {
            noOfCandidates = numCan;
        }
//        else {
//            result = false;
//            JsfUtil.addSuccessMessage("No of Candidate must not be zero");
//        }

        int numMinExp = getMinExpCriteria();
        if (!String.valueOf(numMinExp).isEmpty() && numMinExp != 0) {
            minExp = numMinExp;
        }
//        else {
//            result = false;
//            JsfUtil.addSuccessMessage("Minimum experience must not be zero");
//        }

        int numMaxExp = getMaxExpCriteria();
        if (!String.valueOf(numMaxExp).isEmpty() && numMaxExp != 0) {
            maxExp = numMaxExp;
        }
//        else {
//            result = false;
//            JsfUtil.addSuccessMessage("Minimum experience must not be zero");
//        }
        if (!result) {
            //    sql = " order by CANDIDATE_ID ";
            minExp = 0;
            maxExp = 0;
            noOfCandidates = 0;
        }
        return null;
    }

    public void btnSubmitDisplayCriteria_action() {
        setShortListDisplayCriteria();
        loadShortListCandidate();
    }

    public void btnResetDisplayCriteria_action() {
        sql = "";
        loadShortListCandidate();
        minExp = 0;
        maxExp = 0;
        noOfCandidates = 0;
    }
    // </editor-fold>
}
