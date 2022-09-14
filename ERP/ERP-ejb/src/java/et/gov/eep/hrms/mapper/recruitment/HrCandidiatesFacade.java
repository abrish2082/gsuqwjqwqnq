/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.recruitment;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.lookup.HrLuLanguages;
import et.gov.eep.hrms.entity.lookup.HrLuNationalities;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
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
public class HrCandidiatesFacade extends AbstractFacade<HrCandidiates> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrCandidiatesFacade() {
        super(HrCandidiates.class);
    }

    public List<HrAdvertisements> batchCodes(String type) {
        Query query = em.createNativeQuery("SELECT adv.* "
                + " FROM HR_ADVERTISEMENTS adv "
                + " WHERE adv.ADVERT_TYPE = '" + type + "'"
                + " order by adv.ID DESC", HrAdvertisements.class);
        try {
            return (List<HrAdvertisements>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

//    AND h.endDate >= CURRENT_DATE
    public List<HrAdvertisedJobs> advertizedJobs(int advertId) {
        Query query = em.createNativeQuery("SELECT HR_ADVERTISED_JOBS.*,"
                + " HR_JOB_TYPES.JOB_TITLE "
                + " FROM  HR_ADVERTISED_JOBS, "
                + "       HR_JOB_TYPES "
                + " WHERE HR_ADVERTISED_JOBS.JOB_ID=HR_JOB_TYPES.ID "
                + "       AND HR_ADVERTISED_JOBS.ADVERT_ID='" + advertId + "'",
                HrAdvertisedJobs.class);
        try {
            return (List<HrAdvertisedJobs>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrAdvertisements findBatchCode(HrAdvertisements hrAdvertisements) {
        Query query = em.createNamedQuery("HrAdvertisements.findByBatchCode");
        query.setParameter("batchCode", hrAdvertisements.getBatchCode());
        try {
            HrAdvertisements advertisements = (HrAdvertisements) query.getResultList().get(0);
            return advertisements;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrLuNationalities findNationality(HrLuNationalities hrLuNationalities) {
        Query query = em.createNamedQuery("HrLuNationalities.findByNationality");
        query.setParameter("nationality", hrLuNationalities.getNationality());
        try {
            HrLuNationalities Nationalityname = (HrLuNationalities) query.getResultList().get(0);
            return Nationalityname;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrLuEducTypes> findEducationTypes() {
        Query query = em.createNamedQuery("HrLuEducTypes.findAll");
        try {
            ArrayList<HrLuEducTypes> types = new ArrayList(query.getResultList());
            return types;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrLuEducLevels> findEducationLeves() {
        Query query = em.createNamedQuery("HrLuEducLevels.findAll");
        try {
            ArrayList<HrLuEducLevels> levels = new ArrayList(query.getResultList());
            return levels;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrCandidiates getByCandidateId(HrCandidiates candidiates) {
        Query query = em.createNamedQuery("HrCandidiates.findById", HrCandidiates.class);
        query.setParameter("id", candidiates.getId());
        try {
            HrCandidiates Candidate = (HrCandidiates) query.getResultList().get(0);
            return Candidate;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrCandidiates> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String Col_Value) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + Col_Value);
        if (columnNameResolver.getCol_Name_FromTable() != null && Col_Value != null) {
            Query query = em.createNativeQuery("SELECT * FROM HR_CANDIDIATES\n"
                    + "where " + columnNameResolver.getCol_Name_FromTable().toLowerCase() + " LIKE'" + Col_Value + "%'  ", HrCandidiates.class);
            try {
                ArrayList<HrCandidiates> employeeInformations = new ArrayList(query.getResultList());
                return employeeInformations;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public HrCandidiates getByfirstName(HrCandidiates candidiates) {
        Query query = em.createNamedQuery("HrCandidiates.findByFirstName", HrCandidiates.class);
        query.setParameter("firstName", candidiates.getFirstName());
        try {
            HrCandidiates candi = (HrCandidiates) (query.getResultList().get(0));
            return candi;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrLuLanguages> findAllHrLuLanguages() {
        Query query = em.createNamedQuery("HrLuLanguages.findAll");
        try {
            ArrayList<HrLuLanguages> list = new ArrayList(query.getResultList());
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="recruitment for employee">
    public List<HrCandidiates> readCandidiates(int status1, int status2) {
        Query query = em.createNativeQuery("SELECT HR_CANDIDIATES.* "
                //                + "FIRST_NAME || ' ' | MIDDLE_NAME || ' ' || LAST_NAME AS FULLNAME "
                + "FROM HR_CANDIDIATES "
                + "WHERE STATUS=? OR STATUS=? ", HrCandidiates.class);
        query.setParameter(1, status1);
        query.setParameter(2, status2);
        try {
            return (List<HrCandidiates>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    public List<ColumnNameResolver> findColumns() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('HR_CANDIDIATES')\n"
                + "and column_name NOT IN('ID','MARITAL_STATUS','SKILL','HOBBY','ADDITIONAL_INFORMATION','NO_OF_PAGE','ADDRESS_ID','ADV_JOB_ID','NATIONALITY','STATUS','SEX') ORDER BY column_name ASC");
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
}
