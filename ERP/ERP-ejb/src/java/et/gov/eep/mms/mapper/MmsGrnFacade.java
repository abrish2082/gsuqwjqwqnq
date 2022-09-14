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
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsGrnFacade extends AbstractFacade<MmsGrn> {

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
    public MmsGrnFacade() {
        super(MmsGrn.class);
    }

    /**
     *
     * @param grnNo
     * @return
     */
    // <editor-fold defaultstate="collapsed" desc=" NamedQuery ">
    public ArrayList<MmsGrn> searchGrnNumber(MmsGrn grnNo) {
        Query query = em.createNamedQuery("MmsGrn.findByGrnNoLike");
        query.setParameter("grnNo", grnNo.getGrnNo() + '%');
        try {
            ArrayList<MmsGrn> listofGrn = new ArrayList(query.getResultList());
            return listofGrn;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param grnNo
     * @return
     */
    public MmsGrn getByGrnNo(MmsGrn grnNo) {
        Query query = em.createNamedQuery("MmsGrn.findByGrnNoLike");
        query.setParameter("grnNo", grnNo.getGrnNo());
        try {
            MmsGrn listofGrn = (MmsGrn) (query.getResultList().get(0));
            return listofGrn;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param selectedGrnNo
     * @return
     */
    public MmsGrn getGrnInfoByGrnNo(String selectedGrnNo) {
        Query query = em.createNamedQuery("MmsGrn.findByGrnNo");
        query.setParameter("grnNo", selectedGrnNo);
        try {
            if (query.getResultList().size() > 0) {
                MmsGrn gdnInfo = (MmsGrn) query.getResultList().get(0);

                return gdnInfo;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     *
     * @param selectedGrnNo
     * @return
     */
    public String getAllinspectionData(String selectedGrnNo) {
        Query query = em.createNamedQuery("MmsGrn.findinspectionResult");
        query.setParameter("grnNo", selectedGrnNo);
        try {
            if (query.getResultList().size() > 0) {
                String gdnInfo = query.getSingleResult().toString();

                return gdnInfo;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     *
     * @return
     */
    public MmsGrn getLastGrnNo() {
        Query query = em.createNamedQuery("MmsGrn.findByGRNIdMaximum");
        MmsGrn result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsGrn) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param storeInformation
     * @return
     */
    public ArrayList<MmsGrn> approvedGrnList(MmsStoreInformation storeInformation) {
        int status = 1;
        Query query = em.createNamedQuery("MmsGrn.findByApprovedStatus");
        query.setParameter("storeId", storeInformation.getStoreId());
        query.setParameter("approvedStatus", status);
        try {

            ArrayList<MmsGrn> mmsGrns = new ArrayList(query.getResultList());

            return mmsGrns;
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<MmsGrn> approvedGrnListByStoreAndStatus(MmsGrn grn, int status) {

        Query query = em.createNamedQuery("MmsGrn.findByStatus");
        query.setParameter("storeId", grn.getStoreId());
        query.setParameter("status", status);
        try {

            ArrayList<MmsGrn> mmsGrns = new ArrayList(query.getResultList());

            return mmsGrns;
        } catch (Exception ex) {
            return null;
        }
    }

    public MmsGrn findByGrnId(MmsGrn grnId) {
        Query query = em.createNamedQuery("MmsGrn.findByGrnId");
        query.setParameter("grnId", grnId.getGrnId());
        try {
            MmsGrn listofGrn = (MmsGrn) (query.getResultList().get(0));
            return listofGrn;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsGrn> searchByParameterStore(MmsStoreInformation storeInfoEntity) {
        Query query = em.createNamedQuery("MmsGrn.findByStoreId", MmsGrn.class);

        query.setParameter("storeId", storeInfoEntity);

        try {
            ArrayList<MmsGrn> GrnInformations = new ArrayList(query.getResultList());

            return GrnInformations;
        } catch (Exception ex) {

            return null;
        }
    }

    public List<MmsGrn> searchByParameterStoreAndProcessedBy(MmsStoreInformation storeInfoEntity) {
        Query query = em.createNamedQuery("MmsGrn.findByStoreIdAndProcessedBy", MmsGrn.class);

        query.setParameter("storeId", storeInfoEntity);
        query.setParameter("processedBy", storeInfoEntity.getProcessedBy());
        try {
            ArrayList<MmsGrn> GrnInformations = new ArrayList(query.getResultList());

            return GrnInformations;
        } catch (Exception ex) {

            return null;
        }
    }

    public List<MmsGrn> searchByParameterStoreAndGrnNo(MmsStoreInformation storeInfoEntity, MmsGrn grnEntity) {
        Query query = em.createNamedQuery("MmsGrn.findByStoreIdAndGrnNo", MmsGrn.class);

        query.setParameter("storeId", storeInfoEntity);
        query.setParameter("grnNo", grnEntity.getGrnNo() + '%');

        try {
            ArrayList<MmsGrn> GrnInformations = new ArrayList(query.getResultList());

            return GrnInformations;
        } catch (Exception ex) {

            return null;
        }
    }

    public List<MmsGrn> searchByParameterStoreAndGrnNoAndProcessedBy(MmsStoreInformation storeInfoEntity, MmsGrn grnEntity) {
        Query query = em.createNamedQuery("MmsGrn.findByStoreIdAndGrnNoProcessedBy", MmsGrn.class);

        query.setParameter("storeId", storeInfoEntity);
        query.setParameter("grnNo", grnEntity.getGrnNo() + '%');
        query.setParameter("ProcesssedBy", grnEntity.getProcessedBy());
        try {
            ArrayList<MmsGrn> GrnInformations = new ArrayList(query.getResultList());

            return GrnInformations;
        } catch (Exception ex) {

            return null;
        }
    }

    public List<MmsGrn> findGRNListByWfStatus(int status) {
        Query query = em.createNamedQuery("MmsGrn.findGrnListByWfStatus", MmsGrn.class);
        query.setParameter("status", status);
        try {
            ArrayList<MmsGrn> listofGrns = new ArrayList(query.getResultList());
            return listofGrns;
        } catch (Exception e) {
            return null;
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="NativeQuery">
    public List<MmsGrn> searchGRNList(MmsStoreInformation mmsStoreInformation) {
        try {
            Query query = em.createNativeQuery("SELECT DISTINCT mg.GRN_NO, "
                    + "  mg.GRN_ID "
                    + "FROM MMS_GRN mg "
                    + "INNER JOIN MMS_GRN_DETAIL mgd "
                    + "ON mg.GRN_ID = mgd.GRN_ID "
                    + "INNER JOIN MMS_ITEM_REGISTRATION mir "
                    + "ON mir.MATERIAL_ID = mgd.ITEM_ID "
                    + "LEFT JOIN FMS_STOCK_LEDGER_CARD slc "
                    + "ON mgd.GRN_DETAIL_ID = slc.GRN_DETAIL_ID "
                    + "WHERE slc.REF_NO IS NULL "
                    //                    + "AND mir.ITEM_GROUP  = 0 "
                    + "AND mg.STATUS=3"
                    + "and mg.STORE_ID='" + mmsStoreInformation.getStoreId() + "'", MmsGrn.class);
            return (List<MmsGrn>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getMmsGrnColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_GRN')\n"
                + "   and COLUMN_NAME NOT IN ('DEP_ID','GRN_ID','STORE_ID','DELIVERY_OPTION','PROCESSED_ID')\n"
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

    public List<MmsGrn> getGrnListsByParameter(ColumnNameResolver columnNameResolver, MmsGrn mmsgrn, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsGrn> colValueLists = new ArrayList<>();
        if (mmsgrn.getColumnName() != null && !mmsgrn.getColumnName().equals("")
                && mmsgrn.getColumnValue() != null && !mmsgrn.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_GRN\n"
                    + "   WHERE " + mmsgrn.getColumnName().toLowerCase() + "='" + mmsgrn.getColumnValue() + "'"
                    + "and " + mmsgrn.getProcessedBy() + "='" + mmsgrn.getProcessedBy() + "'", MmsGrn.class);

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
            Query query = em.createNamedQuery("MmsGrn.findByProcessedBy");
            query.setParameter("processedBy", mmsgrn.getProcessedBy());
            colValueLists = query.getResultList();
            return colValueLists;
        }
    }
// </editor-fold>
}
