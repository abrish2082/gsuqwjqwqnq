/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.organization;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.lookup.HrLuGradesBeanLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuJobLevelsBeanLocal;
import et.gov.eep.hrms.businessLogic.lookup.HrLuSalaryStepsBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.PayGradeBeanLocal;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.lookup.HrLuSalarySteps;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
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
 * @author Ob
 */
@Named(value = "payGradeController")
@ViewScoped
public class PayGradeController implements Serializable {

    @Inject
    HrSalaryScaleRanges hrSalaryScaleRanges;
    @Inject
    HrSalaryScales hrSalaryScales;
    @Inject
    HrLuSalarySteps hrLuSalarySteps;
    @Inject
    HrLuJobLevels hrLuJobLevel;
    @Inject
    HrLuGrades hrLuGrades;
    @Inject
    SessionBean SessionBean;

    @EJB
    PayGradeBeanLocal payGradeBeanLocal;
    @EJB
    HrLuGradesBeanLocal gradeBeanLocal;
    @EJB
    HrLuSalaryStepsBeanLocal salaryStepsBeanLocal;
    @EJB
    HrLuJobLevelsBeanLocal jobLevelBeanLocal;
    @EJB
    HrLuSalaryStepsBeanLocal hrLuSalaryStepsBeanLocal;

    DataModel<HrSalaryScales> payGradeDataModel;
    private List<HrLuSalarySteps> listOfSalarySteps;
    private List<HrLuJobLevels> listOfJobLevel;
    DecimalFormat decimalFormat = new DecimalFormat("##.##");

    int updateStatus = 0;
    int popupUpdateStatus = 0;
    private String saveorUpdate = "Save";
    private String popupSaveOrUpdate = "Save";
    private String AddOrModify="Add";
    private String salaryStep;
    private String typeOfTransaction;
    private double minSalary;
    Set<String> setpSet = new HashSet<String>();
    Set<String> checkGrade = new HashSet<>();

    public PayGradeController() {

    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrSalaryScaleRanges getHrSalaryScaleRanges() {
        if (hrSalaryScaleRanges == null) {
            hrSalaryScaleRanges = new HrSalaryScaleRanges();
        }
        return hrSalaryScaleRanges;
    }

    public void setHrSalaryScaleRanges(HrSalaryScaleRanges hrSalaryScaleRanges) {
        this.hrSalaryScaleRanges = hrSalaryScaleRanges;
    }

    public String getAddOrModify() {
        return AddOrModify;
    }

    public void setAddOrModify(String AddOrModify) {
        this.AddOrModify = AddOrModify;
    }

    
    public HrSalaryScales getHrSalaryScales() {
        if (hrSalaryScales == null) {
            hrSalaryScales = new HrSalaryScales();
        }
        return hrSalaryScales;
    }

    public void setHrSalaryScales(HrSalaryScales hrSalaryScales) {
        this.hrSalaryScales = hrSalaryScales;
    }

    public HrLuSalarySteps getHrLuSalarySteps() {
        if (hrLuSalarySteps == null) {
            hrLuSalarySteps = new HrLuSalarySteps();
        }
        return hrLuSalarySteps;
    }

    public void setHrLuSalarySteps(HrLuSalarySteps hrLuSalarySteps) {
        this.hrLuSalarySteps = hrLuSalarySteps;
    }

    public HrLuJobLevels getHrLuJobLevel() {
        if (hrLuJobLevel == null) {
            hrLuJobLevel = new HrLuJobLevels();
        }
        return hrLuJobLevel;
    }

    public void setHrLuJobLevel(HrLuJobLevels hrLuJobLevel) {
        this.hrLuJobLevel = hrLuJobLevel;
    }

    public HrLuGrades getHrLuGrades() {
        return hrLuGrades;
    }

    public void setHrLuGrades(HrLuGrades hrLuGrades) {
        this.hrLuGrades = hrLuGrades;
    }

    public DataModel<HrSalaryScales> getPayGradeDataModel() {
        if (payGradeDataModel == null) {
            payGradeDataModel = new ListDataModel<>();
        }
        return payGradeDataModel;
    }

    public void setPayGradeDataModel(DataModel<HrSalaryScales> payGradeDataModel) {
        this.payGradeDataModel = payGradeDataModel;
    }

    public List<HrLuSalarySteps> getListOfSalarySteps() {
        listOfSalarySteps = salaryStepsBeanLocal.findAll();
        return listOfSalarySteps;
    }

    public void setListOfSalarySteps(List<HrLuSalarySteps> listOfSalarySteps) {
        this.listOfSalarySteps = listOfSalarySteps;
    }

    public List<HrLuJobLevels> getListOfJobLevel() {
        listOfJobLevel = jobLevelBeanLocal.findAll();
        return listOfJobLevel;
    }

    public void setListOfJobLevel(List<HrLuJobLevels> listOfJobLevel) {
        this.listOfJobLevel = listOfJobLevel;
    }

    public String getSaveorUpdate() {
        return saveorUpdate;
    }

    public void setSaveorUpdate(String saveorUpdate) {
        this.saveorUpdate = saveorUpdate;
    }

    public String getPopupSaveOrUpdate() {
        return popupSaveOrUpdate;
    }

    public void setPopupSaveOrUpdate(String popupSaveOrUpdate) {
        this.popupSaveOrUpdate = popupSaveOrUpdate;
    }

    public String getSalaryStep() {
        return salaryStep;
    }

    public void setSalaryStep(String salaryStep) {
        this.salaryStep = salaryStep;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public List<HrLuGrades> getListOfGrades() {
        return gradeBeanLocal.findAll();
    }

    public List<HrLuJobLevels> getListOfJobLevels() {
        return jobLevelBeanLocal.findAll();
    }

    public double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
    }
    //</editor-fold>

    public void grade_vlc(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            if (hrSalaryScaleRanges != null) {
                hrLuGrades = (HrLuGrades) event.getNewValue();
                hrSalaryScaleRanges = payGradeBeanLocal.findByGrade(hrLuGrades);
                hrLuJobLevel = hrSalaryScaleRanges.getLevelId();
                recreateDataModel();
            }
        }
    }

