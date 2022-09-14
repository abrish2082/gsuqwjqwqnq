/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsLuWareHouse;
import et.gov.eep.prms.entity.PrmsClaimRecordingForm;
import et.gov.eep.prms.entity.PrmsContainerAgreement;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import et.gov.eep.prms.entity.PrmsPortEntry;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PrmsContainerAgreementFacade extends AbstractFacade<PrmsContainerAgreement> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsContainerAgreementFacade() {
        super(PrmsContainerAgreement.class);
    }

    public ArrayList<PrmsContainerAgreement> SearchContainer(PrmsContainerAgreement prmsContainerAgreement) {
        Query query = em.createNamedQuery("PrmsContainerAgreement.searchfindByFormNo");
        query.setParameter("formNo", prmsContainerAgreement.getFormNo() + "%");
        query.setParameter("preparedBy", prmsContainerAgreement.getPreparedBy());
        try {
            ArrayList<PrmsContainerAgreement> FormNoList = new ArrayList<>(query.getResultList());

            return FormNoList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsContainerAgreement getLastFormNo() {

        Query query = em.createNamedQuery("PrmsContainerAgreement.findByMaxFormNo");
        PrmsContainerAgreement result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsContainerAgreement) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPortEntry> PortNoList() {

        Query query = em.createNamedQuery("PrmsPortEntry.findAll");
        try {
            ArrayList<PrmsPortEntry> supplier = new ArrayList(query.getResultList());
            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    public PrmsContainerAgreement getFormNumber(String formNo) {
        Query query = em.createNamedQuery("PrmsContainerAgreement.findByFormListNo");
        query.setParameter("formNo", formNo);
        try {
            PrmsContainerAgreement contidlst = (PrmsContainerAgreement) query.getResultList().get(0);
            return contidlst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsContainerAgreement> SearchByFormNum(PrmsContainerAgreement prmsContainerAgreement) {
        Query query = em.createNamedQuery("PrmsContainerAgreement.searchByFormNo", PrmsContainerAgreement.class);
        query.setParameter("storeId", prmsContainerAgreement.getFormNo());

        try {
            ArrayList<PrmsContainerAgreement> formList = new ArrayList(query.getResultList());

            return formList;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsContainerAgreement> searchByFormAndRevisionNum(PrmsContainerAgreement prmsContainerAgreement) {
        String Form = prmsContainerAgreement.getFormNo();
        String Rev = prmsContainerAgreement.getRevisionNo();
        try {
            Query query1 = em.createNativeQuery("SELECT caf.*  "
                    + "FROM PRMS_CONTAINER_AGREEMENT caf          "
                    + "INNER JOIN PRMS_CONTAINER_AGREEMENT mir "
                    + "ON caf.CONTAINER_ID =mir.CONTAINER_ID "
                    + "WHERE mir.FORM_NO ='" + Form + "' "
                    + "AND mir.REVISION_NO Like '" + Rev + "%'",
                    PrmsContainerAgreement.class);
            return (List<PrmsContainerAgreement>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsContainerAgreement> searchByFormAndRevisionNumber(PrmsContainerAgreement prmsContainerAgreement) {
        Query query = em.createNamedQuery("PrmsContainerAgreement.findByFormNumber", PrmsContainerAgreement.class);
        query.setParameter("formNo", prmsContainerAgreement.getFormNo() + '%');
        query.setParameter("revisionNo", prmsContainerAgreement.getRevisionNo() + '%');

        try {

            ArrayList<PrmsContainerAgreement> formList = new ArrayList(query.getResultList());
            return formList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsContainerAgreement> findByParametereRevesion(PrmsContainerAgreement prmsContainerAgreement) {
        Query query = em.createNamedQuery("PrmsContainerAgreement.searchByFormNo", PrmsContainerAgreement.class);
        query.setParameter("revisionNo", prmsContainerAgreement.getRevisionNo() + '%');
        try {

            ArrayList<PrmsContainerAgreement> ContainerList = new ArrayList(query.getResultList());
            return ContainerList;
        } catch (Exception ex) {
            return null;
        }
    }

    public PrmsPortEntry portUpdate(PrmsPortEntry portFrom) {
        Query query = em.createNamedQuery("PrmsPortEntry.findByPortId");
        query.setParameter("portId", portFrom.getPortId());
        System.out.println("selected" + portFrom.getPortId());
        try {
            PrmsPortEntry operatingBudgetInfo = null;
            if (query.getResultList().size() > 0) {
                operatingBudgetInfo = (PrmsPortEntry) query.getResultList().get(0);
            }
            return operatingBudgetInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPortEntry> portTo(PrmsPortEntry portFrom) {
        System.out.println("inside to");
        // Query query = em.createNamedQuery("PrmsPortEntry.findAllByOrder");
        Query query = em.createNamedQuery("PrmsPortEntry.searchByPortTo");
        query.setParameter("portId", portFrom.getPortId());
        System.out.println("tercx=====");
        // System.out.println("Sle id" + portFrom.getPortId());

        try {
            ArrayList<PrmsPortEntry> voyages = new ArrayList<>(query.getResultList());
            System.out.println("to size==" + voyages.size());
            return voyages;
        } catch (Exception e) {
        }
        return null;
    }

    public List<MmsLuWareHouse> listStoreLocations() {
        Query qu = em.createNamedQuery("MmsLuWareHouse.findAll");
        try {
            List<MmsLuWareHouse> sLocationLst = new ArrayList<>();
            if (qu.getResultList().size() > 0) {
                sLocationLst = new ArrayList<>(qu.getResultList());
            }
            System.out.println("list of WareHouses" + sLocationLst);
            return sLocationLst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MmsLuWareHouse changeWareHouses(MmsLuWareHouse mmsLuWareHouse) {
        Query query = em.createNamedQuery("MmsLuWareHouse.findById");
        query.setParameter("id", mmsLuWareHouse.getId());
        try {
            MmsLuWareHouse locationChanged = null;
            if (query.getResultList().size() > 0) {
                locationChanged = (MmsLuWareHouse) (query.getResultList().get(0));
            }
            return locationChanged;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsGoodsEntrance> goodEntranceLists(int approvedStatus) {
        System.out.println("List of Goods Entrance having status===" + approvedStatus);
        Query query = em.createNamedQuery("PrmsGoodsEntrance.findByApprovedStatus");
        query.setParameter("status", approvedStatus);
        try {
            List<PrmsGoodsEntrance> goodsList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                goodsList = query.getResultList();
                System.out.println("selected Goods===" + goodsList);
            }
            return goodsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsContainerAgreement> getcontAgreeReqLists() {
        Query query = em.createNamedQuery("PrmsContainerAgreement.findByReqForApprval");
        try {
            List<PrmsContainerAgreement> contAgreeReqLists = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                contAgreeReqLists = query.getResultList();
            }
            return contAgreeReqLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
     public List<PrmsContainerAgreement> getContainerListsByParameter(PrmsContainerAgreement prmsContainerAgreement) {
        List<PrmsContainerAgreement> colValueLists = new ArrayList<>();
        if (prmsContainerAgreement.getColumnName() != null  && prmsContainerAgreement.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM PRMS_CONTAINER_AGREEMENT\n"
                   + "   WHERE " + prmsContainerAgreement.getColumnName().toLowerCase() + "='" + prmsContainerAgreement.getColumnValue() + "'"
                    + " ", PrmsContainerAgreement.class);
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
            Query query = em.createNamedQuery("PrmsContainerAgreement.findByPreparedBy");
            query.setParameter("preparedBy", prmsContainerAgreement.getPreparedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('PRMS_CONTAINER_AGREEMENT')\n"
                + "   and COLUMN_NAME NOT IN ('IMPORTER','UNDER_BILL_OF_LOADING_NO','REVISION_NO','VESSEL','PORTFK_ID','ATTACHMENT','PORT_TO','PORT_VOYAGE','DRY_PORTPLATE_NUMBER','STATUS','WAREHOUSE_LOCATION')\n"
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

