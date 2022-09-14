/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.committee;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import et.gov.eep.hrms.entity.lookup.HrLuCommitteeTypes;
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
public class HrCommitteesFacade extends AbstractFacade<HrCommittees> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrCommitteesFacade() {
        super(HrCommittees.class);
    }

    public ArrayList<HrCommittees> getCommitee() {
        Query query = em.createNamedQuery("HrCommittees.findAll");
        try {
            ArrayList<HrCommittees> committeeses = new ArrayList<>(query.getResultList());
            return committeeses;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrCommittees getCommittee(HrCommittees comName) {
        Query query = em.createNamedQuery("HrCommittees.findByCommitteeName");
        query.setParameter("committeeName", comName.getCommitteeName());
        try {
            HrCommittees glList = (HrCommittees) query.getResultList().get(0);
            return glList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrCommittees getCommitteeId(HrCommittees comName) {
        Query query = em.createNamedQuery("HrCommittees.findByCommitteeName");
        query.setParameter("committeeName", comName.getId());
        try {
            HrCommittees glList = (HrCommittees) query.getResultList().get(0);
            return glList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrCommittees> getCommitteeList() {
        List<HrCommittees> committeLists = null;
        try {
            Query query = em.createNamedQuery("HrCommittees.findByStatus", HrCommittees.class);
            query.setParameter("status", "Active");
            committeLists = (List<HrCommittees>) query.getResultList();
            return committeLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<HrCommittees> findByCommitteeName(HrLuCommitteeTypes hrLuCommitteeTypes) {
        List<HrCommittees> hrCommitteeses = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("HrCommittees.findByCommitteeNameLike", HrCommittees.class);
            query.setParameter("committeeName", hrLuCommitteeTypes.getDescription().toUpperCase() + '%');
            hrCommitteeses = (List<HrCommittees>) query.getResultList();
            return hrCommitteeses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HrCommittees findByCommitteType(HrLuCommitteeTypes hrLuCommitteeTypes) {
        try {
            Query query = em.createNamedQuery("HrCommittees.findByCommitteType", HrCommittees.class);
            query.setParameter("committeeTypeId", hrLuCommitteeTypes);
            return (HrCommittees) query.getSingleResult();
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }

    public List<HrCommittees> findByCommitteeName(HrCommittees hrCommittees) {
        List<HrCommittees> hrCommitteeses = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("HrCommittees.findByCommitteeNameLike", HrCommittees.class);
            query.setParameter("committeeName", hrCommittees.getCommitteeName().toUpperCase() + '%');
            hrCommitteeses = (List<HrCommittees>) query.getResultList();
            return hrCommitteeses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public boolean getCommitteeNameDup(HrCommittees hrCommittees) {
//        boolean deblicate;
//         Query query = em.createNamedQuery("HrCommittees.findByCommitteeName", HrCommittees.class);
//           query.setParameter("committeeName", hrCommittees.getCommitteeName());
//           try{
//               if(query.getResultList().size() > 0){
//                   deblicate= true;
//               }
//               else{
//                   deblicate= false;
//               }
//               return deblicate;
//           }
//           catch(Exception e){
//               e.printStackTrace();
//           }
//           return false;
//    }
    public List<ColumnNameResolver> findColumns() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('HR_COMMITTEES')\n"
                + "and column_name  IN('COMMITTEE_NAME','TYPE','RESPONSIBILITY','ESTABLISHED_DATE','VALID_FROM','VALID_TO','PREPARED_ON') ORDER BY column_name ASC");
        try {
            List<String> RetrivedColumns = new ArrayList<>();
            RetrivedColumns = query.getResultList();
            System.out.println("RetrivedColumns" + RetrivedColumns);
            List<ColumnNameResolver> ResolvedCol_list = new ArrayList<>();
            System.out.println("RetrivedColumns.size()==" + RetrivedColumns.size());
            if (RetrivedColumns.size() > 0) {
                for (int i = 0; i < RetrivedColumns.size(); i++) {
                    ColumnNameResolver obj = new ColumnNameResolver();
                    System.out.println("RetrivedColumns.get(i)===" + RetrivedColumns.get(i));
                    obj.setCol_Name_FromTable(RetrivedColumns.get(i));
                    obj.setParsed_Col_Name(ColumnParser(RetrivedColumns.get(i)).toLowerCase());
                    ResolvedCol_list.add(obj);
                }
            }
            return ResolvedCol_list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public List<HrCommittees> findByParameteredColumnValue(String col_Name_FromTable, String col_Value) {
       System.out.println("col_Name_FromTable==" + col_Name_FromTable);
        System.out.println("hrEmployees.getCol_Value()==" + col_Value);
        if (col_Name_FromTable != null && col_Value != null) {

            Query query = em.createNativeQuery("SELECT * FROM HR_COMMITTEES\n"
                    + "where UPPER(" + col_Name_FromTable.toLowerCase() + ")LIKE'" +col_Value.toUpperCase() + "%' ", HrCommittees.class);
            try {
                ArrayList<HrCommittees> employeeInformations = new ArrayList(query.getResultList());
                return employeeInformations;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
