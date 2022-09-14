/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.succession;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.succession.HrSmCompetency;
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
public class HrSmCompetencyFacade extends AbstractFacade<HrSmCompetency> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSmCompetencyFacade() {
        super(HrSmCompetency.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query"> 
    public List<HrSmCompetency> findbycompetencyname(HrSmCompetency hrSmCompetency) {
        Query query = em.createNamedQuery("HrSmCompetency.findByCompetencyNameLike");
        query.setParameter("competencyName", hrSmCompetency.getCompetencyName().toUpperCase() + '%');
        try {
            List<HrSmCompetency> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean searchduplicate(HrSmCompetency hrSmCompetency) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrSmCompetency.searchbyduplicate", HrSmCompetency.class);
        query.setParameter("competencyName", hrSmCompetency.getCompetencyName());
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = true;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<HrSmCompetency> findAll(HrSmCompetency hrSmCompetency) {
        Query query = em.createNamedQuery("HrSmCompetency.findAll");
        try {
            ArrayList<HrSmCompetency> request = new ArrayList<>(query.getResultList());
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

    public ArrayList<HrSmCompetency> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue) {
        if (columnNameResolver.getCol_Name_FromTable() != null && columnValue != null) {
            Query query = em.createNativeQuery("SELECT * FROM HR_SM_COMPETENCY\n"
                    + "where " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + columnValue + "%'  ", HrSmCompetency.class);
            try {
                ArrayList<HrSmCompetency> competencyName = new ArrayList(query.getResultList());
                return competencyName;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public List<ColumnNameResolver> findColumns() {
   Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('HR_SM_COMPETENCY')\n"
                + "and column_name NOT IN('ID','DESCRIPTION','PREPARED_BY','PREPARED_ON') ORDER BY column_name ASC");
        try {
            List<String> RetrivedColumns = new ArrayList<>();
            RetrivedColumns = query.getResultList();
            List<ColumnNameResolver> ResolvedCol_list = new ArrayList<>();
            if (RetrivedColumns.size() > 0) {
                for (int i = 0; i < RetrivedColumns.size(); i++) {
                    ColumnNameResolver obj = new ColumnNameResolver();
                    obj.setCol_Name_FromTable(RetrivedColumns.get(i));
                    obj.setParsed_Col_Name(ColumnParser(RetrivedColumns.get(i)).toLowerCase());
                    ResolvedCol_list.add(obj);
                }
            }
            return ResolvedCol_list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        return col;
    }
}
