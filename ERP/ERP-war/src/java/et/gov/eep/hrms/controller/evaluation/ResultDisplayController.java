/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import et.gov.eep.hrms.businessLogic.evaluation.ResultDisplayBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationResultDetails;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationResults;
import et.gov.eep.hrms.entity.evaluation.HrEvaluationSessions;
import et.gov.eep.hrms.entity.organization.HrJobTypes;

/**
 *
 * @author INSA
 */
@Named(value = "resultDisplayController")
@ViewScoped
public class ResultDisplayController implements Serializable {

    @Inject
    HrEvaluationResults hrEvaluationResult;

    @Inject
    HrEvaluationSessions hrEvaluationSessions;

    @Inject
    HrEmployees hrEmployees;

    @Inject
    HrEmployees evalutor;

    @Inject
    HrJobTypes hrJobTypes;

    @EJB
    private ResultDisplayBeanLocal resultDisplayBeanLocal;

    String employeeName;
    String evaluatorEmployee;
    private String newOrSearch = "New";
    private String icone = "ui-icon-document";
    private boolean searchPage = true;
    private boolean newPage = false;
    DataModel<HrEvaluationResults> evaluationResultDataModel;
    DataModel<HrEvaluationResultDetails> resultListDataModel;
    private List<HrEvaluationResults> viewResult;

    public ResultDisplayController() {
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public HrEvaluationResults getHrEvaluationResult() {
        if (hrEvaluationResult == null) {
            hrEvaluationResult = new HrEvaluationResults();
        }
        return hrEvaluationResult;
    }

    public void setHrEvaluationResult(HrEvaluationResults hrEvaluationResult) {
        this.hrEvaluationResult = hrEvaluationResult;
    }

    public HrEvaluationSessions getHrEvaluationSessions() {
        return hrEvaluationSessions;
    }

    public void setHrEvaluationSessions(HrEvaluationSessions hrEvaluationSessions) {
        this.hrEvaluationSessions = hrEvaluationSessions;
    }

    public HrJobTypes getHrJobTypes() {
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
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

    public HrEmployees getEvalutor() {
        return evalutor;
    }

    public void setEvalutor(HrEmployees evalutor) {
        this.evalutor = evalutor;
    }

    public List<HrEvaluationResults> getViewResult() {
        if (viewResult == null) {
            viewResult = new ArrayList<>();
        }
        return viewResult;
    }

    public void setViewResult(List<HrEvaluationResults> viewResult) {
        this.viewResult = viewResult;
    }

    public DataModel<HrEvaluationResults> getEvaluationResultDataModel() {
        return evaluationResultDataModel;
    }

    public void setEvaluationResultDataModel(DataModel<HrEvaluationResults> evaluationResultDataModel) {
        this.evaluationResultDataModel = evaluationResultDataModel;
    }

    public DataModel<HrEvaluationResultDetails> getResultListDataModel() {
        if (resultListDataModel == null) {
            resultListDataModel = new ArrayDataModel<>();
        }
        return resultListDataModel;
    }

    public void setResultListDataModel(DataModel<HrEvaluationResultDetails> resultListDataModel) {
        this.resultListDataModel = resultListDataModel;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEvaluatorEmployee() {
        return evaluatorEmployee;
    }

    public void setEvaluatorEmployee(String evaluatorEmployee) {
        this.evaluatorEmployee = evaluatorEmployee;
    }

    public String getNewOrSearch() {
        return newOrSearch;
    }

    public void setNewOrSearch(String newOrSearch) {
        this.newOrSearch = newOrSearch;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isSearchPage() {
        return searchPage;
    }

    public void setSearchPage(boolean searchPage) {
        this.searchPage = searchPage;
    }

    public boolean isNewPage() {
        return newPage;
    }

    public void setNewPage(boolean newPage) {
        this.newPage = newPage;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main">
    public void resultInfo() {
        switch (newOrSearch) {
            case "New":
                searchPage = false;
                newPage = true;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                searchPage = true;
                newPage = false;
                newOrSearch = "New";
                setIcone("ui-icon-document");
                break;
        }
    }

    public void displayResult() {
        if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName().isEmpty()) {
            viewResult = resultDisplayBeanLocal.findAllResult();
            evaluationResultDataModel = new ListDataModel(viewResult);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName().isEmpty()) {
            viewResult = resultDisplayBeanLocal.findByEmpId(hrEmployees);
            evaluationResultDataModel = new ListDataModel(viewResult);
        } else if (hrEmployees.getEmpId().isEmpty() && hrEmployees.getFirstName() != null) {
            viewResult = resultDisplayBeanLocal.findByName(hrEmployees);
            evaluationResultDataModel = new ListDataModel(viewResult);
        } else if (hrEmployees.getEmpId() != null && hrEmployees.getFirstName() != null) {
            viewResult = resultDisplayBeanLocal.findByTwo(hrEmployees, hrEmployees);
            evaluationResultDataModel = new ListDataModel(viewResult);
        }
    }

    double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void calculatingTotalValue() {
        total = 0.0;
        for (HrEvaluationResultDetails hrEvaluationResultDetailsList : hrEvaluationResult.getHrEvaluationResultDetailsList()) {
            total += hrEvaluationResultDetailsList.getEvaluationValue();
        }
    }

    public void resultdisplayChanged(SelectEvent event) {
        resultListDataModel = null;
        hrEvaluationResult = (HrEvaluationResults) event.getObject();
        hrEvaluationResult.setId(hrEvaluationResult.getId());
        hrEvaluationResult = resultDisplayBeanLocal.getSelectedResult(hrEvaluationResult.getId());
        employeeName = hrEvaluationResult.getEmpId().getFirstName() + " " + hrEvaluationResult.getEmpId().getMiddleName() + " "
                + hrEvaluationResult.getEmpId().getLastName();
        evaluatorEmployee = hrEvaluationResult.getEvaluatorId().getFirstName() + " " + hrEvaluationResult.getEvaluatorId().getMiddleName() + " "
                + hrEvaluationResult.getEvaluatorId().getLastName();
        setHrEmployees(hrEvaluationResult.getEmpId());
        hrEvaluationSessions = hrEvaluationResult.getSessionId();
        hrJobTypes = hrEmployees.getJobId();
        evalutor = hrEvaluationResult.getEvaluatorId();
        calculatingTotalValue();
        newPage = true;
        searchPage = false;
        newOrSearch = "Search";
        setIcone("ui-icon-search");
        recreateDataModel();
    }

    public void recreateDataModel() {
        resultListDataModel = null;
        resultListDataModel = new ListDataModel(new ArrayList(hrEvaluationResult.getHrEvaluationResultDetailsList()));
    }

//</editor-fold>
}