    public void handleGrade(ValueChangeEvent event) {
        try {
            if (event.getNewValue().toString() != null) {
                hrLuGrades = (HrLuGrades) event.getNewValue();
                hrSalaryScaleRanges = payGradeBeanLocal.findByGrade(hrLuGrades);
                if (hrSalaryScaleRanges == null) {
                    hrSalaryScaleRanges = new HrSalaryScaleRanges();
                    hrLuJobLevel = hrSalaryScaleRanges.getLevelId();
                    payGradeDataModel = null;
                    setpSet.clear();
                    payGradeDataModel = new ListDataModel(new ArrayList<>(hrSalaryScaleRanges.getHrSalaryScalesList()));
                } else {
                    minSalary = hrSalaryScaleRanges.getMinSalary();
                    updateStatus = 1;
                    saveorUpdate = "Update";
                }
                setpSet.clear();
                hrLuJobLevel = hrSalaryScaleRanges.getLevelId();
                recreateDataModel();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void vlc_minSalary(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            hrSalaryScaleRanges.setMinSalary(Double.parseDouble(vce.getNewValue().toString()));
        }
    }

    public void vcl_incrementRate(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrSalaryScaleRanges.setIncrementRate(Float.parseFloat(event.getNewValue().toString()));
        }
    }

    public void recreateDataModel() {
        payGradeDataModel = null;
        payGradeDataModel = new ListDataModel(new ArrayList<>(hrSalaryScaleRanges.getHrSalaryScalesList()));
        for (int i = 0; i < hrSalaryScaleRanges.getHrSalaryScalesList().size(); i++) {
            setpSet.add(hrSalaryScaleRanges.getHrSalaryScalesList().get(i).getSalaryStepId().toString());
        }
    }

    public void addPayGrade() {
        hrSalaryScales.setSalaryStepId(hrLuSalarySteps);
        System.out.println("hrSalaryScales.getSalary()=="+hrSalaryScales.getSalary());
        System.out.println("hrSalaryScales.getSalaryRangeId().getMinSalary()="+hrSalaryScales.getSalaryRangeId().getMinSalary());
        System.out.println("hrSalaryScales.getSalaryRangeId().getMaxSalary()="+hrSalaryScales.getSalaryRangeId().getMaxSalary());
        if (hrSalaryScales.getSalary() >= hrSalaryScales.getSalaryRangeId().getMaxSalary() && hrSalaryScales.getSalary() <= hrSalaryScales.getSalaryRangeId().getMinSalary()) {
            JsfUtil.addFatalMessage("The Salary Value should be between the Minimum & Maximum Salary");
        } else {
            if (!setpSet.contains(hrSalaryScales.getSalaryStepId().toString())) {
                getHrSalaryScaleRanges().addPayGrade(hrSalaryScales);
                hrSalaryScales.setSalaryStepId(hrLuSalarySteps);
                recreateDataModel();
                setpSet.add(hrSalaryScales.getSalaryStepId().toString());
                hrSalaryScales = null;
                hrLuSalarySteps = null;
                AddOrModify="Add";
            } else {
                JsfUtil.addFatalMessage("Duplicate entry salary step");
            }
        }
    }

    int selectedRow;

    public void editDataTable() {
        hrSalaryScales = payGradeDataModel.getRowData();
        setpSet.remove(hrSalaryScales.getSalaryStepId().toString());
        hrLuSalarySteps = hrSalaryScales.getSalaryStepId();
        AddOrModify="Modify";
    }

    double RoundUp(double toRound) {
        if (toRound % 10 == 0) {
            return toRound;
        }
        return (10 - toRound % 10) + toRound;
    }

    public void vlc_salaryStep(ValueChangeEvent vce) {
//        if (hrSalaryScaleRanges.getIncrementRate() != 0.0) {
        if (vce.getNewValue() != null) {
            hrLuSalarySteps.setSalaryStep(Integer.valueOf(vce.getNewValue().toString()));
//                if (hrLuSalarySteps.getSalaryStep()==1) {
//                    Double doubleVal = Double.parseDouble(decimalFormat.format(hrSalaryScaleRanges.getMinSalary() + (hrSalaryScaleRanges.getMinSalary() * hrSalaryScaleRanges.getIncrementRate())));
//                    hrSalaryScales.setSalary((RoundUp(doubleVal)));
//                } else {
//                    int step =(hrLuSalarySteps.getSalaryStep()) - 1;
//                    for (int i = 0; i < hrSalaryScaleRanges.getHrSalaryScalesList().size(); i++) {
//                        HrSalaryScales hrSalaryScalesList = hrSalaryScaleRanges.getHrSalaryScalesList().get(i);
//                        if (hrSalaryScalesList.getSalaryStepId().getSalaryStep()==step) {
//                            Double doubleVal = Double.parseDouble(decimalFormat.format(hrSalaryScalesList.getSalary() + (hrSalaryScalesList.getSalary() * hrSalaryScaleRanges.getIncrementRate())));
//                            hrSalaryScales.setSalary((RoundUp(doubleVal)));
//                        }
//                    }
//                }
        }
//        } else {
//            JsfUtil.addFatalMessage("Increment rate should not be zero please enter a new value");
//        }
    }

    public void savePayGrade() {
        if ((!(getPayGradeDataModel().getRowCount() > 0))) {
            JsfUtil.addFatalMessage("Data table shoud be filled");
        } else if (hrSalaryScaleRanges.getMinSalary() > hrSalaryScaleRanges.getMaxSalary()) {
            JsfUtil.addFatalMessage("Maximum salary should be greater than minimum salary.");
        } else {
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(SessionBean.getUserName(), "savePayGrade", dataset)) {
                hrSalaryScaleRanges.setGradeId(hrLuGrades);
                hrSalaryScaleRanges.setLevelId(hrLuJobLevel);
                if (updateStatus == 0) {
                    payGradeBeanLocal.create(hrSalaryScaleRanges);
                    JsfUtil.addSuccessMessage("Pay grade saved successfuly.");
                    clearPayGrade();
                } else {
                    payGradeBeanLocal.edit(hrSalaryScaleRanges);
                    clearPayGrade();
                    JsfUtil.addSuccessMessage("Pay grade updated successfuly.");
                }
                } else {
                    JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                    EventEntry eventEntry = new EventEntry();
                    eventEntry.setSessionId(SessionBean.getSessionID());
                    eventEntry.setUserId(SessionBean.getUserId());
                    QName qualifiedName = new QName("", "project");
                    JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                    eventEntry.setUserLogin(test);
                    security.addEventLog(eventEntry,dataset);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JsfUtil.addErrorMessage("Error opccured while saving data");
            }
        }
    }

