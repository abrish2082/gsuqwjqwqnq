/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.displine;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.compliantHandling.HrAppeals;
import et.gov.eep.hrms.entity.displine.HrDisciplineFileUpload;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.entity.displine.HrEmpDisciplines;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrDisciplineRequestsFacade extends AbstractFacade<HrDisciplineRequests> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrDisciplineRequestsFacade() {
        super(HrDisciplineRequests.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">

    public HrDisciplineRequests findByDisciplineId(HrDisciplineRequests hrDisciplineRequests) {
        Query query = em.createNamedQuery("HrDisciplineRequests.findById");
        query.setParameter("id", hrDisciplineRequests.getId());
        try {
            HrDisciplineRequests getDisciplineRequests = (HrDisciplineRequests) query.getResultList().get(0);
            return getDisciplineRequests;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDisciplineRequests> displayRequests() {
        List<HrDisciplineRequests> requestses;
        try {
            Query query = em.createNamedQuery("HrDisciplineRequests.findByStatus", HrDisciplineRequests.class);
            query.setParameter("status", "0");
            requestses = (List<HrDisciplineRequests>) query.getResultList();
            return requestses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrDisciplineRequests> displayRequestsApprove() {
        List<HrDisciplineRequests> requestses;
        try {
            Query query = em.createNamedQuery("HrDisciplineRequests.findByStatus", HrDisciplineRequests.class);
            query.setParameter("status", "1");
            requestses = (List<HrDisciplineRequests>) query.getResultList();
            return requestses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrDisciplineRequests> searchEmployeeByName(HrEmployees hrEmployeesRequester) {
        Query query = em.createNamedQuery("HrDisciplineRequests.findByRequesterName", HrDisciplineRequests.class);
        query.setParameter("firstName", hrEmployeesRequester.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrDisciplineRequests> employeeInformations = new ArrayList(query.getResultList());
            return employeeInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrDisciplineRequests> searchEmployeeByOffenderName(HrEmployees hrEmployeesAccuser) {

        try {
            Query query = em.createNamedQuery("HrDisciplineRequests.findByRequesterName", HrDisciplineRequests.class);
            query.setParameter("firstName", hrEmployeesAccuser.getFirstName().toUpperCase() + '%');

            ArrayList<HrDisciplineRequests> employeeInformations = new ArrayList(query.getResultList());
            return employeeInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrDisciplineRequests> searchEmployeeByOffenderAndRequesterName(HrEmployees hrEmployeesRequester, HrEmployees hrEmployeesAccuser) {
        try {
            Query query = em.createNamedQuery("HrDisciplineRequests.findByRequsterAndOffenderName", HrDisciplineRequests.class);
            query.setParameter("reqName", hrEmployeesRequester.getFirstName().toUpperCase() + '%');
            query.setParameter("OffenderName", hrEmployeesAccuser.getFirstName().toUpperCase() + '%');
            ArrayList<HrDisciplineRequests> employeeInformations = new ArrayList(query.getResultList());
            return employeeInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrDisciplineRequests> getByStatus(HrDisciplineRequests disciplineRequests) {
        try {
            Query query = em.createNamedQuery("HrDisciplineRequests.findByStatus", HrDisciplineRequests.class);
            query.setParameter("status", disciplineRequests.getStatus());
            ArrayList<HrDisciplineRequests> employeeInformations = new ArrayList(query.getResultList());
            return employeeInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrDisciplineRequests> findByOffenderId(HrDisciplineRequests disciplineRequests) {
        try {
            Query query = em.createNamedQuery("HrDisciplineRequests.findByoffenderId", HrDisciplineRequests.class);
            query.setParameter("offenderId", disciplineRequests.getOffenderId());
            ArrayList<HrDisciplineRequests> employeeInformations = new ArrayList(query.getResultList());
            return employeeInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrDisciplineRequests findDisciplineReqByOffenderIdAndOffenceType(HrDisciplineRequests disciplineRequests) {
        HrDisciplineRequests getDisciplineRequests = new HrDisciplineRequests();
        Query query = em.createNamedQuery("HrDisciplineRequests.findDisciplineReqRecordByOffenderIdAndOffenceType");
        query.setParameter("offenderId", disciplineRequests.getOffenderId());
        query.setParameter("offenceType", disciplineRequests.getOffenceTypeId());
        try {
            getDisciplineRequests = (HrDisciplineRequests) query.getResultList().get(0);
            return getDisciplineRequests;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrDisciplineRequests findDisciplineReqRecordByOffenderIdAndOffenceType(HrDisciplineRequests disciplineRequests) {
        HrDisciplineRequests getDisciplineRequests = new HrDisciplineRequests();
        Query query = em.createNamedQuery("HrDisciplineRequests.findDisciplineReqRecordByOffenderIdAndOffenceType");
        query.setParameter("offenderId", disciplineRequests.getOffenderId());
        query.setParameter("offenceType", disciplineRequests.getOffenceTypeId());
        try {
            getDisciplineRequests = (HrDisciplineRequests) query.getResultList().get(0);
            return getDisciplineRequests;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrAppeals findByCaseId(HrAppeals hrAppeals) {
        HrAppeals appeal = new HrAppeals();
        Query query = em.createNamedQuery("HrAppeals.findByCaseId");
        query.setParameter("caseId", hrAppeals.getCaseId());
        try {
            appeal = (HrAppeals) query.getResultList().get(0);
            return appeal;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDisciplineRequests> findRequestForApproval() {
        Query query = em.createNamedQuery("HrDisciplineRequests.findByStatus");
        query.setParameter("status", "0");
        try {
            return (List<HrDisciplineRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDisciplineFileUpload> getFileListByDiciplineId(HrDisciplineRequests disciplineRequests) {
        System.out.println("here Disp Req id is " + disciplineRequests);
        Query q = em.createNamedQuery("HrDisciplineFileUpload.findByDisciplineCaseId");
        q.setParameter("disciplineCaseId", disciplineRequests);
        try {
            List<HrDisciplineFileUpload> fileListByDisciplineId = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                fileListByDisciplineId = q.getResultList();
            }
            return fileListByDisciplineId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public HrDisciplineRequests findByIds(HrDisciplineRequests disciplineRequests) {
        HrDisciplineRequests request = new HrDisciplineRequests();
        Query query = em.createNamedQuery("HrDisciplineRequests.findById");
        query.setParameter("id", disciplineRequests.getId());
        try {
            request = (HrDisciplineRequests) query.getResultList().get(0);
            return request;
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">

    public List<HrDisciplineFileUpload> findDocumentId(HrDisciplineRequests disciplineRequests) {
        Query query = em.createNativeQuery("SELECT up.DOCUMENT_ID"
                + " FROM HR_DISCIPLINE_FILE_UPLOAD up\n"
                + "inner join HR_DISCIPLINE_REQUESTS rq\n"
                + "on rq.ID = up.DISCIPLINE_CASE_ID where up.DISCIPLINE_CASE_ID='" + disciplineRequests.getId() + "' ", HrDisciplineFileUpload.class);
        System.out.println("query" + query.getResultList());
        try {
            return (List<HrDisciplineFileUpload>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrDisciplineFileUpload findFileUploadId(HrDisciplineRequests disciplineRequests) {
        Query query = em.createNativeQuery("SELECT up.ID  "
                + "FROM HR_DISCIPLINE_FILE_UPLOAD up\n"
                + "inner join HR_DISCIPLINE_REQUESTS rq \n"
                + "on rq.ID = up.DISCIPLINE_CASE_ID where rq.ID='" + disciplineRequests.getId() + "' ", HrDisciplineFileUpload.class);
        System.out.println("query" + query.getResultList());
        try {
            return (HrDisciplineFileUpload) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDisciplineRequests> loadPenalityRequest(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 0) {
            queryStatus = " WHERE(STATUS='0')";
        } else if (status == 1) {
            queryStatus = " WHERE(STATUS='1' OR STATUS='3')";
        } else if (status == 2) {
            queryStatus = " WHERE(STATUS='2' OR STATUS='4')";
        } else if (status == 3) {
            queryStatus = " WHERE(STATUS='0' OR STATUS='1' OR STATUS='2' OR STATUS='3' OR STATUS='4' )";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_DISCIPLINE_REQUESTS "
                + queryStatus
                + "ORDER BY REPORTED_DATE DESC", HrDisciplineRequests.class);
        try {
            return (List<HrDisciplineRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDisciplineRequests> loadPenalityRequestList(Status status) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_DISCIPLINE_REQUESTS rq\n"
                    + " WHERE rq.status='" + status.getStatus1() + "' ", HrDisciplineRequests.class);
            return (List<HrDisciplineRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDisciplineRequests> loadPenalityRequestListForTwo(Status status) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + " FROM HR_DISCIPLINE_REQUESTS rq\n"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR\n"
                    + " rq.status='" + status.getStatus2() + "')", HrDisciplineRequests.class);
            return (List<HrDisciplineRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDisciplineRequests> readApprovedDisciplinaryAppeals() {
        Query query = em.createNativeQuery("SELECT rq.*\n"
                + "FROM HR_DISCIPLINE_REQUESTS rq \n"
                + "inner join HR_APPEALS ap\n"
                + "on ap.CASE_ID = rq.ID\n"
                + "where ap.CASE_CATEGORY = 2 and ap.STATUS  = 3", HrDisciplineRequests.class);
        try {
            return (List<HrDisciplineRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrEmpDisciplines> findDisciplinByOffenderId(Integer offenderId) {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + "FROM HR_EMP_DISCIPLINES d inner join HR_DISCIPLINE_REQUESTS di on di.ID=d.REQUEST_ID "
                    + "where di.STATUS = '3' AND di.OFFENDER_ID='" + offenderId + "'", HrEmpDisciplines.class);
            return (List<HrEmpDisciplines>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDisciplineRequests> findPhaseOutList() {
        try {
            Query query = em.createNativeQuery("SELECT *\n"
                    + "FROM HR_DISCIPLINE_REQUESTS rq inner join HR_EMP_DISCIPLINES ed\n"
                    + "on rq.ID = ed.REQUEST_ID AND  ed.STATUS = 0", HrDisciplineRequests.class);
            return (List<HrDisciplineRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrDisciplineRequests> findApprovedDisciplineCases(int applicantId) {
        String strl = "SELECT request.*, "
                + " offence.OFFENCE_NAME "
                + " FROM HR_DISCIPLINE_REQUESTS request "
                + " LEFT JOIN HR_DISCIPLINE_OFFENCE_TYPES offence "
                + " ON request.OFFENCE_TYPE_ID = offence.ID "
                + " LEFT JOIN HR_DISCIPLINE_OFFENCE_PENALTY decider "
                + " ON request.REPITITION_OF_OFFENCE=decider.REPETITION "
                + " AND request.OFFENCE_TYPE_ID =decider.OFFENCE_TYPE_ID "
                + " WHERE request.OFFENDER_ID =  " + applicantId
                + " AND request.STATUS = 1";

        Query query = em.createNativeQuery(strl, HrDisciplineRequests.class);
        List<HrDisciplineRequests> disciplineRequestses = (List<HrDisciplineRequests>) query.getResultList();
        return disciplineRequestses;

    }
//</editor-fold>
}
