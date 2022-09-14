/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.medical;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;
import et.gov.eep.hrms.entity.medical.HrLocalMedInvoices;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author INSA
 */
@Stateless
public class HrLocalMedInvoicesFacade extends AbstractFacade<HrLocalMedInvoices> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLocalMedInvoicesFacade() {
        super(HrLocalMedInvoices.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrLocalMedInstitutions> getHospitalList() {
        Query query = em.createNamedQuery("HrLocalMedInstitutions.findHospital");
        try {
            ArrayList<HrLocalMedInstitutions> hospitalList = new ArrayList<>(query.getResultList());
            return hospitalList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HrLocalMedInstitutions> getPharmacyList() {
        Query query = em.createNamedQuery("HrLocalMedInstitutions.findPharmacy");
        try {
            ArrayList<HrLocalMedInstitutions> pharmacyList = new ArrayList(query.getResultList());
            return pharmacyList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEmployees> searchPatientByName(String hrEmployee) {
        Query query = em.createNamedQuery("HrEmployees.findByFirstNameLike", HrEmployees.class);
        query.setParameter("firstName", hrEmployee.toUpperCase() + '%');
        try {
            ArrayList<HrEmployees> employee = new ArrayList(query.getResultList());
            return employee;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmployees getPatientByName(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrEmployees.findByFirstName");
            query.setParameter("firstName", hrEmployees.getFirstName());
            HrEmployees empList = (HrEmployees) (query.getResultList().get(0));
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrLocalMedInvoices> findByName(String institutionName) {
        Query query = em.createNamedQuery("HrLocalMedInvoices.findByInstitutionName", HrLocalMedInvoices.class);
        query.setParameter("name", institutionName);
        try {
            return (List<HrLocalMedInvoices>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ArrayList<HrLocalMedInvoices> findAll() {
        Query query = em.createNamedQuery("HrLocalMedInvoices.findAll");
        try {
            ArrayList<HrLocalMedInvoices> invoice = new ArrayList<>(query.getResultList());
            return invoice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrLocalMedInvoices> findByInstitutionName(HrLocalMedInstitutions hrLocalMedInstitutions) {
        Query query = em.createNamedQuery("HrLocalMedInvoices.findByInstitutionName");
        query.setParameter("name", hrLocalMedInstitutions.getName());
        try {
            ArrayList<HrLocalMedInvoices> nameList = new ArrayList<>(query.getResultList());
            System.out.println("inst. name = = = " + nameList);
            return nameList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrLocalMedInvoices getSelectedMedInvoice(int invoice) {
        Query query = em.createNamedQuery("HrLocalMedInvoices.findById");
        query.setParameter("id", invoice);
        try {
            HrLocalMedInvoices selectedInvoice = (HrLocalMedInvoices) query.getResultList().get(0);
            return selectedInvoice;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrLocalMedInstitutions> findByInstType(String instType) {
        try {
            Query query = em.createNamedQuery("HrLocalMedInstitutions.findByType", HrLocalMedInstitutions.class);
            query.setParameter("type", instType);
            System.out.println("Intst Name list = = = " + (List<HrLocalMedInstitutions>) query.getResultList());
            return (List<HrLocalMedInstitutions>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrLocalMedInvoices> findPreparedList() {
        Query query = em.createNamedQuery("HrLocalMedInvoices.findPreparedList");
        try {
            List<HrLocalMedInvoices> preparedList = query.getResultList();
            return preparedList;
        } catch (Exception ex) {
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrLocalMedInvoices> loadMedicalInvoice(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 2) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='1')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_LOCAL_MED_INVOICES "
                + queryStatus
                + "ORDER BY INVOICE_DATE DESC", HrLocalMedInvoices.class);
        try {
            return (List<HrLocalMedInvoices>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrLocalMedInvoices> searchByStatus(int status) {
        try {
            String queryStatus = " WHERE STATUS='" + status + "' ";
            if (status == 0) {
                queryStatus = " WHERE(STATUS='0')";
            } else if (status == 1) {
                queryStatus = " WHERE(STATUS='1' OR STATUS='3')";
            } else if (status == 2) {
                queryStatus = " WHERE(STATUS='2' OR STATUS='4')";
            }
            Query query = em.createNativeQuery("SELECT * FROM HR_LOCAL_MED_INVOICES "
                    + queryStatus
                    + "ORDER BY INVOICE_DATE DESC", HrLocalMedInvoices.class);
            return (List<HrLocalMedInvoices>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrLocalMedInvoices> loadMedicalInvoiceList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_LOCAL_MED_INVOICES rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "' OR"
                    + " rq.status='" + status.getStatus3() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrLocalMedInvoices.class);
            return (List<HrLocalMedInvoices>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrLocalMedInvoices> loadInvoiceList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_LOCAL_MED_INVOICES rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrLocalMedInvoices.class);
            return (List<HrLocalMedInvoices>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrLocalMedInstitutions> findByType() {
        Query query = em.createNativeQuery("SELECT DISTINCT TYPE FROM HR_LOCAL_MED_INSTITUTIONS WHERE STATUS = 0");
        try {
            return (List<HrLocalMedInstitutions>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrLocalMedInvoices> loadReqMedInvoiceList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_LOCAL_MED_INVOICES medInvoice"
                    + " WHERE medInvoice.STATUS ='" + status.getStatus1() + "' AND"
                    + " medInvoice.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY medInvoice.INVOICE_DATE DESC", HrLocalMedInvoices.class);
            return (List<HrLocalMedInvoices>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrLocalMedInvoices> loadApproveMedInvoiceList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_LOCAL_MED_INVOICES medInvoice"
                    + " WHERE (medInvoice.status ='" + status.getStatus1() + "' OR"
                    + " medInvoice.status ='" + status.getStatus2() + "') AND"
                    + " medInvoice.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY medInvoice.INVOICE_DATE DESC", HrLocalMedInvoices.class);
            return (List<HrLocalMedInvoices>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

}
