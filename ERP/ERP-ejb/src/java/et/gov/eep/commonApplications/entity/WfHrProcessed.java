/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.entity;

import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.entity.EmployeeBonus.HrEmployeesBonus;
import et.gov.eep.hrms.entity.actingAssignment.HrActingAssignments;
import et.gov.eep.hrms.entity.attendance.HrAttendances;
import et.gov.eep.hrms.entity.compliantHandling.HrAppeals;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.entity.documentProvidingService.HrDocumentRequests;
import et.gov.eep.hrms.entity.evaluation.HrEvalResultProbations;
import et.gov.eep.hrms.entity.insurance.HrInsurancePayment;
import et.gov.eep.hrms.entity.leave.HrLeaveRequest;
import et.gov.eep.hrms.entity.massSalaryIncrement.HrSalaryIncrements;
import et.gov.eep.hrms.entity.leave.HrLeaveTransfer;
import et.gov.eep.hrms.entity.medical.HrLocalMedInvoices;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import et.gov.eep.hrms.entity.overtime.HrOvertimeRequests;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitments;
import et.gov.eep.hrms.entity.promotion.HrPromotionRequests;
import et.gov.eep.hrms.entity.recruitment.HrCandidateSelected;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlans;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import et.gov.eep.hrms.entity.termination.HrRetirementRequest;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdUnplanTrainingRequest;
import et.gov.eep.hrms.entity.transfer.HrTransferRequests;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author meles
 */
@Entity
@Table(name = "WF_HR_PROCESSED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WfHrProcessed.findAll", query = "SELECT w FROM WfHrProcessed w"),
        @NamedQuery(name = "WfHrProcessed.findByLeaveId", query = "SELECT w FROM WfHrProcessed w WHERE w.leaveId = :leaveId"),
    @NamedQuery(name = "WfHrProcessed.findByWorkflowId", query = "SELECT w FROM WfHrProcessed w WHERE w.workflowId = :workflowId"),
    @NamedQuery(name = "WfHrProcessed.findByDecision", query = "SELECT w FROM WfHrProcessed w WHERE w.decision = :decision"),
    @NamedQuery(name = "WfHrProcessed.findByCommentGiven", query = "SELECT w FROM WfHrProcessed w WHERE w.commentGiven = :commentGiven"),
    @NamedQuery(name = "WfHrProcessed.findByProcessedBy", query = "SELECT w FROM WfHrProcessed w WHERE w.processedBy = :processedBy"),
    @NamedQuery(name = "WfHrProcessed.findByProcessedOn", query = "SELECT w FROM WfHrProcessed w WHERE w.processedOn = :processedOn"),
    @NamedQuery(name = "WfHrProcessed.findByByMassSalId", query = "SELECT w FROM WfHrProcessed w WHERE w.HrSalaryIncrementId.id = :salIncId")})
