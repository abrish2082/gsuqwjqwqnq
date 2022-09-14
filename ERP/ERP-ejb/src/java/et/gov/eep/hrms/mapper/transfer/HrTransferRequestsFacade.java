/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.transfer;

//import et.gov.eep.commonApplications.JsfUtil;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuTransferTypes;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.transfer.HrTransferRequests;
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
public class HrTransferRequestsFacade extends AbstractFacade<HrTransferRequests> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTransferRequestsFacade() {
        super(HrTransferRequests.class);
    }

    public HrLuTransferTypes findTransfertype(HrLuTransferTypes hrLuTransferTypes) {
        Query query = em.createNamedQuery("HrLuTransferTypes.findByTransferType");
        query.setParameter("transferType", hrLuTransferTypes.getTransferType());
        try {
            HrLuTransferTypes Transfertype = (HrLuTransferTypes) query.getResultList().get(0);
            return Transfertype;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrLuTransferTypes> findAllHrLuTransferTypes() {
        Query query = em.createNamedQuery("HrLuTransferTypes.findAll");
        try {
            ArrayList<HrLuTransferTypes> Transfer = new ArrayList(query.getResultList());
            return Transfer;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrDepartments getSelectDepartement(int departmentid) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", departmentid);
        try {
            HrDepartments selectdepartment = (HrDepartments) query.getResultList().get(0);
            return selectdepartment;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrTransferRequests getTransferInfo(int hrTransferRequests) {
        Query query = em.createNamedQuery("HrTransferRequests.findById");
        query.setParameter("id", hrTransferRequests);
        try {
            HrTransferRequests transferInfo = (HrTransferRequests) query.getResultList().get(0);
            return transferInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> searchByName(Integer hrTransferRequests) {
        List<String> hrTransferRequestslist = null;
        try {
            Query query = em.createNamedQuery("HrTransferRequests.findByStatus");
            query.setParameter("status", hrTransferRequests);
            hrTransferRequestslist = (List<String>) query.getResultList();
            return hrTransferRequestslist;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrJobTypes findByName(int name) {
        Query query = em.createNamedQuery("HrJobTypes.findById");
        query.setParameter("id", name);
        try {
            HrJobTypes j = (HrJobTypes) query.getResultList().get(0);
            return j;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<HrJobTypes> listOfJobType(int departmentId) {
        String queryStr
                = "SELECT j.id,j.job_code,j.job_title FROM hr_job_types j inner join  hr_dept_jobs d on j.id=d.job_id where d.DEPT_ID=" + departmentId;
        Query query = em.createNativeQuery(queryStr, HrJobTypes.class);
        List<HrJobTypes> results = query.getResultList();
        return results;
    }

    public List<String> searchAppReList(Integer hrTransferRequests) {
        List<String> hrTransferRequestslist = null;
        try {
            Query query = em.createNamedQuery("HrTransferRequests.findByStatusApprove");
            query.setParameter("status", hrTransferRequests);
            hrTransferRequestslist = (List<String>) query.getResultList();
            return hrTransferRequestslist;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrTransferRequests> findByAll(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrTransferRequests.findByTwo");
        query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrTransferRequests> request = new ArrayList<>(query.getResultList());
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

    public ArrayList<HrTransferRequests> findByEmpId(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrTransferRequests.findByEmpId");
        query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
        try {
            ArrayList<HrTransferRequests> request = new ArrayList<>(query.getResultList());
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

    public ArrayList<HrTransferRequests> findByEmpName(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrTransferRequests.findByName");
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrTransferRequests> request = new ArrayList<>(query.getResultList());
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

    public ArrayList<HrTransferRequests> findByOne(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrTransferRequests.findByOne");
        query.setParameter("empId", hrEmployees.getEmpId());
        query.setParameter("firstName", hrEmployees.getFirstName());
        try {
            ArrayList<HrTransferRequests> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrTransferRequests> findByStatus(HrTransferRequests hrTransferRequests) {
        Query query = em.createNamedQuery("HrTransferRequests.findByStatus");
        query.setParameter("status", hrTransferRequests.getStatus());
        try {
            ArrayList<HrTransferRequests> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<HrTransferRequests> findAll() {
        Query query = em.createNamedQuery("HrTransferRequests.findAll");
        try {
            ArrayList<HrTransferRequests> request = new ArrayList<>(query.getResultList());
            if (query.getResultList().isEmpty()) {
            } else if (query.getResultList().size() > 0) {
                return request;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HrTransferRequests getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrTransferRequests.findById");
        query.setParameter("id", request);
        try {
            HrTransferRequests selectrequest = (HrTransferRequests) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTransferRequests> loadTransferList(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        Query query = em.createNativeQuery("SELECT * FROM HR_TRANSFER_REQUESTS "
                + queryStatus
                + "ORDER BY REQUEST_DATE DESC", HrTransferRequests.class);
        try {
            return (List<HrTransferRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean isRequstExist(HrTransferRequests transferRequest) {
        boolean isExist;
        Query query = em.createNamedQuery("HrTransferRequests.checkDuplicate", HrTransferRequests.class);
        query.setParameter("empId", transferRequest.getRequesterId().getEmpId());
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

    public boolean isApproved(HrTransferRequests transferRequest) {
        boolean isExist;
        Query query = em.createNamedQuery("HrTransferRequests.findApproved", HrTransferRequests.class);
        query.setParameter("empId", transferRequest.getRequesterId().getId());
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

    public String findApproved(HrTransferRequests transferRequest) {
        Query query = em.createNamedQuery("HrTransferRequests.findApproved");
        query.setParameter("empId", transferRequest.getRequesterId().getId());
        try {
            HrTransferRequests approvedDate = (HrTransferRequests) (query.getResultList().get(0));
            return approvedDate.getApproveDate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Munir & Bekele
     *
     * @param transferId
     * @param employeeId
     * @param processType
     * @param fromDeptId
     * @param toDeptId
     * @param oldDepName
     * @param processDate
     * @param status
     * @return
     */
    public int updateEmployeeDepartment(String transferId, String employeeId,
            int processType, String fromDeptId, String toDeptId,
            String oldDepName, String processDate, int status) {
        int checkAllSave = 0;
        Query updateEmployee = em.createNativeQuery("UPDATE HR_EMPLOYEES "
                + " SET DEPT_ID ='" + toDeptId + "'"
                + " WHERE ID ='" + employeeId + "'");
        Query insertHistory = em.createNativeQuery("INSERT INTO HR_EMP_INTERNAL_HISTORY "
                + " ("
                + " EMP_ID,PROCESS_ID,PROCESS_TYPE,"
                + " OLD_DEP_ID,OLD_DEP_NAME,"
                + " PROCESS_DATE"
                + " ) "
                + " VALUES"
                + " ("
                + " '" + employeeId + "',"
                + " '" + transferId + "',"
                + " '" + processType + "',"
                + " '" + fromDeptId + "', '" + oldDepName + "',"
                + " '" + processDate + "'"
                + " )");
        Query updateStatus = em.createNativeQuery("UPDATE HR_TRANSFER_REQUESTS "
                + " SET STATUS ='" + status + "'"
                + " WHERE ID ='" + transferId + "'");
        try {
            if (updateEmployee.executeUpdate() > 0) {
                if (insertHistory.executeUpdate() > 0) {
                    if (updateStatus.executeUpdate() > 0) {
                        checkAllSave = 1;
                    }
                }
            }
            return checkAllSave;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public List<HrTransferRequests> loadTransferReqList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_TRANSFER_REQUESTS rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "' OR"
                    + " rq.status='" + status.getStatus3() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrTransferRequests.class);
            return (List<HrTransferRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTransferRequests> loadTransferList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_TRANSFER_REQUESTS rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrTransferRequests.class);
            return (List<HrTransferRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTransferRequests> findPreparedList() {
        Query query = em.createNamedQuery("HrTransferRequests.findPreparedList");
        try {
            List<HrTransferRequests> preparedList = query.getResultList();
            return preparedList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTransferRequests> searchByStatus(int status) {
        String statusQuery = " WHERE STATUS='" + status + "' ";
        if (status == 0) {
            statusQuery = " WHERE (STATUS='0')";
        } else if (status == 1) {
            statusQuery = " WHERE (STATUS='1' OR STATUS='3')";
        } else if (status == 2) {
            statusQuery = " WHERE (STATUS='2' OR STATUS='4')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_TRANSFER_REQUESTS "
                + statusQuery
                + "ORDER BY REQUEST_DATE DESC", HrTransferRequests.class);
        try {
            return (List<HrTransferRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrTransferRequests> loadReqTranList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_TRANSFER_REQUESTS tran"
                    + " WHERE tran.STATUS ='" + status.getStatus1() + "' AND"
                    + " tran.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY tran.REQUEST_DATE DESC", HrTransferRequests.class);
            return (List<HrTransferRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public List<HrTransferRequests> loadApproveTranList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_TRANSFER_REQUESTS tran"
                    + " WHERE (tran.status ='" + status.getStatus1() + "' OR"
                    + " tran.status ='" + status.getStatus2() + "') AND"
                    + " tran.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY tran.REQUEST_DATE DESC", HrTransferRequests.class);
            return (List<HrTransferRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

}
