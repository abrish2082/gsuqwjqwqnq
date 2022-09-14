
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsInsurance;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsInsuranceFacade extends AbstractFacade<MmsInsurance> {

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
    public MmsInsuranceFacade() {
        super(MmsInsurance.class);
    }

    /**
     *
     * @param insuranceEntity
     * @return
     */
    public ArrayList<MmsInsurance> searchByInsuranceNo(MmsInsurance insuranceEntity) {
        Query query = em.createNamedQuery("MmsInsurance.findByInsNoLike");
        query.setParameter("insNo", insuranceEntity.getInsNo() + '%');
        try {
            ArrayList<MmsInsurance> listofInsuranceNo = new ArrayList(query.getResultList());
            return listofInsuranceNo;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param insuranceEntity
     * @return
     */
    public List<MmsInsurance> searchInsuranceByParameterPrefix(MmsInsurance insuranceEntity) {
        Query query = em.createNamedQuery("MmsInsurance.findByAllParameters");
        query.setParameter("insNo", '%' + insuranceEntity.getInsNo() + '%');
        try {
            ArrayList<MmsInsurance> insuranceList = new ArrayList(query.getResultList());
            return insuranceList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public MmsInsurance getLastInsuranceId() {
        Query query1 = em.createNamedQuery("MmsInsurance.findByInsIdMaximum");

        MmsInsurance result = null;

        try {
            if (query1.getResultList().size() > 0) {
                result = (MmsInsurance) query1.getResultList().get(0);
            } else {
                return result;
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MmsInsurance getSelectedRequest(Integer insId) {
        Query query = em.createNamedQuery("MmsInsurance.findByInsId");
        query.setParameter("insId", insId);
        System.err.println("===" + query.getResultList().size());
        try {
            MmsInsurance selectrequest = (MmsInsurance) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsInsurance> findInsListByWfStatus(int insStatus) {
        Query query = em.createNamedQuery("MmsInsurance.findInsListByWfStatus", MmsInsurance.class);
        query.setParameter("insStatus", insStatus);
        try {
            ArrayList<MmsInsurance> listofins = new ArrayList(query.getResultList());
            return listofins;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsInsurance> searchAllTransmissionsInfoByPreparerId(Integer authorizedBy) {
        Query query = em.createNamedQuery("MmsInsurance.findAllByPreparerId", MmsInsurance.class);

        query.setParameter("authorizedBy", authorizedBy);
        System.out.println("======@facade====" + authorizedBy);
        try {
            ArrayList<MmsInsurance> LocList = new ArrayList(query.getResultList());
            return LocList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
  public List<MmsInsurance> getInsuranceListsByParameter(MmsInsurance insuranceEntity) {
      List<MmsInsurance> colValueLists = new ArrayList<>();
        if (insuranceEntity.getColumnName() != null  && insuranceEntity.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM MMS_INSURANCE\n"
                    + "   WHERE " + insuranceEntity.getColumnName().toLowerCase() + "='" + insuranceEntity.getColumnValue() + "'"
                    + " ", MmsInsurance.class);
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
            Query query = em.createNamedQuery("MmsInsurance.findByAuthorizedBy");
            query.setParameter("AuthorizedBy", insuranceEntity.getAuthorizedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_INSURANCE')\n"
                + "   and COLUMN_NAME NOT IN ('TO_BE_ISSUED','REG_DATE','ISSUED2_DATE','INS_STATUS','AUTHORIZED2_DATE')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<String> RetrivedColumns = new ArrayList<>();
            RetrivedColumns = query.getResultList();
            List<ColumnNameResolver> ResolvedCol_list = new ArrayList<>();
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
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
        
    }
     public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }
}
  

