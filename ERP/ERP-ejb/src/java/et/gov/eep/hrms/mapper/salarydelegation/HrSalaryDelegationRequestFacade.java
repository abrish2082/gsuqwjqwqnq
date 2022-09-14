/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.salarydelegation;

//import et.gov.eep.commonApplications.JsfUtil;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmpFamilies;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.salarydelegation.HrSalaryDelegationRequest;
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
public class HrSalaryDelegationRequestFacade extends AbstractFacade<HrSalaryDelegationRequest> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSalaryDelegationRequestFacade() {
        super(HrSalaryDelegationRequest.class);
    }

    public List<HrSalaryDelegationRequest> requestListByStatus(HrSalaryDelegationRequest hrSalaryDelegationRequest) {
        Query query = em.createNamedQuery("HrSalaryDelegationRequest.findByStatus");
        query.setParameter("status", hrSalaryDelegationRequest.getStatus());
        try {
            List<HrSalaryDelegationRequest> empList = new ArrayList(query.getResultList());
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrSalaryDelegationRequest findSalaryDelById(HrEmployees id) {
        Query query = em.createNamedQuery("HrSalaryDelegationRequest.findByEmpId");
        query.setParameter("id", id.getId());
        if (query.getResultList().isEmpty()) {
            return null;
        } else {
            HrSalaryDelegationRequest emp = (HrSalaryDelegationRequest) query.getResultList().get(0);
            return emp;
        }
    }

    public ArrayList<HrSalaryDelegationRequest> SearchByEmpId(HrSalaryDelegationRequest hrSalaryDelegationRequest) {
        Query query = em.createNamedQuery("HrSalaryDelegationRequest.findByEmpId");
        query.setParameter("empId", hrSalaryDelegationRequest.getFirstName());
        try {
            ArrayList<HrSalaryDelegationRequest> empList = new ArrayList(query.getResultList());
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrSalaryDelegationRequest> searchByFirstName(HrSalaryDelegationRequest hrSalaryDelegationRequest) {
        Query query = em.createNamedQuery("HrSalaryDelegationRequest.findByFirstNameLike");
        query.setParameter("firstName", hrSalaryDelegationRequest.getFirstName() + '%');
        try {
            ArrayList<HrSalaryDelegationRequest> HrSalaryDelegationRequestlist = new ArrayList(query.getResultList());
            System.out.println("obj=== B" + HrSalaryDelegationRequestlist);
            return HrSalaryDelegationRequestlist;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrSalaryDelegationRequest getSalaryInfo(HrSalaryDelegationRequest hrSalaryDelegationRequest) {
        Query query = em.createNamedQuery("HrSalaryDelegationRequest.findByEmpId");
        query.setParameter("empId", hrSalaryDelegationRequest.getFirstName());
        try {
            HrSalaryDelegationRequest seaFreightChargeInfo = (HrSalaryDelegationRequest) query.getResultList().get(0);
            return seaFreightChargeInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> searchByName(Integer hrSalaryDelegationRequest) {
        List<String> hrSalaryDelegationRequestlist = null;
        try {
            Query query = em.createNamedQuery("HrSalaryDelegationRequest.findByStatus");
            query.setParameter("status", hrSalaryDelegationRequest);
            hrSalaryDelegationRequestlist = (List<String>) query.getResultList();
            return hrSalaryDelegationRequestlist;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> searchAppReList(Integer hrSalaryDelegationRequest) {
        List<String> hrSalaryDelegationRequestlist = null;
        try {
            Query query = em.createNamedQuery("HrSalaryDelegationRequest.findByStatusApprove");
            query.setParameter("status", hrSalaryDelegationRequest);
            hrSalaryDelegationRequestlist = (List<String>) query.getResultList();
            return hrSalaryDelegationRequestlist;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void update(HrSalaryDelegationRequest request) {
        em.merge(request);
    }

    public HrSalaryDelegationRequest getDeleationInfo(int hrSalaryDelegationRequest) {
        Query query = em.createNamedQuery("HrSalaryDelegationRequest.findById");
        query.setParameter("id", hrSalaryDelegationRequest);
        try {
            HrSalaryDelegationRequest seaFreightChargeInfo = (HrSalaryDelegationRequest) query.getResultList().get(0);
            return seaFreightChargeInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrEmpFamilies> searchFamname(HrEmpFamilies hrEmpFamilies) {
        Query query = em.createNamedQuery("Families.findByFirstNameLike");
        query.setParameter("firstName", hrEmpFamilies.getFirstName().toUpperCase() + '%');
        System.out.println("obj=== Familly" + query.getResultList().size());
        try {
            ArrayList<HrEmpFamilies> famList = new ArrayList(query.getResultList());
            System.out.println("obj=== Familly" + famList);
            return famList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmpFamilies getFamname(HrEmpFamilies hrEmpFamilies) {
        Query query = em.createNamedQuery("Families.findByFirstName");
        query.setParameter("firstName", hrEmpFamilies.getFirstName());
        try {
            HrEmpFamilies famList = (HrEmpFamilies) (query.getResultList().get(0));
            return famList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<HrSalaryDelegationRequest> findAll() {
        Query query = em.createNamedQuery("HrSalaryDelegationRequest.findAll");
        try {
            ArrayList<HrSalaryDelegationRequest> request = new ArrayList<>(query.getResultList());
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

    public ArrayList<HrSalaryDelegationRequest> findByAll(HrEmployees hrEmp, HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrSalaryDelegationRequest.findByTwo");
        query.setParameter("empId", hrEmp.getEmpId().toUpperCase() + '%');
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrSalaryDelegationRequest> request = new ArrayList<>(query.getResultList());
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

    public ArrayList<HrSalaryDelegationRequest> findByFirstName(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrSalaryDelegationRequest.findByFirstName");
        query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
        try {
            ArrayList<HrSalaryDelegationRequest> request = new ArrayList<>(query.getResultList());
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

    public ArrayList<HrSalaryDelegationRequest> findByEmpName(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrSalaryDelegationRequest.findByName");
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrSalaryDelegationRequest> request = new ArrayList<>(query.getResultList());
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

    public HrSalaryDelegationRequest getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrSalaryDelegationRequest.findById");
        query.setParameter("id", request);
        System.err.println("===" + query.getResultList().size());
        try {
            HrSalaryDelegationRequest selectrequest = (HrSalaryDelegationRequest) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSalaryDelegationRequest> loadSalaryDelegationList(int status) {
        String queryStatus = " WHERE STATUS='" + status + "' ";
        Query query = em.createNativeQuery("SELECT * FROM HR_SALARY_DELEGATION_REQUEST "
                + queryStatus
                + "ORDER BY DATE_OF_DELEGATED DESC", HrSalaryDelegationRequest.class);
        try {
            return (List<HrSalaryDelegationRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

}
