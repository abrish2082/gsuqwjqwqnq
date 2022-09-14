/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.businessLogic;

import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.pms.mapper.PmsCreateProjectsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class PmsCreateProjectBean implements PmsCreateProjectBeanLocal {

    @EJB
    PmsCreateProjectsFacade pmsCreateProjectsFacade;
   

    @Override
    public List<PmsCreateProjects> findAll() {
        return pmsCreateProjectsFacade.findAll();
    }

    @Override
    public PmsCreateProjects findPojectID(PmsCreateProjects pmsCreateProjects) {
       return pmsCreateProjectsFacade.findProjectId(pmsCreateProjects);
    }

    @Override
    public List<PmsCreateProjects> findAllProjects() {
        return pmsCreateProjectsFacade.findAllProjects();
    }
}