    public void clearPayGrade() {
        hrSalaryScaleRanges = new HrSalaryScaleRanges();
        hrSalaryScales = new HrSalaryScales();
        minSalary = 0.0;
        hrLuJobLevel = new HrLuJobLevels();
        payGradeDataModel = new ListDataModel<>();
        setpSet.clear();
        updateStatus = 0;
        saveorUpdate = "Save";
    }

    //<editor-fold defaultstate="collapsed" desc="popup">
    public void saveGrade() {
        if (payGradeBeanLocal.getByGrade(hrLuGrades) == null) {
            hrLuGrades.getGrade();
            hrLuGrades.getDescription();
            if ((checkGrade.contains(hrLuGrades.getGrade()) && checkGrade.contains(hrLuGrades.getDescription()))) {
                JsfUtil.addFatalMessage("Grade " + hrLuGrades.getGrade() + "  is duplicated!. Try with another grade.");
            } else {
                try {
                    if (popupUpdateStatus == 0) {
                        payGradeBeanLocal.save(hrLuGrades);
                        JsfUtil.addSuccessMessage("Successfully saved");
                        hrLuGrades = new HrLuGrades();
                    } else {
                        payGradeBeanLocal.edit(hrLuGrades);
                        JsfUtil.addSuccessMessage("Successfully update");
                        hrLuGrades = new HrLuGrades();
                    }
                    payGradeBeanLocal.findall();
                    popupSaveOrUpdate = "Save";
                    popupUpdateStatus = 0;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            JsfUtil.addFatalMessage("Grade  " + hrLuGrades.getGrade() + "  is already registred. Try with another grade.");
        }
    }

    public ArrayList<HrLuGrades> searchByGrade(String grade) {
        ArrayList<HrLuGrades> gradeList = null;
        hrLuGrades.setGrade(grade);
        gradeList = payGradeBeanLocal.searchByGrade(hrLuGrades);
        popupSaveOrUpdate = "Update";
        popupUpdateStatus = 1;
        return gradeList;
    }

    public void getByGrade(SelectEvent event) {
        try {
            hrLuGrades.setGrade(event.getObject().toString());
            hrLuGrades = payGradeBeanLocal.getByGrade(hrLuGrades);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void btnCancel() {
        hrLuGrades = new HrLuGrades();
    }
    //</editor-fold>

}
