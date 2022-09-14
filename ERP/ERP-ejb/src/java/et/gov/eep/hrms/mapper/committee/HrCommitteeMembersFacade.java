/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.committee;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
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
public class HrCommitteeMembersFacade extends AbstractFacade<HrCommitteeMembers> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrCommitteeMembersFacade() {
        super(HrCommitteeMembers.class);
    }

    public List<HrCommitteeMembers> getCommitteeMembers(HrCommittees comName) {

        List<HrCommitteeMembers> hrCommitteeMembers = null;
        try {
            Query query = em.createNamedQuery("HrCommitteeMembers.findByCommittee", HrCommitteeMembers.class);
            query.setParameter("id", comName.getId());
            hrCommitteeMembers = (List<HrCommitteeMembers>) query.getResultList();
            return hrCommitteeMembers;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<HrEmployees> searchEmp(String hrEmployee) {
        Query query = em.createNamedQuery("HrEmployees.findByFirstNameLike", HrEmployees.class);
        query.setParameter("firstName", hrEmployee.toUpperCase() + '%');
        try {
            ArrayList<HrEmployees> employeeInformations = new ArrayList(query.getResultList());
            return employeeInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmployees getByFName(HrEmployees hrEmployees) {
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

    public List<HrCommitteeMembers> getActiveCommitteeMembers() {
        List<HrCommitteeMembers> hrCommitteeMembers = null;
        try {
            Query query = em.createNamedQuery("HrCommitteeMembers.findByStatus", HrCommitteeMembers.class);
            query.setParameter("status", "Active");
            hrCommitteeMembers = (List<HrCommitteeMembers>) query.getResultList();
            return hrCommitteeMembers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrCommitteeMembers> getCommitteeMembersById(HrCommittees hrCommittees) {
        Query query = em.createNativeQuery("SELECT HR_COMMITTEE_MEMBERS.ID,\n"
                + "  HR_COMMITTEE_MEMBERS.EMP_ID,\n"
                + "  HR_EMPLOYEES.FIRST_NAME\n"
                + "FROM HR_COMMITTEES\n"
                + "INNER JOIN HR_COMMITTEE_MEMBERS\n"
                + "ON HR_COMMITTEES.ID = HR_COMMITTEE_MEMBERS.COMMITTEE_ID\n"
                + "INNER JOIN HR_EMPLOYEES\n"
                + "ON HR_EMPLOYEES.ID = HR_COMMITTEE_MEMBERS.EMP_ID\n"
                + "WHERE HR_COMMITTEE_MEMBERS.COMMITTEE_ID= '" + hrCommittees.getId() + "'", HrCommitteeMembers.class);
        try {
            ArrayList<HrCommitteeMembers> committeeMember = new ArrayList<>();
            committeeMember = new ArrayList<>(query.getResultList());
            return committeeMember;

        } catch (Exception ex) {
            return null;
        }
    }
}