public class WfHrProcessed implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WF_HR_PROCESSED_SEQ")
    @SequenceGenerator(name = "WF_HR_PROCESSED_SEQ", sequenceName = "WF_HR_PROCESSED_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "WORKFLOW_ID")
    private BigDecimal workflowId;
    @Column(name = "DECISION")
    private Integer decision;
    @Size(max = 400)
    @Column(name = "COMMENT_GIVEN")
    private String commentGiven;
    @Column(name = "PROCESSED_BY")
    private Integer processedBy;
    @Size(max = 20)
    @Column(name = "PROCESSED_ON")
    private String processedOn;
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrDocumentRequests documentId;
    @JoinColumn(name = "APPEAL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrAppeals appealId;
    @JoinColumn(name = "ACTING_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrActingAssignments actingId;
    @JoinColumn(name = "TRANSFER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTransferRequests transferId;
    @JoinColumn(name = "SUCCESSION_EVALUATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSmSuccessorEvaluation successionEvaluationId;
    @JoinColumn(name = "TERMINATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTerminationRequests terminationId;
    @JoinColumn(name = "ATTENDANCE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrAttendances attendanceId;
    @JoinColumn(name = "DISCIPLINE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrDisciplineRequests disciplineId;
    @JoinColumn(name = "BONUS_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEmployeesBonus bonusId;
    @JoinColumn(name = "RECRUITMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrRecruitmentRequests recruitmentId;
    @JoinColumn(name = "SUCCESSION_PLANNING_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrSmSuccessionPlans successionPlanningId;
    @JoinColumn(name = "INSURANCE_PAYMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrInsurancePayment insurancePaymentId;
    @JoinColumn(name = "RETIREMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrRetirementRequest retirementId;
    @JoinColumn(name = "UNPLANNED_TRAINING_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdUnplanTrainingRequest unplannedTrainingId;
    @JoinColumn(name = "LEAVE_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLeaveRequest leaveId;

    @JoinColumn(name = "LEAVE_TRANSFER_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLeaveTransfer leaveTransferId;

    @JoinColumn(name = "MEDICAL_CREDIT_BILL_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLocalMedInvoices medicalCreditBillId;
    @JoinColumn(name = "ANNUAL_TRAINING_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrTdAnnualNeedRequests annualTrainingId;
    @JoinColumn(name = "MEDICAL_CASH_REFUND_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrLocalMedSettlements medicalCashRefundId;
    @JoinColumn(name = "OVERTIME_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrOvertimeRequests overtimeId;
    @JoinColumn(name = "PROMOTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrPromotionRequests promotionId;
    @JoinColumn(name = "HRPRECRUITMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrHrpRecruitments hrpRecruitmentId;

    @JoinColumn(name = "MASS_SALARY_INCRMENT", referencedColumnName = "ID")
    @ManyToOne
    private HrSalaryIncrements HrSalaryIncrementId;

    @JoinColumn(name = "PROBATION_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrEvalResultProbations probationId;
    @JoinColumn(name = "SHORTLIST_ID", referencedColumnName = "ID")
    @ManyToOne
    private HrCandidateSelected shortlistId;

    @Transient
    String ChangedStstus;

    public String getChangedStstus() {
        return ChangedStstus;
    }

    public void setChangedStstus(String ChangedStstus) {
        this.ChangedStstus = ChangedStstus;
    }

    public WfHrProcessed() {
    }

    public WfHrProcessed(BigDecimal workflowId) {
        this.workflowId = workflowId;
    }

    public BigDecimal getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(BigDecimal workflowId) {
        this.workflowId = workflowId;
    }

    public Integer getDecision() {
        return decision;
    }

    public void setDecision(Integer decision) {
        this.decision = decision;
    }

    public String getCommentGiven() {
        return commentGiven;
    }

    public void setCommentGiven(String commentGiven) {
        this.commentGiven = commentGiven;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Integer processedBy) {
        this.processedBy = processedBy;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public HrDocumentRequests getDocumentId() {
        return documentId;
    }

    public void setDocumentId(HrDocumentRequests documentId) {
        this.documentId = documentId;
    }

    public HrAppeals getAppealId() {
        return appealId;
    }

    public void setAppealId(HrAppeals appealId) {
        this.appealId = appealId;
    }

    public HrTransferRequests getTransferId() {
        return transferId;
    }

    public void setTransferId(HrTransferRequests transferId) {
        this.transferId = transferId;
    }

    public HrActingAssignments getActingId() {
        return actingId;
    }

    public void setActingId(HrActingAssignments actingId) {
        this.actingId = actingId;
    }

    public HrSmSuccessorEvaluation getSuccessionEvaluationId() {
        return successionEvaluationId;
    }

    public void setSuccessionEvaluationId(HrSmSuccessorEvaluation successionEvaluationId) {
        this.successionEvaluationId = successionEvaluationId;
    }

    public HrTerminationRequests getTerminationId() {
        return terminationId;
    }

    public void setTerminationId(HrTerminationRequests terminationId) {
        this.terminationId = terminationId;
    }

    public HrAttendances getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(HrAttendances attendanceId) {
        this.attendanceId = attendanceId;
    }

    public HrDisciplineRequests getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(HrDisciplineRequests disciplineId) {
        this.disciplineId = disciplineId;
    }

    public HrEmployeesBonus getBonusId() {
        return bonusId;
    }

    public void setBonusId(HrEmployeesBonus bonusId) {
        this.bonusId = bonusId;
    }

    public HrRecruitmentRequests getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(HrRecruitmentRequests recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public HrSmSuccessionPlans getSuccessionPlanningId() {
        return successionPlanningId;
    }

    public void setSuccessionPlanningId(HrSmSuccessionPlans successionPlanningId) {
        this.successionPlanningId = successionPlanningId;
    }

    public HrInsurancePayment getInsurancePaymentId() {
        return insurancePaymentId;
    }

    public void setInsurancePaymentId(HrInsurancePayment insurancePaymentId) {
        this.insurancePaymentId = insurancePaymentId;
    }

    public HrRetirementRequest getRetirementId() {
        return retirementId;
    }

    public void setRetirementId(HrRetirementRequest retirementId) {
        this.retirementId = retirementId;
    }

    public HrTdUnplanTrainingRequest getUnplannedTrainingId() {
        return unplannedTrainingId;
    }

    public void setUnplannedTrainingId(HrTdUnplanTrainingRequest unplannedTrainingId) {
        this.unplannedTrainingId = unplannedTrainingId;
    }

    public HrLeaveRequest getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(HrLeaveRequest leaveId) {
        this.leaveId = leaveId;
    }

    public HrLeaveTransfer getLeaveTransferId() {
        return leaveTransferId;
    }

    public void setLeaveTransferId(HrLeaveTransfer leaveTransferId) {
        this.leaveTransferId = leaveTransferId;
    }

    public HrLocalMedInvoices getMedicalCreditBillId() {
        return medicalCreditBillId;
    }

    public void setMedicalCreditBillId(HrLocalMedInvoices medicalCreditBillId) {
        this.medicalCreditBillId = medicalCreditBillId;
    }

    public HrTdAnnualNeedRequests getAnnualTrainingId() {
        return annualTrainingId;
    }

    public void setAnnualTrainingId(HrTdAnnualNeedRequests annualTrainingId) {
        this.annualTrainingId = annualTrainingId;
    }

    public HrLocalMedSettlements getMedicalCashRefundId() {
        return medicalCashRefundId;
    }

    public void setMedicalCashRefundId(HrLocalMedSettlements medicalCashRefundId) {
        this.medicalCashRefundId = medicalCashRefundId;
    }

    public HrOvertimeRequests getOvertimeId() {
        return overtimeId;
    }

    public void setOvertimeId(HrOvertimeRequests overtimeId) {
        this.overtimeId = overtimeId;
    }

    public HrPromotionRequests getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(HrPromotionRequests promotionId) {
        this.promotionId = promotionId;
    }

    public HrHrpRecruitments getHrpRecruitmentId() {
        return hrpRecruitmentId;
    }

    public void setHrpRecruitmentId(HrHrpRecruitments hrpRecruitmentId) {
        this.hrpRecruitmentId = hrpRecruitmentId;
    }

    public HrSalaryIncrements getHrSalaryIncrementId() {
        return HrSalaryIncrementId;
    }

    public void setHrSalaryIncrementId(HrSalaryIncrements HrSalaryIncrementId) {
        this.HrSalaryIncrementId = HrSalaryIncrementId;
    }

    public HrEvalResultProbations getProbationId() {
        return probationId;
    }

    public void setProbationId(HrEvalResultProbations probationId) {
        this.probationId = probationId;
    }

    public HrCandidateSelected getShortlistId() {
        return shortlistId;
    }
    
    @Transient
    String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    @Transient
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workflowId != null ? workflowId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WfHrProcessed)) {
            return false;
        }
        WfHrProcessed other = (WfHrProcessed) object;
        if ((this.workflowId == null && other.workflowId != null) || (this.workflowId != null && !this.workflowId.equals(other.workflowId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "et.gov.eep.commonApplications.entity.WfHrProcessed[ workflowId=" + workflowId + " ]";
    }

}
