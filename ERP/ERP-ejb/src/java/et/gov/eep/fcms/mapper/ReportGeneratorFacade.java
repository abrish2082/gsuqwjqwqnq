/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

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
 * @author Me
 */
@Stateless
public class ReportGeneratorFacade {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public Collection pentionReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_TAX_REPORT");
        call.useNamedCursorOutputAsResultSet("cur");
//          call.addNamedArgumentValue("TAX_TYPE", hashMap.get("taxName").toString());
        call.addNamedArgumentValue("REPORT_MONTH", hashMap.get("REPORT_MONTH").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (!query.getResultList().isEmpty()) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    public Collection pentionSummery(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_PENTION_SUMMERY");
        call.useNamedCursorOutputAsResultSet("cur");
//          call.addNamedArgumentValue("taxType", hashMap.get("taxName").toString());
        call.addNamedArgumentValue("monthReport", hashMap.get("monthReport").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (!query.getResultList().isEmpty()) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    public Collection incomeTaxAtachment(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_INCOME_TAX_ATTACHMENT");
        call.useNamedCursorOutputAsResultSet("cur");
//          call.addNamedArgumentValue("taxType", hashMap.get("taxName").toString());
        call.addNamedArgumentValue("REPORT_MONTH", hashMap.get("REPORT_MONTH").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (!query.getResultList().isEmpty()) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }

    public Collection incomeTaxSummery(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_INCOMETAX_SUMMERY");
        call.useNamedCursorOutputAsResultSet("cur");
//          call.addNamedArgumentValue("taxType", hashMap.get("taxName").toString());
        call.addNamedArgumentValue("incomeTaxMonth", hashMap.get("incomeTaxMonth").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (!query.getResultList().isEmpty()) {
            return (Collection) query.getResultList();
        } else {

            return null;
        }
    }

    public Collection costSharingAttachmentReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_COSTSHARING_ATTACHMENT");
        call.useNamedCursorOutputAsResultSet("cur");
//          call.addNamedArgumentValue("taxType", hashMap.get("taxName").toString());
        call.addNamedArgumentValue("REPORT_MONTH", hashMap.get("REPORT_MONTH").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (!query.getResultList().isEmpty()) {
            return (Collection) query.getResultList();
        } else {

            return null;
        }
    }

    public Collection costSharingSummeryReport(HashMap hashMap) {
        DataReadQuery databaseQuery = new DataReadQuery();
        StoredProcedureCall call = new StoredProcedureCall();
        call.setProcedureName("FMS_COSTSHARING_SUMMERY");
        call.useNamedCursorOutputAsResultSet("cur");
//          call.addNamedArgumentValue("taxType", hashMap.get("taxName").toString());
        call.addNamedArgumentValue("REPORT_MONTH", hashMap.get("REPORT_MONTH").toString());
        databaseQuery.setCall(call);
        Query query = ((JpaEntityManager) em.getDelegate()).createQuery(databaseQuery);
        if (!query.getResultList().isEmpty()) {
            return (Collection) query.getResultList();
        } else {
            return null;
        }
    }
}
