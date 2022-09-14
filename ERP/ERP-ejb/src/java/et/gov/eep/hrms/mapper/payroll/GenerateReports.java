/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.payroll;

import java.util.Collection;
import java.util.HashMap;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DataReadQuery;
import org.eclipse.persistence.queries.StoredProcedureCall;

/**
 *
 * @author Musie
 */
@Stateless
public class GenerateReports {

    @PersistenceContext(unitName = "ERP-ejbPU")
    EntityManager em;

    /**
     *
     * @return
     */
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection payrollReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_PAYROLL_VIEW");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        call.addNamedArgumentValue("empId", hashMap.get("empIds").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection SummaryReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_PAYROLL_SHEET_SUMMARY");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportSalary(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_SUBREPORTSLIPSALARY");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        call.addNamedArgumentValue("empId", hashMap.get("empIds").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportForAll(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_PAYROLLSLIPFORALL");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySubSlipReportForAll(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_PAYROLLSLIPFORALLSUBREP");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportAllowance(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_SUBREPORTSLIPALLOWANCE");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("empId", hashMap.get("empIds").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportEarning(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_SUBREPORTSLIPEAR");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        call.addNamedArgumentValue("empId", hashMap.get("empIds").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportDeduction(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_SUBREPORTSLIPDED");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        call.addNamedArgumentValue("empId", hashMap.get("empIds").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection paySlipReportTest(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_SUBREPORTSLIPMASTER");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        call.addNamedArgumentValue("empId", hashMap.get("empIds").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection payrollBackReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_BACK_PAY");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        call.addNamedArgumentValue("comDate", hashMap.get("comDate").toString());
        call.addNamedArgumentValue("empId", hashMap.get("empIds").toString());        
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection incomeTaxReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_INCOMETAXREPORT");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        call.addNamedArgumentValue("debId", hashMap.get("debId").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection pensionReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_ALL_PENSION_REP");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        call.addNamedArgumentValue("debId", hashMap.get("debId").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection serviceCompensationReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_SERVICE_COMPENSATION_PAY");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", "1998-9-28");
        call.addNamedArgumentValue("secDate", "2007-11-24");
        call.addNamedArgumentValue("employmentType", "1");
        call.addNamedArgumentValue("empId", "EPSE-314");
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection incomeSubReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_DELAYED_COLLECTION_DETAIL");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("salesStartDate", hashMap.get("salesStartDate").toString());
        call.addNamedArgumentValue("startDate", hashMap.get("startDate").toString());
        call.addNamedArgumentValue("endDate", hashMap.get("endDate").toString());
        call.addNamedArgumentValue("compName", hashMap.get("Names").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection incomeSubReportTow(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_DELAYED_COLLECTION_DETAIL");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("salesStartDate", hashMap.get("salesStartDate").toString());
        call.addNamedArgumentValue("startDate", hashMap.get("startDateSub2").toString());
        call.addNamedArgumentValue("endDate", hashMap.get("endDateSub2").toString());
        call.addNamedArgumentValue("compName", hashMap.get("Names").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection incomeSubReportThri(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_DELAYED_COLLECTION_DETAIL");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("salesStartDate", hashMap.get("salesStartDate").toString());
        call.addNamedArgumentValue("startDate", hashMap.get("startDateSub3").toString());
        call.addNamedArgumentValue("endDate", hashMap.get("endDateSub3").toString());
        call.addNamedArgumentValue("compName", hashMap.get("Names").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }

    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection incomeSubReportForth(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_DELAYED_COLLECTION_DETAIL");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("salesStartDate", hashMap.get("salesStartDate").toString());
        call.addNamedArgumentValue("startDate", hashMap.get("startDateSub4").toString());
        call.addNamedArgumentValue("endDate", hashMap.get("endDateSub4").toString());
        call.addNamedArgumentValue("compName", hashMap.get("Names").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }

    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection delayedCollectionReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_DELAYED_COLLECTION_MASTER");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("startDate", hashMap.get("salesStartDate").toString());
        call.addNamedArgumentValue("compName", hashMap.get("Names").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection representaionReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_REPRESENTATION");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", "2015/11");
        call.addNamedArgumentValue("empId", "EPSE314");
        call.addNamedArgumentValue("repEmpId", "EPSE171");
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection settledAgainstReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_SETTLED_AGAINST");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection halfCollectionReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_HALF_COLLECTION");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection bankReconcilationReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_BANK_REC_MASTER");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("prepareDate", hashMap.get("prepareDate").toString());
        call.addNamedArgumentValue("accountNo", hashMap.get("accountNo").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection detailOfOutstandingChequesReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_DETAIL_OF_OUTSTANDING_CHEQ");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("prepareDate").toString());
        call.addNamedArgumentValue("bankAccountNo", hashMap.get("accountNo").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection detailOfOutstandingDepositReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_DETAIL_OF_OUTSTANDING_DEPO");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("prepareDate").toString());
        call.addNamedArgumentValue("bankAccountNo", hashMap.get("accountNo").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection payDedReport(HashMap hashMap) {
        Collection collection = null;
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_DEDUCTIONREPORT");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        call.addNamedArgumentValue("dedNames", hashMap.get("dedName").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return collection;
        }

    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection unpaidSalaryReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_UNPAIDSALARY");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }

    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection stafLaonReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_STAFLOAN");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection jvSummeryReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_JVSUMMERYMASTER");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }

    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection jvSubReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_JVSUMMERYSUBONE");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("preparedDate", hashMap.get("preparedDate").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }

    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection btPayrollReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_BT_PAYROLL_REPORT");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("fromDates", hashMap.get("fromDates").toString());
        call.addNamedArgumentValue("toDates", hashMap.get("toDates").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }

    }

    /**
     *
     * @param hashMap
     * @return
     */
    public Collection btPayrollDeductionReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_BETWEEN_DEDUCTION_REPORT");
        call.useNamedCursorOutputAsResultSet("O_CURSSOR");
        call.addNamedArgumentValue("fromDate", hashMap.get("fromDate").toString());
        call.addNamedArgumentValue("toDate", hashMap.get("toDate").toString());
        call.addNamedArgumentValue("dedNames", hashMap.get("dedNames").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (query.getResultList().isEmpty() != true) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }

    }

}
