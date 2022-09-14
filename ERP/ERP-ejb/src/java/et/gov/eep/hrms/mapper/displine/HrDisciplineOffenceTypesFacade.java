/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.displine;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
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
public class HrDisciplineOffenceTypesFacade extends AbstractFacade<HrDisciplineOffenceTypes> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrDisciplineOffenceTypesFacade() {
        super(HrDisciplineOffenceTypes.class);
    }
    // <editor-fold defaultstate="collapsed" desc="Named query">

    public HrDisciplineOffenceTypes displayByOffenceCode(String toString) {

        HrDisciplineOffenceTypes OffenceTypesObj;
        try {
            Query query = em.createNamedQuery("HrDisciplineOffenceTypes.findById", HrDisciplineOffenceTypes.class);
            query.setParameter("id", Integer.valueOf(toString));
            OffenceTypesObj = (HrDisciplineOffenceTypes) query.getSingleResult();
            return OffenceTypesObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrDisciplineOffenceTypes> findByOffenceCode(HrDisciplineOffenceTypes offenceTypes) {
        List<HrDisciplineOffenceTypes> disciplineOffenceTypeses = null;
        try {
            Query query = em.createNamedQuery("HrDisciplineOffenceTypes.findByOffenceCodeLike", HrDisciplineOffenceTypes.class);
            query.setParameter("offenceCode", offenceTypes.getOffenceCode().toUpperCase() + '%');
            disciplineOffenceTypeses = (List<HrDisciplineOffenceTypes>) query.getResultList();
            return disciplineOffenceTypeses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findByOffenceName(HrDisciplineOffenceTypes offenceTypes) {
        List<HrDisciplineOffenceTypes> disciplineOffenceTypesList = null;
        try {
            Query query = em.createNamedQuery("HrDisciplineOffenceTypes.findByOffenceNameLike", HrDisciplineOffenceTypes.class);
            query.setParameter("offenceName", offenceTypes.getOffenceName().toUpperCase() + '%');
            disciplineOffenceTypesList = (List<HrDisciplineOffenceTypes>) query.getResultList();
            return disciplineOffenceTypesList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HrDisciplineOffenceTypes findByOffenceNames(HrDisciplineOffenceTypes offenceTypes) {
        Query query = em.createNamedQuery("HrDisciplineOffenceTypes.findByOffenceName");
        query.setParameter("offenceName", offenceTypes.getOffenceName());
        try {
            return (HrDisciplineOffenceTypes) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean checkDuplicationByName(HrDisciplineOffenceTypes offenceTypes) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrDisciplineOffenceTypes.findDuplicationByOffenceName", HrDisciplineOffenceTypes.class);
        query.setParameter("offenceName", offenceTypes.getOffenceName());
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

    public List<HrDisciplineOffenceTypes> findColumnNames() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('HR_DISCIPLINE_OFFENCE_TYPES')\n"
                + "and column_name NOT IN('ID','DESCRIPTION','WEGHIT') ORDER BY column_name ASC");
        try {
            List<HrDisciplineOffenceTypes> searchParamLists = new ArrayList<>();
            searchParamLists = query.getResultList();
            return searchParamLists;
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
    }

    public List<HrDisciplineOffenceTypes> searchOffenceType(HrDisciplineOffenceTypes offenceTypes) {
        System.out.println("column name" + offenceTypes.getColumnName());
        System.out.println("column value" + offenceTypes.getColumnValue());
        if (offenceTypes.getColumnValue() != null && offenceTypes.getColumnName() != null
                && !offenceTypes.getColumnValue().equals("") && !offenceTypes.getColumnName().equals("")) {
            System.out.println("inside if");
            Query query = em.createNativeQuery("SELECT * FROM HR_DISCIPLINE_OFFENCE_TYPES\n"
                    + "where " + offenceTypes.getColumnName().toLowerCase() + " = '" + offenceTypes.getColumnValue() + "' ", HrDisciplineOffenceTypes.class);
            try {
                List<HrDisciplineOffenceTypes> offenceLists = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    offenceLists = query.getResultList();
                }
                return offenceLists;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("HrDisciplineOffenceTypes.findAll");
            return query.getResultList();
        }
    }

    public List<ColumnNameResolver> findColumns() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('HR_DISCIPLINE_OFFENCE_TYPES')\n"
                + "and column_name NOT IN('ID','DESCRIPTION','PHASEOUT_PERIOD','WEGHIT') ORDER BY column_name ASC");
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

    public ArrayList<HrDisciplineOffenceTypes> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue) {
        if (columnNameResolver.getCol_Name_FromTable() != null && columnValue != null) {
            Query query = em.createNativeQuery("SELECT * FROM HR_DISCIPLINE_OFFENCE_TYPES\n"
                    + "where " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + columnValue + "%'  ", HrDisciplineOffenceTypes.class);
            try {
                ArrayList<HrDisciplineOffenceTypes> offenceName = new ArrayList(query.getResultList());
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
