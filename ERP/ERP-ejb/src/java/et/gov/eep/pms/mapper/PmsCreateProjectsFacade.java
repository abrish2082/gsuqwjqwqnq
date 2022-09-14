/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.pms.entity.PmsCreateProjects;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Binyam
 */
@Stateless
public class PmsCreateProjectsFacade extends AbstractFacade<PmsCreateProjects> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmsCreateProjectsFacade() {
        super(PmsCreateProjects.class);
    }
    public PmsCreateProjects findProjectId(PmsCreateProjects pmsCreateProjects) {
        Query query = em.createNamedQuery("PmsCreateProjects.findByProjectName");
        query.setParameter("projectName", pmsCreateProjects.getProjectName());         
        try {
            PmsCreateProjects ccList = (PmsCreateProjects)query.getResultList().get(0);           
            return ccList;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public  List<PmsCreateProjects> findAllProjects(){
        
       
        try{
             Query query =em.createNativeQuery("select * from pms_create_projects ",PmsCreateProjects.class);
            return (List<PmsCreateProjects>) query.getResultList();
        }
        
        
         catch (Exception ex) {
            return null;
        }
    }
}
