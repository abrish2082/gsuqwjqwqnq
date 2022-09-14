/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.insure;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.insurance.HrInsuranceInjuredEmployee;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author meles
 */
@Stateless
public class HrInsuranceInjuredEmployeeFacade extends AbstractFacade<HrInsuranceInjuredEmployee> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrInsuranceInjuredEmployeeFacade() {
        super(HrInsuranceInjuredEmployee.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMmplementatin">
    public List<HrInsuranceInjuredEmployee> findtype(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        Query query = em.createNamedQuery("HrInsuranceInjuredEmployee.findByType", HrInsuranceInjuredEmployee.class);
        query.setParameter("type", hrInsuranceInjuredEmployee.getType().toUpperCase());
        try {

            return (List<HrInsuranceInjuredEmployee>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrInsuranceInjuredEmployee> findakkll(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        Query query = em.createNamedQuery("HrInsuranceInjuredEmployee.ttt", HrInsuranceInjuredEmployee.class);
        query.setParameter("ttt", hrInsuranceInjuredEmployee.getType());
        try {

            return (List<HrInsuranceInjuredEmployee>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrInsuranceInjuredEmployee getSelectedRequest(int id) {
        Query query = em.createNamedQuery("HrInsuranceInjuredEmployee.findById");
        query.setParameter("id", id);
        try {
            HrInsuranceInjuredEmployee selectrequest = (HrInsuranceInjuredEmployee) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrInsuranceInjuredEmployee> findByfullname(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        Query query = em.createNamedQuery("HrInsuranceInjuredEmployee.findByFullName");
        query.setParameter("fullName", hrInsuranceInjuredEmployee.getFullName().toUpperCase() + '%');
        try {
            ArrayList<HrInsuranceInjuredEmployee> request = new ArrayList<>(query.getResultList());
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

    public List<HrInsuranceInjuredEmployee> findByempname(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        Query query = em.createNamedQuery("HrInsuranceInjuredEmployee.empname");
        query.setParameter("empname", hrInsuranceInjuredEmployee.getEmpId().getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrInsuranceInjuredEmployee> request = new ArrayList<>(query.getResultList());
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

    public List<HrInsuranceInjuredEmployee> findAll(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee) {
        Query query = em.createNamedQuery("HrInsuranceInjuredEmployee.findAll");
        try {
            ArrayList<HrInsuranceInjuredEmployee> request = new ArrayList<>(query.getResultList());
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

    public List<HrInsuranceInjuredEmployee> findByempname(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrInsuranceInjuredEmployee.empname");
        query.setParameter("empname", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrInsuranceInjuredEmployee> request = new ArrayList<>(query.getResultList());
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

//</editor-fold>
    public List<ColumnNameResolver> findColumns() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('HR_INSURANCE_INJURED_EMPLOYEE')\n"
                + "and column_name  IN('FULL_NAME','REGISTRATION_NO','ACCIDENT_DATE','ACCIDENT_PLACE','ACCIDENT_INFORMED_DATE','WITNESS1','WITNESS2','WITNESS3','TYPE','PREPARED_ON','PREPARED_BY') ORDER BY column_name ASC");
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

    public List<HrInsuranceInjuredEmployee> findInjuredEmployee(HrInsuranceInjuredEmployee hrInsuranceInjuredEmployee, String col_Name_FromTable) {
        System.out.println("col_Name_FromTable==" + col_Name_FromTable);
        System.out.println("hrEmployees.getCol_Value()==" + hrInsuranceInjuredEmployee.getCol_Value());
        if (col_Name_FromTable != null && hrInsuranceInjuredEmployee.getCol_Value() != null) {

            Query query = em.createNativeQuery("SELECT * FROM HR_INSURANCE_INJURED_EMPLOYEE\n"
                    + "where " + col_Name_FromTable.toLowerCase() + " LIKE'" + hrInsuranceInjuredEmployee.getCol_Value() + "%' ", HrInsuranceInjuredEmployee.class);
            query.setParameter("employmentType", "Contract");
            try {
                ArrayList<HrInsuranceInjuredEmployee> employeeInformations = new ArrayList(query.getResultList());
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
