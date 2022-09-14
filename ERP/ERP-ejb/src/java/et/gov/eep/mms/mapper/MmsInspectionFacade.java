/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsInspectionFacade extends AbstractFacade<MmsInspection> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public MmsInspectionFacade() {
        super(MmsInspection.class);
    }

    /**
     *
     * @param information
     * @return
     */
    //<editor-fold defaultstate="collapsed" desc="NamedQuery">
    public ArrayList<MmsStoreInformation> searchStoreInformation(MmsStoreInformation information) {
        Query query = em.createNamedQuery("MmsStoreInformation.SearchByStoreName", MmsStoreInformation.class);
        query.setParameter("storeName", information.getStoreName() + '%');
        try {
            ArrayList<MmsStoreInformation> storeInformations = new ArrayList(query.getResultList());
            return storeInformations;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param inspection
     * @return
     */
    public ArrayList<MmsInspection> searchByinspectionNo(MmsInspection inspection) {
        Query query = em.createNamedQuery("MmsInspection.findByInspectionNumberLike", MmsInspection.class);
        query.setParameter("inspectionNo", inspection.getInspectionNo() + '%');
        try {
            ArrayList<MmsInspection> listofinspectionNo = new ArrayList(query.getResultList());
            return listofinspectionNo;
        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<MmsInspection> searchByinspectionNoAndProcessedBy(MmsInspection inspection) {
        Query query = em.createNamedQuery("MmsInspection.findByInspectionNumberLikeAndProcessedBy", MmsInspection.class);
        query.setParameter("inspectionNo", inspection.getInspectionNo() + '%');
        query.setParameter("processsedBy", inspection.getProcessedBy());
        try {
            ArrayList<MmsInspection> listofinspectionNo = new ArrayList(query.getResultList());
            return listofinspectionNo;
        } catch (Exception e) {
            return null;
        }

    }

    public List<MmsInspection> searchAllByPreparerId(MmsInspection inspection) {
        Query query = em.createNamedQuery("MmsInspection.findAllByPreparerId", MmsInspection.class);

        query.setParameter("processedBy", inspection.getProcessedBy());

        try {
            ArrayList<MmsInspection> ItemList = new ArrayList(query.getResultList());

            return ItemList;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param inspection
     * @return
     */
    public String getInpsectionId(String inspection) {
        String inspId = null;
        Query query = em.createNamedQuery("MmsInspection.findByInspectionIdByInspNo", MmsInspection.class);
        query.setParameter("inspectionNo", inspection);
        try {
            inspId = query.getSingleResult().toString();
            return inspId;
        } catch (Exception e) {
            return inspId;
        }

    }

    /**
     *
     * @return
     */
    public MmsInspection getLastinspectionNo() {
        MmsInspection result = null;
        Query query = em.createNamedQuery("MmsInspection.findByInspectionIdMaximum", MmsInspection.class);
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsInspection) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param selectedInspNo
     * @return
     */
    public MmsInspection getByInspectionNumber(String selectedInspNo) {
        MmsInspection result = null;
        Query query = em.createNamedQuery("MmsInspection.findByInspectyionNo", MmsInspection.class);
        query.setParameter("inspectionNo", selectedInspNo);

        try {
            if (query.getResultList().size() > 0) {
                result = (MmsInspection) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param selectedInspNo
     * @return
     */
    public ArrayList<MmsInspection> searchByinspectionNo(String selectedInspNo) {
        ArrayList<MmsInspection> result = null;
        Query query = em.createNamedQuery("MmsInspection.findByInspectyionNo");

        try {
            if (query.getResultList().size() > 0) {
                result = new ArrayList(query.getResultList());
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param a
     * @return
     */
    public ArrayList<MmsInspection> findAllByStatus(int a) {
        ArrayList<MmsInspection> result = null;
        Query query = em.createNamedQuery("MmsInspection.finAllWithzero", MmsInspection.class);
        query.setParameter("status", a);

        try {
            if (query.getResultList().size() > 0) {
                result = new ArrayList(query.getResultList());
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param employeinfo
     * @return
     */
    public List<MmsInspection> searchInspectionByParameterInspectionNo(MmsInspection inspection) {
        Query query = em.createNamedQuery("MmsInspection.findByInspectionNumberLike");
        query.setParameter("inspectionNo", '%' + inspection.getInspectionNo() + '%');

        try {
            ArrayList<MmsInspection> inspectionList = new ArrayList(query.getResultList());
            return inspectionList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsInspection getInspectionInfoByInspId(MmsInspection inspection) {
        MmsInspection result = null;
        Query query = em.createNamedQuery("MmsInspection.findByInspectionId", MmsInspection.class);
        query.setParameter("inspectionId", inspection.getInspectionId());

        try {
            if (query.getResultList().size() > 0) {
                result = (MmsInspection) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {

            return null;
        }
    }

    public List<MmsInspection> findInspectionListByWfStatus(int status) {
        Query query = em.createNamedQuery("MmsInspection.findInspectionListByWfStatus", MmsInspection.class);
        query.setParameter("status", status);
        try {
            ArrayList<MmsInspection> listofinspection = new ArrayList(query.getResultList());
            return listofinspection;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsInspection> findinspactionNo(MmsInspection inspaction) {
        Query query = em.createNamedQuery("MmsInspection.findByInspectionNo", MmsInspection.class);
        query.setParameter("inspectionNo", inspaction.getInspectionNo());
        try {
            ArrayList<MmsInspection> listofinspection = new ArrayList(query.getResultList());
            return listofinspection;
        } catch (Exception e) {
            return null;
        }
    }
    //</editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="NativeQuery">

    public List<String> getMmsInspectionColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_INSPECTION')\n"
                + "   and COLUMN_NAME NOT IN ('INSPECTION_ID','APPROVAL_DATE','APPROVER_REMARK','STORE_ID','CONTRACT_ID','COMMITTEE_ID','PURCHASE_O_ID')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<String> colNameLists = new ArrayList<>();
            colNameLists = query.getResultList();
            return colNameLists;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public List<MmsInspection> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, MmsInspection inspection, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsInspection> colValueLists = new ArrayList<>();
        if (inspection.getColumnName() != null && !inspection.getColumnName().equals("")
                && inspection.getColumnValue() != null && !inspection.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_INSPECTION\n"
                    + "   WHERE " + inspection.getColumnName().toLowerCase() + "='" + inspection.getColumnValue() + "'"
                    + "and " + inspection.getProcessedBy() + "='" + inspection.getProcessedBy() + "'", MmsInspection.class);
            try {
                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("MmsInspection.findAllByProcessedBy");
            query.setParameter("processedBy", inspection.getProcessedBy());
            colValueLists = query.getResultList();
            return colValueLists;
        }
    }
    // </editor-fold>
}
