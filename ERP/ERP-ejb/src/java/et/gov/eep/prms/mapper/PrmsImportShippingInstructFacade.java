/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.prms.entity.PrmsClaimRecordingForm;
import et.gov.eep.prms.entity.PrmsImportShippingInstruct;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PrmsImportShippingInstructFacade extends AbstractFacade<PrmsImportShippingInstruct> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsImportShippingInstructFacade() {
        super(PrmsImportShippingInstruct.class);
    }

    public List<PrmsImportShippingInstruct> getgetISINo(PrmsImportShippingInstruct prmsImportShippingInstruct) {
        Query query = em.createNamedQuery("PrmsImportShippingInstruct.findByIsiNoLike");
        query.setParameter("isiNo", prmsImportShippingInstruct.getIsiNo() + '%');
        query.setParameter("preparedBy", prmsImportShippingInstruct.getPreparedBy());

        try {
            ArrayList<PrmsImportShippingInstruct> importShippingInstructs = new ArrayList<>(query.getResultList());
            return importShippingInstructs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsImportShippingInstruct getSelectedId(String id) {
        Query query = em.createNamedQuery("PrmsImportShippingInstruct.findById");
        query.setParameter("id", id);
        try {
            PrmsImportShippingInstruct idlst = (PrmsImportShippingInstruct) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public PrmsImportShippingInstruct getLastISINo() {
        Query query = em.createNamedQuery("PrmsImportShippingInstruct.findByMaxId");
        PrmsImportShippingInstruct result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsImportShippingInstruct) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrDepartments getHrNames(int key) {

        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", key);
        try {
            HrDepartments selectdepartment = (HrDepartments) query.getResultList().get(0);
            return selectdepartment;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsImportShippingInstruct> getImportshipsRequested() {
        Query query = em.createNamedQuery("PrmsImportShippingInstruct.findByReqForApprval");
        ArrayList<PrmsImportShippingInstruct> importshipsRequested = new ArrayList<>(query.getResultList());
        return importshipsRequested;
    }

    public List<PrmsLcRigistration> findApprovedLc(int approvedLc) {
        System.out.println("Status=======   " + approvedLc);
        Query q = em.createNamedQuery("PrmsLcRigistration.findByApprovedStatus");
        q.setParameter("status", approvedLc);
        try {
            List<PrmsLcRigistration> approvedLcLists = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                approvedLcLists = q.getResultList();
            }
            return approvedLcLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<PrmsLcRigistrationAmend> checkingAsLcIsAmendedByLcId(PrmsLcRigistration prmsLcRigistration) {
        System.out.println("here checking by " + prmsLcRigistration.getLcId());
        Query q = em.createNativeQuery("SELECT * FROM prms_lc_rigistration_amend lcamd\n"
                + "inner join (SELECT lc_id ,max(id) as lcAmendMaxId  \n"
                + "FROM prms_lc_rigistration_amend\n"
                + "GROUP BY lc_id)lcamd2\n"
                + "on  lcamd.id=lcamd2.lcAmendMaxId\n"
                + "inner join prms_lc_rigistration lc\n"
                + "on lc.lc_id=lcamd.lc_id\n"
                + "where lc.lc_id='" + prmsLcRigistration.getLcId() + "'", PrmsLcRigistrationAmend.class);
        try {
            List<PrmsLcRigistrationAmend> lcNoAmendedList = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                lcNoAmendedList = q.getResultList();
                System.out.println("Number of Amended with" + prmsLcRigistration.getLcId() + " Lc Id is " + lcNoAmendedList.size());
            }
            return lcNoAmendedList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrmsLcRigistrationAmend getLcAmendedInfoByLcId(PrmsLcRigistration prmsLcRigistration) {
        System.out.println("get info by " + prmsLcRigistration.getLcId());
        Query query = em.createNativeQuery("SELECT * FROM prms_lc_rigistration_amend lcamd\n"
                + "inner join(SELECT max(id) as lcAmendedMaxId\n"
                + "FROM prms_lc_rigistration_amend )lcamd2\n"
                + "on lcamd.id=lcamd2.lcAmendedMaxId\n"
                + "where lcamd.lc_id='" + prmsLcRigistration.getLcId() + "'", PrmsLcRigistrationAmend.class);
        try {
            PrmsLcRigistrationAmend amendedLcInfo = new PrmsLcRigistrationAmend();
            if (query.getResultList().size() > 0) {
                amendedLcInfo = (PrmsLcRigistrationAmend) (query.getResultList().get(0));
                System.out.println("Lc Amount from Amended " + amendedLcInfo.getLcAmount());
            }
            return amendedLcInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
  public List<PrmsImportShippingInstruct> getShippingListsByParameter(PrmsImportShippingInstruct prmsImportShippingInstruct) {
        List<PrmsImportShippingInstruct> colValueLists = new ArrayList<>();
        if (prmsImportShippingInstruct.getColumnName() != null  && prmsImportShippingInstruct.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM PRMS_IMPORT_SHIPPING_INSTRUCT\n"
                   + "   WHERE " + prmsImportShippingInstruct.getColumnName().toLowerCase() + "='" + prmsImportShippingInstruct.getColumnValue() + "'"
                    + " ", PrmsImportShippingInstruct.class);
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
            Query query = em.createNamedQuery("PrmsImportShippingInstruct.findgetPreparedBy");
            query.setParameter("getPreparedBy", prmsImportShippingInstruct.getPreparedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('PRMS_IMPORT_SHIPPING_INSTRUCT')\n"
                + "   and COLUMN_NAME NOT IN ('ADDRESS','INSTRUCTION_TO_CLEAR','DISCHARGE_AT','PER_MV','SUPPLIER_ADDRESS','ON_','SUPPLIER_INVOICE','OBL_DUTY_ENDORSE_TO_YOU','INSURANCE_DEBIT_NOTE','CERTIFICATE_OF_ORIGIN_NO','BANK_PERMIT_NO','DUTY_EXEMOTION_LETTER_NO','PACKAGE_LIST','OCEAN_FREIGHT_INVOICE_NO','RELIEF_OR_GIFT_CERTIFICATE_NO','IMPORT_LICENSE_NO','BOND_OR_LETTER_OF_GARANTEE_NO','CONTAINER_RELEASE','OTHER_DOCUMENT','CUSTOM_DUTY_TO_BE_ASSESED_AS_','PLEASE_SECURE','GOODS_ARE_TO_BE_LOADED','AA_CUSTOMS','SIGNATURE','REMARK','BL','CITY','ID','RECEIPT_NO','IN_CASE_OF_VEHICLES','FROM_DEP_ID','LC_ID','LAND_FREIGHT_INVOICE_NO','FILE_REFERENCE_NUMBER','STATUS')\n"
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



