/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.actingAssignment;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.actingAssignment.HrActingAssignments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
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
public class HrActingAssignmentFacade extends AbstractFacade<HrActingAssignments> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrActingAssignmentFacade() {
        super(HrActingAssignments.class);
    }

    public HrActingAssignments getActingAssignmentInfo(int actingAssignment) {
        Query query = em.createNamedQuery("HrActingAssignments.findById");
        query.setParameter("id", actingAssignment);
        try {
            HrActingAssignments actingInfo = (HrActingAssignments) query.getResultList().get(0);
            return actingInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrJobTypes> SearchByJobTitel(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrJobTypes.findByJobTitleLike");
        query.setParameter("jobTitle", hrJobTypes.getJobTitle().toLowerCase() + '%');
        try {
            ArrayList<HrJobTypes> jobList = new ArrayList(query.getResultList());
            return jobList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrJobTypes getByJobTitel(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrJobTypes.findByJobTitle");
        query.setParameter("jobTitle", hrJobTypes.getJobTitle());
        try {
            HrJobTypes jobList = (HrJobTypes) (query.getResultList().get(0));
            return jobList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> searchByStatus(Integer acting) {
        List<String> actingList = null;
        try {
            Query query = em.createNamedQuery("HrActingAssignments.findByStatus");
            query.setParameter("status", acting);
            actingList = (List<String>) query.getResultList();
            return actingList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> searchAppReList(Integer actingAssignment) {
        List<String> actingAssignmentList = null;
        try {
            Query query = em.createNamedQuery("HrActingAssignment.findByStatusApprove");
            query.setParameter("status", actingAssignment);
            actingAssignmentList = (List<String>) query.getResultList();
            return actingAssignmentList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrActingAssignments getActingInfo(int actingAssignment) {
        Query query = em.createNamedQuery("HrActingAssignments.findById");
        query.setParameter("id", actingAssignment);
        try {
            HrActingAssignments actingInfo = (HrActingAssignments) query.getResultList().get(0);
            return actingInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrActingAssignments> findAllRequest() {
        Query query = em.createNamedQuery("HrActingAssignments.findAll");
        try {
            ArrayList<HrActingAssignments> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrActingAssignments> findByActingType(HrActingAssignments hrActingAssignments) {
        Query query = em.createNamedQuery("HrActingAssignments.findByActingType");
        query.setParameter("actingType", hrActingAssignments.getActingType().toUpperCase() + '%');
        try {
            ArrayList<HrActingAssignments> request = new ArrayList<>(query.getResultList());
            if (query.getResultList().isEmpty()) {
            } else if (query.getResultList().size() > 0) {
                return request;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrActingAssignments> findByRequestDate(HrActingAssignments hrActingAssignments) {
        Query query = em.createNamedQuery("HrActingAssignments.findByRequestDate");
//        query.setParameter("requestDate", hrActingAssignments.getRequestDate() + '%');
        try {
            ArrayList<HrActingAssignments> request = new ArrayList<>(query.getResultList());
            if (query.getResultList().isEmpty()) {
            } else if (query.getResultList().size() > 0) {
                return request;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrActingAssignments> findByTwo(HrActingAssignments hrActingAssignments) {
        Query query = em.createNamedQuery("HrActingAssignments.findByTwo");
        query.setParameter("actingType", hrActingAssignments.getActingType().toUpperCase() + '%');
//        query.setParameter("requestDate", hrActingAssignments.getRequestDate() + '%');
        try {
            ArrayList<HrActingAssignments> request = new ArrayList<>(query.getResultList());
            if (query.getResultList().isEmpty()) {
            } else if (query.getResultList().size() > 0) {
                return request;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * munir
     *
     * @param status
     * @return
     */
    public List<HrActingAssignments> loadActingRequests(int status) {
        Query query = em.createNativeQuery("SELECT * "
                + " FROM HR_ACTING_ASSIGNMENTS ACCTING "
                + " INNER JOIN HR_EMP_DETAIL_VIEW ACTING_EMP "
                + " ON ACCTING.ASSIGNED_BY = ACTING_EMP.ID "
                + " WHERE ACCTING.STATUS ='" + status + "' "
                + " ORDER BY REQUEST_DATE DESC", HrActingAssignments.class);
        try {
            return (List<HrActingAssignments>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrActingAssignments> loadActingRequestList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_ACTING_ASSIGNMENTS acting"
                    + " WHERE acting.STATUS ='" + status.getStatus1() + "' AND"
                    + " acting.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY acting.REQUEST_DATE DESC", HrActingAssignments.class);
            return (List<HrActingAssignments>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrActingAssignments> loadActingList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_ACTING_ASSIGNMENTS acting"
                    + " WHERE (acting.status ='" + status.getStatus1() + "' OR"
                    + " acting.status ='" + status.getStatus2() + "') AND"
                    + " acting.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY acting.REQUEST_DATE DESC", HrActingAssignments.class);
            return (List<HrActingAssignments>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrActingAssignments> findActingList(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        if (status == 0) {
            queryStatus = " WHERE(STATUS='0')";
        } else if (status == 1) {
            queryStatus = " WHERE(STATUS='1' OR STATUS='3')";
        } else if (status == 2) {
            queryStatus = " WHERE(STATUS='2' OR STATUS='4')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_ACTING_ASSIGNMENTS "
                + queryStatus
                + "ORDER BY REQUEST_DATE DESC", HrActingAssignments.class);
        try {
            return (List<HrActingAssignments>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrActingAssignments loadActingRequestDetails(int requestId) {
        Query query = em.createNamedQuery("HrActingAssignments.findById");
        query.setParameter("id", requestId);
        try {
            HrActingAssignments selectrequest = (HrActingAssignments) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int approveActingRequest(String empId, String jobCode, int requestId, String processDate, int status, int processType) {
        int checkApprove = 0;
        Query updateRequest = em.createNativeQuery("UPDATE HR_ACTING_ASSIGNMENTS SET STATUS='" + status + "' "
                + "WHERE ID='" + requestId + "'");
        Query updateEmployee = em.createNativeQuery("UPDATE HR_EMPLOYEES SET IS_ACTING='1'"
                + "WHERE EMP_ID='" + empId + "'");
        Query insertHistory = em.createNativeQuery("INSERT INTO HR_EMP_INTERNAL_HISTORY "
                + " ("
                + " EMP_ID,PROCESS_ID,PROCESS_TYPE,"
                + " OLD_DEP_ID,OLD_DEP_NAME,"
                + " OLD_JOB_ID,OLD_JOB_NAME,"
                + " OLD_GRADE,"
                + " OLD_SALARY_STEP,OLD_SALARY,"
                + " PROCESS_DATE"
                + " ) "
                + " SELECT"
                + " '" + empId + "'," + " '" + requestId + "'," + " '" + processType + "',"
                + " DEPARTMENT_ID,DEP_NAME,"
                + " JOB_ID,JOB_TITLE,"
                + " GRADE_ID,"
                + " SALARY_STEP_ID,SALARY,"
                + " '" + processDate + "'"
                + " FROM HR_VIEW_EMP_DETAIL WHERE EMP_ID='" + empId + "'");
        try {
            if (updateRequest.executeUpdate() > 0) {
                if (insertHistory.executeUpdate() > 0) {
                    if (updateEmployee.executeUpdate() > 0) {
                        checkApprove = 1;
                    }
                }
            }
            return checkApprove;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public boolean isRequstExist(HrActingAssignments actingAssignment) {
        boolean isExist;
        Query query = em.createNamedQuery("HrActingAssignments.checkDuplicate");
        query.setParameter("empId", actingAssignment.getAssignedBy().getEmpId());
        try {
            if (query.getResultList().size() > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
            return isExist;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<HrActingAssignments> findActingPreparedList() {
        Query query = em.createNamedQuery("HrActingAssignments.findByWfPrepared");
        try {
            List<HrActingAssignments> preparedList = query.getResultList();
            return preparedList;
        } catch (Exception ex) {
            return null;
        }
    }

}
