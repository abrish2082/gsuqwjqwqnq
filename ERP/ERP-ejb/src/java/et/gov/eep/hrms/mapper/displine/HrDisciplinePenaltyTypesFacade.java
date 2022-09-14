/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.displine;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenaltyTypes;
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
public class HrDisciplinePenaltyTypesFacade extends AbstractFacade<HrDisciplinePenaltyTypes> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrDisciplinePenaltyTypesFacade() {
        super(HrDisciplinePenaltyTypes.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named query">
    public HrDisciplinePenaltyTypes findByPenalityCode(String toString) {
        HrDisciplinePenaltyTypes penaltyTypesObj;
        try {
            Query query = em.createNamedQuery("HrDisciplinePenaltyTypes.findById", HrDisciplinePenaltyTypes.class);
            query.setParameter("id", Integer.valueOf(toString));
            penaltyTypesObj = (HrDisciplinePenaltyTypes) query.getSingleResult();
            return penaltyTypesObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrDisciplinePenaltyTypes> findByPenalityCode(HrDisciplinePenaltyTypes disciplinePenaltyTypes) {
        List<HrDisciplinePenaltyTypes> disciplinePenaltyTypeses = null;
        try {
            Query query = em.createNamedQuery("HrDisciplinePenaltyTypes.findByPenaltyCodesLike", HrDisciplinePenaltyTypes.class);
            query.setParameter("penaltyCode", disciplinePenaltyTypes.getPenaltyCode().toUpperCase() + '%');
            disciplinePenaltyTypeses = (List<HrDisciplinePenaltyTypes>) query.getResultList();
            return disciplinePenaltyTypeses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrDisciplinePenaltyTypes> findByPenalityName(HrDisciplinePenaltyTypes disciplinePenaltyTypes) {
        List<HrDisciplinePenaltyTypes> disciplinePenaltyTypeses = null;
        try {
            Query query = em.createNamedQuery("HrDisciplinePenaltyTypes.findByPenaltyNameLike", HrDisciplinePenaltyTypes.class);
            query.setParameter("penaltyName", disciplinePenaltyTypes.getPenaltyName().toUpperCase() + '%');
            disciplinePenaltyTypeses = (List<HrDisciplinePenaltyTypes>) query.getResultList();
            return disciplinePenaltyTypeses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrDisciplinePenaltyTypes> getPenalityListByName(String penalityName) {
        List<HrDisciplinePenaltyTypes> disciplinePenalityTypeses = null;
        try {
            Query query = em.createNamedQuery("HrDisciplinePenaltyTypes.findByPenaltyName", HrDisciplinePenaltyTypes.class);
            query.setParameter("penaltyName", penalityName);
            disciplinePenalityTypeses = (List<HrDisciplinePenaltyTypes>) query.getResultList();
            return disciplinePenalityTypeses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkDuplicationByPenaltyName(HrDisciplinePenaltyTypes disciplinePenaltyTypes) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrDisciplinePenaltyTypes.findDuplicationByPenaltyName", HrDisciplinePenaltyTypes.class);
        query.setParameter("penaltyName", disciplinePenaltyTypes.getPenaltyName());
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
    //</editor-fold>

    public List<ColumnNameResolver> findColumns() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('HR_DISCIPLINE_PENALTY_TYPES')\n"
                + "and column_name NOT IN('ID','PENALTY_CLASSIFICATION','DESCRIPTION','ACTION_TAKER') ORDER BY column_name ASC");
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
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        return col;
    }

    public ArrayList<HrDisciplinePenaltyTypes> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue) {
        if (columnNameResolver.getCol_Name_FromTable() != null && columnValue != null) {
            Query query = em.createNativeQuery("SELECT * FROM HR_DISCIPLINE_PENALTY_TYPES\n"
                    + "where " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + columnValue + "%'  ", HrDisciplinePenaltyTypes.class);
            try {
                ArrayList<HrDisciplinePenaltyTypes> offenceName = new ArrayList(query.getResultList());
                return offenceName;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
