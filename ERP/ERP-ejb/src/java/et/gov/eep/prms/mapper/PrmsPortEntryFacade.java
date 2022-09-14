/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.prms.entity.PrmsPortEntry;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsPortEntryFacade extends AbstractFacade<PrmsPortEntry> {

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
    public PrmsPortEntryFacade() {
        super(PrmsPortEntry.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    /**
     *
     * @param portName
     * @return
     */
    //Retrieve  Port Lists By Port Name in Lower case
    public ArrayList<PrmsPortEntry> searchPortByPortName(PrmsPortEntry portName) {
        Query query = em.createNamedQuery("PrmsPortEntry.SearchPortName");
        query.setParameter("portName", portName.getPortName().toLowerCase() + '%');
        try {
            ArrayList<PrmsPortEntry> portLst = new ArrayList(query.getResultList());
            return portLst;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     *
     * @param papmsPortEntry
     * @return
     */
    //get  Port Objects By Name
    public PrmsPortEntry getPortName(PrmsPortEntry papmsPortEntry) {
        Query query = em.createNamedQuery("PrmsPortEntry.findByPortName");
        query.setParameter("portName", papmsPortEntry.getPortName());
        try {
            PrmsPortEntry selectedobj = (PrmsPortEntry) query.getResultList().get(0);

            return selectedobj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public PrmsPortEntry getLastPortNo() {

        Query query = em.createNamedQuery("PrmsPortEntry.findByMaxPortNo");

        try {
            PrmsPortEntry directPurcObj = (PrmsPortEntry) query.getResultList().get(0);

            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //Retrieve  Port Lists By Port Name
    public List<PrmsPortEntry> searchPort(PrmsPortEntry prmsPortEntry) {
       List<PrmsPortEntry> prmsPortEntryList = new ArrayList();
        if (prmsPortEntry.getColumnName() != null && !prmsPortEntry.getColumnName().equals("")
                && prmsPortEntry.getColumnValue() != null && !prmsPortEntry.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_PORT_ENTRY\n"
                    + "where " + prmsPortEntry.getColumnName().toLowerCase() + " = '" + prmsPortEntry.getColumnValue() + "'"
                    + "and " + prmsPortEntry.getPreparedBy() + "='" + prmsPortEntry.getPreparedBy() + "'", PrmsPortEntry.class);
            try {
                if (query.getResultList().size() > 0) {
                    prmsPortEntryList = query.getResultList();
                }
                return prmsPortEntryList;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsPortEntry.findByPreparedBy");
            query.setParameter("preparedBy", prmsPortEntry.getPreparedBy());
            prmsPortEntryList = query.getResultList();
            return prmsPortEntryList;
        }
    }

    //Retrieve  Port Lists By Type
    public ArrayList<PrmsPortEntry> getCapitalBudgetDisbursment(PrmsPortEntry capitalBudget) {
        Query query = em.createNamedQuery("PrmsPortEntry.findByPortType");
        query.setParameter("portType", capitalBudget.getPortType() + '%');
        try {
            ArrayList<PrmsPortEntry> portLst = new ArrayList(query.getResultList());
            return portLst;
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<PrmsPortEntry> approveMaintReqList() {
        Query query = em.createNamedQuery("PrmsPortEntry.findByPortTypee");

        try {
            ArrayList<PrmsPortEntry> pmrs = new ArrayList(query.getResultList());
            return pmrs;
        } catch (Exception ex) {
            return null;
        }

    }

    //Retrieve Dry Type Port Lists
    public ArrayList<PrmsPortEntry> DryPortList() {
        Query query = em.createNamedQuery("PrmsPortEntry.findByDryPortNameList");
        try {
            ArrayList<PrmsPortEntry> DryPorts = new ArrayList(query.getResultList());
            return DryPorts;

        } catch (Exception ex) {
            return null;
        }

    }

    public PrmsPortEntry getSelectedRequest(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsPortEntry.findByPortId");
        query.setParameter("portId", id);
        try {
            PrmsPortEntry selectrequest = (PrmsPortEntry) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsPortEntry> searchBidderRegistration() {

        Query query = em.createNamedQuery("PrmsPortEntry.findAllByStatus");

        try {
            ArrayList<PrmsPortEntry> bissubmision = new ArrayList(query.getResultList());

            return bissubmision;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPortEntry> getNextPortNo(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsPortEntry.findByPortNoLike");
        query.setParameter("portNo", prefix + "-" + '%' + "/" + eYear);
        List<PrmsPortEntry> prmsPortEntryLists = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            prmsPortEntryLists = query.getResultList();
        }
        return prmsPortEntryLists;
    }
    // </editor-fold>

    public List<PrmsPortEntry> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_PORT_ENTRY') \n"
                + "and column_name not in ('PORT_ID','STATUS','REMARK','CURRENT_STATUS')");
        try {
            List<PrmsPortEntry> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
