/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.termination;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.termination.HrClearanceSetting;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ob
 */
@Stateless
public class HrClearanceSettingFacade extends AbstractFacade<HrClearanceSetting> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrClearanceSettingFacade() {
        super(HrClearanceSetting.class);
    }
    
     public ArrayList<HrClearanceSetting> getSelectedDepartmentsList() {
        Query query = em.createNamedQuery("HrClearanceSetting.findByStatus");
        try {
            ArrayList<HrClearanceSetting> selectedDepList = new ArrayList(query.getResultList());
            return selectedDepList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
