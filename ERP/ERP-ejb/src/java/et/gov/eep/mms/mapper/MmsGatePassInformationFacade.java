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
import et.gov.eep.mms.entity.MmsGatePassInformation;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsGatePassInformationFacade extends AbstractFacade<MmsGatePassInformation> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public MmsGatePassInformationFacade() {
        super(MmsGatePassInformation.class);
    }

    /**
     *
     * @param gatePassEntity
     * @return
     */
    //<editor-fold defaultstate="collapsed" desc="NamedQuery">
    public ArrayList<MmsGatePassInformation> searchGatePassByParameterPrefix(MmsGatePassInformation gatePassEntity) {
        Query query = em.createNamedQuery("MmsGatePassInformation.findByAllParameters");
        query.setParameter("gatePassNo", gatePassEntity.getGatePassNo() + '%');
        try {
            ArrayList<MmsGatePassInformation> gatePassList = new ArrayList(query.getResultList());
            return gatePassList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsGatePassInformation> searchGatePassByParameterPrefixAndProcessedBy(MmsGatePassInformation gatePassEntity) {
        Query query = em.createNamedQuery("MmsGatePassInformation.findByAllParametersAndProcessedBy");
        query.setParameter("gatePassNo", gatePassEntity.getGatePassNo() + '%');
        query.setParameter("processedBy", gatePassEntity.getProcessedBy());
        try {
            ArrayList<MmsGatePassInformation> gatePassList = new ArrayList(query.getResultList());
            return gatePassList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsGatePassInformation> searchGatePassByParameterContains(MmsGatePassInformation gatePassEntity) {
        Query query = em.createNamedQuery("MmsGatePassInformation.findByAllParameters");
        query.setParameter("gatePassNo", '%' + gatePassEntity.getGatePassNo() + '%');
        try {
            ArrayList<MmsGatePassInformation> gatePassList = new ArrayList(query.getResultList());
            return gatePassList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsGatePassInformation> searchGatePassByProcessedBy(MmsGatePassInformation gatePassEntity) {
        Query query = em.createNamedQuery("MmsGatePassInformation.findByProcessedBy");
        query.setParameter("Processedby", '%' + gatePassEntity.getProcessedBy() + '%');
        try {
            ArrayList<MmsGatePassInformation> gatePassList = new ArrayList(query.getResultList());
            return gatePassList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public MmsGatePassInformation getLastGetpassNo() {
        Query query = em.createNamedQuery("MmsGatePassInformation.findByGatepassIdMaximum");
        MmsGatePassInformation result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (MmsGatePassInformation) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="NativeQuery">

    public List<String> getMmsGatePassInfoColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_GATE_PASS_INFORMATION')\n"
                + "   and COLUMN_NAME NOT IN ('GATE_PASS_ID','APPROVED_BY','APPROVER_REMARK','SIV_ID','ISIV_ID','STORE_ID','DEPARTMENT')\n"
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

    public List<MmsGatePassInformation> getGatePassListsByParameter(ColumnNameResolver columnNameResolver, MmsGatePassInformation gatePassEntity, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsGatePassInformation> colValueLists = new ArrayList<>();
        if (gatePassEntity.getColumnName() != null && !gatePassEntity.getColumnName().equals("")
                && gatePassEntity.getColumnValue() != null && !gatePassEntity.getColumnValue().equals("")) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_GATE_PASS_INFORMATION\n"
                    + "   WHERE " + gatePassEntity.getColumnName().toLowerCase() + "='" + gatePassEntity.getColumnValue() + "'"
                    + "and " + gatePassEntity.getProcessedBy() + "='" + gatePassEntity.getProcessedBy() + "'", MmsGatePassInformation.class);
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
            Query query = em.createNamedQuery("MmsGatePassInformation.findByProcessedBy");
            query.setParameter("processedBy", gatePassEntity.getProcessedBy());
            colValueLists = query.getResultList();
            return colValueLists;
        }
    }
 // </editor-fold>
}
